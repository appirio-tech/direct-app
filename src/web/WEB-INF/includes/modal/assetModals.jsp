<%--
  - Author: GreatKevin, TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TopCoder Cockpit Asset View And File Version）
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Asset View Release 4 - Resource restriction update)
  - - Update the permission setting to be Project / Public, remove private permission settings
  -
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: modal windows used in the project assets page
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- #savingModal -->
<div id="savingModal" class="outLay">
    <div class="modalHeaderSmall">
        <div class="modalHeaderSmallRight">
            <div class="modalHeaderSmallCenter">
            </div>
        </div>
    </div>
    <div class="modalBody">
        <img src="../images/preloader-loading.gif" alt="Loading" />
        <div class="preloaderTips">Saving...</div>
    </div>
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
</div>
<!-- End #savingModal -->

<!-- #deletingModal -->
<div id="deletingModal" class="outLay">
    <div class="modalHeaderSmall">
        <div class="modalHeaderSmallRight">
            <div class="modalHeaderSmallCenter">
            </div>
        </div>
    </div>
    <div class="modalBody">
        <img src="../images/preloader-loading.gif" alt="Loading" />
        <div class="preloaderTips">Deleting...</div>
    </div>
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
</div>
<!-- End #deletingModal -->

<!-- #uploadFileVersion -->
<div id="uploadFileVersion" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>UPLOAD NEW VERSION OF FILE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails uploadFileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="uploadComfirm">
                <h2>Are you sure to upload new version of this file?</h2>
                <p>New file will replace the current file in Cockpit.</p>
                <span class="note">Note: You may access old versions of this file via version history.</span>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="btnGreyModal btnUseLateVersion"><span class="btnR"><span class="btnC">NO, USE LATEST VERSION</span></span></a>
            <a href="javascript:;" class="newButton1 newButtonGreen btnUploadYes"><span class="btnR"><span class="btnC">YES</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #uploadFileVersion -->

<!-- #selectFileForVersion -->
<div id="selectFileForVersion" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>UPLOAD NEW VERSION OF FILE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails uploadFileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="selectFileSection" id="singleUploadArea">
                <div class="dragDropUpload">
                    <div class="dynamicUpload">
                        <div class="dragPre">
                            <p>Drag & Drop is not supported for now</p>
                        </div>
                        <div class="dragSucess">
                            <p>File added. Please click upload button to start uploading.</p>
                            <ul>
                                <li>
                                    <a href="javascript:;" class="removeFile">X</a>
                                    <span class="icon-file-small zip">ZIP</span>
                                    <div class="fileName">Project Dashboard.zip <span>(1.5 MB)</span></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="bottom">
                        <div class="bottomL">
                            <div class="bottomR">
                                <div class="bottomM"></div>
                            </div>
                        </div>
                    </div>
                    <div class="corner tl"></div>
                    <div class="corner tr"></div>
                </div>
                <!-- AJAX Upload -->
                <div class="commonUpload">
                    <label>Having Problem? Try basic uploader</label>
                    <input type="file" name="files"/>
                </div>
            </div>
            <div class="selectFileSection" id="multipleUploadArea">
                <div class="dragDropUpload">
                    <div class="dynamicUpload">
                        <form method="post" action="uploadAssetFile" enctype="multipart/form-data">
                            <input id="fileDropInput" class="hide" type="file" name="files" />
                            <a id="fileDropInputSubmit" class="hide" href="javascript:;"></a>
                        </form>
                        <div class="dragPre">
                            <p>Please drag and drop the file anywhere on this area to upload.</p>
                        </div>
                        <div class="dragSucess">
                            <p>File added. Please click upload button to start uploading.</p>
                            <ul>
                                <li>
                                    <a href="javascript:;" class="removeFile">X</a>
                                    <span class="icon-file-small zip">ZIP</span>
                                    <div class="fileName">Project Dashboard.zip <span>(1.5 MB)</span></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="bottom">
                        <div class="bottomL">
                            <div class="bottomR">
                                <div class="bottomM"></div>
                            </div>
                        </div>
                    </div>
                    <div class="corner tl"></div>
                    <div class="corner tr"></div>
                </div>
                <!-- AJAX Upload -->
                <div class="commonUpload">
                    <label>Having Problem? Try basic uploader</label>
                    <input type="file" name="files"/>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="btnGreyModal btnCancel"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
            <a href="javascript:;" class="newButton1 btnUpload"><span class="btnR"><span class="btnC"><span class="icon">Upload</span></span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #selectFileForVersion -->

