package com.kfm.controller;

import com.kfm.constant.Constant;
import com.kfm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateAvatar")
@MultipartConfig
public class AvatarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        System.out.println("请求到！！！！！！");
        Part file = req.getPart("file");
        // 获取上传的文件名称
        String fileName = file.getSubmittedFileName();
        System.out.println("上传的文件名称：" + fileName);
        // 获取上传的时候定义的name属性的值 file
        System.out.println("name:" + file.getName());
        // 次级目录
        String secondDir = File.separator+"avatar"+File.separator + fileName;
        fileName = Constant.UPLOAD_PATH + secondDir;
        File file1 = new File(fileName);
        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
        }
        file.write(fileName);
        // 数据库存入头像逻辑
        String json = doUpdateAvatar(req, secondDir);
        resp.setContentType("application/json;charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(json);
        }
    }

    private String doUpdateAvatar(HttpServletRequest req, String secondDir) {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        UserService userService = new UserService();
        System.out.println("存入数据库的头像路径：" + secondDir);
        String s = userService.updateAvatar(id, secondDir);
        return "{\"message\":\"" + s + "\"}";
    }
}
