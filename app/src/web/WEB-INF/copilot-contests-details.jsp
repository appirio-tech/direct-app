<%--
  - Author: isv, GreatKevin, Ghost_141, duxiaoyang
  - Version: 1.9
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (TC Direct - Release Bug Fix Assembly) Change notes:
  - - Change time zone from GMT-04 to UTC-05.
  -
  - Version 1.2 (Direct Improvements Assembly Release 1 ) Change notes:
  - - Fix bug TCCC-2900.
  -
  - Version 1.3 (Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp) Change notes:
  - - Fix the contest requirements preview and integrate new cockpit tinyMCE editor
  -
  - Version 1.4 (Release Assembly - TC Cockpit Contest Edit and Upload Update) Change notes:
  - Fixed bug TCCC-3739. Date/Time inputs for contest start time are initialized in JS code block from this page
  - Fixed bug TCCC-3760. The link for downloading the document is corrected.
  -
  - Version 1.5 (BUGR-6609) Change notes:
  - - Added the submission end date and contest end date in Schedule section.
  -
  - Version 1.6 (Cockpit Customer Copilot Posting Process Revamp Copilot Posting Dashboard)
  - - Added copilot posting dashboard
  -
  - Version 1.7 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - - Fix text inconsistency bug.
  -
  - Version 1.8 (Release Assembly - TC Cockpit Private Challenge Update)
  -- Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
  -
  - Version 1.9 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace name attribute for s:set with var attribute
  -
  - Description: This page renders the list of Copilot Posting contests available to current user.
  - Since: TC Direct - Manage Copilot Postings assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<c:set var="PAGE_TYPE" value="project" scope="request"/>
<c:set var="CURRENT_TAB" value="contests" scope="request"/>
<c:set var="CURRENT_SUB_TAB" value="copilotContestDetails" scope="request"/>

<c:set var="contestDTO" value="${viewData.contestStats.contest}"/>
<c:set var="contest" value="${result}"/>
<c:set var="projectHeader" value="${contest.projectHeader}"/>
<c:set var="assetDTO" value="${contest.assetDTO}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/direct/dashboard-view.css" media="all" type="text/css"/>
	<link rel="stylesheet" href="/css/direct/copilotDetails.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/copilot/copilotPosting.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/moment.min.js"></script>
    <script type="text/javascript" src="/scripts/moment-timezone-with-data-2010-2020.min.js"></script>
    <script type="text/javascript" src="/scripts/dashboard-view.js"></script>
    <script type="text/javascript" src="/scripts/launch/entity.js"></script>
    <script type="text/javascript" src="/scripts/copilots.js"></script>
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#projects2').getSetSSValue('${projectHeader.tcDirectProjectId}');
            $('#billingProjects2').getSetSSValue('${projectHeader.properties["Billing Project"]}');
            projectId = ${projectHeader.id};
            $('#mainContent').data('p1', '${projectHeader.tcDirectProjectId}');
            $('#mainContent').data('p2', '${projectHeader.properties["Billing Project"]}');
            $('#mainContent').data('p3', '${assetDTO.productionDate}');
            var d;
            <c:forEach items="${assetDTO.documentation}" var="doc" varStatus="loop">
                d = {};
                <c:set var="docUrl" value="${doc.url}"/>
                <c:set var="splitURI" value="${fn:split(docUrl, '/')}"/>
                <c:set var="docName" value="${splitURI[fn:length(splitURI)-1]}"/>
                d['fileName'] = '${docName}';
                d['documentId'] = ${doc.id};
                d['description'] = '${fn:escapeXml(doc.documentName)}';
                exsitingDocuments.push(d);
                addFileItem(d);
            </c:forEach>
            <c:forEach items="${projectHeader.prizes}" var="p" varStatus="loop" >
            prizes.push(new com.topcoder.direct.Prize(${p.place}, ${p.prizeAmount}, CONTEST_PRIZE_TYPE_ID, ${p.numberOfSubmissions}));
            </c:forEach>


            if(!prizes || prizes.length == 0) {
                prizes.push(new com.topcoder.direct.Prize(1, ${projectHeader.properties["First Place Cost"]}, CONTEST_PRIZE_TYPE_ID, 1));
                prizes.push(new com.topcoder.direct.Prize(2, ${projectHeader.properties["Second Place Cost"]}, CONTEST_PRIZE_TYPE_ID, 1));
            }

            <c:forEach items="${assetDTO.compUploadedFiles}" var="doc" varStatus="loop">
            <!--
            ${doc.uploadedFileName}
            ${doc.uploadedFileDesc} -->
            </c:forEach>

            <c:forEach items="${viewData.dashboard.copilotProjectTypes}" var="type" varStatus="loop" >
                experiences.push('${type}');
            </c:forEach>
            extraExperience = '${viewData.dashboard.otherManagingExperienceString}';
            budget = '${viewData.dashboard.budget}';
            $('#start2TimeInput').getSetSSValue(getTimePart('${assetDTO.productionDate}'));
            $("#start2DateInput").datePicker().val(getDatePart('${assetDTO.productionDate}')).trigger('change');
            setupCopilotDateTimeSuffix("start2");
        });
    </script>
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
    <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
            value="sessionData.currentProjectContext.name"/></a> &gt;
    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilot Posting</a> &gt;
    <strong><c:out value="${viewData.contestStats.contest.title}"/></strong>
