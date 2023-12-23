package com.download.service.impl;

import com.download.entity.dto.SendTransferMsgDTO;
import com.download.service.TaskService;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class TaskServiceImpl implements TaskService {


    @Override
    public void downLoadFile(SendTransferMsgDTO sendTransferMsgDTO,String destination) {
        try {
            URL url = new URL(sendTransferMsgDTO.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            long size = connection.getContentLength();
            int numChunks = 0;
            if (size > 0) {
                numChunks = partition(size);
            } else {
                System.out.println("未获得文件");
            }
            for(int i=0;i<numChunks;i++){
                int startByte = i * numChunks;
                int endByte = (int) Math.min(size - 1,startByte + numChunks - 1);
                downLoadChunk(String.valueOf(url),destination,startByte,endByte);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void downLoadChunk(String url, String destination , int startByte, int endByte) {
        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
            try (InputStream in = new BufferedInputStream(connection.getInputStream());
                 RandomAccessFile raf = new RandomAccessFile(destination, "wyj")) {
                 raf.seek(startByte);
                 byte[] buffer = new byte[4096];
                 int bytesRead;
                 while ((bytesRead = in.read(buffer)) != -1) {
                    raf.write(buffer, 0, bytesRead);
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int partition(Long size) {
        final int MIN_CHUNK_SIZE_32KB = 32 * 1024;
        final int CHUNK_SIZE_1MB = 1024 * 1024;
        final int CHUNK_SIZE_10MB = 10 * 1024 * 1024;
        final int MAX_SIZE_10MB = 10 * 1024 * 1024;
        final int MAX_SIZE_100MB = 100 * 1024 * 1024;
        int chunkSize;
        if(size <= MAX_SIZE_10MB){
            chunkSize = MIN_CHUNK_SIZE_32KB;
        }else if (size <= MAX_SIZE_100MB) {
            chunkSize = CHUNK_SIZE_1MB;
        }else{
            chunkSize = CHUNK_SIZE_10MB;
        }
        int numChunks = (int) ((size + chunkSize - 1)/chunkSize);
        return numChunks;
    }

}
