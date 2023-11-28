package com.kfm.service;

import com.kfm.constant.PropertiesConstant;
import com.kfm.service.FileService;
import com.kfm.utils.PropertiesUtils;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MonitorService {
    /**
     * 监听文件夹修改的方法
     * @throws Exception 异常
     */
    public void monitor() throws Exception {
        Properties properties = PropertiesUtils.getProperties();
        // 要监测的文件夹路径
        String folderPath = properties.getProperty("filePath");
        // 轮询间隔（毫秒）
        long pollingInterval = 1000;

        File folder = new File(folderPath);

        // 创建一个FileAlterationObserver
        FileAlterationObserver observer = new FileAlterationObserver(folder);

        // 创建FileAlterationListener来处理文件变化事件
        FileAlterationListener listener = new FileAlterationListener() {

            @Override
            public void onFileCreate(File file) {
                // 当文件被创建时触发
                System.out.println("[发送文件]: " + file.getAbsolutePath());
                FileService service = new FileService();
                try {
                    service.sendFiles(file.getName(), PropertiesConstant.OP_TYPE_1);
                } catch (IOException e) {
                    System.out.println("[系统]发送文件失败！");
                    e.printStackTrace();
                }
            }

            @Override
            public void onDirectoryChange(File file) {

            }

            @Override
            public void onDirectoryCreate(File file) {

            }

            @Override
            public void onDirectoryDelete(File file) {

            }

            @Override
            public void onFileChange(File file) {
                // 当文件被修改时触发
                System.out.println("[修改文件]: " + file.getAbsolutePath());
                onFileCreate(file);
            }

            @Override
            public void onFileDelete(File file) {
                // 当文件被删除时触发
                System.out.println("[删除文件]: " + file.getAbsolutePath());
                FileService service = new FileService();
                try {
                    service.sendFiles(file.getName(), PropertiesConstant.OP_TYPE_2);
                } catch (IOException e) {
                    System.out.println("[系统]删除文件失败");
                }
            }

            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {

            }

            @Override
            public void onStop(FileAlterationObserver observer) {
                // 当监测停止时触发
                System.out.println("Monitoring stopped for: " + observer.getDirectory().getAbsolutePath());
            }
        };

        observer.addListener(listener);

        // 创建FileAlterationMonitor并启动监测
        FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInterval);
        monitor.addObserver(observer);
        monitor.start();
    }
}