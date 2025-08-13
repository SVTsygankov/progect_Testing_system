package com.svtsygankov.project_servlet_java_rush.service;

import com.svtsygankov.project_servlet_java_rush.dao.ResultDao;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultService {

    private final ResultDao resultDao;
    private final TestService testService;
    private final UserService userService;

    public ResultService(ResultDao resultDao, TestService testService, UserService userService) {
        this.resultDao = resultDao;
        this.testService = testService;
        this.userService = userService;
    }

    public Result createTestResult(Long userId, Integer testId,  Map<Integer, Integer> questionToAnswerIndex) throws IOException {

        // 2. Получаем тест из базы
        Test test = testService.findById(testId);
        if (test == null) {
            throw new IllegalArgumentException("Тест не найден");
        }

        // 3. Создаем список UserAnswer
        List<Result.UserAnswer> userAnswers = new ArrayList<>();
        List<Question> questions = test.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            Integer answerIndex = questionToAnswerIndex.get(i);

            // Находим выбранный пользователем ответ
            Answer selectedAnswer = null;
            boolean isCorrect = false;
            String selectedAnswerText = "Не ответил";

            if (answerIndex != null && answerIndex < question.getAnswers().size()) {
                selectedAnswer = question.getAnswers().get(answerIndex);
                selectedAnswerText = selectedAnswer.getText();
                isCorrect = selectedAnswer.isCorrect();

                // Находим правильный ответ (или несколько)
                String correctAnswerText = question.getAnswers().stream()
                        .filter(Answer::isCorrect)
                        .map(Answer::getText)
                        .findFirst()
                        .orElse("Правильный ответ не указан");

                // Создаём UserAnswer с correctAnswer
                Result.UserAnswer userAnswer = Result.UserAnswer.builder()
                        .askedQuestion(question.getText())
                        .selectedAnswer(selectedAnswerText)
                        .correct(isCorrect)
                        .correctAnswer(correctAnswerText)  // ✅ Добавляем правильный ответ
                        .build();

                userAnswers.add(userAnswer);
            }
        }
            // 4. Создаем и сохраняем результат
            Result result = Result.builder()
                    .id(resultDao.getNextId())
                    .userId(userId)
                    .testId(testId)
                    .date(LocalDateTime.now())
                    .answers(userAnswers)
                    .build();

        resultDao.save(result);
        return result;
    }

    public List<Result> getUserResults(long userId) throws IOException {
        return resultDao.findAll().stream()
                .filter(r -> r.getUserId() == userId)
                .sorted(Comparator.comparing(Result::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Result getResultById(long resultId) throws IOException {
        return resultDao.findById(resultId);
    }
}
