function confirmDelete(id, number) {

    BootstrapDialog.show({
        title: 'Confirm delete operation',
        message: "Do you confirm removing the train with id " + id + " and number " + number + "?",
        buttons: [{
            label: 'Delete',
            action: function(dialog) {
                $.ajax({
                    url: "delete/" + id,
                    success: function (data) {
                        $('#' + id).remove();
                        dialog.setMessage('Delete success');
                        dialog.close();
                    },
                    error: function (error) {
                        dialog.setMessage(error);
                    }
                });
            }
        }, {
            label: 'Cancel',
            action: function() {
                this.close();
            }
        }]
    });

}

function addModalTrain() {

    $('#inputId').attr('value', '');
    $('#commonId').slideUp('fast');
    $('#inputNumber').attr('value', '');
    $('#inputPQ').attr('value', '');
    $('#submit').attr('onclick', 'addTrain();').text('Add train');
}

function editModalTrain(id, number, placesQty) {
    $('#inputId').attr('value', id);
    $('#commonId').slideDown('fast');
    $('#inputNumber').attr('value', number);
    $('#inputPQ').attr('value', placesQty);
    $('#submit').attr('onclick', 'editTrain();').text('Edit train');
}

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

function addTrain() {
    var inputNumber = document.getElementById("inputNumber");
    var inputPQ = document.getElementById("inputPQ");
    if (validateTrainForm(inputNumber, inputPQ)) {
        var number = inputNumber.value;
        var placesQty = inputPQ.value;

        $.ajax({
            type: "post",
            url: "add",
            data:  encodeURIComponent("number=" + number + "&placesQty=" + placesQty),
            success: function (id) {
                var inner = generateTableRow(id, number, placesQty);
                var addHtml = "<tr id=\"" + id + "\">\n" + inner + "</tr>";
                $('#listOfTrains').append(addHtml);
                $('#close').click();
            },
            error: function (data) {
                alert('Error!');
            }
        });
    }
}

function generateTableRow(id, number, placesQty) {
    return "<td>" + id + "</td>\n" +
        "<td>" + number + "</td>\n" +
        "<td>" + placesQty + "</td>\n" +
        "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
        "data-target=\"#addTrainModal\" onclick=\"editModalTrain(" + id + ", '" + number + "', ' "
        + placesQty + "');\" >Edit</button></td>\n" +
        "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
        ", '" + number + "');\">Delete</button></td>\n";
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
            data: encodeURIComponent("id=" + id + "&number=" + number + "&placesQty=" + placesQty),
            success: function () {
/*                var editHtml = "<td>" + id + "</td>\n" +
                 "<td>" + number + "</td>\n" +
                 "<td>" + placesQty + "</td>\n" +
                 "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
                 "data-target=\"#addTrainModal\" onclick=\"editModalTrain(" + id + ", '" + number + "', ' "
                 + placesQty + "');\" >Edit</button></td>\n" +
                 "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
                 ", '" + number + "');\">Delete</button></td>";*/
                var editHtml = generateTableRow(id, number, placesQty);
                $('#' + id).html(editHtml);
                $('#close').click();
            },
            error: function () {
                alert('Error!');
            }
        });
    }
}

function showError(message) {
    $('#mes').text(message);
    $("#err").slideDown("slow");
}

function resetTrainForm() {
    $('#trainForm').trigger('reset');
    $("#err").slideUp("fast");
    $('#mes').text("");
}

function getTime() {
    $.ajax({url: 'time', success: function (data) {
        $('#tim').html(data)
    }});
}