package com.download.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;

import com.download.server.SettingService;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Resource
    private SettingService settingService;

    @GetMapping()
    @LogAnnotation(operation = "返回")
    public ResponseResult<List<Setting>> fetchSettings(){
        Page<Setting> page = new Page<>();
        page.setSize(2);
        Page<Setting> page1 = settingService.page(page);
        return ResponseResult.ok(page1.getRecords());
    }

    @PostMapping("/saveSettings")
    @ResponseBody
    public ResponseResult saveSetting(@RequestBody Setting setting){
        settingService.save(setting);
        return ResponseResult.ok("true");
    }
}
