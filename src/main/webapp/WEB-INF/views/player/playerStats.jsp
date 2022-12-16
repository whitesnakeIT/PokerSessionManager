<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Player Stats</title>
<jsp:include page="../fragments/navbar.jsp"/>
<body>

<h4>Tournament played: <c:out value="${player.playerStats.tournamentCount}"/></h4>
<h4>Average profit: <c:out value="${player.playerStats.averageProfit}"/></h4>
<h4>Profit: <c:out value="${player.playerStats.profit}"/></h4>
<h4>Tournament wins <c:out value="${player.playerStats.tournamentWins}"/></h4>
<jsp:include page="../fragments/footer.jsp"/>