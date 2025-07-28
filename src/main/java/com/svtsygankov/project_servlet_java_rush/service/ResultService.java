package com.svtsygankov.project_servlet_java_rush.service;

import com.svtsygankov.project_servlet_java_rush.dao.ResultDao;
import com.svtsygankov.project_servlet_java_rush.entity.Answer;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

            if (answerIndex != null && answerIndex < question.getAnswers().size()) {
                Answer selectedAnswer = question.getAnswers().get(answerIndex);

                userAnswers.add(Result.UserAnswer.builder()
                        .askedQuestion(question.getText())
                        .selectedAnswer(selectedAnswer.getText())
                        .isCorrect(selectedAnswer.isCorrect())
                        .build());
            }
        }

        // 4. Создаем и сохраняем результат
        Result result = Result.builder()
                .id(resultDao.getNextId())
                .userId(userId)
                .testId(testId)
                .date(Instant.now())
                .answers(userAnswers)
                .build();

        resultDao.save(result);
        return result;
    }
}
