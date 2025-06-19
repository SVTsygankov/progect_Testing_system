package com.svtsygankov.project_servlet_java_rush.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dao.TestDao;
import com.svtsygankov.project_servlet_java_rush.dao.UserDao;
import com.svtsygankov.project_servlet_java_rush.service.AuthenticationService;
import com.svtsygankov.project_servlet_java_rush.service.LoginAttemptServiceImpl;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.net.URL;

@WebListener
public class ContextListener implements ServletContextListener {

    public static String AUTHENTICATION_SERVICE = "authenticationService";
    public static String TEST_SERVICE = "testService";
    public static String TESTS_PATH =        "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\tests";
    public static String USER_FILE_PATH =    "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\users\\users.json";
    public static String RESULTS_FILE_PATH = "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\results\\results.json";

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        var servletContext = sce.getServletContext();

        var objectMapper = new ObjectMapper();
        var usersFile = new File(USER_FILE_PATH);
        var resultsFile = new File(RESULTS_FILE_PATH);
        var testsDirectory = new File(TESTS_PATH);

        var userDao = new UserDao(objectMapper, usersFile);
        var passwordEncoder = new BCryptPasswordEncoder();
        var userService = new UserService(userDao, passwordEncoder);
        var loginAttemptService = new LoginAttemptServiceImpl();
        var authenticationService = new AuthenticationService(loginAttemptService, userService);
        var testDao = new TestDao(objectMapper, testsDirectory);
        var testService = new TestService(testDao);

        servletContext.setAttribute(AUTHENTICATION_SERVICE, authenticationService);
        servletContext.setAttribute(TEST_SERVICE, testService);
    }
}
