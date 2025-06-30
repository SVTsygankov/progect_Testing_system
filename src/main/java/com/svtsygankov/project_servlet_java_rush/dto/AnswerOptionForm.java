package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerOptionForm {
    private final Integer id;
    private String text;
    private boolean isCorrect;
}
