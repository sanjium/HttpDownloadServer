package com.download.listener;

import com.alibaba.fastjson.JSON;



import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Component
@Slf4j
public class KafkaTaskListener {

    /**
     * MQ主题：提交任务
     */
    public static final String TOPIC_DOWNLOAD_SUBMIT = "download_submit";

    @Resource
    private AsyncService asyncService;

    //监听download队列
    @KafkaListener(topics = TOPIC_DOWNLOAD_SUBMIT,groupId = "group_download_submit")
    public void downloadListener(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }
        log.info("收到消息：" + message);
        SendTransferMsgDTO sendTransferMsgDTO = JSON.parseObject((String)message.get(), SendTransferMsgDTO.class);
        asyncService.executeDownloadAsync(sendTransferMsgDTO);

        //4. 消息消费完成
        ack.acknowledge();
    }
}
