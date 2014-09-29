<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty tripList}">
    <c:forEach items="${tripList}" var="trip">
        <tr class="remove">
            <td>${trip.tripId}</td>
            <td>${trip.pathTitle}</td>
            <td>${trip.departure.date} ${trip.departure.time}</td>
            <td>${trip.arrive.date} ${trip.arrive.time}</td>
            <td>${trip.routeTime}</td>
            <td>${trip.freePlaces}</td>
            <td>
                <a href="#" onclick="buyTicketDialog(${trip.departure.boardId}, ${trip.arrive.boardId}, ${trip.freePlaces})">Buy ticket</a>
            </td>
        </tr>
    </c:forEach>
</c:if>

<c:if test="${empty tripList}">
        <tr class="remove">
            <td colspan="3"></td>
           <td colspan="4">No trips found</td>
        </tr>
</c:if>