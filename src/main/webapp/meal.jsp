<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language ="java"%>

<html>
<head>
</head>
<body>
<jsp:useBean id ="meal" type = "ru.javawebinar.topjava.model.Meal" scope = "request" />
    <section>
<form method="POST" action='meals'>
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />" /> <br />

    LocalDateTime : <input type="datetime-local"
          value="${meal.dateTime}" name ="dateTime"/> <br />

    Description : <input type="text" name="description"
          value="<c:out value="${meal.description}" />" /> <br />

    Calories : <input type="text" name="calories"
        value="<c:out value="${meal.calories}"/>" /> <br />

    <button type = "submit">Save</button>
    <button onclick ="windows.history.back()">Cancel</button>
    <!--<input type="submit" value="Submit" /> -->
</form>
    </section>
</body>
</html>



















    <%--
<script>
    $(function() {
        $('input[name=dob]').datepicker();
    });
</script>

    --%>

<!--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link type="text/css
href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
<title>Add new user</title>
</head>
<body>

-->