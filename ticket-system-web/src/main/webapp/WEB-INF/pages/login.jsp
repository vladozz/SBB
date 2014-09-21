<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${not empty param.error}">
    : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
</c:if>
<form role="form" class="form-horizontal col-sm-6 jumbotron" method="POST" action="<c:url value="/j_spring_security_check" />">
    <div class="form-group">
        <label class="control-label col-sm-4" for="username">Email (or login)</label>

        <div class="col-sm-8">
            <input id="username" type="text" name="j_username" class="form-control" required="true"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-4" for="password">Password</label>

        <div class="col-sm-8">
            <input id="password" type="password" name="j_password" class="form-control" required="true"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-8">
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="_spring_security_remember_me"> Remember me
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-2">
            <input class="btn btn-primary" type="submit" value="Sign in"/>
        </div>
        <div class="col-sm-2">
            <input class="btn btn-default" type="reset" value="Reset"/>
        </div>
    </div>

</form>
