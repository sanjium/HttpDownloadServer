package com.download.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/sse")
    public SseEmitter sse() throws IOException {
        SseEmitter sseEmitter = new SseEmitter();
        //发送一个注释,响应前端请求
        sseEmitter.send(SseEmitter.event().comment("hello world"));
        return sseEmitter;
    }

}
