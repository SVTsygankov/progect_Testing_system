package com.svtsygankov.project_servlet_java_rush.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String text;
    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
