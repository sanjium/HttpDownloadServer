package com.download.service;

import com.download.entity.dto.SendTransferMsgDTO;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
public interface AsyncService {
    /**
     * 执行异步任务
     */
    void executeDownloadAsync(SendTransferMsgDTO sendTransferMsgDTO);
}
