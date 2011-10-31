<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: Launch copilot selection contest page.
  - Since: TC Cockpit Post a Copilot Assembly 1
  - Version 1.0 (TC Cockpit Post a Copilot Assembly 1).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <c:set var="CURRENT_TAB" value="launchCopilot" scope="request"/>
    
    <link rel="stylesheet" href="/css/modal.css?v=207790" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/newProject.css?v=207894" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/get-a-copilot.css?v=208132" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/jquery.jcarousel.css?v=176771" media="all" type="text/css" />

    <script type="text/javascript" src="/scripts/newProject.js?v=207894"></script>
    <script type="text/javascript" src="/scripts/get-a-copilot.js?v=208132"></script> 
    <script type="text/javascript" src="/scripts/jquery.jcarousel.pack.js?v=176771"></script>   
    <script type="text/javascript" src="/scripts/jquery.tools.min.js?v=192105"></script> 
    <script type="text/javascript" src="/scripts/launch/entity.js?v=209174"></script>
    <script type="text/javascript" src="/scripts/launch/main.js?v=209174"></script>
    <script type="text/javascript" src="/scripts/launchCopilotContest.js?v=209239"></script>
    
</head>

<body id="page">
    <div id="wrapper" class="stepPage get-a-copilot">
        <div id="wrapperInner">
            <div id="container">
                <div id="content">

                    <jsp:include page="includes/header.jsp"/>

                    <div id="mainContent" style="overflow:visible">
                        <div id="area1"><!-- the main area -->

						<!-- the main area -->
						<div class="area1Content">
							<div class="currentPage"> 
                                <a href="/direct/dashboardActive.action" class="home">Dashboard</a>
                                &gt; 
                                <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a>
                                &gt; <strong> Post a Copilot</strong> 
                            </div>
							 
                            <!-- container -->
                            <div id="stepContainer">
                            
                                <!-- step title -->
                                <div class="stepTitle">
                                    <h3><span class="small">1</span>Basic</h3>
                                   
                                </div>
                                <!-- End .stepTitle -->
								
								<!-- step -->
								<div class="stepBar">
								
									<ul>
										<li class="first"><span class="active istatus"><span class="arrow">
                                            <span class="bg">
                                                <a href="javascript:;" id="stepLink_1">Step 1</a>
                                                <span class="stepText">Step 1</span>
                                            </span>
                                        </span></span></li>
                                        
										<li><span class="istatus inext"><span class="arrow">
                                            <span class="bg">
                                                <a href="javascript:;" id="stepLink_2">Step 2</a>
                                                <span class="stepText">Step 2</span>
                                            </span>
                                        </span></span></li>
                                        
										<li><span class="istatus inext"><span class="arrow">
                                            <span class="bg">
                                                <a href="javascript:;" id="stepLink_3">Step 3</a>
                                                <span class="stepText">Step 3</span>
                                            </span>
                                        </span></span></li>
                                        
										<li><span class="istatus inext"><span class="arrow">
                                            <span class="bg">
                                                <a href="javascript:;" id="stepLink_4">Step 4</a>
                                                <span class="stepText">Step 4</span>
                                            </span>
                                        </span></span></li>
                                        
										<li class="last"><span class="istatus inext"><span class="arrow">
                                            <span class="bg">
                                                <a href="javascript:;" id="stepLink_5">Step 5</a>
                                                <span class="stepText">Step 5</span>
                                            </span>
                                        </span></span></li>
									</ul>
								
								</div>
								<!-- End .stepBar -->
								
								<!--step buttons-->
                                <div class="stepNav">
									<a id="saveDraftButton" href="javascript:;" class="button6 draft draftBtn"><span class="left"><span class="right">SAVE AS DRAFT</span></span></a>
                                    <a id="submitButton" href="javascript:;" class="button6 smallRed submitButton"><span class="left"><span class="right">SUBMIT</span></span></a>
									<a href="/direct/dashboardActive.action" class="button4 backDashboardBtn">Back to Dashboard</a>
									<div class="clear"></div>
								</div>
								<!--End .stepNav-->
								
                                <!-- step first -->
                                <div class="stepFirst stepContainer">
                                
                                	<div class="geryContent">
                                    	
                                        <!-- top bar -->
                                    	<div class="topBar">
                                            <a href="javascript:;" class="prevStepButton">PREV STEP</a>
                                        	<a href="javascript:;" class="nextStepButton">NEXT STEP</a>
                                        </div>
                                    	<!-- End .topBar -->
                                        
                                        <jsp:include page="includes/copilot/steps/step1.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step2.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step3.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step4.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step5.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step6.jsp"/>
                                        <jsp:include page="includes/copilot/steps/step7.jsp"/>
                                        
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
                                    
                                    <div class="buttonBottom">
                                        <ul>
                                            <li>
                                                <a href="javascript:;" class="viewContest" id="previewButton"><span>View Contest</span></a>
                                            </li>
                                            <li>
                                                <a href="javascript:;" class="viewProjectOverview" id="projectOverviewButton"><span>Project Overview</span></a>
                                            </li>
                                            <li>
                                                <a href="javascript:;" class="forumIcon" id="forumButton"><span>Forum</span></a>
                                            </li>
                                        </ul>
                                        <div class="clear"></div>
                                    </div>
                                    <!--End .buttonBottom-->                                     
                                
                                </div>
                                <!-- End .stepFirst -->
                                
                            </div>
                            <!-- End #stepContainer -->
                            
						</div>                     
                        
                        
                        
                        </div> <!-- End #area1 -->

                    </div>
                    <!-- End #mainContent -->

                    <jsp:include page="includes/footer.jsp"/>

                </div>
                <!-- End #content -->
            </div>
            <!-- End #container -->
        </div>
    </div>
    <!-- End #wrapper -->

