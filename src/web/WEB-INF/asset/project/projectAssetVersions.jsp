<%--
  - Author: GreatKevin, TCSASSEMBLER
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Asset View And File Version)
  -
  - Version 1.1 (TC Direct Rebranding Assembly Project and Contest related pages)
  - Use new font "Source Sans Pro" for the page
  -
  - Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project asset versions view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <ui:projectPageType tab="assets"/>
    <title>Topcoder Direct</title>

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

<div class="uploadSection">
<div class="sectionHeader">
    <div class="sectionHeaderL">
        <div class="sectionHeaderR">
            <div class="sectionHeaderM">
                <a class="button6 btnUpload btnUploadVersion" href="javascript:"><span class="left"><span class="right"><span class="icon">UPLOAD NEW VERSION</span></span></span></a>
                <a class="button7 btnBack" href="<s:url action="projectAssets" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><span class="left"><span class="right">BACK</span></span></a>
                <h2>Versions of : <span><s:property value='viewData.asset.name'/></span></h2>
            </div>
        </div>
    </div>
</div>
<!-- End .sectionHeader -->
<div class="versionView">

    <div class="versionViewTable actnMenuWrap">
        <!-- /.actionMenu -->
        <div class="hArrow"></div>
        <table border="0" cellpadding="0" cellspacing="0" id="fileVersionData">
            <colgroup>
                <col />
                <col width="15%" />
                <col width="15%" />
                <col width="130" />
            </colgroup>
            <thead>
            <tr>
                <th><div><span></span>File Details</div></th>
                <th><div><span></span>Uploader</div></th>
                <th><div><span></span>Date Time</div></th>
                <th><div><span></span>File Management</div></th>
            </tr>
            </thead>
            <tbody>

            <s:iterator value="viewData.assetVersions">
                <tr class="<s:if test='viewData.asset.currentVersion.id == id'>selected</s:if>">
                    <td class="fileDetailsCell">
                        <div class="fileDetails">
                            <ul>
                                <li class="versionList">
                                    <span class="version">Version <s:property value="version"/></span>
                                </li>
                                <li>
                                    <span class="size"><c:out
                                            value="${tcdirect:fileSizeDisplay(fileSizeBytes)}"/></span>
                                </li>
                            </ul>
                        </div>
                        <input type="hidden" value="<s:property value='fileName'/>" name="fileName"/>
                        <input type="hidden" value="<s:property value='viewData.asset.categories[0].name'/>" name="assetCategory"/>
                        <input type="hidden" value="<s:property value='viewData.asset.currentVersion.id'/>" name="latestId"/>
                        <input type="hidden" value="<s:property value='viewData.asset.id'/>" name="assetId"/>
                        <input type="hidden" value="<s:property value='id'/>" name="assetVersionId"/>
                        <input type="hidden" value="<s:property value='viewData.asset.currentVersion.id == id'/>" name="isLatest"/>
                        <img class="fileTypeIcon hide" src="/images/icon-<c:out value='${tcdirect:fileTypeIcon(fileType)}'/>.png"
                             alt="${fileType}"/>
                        <p class="fileNameDes"><s:property value='description'/></p>
                    </td>
                    <td class="uploaderCell">
                        <link:user userId="${uploader.id}"/>
                    </td>
                    <td class="timeCell">
                        <div class="time"><s:date name="uploadTime" format="MM/dd/yyyy HH:mm"/> EDT</div>
                    </td>
                    <td class="managementCell">
                        <a class="action" href="javascript:;"><span class="ico">Action</span></a>
                        <div class="actionMenu hide">
                            <div class="actionMenuBottom">
                                <ul class="actionMenuBody">

                                    <li><a href="javascript:;" class="edit">Edit File Details</a></li>
                                    <li><a href="javascript:;" class="remove">Remove</a></li>
                                    <li><a href="<s:url action='downloadAsset'><s:param name='assetId' value='viewData.asset.id'/><s:param name='assetVersionId' value='id'/></s:url>">Download</a></li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
            </s:iterator>

            </tbody>
        </table>
    </div>
    <!-- End .versionViewTable -->

    <div class="versionViewDetails">
        <div class="icon-lastest">
            <div class="icon">LASTEST</div>
        </div>
        <div class="icon-file"></div>
        <a href="javascript:;" class="fileName"></a>
        <div class="versionAndSize">
            <span class="version"></span>
            <span class="line">|</span>
            <span class="versionSize"></span>
        </div>
        <p class="fileNameDes"></p>
        <div class="cateogry"></div>
        <div class="uploader"></div>
        <div class="time"></div>
        <a href="javascript:;" class="editVersion"><span><span><span class="icon">Edit Details</span></span></span></a>
    </div>
    <!-- End .versionViewDetails -->

</div>

<div class="uploadButtonBox">
    <a class="button6 btnUpload btnUploadVersion" href="javascript:"><span class="left"><span class="right"><span class="icon">UPLOAD NEW VERSION</span></span></span></a>
    <a class="button7 btnBack" href="<s:url action="projectAssets" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>"><span class="left"><span class="right">BACK</span></span></a>
</div>

</div>
<!-- End .uploadSection -->

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
