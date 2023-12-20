package com.download.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTasksDTO implements Serializable {

    private Integer currentPage;

    private Integer limit;

    private Integer pos;

    private String status;

}
