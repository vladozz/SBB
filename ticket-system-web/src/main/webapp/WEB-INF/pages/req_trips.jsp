<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="col-lg-3">

    <div class="form-group">
        <label class="control-label" for="dateBegin">Choose date</label>
        <input type="date" class="form-control" id="dateBegin">
    </div>
    <div class="form-group">
        <label class="control-label" for="timeBegin">Choose time</label>
        <input type="time" class="form-control" id="timeBegin">
    </div>
    <div class="form-group">
        <label class="control-label" for="stationBegin">Choose station</label>
        <select class="form-control" id="stationBegin" name="index">
            <%@include file="guest/station_option.jsp" %>
        </select>
    </div>
    <div class="form-group">
        <label class="control-label" for="dateEnd">Choose date</label>
        <input type="date" class="form-control" id="dateEnd">
    </div>
    <div class="form-group">
        <label class="control-label" for="timeEnd">Choose time</label>
        <input type="time" class="form-control" id="timeEnd">
    </div>
    <div class="form-group">
        <label class="control-label" for="stationEnd">Choose station</label>
        <select class="form-control" id="stationEnd" name="index">
            <%@include file="guest/station_option.jsp" %>
        </select>
    </div>
    <div class="form-group">
        <button id="getBoardBtn" type="button" class="btn btn-primary">Get board</button>
    </div>

</div>
<div class="col-lg-9">
    <div class="panel panel-default">
        <div class="panel-heading" align="center">Board</div>
        <div class="table-responsive">
            <table class="table table-hover" id="tripTable">
                <tr class="active">
                    <th>Trip ID</th>
                    <th>Path</th>
                    <th>Departure date</th>
                    <th>Departure time</th>
                    <th>Arrive date</th>
                    <th>Arrive time</th>
                    <th>Route time</th>
                    <th>Free places</th>
                    <th>Buy ticket</th>
                </tr>
            </table>
        </div>
    </div>
</div>

<div class="hidden">
    <div id="passengerForm">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label for="inputFN" class="col-sm-3 control-label">First name</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputFN" placeholder="First name">
                </div>
            </div>
            <div class="form-group">
                <label for="inputLN" class="col-sm-3 control-label">Last name</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputLN" placeholder="Last name">
                </div>
            </div>
            <div class="form-group">
                <label for="inputDate" class="col-sm-3 control-label">Date</label>

                <div class="col-sm-6">
                    <input type="date" class="form-control" id="inputDate">
                </div>
            </div>
<%--            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-default">Close</button>
                    <button type="submit" class="btn btn-primary">Add passenger</button>
                </div>
            </div>--%>
        </form>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/resources/js/req_trips.js"/> "></script>