</div>
<!-- End .currentPage -->

<div id="copilotsIntroduction">
<div class="orderReview">

<div class="myCopilotsContests">
   <span class="introductionHeadIcon"><img src="/images/ico-copilotPosting.png" alt="copilot contests"/></span>

    <h2 class="sectionHead"><c:out value="${viewData.contestStats.contest.title}"/>
        <span class="fRight currentStatus">Copilot Posting Status

            <c:set var="projectStatus" value="${viewData.contestStats.currentStatus}"/>
            <c:choose>
                <c:when test="${projectStatus eq 'Active'}">
                    <c:choose>
                        <c:when test="${viewData.dashboard.stalled}">
                        </c:when>
                        <c:otherwise>
                            <span class="statActive">Active</span>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${projectStatus eq 'Draft'}">
                    <span class="statDraft">Draft</span>
                </c:when>
                <c:when test="${projectStatus eq 'Deleted'}">

                </c:when>
                <c:when test="${projectStatus == 'Completed'}">
                    <span class="statCompleted">Completed</span>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
        </span>
    </h2>

</div>
<!-- end .getCopilots -->

<div class="myCopilotsContestsList">

<jsp:include page="includes/copilot/copilot-dashboard.jsp"/>

<div class="container2 tabs3Container tabs3Special">

<jsp:include page="includes/copilot/tabs.jsp"/>
<div class="container2Left">
<div class="container2Right">
<div class="container2BottomClear">

<div id="launchContestOut" class="launchCopilotContest">

<s:set var="contestStatus" value="viewData.contestStats.contest.status.name"/>

