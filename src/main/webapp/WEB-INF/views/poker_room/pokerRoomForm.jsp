<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Poker Room Form</title>
</head>
<body>
<form:form method="post" modelAttribute="pokerRoom">
    Name<br>
    <label>
            <%--    <input type="text" name="name"/><br>--%>
        <form:input path="name"/><br>
        <form:errors path="name"/><br>
    </label>
    Url<br>
    <label>
        <form:input path="url"/>
        <form:errors path="url"/><br>
    </label>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
