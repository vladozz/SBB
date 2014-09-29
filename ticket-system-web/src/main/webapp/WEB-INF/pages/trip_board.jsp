<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<br/>
<br/>
<label for="defaultDate" class="col-sm-3 control-label">Default date</label>

<div class="col-sm-4">
    <input type="date" class="form-control" id="defaultDate">
</div>
<button type="button" class="btn btn-primary" onclick="createBoard()">Create board</button>
<button type="button" class="btn btn-primary" onclick="editBoard()">Update board</button>
<div id="tripId" style="display: none">${tripId}</div>
<div id="lci" style="display: none">${lci}</div>
<div class="panel panel-default">
    <div class="panel-heading" align="center">Board</div>
    <div class="table-responsive">
        <table class="table table-hover" id="boardTable">
            <tr class="active">
                <th>Line ID</th>
                <th>Trip ID</th>
                <th>Station title</th>
                <th>GMT</th>
                <th>Arrive date</th>
                <th>Arrive time</th>
                <th>Departure date</th>
                <th>Departure time</th>
                <th>Stand time</th>
                <%--<th width="10%">Delete</th>--%>
            </tr>

        </table>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/js/trip_board.js"/> "></script>