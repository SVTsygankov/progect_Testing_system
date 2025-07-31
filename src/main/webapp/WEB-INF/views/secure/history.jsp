<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 28.07.2025
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Your Test History</h1>

    <c:if test="${empty results}">
        <p>No test results found.</p>
    </c:if>

    <c:forEach items="${results}" var="result">
        <div class="test-result">
            <h3>Test #${result.testId} -
                <fmt:formatDate value="${result.date}" pattern="dd.MM.yyyy HH:mm"/>
            </h3>

            <div class="result-details">
                <p>Correct answers:
                    <c:set var="correctCount" value="0"/>
                    <c:forEach items="${result.answers}" var="answer">
                        <c:if test="${answer.correct}">
                            <c:set var="correctCount" value="${correctCount + 1}"/>
                        </c:if>
                    </c:forEach>
                        ${correctCount} / ${fn:length(result.answers)}
                </p>
                <a href="${pageContext.request.contextPath}/secure/result-details?id=${result.id}"
                   class="btn btn-details">
                    View Details
                </a>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