<!-- add new contest -->
<div class="editMask">
    <div class="addNewContestInfo infoPanel ">
        <h3><span class="icon">General Information</span>
            <s:if test="#contestStatus eq 'Draft' || #contestStatus eq 'Scheduled' || #contestStatus eq 'Active' || #contestStatus eq 'Completed'">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" alt="edit" src="/images/edit-icon.png"/> </a>
            </s:if>
        </h3>

        <div class="infoPanelMask">
            <ul>
                <li><label>Copilot Posting Name :</label>
                    <strong id="contestNameTextLabel"><c:out value="${assetDTO.name}"/></strong>
                </li>
                <li><label>Review Style :</label>
                    <strong id="reviewStyleTextLabel">User Selection</strong>
                </li>
                <li>
                    <label>Project Name :</label>
                    <strong id="projectNameTextLabel">
                        <c:forEach items="${projects}" var="p">
                            <c:if test="${p.projectId eq projectHeader.tcDirectProjectId}">
                                <a href="../projectOverview?formData.projectId=${p.projectId}">${p.name}</a>
                            </c:if>
                        </c:forEach>
                    </strong>
                </li>


                <li>
                    <label>Billing Account :</label>
                    <strong id="billingProjectNameTextLabel">
                        <c:forEach items="${billingAccountsForProject}" var="p">
                            <c:if test="${p.id eq projectHeader.properties['Billing Project']}">
                                <c:out value="${p.name}"/>
                            </c:if>
                        </c:forEach>
                    </strong>
                </li>
                <li style="display:none">
                    <label>Private Group :</label>
                    <strong id="securityGroupName">

                    </strong>
                </li>
                <c:if test='${projectHeader.properties["CloudSpokes CMC Task"] != null}'>
                    <li>
                        <label>CMC Task ID :</label>
                        <strong id="rCMCTaskID">
                                    <c:out value='${projectHeader.properties["CloudSpokes CMC Task"]}'/>
                        </strong>
                    </li>
                </c:if>
                <li>
                    <label>Experience :</label>

                    <strong id="copilotTypesTextLabel">
                        <c:forEach var="copilotType" items="${allProjectCopilotTypes}" >
                            <c:if test="${fn:contains(viewData.dashboard.copilotProjectTypes, copilotType.key)}">
                                ${copilotType.value}&nbsp;
                            </c:if>
                        </c:forEach>

                        <c:if test="${not empty viewData.dashboard.otherManagingExperienceString}">
                            <c:out value="${viewData.dashboard.otherManagingExperienceString}" />
                        </c:if>
                    </strong>
                </li>
                <li>
                    <label>Budget :</label>

                    <strong id="budgetTextLabel">
                        <c:if test="${not empty viewData.dashboard.budget}">
                            $ ${viewData.dashboard.budget}
                        </c:if>
                        <c:if test="${empty viewData.dashboard.budget}">
                            Don't have a budget yet.
                        </c:if>
                    </strong>
                </li>

                <li>
                    <label>Created By :</label>

                    <strong id="challengeCreatorLabel">
                            ${projectHeader.creator}
                    </strong>
                </li>

            </ul>
        </div>
    </div>
    <div class="addNewContest editPanel">
        <h3>
            <span class="icon">General Information</span>
            <a href="javascript:" class="editLink"> <img class="edit_type" alt="edit" src="/images/edit-icon.png"/></a>
        </h3>

        <div class="editPanelMask">
            <!-- Project Name -->
            <div class="row">
                <label for="projects2">Select the project that needs a Copilot :</label>
                <div class="projectSelect">
                    <select id="projects2">
                        <option value="-1">Please select an existing project</option>
                        <c:forEach items="${projects}" var="p">
                            <option value="${p.projectId}"
                                <c:if test="${p.projectId eq projectHeader.tcDirectProjectId}">selected="selected"</c:if>>
                                <c:out value="${p.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="addNew">
                    <a href="javascript:" class="button6" id="addNewProject2">
                        <span class="left"><span class="right">ADD NEW</span></span></a>
                </div>
            </div>

            <!-- Contest Name -->
            <div class="row <s:if test="#contestStatus == 'Completed'">hide</s:if>">
                <label for="contestNameInput2">Create a name for your Copilot Posting :</label>
                <input type="text" class="text" id="contestNameInput2" value="${fn:escapeXml(assetDTO.name)}"/>
            </div>

            <!-- CMC Task ID -->
            <div class="row">
                <label for="contestNameInput2">CMC Task ID</label>
                <input type="text" class="text" value="${projectHeader.properties["CloudSpokes CMC Task"]}" name="CMCTaskID"/>
            </div>

            <!-- Billing Account -->
            <div class="row <s:if test="#contestStatus != 'Draft'">hide</s:if>">
                <label for="billingProjects2">Select Your Billing Account :</label>
                <div class="billingSelect">
                    <select id="billingProjects2">
                        <option value="0">Please select an existing account</option>
                        <c:forEach items="${billingAccountsForProject}" var="p">
                            <option value="${p.id}"
                                <c:if test="${p.id eq projectHeader.properties['Billing Project']}">selected="selected"</c:if>>
                                <c:out value="${p.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row" id="billingGroupCheckBox">
                <label for="contestNameInput2">Private Group</label>
                <input type="checkbox" style=""><span>Run this copilot posting in a private community ? &nbsp;&nbsp;</span>  <select id="billingGroups" name="billingGroups"></select>
                <input type="hidden" id="securityGroupId" value="${projectHeader.securityGroupId}" />
            </div>

            <!-- Experience -->
            <div class="row experienceRow <s:if test="#contestStatus != 'Draft'">hide</s:if>">
                <label>Select Copilot Experience :</label>
                <div class="billingSelect experienceDiv">
                    <ul class="type1">
                        <c:forEach var="copilotType" items="${allProjectCopilotTypes}" end="${fn:length(allProjectCopilotTypes) / 2 - 0.5}">
                        <li>
                            <input type="checkbox" id="${copilotType.value}" value="${copilotType.key}" <c:if test="${fn:contains(viewData.dashboard.copilotProjectTypes, copilotType.key)}">checked='checked'</c:if> />
                            <label for="${copilotType.value}">${copilotType.value}</label>
                        </li>
                        </c:forEach>
                    </ul>
                    <ul class="type3">
                        <c:forEach var="copilotType" items="${allProjectCopilotTypes}" begin="${fn:length(allProjectCopilotTypes) / 2 + 0.5}">
                        <li>
                            <input type="checkbox" id="${copilotType.value}" value="${copilotType.key}" <c:if test="${fn:contains(viewData.dashboard.copilotProjectTypes, copilotType.key)}">checked='checked'</c:if> />
                            <label for="${copilotType.value}">${copilotType.value}</label>
                        </li>
                        </c:forEach>
                    </ul>

                    <div class="clear"></div>

                    <label class="otherExperience">Other:</label>
                    <input type="text" class="text" id="otherExperience" value='${viewData.dashboard.otherManagingExperienceString}'/>
                </div>
            </div>

            <!-- Budget -->
            <div class="row budgetRow experienceRow <s:if test="#contestStatus != 'Draft'">hide</s:if>">
                <label for="projectBudgetInput">Set Budget :</label>

                <ul>
                    <li>
                        <input type="radio" class="radio" name="budget" id="haveBudget" <c:if test="${not empty viewData.dashboard.budget}">checked='checked'</c:if> />
                        <label for="haveBudget">a. I have a budget</label>
                         $&nbsp;<input id="projectBudgetInput" type="text" class="text" value="${viewData.dashboard.budget}" />
                    </li>
                    <li>
                        <input type="radio" class="radio" name="budget" id="notHaveBudget" <c:if test="${empty viewData.dashboard.budget}">checked='checked'</c:if>/>
                        <label for="notHaveBudget">b. I don't have a budget yet.</label>
                    </li>
                </ul>
            </div>
            <div></div>
            <br/>
            <div class="row budgetRow experienceRow">
                        <label for="contestNameInput2">Created By:</label>
                        <span id="challegneCreatorLabel" > ${projectHeader.creator}</span>
            </div>



            <p class="save">
                <a class="cancel_text" href="javascript:" id="cancelContestInfo">cancel</a>
                <a href="javascript:" id="saveContestInfo">
                    <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
            </p>
        </div>
    </div>
