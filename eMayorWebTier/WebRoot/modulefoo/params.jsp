<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="org.apache.struts.util.*" %>

<!-- äöü -->

These are the parameters from the request.
<br>
<%
    Enumeration enum = request.getParameterNames();

    while(enum.hasMoreElements()){

        out.println( "Parameter: " + enum.nextElement() + "<br>");
    }

%>
<br><br>
<%= "This is the query string from the request: " +
    SecureRequestUtils.getRequestParameters(request)
%>



