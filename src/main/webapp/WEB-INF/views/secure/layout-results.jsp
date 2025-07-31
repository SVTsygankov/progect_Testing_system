<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 31.07.2025
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>${pageTitle != null ? pageTitle : 'Результаты тестирования'}</title>
  <style>
    /* Стили ТОЛЬКО для страницы результатов */
    body {
      font-family: Arial, sans-serif;
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }
    header {
      background: #2c3e50;
      color: white;
      padding: 15px;
      margin-bottom: 20px;
      text-align: center;
    }
    footer {
      background: #2c3e50;
      color: white;
      text-align: center;
      padding: 10px;
      margin-top: 30px;
    }
  </style>
</head>
<body>
<header>
  <h1>Система тестирования</h1>
</header>

<main>
  <jsp:include page="${contentPage}"/>
</main>

<footer>
  &copy; 2025 - Система тестирования
</footer>
</body>
</html>
