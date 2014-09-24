function submitChangePassword() {
    var $curPswd = $('#curPswd');
    var $newPswd = $('#newPswd');
    var $confNewPswd = $('#confNewPswd');
    var curPswd = $curPswd.val();
    var newPswd = $newPswd.val();
    var confNewPswd = $confNewPswd.val();

    if (validateChangingPassword()) {
        $.post('/SBB/settings/change_pswd',
            {curPswd: curPswd, newPswd: newPswd},
            function(response) {
                response = response.trim();
                if (!isError(response)) {
                    $('#changePswdForm')[0].reset();
                    popupSuccess(response.substring(8));
                }
            }
        );
    }


    function validateChangingPassword() {
        if (sbb_debug_js_validation_off != undefined) {
            return true;
        }
        if (curPswd === '') {
            popupError('Enter current password!');
            $curPswd.focus();
        } else if (!pswdRegex.test(newPswd)) {
            $confNewPswd.val('');
            $newPswd.val('').focus();
            popupError('Password length must be between 5 and 20 characters!');
        } else if (newPswd != confNewPswd) {
            $confNewPswd.val('').focus();
            popupError('New password and confirm new password fields are not equals!');
        } else {
            return true;
        }
        return false;
    }
}

