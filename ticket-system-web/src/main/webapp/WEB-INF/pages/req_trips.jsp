<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>--%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>


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
            <%@include file="guest/station_option.jsp"%>
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
            <%@include file="guest/station_option.jsp"%>
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


<script type="text/javascript" src="<c:url value="/resources/js/req_trips.js"/> ">


</script>

