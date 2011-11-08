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
        <h3><span>5</span>Files</h3>
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
                     <dd>If you have any existing media assets that would have to assist in the presentation creation.</dd>  
                     <dd>Thes can be: existing presentations, tables, graphics, company logo, product images, brand style guide, othes template, etc.</dd>  
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
                                        Media Asset
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
                                                <span class="summeryForBrowse">any files accepted</span>
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
                                                <div class="selectWrapper">
                                                    <select id="selectFileDescription">
                                                        <option value="0">-- Select File Description --</option>
                                                        <option>Requirements Specification</option>
                                                        <option>Presentation Graphics</option>
                                                        <option>Presentation Slides</option>
                                                        <option>Presentation Template</option>
                                                        <option>Others</option>
                                                    </select>
                                                </div>
                                            </div>
                                            
                                            <div class="subButtonBox">
                                                <input type="text" class="text" id="customFileDescription" value="Custom File Description" />
                                                <a href="javascript:;" class="addMoreButton button12"><span class="btnR"><span class="btnC">Add More</span></span></a>
                                                <div class="errorText">This field cannot be left empty.</div>
                                                <div class="errorIcon"></div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="barTitle">
                        <a href="javascript:;" class="toolTip" id="toolTip5" rel='The deliverable file type.'></a>
                        Deliverables Types (please check all that apply)<span class="red">*</span>
                        <span class="errorStatusTips">No DeliverableType selected.</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->
                    
                    
                    <div class="deliverablesTypesList">
                    
                        <!--.deliverablesTypeItem-->
                        <div class="deliverablesTypeItem firstDeliverablesTypeItem">
                            <div class="typeContainer">
                                <div class="boxTop">
                                    <div class="boxBottom">
                                        <div class="boxBody">
                                            <img src="/images/type-icon-1.png" alt="Deliverables Type" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checkboxBox">
                                <input type="checkbox" />
                                <label>PPT</label>
                            </div>
                        </div>
                        <!--.deliverablesTypeItem-->
                        
                        <!--.deliverablesTypeItem-->
                        <div class="deliverablesTypeItem">
                            <div class="typeContainer">
                                <div class="boxTop">
                                    <div class="boxBottom">
                                        <div class="boxBody">
                                            <img src="/images/type-icon-2.png" alt="Deliverables Type" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checkboxBox">
                                <input type="checkbox" />
                                <label>Keynote</label>
                            </div>
                        </div>
                        <!--.deliverablesTypeItem-->
                        
                        <!--.deliverablesTypeItem-->
                        <div class="deliverablesTypeItem">
                            <div class="typeContainer">
                                <div class="boxTop">
                                    <div class="boxBottom">
                                        <div class="boxBody">
                                            <img src="/images/type-icon-3.png" alt="Deliverables Type" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checkboxBox">
                                <input type="checkbox" />
                                <label>PDF</label>
                            </div>
                        </div>
                        <!--.deliverablesTypeItem-->
                        
                        <!--.deliverablesTypeItem-->
                        <div class="deliverablesTypeItem">
                            <div class="typeContainer">
                                <div class="boxTop">
                                    <div class="boxBottom">
                                        <div class="boxBody">
                                            <img src="/images/type-icon-4.png" alt="Deliverables Type" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checkboxBox">
                                <input type="checkbox" />
                                <label>JPGs/PNGs</label>
                            </div>
                        </div>
                        <!--.deliverablesTypeItem-->
                        
                        <div class="clear"></div>
                    </div>
                    <!--.deliverablesTypesList-->
                    
                    <div class="otherTypeTableWrapper">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <thead>
                                <tr>
                                    <th colspan="2">
                                        <a href="javascript:;" class="toolTip" rel='You can provide other type of deliverable files.'></a>
                                        Other Type
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="tfooter">
                                    <td colspan="2">
                                        <div class="otherTypeAreaInputBox">
                                            <div class="leftPart">Extension:</div>
                                            <div class="rightPart">
                                                <span class="period">.</span>
                                                <div class="fileTypeWrapper">
                                                    <input type="text" class="text" id="fileType" />
                                                    <div class="errorText">This field cannot be left empty.</div>
                                                    <div class="errorIcon"></div>
                                                </div>
                                                <strong>Description:</strong>
                                                <div class="descriptionInputWrapper">
                                                    <input type="text" class="text" id="descriptionInput" />
                                                    <div class="errorText">This field cannot be left empty.</div>
                                                    <div class="errorIcon"></div>
                                                </div>
                                                <a href="javascript:;" class="button12 addFileType"><span class="btnR"><span class="btnC">Add More</span></span></a>
                                                
                                                <div class="clear"></div>
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
