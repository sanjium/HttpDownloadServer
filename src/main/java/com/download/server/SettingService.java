package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.domain.Setting;

public interface SettingService  {

    Setting addSetting(Setting settingId);

    Setting get(long settingId);

    Setting save(Setting setting);
}
