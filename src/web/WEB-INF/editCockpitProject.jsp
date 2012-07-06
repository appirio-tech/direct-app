<%--
  - Author: GreatKevin
  - Version: 1.2.0
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This JSP page is the edit project page.
  -
  - (Version 1.1 Release Assembly - TC Cockpit Edit Project and Project General Info Update) changes:
  - - Add edit and save of project ratings.
  -
  - (version 1.2 Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
  - - Add edit of project type & category
  - - Add manage of project permissions, notifications and contest notifications for the user.
  -
  - Version 1.0 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <link rel="stylesheet" href="/css/editProject.css?v=213353" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/editCockpitProject.js?v=213353"></script>
    <ui:projectPageType tab="editProject"/>
</head>

<body id="page" class="editPage">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">

<jsp:include page="includes/header.jsp"/>

<div id="mainContent">

<jsp:include page="includes/right.jsp"/>

<div id="area1"><!-- the main area -->
<div class="area1Content">
<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
    <a href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="formData.projectId"/></s:url>"><s:property
            value="sessionData.currentProjectContext.name"/>
    </a> &gt;
    <strong>Edit Project Details</strong>
</div>

<div class="spaceWhite"></div>
<h2 class="contestTitle">Edit Project Details
    <a name="saveProject" class="buttonRed1 triggerModal saveProjectButton"
                                                 href="javascript:;"><span>SAVE</span></a>
    <a class="buttonRed1 cancelProjectButton" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="formData.projectId"/></s:url>"><span>CANCEL</span></a>
</h2>

<form class="editProjectForm" action="">
<input type="hidden" name="editProjectId" value="${formData.projectId}"/>
<div class="projectMetaArea singleMetaArea" id="editProjectName">
    <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Name :</h3>

    <div class="projectMetaAreaField extendInput">
        <input id="lim60" name="projectName" type="text" value="<s:property value='viewData.project.name'/>"
               class="textInput wholeLine validateLength" maxlength="60" autocomplete="off"/>
        <span class="hintTxt"><span id="projectNameError" class="customKeyError"></span> <span> 60 characters remaining</span> </span>
    </div>
</div>
<!-- End .projectMetaArea -->

<div class="projectMetaArea singleMetaArea extendInput" id="editProjectDescription">
    <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Description :</h3>

    <div class="projectMetaAreaField">
        <textarea id="lim2000" name="projectDesc" class="wholeLine validateLength" cols="1" rows="5" autocomplete="off"><s:property value='viewData.project.description'/></textarea>
        <span class="hintTxt"><span id="projectDescriptionError" class="customKeyError"></span> <span> 2000 characters remaining</span> </span>
    </div>
</div>
<!-- End .projectMetaArea -->


<!-- start project type selection -->
<div class="projectMetaArea singleMetaArea projectTypeArea">
    <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Type:</h3>
    <div class="comboContainer">
        <div class="rightSide">
            <span class="subLabel">Category :</span>
            <s:select list="viewData.projectCategories" listKey="projectCategoryId" listValue="name"
                      name="projectCategory" value="viewData.project.projectCategory.projectCategoryId"/>
        </div>
        <span class="subLabel firstSubLabel">Type :</span>
        <s:select list="viewData.projectTypes" listKey="projectTypeId" listValue="name"
                  name="projectType" value="viewData.project.projectType.projectTypeId"/>
    </div>
</div>
<!-- end project type selection -->

<div class="projectMetaArea multiMetaArea" id="editProjectInfo">
    <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Information :</h3>

    <s:if test="viewData.hasFullPermission">

    <div class="projectMetaAreaField oddRowItem">
        <h4 class="projectMetaLabel cmIcon">Client Manager Handles :</h4>

        <div class="memberList">
            <ul>
                <s:iterator value="viewData.clientManagerIds" var="id">
                    <li class="memberLink"><span class='hide' name="${id}"></span><link:user userId="${metadataValue}" styleClass="memberLink"/></li>
                </s:iterator>
            </ul>
            <a name="clientManagersModal" class="buttonRed1 triggerModal triggerManagerModal" href="javascript:;"><span>ADD/REMOVE</span></a>
        </div>
    </div>

    <div class="projectMetaAreaField">
        <h4 class="projectMetaLabel pmIcon">Project Manager Handles :</h4>

        <div class="memberList">
            <ul>
                <s:iterator value="viewData.tcManagerIds" var="id">
                    <li class="memberLink"><span class='hide' name="${id}"></span><link:user userId="${metadataValue}" styleClass="memberLink"/></li>
                </s:iterator>
            </ul>

            <a name="projectManagersModal" class="buttonRed1 triggerModal triggerManagerModal" href="javascript:;"><span>ADD/REMOVE</span></a>
        </div>
    </div>

    </s:if>

    <div class="projectMetaAreaField oddRowItem budgetField">
        <h4 class="projectMetaLabel pbIcon">Project Budget :</h4>

        <div class="sliderContainer">
            <div id="budgetSlider"></div>
            <div class="budgetWrapper">
                <input class="output text" name="${viewData.budget.id}" id="budgetOutput" value="<s:property value='viewData.budget.metadataValue'/>" autocomplete="off"/>
                <span>U.S. Dollar</span>
            </div>
        </div>
    </div>

    <div class="projectMetaAreaField durationField">
        <h4 class="projectMetaLabel pdIcon">Project Duration :</h4>

        <div class="durationPanel">
            <div class="sliderView">
                <input type="radio" class="radio radioFix" name="projectDuration" checked="checked" autocomplete="off"/>

                <div id="durationSlider"></div>
            </div>
            <div class="datePickerView">
                <input type="radio" class="radio radioFix" name="projectDuration"/>
                <span>Specific Dates</span>
                <input id="startDate" name="startDate" type="text" value="mm/dd/yyyy" class="text date-pick"
                       readonly="readonly"/>
                <strong>to</strong>
                <input id="endDate" name="endDate" type="text" value="mm/dd/yyyy" class="text date-pick"
                       readonly="readonly"/>
            </div>
        </div>
        <div class="durationOutputPanel">
            <input id="durationOutput" name="${viewData.duration.id}" type="text" class="text" value="<s:property value='viewData.duration.metadataValue'/>" autocomplete="off"/> Days
        </div>
    </div>

    <div class="projectMetaAreaField fieldWithFullRowInput oddRowItem">
        <h4 class="projectMetaLabel psvnIcon">Project SVN Address :</h4>

        <div class="inputContainer">
            <input id="svnAddress" name="${viewData.svnURL.id}" type="text" value="<s:property value='viewData.svnURL.metadataValue'/>" class="textInput" autocomplete="off"/>
        </div>
    </div>

    <div class="projectMetaAreaField fieldWithFullRowInput">
        <h4 class="projectMetaLabel pjiraIcon">Bug Tracker Address :</h4>

        <div class="inputContainer">
            <input id="jiraAddress" name="${viewData.jiraURL.id}" type="text" value="<s:property value='viewData.jiraURL.metadataValue'/>" class="textInput" autocomplete="off"/>
        </div>
    </div>

</div>
<!-- End .projectMetaArea -->

<div class="projectMetaArea multiMetaArea customeMetaArea" id="editCustomProjectMetadata">
    <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Additional Project Information :</h3>

    <s:if test="viewData.clientId == null">
        <span class="customeMetaAreaWarning">Additional Project Information is only available to project associated with the customer</span>
    </s:if>
    <s:else>
        <input type="hidden" name="clientId" value="${viewData.clientId}"/>

        <s:iterator value="viewData.customMetadata" status="stat">

            <div class="projectMetaAreaField <s:if test='#stat.odd'> oddRowItem </s:if> <s:if test='key.single'>multiValueArea </s:if>">
                <h4 class="projectMetaLabel">${key.name}
                    <s:if test='key.single'>
                        (Single):
                    </s:if>
                    <s:else>
                        (Multiple):
                    </s:else>
                </h4>

                <div class="inputCWrapper" id="customKey${key.id}">
                    <s:if test='key.single'>

                        <div class="inputContainer inputWrapper">

                            <s:if test='value.size == 0'>
                                <input name="customMetadataValue" type="text" value="" class="textInput" autocomplete="off" />
                            </s:if>
                            <s:else>
                                <input id="customMetadata${value[0].id}" name="customMetadataValue" type="text"
                                       value="${value[0].metadataValue}" class="textInput" autocomplete="off" />
                            </s:else>
                            <a href="javascript:;" class="clearValue">Clear</a>
                        </div>

                    </s:if>
                    <s:else>

                        <s:iterator value='value'>

                            <div class="inputContainer inputWrapper">
                                <input name="customMetadataValue" id="customMetadata${id}" type="text"
                                       value="${metadataValue}" class="textInput" autocomplete="off" />
                                <a href="javascript:;" class="clearValue">Clear</a>
                            </div>

                        </s:iterator>

                        <s:if test='value.size == 0'>
                            <div class="inputContainer inputWrapper">
                                <input name="customMetadataValue" type="text" value="" class="textInput" autocomplete="off" />
                                <a href="javascript:;" class="clearValue">Clear</a>
                            </div>
                        </s:if>


                        <a href="javascript:;" class="buttonGray moreValue"><span>More Value</span></a>
                    </s:else>
                </div>


            </div>

        </s:iterator>
        <div class="projectMetaAreaField addCustomMetaBtnContainer oddRowItem">
            <a name="addCustomeMeta" href="javascript:;" class="buttonRed1 triggerModal"><span>ADD CUSTOM PROJECT METADATA</span></a>
        </div>
    </s:else>

</div>

<!-- End .projectMetaArea -->
<div class="metaAreaLeft">
    <div class="projectMetaArea singleMetaArea pStatus" id="editProjectStatus">
        <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Status :</h3>
        <ul class="projectMetaAreaField radioContainer">
            <li>
                <input autocomplete="off" name="projectStatus" type="radio" value="5"
                       <s:if test='viewData.project.projectStatusId == 5L'>checked="checked"</s:if> />
                <label class="draftStatus">Draft</label>
            </li>
            <li>
                <input autocomplete="off" name="projectStatus" class="radioFix" type="radio" value="1"
                       <s:if test='viewData.project.projectStatusId == 1L'>checked="checked"</s:if> />
                <label class="activeStatus">Active</label>
            </li>
            <li>
                <input autocomplete="off" name="projectStatus" type="radio" value="2"
                       <s:if test='viewData.project.projectStatusId == 2L'>checked="checked"</s:if> />
                <label class="archivedStatus">Archived</label>
            </li>
            <li>
                <input autocomplete="off" name="projectStatus" type="radio" value="3"
                       <s:if test='viewData.project.projectStatusId == 3L'>checked="checked"</s:if> />
                <label class="deletedStatus">Deleted</label>
            </li>
            <li>
                <input autocomplete="off" name="projectStatus" type="radio" value="4"
                       <s:if test='viewData.project.projectStatusId == 4L'>checked="checked"</s:if> />
                <label class="completedStatus">Completed</label>
            </li>
        </ul>
    </div>


    <div class="projectMetaArea singleMetaArea isPrivate hide" id="editProjectPrivacy">
        <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Privacy :</h3>

        <div class="projectMetaAreaField radioContainer">
            <span class='hide' id="privacyMetadataId" name="${viewData.privacy.id}"></span>
            <input autocomplete="off" name="privateFlag" type="radio" value="true" class="radioFix" <s:if
                    test='viewData.privacy != null && viewData.privacy.metadataValue == "true"'> checked="checked" </s:if>/>
            <label>Yes</label>
            <input autocomplete="off" name="privateFlag" type="radio" value="false" <s:if
                    test='viewData.privacy == null || viewData.privacy.metadataValue == "false"'> checked="checked" </s:if> />
            <label>No</label>
        </div>
    </div>
</div>

<div class="metaAreaRight">
    <div class="projectMetaArea pRatings">
        <h3 class="projectMetaAreaLabel"><a class="toolTipIcon" href="javascript:;"></a>Project Ratings :</h3>
        <ul class="projectMetaAreaField">
            <li>
                <input type="hidden" name="key" value="10"/>
                <s:if test="viewData.businessImpactRating != null">
                    <label>${viewData.businessImpactRating.projectMetadataKey.name}</label>
                    <input type="hidden" name="rating" value="${viewData.businessImpactRating.metadataValue}">
                    <input type="hidden" name="metadataId" value="${viewData.businessImpactRating.id}">
                </s:if>
                <s:else>
                    <label>Business Impact</label>
                    <input type="hidden" name="rating" value="0">
                </s:else>
                <div class="ratingEdit"></div>
            </li>
            <li>
                <input type="hidden" name="key" value="11"/>
                <s:if test="viewData.riskLevelRating != null">
                    <label>${viewData.riskLevelRating.projectMetadataKey.name}</label>
                    <input type="hidden" name="rating" value="${viewData.riskLevelRating.metadataValue}">
                    <input type="hidden" name="metadataId" value="${viewData.riskLevelRating.id}">
                </s:if>
                <s:else>
                    <label>Risk Level</label>
                    <input type="hidden" name="rating" value="0">
                </s:else>
                <div class="ratingEdit"></div>
            </li>
            <li>

                <input type="hidden" name="key" value="12"/>
                <s:if test="viewData.costLevelRating != null">
                    <label>${viewData.costLevelRating.projectMetadataKey.name}</label>
                    <input type="hidden" name="rating" value="${viewData.costLevelRating.metadataValue}">
                    <input type="hidden" name="metadataId" value="${viewData.costLevelRating.id}">
                </s:if>
                <s:else>
                    <label>Cost</label>
                    <input type="hidden" name="rating" value="0">
                </s:else>
                <div class="ratingEdit"></div>
            </li>
            <li>
                <input type="hidden" name="key" value="13"/>
                <s:if test="viewData.difficultyRating != null">
                    <label>${viewData.difficultyRating.projectMetadataKey.name}</label>
                    <input type="hidden" name="rating" value="${viewData.difficultyRating.metadataValue}">
                    <input type="hidden" name="metadataId" value="${viewData.difficultyRating.id}">
                </s:if>
                <s:else>
                    <label>Difficulty</label>
                    <input type="hidden" name="rating" value="0">
                </s:else>
                <div class="ratingEdit"></div>
            </li>

        </ul>

            <div class="clearFix"></div>

    </div>

</div>
<div class="clearFix"></div>

<!-- End .projectMetaArea -->
</form>
<!-- End .projectMetaArea -->

<!-- End form.editProjectForm -->

<div class="editProjectSaveBtnContainer">
    <a class="buttonRed1 cancelProjectButton" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="formData.projectId"/></s:url>"><span>CANCEL</span></a>
    <a name="saveProject" class="buttonRed1 triggerModal saveProjectButton" href="javascript:;"><span>SAVE</span></a>
    <a name="leavePageModal" id="showLeaveButton" class="buttonRed1 triggerModal hidden"
       href="javascript:;"><span>SAVE</span></a>
</div>


<!-- Permissions & Notifications -->
<s:if test="viewData.canAccessPermissionNotification">

<div class="permissionsNotifications">
    <a name="permissionsNotifications"/>
    <h3>Project Permissions &amp; Notifications</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="tableHeader">
        <colgroup>
            <col width="15%" />
            <col width="45%" />
            <col />
            <col />
            <col width="100" />
        </colgroup>
        <thead>
        <tr>
            <th class="userColumn"><span>User</span></th>
            <th>
                <ul>
                    <li>Report</li>
                    <li>Read</li>
                    <li>Write</li>
                    <li>Full Access</li>
                </ul>
            </th>
            <th>Project Forum Notification</th>
            <th>Contest Notifications</th>
            <th><a name="addUserModal" class="buttonRed1 triggerModal" href="javascript:;" id="addUser"><span>ADD USER</span></a></th>
        </tr>
        </thead>
        <tbody>
            <s:iterator value="viewData.projectPermissions">
                <tr>
                    <td class="permissionUser">
                        <a href="javascript:;" class="useName"><s:property value='userHandle'/></a>
                        <input type="hidden" value="<s:property value='userId'/>" />
                    </td>
                    <td>
                        <ul>
                            <li><input type="radio" name="radio<s:property value='userId'/>" <s:if test="permissionType.permissionTypeId == 0L">checked="checked"</s:if> />
                            </li>
                            <li><input type="radio" name="radio<s:property value='userId'/>" <s:if test="permissionType.permissionTypeId == 1L">checked="checked"</s:if> />
                            </li>
                            <li><input type="radio" name="radio<s:property value='userId'/>" <s:if test="permissionType.permissionTypeId == 2L">checked="checked"</s:if> />
                            </li>
                            <li><input type="radio" name="radio<s:property value='userId'/>" <s:if test="permissionType.permissionTypeId == 3L">checked="checked"</s:if> />
                            </li>
                        </ul>
                    </td>
                    <td class="alignCenter">
                        <input type="checkbox" <s:if test="viewData.projectForumNotifications[userId]">checked="checked"</s:if> />
                    </td>
                    <td class="alignCenter"><a name="settingModal" class="setting triggerModal" href="javascript:;">Setting</a></td>
                    <td class="alignCenter"><a name="preloaderModal" class="triggerModal remove"  href="javascript:;">Remove</a></td>
                </tr>

            </s:iterator>
        </tbody>
    </table>
</div>


<!-- End. permissionsNotifications -->

<div class="permissionsButtonContainer">
    <a class="buttonRed1 cancelProjectButton" href="<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="formData.projectId"/></s:url>"><span>CANCEL</span></a>
    <a id="savePermissionNotification" class="buttonRed1" href="javascript:;"><span>SAVE PERMISSIONS</span></a>
</div>
</s:if>

</div>


</div>
<!-- End .container2 -->
</div>
</div>
</div>

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

<jsp:include page="includes/project/edit/editProjectPageModals.jsp"/>

</body>
<!-- End #page -->

</html>
