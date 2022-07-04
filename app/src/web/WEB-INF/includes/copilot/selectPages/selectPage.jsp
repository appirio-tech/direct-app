<%--
  - Author: Ghost_141, TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - Fix multiple bugs.
  - 
  - Description: The select copilot step.
  - Since: Release Assembly - TC Direct Select From Copilot Pool Assembly
  - Version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- container -->
<div id="stepContainerSelect" style="display:none;">

    <!-- step title -->
    <div class="stepTitle">
        <h3 class="infoIcon">Copilot is Selected</h3>
       
    </div>
    <!-- End .stepTitle -->
    
    <!-- step -->
    <div class="stepBar">
    
        <ul>
            <li class="first"><span class="istatus inext"><span class="arrow"><span class="bg">Step 1</span></span></span></li>
            <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 2</span></span></span></li>
            <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
            <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>
            <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 5</span></span></span></li>
        </ul>
    
    </div>
    <!-- End .stepBar -->
    
    <div class="mainContent">
    
        <div class="rightColumn">
            <div class="title">
                    <div class="titleLeft"><div class="titleRight">
                        <h2>Video Tutorial</h2>
                    </div></div>
            </div><!-- End .title -->
            
            <!-- video wrapper --> 
            <object type="application/x-shockwave-flash" data="https://www.youtube.com/v/y4TjXnpgVow" width="327" height="208" style="margin:0 0 9px 0;">
                <param name="movie"  value="https://www.youtube.com/v/y4TjXnpgVow" />
                <param name="FlashVars" value="playerMode=embedded" />
            </object>
            
            <div class="grayTextArea">
                <p>
                    Copilot has its own payment rates are adjusted to the type of challenge and may change depending on the needs of the challenge.
                    <a href="https://apps.topcoder.com/wiki/display/tc/Copilot+Overview" class="blue">view rate</a>
                </p>

            </div>
            <!--End .grayTextArea-->
            
            <a href="https://www.topcoder.com/help/?page_id=15&subject=startproject&catIdx=5809" class="redText viewVideo"><strong>View All Videos</strong></a>
            
            <div class="grayTextAreaCustom">
                <h3>Selected Copilot</h3>
                <div class="copilotInfo">
                    <!-- img -->
                        <div class="copioltPic">
                            <img src="/images/copilot_img.png" alt="" />
                            <a href="javascript:;" class="blue">view profile</a>
                        </div>
                        <!-- End .projectPic -->
                        
                         <div class="infoArea">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <colgroup>
                                    <col width="50%" />
                                    <col width="50%" />
                                </colgroup>
                                <tr>
                                    <td class="firstLine" colspan="2">Handle : <a href="javascript:;" class="redText"><strong>AcmeName</strong></a></td>
                                    
                                </tr>
                                <tr>
                                    <td class="firstTd padding">Status: </td>
                                    <td class="lastTd padding"><strong class="greenText">Active</strong></td>
                                </tr>
                                <tr>
                                    <td class="firstTd">Fullfilment: </td>
                                    <td class="lastTd"><strong>95.45%</strong></td>
                                </tr>
                                <tr>
                                    <td class="firstTd">Reability:</td>
                                    <td class="lastTd"><strong>n/a</strong></td>
                                </tr>
                                
                            </table>
                            
                            <p>Current Working On</p>
                            <ul>
                                <li>
                                    <span>Number of Project: </span>
                                    <strong class="greenText">2</strong>
                                </li>
                                <li>
                                    <span>Number of Challenge: </span>
                                    <strong class="greenText">3</strong>
                                </li>
                            </ul>
                            
                         </div>
                        <!--End .infoArea-->
                    
                        <div class="clear"></div>
                </div>
                <span class="corner tl"></span>
                <span class="corner tr"></span>
                <span class="corner bl"></span>
                <span class="corner br"></span>
            </div>
            <!--End .grayTextAreaCustom-->
            
        </div><!-- End .rightColumn -->
        
        <div class="leftColumn">
            <div class="item">
                <div class="title">
                    <div class="titleLeft"><div class="titleRight">
                        <h2>Select a Project</h2>
                    </div></div>
                </div><!-- End .title -->
                <div class="rowItem projectSelection">
                    <div class="selectProject">
                        <label>Select a project: </label>
                
                        <select id="projectName" name="tcProject" class="projectName">
                            <option value="-1">Please select an existing project</option>
                            <s:iterator value="projects" var="proj">
                            <option value='<s:property value="projectId" />'  <s:if test="%{#proj.projectId==#session.currentProject.id}">selected="selected"</s:if> ><s:property value="name"/>
                            </option>
                            </s:iterator>
                        </select>   
                
                        <a href="javascript:;" class="red-button-big" id="addNewProjectBtn"><span class="right"><span class="middle">CREATE NEW</span></span></a>
                    </div>
                    <div class="clear"></div>
                    <!--End .rowItem-->
                </div>
            </div>
            
            <div class="item">
                <div class="title">
                    <div class="titleLeft"><div class="titleRight">
                        <h2>Copilot Overview</h2>
                    </div></div>
                </div><!-- End .title -->
                <p>
                    A Copilot is a TopCoder Member who manages the TopCoder process for a customer in order to deliver a requested asset. For example, a customer may ask to build a website. A Copilot will work with that customer to agree on a plan and pricing to build that website and then they would manage the process using the TopCoder Platform to deliver the website back to the customer.
                </p>
            </div>
            
            <div class="item">
                <div class="title">
                    <div class="titleLeft"><div class="titleRight">
                        <h2>Project Rule</h2>
                    </div></div>
                </div><!-- End .title -->
                <p>
                    Now you can cooperate with the copilot and discuss about project details directly, please click button below: 
                </p>
                
                <div class="buttonArea">
                    <a href="javascript:;" class="button2" id="hireCopilotBtn"> <span class="left"><span class="right">HIRE THE COPILOT</span></span></a>
                    
                </div>
                <!--End .buttonArea-->
            </div>
            
        </div><!-- End .leftColumn -->
        
        <div class="clear"></div>
    </div>
    <!--End .mainContent-->
    
</div>
<!-- End #stepContainerSelect -->