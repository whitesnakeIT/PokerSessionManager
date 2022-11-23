<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit user details</title>
</head>
<body>
<form:form method="post" modelAttribute="userBasicInfoEdit">
    <label>
        <form:input path="firstName"/>
        <form:errors path="firstName"/>
    </label>
    <label>
        <form:input path="lastName"/>
        <form:errors path="lastName"/>
    </label>
    <label>
        <form:input path="email"/>
        <form:errors path="email"/>
    </label>
    <input type="submit" value="Edit">
</form:form>
</body>
</html>