</div>
<!-- end .addNewContest -->
<div class="editMask greybg">
<!-- Prize Display -->
<div class="no_details contest_prize infoPanel">
    <div class="caption_det_prize">
        <s:if test="#contestStatus eq 'Draft' || #contestStatus eq 'Scheduled' || #contestStatus eq 'Active'">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" style="padding-top:20px; padding-right:8px" alt="edit" src="/images/edit-icon.png"/> </a>
         </s:if>
        <div style="height:30px">
            <h2>Prizes </h2>

        </div>
    </div>
    <!-- End .caption -->
    <div class="detailsContent_det_prize">

        <table cellspacing="10" class="det_font_tab">
            <tr class="rightbor">
                <td style="width:120px" align="left"><strong>1st Place:</strong> $<span id="rswFirstPlace"> <c:out value="${projectHeader.prizes[0].prizeAmount}"/></span></td>
                </td>
                <td style="width:120px" align="left"><strong>2nd Place:</strong> $<span id="rswSecondPlace"> <c:out value="${projectHeader.prizes[1].prizeAmount}"/></span></td>
                <td style=""><strong>Challenge Fee:</strong> $<span id="rswContestFee"><c:out value="${projectHeader.properties['Admin Fee']}"/></span> <span class="percentageInfo"></span>
                    <c:if test="${projectHeader.properties['Contest Fee Percentage'] != null}">
                        <input type="hidden" id="contestFeePercentageValue" value="${projectHeader.properties['Contest Fee Percentage']}" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="sec_tab_prize"><strong>Estimated Challenge Total:</strong> $<span id="rswTotal">
                 <c:out value="${projectHeader.prizes[0].prizeAmount  + projectHeader.prizes[1].prizeAmount + projectHeader.properties['Admin Fee']}"/></span></td>
            </tr>

        </table>
        <div class="totalCostContainer">
                 <p style="padding-left:10px" class="note">
                  Note: Challenge prizes, costs, and fees in this section are estimates. <br>
                  Actual costs are based on prizes paid, review fees based on number of submissions, reliability bonuses incentives paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
                </div>
    </div>
    <!-- End .detailsContent -->
