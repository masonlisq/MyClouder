package com.kfm.controller;

import com.kfm.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

//@WebServlet(value = {"/receiveFiles"},loadOnStartup = 1)
public class ReceiveServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("初始化");
        // 开启服务端接收文件的服务
        FileService fileService = new FileService();
        try {
            fileService.receiveFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[系统]接收文件服务已开启！");
    }
}
