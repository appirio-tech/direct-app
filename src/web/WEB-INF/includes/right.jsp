<%--
  - Author: Blues, GreatKevin, duxiaoyang, Veve
  - Version: 1.7
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
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
  - Version 1.6 (Release Assembly - TC Cockpit Misc Bug Fixes)
  - - Add context tip for contest status and type icons in the right sidebar
  -
  - Version 1.7 (TopCoder Direct - Change Right Sidebar to pure Ajax)
  - - Remove the JSP statements for populating direct projects and project contests data. It's changed to load
  - the data via ajax.
  -
--%>
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="TCConnectURL" value="<%=ServerConfiguration.TOPCODER_CONNECT_URL%>"/>

<div id="area2" class="dashboardPage"><!-- the right column -->

    <div class="newSidebar">
        <div class="topBtns">
            <a href="${ctx}/copilot/launchCopilotContest" class="copilot"
               title="Finds a TopCoder Copilot for your project">Get a Copilot</a>
            <a href="${TCConnectURL}" target="_blank" class="start" title="Starts a new project">Start
            a Project</a>
            <a href="${ctx}/launch/home" class="launch" title="Launch a new challenge for your project">Launch
                Challenge</a>
        </div>

        <div class="contestList">
            <div class="contestListMask">
                <div class="filter">
                    <label>Select Customer</label>

                    <div class="dropdownWidget customerSelectMask">
                        <div class="inputSelect">
                            <input class="paddingleft" type="text" value="All Customers"
                                   onfocus="showHideCustomerList();" onkeyup="filterCustomer();"
                                   onkeydown="selectCustomer(event);"/>
                            <a href="javascript:;" class="arrow"></a>
                        </div>
                        <div class="contestsDropDown" id="dropDown2">
                            <ul class="dropList">
                                <s:if test="viewData.userProjects.projectsCustomers.size <= 1">
                                    <s:iterator value="viewData.userProjects.projectsCustomers" status="status"
                                                var="customer">
                                        <li
                                        <s:if test="#status.even">class="even"</s:if>>
                                        <a class="longWordsBreak" href="#" id="<s:property value='key'/>"><s:property
                                                value="value"/></a>
                                        </li>
                                    </s:iterator>

                                </s:if>
                                <s:else>
                                    <li class="">
                                        <a id="0" class="longWordsBreak" href="javascript:;">All Customers</a>
                                    </li>
                                    <s:iterator value="viewData.userProjects.projectsCustomers" status="status"
                                                var="customer">
                                        <li
                                        <s:if test="#status.odd">class="even"</s:if>>
                                        <a class="longWordsBreak" href="javascript:;"
                                           id="<s:property value='key'/>"><s:property
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
                                <s:textfield name="sessionData.currentProjectContext.name"
                                             onfocus="showHideProjectList();"
                                             onkeyup="filterProject();" onkeydown="selectProject(event);"
                                             readonly="false"/>
                            </ui:isProjectContextSet>
                            <ui:noProjectContextSet>
                                <s:textfield value="Select a Project" onfocus="showHideProjectList();"
                                             onkeyup="filterProject();" onkeydown="selectProject(event);"
                                             readonly="false"/>
                            </ui:noProjectContextSet>
                        </div>

                        <a href="javascript:;" onclick="showHideProjectList();" class="arrow"></a>

                        <div class="contestsDropDown" id="dropDown1">
                            <ul class="dropList ">
                                <s:iterator value="viewData.userProjects.projects" status="status" var="project">
                                    <li
                                    <s:if test="#status.even">class="even"</s:if>>
                                    <link:projectOverview project="${project}"/>
                                    </li>
                                </s:iterator>
                            </ul>
                        </div>
                    </div>
                    <input type='button' value='Go' class='selectProjectBtn'/>

                    <label>Search</label>

                    <div class="dropdownWidget searchMask">
                        <input type='text'/>
                    </div>
                </div>

                <ui:isProjectContextSet>
                    <div class="tableHeader" id="rightTableHeader">
                        <span class="statusTh down">Status</span>
                        <span class="titleTh">Challenge Title</span>
                        <span class="typeTh">Type</span>
                    </div>
                    <div class="tableBody">
                        <table cellpadding="0" cellspacing="0" id="contestsTable">
                            <colgroup>
                                <col width="42px"/>
                                <col width="144px"/>
                                <col class="last"/>
                            </colgroup>
                            <thead>
                            <tr class="hide"><!-- table header is necessary for the sorting functionality-->
                                <th>c1</th>
                                <th>c2</th>
                                <th>c3</th>
                            </tr>
                            </thead>
                            <tbody>

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
        <a class="switchBtn" href="javascript:;"></a>
    </div>

    <span id="contextPath" value="${ctx}"/>
    <!-- End .box -->

    <!-- help widget -->
    <jsp:include page="helpWidget.jsp"/>

</div>
