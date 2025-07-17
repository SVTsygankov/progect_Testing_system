<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 28.06.2025
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%-- /WEB-INF/views/admin/create-test.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Создание теста</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    .question-block {
      background: #f8f9fa;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 20px;
      border: 1px solid #dee2e6;
    }
    .answer-block {
      margin-left: 20px;
      margin-bottom: 10px;
    }
    .correct-answer {
      background-color: #e8f5e9;
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="#">Тестовая система</a>
  </div>
</nav>

<div class="container mt-4">
  <h1 class="mb-4">Создание нового теста</h1>

  <form id="testForm">
    <div class="mb-3">
      <label for="title" class="form-label">Название теста:</label>
      <input type="text" class="form-control" id="title" name="title" required>
    </div>

    <div class="mb-3">
      <label for="topic" class="form-label">Тема:</label>
      <input type="text" class="form-control" id="topic" name="topic">
    </div>

    <div id="questionsContainer" class="mb-4"></div>

    <div class="mb-3">
      <button type="button" class="btn btn-primary" onclick="addQuestion()">Добавить вопрос</button>
      <button type="submit" class="btn btn-success">Сохранить тест</button>
    </div>
  </form>
</div>

<footer class="bg-dark text-white mt-5 py-3">
  <div class="container text-center">
    &copy; 2023 Тестовая система
  </div>
</footer>

<script>
  var questionCounter = 0;
  var answerCounters = {};

  function addQuestion() {
    questionCounter++;
    answerCounters[questionCounter] = 0;

    var container = document.getElementById('questionsContainer');
    var questionDiv = document.createElement('div');
    questionDiv.className = 'question-block';
    questionDiv.id = 'question-' + questionCounter;

    questionDiv.innerHTML =
            '<h3>Вопрос ' + questionCounter + '</h3>' +
            '<textarea class="form-control mb-3" name="questions[' + (questionCounter-1) + '].text" required></textarea>' +
            '<div id="answers-' + questionCounter + '"></div>' +
            '<button type="button" class="btn btn-sm btn-primary me-2" onclick="addAnswer(' + questionCounter + ')">Добавить ответ</button>' +
            '<button type="button" class="btn btn-sm btn-danger" onclick="removeQuestion(' + questionCounter + ')">Удалить вопрос</button>';

    container.appendChild(questionDiv);

    // Добавляем сразу 2 ответа
    addAnswer(questionCounter);
    addAnswer(questionCounter);
  }

  function addAnswer(questionId) {
    answerCounters[questionId] = (answerCounters[questionId] || 0) + 1;
    var answerId = answerCounters[questionId];
    var container = document.getElementById('answers-' + questionId);
    var answerDiv = document.createElement('div');
    answerDiv.className = 'answer-block';

    answerDiv.innerHTML =
            '<div class="input-group mb-2">' +
            '   <div class="input-group-text">' +
            '       <input type="checkbox" name="questions[' + (questionId-1) + '].answers[' + (answerId-1) + '].isCorrect">' +
            '   </div>' +
            '   <input type="text" class="form-control" name="questions[' + (questionId-1) + '].answers[' + (answerId-1) + '].text" required>' +
            '   <button type="button" class="btn btn-outline-danger" onclick="removeAnswer(this)">×</button>' +
            '</div>';

    container.appendChild(answerDiv);

    // Обработчик для подсветки правильного ответа
    var checkbox = answerDiv.querySelector('input[type="checkbox"]');
    checkbox.onchange = function() {
      if (this.checked) {
        answerDiv.classList.add('correct-answer');
      } else {
        answerDiv.classList.remove('correct-answer');
      }
    };
  }

  function removeAnswer(button) {
    button.closest('.answer-block').remove();
  }

  function removeQuestion(questionId) {
    if (questionCounter <= 1) {
      alert("Тест должен содержать хотя бы один вопрос");
      return;
    }

    var questionDiv = document.getElementById('question-' + questionId);
    questionDiv.parentNode.removeChild(questionDiv);
    questionCounter--;
  }

  document.getElementById('testForm').onsubmit = function(e) {
    e.preventDefault();

    var formData = {
      title: document.getElementById('title').value,
      topic: document.getElementById('topic').value,
      questions: []
    };

    var isValid = true;
    var questionBlocks = document.querySelectorAll('.question-block');

    for (var i = 0; i < questionBlocks.length; i++) {
      var questionBlock = questionBlocks[i];
      var questionText = questionBlock.querySelector('textarea').value;
      var questionData = {
        text: questionText,
        answers: []
      };

      if (!questionText.trim()) {
        alert('Вопрос ' + (i+1) + ': текст вопроса не может быть пустым');
        isValid = false;
        break;
      }

      var answerBlocks = questionBlock.querySelectorAll('.answer-block');
      var hasCorrectAnswer = false;

      for (var j = 0; j < answerBlocks.length; j++) {
        var answerBlock = answerBlocks[j];
        var answerText = answerBlock.querySelector('input[type="text"]').value;
        var isCorrect = answerBlock.querySelector('input[type="checkbox"]').checked;

        if (!answerText.trim()) {
          alert('Вопрос ' + (i+1) + ', ответ ' + (j+1) + ': текст ответа не может быть пустым');
          isValid = false;
          break;
        }

        if (isCorrect) hasCorrectAnswer = true;

        questionData.answers.push({
          text: answerText,
          isCorrect: isCorrect
        });
      }

      if (!isValid) break;

      if (!hasCorrectAnswer) {
        alert('Вопрос ' + (i+1) + ' должен иметь хотя бы один правильный ответ');
        isValid = false;
        break;
      }

      formData.questions.push(questionData);
    }

    if (!isValid) return;

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/test/create', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function() {
      if (xhr.status === 201) {
        window.location.href = '/admin/tests';
      } else {
        alert('Ошибка: ' + xhr.responseText);
      }
    };
    xhr.send(JSON.stringify(formData));
  };

  // Инициализация первого вопроса при загрузке
  window.onload = function() {
    addQuestion();
  };
</script>
</body>
</html>