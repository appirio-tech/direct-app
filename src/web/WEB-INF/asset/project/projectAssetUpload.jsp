<%--
  - Author: GreatKevin, TCSASSEMBLER
  -
  - The jsp for the project asset upload and save.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0) changes:
  - - Implements the single upload of assets and save asset details.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
  - - Update the permission setting to be Project / Public, remove private permission settings
  -  
  - Version 1.2 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Bug Fixes)
  - - Hide the Upload save details button by default, only display it when the uploads are finished
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>TopCoder Cockpit</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/screen.css?v=214495" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/launchcontest.css?v=215011" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css?v=215352" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/thickbox.css?v=192822" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/jScrollPane.css?v=176771" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css?v=206355" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css?v=211688" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css" />

    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css?v=203928" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css?v=176771"/>
    <![endif]-->

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css?v=215325"/>
    <![endif]-->

    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css?v=203310"/>
    <![endif]-->

    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css?v=203310"/>
    <![endif]-->

    <!-- External javascript -->
    <script type="text/javascript">
        //<![CDATA[
        window.CKEDITOR_BASEPATH='/scripts/ckeditor/ckeditor/';
        //]]>
    </script>

    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js?v=179771"></script>

    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.bgiframe.js?v=207894"></script>
    <script type="text/javascript" src="/scripts/date.prev.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/date.js?v=185881"></script>
    <script type="text/javascript" src="/scripts/common.js?v=215290"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js?v=214829"></script>
    <script type="text/javascript" src="/scripts/jquery.stylish-select.js?v=188719"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js?v=179771"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js?v=209582"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
    <script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="/scripts/jquery.autocomplete.js?v=183826"></script>
    <script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js?v=215325"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=215325"></script>

    <script type="text/javascript" src="/scripts/dashboard.js?v=215352"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>
    <script type="text/javascript" src="/scripts/maintenance.js?v=2146111"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js"></script>
    <script type="text/javascript" src="/scripts/instantSearch.js"></script>
    <ui:projectPageType tab="assets"/>
    <link rel="stylesheet" href="/css/direct/filter-panel.css" media="all" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/assets-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" href="/css/direct/assets.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/search.js"></script>
    <script type="text/javascript" src="/scripts/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="/scripts/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="/scripts/jquery.fileupload.js"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js"></script>

    <script type="text/javascript" src="/scripts/assets.js"></script>
</head>

<body id="page">

