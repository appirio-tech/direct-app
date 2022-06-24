<%--
  - Author: TCSASSEMBLER, duxiaoyang
  - Version: 1.2
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: Manage copilot projects page.
  - Since: TC Direct Manage Copilots Assembly
  -
  - Version 1.1 - Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0
  - - Fixed upper case issue for SAVE and CANCEL buttons in Copilot Management popup, and scroll issue in IE for copilot removal.
  - -  Re-use same modal windows for manage copilot screen.
  -
  - Version 1.2 (TC Direct Rebranding Assembly Copilot and Reporting related pages)
  - - Rebranding the copilot and reporting related pages.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <c:set var="CURRENT_TAB" value="manageCopilots" scope="request"/>
    <link rel="stylesheet" href="/css/direct/manageCopilots.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/permissions.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/manageCopilots.js"></script>
</head>

<body id="page">
    <div id="wrapper">
        <div id="wrapperInner">
            <div id="container">
                <div id="content">
                    <jsp:include page="includes/header.jsp"/>

                    <div id="mainContent" class="newSidebarCollapse">

                        <jsp:include page="includes/right.jsp"/>

                        <div id="area1"><!-- the main area -->
                            <div class="area1Content">
                                <div class="currentPage">
                                    <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a> &gt;
                                    <strong>Manage Copilots</strong>
                                </div> <!-- End .currentPage -->

                                <div id="copilotsIntroduction">
                                    <div class="orderReview">

                                        <div class="myCopilotsContests">
                                            <span class="introductionHeadIcon"><img src="/images/copilot_contests_icon.png" alt="copilot contests" /></span>
                                            <h2 class="sectionHead">Manage Copilots</h2>
                                        </div>
                                        <!-- end .getCopilots -->

                                        <div class="myCopilotsContestsList">
                                            <div class="container2 tabs3Container tabs4Special">

                                                <div id="tabs3">
                                                    <ul>
                                                        <li class="firstItem on"><a href="javascript:;" id="projectViewLink" class="first"><span class="left"><span class="right">Project View</span></span></a></li>
                                                        <li class="lastItem"><a href="javascript:;" id="contestViewLink" class="last"><span class="left"><span class="right">Challenge View</span></span></a></li>
                                                    </ul>
                                                </div><!-- End #tabs3 -->

                                                <div class="container2Left"><div class="container2Right">
                                                    <div class="tableContainer">
                                                        <table id="copilotProjectTable" class="projectStats newManageStatus" cellpadding="0" cellspacing="0" style="border: none">
                                                            <thead>
                                                                <tr>
                                                                    <th class="thOne">Project / Copilot</th>
                                                                    <th class="thTwo">No Of Copilots</th>
                                                                    <th class="thThree">Copilot Type</th>
                                                                    <th class="thFour">Manage Copilot</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <s:iterator value="viewData.copilotProjects">
                                                                    <tr class="trNormal" name="project_${project.id}">
                                                                        <td class="tdTitle"><a href="javascript:;" class="expand">Expand</a>
                                                                            <a class="longWordsBreak listTitle longdesc" href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="project.id"/></s:url>">
                                                                                ${project.name}
                                                                            </a>
                                                                        </td>
                                                                        <td>${fn:length(copilots)}</td>
                                                                        <td></td>
                                                                        <td><a href="javascript:;" class="downloadProfile triggerModal newButtonGreen copilotManage" name="copilotManage"> <span class="profileLeft">Add Copilot</span></a></td>
                                                                    </tr>

                                                                    <s:iterator value="copilots" status="stat">
                                                                        <tr class="trChild hide <c:if test='${stat.last}'>lastTr</c:if>">
                                                                            <td class="photo"><i class="icon-user"></i>
                                                                                <span name="copilot_${copilotProfileId}">${handle}</span>
                                                                                <input id="project_${project.id}_copilot_${copilotProfileId}" type="hidden" value="${copilotProjectId}">
                                                                                </input>
                                                                            </td>
                                                                            <td></td>
                                                                            <td class="copilotType">${copilotType}</td>
                                                                            <td><a href="javascript:removeCopilotProject(${project.id}, ${copilotProfileId}, ${copilotProjectId});" class="closeProjectCopilot">close</a></td>
                                                                        </tr>
                                                                    </s:iterator>
                                                                </s:iterator>

                                                            </tbody>
                                                        </table><!-- End .projectsStats -->

                                                        <table id="copilotContestTable" class="projectStats newManageStatus newManageContestsStatus hide" cellpadding="0" cellspacing="0">
                                                            <thead>
                                                                <tr>
                                                                    <th class="thOne">Project / Challenge name</th>
                                                                    <th class="thTwo">Challenge Number</th>
                                                                    <th class="thThree">Copilot</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <s:iterator value="viewData.copilotProjects">
                                                                    <tr class="trNormal copilot_project_${project.id}">
                                                                        <td class="tdTitle"><a href="javascript:;" class="expand">Expand</a>
                                                                            <a class="longWordsBreak listTitle" href="<s:url action="projectDetails" namespace="/"><s:param name="formData.projectId" value="%{#attr['value'].project.id}"/></s:url>">
                                                                                ${project.name}
                                                                            </a>
                                                                        </td>
                                                                        <td>${fn:length(contests)}</td>
                                                                        <td></td>
                                                                    </tr>

                                                                    <s:iterator value="contests">
                                                                        <tr class="trChild hide">
                                                                            <td class="copilotTitle">
																				<c:choose>
																					<c:when test="${contest.software}">
																						<link:onlineReviewProjectDetails projectId="${contest.id}" styleClass="">
																							${contest.title}
																						</link:onlineReviewProjectDetails>
																					</c:when>
																					<c:otherwise>
																						${contest.title}
																					</c:otherwise>
																				</c:choose>

																			</td>
                                                                            <td></td>
                                                                            <td class="photo">
                                                                                <s:iterator value="copilots" var="handle">
                                                                                    <div>
                                                                                        <img src="/images/photo_people_small.png" alt="photo" />
                                                                                        <span>${handle}</span>
                                                                                    </div>
                                                                                </s:iterator>
                                                                            </td>
                                                                        </tr>
                                                                    </s:iterator>
                                                                </s:iterator>
                                                            </tbody>
                                                        </table><!-- End .projectsStats -->
                                                    </div>

                                                </div></div>
                                            </div>
                                        </div>
                                        <!-- end .getCopilotsStep -->
                                    </div>
                                    <!-- end .orderReview -->
                                </div>
                                <!-- end #copilotsIntroduction -->
                            </div> <!-- End.area1Content -->
                        </div> <!-- End #area1 -->

                    </div>
                    <!-- End #mainContent -->

                    <jsp:include page="includes/footer.jsp"/>

                </div>
                <!-- End #content -->
            </div>
            <!-- End #container -->
        </div>
    </div>
    <!-- End #wrapper -->

    <!-- dialog -->
    <div class="dialogContent" style="display: none;">
        <div id="manageUserDialog">
            <div class="header">
                    <div class="title">Copilot Management</div>
                    <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
            </div>

            <div class="body">
                <div class="left">
                    <div class="subtitle">
                        <a href="javascript:void(0)" class="leftTxt">Available Copilots</a>
                        <a href="javascript:void(0)" class="rightTxt">Select All</a>
                    </div>
                    <div class="searchBox">
                        <div class="searchTxt"><input type="text" value="adr"/></div>
                        <a class="button1" href="javascript:void(0)"><span>Search</span></a>
                    </div>
                    <div class="list">
                    </div>
                </div>
                <div class="middle">
                    <a class="addItem" href="javascript:void(0)"><img src="/images/add_tech.png" alt=""/></a>
                    <a class="removeItem" href="javascript:void(0)"><img src="/images/remove_tech.png" alt=""/></a>
                </div>
                <div class="right">
                    <div class="subtitle">
                        <a href="javascript:void(0)" class="leftTxt">Chosen Copilots</a>
                        <a href="javascript:void(0)" class="rightTxt">Remove All</a>
                    </div>
                    <div class="list">
                    </div>
                </div>
            </div>

            <div class="foot">
                <div class="separator"></div>
                <div class="buttons">
                    <a class="button6 saveDialogButton makeSureButton" href="javascript:void(0)"><span class="left"><span class="right">SAVE</span></span></a>
                    <a class="button6 closeDialog" href="javascript:void(0)"><span class="left"><span class="right">CANCEL</span></span></a>
                </div>
            </div>
        </div>

        <div id="makeSureDialog">
            <div class="header">
                    <div class="title">Confirmation</div>
                    <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
            </div>

            <div class="body">
                Copilots of project <span class="b projectName"></span> have been saved
            </div>

            <div class="foot">
                <div class="separator"></div>
                <div class="buttons">
                    <a class="button6 okey2Button" href="javascript:void(0)"><span class="left"><span class="right">OK</span></span></a>
                </div>
            </div>
        </div>

        <div id="removeProjectDialog">
            <div class="header">
                    <div class="title">Remove Copilot</div>
                    <a class="dialogClose closeDialog" href="javascript:void(0);"></a>
            </div>

            <div class="body">
                The Copilot <span class="b handle addSpace"> </span> has been removed from Project <span class="b projectName"></span>
            </div>

            <div class="foot">
                <div class="separator"></div>
                <div class="buttons">
                    <a class="button6 removeProjectMakeSureButton okey2Button" href="javascript:void(0)"><span class="left"><span class="right">OK</span></span></a>
                </div>
            </div>
        </div>
    </div>
    <!-- End dialog -->

    <!-- Copilots information -->
    <div id="copilotsList" class="hide">
        <s:iterator value="viewData.copilots">
            <input type="hidden" name="copilot_${copilotProfileId}" value="${handle}"></input>
        </s:iterator>
    </div>
    <!-- End Copilots information -->

    <!-- tr template -->
    <table class="hide">
        <tbody id="trTemplate">
            <tr class="trChild hide">
                <td class="photo"><img src="/images/photo_people_small.png" alt="photo" />
                    <span name="copilot_@copilotProfileId@">@handle@</span>
                    <input id="project_@projectId@_copilot_@copilotProfileId@" type="hidden" value="@copilotProjectId@">
                    </input>
                </td>
                <td></td>
                <td class="copilotType">@copilotType@</td>
                <td><a href="javascript:removeCopilotProject(@projectId@, @copilotProfileId@, @copilotProjectId@);" class="closeProjectCopilot">close</a></td>
            </tr>
        </tbody>
    </table>
    <!-- end tr template -->

    <jsp:include page="includes/popups.jsp"/>
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
