<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../../fragments/header.jsp"/>
<title>Registration page</title>
<jsp:include page="../../fragments/navbar.jsp"/>

<div class="d-flex justify-content-center bg-danger">
    <form:form modelAttribute="newPlayer" method="post">
        <label>
            First name <br>
            <form:input path="firstName"/><br>
            <form:errors path="firstName" cssClass="text-white"/>
        </label><br>
        <label>
            Last name <br>
            <form:input path="lastName"/><br>
            <form:errors path="lastName" cssClass="text-white"/>
        </label><br>
        <label>
            Date of birth <br>
            <form:input path="birthdayDate" type="date"/><br>
            <form:errors path="birthdayDate" cssClass="text-white"/>
        </label><br>
        <label>
            Email<br>
            <form:input path="email"/><br>
            <form:errors path="email" cssClass="text-white"/>
        </label><br>
        <label>
            Username <br>
            <form:input path="username"/><br>
            <form:errors path="username" cssClass="text-white"/>
        </label><br>
        <label>
            Password <br>
            <form:password path="password"/><br>
            <form:errors path="password" cssClass="text-white"/>
        </label><br>
        <label>
            Repeat Password <br>
            <input type="password" name="passwordCheck"><br>
            <c:if test="${isCorrectPass==false}">
                <h7 class="text-white">Passwords not match.</h7>
            </c:if>
        </label><br>
        <input class="mt-3" type="submit" value="Register">
    </form:form>
</div>

<jsp:include page="../../fragments/footer.jsp"/>