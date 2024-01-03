package com.download.controller;

import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.TaskScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskScheduleService service;


    @GetMapping("/status")
    public void getTaskStatus(@RequestBody SendTransferMsgDTO sendTransferMsgDTO){
        service.taskPolling(sendTransferMsgDTO);
    }
}
