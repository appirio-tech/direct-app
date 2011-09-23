<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The step 2 of post a copilot.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.0 (TC Cockpit Post a Copilot Assembly 1).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="postCopilotStep2 stepDiv">
    <!-- form -->
    <div class="form">
        <div class="noteContainer">
            <p>
            In this step you will be directed to select the skills and experience needed by a copilot to work on your project, based on the type of your project. Please fill out the form below: 
           </p>
        </div>
        
        <!-- row -->
        <div class="row">
            <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
            <div class="clear"></div>
            
            <div class="title"><!-- columns title -->
                <div class="titleLeft"><div class="titleRight">
                    <h2>Managing Project Experience</h2>
                </div></div>
            </div><!-- End .title -->
            
            <div class="rowItem">
                <label>I prefer that the copilot has previously managed the following type(s) of projects<span class="red">*</span></label>
                <div class="checkType">
                    <span>Please check the type of projects that meet your requirement :</span>
                    
                    <div class="normal">
                        <ul class="type1">
                            <li>
                                <input type="checkbox" id="Graphic" />
                                <label for="Graphic">Design Graphic</label>
                            </li>
                            <li>
                                <input type="checkbox" id="Animation"  />
                                <label for="Animation">Animation</label>
                            </li>
                            <li>
                                <input type="checkbox" id="Mobile"  />
                                <label for="Mobile">Mobile</label>
                            </li>
                            <li>
                                <input type="checkbox" id="WebApplications"  />
                                <label for="WebApplications">Small Scale Web Applications</label>
                            </li>
                            <li>
                                <input type="checkbox" id="LargeScale"  />
                                <label for="LargeScale">Large Scale Web Applications</label>
                            </li>
                        </ul>
                        <ul class="type3">
                            <li>
                                <input type="checkbox" id="Applications"  />
                                <label for="Applications">Desktop Applications</label>
                            </li>
                            
                            <li>
                                <input type="checkbox" id="Transactional"  />
                                <label for="Transactional">High Transactional Systems</label>
                            </li>
                            
                            <li>
                                <input type="checkbox" id="Warehousing"   />
                                <label for="Warehousing">Data Warehousing</label>
                            </li>
                            
                            <li>
                                <input type="checkbox" id="Campaigns"  />
                                <label for="Campaigns">Marketing Campaigns</label>
                            </li>
                        </ul>
                        <div class="clear"></div>
                        
                        <label class="other">Other:</label>
                        <input type="text" class="text" />
                        
                    </div>
                    
                    
                    
                    <div class="clear"></div>
                </div>
                <!--End .checkType-->
                
            </div>
            <!--End .rowItem-->
            
        </div>
        <!-- End .row -->
    </div>
    <!-- End .form -->    
</div>