<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit password</title>
</head>
<body>
<form:form method="post">
    Old password<br>
    <label>
        <input type="password" name="oldPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label>
    New Password<br>
    <label>
        <input type="password" name="newPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label>
    Confirm new password<br>
    <label>
        <input type="password" name="confirmNewPassword"/><br>
            <%--        <form:input path="password"/>--%>
            <%--        <form:errors path="password"/>--%>
    </label>

    <input type="submit" value="Edit password">
</form:form>
</body>
</html>
