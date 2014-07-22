<%--
  - Author: GreatKevin, TCSASSEMBLER
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Asset View Release 3)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
  - - Update the permission setting to be Project / Public, remove private permission settings
  -
  - Version 1.2 (TC Direct Rebranding Assembly Project and Contest related pages)
  - Use new font "Source Sans Pro" for the page
  -
  - Copyright (C) 2013 -2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the assets batch edit.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <ui:projectPageType tab="assets"/>
    <title>[topcoder] Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <jsp:include page="../../includes/serverConfiguration.jsp"/>

    <!-- External CSS -->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
    <link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/dashboard.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/filter-panel.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css"/>
    <!-- Assets CSS -->
    <link rel="stylesheet" href="/css/direct/assets.css" media="all" type="text/css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/filter-panel-ie7.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/assets-ie7.css"/>
    <![endif]-->

    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css"/>
    <![endif]-->

    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css"/>
    <![endif]-->

    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/common.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js?v=209582"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=215325"></script>

    <script type="text/javascript" src="/scripts/dashboard.js?v=215352"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js?v=211035"></script>
    <script type="text/javascript" src="/scripts/maintenance.js?v=2146111"></script>
    <script type="text/javascript" src="/scripts/instantSearch.js"></script>

    <!-- Table Data -->
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>

    <script type="text/javascript" src="/scripts/search.js"></script>
    <!-- File Upload -->
    <script type="text/javascript" src="/scripts/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="/scripts/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="/scripts/jquery.fileupload.js"></script>
    <script type="text/javascript" src="/scripts/assets.js"></script>

</head>

<body id="page">

<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="asset">
<jsp:include page="../../includes/header.jsp"/>


<div id="mainContent" class="mainContentOverflowVisible newSidebarCollapse">
<jsp:include page="../../includes/right.jsp"/>

<div id="area1"><!-- the main area -->
<div class="area1Content">
<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
    &gt;
    <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
    <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
            value="sessionData.currentProjectContext.name"/></a> &gt;
    <strong>Files</strong>
</div>


<div class="areaHeader">
    <h2 class="title asssetsTitle">Files</h2>
    <input type="hidden" value="<s:property value='sessionData.currentSelectDirectProjectID'/>" id="assetProjectId"/>
</div>
<!-- End .areaHeader -->
<div class="uploadSection" id="bitchEditSection">
<div class="sectionHeader">
    <div class="sectionHeaderL">
        <div class="sectionHeaderR">
            <div class="sectionHeaderM">
                <div class="editBitchButtons">
                    <a class="button6 btnBatchEditSave" href="javascript:"><span class="left"><span class="right">SAVE DETAILS</span></span></a>
                    <a class="button7" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="left"><span class="right">DISCARD</span></span></a>
                </div>
                <!-- End allFileProcessBar -->
                <h2>Batch Edit</h2>
            </div>
        </div>
    </div>
</div>
<!-- End .sectionHeader -->
<div class="sectionContainer" id="processing">

<div class="fileUploadingSection">

<div class="sectionInner batchEditSection">

<s:iterator value="assetsToEdit" status="stat">
    <div class="itemSection">
        <input type="hidden" value="<s:property value="categories[0].id"/>" name="categoryId"/>
        <input type="hidden" value="<s:property value="id"/>" name="assetId"/>
        <input type="hidden" value="<s:property value="public"/>" name="isPublic"/>
        <div class="fileUploadItem">
            <a href="javascript:;" class="removeFileUploadItem">Remove</a>
            <div class="icon-file"><a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"><img src="/images/icon-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png" alt="${currentVersion.fileType}" /></a></div>
            <p class="fileName"><a title="<s:property value='name'/>" href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>">${tcdirect:limitStringLength(name, 80)}</a></p>
            <div class="bitchEdit">
                <p><c:out
                        value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/> Version <s:property value="currentVersion.version"/></p>
                <p>Uploaded by: <link:user userId="${currentVersion.uploader.id}"/></p>
                <p><s:date name="currentVersion.uploadTime" format="MM/dd/yyyy HH:mm"/> EDT</p>
            </div>
            <div class="selectCategory">
                <label>Category :</label>
                <select>
                    <option value="-1">- Please Select the asset category -</option>
                    <s:iterator value="assetCategories">
                        <option value='<s:property value="id"/>'><s:property value="name"/></option>
                    </s:iterator>
                </select>
                <div class="linksBox">
                    <a href="javascript:;" class="LinkNewCategory">New Category</a>
                </div>
                <div class="popUpNewCategory">
                    <div class="popUpNewCategoryInner">
                        <div class="popUpNewCategoryArrow"></div>
                        <label>Enter Category Name</label>
                        <input type="text" />
                        <div class="linksBox">
                            <a href="javascript:;" class="linkAddCategory">New Category</a>
                            <span>|</span>
                            <a href="javascript:;" class="linkCancel">Cancel</a>
                        </div>
                    </div>
                </div>
                <!-- New Category -->
            </div>
            <div class="addDescription">
                <label>Description :</label>
                <div class="textarea">
                    <textarea rows="" cols=""><s:property value="currentVersion.description"/></textarea>
                </div>
            </div>
            <div class="selectAccess">
                <strong class="label">Access Restriction</strong>
                <div class="radioBox">
                    <input type="radio" name="accessRadio<s:property value="#stat.count" />"  id="accessRadioPublic<s:property value="#stat.count" />" class="project" <s:if test="public == false">checked="checked"</s:if>    />
                    <label for="accessRadioPublic<s:property value="#stat.count" />" class="public">Project</label>
                    <input type="radio" name="accessRadio<s:property value="#stat.count" />" id="accessRadioPrivate<s:property value="#stat.count" />" class="public" <s:if test="public == true">checked="checked"</s:if> />
                    <label for="accessRadioPrivate<s:property value="#stat.count" />" class="public">Public</label>
                </div>
            </div>
        </div>
        <!-- End .fileUploadItem -->
    </div>
</s:iterator>

</div>

</div>
<!-- End .fileUploadingSection -->

</div>
<!-- End .sectionContainer -->

<div class="uploadButtonBox">
    <a class="button6 btnBatchEditSave" href="javascript:"><span class="left"><span class="right">SAVE DETAILS</span></span></a>
    <a class="button7" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="left"><span class="right">DISCARD</span></span></a>
</div>

</div>

</div>
</div>

</div>
<!-- End #mainContent -->

<jsp:include page="../../includes/footer.jsp"/>

<!-- End #footer -->
</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="../../includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>

