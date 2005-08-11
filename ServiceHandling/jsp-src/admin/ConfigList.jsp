<%@page contentType="text/html" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Configurations</title>
</head>

<body bgcolor="#FFFFFF">
<table border="0" width="800">
  <tr bgcolor="#999900"> 
    <td height="60" bgcolor="#99CCFF" colspan="3"> <div align="center"><strong><font size="7">eMayor 
        Platform</font></strong></div></td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /></td>
  </tr>
  <tr> 
    <td colspan="3"> <div align="center"><strong><font size="4">System configurations:</font></strong> 
      </div></td>
  </tr>
	<tr height="20">
	</tr>
  <tr> 
	<td colspan="3">
	<div align="center">
	  <form name="Config" action="../adm" method="post">
		<input type="hidden" name="action" value="SWITCHCONFIG"/>
		<select name="configID" size="1">
	<%
		HashSet names = (HashSet) session.getAttribute("ALL_CONFIG_IDS");
		String current = (String) session.getAttribute("CURRENT_CONFIG_ID");
		Iterator i = names.iterator();
		while (i.hasNext()) {
			String name = (String) i.next();
			if (name.equals(current)) {
	%>

		<option selected><%= name %></option>

	<%
		} else {
	%>
		<option><%= name %></option>
	<%
		}
	}
	%>
		</select>
   		<input type="submit" name="submit" value="SWITCH"/>
	</form>
	</div>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">EXIT</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
