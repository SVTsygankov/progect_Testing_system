package com.svtsygankov.project_servlet_java_rush.entity;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class Result {
    private Long id;
    private Long user_id;
    private Integer test_id;
    private Instant date;
    private List<GivenAnswer>  answers;

}
