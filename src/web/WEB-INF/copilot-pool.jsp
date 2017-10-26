<%--
  - Author: Ghost_141, TCSASSEMBLER
  - Version: 1.2
  - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0)
  - Fix multiple bugs.
  -
  - Version 1.2 (TC Direct Rebranding Assembly Copilot and Reporting related pages)
  - - Rebranding the copilot and reporting related pages.
  - 
  - Description: The page for selecting copilot.
  - Since: Release Assembly - TC Direct Select From Copilot Pool Assembly
  - Version 1.0 (Release Assembly - TC Direct Select From Copilot Pool Assembly).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <c:set var="CURRENT_TAB" value="launchCopilot" scope="request"/>
    
    <link rel="stylesheet" href="/css/direct/modal.css?v=211772" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/newProject.css?v=210944" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/direct/get-a-copilot.css?v=215576" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/direct/jquery.jcarousel.css?v=176771" media="all" type="text/css" />
    
    <script type="text/javascript" src="/scripts/jquery.pagination.js?v=210944"></script>
    <script type="text/javascript" src="/scripts/jquery.dataTables.min.js?v=178111"></script>
    <script type="text/javascript" src="/scripts/jquery.tinysort.min.js?v=210944"></script>
	<script type="text/javascript" src="/scripts/jquery.carouFredSel.js?v=213033"></script>
    <script type="text/javascript" src="/scripts/newProject.js?v=209631"></script>
    <script type="text/javascript" src="/scripts/get-a-copilot.js?v=215576"></script> 
    <script type="text/javascript" src="/scripts/select-a-copilot.js?v=211902"></script> 
    
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
                            <div id="stepContainerView">
                            
                                <!-- step title -->
                                <div class="stepTitle">
                                    <h3>Copilot Pool</h3>
                                    <a href="<s:url action='dashboardActive' namespace='/'/>"class="button4 backDashboardBtn">BACK TO DASHBOARD</a>
                                </div>
                                <!-- End .stepTitle -->
								
								<!-- step seven -->
                                <div class="stepSeven stepContainer copilotPool copilotList">
                                	<div class="geryContent">
                                        <!-- form -->
                                        <div class="form">
                                        	
											<!-- row -->
                                            <div class="row">
												<div class="title"><!-- columns title -->
													<div class="titleLeft"><div class="titleRight">
														
														<ul id="layout">
															<li><a href="javascript:;" class="grid" id="switchGridViewLink">Switch to Grid View </a></li>
															<li><a href="javascript:;" class="list" id="switchListViewLink">Switch to List View</a></li>
														</ul>
														
														<ul id="display">
															<li>
																<label>Sort by:</label>
																<select id="sortSelect">
																	<option value="userHandleInput">Copilot's Name</option>
                                                                    
                                                                    <option value="totalProjectsInput">No. of Total Projects #</option>
																	<option value="totalContestsInput">No. of Total Challenges #</option>
																	<option value="currentProjectsInput">No. of Current Projects #</option>
																	<option value="currentContestsInput">No. of Current Challenges #</option>
                                                                    
                                                                    <option value="repostContestsInput">No. of Repost Challenges #</option>
                                                                    <option value="failureContestsInput">No. of Failure Challenges #</option>
                                                                  
																</select>
															</li>
														</ul>
														
													</div></div>
												</div><!-- End .title -->
                                            </div>
                                            <!-- End .row -->
                                            
                                            <jsp:include page="includes/copilot/selectPages/gridPage.jsp"/>
                                        </div>
                                        <!-- End .form -->
										
                                        <jsp:include page="includes/copilot/selectPages/listPage.jsp"/>
                                 
										<!-- bottom bar -->
                                    	<div class="BottomBar" id="copilotGridBottomBar">
                                            <div class="pageArea" id="pagingDiv"></div>
                                        </div>
                                    	<!-- End .BottomBar -->

                                    </div>
									
                                </div>
                                <!-- End .stepSix -->
                                
                            </div>
                            <!-- End #stepContainerView -->
                            
                            <jsp:include page="includes/copilot/selectPages/selectPage.jsp"/>
                            <jsp:include page="includes/copilot/selectPages/confirmation.jsp"/>
                            
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
                    <h2>Errors</h2>
                    <a href="javascript:;" class="closeModal">Close</a> 
                </div>
                <!-- End .modal-title --> 
                <!-- content -->
                <div class="modal-content">
                	<div class="modalContainer">
                        <div class="errorIcon"><img src="/images/errorIcon.png" alt="" /></div>
                        <p>Please select your budget options for this challenge.</p>
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
                    Create New Project
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
                                                                                   onclick="addNewProject();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CREATE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></a>
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
    
	<div id="copilotPopUp" class="outLay">
        <div class="modalTop"><div class="modalBottom"><div class="modalBg">
            <div class="inner">
                
               <h2>Selected Copilot</h2>
                <!-- content -->
                <div class="modal-content">
                	<div class="copilotInfo">
                        <div class="clear"></div>
					</div>
					
					<div class="buttonArea">
						<a href="javascript:;" title="SUBMIT" class="submitBtn button6"><span class="left"><span class="right">SUBMIT</span></span></a>
						<a href="javascript:;" title="CANCEL" class="closebutton button6"><span class="left"><span class="right">CANCEL</span></span></a>
					</div>
                </div>
                <!-- End .content --> 
            </div>
        </div></div></div>
    </div>    
</div>	
    
</body>
<!-- End #page -->

</html>
