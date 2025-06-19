package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.User;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class UserDao extends BaseDao<User>{

    private final AtomicLong currentId = new AtomicLong(0L);

    public UserDao(ObjectMapper mapper, File file) {
        super(mapper, file, new TypeReference<List<User>>() {});
        initCurrentId();
    }

    private void initCurrentId() {
        List<User> users = findAll();
        if (!users.isEmpty()) {
            long maxId = users.stream()
                    .mapToLong(User::getId)
                    .max()
                    .getAsLong();

            currentId.set(maxId);
        }
    }

    public long getNextId() {
        return currentId.incrementAndGet();
    }

    @Override
    public void save(User user) {
        super.save(user);
    }
}
