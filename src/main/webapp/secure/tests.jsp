<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tests</title>
</head>
<body>
<form action="/secure/tests" method="post">
    <fieldset>
        <div>
            <label>
                Name: <input type="text" name="name-product" required>
            </label>
        </div>
        <div>
            <label>
                Image-URL: <input type="text" name="image-url" required>
            </label>
        </div>

        <button type="submit">Add product</button>
    </fieldset>
</form>
<div>
    <c:forEach var="login" items="${userLogins}">
        <p>${login}</p>
    </c:forEach>
</div>

<c:forEach var="product" items="${products}">
    <p>${product.name}</p>
    <img src="${product.imageUrl}">
    <form action="/secure/products" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="hidden" name="product-id" value="${product.id}">
        <button type="submit" name="remove-button" style="color: red">Remove</button>
    </form>
</c:forEach>
</body>
</html>