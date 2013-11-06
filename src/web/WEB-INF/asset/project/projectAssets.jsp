<%--
  - Author: GreatKevin, GreatKevin
  -
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload)
  -
  - Version: 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
  - - Adds the filter panel for assets
  -
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project assets view.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <ui:projectPageType tab="assets"/>
    <title>TopCoder Cockpit</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- External CSS -->
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

<!--
<div class="currentPage emptyHint ">
<p><font color="red" size="2">
<b>BETA ANNOUNCEMENT:</b>
Welcome to the new File Management area of Cockpit.  You will see this new section under each of your projects.  You are welcome to play around and begin using it, but please keep in
mind that some features are not implemented yet.  For a full description of what is currently implemented, check out <a href="http://www.topcoder.com/university/platform-updates/file-management-beta/">Platform Announcements</a>.
</font></p>
</div>
-->

<div class="areaHeader">
    <a class="button6 btnUpload" href="<s:url action="projectAssetUpload" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="left"><span class="right"><span class="icon">UPLOAD FILE(S)</span></span></span></a>

    <h2 class="title asssetsTitle">Files</h2>
    <input type="hidden" value="<s:property value='sessionData.currentSelectDirectProjectID'/>" id="assetProjectId"/>
</div>
<!-- End .areaHeader -->
<form id="filterPanelForm" action="">

<div id="SectionForDateVersion" class="assetsWrapper">

<!-- start filter panel -->
<div class='filterPanel'>
    <div class='filterHead'>
        <div class='rightSide'>
            <div class='inner'>
                <div class='filterText'>
                    <a href='javascript:;' class='collapse'><img src='/images/filter-panel/expand_icon.png'
                                                                 alt=''/></a>
                    <span class='title'>Filter</span>
                </div>
                <div class='searchContainer'>
                    <span class='title'>Search</span>

                    <div class='filterSearch'>
                        <input type='text' class='searchBoxForAssets'/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--end .filterHead-->
    <div class='filterContent'>
        <div class='rightSide'>
            <div class='inner'>
                <div class="columnCategory">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Category:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox01"/>
                                    <label for="multiSelect01CheckBox01" title="Select All">Select All</label>
                                </div>

                                <s:iterator value="viewData.availableCategories" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect01CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect01CheckBox<s:property value='#st.count'/>"
                                               title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Category -->
                <div class="columnUploader">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Uploader:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox01"/>
                                    <label for="multiSelect02CheckBox01" title="Select All">Select All</label>
                                </div>
                                <s:iterator value="viewData.availableUploaders" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect02CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect02CheckBox<s:property value='#st.count + 1'/>" title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Uploader -->
                <div class="columnDateRange">
                    <label>Date Range :</label>

                    <div class="dateRangeRow">
                        <span>From:</span>
                        <input type="text" name="formData.startDate" readonly="readonly" id="startDateDataVersion"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="dateRangeRow">
                        <span>To:</span>
                        <input type="text" name="formData.endDate" readonly="readonly" id="endDateDataVersion"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="buttonApply">
                        <a class="button6 btnApply" href="javascript:"><span class="left"><span
                                class="right">APPLY</span></span></a>
                        <a href="javascript:" class="clearLink">Clear Selection</a>
                    </div>
                </div>
                <!-- DateRange -->
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
<!--end .filterPanel-->

