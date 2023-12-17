package com.download.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendTransferMsgDTO {

    private Long transferId;

    private String url;

}
