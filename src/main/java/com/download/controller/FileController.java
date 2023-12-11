package com.download.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.entity.dto.FetchFileDTO;
import com.download.entity.dto.SortFileDTO;
import com.download.entity.vo.FileVO;
import com.download.server.FileService;
import com.download.server.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult fetchFilterFile(@RequestParam String path,@RequestParam String type) {
        Path path1 = Paths.get(path);

        LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        System.out.println("--------------------------");
        System.out.println(path1.toString());
        wrapper.eq(Setting::getDownloadPath, path1.toString());
        String pathOne = settingService.getOne(wrapper).getDownloadPath();
        if (pathOne == null){
            return null;
        }
        List<FileVO> fileList = fileService.getFileList(String.valueOf(path1.toAbsolutePath()),
                type);
        return ResponseResult.ok(fileList);
    }
    /*
    内存从小到大
     */
    @PostMapping("/sort_file_list")
    @LogAnnotation(operation = "文件排序")
    public ResponseResult sortFileList(@RequestBody SortFileDTO sort) {
        Path path1 = Paths.get(sort.getPath());
        //Path parent = path1.toAbsolutePath();
        List<FileVO> fileLists = fileService.sortFileList(String.valueOf(path1.toAbsolutePath()), sort.getSort());
        return ResponseResult.ok(fileLists);
    }
}
