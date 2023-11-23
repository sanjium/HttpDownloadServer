package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.domain.File;
import com.download.mapper.FileMapper;
import com.download.server.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,File> implements FileService {

}
