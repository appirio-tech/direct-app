<%--
  - Author: TCSASSEMBLER
  - Version: 1.2
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: provide two ways to select a copilot.
  - Since: TC Direct - Launch Copilot Selection Contest assembly
  - Version 1.1 (TC Direct Manage Copilots Assembly) changes: set CURRENT_TAB parameter.
  - Version 1.2 (TC Cockpit Post a Copilot Assembly 1) changes: apply to new prototype.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <c:set var="CURRENT_TAB" value="launchCopilot" scope="request"/>
    
    <link rel="stylesheet" href="/css/modal.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/newProject.css" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/get-a-copilot.css" media="all" type="text/css"/> 
    <link rel="stylesheet" href="/css/jquery.jcarousel.css" media="all" type="text/css" />

    <script type="text/javascript" src="/scripts/newProject.js"></script>
    <script type="text/javascript" src="/scripts/get-a-copilot.js"></script> 
    <script type="text/javascript" src="/scripts/jquery.jcarousel.pack.js"></script>   
    
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
                                            <!--<li><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
                                            <li><span class="istatus inext"><span class="arrow"><span class="bg">Step 4</span></span></span></li>-->
                                            <li class="last"><span class="istatus inext"><span class="arrow"><span class="bg">Step 3</span></span></span></li>
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
                                                    Copilot has its own payment rates are adjusted to the type of contest and may change depending on the needs of the contest.
                                                    <a href="javascript:;" class="blue">view rate</a>
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
                                                <div class="carousel-content">
                                                    <ul>
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/Ghostar.jpeg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=151743" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=151743">Ghostar</a></p>
                                                                    <p><span>68</span>Project :</p>
                                                                    <p><span>598</span>Contest :</p>
                                                                    <p><span>91</span>Repost :</p>
                                                                    <p><span>89</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">10</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">14</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/iRabbit_big.jpg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=15992135" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=15992135">iRabbit</a></p>
                                                                    <p><span>39</span>Project :</p>
                                                                    <p><span>289</span>Contest :</p>
                                                                    <p><span>40</span>Repost :</p>
                                                                    <p><span>42</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">10</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">16</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>

                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/copilots/person-01.png" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=21832874" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=21832874">lmmortal</a></p>
                                                                    <p><span>39</span>Project :</p>
                                                                    <p><span>206</span>Contest :</p>
                                                                    <p><span>24</span>Repost :</p>
                                                                    <p><span>31</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">3</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">3</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/hello-c.jpeg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22691760" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="#">&nbsp;</a><tc-webtag:handle coderId='22691760' darkBG="true" /></p>
                                                                    <p><span>16</span>Project :</p>
                                                                    <p><span>146</span>Contest :</p>
                                                                    <p><span>31</span>Repost :</p>
                                                                    <p><span>25</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">4</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">6</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/murphydog.jpeg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22630508" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22630508">murphydog</a></p>
                                                                    <p><span>8</span>Project :</p>
                                                                    <p><span>94</span>Contest :</p>
                                                                    <p><span>21</span>Repost :</p>
                                                                    <p><span>27</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">0</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">0</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/copilots/person-01.png" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706667" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706667">microwishing</a></p>
                                                                    <p><span>16</span>Project :</p>
                                                                    <p><span>86</span>Contest :</p>
                                                                    <p><span>0</span>Repost :</p>
                                                                    <p><span>4</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">5</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">7</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/lunarkid.jpeg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873">lunarkid</a></p>
                                                                    <p><span>10</span>Project :</p>
                                                                    <p><span>76</span>Contest :</p>
                                                                    <p><span>0</span>Repost :</p>
                                                                    <p><span>0</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">3</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">5</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                        
                                                        <li>
                                                            <div class="copilot-wrapper">
                                                                <div class="photo">
                                                                    <img src="http://www.topcoder.com/i/m/Luca_big.jpg" alt="handle" />
                                                                    <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=10348862" class="black-button"><span class="right"><span class="middle">View Profile</span></span></a>
                                                                </div>
                                                                <!-- .photo -->
                                                                <div class="info">
                                                                    <p class="handle">Handle : <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=10348862">Luca</a></p>
                                                                    <p><span>7</span>Project :</p>
                                                                    <p><span>71</span>Contest :</p>
                                                                    <p><span>14</span>Repost :</p>
                                                                    <p><span>14</span>Failure :</p>
                                                                    <p><span>n/a</span>Bugrace :</p>
                                                                    <div class="current">
                                                                        <p><a href="javascript:;" class="link">1</a>Current Projects :</p>
                                                                        <p><a href="javascript:;" class="link">1</a>Current Contests :</p>
                                                                    </div>
                                                                </div>
                                                                <!-- .info -->
                                                                <div class="clear"></div>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <div class="clear"></div>
                                                </div>
                                                <div class="foot-bar">
                                                    <a href="javascript:;" class="next">&gt;</a>
                                                    <a href="javascript:;" class="prev">&lt;</a>
                                                    <strong>1</strong> / 8
                                                </div>
                                                <!-- .foot-bar -->
                                                
                                            </div>
                                            <!--End .copilot-carousel-->
                                            <a href="http://community.topcoder.com/tc?module=ViewCopilotPool&size=20&view=0&sort=12" class="redText"><strong>View All Copilots</strong></a>
                                            
                                            
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
                                                        <span class="left"><span class="right">Find a Copilot</span></span>
                                                    </a>
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
