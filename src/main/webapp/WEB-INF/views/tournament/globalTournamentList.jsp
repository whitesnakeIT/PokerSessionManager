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
    <c:forEach items="${tournamentGlobalList}" var="favouriteTournament" varStatus="loop">
        <tr>
            <td>${favouriteTournament.name}</td>
            <td>${favouriteTournament.type}</td>
            <td>${favouriteTournament.speed}</td>
            <td>${favouriteTournament.buyIn}</td>
            <td>${favouriteTournament.reBuy}</td>
            <td>${favouriteTournament.handed}</td>
            <sec:authorize access="hasRole('ADMIN')">

                <td><a href="<c:url value='/tournament/edit/${favouriteTournament.id}'/>">Edit</a></td>
                <td>
                    <label onclick="return confirm('Are You sure to delete?')">
                        <a href="<c:url value='/tournament/del/${favouriteTournament.id}'/>">Delete</a></label>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>

<sec:authorize access="hasRole('USER')">
    <h4><a href="<c:url value="/app/tournament/suggest/add"/>">Add Tournament for Suggestions</a></h4>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <h4><a href="<c:url value="/tournament/add"/>">Add new Global Tournament</a></h4>
</sec:authorize>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>

</body>
</html>
