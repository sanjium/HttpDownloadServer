package com.download.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.Setting;
import com.download.entity.dto.FetchFileDTO;
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
    @PostMapping ("/file_list")
    @LogAnnotation(operation = "查看文件")
    public ResponseResult fetchFilterFile(@RequestBody FetchFileDTO fetchFileDTO) {
        Path path1 = Paths.get(fetchFileDTO.getPath());
        LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setting::getDownloadPath, path1.toString());
        String pathOne = settingService.getOne(wrapper).getDownloadPath();
        if (pathOne == null){
            return null;
        }
//        System.out.println("====================================");
//        System.out.println(path1.toAbsolutePath().getRoot());
//        System.out.println(path1.getRoot());
//        System.out.println(path1.toAbsolutePath().getRoot().toString()+path1.toString());
//        System.out.println("-----------------------------");
//        System.out.println(path1.toAbsolutePath());
        if(fetchFileDTO.getSort().equals("null")) {
            List<FileVO> fileList = fileService.getFileList(path1.toAbsolutePath().getRoot().toString() + path1.toString(),
                    fetchFileDTO.getType());
            return ResponseResult.ok(fileList);
        }else{
            List<FileVO> fileList2 = fileService.sortFileList(path1.toAbsolutePath().getRoot().toString() + path1.toString(),
                    fetchFileDTO.getSort(),fetchFileDTO.getOrder());
            return ResponseResult.ok(fileList2);
        }
    }
    /*
    内存从小到大
     */
//    @PostMapping("/sort_file_list")
//    @LogAnnotation(operation = "文件排序")
//    public ResponseResult sortFileList(@RequestBody SortFileDTO sortFileDTO) {
//        Path path1 = Paths.get(sortFileDTO.getPath());
//        //Path parent = path1.toAbsolutePath();
//        List<FileVO> fileLists = fileService.sortFileList(String.valueOf(path1.toAbsolutePath()),sortFileDTO.getSort());
//        return ResponseResult.ok(fileLists);
//    }
}
