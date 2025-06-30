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

    public Test findById(Integer id) throws IOException {
        return testDao.findById(id);
    }

    public void createTest(String title, String topic, Long createdById, List<Question> questions) throws IOException {
        Test test = Test.builder()
                .id(testDao.getNextId())
                .title(title)
                .topic(topic)
                .created_by(createdById)
                .questions(questions)
                .build();
        testDao.save(test);
    }

    public void updateTest(int id, String title, String topic, List<Question> questions) throws IOException {
        Test existingTest = testDao.findById(id);
        if (existingTest == null) {
            throw new IOException("Тест с ID " + id + " не найден");
        }

        Test updatedTest = Test.builder()
                .id(id)
                .title(title)
                .topic(topic)
                .created_by(existingTest.getCreated_by()) // сохраняем оригинального автора
                .questions(questions)
                .build();

        testDao.save(updatedTest);
    }
}
