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
            response = $.trim(response);
            if (isError(response)) {
                return;
            }
            $inputLogin.val('');
            $inputPassword.val('');
            popupSuccess('User ' + login + ' added successfully');
            $('#listOfUsers').find('tbody').append(response);
        }
    );
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
                    );
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
    var newPswd = generatePass(10);
    BootstrapDialog.show({
        title: 'Change password',
        message: 'New password is ' + newPswd + '. Do you confirm changing the password?' ,
        buttons: [
            {
                label: 'Change password',
                action: function (dialog) {
                    dialog.close();
                    $.post('/SBB/user/edit',
                        {userId: userId, password: newPswd},
                        function (response) {
                            if (isError(response)) {
                                return;
                            }
                            popupSuccess('Password changing success');
                        }
                    );
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
    var f = false;
    for (var i = 0; i < plength; i++) {
        var c = keylist.charAt(Math.floor(Math.random() * keylist.length));
        if (!f) {
            f = true;
            c = c.toUpperCase();
        }
        temp += c;
    }
    if (!f) {
        return generatePass(10);
    }
    return temp;
}