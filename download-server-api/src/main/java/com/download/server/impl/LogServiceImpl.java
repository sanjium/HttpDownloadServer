package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.domain.Log;
import com.download.mapper.LogMapper;
import com.download.server.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
}
