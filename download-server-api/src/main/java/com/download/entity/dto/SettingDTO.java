package com.download.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingDTO {

    private String downloadPath;

    private int maxTasks;

    private long maxDownloadSpeed;

    private long maxUploadSpeed;

}