<div class="viewOptionArea">
    <div class="optionAreaSide">
        <div class="allAssetsCheckbox">
            <input type="checkbox" id="checkVersionDate"/>
            <label for="checkVersionDate">All Files</label>
        </div>
        <!-- End .allAssetsCheckbox -->
        <div class="batchOperation">
            <div class="versionDate">
                <ul>
                    <li><a href="javascript:;" class="delete">Batch Delete</a></li>
                    <li><a href="javascript:;" class="edit">Batch Edit</a></li>
                    <li><a href="javascript:;" class="download">Batch Download</a></li>
                    <li><a href="javascript:;" class="setPermission">Batch Set Permission</a></li>
                </ul>
            </div>
        </div>
        <!-- End .batchOperation -->
    </div>
    <div class="operationView">
        <div class="view">
            <label>View :</label>

            <div class="optionWrapper">
                <ul>
                    <li class="active"><a href="javascript:;" class="currentVersion"><span><span class="icon">Current Version</span></span></a>
                    </li>
                    <li class="last"><a href="javascript:;" class="myFile"><span><span
                            class="icon">My File</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .view -->
        <div class="groupBy hide">
            <label>Group by :</label>

            <div class="optionWrapper">
                <ul>
                    <li class="active"><a href="javascript:;" class="date"><span><span
                            class="icon">Date</span></span></a></li>
                    <li class="last"><a href="javascript:;" class="category"><span><span
                            class="icon">Category</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .groupBy -->
    </div>
</div>
<!-- End .viewOptionArea -->

<!-- Resule -->
<div class="resultSearch">
    <div class="allAssets"><a href="javascript:;">All Files</a></div>
    <div class="result"></div>
</div>
<!-- End .resultSearch -->

<div class="container2 resultTableContainer">

    <div class="tabContainer actnMenuWrap">
        <table id="assetsCurrentDateData" class="projectStats contests paginatedDataTable resultTable" cellpadding="0"
               cellspacing="0">
            <thead>
            <tr>
                <th>
                    <div><span></span>Date </div>
                </th>
                <th>
                    <div><span></span>File Details</div>
                </th>
                <th>
                    <div><span></span>Category</div>
                </th>
                <th>
                    <div><span></span>Uploader</div>
                </th>
                <th>
                    <div><span></span>Date Time</div>
                </th>
                <th>
                    <div><span></span>File Management</div>
                </th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="viewData.dateGroupedAssets">

                <s:iterator value="value">
                    <tr class="groupA">
                        <td><s:date name="key" format="EEEEEEE, MMMMMM dd, yyyy"/></td>
                        <td class="first fileDetailsCell">
                            <div class="fileDetailsSection">
                                <input type="checkbox"/>
                                <input type="hidden" name="assetId" value="${id}"/>
                                <input type="hidden" name="assetVersionId" value="${currentVersion.id}"/>

                                <div class="fileSection">

                                    <a href="<c:choose>
                                        <c:when test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>
                                        javascript:;
                                        </c:when>
                                        <c:otherwise>
                                            <s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>
                                        </c:otherwise>
                                    </c:choose>"
                                       class="icon-file-small <c:if test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>imageView</c:if>">
                                        <img src="/images/icon-small-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png"
                                             alt="${currentVersion.fileType}"/>
                                    </a>

                                    <div class="fileDetails">
                                        <ul>
                                            <li class="first">
                                                <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                                                   title="<s:property value='name'/>"><c:out
                                                        value="${tcdirect:limitStringLength(name, 36)}"/></a>
                                            </li>
                                            <li>
                                                <a href="<s:url action='viewAssetVersions'><s:param name='assetId' value='id'/></s:url>"
                                                   class="version modifyVersion">Version <s:property
                                                        value="currentVersion.version"/></a>
                                            </li>
                                            <li>
                                                <span class="size"><c:out
                                                        value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                            </li>
                                        </ul>
                                        <p>
                                            <span class="trimmedText"><c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/></span>
                                            <s:if test="currentVersion.description.length() > 80">
                                                <span class="ellipsis">...</span><a href="javascript:;">More</a>
                                                <span class="moreText"> <s:property value="currentVersion.description"/></span>
                                            </s:if>

                                        </p>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="categoryCell"><strong><s:property value="categories[0].name"/></strong></td>
                        <td class="uploaderCell"><link:user userId="${currentVersion.uploader.id}"/></td>
                        <td class="dateCell"><s:date name="currentVersion.uploadTime" format="MM/dd/yyyy HH:mm"/> EDT
                        </td>
                        <td class="managementCell">
                            <a class="action" href="javascript:;"><span class="ico">Action</span></a>
                            <div class="actionMenu hide">
                                <div class="actionMenuBottom">
                                    <ul class="actionMenuBody">
                                        <li><a href="javascript:;" class="upload">Upload New Version</a></li>
                                        <li><a href="javascript:;" class="edit">Edit Details</a></li>
                                        <li><a href="javascript:;" class="remove">Remove</a></li>
                                        <li><a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>">Download</a></li>
                                    </ul>
                                </div>
                            </div>
                            <%--
                            <a href="javascript:;" class="upload" style="display:none !important">Upload New Version</a>
                            <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                               class="download">Download</a>
                            <a href="javascript:;" class="edit" style="display:none !important">Edit File Details</a>
                            <a href="javascript:;" class="remove" style="display:none !important">Remove</a>
                            --%>
                        </td>
                    </tr>
                </s:iterator>

            </s:iterator>

            </tbody>
        </table>
        <!-- End #assetsCurrentDateData -->
    </div>

    <div class="container2Left">
        <div class="container2Right">
            <div class="container2Bottom"></div>
        </div>
    </div>

    <div class="corner tl"></div>
    <div class="corner tr"></div>

