<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>List of <c:out value="${fn:toLowerCase(tournament.tournamentScope)}"/> tournaments</title>
<jsp:include page="../fragments/navbar.jsp"/>

<c:url value="/app/tournament/" var="basicUrl"/>
<c:choose>
    <c:when test="${tournament.tournamentScope.equals('SUGGESTION')}">
        <c:set var="basicUrl" value="${basicUrl}${fn:toLowerCase(tournament.tournamentScope)}/"/>
    </c:when>

    <c:when test="${tournament.tournamentScope.equals('LOCAL')}">
        <c:set var="basicUrl" value="${basicUrl}${fn:toLowerCase(tournament.tournamentScope)}/"/>
    </c:when>
    <c:when test="${tournament.tournamentScope.equals('GLOBAL')}">
        <c:set var="basicUrl" value="${basicUrl}${fn:toLowerCase(tournament.tournamentScope)}/"/>
    </c:when>
</c:choose>

<div class="table-responsive bg-danger">
    <h1><c:out value="${fn:toLowerCase(tournament.tournamentScope)}"/> tournament List</h1>

    <c:if test="${tournamentList.size()>0}">
        <table class="table table-hover table-bordered table-striped">

            <thead>
            <tr>
                <th>Id</th>
                <th>Scope</th>
                <th>Name</th>
                <th>Type</th>
                <th>Speed</th>
                <th>Buy In</th>
                <th>Rebuy</th>
                <th>Handed</th>
                <th>Delete from <c:out value="${fn:toLowerCase(tournament.tournamentScope)}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tournamentList}" var="tournament" varStatus="loop">
                <tr>

                    <td>${tournament.id}</td>
                    <td>${tournament.tournamentScope}</td>
                    <td>${tournament.name}</td>
                    <td>${tournament.type}</td>
                    <td>${tournament.speed}</td>
                    <td>${tournament.buyIn}</td>
                    <td>${tournament.reBuy}</td>
                    <td>${tournament.handed}</td>
                    <td>
                        <label onclick="return confirm('Are You sure to delete from <c:out
                                value="${fn:toLowerCase(tournament.tournamentScope)}"/>?')">
                            <a class="text-white" href="<c:url value="${basicUrl}${fn:toLowerCase(tournament.tournamentScope)}/delete/${tournament.id}"/>">Delete</a></label>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

    </c:if>
</div>
<c:if test="${empty tournamentList}">
    <h3 class="text-white d-flex justify-content-center mt-5">You don't have <c:out
            value="${fn:toLowerCase(tournament.tournamentScope)}"/> tournaments</h3>
</c:if>
<div class="d-flex align-items-center justify-content-center py-3">
    <a class="btn btn-outline-danger" href="<c:url value="${basicUrl}${fn:toLowerCase(tournament.tournamentScope)}/add"/>">Add tournament to <c:out
            value="${fn:toLowerCase(tournamentScopeFromUrl)}"/>
        list</a>
</div>
<jsp:include page="../fragments/footer.jsp"/>
