package com.download.server.impl;
import com.download.entity.vo.FileVO;
import com.download.server.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileService fileService;

    @Override
    public List<FileVO> getFileList(String path, String type) {
        List<FileVO> fileList = new ArrayList<>();
        // 读取本地目录中的文件信息
        List<File> files = readLocalFiles(path, type);
        System.out.println("---------------------------------------");
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
                fileVo.setChildren(getFileList(file.getPath(), type));
            }
            fileList.add(fileVo);
        }
        return fileList;
    }

    @Override
    public List<FileVO> sortFileList(String path, String sort,String order) {
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
        //排序
        if (sort.equals("name")) {
            Collections.sort(fileList, new Comparator<FileVO>() {
                @Override
                public int compare(FileVO f1, FileVO f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            });
        } else if(sort.equals("size")) {
            Collections.sort(fileList, new Comparator<FileVO>() {
                @Override
                public int compare(FileVO f1, FileVO f2) {
                    return Long.compare(Long.parseLong(f1.getSize().substring(0,f1.getSize().indexOf("B"))),
                            Long.parseLong(f2.getSize().substring(0,f2.getSize().indexOf("B"))) );
                }
            });
        }else if(sort.equals("time")){
            Collections.sort(fileList, new Comparator<FileVO>() {
                @Override
                public int compare(FileVO f1, FileVO f2) {
                    return f1.getCreateAt().compareTo(f2.getCreateAt());
                }
            });
        }
        //降序
        if(order.equals("down")){
            Collections.reverse(fileList);
        }else{
            return fileList;
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
    private List<File> readLocalFiles(String path, String type) {
        // 根据path和filter读取本地目录中的文件信息并返回
        // 读取目录中的文件信息
        // 注意：这里需要根据path和filter筛选出符合条件的文件
        Map<String,String> filterParms = new HashMap<>();
        filterParms.put("document",".doc");
        filterParms.put("archive",".zip");
        filterParms.put("video",".mp4");
        File directory = new File(path);
        File[] fileList = directory.listFiles();
        List<File> fileLists = new ArrayList<>();
        if(fileList != null){
            for(File file : fileList){
                if(getFileExtension(file).equals(filterParms.get(type))){
                    fileLists.add(file);
                }
            }
        }
        return fileLists;
    }
    /**
     * 获取文件的扩展名。
     *
     * @param file 要获取扩展名的文件对象
     * @return 文件的扩展名（不包括点号）
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex > 0 ? fileName.substring(dotIndex) : "";
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
