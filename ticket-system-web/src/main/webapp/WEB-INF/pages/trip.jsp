<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<br/>
<br/>
<c:if test="${!empty pathList and !empty trainList}">
    <label for="pathSelect" class="control-label col-sm-1">Path</label>

    <div class="col-sm-4"><select class="form-control" id="pathSelect" onchange="findTrips();">
        <option value="0" selected>Choose path</option>
        <c:forEach items="${pathList}" var="path">
            <option value="${path.id}">${path.title} / ${path.returnTitle}</option>
        </c:forEach>
    </select>

    </div>
</c:if>

<c:if test="${!empty trainList}">
    <label for="trainSelect" class="control-label col-sm-1">Train</label>

    <div class="col-sm-4"><select class="form-control" id="trainSelect" onchange="findTrips();">
        <option value="0" selected>Choose train</option>
        <c:forEach items="${trainList}" var="train">
            <option value="${train.id}">${train.number}</option>
        </c:forEach>
    </select>

    </div>
    <button type="button" class="btn btn-default" onclick="addTrip();">Add new trip</button>
    <br/>
    <br/>
    <br/>
</c:if>

<label for="trainSelect" class="control-label col-sm-1">Forward</label>
<input type="checkbox" id="forward"/>

<div class="panel panel-default">
    <div class="panel-heading" align="center">List of trips</div>
    <div class="table-responsive">
        <table class="table table-hover" id="listOfTrips">
            <tr class="active">
                <th>ID</th>
                <th>Path ID</th>
                <th>Path Title</th>
                <th>Train ID</th>
                <th>Train Number</th>
                <th>Version</th>
                <th>Departure date</th>
                <th>Arrive date</th>
                <th>Board</th>
                <th>Passengers</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>

        </table>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/js/trip.js"/> "></script>