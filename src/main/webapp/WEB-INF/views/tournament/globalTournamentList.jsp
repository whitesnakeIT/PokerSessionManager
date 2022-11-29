<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of tournamentGlobals</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Speed</th>
        <th>Buy In</th>
        <th>Rebuy</th>
        <th>Handed</th>
        <sec:authorize access="hasRole('ADMIN')">
            <th>Edit</th>
            <th>Delete</th>
        </sec:authorize>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tournamentGlobalList}" var="suggestionTournament" varStatus="loop">
        <tr>
            <td>${suggestionTournament.name}</td>
            <td>${suggestionTournament.type}</td>
            <td>${suggestionTournament.speed}</td>
            <td>${suggestionTournament.buyIn}</td>
            <td>${suggestionTournament.reBuy}</td>
            <td>${suggestionTournament.handed}</td>
            <sec:authorize access="hasRole('ADMIN')">

                <td><a href="<c:url value='/tournament/edit/${suggestionTournament.id}'/>">Edit</a></td>
                <td>
                    <label onclick="return confirm('Are You sure to delete?')">
                        <a href="<c:url value='/tournament/del/${suggestionTournament.id}'/>">Delete</a></label>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>

<sec:authorize access="hasRole('USER')">
    <h4><a href="<c:url value="/app/tournaments/suggest/add"/>">Add Tournament for Suggestions</a></h4>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <h4><a href="<c:url value="/tournament/add"/>">Add new Global Tournament</a></h4>
</sec:authorize>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>

</body>
</html>
