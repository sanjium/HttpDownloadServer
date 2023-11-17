package com.download.controller;

import com.download.aop.LogAnnotation;
import com.download.entity.domain.Setting;
import com.download.server.SettingService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Setting")
public class SettingController {
    @Resource
    private Setting setting;
    @Resource
    private SettingService settingService;

    @GetMapping
    @LogAnnotation(operation = "返回")
    public Setting fetchSettings(Model model){
        Setting settings=downloadSetting();
        model.addAttribute("settings",settings);
        return null;
       // return "setting";
    }

    @GetMapping("/downloadSetting")
    @ResponseBody
    public Setting downloadSetting(){

        long settingId=setting.getSettingId();
        setting=settingService.get(settingId);
        /*if(setting==null){

        }*/
        return setting;

    }

    @GetMapping("/saveSettings")
    @ResponseBody
    public boolean saveSetting(){
        long settingId=setting.getSettingId();
        setting=settingService.get(settingId);
        settingService.save(setting);
        return true;
    }
}
