package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.File;
import com.download.mapper.FileMapper;
import com.download.mapper.SettingMapper;
import com.download.server.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,File> implements FileService {

    final FileMapper fileMapper;

    @Autowired
    private FileService fileService;
    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {this.fileMapper = fileMapper;
    }


    @Override
    public ResponseResult fetchFileList(List<Long> fileId) {
        return null;
    }


    @Override
    public ResponseResult fetchFilterFile(Long fileId, String filePath, String filter) {
        List<List<File>> listList=new ArrayList<>();
        List<File> file= (List<File>) fileMapper.selectById(fileId);
        if(filter!=null){
            listList.add(file);
        }
        return ResponseResult.ok("过滤文件列表");
    }

    @Override
    public ResponseResult getByPath() {
        return null;
    }

}
