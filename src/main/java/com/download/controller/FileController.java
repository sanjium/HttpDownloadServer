package com.download.controller;

import com.download.aop.LogAnnotation;
import com.download.entity.ResponseResult;
import com.download.entity.domain.File;
import com.download.entity.domain.Transfer;
import com.download.server.FileService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/fetchFileList")
    @ResponseBody
    public List<File> fetchFileList(){
        List<File> fileList=fileService.getAll();
        if(fileList==null){
            return null;
        }
        if(fileList.size()>0){
           return fileList;
        }
        return null;
    }

    /*
    内存从小到大
     */
    @GetMapping("/sortFileList")
    @ResponseBody
    public List<File> sortFileList(String fileSize){
        List<File> fileList=fileService.getAll();
        if(fileList.size()>0){
           for(int i=0;i< fileList.size();i++){
               if(Integer.parseInt(fileList.get(i).getFileSize()) >
                       Integer.parseInt(fileList.get(i + 1).getFileSize())) {
                   Collections.swap(fileList,i,i+1);
               }
           }
        }
        return fileList;
    }

}
