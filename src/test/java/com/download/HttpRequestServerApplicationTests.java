package com.download;

import com.download.entity.ResponseResult;
import com.download.entity.domain.Transfer;
import com.download.server.TransferService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HttpRequestServerApplicationTests {

    @Resource
    private TransferService transferService;


    @Test
    void contextLoads() {
        ResponseResult tasks = transferService.getTasks(0, 5,"");
        System.out.println(tasks.getData());
    }

}
