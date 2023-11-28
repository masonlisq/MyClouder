package com.kfm.controller;

import com.kfm.constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 下载的逻辑
        // 告诉浏览器响应的是二进制流
        System.out.println(req.getParameter("fileName"));
        resp.setContentType("application/octet-stream");
        // 获取本次请求要下载的文件名
        String file = req.getParameter("fileName");
        // 使用URLEncoder对文件名进行编码
        String encodedFileName = URLEncoder.encode(file, StandardCharsets.UTF_8);
        // 设置以附件的形式下载，指定下载的名字是文件原来的名字
        resp.setHeader("content-disposition", "attachment; filename=" + encodedFileName);
        // 要下载的文件的完整路径
        String path = Constant.UPLOAD_PATH + File.separator + file;
        FileInputStream in = new FileInputStream(path);
        // 获取响应的输出流
        ServletOutputStream out = resp.getOutputStream();
        // 将文件内容写入到输出流
        in.transferTo(out);
        out.flush();
        out.close();
        in.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
