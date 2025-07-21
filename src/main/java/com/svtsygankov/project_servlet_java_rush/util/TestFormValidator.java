package com.svtsygankov.project_servlet_java_rush.util;

import com.svtsygankov.project_servlet_java_rush.dto.TestForm;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.Question;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class TestFormValidator {

    // Общие проверки (статический метод)
    public static void validateCommon(TestForm test) throws ValidationException {
        Map<String, String> errors = new HashMap<>();

        if (test.getTitle() == null || test.getTitle().trim().isEmpty()) {
            errors.put("title", "Название теста обязательно");
        }

        if (test.getTopic() == null || test.getTopic().trim().isEmpty()) {
            errors.put("topic", "Тема теста обязательна");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Ошибка валидации теста", errors);
        }
    }

    // Валидация для создания (статический метод)
    public static void validateForCreate(TestForm test) throws ValidationException {
        validateCommon(test);

        Map<String, String> errors = new HashMap<>();

        if (test.getQuestions() == null || test.getQuestions().isEmpty()) {
            errors.put("questions", "Тест должен содержать хотя бы один вопрос");
        } else {
            validateQuestions(test.getQuestions(), errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Ошибка создания теста", errors);
        }
    }

    // Валидация для редактирования (статический метод)
    public static void validateForUpdate(TestForm test) throws ValidationException {
        validateCommon(test);

        Map<String, String> errors = new HashMap<>();

        if (test.getId() == null) {
            errors.put("id", "ID теста обязательно для редактирования");
        }

        if (test.getQuestions() != null) {
            validateQuestions(test.getQuestions(), errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Ошибка редактирования теста", errors);
        }
    }

    // Общая валидация вопросов (приватный статический метод)
    private static void validateQuestions(List<Question> questions, Map<String, String> errors) {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String prefix = "questions[" + i + "]";

            if (q.getText() == null || q.getText().trim().isEmpty()) {
                errors.put(prefix + ".text", "Текст вопроса обязателен");
            }

            if (q.getAnswers() == null || q.getAnswers().isEmpty()) {
                errors.put(prefix + ".answers", "Вопрос должен содержать хотя бы один ответ");
            } else {
                validateAnswers(q.getAnswers(), prefix, errors);
            }
        }
    }

    // Валидация ответов (приватный статический метод)
    private static void validateAnswers(List<Answer> answers, String prefix, Map<String, String> errors) {
        boolean hasCorrect = false;

        for (int i = 0; i < answers.size(); i++) {
            Answer a = answers.get(i);
            String answerPrefix = prefix + ".answers[" + i + "]";

            if (a.getText() == null || a.getText().trim().isEmpty()) {
                errors.put(answerPrefix + ".text", "Текст ответа обязателен");
            }

            if (a.isCorrect()) {
                hasCorrect = true;
            }
        }

        if (!hasCorrect) {
            errors.put(prefix + ".answers", "Должен быть хотя бы один правильный ответ");
        }
    }
}