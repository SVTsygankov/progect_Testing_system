package com.svtsygankov.project_servlet_java_rush.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
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
            TestFormValidator.validate(form, resp);

            // Обновление теста
            testService.updateTest(convertToDomain(form, req));

            // Редирект после успешного сохранения
            resp.sendRedirect(req.getContextPath() + "/admin/tests");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка: " + e.getMessage());
        }
    }

    private Test convertToDomain(TestForm form, HttpServletRequest req) {
        User currentUser = (User) req.getSession().getAttribute("user");

        return new Test(
                form.getId(),
                form.getTitle(),
                form.getTopic(),
                currentUser.getId(),
                form.getQuestions().stream()
                        .map(q -> new Question(
                                q.getId(),
                                q.getText(),
                                q.getAnswers().stream()
                                        .map(a -> new Answer(a.getId(), a.getText(), a.isCorrect()))
                                        .toList()
                        ))
                        .toList()
        );
    }
}