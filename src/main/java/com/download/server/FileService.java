package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.ResponseResult;
import com.download.entity.domain.File;
import com.download.mapper.FileMapper;

import java.util.List;

public interface FileService  extends IService<File>{

    ResponseResult fetchFileList(List<Long> fileId);

    ResponseResult fetchFilterFile(Long fileId, String filePaths, String filter);

    ResponseResult sortFileList(Long FileId,String filePath,String sort);
}
