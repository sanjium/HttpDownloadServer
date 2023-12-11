package com.download.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;

import com.download.entity.vo.SettingVO;
import com.download.server.SettingService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping()
    @LogAnnotation(operation = "返回")
    public ResponseResult<List<SettingVO>> fetchSettings(){
        Page<Setting> page = new Page<>();

        Page<Setting> page1 = settingService.page(page);
        ArrayList<SettingVO> list = new ArrayList<>();
        for (Setting record : page1.getRecords()) {
            SettingVO settingVO = new SettingVO();
            BeanUtils.copyProperties(record, settingVO);
            list.add(settingVO);
        }
        return ResponseResult.ok(list);
    }

    @PostMapping("/savesettings")
    @ResponseBody
    public ResponseResult saveSetting(@RequestBody Setting setting){
        setting.setCreateAt(new Date());
        settingService.save(setting);
        return ResponseResult.ok("true");
    }
}
