package com.download.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("download_log")
public class Log {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("type")
    private String type;

    @TableField("err_msg")
    private String errMsg;

    @TableField("module")
    private String module;

    @TableField("operation")
    private String operation;

    @TableField("method")
    private String method;

    @TableField("request_method")
    private String requestMethod;

    @TableField("path")
    private String path;

    @TableField("params")
    private String params;

    @TableField("ip")
    private String ip;

    @TableField("run_time")
    private String runTime;

    @TableField("create_time")
    private String createTime;

    @TableField("del_flag")
    private Integer delFlag;

}
