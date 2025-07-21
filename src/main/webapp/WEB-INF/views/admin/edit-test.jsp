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
    .error-message {
      color: #f44336;
      font-size: 12px;
      margin-top: 5px;
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
    console.log("Initial test state:", testState); // Отладочный вывод

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
        questionDiv.dataset.questionIndex = qIndex; // Сохраняем индекс вопроса

        var questionLabel = document.createElement('label');
        questionLabel.innerHTML = '<span class="question-number">Вопрос ' + questionNumber + ':</span>';

        var textarea = document.createElement('textarea');
        textarea.rows = 3;
        textarea.value = question.text || '';
        textarea.placeholder = "Введите текст вопроса";
        textarea.dataset.questionId = qIndex; // Связываем с вопросом

        var answerContainer = document.createElement('div');
        answerContainer.className = 'answer-block';

        var addAnswerBtn = document.createElement('button');
        addAnswerBtn.type = 'button';
        addAnswerBtn.className = 'add-answer-btn';
        addAnswerBtn.textContent = '+ Добавить ответ';
        addAnswerBtn.dataset.questionId = qIndex;

        var deleteQuestionBtn = document.createElement('button');
        deleteQuestionBtn.type = 'button';
        deleteQuestionBtn.className = 'delete-question-btn delete-btn';
        deleteQuestionBtn.textContent = 'Удалить вопрос';

        // Рендеринг ответов
        if (question.answers && question.answers.length > 0) {
          question.answers.forEach(function(answer, aIndex) {
            var answerDiv = document.createElement('div');
            answerDiv.className = 'answer-item';
            answerDiv.dataset.answerIndex = aIndex;

            if (answer.isCorrect) {
              answerDiv.classList.add('correct-answer');
            }

            var answerLabel = document.createElement('label');
            answerLabel.innerHTML = '<span class="answer-number">Ответ ' + (aIndex + 1) + ':</span>';

            var answerInput = document.createElement('input');
            answerInput.type = 'text';
            answerInput.value = answer.text || '';
            answerInput.placeholder = "Введите текст ответа";
            answerInput.required = true;
            answerInput.dataset.answerIndex = aIndex;

            answerInput.addEventListener('input', function(e) {
              question.answers[aIndex].text = e.target.value;
            });

            var checkboxLabel = document.createElement('label');
            checkboxLabel.style.marginLeft = '10px';

            var checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.checked = answer.isCorrect;
            checkbox.style.marginRight = '5px';
            checkbox.dataset.answerIndex = aIndex;

            checkbox.addEventListener('change', function(e) {
              question.answers[aIndex].isCorrect = e.target.checked;
              answerDiv.classList.toggle('correct-answer', e.target.checked);
            });

            checkboxLabel.appendChild(checkbox);
            checkboxLabel.appendChild(document.createTextNode('Правильный ответ'));

            var deleteBtn = document.createElement('button');
            deleteBtn.type = 'button';
            deleteBtn.className = 'delete-btn';
            deleteBtn.textContent = 'Удалить';
            deleteBtn.dataset.answerIndex = aIndex;

            deleteBtn.addEventListener('click', function() {
              if (question.answers.length <= 1) {
                alert("Должен остаться хотя бы один ответ!");
                return;
              }

              if (confirm('Удалить этот ответ?')) {
                question.answers.splice(aIndex, 1);
                renderQuestions(); // Полная перерисовка после удаления
              }
            });

            answerDiv.appendChild(answerLabel);
            answerDiv.appendChild(answerInput);
            answerDiv.appendChild(checkboxLabel);
            answerDiv.appendChild(deleteBtn);

            answerContainer.appendChild(answerDiv);
          });
        }

        // Обработчики для вопросов
        textarea.addEventListener('input', function(e) {
          testState.questions[qIndex].text = e.target.value;
        });

        addAnswerBtn.addEventListener('click', function() {
          if (!testState.questions[qIndex].answers) {
            testState.questions[qIndex].answers = [];
          }

          testState.questions[qIndex].answers.push({
            id: Date.now(),
            text: "",
            isCorrect: false
          });

          renderQuestions();
        });

        deleteQuestionBtn.addEventListener('click', function() {
          if (testState.questions.length <= 1) {
            alert("Тест должен содержать хотя бы один вопрос!");
            return;
          }

          if (confirm('Удалить этот вопрос и все ответы в нём?')) {
            testState.questions.splice(qIndex, 1);
            renderQuestions();
          }
        });

        questionDiv.appendChild(questionLabel);
        questionDiv.appendChild(textarea);
        questionDiv.appendChild(answerContainer);
        questionDiv.appendChild(addAnswerBtn);
        questionDiv.appendChild(deleteQuestionBtn);

        container.appendChild(questionDiv);
      });
    }

    // Обработчик добавления нового вопроса
    document.getElementById('add-question-btn').addEventListener('click', function() {
      if (!testState.questions) {
        testState.questions = [];
      }

      testState.questions.push({
        text: "",
        answers: []
      });

      renderQuestions();
    });

    // Обработчик отправки формы
    document.getElementById('test-form').addEventListener('submit', function(e) {
      e.preventDefault();

      // Проверка на пустые вопросы
      const hasEmptyQuestions = testState.questions.some(q => !q.text.trim());
      if (hasEmptyQuestions) {
        alert("Все вопросы должны содержать текст!");
        return;
      }

      // Проверка на пустые ответы
      const hasEmptyAnswers = testState.questions.some(q =>
              q.answers && q.answers.some(a => !a.text.trim())
      );
      if (hasEmptyAnswers) {
        alert("Все ответы должны содержать текст!");
        return;
      }

      // Обновляем title и topic
      testState.title = document.getElementById('test-title').value;
      testState.topic = document.getElementById('test-topic').value;

      // Отправка данных
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
                  return response.text().then(text => { throw new Error(text) });
                }
              })
              .catch(function(error) {
                console.error('Error:', error);
                alert('Ошибка при сохранении: ' + error.message);
              });
    });

    // Первоначальный рендеринг
    renderQuestions();
  });
</script>

</body>
</html>