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
                <th>Delete from favourites</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${favouriteTournaments}" var="tournament" varStatus="loop">
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
                            <td>
                                <label onclick="return confirm('Are You sure to delete from favourites?')">
                                    <a href="<c:url value='/user/favourite/${userId}/${tournament.id}/delete'/>">Delete</a></label>
                            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<h1>All tournaments</h1>

<%--<form method="post" action="<c:url value="/user/favourite/${userId}/add"/>">--%>
    <table border="1">
        <thead>
        <tr>
            <%--        <th>Id</th>--%>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Add to favourite</th>
            <%--        <th>Rebuy</th>--%>
            <%--        <th>Starting Time</th>--%>
            <%--        <th>Handed</th>--%>
            <%--        <th>Edit</th>--%>
            <%--        <th>Delete</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tournamentsPossibleToFavourites}" var="tournamentPossibleToFavourites" varStatus="loop">
            <tr>
                    <%--            <td>${tournament.id}</td>--%>
                <td>${tournamentPossibleToFavourites.name}</td>
                <td>${tournamentPossibleToFavourites.type}</td>
                <td>${tournamentPossibleToFavourites.speed}</td>
                <td>${tournamentPossibleToFavourites.buyIn}</td>
<%--                <td>--%>
<%--                    <label>--%>
<%--                        <input type="checkbox" value="${nonFavouriteTournament.id}"--%>
<%--                               name="${nonFavouriteTournament.name}">--%>
<%--                    </label>--%>
<%--                </td>--%>
                    <%--            <td>${tournament.reBuy}</td>--%>
                    <%--            <td>${tournament.tournamentStartDateTime}</td>--%>
                    <%--            <td>${tournament.handed}</td>--%>
                    <%--            <td><a href="<c:url value='/tournament/edit/${tournament.id}'/>">Edit</a></td>--%>
                    <%--            <td>--%>
                                 <td><a href="<c:url value='/user/favourite/${userId}/${tournamentPossibleToFavourites.id}/add'/>">Add</a></td>
<%--                                <td>--%>
                    <%--                <label onclick="return confirm('Are You sure to delete?')">--%>
                    <%--                    <a href="<c:url value='/tournament/del/${tournament.id}'/>">Delete</a></label>--%>
                    <%--            </td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<%--    <input type="submit" value="Add to favourites">--%>
<%--</form>--%>
<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
