<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${allPokerRooms}" var="pokerRoom" varStatus="loop">
        <tr>
            <td>${pokerRoom.id}</td>
            <td>${pokerRoom.name}</td>
            <td>${pokerRoom.url}</td>
            <td><a href="<c:url value='/poker_room/edit/${pokerRoom.id}'/>">Edit</a></td>
            <td>
                <label onclick="return confirm('Are You sure to delete?')">
                    <a href="<c:url value='/poker_room/delete/${pokerRoom.id}'/>">Delete</a></label>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h4><a href="<c:url value="/poker_room/add"/>">Add new PokerRoom</a></h4>

<h4><a href="<c:url value="/"/>">Main menu</a></h4>
</body>
</html>
