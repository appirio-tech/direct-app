<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the 4th step of mobile project creation.
  -
  - Version 1.0 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT Projects Flow)
--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="newSharedProjectStep4" class="hide newProjectStep">

    <!-- step title -->
    <div class="stepTitle">
        <h3><span>4</span>Detailed specifications</h3>
        <a href='<s:url action="dashboardActive" namespace="/"/>' class="button4">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->

    <!-- step first -->
    <div class="stepFourth2 stepContainer2">

        <div class="geryContent">

            <!-- top bar -->
            <div class="topBar">
                <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                <a href="javascript:;" class="nextStepButton">NEXT STEP</a>
            </div>
            <!-- End .topBar -->

            <div class="top">
                <div class="filter">
                    <div class="row first">
                        <a href="javascript:;" class="toolTip" rel="Demo text"></a>
                        <span class="label">Type:</span>
                        <label class="first"><input checked="checked" type="radio" name="type"/>Content outline</label>
                        <label><input type="radio" name="type"/>Design document</label>
                        <label><input type="radio" name="type"/>Data document</label>
                        <label><input type="radio" name="type"/>Free-form comments</label>
                        <label><input type="radio" name="type" value="other"/></label>
                        <div class="inputCnt">
                            <input name="other" disabled="disabled" type="text" class="waterMark text disable"
                                   title="other"value="other" />
                            <p class="message2 padding">
                                <span class="errorText">This field cannot be left empty.</span>
                            </p>
                            <span class="errorIcon"></span>
                        </div>

                        <div class="clear"></div>
                    </div>
                    <div class="row">
                        <a href="javascript:;" class="toolTip" rel="Demo text"></a>
                        <span class="label">Source:</span>
                        <label class="first"><input value="upload" checked="checked" type="radio" name="source"/>Upload</label>
                        <label><input value="url" type="radio" name="source"/>Remote file (URL)</label>
                        <label><input value="source" type="radio" name="source"/>Direct entry </label>

                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <!--input content-->
            <div class="tableContainer">
                <div class="inputs">
                    <div class="top">
                    
                        <div class="row url">
                            <input type="text" class="text url" name="http://" value="http://"/>
                            <p class="message padding">
                                <span class="errorText">This field cannot be left empty.</span>
                            </p>
                        </div>
                        
                        <div class="row upload">
                            <input type="text" class="text textUploadPhoto" readonly="readonly"/>

                            <div class="browserWrapper">
                                <input type="file" class="file" name="document"/>
                                <div class="btn"><a href="javascript:">CHOOSE...</a><span>&nbsp;</span></div>
                            </div>
                            <div class="clear"></div>
                            <p class="message padding">
                                <span class="errorText">This field cannot be left empty.</span>
                            </p>
                        </div>
                        
                        <div class="row source">
                            <textarea rows="" cols=""></textarea>
                            <p class="message padding">
                                <span class="errorText">This field cannot be left empty.</span>
                            </p>
                        </div>
                        
                        <div class="last">
                            <div class=" button">
                                <a class="addBtn" href="javascript:">ADD A NEW ITEM</a>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!--table-->
                <table class="addedItem" cellpadding="0" cellspacing="0">
                    <colgroup>
                        <col width="185px"/>
                        <col width="175px"/>
                        <col width="520px"/>
                        <col width="82px"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="first">Type</th>
                        <th>Source</th>
                        <th>Data</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="hide">
                        <td colspan="4"></td>
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
