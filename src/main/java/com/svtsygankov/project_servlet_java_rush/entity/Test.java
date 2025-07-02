package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    private Integer id;
    private String title;
    private String topic;
    private Long created_by;        // id админа создавшего тест
    private List<Question> questions;
}
