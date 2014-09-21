<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
error <c:if test="${!empty errorList}">
    <c:forEach items="${errorList}" var="error">
        <p>${error}</p>
    </c:forEach>
</c:if>