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

<h1>List of submissions</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Contest Title</th>
                <th>Submission</th>
                <th>Requirements</th>
            </tr>
            <c:forEach var="submissions" items="${listSubmission}">
                <tr style="text-align:center">
                    <td><c:out value="${contests.getTitle()}" /></td>
                    <td><c:out value="${contests.getSubmission()}" /></td>
                    <td><c:out value="${contests.getEndDate()}" /></td>
            </c:forEach>
        </table>
	</div>
	</div>

</body>
</html>