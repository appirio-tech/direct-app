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
    <link rel="stylesheet" href="/css/get-a-copilot.css?v=210944" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/jquery.jcarousel.css?v=176771" media="all" type="text/css" />

    <script type="text/javascript" src="/scripts/newProject.js?v=209631"></script>
    <script type="text/javascript" src="/scripts/get-a-copilot.js?v=211902"></script> 
    <script type="text/javascript" src="/scripts/jquery.jcarousel.pack.js?v=176771"></script> 
    
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
                                            <div class="videoWrap">
                                                <object type="application/x-shockwave-flash" data="http://www.youtube.com/v/y4TjXnpgVow" width="327" height="208">
                                                    <param name="movie"  value="http://www.youtube.com/v/y4TjXnpgVow" />
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
                                                    <ul>
                                                        <c:set var="serverName" value="<%=ApplicationServer.SERVER_NAME%>"/>
                                                        <c:forEach var="profile" items="${profiles}">
                                                            <c:set var="profileLink" value="http://${serverName}/tc?module=ViewCopilotProfile&pid=${profile.member.copilotProfile.userId}\" target=\"_blank"/>
                                                            <li>
                                                                <div class="copilot-wrapper">
                                                                    <div class="photo">
                                                                        <img src="${profile.photo.photoPath}" alt="handle" />
                                                                        <a href="${profileLink}" class="black-button">
                                                                            <span class="right"><span class="middle">View Profile</span></span>
                                                                        </a>
                                                                    </div>
                                                                    <!-- .photo -->
                                                                    <div class="info">
                                                                        <p class="handle">Handle : 
                                                                            <tc-webtag:handle coderId="${profile.member.copilotProfile.userId}" link="${profileLink}"/>
                                                                        </p>
                                                                        <p><span class="field_${profile.member.copilotProfile.userId}_totalProjects">loading</span>Project :</p>
                                                                        <p><span class="field_${profile.member.copilotProfile.userId}_totalContests">loading</span>Contest :</p>
                                                                        <p><span class="field_${profile.member.copilotProfile.userId}_totalRepostedContests">loading</span>Repost :</p>
                                                                        <p><span class="field_${profile.member.copilotProfile.userId}_totalFailedContests">loading</span>Failure :</p>
                                                                        <p><span class="field_${profile.member.copilotProfile.userId}_totalBugRaces">loading</span>Bugrace :</p>
                                                                        <div class="current">
                                                                            <p><a href="javascript:;" class="link field_${profile.member.copilotProfile.userId}_currentProjects">loading</a>Current Projects :</p>
                                                                            <p><a href="javascript:;" class="link field_${profile.member.copilotProfile.userId}_currentContests">loading</a>Current Contests :</p>
                                                                        </div>
                                                                        
                                                                    </div>
                                                                    <!-- .info -->
                                                                    <div class="clear"></div>
                                                                </div>
                                                            </li>                                                        
                                                        </c:forEach>
                                                 
                                                    </ul>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="foot-bar">
                                                    <a href="javascript:;" class="next">&gt;</a>
                                                    <a href="javascript:;" class="prev">&lt;</a>
                                                    <strong>0</strong> / ${fn:length(profiles)} 
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
