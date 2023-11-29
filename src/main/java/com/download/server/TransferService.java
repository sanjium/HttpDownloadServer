package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;

import java.util.List;

public interface TransferService extends IService<Transfer> {
    ResponseResult submitTransfer(List<Transfer> transfers);

    ResponseResult pauseTransfer(List<Long> ids);

    ResponseResult deleteTransfer(List<Long> ids);

    ResponseResult refreshTransfer(List<Long> ids);

    ResponseResult<String> updateThreadTransfer(Long id, Integer count);

    ResponseResult getTasks(Integer pageNum, Integer pageSize);
}
