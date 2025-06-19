package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestDao {

    private final ObjectMapper objectMapper;
    private final File testsDirectory;

    private final AtomicInteger currentId = new AtomicInteger(0);

    public TestDao(ObjectMapper objectMapper, File testsDirectory) throws IOException {
        this.objectMapper = objectMapper;
        this.testsDirectory = testsDirectory;
        initCurrentId();
    }

    private void initCurrentId() throws IOException {
        List<Test> tests = findAll();
        if (!tests.isEmpty()) {
            int maxId = tests.stream()
                    .mapToInt(Test::getId)
                    .max()
                    .getAsInt();

            currentId.set(maxId);
        }
    }

    public int getNextId() {
        return currentId.incrementAndGet();
    }

    public List<Test> findAll() throws IOException {
        List<Test> tests = new ArrayList<>();

        for (File file : Objects.requireNonNull(testsDirectory.listFiles())) {
            if (file.getName().startsWith("test") && file.getName().endsWith(".json")) {
                Test test = objectMapper.readValue(file, Test.class);
                tests.add(test);
            }
        }
        return tests;
    }

    // Найти тест по ID
    public Test getTestById(Integer id) throws IOException {
        File file = new File(testsDirectory, "test" + id + ".json");
        if (!file.exists()) {
            return null;
        }
        return objectMapper.readValue(file, Test.class);
    }

    // Поиск тестов по имени (частичное совпадение)
    public List<Test> findTestsByName(String partialName) throws IOException {
        String lowerCase = partialName.toLowerCase();
        return findAll().stream()
                .filter(t -> t.getName() != null && t.getName().toLowerCase().contains(lowerCase))
                .collect(Collectors.toList());
    }

    // Поиск тестов по теме (частичное совпадение)
    public List<Test> findTestsByTopic(String partialTopic) throws IOException {
        String lowerCase = partialTopic.toLowerCase();
        return findAll().stream()
                .filter(t -> t.getTopic() != null && t.getTopic().toLowerCase().contains(lowerCase))
                .collect(Collectors.toList());
    }

    // Сохранить тест в файл
    public void save(Test test) throws IOException {
        File file = new File(testsDirectory, "test" + test.getId() + ".json");
        objectMapper.writeValue(file, test);
    }

    // Удалить тест по ID
    public boolean deleteTest(Integer id) {
        File file = new File(testsDirectory, "test" + id + ".json");
        return file.exists() && file.delete();
    }
}