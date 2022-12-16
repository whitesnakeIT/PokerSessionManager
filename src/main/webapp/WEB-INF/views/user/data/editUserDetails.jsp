<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../../fragments/header.jsp"/>
<title>Edit user details</title>
<jsp:include page="../../fragments/navbar.jsp"/>

<div class="d-flex justify-content-center bg-danger">
    <form:form method="post" modelAttribute="userBasicInfoEdit">
        First name<br>
        <label>
            <form:input path="firstName"/><br>
            <form:errors path="firstName"/><br>
        </label><br>
        Last name<br>
        <label>
            <form:input path="lastName"/><br>
            <form:errors path="lastName"/><br>
        </label><br>
        <%--        Username<br>--%>
        <%--    <label>--%>
        <%--        <form:input path="username"/><br>--%>
        <%--        <form:errors path="username"/><br>--%>
        <%--    </label>--%>
        Email<br>
        <label>
            <form:input path="email" readonly="true"/><br>
            <form:errors path="email"/><br>
        </label>
        <br>
        Enter the password to confirm<br>
        <label>
                <%--        <form:input path="password" /><br>--%>
                <%--        <form:errors path="password"/><br>--%>
            <input type="password" name="passwordToCheck"><br>
        </label><br>

        <input class="bg-black btn btn-outline-danger" type="submit" value="Edit details">
    </form:form>
</div>
<div class="d-flex align-items-center justify-content-center py-3">
    <a class="btn btn-outline-danger" href="<c:url value="/app/user/edit-password"/>">Edit password</a>
</div>
<jsp:include page="../../fragments/footer.jsp"/>