</div>
<!-- End .container2 -->


</div>
<!-- End #SectionForDateVersion -->

<div id="SectionForCategoryVersion" class="assetsWrapper">

<!-- start filter panel -->
<div class='filterPanel'>
    <div class='filterHead'>
        <div class='rightSide'>
            <div class='inner'>
                <div class='filterText'>
                    <a href='javascript:;' class='collapse'><img src='/images/filter-panel/expand_icon.png'
                                                                 alt=''/></a>
                    <span class='title'>Filter</span>
                </div>
                <div class='searchContainer'>
                    <span class='title'>Search</span>

                    <div class='filterSearch'>
                        <input type='text' class='searchBoxForAssets'/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--end .filterHead-->
    <div class='filterContent'>
        <div class='rightSide'>
            <div class='inner'>
                <div class="columnCategory">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Category:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox01"/>
                                    <label for="multiSelect03CheckBox01" title="Select All">Select All</label>
                                </div>

                                <s:iterator value="viewData.availableCategories" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect03CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect03CheckBox<s:property value='#st.count'/>"
                                               title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Category -->
                <div class="columnUploader">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Uploader:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox01"/>
                                    <label for="multiSelect04CheckBox01" title="Select All">Select All</label>
                                </div>
                                <s:iterator value="viewData.availableUploaders" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect04CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect04CheckBox<s:property value='#st.count + 1'/>" title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Uploader -->
                <div class="columnDateRange">
                    <label>Date Range :</label>

                    <div class="dateRangeRow">
                        <span>From:</span>
                        <input type="text" name="formData.startDate" readonly="readonly" id="startDateCaegoryVersion"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="dateRangeRow">
                        <span>To:</span>
                        <input type="text" name="formData.endDate" readonly="readonly" id="endDateCategoryVersion"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="buttonApply">
                        <a class="button6 btnApply" href="javascript:"><span class="left"><span
                                class="right">APPLY</span></span></a>
                        <a href="javascript:" class="clearLink">Clear Selection</a>
                    </div>
                </div>
                <!-- DateRange -->
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
<!--end .filterPanel-->

