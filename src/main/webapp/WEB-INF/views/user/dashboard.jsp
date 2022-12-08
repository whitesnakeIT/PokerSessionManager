<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
    <p>Zalogowany jako: <sec:authentication property="principal.username"/></p>
    <p>Posiada role: <sec:authentication property="authorities"/></p>
    <h4><a href="<c:url value="/app/tournament/local/add"/>">Add new tournament to My list</a></h4>
    <h4><a href="<c:url value="/app/tournament/local/all"/>">All tournament in my list</a></h4>
    <h4><a href="<c:url value="/app/tournament/suggest/add"/>">Suggest a tournament for moderators</a></h4>
    <h4><a href="<c:url value="/app/tournament/suggest/all"/>">My suggested tournaments list</a></h4>
    <h4><a href="<c:url value="/app/tournament/favourites"/>"> My favourite tournament</a></h4>
    <h4><a href="<c:url value="/poker_room/add"/>">Add Poker Room Local</a></h4>
    <h4><a href="<c:url value="/poker_room/all"/>">All Poker Rooms Local </a></h4>
    <h4><a href="<c:url value="/app/session/add"/>">Add a session</a></h4>
    <h4><a href="<c:url value="/app/session/all"/>">My all sessions</a></h4>
    <h4><a href="<c:url value="/app/user/show-details"/>">My info</a></h4>
    <h4><a href="<c:url value="/app/user/edit-details"/>">Edit my info</a></h4>
    <h4><a href="<c:url value="/app/user/edit-password"/>">Edit password</a></h4>
</body>
</html>
