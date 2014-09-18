<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Main</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/train" /> ">Trains</a></li>
                <li><a href="<c:url value="/station" /> ">Stations</a></li>
                <li><a href="<c:url value="/path" /> ">Paths</a></li>
                <li><a href="<c:url value="/trip" /> ">Trips</a></li>
                <li><a href="<c:url value="/passenger" /> ">Passenger</a></li>
                <sec:authorize ifAllGranted="ROLE_ADMIN">
                    <li><a href="<c:url value="/passenger" /> ">Passenger</a></li>
                </sec:authorize>
                
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/SBB/logout">Log in</a></li>
                <li class="dropdown">
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

