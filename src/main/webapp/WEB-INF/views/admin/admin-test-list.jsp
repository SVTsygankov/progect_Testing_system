<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 02.07.2025
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Управление тестами</h2>

<div style="margin-bottom: 15px;">
  <a href="/admin/test/create">+ Создать тест</a>
</div>

<c:if test="${empty testsByTopic}">
  <p style="color: red;">Нет доступных тестов.</p>
</c:if>

<c:if test="${not empty testsByTopic}">
  <c:forEach items="${testsByTopic}" var="entry">
    <h3>${entry.key}</h3>
    <ul style="list-style: none; padding-left: 0;">
      <c:forEach items="${entry.value}" var="test">
        <li style="margin-bottom: 10px;">
          <a href="/take-test?id=${test.id}">${test.title}</a>
          |
          <a href="/admin/test/edit?id=${test.id}">Редактировать</a>
          |
          <a href="/admin/test/delete?id=${test.id}">Удалить</a>
          |
          <a href="/admin/test/stats?id=${test.id}">Статистика</a>
        </li>
      </c:forEach>
    </ul>
  </c:forEach>
</c:if>
