<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty removed}">
    <%@include file="modals/add_train_modal.jsp" %>
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addTrainModal" onclick="addModalTrain();">
        Add new train
    </button>
</c:if>

<br/>
<br/>

<c:if test="${!empty trainList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">
            <c:if test="${empty removed}">List of trains <a href="removed">(show removed)</a></c:if>
            <c:if test="${removed == true}">List of removed trains <a href="index">(show actual)</a></c:if>
        </div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfTrains">
                <tr class="active">
                    <th>ID</th>
                    <th>Number</th>
                    <th>Quantity of places</th>
                    <c:if test="${empty removed}">
                        <th width="10%">Edit</th>
                        <th width="10%">Delete</th>
                    </c:if>
                </tr>
                <c:forEach items="${trainList}" var="train">
                    <%@include file="train_row.jsp" %>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>





