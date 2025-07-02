<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 30.06.2025
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Доступные тесты</h2>


<c:if test="${not empty testsByTopic}">
  <p style="color: green;">✅ Данные успешно получены. Количество тем: ${testsByTopic.size()}</p>

  <!-- Для каждой темы выводим заголовок и список тестов -->
  <c:forEach items="${testsByTopic}" var="entry">
    <h3>📁 Тема: <strong>${entry.key}</strong></h3>

    <!-- Список тестов -->
    <ul style="list-style: none; padding-left: 0;">
      <c:forEach items="${entry.value}" var="test">
        <li style="margin-bottom: 10px; background-color: #fff; padding: 10px; border-radius: 5px; box-shadow: 0 0 5px rgba(0,0,0,0.1);">
          🔹 Название: <strong>${test.title}</strong><br/>
          ID: <code>${test.id}</code><br/>
          Создан пользователем: <code>${test.created_by}</code><br/>
          <a href="/take-test?id=${test.id}">▶ Пройти тест</a>
        </li>
      </c:forEach>
    </ul>
  </c:forEach>
</c:if>