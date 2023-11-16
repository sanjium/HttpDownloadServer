package com.download.controller;

import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @PostMapping("/submit")
    @LogAnnotation(module = "任务列表模块", operation = "提交任务")
    public ResponseResult submitTransfer(@RequestBody Transfer transfer) {
        return ResponseResult.ok("访问提交接口成功");
    }

    @PostMapping("/pause")
    @LogAnnotation(module = "任务列表模块", operation = "暂停/启动任务")
    public ResponseResult pauseTransfer(@RequestParam Long id) {
        return ResponseResult.ok("访问暂停/启动任务接口成功");
    }

    @PostMapping("/delete")
    @LogAnnotation(module = "任务列表模块", operation = "删除任务")
    public ResponseResult deleteTransfer(@RequestParam Long id) {
        return ResponseResult.ok("访问删除任务接口成功");
    }

    @PostMapping("/refresh")
    @LogAnnotation(module = "任务列表模块", operation = "重新提交任务")
    public ResponseResult refreshTransfer(@RequestParam Long id) {
        return ResponseResult.ok("访问重新提交任务接口成功");
    }

    @PostMapping("/updateThread")
    @LogAnnotation(module = "任务列表模块", operation = "更改任务下载线程数")
    public ResponseResult updateThreadTransfer(@RequestParam Long id) {
        return ResponseResult.ok("访问更改任务下载线程数接口成功");
    }

}
