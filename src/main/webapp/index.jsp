<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Poker Session Manager</title>
</head>
<body>
<h1>Test Index</h1>
<h3>Tournament</h3>
<h4><a href="<c:url value="/tournament/add"/>">Add new tournament</a></h4>
<h4><a href="<c:url value="/tournament/all"/>">All tournaments</a></h4>

<h3>user</h3>
<h4><a href="<c:url value="/user/add"/>">Add new user</a> </h4>
<h4><a href="<c:url value="/user/all"/>">All users</a> </h4>

</body>
</html>
