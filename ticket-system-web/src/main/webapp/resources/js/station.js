function confirmDelete(id, title) {

    BootstrapDialog.show({
        title: 'Confirm delete operation',
        message: "Do you confirm removing the station with id " + id + " and title " + title + "?",
        buttons: [
            {
                label: 'Delete',
                action: function (dialog) {
                    $.ajax({
                        url: "delete/" + id,
                        success: function () {
                            $('#' + id).remove();
                            dialog.setMessage('Delete success');
                            dialog.close();
                        },
                        error: function (error) {
                            dialog.setMessage(error);
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
    $('#inputId').attr('value', '');
    $('#commonId').slideUp('fast');
    $('#inputTitle').attr('value', '');
    $("#inputTZ").find("[value='GMT']").attr('selected', true);
    $('#submit').attr('onclick', 'addStation();').text('Add station');
}

function editModalStation(id, title, timeZone) {
    $('#myModalLabel').text('Edit station');
    $('#inputId').attr('value', id);
    $('#commonId').slideDown('fast');
    $('#inputTitle').attr('value', title);
   // $('select option:selected').attr('selected', false);
    $("#inputTZ").find("[value='"+ timeZone + "']").attr('selected', true);
    $('#submit').attr('onclick', 'editStation();').text('Edit station');
}

function validateStationForm(inputTitle, inputTZ) {

    var title = inputTitle.value;
    if (title.length < 1 || title.length > 50) {
        showError("Title field must contain between 1 and 50 characters");
        inputTitle.focus();
        return false;
    }

    $("#err").slideUp("fast");
    return true;


}

function addStation() {
    var inputTitle = document.getElementById("inputTitle");
    var inputTZ = document.getElementById("inputTZ");
    if (validateStationForm(inputTitle, inputTZ)) {
        var title = inputTitle.value;
        var timeZone = inputTZ.value;

        $.ajax({
            type: "post",
            url: "add",
            data: "title=" + encodeURIComponent(title) + "&timeZone=" + encodeURIComponent(timeZone),
            success: function (id) {
                $('.close').click();
                $('#close').click();
                var inner = generateTableRow(id, title, timeZone);
                var addHtml = "<tr id=\"" + id + "\">\n" + inner + "</tr>";
                $('#listOfStations').append(addHtml);
            },
            error: function (data) {
                alert('Error!' + data);
            }
        });
    }
}

function generateTableRow(id, title, timeZone) {
    return "<td>" + id + "</td>\n" +
        "<td>" + title + "</td>\n" +
        "<td>" + timeZone + "</td>\n" +
        "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
        "data-target=\"#addStationModal\" onclick=\"editModalStation(" + id + ", '" + title + "', '"
        + timeZone + "');\" >Edit</button></td>\n" +
        "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
        ", '" + title + "');\">Delete</button></td>\n";
}


function editStation() {
    var inputId = document.getElementById("inputId");
    var inputTitle = document.getElementById("inputTitle");
    var inputTZ = document.getElementById("inputTZ");
    if (validateStationForm(inputTitle, inputTZ)) {
        var id = inputId.value;
        var title = inputTitle.value;
        var timeZone = inputTZ.value;
        $.ajax({
            type: "post",
            url: "edit",
            data: "id=" + encodeURIComponent(id) + "&title=" + encodeURIComponent(title) + "&timeZone=" + encodeURIComponent(timeZone),
            success: function () {
                $('.close').click();
                $('#close').click();
                var editHtml = generateTableRow(id, title, timeZone);
                $('#' + id).html(editHtml);
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

function resetStationForm() {
    $('#stationForm').trigger('reset');
    $("#err").slideUp("fast");
    $('#mes').text("");
}