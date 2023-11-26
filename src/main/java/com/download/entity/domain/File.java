package com.download.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("download_file")
public class File {


    @TableId(value="file_id", type = IdType.AUTO)
    private long fileId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;


    /**
     * 文件类型
     */
    @TableField("file_type")
    private String filetype;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private String fileSize;

    /**
     * 创建时间
     */
    @TableField("file_creattime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fileCreatTime;

    /**
     * 修改时间
     */
    @TableField("file_modified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fileModifiedTime;

    @TableField("file_isdirectory")
    private boolean fileIsDirectory;

    /**
     * 文件路径
     */

    @TableField("file_path")
    private String filePath;

    @TableField("file_filter")
    private String filefilter;


    public File(String fileName, boolean fileIsDirectory,String filePath, LocalDateTime fileCreatTime) {
    }
}
