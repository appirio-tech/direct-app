<%--
  - Author: TCSASSEMBLER
  - Date: 28th June 2009
  - Version: 1.0
  - Since: Jira & Confluence User Sync Service
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the User Sync Service web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the User Sync Service web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Sync Service Demo</title>
<script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
</head>
<body>
<p>User Sync Service Demo (User: <%=request.getUserPrincipal().getName()%>
(<%=request.isUserInRole("Cockpit Administrator") ? "Cockpit Administrator" : "Cockpit User"%>))</p>
<br />
<br />
<span style="color: blue"><b>User Sync Service Demo</b></span>
<br />
<form action="callService.jsp" method="POST" name="Form" id="Form">
<input type="hidden" name="operation" value="">
<ul>
	<li>UserServiceFacade.getJiraUser(userHandle) <br />
	&nbsp; &nbsp; User Handle: <input type="text" name="handle_jira"
		value=""> &nbsp; : &nbsp; <input type="button" value="Execute"
		onclick="callService('getJiraUser');" /></li>
	<li />
	<li>UserServiceFacade.getConfluenceUser(userHandle) <br />
	&nbsp; &nbsp; User Handle: <input type="text" name="handle_confluence"
		value=""> &nbsp; : &nbsp; <input type="button" value="Execute"
		onclick="callService('getConfluenceUser');" /></li>
</ul>
</form>
<br />
<br />
</body>
</html>