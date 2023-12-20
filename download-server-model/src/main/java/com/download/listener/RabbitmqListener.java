package com.download.listener;

import com.alibaba.fastjson.JSON;
import com.download.config.RabbitmqConfig;


import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.AsyncService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Component
public class RabbitmqListener {

    @Resource
    private AsyncService asyncService;

    //监听download队列
    @RabbitListener(queues = RabbitmqConfig.QUEUE_INFORM_DOWNLOAD)
    public void downloadListener(String message) {
        System.out.println(message);
        SendTransferMsgDTO sendTransferMsgDTO = JSON.parseObject(message, SendTransferMsgDTO.class);
        asyncService.executeDownloadAsync(sendTransferMsgDTO);
    }
}
