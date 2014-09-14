<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>
<script type="text/javascript" src="https://github.com/Krinkle/jquery-json/raw/master/src/jquery.json.js"></script>

<br/>
<br/>
<c:if test="${!empty pathList and !empty trainList}">
    <label for="pathSelect" class="control-label col-sm-1">Path</label>

    <div class="col-sm-4"><select class="form-control" id="pathSelect" onchange="findTrips();">
        <option value="0" selected>Choose path</option>
        <c:forEach items="${pathList}" var="path">
            <option value="${path.id}">${path.title}</option>
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
                <th>LCI</th>
                <th>Departure date</th>
                <th>Arrive date</th>
                <th width="10%">Edit</th>
                <th width="10%">Delete</th>
            </tr>

        </table>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/js/trip.js"/> "></script>

