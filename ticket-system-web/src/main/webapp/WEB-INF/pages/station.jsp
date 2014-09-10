<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addStationModal" onclick="addModalStation();">
    Add new station
</button>
<!-- Button trigger modal -->
<%@include file="modals/add_station_modal.jsp" %>

<br/>
<br/>

<c:if test="${!empty stationList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of stations</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfStations">
                <tr class="active">
                    <th>ID</th>
                    <th>Title</th>
                    <th>TimeZone</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${stationList}" var="station">
                    <tr id="${station.id}">
                        <td class = "id">${station.id}</td>
                        <td class = "title">${station.title}</td>
                        <td class= "timeZone">${station.timeZone}</td>

                        <td>
                            <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addStationModal"
                                    onclick="editModalStation(${station.id}, '${station.title}', '${station.timeZone}');">Edit</button>
                        </td>
                        <td>
                            <a <%--href="delete/${station.id}"--%>
                                    onclick="confirmDelete(${station.id}, '${station.title}');">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





