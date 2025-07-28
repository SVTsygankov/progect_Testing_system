package com.svtsygankov.project_servlet_java_rush.listener;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
abstract class UserAnswerMixin {
    @JsonProperty("askedQuestion") String askedQuestion;
    @JsonProperty("selectedAnswer") String selectedAnswer;
    @JsonProperty("isCorrect") boolean isCorrect;
}