function validateRegisterForm(email, password) {
    var $confirmPasswordField =  $('#registerForm').find('input[path="confirmPassword"]');
    var confirmPassword = $confirmPasswordField.val();



    if (!emailRegex.test(email)) {
        popupError('Invalid email');
    } else if (!pswdRegex.test(password)) {
        popupError('Password must contain between 8 and 20 characters: at least one upper case letter, lower case letter and digit');
    } else if ($confirmPasswordField.attr('disabled') !== 'disabled' && password != confirmPassword) {
        popupError('Passwords are not equal');
    } else {
        return true;
    }

    return false;
}

function submitRegisterForm() {
    var $registerForm = $('#registerForm');
    var email = $registerForm.find('input[path="userName"]').val();
    var password = $registerForm.find('input[path="password"]').val();
    if (validateRegisterForm(email, password)) {
        $.post('/SBB/register',
            {email: email, password: password},
            function (response) {
                if (isError(response)) {
                    return;
                }
                popupSuccess("Register success! You will be logged in automatically in a few seconds");
                setTimeout(function () {
                    $.post('/SBB/j_spring_security_check',
                        {j_username: email, j_password: password},
                        function () {
                            location.href = '/SBB';
                        }
                    );
                }, 3000);
            }
        );
    }
}

function showPassword() {
    var check = $('#showPswdChBox').prop('checked');
    var $registerForm = $('#registerForm');
    if (check) {
        $registerForm.find('input[path="password"]').attr('type', 'text');
        $registerForm.find('input[path="confirmPassword"]').val('').attr('disabled', 'disabled');
    } else {
        $registerForm.find('input[path="password"]').attr('type', 'password');
        $registerForm.find('input[path="confirmPassword"]').removeAttr('disabled');
    }
}