</div>
<!-- End .details -->
<!-- End Prize Display -->

<!-- Prize Edit -->
<div class="no_details contest_prize_edit editPanel hide">
    <div class="caption_det_prize_edit">
        <div class="captionInner">
            <h2>Prizes</h2>
        </div>
    </div>
    <!-- End .caption -->

    <div class="detailsContent_det_prize_edit">


        <div class="prizes">
            <div style="height:120px;padding:6px;">
                <span class="head"><p>Please Set the prize below:</p></span>
                <br/>

                <div class="prizesInner">
                    <label class="first">1st Place</label>
                    <span class="dw">$</span>
                    <input type="text" class="prizesInput" name="firstPlacePrize" value='<c:out value="${projectHeader.prizes[0].prizeAmount}"/>'
                          />
                    <label class="second">2nd Place</label>
                    <span class="dw">$</span>
                    <span id="swSecondPlace" class="prizeInfo" style="line-height:30px;"><c:out value="${projectHeader.prizes[1].prizeAmount}"/></span>
                    <span class="mid_info">Challenge Fee:&nbsp;&nbsp;$  <span id="swContestFee"><c:out
                            value="${projectHeader.properties['Admin Fee']}"/></span><span class="percentageInfo"></span></span>
                </div>
                <br/>
                <!--<span class="last_info"><strong>Estimated Challenge Total:&nbsp;&nbsp;$ <span id="swTotal"> <c:out value="${projectHeader.prizes[0].prizeAmount  + projectHeader.prizes[1].prizeAmount + projectHeader.properties['Admin Fee']}"/></span></strong></span>-->
                <div class="totalCostContainer"><strong>Estimated Challenge Total:&nbsp;&nbsp;$ <span id="swTotal"></span></strong>
                <br>
                <p class="note">
                  Note: Challenge prizes, costs, and fees in this section are estimates. <br>
                  Actual costs are based on prizes paid, review fees based on number of submissions, reliability bonuses incentives paid, co-pilot fees, and so on.  Challenge fees are also part of the final costs. </p>
                </div>
            </div>
        </div>
        <!-- end .prizes -->

        <p class="save">
            <a class="cancel_text" href="javascript:" id="cancelPrize">cancel</a>
            <a href="javascript:" id="savePrize">
                <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
        </p>

    </div>
    <!-- End .detailsContent -->
</div>
<!-- End .details -->
<!-- End Prize Edit -->

</div>

<!-- Contest Schedule -->
<div class="editMask">
    <div class="infoPanel scheduleInfo ">
        <h3>
            <span class="icon">Schedule</span>
            <if:isEditable typedContestBrief="${contestDTO}">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" alt="edit" src="/images/edit-icon.png"/> </a>
            </if:isEditable>
        </h3>

        <div class="infoPanelMask">
            <ul>
                <li>
                    <label style="width:200px;">Start Date/Time:</label>
                    <span id="startDateLabel"><fmt:formatDate value="${tcdirect:toDate(assetDTO.productionDate)}"
                                                              pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></span>
                </li>
                <li>
                    <label style="width:200px;">Submission End Date/Time:</label> <span id="subEndDateLabel">${subEndDate}</span>
                </li>
                <li>
                    <label style="width:200px;">End Date/Time:</label> <span id="endDateLabel">${contestEndDate}</span>
                </li>
            </ul>
        </div>
    </div>
    <div class="schedule editPanel">
        <h3>
            <span class="icon">Schedule</span>
        </h3>

        <div class="editPanelMask">
        <!-- Start -->
            <div class="row">
                <label for="start2DateInput">Start:</label>
                <s:textfield cssClass="text date-pick" id="start2DateInput" readonly="true"/>

                <div class="startEtSelect">
                    <select id="start2TimeInput">
                        <jsp:include page="/WEB-INF/includes/common/timeOptions.jsp"/>
                    </select>
                </div>
                <span id="start2Timezone"><fmt:formatDate value="<%= new java.util.Date()%>"
                                      pattern="z" timeZone="${defaultTimeZone}"/></span>
            </div>

            <p class="save">
                <a class="cancel_text" href="javascript:" id="cancelDates">cancel</a>
                <a href="javascript:" id="saveDates">
                    <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
            </p>
        </div>
    </div>
</div>
<!-- end .schedule -->

<!-- Contest Description -->

