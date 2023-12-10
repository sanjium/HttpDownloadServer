package com.download.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.download.entity.domain.Setting;
import com.download.entity.vo.SettingVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingMapper extends BaseMapper<Setting> {

}
