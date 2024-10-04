<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>SQL Gateway</title>
  <link rel="stylesheet" href="styles/main.css" type="text/css"/>
  <!-- Latest compiled and minified CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Latest compiled JavaScript -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <c:if test="${sqlStatement == null}">
    <c:set var="sqlStatement" value="select * from employee" />
  </c:if>

  <div class="container d-flex flex-column align-items-center">
    <div  class="row"></div>
    <h1 class="text-primary mt-3">The SQL Gateway</h1>
    <p>Enter an SQL statement and click the Execute button.</p>

    <p><b>SQL statement:</b></p>
    <form action="sqlGateway" method="post" class="form-floating d-flex flex-column align-items-center">
      <textarea name="sqlStatement" cols="60" rows="8" class="form-control p-3" style="height: 200px">${sqlStatement}</textarea>
      <input type="submit" value="Execute" style="width: 100%"  class="mt-3 btn btn-primary">
    </form>

    <p><b>SQL result:</b></p>
    ${sqlResult}
  </div>



</body>
</html>
