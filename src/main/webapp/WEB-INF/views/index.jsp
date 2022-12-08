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
<h4><a href="<c:url value="/logout"/>">Logout</a> </h4>
<h4><a href="<c:url value="/login"/>">Login</a> </h4>
    <h4><a href="<c:url value="/poker_room/add"/>">Add Poker Room</a></h4>
    <h4><a href="<c:url value="/poker_room/all"/>">All Poker Rooms </a></h4>
    <h4><a href="<c:url value="/admin/dashboard"/>">Admin dashboard</a></h4>
<h3>User</h3>
<h4><a href="<c:url value="/app/dashboard"/>">User dashboard</a></h4>
<h4><a href="<c:url value="/app/tournament/local/add"/>">Add new tournament to My list</a></h4>
<h4><a href="<c:url value="/app/tournament/local/all"/>">All tournament in my list</a></h4>
<h4><a href="<c:url value="/app/tournament/suggest/add"/>">Suggest a tournament for moderators</a></h4>
<h4><a href="<c:url value="/app/tournament/suggest/all"/>">My suggested tournaments list</a></h4>
<h4><a href="<c:url value="/app/tournament/favourites"/>"> My favourite tournament</a></h4>
<h4><a href="<c:url value="/app/poker_room/add"/>">Add Poker Room Local</a></h4>
<h4><a href="<c:url value="/app/poker_room/all"/>">All Poker Rooms Local </a></h4>
<h4><a href="<c:url value="/app/session/add"/>">Add a session</a></h4>
<h4><a href="<c:url value="/app/session/all"/>">My all sessions</a></h4>
<h4><a href="<c:url value="/app/user/show-details"/>">My info</a></h4>
<h4><a href="<c:url value="/app/user/edit-details"/>">Edit my info</a></h4>
<h4><a href="<c:url value="/app/user/edit-password"/>">Edit password</a></h4>
<%--<h4><a href="<c:url value="/test/principal"/>">PRINCIPAL</a> </h4>--%>
<%--<h4><a href="<c:url value="/app/tournamentGlobals/session/all"/>">My all sessions</a></h4>--%>


<%--<h3>Annonymous</h3>--%>

<%--<h4><a href="<c:url value="/registration"/>">Add new user</a> </h4>--%>
<%--<h4><a href="<c:url value="tournament/all"/>">All tournament</a></h4>--%>

<h3>test</h3>
<h4><a href="<c:url value="/test/test"/>">TEST</a> </h4>

<%--<h4><a href="<c:url value="/security/"/>">Security</a> </h4>--%>

</body>
</html>
