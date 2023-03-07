<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contestant page</title>
</head>
<body>

<div align = "center">
	
	<form action = "search">
		<input type = "text" value = ""/>
		<input type = "submit" value = "Search!"/>
	</form>
	<a href="login.jsp"target ="_self" > logout</a><br><br> 

<h1>List all users</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Contests</h2></caption>
            <tr>
                <th>Title</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Requirements</th>
                <th>Status</th>
                <th>Reward</th>
                <th> - - - - - </th>
            </tr>
            <c:forEach var="contests" items="${listContest}">
                <tr style="text-align:center">
                    <td><c:out value="${contests.getTitle()}" /></td>
                    <td><c:out value="${contests.getStart()}" /></td>
                    <td><c:out value="${contests.getEnd()}" /></td>
                    <td><c:out value="${contests.getRequirements()}" /></td>
                    <td><c:out value="${contests.getStatus()}" /></td>
                    <td><c:out value="${contests.getReward()}" /></td>
                    <td><input type = "submit" value = "Submit!"/></td>
            </c:forEach>
        </table>
	</div>
	</div>

</body>
</html>
