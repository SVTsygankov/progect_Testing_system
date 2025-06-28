package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDao {

    private final ObjectMapper objectMapper;
    private final File testsDirectory;
    private final AtomicInteger currentId = new AtomicInteger(0);

    public TestDao(ObjectMapper objectMapper, File testsDirectory) throws IOException {

        this.objectMapper = objectMapper;
        this.testsDirectory = testsDirectory;

        if (!testsDirectory.exists()) {
            testsDirectory.mkdirs();
        }

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

        if (testsDirectory.exists() && testsDirectory.isDirectory()) {
            File[] jsonFiles = testsDirectory.listFiles((dir, name) -> name.matches("test\\d+\\.json"));
            if (jsonFiles != null) {
                for (File file : jsonFiles) {
                   Test test = objectMapper.readValue(file, Test.class);
                   tests.add(test);
                }
            }
        }
        return tests;
    }

    public Test findById(int id) throws IOException {
        File file = new File(testsDirectory, "test" + id + ".json");
        if (!file.exists()) {
            return null;
        }
        return objectMapper.readValue(file, Test.class);
    }

    public List<Test> findByTitle(String title) throws IOException {
        List<Test> tests = findAll();
        return tests.stream()
                .filter(test -> test.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    public List<Test> findByTopic(String topic) throws IOException {
        List<Test> tests = findAll();
        return tests.stream()
                .filter(test -> test.getTopic().toLowerCase().contains(topic.toLowerCase()))
                .toList();
    }

    public void save(Test test) throws IOException {
        String filename = "test" + test.getId() + ".json";
        File fileToSave = new File(testsDirectory, filename);
        objectMapper.writeValue(fileToSave, test);
    }

    public boolean deleteById(int id) throws IOException {
        String filename = "test" + id + ".json";
        File fileToDelete = new File(testsDirectory, filename);

        if (!fileToDelete.exists()) {
            return false;
        }

        return fileToDelete.delete();
    }

    // Вспомогательный метод: извлекает ID из имени файла test123.json
    private Integer extractTestId(File file) {
        String name = file.getName();
        return Integer.parseInt(name.replaceAll("[^\\d]", ""));
    }
}