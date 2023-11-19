package com.download.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.download.entity.domain.File;
import com.download.entity.domain.Log;

import java.util.List;

public interface FileService  {
    String addFile(long fileId);

    String get(long fileId);

    List<File> getAll();
}
