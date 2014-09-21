function confirmDelete(id, title) {

    BootstrapDialog.show({
        title: 'Confirm delete operation',
        message: "Do you confirm removing the path with id " + id + " and title " + title + "?",
        buttons: [
            {
                label: 'Delete',
                action: function (dialog) {
                    $.ajax({
                        type: 'get',
                        url: "/SBB/path/delete/" + id,
                        success: function () {
                            $('#' + id).remove();
                            dialog.setMessage('Delete success');
                            dialog.close();
                        },
                        error: function (error) {
                            dialog.setMessage("Error: " + error);
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

function addModalPath() {

    $('#myModalLabel').text('Add new path');
    $('#inputId').attr('value', '');
    $('#commonId').slideUp('fast');
    $('#inputLC').attr('value', '');
    $('#commonLC').slideUp('fast');
    $('#inputTitle').attr('value', '');
    $('#submit').attr('onclick', 'addPath();').text('Add path');
}

function editModalPath(id, title, lastChange) {
    $('#myModalLabel').text('Edit path');
    $('#inputId').attr('value', id);
    $('#commonId').slideDown('fast');
    $('#inputTitle').attr('value', title);
    $('#inputLC').attr('value', lastChange);
    $('#commonLC').slideDown('fast');
    $('#submit').attr('onclick', 'editPath();').text('Edit path');
}

function validatePathForm(inputTitle) {

    var title = inputTitle.value;
    if (title.length < 1 || title.length > 50) {
        showError("Title field must contain between 1 and 50 characters");
        inputTitle.focus();
        return false;
    }

    $("#err").slideUp("fast");
    return true;


}

function addPath() {
    var inputTitle = document.getElementById("inputTitle");
    if (validatePathForm(inputTitle)) {
        var title = inputTitle.value;

        $.ajax({
            type: "post",
            url: "add",
            data: "title=" + encodeURIComponent(title),
            success: function (id) {
                $('.close').click();
                $('#close').click();
                var inner = generateTableRow(id, title, 1);
                var addHtml = "<tr id=\"" + id + "\">\n" + inner + "</tr>";
                $('#listOfPaths').append(addHtml);
            },
            error: function (data) {
                alert('Error!' + data);
            }
        });
    }
}

function generateTableRow(id, title, lastChange) {
    return "<td>" + id + "</td>\n" +
        "<td>" + title + "</td>\n" +
        "<td>" + "" + "</td>\n" +
        "<td>" + "" + "</td>\n" +
        "<td>" + lastChange + "</td>\n" +
        "<td><a href=\"/SBB/path/stations/" + id + "\">"+
        "<button type=\"button\" class=\"btn btn-primary\">Stations</button> </a></td>\n" +
        "<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" " +
        "data-target=\"#addPathModal\" onclick=\"editModalPath(" + id + ", '" + title + "', '"
        + lastChange + "');\" >Edit</button></td>\n" +
        "<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"confirmDelete(" + id +
        ", '" + title + "');\">Delete</button></td>\n";
}


function editPath() {
    var inputId = document.getElementById("inputId");
    var inputTitle = document.getElementById("inputTitle");
    var inputLC = document.getElementById("inputLC");
    if (validatePathForm(inputTitle)) {
        var id = inputId.value;
        var title = inputTitle.value;
        var lastChange = inputLC.value;
        $.ajax({
            type: "post",
            url: "edit",
            data: "id=" + encodeURIComponent(id) + "&title=" + encodeURIComponent(title) + "&lastChange=" + encodeURIComponent(lastChange),
            success: function (resp) {
                if (resp == 0) {
                    BootstrapDialog.show({
                        title: 'Error',
                        message: 'Editing of this path is failed. Please reload page and try again.',
                        buttons: [{
                            label: 'OK',
                            action: function() {
                                location.reload();
                            }
                        }]
                    });
                } else {
                    $('.close').click();
                    $('#close').click();
                    var editHtml = generateTableRow(id, title, ++lastChange);
                    $('#' + id).html(editHtml);
                }
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

function resetPathForm() {
    $('#pathForm').trigger('reset');
    $("#err").slideUp("fast");
    $('#mes').text("");
}