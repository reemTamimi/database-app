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
            </tr>
            <c:forEach var="c" items="${listClosedContest}">
                <tr style="text-align:center">
                    <td><c:out value="${c.getTitle()}" /></td>
                    <td><c:out value="${c.getStatus()}" /></td>
                    <td><c:out value="${c.getFee()}" /></td>
            </c:forEach>
        </table>
	</div>
	<form action="sponsor_do_distribute">
		<h3>Click below to distribute funds to all above-listed closed contests</h3>
    	<input type="submit" value="Distribute!">
    </form>
	<br><br>
	<!-- <a href="sponsorView.jsp" target="_self" >Back to Actions</a><br><br> -->
	<form action="sponsor_create">
		<input type="submit" value="Go To: Create Contest"/>
	</form>
	</div>

</body>
</html>
