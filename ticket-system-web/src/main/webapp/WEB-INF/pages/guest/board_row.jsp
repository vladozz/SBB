<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty boardList}">
    <c:forEach items="${boardList}" var="boardLine">
        <tr>
            <td>${boardLine.trip.id}</td>
            <td>${boardLine.trip.path.title}</td>
            <td>${boardLine.arriveTime}</td>
            <td>${boardLine.departureTime}</td>
            <td></td>
        </tr>
    </c:forEach>
</c:if>