<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>List my tasks!</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
    <style type="text/css">
<!--
.style1 {font-size: 12px}
.style2 {font-size: 14px}
.style3 {font-size: 14}
.style4 {font-size: 12px; font-weight: bold; }
-->
    </style>
</head>

<body bgcolor="#FFFFFF">
<p><span class="style2"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
  <br/>
    <span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
  <br/>
</p>
	<h2>GOT FOLLOWING TASKS FROM BPEL SERVER:</h2>
	<br/>
<table width="600" border="1" cellspacing="0" cellpadding="0">
  <tr bgcolor="#CCCC99">
    <th width="36" scope="col"><div align="center" class="style3">#</div></th>
    <th width="316" scope="col"><span class="style3">Document type </span></th>
    <th width="124" scope="col"><div align="center" class="style3">Incoming date </div></th>
    <th width="114" scope="col"><div align="center" class="style3">Action</div></th>
  </tr>
<%
	Task[] tasks = (Task[])session.getAttribute("MY_TASKS");
	for (int i = 0; i < tasks.length; i++) {
		Task task = tasks[i];
		String taskId = task.getTaskId();
		String docType = task.getExtraInfo();
		SimpleDateFormat formater = new SimpleDateFormat("EEE, MMM d, ''yyyy");
		Calendar cal = task.getIncoming();
		String incoming = formater.format(cal.getTime());
		if ((i % 2) == 1) {
		%>
  <tr>
  		<%
		}
		%>
    <td><div align="center" class="style1"><%= (i+1) %></div></td>
    <td><div align="center" class="style4"><%= docType %></div></td>
    <td><div align="center" class="style1"><%= incoming %></div></td>
    <td align="center" valign="middle" bgcolor="#CCCCFF">
		<form action="ServiceHandlingTest" method="post" class="style1">
			<input type="hidden" name="action" value="showTaskDetails"/>
			<input name="taskId" type="hidden" value="<%= taskId %>"/>
			<input name="taskIndex" type="hidden" value="<%= i %>"/>
		  	<input type="submit" name="Submit" value="show details" />
		</form>
	</td>
  </tr>
<%  
  }
%>
</table>
<p>&nbsp;
</p>

<hr/>
<table width="600">
<tr></tr>
<td>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a> 
</td>
<td><div align="right"><a href="ServiceHandlingTest?action=listMyTasks" class="style2">RELOAD LIST OF TASKS</a></div></td>
</tr>
</table>
</body>

</html>
