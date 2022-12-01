<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of favourite tournamentGlobals</title>
</head>
<body>
<h1>Local tournament List</h1>
<c:if test="${localTournamentList.size()>0}">
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Rebuy</th>
            <th>Handed</th>
            <th>Delete from Local</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${localTournamentList}" var="localTournament" varStatus="loop">
            <tr>

                <td>${localTournament.name}</td>
                <td>${localTournament.type}</td>
                <td>${localTournament.speed}</td>
                <td>${localTournament.buyIn}</td>
                <td>${localTournament.reBuy}</td>
                <td>${localTournament.handed}</td>
                <td>
                    <label onclick="return confirm('Are You sure to delete from local?')">
                        <a href="<c:url value='/app/tournaments/local/delete/${localTournament.id}'/>">Delete</a></label>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</c:if>
<c:if test="${empty localTournamentList}">
    <p>You don't have Local tournaments</p>
</c:if>
<h4><a href="<c:url value="/app/tournaments/local/add"/>">Add tournament to local list</a></h4>
<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