<!-- #uploadingFileVersion -->
<div id="uploadingFileVersion" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>UPLOAD NEW VERSION OF FILE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails uploadFileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="uploadingFileSection">
                <h2>Uploading file</h2>
                <div class="fileGroup">
                    <span class="icon-file-small"><img src="../images/icon-small-zip.png" alt="ZIP" /></span>
                    <div class="fileName">Project Dashboard.zip <span>(1.5 MB)</span></div>
                </div>
                <div class="processBar">
                    <div class="processBarInner"></div>
                </div>
                <div class="fileInfor">
                    <div class="fileSize"><span>0</span> MB of total 1.5 MB</div>
                    <div class="ramainTime">30 seconds left</div>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="newButton1 btnCancelUpload"><span class="btnR"><span class="btnC">CANCEL UPLOADING</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #uploadingFileVersion -->

<!-- #uploadCancel -->
<div id="uploadCancel" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>UPLOAD NEW VERSION OF FILE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails uploadFileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="cancelComfirm">
                <div class="comfirmSection">
                    <h2>Cancel upload?</h2>
                    <div class="icon-alert">ALERT</div>
                    <p>Are you sure you to cancel the upload process?</p>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="btnGreyModal btnContinue"><span class="btnR"><span class="btnC">NO, CONTINUE UPLOAD</span></span></a>
            <a href="javascript:;" class="newButton1 btnUploapCancel"><span class="btnR"><span class="btnC">YES</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #uploadCancel -->

<!-- #uploadFinish -->
<div id="uploadFinish" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>UPLOAD NEW VERSION OF FILE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails uploadFileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="editFileSection">
                <h2>Would you like to update file details?</h2>
                <div class="addDescription">
                    <label>Description :</label>
                    <div class="textarea">
                        <textarea rows="" cols=""></textarea>
                    </div>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="btnGreyModal btnNoUpload"><span class="btnR"><span class="btnC">No, USE EXISTING DETAILS</span></span></a>
            <a href="javascript:;" class="newButton1 btnSaveDetails saveNewVersionDetails"><span class="btnR"><span class="btnC">SAVE DETAILS</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #uploadFinish -->

<!-- #removeFileFromCockpit -->
<div id="removeFileFromCockpit" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>REMOVE FILES FROM COCKPIT?</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <div class="fileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="../images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by: <a href="javascript:;" class="blue">caru</a></p>
                    <p class="date">12/20/2012 00:00 EDT</p>
                </div>
            </div>
            <div class="deleteComfirm">
                <p class="comfirmTxt">Please select version to delete :</p>
                <div class="radioRow">
                    <input type="radio" name="versionRadio" checked="checked" id="currentVersion" />
                    <label for="currentVersion">This Current Version Only (3.2)</label>
                </div>
                <div class="radioRow">
                    <input type="radio" name="versionRadio" id="allVersions" />
                    <label for="allVersions">All Versions</label>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="newButton1 newButtonGreen btnYes"><span class="btnR"><span class="btnC">YES</span></span></a>
            <a href="javascript:;" class="newButton1 newButtonGreen btnCancel"><span class="btnR"><span class="btnC">NO</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #removeFileFromCockpit -->

