<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="../fragments/header.jsp"/>
<title>Login page</title>
<jsp:include page="../fragments/navbar.jsp"/>

<div class="bg-danger d-flex justify-content-center ">
<form method="post">
    <div>
        <label>
            Email<br>
            <input type="text" name="email" placeholder="Login"><br>
        </label>
    </div>
    <div>
        <label>
            Password<br>
            <input type="password" name="password" placeholder="Password"><br>
        </label>
    </div>
    <div>
        <input class="bg-black btn btn-outline-danger" type="submit" value="Sign in">
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</div>
<jsp:include page="../fragments/footer.jsp"/>
