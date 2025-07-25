package com.svtsygankov.project_servlet_java_rush.listener;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dao.ResultDao;
import com.svtsygankov.project_servlet_java_rush.dao.TestDao;
import com.svtsygankov.project_servlet_java_rush.dao.UserDao;
import com.svtsygankov.project_servlet_java_rush.service.AuthenticationService;
import com.svtsygankov.project_servlet_java_rush.service.LoginAttemptServiceImpl;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.service.UserService;
import com.svtsygankov.project_servlet_java_rush.util.TestFormValidator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.File;

@WebListener
public class ContextListener implements ServletContextListener {

    public static final String AUTHENTICATION_SERVICE = "authenticationService";
    public static final String TEST_SERVICE = "testService";
    public static final String RESULTS_SERVICE = "resultsService";
    public static final String OBJECT_MAPPER = "objectMapper";
    public static final String TEST_FORM_VALIDATOR = "testFormValidator";
    public static final String TESTS_PATH =        "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\tests";
    public static final String USER_FILE_PATH =    "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\users\\users.json";
    public static final String RESULTS_FILE_PATH = "E:\\JavaRush\\Project_servlet_Java_Rush\\src\\main\\resources\\data\\results\\results.json";


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
        var resultsDao = new ResultDao(objectMapper, resultsFile);
        var resultsService = new ResultService(resultsDao, testService, userService);
        var validator = new TestFormValidator();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        servletContext.setAttribute(AUTHENTICATION_SERVICE, authenticationService);
        servletContext.setAttribute(TEST_SERVICE, testService);
        servletContext.setAttribute(RESULTS_SERVICE, resultsService);
        servletContext.setAttribute(OBJECT_MAPPER, objectMapper);
        servletContext.setAttribute(TEST_FORM_VALIDATOR, validator);

    }
}
