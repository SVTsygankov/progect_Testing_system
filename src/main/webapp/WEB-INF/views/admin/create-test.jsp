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

<form id="test-form">
  <input type="text" name="title" placeholder="Название теста" required>
  <input type="text" name="topic" placeholder="Тема теста" required>

  <h4 style="margin-top: 20px;">Вопросы</h4>
  <div id="questions-container"></div>
  <button type="button" onclick="addQuestion()">+ Добавить вопрос</button>

  <br/><br/>
  <input type="submit" value="Сохранить тест">
</form>

<script>
  let testState = {
    title: "",
    topic: "",
    questions: []
  };

  function addQuestion() {
    const newQuestion = {
      text: "",
      answers: [
        { text: "", isCorrect: false },
        { text: "", isCorrect: false }
      ]
    };
    testState.questions.push(newQuestion);
    renderQuestions();
  }

  function addAnswer(qIndex) {
    const question = testState.questions[qIndex - 1];
    if (!question) return;

    question.answers.push({ text: "", isCorrect: false });
    renderQuestions();
  }

  function renderQuestions() {
    const container = document.getElementById("questions-container");
    container.innerHTML = "";

    testState.questions.forEach((question, index) => {
      const qIndex = index + 1;
      const questionDiv = document.createElement("div");
      questionDiv.className = "question-block";

      questionDiv.innerHTML = `
        <h5>Вопрос ${qIndex}</h5>
        <label>Текст вопроса:</label><br/>
        <input type="text" name="questionText_${qIndex}" value="${question.text}" required style="width:100%; margin-bottom: 10px;"><br/>

        <div id="answers-container-${qIndex}"></div>
        <button type="button" onclick="addAnswer(${qIndex})">+ Добавить ответ</button><br/><br/>
      `;

      container.appendChild(questionDiv);

      const answerContainer = document.getElementById(`answers-container-${qIndex}`);
      answerContainer.innerHTML = "";
      question.answers.forEach((answer, aIndex) => {
        const answerDiv = document.createElement("div");
        answerDiv.innerHTML = `
          <label>
            Ответ ${aIndex + 1}:
            <input type="text" name="answerText_${qIndex}_${aIndex + 1}" value="${answer.text}" required style="width: 70%">
            Правильный? <input type="checkbox" name="isCorrect_${qIndex}_${aIndex + 1}" ${answer.isCorrect ? "checked" : ""}>
          </label><br/>
        `;
        answerContainer.appendChild(answerDiv);
      });
    });
  }

  document.getElementById("test-form").addEventListener("submit", function (e) {
    e.preventDefault();

    testState.title = document.getElementsByName("title")[0].value;
    testState.topic = document.getElementsByName("topic")[0].value;

    const formData = new FormData();
    formData.append("testData", JSON.stringify(testState));

    fetch("/admin/test/create", {
      method: "POST",
      body: formData
    }).then(() => {
      window.location.href = "/tests";
    });
  });

  window.onload = function () {
    addQuestion();
  };
</script>