<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"/>

<form role="form" class="form-horizontal col-sm-6 jumbotron" id="registerForm" method="post"
      onsubmit="validateRegisterForm()"
      action="<c:url value="/register"/> ">
    <div class="form-group">
        <label class="control-label col-sm-4">E-mail</label>

        <div class="col-sm-8">
            <input path="userName" class="form-control" type="text" required="true" size="40"/>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-sm-4">Password</label>

        <div class="col-sm-8">
            <input path="password" class="form-control" type="password" required="true" size="20"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-8">
            <div class="checkbox">
                <label>
                    <input id="showPswdChBox" type="checkbox" onclick="showPassword()"/> Show password
                </label>
            </div>
        </div>
    </div>


    <div class="form-group">
        <label class="control-label col-sm-4">Confirm Password</label>

        <div class="col-sm-8">
            <input path="confirmPassword" class="form-control" type="password" required="true" size="20"/>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-2">
            <button type="button" class="btn btn-primary" onclick="submitRegisterForm()">Submit</button>
        </div>
        <div class="col-sm-2">
            <button type="reset" class="btn btn-default col-sm-offset-1">Reset</button>
        </div>
    </div>
</form>

<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<%--<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.js"/>"></script>--%>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>


<script type="text/javascript">
    function validateRegisterForm(email, password) {
        var $confirmPasswordField =  $('#registerForm').find('input[path="confirmPassword"]');
        var confirmPassword = $confirmPasswordField.val();
        var emailRegex = new RegExp('^[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}$', 'i');
        var pswdRegex = (/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$/);

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


</script>
