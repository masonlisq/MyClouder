package com.kfm.controller;

import com.kfm.constant.Constant;
import com.kfm.model.domain.User;
import com.kfm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/selectElement")
public class ElementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/listManager.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserService userService = new UserService();
        Long id = (Long)session.getAttribute("id");
        User user = userService.queryUser(id);
        session.setAttribute("nickName",user.getNickName());
        session.setAttribute("avatar", Constant.UPLOAD_PATH+user.getAvatar());
        String avatar = "http://localhost:8080/getAvatar";
        System.out.println(user.getNickName()+" "+avatar);
        String json = "{\"nickName\":\""+user.getNickName()+"\",\"avatarUrl\":\""+avatar+"\"}";
        System.out.println(json);
        System.out.println("获取了一次头像和昵称！！");
        resp.setContentType("application/json;charset=utf-8");
        try(PrintWriter writer = resp.getWriter()){
            writer.write(json);
        }
    }
}
