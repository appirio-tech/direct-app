<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the new create new presentation project step 4.
  -
  - Version 1.0
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newPPTProjectStep4" class="hide newProjectStep">
    <!-- step title -->
    <div class="stepTitle">
        <h3><span>4</span>Describe the purpose of your presentation.</h3>
        <a href="<s:url action="dashboardActive" namespace="/"/>" class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    
    <!-- step ppt forth -->
    <div class="stepForth stepContainer">
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
                     <dd class="first">In this step we are trying to get a general idea of what you want to
accomplish with your presentation.</dd>
                     <dd>A subsequent step will let you upload files containing your presentation
outline, notes, and any other content you may have.</dd>
                </dl>
            </div><!-- End .noteMask -->
            
            <!-- form -->
            <div class="form speciealForm">
                
                <div class="tableWrapper">
                    <div class="topNotice">
                        <div class="noticeRight">All fields marked with <span class="red">*</span> are mandatory</div>
                    </div>
                    
                    <div class="barTitle">What is the subject of your presentation?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->
                    
                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="subject notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->

                    <div class="barTitle">What is the overall message for your audience?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->

                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="overallMessage notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->

                    <div class="barTitle">What are the most important points you want to get across?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->

                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="importantPoints notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->

                    <div class="barTitle">
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

                    <div class="barTitle">Do you have any restrictions pertaining to the presentation graphics?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->

                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="restrictions notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->

                    <div class="barTitle">Are there any other special requirements we should know about?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->

                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="specialRequirements notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->
                    
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
    </div><!-- End .stepForth -->
</div>