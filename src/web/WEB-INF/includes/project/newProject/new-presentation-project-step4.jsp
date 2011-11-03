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
        <h3><span>4</span>Presentation Style</h3>
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
                     <dd class="first">You can select one of the listed presentation styles as the preferred style for your presentation.</dd>
                     <dd>We will design your presentation slides based on your presentation style selection.</dd>
                     <dd>You can also specify the length of your presentation slides and provide additional notes about the presentation style.</dd>
                </dl>
            </div><!-- End .noteMask -->
            
            <!-- form -->
            <div class="form speciealForm">
                
                <div class="tableWrapper">
                    <div class="topNotice">
                        <div class="noticeRight">All fields marked with <span class="red">*</span> are mandatory</div>
                    </div>
                    
                    <div class="barTitle">
                        <a href="javascript:;" class="toolTip" rel='Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'></a>
                        Which are your preferred style for the presentation?<span class="red">*</span>
                        <span class="errorStatusTips">No presentation style selected</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->
                    
                    <div class="styleSelectList">
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Minimalist Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-1.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Edgy Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-2.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Corporate Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-3.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Futuristic Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-4.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Playful Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-5.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <!--.styleItem-->
                        <div class="styleItem">
                            <div class="radioBox">
                                <input type="radio" name="styleSelectRadio" class="radio" /><label>Other Style</label>
                            </div>
                            <div class="styleContainer">
                                <div class="boxHeader"><div class="boxHeaderR"><div class="boxHeaderC"></div></div></div>
                                <div class="boxBody">
                                    <div class="boxBodyR">
                                        <div class="boxInner">
                                            <img src="/images/style-image-6.png" alt="style" />
                                        </div>
                                    </div>
                                </div>
                                <div class="boxFooter"><div class="boxFooterR"><div class="boxFooterC"></div></div></div>
                            </div>
                        </div>
                        <!--end .styleItem-->
                        
                        <div class="clear"></div>
                    </div>
                    
                    <div class="barTitle">
                        <a href="javascript:;" class="toolTip" rel='Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'></a>
                        Any notes on presentation style?<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->
                    
                    <!-- row -->
                    <div class="row">
                        <div class="textFieldWrapper">
                            <div class="errorText">This field cannot be left empty.</div>
                            <div class="errorIcon"></div>
                            <textarea rows="10" cols="10" class="notePresentation"></textarea>
                        </div>
                    </div>
                    <!-- End .row -->
                    
                    
                    <div class="barTitle">
                        <a href="javascript:;" class="toolTip" id="toolTip4" rel='Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'></a>
                        Page Length<span class="red">*</span>
                        <div class="clear"></div>
                    </div>
                    <!-- .barTitle -->
                    
                    <!-- row -->
                    <div class="row">
                        <div class="pageLengthSetArea">
                            <div class="part">
                                <input type="radio" name="pageLengthSetting" checked="checked" />
                                <label>Small (1-5 pages)</label>
                            </div>
                            <div class="part">
                                <input type="radio" name="pageLengthSetting" />
                                <label>Medium (5-10 pages)</label>
                            </div>
                            <div class="clear"></div>
                            <div class="rightPart">
                                <input type="radio" name="pageLengthSetting" />
                                <label>Large (10-20 pages)</label>
                                <div class="clear"></div>
                            </div>
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