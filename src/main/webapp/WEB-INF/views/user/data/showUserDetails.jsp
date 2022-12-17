<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../../fragments/header.jsp"/>
<title>User details</title>
<jsp:include page="../../fragments/navbar.jsp"/>
<div class="bg-danger">
    <h1 class="text-white d-flex justify-content-center">User info</h1>

    <ul class="list-group justify-content-center d-flex mt-3">
        <li class="list-group-item bg-danger justify-content-center d-flex">First Name: <c:out
                value="${userSlim.firstName}"/></li>
        <li class="list-group-item bg-danger justify-content-center d-flex">Last Name: <c:out
                value="${userSlim.lastName}"/></li>
        <li class="list-group-item bg-danger justify-content-center d-flex">Username: <c:out
                value="${userSlim.username}"/></li>
    </ul>

</div>
<div class="d-flex align-items-center justify-content-center py-3">
    <a class="btn btn-outline-danger" href="<c:url value="/app/user/edit-details"/>">Edit details</a>
</div>
<jsp:include page="../../fragments/footer.jsp"/>