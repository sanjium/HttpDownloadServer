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
public class SettingVO extends Setting {

    private int maxTasks;

    private long maxDownloadSpeed;

    private long maxUploadSpeed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
}
