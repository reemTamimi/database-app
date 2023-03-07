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
			<!-- 	<tr>
					<th>Sponsors: </th>
					<c:forEach var="users" items="${listUser}">
						<td><c:out value="${users.getWallet()}" />
							<input type="checkbox" id="${users.getWallet()}" name="sponsors" value="${users.getWallet()}" onfocus="this.value=''">
							<label for="${users.getWallet()}">${users.getWallet()}</label><br>
						</td>
					</c:forEach>
					 	<input type="checkbox" name="sponsors" value="1" onfocus="this.value=''">
						<br>
						<input type="checkbox" name="sponsors" value="2" onfocus="this.value=''">
					</td>
				</tr> -->
			</table>
			<h3>Sponsors:</h3>
			<p>test1</p>
			<c:forEach var="users" items="${listUser}">
				<p><c:out value="${users.getWallet()}" /></p>
			</c:forEach>
			<input type="submit" value="Create Contest"/>
		</form>
	</div>
</body>