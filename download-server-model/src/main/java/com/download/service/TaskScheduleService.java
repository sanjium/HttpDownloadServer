package com.download.service;

import com.download.entity.TaskState;
import com.download.entity.dto.SendTransferMsgDTO;

public interface TaskScheduleService {


    void taskPolling(SendTransferMsgDTO sendTransferMsgDTO);


   // TaskState getTaskState(SendTransferMsgDTO sendTransferMsgDTO);
}
