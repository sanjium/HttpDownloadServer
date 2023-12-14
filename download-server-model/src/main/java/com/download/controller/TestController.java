package com.download.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/14
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "测试";
    }
}
