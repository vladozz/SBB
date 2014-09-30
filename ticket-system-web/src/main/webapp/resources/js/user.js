$(document).ready(function () {
    $('#addUserButton').click(function () {
        $('#addUserForm').slideToggle('slow');
    });
});

function addUser() {
    var $inputLogin = $('#inputLogin');
    var $inputPassword = $('#inputPassword');
    var login = $inputLogin.val();
    var password = $inputPassword.val();
    var role = $('#inputRole').find('option:selected').val();

    $.post('/SBB/user/add',
        {login: login, password: password, roleId: role},
        function (response) {
            $inputLogin.val('');
            $inputPassword.val('');
            popupSuccess('User ' + login + ' added successfully');
            $('#listOfUsers').find('tbody').append(response);
        }
    ).error(function (jdXHR) {
            popupjdXHRError(jdXHR);
        });
}

function confirmDelete(id, login) {
    BootstrapDialog.show({
        title: 'Confirm delete operation',
        message: "Do you confirm removing the user with id " + id + " and login " + login + "?",
        buttons: [
            {
                label: 'Delete',
                action: function (dialog) {
                    dialog.close();
                    $.get("/SBB/user/delete/" + id,
                        function (response) {
                            if (isError(response)) {
                                return;
                            }
                            $('#' + id).remove();
                            popupSuccess('Delete success');
                        }
                    ).error(function (jdXHR) {
                            popupjdXHRError(popupError(jdXHR.status + ' ' + jdXHR.statusText + '\n' + jdXHR.responseText));
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

function changePassword(userId) {
    /*var $body = $('<form/>').attr('class', 'form-horizontal').append($('#passwordFG').clone().removeAttr('id'));
     $body.find('label[for="inputPassword"]').text('New password');*/
    var newPswd = generatePass(5);
    BootstrapDialog.show({
        title: 'Change password',
        message: 'New password is <strong>' + newPswd + '</strong>. Do you confirm changing the password?',
        buttons: [
            {
                label: 'Change password',
                action: function (dialog) {
                    dialog.close();
                    $.post('/SBB/user/edit',
                        {userId: userId, password: newPswd, version: $('#' + userId + ' .version').text()},
                        function (response) {
                            popupSuccess(response);
                        }
                    ).error(function (jdXHR) {
                            popupjdXHRError(jdXHR);
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


function generatePass(plength) {
    var keylist = "abcdefghijklmnopqrstuvwxyz123456789";
    var temp = '';
    for (var i = 0; i < plength; i++) {
        temp += keylist.charAt(Math.floor(Math.random() * keylist.length));
    }
    return temp;
}