<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Poker Room List</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Url</th>
        <sec:authorize access="isAuthenticated()">
            <th>Edit</th>
            <th>Delete</th>
        </sec:authorize>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${allPokerRooms}" var="pokerRoom" varStatus="loop">
        <tr>
            <td>${pokerRoom.id}</td>
            <td>${pokerRoom.name}</td>
            <td>${pokerRoom.url}</td>
            <sec:authorize access="isAuthenticated()">
                <td><a href="<c:url value='/poker_room/edit/${pokerRoom.id}'/>">Edit</a></td>
                <td>
                    <label onclick="return confirm('Are You sure to delete?')">
                        <a href="<c:url value='/poker_room/delete/${pokerRoom.id}'/>">Delete</a></label>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%--<sec:authorize access="hasRole('USER')">--%>
<%--    <h4><a href="<c:url value="/app/poker_room/add"/>">Add Local Poker Room</a></h4>--%>
<%--</sec:authorize>--%>
<%--<sec:authorize access="hasRole('ADMIN')">--%>
    <h4><a href="<c:url value="/poker_room/add"/>">Add new Global Poker Room</a></h4>
<%--</sec:authorize>--%>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
