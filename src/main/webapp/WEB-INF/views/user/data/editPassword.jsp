<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../../fragments/header.jsp"/>
<title>Edit user password</title>
<jsp:include page="../../fragments/navbar.jsp"/>

<div class="d-flex justify-content-center bg-danger">
<form:form method="post">
    Old password<br>
    <label>
        <input type="password" name="oldPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label><br>
    New Password<br>
    <label>
        <input type="password" name="newPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label><br>
    Confirm new password<br>
    <label>
        <input type="password" name="confirmNewPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label><br>

    <input class="bg-black btn btn-outline-danger" type="submit" value="Edit password">
</form:form>
</div>
<jsp:include page="../../fragments/footer.jsp"/>