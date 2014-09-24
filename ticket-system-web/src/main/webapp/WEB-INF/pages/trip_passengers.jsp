<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="panel panel-default">
    <div class="panel-heading" align="center">Passengers of trip ${trip.id}. Route: ${trip.path.title} </div>
    <div class="table-responsive">
        <table class="table table-hover" id="boardTable">
            <tr class="active">
                <th>Ticket ID</th>
                <th>Passenger ID</th>
                <th>Name</th>
                <th>Date of birth</th>
                <th>Departure station</th>
                <th>Arrive station</th>
            </tr>
            <c:if test="${!empty ticketList}">
                <c:forEach items="${ticketList}" var="ticket">
                    <tr>
                        <td>${ticket.id}</td>
                        <td>${ticket.passenger.id}</td>
                        <td>${ticket.passenger.firstName} ${ticket.passenger.lastName}</td>
                        <td><fmt:formatDate value="${ticket.passenger.birthDate.time}" type="date" pattern="yyyy-MM-dd"/></td>
                        <td>${ticket.departure.station.title}</td>
                        <td>${ticket.arrive.station.title}</td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
    </div>
</div>