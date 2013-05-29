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

    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
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
    <script type="text/javascript" src="/scripts/filedrop-min.js"></script>
    <script type="text/javascript" src="/scripts/assets.js"></script>

</head>

<body id="page">

<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content" class="asset">
<jsp:include page="../../includes/header.jsp"/>


<div id="mainContent">
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
    <a class="button6 btnUpload" href="<s:url action="projectAssetUpload" namespace="/"> <s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID" /></s:url>"><span class="left"><span class="right"><span class="icon">UPLOAD FILE(S)</span></span></span></a>

    <h2 class="title asssetsTitle">Files</h2>
</div>
<!-- End .areaHeader -->
<form id="filterPanelForm" action="">

<div id="SectionForDateVersion" class="assetsWrapper">

<!-- start filter panel -->
<div class='filterPanel hide'>
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
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox02"/>
                                    <label for="multiSelect01CheckBox02"
                                           title="Conceptualization">Conceptualization</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox03"/>
                                    <label for="multiSelect01CheckBox03" title="Specification">Specification</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox04"/>
                                    <label for="multiSelect01CheckBox04" title="Architecture Deliverable">Architecture
                                        Deliverable</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox05"/>
                                    <label for="multiSelect01CheckBox05" title="UI Prototype">UI Prototype</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox06"/>
                                    <label for="multiSelect01CheckBox06" title="Client Questionnaire">Client
                                        Questionnaire</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox07"/>
                                    <label for="multiSelect01CheckBox07" title="Copilot Project Game Plan">Copilot
                                        Project Game Plan</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox08"/>
                                    <label for="multiSelect01CheckBox08" title="Screenshots">Screenshots</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect01CheckBox09"/>
                                    <label for="multiSelect01CheckBox09" title="Storyboard">Storyboard</label>
                                </div>
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
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox02"/>
                                    <label for="multiSelect02CheckBox02" title="lunarkid">lunarkid</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox03"/>
                                    <label for="multiSelect02CheckBox03" title="caru">caru</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox04"/>
                                    <label for="multiSelect02CheckBox04" title="[ambi]">[ambi]</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox05"/>
                                    <label for="multiSelect02CheckBox05" title="TonyJ">TonyJ</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect02CheckBox06"/>
                                    <label for="multiSelect02CheckBox06" title="izhari">izhari</label>
                                </div>
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
        <div class="allAssetsCheckbox" style="display:none !important">
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
        <div class="view"  style="display:none !important">
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

    <div class="tabContainer">
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
                                    <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                                       class="icon-file-small">
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
                                                <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                                                   class="version modifyVersion">Version <s:property
                                                        value="currentVersion.version"/></a>
                                            </li>
                                            <li>
                                                <span class="size"><c:out
                                                        value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                            </li>
                                        </ul>
                                        <p>
                                            <c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/>
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
                            <a href="javascript:;" class="upload" style="display:none !important">Upload New Version</a>
                            <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>"
                               class="download">Download</a>
                            <a href="javascript:;" class="edit" style="display:none !important">Edit File Details</a>
                            <a href="javascript:;" class="remove" style="display:none !important">Remove</a>
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
<div class='filterPanel hide'>
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
                                    <input type="checkbox" id="multiSelect03CheckBox01"/>
                                    <label for="multiSelect03CheckBox01" title="Select All">Select All</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox02"/>
                                    <label for="multiSelect03CheckBox02"
                                           title="Conceptualization">Conceptualization</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox03"/>
                                    <label for="multiSelect03CheckBox03" title="Specification">Specification</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox04"/>
                                    <label for="multiSelect03CheckBox04" title="Architecture Deliverable">Architecture
                                        Deliverable</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox05"/>
                                    <label for="multiSelect03CheckBox05" title="UI Prototype">UI Prototype</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox06"/>
                                    <label for="multiSelect03CheckBox06" title="Client Questionnaire">Client
                                        Questionnaire</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox07"/>
                                    <label for="multiSelect03CheckBox07" title="Copilot Project Game Plan">Copilot
                                        Project Game Plan</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox08"/>
                                    <label for="multiSelect03CheckBox08" title="Screenshots">Screenshots</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect03CheckBox09"/>
                                    <label for="multiSelect03CheckBox09" title="Storyboard">Storyboard</label>
                                </div>
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
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox02"/>
                                    <label for="multiSelect04CheckBox02" title="lunarkid">lunarkid</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox03"/>
                                    <label for="multiSelect04CheckBox03" title="caru">caru</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox04"/>
                                    <label for="multiSelect04CheckBox04" title="[ambi]">[ambi]</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox05"/>
                                    <label for="multiSelect04CheckBox05" title="TonyJ">TonyJ</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect04CheckBox06"/>
                                    <label for="multiSelect04CheckBox06" title="izhari">izhari</label>
                                </div>
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
        <div class="allAssetsCheckbox" style="display:none !important">
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
        <div class="view" style="display:none !important">
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

    <div class="tabContainer">
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
                                    <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" class="icon-file-small">
                                        <img src="/images/icon-small-<c:out value='${tcdirect:fileTypeIcon(currentVersion.fileType)}'/>.png" alt="${currentVersion.fileType}"/></a>

                                    <div class="fileDetails">
                                        <ul>
                                            <li class="first">
                                                <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" title="<s:property value="name"/>"><c:out value="${tcdirect:limitStringLength(name, 36)}"/></a>
                                            </li>
                                            <li>
                                                <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" class="version modifyVersion">Version <s:property value="currentVersion.version"/></a>
                                            </li>
                                            <li>
                                                <span class="size"><c:out value="${tcdirect:fileSizeDisplay(currentVersion.fileSizeBytes)}"/></span>
                                            </li>
                                        </ul>
                                        <p>
                                            <c:out value="${tcdirect:limitStringLength(currentVersion.description, 80)}"/>
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
                            <a href="javascript:;" class="upload"  style="display:none !important">Upload New Version</a>
                            <a href="<s:url action='downloadAsset'><s:param name='assetId' value='id'/><s:param name='assetVersionId' value='currentVersion.id'/></s:url>" class="download">Download</a>
                            <a href="javascript:;" class="edit" style="display:none !important">Edit File Details</a>
                            <a href="javascript:;" class="remove" style="display:none !important">Remove</a>
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
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox02"/>
                                    <label for="multiSelect05CheckBox02"
                                           title="Conceptualization">Conceptualization</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox03"/>
                                    <label for="multiSelect05CheckBox03" title="Specification">Specification</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox04"/>
                                    <label for="multiSelect05CheckBox04" title="Architecture Deliverable">Architecture
                                        Deliverable</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox05"/>
                                    <label for="multiSelect05CheckBox05" title="UI Prototype">UI Prototype</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox06"/>
                                    <label for="multiSelect05CheckBox06" title="Client Questionnaire">Client
                                        Questionnaire</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox07"/>
                                    <label for="multiSelect05CheckBox07" title="Copilot Project Game Plan">Copilot
                                        Project Game Plan</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox08"/>
                                    <label for="multiSelect05CheckBox08" title="Screenshots">Screenshots</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect05CheckBox09"/>
                                    <label for="multiSelect05CheckBox09" title="Storyboard">Storyboard</label>
                                </div>
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

