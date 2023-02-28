<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Users list</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="table-responsive bg-danger">
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
            <th scope="col">More info</th>
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
                    <a class="text-white" href="<c:url value='/admin/users/delete/${user.id}'/>">Delete</a></label>
                </td>
                <td><a class="text-white" href="<c:url value='/admin/users/details/${user.id}'/>">More info</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../fragments/footer.jsp"/>