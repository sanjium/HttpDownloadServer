package com.download.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchFileDTO {
    private String path;

    //筛选选项
    private String type;

    private String sort;

    private String order;
}
