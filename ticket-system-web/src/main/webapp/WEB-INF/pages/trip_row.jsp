<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${!empty tripList}">
    <c:forEach items="${tripList}" var="trip">
        <tr class="remove" id="${trip.id}">
            <td class="id">${trip.id}</td>
            <td class="pathId">${trip.path.id}</td>
            <td class="pathTitle">${trip.path.title}</td>
            <td class="forward hidden">${trip.forward}</td>
            <td class="trainId">${trip.train.id}</td>
            <td class="trainNumber">${trip.train.number}</td>
            <td class="version">${trip.version}</td>
            <c:if test="${!empty trip.boardList}">
                <td class="departureStation">${trip.boardList[0].station.title}</td>
                <td class="arriveStation">${trip.boardList[fn:length(trip.boardList)].station.title}</td>
            </c:if>
            <c:if test="${empty trip.boardList}">
                <td class="departureStation"></td>
                <td class="arriveStation"></td>
            </c:if>
            <td>
                <button class="btn btn-primary" type="button">Board</button>
            </td>
            <td>
                <button class="btn btn-primary" type="button">Passengers</button>
            </td>
            <td>
                <button class="btn btn-warning" type="button">Edit</button>
            </td>
            <td>
                <button class="btn btn-danger" type="button">Delete</button>
            </td>
        </tr>
    </c:forEach>
</c:if>