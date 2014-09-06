<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Modal -->
<div class="modal fade" id="addTrainModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new train</h4>
            </div>
            <div class="jumbotron" id="err" style="display: none; background-color: #FFA0A0">
                <div class="container">
                    <h3 id="mes">Error</h3>
                </div>

            </div>
            <div class="modal-body">
                <form:form id="trainForm" class="form-horizontal" role="form" action="add" method="get" commandName="train">
                    <div class="form-group">
                        <label for="inputId" class="col-sm-3 control-label">ID</label>

                        <div class="col-sm-9">
                            <form:input path="id" type="text" class="form-control" id="inputId" placeholder="" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputNumber" class="col-sm-3 control-label">Number</label>

                        <div class="col-sm-9">
                            <form:input path="number" type="text" class="form-control" id="inputNumber" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPQ" class="col-sm-3 control-label">Quantity of palaces</label>

                        <div class="col-sm-9">
                            <form:input path="placesQty" type="text" class="form-control" id="inputPQ" placeholder=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">

                            <button id ="submit" type="button" class="btn btn-primary" onclick="addTrain();">Add train</button>
                            <button id="close" type="button" class="btn btn-default" data-dismiss="modal" onclick="resetTrainForm()">Close</button>
                        </div>
                        <div id="tim"></div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function validateTrainForm(inputNumber, inputPQ) {

        var number = inputNumber.value;
        if (number.length < 1 || number.length > 30) {
            showError("Number field must contain between 1 and 30 characters");
            inputNumber.focus();
            return false;
        }

        var placesQty = inputPQ.value;
        if (placesQty.length < 1) {
            showError("Quantity of places field cannot be empty!");
            inputPQ.focus();
            return false;
        } else if (!(placesQty >= 0 || placesQty <= 0)) {
            //if field is not number (for number this condition is always false)
            showError("Quantity of places field must be numeric");
            inputPQ.value = "";
            inputPQ.focus();
            return false;
        } else if (!(placesQty > 0 && placesQty <= 2000)) {
            showError("Quantity of places field must be between 1 and 2000");
            inputPQ.focus();
            return false;
        }
        $("#err").slideUp("fast");
        return true;


    }

    function addTrain () {
        var inputNumber = document.getElementById("inputNumber");
        var inputPQ = document.getElementById("inputPQ");
        if (validateTrainForm(inputNumber, inputPQ)) {
            var number = inputNumber.value;
            var placesQty = inputPQ.value;
            $.ajax({
                type: "post",
                url: "add",
                data: "number=" + number + "&placesQty=" + placesQty,
                success: function (id) {
                    var addHtml = "<tr id=\"" + id + "\">\n" +
                            "<td>" + id + "</td>\n" +
                            "<td>" + number + "</td>\n" +
                            "<td>" + placesQty + "</td>\n" +
                            "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
                            "data-target=\"#addTrainModal\" onclick=\"editModalTrain(" + id +", '" + number + "', ' "
                            + placesQty + "');\" >Edit</button></td>\n" +
                            "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
                            ", '" + number + "');\">Delete</button></td>\n</tr>";
                    $('#listOfTrains').append(addHtml);
                    $('#close').click();
                },
                error: function (e) {
                    alert('Error: ' + e);
                }
            });
        }
    }

    function editTrain() {
        var inputId = document.getElementById("inputId");
        var inputNumber = document.getElementById("inputNumber");
        var inputPQ = document.getElementById("inputPQ");
        if (validateTrainForm(inputNumber, inputPQ)) {
            var id = inputId.value;
            var number = inputNumber.value;
            var placesQty = inputPQ.value;
            $.ajax({
                type: "post",
                url: "edit",
                data: "id=" + id + "&number=" + number + "&placesQty=" + placesQty,
                success: function (response) {
                    var editHtml = "<td>" + id + "</td>\n" +
                            "<td>" + number + "</td>\n" +
                            "<td>" + placesQty + "</td>\n" +
                            "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
                            "data-target=\"#addTrainModal\" onclick=\"editModalTrain(" + id +", '" + number + "', ' "
                            + placesQty + "');\" >Edit</button></td>\n" +
                            "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
                            ", '" + number + "');\">Delete</button></td>";
                    $('#' + id).html(editHtml);
                    $('#close').click();
                },
                error: function (e) {
                    alert('Error: ' + e);
                }
            });
        }
    }

    function showError(message) {
        $('#mes').text(message);
        $("#err").slideDown("slow");
    }

    function resetTrainForm() {
        $('#trainForm').trigger( 'reset' );
        $("#err").slideUp("fast");
        $('#mes').text("");
    }

    function getTime() {
        $.ajax({url: 'time', success: function(data) {$('#tim').html(data) }} );
    }

</script>

