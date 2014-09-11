<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.1
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from index.jsp for invoking the selected web service operation
  - with provided parameters and displaying the results of the call. In fact, this page acts like a web service client
  - demonstrating the code which could be used for calling the web service.
  -
  - V1.1: Update in Cockpit Security Facade V1.0 change to new WebService facade.
--%>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.facade.project.ProjectServiceFacadeWebService" %>
<%@ page import="com.topcoder.service.project.ProjectData" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="com.topcoder.clients.model.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        QName serviceName = new QName("http://ejb.project.facade.service.topcoder.com/",
                                      "ProjectServiceFacadeWebServiceBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        ProjectServiceFacadeWebService port = service.getPort(ProjectServiceFacadeWebService.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                         request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Call the appropriate wen service operation passing provided parameters
        if ("createProject".equals(operation)) {
            String name = request.getParameter("pname");
            String desc = request.getParameter("pdesc");
            ProjectData newProject = new ProjectData();
            newProject.setName(name);
            newProject.setDescription(desc);
            newProject = port.createProject(newProject);
            callResult = "Created new project: ID = " + newProject.getProjectId() + ", name = " + newProject.getName()
                         + ", desc = " + newProject.getDescription();
        } /*else if ("updateProject".equals(operation)) {
            String name = request.getParameter("uname");
            String desc = request.getParameter("udesc");
            String uid = request.getParameter("uid");
            ProjectData project = new ProjectData();
            project.setName(name);
            project.setDescription(desc);
            project.setProjectId(new Long(uid));
            port.updateProject(project);
            callResult = "Updated project: ID = " + project.getProjectId() + ", name = " + project.getName()
                         + ", desc = " + project.getDescription();
        } else if ("deleteProject".equals(operation)) {
            String did = request.getParameter("did");
            boolean result = port.deleteProject(Long.parseLong(did));
            callResult = "Deleted project: ID = " + did + ", success = " + result;
        } */else if ("getProject".equals(operation)) {
            String pid = request.getParameter("pid");
            ProjectData project = port.getProject(Long.parseLong(pid));
            callResult = "Retrieved project: ID = " + project.getProjectId() + ", name = " + project.getName()
                         + ", desc = " + project.getDescription();
        } else if ("getProjectsForUser".equals(operation)) {
            String userid = request.getParameter("userid");
            List<ProjectData> projects = port.getProjectsForUser(Long.parseLong(userid));
            StringBuilder b = new StringBuilder();
            for (ProjectData p : projects) {
                b.append("    Project: ID = ").append(p.getProjectId()).append(", name = ").append(p.getName()).
                        append(", desc = ").append(p.getDescription()).append("<br/>");
            }
            callResult = "Retrieved projects for user: " + userid + "<br/>" + b.toString();
        } else if ("getAllProjects".equals(operation)) {
            List<ProjectData> projects = port.getAllProjects();
            StringBuilder b = new StringBuilder();
            for (ProjectData p : projects) {
                b.append("    Project: ID = ").append(p.getProjectId()).append(", name = ").append(p.getName()).
                        append(", desc = ").append(p.getDescription()).append("<br/>");
            }
            callResult = "Retrieved all projects:<br/>" + b.toString();
        }/* else if ("getClientProjectsForClient".equals(operation)) {
        	String clientId = request.getParameter("clientId");
        	Client client = port.retrieveClientById(Long.parseLong(clientId));
        	List<Project> clientProjects = port.getClientProjectsForClient(client);
        	StringBuilder b = new StringBuilder();
            for (Project p : clientProjects) {
                b.append("    ClientProject: ID = ").append(p.getId()).append(", name = ").append(p.getName()).
                        append(", desc = ").append(p.getDescription()).
                        append(", company: name = ").append(p.getCompany().getName()).append("<br/>");
            }
            callResult = "Retrieved all Client projects:<br/>" + b.toString();        	
        } else if ("retrieveClientById".equals(operation)) {
        	String clientId = request.getParameter("clientId2");
        	Client client = port.retrieveClientById(Long.parseLong(clientId));
        	callResult = "Retrieved Client:<br/> Id=" + client.getId() + ", name =" + client.getName() + ", status=" +
        			client.getClientStatus().getName() + "<br/>";
        } else if ("retrieveAllClients".equals(operation)) {
        	List<Client> clients = port.retrieveAllClients();
            StringBuilder b = new StringBuilder();
            for (Client c : clients) {
                b.append("    Client: ID = ").append(c.getId()).append(", name = ").append(c.getName()).append("<br/>");
            }
            callResult = "Retrieved all Clients:<br/>" + b.toString();
        } else if ("searchClientsByName".equals(operation)) {
        	String clientName = request.getParameter("clientName");        	
        	List<Client> clients = port.searchClientsByName(clientName);
            StringBuilder b = new StringBuilder();
            for (Client c : clients) {
                b.append("    Client: ID = ").append(c.getId()).append(", name = ").append(c.getName()).append("<br/>");
            }
            callResult = "Search results Clients:<br/>" + b.toString();
        } else if("saveClient".equals(operation)) {
        	String clientId = request.getParameter("clientId3");
        	String clientName = request.getParameter("clientName3");
        	Client client = null;
        	if (clientId == null || clientId.trim().length() == 0) {
        		client = port.retrieveClientById(Long.parseLong(clientId));
        		client.setName(clientName);
        		client = port.saveClient(client);
        	} else {
        		client = new Client();
        		client.setName(clientName);
        		client = port.saveClient(client);
        	}
        	callResult = "Upsert Client:<br/> Id=" + client.getId() + ", name =" + client.getName() + "<br/>";
        } else if("deleteClient".equals(operation)) {
        	String clientId = request.getParameter("clientId4");
        	Client client = port.retrieveClientById(Long.parseLong(clientId));
        	port.deleteClient(client);
        	callResult = "Deleted Client: ID = " + clientId;
        } else if ("getClientsWithStatus".equals(operation)) {
        	String clientStatusId = request.getParameter("clientStatusId");
        	ClientStatus status = port.retrieveClientStatusById(Long.parseLong(clientStatusId));
        	List<Client> clients = port.getClientsWithStatus(status);
        	StringBuilder b = new StringBuilder();
            for (Client p : clients) {
                b.append("    Client: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved clients with status:<br/>" + b.toString();        	
        } else if("retrieveClientStatusById".equals(operation)) {
        	String clientStatusId = request.getParameter("clientStatusId2");
        	ClientStatus status = port.retrieveClientStatusById(Long.parseLong(clientStatusId));
        	callResult = "Retrieved ClientStatus:<br/> Id=" + status.getId() + ", name =" + status.getName() + ", desc=" +
        		status.getDescription() + "<br/>";
        } else if("retrieveAllClientStatus".equals(operation)) {
        	List<ClientStatus> status = port.retrieveAllClientStatus();        	
        	StringBuilder b = new StringBuilder();
            for (ClientStatus p : status) {
                b.append("    ClientStatus: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved all client status:<br/>" + b.toString();   
        } else if("searchClientStatusByName".equals(operation)){
        	String clientStatusName = request.getParameter("clientStatusName");
        	List<ClientStatus> status = port.searchClientStatusByName(clientStatusName);        	
        	StringBuilder b = new StringBuilder();
            for (ClientStatus p : status) {
                b.append("    ClientStatus: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved client status:<br/>" + b.toString();   
        } else if("saveClientStatus".equals(operation)) {
        	String id = request.getParameter("clientStatusId3");
        	String name = request.getParameter("clientStatusName3");
        	ClientStatus status = null;
        	if (id == null || id.trim().length() == 0) {
        		status = port.retrieveClientStatusById(Long.parseLong(id));
        		status.setName(name);
        		status = port.saveClientStatus(status);
        	} else {
        		status = new ClientStatus();
        		status.setName(name);
        		status = port.saveClientStatus(status);
        	}
        	callResult = "Upsert ClientStatus:<br/> Id=" + status.getId() + ", name =" + status.getName() + "<br/>";
        } else if("deleteClientStatus".equals(operation)) {
        	String id = request.getParameter("clientStatusId4");
        	ClientStatus status = port.retrieveClientStatusById(Long.parseLong(id));
        	port.deleteClientStatus(status);
        	callResult = "Delete ClientStatus Client: ID = " + id;
        } else if ("getClientsForCompany".equals(operation)) {
        	String companyId = request.getParameter("companyId");
        	Company company = port.retrieveCompanyById(Long.parseLong(companyId));
        	List<Client> clients = port.getClientsForCompany(company);
        	StringBuilder b = new StringBuilder();
            for (Client p : clients) {
                b.append("    Client: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved clients with company:<br/>" + b.toString();        	
        } else if ("getClientProjectsForCompany".equals(operation)) {
        	String companyId = request.getParameter("companyId5");
        	Company company = port.retrieveCompanyById(Long.parseLong(companyId));
        	List<Project> projects = port.getClientProjectsForCompany(company);
        	StringBuilder b = new StringBuilder();
            for (Project p : projects) {
                b.append("    Client: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved projects with company:<br/>" + b.toString();        	
        } else if("retrieveCompanyById".equals(operation)) {
        	String companyId = request.getParameter("companyId2");
        	Company company = port.retrieveCompanyById(Long.parseLong(companyId));
        	callResult = "Retrieved Company:<br/> Id=" + company.getId() + ", name =" + company.getName() + "<br/>";
        } else if("retrieveAllCompanies".equals(operation)) {
        	List<Company> company = port.retrieveAllCompanies();        	
        	StringBuilder b = new StringBuilder();
            for (Company p : company) {
                b.append("    Company: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved all company:<br/>" + b.toString();   
        } else if("searchCompaniesByName".equals(operation)){
        	String companyName = request.getParameter("companyName");
        	List<Company> company = port.searchCompaniesByName(companyName);        	
        	StringBuilder b = new StringBuilder();
            for (Company p : company) {
                b.append("    Company: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved companies:<br/>" + b.toString();   
        } else if("saveCompany".equals(operation)) {
        	String id = request.getParameter("companyId3");
        	String name = request.getParameter("companyName3");
        	Company company = null;
        	if (id == null || id.trim().length() == 0) {
        		company = port.retrieveCompanyById(Long.parseLong(id));
        		company.setName(name);
        		company = port.saveCompany(company);
        	} else {
        		company = new Company();
        		company.setName(name);
        		company = port.saveCompany(company);
        	}
        	callResult = "Upsert Company:<br/> Id=" + company.getId() + ", name =" + company.getName() + "<br/>";
        } else if("deleteCompany".equals(operation)) {
        	String id = request.getParameter("companyId4");
        	Company company = port.retrieveCompanyById(Long.parseLong(id));
        	port.deleteCompany(company);
        	callResult = "Delete Company : ID = " + id;
        } else if ("retrieveClientProjectById".equals(operation)) {
        	String projectId = request.getParameter("projectId");
        	String includeChildren = request.getParameter("includeChildren");
        	Project project = port.retrieveClientProjectById(Long.parseLong(projectId), Boolean.parseBoolean(includeChildren));        	
        	StringBuilder b = new StringBuilder();
            b.append("    Project: ID = ").append(project.getId()).append(", name = ").append(project.getName()).append("<br/>");
            if ( project.getChildProjects() != null) {
            	for (Project p : project.getChildProjects()) {
                    b.append("         client project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
                }
            }
            callResult = "Retrieved project:<br/>" + b.toString();        	
        } else if ("retrieveClientProjectById2".equals(operation)) {
        	String projectId = request.getParameter("projectId2");
        	Project project = port.retrieveClientProjectByProjectId(Long.parseLong(projectId));        	
        	StringBuilder b = new StringBuilder();
            b.append("    Project: ID = ").append(project.getId()).append(", name = ").append(project.getName()).append("<br/>");
            if ( project.getChildProjects() != null) {
            	for (Project p : project.getChildProjects()) {
                    b.append("         client project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
                }
            }
            callResult = "Retrieved project:<br/>" + b.toString();        	
        } else if("retrieveClientProjects".equals(operation)) {
        	String includeChildren = request.getParameter("includeChildren");
        	List<Project> projects = port.retrieveClientProjects(Boolean.parseBoolean(includeChildren));
        	StringBuilder b = new StringBuilder();
        	for (Project p : projects) {
                b.append(" Project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
                if ( p.getChildProjects() != null) {
                	for (Project sp : p.getChildProjects()) {
                        b.append("         client project : ID = ").append(sp.getId()).append(", name = ").append(sp.getName()).append("<br/>");
                    }
                }
            }
        	callResult = "Retrieved Projects:<br/> Id=" + b.toString();
        } else if("retrieveAllClientProjects2".equals(operation)) {        	
        	List<Project> projects = port.retrieveAllClientProjects();
        	StringBuilder b = new StringBuilder();
        	for (Project p : projects) {
                b.append(" Project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
                if ( p.getChildProjects() != null) {
                	for (Project sp : p.getChildProjects()) {
                        b.append("         client project : ID = ").append(sp.getId()).append(", name = ").append(sp.getName()).append("<br/>");
                    }
                }
            }
        	callResult = "Retrieved Projects:<br/> Id=" + b.toString();
        } else if("searchClientProjectsByName".equals(operation)){
        	String projectName = request.getParameter("projectName");
        	List<Project> project = port.searchClientProjectsByName(projectName);        	
        	StringBuilder b = new StringBuilder();
            for (Project p : project) {
                b.append("    Project: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved projects :<br/>" + b.toString();   
        } else if("saveClientProject".equals(operation)) {
        	String id = request.getParameter("projectId3");
        	String name = request.getParameter("projectName3");
        	Project project = null;
        	if (id == null || id.trim().length() == 0) {
        		project = port.retrieveClientProjectByProjectId(Long.parseLong(id));
        		project.setName(name);
        		project = port.saveClientProject(project);
        	} else {
        		project = new Project();
        		project.setName(name);
        		project = port.saveClientProject(project);
        	}
        	callResult = "Upsert Project:<br/> Id=" + project.getId() + ", name =" + project.getName() + "<br/>";
        } else if("deleteClientProject".equals(operation)) {
        	String id = request.getParameter("projectId4");
        	Project project = port.retrieveClientProjectByProjectId(Long.parseLong(id));
        	port.deleteClientProject(project);
        	callResult = "Delete Project : ID = " + id;
        } else if("getClientProjectsWithStatus".equals(operation)) {
        	String statusId = request.getParameter("projectStatusId");
        	ProjectStatus status = port.retrieveProjectStatusById(Long.parseLong(statusId));
        	List<Project> projects = port.getClientProjectsWithStatus(status);
        	StringBuilder b = new StringBuilder();
        	for (Project p : projects) {
                b.append(" Project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
                if ( p.getChildProjects() != null) {
                	for (Project sp : p.getChildProjects()) {
                        b.append("         client project : ID = ").append(sp.getId()).append(", name = ").append(sp.getName()).append("<br/>");
                    }
                }
            }
        	callResult = "Retrieved Projects with status:<br/> Id=" + b.toString();
        } else if ("retrieveProjectStatusById".equals(operation)) {
        	String projectStatusId = request.getParameter("projectStatusId");
        	ProjectStatus status = port.retrieveProjectStatusById(Long.parseLong(projectStatusId));        	
        	StringBuilder b = new StringBuilder();
            b.append("    ProjectStatus: ID = ").append(status.getId()).append(", name = ").append(status.getName()).append("<br/>");            
            callResult = "Retrieved <br/>" + b.toString();        	
        } else if("retrieveAllProjectStatus".equals(operation)) {        	
        	List<ProjectStatus> projects = port.retrieveAllProjectStatus();
        	StringBuilder b = new StringBuilder();
        	for (ProjectStatus p : projects) {
                b.append(" ProjectStatus : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");                
            }
        	callResult = "Retrieved ProjectStatus:<br/> Id=" + b.toString();
        } else if("searchProjectStatusByName".equals(operation)){
        	String projectName = request.getParameter("projectStatusName");
        	List<ProjectStatus> project = port.searchProjectStatusByName(projectName);        	
        	StringBuilder b = new StringBuilder();
            for (ProjectStatus p : project) {
                b.append("    ProjectStatus: ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");
            }
            callResult = "Retrieved ProjectStatus list :<br/>" + b.toString();   
        } else if("saveProjectStatus".equals(operation)) {
        	String id = request.getParameter("projectStatusId3");
        	String name = request.getParameter("projectStatusName3");
        	ProjectStatus project = null;
        	if (id == null || id.trim().length() == 0) {
        		project = port.retrieveProjectStatusById(Long.parseLong(id));
        		project.setName(name);
        		project = port.saveProjectStatus(project);
        	} else {
        		project = new ProjectStatus();
        		project.setName(name);
        		project = port.saveProjectStatus(project);
        	}
        	callResult = "Upsert ProjectStatus:<br/> Id=" + project.getId() + ", name =" + project.getName() + "<br/>";
        } else if("deleteProjectStatus".equals(operation)) {
        	String id = request.getParameter("projectStatusId4");
        	ProjectStatus project = port.retrieveProjectStatusById(Long.parseLong(id));
        	port.deleteProjectStatus(project);
        	callResult = "Delete ProjectStatus : ID = " + id;
        }*/ else if("getClientProjectsByUserId".equals(operation)) {
        	String userId = request.getParameter("userIDDDD");
        	
        	List<Project> projects = port.getClientProjectsByUser();
        	StringBuilder b = new StringBuilder();
        	for (Project p : projects) {
                b.append(" Project : ID = ").append(p.getId()).append(", name = ").append(p.getName()).append("<br/>");                
            }
        	callResult = "Retrieved Projects:<br/> Id=" + b.toString();
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
<html>
  <head>
      <title>Project Service Facade Demo</title>
  </head>
  <body>
      <p>Called Project Service Facade operation: <%=calledOperation%></p>
      <p>Result of the call: <%=callResult%></p>
      <a href="index.jsp">Back to list of available operations</a>
  </body>
</html>
