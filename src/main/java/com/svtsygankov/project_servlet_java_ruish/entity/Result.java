package com.svtsygankov.project_servlet_java_ruish.entity;

import java.time.Instant;
import java.util.List;

public class Result {
    private Long id;
    private Long user_id;
    private Integer test_id;
    private Instant date;
    private List<GivenAnswer>  answers;

}
