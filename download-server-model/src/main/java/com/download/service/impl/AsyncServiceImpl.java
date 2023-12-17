package com.download.service.impl;

import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

//    public static void main(String[] args) {
//        try {
//            URL url = new URL("https://plugins.jetbrains.com/files/6954/442937/kotlin-plugin-232-1.9.21-release-633-IJ10072.27.zip?updateId=442937&pluginId=6954&family=INTELLIJ");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = conn.getInputStream();
//            //文件大小
//            long size = conn.getContentLengthLong();
//            //获取header 确定文件名和扩展名，并防止乱码
//            String filename = "";
//            if (conn.getHeaderField("Content-Disposition") != null) {
//                filename = new String(conn.getHeaderField("Content-Disposition").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//            }
//            filename = filename.contains("filename=") ? filename.substring(filename.lastIndexOf("filename=") + "filename=".length()) : filename;
//            filename = filename.replaceAll("\"", "");
//            //下载路径
//            String path = "./" + filename;
//            FileOutputStream fileOutputStream = new FileOutputStream(path);
//            var ref = new Object() {
//                double byteSum = 0;
//                int byteRead = 0;
//            };
//            byte[] buffer = new byte[1024];
//            CompletableFuture<Void> printState = CompletableFuture.runAsync(() -> {
//                while (true) {
//                    try {
//                        System.out.println(ref.byteSum * 100 / size);
//                        Thread.sleep(500);
//                        if (ref.byteSum == size) {
//                            break;
//                        }
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//            try {
//                while ((ref.byteRead = inputStream.read(buffer)) != -1) {
//                    ref.byteSum += ref.byteRead;
//                    fileOutputStream.write(buffer, 0, ref.byteRead);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            CompletableFuture.allOf(printState);
//
//            fileOutputStream.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Async("asyncServiceExecutor")
    @Override
    public void executeDownloadAsync(SendTransferMsgDTO sendTransferMsgDTO) {
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
                        Thread.sleep(500);
                        if (ref.byteSum == size) {
                            log.info("传输任务id:" + sendTransferMsgDTO.getTransferId() + " 下载进度:100%");
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

    }
}
