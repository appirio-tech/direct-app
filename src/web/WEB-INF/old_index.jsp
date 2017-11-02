<%--
  - Author: isv, winsty, Blues, Ghost_141
  - Version: 1.2
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Landing page for TC Direct application.
  -
  - Version 1.0.1 (Direct Release 6 assembly) changes: pressing Enter on Login form input fields submits the form.
  - Version 1.0.2 (Direct Improvements Assembly Release 1 ) Change notes: Add a hidden field forwarUrl to support redirecting
  - Version 1.0.3 (TC Direct UI Improvement Assembly 1 ) Change notes: Solve "404 not found when click "Projects Available for Conpilots" link in Copilots section in home page."
  - to the latest URL after user login in.
  - Version 1.1 (Release Assembly - TC Cockpit Sidebar Header and Footer Update) changes:
  - 1) Updated the video area of the home page right sidebar.
  - 2) Added a help center widget to the home page right sibebar.
  - 3) Updated the footer of the home page.
  - 4) Added a link to topcoder community in the home page.
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - Fix multiple bugs.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" 
 import="com.topcoder.direct.services.configs.ServerConfiguration,
         com.topcoder.direct.services.view.util.DirectProperties" %>
<%@ page import="com.topcoder.shared.util.ApplicationServer" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>Topcoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/screen.css?v=214495" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/old_homepage.css?v=203310" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery.jcarousel.css?v=176771" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/thickbox.css?v=192822" media="all" type="text/css"/>

   <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css?v=176771"/>
    <![endif]-->
    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css?v=215325"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css?v=203310"/>
    <![endif]-->
    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css?v=203310"/>
    <![endif]-->
    
    <!--[if IE 6]>
        <script type="text/javascript" src="/scripts/DD_belatedPNG.js?v=185283"></script>
        <script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></script>
        <script type="text/javascript" src="/scripts/ie6.js?v=205148"></script>
    <![endif]-->


    <!-- External javascripts -->
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/jquery.jcarousel.pack.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js?v=186145"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js?v=179836"></script>
    <script type="text/javascript" src="/scripts/old_scripts.js?v=203345"></script>
    <script type="text/javascript" src="/scripts/AJAXProcessor.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/RSSProcessor.js?v=176771"></script>
    <script type="text/javascript" src="/scripts/FeedLoader.js?v=213163"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js?v=215005"></script>


    <script type="text/javascript">
        $(document).ready(function() {
            $("#LoginForm [name=formData.username]").focus();
            loadHomePageFeeds();
        });
        function submitOnEnter(e) {
            if (!isEnterKeyPressed(e)) return true;
            $('#LoginForm').submit();
            return false;
        }

        function isEnterKeyPressed(e) {
            if (window.event) return (window.event.keyCode == 13);
            if (e) return (e.which == 13);
            return false;
        }
    </script>
	 <script src="/scripts/auth0-widget-2.3.6.min.js"></script>
    <script src="https://d19p4zemcycm7a.cloudfront.net/w2/auth0-widget-2.3.6.min.js"></script>
        <script type="text/javascript">
            var widget = new Auth0Widget({
                domain: '<%=DirectProperties.DOMAIN_AUTH0%>',
                clientID: '<%=DirectProperties.CLIENT_ID_AUTH0%>',
                callbackURL: 'https://<%=DirectProperties.REG_SERVER_NAME%><%=DirectProperties.REDIRECT_URL_AUTH0%>'
            });

            function showAuth0Widget(){
                widget.signin({
                    state : 'https://<%=ApplicationServer.SERVER_NAME%>/direct/',
                    icon: 'http://www.topcoder.com/i/24x24_brackets.png', 
                    showIcon: true}).on('signin_ready', function() {
                    $('.a0-email input').each(function() {
                        $(this)
                        .clone()
                        .attr('type','text')
                        .attr('placeholder', 'Username')
                        .attr('title', 'Username')
                        .insertAfter($(this)).prev().remove();
                    });
                });  
            }            
            
            $(function () {
                $('#social-login').click(function () {
                    showAuth0Widget();
                });
            });
            
        </script> 

</head>