<div class="viewOptionArea">
    <div class="optionAreaSide">
        <div class="allAssetsCheckbox">
            <input type="checkbox" id="checkVersionCategory"/>
            <label for="checkVersionCategory">All Files</label>
        </div>
        <!-- End .allAssetsCheckbox -->
        <div class="batchOperation">
            <div class="versionDate">
                <ul>
                    <li><a href="javascript:;" class="delete">Batch Delete</a></li>
                    <li><a href="javascript:;" class="edit">Batch Edit</a></li>
                    <li><a href="javascript:;" class="download">Batch Download</a></li>
                    <li><a href="javascript:;" class="setPermission">Batch Set Permission</a></li>
                </ul>
            </div>
        </div>
        <!-- End .batchOperation -->
    </div>
    <div class="operationView">
        <div class="view">
            <label>View :</label>

            <div class="optionWrapper">
                <ul>
                    <li class="active"><a href="javascript:;" class="currentVersion"><span><span class="icon">Current Version</span></span></a>
                    </li>
                    <li class="last"><a href="javascript:;" class="myFile"><span><span
                            class="icon">My File</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .view -->
        <div class="groupBy">
            <label>Group by :</label>

            <div class="optionWrapper">
                <ul>
                    <li><a href="javascript:;" class="date"><span><span class="icon">Date</span></span></a></li>
                    <li class="last active"><a href="javascript:;" class="category"><span><span
                            class="icon">Category</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .groupBy -->
    </div>
</div>
<!-- End .viewOptionArea -->

<!-- Resule -->
<div class="resultSearch">
    <div class="allAssets"><a href="javascript:;">All Files</a></div>
    <div class="result"></div>
</div>
<!-- End .resultSearch -->

<div class="container2 resultTableContainer">

    <div class="tabContainer actnMenuWrap">
        <table id="assetsCurrentCategoryData" class="projectStats contests paginatedDataTable resultTable" cellpadding="0"
               cellspacing="0">
            <thead>
            <tr>
                <th>
                    <div><span></span>Group By</div>
                </th>
                <th>
                    <div><span></span>File Details</div>
                </th>
                <th>
                    <div><span></span>Category</div>
                </th>
                <th>
                    <div><span></span>Uploader</div>
                </th>
                <th>
                    <div><span></span>Date Time</div>
                </th>
                <th>
                    <div><span></span>File Management</div>
                </th>
            </tr>
            </thead>
            <tbody>

            <s:iterator value="viewData.categoryGroupedAssets">

                <s:iterator value="value">
                    <tr>
                        <td><s:property value="key"/></td>
                        <td class="first fileDetailsCell">
                            <div class="fileDetailsSection">
                                <input type="checkbox"/>
                                <input type="hidden" name="assetId" value='${id}'/>
                                <input type="hidden" name="assetVersionId" value='${currentVersion.id}'/>
                                <div class="fileSection">
                                    <a href="<c:choose>
                                        <c:when test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>
                                        javascript:;
                                        </c:when>
                                        <c:otherwise>
                                            <s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>
                                        </c:otherwise>
                                    </c:choose>"
                                       class="icon-file-small <c:if test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>imageView</c:if>">
                                        <img src="/images/icon-small-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png"
                                             alt="${currentVersion.fileType}"/>
                                    </a>

                                    <div class="fileDetails">
                                        <ul>
                                            <li class="first">
                                                <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" title="<s:property value="name"/>"><c:out value="${tcdirect:limitStringLength(name, 36)}"/></a>
                                            </li>
                                            <li>
                                                <a href="<s:url action='viewAssetVersions'><s:param name='assetId' value='id'/></s:url>" class="version modifyVersion">Version <s:property value="currentVersion.version"/></a>
                                            </li>
                                            <li>
                                                <span class="size"><c:out value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                            </li>
                                        </ul>
                                        <p>
                                            <span class="trimmedText"><c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/></span>
                                            <s:if test="currentVersion.description.length() > 80">
                                                <span class="ellipsis">...</span><a href="javascript:;">More</a>
                                                <span class="moreText"> <s:property value="currentVersion.description"/></span>
                                            </s:if>

                                        </p>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="categoryCell"><strong><s:property value="categories[0].name"/></strong></td>
                        <td class="uploaderCell"><link:user userId="${currentVersion.uploader.id}"/></td>
                        <td class="dateCell"><s:date name="currentVersion.uploadTime" format="MM/dd/yyyy HH:mm"/> EDT</td>
                        <td class="managementCell">
                            <a class="action" href="javascript:;"><span class="ico">Action</span></a>
                            <div class="actionMenu hide">
                                <div class="actionMenuBottom">
                                    <ul class="actionMenuBody">
                                        <li><a href="javascript:;" class="upload">Upload New Version</a></li>
                                        <li><a href="javascript:;" class="edit">Edit Details</a></li>
                                        <li><a href="javascript:;" class="remove">Remove</a></li>
                                        <li><a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>">Download</a></li>
                                    </ul>
                                </div>
                            </div>
                        </td>

                    </tr>
                </s:iterator>

            </s:iterator>
            </tbody>
        </table>
        <!-- End #assetsCurrentCategoryData -->
    </div>

    <div class="container2Left">
        <div class="container2Right">
            <div class="container2Bottom"></div>
        </div>
    </div>

    <div class="corner tl"></div>
    <div class="corner tr"></div>