<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content" class="asset">
                <jsp:include page="../../includes/header.jsp"/>


                <div id="mainContent" class="newSidebarCollapse">
                    <jsp:include page="../../includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                        <div class="currentPage">
                            <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                            &gt;
                            <a href="<s:url action="allProjects" namespace="/"/>">Projects</a> &gt;
                            <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
                                    value="sessionData.currentProjectContext.name"/></a> &gt;
                            <a href='<s:url action="projectAssets" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'>Files</a> &gt;
                            <strong>Upload Files</strong>
                        </div>

                            <div class="areaHeader">
                                <h2 class="title asssetsTitle">Files</h2>
                                <input type="hidden" id="assetProjectId" value="${formData.projectId}"/>
                            </div>
                            <!-- End .areaHeader -->

                            <div class="uploadSection" id="beforeUpload">
                                <div class="sectionHeader">
                                    <div class="sectionHeaderL">
                                        <div class="sectionHeaderR">
                                            <div class="sectionHeaderM">
                                                <a class="button6 btnUpload singleUploadButton" href="javascript:;"><span
                                                        class="left"><span class="right"><span
                                                        class="icon">UPLOAD</span></span></span></a>

                                                <h2>Upload File(s)</h2>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- End .sectionHeader -->
                                <div class="sectionContainer uploadContainer hide" id="multipleUploadArea">

                                    <div class="dragDropUpload">

                                        <div class="dynamicUpload">
                                            <form method="post" action="uploadAssetFile" enctype="multipart/form-data">
                                                <input id="fileDropInput" class="hide" type="file" name="files" />
                                                <a id="fileDropInputSubmit" class="hide" href="javascript:;"></a>
                                            </form>
                                            <div class="dragPre">
                                                <p>You can select more than one file at a time. You can also <br />drag and drop files anywhere on this area to upload.</p>
                                            </div>
                                            <div class="dragSucess">
                                                <p><span>0</span> Files added, Please click upload button to start uploading, you can also add more files</p>
                                                <ul>
                                                </ul>
                                            </div>
                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>
                                        <div class="corner bl"></div>
                                        <div class="corner br"></div>

                                    </div>
                                    <!-- AJAX Upload -->

                                    <div class="commonUpload">
                                        <label>Having Problem? Try basic uploader</label>
                                        <div class="uploadRow">
                                            <input type="file" size="20" name="files" />
                                        </div>
                                        <div class="uploadRow">
                                            <input type="file" size="20" name="files" />
                                        </div>
                                        <div class="uploadRow">
                                            <input type="file" size="20" name="files" />
                                        </div>
                                        <div class="uploadRow">
                                            <input type="file" size="20" name="files" />
                                        </div>
                                        <div class="uploadRow">
                                            <input type="file" size="20" name="files" />
                                        </div>
                                        <div class="commonButton">
                                            <a href="javascript:;" class="btnMoreFile"><span><span><span class="icon">More File</span></span></span></a>
                                        </div>
                                    </div>
                                    <!-- End .commonUpload -->

                                </div>

                                <div class="sectionContainer uploadContainer hide" id="singleUploadArea">

                                    <div class="dragDropUpload">

                                        <div class="dynamicUpload">
                                            <div class="dragPre">
                                                <%--<p>You can select more than one file at a time. You can also <br/>drag--%>
                                                <%--and drop files anywhere on this area to upload.</p>--%>
                                                <p>Drag & Drop is not supported for Internet Explorer. We recommend to use Chrome or Firefox</p>
                                            </div>
                                            <div class="dragSucess">
                                                <p><span>0</span> Files added, Please click upload button to start
                                                    uploading, you can also add more files</p>
                                                <ul>
                                                    <li>
                                                        <a href="javascript:;" class="removeFile">X</a>
                                                        <span class="icon-file-small zip">ZIP</span>

                                                        <div class="fileName">Project Dashboard.zip
                                                            <span>(1.5 MB)</span></div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>

                                        <div class="corner tl"></div>
                                        <div class="corner tr"></div>
                                        <div class="corner bl"></div>
                                        <div class="corner br"></div>

                                    </div>
                                    <!-- AJAX Upload -->

                                    <div class="commonUpload">
                                        <label>Having Problem? Try basic uploader</label>
                                        <div class="uploadRow uploadInputsSection">
                                            <input type="file" size="20" name="files"/>
                                        </div>
                                    </div>
                                    <!-- End .commonUpload -->

                                </div>
                                <!-- End .sectionContainer -->
                                <div class="uploadButtonBox">
                                    <a class="button6 btnUpload singleUploadButton" href="javascript:;"><span class="left"><span
                                            class="right"><span class="icon">UPLOAD</span></span></span></a>
                                </div>
                            </div>
                                <!-- End .sectionContainer -->

                            <div class="uploadSection hide" id="uploading">
                                <div class="sectionHeader">
                                    <div class="sectionHeaderL">
                                        <div class="sectionHeaderR">
                                            <div class="sectionHeaderM">
                                                <div class="uploadSucess">
                                                    <a class="button6 btnSaveDetails" href="javascript:"><span class="left"><span class="right">SAVE DETAILS</span></span></a>
                                                    <a class="button7" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="left"><span class="right">DISCARD</span></span></a>
                                                    <p>All Files have been successfully uploaded</p>
                                                </div>
                                                <!-- End allFileProcessBar -->
                                                <div class="uploading">
                                                    <a class="button6 btnCancelUploading" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="left"><span class="right">CANCEL UPLOADING</span></span></a>
                                                    <div class="allFileProcessBar">
                                                        <div class="allFileInfor"><strong><em class="fileTotalNumber">3</em> Files</strong> <span>|</span> <em class="fileTotalSize">4.5 MB</em></div>
                                                        <div class="processBar">
                                                            <div class="processBarInner">
                                                                <div class="processed"></div>
                                                            </div>
                                                        </div>
                                                        <div class="remainTime"><span class="processTime">20</span> Seconds Left</div>
                                                    </div>
                                                </div>
                                                <h2>Upload File(s)</h2>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- End .sectionHeader -->
                                <div class="sectionContainer" id="processing">

                                    <div class="fileUploadingSection">

                                        <div class="sectionInner">

                                            <div class="itemSection itemSection-temp">
                                                <div class="fileUploadItem">
                                                    <a href="javascript:;" class="removeFileUploadItem">Remove</a>
                                                    <div class="icon-file"><img src="/images/icon-zip.png" alt="ZIP"/></div>
                                                    <p class="fileName"></p>
                                                    <div class="uploading">
                                                        <p class="processInfor"><span class="fileSize">0 MB</span> of <span class="size">1.5 MB</span> <span class="line">|</span> <span class="processTime">20</span> Seconds Left</p>
                                                        <div class="processBar">
                                                            <div class="processBarInner">
                                                                <div class="processed"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="uploadSucess">
                                                        <p><span class="size"></span> Version 1.0</p>
                                                        <p>Uploaded by: <span class="uploader"></span></p>
                                                        <p class="uploadTime"></p>
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
                                                            <textarea rows="" cols=""></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="selectAccess">
                                                        <strong class="label">Access Restriction</strong>
                                                        <div class="radioBox">
                                                            <input type="radio" name="accessRadio" checked="checked" id="accessRadioProject" class="project" title="This file is only visible to users with permissions on this project"/>
                                                            <label for="accessRadioProject" class="public" title="This file is only visible to users with permissions on this project">Project</label>
                                                            <input type="radio" name="accessRadio" id="accessRadioPublic" class="public" title="This file is visible to all TopCoder Members"/>
                                                            <label for="accessRadioPublic" class="public" title="This file is visible to all TopCoder Members">Public</label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- End .fileUploadItem -->
                                            </div>

                                        </div>

                                    </div>
                                    <!-- End .fileUploadingSection -->

                                </div>
                                <!-- End .sectionContainer -->

                                <div class="uploadButtonBox">
                                    <div class="uploadSucess" style="display: none">
                                        <a class="button6 btnSaveDetails" href="javascript:"><span class="left"><span class="right">SAVE DETAILS</span></span></a>
                                        <a class="button7" href="<s:url action="projectAssets" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="left"><span class="right">DISCARD</span></span></a>
                                    </div>
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
