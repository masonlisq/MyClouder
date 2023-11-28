package com.kfm.service;

import com.kfm.model.response.FileItem;
import com.kfm.utils.PropertiesUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.nio.file.Files;

public class FileService {
    public static Thread watchThread;
    public void receiveFiles() throws IOException {
        Properties properties = PropertiesUtils.getProperties();
        int port = Integer.parseInt(properties.getProperty("port"));

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动,监听端口"+port);
        watchThread =  new Thread(()->{
            while(true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 为每个客户端连接创建线程
                Socket finalSocket = socket;
                Thread thread = new Thread(() -> {
                    try {
                        handleRequest(finalSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        });
        watchThread.start();

    }

    /**
     * 读取文件的方法
     * @param socket socket对象
     * @throws IOException 流异常
     */
    private static void handleRequest(Socket socket) throws IOException {
        Properties properties = PropertiesUtils.getProperties();
        String savePath = properties.getProperty("filePath");
        socket.setSoTimeout(500000);

        InputStream inType = socket.getInputStream();
        DataInputStream disType = new DataInputStream(inType);
        // 读取操作类型
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

    /**
     * 查询文件列表的方法
     * @return 文件集合
     * @throws IOException 流异常
     */
    public List<FileItem> selectAllFiles() throws IOException {
        String filePath = null;
        try {
            filePath = PropertiesUtils.getProperties().getProperty("filePath");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File files = new File(filePath);
        File[] filesList = files.listFiles();
        List<FileItem> fileItemList = new ArrayList<>();
        for (File file : filesList) {
            String name = file.getName();
            long length = file.length();
            long l = file.lastModified();
            String size = String.valueOf(String.format("%.2f",(double)length/1024))+"KB";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(l);
            FileItem fileItem = new FileItem(name,size,time);
            fileItemList.add(fileItem);
        }
        return fileItemList;
    }
}
