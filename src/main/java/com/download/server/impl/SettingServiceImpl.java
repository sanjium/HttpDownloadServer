package com.download.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.mapper.SettingMapper;
import com.download.server.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
    @Autowired
    private SettingMapper settingMapper;
    @Override
    public ResponseResult getByPath(String path) {
       /* LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setting::getDownloadPath, path);
        Setting setting = settingMapper.selectOne(wrapper);
        File file = new File(setting.getName(), StringUtils.isEmpty(setting), setting.getDownloadPath(), setting.getSize(), setting.getCreateAt());
        return ResponseResult.ok(file);*/
        return null;
    }
}
