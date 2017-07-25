
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


<%-- Выдаёт null почемуто насчёт - а потомучто в web.xml не был подключен сервлет Java
<%= request.getAttribute("mewiexList")  %>
волшебных знаков процента работы с кодом
<%=
request.getAttribute("mewiexList")
%>

  ${!empty requestScope.mewiexList}

  парам для одного элемента, requestScope для листа
--%>


<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>

<tr>
    <td width =200px>DateTime</td>
    <td width =200px>Description</td>
    <td width =200px>Calories</td>
    <td width =200px>Exceed</td>
</tr>


<c:if test = "${!empty requestScope.mewiexList}" var = "MealWithExceed">



    <c:forEach items = "${requestScope.mewiexList}" var = "MealWithExceed">
        <%--важное дополнение Items а не тэст
        2.var тип элемента а не пустота
        --%>

     <c:if test = "${MealWithExceed.exceed}">

         <tr style = "color:red;">

     </c:if>

        <c:if test = "${!MealWithExceed.exceed}">

            <tr style = "color:green;">

        </c:if>



        <%--'T'hh:mm
        <javatime:parseLocalDateTime value="${MealWithExceed.dateTime}" pattern="yyyy-MM-dd'T'hh:mm" var="parsedDate" />
        --%>

        <td width =200px>${MealWithExceed.formatLocalDateTime(MealWithExceed.dateTime)}</td>
        <td width =200px>${MealWithExceed.description}</td>
        <td width =200px>${MealWithExceed.calories}</td>
        <td width =200px>${MealWithExceed.exceed}</td>



    </tr>

    </c:forEach>


</c:if>

</table>



</body>
</html>
