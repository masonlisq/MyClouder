package com.kfm.controller;

import com.kfm.constant.Constant;
import com.kfm.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickName = req.getParameter("nickName");
        String sex = req.getParameter("sex");
        String avatar = req.getParameter("avatar");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        UserService userService = new UserService();
        System.out.println(username);
        int i = userService.doRegister(username, password, nickName, sex, avatar, email, phone);
        if (i > Constant.RETURN_VALUE_ONE) {
            String html = """
                    <script>
                    alert("注册成功！请您登陆！");
                    window.location.href="index.html";
                    </script>
                    """;
            PrintWriter writer = resp.getWriter();
            writer.write(html);
            writer.flush();
            writer.close();
            System.out.println("注册成功");
        }
        String html = """
                <script>
                alert("注册失败！用户名已注册！请您重新注册！");
                window.location.href="register.html"
                </script>
                """;
        PrintWriter writer = resp.getWriter();
        writer.write(html);
        writer.flush();
//        writer.close();
//        resp.sendRedirect("/register.html");
        System.out.println("注册失败");

    }
}
