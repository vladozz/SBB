<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addPathModal" onclick="addModalPath();">
    Add new path
</button>
<!-- Button trigger modal -->
<%@include file="modals/add_path_modal.jsp" %>

<br/>
<br/>

<c:if test="${!empty pathList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of paths</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfPaths">
                <tr class="active">
                    <th>ID</th>
                    <th>Title</th>
                    <th>Begin Station</th>
                    <th>End Station</th>
                    <th>LCI</th>
                    <th width="10%">Stations</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${pathList}" var="path">
                    <tr id="${path.id}">
                        <td class="id">${path.id}</td>
                        <td class="title">${path.title}</td>
                        <td class="beginStation">${path.beginStation}</td>
                        <td class="endStation">${path.endStation}</td>
                        <td>${path.lastChange}</td>
                        <td>
                            <a href="<c:url value="/path/stations/"/>${path.id}">
                                <button type="button" class="btn btn-primary">Stations</button>
                            </a>
                        </td>
                        <td>
                            <button type="button" class="btn btn-warning" data-toggle="modal"
                                    data-target="#addPathModal"
                                    onclick="editModalPath(${path.id}, '${path.title}', '${path.lastChange}');">Edit
                            </button>
                        </td>
                        <td>
                            <a onclick="confirmDelete(${path.id}, '${path.title}');">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





