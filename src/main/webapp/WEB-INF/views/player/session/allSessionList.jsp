<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../../fragments/header.jsp"/>
<title>Session list</title>
<jsp:include page="../../fragments/navbar.jsp"/>

<div class="bg-danger">
    <h1>Session List</h1>
    <c:if test="${allUserSessions.size()>0}">

        <div class="table-responsive bg-danger">
            <table class="table table-hover table-bordered table-striped">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Session Tournaments</th>
                    <th>Count</th>
                    <th>Session cost</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${allUserSessions}" var="session" varStatus="loop">
                    <tr>
                        <td>${session.id}</td>
                        <td>${session.name}</td>
                        <td>
                            <c:forEach items="${session.sessionTournaments}" var="sessionTournament" varStatus="loop">
                                ${sessionTournament.concatFields}<br>
                            </c:forEach>
                        </td>
                        <td>${session.tournamentCount}</td>
                        <td>${session.totalCost}</td>
                        <td><a class="text-white" href="<c:url value='/app/session/edit/${session.id}'/>">Edit</a></td>
                        <td>
                            <label onclick="return confirm('Are You sure to delete?')">
                                <a class="text-white"
                                   href="<c:url value='/app/session/delete/${session.id}'/>">Delete</a></label>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<c:if test="${empty allUserSessions}">
    <h3 class="text-white d-flex justify-content-center mt-5">You don't have any sessions.</h3>
</c:if>
<div class="d-flex align-items-center justify-content-center py-3">
    <a class="bg-black btn btn-outline-danger" href="<c:url value="/app/session/add"/>">Add new Session</a>
</div>
<jsp:include page="../../fragments/footer.jsp"/>
