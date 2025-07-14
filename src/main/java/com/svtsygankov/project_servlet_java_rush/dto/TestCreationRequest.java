package com.svtsygankov.project_servlet_java_rush.dto;

import com.svtsygankov.project_servlet_java_rush.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class TestCreationRequest {

    private String title;
    private String topic;
    private List<Question> questions;
}
