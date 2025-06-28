package com.svtsygankov.project_servlet_java_rush.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTestForm {
    private String title;
    private String topic;
    private List<QuestionForm> questions;
}
