package com.download.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.download.entity.domain.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
}