<!-- #editFileDetails -->
<div id="editFileDetails" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>EDIT FILE DETAILS</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <!-- left sidebar -->
            <div class="fileDetails">
                <div class="versionSection lastVersion">
                    <div class="icon-file"><img src="/images/icon-zip.png" alt="ZIP" /></div>
                    <a href="javascript:;" class="fileName">Project Dashboard.zip</a>
                    <ul>
                        <li class="first"><strong class="version">Version 1.2</strong></li>
                        <li><span>2.1 MB</span></li>
                    </ul>
                    <p class="description">Lorem ipsum dolor sit amet</p>
                    <p class="fileCategory">Storyboard</p>
                    <p class="uploadBy">Uploaded by:</p>
                    <p class="date"></p>
                </div>
            </div>
            <!-- edit panel -->
            <div class="editFileSection">
                <h2>File Details</h2>
                <div class="selectCategory">
                    <label>Category :</label>
                    <select>
                        <option value="-1">- Please Select the asset category -</option>
                        <s:iterator value="viewData.assetCategories">
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
                        <textarea rows="" cols="">description holder</textarea>
                    </div>
                </div>
                <div class="selectAccess">
                    <strong class="label">Access Restriction</strong>
                    <div class="radioBox">
                        <input type="radio" name="editAccessRadio" checked="checked" id="accessRadio5" class="project" title="This file is only visible to users with permissions on this project"/>
                        <label for="accessRadio5" class="project" title="This file is only viewable to user who has permission on this project">Project</label>
                        <input type="radio" name="editAccessRadio" id="accessRadio6" class="public" title="This file is visible to all TopCoder Members"/>
                        <label for="accessRadio6" class="public" title="This file is visible to all TopCoder Members">Public</label>
                    </div>
                    <div class="clearFix"></div>
                </div>
            </div>
            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <a href="javascript:;" class="btnGreyModal btnNoUpload"><span class="btnR"><span class="btnC">DISCARD</span></span></a>
            <a href="javascript:;" class="newButton1 btnSaveDetails"><span class="btnR"><span class="btnC">SAVE DETAILS</span></span></a>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #editFileDetails -->

<!-- #batchDelete -->
<div id="batchDelete" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>BATCH DELETE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">

            <div class="fileList">
                <p>Selected Files(<span class="listFileNumber">0</span>)</p>
                <ul>
                    <li class="item-temp hide">
                        <div class="item">
                            <span class="icon-file-small"><img src="/images/icon-small-doc.png" alt="DOC" /></span>
                            <span class="fileName">Client Questionnaire.docx</span>
                            <span class="size">(1.5 MB)</span>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- End .fileList -->

            <div class="fileDeleteing">
                <div class="icon-delete">DELETE</div>
                <div class="fileDeleteingInfor">
                    <h2>Do you really want to delete all files listed here?</h2>
                    <p>This action will delete your selected files listed in the left.</p>
                </div>
            </div>
            <!-- End .fileDeleteing -->

            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <div class="deleteingButtons">
                <a href="javascript:;" class="btnGreyModal btnCloseModal"><span class="btnR"><span class="btnC">NO</span></span></a>
                <a href="javascript:;" class="newButton1 btnSucess"><span class="btnR"><span class="btnC">YES</span></span></a>
            </div>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #batchDelete -->

<!-- #batchDeleted -->
<div id="batchDeleted" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>BATCH DELETE</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">

            <div class="fileList">
                <p>Selected Files(4)</p>
                <ul>
                    <li>
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-doc.png" alt="DOC" /></span>
                            <span class="fileName">Client Questionnaire.docx</span>
                            <span class="size">(1.5 MB)</span>
                        </div>
                    </li>
                    <li>
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-xls.png" alt="XLS" /></span>
                            <span class="fileName">Timeline.xls</span>
                            <span class="size">(1.1 MB)</span>
                        </div>
                    </li>
                    <li>
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-png.png" alt="PNG" /></span>
                            <span class="fileName">Client Ideas (Sketch).png</span>
                            <span class="size">(1.0 MB)</span>
                        </div>
                    </li>
                    <li>
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-pdf.png" alt="PDF" /></span>
                            <span class="fileName">BRD Document.pdf</span>
                            <span class="size">(1.2 MB)</span>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- End .fileList -->

            <div class="fileDeleted">
                <div class="icon-delete">DELETE</div>
                <div class="fileDeletedInfor">
                    <h2>All files has been deleted from the Cockpit Asset</h2>
                    <span class="note">Note: You may access deleted file from Activity tab.</span>
                </div>
            </div>
            <!-- End .fileDeleted -->

            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <div class="deletedButtons">
                <a href="fileVersionView.html" class="newButton1 btnFileHistory"><span class="btnR"><span class="btnC">VIEW FILES HISTORY</span></span></a>
            </div>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #batchDeleted -->

