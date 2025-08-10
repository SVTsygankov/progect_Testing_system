<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 30.06.2025
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Доступные тесты</title>
<%--  <style>--%>
<%--    body {--%>
<%--      font-family: Arial, sans-serif;--%>
<%--      margin: 20px;--%>
<%--    }--%>
<%--    table {--%>
<%--      border-collapse: collapse;--%>
<%--      width: 100%;--%>
<%--      margin-bottom: 20px;--%>
<%--    }--%>
<%--    th, td {--%>
<%--      border: 1px solid #ddd;--%>
<%--      padding: 8px;--%>
<%--      text-align: left;--%>
<%--    }--%>
<%--    th {--%>
<%--      background-color: #f2f2f2;--%>
<%--    }--%>
<%--    a {--%>
<%--      text-decoration: none;--%>
<%--      color: #0066cc;--%>
<%--    }--%>
<%--    a:hover {--%>
<%--      text-decoration: underline;--%>
<%--    }--%>
<%--    .topic-header {--%>
<%--      margin-top: 20px;--%>
<%--      font-weight: bold;--%>
<%--    }--%>
<%--  </style>--%>
</head>
<body>
<h1>Доступные тесты</h1>

<c:if test="${empty testsByTopic}">
  <p>Нет доступных тестов.</p>
</c:if>

<c:forEach items="${testsByTopic}" var="topicGroup">
  <div class="topic-header">Тема: ${topicGroup.key}</div>
  <table>
    <tr>
      <th>Название теста</th>
      <th>Действия</th>
    </tr>
    <c:forEach items="${topicGroup.value}" var="test">
      <tr>
        <td>${test.title}</td>
        <td>
          <a href="${pageContext.request.contextPath}/start-test?id=${test.id}">Пройти тест</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:forEach>

<div style="margin-top: 20px;">
  <a href="${pageContext.request.contextPath}/secure/history" style="padding: 5px 10px; background: #f2f2f2; border: 1px solid #ddd;">
    Просмотр истории тестирований
  </a>
</div>
</body>
</html>