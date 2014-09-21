<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tr id="${user.id}">
    <td class="id">${user.id}</td>
    <td class="login">${user.login}</td>
    <td class="role">${user.role.title}</td>
    <td>
        <button type="button" class="btn btn-warning" onclick="changePassword(${user.id})">Change password</button>
    </td>
    <td>
        <button type="button" class="btn btn-danger" onclick="confirmDelete(${user.id}, '${user.login}');">Delete</button>
    </td>
</tr>