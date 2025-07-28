<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 27.07.2025
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Результаты теста</title>
  <style>
    .result-container { margin: 20px; padding: 20px; border: 1px solid #ddd; }
    .correct { color: green; }
    .incorrect { color: red; }
    .score { font-size: 1.2em; font-weight: bold; margin: 20px 0; }
  </style>
</head>
<body>
<div class="result-container">
  <h1>Результаты тестирования</h1>
  <p>Тест: ${result.testId}</p>
  <p>Дата: ${result.date}</p>

  <div class="score">
    Правильных ответов:
    ${result.answers.stream().filter(a -> a.correct).count()} / ${result.answers.size()}
  </div>

  <c:forEach items="${result.answers}" var="answer" varStatus="loop">
    <div class="answer ${answer.correct ? 'correct' : 'incorrect'}">
      <h3>Вопрос ${loop.index + 1}: ${answer.askedQuestion}</h3>
      <p>Ваш ответ: ${answer.selectedAnswer}</p>
      <p>Статус: ${answer.correct ? 'Верно ✓' : 'Неверно ✗'}</p>
    </div>
  </c:forEach>

  <a href="${pageContext.request.contextPath}/secure/tests">Вернуться к списку тестов</a>
</div>
</body>
</html>
