<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>


<br/>
<br/>
<label for="defaultDate" class="col-sm-3 control-label">Default date</label>

<div class="col-sm-4">
    <input type="date" class="form-control" id="defaultDate">
</div>
<button type="button" class="btn btn-primary" onclick="createBoard()">Create board</button>
<div id="tripId" style="display: none">${tripId}</div>
<div id="lci" style="display: none">${lci}</div>
<div class="panel panel-default">
    <div class="panel-heading" align="center">Board</div>
    <div class="table-responsive">
        <table class="table table-hover" id="boardTable">
            <tr class="active">
                <th>ID</th>
                <th>Station title</th>
                <th>Departure date</th>
                <th>Departure time</th>
                <th>Arrive date</th>
                <th>Arrive time</th>
                <th>Stand time</th>
                <th width="10%">Delete</th>
            </tr>

        </table>
    </div>
</div>


<script type="text/javascript">
    function createBoard() {
        var tripId = $('#tripId').text();
        var lci = $('#lci').text();

        var date = $('#defaultDate').val();
        if (date === "") {
            date = formatDate(new Date());
        }
        alert(date);
        $.post('SBB/trip/board/create',
                {tripId: tripId, lci: lci, date: date},
                function (response) {

                }
        );
    }

    function formatDate(date) {
        var dd = date.getDate();
        if (dd < 10) {
            dd = '0' + dd;
        }
        var mm = date.getMonth() + 1;
        if (mm < 10) {
            mm = '0' + mm;
        }
        var yyyy = date.getFullYear();
        return yyyy + '-' + mm + '-' + dd;
    }

</script>

