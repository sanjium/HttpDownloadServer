package com.download.controller;

import com.download.config.WebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/14
 */
@Controller
@RequestMapping("/test")
public class TestController {
    /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8081/test/toWebSocketDemo/user-1
     *
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/toWebSocketDemo/{cid}")
    public String toWebSocketDemo(@PathVariable String cid, Model model) {
        model.addAttribute("cid", cid);
        return "websocketTest";
    }

}
