package com.download.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("download_setting")
public class Setting {

    @TableId(value = "download_settingId", type = IdType.AUTO)
    private long settingId;

    @TableField("setting_downloadPath")
    private String downloadPath;

    /**
     * 最大工作任务
     */
    @TableField("setting_maxTasks")
    private int maxTasks;

    /**
     * 最大下载速度
     */
    @TableField("setting_downloadSpeed")
    private long maxDownloadSpeed;

    /**
     * 最大上传速度
     */
    @TableField("setting_maxUploadSpeed")
    private long maxUploadSpeed;
}
