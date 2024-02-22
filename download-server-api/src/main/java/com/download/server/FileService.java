package com.download.server;

import com.download.entity.vo.FileVO;

import java.util.List;

public interface FileService{

    List<FileVO> getFileList(String path, String type);

    List<FileVO> sortFileList(String path, String sort,String order);
}
