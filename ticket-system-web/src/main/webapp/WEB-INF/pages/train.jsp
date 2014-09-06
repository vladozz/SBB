<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addTrainModal" onclick="addModalTrain();">
    Add new train
</button>
<!-- Button trigger modal -->
<%@include file="modals/add_train_modal.jsp" %>

<br/>
<br/>

<c:if test="${!empty trainList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of trains</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfTrains">
                <tr class="active">
                    <th>ID</th>
                    <th>Number</th>
                    <th>Quanity of places</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${trainList}" var="train">
                    <tr id="${train.id}">
                        <td>${train.id}</td>
                        <td>${train.number}</td>
                        <td>${train.placesQty}</td>

                        <td>
                            <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addTrainModal"
                                    onclick="editModalTrain(${train.id}, '${train.number}', '${train.placesQty}');">Edit</button>
                        </td>
                        <td>
                            <a <%--href="delete/${train.id}"--%>
                                    onclick="confirmDelete(${train.id}, '${train.number}');">
                                <button type="button" class="btn btn-danger">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>


<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>

<script type="text/javascript">
    function confirmDelete(id, number) {

        var ans = confirm("Do you confirm removing the train with id " + id + " and number " + number + "?");
        if (ans) {
            //document.location.href = "delete/" + id;
            $.ajax({
                url: "delete/" + id,
                success: function (data) {
                    $('#' + id).remove();
                }});
        }
    }

    function addModalTrain() {

        $('#inputId').attr('value', '').slideUp('fast');
        $('#inputNumber').attr('value', '');
        $('#inputPQ').attr('value', '');
        $('#submit').attr('onclick', 'addTrain();').text('Add train');
    }

    function editModalTrain(id, number, placesQty) {
        $('#inputId').attr('value', id).slideDown('fast');
        $('#inputNumber').attr('value', number);
        $('#inputPQ').attr('value', placesQty);
        $('#submit').attr('onclick', 'editTrain();').text('Edit train');
    }

</script>
