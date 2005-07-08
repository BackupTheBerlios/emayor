<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Residence Certification Service - Sign Data</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
    <style type="text/css">
<!--
.style1 {
	font-size: 14px;
	font-weight: bold;
}
.style4 {font-size: 12px; font-weight: bold; }
-->
    </style>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	boolean status = task.getStatus().equals("YES");
	String forename = (String)session.getAttribute("REQ_FORENAME");
	String surname = (String)session.getAttribute("REQ_SURNAME");
	String email = (String)session.getAttribute("REQ_EMAIL");
%>
<body bgcolor="#FFFFFF">
<p><span class="style2"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
  <br/>
    <span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
  <br/>
</p>
<h2>Signing Site for the Residence Certification Service</h2>
<form method="post" action="ServiceHandlingTest">
	<table width="600" border="1" cellspacing="2" cellpadding="2">
		  <tr bgcolor="#CCCC66">
			<th width="121" scope="col"><div align="center" class="style1">Name</div></th>
			<th width="459" scope="col"><div align="center" class="style1">Value</div></th>
	  </tr>
		  <tr>
		  	<td><span class="style4">Task Id</span></td>
			<td><%= task.getTaskId() %></td>
		  </tr>
		  <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		  	<td><span class="style4">Task Status</span></td>
			<td><%= task.getStatus() %></td>
		  </tr>
		  <tr>
		  	<td><span class="style4">Request document</span></td>
			<td>
				<%= task.getXMLDocument() %>
			</td>
		  </tr>
		  <tr bgcolor="#CCCCCC">
		  	<td><span class="style4">Requester forename: </span></td>
			<td>
		    	<%= forename %>
			</td>
		  </tr>
		  <tr>
		  	<td><span class="style4">Requester surname: </span></td>
			<td>
				<%= surname %>
			</td>
		  </tr>
		  <tr bgcolor="#CCCCCC">
		  	<td><span class="style4">Requester email: </span></td>
			<td>
				<%= email %>
			</td>
		  </tr>
		  <tr bgcolor="#CCCC66">
		    <td colspan="2">&nbsp;</td>
      </tr>
		  <tr bgcolor="#CCCCFF">
			<td colspan="2">
			  <div align="left">
			    <input type="submit" name="submit" value="Sign and post request"/>
			    <input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
			    <input type="hidden" name="action" value="ServiceHandlingPostSignRequest"/>
	            </div></td>
		  </tr>
  </table>
</form>
<br/>
<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>
</body>

</html>
