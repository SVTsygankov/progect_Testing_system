package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOptionForm {
    private Integer id;
    private String text;
    private boolean correct;
}
