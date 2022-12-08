<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All Users List</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>ID</th>
        <th>Birthday Date</th>
        <th>Created</th>
        <th>Email</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Password</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.birthdayDate}</td>
            <td>${user.created}</td>
            <td>${user.email}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.password}</td>
            <td><label onclick="return confirm('Are You sure to delete this user?')">
                <a href="<c:url value='/admin/users/delete/${user.id}'/>">Delete</a></label>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
