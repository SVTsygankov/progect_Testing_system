package com.svtsygankov.project_servlet_java_rush.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.util.TestFormParser;
import com.svtsygankov.project_servlet_java_rush.util.TestFormValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.OBJECT_MAPPER;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_FORM_VALIDATOR;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/create")
public class CreateTestServlet extends HttpServlet {
    private TestService testService;
    private ObjectMapper objectMapper;
    private TestFormValidator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
        this.objectMapper = (ObjectMapper) config.getServletContext().getAttribute(OBJECT_MAPPER);
        this.validator = (TestFormValidator) config.getServletContext().getAttribute(TEST_FORM_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Создаем пустой тест для формы
        Test emptyTest = Test.builder()
                .title("")
                .topic("")
                .questions(new ArrayList<>())
                .build();

        req.setAttribute("test", emptyTest);
        req.setAttribute("contentPage", "/WEB-INF/views/admin/create-test.jsp");
        req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        resp.setContentType("application/json"); // Убедитесь, что это в начале метода

        if (currentUser == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", Collections.singletonList("Пользователь не авторизован"));
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            objectMapper.writeValue(resp.getWriter(), errorResponse);
            return;
        }

        try {
            TestForm form = TestFormParser.parse(req, objectMapper);

            if (!validator.validateForCreate(form)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("errors", validator.getErrors());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), errorResponse);
                return;
            }

            Test createdTest = testService.createTest(
                    form.getTitle(),
                    form.getTopic(),
                    currentUser.getId(),
                    form.getQuestions()
            );

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("success", true);
            successResponse.put("redirectUrl", "/admin/tests");
            objectMapper.writeValue(resp.getWriter(), successResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", Collections.singletonList(
                    e.getMessage() != null ? e.getMessage() : "Неизвестная ошибка"
            ));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), errorResponse);
        }
    }
}
