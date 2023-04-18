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
    <form action="judge_submission">
        <table border="1" cellpadding="6">
            <tr>
            	<th style="display:none;">Contestant Wallet</th>
            	<th style="display:none;">Contest Wallet</th>
                <th>Contest Title</th>
                <th>Submission</th>
                <th>Requirements</th>
                <th>Grade [0-100]</th>
            </tr>
            
            <c:forEach var="submissions" items="${listSubmission}">
                <tr style="text-align:center">
                	<td style="display:none;"><input type="text" name="contestantWallet" value="${submissions.getContestant()}"></td>
                	<td style="display:none;"><input type="text" name="contestWallet" value="${submissions.getContest()}"></td>
                    <td><c:out value="${submissions.getTitle()}" /></td>
<%--                     <td><a href="resources/${submissions.getSubmission()}" target="_blank">submission</a></td>
                    <td><a href="resources/${submissions.getRequirements()}" target="_blank">requirements</a></td> --%>
                    <td><c:out value="${submissions.getSubmission()}" /></td>
                    <td><c:out value="${submissions.getRequirements()}" /></td>
                    <td><input type="number" name="grade" value="0-100" onfocus="this.value''"></td>
                    <td><input type="submit" value="Submit!"></td>
                </tr>
            </c:forEach>
        </table>

        </form>
	</div>
	</div>

</body>
</html>