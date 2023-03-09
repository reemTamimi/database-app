<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Judge Page</title>
</head>
<body>

<div align="center">

	<a href="login.jsp" target="_self" > logout</a><br><br> 

<h1>List of contests</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Title</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Requirements</th>
                <th>Status</th>
                <th>Reward</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="contests" items="${listContest}">
                <tr style="text-align:center">
                    <td><c:out value="${contests.getTitle()}" /></td>
                    <td><c:out value="${contests.getStartDate()}" /></td>
                    <td><c:out value="${contests.getEndDate()}" /></td>
                    <td><c:out value="${contests.getRequirements()}" /></td>
                    <td><c:out value="${contests.getStatus()}" /></td>
                    <td><c:out value="${contests.getFee()}" /></td>
                    <td><c:if test="${contests.getStatus()=='opened'}"><input type = "file"/></c:if></td>
                    <td><c:if test="${contests.getStatus()=='opened'}"><input type = "submit" value = "Submit!"/></c:if></td>
            </c:forEach>
        </table>
	</div>
	</div>

</body>
</html>