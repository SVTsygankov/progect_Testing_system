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

//    public Test findById(Integer id) throws IOException {
//        return testDao.findById(id);
//    }

    public Test createTest(String title, String topic, Long authorId,
                       List<Question> questions) throws IOException {
        System.out.println("Создаем новый тест");
        Test test = Test.builder()
                    .id(testDao.getNextId())
                    .title(title)
                    .topic(topic)
                    .created_by(authorId)
                    .questions(questions)
                    .build();
        testDao.save(test);
        return test;
    }

    public void updateTest(Test test) throws IOException {
        testDao.save(test);
    }

    public Test findById(int id) throws IOException {
        return testDao.findById(id);
    }
    public boolean deleteById(int id) throws IOException {
        return testDao.deleteById(id);
    }
    public long getTestCount() throws IOException {
        return testDao.findAll().size();
    }

}
