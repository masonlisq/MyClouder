package com.kfm.filter;


import com.kfm.constant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoginFilter 执行");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // 上下文路径
        String path = req.getContextPath();
        System.out.println("path=" + path);
        // 请求的uri
        String uri = req.getRequestURI();
        System.out.println("uri=" + uri);
        uri = uri.substring(path.length());
        if ("/".equals(uri)) {
            chain.doFilter(req, resp);
        }
        String[] urls = {"/receiveFiles","/login", "/index.html", "/captcha", ".css", ".js", "/register", "/register.html"};
        for (String url : urls) {
            if (uri.contains(url)) { // 说明访问的是登录/注册需要的资源，直接放行
                // 放行 让浏览器访问本该访问的东西
                System.out.println(uri + "放行");
                chain.doFilter(req, resp);
                return;
            }
        }
        // 判断是否已经登录 session  --> user 如果存在--> 放行 不存在-->跳转到登录页面
        HttpSession session = req.getSession();
        Object user = session.getAttribute(Constant.LOGIN_USER_KEY);
        if (user == null) { // 未登录，去登录页面
            System.out.println("未登录");
            resp.sendRedirect("/");
            return;
        }
        // 放行 让浏览器访问本该访问的东西
        chain.doFilter(req, resp);
        System.out.println("LoginFilter 执行完毕");
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter 销毁");
    }
}
