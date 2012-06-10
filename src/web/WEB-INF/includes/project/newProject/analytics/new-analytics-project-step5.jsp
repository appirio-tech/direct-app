<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 5th step of analytics project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Analytics Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newAnalyticsProjectStep5" class="hide analytics newProjectStep analyticsSteps">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>5</span>Other details and documents</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    <div class="stepContainer step6Container">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->
               <p class="intro">All fields marked with <span class="red">*</span> are mandatory</p>

                <div class="step6">
                    <div class="otherDetails">
                        <div class="title">
                            <a rel="&lt;p&gt; Please list all other details that are not covered by any questions in the previous steps.&lt;/p&gt;" class="toolTip" href="javascript:;"></a>
                            <label>If there are any other details that you think are relevant but are not covered by any questions in the previous steps, please list them here.<span class="red">*</span></label>
                        </div>
                        <textarea cols="10" rows="10" autocomplete="off"></textarea>
                        <span class="errorIcon"></span>
                        <p class="errorTxt">This field cannot be left empty.</p>
                    </div>
                    <div class="documents">
                        <div class="radios">
                            <a rel="&lt;p&gt; Please choose the document source type and provide the document or its URL/Direct Entry.&lt;/p&gt;"  class="toolTip" href="javascript:;"></a>
                            <input type="radio" name="document" id="isUpload" checked="checked" value="isUpload" />
                            <label for="isUpload">Upload</label>
                            <input type="radio" name="document" id="isUrl"  value="isUrl"/>
                            <label for="isUrl">Remote file (URL)</label>
                            <input type="radio" name="document" id="isDirect" value="isDirect" />
                            <label for="isDirect">Direct entry</label>
                        </div>
                        
                        <div class="isUpload inputRow">
                            <input type="text" class="text textUploadPhoto" readonly="readonly" autocomplete="off"/>

                            <div class="browserWrapper">
                                <input type="file" class="file" name="document" style="opacity:0" autocomplete="off"/>
                                <div class="btn"><a href="javascript:">CHOOSE...</a><span>&nbsp;</span></div>
                            </div>
                        </div>
                        <div class="isUrl inputRow hide">
                            <input type="text" class="text" name="http://" value="http://" autocomplete="off"/> 
                        </div>
                        <div class="isDirect inputRow hide">
                            <textarea cols="10" rows="10" class="text" autocomplete="off"></textarea>
                        </div>
                        <div class="action">
                            <p class="errorTxt">This field cannot be left empty.</p>
                            <a class="addBtn" href="javascript:">ADD A NEW ITEM</a>
                        </div>
                    </div>
                    <table cellpadding="0" cellspacing="0" style="display:none">
                            <colgroup>
                                <col width="15%" />
                                <col width="75%" />
                                <col width="10%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th class="first">Source</th>
                                    <th>Data</th>
                                    <th class="last">&nbsp;</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="first">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td class="last">&nbsp;</td>
                                </tr>
                            </tbody>
                        </table>
                </div>
                                        
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