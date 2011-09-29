<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 6 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.0 (TC Cockpit Post a Copilot Assembly 1).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep6 stepDiv">
    <!-- form -->
    <div class="form">
        
        <!-- row -->
        <div class="row">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep1"></a>
                    <h2>Project Details</h2>
                    
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
            
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Project Name:</label></td>
                        <td><span id="projectNameSummary"></span></td>
                    </tr>
                </table>
                
                <div class="clear"></div>
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row">
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep1"></a>
                    <h2>Copilot Opportunity Details</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Copilot Opportunity Title:</label></td>
                        <td><span id="contestNameSummary"></span></td>
                    </tr>
                    <tr>
                        <td><label>Public Description:</label></td>
                        <td><span id="publicDescriptionSummary"></span></td>
                    </tr>
                    <tr>
                        <td><label>Specific Description:</label></td>
                        <td><span id="specificDescriptionSummary"></span></td>
                    </tr>
                </table>
                
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row">
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep1"></a>
                    <h2>Document Upload</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Uploaded Documents:</label></td>
                        <td>
                            <ul id="uploadFilesSummary">
                            </ul>
                        </td>
                    </tr>
                    
                </table>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row hide">
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep2"></a>
                    <h2>Managing Project Experiences</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Type of projects:</label></td>
                        <td>
                            <!-- left to next assembly -->
                            <span>Design Graphic, Large scale web applications</span>
                        </td>
                    </tr>
                    
                </table>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row hide">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep3"></a>
                    <h2>Budget Information</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Budget:</label></td>
                        <td>
                            <!-- left to next assembly -->
                            <span>30.000 US</span>
                        </td>
                    </tr>
                    
                </table>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep4"></a>
                    <h2>Project Schedule</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Action:</label></td>
                        <td>
                            <span id="startTimeSummary"></span>
                        </td>
                    </tr>
                    <tr>
                        <td><label>1ST place:</label></td>
                        <td>
                            <span id="amountSummaryFirst"></span>
                        </td>
                    </tr>
					<tr>
                        <td><label>2ND place:</label></td>
                        <td>
                            <span id="amountSummarySecond"></span>
                        </td>
                    </tr>
					<tr>
                        <td><label>Total:</label></td>
                        <td>
                            <span id="amountSummaryTotal"></span>
                        </td>
                    </tr>
                    
                </table>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
        
        <!-- row -->
        <div class="row">
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <a href="javascript:;" class="edit" name="editStep5"></a>
                    <h2>Billing</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>Billing Account:</label></td>
                        <td>
                            <span id="billingSummary"></span>
                        </td>
                    </tr>
                    
                </table>
            </div>
            <!--End .rowItem-->
        </div>
        <!-- End .row -->
        
    </div>
    <!-- End .form -->
</div>