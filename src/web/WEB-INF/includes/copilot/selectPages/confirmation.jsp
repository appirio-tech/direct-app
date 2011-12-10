<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
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
        <a href="<s:url action='dashboardActive' namespace='/'/>"class="button4 backDashboardBtn">Back to Dashboard</a>
    </div>
    <!-- End .stepTitle -->
    
    <!-- step seven -->
    <div class="stepSeven stepContainer summary">
    
        <div class="geryContent">
            
           
            <!-- form -->
            <div class="form">
                
                <div class="noteContainer">
                    <p>
                    Your selected copilot has been inform about this oppurtunity. The copilot will contact you to confirm the message.
                   </p>
                </div>
                
                <div class="textArea">
                    <p>
                        Next you will be able to manage/review/adjust any copilots need via the project page. From there, after you accept any copilot rosters in your project, you can interact to them via any available communications channels that has been provided.
                    
                    </p>
                    <p>
                        Good luck with your project!
                    </p>
                </div>
            </div>
            <!-- End .form -->
          
            <!-- corner -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>
            
        </div>
        
        <div class="buttonBottom">
            <ul>
                <!--
                <li>
                    <a href="javascript:;" class="viewContest"><span>View Contest</span></a>
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