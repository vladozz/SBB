<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>

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
                    <th>Train ID</th>
                    <th>Train Number</th>
                    <th>Path ID</th>
                    <th>Path Number</th>
                    <th>Departure date</th>
                    <th>Arrive date</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>

            </table>
        </div>
    </div>



<script type="text/javascript">
    var tr = $("<tr/>").attr('class', 'remove');
    var td = $("<td/>");
    var btnDelete = $("<button/>").attr('class', 'btn btn-danger').attr('type', 'button').text('Delete');
    var btnEdit = $("<button/>").attr('class', 'btn btn-warning').attr('type', 'button').text('Edit');

    function findTrips() {
        var pathId = $('#pathSelect').find("option:selected").val();
        var trainId = $('#trainSelect').find("option:selected").val();
        $.post('/SBB/trip/select',
                { pathId: pathId, trainId: trainId},
                function(response) {
                    var trips = $.parseJSON(response);
                    $('.remove').remove();
                    $.each(trips, function(index, trip) {
                        appendRow(trip);
                    });
                }
        );
    }

    function addTrip() {
        var pathId = $('#pathSelect').find("option:selected").val();
        var trainId = $('#trainSelect').find("option:selected").val();
        $.post('/SBB/trip/add',
                { pathId: pathId, trainId: trainId},
                function(response) {
                    var trip = $.parseJSON(response);
                    appendRow(trip);
                }

        );
    }

    function appendRow(trip) {
        var row = tr.clone();
        $.each(trip, function(key, value) {
            row.append(td.clone().text(value));
        });
        row.append(td.clone().html(btnEdit.clone().click(function() {
            editTrip(trip.id);
        })));
        row.append(td.clone().html(btnDelete.clone().click(function() {
            removeTrip(trip.id);
        })));
        $('#listOfTrips').append(row);
    }

    function removeTrip(tripId) {
        alert("trip with id " + tripId + " removed");
    }

    function editTrip(tripId) {
        alert("trip with id " + tripId + " edited");
    }
</script>

