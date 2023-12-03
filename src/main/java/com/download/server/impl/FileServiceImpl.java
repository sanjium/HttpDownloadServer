package com.download.server.impl;
import com.download.entity.vo.FileVO;
import com.download.mapper.SettingMapper;
import com.download.server.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
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

    @Override
    public List<FileVO> sortFileList(String path, String sort) {
        FileVO fileVO = new FileVO();
        List<FileVO> fileList = new ArrayList<>();
        // 读取本地目录中的文件信息
        List<File> files = readLocalFiles(path);
        // 将文件信息封装到FileVo对象中
        for (File file : files) {
            FileVO fileVo = new FileVO();
            fileVo.setName(file.getName());
            fileVo.setDirectory(file.isDirectory());
            fileVo.setPath(file.getPath());
            fileVo.setSize(getFileSize(file));
            fileVo.setCreateAt(getFileCreationTime(file));
            fileVo.setChildren(null);
            fileList.add(fileVo);
        }

        for(int i=0;i<fileList.size()-1;i++){
            FileVO fileVO1 = fileList.get(i);
            FileVO fileVO2 = fileList.get(i+1);
            //从小到大
            if(sort.equals("name")){
                if(fileVO1.getName().compareTo(fileVO2.getName())>0){
                    fileVO = fileVO1;
                    fileVO1 = fileVO2;
                    fileVO2 = fileVO;
                }
                //从小到大
            } else if (sort.equals("size")) {
                if(Integer.parseInt(fileVO1.getSize().substring(0,fileVO1.getSize().indexOf("B")))>
                        Integer.parseInt(fileVO2.getSize().substring(0,fileVO2.getSize().indexOf("B")))){
                    fileVO = fileVO1;
                    fileVO1 = fileVO2;
                    fileVO2 = fileVO;
                }
                //创建时间先后
            }else if(sort.equals("time")){
                if(fileVO1.getCreateAt().compareTo(fileVO2.getCreateAt())>0){
                    fileVO = fileVO1;
                    fileVO1 = fileVO2;
                    fileVO2 = fileVO;
                }
            }
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
        List<File> files1 = new ArrayList<>();
        List<File> files2 = new ArrayList<>();
        List<File> files3 = new ArrayList<>();
        List<File> files4 = new ArrayList<>();

        File directory = new File(path);
        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                // 如果文件符合过滤条件，则添加到文件列表中
                if (file.getName().contains(".docx") && filter.equals("document")) {
                    files1.add(file);
                } else if (file.getName().contains(".zip") && filter.equals("archive")) {
                    files2.add(file);
                }else if (file.getName().contains(".mp4") && filter.equals("video")) {
                    files3.add(file);
                }else {
                    files4.add(file);
                }
            }
        }
        if(filter.equals("document")){
            return files1;
        } else if (filter.equals("archive")) {
            return files2;
        } else if (filter.equals("video")) {
            return files3;
        }
        return files4;
    }

    // 获取文件大小
    private String getFileSize(File file) {
        // 获取文件大小的逻辑处理
        long size = file.length();
        return String.valueOf(size) + "B";
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

    private List<File> readLocalFiles(String path) {
        List<File> files = new ArrayList<>();
        File directory = new File(path);
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                files.add(file);
            }
        }
        return files;
    }
}
