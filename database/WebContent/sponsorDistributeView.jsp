<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Distribute Contest Rewards</title>
</head>
<body>

<div align="center">

	<a href="login.jsp" target="_self" > logout</a><br><br> 

<h1>List of closed contests</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Contest Title</th>
                <th>Status</th>
                <th>Sponsor Fee</th>
                <th></th>
            </tr>
            <c:forEach var="c" items="${listClosedContest}">
                <tr style="text-align:center">
                    <td><c:out value="${c.getTitle()}" /></td>
                    <td><c:out value="${c.getSubmission()}" /></td>
                    <td><c:out value="${c.getFee()}" /></td>
                    <td><input type="submit" value="Distribute!"></td>
            </c:forEach>
        </table>
	</div>
	<br><br>
	<a href="sponsorView.jsp" target="_self" >Back to Actions</a><br><br>
	
	</div>

</body>
</html>