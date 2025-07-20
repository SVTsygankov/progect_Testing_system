package com.svtsygankov.project_servlet_java_rush.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
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

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.OBJECT_MAPPER;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/create")
public class CreateTestServlet extends HttpServlet {
    private TestService testService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
        this.objectMapper = (ObjectMapper) config.getServletContext().getAttribute(OBJECT_MAPPER);
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
            TestForm form = TestFormParser.parse(req, objectMapper);
            TestFormValidator.validateForCreate(form, resp);

            testService.createTest(
                    form.getTitle(),
                    form.getTopic(),
                    currentUser.getId(),
                    form.getQuestions()
            );
            resp.sendRedirect(req.getContextPath() + "/admin/tests");

        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат данных");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при сохранении теста: " + e.getMessage());
        }
    }
}
