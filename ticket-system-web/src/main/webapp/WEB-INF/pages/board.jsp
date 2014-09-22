<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>--%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>


<div class="col-lg-3">

    <div class="form-group">
        <label class="control-label" for="date">Choose date</label>
        <input type="date" class="form-control" id="date">
    </div>
    <div class="form-group">
        <label class="control-label" for="stationList">Choose station</label>
        <select class="form-control" id="stationList" name="index">
            <c:if test="${!empty stationList}">
                <c:forEach items="${stationList}" var="station">
                    <option value="${station.id}">${station.title}</option>
                </c:forEach>
            </c:if>
        </select>
    </div>
    <div class="form-group">
        <button id="getBoardBtn" type="button" class="btn btn-primary">Get board</button>
    </div>

</div>
<div class="col-lg-9">
    <div class="panel panel-default" id="board" style="display: none">
        <div class="panel-heading" align="center">Board</div>
        <div class="table-responsive">
            <table class="table table-hover" id="boardTable">
                <tr class="active">
                    <th>Trip ID</th>
                    <th>Route</th>
                    <th>Train</th>
                    <th>Arrive time</th>
                    <th>Departure time</th>
                    <th>Stand time</th>
                </tr>
            </table>
        </div>
    </div>
</div>


<script type="text/javascript" src="<c:url value="/resources/js/board.js"/> ">


</script>

