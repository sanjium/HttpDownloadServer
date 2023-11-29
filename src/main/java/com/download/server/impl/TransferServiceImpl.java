package com.download.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.entity.vo.GetTasksVO;
import com.download.mapper.TransferMapper;
import com.download.server.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements TransferService {

    final TransferMapper transferMapper;

    @Autowired
    public TransferServiceImpl(TransferMapper transferMapper) {
        this.transferMapper = transferMapper;
    }

    @Override
    public ResponseResult<String> submitTransfer(List<Transfer> transfers) {
        for (Transfer transfer : transfers) {
            transferMapper.insert(transfer);
        }
        return ResponseResult.ok("访问提交接口成功");
    }

    @Override
    public ResponseResult<String> pauseTransfer(List<Long> ids) {
        List<Transfer> transfers = transferMapper.selectBatchIds(ids);
        for (Transfer transfer : transfers) {
            if (transfer.getStatus().equals("1")) {
                transfer.setStatus("0");
            } else {
                transfer.setStatus("1");
            }
            transferMapper.insert(transfer);
        }
        return ResponseResult.ok("访问暂停/启动任务接口成功");
    }

    @Override
    public ResponseResult<String> deleteTransfer(List<Long> ids) {
        transferMapper.deleteBatchIds(ids);
        return ResponseResult.ok("访问删除任务接口成功");
    }

    @Override
    public ResponseResult<String> refreshTransfer(List<Long> ids) {
        List<Transfer> transfers = transferMapper.selectBatchIds(ids);
        //TODO
        return ResponseResult.ok("访问重新提交任务接口成功");
    }

    @Override
    public ResponseResult<String> updateThreadTransfer(Long id, Integer count) {
        Transfer transfer = transferMapper.selectById(id);
        transfer.setThreads(count);
        transferMapper.insert(transfer);
        return ResponseResult.ok("访问更改任务下载线程数接口成功");
    }

    @Override
    public ResponseResult getTasks(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Transfer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<Transfer> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        List<Transfer> records = page.getRecords();
        Integer total = records.size();
        GetTasksVO getTasksVO = new GetTasksVO(total, records);
        return ResponseResult.ok(getTasksVO);
    }
}
