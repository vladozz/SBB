<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty messages}">
    <c:forEach items="${messages}" var="message">
        <p>${message}</p>
    </c:forEach>
</c:if>