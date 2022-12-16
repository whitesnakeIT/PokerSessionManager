<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Tournament list</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="table-responsive bg-danger">
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Speed</th>
            <th>Buy In</th>
            <th>Rebuy</th>
            <th>Handed</th>
            <sec:authorize access="hasRole('ADMIN')">
                <th>Edit</th>
                <th>Delete</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tournamentGlobalList}" var="favouriteTournament" varStatus="loop">
            <tr>
                <td>${favouriteTournament.name}</td>
                <td>${favouriteTournament.type}</td>
                <td>${favouriteTournament.speed}</td>
                <td>${favouriteTournament.buyIn}</td>
                <td>${favouriteTournament.reBuy}</td>
                <td>${favouriteTournament.handed}</td>
                <sec:authorize access="hasRole('ADMIN')">

                    <td><a href="<c:url value='/tournament/edit/${favouriteTournament.id}'/>">Edit</a></td>
                    <td>
                        <label onclick="return confirm('Are You sure to delete?')">
                            <a href="<c:url value='/tournament/del/${favouriteTournament.id}'/>">Delete</a></label>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<sec:authorize access="hasRole('USER')">
    <div class="d-flex align-items-center justify-content-center py-3">
        <a class="bg-black btn btn-outline-danger" href="<c:url value="/tournament/add"/>">Add new Tournament</a>
    </div>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <div class="d-flex align-items-center justify-content-center py-3">
        <a class="bg-black btn btn-outline-danger" href="<c:url value="/tournament/add"/>">Add new Tournament</a>
    </div>
</sec:authorize>


<jsp:include page="../fragments/footer.jsp"/>