</div>
<!-- End .container2 -->


</div>
<!-- End #SectionForCategoryVersion -->

<div id="SectionForDateProfile" class="assetsWrapper">

<!-- start filter panel -->
<div class='filterPanel'>
    <div class='filterHead'>
        <div class='rightSide'>
            <div class='inner'>
                <div class='filterText'>
                    <a href='javascript:;' class='collapse'><img src='../images/filter-panel/expand_icon.png'
                                                                 alt=''/></a>
                    <span class='title'>Filter</span>
                </div>
                <div class='searchContainer'>
                    <span class='title'>Search</span>

                    <div class='filterSearch'>
                        <input type='text' class='searchBoxForAssets'/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--end .filterHead-->
    <div class='filterContent'>
        <div class='rightSide'>
            <div class='inner'>
                <div class="columnCategory">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Category:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox01"/>
                                    <label for="multiSelect05CheckBox01" title="Select All">Select All</label>
                                </div>
                                <s:iterator value="viewData.myAvailableCategories" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect05CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect05CheckBox<s:property value='#st.count'/>"
                                               title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Category -->
                <div class="columnDateRange">
                    <label>Date Range :</label>

                    <div class="dateRangeRow">
                        <span>From:</span>
                        <input type="text" name="formData.startDate" readonly="readonly" id="startDateDateProfile"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="dateRangeRow">
                        <span>To:</span>
                        <input type="text" name="formData.endDate" readonly="readonly" id="endDateDateProfile"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="buttonApply">
                        <a class="button6 btnApply" href="javascript:"><span class="left"><span
                                class="right">APPLY</span></span></a>
                        <a href="javascript:" class="clearLink">Clear Selection</a>
                    </div>
                </div>
                <!-- DateRange -->
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
<!--end .filterPanel-->

<div class="viewOptionArea">
    <div class="optionAreaSide">
        <div class="allAssetsCheckbox">
            <input type="checkbox" id="checkProfileDate"/>
            <label for="checkProfileDate">All Files</label>
        </div>
        <!-- End .allAssetsCheckbox -->
        <div class="batchOperation">
            <div class="versionDate">
                <ul>
                    <li><a href="javascript:;" class="delete">Batch Delete</a></li>
                    <li><a href="javascript:;" class="edit">Batch Edit</a></li>
                    <li><a href="javascript:;" class="download">Batch Download</a></li>
                    <li><a href="javascript:;" class="setPermission">Batch Set Permission</a></li>
                </ul>
            </div>
        </div>
        <!-- End .batchOperation -->
    </div>
    <div class="operationView">
        <div class="view">
            <label>View :</label>

            <div class="optionWrapper">
                <ul>
                    <li><a href="javascript:;" class="currentVersion"><span><span
                            class="icon">Current Version</span></span></a></li>
                    <li class="last active"><a href="javascript:;" class="myFile"><span><span
                            class="icon">My File</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .view -->
        <div class="groupBy">
            <label>Group by :</label>

            <div class="optionWrapper">
                <ul>
                    <li class="active"><a href="javascript:;" class="date"><span><span
                            class="icon">Date</span></span></a></li>
                    <li class="last"><a href="javascript:;" class="category"><span><span
                            class="icon">Category</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .groupBy -->
    </div>
