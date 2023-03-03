<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>


</head>
<body>


<nav class="navbar navbar-expand-lg bg-dark">
    <div class="container-fluid">
        <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
        <sec:authorize access="hasRole('ROLE_USER')" var="isUser"/>
        <sec:authorize access="isAnonymous()" var="isAnonymous"/>
        <c:choose>
            <c:when test="${isAdmin}">
                <c:url var="dashboardUrl" value='/app/admin/dashboard'/>
            </c:when>
            <c:when test="${isUser}">
                <c:url var="dashboardUrl" value='/app/player/dashboard'/>
            </c:when>
            <c:when test="${isAnonymous}">
                <c:url var="dashboardUrl" value='/login'/>
            </c:when>
        </c:choose>
        <a class="navbar-brand text-danger" href="<c:url value="${dashboardUrl}"/>">
            Poker Session Manager
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class=" navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active text-danger" aria-current="page"
                       href="<c:url value='/app/user/show-details'/>">
                        My account
                    </a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<c:url value='/app/admin/users/all'/>">
                            All users
                        </a>
                    </li>
                </sec:authorize>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Tournaments
                    </a>
                    <ul class="dropdown-menu">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/tournament/global/add'/>">
                                    Add tournament
                                </a>
                            </li>
                            <%--                            <li><a class="dropdown-item text-danger" href="<c:url value='/admin/suggest'/>">Accept--%>
                            <%--                                suggestion tournament</a></li>--%>
                            <%--                            <li>--%>
                            <hr class="dropdown-divider">

                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/tournament/local/add'/>">
                                    Add tournament to local list
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item text-danger"
                                   href="<c:url value='/app/tournament/suggestion/add'/>">
                                    Suggest tournament for moderators
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/tournament/favourites'/>">
                                    Favourite tournaments
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/tournament/local/all'/>">
                                    Tournament local list
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item text-danger"
                                   href="<c:url value='/app/tournament/suggestion/all'/>">
                                    Tournament suggestions list
                                </a>
                            </li>

                        </sec:authorize>
                        <li>
                            <sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
                            <c:url var="globalTournamentUrl"
                                   value="${isAuthenticated ? '/app/tournament/global/all' : '/tournament/all'}"/>
                            <a class="dropdown-item text-danger" href="${globalTournamentUrl}">
                                Tournament global list
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Poker rooms
                    </a>
                    <ul class="dropdown-menu">
                        <sec:authorize access="isAuthenticated()">
                            <li>
                                <c:if test="${isAdmin}">
                                    <c:url var="pokerRoomScopeUrl" value="/app/poker_room/global/add"/>
                                </c:if>
                                <c:if test="${isUser}">
                                    <c:url var="pokerRoomScopeUrl" value="/app/poker_room/local/add"/>
                                </c:if>
                                <a class="dropdown-item text-danger" href="${pokerRoomScopeUrl}">
                                    Add poker room
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>

                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/poker_room/local/all'/>">
                                    Local poker rooms
                                </a>
                            </li>
                        </sec:authorize>
                        <li>
                            <c:url var="globalPokerRoomUrl"
                                   value="${isAuthenticated ? '/app/poker_room/global/all' : '/poker_room/all'}"/>
                            <a class="dropdown-item text-danger" href="<c:url value="${globalPokerRoomUrl}"/>">
                                Global poker rooms
                            </a>
                        </li>

                    </ul>
                </li>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Sessions
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/session/add'/>">
                                    Add session
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item text-danger" href="<c:url value='/app/session/all'/>">
                                    Session list
                                </a>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
            <ul>

            </ul>
            <sec:authorize access="isAuthenticated()">
                <label onclick="return confirm('Are You sure to logout?')">
                    <a class="btn btn-outline-danger" href="<c:url value='/logout'/>">
                        Logout
                    </a>
                </label>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <a class="btn btn-outline-danger" href="<c:url value='/login'/>">
                    Login
                </a>
            </sec:authorize>
        </div>
    </div>
</nav>
<div class="bg-black" style="background:#C0C0C0; margin-top:15%; padding-bottom: 140px">