<%--
  - Author: Blues, GreatKevin, duxiaoyang
  - Version: 1.5
  - Copyright (C) 2010-2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: The footer of the topcoder cockpit.
  -
  - Version 1.1  (Release Assembly - TC Cockpit Sidebar Header and Footer Update changes:
  - 1) updated the style of direct project dropdown.
  - 2) updated the style of project contests list
  - 3) added the help widget.
  -
  - Version 1.2 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination) changes:
  - 1) Add a new customer dropdown.
  - 2) updated the logic of showing project dropdown
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R1) changes:
  - 1) Change create a new project button to new one.
  -
  - Version 1.4 (Release Assembly - TC Direct Project Forum Configuration Assembly 2) changes:
  - Added quotes for rightSidebarData to avoid JavaScript error.
  -
  - Version 1.5 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0) changes:
  - Matched font size title for sub dropdown on "Select Customer" and "Select Project". 
  - Made both read only.
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="area2" class="dashboardPage"><!-- the right column -->

    <script type="text/javascript">
         var rightSidebarData = <s:property value="viewData.userProjects.jsonResult" default="''" escape="false"/>;
         var currentProjectName = '<s:property value="sessionData.currentProjectContext.name"/>';
         var isInProjectScope = ('project' == $.trim("<s:property value='#request.PAGE_TYPE'/>"));
         var currentProjectContests;
    </script>
    
    <div class="newSidebar">
        <div class="topBtns">
            <a href="${ctx}/copilot/launchCopilotContest" class="copilot">Get a Copilot</a>
            <a href="<s:url action="createNewProject" namespace="/"/>" class="start">Start a Project</a>
            <a href="${ctx}/launch/home" class="launch">Launch Contest</a>
        </div>
        
        
        
        <div class="contestList">
            <div class="contestListMask">
                <div class="filter">
                    <label>Select Customer</label>
                    
                    <div class="dropdownWidget customerSelectMask">
                        <div class="inputSelect">
                            <input class="paddingleft" type="text" value="All Customers" onfocus="showHideCustomerList();" onkeyup="filterCustomer();" onkeydown="selectCustomer(event);" />
                            <a href="javascript:;" class="arrow"></a>
                        </div>
                        <div class="contestsDropDown" id="dropDown2">
                            <ul class="dropList">
                                <s:if test="viewData.userProjects.projectsCustomers.size <= 1">
                                    <s:iterator value="viewData.userProjects.projectsCustomers" status="status" var="customer">
                                        <li <s:if test="#status.even">class="even"</s:if>>
                                            <a class="longWordsBreak" href="#" id="<s:property value='key'/>"><s:property
                                                    value="value"/></a>
                                        </li>
                                    </s:iterator>
                                                                        
                                </s:if>
                                <s:else>
                                    <li class="">
                                        <a id="0" class="longWordsBreak" href="#">All Customers</a>
                                    </li>
                                    <s:iterator value="viewData.userProjects.projectsCustomers" status="status" var="customer">
                                        <li <s:if test="#status.odd">class="even"</s:if>>
                                            <a class="longWordsBreak" href="#" id="<s:property value='key'/>"><s:property
                                                    value="value"/></a>
                                        </li>
                                    </s:iterator>
                                </s:else>  
                            </ul>
                        </div>
                    </div>
                   
                    <label>Select Project</label>
                    
                    <div class="dropdownWidget projectSelectMask">
                        <div class="inputSelect">
                        <ui:isProjectContextSet>
                            <s:textfield name="sessionData.currentProjectContext.name" onfocus="showHideProjectList();"
                                         onkeyup="filterProject();" onkeydown="selectProject(event);" readonly="false" />
                        </ui:isProjectContextSet>
                        <ui:noProjectContextSet>
                            <s:textfield value="Select a Project" onfocus="showHideProjectList();" onkeyup="filterProject();" onkeydown="selectProject(event);" readonly="false"/>
                        </ui:noProjectContextSet>
                        </div>
                        
                        <a href="javascript:;" onclick="showHideProjectList();" class="arrow"></a>
                        
                        <div class="contestsDropDown" id="dropDown1" >                        
                            <ul class="dropList ">
                                <s:iterator value="viewData.userProjects.projects" status="status" var="project">
                                    <li <s:if test="#status.even">class="even"</s:if>>
                                        <link:projectOverview project="${project}"/>
                                    </li>
                                </s:iterator>                            
                            </ul>
                        </div>
                    </div>
                </div>
                
                <ui:isProjectContextSet>
                    <div class="tableHeader" id="rightTableHeader">
                        <span class="statusTh down">Status</span>
                        <span class="titleTh">Contest Title</span>
                        <span class="typeTh">Type</span>
                    </div>
                    <div class="tableBody">                        
                        <table cellpadding="0" cellspacing="0" id="contestsTable">
                            <colgroup>
                                <col width="42px" />
                                <col width="144px" />
                                <col class="last" />
                            </colgroup>
                            <thead>
                            <tr class="hide"><!-- table header is necessary for the sorting functionality-->
                                <th>c1</th>
                                <th>c2</th>
                                <th>c3</th>
                            </tr>
                            </thead>                            
                            <tbody>                                
                                <s:iterator value="sessionData.currentProjectContests" status="status" var="contest">
                                    <tr <s:if test="#status.even">class="even"</s:if> 
                                    <s:if test="contestType.name == 'Copilot Posting'">
                                    onclick="document.location.href = '<s:url action="copilotContestDetails" namespace="/copilot"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>'; 
                                    </s:if>
                                    <s:if test="contestType.name != 'Copilot Posting'">
                                    onclick="document.location.href = '<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>'; 
                                    </s:if>
                                    
                                    this.style.cursor='pointer';">
                                        <td>
                                            <span class="<s:property value="status.shortName"/>"></span>
                                        </td>
                                        <td class="leftAlign">
                                            <c:out value="${contest.title}" />
                                        </td>
                                        <td>
                                            <img src="/images/<s:property value="contestType.letter"/>_small.png"
                                                     alt="<s:property value="contestType.letter"/>"/>
                                        </td>
                                    </tr>
                                </s:iterator>                                
                                
                            </tbody>
                        </table>
                    </div>
                </ui:isProjectContextSet>
                <div class="tl"></div>
                <div class="tr"></div>
                <div class="bl"></div>
                <div class="br"></div>
            </div>
        </div>        
        
        <div class="archiveLink">
            <a href="<s:url action='allProjects'/>">All Projects</a>
        </div>           
    </div>

    <span id="contextPath" value="${ctx}"/>
    <!-- End .box -->

    <!-- help widget -->
    <jsp:include page="helpWidget.jsp"/>

</div>
