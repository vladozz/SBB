<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 19.09.2014
  Time: 1:07
  To change this template use File | Settings | File Templates.
--%>
<button type="button" class="btn btn-primary" id="addUserButton">Add new user</button>

<div class="jumbotron col-lg-12" id="addUserForm" style="display: none">
    <form class="form" role="form" method="post" action="<c:url value="/user/add"/>">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputLogin">Login</label>
            <input type="text" id="inputLogin"/>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputRole">Choose role</label>
            <select class="form-control" id="inputRole" name="role">
                <c:if test="${!empty roleList}">
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.id}">${role.title}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <button type="button" class="btn btn-default" onclick="addUser();">Add</button>
    </form>
</div>

<c:if test="${!empty userList}">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" align="center">List of users</div>
        <div class="table-responsive">
            <table class="table table-hover" id="listOfusers">
                <tr class="active">
                    <th>ID</th>
                    <th>Login</th>
                    <th>Role</th>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <tr id="${user.id}">
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="role">${user.role.title}</td>
                        <td>
                            <button type="button" class="btn btn-warning">Edit</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>

<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-dialog.js'/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#addUserButton').click(function () {
            $('#addUserForm').slideToggle('slow');
        });
    });

    function addUser() {
        var login = $('#inputLogin').val();
        var role = $('#inputRole').find('option:selected').val();

        $.post('/SBB/user/add',
                {login: login, roleId: role},
                function (response) {
                    BootstrapDialog.alert(response);
                }
        );
    }
</script>