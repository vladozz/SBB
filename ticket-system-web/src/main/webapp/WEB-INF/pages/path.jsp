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
                    <th>Return title</th>
                    <th>Begin Station</th>
                    <th>End Station</th>
                    <th>LCI</th>
                    <th width="10%">Stations</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${pathList}" var="path">
                    <%@include file="path_row.jsp"%>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





