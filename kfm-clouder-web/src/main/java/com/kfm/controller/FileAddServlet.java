package com.kfm.controller;

import com.kfm.constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/addFiles")
@MultipartConfig
public class FileAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码格式
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            String name = part.getSubmittedFileName();
            String fileName = Constant.UPLOAD_PATH + File.separator + name;
            File file = new File(fileName);
            // 如果目录不存在就去创建目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            part.write(fileName);
        }
    }
}
