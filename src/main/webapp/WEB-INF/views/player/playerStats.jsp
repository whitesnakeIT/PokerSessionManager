<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Player Stats</title>
<jsp:include page="../fragments/navbar.jsp"/>
<body>

<div class="bg-danger">
    <h1 class="text-white d-flex justify-content-center">${player.fullName} stats:</h1>

    <ul class="list-group justify-content-center d-flex mt-3">
        <li class="list-group-item bg-danger justify-content-center d-flex">Tournament played: <c:out
                value="${player.playerStats.tournamentCount}"/></li>
        <li class="list-group-item bg-danger justify-content-center d-flex">Average profit: <c:out
                value="${player.playerStats.averageProfit}"/></li>
        <li class="list-group-item bg-danger justify-content-center d-flex">Profit: <c:out
                value="${player.playerStats.profit}"/></li>
           <li class="list-group-item bg-danger justify-content-center d-flex">Tournament wins: <c:out
                value="${player.playerStats.tournamentWins}"/></li>
    </ul>

<jsp:include page="../fragments/footer.jsp"/>