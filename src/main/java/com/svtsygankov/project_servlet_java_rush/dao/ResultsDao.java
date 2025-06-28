package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.Result;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ResultsDao extends BaseDao<Result> {

    private final AtomicLong currentId = new AtomicLong(0L);

    public ResultsDao(ObjectMapper mapper, File file) throws IOException {
        super(mapper, file, new TypeReference<List<Result>>() {
        });
        initCurrentId();
    }

    private void initCurrentId() throws IOException {
        List<Result> results = findAll();
        if (!results.isEmpty()) {
            long maxId = results.stream()
                    .mapToLong(Result::getId)
                    .max()
                    .getAsLong();

            currentId.set(maxId);
        }
    }
}
