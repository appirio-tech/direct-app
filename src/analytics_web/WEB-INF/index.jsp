<%--
  - Author: pinoydream
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 TopCoder Analytics Landing Page Assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>LANDINGPAGE</title>

<link rel="stylesheet" type="text/css" href="/css/analytics/screen.css" media="screen"/>

<script type="text/javascript" src="/scripts/analytics/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/scripts/analytics/jquery.anythingslider.js"></script>
<script type="text/javascript" src="/scripts/analytics/jquery.jcarousel.min.js"></script>
<script type="text/javascript" src="/scripts/analytics/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/scripts/analytics/jquery.jscrollpane.js"></script>
<script type="text/javascript" src="/scripts/analytics/script.js"></script>

<script type="text/javascript">
var ctx = '<c:out value="${ctx}"/>';
var withLoggedUser = false;
<c:if test="${not empty sessionScope.userHandle}">
withLoggedUser = true;
</c:if>
</script>
</head>

<body>
	
	<!-- header -->
	<div id="header">
		<div class="headerInner">
			<div class="logo"><a href="javascript:;"><img src="/images/analytics/tc-logo.png" alt="TOPCODER" /></a></div>
			<div class="nav">
				<ul>
					<li><a href="javascript:;">Start New Project</a></li>
					<li><a href="javascript:;">Blog</a></li>
					<li><a href="javascript:;">Help</a></li>
					<li><a href="javascript:;" id="loginLink">Login</a></li>
				</ul>
			</div>
			<div class="loginNav">
				<ul>
					<li><a href="javascript:;">Start New Project</a></li>
					<li><a href="javascript:;">Blog</a></li>
					<li><a href="javascript:;">Help</a></li>
					<li><a href="javascript:;" id="logoutLink">Logout</a></li>
					<li class="welcome"><span>|</span>Welcome <a href="javascript:;" class="loggedUsername"><c:out value="${sessionScope.userHandle}"/></a>!</li>
				</ul>
			</div>
			<!-- login -->
			<div class="loginSection">
				<a href="javascript:;" class="close">CLOSE</a>
				<div class="loginSectionLeft">
					<div class="loginSectionRight">
						<div class="loginSectionInner">
							<div class="register">
								Have no login ?
								<a href="javascript:;" class="registerModal">Register now !</a>
                                <div class="errorMessage">
                                    <span class="errorInfo">Username or password incorrect.</span>
                                </div>								
							</div>
							<div class="login">
								<label>Login:</label>
								<div>
									<input type="text" class="text" id="userName" tabindex="1" />
									<span class="errorInfo"></span>
								</div>
							</div>
							<div class="password">
								<a href="javascript:;">Forgot ?</a>
								<label>Password:</label>
								<div>
									<input type="password" class="text" id="password" tabindex="2" />
									<span class="errorInfo"></span>
								</div>
							</div>
							<a href="javascript:;" class="btn" id="loginBtn"><span class="left"><span class="right">Login</span></span></a>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- End .loginSection -->
			<div class="clear"></div>
		</div>
	</div>
	<!-- End #header -->
	
	<!-- mainContainer -->
	<div id="mainContainer">
		<!-- banner -->
		<div class="banner">
			<div class="bannerInner">
				<h2>Big Data + Big Community <strong>= Big Results</strong></h2>
				<p>TopCoder Analitics Solutions helps you predict and improve your business outcome, reduce costs and make decisions faster. If you want to reach your goals in no time take advange of TopCoder Analytics today!</p>
				<a href="javascript:;" class="btnGetStartedNow">Get Started Now!</a>
			</div>
		</div>
		<!-- End .banner -->
		<!-- section -->
		<div class="section stepSection">
			<ul>
				<li class="stepFirst">
					<span>1.</span>
					<h3>Start Project</h3>
					<p>Here, you will provide a rough sketch of the problem you are trying to solve.  Don't worry - a copilot will help you through the process.</p>
				</li>
				<li class="stepSecond">
					<span>2.</span>
					<h3>Provide Sample Data</h3>
					<p>Here, you will be able to upload sample data that helps frame the problem and evaluate submitted candidate solutions.</p>
				</li>
				<li class="stepThird">
					<span>3.</span>
					<h3>Let the World Compete to Find Your Solution</h3>
					<p>This is the fun part. Tens to hundreds, possibly even thousands, of people will work on finding a solution to your problem at the same time. The only thing cooler than watching the process is what the results will do for your business!</p>
				</li>
				<li class="stepForth">
					<span>4.</span>
					<h3>Evaluation</h3>
					<p>As part of the problem setup, a copilot helped put together a comprehensive test plan that will be run during this phase to determine the winner. The only people more interested in your solution than you at this point will be the competitors!</p>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- End .section -->
		<!-- section -->
		<div class="section showcaseSection">
			<h2>Showcase</h2>
			<ul id="showcaseCarousel">
				<li>
					<div class="title nas">
						<h3>Crater Detection</h3>
						<img src="/images/analytics/small-nsa-logo.png" alt="NAS" />
					</div>
					<dl>
						<dt>Client:</dt>
						<dd>NASA NTL</dd>
						<dt>Contest:</dt>
						<dd><a href="javascript:;">NASA NTL Marathon Match 2</a></dd>
						<dt>Problem:</dt>
						<dd>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.</dd>
						<dt>Competitors:</dt>
						<dd>70</dd>
						<dt>Winner:</dt>
						<dd><a href="javascript:;">nhzp339</a></dd>
					</dl>
					<a href="javascript:;" class="btn" id="button1"><span class="left"><span class="right">View Details</span></span></a>
				</li>
				<li>
					<div class="title intel">
						<h3>Intel Multi-Threading</h3>
						<img src="/images/analytics/small-intel-logo.png" alt="INTEL" />
					</div>
					<dl>
						<dt>Client:</dt>
						<dd>intel</dd>
						<dt>Contest:</dt>
						<dd><a href="javascript:;">Intel Multi-Threading Competition 4</a></dd>
						<dt>Problem:</dt>
						<dd>Nearest Neighbors - given two sets of points in N-dimensional space A and B, for every point b in set B, find the point a in set A that is...</dd>
						<dt>Competitors:</dt>
						<dd>274</dd>
						<dt>Winner:</dt>
						<dd><a href="javascript:;">aanbar</a></dd>
					</dl>
					<a href="javascript:;" class="btn" id="button2"><span class="left"><span class="right">View Details</span></span></a>
				</li>
				<li>
					<div class="title lindenLab">
						<h3>J2KDecode</h3>
						<img src="/images/analytics/small-linden-lab-logo.png" alt="LINDEN-LAB" />
					</div>
					<dl>
						<dt>Client:</dt>
						<dd>Linden Lab</dd>
						<dt>Contest:</dt>
						<dd><a href="javascript:;">Linden Lab OpenJPEG </a></dd>
						<dt>Problem:</dt>
						<dd>Build the OpenJPEG library to improve the decode speed of  JPEF2000 image format across a network channel. </dd>
						<dt>Competitors:</dt>
						<dd>93</dd>
						<dt>Winner:</dt>
						<dd><a href="javascript:;">Psyho</a></dd>
					</dl>
					<a href="javascript:;" class="btn" id="button3"><span class="left"><span class="right">View Details</span></span></a>
				</li>
				<li>
					<div class="title nas">
						<h3>Crater Detection</h3>
						<img src="/images/analytics/small-nas-logo.png" alt="NAS" />
					</div>
					<dl>
						<dt>Client:</dt>
						<dd>NAS</dd>
						<dt>Contest:</dt>
						<dd><a href="javascript:;">NSA Marathon Match 1</a></dd>
						<dt>Problem:</dt>
						<dd>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut.</dd>
						<dt>Competitors:</dt>
						<dd>100</dd>
						<dt>Winner:</dt>
						<dd><a href="javascript:;">gset123</a></dd>
					</dl>
					<a href="javascript:;" class="btn" id="button4"><span class="left"><span class="right">View Details</span></span></a>
				</li>
			</ul>
		</div>
		<!-- End .section -->
		<!-- section -->
		<div class="section middleSection">
			<!-- section -->
			<div class="section pressClippingsSection">
				<h2>Press Clippings</h2>
				<div class="articleAll">
				<c:forEach var="rssItem" items="${requestScope.rssFeed.items}" begin="0" end="2" step="1">
                    <div class="article">
                        <h3><a href="<c:out value='${rssItem.link}'/>" target="_blank"><c:out value="${rssItem.title}"/></a></h3>
                        <p><span class="time"><c:out value="${rssItem.publishedDate}"/></span> <c:out value="${rssItem.description}" escapeXml="false"/></p>
                        <div class="readMoreLink"><a href="<c:out value='${rssItem.link}'/>" target="_blank">Read More</a></div>
                    </div>
				</c:forEach>
					<a href="http://www.topcoder.com/aboutus/news/" target="_blank" class="seeAllArticlesLink">See All Articles</a>
				</div>
			</div>
			<!-- End .pressClippingsSection -->
			<!-- aside -->
			<div class="aside ourClientAside">
				<h2>Our Clients</h2>
				<div class="figure nasaFigure"><img src="/images/analytics/nasa-logo.png" alt="NASA" /></div>
				<div class="figure"><img src="/images/analytics/nas-logo.png" alt="NAS" /></div>
				<div class="figure intelFigure"><img src="/images/analytics/intel-logo.png" alt="INTEL" /></div>
				<div class="figure amdFigure"><img src="/images/analytics/amd-logo.png" alt="AMD" /></div>
				<div class="figure lindenFigure"><img src="/images/analytics/linden-lab-logo.png" alt="LINDEN-LAB" /></div>
			</div>
			<!-- End .ourClientAside -->
			<div class="clear"></div>
		</div>
		<!-- End .middleSection -->
		<!-- section -->
		<div class="section bottomSection">
			<div class="bottomSectionBg">
				<div class="bottomSectionInner">
					<!-- article -->
					<div class="article topcoderArticle">
						<h3>Top Coder</h3>
						<ul>
							<li><a href="javascript:;">Home</a></li>
							<li><a href="javascript:;">Community</a></li>
							<li><a href="javascript:;">Cockpit</a></li>
							<li><a href="javascript:;">Register</a></li>
						</ul>
					</div>
					<!-- End .topcoderArticle -->
					<!-- article -->
					<div class="article parentSitesArticle">
						<h3>Partner Sites</h3>
						<ul>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
							<li><a href="javascript:;">Lorem Ipsum Dolor Sit</a></li>
						</ul>
					</div>
					<!-- End .parentSitesArticle -->
					<!-- article -->
					<div class="article testimonialsArticle">
						<h3>Testimonials</h3>
						<div class="figure">
							<img src="/images/analytics/image-client-pic.jpg" alt="IMAGE CLIENT" />
							<div class="detail">
								<em class="leftQ">"</em>Lorem ipsum dolor sit amet, con- sectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex est laborum!<em class="rightQ">"</em>
							</div>
							<p>Alan Smith</p>
						</div>
					</div>
					<!-- End .testimonialsArticle -->
					<!-- article -->
					<div class="article contactUsArticle">
						<h3>Contact Us</h3>
						<div class="figure">
							<img src="/images/analytics/google-map-pic.jpg" alt="GOOGLE MAP" />
							<div class="detail">
								<p>The Company Name Inc.8901 Marmora Lorem Ipsum  D04 89GR.</p>
								<dl>
									<dt>Telephone:</dt>
									<dd>+1 800 603 6035</dd>
									<dt>FAX:</dt>
									<dd>+1 800 889 9898</dd>
								</dl>
							</div>
							<div class="clear"></div>
						</div>
						<div class="command">
							<span>Follow us on:</span>
							<a href="javascript:;" class="faceboxLink">Facebox</a>
							<a href="javascript:;" class="twitterLink">Twitter</a>
							<a href="javascript:;" class="linkelnLink">Linkeln</a>
						</div>
					</div>
					<!-- End .contactUsArticle -->
				</div>
			</div>
		</div>
		<!-- End .bottomSection -->
	</div>
	<!-- End #mainContainer -->
	
	<!-- footer -->
	<div id="footer">
		<div class="footerInner">
			<p class="copyRight">&copy; 2012 TopCoder. All rights reserved.</p>
			<a href="javascript:;"><img src="/images/analytics/tc-footer-logo.png" alt="TOPCODER" /></a>
		</div>
	</div>
	<!-- End #footer -->
	
	<!-- modal -->
	<div id="modalBackground"></div>
	<div id="modal">
		<!-- register -->
		<div id="registerModal" class="modal">
			<a href="javascript:;" class="colseModal">CLOSE</a>
			<!-- title -->
			<div class="modalTitle">
				<div class="modalTitleLeft">
					<div class="modalTitleRight">
						<div class="modalTitleInner">
							<h2>REGISTER</h2>
						</div>
					</div>
				</div>
			</div>
			<!-- End .modalTitle -->
			<!-- section -->
			<div class="modalSection">
				<div class="modalSectionInner">
					<div class="row">
						<label>First Name:</label>
						<div class="field">
							<input type="text" id="firstName" class="text" tabindex="3" />
						</div>
					</div>
                    <div class="errorRow firstNameErr hide">
                        <span>Please fill in your first name</span>
                    </div>
					<div class="row">
						<label>Last Name:</label>
						<div class="field">
							<input type="text" id="lastName" class="text" tabindex="4" />
						</div>
					</div>
                    <div class="errorRow lastNameErr hide">
                        <span>Please fill in your last name</span>
                    </div>
					<div class="row">
						<label>Handle:</label>
						<div class="field">
							<input type="text" class="handleText" id="handle" tabindex="5" />
						</div>
					</div>
                    <div class="errorRow handleErr hide">
                        <span>Please fill in your desired handle</span>
                    </div>
					<div class="row">
						<label>E-mail:</label>
						<div class="field">
							<input type="text" class="text" id="email" tabindex="6" />
						</div>
					</div>
                    <div class="errorRow emailErr hide">
                        <span>Please fill in your email</span>
                    </div>
					<div class="row">
						<label>Password:</label>
						<div class="field">
							<input type="password" class="text" id="passwordWord" tabindex="7" />
						</div>
					</div>
                    <div class="errorRow regPasswordErr hide">
                        <span>Please fill in your password</span>
                    </div>
					<div class="passwordStrength">
						<span>Password Strength:</span>
						<img src="/images/analytics/password-strength.png" alt="" />
						<img src="/images/analytics/password-strength.png" alt="" />
						<img src="/images/analytics/password-strength-empty.png" alt="" />
						<img src="/images/analytics/password-strength-empty.png" alt="" />
					</div>
					<div class="row">
						<label>Confirm Password:</label>
						<div class="field">
							<input type="password" class="text" id="confirmPassword" tabindex="8" />
						</div>
					</div>
                    <div class="errorRow passwordNotMactchErr hide">
                        <span>Password does not match</span>
                    </div>
					<div class="row">
						<label>Verification Code:</label>
						<div class="code">
							<img src="/images/analytics/code.png" alt="CODE" id="veriImg"/>
						</div>
					</div>
					<div class="codeInput">
						<a href="javascript:;" class="tryAnotherCode">Try another code</a>
						<div class="clear"></div>
						<div class="field">
							<input type="text" id="verificationCode" class="text" tabindex="9" />
						</div>
					</div>
                    <div class="errorRow veriCodeErr hide">
                        <span>Please enter the verification code</span>
                    </div>
					<div class="clear"></div>
					<div class="accept">
						<input type="checkbox" class="checobox" id="checkboxPrivacy" tabindex="10" />
						<label>I have read and accept the <a href="https://www.topcoder.com/reg/privacy_policy.jsp" target="_blank">Privacy Policy</a></label>
					</div>
					<div class="clear"></div>
                    <div class="errorRow policyErr hide">
                        <span>Please accept the privary policy</span>
                    </div>
					<div class="buttonBox">
						<a href="javascript:;" class="btn" id="submitButton"><span class="left"><span class="right">Submit</span></span></a>
						<a href="javascript:;" class="btn resetButton"><span class="left"><span class="right">Reset</span></span></a>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!-- End .modalSection -->
			<div class="errorModalBottom">
				<div class="modalBottomLeft">
					<div class="modalBottomRight">
						<p>Please correct the errors above.</p>
					</div>
				</div>
			</div>
			<!-- bottom -->
			<div class="modalBottom">
				<div class="modalBottomLeft">
					<div class="modalBottomRight"></div>
				</div>
			</div>
			<!-- End .modalBottom -->
		</div>
		<!-- End #register -->
		<!-- detail -->
		<div id="detailModal" class="modal">
			<a href="javascript:;" class="colseModal">CLOSE</a>
			<!-- title -->
			<div class="modalTitle">
				<div class="modalTitleLeft">
					<div class="modalTitleRight">
						<div class="modalTitleInner">
							<h2>Showcase</h2>
						</div>
					</div>
				</div>
			</div>
			<!-- End .modalTitle -->
			<!-- section -->
			<div class="modalSection">
				<div class="modalSectionInner">
					<ul id="detailModalCarousel">
						<li>
							<div class="modalContainer">
								<div class="scrollPanel">
									<div class="modalLogo"><img src="/images/analytics/big-nas-logo.png" alt="NASA" /></div>
									<h3>Crater Detection</h3>
									<dl>
										<dt>Client:</dt>
										<dd>NASA NTL</dd>
										<dt>Contest:</dt>
										<dd><a href="javascript:;">NASA NTL Marathon Match 2</a></dd>
										<dt>Competitors:</dt>
										<dd>70</dd>
										<dt>Winner:</dt>
										<dd><a href="javascript:;" target="_blank">nhzp339</a></dd>
									</dl>
									<h4>The Problem</h4>
									<p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<div class="figure">
										<img src="/images/analytics/pic-01.jpg" alt="PIC" />
										<img src="/images/analytics/pic-02.jpg" alt="PIC" />
										<img src="/images/analytics/pic-03.jpg" alt="PIC" />
										<img src="/images/analytics/pic-04.jpg" alt="PIC" />
										<em>Note: Images can be used here if exists.</em>
									</div>
									<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
									<br />
									<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
									<br />
									<h4>The Solution</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
									<br />
									<p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
									<br />
									<h4>The Results</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
									<br />
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<br />
									<div class="buttonBox">
										<a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</li>
						<li>
							<div class="modalContainer">
								<div class="scrollPanel">
									<div class="modalLogo"><img src="/images/analytics/big-intel-logo.png" alt="INTEL" /></div>
									<h3>Intel Multi-Threading</h3>
									<dl>
										<dt>Client:</dt>
										<dd>intel</dd>
										<dt>Contest:</dt>
										<dd><a href="javascript:;">Intel Multi-Threading Competition 4</a></dd>
										<dt>Competitors:</dt>
										<dd>274</dd>
										<dt>Winner:</dt>
										<dd><a href="javascript:;">aanbar</a></dd>
									</dl>
									<h4>The Problem</h4>
									<p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<div class="figure">
										<img src="/images/analytics/pic-01.jpg" alt="PIC" />
										<img src="/images/analytics/pic-02.jpg" alt="PIC" />
										<img src="/images/analytics/pic-03.jpg" alt="PIC" />
										<img src="/images/analytics/pic-04.jpg" alt="PIC" />
										<em>Note: Images can be used here if exists.</em>
									</div>
									<h4>The Solution</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
									<br />
									<h4>The Results</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<br />
									<div class="buttonBox">
										<a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</li>
						<li>
							<div class="modalContainer">
								<div class="scrollPanel">
									<div class="modalLogo bigLinden"><img src="/images/analytics/big-linden-lab-logo.png" alt="LINDEN-LAB" /></div>
									<h3>J2KDecode</h3>
									<dl>
										<dt>Client:</dt>
										<dd>Linden Lab</dd>
										<dt>Contest:</dt>
										<dd><a href="javascript:;">Linden Lab OpenJPEG </a></dd>
										<dt>Competitors:</dt>
										<dd>93</dd>
										<dt>Winner:</dt>
										<dd><a href="javascript:;">Psyho</a></dd>
									</dl>
									<h4>The Problem</h4>
									<p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<div class="figure">
										<img src="/images/analytics/pic-01.jpg" alt="PIC" />
										<img src="/images/analytics/pic-02.jpg" alt="PIC" />
										<img src="/images/analytics/pic-03.jpg" alt="PIC" />
										<img src="/images/analytics/pic-04.jpg" alt="PIC" />
										<em>Note: Images can be used here if exists.</em>
									</div>
									<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
									<br />
									<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
									<br />
									<h4>The Solution</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
									<br />
									<p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
									<br />
									<h4>The Results</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
									<br />
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<br />
									<div class="buttonBox">
										<a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</li>
						<li>
							<div class="modalContainer">
								<div class="scrollPanel">
									<div class="modalLogo"><img src="/images/analytics/big-nasa-logo.png" alt="NAS" /></div>
									<h3>Crater Detection</h3>
									<dl>
										<dt>Client:</dt>
										<dd>NAS</dd>
										<dt>Contest:</dt>
										<dd><a href="javascript:;">NSA Marathon Match 1</a></dd>
										<dt>Competitors:</dt>
										<dd>100</dd>
										<dt>Winner:</dt>
										<dd><a href="javascript:;">gset123</a></dd>
									</dl>
									<h4>The Problem</h4>
									<p>Detect craters in a given set of orbital images taken under various illumination conditions and camera poses.  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<div class="figure">
										<img src="/images/analytics/pic-01.jpg" alt="PIC" />
										<img src="/images/analytics/pic-02.jpg" alt="PIC" />
										<img src="/images/analytics/pic-03.jpg" alt="PIC" />
										<img src="/images/analytics/pic-04.jpg" alt="PIC" />
										<em>Note: Images can be used here if exists.</em>
									</div>
									<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. </p>
									<br />
									<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.</p>
									<br />
									<h4>The Solution</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occa cat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
									<br />
									<p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.</p>
									<br />
									<h4>The Results</h4>
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
									<br />
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
									<br />
									<div class="buttonBox">
										<a href="#" class="btn"><span class="left"><span class="right"><span class="icon">Go to Contest</span></span></span></a>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<!-- bottom -->
			<div class="modalBottom">
				<div class="modalBottomLeft">
					<div class="modalBottomRight"></div>
				</div>
			</div>
			<!-- End .modalBottom -->
		</div>
		<!-- End #register -->
	</div>
	<!-- End #modal -->

</body>
</html>
