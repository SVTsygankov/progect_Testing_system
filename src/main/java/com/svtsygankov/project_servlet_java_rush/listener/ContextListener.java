package com.svtsygankov.project_servlet_java_rush.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dao.UserDao;
import com.svtsygankov.project_servlet_java_rush.service.AuthenticationService;
import com.svtsygankov.project_servlet_java_rush.service.LoginAttemptServiceImpl;
import com.svtsygankov.project_servlet_java_rush.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.net.URL;

@WebListener
public class ContextListener implements ServletContextListener {

    public static String AUTHENTICATION_SERVICE = "authenticationService";
    public static String TESTS_PATH = "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\tests";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        var servletContext = sce.getServletContext();

        var objectMapper = new ObjectMapper();
        var usersFile = new File
                ("E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\users\\users.json");
        var resultsFile = new File
                ("E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\results\\results.json");
        URL usersUrl = getClass().getClassLoader().getResource("data/users/users.json");
        System.out.println("users.json путь: " + usersUrl);
        URL resultsUrl = getClass().getClassLoader().getResource("data/results/results.json");
        System.out.println("results.json путь: " + resultsUrl);
        var userDao = new UserDao(objectMapper, usersFile);
        var passwordEncoder = new BCryptPasswordEncoder();
        var userService = new UserService(userDao, passwordEncoder);
        var loginAttemptService = new LoginAttemptServiceImpl();
        var authenticationService = new AuthenticationService(loginAttemptService, userService);

        servletContext.setAttribute(AUTHENTICATION_SERVICE, authenticationService);
    }
}