<div class="tabContainer">
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
<tr class="groupA">
    <td>Monday, October 15, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-zip.png"
                                                                            alt="ZIP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.zip</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupA">
    <td>Monday, October 15, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-rar.png"
                                                                            alt="RAR"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.rar</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupA">
    <td>Monday, October 15, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-7zip.png"
                                                                            alt="7ZIP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.7z</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupA">
    <td>Monday, October 15, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-pdf.png"
                                                                            alt="PDF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">BRD Doccument Template.pdf</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Conceptualization</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupB">
    <td>Friday, October 19, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-doc.png"
                                                                            alt="DOC"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Questionaire.docx</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Client Questionnaire</strong></td>
    <td class="dateCell">10/19/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Friday, October 26, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-xls.png"
                                                                            alt="XLS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Timeline.xls</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Timeline for consideration.</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Copilot Project Game Plan</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Friday, October 26, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-ppt.png"
                                                                            alt="PPT"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Questionaire.ppt</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Client Questionnaire</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Friday, October 26, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-txt.png"
                                                                            alt="TXT"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Timeline.txt</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Timeline for consideration.</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Copilot Project Game Plan</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Friday, October 26, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-png.png"
                                                                              alt="PNG"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Ideas (Sketch).png</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version imageVersion">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-gif.png"
                                                                              alt="GIF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.gif</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-jpg.png"
                                                                              alt="JPG"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.jpg</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-bmp.png"
                                                                              alt="BMP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.bmp</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-psd.png"
                                                                            alt="PSD"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.psd</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-ai.png" alt="AI"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.ai</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-eps.png"
                                                                            alt="EPS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.eps</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-fla.png"
                                                                            alt="FLA"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.fla</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-swf.png"
                                                                            alt="SWF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.swf</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 3.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-html.png"
                                                                            alt="HTML"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.html</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-css.png"
                                                                            alt="CSS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.css</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Protoytpe</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-js.png" alt="JS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.js</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-xml.png"
                                                                            alt="XML"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.xml</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Wednesday, November 07, 2012</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-other.png"
                                                                            alt="EXE"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.exe</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">4.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Specification</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
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
                                    <input type="checkbox" id="multiSelect06CheckBox01"/>
                                    <label for="multiSelect06CheckBox01" title="Select All">Select All</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox02"/>
                                    <label for="multiSelect06CheckBox02"
                                           title="Conceptualization">Conceptualization</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox03"/>
                                    <label for="multiSelect06CheckBox03" title="Specification">Specification</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox04"/>
                                    <label for="multiSelect06CheckBox04" title="Architecture Deliverable">Architecture
                                        Deliverable</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox05"/>
                                    <label for="multiSelect06CheckBox05" title="UI Prototype">UI Prototype</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox06"/>
                                    <label for="multiSelect06CheckBox06" title="Client Questionnaire">Client
                                        Questionnaire</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox07"/>
                                    <label for="multiSelect06CheckBox07" title="Copilot Project Game Plan">Copilot
                                        Project Game Plan</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox08"/>
                                    <label for="multiSelect06CheckBox08" title="Screenshots">Screenshots</label>
                                </div>
                                <div class="multiOptionRow">
                                    <input type="checkbox" id="multiSelect06CheckBox09"/>
                                    <label for="multiSelect06CheckBox09" title="Storyboard">Storyboard</label>
                                </div>
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

