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

import javax.crypto.spec.PSource;
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
        LambdaQueryWrapper<Setting> wrapper = new LambdaQueryWrapper<>();
        String path = "";
        if(fetchFileDTO.getPath().equals("/")){
            path = settingService.getOne(wrapper).getDownloadPath();
        }else{
            path = fetchFileDTO.getPath();
        }
        Path path1 = Paths.get(path);
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

}
