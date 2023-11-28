package com.kfm.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
@WebServlet("/getAvatar")
public class GetAvatar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String avatarUrl = (String)session.getAttribute("avatar");
        String localFilePath = avatarUrl;
        System.out.println("头像路径："+localFilePath);
        // 设置响应内容类型
        resp.setContentType("image/png");
        try (InputStream inputStream = new FileInputStream(localFilePath);
             OutputStream outputStream = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
