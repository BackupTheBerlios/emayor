<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Residence Certification Document</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
		    <style type="text/css">
<!--
.style2 {font-size: 14px}
.style3 {font-size: 12px}
-->
        </style>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	SimpleDateFormat formater = new SimpleDateFormat("EEE, MMM d, ''yyyy");
	Calendar cal = task.getIncoming();
	String incoming = formater.format(cal.getTime());
	cal = task.getDeadline();
	String deadline = formater.format(cal.getTime());
	boolean b = task.isSignatureStatus();
	String sigStatus = (b)?"OK":"NOT OK";
%>
<body bgcolor="#FFFFFF">
<p><span class="style2"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
  <br/>
    <span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
  <br/>
</p>
<form action="ServiceHandlingTest" method="post">
<input name="action" type="hidden" value="CVHandleTask"/>
<table width="587" border="1" cellspacing="1" cellpadding="1">
  <tr bgcolor="#CCCC66">
    <th width="164" scope="col"><div align="center" class="style2">Property name </div></th>
    <th width="410" scope="col"><div align="center" class="style2">Property value </div></th>
  </tr>
  <tr>
    <td><span class="style3">Document type </span></td>
    <td><div align="right" class="style3">
      <div align="left"><%= task.getExtraInfo() %></div>
    </div></td>
  </tr>
  <tr>
    <td><span class="style3">Document status </span></td>
    <td><span class="style3"><%= task.getStatus() %></span></td>
  </tr>
  <tr>
    <td><div align="left" class="style3">Requester forename </div></td>
    <td><div align="right" class="style3">
      <div align="left"><%= session.getAttribute("FORENAME") %></div>
    </div></td>
  </tr>
  <tr>
    <td><div align="left" class="style3">Requester surname </div></td>
    <td><div align="right" class="style3">
      <div align="left"><%= session.getAttribute("SURNAME") %></div>
    </div></td>
  </tr>
  <tr>
    <td><div align="left" class="style3">Requester email </div></td>
    <td><div align="right" class="style3">
      <div align="left"><%= session.getAttribute("EMAIL") %></div>
    </div></td>
  </tr>
  <tr>
    <td><span class="style3">Incoming date </span></td>
    <td><div align="right" class="style3">
      <div align="left"><%= incoming %></div>
    </div></td>
  </tr>
  <tr>
    <td><span class="style3">Deadline</span></td>
    <td><div align="right" class="style3">
      <div align="left"><%= deadline %></div>
    </div></td>
  </tr>
  <tr>
    <td><span class="style3">Dig. signature</span></td>
    <td><div align="right" class="style3">
      <div align="left"><%= sigStatus %></div>
    </div></td>
  </tr>
  <tr>
    <td colspan="2"><div align="left"></div>      
      <div align="center">
        <input type="submit" name="Submit" value="Approve" />
		&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="Submit" value="Decline" />
      </div></td>
  </tr>
</table>
</form>
<br/>
<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>
</body>

</html>
