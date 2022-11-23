<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration for new users</title>
</head>
<body>
<form:form modelAttribute="newUser" method="post">
    <label>
        First name <br>
        <form:input path="firstName"/><br>
    </label>
    <form:errors path="firstName"/><br>
    <label>
        Last name <br>
        <form:input path="lastName"/><br>
    </label>
    <form:errors path="lastName"/><br>
    <label>
        Date of birth <br>
        <input type="date" name="birthdayDate"/><br>
    </label>
        <form:errors path="birthdayDate"/><br>
    <label>
        Email<br>
        <form:input path="email"/><br>
    </label>
    <form:errors path="email"/><br>
    <label>
        Password <br>
        <form:input path="password"/><br>
    </label>
    <form:errors path="password"/><br>

    <input type="submit" value="Register">

</form:form>
</body>
</html>
