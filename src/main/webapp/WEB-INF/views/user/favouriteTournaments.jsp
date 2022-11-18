<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>My favourite tournaments</title>
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of tournaments</title>
</head>
<body>
<h1>Favourite tournaments</h1>
<table border="1">
    <thead>
    <tr>
        <%--        <th>Id</th>--%>
        <th>Name</th>
        <th>Type</th>
        <th>Speed</th>
        <th>Buy In</th>
<%--        <th>Rebuy</th>--%>
        <%--        <th>Starting Time</th>--%>
<%--        <th>Handed</th>--%>
<%--        <th>Edit</th>--%>
<%--        <th>Delete</th>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tournamentSlimDtoList}" var="tournament" varStatus="loop">
        <tr>
                <%--            <td>${tournament.id}</td>--%>
            <td>${tournament.name}</td>
            <td>${tournament.type}</td>
            <td>${tournament.speed}</td>
            <td>${tournament.buyIn}</td>
<%--            <td>${tournament.reBuy}</td>--%>
                <%--            <td>${tournament.tournamentStartDateTime}</td>--%>
<%--            <td>${tournament.handed}</td>--%>
<%--            <td><a href="<c:url value='/tournament/edit/${tournament.id}'/>">Edit</a></td>--%>
<%--            <td>--%>
<%--                <label onclick="return confirm('Are You sure to delete?')">--%>
<%--                    <a href="<c:url value='/tournament/del/${tournament.id}'/>">Delete</a></label>--%>
<%--            </td>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<h4><a href="<c:url value="/tournament/add"/>">Add new Tournament</a></h4>--%>
<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>

</body>
</html>
