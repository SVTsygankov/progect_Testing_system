package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForm {
    private Integer id;
    private String text;
    private List<AnswerOptionForm> answers;
}
