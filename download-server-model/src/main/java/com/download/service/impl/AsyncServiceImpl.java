package com.download.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.AsyncService;
import lombok.extern.slf4j.Slf4j;

import lombok.var;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Slf4j
@Service
@ServerEndpoint("/websocket/transfer/{tid}")
public class AsyncServiceImpl implements AsyncService {

    @Resource(name = "asyncServiceExecutor")
    ThreadPoolTaskExecutor asyncServiceExecutor;

    /**
     * 静态变量，用来记录当前下载任务数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /*
     * 当前存在的下载任务
     * */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    /*
     * 当前传输任务的id和session
     * */
    private String tid;

    private Session session;

    @OnOpen
    public void onOpen(@PathParam("tid") String tid, Session session) {
        log.info("连接建立中 ==> session_id = {}， tid = {}", session.getId(), tid);

        onlineSessionClientCount.incrementAndGet();
        onlineSessionClientMap.put(tid, session);
        this.tid = tid;
        this.session = session;

        sendToClient(tid, "tid:" + tid + " 连接成功");
        log.info("连接建立成功，当前任务传输数为：{} ==> 开始监听新连接：session_id = {}， tid = {},。", onlineSessionClientCount, session.getId(), tid);
    }


    @OnClose
    public void onClose(@PathParam("tid") String tid, Session session) {
        onlineSessionClientCount.decrementAndGet();
        onlineSessionClientMap.remove(tid);

        log.info("连接关闭成功，当前在线数为：{} ==> 关闭该连接信息：session_id = {}， tid = {},。", onlineSessionClientCount, session.getId(), tid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        /*
          html界面传递来得数据格式，可以自定义.
          {"tid":"1","message":"hello websocket"}
         */
        JSONObject jsonObject = JSON.parseObject(message);
        String tid = jsonObject.getString("tid");
        String msg = jsonObject.getString("message");
        log.info("服务端收到客户端消息 ==> fromTid = {}, toTid = {}, message = {}", tid, tid, message);

        sendToClient(tid, msg);
    }

    /**
     * 发生错误调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        error.printStackTrace();
    }

    public void sendToClient(String toTid, String message) {
        Session toSession = onlineSessionClientMap.get(toTid);
        if (Objects.isNull(toSession)) {
            log.error("服务端给客户端发送消息 ==> toTid = {} 不存在, message = {}", toTid, message);
            return;
        }
        log.info("服务端给客户端发送消息 ==> toTid = {}, message = {}", toTid, message);
        toSession.getAsyncRemote().sendText(message);
    }


    @Override
    public void executeDownloadAsync(SendTransferMsgDTO sendTransferMsgDTO) {
        asyncServiceExecutor.execute(() -> {
            log.info("start executeAsync");
            log.info("当前运行的线程名称：" + Thread.currentThread().getName());
            //TODO 执行下载任务
            log.info("执行下载:" + sendTransferMsgDTO.toString());
            try {
                URL url = new URL(sendTransferMsgDTO.getUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                //文件大小
                long size = conn.getContentLengthLong();
                //获取header 确定文件名和扩展名，并防止乱码
                String filename = "";
                if (conn.getHeaderField("Content-Disposition") != null) {
                    filename = new String(conn.getHeaderField("Content-Disposition").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                }
                filename = filename.contains("filename=") ? filename.substring(filename.lastIndexOf("filename=") + "filename=".length()) : filename;
                filename = filename.replaceAll("\"", "");
                //下载路径
                String path = "./" + filename;
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                var ref = new Object() {
                    double byteSum = 0;
                    int byteRead = 0;
                };
                byte[] buffer = new byte[1024];
                CompletableFuture<Void> printState = CompletableFuture.runAsync(() -> {
                    while (true) {
                        try {
                            String currentProgress = ref.byteSum * 100 / size + "%";
                            log.info("传输任务id:" + sendTransferMsgDTO.getTransferId() + " 下载进度:" + currentProgress);
                            sendToClient(sendTransferMsgDTO.getTransferId().toString(), currentProgress);
                            Thread.sleep(500);
                            if (ref.byteSum == size) {
                                log.info("传输任务id:" + sendTransferMsgDTO.getTransferId() + " 下载进度:100%");
                                sendToClient(sendTransferMsgDTO.getTransferId().toString(), "100%");
                                break;
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                try {
                    while ((ref.byteRead = inputStream.read(buffer)) != -1) {
                        ref.byteSum += ref.byteRead;
                        fileOutputStream.write(buffer, 0, ref.byteRead);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileOutputStream.close();
                CompletableFuture.allOf(printState).thenRun(() -> log.info("end executeAsync"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
