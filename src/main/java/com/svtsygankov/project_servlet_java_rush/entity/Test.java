package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Test {

    private Integer id;
    private String name;
    private String topic;
    private Long created_by;
    private List<Question> questions;
}
