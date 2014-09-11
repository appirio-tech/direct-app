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
<%@ page import="javax.xml.namespace.QName"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="javax.xml.ws.Service"%>
<%@ page import="org.jboss.ws.core.StubExt"%>
<%@ page import="javax.xml.ws.BindingProvider"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.StringWriter"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.topcoder.service.facade.admin.AdminServiceFacadeWebService"%>
<%@ page import="com.topcoder.clients.model.Project"%>
<%@ page import="com.topcoder.clients.model.ProjectContestFee"%>

<%
    String calledOperation = null;
    Object callResult = "";
    Throwable error = null;
    try {
        // Determine the requested operation
        String operation = request.getParameter("operation");        
        calledOperation = "None has been called.";

        // Obtain a client stub for accessing the web service
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.admin.facade.service.topcoder.com/", "AdminServiceFacadeWebServiceBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        AdminServiceFacadeWebService port = service.getPort(AdminServiceFacadeWebService.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Call the appropriate wen service operation passing provided parameters
        if ("searchProjectsByProjectName".equals(operation)) {
                calledOperation = operation;
				String projectName = request.getParameter("projectName");
				List<Project> projects = port
						.searchProjectsByProjectName(projectName);
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("returns %1$d project(s) :<br/>",
						projects.size()));
				int count = 1;
				for (Project project : projects) {
					sb.append(String.format(
							"project %1$d: id[%2$d] name[%3$s] client_name[%4$s] <br/> ", count,
							project.getId(), project.getName(),
							project.getClient() != null ? project.getClient()
									.getName() : "NULL"));
					count++;
				}
				callResult = sb.toString();
				calledOperation = operation;
        } 
        
        if ("searchProjectsByClientName".equals(operation)) {
            calledOperation = operation;
            String clientName = request.getParameter("clientName");
            List<Project> projects = port.searchProjectsByClientName(clientName);
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("returns %1$d project(s) :<br/>",
						projects.size()));
				int count = 1;
				for (Project project : projects) {
					sb.append(String.format(
							"project %1$d: id[%2$d] name[%3$s] client_name[%4$s] <br/> ", count,
							project.getId(), project.getName(),
							project.getClient() != null ? project.getClient()
									.getName() : "NULL"));
					count++;
				}
				callResult = sb.toString();
        }
        
		if ("getContestFeesByProject".equals(operation)) {
		    calledOperation = operation;
			String projectId = request.getParameter("projectId");
			List<ProjectContestFee> fees = port
					.getContestFeesByProject(Long.parseLong(projectId));
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("returns %1$d fee(s) :<br/>",
					fees.size()));
			int count = 1;
			for (ProjectContestFee fee : fees) {
				sb.append(String.format(
						"contest fee %1$d: contest type[%2$d] is_studio[%3$s] fee[%4$.2f] <br/> ", count,
						fee.getContestTypeId(),fee.isStudio(),fee.getContestFee()
						));
				count++;
			}
			callResult = sb.toString();			
		}        
			if ("saveContestFees".equals(operation)) {
				calledOperation = operation;
				long projectId = Long.parseLong(request.getParameter("projectId2"));
				double assemblyFee = Double.parseDouble(request.getParameter("assembly"));
				String studioSubType = request.getParameter("subType");
				double studioFee = Double.parseDouble(request.getParameter("studio"));
				ProjectContestFee fee1 = new ProjectContestFee();
				fee1.setProjectId(projectId);
				fee1.setContestTypeId(14);
				fee1.setContestFee(assemblyFee);
				fee1.setStudio(false);
				
				
				ProjectContestFee fee3 = new ProjectContestFee();
				fee3.setProjectId(projectId);
				if (studioSubType.equalsIgnoreCase("Web Design")){
					fee3.setContestTypeId(1);
				} else if (studioSubType.equalsIgnoreCase("Banners/Icons")){
					fee3.setContestTypeId(4);
				} else if (studioSubType.equalsIgnoreCase("Application Front-End Design")){
					fee3.setContestTypeId(5);
				}
				fee3.setContestFee(studioFee);
				port.saveContestFees(Arrays.asList(fee1,fee3), projectId);

				callResult = String.format("Contest Fees have been saved/refreshed successfully for project with id of %1$d", projectId);
			}
    } catch (Throwable e) {
        error = e;
    }
    if (error != null) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        callResult = "ERROR!<br/>" + sw.getBuffer().toString().replaceAll("\\n", "<br/>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Service Facade Demo</title>
</head>
<body>
<p>Called Admin Service Facade Demo operation: <%=calledOperation%></p>
<p>Result of the call: <%=callResult%></p>
<a href="index.jsp">Back to list of available operations</a>
<p>PLEASE NOTE: A 'null' value or javax.xml.ws.soap.SOAPFaultException:
java.lang.NullPointerException exception means some errors in the service happening including
some illegal parameters.</p>

</body>
</html>
