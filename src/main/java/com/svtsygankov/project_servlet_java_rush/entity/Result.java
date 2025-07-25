package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Long id;
    private Long userId;
    private Integer testId;
    private Instant date;
    private List<UserAnswer>  answers;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAnswer {
        private String askedQuestion;  // Текст вопроса на момент прохождения
        private String selectedAnswer; // Текст выбранного ответа
        private boolean isCorrect; // Правильность ответа
    }
}