<!-- #batchDownload -->
<div id="batchDownload" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>BATCH DOWNLOAD</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">

            <div class="fileList">
                <p>Selected Files(<span class="listFileNumber">0</span>)</p>
                <ul>
                    <li class="item-temp hide">
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-doc.png" alt="DOC" /></span>
                            <span class="fileName">Client Questionnaire.docx</span>
                            <span class="size">(1.5 MB)</span>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- End .fileList -->

            <div class="fileDondloaded">
                <h2>File Ready</h2>
                <div class="fileDdownloadInfor">
                    <p>All files have been zipped and ready for download.</p>
                    <div class="fileInfor">
                        <span class="icon-file"><img src="/images/icon-zip.png" alt="ZIP" /></span>
                        <span class="filaName">2012-10-08 Cockpit Files.zip <span>(4.8 MB)</span></span>
                    </div>
                </div>
            </div>
            <!-- End .fileDeleted -->

            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <div class="downloadedButtons">
                <a href="javascript:;" class="newButton1 btnDownload"><span class="btnR"><span class="btnC"><span class="icon">DOWNLOAD</span></span></span></a>
            </div>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>
<!-- end #batchDownload -->

<!-- #batchSetPermission -->
<div id="batchSetPermission" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>BATCH SET PERMISSION</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">

            <div class="fileList">
                <p>Selected Files(<span class="listFileNumber">0</span>)</p>
                <ul>
                    <li class="item-temp hide">
                        <div class="item">
                            <span class="icon-file-small"><img src="../images/icon-small-doc.png" alt="DOC" /></span>
                            <span class="fileName">Client Questionnaire.docx</span>
                            <span class="size">(1.5 MB)</span>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- End .fileList -->

            <div class="fileSetPermission">
                <div class="icon-clock">LOCK</div>
                <div class="setPermissionSection">
                    <h2>Set permission of listed files to:</h2>
                    <div class="selectAccess">
                        <strong class="label">Access Restriction</strong>
                        <div class="radioBox">
                            <input type="radio" name="radioModalPermission" checked="checked" id="radioModalProject" class="project" title="This file is only visible to users with permissions on this project"/>
                            <label for="radioModalProject" class="project" title="This file is only viewable to user who has permission on this project">Project</label>
                            <input type="radio" name="radioModalPermission" id="radioModalPublic" class="public" title="This file is visible to all TopCoder Members"/>
                            <label for="radioModalPublic" class="public" title="This file is visible to all TopCoder Members">Public</label>
                        </div>
                        <div class="popUpPrivateAccess">
                            <div class="popUpPrivateAccseeInner">
                                <div class="popUpPrivateAccessSection">
                                    <div class="popUpPrivateAccessSArrow"></div>
                                    <div class="accessUser">
                                        <div class="group">
                                            <div class="firstItem">
                                                <input type="checkbox" id="group10check01" />
                                                <label for="group10check01">Client Managers</label>
                                            </div>
                                            <s:iterator value="viewData.clientManagers">
                                                <div>
                                                    <input type="checkbox" id="<s:property value='id'/>" class="assetUserPermission"/>
                                                    <label for="<s:property value='id'/>"><link:user userId="${id}"/></label>
                                                </div>
                                            </s:iterator>
                                        </div>
                                        <div class="group">
                                            <div class="firstItem">
                                                <input type="checkbox" id="group11check01" />
                                                <label for="group11check01">TopCoder Managers</label>
                                            </div>
                                            <s:iterator value="viewData.topcoderManagers">
                                                <div>
                                                    <input type="checkbox" id="<s:property value='id'/>" class="assetUserPermission"/>
                                                    <label for="<s:property value='id'/>"><link:user userId="${id}"/></label>
                                                </div>
                                            </s:iterator>
                                        </div>
                                        <div class="group">
                                            <div class="firstItem">
                                                <input type="checkbox" id="group12check01" />
                                                <label for="group12check01">Copilots</label>
                                            </div>
                                            <s:iterator value="viewData.projectCopilots">
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
                        <!-- Priveate Access -->
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
                        <!-- Priveate Access View -->
                        <div class="clearFix"></div>
                    </div>
                    <p class="note">Note: You may set specific file permissions for each file individually at a time by Batch Edit.</p>
                </div>
            </div>
            <!-- End .fileSetPermission -->

            <div class="clearFix"></div>
        </div>
        <div class="modalCommandBox">
            <div class="downloadingButtons">
                <a href="javascript:;" class="btnGreyModal btnDiscard"><span class="btnR"><span class="btnC">DISCARD</span></span></a>
                <a href="javascript:;" class="newButton1 btnSaveChanges"><span class="btnR"><span class="btnC">SAVE CHANGES</span></span></a>
            </div>
        </div>
    </div>
    <!-- end .modalBody -->

    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter"></div>
        </div>
    </div>
    <!-- end .modalFooter -->
