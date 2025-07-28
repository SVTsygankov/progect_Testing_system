package com.svtsygankov.project_servlet_java_rush.listener;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

abstract class InstantMixin {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Instant date;
}