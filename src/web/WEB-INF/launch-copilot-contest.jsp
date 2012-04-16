<%--
  - Author: tangzx, duxiaoyang
  - Version: 1.3
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: provide two ways to select a copilot.
  - Since: TC Direct - Launch Copilot Selection Contest assembly
  -
  - Version 1.1 (TC Direct Manage Copilots Assembly) changes: set CURRENT_TAB parameter.
  -
  - Version 1.2 (TC Cockpit Post a Copilot Assembly 1) changes: apply to new prototype.
  - Changes in version 1.3 (TC Cockpit Post a Copilot Assembly 2):
  - Remove "Select from Copilot Pool" button.
  -
  - Version 1.3 (Release Assembly - TC Direct Select From Copilot Pool Assembly) changes:
  - Use the really copilot profile data.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <c:set var="CURRENT_TAB" value="launchCopilot" scope="request"/>
    
    <link rel="stylesheet" href="/css/modal.css?v=211772" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/newProject.css?v=210944" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/get-a-copilot.css?v=215325" media="all" type="text/css"/> 

    <script type="text/javascript" src="/scripts/newProject.js?v=209631"></script>
    <script type="text/javascript" src="/scripts/jquery.carouFredSel.js?v=213033"></script>
    <script type="text/javascript" src="/scripts/get-a-copilot.js?v=215325"></script> 
    
</head>

