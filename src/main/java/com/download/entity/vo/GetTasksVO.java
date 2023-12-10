package com.download.entity.vo;

import com.download.entity.domain.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTasksVO implements Serializable {

    private Integer total;            
    private List<Transfer> items;

}
