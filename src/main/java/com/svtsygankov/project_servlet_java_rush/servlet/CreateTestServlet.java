package com.svtsygankov.project_servlet_java_rush.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.TestCreationRequest;
import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
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
import java.util.List;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.OBJECT_MAPPER;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/create")
public class CreateTestServlet extends HttpServlet {

    private TestService testService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
        objectMapper = (ObjectMapper) config.getServletContext().getAttribute(OBJECT_MAPPER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/create-test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Пользователь не авторизован");
            return;
        }

        try {
            TestCreationRequest form = TestFormParser.parse(req, objectMapper);
            TestFormValidator.validate(form, resp);

            List<Question> questions = form.getQuestions();

            testService.createTest(
                    form.getTitle(),
                    form.getTopic(),
                    currentUser.getId(),
                    questions
            );

            resp.sendRedirect(req.getContextPath() + "/tests");

        } catch (Exception e) {
            throw new IOException("Ошибка при сохранении теста", e);
        }
    }
}