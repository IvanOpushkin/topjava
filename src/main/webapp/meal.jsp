<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new user</title>
</head>
<body>
<script>
    $(function() {
        $('input[name=dob]').datepicker();
    });
</script>

<form method="POST" action='delete' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${param.mealId}" />" /> <br />
    <%--   LocalDateTime : <input
          type="text" name="dateTime"
          value="<<fmt:formatDate pattern="YYYY/MM/DD/HH/MM" value="${param.dateTime}" />" /> <br /> --%>
     Description : <input
          type="text" name="description"
          value="<c:out value="${param.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${param.calories}"/>" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>

