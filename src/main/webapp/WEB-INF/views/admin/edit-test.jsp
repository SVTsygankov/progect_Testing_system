<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 28.06.2025
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%-- /WEB-INF/views/admin/edit-test.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.svtsygankov.project_servlet_java_rush.entity.Test" %>

<%
  Test test = (Test) request.getAttribute("test");
  ObjectMapper objectMapper = (ObjectMapper) application.getAttribute("objectMapper");
  String testJson = objectMapper.writeValueAsString(test)
          .replace("\\", "\\\\")
          .replace("'", "\\'")
          .replace("\"", "\\\"")
          .replace("\n", "\\n")
          .replace("\r", "\\r");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Редактировать тест</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      line-height: 1.6;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .question-block {
      border: 1px solid #ddd;
      padding: 15px;
      margin-bottom: 20px;
      border-radius: 5px;
      background: #f9f9f9;
    }
    .answer-block {
      margin-left: 20px;
      margin-top: 10px;
    }
    .answer-item {
      margin-bottom: 10px;
      padding: 10px;
      background: white;
      border-radius: 3px;
      border-left: 4px solid #4CAF50;
    }
    button {
      margin-right: 10px;
      padding: 8px 15px;
      cursor: pointer;
      background: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 14px;
    }
    button:hover {
      opacity: 0.9;
    }
    .delete-btn {
      background: #f44336;
    }
    textarea {
      width: 100%;
      padding: 8px;
      margin-top: 5px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    input[type="text"] {
      padding: 8px;
      width: 70%;
      margin-left: 5px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    label {
      display: block;
      margin: 10px 0 5px;
      font-weight: bold;
      color: #333;
    }
    .question-number {
      font-weight: bold;
      color: #2196F3;
    }
    .answer-number {
      font-weight: bold;
      color: #FF9800;
    }
  </style>
</head>
<body>
<h2>Редактировать тест</h2>

<form id="test-form">
  <input type="hidden" id="test-id" value="${test.id}">

  <div class="form-group">
    <label>Название теста:</label>
    <input type="text" id="test-title" value="${fn:escapeXml(test.title)}" required>
  </div>

  <div class="form-group">
    <label>Тема теста:</label>
    <input type="text" id="test-topic" value="${fn:escapeXml(test.topic)}" required>
  </div>

  <h3>Вопросы</h3>
  <div id="questions-container">
    <!-- Вопросы будут рендериться здесь -->
  </div>

  <button type="button" id="add-question-btn">+ Добавить вопрос</button>
  <button type="submit">Сохранить изменения</button>
</form>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    // Инициализация данных теста
    var testState = JSON.parse('<%= testJson %>');

    // Функция рендеринга вопросов и ответов
    function renderQuestions() {
      var container = document.getElementById('questions-container');
      container.innerHTML = '';

      if (!testState.questions || testState.questions.length === 0) {
        container.innerHTML = '<p>Пока нет вопросов. Добавьте первый вопрос.</p>';
        return;
      }

      testState.questions.forEach(function(question, qIndex) {
        var questionNumber = qIndex + 1;
        var questionDiv = document.createElement('div');
        questionDiv.className = 'question-block';

        var questionLabel = document.createElement('label');
        questionLabel.innerHTML = '<span class="question-number">Вопрос ' + questionNumber + ':</span>';

        var textarea = document.createElement('textarea');
        textarea.rows = 3;
        textarea.value = question.text || '';
        textarea.placeholder = "Введите текст вопроса";

        var answerContainer = document.createElement('div');
        answerContainer.className = 'answer-block';

        var addAnswerBtn = document.createElement('button');
        addAnswerBtn.type = 'button';
        addAnswerBtn.className = 'add-answer-btn';
        addAnswerBtn.textContent = '+ Добавить ответ';

        var deleteQuestionBtn = document.createElement('button');
        deleteQuestionBtn.type = 'button';
        deleteQuestionBtn.className = 'delete-question-btn delete-btn';
        deleteQuestionBtn.textContent = 'Удалить вопрос';

        questionDiv.appendChild(questionLabel);
        questionDiv.appendChild(textarea);
        questionDiv.appendChild(answerContainer);
        questionDiv.appendChild(addAnswerBtn);
        questionDiv.appendChild(deleteQuestionBtn);

        container.appendChild(questionDiv);

        // Рендеринг ответов
        if (question.answers && question.answers.length > 0) {
          question.answers.forEach(function(answer, aIndex) {
            console.log("Answer data:", answer);

            var answerNumber = aIndex + 1;
            var answerDiv = document.createElement('div');
            answerDiv.className = 'answer-item';

            if (answer.isCorrect) {
              answerDiv.classList.add('correct-answer');
            }

            var answerLabel = document.createElement('label');
            answerLabel.innerHTML = '<span class="answer-number">Ответ ' + answerNumber + ':</span>';

            var answerInput = document.createElement('input');
            answerInput.type = 'text';
            answerInput.value = answer.text || '';

            var checkboxLabel = document.createElement('label');
            checkboxLabel.style.marginLeft = '10px';

            var checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.checked = answer.isCorrect;
            checkbox.style.marginRight = '5px';

            checkbox.addEventListener('change', function(e) {
              answer.isCorrect = e.target.checked;
              if (e.target.checked) {
                answerDiv.classList.add('correct-answer');
              } else {
                answerDiv.classList.remove('correct-answer');
              }
            });

            checkboxLabel.appendChild(checkbox);
            checkboxLabel.appendChild(document.createTextNode('Правильный ответ'));

            var deleteBtn = document.createElement('button');
            deleteBtn.type = 'button';
            deleteBtn.className = 'delete-btn';
            deleteBtn.textContent = 'Удалить';

            answerDiv.appendChild(answerLabel);
            answerDiv.appendChild(answerInput);
            answerDiv.appendChild(checkboxLabel);
            answerDiv.appendChild(deleteBtn);

            answerContainer.appendChild(answerDiv);
          });
        }

        // Обработчики событий для вопросов
        textarea.addEventListener('input', function(e) {
          question.text = e.target.value;
        });

        addAnswerBtn.addEventListener('click', function() {
          if (!question.answers) question.answers = [];
          question.answers.push({
            id: Date.now(),
            text: "Новый ответ",
            correct: false
          });
          renderQuestions();
        });

        deleteQuestionBtn.addEventListener('click', function() {
          if (confirm('Удалить этот вопрос?')) {
            testState.questions.splice(qIndex, 1);
            renderQuestions();
          }
        });
      });
    }

    // Добавление нового вопроса
    document.getElementById('test-form').addEventListener('submit', function(e) {
      e.preventDefault();

      testState.title = document.getElementById('test-title').value;
      testState.topic = document.getElementById('test-topic').value;

      fetch('/admin/test/edit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(testState)  // Отправляем весь объект testState
      })
              .then(function(response) {
                if (response.ok) {
                  window.location.href = '/admin/tests';
                } else {
                  alert('Ошибка при сохранении теста');
                }
              })
              .catch(function(error) {
                console.error('Error:', error);
                alert('Произошла ошибка при отправке данных');
              });
    });

    // Отправка формы
    document.getElementById('test-form').addEventListener('submit', function(e) {
      e.preventDefault();

      testState.title = document.getElementById('test-title').value;
      testState.topic = document.getElementById('test-topic').value;

      fetch('/admin/test/edit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(testState)
      })
              .then(function(response) {
                if (response.ok) {
                  window.location.href = '/admin/tests';
                } else {
                  alert('Ошибка при сохранении теста');
                }
              })
              .catch(function(error) {
                console.error('Error:', error);
                alert('Произошла ошибка при отправке данных');
              });
    });

    // Первоначальный рендеринг
    renderQuestions();
  });
</script>
</body>
</html>