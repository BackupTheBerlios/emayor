<%@page contentType="text/html" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
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
    <td colspan="3"> <form name="Config" action="../adm" method="post">
		<input type="hidden" name="action" value="CHANGE_LOG4J_CONFIG_POST"/>
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="198"> 
          <div align="left"><strong>Attribute name </strong></div></td>
				
          <td width="568"> 
          <div align="left"><strong>Attribute value </strong></div></td>
			<tr height="20">
			</tr>
		  </tr>
			<%
				HashMap conf = (HashMap) session.getAttribute("LOG4J_CONFIG");
				HashSet prios = (HashSet) session.getAttribute("LOG4J_PRIOS");
				Iterator i = conf.keySet().iterator();
    			String key, value;
    			while (i.hasNext()) {
    				key = (String) i.next();
					value = (String) conf.get(key);
			%>	
			<tr>
				<td><%= key %></td>
				<td><select name="ECONFIG-<%= key %>" size="1">
						<%
							Iterator i2 = prios.iterator();
							String option;
							while (i2.hasNext()) {
								option = (String) i2.next();
								if (option.equals(value)) {
						%>
						<option selected><%= option %></option>
						<%
								} else {
						%>
						<option><%= option %></option>
						<%
								}
						}
						%>
					</select>
				</td>
			</tr>
			<%	} %>
            <tr> 
              <td colspan="2" align="right">
              	<input type="reset" value="RESET"/> 
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="submit" name="create" value="APPLY"/>
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
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">logout</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
