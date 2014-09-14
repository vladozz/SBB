<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path_station.js'/> "></script>
<br/><br/>

<div style="display: none" id="lci">${lastChange}</div>
<div style="display: none" id="pathId">${pathId}</div>
<c:if test="${!empty pathList}">
    <label for="pathSelect" class="control-label col-sm-1">Path list</label>

    <div class="col-sm-4"><select class="form-control" id="pathSelect" onchange="go();">
        <c:forEach items="${pathList}" var="path">
            <option id="ps${path.id}" value="${path.id}"
                    <c:if test="${path.id == pathId}">selected</c:if> >${path.title}</option>
        </c:forEach>
    </select>

    </div>
    <div class="col-sm-3">
        <button class="btn btn-primary" onclick="toggleAddForm();">Add station to path</button>
    </div>
    <br/>
    <br/>
    <br/>
</c:if>

<div class="jumbotron col-lg-12" id="addForm" style="display: none">
    <form class="form" role="form" method="post" action="<c:url value="/path/stations"/>">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputStation">Choose station</label>
            <select class="form-control" id="inputStation" name="stationId">
                <c:if test="${!empty stationList}">
                    <c:forEach items="${stationList}" var="station">
                        <option value="${station.id}">${station.title}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputIndex">Insert before</label>
            <select class="form-control" id="inputIndex" name="index">
                <c:if test="${!empty pathStationList}">
                    <c:forEach items="${pathStationList}" var="station">
                        <option value="${station.id}">${station.title}</option>
                    </c:forEach>
                </c:if>
                <option value="0" selected>To the end</option>
            </select>
        </div>
        <button type="button" class="btn btn-default" onclick="addStationToPath();">Add</button>
    </form>
</div>



    <div class="panel panel-default col-lg-12">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of stations</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfStations">
                <tr class="active">
                    <th>ID</th>
                    <th>Title</th>
                    <th>Remove</th>
                </tr>
                <c:if test="${!empty pathStationList}">
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
                </c:forEach>
                </c:if>
            </table>
        </div>
    </div>









