<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../fragmentss/header.jsp"/>
<title>Users list</title>
<jsp:include page="../fragmentss/navbar.jsp"/>
<div class="table-responsive">
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Birthday Date</th>
            <th scope="col">Created</th>
            <th scope="col">Email</th>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
            <th scope="col">Password</th>
            <th scope="col">Delete</th>
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
</div>

<jsp:include page="../fragmentss/footer.jsp"/>