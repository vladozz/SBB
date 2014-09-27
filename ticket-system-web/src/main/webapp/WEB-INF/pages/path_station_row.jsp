<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tr id="${station.id}">
    <td class="id">${station.id}</td>
    <td class="title">${station.title}</td>
    <td class="remove">
        <button class="btn btn-warning"
                onclick="confirmRemoveStationFromPath(${station.id});">
            Remove
        </button>
    </td>
</tr>