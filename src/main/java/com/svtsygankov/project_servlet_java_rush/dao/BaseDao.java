package com.svtsygankov.project_servlet_java_rush.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svtsygankov.project_servlet_java_rush.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class BaseDao<T extends Entity> {

    final ObjectMapper objectMapper;
    final File file;
    private TypeReference<List<T>> typeReference;

        public void save(T object)  throws IOException{
        List<T> objects = findAll();

        objects.add(object);

        objectMapper.writeValue(file, objects);
    }

    @SneakyThrows
    public List<T> findAll() throws IOException {
        List<T> objects = new ArrayList<>();

        // Если файл существует и не пустой — читаем текущий список сущностей
        if (file.exists() && file.length() > 0) {
            objects = objectMapper.readValue(file, typeReference);
        }
        return objects;
    }

    public T findById(long id) throws IOException {
        return findAll().stream()
                .filter(entity -> entity.getId() == id)
                .findFirst()
                .orElse(null); // или бросить исключение
    }

}
