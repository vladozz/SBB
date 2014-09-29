<tr id="${train.id}">
    <td class="id">${train.id}</td>
    <td class="version hidden">${train.version}</td>
    <td class="number">${train.number}</td>
    <td class="placesQty">${train.placesQty}</td>
    <c:if test="${empty removed}">
    <td>
        <button type="button" class="btn btn-warning" data-toggle="modal"
                data-target="#addTrainModal"
                onclick="editModalTrain(${train.id});">Edit
        </button>
    </td>
    <td>
            <button type="button" class="btn btn-danger" onclick="confirmDelete(${train.id});">Remove</button>

    </td>
    </c:if>
</tr>