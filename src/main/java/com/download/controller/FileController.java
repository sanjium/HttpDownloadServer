package com.download.controller;


import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.vo.FileVO;
import com.download.server.FileService;
import com.download.server.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/fetchFilterFile")
    @LogAnnotation(operation = "查看文件")
    public ResponseResult fetchFilterFile(@RequestParam String path, @RequestParam String filter) {
        List<FileVO> fileList = fileService.getFileList(path, filter);
        return ResponseResult.ok(fileList);
    }
    /*
    内存从小到大
     */
    @PostMapping("/sortFileList")
    @LogAnnotation(operation = "文件排序")
    public ResponseResult sortFileList(@RequestBody Long fileId,@RequestBody String filePath,@RequestBody String sort){
        return null;
    }

}
