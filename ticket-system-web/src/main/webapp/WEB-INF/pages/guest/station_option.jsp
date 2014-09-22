<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty stationList}">
    <c:forEach items="${stationList}" var="station">
        <option value="${station.id}">${station.title}</option>
    </c:forEach>
</c:if>