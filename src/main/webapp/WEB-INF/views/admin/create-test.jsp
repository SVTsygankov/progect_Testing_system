<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 28.06.2025
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%-- /WEB-INF/views/admin/create-test.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Создать тест</h2>

<c:if test="${not empty errorMessage}">
  <div class="error">${errorMessage}</div>
</c:if>

<form method="post">
  <input type="text" name="title" placeholder="Название теста" required>
  <input type="text" name="topic" placeholder="Тема теста" required>

  <h4 style="margin-top: 20px;">Вопросы</h4>
  <div id="questions-container">
    <!-- Вопрос будет добавлен динамически -->
  </div>
  <button type="button" onclick="addQuestion()">+ Добавить вопрос</button>

  <br/><br/>
  <input type="submit" value="Сохранить тест">
</form>

<script>
  let questionIndex = 0;

  function addQuestion() {
    questionIndex++;
    console.log("Индекс вопроса " + questionIndex);

    const container = document.getElementById("questions-container");

    const questionDiv = document.createElement("div");
    questionDiv.className = "question-block";

    // Конкатенация вместо template literal
    questionDiv.innerHTML =
            "<h5>Вопрос " + questionIndex + "</h5>" +
            "<input type='hidden' name='questionId_" + questionIndex + "' value='" + questionIndex + "'>" +

            "<label>Текст вопроса:</label><br/>" +
            "<input type='text' name='questionText_" + questionIndex +
            "' placeholder='Введите текст вопроса' required style='width: 100%; margin-bottom: 10px;'><br/>" +

            "<div id='answers-container-" + questionIndex + "'></div>" +
            "<button type='button' onclick='addAnswer(" + questionIndex + ")'>+ Добавить ответ</button><br/><br/>";


    container.appendChild(questionDiv);

    console.log("Created answers container for question " + questionIndex);

    // Добавляем два начальных ответа через микрорасписание
    setTimeout(function () {
      addAnswer(questionIndex);
      addAnswer(questionIndex);
    }, 0);
  }

  function addAnswer(qIndex) {
    console.log("Adding answer for question " + qIndex); // Отладочное сообщение

    const answerContainer = document.getElementById("answers-container-" + qIndex);
    if (!answerContainer) {
      console.error("Error: Container 'answers-container-" + qIndex + "' not found");
      return;
    }

    const aIndex = answerContainer.children.length + 1;

    const answerDiv = document.createElement("div");
    answerDiv.innerHTML =
            "<label>" +
            "Ответ " + aIndex + ":" +
            "<input type='text' name='answerText_" + qIndex + "_" + aIndex + "' required>" +
            "Правильный? <input type='checkbox' name='isCorrect_" + qIndex + "_" + aIndex + "'>" +
            "</label><br/>";

    answerContainer.appendChild(answerDiv);
  }

  // При загрузке формы сразу добавляем один вопрос
  window.onload = function () {
    addQuestion();
  };
</script>