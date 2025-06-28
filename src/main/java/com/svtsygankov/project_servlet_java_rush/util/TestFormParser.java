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

public class TestFormParser {

    public static CreateTestForm parse(HttpServletRequest req) {
        List<String> filteredParams = Collections.list(req.getParameterNames()).stream()
                .filter(name -> name.startsWith("questionText_") || name.startsWith("answerText_"))
                .toList();

        Map<Integer, String> questionTexts = parseQuestionTexts(filteredParams, req);
        Map<Integer, List<AnswerOptionForm>> questionAnswers = parseAnswerOptions(filteredParams, req);

        CreateTestForm form = new CreateTestForm();
        form.setTitle(req.getParameter("title"));
        form.setTopic(req.getParameter("topic"));

        List<QuestionForm> questions = questionTexts.entrySet().stream()
                .map(entry -> {
                    int qId = entry.getKey();
                    String qText = entry.getValue();
                    List<AnswerOptionForm> answers = questionAnswers.getOrDefault(qId, Collections.emptyList());

                    QuestionForm question = new QuestionForm();
                    question.setText(qText);
                    question.setAnswers(answers);
                    return question;
                })
                .toList();

        form.setQuestions(questions);
        return form;
    }

    private static Map<Integer, String> parseQuestionTexts(List<String> filteredParams, HttpServletRequest req) {
        Map<Integer, String> questionTexts = new HashMap<>();
        for (String name : filteredParams) {
            if (name.startsWith("questionText_")) {
                int qIndex = extractIndex(name);
                String text = req.getParameter(name);
                questionTexts.put(qIndex, text);
            }
        }
        return questionTexts;
    }

    private static Map<Integer, List<AnswerOptionForm>> parseAnswerOptions(List<String> filteredParams, HttpServletRequest req) {
        Map<Integer, List<AnswerOptionForm>> questionAnswers = new HashMap<>();

        for (String name : filteredParams) {
            if (name.startsWith("answerText_")) {
                String[] parts = name.split("_");
                int qIndex = Integer.parseInt(parts[1]);
                int aIndex = Integer.parseInt(parts[2]);

                String answerText = req.getParameter(name);
                String isCorrectStr = req.getParameter("isCorrect_" + qIndex + "_" + aIndex);
                boolean isCorrect = "on".equals(isCorrectStr);

                AnswerOptionForm answer = new AnswerOptionForm();
                answer.setText(answerText);
                answer.setCorrect(isCorrect);

                questionAnswers.computeIfAbsent(qIndex, k -> new ArrayList<>()).add(answer);
            }
        }

        return questionAnswers;
    }

    private static int extractIndex(String paramName) {
        String[] parts = paramName.split("_");
        return Integer.parseInt(parts[1]);
    }
}
