function confirmDelete(id) {
    var number = $('#' + id + ' .number').text();
    BootstrapDialog.show({
        title: 'Confirm remove operation',
        message: "Do you confirm removing the train with id " + id + " and number " + number + "?",
        buttons: [
            {
                label: 'Confirm',
                action: function (dialog) {
                    $.ajax({
                        url: "delete",
                        data: {id: id, version: $('#' + id + ' .version').text()},
                        type: 'post',
                        success: function (response) {
                            $('#' + id).remove();
                            dialog.close();
                            popupSuccess(response);
                        },
                        error: function (jdXHR) {
                            dialog.close();
                            popupjdXHRError(jdXHR);
                        }
                    });
                }
            },
            {
                label: 'Cancel',
                action: function (dialog) {
                    dialog.close();
                }
            }
        ]
    });

}

function addModalTrain() {
    $('#myModalLabel').text('Add new train');
    $('#inputId').val('');
    $('#commonId').slideUp('fast');
    $('#inputNumber').val('');
    $('#inputPQ').val('');
    $('#submit').text('Add train').attr('onclick', 'addTrain();');
}

function editModalTrain(id) {
    var ptr = '#' + id + ' ';
    $('#myModalLabel').text('Edit train');
    $('#inputId').val(id);
    $('#commonId').slideDown('fast');
    $('#inputNumber').val($(ptr + '.number').text());
    $('#inputPQ').val($(ptr + '.placesQty').text());
    $('#submit').attr('onclick', 'editTrain();').text('Edit train');
}

function validateTrainForm(inputNumber, inputPQ) {
    if (sbb_debug_js_validation_off != undefined) {
        return true;
    }
    var number = inputNumber.val();
    if (number.length < 1 || number.length > 30) {
        showError("Number field must contain between 1 and 30 characters");
        inputNumber.focus();
        return false;
    }

    var placesQty = inputPQ.val();
    if (placesQty.length < 1) {
        showError("Quantity of places field cannot be empty!");
        inputPQ.focus();
        return false;
    } else if (!(placesQty >= 0 || placesQty <= 0)) {
        //if field is not number (for number this condition is always false)
        showError("Quantity of places field must be numeric");
        inputPQ.val('');
        inputPQ.focus();
        return false;
    } else if (!(placesQty > 0 && placesQty <= 5000)) {
        showError("Quantity of places field must be between 1 and 2000");
        inputPQ.focus();
        return false;
    }
    $("#err").slideUp("fast");
    return true;


}

function addTrain() {
    var inputNumber = $("#inputNumber");
    var inputPQ = $("#inputPQ");
    if (validateTrainForm(inputNumber, inputPQ)) {
        var number = inputNumber.val();
        var placesQty = inputPQ.val();
        $.ajax({
            type: "post",
            url: "add",
            data: {number: number, placesQty: placesQty},
            success: function (response) {
                $('.close').click();
                $('#close').click();

                $('#listOfTrains').append(response);
            },
            error: function (jdXHR) {
                popupjdXHRError(jdXHR);
            }
        });
    }
}


function editTrain() {
    var inputId = $("#inputId");
    var inputNumber =$("#inputNumber");
    var inputPQ = $("#inputPQ");
    if (validateTrainForm(inputNumber, inputPQ)) {
        var id = inputId.val();
        var number = inputNumber.val();
        var placesQty = inputPQ.val();
        var version = $('#' + id + ' .version').text();
        $.ajax({
            type: "post",
            url: "edit",
            data: {id: id, number: number, placesQty: placesQty, version: version},
            success: function (response) {
                $('.close').click();
                $('#close').click();
                $('#' + id).replaceWith(response);
            },
            error: function (jdXHR) {
                popupjdXHRError(jdXHR);
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