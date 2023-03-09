<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Contest</title>
</head>
<body>
	<div align="center">
	
	<a href="login.jsp" target="_self" > logout</a><br><br> 
		<h1>Create Contest</h1>
		<p> ${errorOne } </p>
		<p> ${errorTwo } </p>
		<form action="register">
			<table border="1" cellpadding="5">
				<tr>
					<th>Wallet Address: </th>
					<td align="center" colspan="3">
						<input type="text" name="walletAddress" size="42"  value="0x000000000000000000000000000000000000000A" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Title: </th>
					<td align="center" colspan="3">
						<input type="text" name="title" size="45" value="Title" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Start Date: </th>
					<td align="center" colspan="3"> 
						<input type="date" name="startDate" size="45" value="2023-03-01" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>End Date: </th>
					<td align="center" colspan="3">
						<input type="date" name="endDate" size="45" value="2023-09-01" onfocus="this.value=''">
					</td>
				
				</tr>
				<tr>
					<th>Sponsor Fee: </th>
					<td align="center" colspan="3">
						<input type="text" name="sponsorFee" size="45" value="$00.00" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Requirements: </th>
					<td align="center" colspan="3">
						<input type="file" name="requirements" size="45" value="List Requirements" onfocus="this.value=''">
					</td>
				</tr>
<%-- 			 	<tr>
					<th>Judges: </th>
					<c:forEach var="j" items="${listJudges}">
					<tr style="text-align:center">
						<td><c:out value="${j.getWallet()}" /> <br>
						
							<input type="checkbox" id="${judges.getWallet()}" name="sponsors" value="${users.getWallet()}" onfocus="this.value=''">
							<label for="${judges.getWallet()}">${judges.getWallet()}</label><br>
						</td>
					</c:forEach>
				</tr> --%>
			</table>
			<h3>Select 5-10 Judges:</h3>
			<c:forEach var="j" items="${listJudge}">
				<input type="checkbox" id="${j.getWallet()}" name="judges" value="${j.getWallet()}" onfocus="this.value=''">
				<label for="${j.getWallet()}">${j.getWallet()}</label><br>
			</c:forEach>
			<input type="submit" value="Create Contest"/>
		</form>
		<br><br>
		<a href="sponsorView.jsp" target="_self" >Back to Actions</a><br><br>
		
	</div>
</body>