</div>
<!-- End .viewOptionArea -->

<!-- Resule -->
<div class="resultSearch">
    <div class="allAssets"><a href="javascript:;">All Files</a></div>
    <div class="result"></div>
</div>
<!-- End .resultSearch -->

<div class="container2 resultTableContainer">

<div class="tabContainer actnMenuWrap">
<table id="assetsDateData" class="projectStats contests paginatedDataTable resultTable" cellpadding="0" cellspacing="0">
<thead>
<tr>
    <th>
        <div><span></span>Date</div>
    </th>
    <th>
        <div><span></span>File Details</div>
    </th>
    <th>
        <div><span></span>Category</div>
    </th>
    <th>
        <div><span></span>Date Time</div>
    </th>
    <th>
        <div><span></span>File Management</div>
    </th>
</tr>
</thead>
<tbody>
<s:iterator value="viewData.myDateGroupedAssets">

    <s:iterator value="value">
        <tr class="groupA">
            <td><s:date name="key" format="EEEEEEE, MMMMMM dd, yyyy"/></td>
            <td class="first fileDetailsCell">
                <div class="fileDetailsSection">
                    <input type="checkbox"/>
                    <input type="hidden" name="assetId" value="${id}"/>
                    <input type="hidden" name="assetVersionId" value="${currentVersion.id}"/>

                    <div class="fileSection">
                        <a href="<c:choose>
                                        <c:when test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>
                                        javascript:;
                                        </c:when>
                                        <c:otherwise>
                                            <s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>
                                        </c:otherwise>
                                    </c:choose>"
                           class="icon-file-small <c:if test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>imageView</c:if>">
                            <img src="/images/icon-small-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png"
                                 alt="${currentVersion.fileType}"/>
                        </a>

                        <div class="fileDetails">
                            <ul>
                                <li class="first">
                                    <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                                       title="<s:property value='name'/>"><c:out
                                            value="${tcdirect:limitStringLength(name, 36)}"/></a>
                                </li>
                                <li>
                                    <a href="<s:url action='viewAssetVersions'><s:param name='assetId' value='id'/></s:url>"
                                       class="version modifyVersion">Version <s:property
                                            value="currentVersion.version"/></a>
                                </li>
                                <li>
                                                <span class="size"><c:out
                                                        value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                </li>
                            </ul>
                            <p>
                                <span class="trimmedText"><c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/></span>
                                <s:if test="currentVersion.description.length() > 80">
                                    <span class="ellipsis">...</span><a href="javascript:;">More</a>
                                    <span class="moreText"> <s:property value="currentVersion.description"/></span>
                                </s:if>

                            </p>
                        </div>
                    </div>
                </div>
            </td>
            <td class="categoryCell"><strong><s:property value="categories[0].name"/></strong></td>
            <td class="dateCell"><s:date name="currentVersion.uploadTime" format="MM/dd/yyyy HH:mm"/> EDT
            </td>
            <td class="managementCell">
                <a class="action" href="javascript:;"><span class="ico">Action</span></a>
                <div class="actionMenu hide">
                    <div class="actionMenuBottom">
                        <ul class="actionMenuBody">
                            <li><a href="javascript:;" class="upload">Upload New Version</a></li>
                            <li><a href="javascript:;" class="edit">Edit Details</a></li>
                            <li><a href="javascript:;" class="remove">Remove</a></li>
                            <li><a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>">Download</a></li>
                        </ul>
                    </div>
                </div>
            </td>
        </tr>
    </s:iterator>

</s:iterator>
</tbody>
</table>
<!-- End #assetsDateData -->
</div>

<div class="container2Left">
    <div class="container2Right">
        <div class="container2Bottom"></div>
    </div>
</div>

