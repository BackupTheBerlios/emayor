<%@page contentType="text/html" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor - Testing results</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">System test:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3">
		<table width="784" cellpadding="4" cellspacing="0">
			<%
				Map tests = (Map) session.getAttribute("ETESTS");
				Map results = (Map) session.getAttribute("ETEST_RESULTS");
				Set s = tests.keySet();
				Iterator i = s.iterator();
				int index = 0;
				while (i.hasNext()) {
            		Integer key = (Integer) i.next();
					if (results.containsKey(key)) {
						String value = (String) results.get(key);
						String name = (String) tests.get(key);
			%>	
			<tr height="5"></tr>
			<tr>
				<td width="50"><strong>Test:</strong></td>
				<td><%= name %></td>
			</tr>
			<tr>
				<td width="50"><strong>Result:</strong></td>
				<td bgcolor="#FFCCCC"><%= value %></td>
			</tr>
			<tr height="2"><td colspan="2"> <hr /> </td></tr>
			<tr height="5"></tr>
			<%	}
				} %>
            <tr> 
            </tr>
		</table>
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
