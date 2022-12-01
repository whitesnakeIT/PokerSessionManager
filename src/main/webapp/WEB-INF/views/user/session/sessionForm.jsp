<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Session form</title>
</head>
<body>
<form:form method="post" modelAttribute="session">
    Session name<br>
    <label>
            <%--    <input type="text" name="name"/><br>--%>
        <form:input path="name"/><br>
        <form:errors path="name"/><br>
    </label>
    Add tournaments to Session<br>
    <label>
        <form:select path="sessionTournaments">
            <form:options items="${availableSessionTournaments}" itemValue="id" itemLabel="concatFields"/>
        </form:select><br>
        <form:errors path="sessionTournaments"/><br>

    </label>

    <input type="submit" value="Save">
</form:form>
</body>
</html>
