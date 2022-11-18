<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tournament form</title>
</head>
<body>

<form:form method="post" modelAttribute="tournament">

    <label>
        Type
        <form:select path="type"><br>
            <form:option value="" label="---Choose type---"/>
            <form:options items="${availableTournamentTypes}"/>
        </form:select>
        <form:errors path="type"/>
    </label><br>
    <label>
        Name
        <form:input path="name"/><br>
        <form:errors path="name"/>
    </label><br>
    <label>
        Speed
        <form:select path="speed"><br>
            <form:option value="" label="---Choose speed---"/>
            <form:options items="${availableTournamentSpeed}"/>
        </form:select>
        <form:errors path="speed"/>
    </label><br>
    <label>
        Buy-in
        <form:input path="buyIn" type="number" step="0.01"/><br>
        <form:errors path="buyIn"/>
    </label><br>
    <label>
        Re-buy
        <form:checkbox path="reBuy"/><br>
        <form:errors path="reBuy"/>
    </label><br>
    <label>
        Handed
        <form:input path="handed" type="number" step="1"/><br>
        <form:errors path="handed"/>
    </label><br> <label>
    Starting Time
    <form:input path="tournamentStartDateTime" type="time"/><br>
    <form:errors path="tournamentStartDateTime"/>
</label><br>

    <input type="submit" value="Save"><br>
</form:form>
<h4><a href="<c:url value="/tournament/all"/>">List of Tournaments</a></h4>
</body>
</html>
