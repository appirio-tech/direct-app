<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the TopCoder Project Service Facade web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the Project Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Project Service Facade Demo</title>
    <script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
</head>

<body>
<p>Project Service Facade Demo
    (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                        ? "Cockpit Administrator" : "Cockpit User"%>))</p><br/><br/>
<span style="color:blue"><b>Project Services Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ProjectService.createProject(ProjectData) <br/>&nbsp; &nbsp;
        Project Name: <input type="text" name="pname" value=""> &nbsp; &nbsp;
        Project Description: <input type="text" name="pdesc" value=""> &nbsp; : &nbsp;
        <input type="button" value="Execute" onclick="callService('createProject');"/></li><br/>
    <li>ProjectService.updateProject(ProjectData) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="uid" value=""> &nbsp; &nbsp;
        Project Name: <input type="text" name="uname" value=""> &nbsp; &nbsp;
        Project Description: <input type="text" name="udesc" value=""> &nbsp; : &nbsp;
        <input type="button" value="Execute" onclick="callService('updateProject');"/></li><br/>
    <li>ProjectService.deleteProject(long) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="did" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteProject');"/></li><br/>
    <li>ProjectService.getProject(long) <br/>&nbsp; &nbsp;
        Project ID: <input type="text" name="pid" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getProject');"/></li><br/>
    <li>ProjectService.getGetProjectsForUser(long) <br/>&nbsp; &nbsp;
        User ID: <input type="text" name="userid" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getProjectsForUser');"/></li><br/>
    <li>ProjectService.getAllProjects() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllProjects');"/></li><br/>
    
    <p><b>New added methods:</b></p>    
    <li>client_project_entities_dao#clientDAO#.getClientProjectsForClient(Client) &nbsp; &nbsp;
    	Client ID:<input type="text" name="clientId" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientProjectsForClient');"/></li><br/> 
    <li>client_project_entities_dao#clientDAO#.retrieveClientById(long) &nbsp; &nbsp;
    	Client ID:<input type="text" name="clientId2" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveClientById');"/></li><br/> 
    <li>client_project_entities_dao#clientDAO#.retrieveAllClients() &nbsp; &nbsp;    	
        <input type="button" value="Execute" onclick="callService('retrieveAllClients');"/></li><br/> 
    <li>client_project_entities_dao#clientDAO#.searchClientsByName(String) &nbsp; &nbsp;   
     	Client Name:<input type="text" name="clientName" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('searchClientsByName');"/></li><br/> 
    <li>client_project_entities_dao#clientDAO#.saveClient(Client) (input id to update while not input id as create new one)&nbsp; &nbsp;   
     	Client ID:<input type="text" name="clientId3" value=""> &nbsp; &nbsp;
     	Client Name:<input type="text" name="clientName3" value=""> &nbsp; &nbsp;     	
        <input type="button" value="Execute" onclick="callService('saveClient');"/></li><br/>         
     <li>client_project_entities_dao#clientDAO#.deleteClient(Client) &nbsp; &nbsp;
    	Client ID:<input type="text" name="clientId4" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteClient');"/></li><br/> 
        
    <li>client_project_entities_dao#clientStatusDAO#.getClientsWithStatus(ClientStatus) &nbsp; &nbsp;
    	ClientStatus ID:<input type="text" name="clientStatusId" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientsWithStatus');"/></li><br/> 
    <li>client_project_entities_dao#clientStatusDAO#.retrieveClientStatusById(long) &nbsp; &nbsp;
    	ClientStatus ID:<input type="text" name="clientStatusId2" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveClientStatusById');"/></li><br/> 
    <li>client_project_entities_dao#clientStatusDAO#.retrieveAllClientStatus() &nbsp; &nbsp;    	
        <input type="button" value="Execute" onclick="callService('retrieveAllClientStatus');"/></li><br/> 
    <li>client_project_entities_dao#clientStatusDAO#.searchClientStatusByName(String) &nbsp; &nbsp;   
     	ClientStatus Name:<input type="text" name="clientStatusName" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('searchClientStatusByName');"/></li><br/> 
    <li>client_project_entities_dao#clientStatusDAO#.saveClientStatus(ClientStatus) (input id to update while not input id as create new one)&nbsp; &nbsp;   
     	ClientStatus ID:<input type="text" name="clientStatusId3" value=""> &nbsp; &nbsp;
     	ClientStatus Name:<input type="text" name="clientStatusName3" value=""> &nbsp; &nbsp;     	
        <input type="button" value="Execute" onclick="callService('saveClientStatus');"/></li><br/>         
     <li>client_project_entities_dao#clientStatusDAO#.deleteClientStatus(ClientStatus) &nbsp; &nbsp;
    	ClientStatus ID:<input type="text" name="clientStatusId4" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteClientStatus');"/></li><br/> 
        
    <li>client_project_entities_dao#CompanyDAO#.getClientsForCompany(Company) &nbsp; &nbsp;
    	Company ID:<input type="text" name="companyId" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientsForCompany');"/></li><br/> 
    <li>client_project_entities_dao#CompanyDAO#.getClientProjectsForCompany(Company) &nbsp; &nbsp;
    	Company ID:<input type="text" name="companyId5" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientProjectsForCompany');"/></li><br/>     
        
    <li>client_project_entities_dao#CompanyDAO#.retrieveCompanyById(long) &nbsp; &nbsp;
    	Company ID:<input type="text" name="companyId2" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveCompanyById');"/></li><br/> 
    <li>client_project_entities_dao#CompanyDAO#.retrieveAllCompanies() &nbsp; &nbsp;    	
        <input type="button" value="Execute" onclick="callService('retrieveAllCompanies');"/></li><br/> 
    <li>client_project_entities_dao#CompanyDAO#.searchCompaniesByName(String) &nbsp; &nbsp;   
     	Company Name:<input type="text" name="companyName" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('searchCompaniesByName');"/></li><br/> 
    <li>client_project_entities_dao#CompanyDAO#.saveCompany(Company) (input id to update while not input id as create new one)&nbsp; &nbsp;   
     	Company ID:<input type="text" name="companyId3" value=""> &nbsp; &nbsp;
     	Company Name:<input type="text" name="companyName3" value=""> &nbsp; &nbsp;     	
        <input type="button" value="Execute" onclick="callService('saveCompany');"/></li><br/>         
     <li>client_project_entities_dao#CompanyDAO#.deleteCompany(Company) &nbsp; &nbsp;
    	Company ID:<input type="text" name="companyId4" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteCompany');"/></li><br/>       
        
    <li>client_project_entities_dao#ProjectDAO#.retrieveClientProjectById(Long id, boolean includeChildren) &nbsp; &nbsp;
    	Project ID:<input type="text" name="projectId" value=""> &nbsp; &nbsp;
    	includeChildOrNot: <input type="checkbox" name="includeChildren"> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveClientProjectById');"/></li><br/> 
    <li>client_project_entities_dao#ProjectDAO#.retrieveClientProjects(includeChildren) &nbsp; &nbsp;
    	includeChildOrNot: <input type="checkbox" name="includeChildren2"> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveClientProjects');"/></li><br/>             
    <li>client_project_entities_dao#ProjectDAO#.retrieveClientProjectByProjectId(long) &nbsp; &nbsp;
    	Project ID:<input type="text" name="projectId2" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveClientProjectById2');"/></li><br/> 
    <li>client_project_entities_dao#ProjectDAO#.retrieveAllClientProjects() &nbsp; &nbsp;    	
        <input type="button" value="Execute" onclick="callService('retrieveAllClientProjects2');"/></li><br/> 
    <li>client_project_entities_dao#ProjectDAO#.searchClientProjectsByName(String) &nbsp; &nbsp;   
     	Project Name:<input type="text" name="projectName" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('searchClientProjectsByName');"/></li><br/> 
    <li>client_project_entities_dao#ProjectDAO#.saveClientProject(Project) (input id to update while not input id as create new one)&nbsp; &nbsp;   
     	Project ID:<input type="text" name="projectId3" value=""> &nbsp; &nbsp;
     	Project Name:<input type="text" name="projectName3" value=""> &nbsp; &nbsp;     	
        <input type="button" value="Execute" onclick="callService('saveClientProject');"/></li><br/>         
     <li>client_project_entities_dao#ProjectDAO#.deleteClientProject(Project) &nbsp; &nbsp;
    	Project ID:<input type="text" name="projectId4" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteClientProject');"/></li><br/> 
        
        
     <li>client_project_entities_dao#ProjectStatusDAO#.getClientProjectsWithStatus(ProjectStatus) &nbsp; &nbsp;
    	ProjectStatus ID:<input type="text" name="projectStatusId" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientProjectsWithStatus');"/></li><br/> 
     <li>client_project_entities_dao#ProjectStatusDAO#.retrieveProjectStatusById(long) &nbsp; &nbsp;
    	ProjectStatus ID:<input type="text" name="projectStatusId2" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('retrieveProjectStatusById');"/></li><br/> 
    <li>client_project_entities_dao#ProjectStatusDAO#.retrieveAllProjectStatus() &nbsp; &nbsp;    	
        <input type="button" value="Execute" onclick="callService('retrieveAllProjectStatus');"/></li><br/> 
    <li>client_project_entities_dao#ProjectStatusDAO#.searchProjectStatusByName(String) &nbsp; &nbsp;   
     	ProjectStatus Name:<input type="text" name="projectStatusName" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('searchProjectStatusByName');"/></li><br/> 
    <li>client_project_entities_dao#ProjectStatusDAO#.saveProjectStatus(ProjectStatus) (input id to update while not input id as create new one)&nbsp; &nbsp;   
     	ProjectStatus ID:<input type="text" name="projectStatusId3" value=""> &nbsp; &nbsp;
     	ProjectStatus Name:<input type="text" name="projectStatusName3" value=""> &nbsp; &nbsp;     	
        <input type="button" value="Execute" onclick="callService('saveProjectStatus');"/></li><br/>         
     <li>client_project_entities_dao#ProjectStatusDAO#.deleteProjectStatus(ProjectStatus) &nbsp; &nbsp;
    	ProjectStatus ID:<input type="text" name="projectStatusId4" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('deleteProjectStatus');"/></li><br/>      
      
      <li>getClientProjectsByUser(Long userId) &nbsp; &nbsp;
    	User ID:<input type="text" name="userIDDDD" value=""> &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getClientProjectsByUserId');"/></li><br/>                     
</ul>
</form>
<br/>
<br/>
</body>
</html>
