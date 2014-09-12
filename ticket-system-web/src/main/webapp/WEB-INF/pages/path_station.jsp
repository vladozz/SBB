<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/path.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>

<br/><br/>

<div style="display: none" id="lci">${lci}</div>
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
                                    onclick="confirmRemoveStationFromPath(${pathId}, '${station.title}' ,${count}, ${lci});">
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
<h1>LCI ${lci}</h1>

<div class="jumbotron">
    <form class="form-inline" role="form" method="post" action="<c:url value="/path/stations"/>">
        <input type="hidden" name="pathId" value="${pathId}"/>
        <input type="hidden" name="lci" value="${lci}"/>

        <div class="form-group">
            <label class="sr-only" for="inputIndex">Insert before</label>
            <select class="form-control" id="inputIndex" name="index">
                <c:if test="${!empty pathStationList}">
                    <c:set var="count" value="1" scope="page"/>
                    <c:forEach items="${pathStationList}" var="station">
                        <option id="ii${count}" value="${count}">${station.title}</option>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:forEach>
                </c:if>
                <option value="0" selected>To the end</option>
            </select>
        </div>
        <div class="form-group">
            <label class="sr-only" for="inputStation">Station</label>
            <select class="form-control" id="inputStation" name="stationId">
                <c:if test="${!empty stationList}">
                    <c:forEach items="${stationList}" var="station">
                        <option id="is${station.id}" value="${station.id}">${station.title}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <button type="button" class="btn btn-default" onclick="addStationToPath();">Sign in</button>
    </form>

</div>

<script type="text/javascript">
    function addStationToPath() {
        var pathId = $('#pathSelect').find('option:selected').attr('value');
        var stationId = $('#inputStation').find('option:selected').attr('value');
        var index = $('#inputIndex').find('option:selected').attr('value');
        var lci = $('#lci').text();
        $.post("<c:url value="/path/stations"/>",
                {
                    pathId: pathId,
                    stationId: stationId,
                    index: index,
                    lci: lci
                },
                function (data) {
                    if (data == "false") {
                        BootstrapDialog.show({
                            title: 'Update error!',
                            message: 'You have irrelevant version of page. Please reload the page and try again',
                            buttons: [
                                {
                                    label: 'Reload',
                                    action: function () {
                                        location.reload();
                                    }
                                }
                            ]
                        });
                    } else {
                        BootstrapDialog.show({
                            title: 'Update success!',
                            message: 'Update success',
                            buttons: [
                                {
                                    label: 'OK',
                                    action: function () {

                                        //location.reload();
                                    }
                                }
                            ]
                        });
                    }
                });
    }

    function go() {
        var pathId = $("#pathSelect").find("option:selected").attr('value');
        location.href = "<c:url value='/path/stations/'/>" + pathId;
    }

    function removeStationFromPath(pathId, index, lci) {
        $.post("<c:url value="/path/stations/remove"/>", { pathId: pathId, index: index, lci: lci }, function (data) {
            if (data == "false") {
                BootstrapDialog.show({
                    title: 'Update error!',
                    message: 'You have irrelevant version of page. Please reload the page and try again',
                    buttons: [
                        {
                            label: 'Reload',
                            action: function () {
                                location.reload();
                            }
                        }
                    ]
                });
            } else {
                BootstrapDialog.show({
                    title: 'Update success!',
                    message: 'Update success',
                    buttons: [
                        {
                            label: 'OK',
                            action: function () {

                                //location.reload();
                            }
                        }
                    ]
                });
            }
        });
    }

    function confirmRemoveStationFromPath(pathId, station, index, lci) {
        BootstrapDialog.show({
            title: 'Confirm removing',
            message: 'Do you confirm removing station ' + station + ' from path with id: ' + pathId + '?',
            buttons: [
                {
                    label: 'Confirm',
                    action: function (dialog) {
                        removeStationFromPath(pathId, index, lci);
                        dialog.close();
                    }
                },
                {
                    label: 'Cancel',
                    action: function (dialog) {
                        dialog.close();
                    }
                }
            ]
        });
    }

    function moveOption (option, optionBefore) {
        var html = $(option).outerHTML;
        $(option).remove();
        $(optionBefore).before(html);
    }

</script>




