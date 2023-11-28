package com.kfm.service;

import com.kfm.utils.PropertiesUtils;

import java.io.*;
import java.net.Socket;
import java.util.Properties;


public class FileService {
    /**
     * 发送文件的方法
     * @param filename 文件名
     * @param type 操作类型
     * @throws IOException 流异常
     */
    public void sendFiles(String filename ,String type) throws IOException {
        Properties properties = PropertiesUtils.getProperties();
        String serverIp = properties.getProperty("serverIp");
        System.out.println(serverIp);
        int port = Integer.parseInt(properties.getProperty("port"));
        System.out.println(port);
        Socket socket = new Socket(serverIp, port);
        //指定文件夹路径
        String filePath = properties.getProperty("filePath");
        String dirFilePath = filePath + File.separator + filename;

        // 创建线程
        Thread thread = new Thread(() -> {
            try {
                sendOperationType(socket, type);
                // 发送文件名
                sendFileName(socket, filename);
                if (!type.equals("deleteFile")){
                    // 发送文件内容
                    sendFileContent(socket, dirFilePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("文件同步开始");
    }

    /**
     * 发送操作类型的方法
     * @param socket socket对象
     * @param type 操作类型
     * @throws IOException 流异常
     */
    private void sendOperationType(Socket socket, String type) throws IOException {
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(type);
    }

    /**
     * 发送文件名的方法
     * @param socket socket对象
     * @param filename 文件名
     * @throws IOException 流异常
     */
    private void sendFileName(Socket socket, String filename) throws IOException{
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(filename);
    }

    /**
     * 发送文件内容的方法
     * @param socket socket对象
     * @param dirFilePath 文件路径
     * @throws IOException 流异常
     */
    private void sendFileContent(Socket socket, String dirFilePath) throws IOException{
        File file = new File(dirFilePath);
        FileInputStream fis = new FileInputStream(file);
        OutputStream os = socket.getOutputStream();
        byte[] bytes = fis.readAllBytes();
        os.write(bytes, 0, bytes.length);
        os.flush();
        os.close();
        fis.close();
        System.out.println("文件发送成功");
    }
}
