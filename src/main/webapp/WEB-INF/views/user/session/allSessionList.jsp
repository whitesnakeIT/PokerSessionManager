<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All sessions</title>
</head>
<body>
<c:if test="${allUserSessions.size()>0}">

<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Session Tournaments</th>
        <th>Count</th>
        <th>Session cost</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${allUserSessions}" var="session" varStatus="loop">
        <tr>
            <td>${session.id}</td>
            <td>${session.name}</td>
            <td>
                <c:forEach items="${session.sessionTournaments}" var="sessionTournament" varStatus="loop">
                            ${sessionTournament.concatFields}<br>
                </c:forEach>
            </td>
            <td>${session.tournamentCount}</td>
            <td>${session.totalCost}</td>
            <td><a href="<c:url value='/app/session/edit/${session.id}'/>">Edit</a></td>
            <td>
                <label onclick="return confirm('Are You sure to delete?')">
                    <a href="<c:url value='/app/session/delete/${session.id}'/>">Delete</a></label>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
<c:if test="${empty allUserSessions}">
    <p>You don't have any sessions.</p>
</c:if>
</body>
<h4><a href="<c:url value="/app/session/add"/>">Add new Session</a></h4>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>

</html>