<!-- modal -->
<div id="modalBackground"></div>
<div id="new-modal">
	<div id="errortModal" class="outLay smallModal">
        
        <div class="modalTop"><div class="modalBottom"><div class="modalBg">
            <div class="inner">
                <!-- title -->
                <div class="modal-title">
                    <h2>ERRORS</h2>
                    <a href="javascript:;" class="closeModal">Close</a> 
                </div>
                <!-- End .modal-title --> 
                <!-- content -->
                <div class="modal-content">
                	<div class="modalContainer">
                        <div class="errorIcon"><img src="/images/errorIcon.png" alt="" /></div>
                        <p>Please select your budget options for this contest.</p>
                        <dl>
                            <dd>Project name could not be empty.</dd>
                            <dd>Copilot Opportunity Title could not be empty.</dd>
							<dd>Public Description could not be empty.</dd>
                            <dd>Specific Description could not be empty.</dd>
                        </dl>                        
    				</div>
                    <div class="clear"></div>
                    <div class="buttonArea"> 
                        <a href="javascript:;" title="OK" class="closebutton button6"><span class="left"><span class="right">OK</span></span></a>
                    </div>
                </div>
                <!-- End .content --> 
            </div>
        </div></div></div>
    </div>
	 <div class="outLay" id="preloaderModal" style="display: none;">
            <div class="modalHeaderSmall">
                <div class="modalHeaderSmallRight">
                    <div class="modalHeaderSmallCenter"></div>
                </div>
            </div>
            <div class="modalBody">
                <span id="preloaderAnimation">
                <img alt="Loading" src="/images/preloader-loading.gif">
                </span>
                <div class="preloaderTips">Loading...</div>
            </div>
            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter">
                        <div class="&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- #addNewProjectModal -->
        <div id="addNewProjectModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        CREATE NEW PROJECT
                        <a href="javascript:;" class="closeModal closeProjectModal" title="Close" onclick="modalCloseAddNewProject();">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Name:</label>
                        <input type="text" class="text" id="newProjectName" name="newProjectName"/>

                        <div class="clearFix"></div>
                    </div>
                    <div class="modalRow">
                        <label>Description:</label>
                        <textarea id="newProjectDescription" name="newProjectDescription" class="textField" rows=""
                                  cols=""></textarea>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1"><span class="btnR"><span class="btnC"
                                                                                       onclick="addNewProject();">YES</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal closeProjectModal"
                       onclick="modalCloseAddNewProject();"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
                </div>
            </div>
            <!-- end .modalBody -->

            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter"></div>
                </div>
            </div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #addNewProjectModal -->
</div>	
    
</body>
<!-- End #page -->

</html>
