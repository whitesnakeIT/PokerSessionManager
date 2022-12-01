<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit user details</title>
</head>
<body>
<form:form method="post" modelAttribute="userBasicInfoEdit">
    First name<br>
    <label>
        <form:input path="firstName"/><br>
        <form:errors path="firstName"/><br>
    </label>
    Last name<br>
    <label>
        <form:input path="lastName"/><br>
        <form:errors path="lastName"/><br>
    </label>
    <%--        Username<br>--%>
    <%--    <label>--%>
    <%--        <form:input path="username"/><br>--%>
    <%--        <form:errors path="username"/><br>--%>
    <%--    </label>--%>
    Email<br>
    <label>
        <form:input path="email" readonly="true"/><br>
        <form:errors path="email"/><br>
    </label>
    <br>
    Enter the password to confirm<br>
    <label>
<%--        <form:input path="password" /><br>--%>
<%--        <form:errors path="password"/><br>--%>
    <input type="password" name="passwordToCheck"><br>
    </label>

    <input type="submit" value="Edit">
</form:form>
<h4><a href="<c:url value="/app/user/edit-password"/>">Edit password</a></h4>
</body>
</html>