<div class="corner tl"></div>
<div class="corner tr"></div>

</div>
<!-- End .container2 -->


</div>
<!-- End #SectionForDateProfile -->

<div id="SectionForCategoryProfile" class="assetsWrapper">

<!-- start filter panel -->
<div class='filterPanel'>
    <div class='filterHead'>
        <div class='rightSide'>
            <div class='inner'>
                <div class='filterText'>
                    <a href='javascript:;' class='collapse'><img src='/images/filter-panel/expand_icon.png'
                                                                 alt=''/></a>
                    <span class='title'>Filter</span>
                </div>
                <div class='searchContainer'>
                    <span class='title'>Search</span>

                    <div class='filterSearch'>
                        <input type='text' class='searchBoxForAssets'/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--end .filterHead-->
    <div class='filterContent'>
        <div class='rightSide'>
            <div class='inner'>
                <div class="columnCategory">
                    <div class="multiSelectArea">
                        <div class="multiSelectAreaInner">
                            <label class="multiSelectAreaTitle">Category:</label>

                            <div class="multiSelectBox">
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox01"/>
                                    <label for="multiSelect06CheckBox01" title="Select All">Select All</label>
                                </div>
                                <s:iterator value="viewData.myAvailableCategories" status="st">
                                    <div class="multiOptionRow">
                                        <input type="checkbox" id="multiSelect06CheckBox<s:property value='#st.count + 1'/>"/>
                                        <label for="multiSelect06CheckBox<s:property value='#st.count'/>"
                                               title="<s:property/>"><s:property/></label>
                                    </div>
                                </s:iterator>
                            </div>
                        </div>
                    </div>
                    <!-- end .multiSelectArea -->
                </div>
                <!-- Category -->
                <div class="columnDateRange">
                    <label>Date Range :</label>

                    <div class="dateRangeRow">
                        <span>From:</span>
                        <input type="text" name="formData.startDate" readonly="readonly" id="startDateCaegoryProfile"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="dateRangeRow">
                        <span>To:</span>
                        <input type="text" name="formData.endDate" readonly="readonly" id="endDateCategoryProfile"
                               class="fLeft text date-pick dp-applied"/>
                    </div>
                    <div class="buttonApply">
                        <a class="button6 btnApply" href="javascript:"><span class="left"><span
                                class="right">APPLY</span></span></a>
                        <a href="javascript:" class="clearLink">Clear Selection</a>
                    </div>
                </div>
                <!-- DateRange -->
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
<!--end .filterPanel-->

<div class="viewOptionArea">
    <div class="optionAreaSide">
        <div class="allAssetsCheckbox">
            <input type="checkbox" id="checkProfileCategory"/>
            <label for="checkProfileCategory">All Files</label>
        </div>
        <!-- End .allAssetsCheckbox -->
        <div class="batchOperation">
            <div class="versionDate">
                <ul>
                    <li><a href="javascript:;" class="delete">Batch Delete</a></li>
                    <li><a href="javascript:;" class="edit">Batch Edit</a></li>
                    <li><a href="javascript:;" class="download">Batch Download</a></li>
                    <li><a href="javascript:;" class="setPermission">Batch Set Permission</a></li>
                </ul>
            </div>
        </div>
        <!-- End .batchOperation -->
    </div>
    <div class="operationView">
        <div class="view">
            <label>View :</label>

            <div class="optionWrapper">
                <ul>
                    <li><a href="javascript:;" class="currentVersion"><span><span
                            class="icon">Current Version</span></span></a></li>
                    <li class="last active"><a href="javascript:;" class="myFile"><span><span
                            class="icon">My File</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .view -->
        <div class="groupBy">
            <label>Group by :</label>

            <div class="optionWrapper">
                <ul>
                    <li><a href="javascript:;" class="date"><span><span class="icon">Date</span></span></a></li>
                    <li class="last active"><a href="javascript:;" class="category"><span><span
                            class="icon">Category</span></span></a></li>
                </ul>
            </div>
        </div>
        <!-- End .groupBy -->
    </div>
