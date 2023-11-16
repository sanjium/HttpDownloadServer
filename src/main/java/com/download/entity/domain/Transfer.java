package com.download.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    * 下载任务状态
    * */
    @TableField("status")
    private Integer status;

    /*
    * 下载任务名称
    * */
    @TableField("name")
    private String name;

    /*
    * 当前下载进度
    * */
    @TableField("current_progress")
    private String currentProgress;

    /*
    * 总下载量
    * */
    @TableField("total_progress")
    private String totalProgress;

    /*
    * 当前下载速度
    * */
    @TableField("current_download_speed")
    private String currentDownloadSpeed;

    /*
    * 线程数
    * */
    @TableField("thread_count")
    private Integer threadCount;

    /*
    * 运行时间
    * */
    @TableField("run_time")
    private String runTime;

    /*
    * 创建时间
    * */
    @TableField("create_time")
    private LocalDateTime createTime;

    /*
    * 删除标记(0为删除，1为未删除)
    * */
    @TableField("del_flag")
    private Integer delFlag;

}
