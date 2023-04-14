<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
</head>
<body>

<div align = "center">
	
	<form action = "initialize">
		<input type = "submit" value = "Initialize the Database"/>
	</form>
	<a href="login.jsp"target ="_self" > logout</a><br><br> 

<h1>List all users</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>Wallet Address</th>
                <th>Role</th>
                <th>Password</th>
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
                    <td><c:out value="${users.getRole()}" /></td>
                    <td><c:out value="${users.getPass()}" /></td>
            </c:forEach>
        </table>
	</div>
	
<h1>Big Sponsors</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Wallet Address</th>
            </tr>
            <c:forEach var="users" items="${bigSponsors}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>
	
<h1>Top Judges</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Wallet Address</th>
            </tr>
            <c:forEach var="users" items="${topJudges}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>

<h1>Best Contestants</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Wallet Address</th>
            </tr>
            <c:forEach var="users" items="${bestContestants}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>
	
<h1>Sleepy Contestants</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Wallet Address</th>
            </tr>
            <c:forEach var="users" items="${sleepyContestants}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>
	
<h1>Tough Contest</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Wallet Address</th>
            </tr>
            <c:forEach var="users" items="${toughContests}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>
	
	
	</div>

</body>
</html>
