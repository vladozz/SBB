<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form role="form" class="form-horizontal col-sm-6" id="changePswdForm" method="post">
    <h3>Profile settings</h3>
    <br/>
    <br/>
    <div class="form-group">
        <label class="control-label col-sm-4">Your login: </label>

        <div class="col-sm-8">
            <h5><sec:authentication property="principal.username"/></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4">Your authorities: </label>

        <div class="col-sm-8">
            <h5><sec:authentication property="principal.authorities[0]"/></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4">Current password</label>

        <div class="col-sm-8">
            <input id="curPswd" class="form-control" type="password" required="true" size="20"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4">New password</label>

        <div class="col-sm-8">
            <input id="newPswd" class="form-control" type="password" required="true" size="20"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4">Confirm new password</label>

        <div class="col-sm-8">
            <input id="confNewPswd" class="form-control" type="password" required="true" size="20"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-2">
            <button type="button" class="btn btn-primary" onclick="submitChangePassword()">Change password</button>
        </div>
        <div class="col-sm-2 col-sm-offset-1">
            <button type="reset" class="btn btn-default col-sm-offset-3">Reset</button>
        </div>
    </div>
</form>

<script type="text/javascript" src="<c:url value="/resources/js/setting.js"/>"></script>