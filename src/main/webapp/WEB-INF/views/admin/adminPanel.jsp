<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>
<p>Zalogowany jako: <sec:authentication property="principal.username"/></p>
<p>Posiada role: <sec:authentication property="authorities"/></p>
<h4><a href="<c:url value="/admin/users/all"/>">All users</a> </h4>
<h4><a href="<c:url value="/poker_room/add"/>">Add Poker Room</a></h4>
<h4><a href="<c:url value="/poker_room/all"/>">All Poker Rooms </a></h4>
</body>
</html>

