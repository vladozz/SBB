<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty removed}">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addStationModal"
            onclick="addModalStation();">
        Add new station
    </button>
    <%@include file="modals/add_station_modal.jsp" %>
</c:if>
<br/>
<br/>

<c:if test="${!empty stationList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">
            <c:if test="${empty removed}">List of stations <a href="removed">(show removed)</a></c:if>
            <c:if test="${removed == true}">List of removed stations <a href="index">(show actual)</a></c:if>
        </div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfStations">
                <tr class="active">
                    <th>ID</th>
                    <th>Title</th>
                    <th>TimeZone</th>
                    <c:if test="${empty removed}">
                        <th width="10%">Edit</th>
                        <th width="10%">Delete</th>
                    </c:if>
                </tr>
                <c:forEach items="${stationList}" var="station">
                    <%@include file="station_row.jsp" %>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





