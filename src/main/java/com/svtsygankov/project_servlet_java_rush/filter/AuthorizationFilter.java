package com.svtsygankov.project_servlet_java_rush.filter;

import com.svtsygankov.project_servlet_java_rush.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        var path = req.getRequestURI().substring(req.getContextPath().length());

        var user = (User) req.getSession().getAttribute("user");

        // Проверяем доступ к защищённым страницам
        if (path.startsWith("/secure/")) {
            if (user != null) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        if (path.startsWith("/admin/")) {
            if (user != null && "ADMIN".equals(user.getRole())) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Все остальные запросы пропускаем
        chain.doFilter(request, response);
    }
}