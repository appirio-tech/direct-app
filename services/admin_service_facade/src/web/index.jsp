<%--
  - Author: TCSASSEMBLER
  - Date: July 20, 2009
  - Version: 1.0
  - Since Configurable Contest Fees v1.0 Assembly
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the AdminServiceFacade web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the AdminServiceFacade and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AdminServiceFacade Demo</title>
<script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
</head>
<body>
<p>AdminServiceFacade Demo (User is <%=request.getUserPrincipal().getName()%>
(<%=request.isUserInRole("TC Accounting") ? "TC Accounting" : "The user doesn't have 'TC Accounting' role assigned and calls will be failed."%>))</p>
<br />
<br />
<span style="color: blue"><b>AdminServiceFacade Demo</b></span>
<br />
<form action="callService.jsp" method="POST" name="Form" id="Form">
<!-- indicate which method to test -->    
<input type="hidden" name="operation" value="">
<ul>
	<li>AdminServiceFacade.searchProjectsByProjectName(projectName) <br />
	&nbsp; &nbsp; Project Name (case insensitive and partial name search is allowed.): 
	<input type="text" name="projectName" value=""> &nbsp; : &nbsp; 
	<input type="button" value="Execute"
		onclick="callService('searchProjectsByProjectName');" />
    </li>
	<li>AdminServiceFacade.searchProjectsByClientName(clientName) <br />
	&nbsp; &nbsp; Client Name (case insensitive and partial name search is allowed.): 
	<input type="text" name="clientName" value=""> &nbsp; : &nbsp; 
	<input type="button" value="Execute"
		onclick="callService('searchProjectsByClientName');" />
	</li>	
	<li>AdminServiceFacade.getContestFeesByProject(projectId) <br />
	&nbsp; &nbsp; Project Id : 
	<input type="text" name="projectId" value=""> &nbsp; : &nbsp; 
	<input type="button" value="Execute"
		onclick="callService('getContestFeesByProject');" />
	</li>	
	<li>AdminServiceFacade.saveContestFees(contestFees,projectId) <br />
	&nbsp; &nbsp; Project Id : 
	<input type="text" name="projectId2" value=""> &nbsp; : &nbsp; <br/>
	&nbsp; &nbsp; Assembly Contest Fee : 
	<input type="text" name="assembly" value=""> &nbsp; : &nbsp; <br/>

	&nbsp; &nbsp; Studio :  
	subType (now try 'Web Design','Banners/Icons','Application Front-End Design')
	<input type="text" name="subType" value="">  Fee <input type="text" name="studio" value=""> 
	&nbsp; : &nbsp; <br/>	
	<input type="button" value="Execute"
		onclick="callService('saveContestFees');" />
	</li>
</ul>
</form>
<br />
<br />
</body>
</html>
