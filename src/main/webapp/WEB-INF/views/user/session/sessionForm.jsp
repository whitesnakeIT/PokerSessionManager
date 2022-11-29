<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Session</title>
</head>
<body>
<form:form method="post" modelAttribute="session">
  Session name<br>
    <form:hidden path="id"/>
    <form:hidden path="user"/>
  <label>
<%--    <input type="text" name="name"/><br>--%>
              <form:input path="name"/><br>
              <form:errors path="name"/><br>
  </label>
Session tournaments<br>
  <label>
      content<br>
<%--    <input type="password" name="newPassword"/><br>--%>
      <%--        <form:input path="password"/>--%>
      <%--        <form:errors path="password"/>--%>
  </label>

  <input type="submit" value="Save">
</form:form>
</body>
</html>
