package com.kfm.service;

import com.kfm.utils.PropertiesUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class ReceiveFileService {
    public void start() throws IOException {

        Properties properties = PropertiesUtils.getProperties();
        int port = Integer.parseInt(properties.getProperty("port"));

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动,监听端口"+port);
        while(true) {
            Socket socket = serverSocket.accept();
            // 为每个客户端连接创建线程
            Thread thread = new Thread(() -> {
                try {
                    handleRequest(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }
    private static void handleRequest(Socket socket) throws IOException {
        Properties properties = PropertiesUtils.getProperties();
        String savePath = properties.getProperty("filePath");
        socket.setSoTimeout(500000);

        InputStream inType = socket.getInputStream();
        DataInputStream disType = new DataInputStream(inType);
        // 读取文件名
        String fileType = disType.readUTF();
        System.out.println("[系统]：操作类型为："+fileType);

        // 读取文件名
        InputStream isName = socket.getInputStream();
        DataInputStream disName = new DataInputStream(isName);
        String fileName = disName.readUTF();
        String filePath = savePath + File.separator + fileName;
        System.out.println("[系统]：文件路径为："+filePath);

        // 判断是否为删除文件进行操作
        if (fileType.equals("deleteFile")){
            File file = new File(filePath);
            if (file.exists()){
                file.delete();
            }
            if (!file.exists()){
                System.out.println("文件已被删除！");
            }
            return;
        }

        // 写出文件
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] bytes = disName.readAllBytes();
        fos.write(bytes);
        fos.flush();
        fos.close();
        socket.close();
        System.out.println("文件["+fileName+"]接收完成!");
    }
}
