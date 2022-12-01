<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of favourite tournamentGlobals</title>
</head>
<body>
<h1>Suggestion tournament List</h1>
<c:if test="${suggestionTournamentList.size()>0}">
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Rebuy</th>
            <th>Handed</th>
            <th>Delete from Suggestions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${suggestionTournamentList}" var="suggestionTournament" varStatus="loop">
            <tr>

                <td>${suggestionTournament.name}</td>
                <td>${suggestionTournament.type}</td>
                <td>${suggestionTournament.speed}</td>
                <td>${suggestionTournament.buyIn}</td>
                <td>${suggestionTournament.reBuy}</td>
                <td>${suggestionTournament.handed}</td>
                <td>
                    <label onclick="return confirm('Are You sure to delete from suggestions?')">
                        <a href="<c:url value='/app/tournaments/suggest/delete/${suggestionTournament.id}'/>">Delete</a></label>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</c:if>
<c:if test="${empty suggestionTournamentList}">
    <p>You don't have Suggestion tournaments</p>
</c:if>
<h4><a href="<c:url value="/app/tournaments/suggest/add"/>">Suggest a tournament for moderators</a></h4>
<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
