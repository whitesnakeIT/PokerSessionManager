<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Poker Room Form</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="bg-danger d-flex justify-content-center">
    <form:form method="post" modelAttribute="pokerRoomSlim">
        Name<br>
        <label>
            <form:input path="name"/><br>
            <form:errors path="name" cssClass="text-white"/>
        </label><br>
        Url<br>
        <label>
            <form:input path="url"/><br>
            <form:errors path="url" cssClass="text-white"/>
        </label><br>
        <input class="bg-black btn btn-outline-danger" type="submit" value="Save">
    </form:form>
</div>
<jsp:include page="../fragments/footer.jsp"/>