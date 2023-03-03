<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../fragments/header.jsp"/>
<title>Session form</title>
<jsp:include page="../fragments/navbar.jsp"/>
<div class="bg-danger d-flex justify-content-center">
    <form:form method="post" modelAttribute="session">
        Session name<br>
        <label>
            <form:input path="name"/><br>
            <form:errors path="name" cssClass="text-white"/><br>
        </label><br>
        Add tournaments to Session<br>
        <label>
            <form:select path="sessionTournaments">
                <form:options items="${availableSessionTournaments}" itemValue="id" itemLabel="concatFields"/>
            </form:select><br>
            <form:errors path="sessionTournaments" cssClass="text-white"/><br>

        </label><br>

        <input class="bg-black btn btn-outline-danger" type="submit" value="Save">
    </form:form>
</div>
<jsp:include page="../fragments/footer.jsp"/>