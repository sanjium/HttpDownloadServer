package com.download.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.download.entity.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {
}