<body class="homePage" id="page">


    <!-- ie6 notification module -->
    <div id="ie6-notification">
        <div class="homeContent">
            <div class="alert">
                <span class="title">You&acute;re using Internet Explorer 6 or Lower. </span>
                <span class="desc">For the full features supported and best experience we strongly recommend upgrading to 
                    <strong class="ie8"><a href="javascript:;">Internet Explorer 8</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/ie_icon.png" alt="Internet Explorer 8" /></span>
                                <a href="https://windows.microsoft.com/en-US/internet-explorer/downloads/ie-8" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>, 
                    <strong class="ff"><a href="javascript:;">Firefox 5.0</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/ff_icon.png" alt="Firefox 5.0" /></span>
                                <a href="https://www.mozilla.com/en-US/firefox/fx/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>, 
                    <strong class="safari"><a href="javascript:;">Safari 5</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/safari_icon.png" alt="Safari 5.0" /></span>
                                <a href="https://www.apple.com/safari/download/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong> or 
                    <strong class="chrome"><a href="javascript:;">Google Chrome</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/chrome_icon.png" alt="Google Chrome" /></span>
                                <a href="https://www.google.com/chrome/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>
                </span>
            </div>
            <div class="noAsk">
            	<a href="javascript:;" class="close">Close</a>
                <div class="clear"></div>
                <div class="noAakInner"><input type="checkbox" id="noAshAgain" /><label>Don&acute;t show again</label></div>
            </div>
        </div>
    </div>
    <!-- ie6 notification module ends -->

	<div id="landingPage">
	
		<!-- The header of the landing page -->
		<div id="header">
			<div class="headerInner">
			
				<a href="#" class="logo"><img src="/images/logo.png" alt="TopCoder"/></a>
				
				<div class="welcomeHelp">
				<!-- this will contain the welcome message and the help icon -->
					<div class="help"><link:help/></div><!-- End .help -->
					<div class="welcome">
						<img src="/images/welcome.png" alt="Welcome To TopCoder Direct" />
					</div><!-- end .welcome -->
                    <div class="clear"></div>
                    <p class="lookCP">Looking for Community Portal?
                        <a onclick="window.open('https://community.topcoder.com/');" href="javascript:;">Go There Now</a>
                    </p>
				</div><!-- End .welcomeHelp -->
			</div><!-- End .headerInner -->
		</div><!-- End #header -->
		
		<div id="wrapper">
			<div class="wrapperTop"><div class="wrapperBottom"><div class="wrapperLeft"><div class="wrapperRight">
				<div class="wrapperTopLeft"><div class="wrapperBottomLeft">
				
					<div class="content">
						<div class="contentInner">
						
						<!-- this area will contain the carousel, news and statistics about TopCoder... -->
						<div class="area1">
							
							<div class="carouselArea">
								<div id="mycarousel" class="jcarousel-skin-topCoder">
									<ul id="myCarouselUL">
										<li><a href="#"><img src="/images/carousel_img1.jpg" alt="image1" /></a></li>
										<li><a href="#"><img src="/images/carousel_img4.jpg" alt="image4" /></a></li>
                                                                                <li><a href="#"><img src="/images/carousel_img5.jpg" alt="image5" /></a></li>
                                                                                <li><a href="#"><img src="/images/carousel_img6.jpg" alt="image6" /></a></li>
									</ul>
								</div><!-- End #mycarousel -->
								
								<div class="jcarousel-control">
									 
									  
									  <div class="jcarousel-pages">
									  	<a href="javascript:;" class="nextCarousel"><img src="/images/arrow_next.png" alt="Next" /></a>
									  	<span class="pageNum"><span id="currentPage">1</span> / <span id="totalPages">8</span></span>
									  </div>
							     </div>
								
							</div><!-- End .carouselArea -->
							
							<!-- this area will contain two columns. The first one is the news column and the second will contain statistics about topcoder--> 
							<div class="area1content">
							
								<!-- this area will contain the news -->
								<div class="newsColumn hide" id="newsColumn">
                                    <div id="rssLoading" style="padding-left:100px;padding-top:100px;">
                                        <img src="/images/rss_loading.gif" alt="loading">
                                    </div>
								</div><!-- End .newsColumn -->
								
								<div class="statColumn">
									<div>
										<div class="title">
											<div class="titleLeft"><div class="titleRight">
												<h2>Some facts about the TopCoder Platform</h2>
											</div></div>
										</div><!-- End .title -->
                                        <s:push value="viewData.topcoderDirectFacts">
										<table width="100%">
											<tr>
												<td class="first">
                                                    <strong>Active Projects</strong>
                                                    <link:helpIcon/>
                                                </td>
												<td class="last highLight">
                                                    <strong><s:property value="activeProjectsNumber"/></strong>
                                                </td>
											</tr>
											<tr>
												<td class="first">
                                                    <strong>Active Challenges</strong>
                                                    <link:helpIcon/>
                                                </td>
												<td class="last highLight">
                                                    <strong><s:property value="activeContestsNumber"/></strong>
                                                </td>
											</tr>
											<tr class="even">
												<td class="first">
                                                    <strong>Prizes Purse</strong> in Running Challenge
                                                    <link:helpIcon/>
                                                </td>
												<td class="last highLight2">
                                                    <strong>$<s:property value="prizePurse"/></strong>
                                                </td>
											</tr>
                                            
											<tr>
												<td class="first">
                                                    <strong>Active Members</strong> (Competing)
                                                    <link:helpIcon/>
                                                </td>
												<td class="last highLight2">
                                                    <strong><s:property value="activeMembersNumber"/></strong>
                                                </td>
											</tr>
										</table>
                                        </s:push>
									</div>
									
									<div class="copilots">
										<!-- information about copilots -->
										<div class="title">
                                            <div class="titleLeft">
                                                <div class="titleRight">
                                                    <h2>Copilots <link:helpIcon/></h2>
                                                </div>
                                            </div>
                                        </div><!-- End .title -->
                                        <s:push value="viewData.coPilotStats">
                                        <table width="100%">
                                            <tr>
                                                <td><strong>Available</strong> Copilots</td>
                                                <td class="highLight2">
                                                    <strong><s:property value="availableCopilots"/></strong>
                                                </td>
                                            </tr>
                                            <tr class="even">
                                                <td><a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=ActiveContests&pt=29" style="color:black;text-decoration: none;" target="_blank">
                                                    <strong>Projects Available</strong> for Copilots</a>
                                                </td>
                                                <td class="highLight2">
                                                    <strong><s:property value="availableCopilotProjects"/></strong>
                                                </td>
                                            </tr>
                                        </table>
                                        </s:push>
                                        <!--<button:hireCoPilot styleClass="fLeft"/>-->
									</div>
								</div><!-- End .newsColumn -->
							</div><!-- End .area1content -->

						</div><!-- End area1 -->
						
						<!-- this area will contain the login form, video area and somme links... -->
						<div class="area2">
							<div class="area2Content">
							
								<div class="login">
                                    <s:form action="home" namespace="/" id="LoginForm" method="post">
                                        <s:actionerror/>
                                        <h3>Please Login</h3>
                                        <p>
                                            <s:textfield name="formData.username" value="" cssClass="text"
                                                         onkeypress="submitOnEnter(event);"/>
                                        </p>
                                        <p>
                                            <s:password name="formData.password" cssClass="text"
                                                        onkeypress="submitOnEnter(event);"/>
                                        </p>
                                        <p>
                                            <s:checkbox id="remember_me" cssClass="rememberMe"
                                                        name="formData.remember"/>
                                            <s:label for="remember_me" cssClass="rememberMe">Remember Me</s:label>
                                            <a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=FindUser"
                                               class="forgotPass" target="_blank">Forgot Password?</a>
                                            <s:submit cssStyle="display:none;"/>
                                        </p>

                                        <div class="register">
                                            <p class="registerLink">New to TopCoder? Please
                                                <a href="https://www.topcoder.com/reg2" target="_blank">Register</a>
                                            </p>
                                            <p class="registerLink">
                                                <a href="javascript:document.LoginForm.submit();" class="button8">
                                                    <span class="left"><span class="right">LOGIN</span></span></a>
                                            </p>
                                        </div><!-- End .register -->
                                        <c:if test="${not empty sessionScope.redirectBackUrl}">
                                            <s:hidden name="forwardUrl" value="%{#session.redirectBackUrl}" />
                                        </c:if>
                                        <div class="register">
                                            <a  style="width:100%;text-align:center"class="button8" id="social-login">
                                                <span class="left"><span class="right">SOCIAL LOGIN</span></span>
                                            </a>
                                        </div>
                                    </s:form>
								</div><!-- End .login -->
								
								<!-- video Widget -->
								<jsp:include page="includes/videoWidget.jsp"/>
								
                                <jsp:include page="includes/helpWidget.jsp"/>

							</div><!-- End .area2Content -->
						</div><!-- End area2 -->
						</div><!-- End .contentInner -->
					</div><!-- end .content --> 
				</div></div>
			</div></div></div></div>
		</div><!-- End #wrapper -->

        <jsp:include page="includes/footer.jsp"/>
		
	</div><!-- End #landingPage -->

    <jsp:include page="includes/popups.jsp"/>

</body><!-- End #page -->

</html>
