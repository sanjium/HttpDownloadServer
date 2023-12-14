package com.download.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.entity.dto.SettingDTO;
import com.download.server.SettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping()
    @LogAnnotation(operation = "返回")
    public ResponseResult<List<SettingDTO>> fetchSettings(){
        Page<Setting> page = new Page<>();
        Page<Setting> page1 = settingService.page(page);
        ArrayList<SettingDTO> list = new ArrayList<>();
        for (Setting record : page1.getRecords()) {
            SettingDTO settingVO = new SettingDTO();
            BeanUtils.copyProperties(record, settingVO);
            list.add(settingVO);
        }
        return ResponseResult.ok(list);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseResult saveSetting(@RequestBody SettingDTO settingDTO){
        LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        settingService.remove(wrapper);
        Setting setting = new Setting();
        BeanUtils.copyProperties(settingDTO, setting);
        setting.setCreateAt(new Date());
        settingService.save(setting);
        return ResponseResult.ok("true");
    }
}
