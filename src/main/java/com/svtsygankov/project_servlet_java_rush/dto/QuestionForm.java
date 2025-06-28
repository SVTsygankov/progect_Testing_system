package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.Data;

import java.util.List;
@Data
public class QuestionForm {
    private String text;
    private List<AnswerOptionForm> answers;
}
