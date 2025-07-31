<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 31.07.2025
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="result-content">
    <h2>Результат тестирования</h2>

    <div class="test-info">
        <h3>${test.title}</h3>
        <p class="topic"><strong>Тема:</strong> ${test.topic}</p>
        <p class="date">
            <strong>Дата прохождения:</strong>
            <fmt:formatDate value="${result.date}" pattern="dd.MM.yyyy HH:mm"/>
        </p>
    </div>

    <div class="score-summary">
        <div class="progress-container">
            <div class="progress-bar"
                 style="width: ${(result.correctAnswersCount/result.answers.size())*100}%">
            </div>
        </div>
        <p class="score">
            Правильных ответов: <strong>${result.correctAnswersCount} из ${result.answers.size()}</strong>
            (<fmt:formatNumber value="${(result.correctAnswersCount/result.answers.size())*100}"
                               maxFractionDigits="2"/>%)
        </p>
    </div>

    <div class="questions-list">
        <h4>Детализация ответов:</h4>

        <c:forEach items="${result.answers}" var="answer" varStatus="loop">
            <div class="question ${answer.correct ? 'correct' : 'incorrect'}">
                <div class="question-header">
                    <span class="question-number">Вопрос ${loop.index + 1}:</span>
                    <span class="question-text">${answer.askedQuestion}</span>
                </div>
                <p class="user-answer">Ваш ответ: ${answer.selectedAnswer}</p>
                <p class="verdict">
                        ${answer.correct ? '✓ Верно' : '✗ Неверно'}
                </p>
            </div>
        </c:forEach>
    </div>

    <div class="actions">
        <a href="/tests" class="btn btn-primary">Вернуться к тестам</a>
        <a href="/secure/history" class="btn btn-secondary">История тестирования</a>
    </div>
</div>

<style>
    /* Стили специфичные только для этой страницы */
    .result-content {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background: white;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .test-info h3 {
        color: #2c3e50;
        margin-bottom: 5px;
    }

    .topic, .date {
        color: #555;
        margin: 5px 0;
    }

    .score-summary {
        margin: 20px 0;
        padding: 15px;
        background: #f8f9fa;
        border-radius: 5px;
    }

    .progress-container {
        height: 10px;
        background: #e9ecef;
        border-radius: 5px;
        margin-bottom: 10px;
    }

    .progress-bar {
        height: 100%;
        background: #28a745;
        border-radius: 5px;
    }

    .score {
        font-size: 1.1em;
        text-align: center;
        margin: 10px 0;
    }

    .questions-list {
        margin-top: 30px;
    }

    .question {
        padding: 15px;
        margin-bottom: 15px;
        border-radius: 5px;
    }

    .question-header {
        display: flex;
        gap: 10px;
        margin-bottom: 10px;
    }

    .question-number {
        font-weight: bold;
    }

    .user-answer {
        margin: 8px 0;
        padding-left: 20px;
    }

    .verdict {
        font-weight: bold;
        margin-top: 10px;
        padding-left: 20px;
    }

    .correct {
        background-color: rgba(40, 167, 69, 0.1);
        border-left: 4px solid #28a745;
    }

    .incorrect {
        background-color: rgba(220, 53, 69, 0.1);
        border-left: 4px solid #dc3545;
    }

    .actions {
        display: flex;
        justify-content: center;
        gap: 15px;
        margin-top: 30px;
    }

    .btn {
        padding: 8px 20px;
        border-radius: 4px;
        text-decoration: none;
        font-weight: 500;
    }

    .btn-primary {
        background-color: #007bff;
        color: white;
    }

    .btn-secondary {
        background-color: #6c757d;
        color: white;
    }
</style>