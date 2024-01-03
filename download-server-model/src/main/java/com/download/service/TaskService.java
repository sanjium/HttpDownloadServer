package com.download.service;

import com.download.entity.dto.SendTransferMsgDTO;

public interface TaskService {


    //分片
    int partition(Long size);
    //下载
    void downLoadFile(SendTransferMsgDTO sendTransferMsgDTO , String destination );
    //下载
    void downLoadChunk(String url,String destination,int startByte,int endByte);

}
