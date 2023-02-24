<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../fragments/header.jsp"/>
<title>Favourite tournament list</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="table-responsive bg-danger">
    <h1>Favourite tournaments</h1>
    <c:if test="${favouriteTournaments.size()>0}">
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Speed</th>
                <th>Buy In</th>
                <th>Delete from favourites</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${favouriteTournaments}" var="favouriteTournament" varStatus="loop">
                <tr>

                    <td>${favouriteTournament.name}</td>
                    <td>${favouriteTournament.type}</td>
                    <td>${favouriteTournament.speed}</td>
                    <td>${favouriteTournament.buyIn}</td>
                    <td>
                        <label onclick="return confirm('Are You sure to delete from favourites?')">
                            <a class="text-white" href="<c:url value='/app/tournament/favourites/delete/${favouriteTournament.id}'/>">Delete</a></label>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </c:if>
</div>
    <c:if test="${empty favouriteTournaments}">
        <h3 class="text-white d-flex justify-content-center mt-5">You don't have favourite tournament</h3>
    </c:if>
    <div class="table-responsive bg-danger mt-5">
    <h1>Available tournaments</h1>
<c:if test="${tournamentsPossibleToFavourites.size()>0}">
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Add to favourite</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tournamentsPossibleToFavourites}" var="tournamentPossibleToFavourites" varStatus="loop">
            <tr>
                <td>${tournamentPossibleToFavourites.name}</td>
                <td>${tournamentPossibleToFavourites.type}</td>
                <td>${tournamentPossibleToFavourites.speed}</td>
                <td>${tournamentPossibleToFavourites.buyIn}</td>
                <td>
                    <a class="text-white" href="<c:url value='/app/tournament/favourites/add/${tournamentPossibleToFavourites.id}'/>">Add</a>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</div>

<c:if test="${empty tournamentsPossibleToFavourites}">
    <h3 class="text-white d-flex justify-content-center mt-5">List of possible tournaments to add is empty</h3>
</c:if>

<jsp:include page="../fragments/footer.jsp"/>
