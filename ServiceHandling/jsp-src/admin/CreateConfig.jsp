<%@page contentType="text/html" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Create Configuration</title>
</head>

<script language="JavaScript">
function chkConfig () {
  if (document.Config.ConfigID.value == "") {
    alert("Please insert a valid name for the new configuration!");
    document.Config.ConfigID.focus();
    return false;
  }
/*
  alert("everything is fine");
  String[] configs = (String[]) session.getAttribute("CONFIG_IDS");
  if (configs[1] == document.Config.ConfigID.value) {
    alert("Configuration already existant, please choose a different name!");
    document.Config.ConfigID.focus();
    return false;
  }
  alert("everything is fine");
*/
}
</script>

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
	  <form name="Config" action="../adm" method="post" onSubmit="return chkConfig()">
		<input type="hidden" name="action" value="CREATECONFIGPOST"/>
		<table width="784" cellpadding="4" cellspacing="0">
		  <tr height="20"></tr>
		  <tr>
				<td>
					name of new configuration
				</td>
				<td>
					<input type="text" name="ConfigID" size="40" maxlength="200" value="configName"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    	<input type="submit" name="submit" value="CREATE"/>
				</td>
		  </tr>
		  <tr height="20"></tr>
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
