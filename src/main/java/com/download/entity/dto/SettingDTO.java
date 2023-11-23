package com.download.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SettingDTO {
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
