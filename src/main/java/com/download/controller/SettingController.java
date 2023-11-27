package com.download.controller;

import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.entity.dto.SettingDTO;

import com.download.server.SettingService;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping("/setting")
public class SettingController {
    @Resource
    private SettingService settingService;

    @GetMapping("/{id}")
    @LogAnnotation(operation = "返回")
    public ResponseResult<SettingDTO> fetchSettings(@PathVariable Long id ){
        Setting setting = settingService.getById(id);
        SettingDTO settingDTO = new SettingDTO();
        BeanUtils.copyProperties(setting,settingDTO);
        return ResponseResult.ok(settingDTO);
    }


    @PostMapping("/saveSettings")
    @ResponseBody
    public ResponseResult saveSetting(@RequestBody Setting setting){
        settingService.save(setting);
        return ResponseResult.ok("true");
    }
}
