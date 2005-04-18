<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Download the document</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
	<style type="text/css">
<!--
.style2 {font-size: 14px}
.style5 {font-size: 14px; font-weight: bold; }
.style7 {font-weight: bold; font-size: 12px; }
-->
        </style>
</head>

<body bgcolor="#FFFFFF">
<p><span class="style2"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
  <br/>
    <span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
  <br/>
</p>

<h2>Please download your document and after that press the DONE button!</h2>

<p><a href="<%= session.getAttribute("DOWNLOAD_LINK") %>">DOWNLOAD</a>
</p>
<p>&nbsp; </p>
<form action="ServiceHandlingTest" method="post">
	<input type="submit" name="submit" value="DONE"/>
	<input type="hidden" name="action" value="AfterDownload"/>
</form>
</body>

</html>
