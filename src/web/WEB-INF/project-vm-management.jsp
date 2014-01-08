<%--
  - Author: jiajizhou86
  -
  - Version: 1.0 (Release Assembly - TopCoder Direct VM Instances Management)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project vm management view.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
    <script type="text/javascript">
        var tcDirectProjectId = <s:property value="formData.projectId"/>;
        var hasFullOrWriteAccess = <s:property value="allowVMTermination"/>;
        var userHandler = "<s:property value="sessionData.currentUserHandle"/>";
        var vmErrorMessage = "<s:property value="vmErrorMessage"/>";
        var isErrorLoadingVM = <s:property value="errorLoadingVM"/>;
    </script>
    <script type="text/javascript" src="../scripts/project-vm-service.js?v=234604"></script>
    <jsp:include page="includes/filterPanel.jsp"/>

    <ui:projectPageType tab="vmManagement"/>
    <link rel="stylesheet" href="../css/direct/projectVMManagement.css?v=234604" media="all" type="text/css"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <!-- begin area1 -->
                    <div id="area1">

                        <!-- the main area -->
                        <div class="area1Content">

                            <!-- begin navigation -->
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                                <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong>VM Management</strong>
                            </div>
                            <!-- end navigation -->

                            <div class="spaceWhite"></div>

                            <!-- begin topLine -->
                            <div class="projectVMManagement">
                                <h2>VM Management</h2>
                            </div>
                            <!-- end topLine -->

                            <!-- begin summary -->
                            <div class="summary">
                                <span id="vmCount">
                                    Total ${totalVMCount} (active ${activeVMCount}, terminated ${terminatedVMCount})
                                </span>
                                <a id="refreshBtn" href="javascript:projectVMService.refresh(tcDirectProjectId);" class="grayButton">
                                    <span class="buttonMask"><span class="text">Refresh</span></span>
                                </a>
                                <a class="lanchVMInstanceBtn button6 btnAddNew" href="<s:url action="dashboardVMAction" namespace="/"/>">
                                    <span class="left">
                                        <span class="right">LAUNCH VIRTUAL MACHINE</span>
                                    </span>
                                </a>
                            </div>
                            <!-- end summary -->

                            <!-- begin filter -->
                            <div class='filterPanel' id='ProjectVMFilter' style="margin-top:10px">
                                <div class='filterHead'>
                                    <div class='rightSide'>
                                        <div class='inner'>
                                            <div class='filterText'>
                                                <a href='javascript:;' class='collapse'><img
                                                        src='../images/filter-panel/expand_icon.png' alt=''/></a>
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
                                <!-- end .filterHead -->
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
                                                    <span class='title'>TC Member Handle</span>
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
                                <!-- end .filterContent -->

                                <div class='filterBottom'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                                <!-- end .filterBottom -->
                                <div class='collapseBottom hide'>
                                    <div class='rightSide'>
                                        <div class='inner'></div>
                                    </div>
                                </div>
                            </div>
                            <!-- end filter -->

                            <span id="deleteSuccess" style="color:green" class="success hide"></span>

                            <div class="resultTableContainer vmTableContainer">
                            <!-- begin table -->
                            <table border="1px" class="projectStats contests" id="contest_vms">
                                <thead>
                                <tr>
                                    <th>Challenge Id</th>
                                    <th>Challenge Name</th>
                                    <th>TC Image Name</th>
                                    <th>Account Name</th>
                                    <th>SVN Branch</th>
                                    <th>Member Handle</th>
                                    <th>Created By</th>
                                    <th>Public IP</th>
                                    <th>Creation Time</th>
                                    <th>Usage</th>
                                    <th>Status</th>
                                    <th class="vm_instance_action">Action</th>
                                </tr>
                                </thead>
                                <tbody class="vm_instances_body">
                                <c:forEach items="${vmInstances}" var="vmInstance">
                                    <tr>
                                        <td>${vmInstance.instance.contestId}</td>
                                        <td>${vmInstance.contestName}</td>
                                        <td>${vmInstance.vmImageTcName}</td>
                                        <td>${vmInstance.accountName}</td>
                                        <td>${vmInstance.instance.svnBranch}</td>
                                        <td>${vmInstance.instance.tcMemberHandle}</td>
                                        <td>${vmInstance.managerHandle}</td>
                                        <td>${vmInstance.instance.publicIP}</td>
                                        <td><fmt:formatDate value="${vmInstance.instance.creationTime}" pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td>
                                            <c:forEach items="${vmUsages}" var="vmUsage">
                                                <c:if test="${vmUsage.id == vmInstance.instance.usageId}">
                                                    ${vmUsage.name}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td class="vm_instance_status">${vmInstance.status}</td>
                                        <td class="vm_instance_action" align="center">
                                            <div class="actionDropDownList" style="position: relative;">
                                                <a href="javascript:void(0)" class="actionLink">Action<span class="arrow"></span></a>
                                                <div class="actionList">
                                                    <ul>
                                                        <li>
                                                            <c:if test="${vmInstance.status eq 'RUNNING' && (allowVMTermination || vmInstance.managerHandle eq sessionData.currentUserHandle)}">
                                                                <a href="javascript:void(0)" class="enabled"
                                                                   onclick="javascript:projectVMService.terminate(${vmInstance.instance.id}, this);">
                                                                    Terminate
                                                                </a>
                                                            </c:if>
                                                            <c:if test="${vmInstance.status ne 'RUNNING' || not (allowVMTermination || vmInstance.managerHandle eq sessionData.currentUserHandle)}">
                                                                <a href="javascript:void(0)" class="disabled">
                                                                    Terminate
                                                                </a>
                                                            </c:if>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <!-- end table -->

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
                        </div>
                        <!-- end main area -->
                    </div>
                    <!-- end area1 -->
                </div>
                <!-- end main content -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- end content -->
        </div>
        <!-- end container -->
    </div>
    <!-- end wrapper inner -->
</div>
<!-- end wrapper -->

<jsp:include page="includes/popups.jsp"/>
</body>