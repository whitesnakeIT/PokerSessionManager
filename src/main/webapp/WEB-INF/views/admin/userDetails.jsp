<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Users list</title>
<jsp:include page="../fragments/navbar.jsp"/>
<c:url value="//tournament/" var="basicUrl"/>
<h1 class="d-flex justify-content-center text-white">${player.username}</h1>
<div>
    <div class="bg-danger my-4">
        <div class="table-responsive bg-danger">
            <h1>Local tournament List</h1>

            <c:if test="${player.localTournaments.size()>0}">
                <table class="table table-hover table-bordered table-striped">

                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Speed</th>
                        <th>Buy In</th>
                        <th>Rebuy</th>
                        <th>Handed</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${player.localTournaments}" var="tournament" varStatus="loop">
                        <tr>

                            <td>${tournament.id}</td>
                            <td>${tournament.name}</td>
                            <td>${tournament.type}</td>
                            <td>${tournament.speed}</td>
                            <td>${tournament.buyIn}</td>
                            <td>${tournament.reBuy}</td>
                            <td>${tournament.handed}</td>
                            <td>
                                <label onclick="return confirm('Are You sure to delete this tournament?')">
                                    <a class="text-white"
                                       href="<c:url value="/app/admin/users/details/${playerId}/tournament/delete/${tournament.id}"/>">Delete</a></label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </c:if>
        </div>
        <c:if test="${empty player.localTournaments}">
            <h3 class="text-white d-flex justify-content-center mt-5">Player don't have local tournaments</h3>
        </c:if>
    </div>
    <div class="bg-danger my-4">
        <div class="table-responsive bg-danger">
            <h1>Suggested tournament List</h1>

            <c:if test="${player.suggestedTournaments.size()>0}">
                <table class="table table-hover table-bordered table-striped">

                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Speed</th>
                        <th>Buy In</th>
                        <th>Rebuy</th>
                        <th>Handed</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${player.suggestedTournaments}" var="tournament" varStatus="loop">
                        <tr>

                            <td>${tournament.id}</td>
                            <td>${tournament.name}</td>
                            <td>${tournament.type}</td>
                            <td>${tournament.speed}</td>
                            <td>${tournament.buyIn}</td>
                            <td>${tournament.reBuy}</td>
                            <td>${tournament.handed}</td>
                            <td>
                                <label onclick="return confirm('Are You sure to delete this tournament?')">
                                    <a class="text-white" href="<c:url
                                        value="/app/admin/users/details/${playerId}/tournament/delete/${tournament.id}"/>">Delete</a>
                                </label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </c:if>
        </div>

        <c:if test="${empty player.suggestedTournaments}">
            <h3 class="text-white d-flex justify-content-center mt-5">Player don't have suggested tournaments</h3>
        </c:if>
    </div>
    <div class="bg-danger my-4">

        <h1>Session List</h1>
        <c:if test="${player.sessions.size()>0}">

            <div class="table-responsive bg-danger">
                <table class="table table-hover table-bordered table-striped">
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
                    <c:forEach items="${player.sessions}" var="session" varStatus="loop">
                        <tr>
                            <td>${session.id}</td>
                            <td>${session.name}</td>
                            <td>
                                <c:forEach items="${session.sessionTournaments}" var="sessionTournament"
                                           varStatus="loop">
                                    ${sessionTournament.concatFields}<br>
                                </c:forEach>
                            </td>
                            <td>${session.tournamentCount}</td>
                            <td>${session.totalCost}</td>
                            <td>
                                <a class="text-white"
<%--                                   href="<c:url value='/admin/users/details/${playerId}/session/edit/${session.id}'/>">--%>
                                   href="<c:url value='/app/session/edit/${session.id}'/>">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <label onclick="return confirm('Are You sure to delete?')">
                                    <a class="text-white"
<%--                                       href="<c:url value='/admin/users/details/${playerId}/session/delete/${session.id}'/>">--%>
                                       href="<c:url value='/app/session/delete/${session.id}'/>">
                                        Delete
                                    </a>
                                </label>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${empty player.sessions}">
            <h3 class="text-white d-flex justify-content-center mt-5">Player don't have any sessions.</h3>
        </c:if>
    </div>

    <div class="table-responsive bg-danger">
        <h1>Poker room list</h1>
        <c:if test="${player.pokerRoomsLocal.size()>0}">
            <table class="table table-hover table-bordered table-striped">
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
                <c:forEach items="${player.pokerRoomsLocal}" var="pokerRoom" varStatus="loop">
                    <tr>
                        <td>${pokerRoom.id}</td>
                        <td>${pokerRoom.name}</td>
                        <td>${pokerRoom.url}</td>
                        <td>
                            <a class="text-white" href="<c:url value='/app/poker_room/edit/${pokerRoom.id}'/>">
                                    <%--                            <a class="text-white" href="<c:url value='/admin/users/details/${playerId}/poker_room/edit/${pokerRoom.id}'/>">--%>

                                Edit
                            </a>
                        </td>
                        <td>
                            <label onclick="return confirm('Are You sure to delete?')">
                                <a class="text-white"
                                   href="<c:url value='/app/poker_room/delete/${pokerRoom.id}'/>">
                                    Delete
                                </a>
                            </label>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty player.pokerRoomsLocal}">
            <h3 class="text-white d-flex justify-content-center mt-5">Player don't have any local Poker Rooms.</h3>
        </c:if>
    </div>
</div>
<jsp:include page="../fragments/footer.jsp"/>