package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class BaseDao<T> {

    final ObjectMapper objectMapper;
    final File file;
    private TypeReference<List<T>> typeReference;

    @SneakyThrows
    public void save(T object) {
        List<T> objects = findAll();

        objects.add(object);

        objectMapper.writeValue(file, objects);
    }

    @SneakyThrows
    public List<T> findAll()  {
        List<T> objects = new ArrayList<>();

        // Если файл существует и не пустой — читаем текущий список сущностей
        if (file.exists() && file.length() > 0) {
            objects = objectMapper.readValue(file, typeReference);
        }
        return objects;
    }
}
