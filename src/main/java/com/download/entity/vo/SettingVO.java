package com.download.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.download.entity.domain.Setting;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingVO {

    private String downloadPath;

    private int maxTasks;

    private long maxDownloadSpeed;

    private long maxUploadSpeed;

}
