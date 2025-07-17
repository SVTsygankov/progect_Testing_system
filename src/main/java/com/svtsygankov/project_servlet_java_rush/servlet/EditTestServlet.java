package com.svtsygankov.project_servlet_java_rush.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.util.TestFormParser;
import com.svtsygankov.project_servlet_java_rush.util.TestFormValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.OBJECT_MAPPER;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/edit")
public class EditTestServlet extends HttpServlet {
    private TestService testService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
        objectMapper = (ObjectMapper) config.getServletContext().getAttribute(OBJECT_MAPPER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int testId = Integer.parseInt(req.getParameter("id"));
            Test test = testService.findById(testId);

            req.setAttribute("test", test);
            req.setAttribute("contentPage", "/WEB-INF/views/admin/edit-test.jsp");
            req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID теста");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            // Парсинг данных формы
            TestForm form = TestFormParser.parse(req, objectMapper);
            // Валидация
            TestFormValidator.validateForUpdate(form, resp);
            // Получаем текущий тест для сохранения created_by
            Test existingTest = testService.findById(form.getId());
            // Обновление теста
            Test updatedTest = Test.builder()
                    .id(form.getId())
                    .title(form.getTitle())
                    .topic(form.getTopic())
                    .created_by(existingTest.getCreated_by())
                    .questions(form.getQuestions())
                    .build();

            testService.updateTest(updatedTest);

            // Редирект после успешного сохранения
            resp.sendRedirect(req.getContextPath() + "/admin/tests");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка: " + e.getMessage());
        }
    }
}