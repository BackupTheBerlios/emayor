<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor Admin Interface (C) - v.1.0.0</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">Lookup service profile:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<form action="../adm" method="post">
		<input type="hidden" name="action" value="LOOKUP_SERVICE_PROFILE"/>
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="198"> 
            <div align="left"><strong>Attribute name </strong></div></td>
				
          <td width="568"> 
            <div align="left"><strong>Attribute value </strong></div></td>

		  </tr>	
			<tr>
				<td>Session id</td>
				<td>
					<input name="SID" type="text" value="" maxlength="" size="40"/>
					<%
						String[] ids = (String[])session.getAttribute("SERVICE_PROFILE_ID_ARRAY");
						if (ids != null && ids.length != 0) {		
					%>					
					&nbsp;&nbsp;or&nbsp;&nbsp;
					<select name="SID2">
					<%
						for (int i = 0; i < ids.length; i++) {
					%>
						<option value="<%= ids[i] %>"><%= ids[i] %></option>
					<%
						}
					%>
					</select>
					<%
						}
					%>	
				</td>
			</tr>
			<tr>
          <td colspan="2"> <div align="center">
              <input name="lookup" type="submit" value="LOOKUP"/>
            </div></td>
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
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">logout</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=LOOKUP_SERVICE_PROFILE_INPUT"><font size="2">refresh it</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
