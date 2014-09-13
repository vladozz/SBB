<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path_station.js'/> "></script>
<br/><br/>

<div style="display: none" id="lci">${lci}</div>
<div style="display: none" id="pathId">${pathId}</div>
<c:if test="${!empty pathList}">
    <div class="container">
        <div class="form-group">
            <label for="pathSelect" class="control-label col-sm-1">Path list</label>

            <div class="col-sm-6"><select class="form-control" id="pathSelect" onchange="go();">
                <c:forEach items="${pathList}" var="path">
                    <option id="ps${path.id}" value="${path.id}"
                            <c:if test="${path.id == pathId}">selected</c:if> >${path.title}</option>
                </c:forEach>
            </select>
            </div>
        </div>
    </div>
    <br/>
</c:if>


<c:if test="${!empty pathStationList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of stations</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfStations">
                <tr class="active">
                    <th>ID</th>
                    <th>Title</th>
                    <th>Remove</th>
                </tr>
                <c:set var="count" value="1" scope="page"/>
                <c:forEach items="${pathStationList}" var="station">
                    <tr id="${station.id}">
                        <td class="id">${station.id}</td>
                        <td class="title">${station.title}</td>
                        <td class="remove">
                            <button class="btn btn-warning"
                                    onclick="confirmRemoveStationFromPath(${pathId}, '${station.title}' ,${station.id});">
                                Remove
                            </button>
                        </td>
                    </tr>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>

<div class="jumbotron">
    <form class="form" role="form" method="post" action="<c:url value="/path/stations"/>">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputIndex">Insert before</label>
            <select class="form-control" id="inputIndex" name="index">
                <c:if test="${!empty pathStationList}">
                    <c:set var="count" value="1" scope="page"/>
                    <c:forEach items="${pathStationList}" var="station">
                        <option value="${station.id}">${station.title}</option>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:forEach>
                </c:if>
                <option value="0" selected>To the end</option>
            </select>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputStation">Station</label>
            <select class="form-control" id="inputStation" name="stationId">
                <c:if test="${!empty stationList}">
                    <c:forEach items="${stationList}" var="station">
                        <option value="${station.id}">${station.title}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <button type="button" class="btn btn-default" onclick="addStationToPath();">Add</button>
    </form>
</div>






