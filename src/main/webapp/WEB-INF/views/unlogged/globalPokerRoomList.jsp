<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>List of global Poker Rooms</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="table-responsive bg-danger">
    <h1>Global Poker rooms</h1>
<c:if test="${globalPokerRooms.size()>0}">
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Url</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${globalPokerRooms}" var="pokerRoom" varStatus="loop">
            <tr>
                <td>${pokerRoom.id}</td>
                <td>${pokerRoom.name}</td>
                <td>${pokerRoom.url}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</div>
<c:if test="${empty globalPokerRooms}">
    <h3 class="text-white d-flex justify-content-center mt-5">Global Poker Rooms List is empty</h3>
</c:if>
<jsp:include page="../fragments/footer.jsp"/>