<div class="editMask greybg">
    <div class="htmlDescription descriptionInfo ">
        <h3>
            <span class="icon">Description that you want everyone to see</span>
            <s:if test="#contestStatus eq 'Draft' || #contestStatus eq 'Scheduled' || #contestStatus eq 'Active'">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" alt="edit" src="/images/edit-icon.png"/> </a>
            </s:if>
        </h3>

        <div class="infoPanelMask previewMask">
            <div id="publicDescriptionText">${projectHeader.projectSpec.detailedRequirements}</div>
        </div>
    </div>
    <div class="description editPanel">
        <h3>
            <span class="icon">Enter a description that you want everyone to see</span>
        </h3>

        <div class="editPanelMask">
            <div class="textarea">
                <textarea rows="15" cols="80" style="width:100%;"
                          id="publicCopilotPostingDescription2">${projectHeader.projectSpec.detailedRequirements}</textarea>
            </div>

            <p class="save">
                <a class="cancel_text" href="javascript:" id="cancelPublicDesc">cancel</a>
                <a href="javascript:" id="savePublicDesc">
                    <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
            </p>
        </div>
    </div>
</div>
<!-- end .description -->

<div class="editMask">
    <div class="htmlDescription descriptionInfo ">
        <h3>
            <span class="icon">Description that is only viewable to copilots that register for this posting</span>
            <s:if test="#contestStatus eq 'Draft' || #contestStatus eq 'Scheduled' || #contestStatus eq 'Active'">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" alt="edit" src="/images/edit-icon.png"/> </a>
            </s:if>
        </h3>

        <div class="infoPanelMask previewMask">
            <div id="privateDescriptionText">${projectHeader.projectSpec.privateDescription}</div>
        </div>
    </div>
    <div class="description editPanel">
        <h3>
            <span class="icon">Enter a description that is only viewable to copilots that register for this posting</span>

        <div class="editPanelMask">
            <div class="textarea">
                <textarea rows="15" cols="80" style="width:100%;"
                          id="privateCopilotPostingDescription2">${projectHeader.projectSpec.privateDescription}</textarea>
            </div>

            <p class="save">
                <a class="cancel_text" href="javascript:" id="cancelPrivateDesc">cancel</a>
                <a href="javascript:" id="savePrivateDesc">
                    <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
            </p>
        </div>
    </div>
</div>
<!-- end .description -->


<!-- fileUpload -->
<div class="editMask greybg">
    <div class="infoPanel fileUploadInfo ">
        <h3>
            <span class="icon">Files</span>
            <s:if test="#contestStatus eq 'Draft' || #contestStatus eq 'Scheduled' || #contestStatus eq 'Active'">
                <a href="javascript:" class="editLink">
                    <img class="edit_type" alt="edit" src="/images/edit-icon.png"/> </a>
            </s:if>
        </h3>

        <div class="infoPanelMask">
        <table id="uploadedDocumentsTable">
            <c:forEach items="${assetDTO.documentation}" var="doc" varStatus="loop">
                <tr id="rowDoc_${doc.id}">
                    <c:set var="docUrl" value="${doc.url}"/>
                    <c:set var="splitURI" value="${fn:split(docUrl, '/')}"/>
                    <c:set var="docName" value="${splitURI[fn:length(splitURI)-1]}"/>
                    <td class="fileName"><span>${loop.index + 1}.</span>
                        <a href="${ctx}/launch/downloadDocument?documentId=${doc.id}"><c:out value="${docName}"/></a></td>
                    <td class="fileDesc"><c:out value="${doc.documentName}" /></td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>
    <div class="fileUpload editPanel">
        <h3>
            <span class="icon">Files (20MB maximum)</span>
        </h3>

        <div class="editPanelMask">
            <dl>
                <dd class="addFile">
                    <div class="attachFile" id="attachFileDiv2">
                        <input type="file" class="fileInput" name="document"/>
                        <table>
                            <tbody>
                            <tr>
                                <td class="fakeText"><input type="text" id="fakeTextInput2"/></td>
                                <td class="uploadBtn"><a href="javascript:" class="button6">
                                    <span class="left"><span class="right">CHOOSE...</span></span></a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="attachDesc">
                        <table>
                            <tbody>
                            <tr>
                                <td class="descInput"><input type="text" id="fileDescription2"/></td>
                                <td class="addDelBtn">
                                    <a class="addButton" id="fileUploadBtn2" href="javascript:"><span class="hide">ADD</span></a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </dd>
            </dl>
            <p class="save">
                <a class="cancel_text" href="javascript:" id="cancelFiles">cancel</a>
                <a href="javascript:" id="saveFiles">
                    <img class="save_btn" alt="save" src="/images/save_change.png"/></a>
            </p></div>
    </div>
