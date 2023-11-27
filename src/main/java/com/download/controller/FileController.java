package com.download.controller;


import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.File;
import com.download.server.FileService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.FilePermission;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/fetchFileList")
    @LogAnnotation(operation = "返回文件列表")
    public ResponseResult fetchFileList(String path){

        return fileService.getByPath();
    }

    @GetMapping("/fetchFilterFile")
    @LogAnnotation(operation = "查看文件")
    public ResponseResult fetchFilterFile(@RequestBody Long fileId,@RequestBody String filePath,@RequestBody String filter){
        return fileService.fetchFilterFile(fileId,filePath,filter);
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
