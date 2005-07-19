<%@page contentType="text/html" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Properties" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>JSP Page</title>
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
    <td colspan="3"> 
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="198"> 
          <div align="left"><strong>Attribute name </strong></div></td>
				
          <td width="568"> 
          <div align="left"><strong>Attribute value </strong></div></td>

		  </tr>
			<%
				Properties props = (Properties) session.getAttribute("SYSTEM_CONFIGURATION");
				TreeMap map = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                map.putAll(props);
				Set s = map.keySet();
				Iterator i = s.iterator();
				int index = 0;
				while (i.hasNext()) {
            		String key = (String) i.next();
            		String value = (String) map.get(key);
					if ( (index % 2) == 0) {
			%>	
			<tr>
			<%		} else { %>
			<tr bgcolor="#FFCCCC">
			<%		} 
					++index;
			%>
				<td><%= key %></td>
				<td><%= value %></td>
			</tr>
			<%	} %>
		</table>
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
