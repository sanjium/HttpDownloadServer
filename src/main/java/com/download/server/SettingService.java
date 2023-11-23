package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.domain.Setting;
import com.download.entity.dto.SettingDTO;
import com.download.mapper.SettingMapper;
import org.apache.ibatis.annotations.Select;

public interface SettingService  extends IService<Setting> {

}