</div>
<!-- end .fileUpload -->

</div>
<!-- end #launchContestOut -->
<if:isDraft typedContestBrief="${contestDTO}">
    <div id="resubmit">
          <a href="javascript:activateContest();" class="activateButton"></a>
    </div>
</if:isDraft>


</div>
</div>
</div>

</div>
</div>
<!-- end .getCopilotsStep -->


</div>
<!-- end .orderReview -->


</div>
<!-- end #copilotsIntroduction -->

<!-- end #launchContestOut -->
</div>
<!-- End.area1Content -->
</div>
<!-- End #area1 -->

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
<!-- this area will contain the popups of this page -->
<div id="saveAsDraft" class="acceptRejectPopup hide">
    <div class="popupMask">
        <div class="popupWrap">
            <div class="popupContent">
                <dl>
                    <dt>Your Copilot Selection Challenge has been saved as draft</dt>

                    <dd class="yesno">
                         <a href="#" class="button6" id="saveAsDraftOK"><span class="left"><span class="right">OK</span></span></a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</div>
<div id="savedAsDraftAndActivated" class="acceptRejectPopup hide">
    <div class="popupMask">
        <div class="popupWrap">
            <div class="popupContent">
                <dl>
                    <dt>Your Copilot Selection Challenge has been activated successfully</dt>

                    <dd class="yesno">
                         <a href="#" class="button6" id="saveAsDraftOK2"><span class="left"><span class="right">OK</span></span></a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</div>
<div class="popups">
    <!-- Add Project Dialog-->
    <div id="addProjectDialog" title="Create New Project" class="dialog-box hide">
        <div id="addProjectForm">
            <div class="fi">
                <label for="projectName">Name:</label>
                <input id="projectName" name="projectName" type="text" width="30" maxlength="255"/>
            </div>
            <div class="fi">
                <label for="projectDescription">Description:</label>
                <textarea id="projectDescription" name="projectDescription" rows="5" cols="30"></textarea>
            </div>
            <div class="popupButtons">
                <a href="javascript:" onclick="closeDialog(this);" class="button1"><span>CANCEL</span></a>
                <a href="javascript:" onclick="addNewProject();" class="button1"><span>YES</span></a>
            </div>
        </div>
        <!-- End #addProjectForm -->

        <div id="addProjectResult">
            <p></p>

            <div class="popupButtons">
                <a href="javascript:" onclick="closeDialog(this);" class="button1"><span>Close</span></a>
            </div>
        </div>
        <!-- End #addProjectResult -->
    </div>
    <!-- End #addProjectDialog -->
</div>
<div id="uploadedDocumentTemplate" class="hide">
    <dd id="doc{0}" class="uploadedDocumentItem">
        <div class="attachFile"><span style="line-height:30px"><span style="margin-right:5px"><b>File Name:</b></span><span class="uploadedDocumentItemFileName">{1}</span></span></div>
        <div class="attachDesc">
            <table>
                <tbody>
                <tr>
                    <td class="descInput"><span style="margin-right:5px"><b>Description:</b></span>{2}</td>
                    <td class="addDelBtn">
                        <a class="removeButton" href="javascript:removeFileItem('{0}', newDocuments);" id="removeButton{0}">
                            <span class="hide">REMOVE</span></a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </dd>
</div>

<div id="existingDocumentTemplate" class="hide">
    <tr id="rowDoc_{0}">
        <c:set var="docUrl" value="${doc.url}"/>
        <c:set var="splitURI" value="${fn:split(docUrl, '/')}"/>
        <c:set var="docName" value="${splitURI[fn:length(splitURI)-1]}"/>
        <td class="fileName"><span>${loop.index + 1}.</span>
            <a href="${ctx}/launch/downloadDocument?documentId={0}"><c:out value="${docName}"/></a></td>
        <td class="fileDesc"><c:out value="${doc.documentName}"/></td>
    </tr>
</div>


<!-- End .popups -->
<jsp:include page="includes/popups.jsp"/>
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->
</html>
