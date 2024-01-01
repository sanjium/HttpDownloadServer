package com.download.controller;

import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.entity.dto.GetTasksDTO;
import com.download.entity.dto.SubmitTransferDTO;
import com.download.entity.dto.UpdateThreadTransferDTO;
import com.download.server.TransferService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/submit")
    @LogAnnotation(module = "任务列表模块", operation = "提交任务")
    public ResponseResult submitTransfer(@RequestBody SubmitTransferDTO submitTransferDTO) {
        return transferService.submitTransfer(submitTransferDTO.getUrl());
    }

    @PostMapping("/pause")
    @LogAnnotation(module = "任务列表模块", operation = "暂停/启动任务")
    public ResponseResult pauseTransfer(@RequestBody List<Long> ids) {
        return transferService.pauseTransfer(ids);
    }

    @PostMapping("/delete")
    @LogAnnotation(module = "任务列表模块", operation = "删除任务")
    public ResponseResult deleteTransfer(@RequestBody List<Long> ids) {
        return transferService.deleteTransfer(ids);
    }

    @PostMapping("/refresh")
    @LogAnnotation(module = "任务列表模块", operation = "重新提交任务")
    public ResponseResult refreshTransfer(@RequestBody List<Long> ids) {
        return transferService.refreshTransfer(ids);
    }

    @PostMapping("/update_thread")
    @LogAnnotation(module = "任务列表模块", operation = "更改任务下载线程数")
    public ResponseResult<String> updateThreadTransfer(@RequestBody UpdateThreadTransferDTO updateThreadTransferDTO) {
        return transferService.updateThreadTransfer(updateThreadTransferDTO.getId(), updateThreadTransferDTO.getThreads());
    }



    @PostMapping("/get_tasks")
    @LogAnnotation(module = "任务列表模块", operation = "获取传输任务列表")
    public ResponseResult getTasks(@RequestBody GetTasksDTO getTasksDTO) {
        System.out.println(getTasksDTO);
        return transferService.getTasks(getTasksDTO.getCurrentPage(), getTasksDTO.getLimit(),getTasksDTO.getStatus());
    }

}
