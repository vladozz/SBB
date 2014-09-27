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
    $('#inputId').val('');
    $('#commonId').slideUp('fast');
    $('#inputLC').val('');
    $('#commonLC').slideUp('fast');
    $('#inputTitle').val('');
    $('#inputReturnTitle').val('');
    $('#submit').attr('onclick', 'addPath();').text('Add path');
}

function editModalPath(id) {
    var $row = $('#' + id);
    var title = $row.find('.title').text();
    var returnTitle = $row.find('.returnTitle').text();
    var lastChange = $row.find('.lastChange').text();
    $('#myModalLabel').text('Edit path');
    $('#inputId').val(id);
    $('#commonId').slideDown('fast');
    $('#inputTitle').val(title);
    $('#inputReturnTitle').val(returnTitle);
    $('#inputLC').val(lastChange);
    $('#commonLC').slideDown('fast');
    $('#submit').attr('onclick', 'editPath();').text('Edit path');
}

function validatePathForm(inputTitle) {
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

function addPath() {
    var inputTitle = $("#inputTitle");
    var inputReturnTitle = $("#inputReturnTitle");
    if (validatePathForm(inputTitle)) {
        var title = inputTitle.val();
        var returnTitle = inputReturnTitle.val();

        $.ajax({
            type: "post",
            url: "add",
            data: {title: title, returnTitle: returnTitle},
            success: function (response) {
                $('.close').click();
                $('#close').click();
                $('#listOfPaths').find('tbody').append(response);
                operSuccess();
            },
            error: function (jdXHR) {
                if (jdXHR.status != 400) {
                    $('.close').click();
                    $('#close').click();
                }
                popupError(jdXHR.status + " " + jdXHR.statusText);
            }
        });
    }
}

function editPath() {
    var inputId = $("#inputId");
    var inputTitle = $("#inputTitle");
    var inputReturnTitle = $("#inputReturnTitle");
    var inputLC = $("#inputLC");
    if (validatePathForm(inputTitle)) {
        var id = inputId.val();
        var title = inputTitle.val();
        var returnTitle = inputReturnTitle.val();
        var lastChange = inputLC.val();
        $.ajax({
            type: "post",
            url: "edit",
            data: {id: id, title: title, returnTitle: returnTitle, lastChange: lastChange},
            success: function (resp) {
                $('.close').click();
                $('#close').click();
                $('#' + id).replaceWith(resp);
                operSuccess();
            },
            error: function (jdXHR) {
                if (jdXHR.status != 400) {
                    $('.close').click();
                    $('#close').click();
                }
                popupError(jdXHR.status + " " + jdXHR.statusText);
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