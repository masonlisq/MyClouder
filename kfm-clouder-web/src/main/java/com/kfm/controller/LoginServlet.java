package com.kfm.controller;

import com.kfm.constant.Constant;
import com.kfm.model.domain.User;
import com.kfm.service.UserService;
import com.kfm.utils.JSONUtils;
import com.wf.captcha.utils.CaptchaUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        Object user = session.getAttribute("user");
//        if (user == null) {
//            resp.sendRedirect("/index.html");
//        } else {
//            req.getRequestDispatcher("WEB-INF/listManager.html").forward(req, resp);
//        }
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String verCode = req.getParameter("checkcode");
        if (!CaptchaUtil.ver(verCode, req)) {
            CaptchaUtil.clear(req);
            resp.setContentType("text/html;charset=utf-8");
            String alert = "<script>alert('验证码输入错误！');" +
                    "window.location.href=\"index.html\"</script>";
            PrintWriter writer = resp.getWriter();
            writer.write(alert);
            writer.flush();
            writer.close();
            resp.sendRedirect("index.html");
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserService userService = new UserService();
        User user = userService.doLogin(username, password);
        if (user == null) {
            resp.setContentType("text/html;charset=utf-8");
            String alert = "<script>alert('登陆失败！请检查您的账户和密码！');" +
                    "window.location.href=\"index.html\"</script>";
            PrintWriter writer = resp.getWriter();
            writer.write(alert);
            writer.flush();
            writer.close();
            resp.sendRedirect("index.html");
        }
        // 存session
        HttpSession session = req.getSession();
        assert user != null;
        session.setAttribute("id", user.getId());
        session.setAttribute("nickName", user.getNickName());
        session.setAttribute("avatar", Constant.UPLOAD_PATH +user.getAvatar());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("phoneNumber", user.getPhoneNumber());
        String toJSON = JSONUtils.getToJSON(user);
        session.setAttribute("user",toJSON);
        // 存cookie
        Cookie cookie = new Cookie("username", user.getUsername());
        resp.addCookie(cookie);
        cookie.setMaxAge(60 * 60 * 24);

        resp.sendRedirect("/listManager");
    }
}
