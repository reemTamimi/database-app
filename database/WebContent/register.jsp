<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head><title>Registration</title></head>
<body>
	<div align="center">
		<p> ${errorOne } </p>
		<p> ${errorTwo } </p>
		<form action="register">
		
			<select name="userRole">
		        <option value="contestant">Contestant</option>
			    <option value="sponsor">Sponsor</option>
			    <option value="judge">Judge</option>
			</select>
		
			<table border="1" cellpadding="5">
				<tr>
					<th>Wallet Address: </th>
					<td align="center" colspan="3">
						<input type="text" name="walletAddress" size="42"  value="0x000000000000000000000000000000000000000A" onfocus="this.value=''">
					</td>
				</tr>
				
				<%----<tr>
					<th>Role: </th>
					<td align="center" colspan="3">
						<input type="text" name="userRole" size="45" value="Role" onfocus="this.value=''">
					</td>
				</tr> --%>
				
				<tr>
					<th>Password: </th>
					<td align="center" colspan="3"> 
						<input type="password" name="password" size="45" value="password" onfocus="this.value=''">
					</td>
				</tr>
				<tr>
					<th>Password Confirmation: </th>
					<td align="center" colspan="3">
						<input type="password" name="confirmation" size="45" value="password" onfocus="this.value=''">
					</td>
				
				</tr>
				<tr>
					<td align="center" colspan="5">
						<input type="submit" value="Register"/>
					</td>
				</tr>
			</table>
			<a href="login.jsp" target="_self">Return to Login Page</a>
		</form>
	</div>
</body>
