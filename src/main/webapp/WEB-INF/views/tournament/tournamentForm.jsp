<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Tournament form</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="d-flex justify-content-center bg-danger">
<form:form method="post" modelAttribute="tournament">

    <label>
        Type
        <form:select path="type">
            <form:option value="" label="---Choose type---"/>
            <form:options items="${availableTournamentTypes}"/>
        </form:select><br>
        <form:errors path="type" cssClass="text-white"/>
    </label><br>
    <label>
        Name
        <form:input path="name"/><br>
        <form:errors path="name" cssClass="text-white"/>
    </label><br>
    <label>
        Speed
        <form:select path="speed">
            <form:option value="" label="---Choose speed---"/>
            <form:options items="${availableTournamentSpeed}"/>
        </form:select><br>
        <form:errors path="speed" cssClass="text-white"/>
    </label><br>
    <label>
        Buy-in
        <form:input path="buyIn" type="number" step="0.01"/><br>
        <form:errors path="buyIn" cssClass="text-white"/>
    </label><br>
    <label>
        Re-buy
        <form:checkbox path="reBuy"/><br>
        <form:errors path="reBuy" cssClass="text-white"/>
    </label><br>
    <label>
        Handed
        <form:input path="handed" type="number" step="1"/><br>
        <form:errors path="handed" cssClass="text-white"/>
    </label><br>
    <label>
        Poker Room
        <form:select path="pokerRoom">
            <form:option value="" label="---Choose Poker Room--"/>
            <form:options items="${availablePokerRooms}" itemLabel="name" itemValue="id"/>
        </form:select><br>
        <form:errors path="pokerRoom" cssClass="text-white"/>
    </label><br>

    <input class="bg-black btn btn-outline-danger" type="submit" value="Save"><br>
</form:form>
</div>

<jsp:include page="../fragments/footer.jsp"/>