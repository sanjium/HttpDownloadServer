package com.download.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.entity.vo.FileVO;
import com.download.entity.vo.SettingVO;
import com.download.server.FileService;
import com.download.server.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SettingService settingService;
    @GetMapping("/fetch_filter_file")
    @LogAnnotation(operation = "查看文件")
    public ResponseResult fetchFilterFile(@RequestParam String path, @RequestParam String filter) {
        LambdaQueryWrapper<SettingVO> wrapper = new LambdaQueryWrapper<>();
        String pathOne = settingService.getOne(wrapper).getDownloadPath();
        Path path1 = Paths.get(path);
        Path path2 = Paths.get(pathOne);
        Path parent = path1.toAbsolutePath().getRoot();
        if(!parent.equals(path2)){
            return null;
        }
        List<FileVO> fileList = fileService.getFileList(String.valueOf(path1.toAbsolutePath()), filter);
        return ResponseResult.ok(fileList);
    }
    /*
    内存从小到大
     */
    @PostMapping("/sort_file_list")
    @LogAnnotation(operation = "文件排序")
    public ResponseResult sortFileList(@RequestParam String path,@RequestParam String sort) {
        Path path1 = Paths.get(path);
        //Path parent = path1.toAbsolutePath();
        List<FileVO> fileLists = fileService.sortFileList(String.valueOf(path1.toAbsolutePath()), sort);
        return ResponseResult.ok(fileLists);
    }
}
