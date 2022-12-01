<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<sec:authorize access="hasRole('USER')">
    <p>Zalogowany jako: <sec:authentication property="principal.username"/></p>
    <p>Posiada role: <sec:authentication property="authorities"/></p>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <c:redirect url="/"/>
</sec:authorize>
</body>
</html>
