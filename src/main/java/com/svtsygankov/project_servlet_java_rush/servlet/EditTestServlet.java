package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.dto.CreateTestForm;
import com.svtsygankov.project_servlet_java_rush.dto.QuestionForm;
import com.svtsygankov.project_servlet_java_rush.entity.AnswerOption;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/edit")
public class EditTestServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String testIdParam = req.getParameter("id");

        if (testIdParam == null || !testIdParam.matches("\\d+")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID теста");
            return;
        }

        int testId = Integer.parseInt(testIdParam);
        Test test = testService.findById(testId);

        if (test == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тест не найден");
            return;
        }

        req.setAttribute("test", test);
        req.getRequestDispatcher("/WEB-INF/views/admin/edit-test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Пользователь не авторизован");
            return;
        }

        String testIdParam = req.getParameter("id");

        if (testIdParam == null || !testIdParam.matches("\\d+")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID теста");
            return;
        }

        int testId = Integer.parseInt(testIdParam);

        try {
            CreateTestForm form = TestFormParser.parse(req);
            TestFormValidator.validate(form, resp);

            List<Question> domainQuestions = form.getQuestions().stream()
                    .map(this::convertToDomain)
                    .toList();

            testService.updateTest(
                    testId,
                    form.getTitle(),
                    form.getTopic(),
                    domainQuestions
            );

            resp.sendRedirect(req.getContextPath() + "/tests");

        } catch (Exception e) {
            throw new IOException("Ошибка при обновлении теста", e);
        }
    }

    private Question convertToDomain(QuestionForm formQuestion) {
        List<AnswerOption> answers = formQuestion.getAnswers().stream()
                .map(a -> new AnswerOption(null, a.getText(), a.isCorrect()))
                .toList();

        return new Question(null, formQuestion.getText(), answers);
    }
}