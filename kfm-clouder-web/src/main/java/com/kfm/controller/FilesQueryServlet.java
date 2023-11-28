package com.kfm.controller;

import com.kfm.model.response.FileItem;
import com.kfm.service.FileService;
import com.kfm.utils.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/selectAllFiles")
public class FilesQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/listManager.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileService fileService = new FileService();
        List<FileItem> fileItemList = fileService.selectAllFiles();
        String s = JSONUtils.convertToJSON(fileItemList);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        // 获取响应输出流并将JSON数据写入
        try (PrintWriter out = resp.getWriter()) {
            out.print(s);
        }
    }
}
