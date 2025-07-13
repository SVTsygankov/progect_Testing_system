package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Integer id;
    private String text;
    private boolean correct;
}
