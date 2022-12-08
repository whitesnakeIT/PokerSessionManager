<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <c:url value="/app/tournament/" var="basicUrl"/>
    <c:choose>
        <c:when test="${tournamentGenus=='SUGGESTION'}">
            <c:set var="basicUrl" value="${basicUrl}suggest/"/>
        </c:when>

        <c:when test="${tournamentGenus=='LOCAL'}">
            <c:set var="basicUrl" value="${basicUrl}local/"/>
        </c:when>
    </c:choose>
    <title>List of <c:out value="${fn:toLowerCase(tournamentGenus)}"/> tournaments</title>
</head>
<body>
<h1><c:out value="${fn:toLowerCase(tournamentGenus)}"/> tournament List</h1>
<c:if test="${tournamentList.size()>0}">
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Rebuy</th>
            <th>Handed</th>
            <th>Delete from <c:out value="${fn:toLowerCase(tournamentGenus)}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tournamentList}" var="tournament" varStatus="loop">
            <tr>

                <td>${tournament.name}</td>
                <td>${tournament.type}</td>
                <td>${tournament.speed}</td>
                <td>${tournament.buyIn}</td>
                <td>${tournament.reBuy}</td>
                <td>${tournament.handed}</td>
                <td>
                    <label onclick="return confirm('Are You sure to delete from <c:out value="${fn:toLowerCase(tournamentGenus)}"/>?')">
                        <a href="<c:url value="${basicUrl}delete/${tournament.id}"/>">Delete</a></label>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</c:if>
<c:if test="${empty tournamentList}">
    <p>You don't have <c:out value="${fn:toLowerCase(tournamentGenus)}"/> tournaments</p>
</c:if>
<h4><a href="<c:url value="${basicUrl}add"/>">Add tournament to <c:out value="${fn:toLowerCase(tournamentGenus)}"/> list</a></h4>
<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
