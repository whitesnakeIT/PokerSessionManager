<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>User Dashboard</title>
<jsp:include page="../fragments/navbar.jsp"/>
<div class="bg-danger">
    <h1 class="text-white d-flex justify-content-center">Permission: <sec:authentication
            property="principal.authorities"/></h1>
</div>
    <jsp:include page="../fragments/footer.jsp"/>
