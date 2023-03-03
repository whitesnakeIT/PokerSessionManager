<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<c:set var="pokerRoomScope" value="${fn:toLowerCase(pokerRoomScope) }"/>
<title>Poker Room List ${pokerRoomScope}</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="table-responsive bg-danger">
    <h1>Poker room list ${pokerRoomScope}</h1>
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Scope</th>
            <th>Url</th>
            <sec:authorize access="isAuthenticated()">
                <th>Edit</th>
                <th>Delete</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pokerRoomList}" var="pokerRoom" varStatus="loop">
            <tr>
                <td>${pokerRoom.id}</td>
                <td>${pokerRoom.name}</td>
                <td>${pokerRoom.pokerRoomScope.toString()}</td>
                <td>${pokerRoom.url}</td>
                <sec:authorize access="isAuthenticated()">
                    <td><a class="text-white" href="<c:url value='/app/poker_room/${pokerRoomScope}/edit/${pokerRoom.id}'/>">Edit</a></td>
                    <td>
                        <label onclick="return confirm('Are You sure to delete?')">
                            <a class="text-white" href="<c:url value='/app/poker_room/${pokerRoomScope}/delete/${pokerRoom.id}'/>">Delete</a></label>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<sec:authorize access="isAuthenticated()">
    <div class="d-flex align-items-center justify-content-center py-3">
        <sec:authorize access="hasRole('USER')" var="isUser"/>
        <c:if test="${isUser}" >
            <c:set var="pokerRoomScope" value="local"/>
        </c:if>
        <a class="bg-black btn btn-outline-danger" href="<c:url value="/app/poker_room/${pokerRoomScope}/add"/>">Add new Poker Room ${pokerRoomScope}</a>
    </div>
</sec:authorize>

<jsp:include page="../fragments/footer.jsp"/>