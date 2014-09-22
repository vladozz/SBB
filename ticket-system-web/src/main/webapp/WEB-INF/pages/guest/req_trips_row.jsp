<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty tripList}">
    <c:forEach items="${tripList}" var="trip">
        <tr class="remove">
            <td>${trip.tripId}</td>
            <td>${trip.pathTitle}</td>
            <td>${trip.departure.date}</td>
            <td>${trip.departure.time}</td>
            <td>${trip.arrive.date}</td>
            <td>${trip.arrive.time}</td>
            <td>${trip.routeTime}</td>
            <td>${trip.freePlaces}</td>
        </tr>
    </c:forEach>
</c:if>