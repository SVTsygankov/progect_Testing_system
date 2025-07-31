<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 28.07.2025
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>История тестирования</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .test-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .test-meta {
            display: flex;
            justify-content: space-between;
            color: #666;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>История тестирования</h1>

    <c:if test="${empty results}">
        <p>Нет данных о пройденных тестах</p>
    </c:if>

    <c:forEach items="${results}" var="result">
        <c:set var="test" value="${testsMap[result.testId]}"/>
        <div class="test-card">
            <h3>${test.title}</h3>

            <div class="test-meta">
                <span class="topic">Тема: ${test.topic}</span>
                <span class="date">
                        ${not empty result.date ?
                                result.date.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))
                                : 'Дата не указана'}
                </span>
            </div>

            <p>Правильных ответов: ${result.correctAnswersCount} из ${result.answers.size()}</p>
            <a href="/secure/result-details?id=${result.id}">Подробнее</a>
        </div>
    </c:forEach>
</div>
</body>
</html>