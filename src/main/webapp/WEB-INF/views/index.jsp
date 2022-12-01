<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Poker Session Manager</title>
</head>
<body>
<h1>Test Index</h1>

<h3>Admin</h3>
<h4><a href="<c:url value="/admin/users/all"/>">All users</a> </h4>

<h3>User</h3>
<h4><a href="<c:url value="/app/tournaments/local/add"/>">Add new tournament to My list</a></h4>
<%--<h4><a href="<c:url value="/tournament/all"/>">All tournamentin Service</a></h4>--%>
<h4><a href="<c:url value="/app/tournaments/local/all"/>">All tournament in my list</a></h4>
<h4><a href="<c:url value="/app/tournaments/suggest/add"/>">Suggest a tournament for moderators</a></h4>
<h4><a href="<c:url value="/app/tournaments/suggest/all"/>">My suggested tournaments list</a></h4>
<h4><a href="<c:url value="/app/tournaments/favourite/all"/>"> My favourite tournament</a></h4>
<h4><a href="<c:url value="/app/user/session/add"/>">Add a session</a></h4>
<h4><a href="<c:url value="/app/user/session/all"/>">My all sessions</a></h4>
<h4><a href="<c:url value="/app/user/show-details"/>">My info</a></h4>
<h4><a href="<c:url value="/app/user/edit-details"/>">Edit my info</a></h4>
<h4><a href="<c:url value="/app/user/edit-password"/>">Edit password</a></h4>
<h4><a href="<c:url value="/logout"/>">Logout</a> </h4>
<%--<h4><a href="<c:url value="/test/principal"/>">PRINCIPAL</a> </h4>--%>
<%--<h4><a href="<c:url value="/app/tournamentGlobals/session/all"/>">My all sessions</a></h4>--%>


<%--<h3>Annonymous</h3>--%>

<%--<h4><a href="<c:url value="/registration"/>">Add new user</a> </h4>--%>
<%--<h4><a href="<c:url value="/login"/>">Login</a> </h4>--%>
<%--<h4><a href="<c:url value="tournament/all"/>">All tournament</a></h4>--%>

<h3>test</h3>
<h4><a href="<c:url value="/test/test"/>">Security</a> </h4>

<%--<h4><a href="<c:url value="/security/"/>">Security</a> </h4>--%>

</body>
</html>
