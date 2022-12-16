<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
  <title>Admin Panel</title>
<jsp:include page="../fragments/navbar.jsp"/>
<h1 class="text-white d-flex justify-content-center">Permission: <sec:authentication
        property="principal.authorities"/></h1>
<jsp:include page="../fragments/footer.jsp"/>
