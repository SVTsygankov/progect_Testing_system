<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 19.06.2025
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/css/styles.css">
  <title><c:out value="${pageTitle}" default="Приложение"/></title>

</head>
<body>

<header>
  <h1>Приложение для создания и прохождения тестов</h1>
  <div class="mode-switcher">
    <c:choose>
      <%-- Для админа в админ-панели --%>
      <c:when test="${requestScope['jakarta.servlet.forward.request_uri'].startsWith('/admin/')}">
        <a href="/secure/switch-to-user" class="btn btn-user-mode">
          <i class="fas fa-user"></i> User Mode
        </a>
      </c:when>

      <%-- Для админа в пользовательском разделе --%>
      <c:when test="${sessionScope.user.role == 'ADMIN' &&
                        !requestScope['jakarta.servlet.forward.request_uri'].startsWith('/admin/')}">
        <a href="/secure/switch-to-admin" class="btn btn-admin">
          <i class="fas fa-cog"></i> Admin Panel
        </a>
      </c:when>
    </c:choose>

    <%-- Кнопка выхода для всех --%>
    <a href="/secure/logout" class="btn btn-logout">
      <i class="fas fa-sign-out-alt"></i> Logout
    </a>
  </div>
</header>

<div class="container">
  <div class="form-container">
    <jsp:include page="${contentPage}"/>
  </div>
</div>

<footer>
  &copy; 2025 — Веб-приложение для создания и прохождения тестов
</footer>

</body>
</html>