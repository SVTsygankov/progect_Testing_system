package com.svtsygankov.project_servlet_java_rush.util;

import com.svtsygankov.project_servlet_java_rush.entity.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public class CredentialsExtractor {
    public static Credentials extract(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        return new Credentials(login, password);
    }
}
