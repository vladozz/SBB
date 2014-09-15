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
                <th>Line ID</th>
                <th>Trip ID</th>
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
    var $tr = $("<tr/>").attr('class', 'remove');
    var $td = $("<td/>");

    $(document).ready(
            function() {
                getBoard();
            }
    );

    function createBoard() {
        var tripId = $('#tripId').text();
        var lci = $('#lci').text();

        var date = $('#defaultDate').val();
        if (date === "") {
            date = formatDate(new Date());
        }
        $.post('/SBB/trip/board/create',
                {tripId: tripId, lci: lci, date: date},
                function (response) {
                    if (isError(response)) {
                        return;
                    }
                    var board = $.parseJSON(response);
                    fillBoardTable(board);
                }
        );
    }

    function fillBoardTable(board) {
        $('.remove').remove();
        $.each(board, function (index, boardLine) {
            $('#boardTable').append(createRow(boardLine));
        });
    }
    function getBoard() {
        var tripId = $('#tripId').text();

        $.post('/SBB/trip/board/select',
                {tripId: tripId},
                function (response) {
                    if (isError(response)) {
                        return;
                    }
                    var board = $.parseJSON(response);
                    fillBoardTable(board);
                }
        );
    }

    function createRow(boardLine) {
        var boardId = boardLine.boardId;
        var row = $tr.clone().attr("id", boardId);
        $.each(boardLine, function (key, value) {
            var mutable = ['departureDate', 'departureTime', 'arriveDate', 'arriveTime'];
            var $cell = $td.clone().attr('class', key).text(value);
            if ($.inArray(key, mutable) != -1) {
                $($cell).click(function() {
                    $('#' + boardId +' .' + key).html("<a");
                });
            }

            row.append($cell);
        });
/*        row.append($td.clone().html($btnBoard.clone().click(function () {
            toBoard(boardLine.id);
        })));
        row.append($td.clone().html($btnEdit.clone().click(function () {
            editTrip(boardLine.id);
        })));
        row.append($td.clone().html($btnDelete.clone().click(function () {
            removeTrip(boardLine.id);
        })));*/
        return row;
    }

    function isError(response) {
        function alertError(errorMessage) {
            BootstrapDialog.show({
                title: 'Error',
                message: errorMessage,
                type: BootstrapDialog.TYPE_DANGER
            });
        }

        if (response.substring(0, 5) === "error") {
            alertError(response.substring(6));
        } else if (response.substring(0, 7) === "outdate") {
            alertError('Your page is irrelevant! Try again.\n' + response.substring(8));
        } else {
            return false;
        }
        return true;
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

