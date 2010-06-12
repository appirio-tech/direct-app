<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="launch" scope="request"/>
    <script type="text/javascript" src="/scripts/launch/entity.js?v=20"></script>	
    <script type="text/javascript" src="/scripts/launch/main.js?v=21"></script>	
    <script type="text/javascript" src="/scripts/launchcontest.js?v=24"></script>
    <script type="text/javascript" src="/scripts/launch/pages/review.js?v=22"></script>	
    <script type="text/javascript" src="/scripts/launch/pages/orderReview.js?v=21"></script>	
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">

                            <div class="currentPage">
                                <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                <strong>Launch New Contest</strong>
                            </div> <!-- End .currentPage -->

                            <div id="launchContestOut">
                            
                             <div id="contestSelectionPage" class="launchpage">                            
                               <jsp:include page="includes/launch/contestSelection.jsp"/>
                             </div>    
                             
                             <div id="overviewPage" class="launchpage hide">                            
                               <jsp:include page="includes/launch/overview.jsp"/>
                             </div>    
                             
                             <div id="reviewPage" class="launchpage hide">                            
                               <jsp:include page="includes/launch/review.jsp"/>
                             </div>                                
                             
                             <div id="orderReviewPage" class="launchpage hide">                            
                               <jsp:include page="includes/launch/orderReview.jsp"/>
                             </div>                                

                            </div>
                            <!-- end #launchContestOut -->


                    </div> <!-- End.area1Content -->
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

<!-- this area will contain the popups of this page -->
<div class="popups">
	  <!-- Add Project Dialog-->
		<div id="addProjectDialog" title="Create New Project" class="dialog-box hide">
			<div id="addProjectForm">
				<div class="fi">
					 <label for="projectName">Name:</label>
					 <input id="projectName" name="projectName" type="text" width="30" maxlength="255" />
				</div>
				<div class="fi">
					 <label for="projectDescription">Description:</label>
					 <textarea id="projectDescription" name="projectDescription" rows="5" cols="30" ></textarea>
				</div>
				<div class="popupButtons">
					<a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
					<a href="javascript:;" onclick="addNewProject();" class="button1"><span>Yes</span></a>
				</div>
			</div><!-- End #addProjectForm -->
			
			<div id="addProjectResult">
				<p></p>
				<div class="popupButtons">
					<a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Close</span></a>
				</div>
			</div><!-- End #addProjectResult -->			 
		</div><!-- End #addProjectDialog -->			
</div>
<!-- End .popups -->	

<div class="tooltipArea">
    <div id="contestLaunch1" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">
        
                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>                            
                         </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                
                <div class="tooltipContent" id="contestDescriptionTooltip">
                    <p>Contest Description</p>
                </div><!-- End .tooltipContent -->
                
            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<div class="tooltipArea">
    <div id="contestLaunch2" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">
        
                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>                           
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                
                <div class="tooltipContent">
                    <p>Milestone Prizes</p>
                </div><!-- End .tooltipContent -->
                
            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<div class="tooltipArea">
    <div id="contestLaunch3" class="tooltipContainer">
        <span class="arrow"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">
        
                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                            <a href="javascript:;" class="closeIco"></a>                            
                        </div><!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                
                <div class="tooltipContent">
                    <p>Final Deliverables</p>
                </div><!-- End .tooltipContent -->
                
            </div></div>
        </div></div></div>
    </div>
  <!-- End .tooltipContainer -->
</div>

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
