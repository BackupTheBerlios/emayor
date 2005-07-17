<%@page contentType="text/html" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor - System configuration</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">System configuration:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> <form action="../adm" method="post">
		<input type="hidden" name="action" value="reconfigure"/>
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="198"> 
          <div align="left"><strong>Attribute name </strong></div></td>
				
          <td width="568"> 
          <div align="left"><strong>Attribute value </strong></div></td>

		  </tr>
			<%
				Properties props = (Properties)session.getAttribute("SYSTEM_CONFIGURATION");
				int index = 0;
				for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
            		String key = (String)e.nextElement();
            		String value = props.getProperty(key);
					if ( (index % 2) == 0) {
			%>	
			<tr>
			<%		} else { %>
			<tr bgcolor="#FFCCCC">
			<%		} 
					++index;
			%>
				<td><%= key %></td>
				<td><input type="text" name="ECONFIG-<%= key %>" size="50" maxlength="200" value="<%= value %>"/></td>
			</tr>
			<%	} %>
            <tr> 
              <td colspan="2" align="right"><input type="reset" value="RESET"/> 
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" name="submit" value="RECONFIGURE"/></td>
            </tr>
		</table>
		</form>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">logout</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
