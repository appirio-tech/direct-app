<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.3
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
  - - Change the use of %{#session.currentSelectDirectProjectID} to sessionData.currentSelectDirectProjectID so the JSP
  -   page can access the session on the first hit.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 2.0)
  - - Fixed long description of milestones.
  -
  - Version 1.3 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
  - - Update to support displaying contest associations
  -
  - Description: This page renders the project milestones batch creation view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie7.css"/>
    <![endif]-->
    <ui:projectPageType tab="milestone"/>
    <link rel="stylesheet" href="/css/direct/dashboard-view.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/projectMilestone.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="/scripts/jsrender.min.js"></script>
    <script type="text/javascript" src="/scripts/projectMilestone.js"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
    <script id="milestoneTemplate" type="text/x-jsrender">
            <dd id="milestone{{:milestone.id}}">
                <input type="hidden" name="milestoneId" value="{{:milestone.id}}">
                <input type="hidden" name="notification" value="{{:milestone.sendNotifications}}">
                <div class="project">
                    <div class="projectT">
                    <input type="checkbox" name="projectName" {{if milestone.mapRepresentation.completionDate != null}}checked="checked"{{/if}} {{if completedContestNumber != totalContestNumber}}disabled="disabled" title="There are uncompleted challenges, cannot mark as completed. If you wan to, please either move the challenge(s) to future milestone or delete/cancel the challenges first"{{/if}}/>
                    <label>{{>milestone.name}}</label>
                    {{if milestone.owners.length > 0}}
                        {{:~getUserLink(milestone.owners[0].name)}}
                        <input type="hidden" name="ownerId" value="{{:milestone.owners[0].userId}}"/>
                    {{/if}}
                    </div>
                    <div class="projectS">
                        <p>
                           {{if contests != null && contests.length > 0}}
                                <span class="bar"><i style="width:{{:completedContestNumber * 100 /totalContestNumber}}%"></i></span>
                                <span class="completedNumber">{{:completedContestNumber}}</span>/<span class="totalNumber">{{:totalContestNumber}}</span> Challenges have been completed
                           {{else}}
                                 No challenges associated to this milestone
                           {{/if}}
                        </p>
                        <a href="javascript:;" class="showHideDetails">Show Details</a>
                    </div>
                    <div class="projectD">
                        {{>milestone.description}}
                    </div>
                    {{if milestone.mapRepresentation.completionDate != null}}
                        <span class="date">{{:~formatDate(milestone.mapRepresentation.completionDate, "mm/dd/yy", "DD, dd MM yy")}}
                    {{else}}
                        <span class="date">{{:~formatDate(milestone.mapRepresentation.dueDate, "mm/dd/yy", "DD, dd MM yy")}}
                    {{/if}}
                    <input type="hidden" name="dueDate" value="{{:milestone.mapRepresentation.dueDate}}"/>
                    {{if milestone.mapRepresentation.completionDate != null}}
                        <input type="hidden" name="type" value="completed"/>
                        <input type="hidden" name="completionDate" value="{{:milestone.mapRepresentation.completionDate}}"/>
                    {{/if}}
                    </span>
                    <div class="actions">
                        <a href="javascript:;" class="edit">Edit</a>
                        <a href="javascript:;" class="remove">Remove</a>
                    </div>
                </div>
                {{if contests != null && contests.length > 0}}
                    <table cellspacing="0" cellpadding="0" class="hide">
                        <colgroup>
                            <col width="9%"/>
                            <col width="17%"/>
                            <col width="44%"/>
                            <col width="20%"/>
                            <col width="10%"/>
                        </colgroup>
                        <tbody>
                            {{for contests}}
                                <tr class="contestRow" id="contest{{:contestId}}">
                                    <input type="hidden" name="contestId" value="{{:contestId}}"/>
                                    <td class="{{:contestShortStatus}}" title="{{:contestStatus}}">{{:contestShortStatus}}</td>
                                    <td><strong>{{:contestType}}</strong></td>
                                    <td><a target="_blank" href="/direct/contest/detail.action?projectId={{:contestId}}">{{:contestName}}</a></td>
                                    <td>{{:startDate}}  - {{:endDate}}</td>
                                    <td class="links">
                                        <a href="javascript:;" class="js-move">Move</a>
                                        <span class="line">|</span>
                                        <a href="javascript:;" class="delete" style="font-size:11px">Delete</a>
                                    </td>
                                </tr>
                            {{/for}}
                        </tbody>
                    </table>
                {{/if}}
            </dd>
    </script>
    <script id="milestoneList" type="text/x-jsrender">
        {{if overdue.length > 0}}
          <dl class="milestoneList overdue">
            <dt>
                    <span class="tabL">
                        <span class="tabR">
                            <span>Overdue Milestones</span>
                        </span>
                    </span>
            </dt>
            {{for overdue tmpl="#milestoneTemplate"/}}
          </dl>
        {{/if}}

        {{if upcoming.length > 0}}
          <dl class="milestoneList upcoming">
            <dt>
                    <span class="tabL">
                        <span class="tabR">
                            <span>Upcoming Milestones</span>
                        </span>
                    </span>
            </dt>
            {{for upcoming tmpl="#milestoneTemplate"/}}

          </dl>
        {{/if}}

        {{if completed.length > 0}}
          <dl class="milestoneList completed">
            <dt>
                    <span class="tabL">
                        <span class="tabR">
                            <span>Completed Milestones</span>
                        </span>
                    </span>
            </dt>
            {{for completed tmpl="#milestoneTemplate"/}}

          </dl>
        {{/if}}
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="projectMilestoneListViewPage">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong>Milestones</strong>
                            </div>

                            <div class="spaceWhite"></div>

                            <div class="milestoneManage">
                                <div class="topLine">
                                    <h2>Project Milestone</h2>
                                    <a class="grayButton" href="javascript:;">
                                        <span class="buttonMask"><span class="text"><span
                                                class="plus">Add Milestone</span></span></span>
                                    </a>

                                    <div class="viewBtns">
                                        <a class="listViewBtn active" href="javascript:;">
                                            <span>List View</span>
                                        </a>
                                        <a class="calendarViewBtn"
                                           href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/><s:param name="formData.viewType">calendar</s:param></s:url>">
                                            <span>Calendar</span>
                                        </a>
                                    </div>
                                </div>
                                <!-- End .topLine -->
                                <div class="milestoneListView">
                                    <p class="notes">
                                        To mark a milestone complete, check the box next to the milestone name. To set a milestone back to incomplete, uncheck the box.
                                    </p>
                                    <!-- End .notes -->

                                </div>
                                <!-- End completed .milestoneList -->


                                <div class="bottomLine">
                                    <a class="grayButton" href="javascript:;">
                                        <span class="buttonMask"><span class="text"><span
                                                class="plus">Add Milestone</span></span></span>
                                    </a>
                                </div>

                                <div id="responsiblePersonList" class="hide">
                                    <s:iterator value="viewData.responsiblePersons">
                                        <link:user userId="${userId}"/>
                                    </s:iterator>
                                </div>

                                <div id="milestoneListItemTemplate" class="hide">
                                    <dd>
                                        <input type="hidden" value="{6}" name="milestoneId">
                                        <input type="hidden" name="notification" value="{7}">
                                        <div class="date">
                                            <span>{0}</span>
                                            <input type="hidden" name="type" value="{1}"/>
                                            <input type="hidden" name="dueDate"
                                                   value='{2}'/>
                                        </div>
                                        <div class="project">
                                            <div class="projectT">
                                                <input type="checkbox" name="projectName"/>
                                                <label>{3}</label>

                                            </div>
                                            <div class="projectD">
                                                <span class="short longdesc">
                                                  {4}
                                                  <a href="javascript:;">more</a>
                                                </span>
                                                <span class="long longdesc hide">
                                                    <span>
                                                        {5}
                                                    </span>
                                                    <a href="javascript:;">Hide</a>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="actions">
                                            <a href="javascript:;" class="edit">Edit</a>
                                            <a href="javascript:;" class="remove">Remove</a>
                                        </div>
                                    </dd>
                                </div>

                            </div>
                        </div>
                        <!-- end area1 -->
                    </div>
                </div>
                <!-- End #mainContent -->

                <jsp:include page="../includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/popups.jsp"/>

<div class="moveMilestonePopup" id="moveMilestonePopup">
    <div class="popupMask">
        <p>
            Select Milestone you want this challenge to be moved to
        </p>
        <select>
            <option value="0">- Select a milestone to move to -</option>
        </select>
        <a href="javascript:;" class="grayButton">
            <span class="buttonMask"><span class="text">Move</span></span>
        </a>
    </div>
    <div class="arrow"></div>
</div>
<jsp:include page="../includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
