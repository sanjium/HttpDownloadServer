package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.domain.Setting;
import com.download.mapper.SettingMapper;
import com.download.server.SettingService;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
}
