package com.svtsygankov.project_servlet_java_rush.util;

import com.svtsygankov.project_servlet_java_rush.dto.AnswerOptionForm;
import com.svtsygankov.project_servlet_java_rush.dto.CreateTestForm;
import com.svtsygankov.project_servlet_java_rush.dto.QuestionForm;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Enumeration;

public class TestFormParser {

    public static CreateTestForm parse(HttpServletRequest req) {
        String title = req.getParameter("title");
        String topic = req.getParameter("topic");

        Map<Integer, String> questionTexts = new HashMap<>();
        Map<Integer, List<AnswerOptionForm>> questionAnswers = new HashMap<>();

        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();

            if (name.startsWith("questionText_")) {
                int qIndex = extractQuestionIndex(name);
                String text = req.getParameter(name);

                // Убираем значения вроде "Вопрос 1", если они совпадают с заголовком
                if (!text.equals("Вопрос " + qIndex)) {
                    questionTexts.put(qIndex, text);
                }
            }

            if (name.startsWith("answerText_")) {
                int qIndex = extractQuestionIndex(name);
                int aIndex = extractAnswerIndex(name);

                String answerText = req.getParameter(name);
                boolean isCorrect = "on".equals(req.getParameter("isCorrect_" + qIndex + "_" + aIndex));

                AnswerOptionForm answer = new AnswerOptionForm(aIndex, answerText, isCorrect);
                questionAnswers.computeIfAbsent(qIndex, k -> new ArrayList<>()).add(answer);
            }
        }

        List<QuestionForm> questions = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : questionTexts.entrySet()) {
            Integer qId = entry.getKey();
            String qText = entry.getValue();
            List<AnswerOptionForm> qAnswers = questionAnswers.getOrDefault(qId, Collections.emptyList());

            questions.add(new QuestionForm(qId, qText, qAnswers));
        }

        return new CreateTestForm(title, topic, questions);
    }

    private static int extractQuestionIndex(String paramName) {
        String[] parts = paramName.split("_");
        return Integer.parseInt(parts[1]);
    }

    private static int extractAnswerIndex(String paramName) {
        String[] parts = paramName.split("_");
        return Integer.parseInt(parts[2]);
    }
}