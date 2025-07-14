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
  <title>Create New Test</title>
  <style>
    /* Стили из edit-test.gsp */
    .test-form { margin: 20px; }
    .question-block { margin-bottom: 20px; border: 1px solid #ddd; padding: 10px; }
    .answer-block { margin-left: 20px; margin-bottom: 10px; }
    .correct-answer { background-color: #f0fff0; }
  </style>
</head>
<body>
<div class="test-form">
  <h1>Create New Test</h1>

  <form id="testForm" action="/admin/test/create" method="post">
    <div>
      <label for="title">Test Title:</label>
      <input type="text" id="title" name="title" required>
    </div>

    <div>
      <label for="topic">Topic:</label>
      <input type="text" id="topic" name="topic">
    </div>

    <div id="questionsContainer">
      <!-- Questions will be added here dynamically -->
    </div>

    <button type="button" onclick="addQuestion()">Add Question</button>
    <button type="submit">Save Test</button>
  </form>
</div>

<script>
  // Question counter
  var questionCounter = 0;

  // Answer counters per question
  var answerCounters = {};

  // Initialize with one question
  window.onload = function() {
    addQuestion();
  };

  function addQuestion() {
    questionCounter++;
    answerCounters[questionCounter] = 0;

    var container = document.getElementById('questionsContainer');
    var questionDiv = document.createElement('div');
    questionDiv.className = 'question-block';
    questionDiv.id = 'question-' + questionCounter;

    questionDiv.innerHTML =
            '<h3>Question ' + questionCounter + '</h3>' +
            '<textarea name="questions[' + (questionCounter-1) + '].text" required></textarea>' +
            '<div id="answers-' + questionCounter + '"></div>' +
            '<button type="button" onclick="addAnswer(' + questionCounter + ')">Add Answer</button>' +
            '<button type="button" onclick="removeQuestion(' + questionCounter + ')">Remove Question</button>';

    container.appendChild(questionDiv);
    addAnswer(questionCounter);
    addAnswer(questionCounter);
  }

  function addAnswer(questionId) {
    answerCounters[questionId]++;
    var answerId = answerCounters[questionId];
    var container = document.getElementById('answers-' + questionId);
    var answerDiv = document.createElement('div');
    answerDiv.className = 'answer-block';

    answerDiv.innerHTML =
            '<input type="checkbox" name="questions[' + (questionId-1) + '].answers[' + (answerId-1) + '].isCorrect"> ' +
            '<input type="text" name="questions[' + (questionId-1) + '].answers[' + (answerId-1) + '].text" required>';

    container.appendChild(answerDiv);

    // Add correct-answer class when checkbox is checked
    var checkbox = answerDiv.querySelector('input[type="checkbox"]');
    checkbox.onchange = function() {
      if (this.checked) {
        answerDiv.classList.add('correct-answer');
      } else {
        answerDiv.classList.remove('correct-answer');
      }
    };
  }

  function removeQuestion(questionId) {
    if (questionCounter <= 1) {
      alert("Test must have at least one question");
      return;
    }

    var questionDiv = document.getElementById('question-' + questionId);
    questionDiv.parentNode.removeChild(questionDiv);
    delete answerCounters[questionId];
    questionCounter--;

    // Reindex remaining questions
    var questions = document.querySelectorAll('[id^="question-"]');
    for (var i = 0; i < questions.length; i++) {
      var newId = i + 1;
      questions[i].id = 'question-' + newId;
      questions[i].querySelector('h3').textContent = 'Question ' + newId;

      // Update all names in inputs
      var textarea = questions[i].querySelector('textarea');
      textarea.name = 'questions[' + i + '].text';

      var answerDivs = questions[i].querySelectorAll('.answer-block');
      for (var j = 0; j < answerDivs.length; j++) {
        var inputs = answerDivs[j].querySelectorAll('input');
        inputs[0].name = 'questions[' + i + '].answers[' + j + '].isCorrect';
        inputs[1].name = 'questions[' + i + '].answers[' + j + '].text';
      }
    }
  }

  document.getElementById('testForm').onsubmit = function() {
    // Validate at least one correct answer per question
    var questions = document.querySelectorAll('.question-block');
    for (var i = 0; i < questions.length; i++) {
      var checkboxes = questions[i].querySelectorAll('input[type="checkbox"]');
      var hasChecked = false;

      for (var j = 0; j < checkboxes.length; j++) {
        if (checkboxes[j].checked) {
          hasChecked = true;
          break;
        }
      }

      if (!hasChecked) {
        alert('Each question must have at least one correct answer');
        return false;
      }
    }
    return true;
  };
</script>
</body>
</html>