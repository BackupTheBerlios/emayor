<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Policy Enforcer Admin Interface (C) - v.1.0.0</title>
</head>

<body bgcolor="#FFFFFF">
<table border="0" width="800">
  <tr bgcolor="#999900"> 
    <td height="60" bgcolor="#99CCFF" colspan="3"> <div align="center"><strong><font size="7">eMayor 
        Platform - Policies Managment</font></strong></div></td>
  </tr>
  <tr> 
<%
	String CurrentPolicyName = (String)session.getAttribute("PolicyName");

if (CurrentPolicyName!=null) {
    %>
    <tr> 
    <td colspan="3"> <div align="center">Managed Policy: <%=CurrentPolicyName%> </div></td>
  	</tr> <%
}
%>

    <td colspan="3"> <hr /></td>
  </tr>
  <tr> 
    <td colspan="3"> <div align="left"><strong><font size="4">Please, choose a Policy to manage:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="388" cellpadding="4" cellspacing="4">
	</tr>		
          		
<!-- List Policies -->
<%
	java.util.Set PoliciesSet = (java.util.Set)session.getAttribute("PoliciesList");
    if (PoliciesSet!=null) {
		java.util.Iterator it = PoliciesSet.iterator();
		while (it.hasNext()) {
			String MyName = (String)(it.next());
			%>
				<tr>
				<td>
						- <a href="../admpe?action=ShowPolicy&PolicyID=<%=MyName%>"><font size="2"><%=MyName%></font></a>
				</td> 
				</tr>
			<%
		}
	} else {
			%>	<tr>
				<td>
					- <a href="../admpe?action=ListPolicies"><font size="2">List Loaded Policies</font></a>
				</td>
				</tr> <%
}
%>

			
		</table>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../admpe?action=STOP"><font size="2">EXIT</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
