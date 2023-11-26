package com.download.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.download.entity.ResponseResult;
import com.download.entity.domain.File;
import com.download.mapper.FileMapper;
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

    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    @Override
    public ResponseResult fetchFileList(List<Long> fileId) {
        List<File> files=fileMapper.selectBatchIds(fileId);
        List<List<File>> fileList=new ArrayList<>();
        for(File file:files){
            if(file.getFilePath()==null||file.getFilePath()=="/file"){
                fileList.add((List<File>) new File(file.getFileName(), file.isFileIsDirectory(), file.getFilePath(),file.getFileCreatTime()));
            }else {
                List<String> filePaths = new ArrayList<>();
                filePaths.add(file.getFilePath());
                for (String filepath : filePaths) {
                    fileList.add((List<File>) new File(file.getFileName(), file.isFileIsDirectory(), file.getFilePath(), file.getFileCreatTime()));
                }
            }
        }
        return ResponseResult.ok("访问文件列表");
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
    public ResponseResult sortFileList(Long fileId,String filePath, String sort) {
        File file= fileMapper.selectById(fileId);
        fileService.sortFileList(fileId, file.getFilePath(),sort);
        return ResponseResult.ok("文件排序");
    }

}