</div>


<!-- #ImageViewModal -->
<div id="imageViewModal">
    <div class="imageViewModalInner">
        <div class="viewSection"><img src="" alt="asset image preview" /></div>
        <a href="javascript:;" class="closeModal" title="Close">Close</a>
    </div>
    <div class="imageViewModalFooter">
        <span class="fileName">Client Ideas (Sketch).png</span>
        <a href="javascript:;" class="btnImageModal viewFullSize"><span><span>VIEW FULL SIZE</span></span></a>
        <a href="javascript:;" class="btnImageModal"><span><span>DOWNLOAD</span></span></a>
    </div>
</div>
<!-- End #imageViewModal -->

<!-- #ImageVersionViewModal -->
<div id="ImageVersionViewModal">
    <div class="imageViewModalInner">
        <a href="javascript:;" class="closeModal" title="Close">Close</a>
        <div class="viewSection">
            <ul>
                <li class="first"><img src="../images/larger_submission_1.png" alt="PLACEHOLDER" /></li>
                <li><img src="../images/larger_submission_2.png" alt="PLACEHOLDER" /></li>
                <li><img src="../images/larger_submission_3.png" alt="PLACEHOLDER" /></li>
                <li><img src="../images/larger_submission_4.png" alt="PLACEHOLDER" /></li>
                <li><img src="../images/larger_submission_1.png" alt="PLACEHOLDER" /></li>
            </ul>
        </div>
        <div class="navigation">
            <h2>FILE HISTORY</h2>
            <p class="note">Click at version to see preview image</p>
            <ul>
                <li class="currentVersion">
                    <div class="version">Verision 1.4 <span>(Current Version)</span></div>
                    <div class="uploader">Uploaded by: <a href="javascript:;" class="blue">caru</a></div>
                    <div class="time">12/20/2012 00:00 EDT</div>
                </li>
                <li>
                    <div class="version">Verision 1.3 <span>(Current Version)</span></div>
                    <div class="uploader">Uploaded by: <a href="javascript:;" class="blue">caru</a></div>
                    <div class="time">12/20/2012 00:00 EDT</div>
                </li>
                <li>
                    <div class="version">Verision 1.2 <span>(Current Version)</span></div>
                    <div class="uploader">Uploaded by: <a href="javascript:;" class="blue">caru</a></div>
                    <div class="time">12/20/2012 00:00 EDT</div>
                </li>
                <li>
                    <div class="version">Verision 1.1 <span>(Current Version)</span></div>
                    <div class="uploader">Uploaded by: <a href="javascript:;" class="blue">caru</a></div>
                    <div class="time">12/20/2012 00:00 EDT</div>
                </li>
                <li>
                    <div class="version">Verision 1.0 <span>(Current Version)</span></div>
                    <div class="uploader">Uploaded by: <a href="javascript:;" class="blue">caru</a></div>
                    <div class="time">12/20/2012 00:00 EDT</div>
                </li>
            </ul>
        </div>
    </div>
    <div class="imageViewModalFooter">
        <span class="fileName">Client Ideas (Sketch).png</span>
        <a href="javascript:;" class="btnImageModal viewFullSize"><span><span>VIEW FULL SIZE</span></span></a>
        <a href="javascript:;" class="btnImageModal"><span><span>DOWNLOAD</span></span></a>
    </div>
</div>
<!-- End #ImageVersionViewModal -->

