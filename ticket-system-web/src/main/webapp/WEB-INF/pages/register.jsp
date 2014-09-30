<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

<script type="text/javascript" src="<c:url value="/resources/js/register.js"/>"></script>
