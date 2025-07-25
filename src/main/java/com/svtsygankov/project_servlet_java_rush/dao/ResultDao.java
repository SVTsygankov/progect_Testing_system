package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.Result;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ResultDao extends BaseDao<Result> {

    private final AtomicLong currentId = new AtomicLong(0L);

    public ResultDao(ObjectMapper mapper, File file) throws IOException {
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
                    .orElse(0L);

            currentId.set(maxId);
        }
    }

    public long getNextId() {
        return currentId.incrementAndGet();
    }

    @Override
    public void save(Result result) throws IOException {
        super.save(result);
    }

    public List<Result> findAllByUserId(long userId) throws IOException {
        return findAll().stream()
                .filter(r -> r.getUserId() == userId)
                .toList();
    }
}
