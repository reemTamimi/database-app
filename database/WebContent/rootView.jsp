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
	
<h1>Common Contests</h1>
<div align="center">
	<form action = "find_common">
		<select name="contestant1">
	            <c:forEach var="users" items="${contestants}">
	                    <option value="${users.getWallet()}">${users.getWallet()}</option>
	            </c:forEach>
		</select>
		<select name="contestant2">
	            <c:forEach var="users" items="${contestants}">
	                    <option value="${users.getWallet()}">${users.getWallet()}</option>
	            </c:forEach>
		</select>
		<input type="submit" value="Search!"/>
	</form>
        <table border="1" cellpadding="6">
            <tr>
                <th>${c1} & ${c2}</th>
            </tr>
            <c:forEach var="common" items="${commonContests}">
                <tr style="text-align:center">
                    <td><c:out value="${common}" /></td>
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
	
<h1>Copy Cats</h1>
<div align="center">
	<form action = "find_copycats">
		<select name="contestantWallet">
	            <c:forEach var="users" items="${contestants}">
	                    <option value="${users.getWallet()}">${users.getWallet()}</option>
	            </c:forEach>
		</select>
		<input type="submit" value="Search!"/>
	</form>
        <table border="1" cellpadding="6">
            <tr>
                <th>Copied Cat: ${copiedcat}</th>
            </tr>
            <c:forEach var="users" items="${copyCats}">
                <tr style="text-align:center">
                    <td><c:out value="${users.getWallet()}" /></td>
            </c:forEach>
        </table>
	</div>
	
<h1>Past Statistics</h1>
<div align="center">
        <table border="1" cellpadding="6">
            <tr>
                <th>Sponsors</th>
                <th>Judges</th>
                <th>Contestants</th>
                <th>Contests</th>
                <th>Sponsor Fees</th>
                <th>Judge Reward Balance</th>
                <th>Contestant Reward Balance</th>
                
            </tr>
            <c:forEach var="num" items="${statistics}">
                <!-- <tr style="text-align:center"> -->
                    <td style="text-align:center"><c:out value="${num.getNumber()}" /></td>
            </c:forEach>
        </table>
	</div>
	
	
	</div>

</body>
</html>
