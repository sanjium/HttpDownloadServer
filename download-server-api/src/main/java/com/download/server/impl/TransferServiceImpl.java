package com.download.server.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.entity.dto.SendTransferMsgDTO;
import com.download.entity.vo.GetTasksVO;
import com.download.mapper.TransferMapper;
import com.download.server.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements TransferService {

    final TransferMapper transferMapper;

    final TransferUpdateService transferUpdateService;

    final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public TransferServiceImpl(TransferMapper transferMapper, TransferUpdateService transferUpdateService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.transferMapper = transferMapper;
        this.transferUpdateService = transferUpdateService;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * MQ主题：提交任务
     */
    public static final String TOPIC_DOWNLOAD_SUBMIT = "download_submit";

    @Override
    public ResponseResult<String> submitTransfer(String url) {
        Transfer transfer = new Transfer();
        transfer.setType("http");
        transfer.setName("测试名称");
        transfer.setUrl(url);
        transfer.setStatus("downloading");
        transfer.setProgress("0");
        transfer.setSpeed("0KB");
        transfer.setSize("1.2GB");
        transfer.setDownloadSpeed("333KB");
        transfer.setThreads(2);
        transfer.setTimeLeft("0m 0s");
        transfer.setFinishedAt(LocalDateTime.now().plusHours(1));
        boolean isSave = save(transfer);
        if (isSave) {
            SendTransferMsgDTO transferMsgDTO = new SendTransferMsgDTO(transfer.getId(), transfer.getUrl());
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_DOWNLOAD_SUBMIT, JSON.toJSONString(transferMsgDTO));
            log.info("发送消息:{}", JSON.toJSONString(transferMsgDTO));
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    log.info("消息发送成功!");
                }
                @Override
                public void onFailure(Throwable ex) {
                    log.info("消息发送失败!!");
                }
            });
        }
        return ResponseResult.ok("访问提交接口成功");
    }

    @Override
    public ResponseResult<String> pauseTransfer(List<Long> ids) {
        List<Transfer> transfers = transferMapper.selectBatchIds(ids);
        transfers.forEach(transfer -> {
            if (transfer.getStatus().equals("downloading")) {
                transfer.setStatus("pending");
            } else {
                transfer.setStatus("downloading");
            }
        });
        transferUpdateService.updateBatchById(transfers);
        return ResponseResult.ok("访问暂停/启动任务接口成功");
    }

    @Override
    public ResponseResult<String> deleteTransfer(List<Long> ids) {
        transferMapper.deleteBatchIds(ids);
        return ResponseResult.ok("访问删除任务接口成功");
    }

    @Override
    public ResponseResult refreshTransfer(List<Long> ids) {
        List<Transfer> transfers = transferMapper.selectBatchIds(ids);
        transfers.forEach(transfer -> {
            transfer.setStatus("downloading");
            transfer.setProgress("0");
            transfer.setSpeed("0KB");
            transfer.setTimeLeft("0m 0s");
            transfer.setCreatedAt(LocalDateTime.now());
        });
        boolean isRefresh = transferUpdateService.updateBatchById(transfers);
        if (isRefresh) {
            return ResponseResult.ok("访问重新提交任务接口成功");
        } else {
            return ResponseResult.error("访问重新提交任务接口失败");
        }

    }

    @Override
    public ResponseResult updateThreadTransfer(Long id, Integer count) {
        Transfer transfer = transferMapper.selectById(id);
        transfer.setThreads(count);
        boolean isUpdate = updateById(transfer);
        if (isUpdate) {
            return ResponseResult.ok("访问更改任务下载线程数接口成功");
        } else {
            return ResponseResult.error("访问更改任务下载线程数接口失败");
        }

    }

    @Override
    public ResponseResult getTasks(Integer pageNum, Integer pageSize, String status) {
        LambdaQueryWrapper<Transfer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(getAllTasks(status), Transfer::getStatus, status);
        Page<Transfer> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        List<Transfer> records = page.getRecords();
        records.forEach(transfer -> {
            String transferStatus = transfer.getStatus();
            switch (transferStatus) {
                case "downloading":
                    String size = transfer.getSize();
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    double downloadSpeed = Double.parseDouble(p.matcher(transfer.getDownloadSpeed()).replaceAll("").trim());
                    double parseSize = Double.parseDouble(p.matcher(size).replaceAll("").trim());
                    if (size.endsWith("KB")) {
                        double remainingTime = (parseSize * (1 - (Double.parseDouble(transfer.getProgress()) / 100))) / downloadSpeed;
                        transfer.setRemainingTime(Double.toString(remainingTime));
                    } else if (size.endsWith("MB")) {
                        double remainingTime = (parseSize * 1024 * (1 - (Double.parseDouble(transfer.getProgress()) / 100))) / downloadSpeed;
                        transfer.setRemainingTime(Double.toString(remainingTime));
                    } else if (size.endsWith("GB")) {
                        double remainingTime = (parseSize * 1024 * 1024 * (1 - (Double.parseDouble(transfer.getProgress()) / 100))) / downloadSpeed;
                        transfer.setRemainingTime(Double.toString(remainingTime));
                    }
                    break;
                case "downloaded":
                    LocalDateTime finishedAt = transfer.getFinishedAt();
                    LocalDateTime createdAt = transfer.getCreatedAt();
                    long remainingTime = Math.abs(finishedAt.until(createdAt, ChronoUnit.SECONDS));
                    transfer.setRemainingTime(remainingTime + "s");
                    break;
                case "pending":
                    transfer.setRemainingTime(null);
                    break;
            }
        });
        Long total = page.getTotal();
        GetTasksVO getTasksVO = new GetTasksVO(total, records);
        return ResponseResult.ok(getTasksVO);
    }

    public Boolean getAllTasks(String status) {
        return !StringUtils.isBlank(status) && !status.equals("all");
    }
}
