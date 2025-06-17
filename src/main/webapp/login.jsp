<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
</head>
<body>
<form action="/login" method="post">
  <fieldset>
    <div>
      <label>
        Login: <input type="text" name="login">
      </label>
    </div>
    <div>
      <label>
        Password: <input type="password" name="password">
      </label>
    </div>

    <button type="submit">Login</button>
  </fieldset>
</form>

<c:out value="${authMessageError}"></c:out>
</body>
</html>