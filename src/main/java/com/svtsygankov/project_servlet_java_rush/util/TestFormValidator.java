package com.svtsygankov.project_servlet_java_rush.util;

import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TestFormValidator {

    public static void validate(TestForm form, HttpServletResponse resp) throws IOException {
        if (form.getTitle() == null || form.getTitle().isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указано название теста");
            return;
        }

        if (form.getTopic() == null || form.getTopic().isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указана тема теста");
            return;
        }

        if (form.getQuestions().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Должен быть хотя бы один вопрос");
            return;
        }

        for (Question question : form.getQuestions()) {
            if (question.getAnswers().size() < 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "У вопроса \"" + question.getText() + "\" должно быть минимум 2 варианта ответа");
                return;
            }

            boolean hasCorrect = question.getAnswers().stream().anyMatch(Answer::isCorrect);
            if (!hasCorrect) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "У вопроса \"" + question.getText() + "\" должен быть хотя бы один правильный вариант");
                return;
            }
        }
    }
}