<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of favourite tournaments</title>
</head>
<body>
<h1>Favourite tournaments</h1>
<c:if test="${favouriteTournaments.size()>0}">
<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Speed</th>
        <th>Buy In</th>
        <th>Delete from favourites</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${favouriteTournaments}" var="suggestionTournament" varStatus="loop">
        <tr>

            <td>${suggestionTournament.name}</td>
            <td>${suggestionTournament.type}</td>
            <td>${suggestionTournament.speed}</td>
            <td>${suggestionTournament.buyIn}</td>
            <td>
                <label onclick="return confirm('Are You sure to delete from favourites?')">
                    <a href="<c:url value='/app/tournaments/favourite/delete/${suggestionTournament.id}'/>">Delete</a></label>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</c:if>
<c:if test="${empty favouriteTournaments}">
    <p>You don't have favourite tournament</p>
</c:if>
<h1>All tournament</h1>

<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Speed</th>
        <th>Buy In</th>
        <th>Add to favourite</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tournamentsPossibleToFavourites}" var="tournamentPossibleToFavourites" varStatus="loop">
        <tr>
            <td>${tournamentPossibleToFavourites.name}</td>
            <td>${tournamentPossibleToFavourites.type}</td>
            <td>${tournamentPossibleToFavourites.speed}</td>
            <td>${tournamentPossibleToFavourites.buyIn}</td>
            <td><a href="<c:url value='/app/tournaments/favourite/add/${tournamentPossibleToFavourites.id}'/>">Add</a>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
