package com.svtsygankov.project_servlet_java_rush.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Entity {
    private long id;
    private long userId;
    private Integer testId;
    private LocalDateTime date;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserAnswer> answers;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonSerialize(as = UserAnswer.class)
    public static class UserAnswer {
        private String askedQuestion;
        private String selectedAnswer;
//        @JsonProperty("isCorrect")
        private boolean correct;
        private String correctAnswer; // ✅ Новое поле
    }

    @Override
    public long getId() {
        return id;
    }
}