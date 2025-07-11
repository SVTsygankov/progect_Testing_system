package com.svtsygankov.project_servlet_java_rush.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.dto.CreateTestForm;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class TestFormParser {
    public static CreateTestForm parse(HttpServletRequest req, ObjectMapper objectMapper)
            throws IOException {

        // Читаем тело запроса
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        if (json.isEmpty()) {
            throw new IllegalArgumentException("Отсутствуют данные теста");
        }

        return objectMapper.readValue(json, CreateTestForm.class);
    }
}