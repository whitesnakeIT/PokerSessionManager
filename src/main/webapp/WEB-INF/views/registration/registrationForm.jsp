<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Registration page</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="d-flex justify-content-center bg-danger">
<form:form modelAttribute="newPlayer" method="post">
    <label>
        First name <br>
        <form:input path="firstName"/><br>
    </label>
    <form:errors path="firstName"/><br>
    <label>
        Last name <br>
        <form:input path="lastName"/><br>
    </label>
    <form:errors path="lastName"/><br>
    <label>
        Date of birth <br>
        <form:input path="birthdayDate" type="date"/><br>
    </label>
    <form:errors path="birthdayDate"/><br>
    <label>
        Email<br>
        <form:input path="email"/><br>
    </label>
    <form:errors path="email"/><br>
    <label>
        Username <br>
        <form:input path="username"/><br>
        <form:errors path="username"/><br>
    </label><br>
    <label>
        Password <br>
        <form:password path="password"/><br>
        <form:errors path="password"/><br>
    </label><br>
    <label>
        Repeat Password <br>
        <input type="password" name="passwordCheck"><br>
    </label><br>
<c:if test="${isCorrectPass==false}">
    <h4>Second password wrong</h4>
</c:if>
    <input type="submit" value="Register">
</form:form>
</div>

<jsp:include page="../fragments/footer.jsp"/>