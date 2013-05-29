<%--
  - Author: TCSASSEMBLER
  -
  - The jsp for the project asset upload and save.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0) changes:
  - - Implements the single upload of assets and save asset details.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../../includes/htmlhead.jsp"/>
    <ui:projectPageType tab="assets"/>
    <link rel="stylesheet" href="/css/direct/filter-panel.css" media="all" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/assets-ie7.css"/>
    <![endif]-->
    <link rel="stylesheet" href="/css/direct/assets.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/search.js"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js"></script>
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
                            <a href='<s:url action="projectAssets" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'>Files</a> &gt;
                            <strong>Upload Files</strong>
                        </div>

                            <div class="areaHeader">
                                <h2 class="title asssetsTitle">Files</h2>
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
                                <div class="sectionContainer">

                                    <div id="dynamicUpload">

                                        <div class="dynamicUpload">
                                            <div class="dragPre">
                                                <%--<p>You can select more than one file at a time. You can also <br/>drag--%>
                                                    <%--and drop files anywhere on this area to upload.</p>--%>
                                                <p>Drag & Drop is not supported right now</p>
                                            </div>
                                            <div class="dragSucess">
                                                <p><span>3</span> Files added, Please click upload button to start
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
                                        <input type="hidden" id="assetProjectId" value="${formData.projectId}"/>
                                        <div class="uploadRow uploadInputsSection">
                                            <input type="file" size="20" name="uploadFile"/>
                                        </div>
                                        <%--<div class="uploadRow">--%>
                                            <%--<input type="file" size="20"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="uploadRow">--%>
                                            <%--<input type="file" size="20"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="uploadRow">--%>
                                            <%--<input type="file" size="20"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="uploadRow">--%>
                                            <%--<input type="file" size="20"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="commonButton">--%>
                                            <%--<a href="javascript:;" class="btnMoreFile"><span><span><span class="icon">More File</span></span></span></a>--%>
                                        <%--</div>--%>
                                    </div>
                                    <!-- End .commonUpload -->

                                </div>
                                <!-- End .sectionContainer -->

                                <div class="uploadButtonBox">
                                    <a class="button6 btnUpload singleUploadButton" href="javascript:;"><span class="left"><span
                                            class="right"><span class="icon">UPLOAD</span></span></span></a>
                                </div>

                            </div>
                            </form>

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
                                                <%--<div class="uploading">--%>
                                                    <%--<a class="button6 btnCancelUploading" href="asset.html"><span class="left"><span class="right">CANCEL UPLOADING</span></span></a>--%>
                                                    <%--<div class="allFileProcessBar">--%>
                                                        <%--<div class="allFileInfor"><strong><em class="fileTotalNumber">3</em> Files</strong> <span>|</span> <em class="fileTotalSize">4.5 MB</em></div>--%>
                                                        <%--<div class="processBar">--%>
                                                            <%--<div class="processBarInner">--%>
                                                                <%--<div class="processed"></div>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="remainTime"><span class="processTime">20</span> Seconds Left</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
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
                                                            <a href="javascript:;" class="LinkNewCategory noFlyout">New Category</a>
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
                                                            <input type="radio" name="accseeRadio" checked="checked" id="accessRadio1" class="public" />
                                                            <label for="accessRadio1" class="public">Public</label>
                                                            <input type="radio" name="accseeRadio" id="accessRadio2" class="private noFlyout" />
                                                            <label for="accessRadio2" class="private noFlyout">Private</label>
                                                            <span class="accessOperate">
                                                                <a href="javascript:;" class="linkViewAccess">View Access</a>
                                                                <span>|</span>
                                                                <a href="javascript:;" class="linkEditAccess">Edit</a>
                                                            </span>
                                                        </div>
                                                        <div class="popUpPrivateAccess">
                                                            <div class="popUpPrivateAccseeInner">
                                                                <div class="popUpPrivateAccessSection">
                                                                    <div class="popUpPrivateAccessSArrow"></div>
                                                                    <div class="accessUser">
                                                                        <div class="group">
                                                                            <div class="firstItem">
                                                                                <input type="checkbox" id="group01check01" class="groupCheck"/>
                                                                                <label for="group01check01">Client Managers</label>
                                                                            </div>

                                                                            <s:iterator value="clientManagers">
                                                                                <div>
                                                                                    <input type="checkbox" id="<s:property value='id'/>" class="assetUserPermission"/>
                                                                                    <label for="<s:property value='id'/>"><link:user userId="${id}"/></label>
                                                                                </div>
                                                                            </s:iterator>
                                                                        </div>
                                                                        <div class="group">
                                                                            <div class="firstItem">
                                                                                <input type="checkbox" id="group02check01" class="groupCheck"/>
                                                                                <label for="group02check01">TopCoder Managers</label>
                                                                            </div>
                                                                            <s:iterator value="topcoderManagers">
                                                                                <div>
                                                                                    <input type="checkbox" id="<s:property value='id'/>" class="assetUserPermission"/>
                                                                                    <label for="<s:property value='id'/>"><link:user userId="${id}"/></label>
                                                                                </div>
                                                                            </s:iterator>
                                                                        </div>
                                                                        <div class="group">
                                                                            <div class="firstItem">
                                                                                <input type="checkbox" id="group03check01" class="groupCheck"/>
                                                                                <label for="group03check01">Copilots</label>
                                                                            </div>
                                                                            <s:iterator value="projectCopilots">
                                                                                <div>
                                                                                    <input type="checkbox" id="<s:property value='id'/>" class="assetUserPermission"/>
                                                                                    <label for="<s:property value='id'/>"><link:user userId="${id}"/></label>
                                                                                </div>
                                                                            </s:iterator>
                                                                        </div>
                                                                    </div>
                                                                    <div class="linksBox">
                                                                        <a href="javascript:;" class="linkSubmit">Submit</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- Private Access -->
                                                        <div class="popUpPrivateAccessView">
                                                            <div class="popUpPrivateAccseeInner">
                                                                <div class="popUpPrivateAccessSection">
                                                                    <div class="popUpPrivateAccessSArrow"></div>
                                                                    <div class="accessUser"></div>
                                                                    <div class="linksBox">
                                                                        <a href="javascript:;" class="linkClose">Close</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!-- Private Access View -->
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
                                    <div class="uploadSucess">
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
