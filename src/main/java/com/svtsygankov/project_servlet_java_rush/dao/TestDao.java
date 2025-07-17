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
    private AtomicInteger testIdCounter;

    public TestDao(ObjectMapper objectMapper, File testsDirectory) throws IOException {

        this.objectMapper = objectMapper;
        this.testsDirectory = testsDirectory;
        initialize();

    }

    private void initialize() {
        // Создаем директорию, если не существует
        if (!testsDirectory.exists()) {
            testsDirectory.mkdirs();
        }

        // Инициализируем счетчик
        this.testIdCounter = new AtomicInteger(calculateMaxTestId() + 1);
    }

    private int calculateMaxTestId() {
        File[] testFiles = testsDirectory.listFiles((dir, name) ->
                name.startsWith("test") && name.endsWith(".json"));

        if (testFiles == null) return 0;

        int maxId = 0;
        for (File file : testFiles) {
            try {
                String idStr = file.getName()
                        .replace("test", "")
                        .replace(".json", "");
                int id = Integer.parseInt(idStr);
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {
                System.err.println("Invalid test filename: " + file.getName());
            }
        }
        return maxId;
    }

    public int getNextId() {
        return testIdCounter.getAndIncrement();
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
        File testFile = new File(testsDirectory, filename);
        objectMapper.writeValue(testFile, test);
    }

    public boolean deleteById(int id) throws IOException {
        String filename = "test" + id + ".json";
        File fileToDelete = new File(testsDirectory, filename);

        if (!fileToDelete.exists()) {
            return false;
        }

        return fileToDelete.delete();
    }
}