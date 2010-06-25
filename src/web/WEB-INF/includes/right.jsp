<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div id="area2"><!-- the right column -->
    <c:if test="${requestScope.PAGE_TYPE ne 'dashboard'}">
        <div class="box">
            <a href="<s:url action="dashboard" namespace="/" />" class="button3">Back to Dashboard</a>
        </div>
        <!-- End .box -->
    </c:if>

    <div class="box">
        <a href="${ctx}/launch/home" class="button2">Launch New Contest</a>
    </div>
    <!-- End .box -->
    <div class="box">
        <h2 class="title">My Projects</h2>
        <a href="<s:url action="createProjectView" namespace="/"/>" class="button2">Create a New Project</a>

        <div class="contestsContainer">

            <p class="label">Select a Project</p>

            <div class="inputSelect">
                <ui:isProjectContextSet>
                    <s:textfield name="sessionData.currentProjectContext.name" onfocus="showHideList();"/>
                </ui:isProjectContextSet>
                <ui:noProjectContextSet>
                    <s:textfield value="Select a Project" onfocus="showHideList();"/>
                </ui:noProjectContextSet>
                <a href="javascript:;" onclick="showHideList();" class="selectIco"></a>
            </div>

            <div id="dropDown1" class="contestsDropDown" style="height:200px;overflow:auto;">
                <!-- when the user click the selectIco button we will show this dropdown list -->
                <ul>
                    <s:iterator value="viewData.userProjects.projects" status="status" var="project">
                        <li <s:if test="#status.even">class="even"</s:if>>
                            <link:projectDetails project="${project}"/>
                        </li>
                    </s:iterator>
                </ul>
            </div>
            <!-- End .contestsDropDown -->

            <ui:isProjectContextSet>
                <div class="contestsList"><!-- This will contain the list of contests -->
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
                                <tr <s:if test="#status.even">class="even"</s:if> onclick="document.location.href = '<s:url action="detail" namespace="/contest"><s:param name="contestId" value="%{#attr['contest'].id}"/></s:url>'; this.style.cursor='pointer';">
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

            <p class="projectArchive">
                Projects Archive</p>
        </div>
        <!-- End .contestsContainer -->

    </div>
    <!-- End .box -->

    <ui:isDashboardPage>
        <jsp:include page="links.jsp"/>
    </ui:isDashboardPage>
</div>
