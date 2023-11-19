package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.domain.File;
import com.download.entity.domain.Log;
import com.download.entity.domain.Transfer;
import com.download.mapper.FileMapper;
import com.download.mapper.LogMapper;
import com.download.mapper.TransferMapper;

import com.download.server.FileService;
import com.download.server.LogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String addFile(long fileId) {
        return null;
    }

    @Override
    public String get(long fileId) {
        return null;
    }

    @Override
    public List<File> getAll() {
        return null;
    }

}
