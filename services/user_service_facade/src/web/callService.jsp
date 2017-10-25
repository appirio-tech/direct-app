<%--
  - Author: TCSASSEMBLER
  - Date: 28th June 2009
  - Version: 1.1
  - Since: Jira & Confluence User Sync Service
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from index.jsp for invoking the selected web service operation
  - with provided parameters and displaying the results of the call. In fact, this page acts like a web service client
  - demonstrating the code which could be used for calling the web service.
  -
  - V1.1: Update in Cockpit Security Facade V1.0 change to new WebService facade.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.xml.namespace.QName"%>
<%@ page import="java.net.URL"%>
<%@ page import="javax.xml.ws.Service"%>
<%@ page import="org.jboss.ws.core.StubExt"%>
<%@ page import="javax.xml.ws.BindingProvider"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.StringWriter"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.topcoder.service.facade.user.UserServiceFacadeWebService"%>
<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;
    try {
        // Determine the requested operation
        String operation = request.getParameter("operation");
        calledOperation = "operation";

        // Obtain a client stub for accessing the web service
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.user.facade.service.topcoder.com/", "UserServiceFacadeWebServiceBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        UserServiceFacadeWebService port = service.getPort(UserServiceFacadeWebService.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Call the appropriate wen service operation passing provided parameters
        if ("getJiraUser".equals(operation)) {
            String handle = request.getParameter("handle_jira");
            String email = port.getJiraUser(handle);
            callResult = "Got email id from the Jira User Service = " + email;
        } else if ("getConfluenceUser".equals(operation)) {
            String handle = request.getParameter("handle_confluence");
            String email = port.getConfluenceUser(handle);
            callResult = "Got email id from the Confluence User Service = " + email;
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
<title>User Sync Service Demo</title>
</head>
<body>
<p>**Note: A 'null' value or javax.xml.ws.soap.SOAPFaultException:
java.lang.NullPointerException exception means user has not been created
by the remote (Jira / Confluence) service.**</p>
<p />
<p>Called User Sync Service Demo operation: <%=calledOperation%></p>
<p>Result of the call: <%=callResult%></p>
<a href="index.jsp">Back to list of available operations</a>
</body>
</html>