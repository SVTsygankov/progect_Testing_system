<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 25.07.2025
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Прохождение теста</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      margin: 20px;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      background: white;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h1 {
      color: #2c3e50;
      border-bottom: 2px solid #3498db;
      padding-bottom: 10px;
    }
    .question {
      margin-bottom: 25px;
      padding: 15px;
      border: 1px solid #ddd;
      border-radius: 5px;
      background: #f9f9f9;
    }
    .question-text {
      font-weight: bold;
      margin-bottom: 10px;
      font-size: 1.1em;
    }
    .answer-option {
      margin: 8px 0;
    }
    .submit-btn {
      background: #3498db;
      color: white;
      border: none;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
      margin-top: 20px;
    }
    .submit-btn:hover {
      background: #2980b9;
    }
    .test-title {
      color: #3498db;
      margin-bottom: 30px;
    }
  </style>
</head>
<body>
<div class="container">
  <h1 class="test-title">Тест: ${test.title}</h1>

  <form action="${pageContext.request.contextPath}/submit-test" method="post">
    <input type="hidden" name="testId" value="${test.id}">

    <c:forEach items="${test.questions}" var="question" varStatus="qLoop">
      <div class="question">
        <div class="question-text">
          Вопрос ${qLoop.index + 1}: ${question.text}
        </div>

        <c:forEach items="${question.answers}" var="answer" varStatus="aLoop">
          <div class="answer-option">
            <input type="radio"
                   id="q_${qLoop.index}_a_${aLoop.index}"
                   name="question_${qLoop.index}"
                   value="${aLoop.index}"
                   required>
            <label for="q_${qLoop.index}_a_${aLoop.index}">
                ${answer.text}
            </label>
          </div>
        </c:forEach>
      </div>
    </c:forEach>

    <button type="submit" class="submit-btn">Завершить тест</button>
  </form>
</div>
</body>
</html>
