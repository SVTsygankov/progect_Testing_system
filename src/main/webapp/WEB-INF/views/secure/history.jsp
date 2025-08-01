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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Your Test History</h1>

    <c:if test="${empty resultsWithCounts}">
        <p>No test results found.</p>
    </c:if>

    <c:forEach items="${resultsWithCounts}" var="resultData">
        <div class="test-result">
            <h3>Test #${resultData.result.testId} -
                <fmt:formatDate value="${resultData.dateAsDate}" pattern="dd.MM.yyyy HH:mm"/>
            </h3>

            <div class="result-details">
                <p>Correct answers:
                        ${resultData.correctAnswersCount} / ${fn:length(resultData.result.answers)}
                </p>
                <a href="${pageContext.request.contextPath}/secure/result-details?id=${resultData.result.id}"
                   class="btn btn-details">
                    View Details
                </a>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
