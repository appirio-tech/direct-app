<%--
  - Author: Blues, GreatKevin
  - Version: 1.3
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
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
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="area2" class="dashboardPage"><!-- the right column -->

    <script type="text/javascript">
         var rightSidebarData = <s:property value="viewData.userProjects.jsonResult" escape="false"/>;
         var currentProjectName = '<s:property value="sessionData.currentProjectContext.name"/>';
         var isInProjectScope = ('project' == $.trim("<s:property value='#request.PAGE_TYPE'/>"));
         var currentProjectContests;
    </script>

    <div class="box">
        <a href="${ctx}/copilot/launchCopilotContest" class="button2">Get A Copilot</a>
        <br />
        <a href="${ctx}/launch/home" class="button2">Launch New Contest</a>
    </div>
    <span id="contextPath" value="${ctx}"/>
    <!-- End .box -->
    <div class="box">
        <h2 class="title">My Projects</h2>
        <!-- solve your problem -->
        <div class="solveProblem">

            <div class="inner">
                <h3>We can help you!</h3>

                <p>Click below to start working with the community.</p>

                <div class="startButtonBox">
                    <a href="<s:url action="createNewProject" namespace="/"/>" class="button2">Start Your New Project Now</a>
                </div>
            </div>

            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>

        </div>
        <!-- End .solveProblem -->

        <div class="contestsContainer">

            <div class="customerSelectMask selectMask">
                <p class="label">Select a Customer</p>

                <div class="inputSelect">
                    <input type="text" name="" value="All Customers">
                    <a class="selectIco" href="javascript:;"></a>
                </div>

                <div id="dropDown2" class="contestsDropDown">
                    <!-- when the user click the selectIco button we will show this dropdown list -->
                    <ul>

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


            <div class="projectSelectMask selectMask">
                <p class="label">Select a Project</p>

                <div class="inputSelect">
                    <ui:isProjectContextSet>
                        <s:textfield name="sessionData.currentProjectContext.name" onfocus="showHideProjectList();"
                                     onkeyup="filterProject();"/>
                    </ui:isProjectContextSet>
                    <ui:noProjectContextSet>
                        <s:textfield value="Select a Project" onfocus="showHideProjectList();" onkeyup="filterProject();"/>
                    </ui:noProjectContextSet>
                    <a href="javascript:;" onclick="showHideProjectList();" class="selectIco"></a>
                </div>

                <div id="dropDown1" class="contestsDropDown">
                    <!-- when the user click the selectIco button we will show this dropdown list -->
                    <ul>
                        <s:iterator value="viewData.userProjects.projects" status="status" var="project">
                            <li <s:if test="#status.even">class="even"</s:if>>
                                <link:projectOverview project="${project}"/>
                            </li>
                        </s:iterator>
                    </ul>
                </div>

            </div>
            <!-- End .contestsDropDown -->

            <ui:isProjectContextSet>
                <div class="contestsList">
                    <!-- This will contain the list of contests -->
                    <div class="caption">
                        <label>Sort Contests by &nbsp;</label>
                        <select id="sortTableBy" onchange="sortTable(this);">
                            <option value="title">Title</option>
                            <option value="status">Status</option>
                            <option value="type">Type</option>
                        </select>
                    </div>
                    <!-- End .caption -->
                    <div class="contestsContent">
                        <table id="contestsTable" width="100%" class="table-sidebar">
                            <thead>
                            <tr class="hide"><!-- table header is necessary for the sorting functionality-->
                                <th>c1</th>
                                <th>c2</th>
                                <th>c3</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="sessionData.currentProjectContests" status="status" var="contest">
                            	<tr <s:if test="#status.even">class="even"</s:if> onclick="document.location.href = '<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>'; this.style.cursor='pointer';">
                                    <td class="status" width="15%">
                                        <span class="<s:property value="status.shortName"/>"><span>a</span></span>
                                    </td>
                                    <td width="70%">
                                        ${contest.title}
                                    </td>
                                    <td class="type" width="15%">

                                        <div style="display: table-cell; vertical-align: middle;">
                                            <img src="/images/<s:property value="contestType.letter"/>_small.png"
                                                 alt="<s:property value="contestType.letter"/>"/>

                                        </div>
                                    </td>
                                </tr>
                            </s:iterator>

                            </tbody>
                        </table>
                    </div>
                    <!-- End .contestsContent -->
                </div>
                <!-- End .contestsList -->
            </ui:isProjectContextSet>

            <p class="projectArchive"><a href="<s:url action='allProjects'/>">Project Archive</a></p>
        </div>
        <!-- End .contestsContainer -->

    </div>
    <!-- End .box -->

    <!-- help widget -->
    <jsp:include page="helpWidget.jsp"/>

</div>
