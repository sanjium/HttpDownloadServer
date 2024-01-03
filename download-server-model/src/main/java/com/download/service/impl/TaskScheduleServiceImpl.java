package com.download.service.impl;
import com.download.entity.domain.Transfer;
import com.download.entity.dto.SendTransferMsgDTO;
import com.download.mapper.TransferMapper;
import com.download.service.TaskScheduleService;
import org.apache.tomcat.util.threads.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class TaskScheduleServiceImpl implements TaskScheduleService {

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public void taskPolling(SendTransferMsgDTO sendTransferMsgDTO) {
        Map<Long, TaskThread> taskThreads = new HashMap<>();
        Long taskId = sendTransferMsgDTO.getTransferId();
        Transfer res = transferMapper.selectById(taskId);
        while(res!=null){
            String taskStatus = res.getStatus();
            TaskThread taskThread = taskThreads.get(taskId);
            if(taskStatus.equalsIgnoreCase("canceled")){
                //数据库状态为暂停，查找进行中的任务并暂停任务
                taskThread.stop();
                System.out.println(taskId + "is paused.");
            }else if (taskStatus.equalsIgnoreCase("Downloaded")){
                if(taskThread == null || !taskThread.isAlive()){
                    //创建任务
                    taskThread.start();
                    taskThreads.put(taskId,taskThread);
                }
            } else if (taskStatus.equalsIgnoreCase("pending")) {
                if (taskThread != null && taskThread.isAlive()) {
                    // 关闭任务线程实现任务的关闭
                    taskThread.stop();
                    System.out.println("Task " + taskId + " is stopped.");
                }
                taskThreads.remove(taskId); // 从任务线程列表中移除已关闭的任务线程
            }
            try {
                Thread.sleep(200); // 轮询间隔时间，根据需要调整
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
