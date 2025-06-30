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

<h2>Редактировать тест</h2>

<c:if test="${not empty errorMessage}">
  <div class="error">${errorMessage}</div>
</c:if>

<form method="post">
  <input type="hidden" name="id" value="${test.id}">
  <input type="text" name="title" placeholder="Название теста" value="${test.title}" required>
  <input type="text" name="topic" placeholder="Тема теста" value="${test.topic}" required>

  <h4 style="margin-top: 20px;">Вопросы</h4>
  <div id="questions-container">
    <c:forEach items="${test.questions}" var="question" varStatus="qs">
      <div class="question-block">
        <h5>Вопрос ${qs.index + 1}</h5>
        <input type="hidden" name="questionText_${qs.index + 1}" value="Вопрос ${qs.index + 1}">
        <label>Текст вопроса:</label><br/>
        <input type="text" name="questionText_${qs.index + 1}"
               value="${question.text}" required style="width: 100%; margin-bottom: 10px;"><br/>

        <div id="answers-container-${qs.index + 1}">
          <c:forEach items="${question.answers}" var="answer" varStatus="as">
            <label>
              Ответ ${as.index + 1}:
              <input type="text" name="answerText_${qs.index + 1}_${as.index + 1}"
                     value="${answer.text}" required>
              Правильный?
              <input type="checkbox" name="isCorrect_${qs.index + 1}_${as.index + 1}"
                ${answer.isCorrect ? 'checked' : ''}>
            </label><br/>
          </c:forEach>
        </div>
        <button type="button" onclick="addAnswer(${qs.index + 1})">+ Добавить ответ</button><br/><br/>
      </div>
    </c:forEach>
  </div>

  <button type="button" onclick="addQuestion()">+ Добавить вопрос</button><br/><br/>
  <input type="submit" value="Сохранить изменения">
</form>

<script>
  let questionIndex = ${test.questions.size()};

  function addQuestion() {
    questionIndex++;
    const container = document.getElementById("questions-container");

    const questionDiv = document.createElement("div");
    questionDiv.className = "question-block";
    questionDiv.innerHTML = `
            <h5>Вопрос \${questionIndex}</h5>
            <input type="hidden" name="questionText_\${questionIndex}" value="Вопрос \${questionIndex}">
            <label>Текст вопроса:</label><br/>
            <input type="text" name="questionText_\${questionIndex}" placeholder="Введите текст вопроса" required style="width: 100%; margin-bottom: 10px;"><br/>

            <div id="answers-container-\${questionIndex}"></div>
            <button type="button" onclick="addAnswer(\${questionIndex})">+ Добавить ответ</button><br/><br/>
        `;
    container.appendChild(questionDiv);

    addAnswer(questionIndex);
    addAnswer(questionIndex);
  }

  function addAnswer(qIndex) {
    const answerContainer = document.getElementById("answers-container-" + qIndex);
    const aIndex = answerContainer.children.length + 1;

    const answerDiv = document.createElement("div");
    answerDiv.innerHTML = `
            <label>
                Ответ \${aIndex}:
                <input type="text" name="answerText_\${qIndex}_\${aIndex}" required>
                Правильный? <input type="checkbox" name="isCorrect_\${qIndex}_\${aIndex}">
            </label><br/>
        `;
    answerContainer.appendChild(answerDiv);
  }
</script>
