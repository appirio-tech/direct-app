<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>TopCoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- External CSS -->
    <link rel="stylesheet" href="/css/screen.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/homepage.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/jquery.jcarousel.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/thickbox.css" media="all" type="text/css"/>
    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="css/homepage-ie6.css"/>
    <![endif]-->

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.jcarousel.pack.js"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js"></script>
    <script type="text/javascript" src="/scripts/scripts.js"></script>
    <script type="text/javascript" src="/scripts/AJAXProcessor.js"></script>
    <script type="text/javascript" src="/scripts/RSSProcessor.js"></script>
    <script type="text/javascript" src="/scripts/FeedLoader.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#LoginForm [name=formData.username]").focus();
            loadHomePageFeeds();
        });
    </script>
</head>

<body id="page">
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
										<li><a href="#"><img src="/images/carousel_img1.png" alt="image1" /></a></li>
										<li><a href="#"><img src="/images/carousel_img1.png" alt="image1" /></a></li>
										<li><a href="#"><img src="/images/carousel_img1.png" alt="image1" /></a></li>
										<li><a href="#"><img src="/images/carousel_img1.png" alt="image1" /></a></li>
									</ul>
								</div><!-- End #mycarousel -->
								
								<div class="jcarousel-control">
									  <a href="javascript:alert('To be implemented by sub-sequent assemblies');">Active Contests</a>&nbsp;&nbsp;|&nbsp;&nbsp;
									  <a href="javascript:alert('To be implemented by sub-sequent assemblies');">Past Contests</a>
									  
									  <div class="jcarousel-pages">
									  	<a href="javascript:;" class="nextCarousel"><img src="/images/arrow_next.png" alt="Next" /></a>
									  	<span class="pageNum"><span id="currentPage">1</span> / <span id="totalPages">8</span></span>
									  </div>
							     </div>
								
							</div><!-- End .carouselArea -->
							
							<!-- this area will contain two columns. The first one is the news column and the second will contain statistics about topcoder--> 
							<div class="area1content">
							
								<!-- this area will contain the last news -->
								<div class="newsColumn" id="newsColumn">
								</div><!-- End .newsColumn -->
								
								<div class="statColumn">
									<div>
										<div class="title">
											<div class="titleLeft"><div class="titleRight">
												<h2>Did you know some facts about TC Direct?</h2>
											</div></div>
										</div><!-- End .title -->
                                        <s:push value="viewData.topcoderDirectFacts">
										<table width="100%">
											<tr>
												<td class="first">
                                                    <strong>Active Projects</strong> in Direct
                                                    <a href="#" class="helpIco">&nbsp;</a>
                                                </td>
												<td class="last highLight">
                                                    <strong><s:property value="activeProjectsNumber"/></strong>
                                                </td>
											</tr>
											<tr>
												<td class="first">
                                                    <strong>Active Contests</strong>
                                                    <a href="#" class="helpIco">&nbsp;</a>
                                                </td>
												<td class="last highLight">
                                                    <strong><s:property value="activeContestsNumber"/></strong>
                                                </td>
											</tr>
											<tr class="even">
												<td class="first">
                                                    <strong>Prizes Purse</strong> in Running Contest
                                                    <a href="#" class="helpIco">&nbsp;</a>
                                                </td>
												<td class="last highLight2">
                                                    <strong>$<s:property value="prizePurse"/></strong>
                                                </td>
											</tr>
											<tr>
												<td class="first">
                                                    <strong>Active Members</strong> (Competing)
                                                    <a href="#" class="helpIco">&nbsp;</a>
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
                                                    <h2>CoPilots <a href="#" class="helpIco">&nbsp;</a></h2>
                                                </div>
                                            </div>
                                        </div><!-- End .title -->
                                        <s:push value="viewData.coPilotStats">
                                        <table width="100%">
                                            <tr>
                                                <td><strong>Available</strong> Co-Pilots</td>
                                                <td class="highLight2">
                                                    <strong><s:property value="availableCopilots"/></strong>
                                                </td>
                                            </tr>
                                            <tr class="even">
                                                <td><strong>Projects Available</strong> for Co-Pilots</td>
                                                <td class="highLight2">
                                                    <strong><s:property value="availableCopilotProjects"/></strong>
                                                </td>
                                            </tr>
                                        </table>
                                        </s:push>
                                        <button:hireCoPilot styleClass="fLeft"/>
									</div>
								</div><!-- End .newsColumn -->
							</div><!-- End .area1content -->

						</div><!-- End area1 -->
						
						<!-- this area will contain the login form, video area and somme links... -->
						<div class="area2">
							<div class="area2Content">
							
								<div class="login">
                                    <s:form action="login" namespace="/" id="LoginForm" method="post">
                                        <s:actionerror/>
                                        <h3>Please Login</h3>
                                        <p>
                                            <s:textfield name="formData.username" value="" cssClass="text"/>
                                        </p>
                                        <p>
                                            <s:password name="formData.password" cssClass="text"/>
                                        </p>
                                        <p>
                                            <s:checkbox id="remember_me" cssClass="rememberMe"
                                                        name="formData.remember"/>
                                            <s:label for="remember_me" cssClass="rememberMe">Remember me</s:label>
                                            <a href="http://www.topcoder.com/tc?module=RecoverPassword"
                                               class="forgotPass" target="_blank">Forgot Password?</a>
                                            <s:submit cssStyle="display:none;"/>
                                        </p>

                                        <div class="register">
                                            <p class="registerLink">New to TC Direct? Please
                                                <a href="http://www.topcoder.com/reg" target="_blank">Register</a>
                                            </p>
                                            <p class="registerLink">
                                                <a href="javascript:document.LoginForm.submit();" class="button8">
                                                    <span class="left"><span class="right">LOGIN</span></span></a>
                                            </p>
                                        </div><!-- End .register -->
                                    </s:form>
								</div><!-- End .login -->
								
								<!-- this area will contain some links -->
								<div class="box">
								<!-- video wrapper -->
									<img src="/images/video.png" alt="Video"/>
                                    <link:allVideos/>
								</div><!-- End .box -->
								
                                <jsp:include page="includes/links.jsp"/>
								
								<!-- the need help box -->
								<div class="box">
									<div class="needHelpBox">
										<div class="needHelpLeft"><div class="needHelpRight">
											<div class="needHelpContent">
												<div>
													<h3>Need Help?</h3>
													<p><strong>1-866-TOPCODER</strong> or</p>
													<p><link:mailToDirectAssist/></p>
                                                </div>
                                                <div class="faq">
													<div class="faqHelp">
														<link:faq/>, your answers maybe there already!
													</div>
												</div><!-- End .faq -->
											</div><!-- needHelpContent -->
										</div></div>
									</div><!-- End .needHelpBox -->
								</div><!-- End .box -->
								
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