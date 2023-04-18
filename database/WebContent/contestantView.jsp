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


<div align="center">
<h1>You have \$${reward} in rewards!</h1>
	
	<form action = "contest_search">
		<input type="text" name="pattern" value=""/>
		<input type="submit" value="Search!"/>
	</form>
	<a href="login.jsp" target="_self" > logout</a><br><br> 

<h1>List of contests</h1>
<h3>Filter for single contest prior to submitting!</h3>
    <div align="center">
    	<form action="contestant_submission">
	        <table border="1" cellpadding="6">
	            <tr>
	            	<th style="display:none;">Contest Wallet</th>
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
	                	<td style="display:none;">><input type="text" name="contestWallet" value="${contests.getWallet()}"></td> <%--<c:out value="${contests.getWallet()}"/ --%>
	                    <td><c:out value="${contests.getTitle()}" /></td>
	                    <td><c:out value="${contests.getStartDate()}" /></td>
	                    <td><c:out value="${contests.getEndDate()}" /></td>
	                    <%-- <td><a href="resources/${contests.getRequirements()}" target="_blank">requirements</a></td> --%>
	                    <td><c:out value="${contests.getRequirements()}"/></td>
	                    <td><c:out value="${contests.getStatus()}" /></td>
	                    <td><c:out value="${contests.getFee()}" /></td>
	                    <%--  <td><c:if test="${contests.getStatus()=='opened'}"><input type = "file"/></c:if></td> --%>
	                    <td><c:if test="${contests.getStatus()=='opened'}"><textarea name="submission" rows="2" cols="50"></textarea></c:if></td>
	                    <td><c:if test="${contests.getStatus()=='opened'}"><input type = "submit" value = "Submit!"/></c:if></td>
	            </c:forEach>
	        </table>
        </form>
	</div>
	</div>

</body>
</html>
