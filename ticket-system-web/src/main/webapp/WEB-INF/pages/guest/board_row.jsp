<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty boardList}">
    <c:forEach items="${boardList}" var="boardLine">
        <tr class="remove">
            <td>${boardLine.tripId}</td>
            <td>${boardLine.pathTitle}</td>
            <td>${boardLine.trainNumber}</td>
            <td>${boardLine.arriveTime}</td>
            <td>${boardLine.departureTime}</td>
            <td>${boardLine.standTime}</td>
        </tr>
    </c:forEach>
</c:if>