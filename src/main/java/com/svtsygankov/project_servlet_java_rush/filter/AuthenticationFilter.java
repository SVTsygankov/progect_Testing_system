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
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Разрешённые пути без авторизации
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) req.getSession().getAttribute("user");

        // Если пользователь не авторизован — отправляем на /login
        if (user == null) {
            if (isAjaxRequest(req)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Необходимо войти");
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
            return;
        }

        // Пропускаем дальше
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/") ||
                path.equals("/login") ||
                path.equals("/registration") ||
                path.startsWith("/WEB-INF");
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}