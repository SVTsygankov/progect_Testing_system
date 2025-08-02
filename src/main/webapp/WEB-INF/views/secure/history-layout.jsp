<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 01.08.2025
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<%@ page import="java.util.Locale" %>
<head>
  <title><fmt:message key="history.title"/></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/history.css">
</head>
<body>
<!-- Хедер (как в layout.jsp) -->
<header class="header">
  <div class="container">
    <h1><fmt:message key="header.title"/></h1>
    <nav>
      <a href="${pageContext.request.contextPath}/secure/tests" class="btn"><fmt:message key="menu.tests"/></a>
      <a href="${pageContext.request.contextPath}/secure/history" class="btn active"><fmt:message key="menu.history"/></a>
      <a href="${pageContext.request.contextPath}/logout" class="btn"><fmt:message key="menu.logout"/></a>
    </nav>
  </div>
</header>

<!-- Основной контент -->
<main class="container">
  <jsp:include page="${contentPage}"/>
</main>

<!-- Футер (как в layout.jsp) -->
<footer class="footer">
  <div class="container">
    <p><fmt:message key="footer.copyright"/></p>
  </div>
</footer>
</body>
</html>
