package com.svtsygankov.project_servlet_java_rush.dto;

import com.svtsygankov.project_servlet_java_rush.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestForm {
    private Integer id;
    private String title;
    private String topic;
    private List<Question> questions;
}

