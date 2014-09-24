<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--<img src="<c:url value='/resources/res/logo.png'/>" class="img-responsive" alt="Responsive image">--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="/"/>">Main</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasAnyRole('ROLE_GUEST, ROLE_USER')">
                    <li><a href="<c:url value="/board" /> ">Board</a></li>
                    <li><a href="<c:url value="/reqtrip" /> ">Choose trip</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_MANAGER')">
                    <li><a href="<c:url value="/train" /> ">Trains</a></li>
                    <li><a href="<c:url value="/station" /> ">Stations</a></li>
                    <li><a href="<c:url value="/path" /> ">Paths</a></li>
                    <li><a href="<c:url value="/trip" /> ">Trips</a></li>
                    <li><a href="<c:url value="/passenger" /> ">Passenger</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<c:url value="/user" /> ">Users</a></li>
                </sec:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('ROLE_GUEST')">
                    <li><a href="<c:url value="/register" /> ">Register</a></li>
                    <li><a href="<c:url value="/login" /> ">Log in</a></li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <span
                                class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="disabled"><a><sec:authentication property="principal.username"/></a></li>
                            <li><a href="<c:url value="/settings" /> ">Settings</a></li>
                            <sec:authorize access="hasRole('ROLE_USER')">
                                <li><a href="#">My tickets</a></li>
                            </sec:authorize>
                            <li class="divider"></li>
                            <li><a href="<c:url value="/logout" />">Log out</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript">

</script>