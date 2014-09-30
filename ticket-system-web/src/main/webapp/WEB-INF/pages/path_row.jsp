<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tr id="${path.id}">
    <td class="id">${path.id}</td>
    <td class="title">${path.title}</td>
    <td class="returnTitle">${path.returnTitle}</td>
    <td class="version hidden">${path.version}</td>
    <td class="beginStation">
        <c:if test="${!empty path.stations}">${path.stations[0].title}</c:if>
    </td>
    <td class="endStation">
        <c:if test="${!empty path.stations}">${path.stations[fn:length(path.stations) - 1].title}</c:if>
    </td>
    <td>
        <a href="<c:url value="/path/stations/"/>${path.id}">
            <button type="button" class="btn btn-primary">Stations</button>
        </a>
    </td>
    <td>
        <button type="button" class="btn btn-warning" data-toggle="modal"
                data-target="#addPathModal"
                onclick="editModalPath(${path.id});">Edit
        </button>
    </td>
    <td>
        <a onclick="confirmDelete(${path.id});">
            <button type="button" class="btn btn-danger">Delete</button>
        </a>
    </td>
</tr>