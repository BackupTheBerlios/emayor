<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor: Main menu page</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
</head>

<body bgcolor="#FFFFFF">
<font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font>
<br>

<h2>Main Menu Page</h2>
		
		<br/>
		<a href="ServiceHandlingTest?action=welcome">service listing :-)</a> 
		<br/>
		<a href="ServiceHandlingTest?action=listMyTasks">my tasks listing :-)</a>
		<br/>
		<a href="ServiceHandlingTest?action=testPrintService">print something</a>
		<br/>
		<a href="ServiceHandlingTest?action=ShowInvokeBPELCallbackPage">invoke BPEL callback</a>
		<br/>
		<a href="ServiceHandlingTest?action=ForwardRequest&asid=<%= session.getAttribute("ASID") %>&ServiceName=ResidenceCertificationService/v10">forward sample request</a>

<hr/>
<a href="ServiceHandlingTest?action=Logout"> LOGOUT </a>
</body>

</body>

</html>
