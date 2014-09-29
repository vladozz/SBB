function confirmDelete(id) {
    var title = $('#' + id + ' .title').text();
    BootstrapDialog.show({
        title: 'Confirm delete operation',
        message: "Do you confirm removing the station with id " + id + " and title " + title + "?",
        buttons: [
            {
                label: 'Delete',
                action: function (dialog) {
                    $.ajax({
                        url: "delete",
                        type: 'post',
                        data: {id: id, version: $('#' + id + ' .version').text()},
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
                action: function () {
                    this.close();
                }
            }
        ]
    });

}

function addModalStation() {

    $('#myModalLabel').text('Add new station');
    $('#inputId').val();
    $('#commonId').slideUp('fast');
    $('#inputTitle').val();
    $("#inputTZ").find("[value='GMT']").attr('selected', true);
    $('#submit').attr('onclick', 'addStation();').text('Add station');
}

function editModalStation(id) {
    var ptr = '#' + id + ' ';
    $('#myModalLabel').text('Edit station');
    $('#inputId').val(id);
    $('#commonId').slideDown('fast');
    $('#inputTitle').val($(ptr + '.title').text());
    // $('select option:selected').attr('selected', false);
    $("#inputTZ").find("[value='" + $(ptr + '.timeZone').text() + "']").attr('selected', true);
    $('#submit').attr('onclick', 'editStation();').text('Edit station');
}

function validateStationForm(inputTitle, inputTZ) {
    if (sbb_debug_js_validation_off != undefined) {
        return true;
    }

    var title = inputTitle.val();
    if (title.length < 1 || title.length > 50) {
        showError("Title field must contain between 1 and 50 characters");
        inputTitle.focus();
        return false;
    }

    $("#err").slideUp("fast");
    return true;


}

function addStation() {
    var inputTitle = $("#inputTitle");
    var inputTZ = $("#inputTZ");
    if (validateStationForm(inputTitle, inputTZ)) {
        var title = inputTitle.val();
        var timeZone = inputTZ.val();

        $.ajax({
            type: "post",
            url: "add",
            data: {title: title, timeZone: timeZone},
            success: function (response) {
                $('.close').click();
                $('#close').click();
                $('#listOfStations').append(response);
            },
            error: function (jdXHR) {
                popupjdXHRError(jdXHR);
            }
        });
    }
}

function editStation() {
    var inputId = $("#inputId");
    var inputTitle = $("#inputTitle");
    var inputTZ = $("#inputTZ");
    if (validateStationForm(inputTitle, inputTZ)) {
        var id = inputId.val();
        var title = inputTitle.val();
        var timeZone = inputTZ.val();
        var version = $('#' + id + ' .version').text();
        $.ajax({
            type: "post",
            url: "edit",
            data: {id: id, title: title, timeZone: timeZone, version: version},
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

function resetStationForm() {
    $('#stationForm').trigger('reset');
    $("#err").slideUp("fast");
    $('#mes').text("");
}