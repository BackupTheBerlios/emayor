<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Residence Certification Service - Data Collection</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
	 <style type="text/css">
<!--
.style1 {
	font-size: 14px;
	font-weight: bold;
}
.style4 {font-size: 12px; font-weight: bold; }
.style5 {font-size: 12px}
-->
     </style>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	boolean status = task.getStatus().equals("YES");
	if (status) 
		response.sendRedirect("RCSDataSign.jsp");
	String forename = (String)session.getAttribute("REQ_FORENAME");
	String surname = (String)session.getAttribute("REQ_SURNAME");
	String email = (String)session.getAttribute("REQ_EMAIL");
	String servingMunicipality = (String)session.getAttribute("REQ_SERVING_MUNICIPALITY");
	String receivingMunicipality = (String)session.getAttribute("REQ_RECEIVING_MUNICIPALITY");
%>
<body bgcolor="#FFFFFF">
	Current Access Session id = <%= session.getAttribute("ASID") %>
	<br/>
	<h2>GOT DETAILS FOR INPUT DATA PAGE</h2>
	<form method="post" action="ServiceHandlingTest">
		
  <table width="600" border="1" cellspacing="0" cellpadding="0">
    <tr bgcolor="#CCCC66"> 
      <th width="175" scope="col"><div align="center" class="style1">Name</div></th>
      <th width="582" scope="col"><div align="center" class="style1">Value</div></th>
    </tr>
    <tr> 
      <td class="style4"><strong>Task Id</strong></td>
      <td><span class="style5"><%= task.getTaskId() %></span></td>
    </tr>
    <tr bgcolor="#CCCCCC"> 
      <td class="style4"><strong>Task Status</strong></td>
      <td><span class="style5"><%= task.getStatus() %></span></td>
    </tr>
    <tr> 
      <td class="style4"><strong>Requester forename: </strong></td>
      <td> <span class="style5">
        <input name="REQ_FORENAME" type="text" value="<%= forename %>" size="60" /> 
        <font color="#FF0000">*</font> </span></td>
    </tr>
    <tr bgcolor="#CCCCCC"> 
      <td class="style4"><strong>Requester surname: </strong></td>
      <td> <span class="style5">
        <input name="REQ_SURNAME" type="text" value="<%= surname %>" size="60" /> 
        <font color="#FF0000">*</font> </span></td>
    </tr>
    <tr> 
      <td class="style4"><strong>Requester email: </strong></td>
      <td> <span class="style5">
        <input name="REQ_EMAIL" type="text" value="<%= email %>" size="60" /> 
        <font color="#FF0000">*</font> </span></td>
    </tr>
    <tr bgcolor="#CCCCCC"> 
      <td class="style4"><strong>Serving Municipality: </strong></td>
      <td><span class="style5">
        <select name="REQ_SERVING_MUNICIPALITY">
		<% if (servingMunicipality.equals("Siena")) { %>
          <option value="Siena" selected="true">Municipality of Siena</option>
		<% } else { %>
		  <option value="Siena">Municipality of Siena</option>
		<% } %>
		<% if (servingMunicipality.equals("Aachen")) { %>
          <option value="Aachen" selected="true">Municipality of Achen</option>
		<% } else { %>
		  <option value="Aachen">Municipality of Achen</option>
		<% } %>
		<% if (servingMunicipality.equals("Bolzano/Bozen")) { %>
		  <option value="Bolzano/Bozen" selected="true">Municipality of Bozen/Bolzano</option>
		<% } else { %>
          <option value="Bolzano/Bozen">Municipality of Bozen/Bolzano</option>
		<% } %>
		<% if (servingMunicipality.equals("Seville")) { %>
		  <option value="Seville" selected="true">Municipality of Seville</option>
		<% } else { %>
          <option value="Seville">Municipality of Seville</option>
		<% } %>
        </select> 
        <font color="#FF0000">*</font></span></td>
    </tr>
	<tr> 
      <td class="style4"><strong>Receiving Municipality: </strong></td>
      <td> <span class="style5">
         <select name="REQ_RECEIVING_MUNICIPALITY">
          <option value="Siena">Municipality of Siena</option>
          <option value="Aachen" selected="true">Municipality of Achen</option>
          <option value="Bolzano-Bozen">Municipality of Bozen/Bolzano</option>
		  <option value="Seville">Municipality of Seville</option>
        </select> 
        <font color="#FF0000">*</font></span></td>
    </tr>
    <tr bgcolor="#CCCC66"> 
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr bgcolor="#CCCCFF"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="submit" value="validate data"/>
          <input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
          <input type="hidden" name="action" value="ValidateInputData"/>
        </div></td>
    </tr>
  </table>
	</form>
	<br/>
<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>
</body>

</html>
