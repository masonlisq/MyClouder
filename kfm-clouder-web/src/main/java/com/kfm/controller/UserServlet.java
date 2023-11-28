package com.kfm.controller;

import com.kfm.model.domain.User;
import com.kfm.service.UserService;
import com.kfm.utils.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        if (type.equals("query")) {
            String json = doQuery(req, resp);
            resp.setContentType("application/json;charset=utf-8");
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(json);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        switch (type) {
            case "edit" -> {
                String json = doEdit(req, resp);
                resp.setContentType("application/json;charset=utf-8");
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write(json);
                }
            }
            case "editPassword" -> {
                String json = doUpdatePassword(req, resp);
                resp.setContentType("application/json;charset=utf-8");
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write(json);
                }
            }
        }
    }

    private String doQuery(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        UserService userService = new UserService();
        User user = userService.queryUser(id);
        session.removeAttribute("user");
        String json = JSONUtils.getToJSON(user);
        session.setAttribute("user", json);
        return (String) session.getAttribute("user");
    }

    /**
     * 更新用户信息的方法
     *
     * @param req  请求
     * @param resp 响应
     * @return 信息
     */
    private String doEdit(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        String nickName = req.getParameter("nickName");
        String sex = req.getParameter("sex");
        String email = req.getParameter("email");
        String phone = req.getParameter("phoneNumber");
        User user = new User();
        user.setId(id);
        user.setNickName(nickName);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        UserService userService = new UserService();
        String s = userService.updateUser(user);
        return "{\"message\":\"" + s + "\"}";
    }

    /**
     * 更新用户密码的方法
     *
     * @param req  请求
     * @param resp 响应
     * @return 响应信息
     */
    private String doUpdatePassword(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");
        UserService userService = new UserService();
        String s = userService.updatePassword(id, password, rePassword);
        return "{\"message\":\"" + s + "\"}";
    }
}
