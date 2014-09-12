<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<br/>
<br/>

<c:if test="${!empty trainList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of trains</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfTrains">
                <tr class="active">
                    <th>ID</th>
                    <th>Number</th>
                    <th>Quantity of places</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${trainList}" var="train">
                    <tr id="${train.id}">
                        <td class = "id">${train.id}</td>
                        <td class = "number">${train.number}</td>
                        <td class= "placesQty">${train.placesQty}</td>

                        <td>
                            <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addTrainModal"
                                    onclick="editModalTrain(${train.id}, '${train.number}', '${train.placesQty}');">Edit</button>
                        </td>
                        <td>
                            <a <%--href="delete/${train.id}"--%>
                                    onclick="confirmDelete(${train.id}, '${train.number}');">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





