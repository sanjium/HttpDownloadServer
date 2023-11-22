package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.mapper.TransferMapper;
import com.download.server.TransferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements TransferService {
    @Override
    public ResponseResult submitTransfer(List<Transfer> transfers) {
        return ResponseResult.ok("访问提交接口成功");
    }

    @Override
    public ResponseResult pauseTransfer(List<Long> ids) {
        return ResponseResult.ok("访问暂停/启动任务接口成功");
    }

    @Override
    public ResponseResult deleteTransfer(List<Long> ids) {
        return ResponseResult.ok("访问删除任务接口成功");
    }

    @Override
    public ResponseResult refreshTransfer(List<Long> ids) {
        return ResponseResult.ok("访问重新提交任务接口成功");
    }

    @Override
    public ResponseResult updateThreadTransfer(Long id, Integer count) {
        return ResponseResult.ok("访问更改任务下载线程数接口成功");
    }
}
