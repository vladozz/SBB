<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tr id="${path.id}">
    <td class="id">${path.id}</td>
    <td class="title">${path.title}</td>
    <td class="returnTitle">${path.returnTitle}</td>
    <td class="beginStation">${path.beginStation}</td>
    <td class="endStation">${path.endStation}</td>
    <td class="lastChange">${path.lastChange}</td>
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
        <a onclick="confirmDelete(${path.id}, '${path.title}/${path.returnTitle}');">
            <button type="button" class="btn btn-danger">Delete</button>
        </a>
    </td>
</tr>