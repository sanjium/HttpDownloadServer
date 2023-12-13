package com.download.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("download_transfer")
public class Transfer {

    /*
     * 主键
     * */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*
     * 下载任务类型
     * */
    @TableField("type")
    private String type;

    /*
     * 下载链接(当type为http必填)
     * */
    @TableField("url")
    private String url;

    /*
     * 下载任务状态
     * */
    @TableField("status")
    private String status;

    /*
     * 下载任务名称
     * */
    @TableField("name")
    private String name;

    /*
     * 当前下载进度(当前下载量/总量)
     * */
    @TableField("progress")
    private String progress;

    /*
     * 当前下载量
     * */
    @TableField("speed")
    private String speed;

    @TableField("size")
    private String size;

    /*
     * 当前下载速度
     * */
    @TableField("download_speed")
    private String downloadSpeed;

    /*
     * 上传速度(当type为bt时 必填)
     * */
    @TableField("upload_speed")
    private String uploadSpeed;

    /*
     * 线程数
     * */
    @TableField("threads")
    private Integer threads;

    /*
     * 运行时间
     * */
    @TableField("time_left")
    private String timeLeft;

    /*
     * 创建时间
     * */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /*
     * 完成时间
     * */
    @TableField("finished_at")
    private LocalDateTime finishedAt;

    @TableField(exist = false)
    private String remainingTime;

    /*
     * 删除标记(0为删除，1为未删除)
     * */
    @TableField("del_flag")
    private Integer delFlag;

}
