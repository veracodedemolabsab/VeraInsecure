<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VeraInsecure</title>
</head>
<body>
	<h1>Flaw Examples</h1>
	<h2>CWE 117: CRLF Injection</h2>
	<form action="crlfinjection" method="post">
		Username: <input type="text" name="crlfusername" size="20"><br>
		<input type="submit" value="Login" />
	</form>
	<h2>CWE 80: Cross-Site Scripting</h2>
	<form action="xss" method="post">
		Username: <input type="text" name="xssusername" size="20"><br>
		Password: <input type="password" name="xsspassword" size="20"><br>
		<input type="submit" value="Login" />
	</form>
	<h2>CWE 73: File Name or Path Control</h2>
	<form action="pathtraversal" method="post">
		Receipt Name: <input type="text" name="receiptname" size="20"><br>
		<input type="submit" value="Delete Receipt" />
	</form>
	<h2>CWE 209: Information Exposure Through An Error Message</h2>
	<form action="errormessageexposure" method="post">
		Username: <input type="text" name="tmiusername" size="20"><br>
		Password: <input type="password" name="tmipassword" size="20"><br>
		<input type="submit" value="Login" />
	</form>
	<h2>CWE 89: SQL Injection</h2>
	<form action="sqlinjection" method="post">
		User ID: <input type="text" name="sqlusername" size="20"><br>
		<input type="submit" value="Get Balance" />
	</form>
</body>
</html>