package com.svtsygankov.project_servlet_java_rush.service;

import com.svtsygankov.project_servlet_java_rush.dao.TestDao;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class TestService {

    private final TestDao testDao;
    public List<Test> findAll() throws IOException {
        return testDao.findAll();
    }

    public void createTest(String name, String topic, Long createdById, List<Question> questions) throws IOException {
        Test test = Test.builder()
                .id(testDao.getNextId())
                .name(name)
                .topic(topic)
                .created_by(createdById)
                .questions(questions)
                .build();
        testDao.save(test);
    }
}
