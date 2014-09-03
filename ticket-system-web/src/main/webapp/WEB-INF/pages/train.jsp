<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addTrainModal">
    Add new train
</button>
<!-- Button trigger modal -->
<%@include file="modals/add_train_modal.jsp"%>

<br/>
<br/>

<c:if test="${!empty trainList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of trains</div>
        <div class="table-responsive">
            <table class="table table-hover">
                <tr class="active">
                    <th>ID</th>
                    <th>Number</th>
                    <th>Quanity of places</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${trainList}" var="train">
                    <tr>
                        <td>${train.id}</td>
                        <td>${train.number}</td>
                        <td>${train.placesQty}</td>

                        <td>
                            <button type="button" class="btn btn-warning">Edit</button>
                        </td>
                        <td>
                            <a href="delete/${train.id}">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>
</div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>