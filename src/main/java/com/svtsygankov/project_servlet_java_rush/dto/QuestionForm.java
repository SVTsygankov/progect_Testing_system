package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class QuestionForm {
    private Integer id;
    private String text;
    private List<AnswerOptionForm> answers;
}

