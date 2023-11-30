package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.ResponseResult;
import com.download.entity.vo.FileVO;

import java.util.List;

public interface FileService{

    List<FileVO> getFileList(String path, String filter);

}
