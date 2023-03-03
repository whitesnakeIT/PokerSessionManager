<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>List of Global tournaments</title>
<jsp:include page="../fragments/navbar.jsp"/>

<c:url value="/app/tournament/" var="basicUrl"/>

<div class="table-responsive bg-danger">
    <h1>Global tournament List</h1>

    <c:if test="${globalTournaments.size()>0}">
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
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${globalTournaments}" var="tournament" varStatus="loop">
                <tr>

                    <td>${tournament.id}</td>
                    <td>${tournament.name}</td>
                    <td>${tournament.type}</td>
                    <td>${tournament.speed}</td>
                    <td>${tournament.buyIn}</td>
                    <td>${tournament.reBuy}</td>
                    <td>${tournament.handed}</td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

    </c:if>
</div>
<c:if test="${empty globalTournaments}">
    <h3 class="text-white d-flex justify-content-center mt-5">Global Tournaments List is empty</h3>
</c:if>

<jsp:include page="../fragments/footer.jsp"/>
