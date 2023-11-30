package com.download.server.impl;
import com.download.entity.ResponseResult;
import com.download.entity.vo.FileVO;
import com.download.mapper.SettingMapper;
import com.download.server.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileService fileService;

    @Autowired
    private SettingMapper settingMapper;

    @Override
    public List<FileVO> getFileList(String path, String filter) {

            List<FileVO> fileList = new ArrayList<>();

            // 读取本地目录中的文件信息
            List<File> files = readLocalFiles(path, filter);

            // 将文件信息封装到FileVo对象中
            for (File file : files) {
                FileVO fileVo = new FileVO();
                fileVo.setName(file.getName());
                fileVo.setDirectory(file.isDirectory());
                fileVo.setPath(file.getPath());
                fileVo.setSize(getFileSize(file));
                fileVo.setCreateAt(getFileCreationTime(file));
                // 如果是目录，则递归获取子文件列表
                if (file.isDirectory()) {
                    fileVo.setChildren(getFileList(file.getPath(), filter));
                }
                fileList.add(fileVo);
            }
            return fileList;
        }



    private List<File> readLocalFilesOne(String path) {
        //创建一个存储
        List<File> files = new ArrayList<>();
        File directory = new File(path);
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                files.add(file);
            }
        }
        return null;
    }
    // 读取本地目录中的文件信息
    private List<File> readLocalFiles(String path, String filter) {
        // 根据path和filter读取本地目录中的文件信息并返回
        // 读取目录中的文件信息
        // 注意：这里需要根据path和filter筛选出符合条件的文件
        List<File> files = new ArrayList<>();

        File directory = new File(path);
        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                // 如果文件符合过滤条件，则添加到文件列表中
                if (file.getName().contains(".doc") && filter.equals("document")) {
                    files.add(file);
                } else if (file.getName().contains(".zip") && filter.equals("archive")) {
                    files.add(file);
                }else if (file.getName().contains(".mp4") && filter.equals("video")) {
                    files.add(file);
                }else {
                    files.add(file);
                }
            }
        }

        return files;
    }

    // 获取文件大小
    private String getFileSize(File file) {
        // 获取文件大小的逻辑处理
        long size = file.length();
        return String.valueOf(size) + " B";
    }

    // 获取文件创建时间
    private Date getFileCreationTime(File file) {
        // 获取文件创建时间的逻辑处理
        long timestamp = file.lastModified();
        Date date = new Date(timestamp);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //return sdf.format(date);
        return date;
    }

    private String getFileExtension(String fileName) {
        // 获取最后一个点号的位置
        int dotIndex = fileName.lastIndexOf(".");

        // 如果文件名中没有点号或者点号在文件名的第一个字符位置，那么文件没有后缀名
        if (dotIndex == -1 || dotIndex == 0 || dotIndex == fileName.length() - 1) {
            return "";
        }

        // 截取从最后一个点号之后到文件名末尾的字符串作为后缀名
        return fileName.substring(dotIndex + 1);
    }
}
