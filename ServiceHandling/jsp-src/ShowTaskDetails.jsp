<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<%@page import="org.emayor.servicehandling.bpel.task._task" %>
<%@page import="org.emayor.servicehandling.bpel.task._task_status" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="org.apache.axis.types.Duration" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Showing Task Details</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
	<style type="text/css">
<!--
.style2 {font-size: 14px}
.style5 {font-size: 14px; font-weight: bold; }
.style7 {font-weight: bold; font-size: 12px; }
-->
        </style>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	SimpleDateFormat formater = new SimpleDateFormat("EEE, MMM d, ''yyyy");
	Calendar cal = task.getIncoming();
	String incoming = formater.format(cal.getTime());
	boolean b = task.isSignatureStatus();
	String sigStatus = (b)?"OK":"NOT OK";
%>
<body bgcolor="#FFFFFF">
<p><span class="style2"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
  <br/>
    <span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
  <br/>
</p>
	<h2>GOT DETAILS OF SPECIFIED TASK FROM BPEL SERVER:</h2>

	<form method="post" action="ServiceHandlingTest">
	<input name="action" type="hidden" value="completeTask"/>
	<table width="587" border="1" cellspacing="1" cellpadding="1">
  <tr bgcolor="#CCCC66">
    <th width="164" scope="col"><div align="center" class="style2">Property name </div></th>
    <th width="410" scope="col"><div align="center" class="style2">Property value </div></th>
  </tr>
  <tr>
    <td><span class="style7">Document type </span></td>
    <td><div align="right" class="style7">
      <div align="left"><%= task.getExtraInfo() %></div>
    </div></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td><span class="style7">Document status </span></td>
    <td><span class="style7"><%= task.getStatus() %></span></td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#CCCC66"><div align="center"><strong><span class="style2">Concerned Person Data </span></strong></div></td>
    </tr>
  <tr>
    <td><div align="left" class="style7">Forename </div></td>
    <td><div align="right" class="style7">
      <div align="left"><%= session.getAttribute("FORENAME") %></div>
    </div></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td><div align="left" class="style7">Surname </div></td>
    <td><div align="right" class="style7">
      <div align="left"><%= session.getAttribute("SURNAME") %></div>
    </div></td>
  </tr>
  <tr>
    <td><div align="left" class="style7">Email </div></td>
    <td><div align="right" class="style7">
      <div align="left"><%= session.getAttribute("EMAIL") %></div>
    </div></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td><span class="style7">Sex</span></td>
    <td><div align="right" class="style7">
      <div align="left"><%= session.getAttribute("SEX") %></div>
    </div></td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#CCCC66"><div align="center"><span class="style2"><span class="style5">General document data </span></span></div></td>
    </tr>
  <tr>
    <td><strong class="style7">Incoming date </strong></td>
    <td><div align="right" class="style7">
      <div align="left"><%= incoming %></div>
    </div></td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td><span class="style7">Dig. signature</span></td>
    <td><div align="right" class="style7">
      <div align="left"><%= sigStatus %></div>
    </div></td>
  </tr>
  <tr bgcolor="#CCCC66">
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr bgcolor="#CCCCFF">
    <td colspan="2"><div align="left"></div>      
      <div align="center">
        <input type="submit" name="Submit" value="ACCEPT" />
      </div></td>
  </tr>
</table>

 <!--
    <td colspan="2">
    	<input type="submit" name="submit" value="complete task"/>
		<input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
		<input type="hidden" name="action" value="completeTask"/>
    </td>
  </tr> -->
	</form>
	<br/>
<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>
</body>

</html>
