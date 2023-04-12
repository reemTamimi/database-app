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
		<form action="sponsor_create">
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
						<!-- <input type="text" name="requirements" size="45" height="48" value="List Requirements" onfocus="this.value=''"> -->
						<textarea name="requirements" rows="4" cols="50"></textarea>
					</td>
				</tr>
			</table>
			<h3>Select 5-10 Judges:</h3>
			<select name="judges" multiple>
				<c:forEach var="j" items="${listJudge}">
					<option value="${j.getWallet()}">${j.getWallet()}</option>
				</c:forEach>
			</select> 
			<input type="submit" value="Create Contest"/>
		</form>
		<br><br>
		<!-- <a href="sponsorView.jsp" target="_self" >Back to Actions</a><br><br> -->
		<form action="sponsor_distribute">
			<input type="submit" value="View Closed Contests"/>
		</form>
	<!-- <a href="sponsorDistributeView.jsp" target="_self" >View Closed Contests</a><br><br> comment-->
	</div>
</body>
