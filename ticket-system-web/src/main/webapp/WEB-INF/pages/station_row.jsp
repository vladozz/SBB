<tr id="${station.id}">
    <td class="id">${station.id}</td>
    <td class="title">${station.title}</td>
    <td class="version hidden">${station.version}</td>
    <td class="timeZone">${station.timeZone.ID}</td>
    <c:if test="${empty removed}">
        <td>
            <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addStationModal"
                    onclick="editModalStation(${station.id});">Edit
            </button>
        </td>
        <td>
            <button type="button" class="btn btn-danger" onclick="confirmDelete(${station.id});">Delete</button>
        </td>
    </c:if>
</tr>