<body id="page">
    <div id="wrapper" class="stepPage get-a-copilot">
        <div id="wrapperInner">
            <div id="container">
                <div id="content">

                    <jsp:include page="includes/header.jsp"/>

                    <div id="mainContent">
                        <div id="area1"><!-- the main area -->

                            <!-- the main area -->
                            <div class="area1Content">
                                <div class="currentPage"> 
                                    <a href="/direct/dashboardActive.action" class="home">Dashboard</a>
                                    &gt;
                                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a>
                                    &gt;<strong> Introduction Get a Copilot</strong> </div>
                                 
                                <!-- container -->
                                <div id="stepContainer">
                                
                                    <!-- step title -->
                                    <div class="stepTitle">
                                        <h3 class="infoIcon">Introduction</h3>
                                       
                                    </div>
                                    <!-- End .stepTitle -->

                                    <div class="mainContent">
                                    
                                        <div class="rightColumn">
                                            <div class="title">
                                                    <div class="titleLeft"><div class="titleRight">
                                                        <h2>Video Tutorial</h2>
                                                    </div></div>
                                            </div><!-- End .title -->
                                            
                                            <!-- video wrapper --> 
                                            <div class="videoWrap">
                                                <object type="application/x-shockwave-flash" data="http://www.youtube.com/v/wUVMK6EhQaU" width="327" height="208">
                                                    <param name="movie"  value="http://www.youtube.com/v/wUVMK6EhQaU" />
                                                    <param name="FlashVars" value="playerMode=embedded" />
                                                </object>
                                            </div>
                                            
                                            <div class="grayTextArea">
                                                <p>
                                                    Copilots are paid per contest based on outcomes, not hours.
                                                    <a href="http://apps.topcoder.com/wiki/display/tc/Copilot+Overview" class="blue">View More</a>
                                                </p>
                                                
                                                <span class="corner tl"></span>
                                                <span class="corner tr"></span>
                                                <span class="corner bl"></span>
                                                <span class="corner br"></span>
                                            </div>
                                            <!--End .grayTextArea-->
                                            
                                            <a href="javascript:;" title="View All Videos" class="redText viewVideo"><strong>View All Videos</strong></a>
                                            
                                            <div class="grayTextAreaCustom">
                                                <p>
                                                    <strong>It is important to note</strong> that the Copilot is responsible for running contests, but more importantly they are responsible for delivering the "product" to the customer. Many customers will not want to know the details of what contests you are running. In fact, the concept of a contest will not be important to them. They just want their product completed. So, it is the Copilot's responsibility to work with the customer at an appropriate level of detail.
                                                </p>
                                                
                                                <span class="corner tl"></span>
                                                <span class="corner tr"></span>
                                                <span class="corner bl"></span>
                                                <span class="corner br"></span>
                                            </div>
                                            <!--End .grayTextAreaCustom-->
                                            
                                        </div><!-- End .rightColumn -->
                                        
                                        <div class="leftColumn">
                                            <div class="copilot-carousel">
                                                <span class="corner tl"></span>
                                                <span class="corner tr"></span>
                                                <div class="carousel-content" id="copilotProfileCarouselDiv">
                                                    <c:set var="serverName" value="<%=ApplicationServer.SERVER_NAME%>"/>
                                                    <c:forEach var="profile" items="${profiles}" varStatus="varStatus">
                                                        <div class="copilot-details <c:if test="${varStatus.index gt 0}">hide</c:if>" id="copilot-${varStatus.index}">
                                                        <c:set var="profileLink" value="http://${serverName}/tc?module=ViewCopilotProfile&pid=${profile.member.copilotProfile.userId}\" target=\"_blank"/>
                                                            <img src="${profile.photo.photoPath}" alt="Copilot" />
                                                            <div class="main-content">
                                                                <h2><tc-webtag:handle coderId="${profile.member.copilotProfile.userId}" link="${profileLink}"/></h2>
                                                                <div class="clear"></div>
                                                                <div class="colLeft">
                                                                    <div class="row first"></div>
                                                                    <div class="row">
                                                                        <label>Projects:</label><span class="field_${profile.member.copilotProfile.userId}_totalProjects">loading</span>
                                                                    </div>
                                                                    <div class="row">
                                                                        <label>Contests:</label><span class="field_${profile.member.copilotProfile.userId}_totalContests">loading</span>
                                                                    </div>
                                                                    <div class="row">
                                                                        <label>Reposts:</label><span class="field_${profile.member.copilotProfile.userId}_totalRepostedContests">loading</span>
                                                                    </div>
                                                                    <div class="row">
                                                                        <label>Failures:</label><span class="field_${profile.member.copilotProfile.userId}_totalFailedContests">loading</span>
                                                                    </div>
                                                                    <div class="row">
                                                                        <label>Bug Races:</label><span class="field_${profile.member.copilotProfile.userId}_totalBugRaces">loading</span>
                                                                    </div>
                                                                </div>
                                                                <!-- /.colLeft -->
                                                                <div class="colRight">
                                                                    <div class="row first"></div>
                                                                    <div class="row">
                                                                        <label>Current Projects:</label><span class="field_${profile.member.copilotProfile.userId}_currentProjects">loading</span>
                                                                    </div>
                                                                    <div class="row">
                                                                        <label>Current Contests:</label><span class="field_${profile.member.copilotProfile.userId}_currentContests">loading</span>
                                                                    </div>


                                                                    <div class="buttonArea">
                                                                        <a href="${profileLink}" class="btnRed"> <span class="right"> <span class="middle">View Profile</span> </span> </a>
                                                                    </div>
                                                                </div>
                                                                <!-- /.colRight -->
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <div class="carouselNav">
														<div class="carouselNavContent">
															<div class="navBack"></div>
															<div class="navThumb">
																<ul class="ad-thumb-list">
                                                                    <c:forEach var="profile" items="${profiles}" varStatus="varStatus">
																	<li <c:if test="${varStatus.index eq 0}">class="current"</c:if>><a href="javascript:;"> <img src="${profile.photo.photoPath}" alt="thumb" /> </a></li>
                                                                    </c:forEach>
																</ul>
															</div>
															<div class="navForward"></div>
														</div>
													</div>
													<!-- /.carouselNav -->
                                                </div>
                                                <div class="foot-bar">
                                                    <span class="footCorner bl"></span> <span class="footCorner br"></span> <span class="currentThumb"><strong>1</strong> / ${fn:length(profiles)} </span>
                                                </div>
                                                <!-- .foot-bar -->
                                                
                                            </div>
                                            <!--End .copilot-carousel-->
                                            <a href="<s:url action='selectFromCopilotPool' namespace='/copilot'/>" class="redText"><strong>View All Copilots</strong></a>
                                            
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
                                               
                                                <div class="buttonArea">
                                                    <a href="<s:url action='postACopilot' namespace='/copilot'/>" class="button2">
                                                        <span class="left"><span class="right">Engage the Copilots</span></span>
                                                    </a>
                                                    <span class="or">or</span>
                                                    <a href="<s:url action='selectFromCopilotPool' namespace='/copilot'/>" class="button2"> <span class="left"><span class="right">Choose a Copilot</span></span></a>                                                    
                                                </div>
                                                <!--End .buttonArea-->
                                            </div>
                                            
                                        </div><!-- End .leftColumn -->
                                        
                                        
                                        <div class="clear"></div>
                                    </div>
                                    <!--End .mainContent-->
                                    
                                    
                                  
                                    
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

    <div id="TB_overlay" class="TB_overlayBG"></div>
    <div id="TB_window">
        <div id="TB_title">
            <div id="TB_ajaxWindowTitle"></div>
            <div id="TB_closeAjaxWindow">
                <a href="javascript:;" id="TB_closeWindowButton"></a>
            </div>
        </div>
        <div id="TB_ajaxContent">
            <div class="helpPopupInner details">
                
                <div class="logoArea">
                    <a href="javascript:;"><img src="/images/TopCoder_logo.png" alt="TopCoder" /></a>
                </div>
            </div><!-- End .helpPopupInner -->
        </div>
        <div id="placeHolder">PlaceHoldertest</div>
    </div>
        
    
    <div id="allDescriptionIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> Enter a description of the opportunity that you are comfortable showing to any logged in TopCoder member.  This is typically a relatively short overview that will gain the interest of copilots that are scanning opportunities.  Do not include any confidential information.
                    </p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>

    <div id="privateDescriptionIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> Enter a complete description of the opportunity.  This information will only be visible to approved copilots that have confidentiality agreements on file with TopCoder.  Include as much detail about your project as possible.  You can also upload files, which will only be visible as part of the private description.
                    </p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    
    <div id="costIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> The Adminstration Fee is the cost to you for posting this Copilot Opportunity.  The 1st and 2nd prizes will be awarded to the top 2 Copilots based on their submissions and review of the game plans, historical performance, etc.  This is NOT the payment the Copilot will receive for managing your project.</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    
    <div id="draftPanelTrigger" class="hide">
    </div>
    <div id="saveAsDraft" class="acceptRejectPopup hide">
        <div class="popupMask">
            <div class="popupWrap">
                <div class="popupContent">
                    <dl>
                        <dt>Your Copilot Selection Contest has been saved as draft</dt>
                         
                        <dd class="yesno">
                             <a href="#" class="button6" id="saveAsDraftOK"><span class="left"><span class="right">OK</span></span></a>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="includes/popups.jsp"/>
    
</body>
<!-- End #page -->

</html>
