<%--
  - Author: Ghost_141, TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (ReRelease Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - Fix multiple bugs.
  - 
  - Description: The confirmation step of selecting copilot.
  - Since: Release Assembly - TC Direct Select From Copilot Pool Assembly
  - Version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- container -->
<div id="stepContainerConfirmation" style="display:none;">

    <!-- step title -->
    <div class="stepTitle">
        <h3 class="infoIcon">Confirmation</h3>
        <a href="<s:url action='dashboardActive' namespace='/'/>"class="button4 backDashboardBtn">BACK TO DASHBOARD</a>
    </div>
    <!-- End .stepTitle -->
    
    <!-- step seven -->
    <div class="stepSeven stepContainer summary">
    
        <div class="geryContent">
            
           
            <!-- form -->
            <div class="form" style="min-height:0">
                <div class="textArea">
                    <p>
                        <span id="confirm-copilotHandle"></span> has been added as the copilot to your project named <span id="confirm-projectName"></span>.
                    </p>
                    <p>
                        You will now be directed to your project, where you can communicate in your project forum with your copilot.
                    </p>
                </div>
            </div>
            <!-- End .form -->

        </div>
        
        <div class="buttonBottom">
            <ul>
                <!--
                <li>
                    <a href="javascript:;" class="viewContest"><span>View Challenge</span></a>
                </li>
                <li>
                    <a href="javascript:;" class="forumIcon"><span>Forum</span></a>
                </li>
                -->
            </ul>
            <div class="clear"></div>
        </div>
        <!--End .buttonBottom-->
    
    </div>
    <!-- End .stepSix -->
    
</div>
<!-- End #stepContainer -->