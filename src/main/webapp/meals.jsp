
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%--
  Created by IntelliJ IDEA.
  User: Mega
  Date: 24.07.2017
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <style>


    </style>

    <title>Meals</title>
</head>
<body>

<p><a href="meals?action=create">Add Meal</a></p>





<h3><a href="index.jsp">Home</a></h3>
<h2>Meal List</h2>

<table>

<tr>
    <td width =200px style="text-align:center">ID</td>
    <td width =200px>DateTime</td>
    <td width =200px style="text-align:center">Description</td>
    <td width =200px>Calories</td>
    <th colspan=2>Action</th>
</tr>


<c:if test = "${!empty mealList}" var = "MealWithExceed">
    <c:forEach items = "${mealList}" var = "MealWithExceed">



        <c:if test = "${MealWithExceed.exceed}">

            <tr style = "color:red;">

        </c:if>

        <c:if test = "${!MealWithExceed.exceed}">

            <tr style = "color:green;">

        </c:if>

        <td width =200px>${MealWithExceed.id}</td>
        <!-- хз нужна ли тут синхронизация форматтера. По идее тысячи страниц если mb. По предохранению даты
        почему теряюца атрибуты п
        -->
        <td width =200px>${MealWithExceed.formatLocalDateTime(MealWithExceed.dateTime)}</td>
        <td width =200px style="text-align:center">${MealWithExceed.description}</td>
        <td width =200px>${MealWithExceed.calories}</td>
      <!-- си оут не только видимое, но и передаётся-->
        <td><a href="meals?action=edit&id=<c:out value="${MealWithExceed.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${MealWithExceed.id}"/>">Delete</a></td>



    </tr>

    </c:forEach>


</c:if>


</table>

</body>
</html>



<%--
  процентный закрывает всё. а не процентный только графический


  --%>

<%--'T'hh:mm
<javatime:parseLocalDateTime value="${MealWithExceed.dateTime}" pattern="yyyy-MM-dd'T'hh:mm" var="parsedDate" />
--%>



<!--
<%-- Выдаёт null почемуто насчёт - а потомучто в web.xml не был подключен сервлет Java
<%= request.getAttribute("mewiexList")  %>
волшебных знаков процента работы с кодом
<%=
request.getAttribute("mewiexList")
%>

  ${!empty requestScope.mewiexList}

  парам для одного элемента, requestScope для листа
--%>
