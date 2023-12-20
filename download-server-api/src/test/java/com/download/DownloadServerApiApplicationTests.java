package com.download;

import com.alibaba.fastjson.JSON;
import com.download.config.RabbitmqConfig;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.entity.dto.SendTransferMsgDTO;
import com.download.server.TransferService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DownloadServerApiApplicationTests {

    @Resource
    private TransferService transferService;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    void contextLoads() {
        ResponseResult tasks = transferService.getTasks(0, 5, "");
        System.out.println(tasks.getData());
    }

    @Test
    void sendMessage() {
        SendTransferMsgDTO transferMsgDTO = new SendTransferMsgDTO(1L, "https://plugins.jetbrains.com/files/6954/442937/kotlin-plugin-232-1.9.21-release-633-IJ10072.27.zip?updateId=442937&pluginId=6954&family=INTELLIJ");
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, RabbitmqConfig.ROUTINGKEY_DOWNLOAD, JSON.toJSONString(transferMsgDTO));
    }

}
