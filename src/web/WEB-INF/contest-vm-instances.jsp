<%--
  - Author: gentva
  - Version: 1.0
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the contest vm instances page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/contest-vmservice.js"></script>
    <jsp:include page="includes/filterPanel.jsp"/>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="vmInstances"/>
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
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->

                            <jsp:include page="includes/contest/dashboard.jsp"/>

                            <div class="container2 tabs3Container" id="contestVMInstances" >

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left"><div class="container2Right">
                                    <div class="container2Content">

                                        <div class="summary">
                                            Total ${totalVMCount} (active ${activeVMCount}, terminated ${terminatedVMCount})
                                        </div>

                                        <div class='filterPanel' id='ContestVMFilter' style="margin-top:10px">
                                            <div class='filterHead'>
                                                <div class='rightSide'>
                                                    <div class='inner'>
                                                        <div class='filterText'>
                                                            <a href='javascript:;' class='collapse'><img
                                                                    src='/images/filter-panel/expand_icon.png' alt=''/></a>
                                                            <span class='title'>Filter</span>
                                                        </div>
                                                        <div class='searchContainer'>
                                                            <span class='title'>Search</span>

                                                            <div class='filterSearch'>
                                                                <input type='text' class='searchBox'/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--end .filterHead-->
                                            <div class='filterContent'>
                                                <div class='rightSide'>
                                                    <div class='inner'>
                                                        <div class='column1'>
                                                            <div class='row'>
                                                                <span class='title'>TC Image Name</span>
                                                                <select id='imageNameFilter'>
                                                                    <option value='All'>All Image Names</option>
                                                                </select>
                                                            </div>
                                                            <div class='row'>
                                                                <span class='title'>TC  Member Handle</span>
                                                                <select id='memberHandleFilter'>
                                                                    <option value='All'>All Handles</option>
                                                                </select>
                                                            </div>
                                                            <div class='row'>
                                                                <span class='title'>VM Status</span>
                                                                <select id='vmStatusFilter'>
                                                                    <option value='All'>All Statuses</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <!--end .column1-->
                                                        <div class='column3'>
                                                            <div class='row'>
                                                                <span class='title dateLabel'>Creation Date</span>
                                                                <input id='startDateBegin' type='text' readonly="readonly" class='date-pick'/>
                                                                <span class='title toLabel'>To</span>
                                                                <input id='startDateEnd' type='text' readonly="readonly" class='date-pick'/>
                                                            </div>
                                                            <div class='row'>
                                                                <span class='title' style="width:90px">VM Usage</span>
                                                                <select id='vmUsageFilter'>
                                                                    <option value='All'>All VM Usages</option>
                                                                </select>
                                                            </div>

                                                            <div class='row'>
                                                                <span class='title' style="width:90px">Created By</span>
                                                                <select id='createdByFilter'>
                                                                    <option value='All'>All</option>
                                                                </select>
                                                            </div>

                                                        </div>
                                                        <!--end .column3-->
                                                    </div>
                                                </div>
                                            </div>
                                            <!--end .filterHead-->
                                            <div class='filterBottom'>
                                                <div class='rightSide'>
                                                    <div class='inner'></div>
                                                </div>
                                            </div>
                                            <!--end .filterBottom-->
                                            <div class='collapseBottom hide'>
                                                <div class='rightSide'>
                                                    <div class='inner'></div>
                                                </div>
                                            </div>
                                        </div>
                                        <span id="deleteSuccess" style="color:green" class="success hide"></span>
                                        <div class="resultTableContainer">
                                            <table border="1px" class="projectStats contests" id="contestVMs">
                                                <thead>
                                                <tr>
                                                    <th>TC Image Name</th>
                                                    <th>Account Name</th>
                                                    <th>SVN Branch</th>
                                                    <th>TC Member Handle</th>
                                                    <th>Public IP</th>
                                                    <th>Creation Time</th>
                                                    <th>Usage</th>
                                                    <th>Status</th>
                                                    <th>Created By</th>
                                                    <th>Action</th>
                                                </tr>
                                                </thead>
                                                <tbody class="vm_instances_body">
                                                <c:forEach items="${vmInstances}" var="vmInstance">
                                                    <tr>
                                                        <td><c:out value="${vmInstance.vmImageTcName}" /></td>
                                                        <td><c:out value="${vmInstance.accountName}" /></td>
                                                        <td><c:out value="${vmInstance.instance.svnBranch}" /></td>
                                                        <td><c:out value="${vmInstance.instance.tcMemberHandle}" /></td>
                                                        <td><c:out value="${vmInstance.instance.publicIP}" /></td>
                                                        <td><fmt:formatDate value="${vmInstance.instance.creationTime}" pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></td>
                                                        <td>
                                                            <c:forEach items="${vmUsages}" var="vmUsage">
                                                                <c:if test="${vmUsage.id == vmInstance.instance.usageId}">
                                                                    <c:out value="${vmUsage.name}" />
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <td class="vm_instance_status"><c:out value="${vmInstance.status}" /></td>
                                                        <td><c:out value="${vmInstance.managerHandle}" /></td>
                                                        <td class="vm_instance_action" align="center"><c:if test="${vmInstance.status eq 'RUNNING' && (hasWriteOrFullPermission || vmInstance.managerHandle eq sessionData.currentUserHandle)}">
                                                            <div class="term1"><div>
                                                                <a href="javascript:void(0)" onclick="javascript:contestVMService.terminate(${vmInstance.instance.id}, ${projectId}, ${studio}, this);" class="button6" style="margin:auto;"><span class="left"><span class="right">Terminate</span></span></a>&nbsp;
                                                            </div></div>

                                                        </c:if></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                            <div class="container2Left">
                                                <div class="container2Right">
                                                    <div class="container2Bottom">
                                                        <div>
                                                            <div>

                                                                <div class="panel tableControlPanel"></div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel">
                                            &nbsp;
                                        </div>
                                    </div><!-- End .container2Content -->
                                </div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End #mainContent -->

            <jsp:include page="includes/footer.jsp"/>

        </div>
        <!-- End #content --></div>
    <!-- End #container -->

    <!-- End #wrapper -->

    <jsp:include page="includes/popups.jsp"/>


</body>
<!-- End #page -->

</html>
