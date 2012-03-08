<%--
  - Author: TCSASSEMBLER
  -
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
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
    <link rel="stylesheet" type="text/css" media="screen" href="/css/dashboard-ie7.css?v=214041"/>
    <![endif]-->
    <ui:projectPageType tab="milestone"/>
    <link rel="stylesheet" href="/css/dashboard-view.css?v=212459" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/projectMilestone.css?v=214041" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script>
    <script type="text/javascript" src="/scripts/projectMilestone.js?v=214061"></script>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
    </script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="projectMilestoneListViewPage">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">

                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/></s:url>'><s:property
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
                                           href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/><s:param name="formData.viewType">calendar</s:param></s:url>">
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



                                    <dl class="milestoneList overdue <s:if test="viewData.overdueMilestones.size == 0">hide</s:if>">
                                        <dt>
                                            <span class="tabL">
                                                <span class="tabR">
                                                    <span>Overdue Milestones</span>
                                                </span>
                                            </span>
                                        </dt>

                                        <s:iterator  value="viewData.overdueMilestones">

                                            <dd>
                                                <input type="hidden" name="milestoneId" value="${id}">
                                                <input type="hidden" name="notification" value="${sendNotifications}">
                                                <div class="date">
                                                    <span><fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${dueDate}"/></span>
                                                    <input type="hidden" name="type" value="overdue"/>
                                                    <input type="hidden" name="dueDate" value='<fmt:formatDate pattern="MM/dd/yyyy" value="${dueDate}" />'/>
                                                </div>
                                                <div class="project">
                                                    <div class="projectT">
                                                        <input type="checkbox" name="projectName"/>
                                                        <label><s:property value="name"/></label>
                                                        <s:if test="owners.size > 0">
                                                            <link:user userId="${owners[0].userId}"/>
                                                            <input type="hidden" name="ownerId" value="${owners[0].userId}"/>
                                                        </s:if>
                                                    </div>
                                                    <div class="projectD">

                                                        <s:if test="%{description.length() > 100}">
                                                           <span class="short">
                                                               <c:out value="${fn:substring(description, 0, 100)}"/>
                                                             <a href="javascript:;">more</a>
                                                           </span>
                                                           <span class="long hide">
                                                               <span>
                                                                   <s:property value="description"/>
                                                               </span>
                                                               <a href="javascript:;">Hide</a>
                                                           </span>

                                                        </s:if>
                                                        <s:else>
                                                           <span class="short">
                                                               <s:property value="description"/>
                                                           </span>
                                                        </s:else>
                                                    </div>
                                                </div>
                                                <div class="actions">
                                                    <a href="javascript:;" class="edit">Edit</a>
                                                    <a href="javascript:;" class="remove">Remove</a>
                                                </div>
                                            </dd>

                                        </s:iterator >
                                    </dl>


                                    <dl class="milestoneList upcoming <s:if test="viewData.upcomingMilestones.size == 0">hide</s:if>">
                                        <dt>
                                            <span class="tabL">
                                                <span class="tabR">
                                                    <span>Upcoming Milestones</span>
                                                </span>
                                            </span>
                                        </dt>

                                        <s:iterator  value="viewData.upcomingMilestones">

                                            <dd>
                                                <input type="hidden" name="milestoneId" value="${id}">
                                                <input type="hidden" name="notification" value="${sendNotifications}">
                                                <div class="date">
                                                    <span><fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${dueDate}"/></span>
                                                    <input type="hidden" name="type" value="upcoming"/>
                                                    <input type="hidden" name="dueDate" value='<fmt:formatDate pattern="MM/dd/yyyy" value="${dueDate}" />'/>
                                                </div>
                                                <div class="project">
                                                    <div class="projectT">
                                                        <input type="checkbox" name="projectName"/>
                                                        <label><s:property value="name"/></label>
                                                        <s:if test="owners.size > 0">
                                                            <link:user userId="${owners[0].userId}"/>
                                                            <input type="hidden" name="ownerId" value="${owners[0].userId}"/>
                                                        </s:if>
                                                    </div>
                                                    <div class="projectD">

                                                        <s:if test="description.length() > 100">
                                                           <span class="short">
                                                               <c:out value="${fn:substring(description, 0, 100)}"/>
                                                             <a href="javascript:;">more</a>
                                                           </span>
                                                           <span class="long hide">
                                                               <span>
                                                                   <s:property value="description"/>
                                                               </span>
                                                               <a href="javascript:;">Hide</a>
                                                           </span>

                                                        </s:if>
                                                        <s:else>
                                                           <span class="short">
                                                               <s:property value="description"/>
                                                           </span>
                                                        </s:else>
                                                    </div>
                                                </div>
                                                <div class="actions">
                                                    <a href="javascript:;" class="edit">Edit</a>
                                                    <a href="javascript:;" class="remove">Remove</a>
                                                </div>
                                            </dd>

                                        </s:iterator >
                                    </dl>
                                    <!-- end completed .milestoneList -->

                                    <!-- start completed .milestoneList -->
                                    <dl class="milestoneList completed <s:if test="viewData.completedMilestones.size == 0">hide</s:if>">
                                        <dt>
                                            <span class="tabL">
                                                <span class="tabR">
                                                    <span>Completed Milestones</span>
                                                </span>
                                            </span>
                                        </dt>

                                        <s:iterator  value="viewData.completedMilestones">

                                            <dd>
                                                <input type="hidden" name="milestoneId" value="${id}">
                                                <input type="hidden" name="notification" value="${sendNotifications}">
                                                <div class="date">
                                                    <span><fmt:formatDate pattern="EEEE, dd MMMM yyyy" value="${completionDate}"/></span>
                                                    <input type="hidden" name="type" value="completed"/>
                                                    <input type="hidden" name="dueDate" value='<fmt:formatDate pattern="MM/dd/yyyy" value="${dueDate}" />'/>
                                                    <input type="hidden" name="completionDate" value='<fmt:formatDate pattern="MM/dd/yyyy" value="${completionDate}" />'/>
                                                </div>
                                                <div class="project">
                                                    <div class="projectT">
                                                        <input type="checkbox" name="projectName" checked="checked"/>
                                                        <label><s:property value="name"/></label>
                                                        <s:if test="owners.size > 0">
                                                            <link:user userId="${owners[0].userId}"/>
                                                            <input type="hidden" name="ownerId" value="${owners[0].userId}"/>
                                                        </s:if>
                                                    </div>
                                                    <div class="projectD">

                                                        <s:if test="description.length() > 100">
                                                           <span class="short">
                                                               <c:out value="${fn:substring(description, 0, 100)}"/>
                                                             <a href="javascript:;">more</a>
                                                           </span>
                                                           <span class="long hide">
                                                               <span>
                                                                   <s:property value="description"/>
                                                               </span>
                                                               <a href="javascript:;">Hide</a>
                                                           </span>

                                                        </s:if>
                                                        <s:else>
                                                           <span class="short">
                                                               <s:property value="description"/>
                                                           </span>
                                                        </s:else>
                                                    </div>
                                                </div>
                                                <div class="actions">
                                                    <a href="javascript:;" class="edit">Edit</a>
                                                    <a href="javascript:;" class="remove">Remove</a>
                                                </div>
                                            </dd>

                                        </s:iterator >
                                    </dl>
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
                                                <span class="short">
                                                  {4}
                                                  <a href="javascript:;">more</a>
                                                </span>
                                                <span class="long hide">
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

</body>
<!-- End #page -->

</html>