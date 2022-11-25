<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of tournaments</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Type</th>
        <th>Speed</th>
        <th>Buy In</th>
        <th>Rebuy</th>
        <%--            not necessary to see--%>
        <%--        <th>Starting Time</th>--%>
        <th>Handed</th>
        <sec:authorize access="hasRole('ADMIN')">
            <th>Edit</th>
            <th>Delete</th>
        </sec:authorize>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tournamentList}" var="tournament" varStatus="loop">
        <tr>
            <td>${tournament.id}</td>
            <td>${tournament.name}</td>
            <td>${tournament.type}</td>
            <td>${tournament.speed}</td>
            <td>${tournament.buyIn}</td>
            <td>${tournament.reBuy}</td>
                <%--            not necessary to see--%>
                <%--            <td>${tournament.tournamentStartDateTime}</td>--%>
            <td>${tournament.handed}</td>
            <sec:authorize access="hasRole('ADMIN')">
                <td><a href="<c:url value='/tournament/edit/${tournament.id}'/>">Edit</a></td>
                <td>
                    <label onclick="return confirm('Are You sure to delete?')">
                        <a href="<c:url value='/tournament/del/${tournament.id}'/>">Delete</a></label>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<sec:authorize access="isAuthenticated()">--%>
<%--    <sec:authorize access="hasRole('USER')">--%>
        <h4><a href="<c:url value="/app/tournament/suggest"/>">Add Tournament for Suggestions</a></h4>
<%--    </sec:authorize>--%>
<%--    <sec:authorize access="hasRole('ADMIN')">--%>
        <h4><a href="<c:url value="/tournament/add"/>">Add new Global Tournament</a></h4>
<%--    </sec:authorize>--%>
    <h4><a href="<c:url value="/"/>">Main menu</a></h4>
<%--</sec:authorize>--%>
</body>
</html>
