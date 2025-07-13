/*
package com.svtsygankov.project_servlet_java_rush.util;

public class IdGenerator {
    public static String generateTestId(Collection<Test> existingTests, TestDao testDao) {
        return String.valueOf(testDao.findMaxTestId(existingTests) + 1);
    }

    public static String generateQuestionId(Test test, TestDao testDao) {
        return String.valueOf(testDao.findMaxQuestionId(test) + 1);
    }

    public static String generateAnswerId(Question question, TestDao testDao) {
        return String.valueOf(testDao.findMaxAnswerId(question) + 1);
    }
}

 */