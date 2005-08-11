<%@page contentType="text/html" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor - Testing</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">Platform tests:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> <form name="Config" action="../adm" method="post">
		<input type="hidden" name="action" value="test_platform_exec"/>
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="20"> </td>
				
          <td> 
          <div align="left"><strong>Description</strong></div></td>

		  </tr>
		  <tr height="10"></tr>
			<%
				Map tests = (Map) session.getAttribute("ETESTS");
				Set s = tests.keySet();
				Iterator i = s.iterator();
				int index = 0;
				while (i.hasNext()) {
            		Integer key = (Integer) i.next();
            		String value = (String) tests.get(key);
			%>	
			<tr>
				<td><input type="checkbox" name="<%= key %>" value="<%= key %>" checked/></td>
				<td><%= value %></td>
			</tr>
			<%	} %>
            <tr> 
              <td colspan="2" align="right">
              	<input type="reset" value="RESET"/> 
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="submit" name="test" value="TEST PLATFORM"/>
              </td>
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
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">EXIT</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
