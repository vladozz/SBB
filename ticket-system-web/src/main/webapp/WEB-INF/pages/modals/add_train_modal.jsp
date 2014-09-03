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
                <form:form id="trainForm" class="form-horizontal" role="form" action="add" method="post" commandName="train">
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
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="resetTrainForm()">Close</button>
                            <button type="button" class="btn btn-primary" onclick="validateTrainForm();">Add train</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function validateTrainForm() {

        var inputNumber = document.getElementById("inputNumber");
        var number = inputNumber.value;
        if (number.length < 1 || number.length > 30) {
            showError("Number field must contain between 1 and 30 characters");
            inputNumber.focus();
            return;
        }
        var inputPQ = document.getElementById("inputPQ");
        var placesQty = inputPQ.value;
        if (placesQty.length < 1) {
            showError("Quantity of places field cannot be empty!");
            inputPQ.focus();
            return;
        } else if (!(placesQty >= 0 || placesQty <= 0)) {
            //if field is not number (for number this condition is always false)
            showError("Quantity of places field must be numeric");
            inputPQ.value = "";
            inputPQ.focus();
            return;
        } else if (!(placesQty > 0 && placesQty <= 2000)) {
            showError("Quantity of places field must be between 1 and 2000");
            inputPQ.focus();
            return;
        }
        $("#err").slideUp("fast");
        document.getElementById("trainForm").submit();

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

</script>