</div>
<!-- End .viewOptionArea -->

<!-- Resule -->
<div class="resultSearch">
    <div class="allAssets"><a href="javascript:;">All Files</a></div>
    <div class="result"></div>
</div>
<!-- End .resultSearch -->

<div class="container2 resultTableContainer">

<div class="tabContainer actnMenuWrap">
<table id="assetsCategoryData" class="projectStats contests paginatedDataTable resultTable" cellpadding="0"
       cellspacing="0">
<thead>
<tr>
    <th>
        <div><span></span>Group By</div>
    </th>
    <th>
        <div><span></span>File Details</div>
    </th>
    <th>
        <div><span></span>Category</div>
    </th>
    <th>
        <div><span></span>Date Time</div>
    </th>
    <th>
        <div><span></span>File Management</div>
    </th>
</tr>
</thead>
<tbody>
<s:iterator value="viewData.myCategoryGroupedAssets">

    <s:iterator value="value">
        <tr>
            <td><s:property value="key"/></td>
            <td class="first fileDetailsCell">
                <div class="fileDetailsSection">
                    <input type="checkbox"/>
                    <input type="hidden" name="assetId" value='${id}'/>
                    <input type="hidden" name="assetVersionId" value='${currentVersion.id}'/>
                    <div class="fileSection">
                        <a href="<c:choose>
                                        <c:when test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>
                                        javascript:;
                                        </c:when>
                                        <c:otherwise>
                                            <s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>
                                        </c:otherwise>
                                    </c:choose>"
                           class="icon-file-small <c:if test='${tcdirect:isFilePreviewable(currentVersion.fileType)}'>imageView</c:if>">
                            <img src="/images/icon-small-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png"
                                 alt="${currentVersion.fileType}"/>
                        </a>

                        <div class="fileDetails">
                            <ul>
                                <li class="first">
                                    <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" title="<s:property value="name"/>"><c:out value="${tcdirect:limitStringLength(name, 36)}"/></a>
                                </li>
                                <li>
                                    <a href="<s:url action='viewAssetVersions'><s:param name='assetId' value='id'/></s:url>" class="version modifyVersion">Version <s:property value="currentVersion.version"/></a>
                                </li>
                                <li>
                                    <span class="size"><c:out value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                </li>
                            </ul>
                            <p>
                                <span class="trimmedText"><c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/></span>
                                <s:if test="currentVersion.description.length() > 80">
                                    <span class="ellipsis">...</span><a href="javascript:;">More</a>
                                    <span class="moreText"> <s:property value="currentVersion.description"/></span>
                                </s:if>

                            </p>
                        </div>
                    </div>
                </div>
            </td>
            <td class="categoryCell"><strong><s:property value="categories[0].name"/></strong></td>
            <td class="dateCell"><s:date name="currentVersion.uploadTime" format="MM/dd/yyyy HH:mm"/> EDT</td>
            <td class="managementCell">
                <a class="action" href="javascript:;"><span class="ico">Action</span></a>
                <div class="actionMenu hide">
                    <div class="actionMenuBottom">
                        <ul class="actionMenuBody">
                            <li><a href="javascript:;" class="upload">Upload New Version</a></li>
                            <li><a href="javascript:;" class="edit">Edit Details</a></li>
                            <li><a href="javascript:;" class="remove">Remove</a></li>
                            <li><a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>">Download</a></li>
                        </ul>
                    </div>
                </div>
            </td>

        </tr>
    </s:iterator>

</s:iterator>
</tbody>
</table>
<!-- End #assetsCategoryData -->
</div>

<div class="container2Left">
    <div class="container2Right">
        <div class="container2Bottom"></div>
    </div>
</div>

<div class="corner tl"></div>
<div class="corner tr"></div>

</div>
<!-- End .container2 -->


</div>
<!-- End #SectionForCategoryProfile -->

</form>

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

