package com.download.service.impl;
import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.TaskScheduleService;
import org.apache.tomcat.util.threads.TaskThread;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
@Service
public class TaskScheduleServiceImpl implements TaskScheduleService {

    /*@Override
    @Scheduled(fixedRate = 200)
    public void pollDatabase() {

    }
*/
    @Override
    public void start(SendTransferMsgDTO sendTransferMsgDTO) {
        Map<Long, TaskThread> taskThreads = new HashMap<>();
        // 连接数据库
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://43.248.191.29:33063/download_server")) {
            // 轮询数据库状态
            Statement statement = connection.createStatement();
            long taskId = sendTransferMsgDTO.getTransferId();
            ResultSet res = statement.executeQuery("SELECT status from transfer where id = " + taskId);
            // 遍历结果集，对比任务状态并进行相应操作
            while(res.next()){
                String taskStatus = res.getString("status");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
