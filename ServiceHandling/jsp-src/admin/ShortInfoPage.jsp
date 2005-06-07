<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.gui.admin.ShortInfoPageData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	ShortInfoPageData data = (ShortInfoPageData)session.getAttribute(ShortInfoPageData.ATT_NAME);
%>
<html>

<head>
	<title><%= data.getPageTitle() %></title>
	<META http-equiv="Refresh" content="<%= data.getSleepTime() %>; URL=<%= data.getRedirectionUrl() %>"/>
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
    <td colspan="3"> <div align="center"><strong><font size="4"><%= data.getSectionTitle() %></font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="784" cellpadding="4" cellspacing="4">
        <tr>	
          		<td>
					<%= data.getRedirectionText() %>
				</td>
			</tr>
			<form action="../adm" method="post">
			<tr>
				
            <td> <div align="center">
                <input name="action" type="hidden" value="<%= data.getRedirectionCancelAction() %>"/>
                <input name="submit" type="submit" value="<%= data.getRedirectionCancelButtonTitle() %>"/>
              </div></td>
			</tr>
			</form>
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
