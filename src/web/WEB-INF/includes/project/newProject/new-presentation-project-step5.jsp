<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new presentation project step 5.
  -
  - Version 1.0
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPPTProjectStep5" class="hide newProjectStep">
    <!-- step title -->
    <div class="stepTitle">
        <h3><span>5</span>Upload content and any graphics for your presentation.</h3>
        <a href="<s:url action="dashboardActive" namespace="/"/>" class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    
    <!-- step ppt forth -->
    <div class="stepFifth stepContainer">
        <div class="geryContent">
                                        
            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <!-- .noteMask -->
            <div class="noteMask">
                <dl class="speciealDl">
                    <dt>
                        <strong>Note:</strong>
                        <a href="javascript:void(0)">Hide</a>
                    </dt>
                     <dd>Please upload a detailed outline of your presentation and any
additional notes or documents that are needed to prepare the text of
your presentation.</dd>
                     <dd>If the full text of the presentation already exists, please upload it
and tell us what changes, if any, you would like us to make.</dd>
                    <dd>Please upload any existing graphical assets, such as corporate branding,
product images, or existing presentation graphics that you want us to adapt or improve.</dd>
                </dl>
            </div><!-- End .noteMask -->
            
            <!-- form -->
            <div class="form speciealForm"> 
                
                <div class="tableWrapper">
                    <div class="topNotice">
                        <div class="noticeRight">All fields marked with <span class="red">*</span> are mandatory</div>
                    </div>
                    
                    <div class="mediaAssetTableWrapper">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <thead>
                                <tr>
                                    <th colspan="2">
                                        <a href="javascript:;" class="toolTip" rel='Any file is accepted.'></a>
                                        File upload
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="noMediaAsset">
                                    <td colspan="2">No media assets provided</td>
                                </tr>
                                <tr class="tfooter">
                                    <td colspan="2">
                                        <div class="browserParts">
                                            <div class="topRowWrapper">
                                                <input type="text" class="text browserInput" name="pptMediaFileName" value="File Name" readonly="readonly" />
                                                <div class="errorText">No file upload selected.</div>
                                                <div class="errorIcon"></div>
                                            </div>
                                            <div class="subButtonBox">
                                                <span class="summeryForBrowse">File location (all file types accepted)</span>
                                                <div class="rightPart">
                                                    <div class="browserWrapper">
                                                        <input type="file" id="uploadFile" name="document" />
                                                        <a href="javascript:;" class="button13" id="browserButton"><span class="btnR"><span class="btnC">Browse</span></span></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="addMoreParts">
                                            <div class="topRowWrapper">
                                                <div class="categoryWrapper">
                                                <label>Category:</label> <span class="option"><input type="radio" name="category" value="content" checked="checked"/>content</span> <span class="option"> <input type="radio" name="category" value="graphics" />graphics</span>
                                                </div>
                                            </div>
                                            
                                            <div class="subButtonBox">
                                                <input type="text" class="text" id="customFileDescription" value="Instructions (tell us how to use the uploaded file)" />
                                                <a href="javascript:;" class="uploadFileButton button12"><span class="btnR"><span class="btnC">Upload file</span></span></a>
                                                <div class="errorText">This field cannot be left empty.</div>
                                                <div class="errorIcon"></div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                </div>
                
            </div>
            <!-- End .form -->
            
            <!-- bottom bar -->
            <div class="BottomBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .BottomBar -->
            
            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>
            
        </div>
    </div>
</div>
