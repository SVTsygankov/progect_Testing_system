package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Question {
    private Integer id;
    private String text;
    private List<AnswerOption> answers;
}
