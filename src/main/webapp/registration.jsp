<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Регистрация</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f7f7f7;
      text-align: center;
      padding-top: 100px;
    }
    .form-container {
      display: inline-block;
      background: white;
      padding: 25px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h2 {
      margin-bottom: 20px;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 10px;
      margin: 8px 0;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
    .error {
      color: red;
      font-size: 14px;
      margin-bottom: 15px;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>Регистрация</h2>

  <%-- Отображение ошибки --%>
  <c:if test="${not empty registrationErrorMessage}">
    <div class="error">${registrationErrorMessage}</div>
  </c:if>

  <form method="post">
    <input type="text" name="login" placeholder="Логин" required>
    <input type="password" name="password" placeholder="Пароль" required>
    <input type="password" name="confirmPassword" placeholder="Подтвердите пароль" required>
    <input type="submit" value="Зарегистрироваться">
  </form>

  <p style="margin-top: 15px;">
    <a href="/login">Уже есть аккаунт?</a>
  </p>
</div>

</body>
</html>