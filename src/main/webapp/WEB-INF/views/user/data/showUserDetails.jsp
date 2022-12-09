<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>User details</title>
</head>
<body>
<h1>User info</h1>
<h4>First Name: <c:out value="${userBasicInfo.firstName}"/></h4>
<h4>Last Name: <c:out value="${userBasicInfo.lastName}"/></h4>
<h4>Email: <c:out value="${userBasicInfo.email}"/></h4>
<h4>-----------------------------------------</h4>
<h4>Tournament played: <c:out value="${userBasicInfo.playerStats.tournamentCount}"/></h4>
<h4>Average profit: <c:out value="${userBasicInfo.playerStats.averageProfit}"/></h4>
<h4>Profit: <c:out value="${userBasicInfo.playerStats.profit}"/></h4>
<h4>Tournament wins <c:out value="${userBasicInfo.playerStats.tournamentWins}"/></h4>

<h4><a href="<c:url value="/app/user/edit-details"/>">Edit details</a></h4>
</body>
</html>
