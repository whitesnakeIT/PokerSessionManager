<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>


</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand text-danger"
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    href="<c:url value='/admin/dashboard'/>">
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    href="<c:url value='/app/dashboard'/>">
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    href="<c:url value='/login'/>">
                </sec:authorize>
                Poker Session Manager</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class=" navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active text-danger" aria-current="page"
                       href="<c:url value='/app/user/show-details'/>">My account</a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="<c:url value='/admin/users/all'/>">All users</a>
                    </li>
                </sec:authorize>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Tournaments
                    </a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item text-danger" href="<c:url value='/tournament/add'/>">Add
                                tournament</a></li>
<%--                            <li><a class="dropdown-item text-danger" href="<c:url value='/admin/suggest'/>">Accept--%>
<%--                                suggestion tournament</a></li>--%>
<%--                            <li>--%>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/tournament/all'/>">Tournament
                                list</a></li>
                        </ul>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/tournament/local/add'/>">Add
                                tournament to local list</a></li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/tournament/suggest/add'/>">Suggest tournament for moderators</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/tournament/favourites'/>">Favourite tournaments</a></li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/tournament/local/all'/>">Tournament local
                                list</a></li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/tournament/suggest/all'/>">Tournament suggestions
                                list</a></li>
                        </ul>

                    </sec:authorize>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Poker rooms
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item text-danger" href="<c:url value='/poker_room/add'/>">Add poker
                            room</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item text-danger" href="<c:url value='/poker_room/all'/>">Poker room
                            list</a></li>
                    </ul>
                </li>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-danger" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Sessions
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/session/add'/>">Add
                                session</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/app/session/all'/>">Session
                                list</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
            <ul>

            </ul>
            <sec:authorize access="isAuthenticated()">
                <label onclick="return confirm('Are You sure to logout?')">
                    <a class="btn btn-outline-danger" href="<c:url value='/logout'/>">Logout</a>
                </label>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <a class="btn btn-outline-danger" href="<c:url value='/login'/>">Login</a>
            </sec:authorize>
        </div>
    </div>
</nav>
<div class="bg-black" style="background:#C0C0C0; margin-top:15%">