<div class="tabContainer">
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
<tr class="groupA">
    <td>Client Questionnaire</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-doc.png"
                                                                            alt="DOC"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Questionaire.docx</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Client Questionnaire</strong></td>
    <td class="dateCell">10/19/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupA">
    <td>Client Questionnaire</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-ppt.png"
                                                                            alt="PPT"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Questionaire.ppt</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Client Questionnaire</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupB">
    <td>Conceptualization</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-pdf.png"
                                                                            alt="PDF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">BRD Doccument Template.pdf</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Conceptualization</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Copilot Project Game Plan</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-xls.png"
                                                                            alt="XLS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Timeline.xls</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Timeline for consideration.</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Copilot Project Game Plan</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupC">
    <td>Copilot Project Game Plan</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-txt.png"
                                                                            alt="TXT"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Timeline.txt</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Timeline for consideration.</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Copilot Project Game Plan</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Screenshots</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-png.png"
                                                                              alt="PNG"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Client Ideas (Sketch).png</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version imageVersion">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Questionaire</p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">10/26/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Screenshots</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-gif.png"
                                                                              alt="GIF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.gif</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Screenshots</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-jpg.png"
                                                                              alt="JPG"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.jpg</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupD">
    <td>Screenshots</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="javascript:;" class="icon-file-small imageView"><img src="../images/icon-small-bmp.png"
                                                                              alt="BMP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.bmp</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="version modifyVersion imageVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Screenshots</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupE">
    <td>Specification</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-other.png"
                                                                            alt="EXE"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.exe</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">4.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Specification</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-zip.png"
                                                                            alt="ZIP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.zip</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-rar.png"
                                                                            alt="RAR"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.rar</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-7zip.png"
                                                                            alt="7ZIP"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.7z</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 2.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">10/15/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-psd.png"
                                                                            alt="PSD"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.psd</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-ai.png" alt="AI"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.ai</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-eps.png"
                                                                            alt="EPS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.eps</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-fla.png"
                                                                            alt="FLA"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.fla</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 1.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-swf.png"
                                                                            alt="SWF"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.swf</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version modifyVersion">Version 3.1</a>
                        </li>
                        <li>
                            <span class="size">1.5 MB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>Storyboard</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-html.png"
                                                                            alt="HTML"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.html</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-css.png"
                                                                            alt="CSS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.css</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Protoytpe</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-js.png" alt="JS"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.js</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
<tr class="groupF">
    <td>Storyboard</td>
    <td class="first fileDetailsCell">
        <div class="fileDetailsSection">
            <input type="checkbox"/>

            <div class="fileSection">
                <a href="fileVersionView.html" class="icon-file-small"><img src="../images/icon-small-xml.png"
                                                                            alt="XML"/></a>

                <div class="fileDetails">
                    <ul>
                        <li class="first">
                            <a href="fileVersionView.html">Project Dashboard.xml</a>
                        </li>
                        <li>
                            <a href="fileVersionView.html" class="version">Version 1.0</a>
                        </li>
                        <li>
                            <span class="size">50 KB</span>
                        </li>
                    </ul>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis felis. onsectetur adipiscing
                        elit. In <span class="ellipsis">..</span><a href="javascript:;">More</a><span class="moreText"> mollis felis. onsectetur adipiscing elit</span>
                    </p>
                </div>
            </div>
        </div>
    </td>
    <td class="categoryCell"><strong>UI Prototype</strong></td>
    <td class="dateCell">11/07/2012 8:00 EDT</td>
    <td class="managementCell">
        <a href="javascript:;" class="upload">Upload New Version</a>
        <a href="javascript:;" class="download">Download</a>
        <a href="javascript:;" class="edit">Edit File Details</a>
        <a href="javascript:;" class="remove">Remove</a>
    </td>
</tr>
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

