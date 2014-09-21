<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="form-group">
    <button type="button" class="btn btn-primary" id="addUserButton">Add new user</button>
</div>
<div class="jumbotron" id="addUserForm" style="display: none">
    <div class="container">
        <form class="form-horizontal col-sm-6" role="form" method="post" action="<c:url value="/user/add"/>">
            <div class="form-group">
                <label class="col-sm-3 control-label" for="inputLogin">Login</label>

                <div class="col-sm-9">
                    <input class="form-control" type="text" id="inputLogin"/>
                </div>
            </div>
            <div class="form-group" id="passwordFG">
                <label class="col-sm-3 control-label" for="inputPassword">Password</label>

                <div class="col-sm-9">
                    <input class="form-control" type="text" id="inputPassword"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label" for="inputRole">Role</label>

                <div class="col-sm-9">
                    <select class="form-control" id="inputRole" name="role">
                        <c:if test="${!empty roleList}">
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">${role.title}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="button" class="btn btn-default" onclick="addUser();">Add user</button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:if test="${!empty userList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of users</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfUsers">
                <tr class="active">
                    <th>ID</th>
                    <th>Login</th>
                    <th>Role</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <%@include file="admin/user_row.jsp" %>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>

<script type="text/javascript" src="<c:url value="/resources/js/user.js"/> "></script>