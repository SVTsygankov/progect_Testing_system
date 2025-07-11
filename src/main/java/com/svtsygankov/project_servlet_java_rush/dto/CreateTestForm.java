package com.svtsygankov.project_servlet_java_rush.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestForm {
    private Integer id;
    private String title;
    private String topic;
    private List<QuestionForm> questions;
}

