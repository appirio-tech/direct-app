<%--
  - Author: pinoydream
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 : BUGR-6377 TC Analytics Landing Page Assembly Enhancement
  - Version 1.0 TopCoder Analytics Landing Page Assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.System" %>
<% System.setProperty("com.sun.media.jai.disableMediaLib", "true"); %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>TopCoder Analytics</title>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<script type="text/javascript"><!--
    var ctx = '<c:out value="${ctx}"/>';
<c:if test="${not empty sessionScope.userHandle}">
    var withLoggedUser = true;
</c:if>
<c:if test="${empty sessionScope.userHandle}">
    var withLoggedUser = false;
</c:if>
//-->
</script>
</head>
<body>
    <!-- header -->
    <div id="header">
        <div class="headerInner">
            <div class="logo"><a href="http://www.topcoder.com/tc"><img src="/images/analytics/tc-logo.png" alt="TOPCODER" /></a></div>
            <div class="nav">
                <ul>
                    <li><a href="/direct/createNewProject.action">Start New Project</a></li>
                    <li><a href="http://www.topcoder.com/blog">Blog</a></li>
                    <li><a href="http://www.topcoder.com/help">Help</a></li>
                    <li><a href="javascript:;" id="loginLink">Login</a></li>
                </ul>
            </div>
            <div class="loginNav">
                <ul>
                    <li><a href="/direct/createNewProject.action">Start New Project</a></li>
                    <li><a href="http://www.topcoder.com/blog">Blog</a></li>
                    <li><a href="http://www.topcoder.com/help">Help</a></li>
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
                            <div class="errorMessage">
                                <p class="errorBoth">Username or password incorrect.</p>
                            </div>
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
                <p>TopCoder Analytics Solutions helps you predict and improve your business outcome, reduce costs and make decisions faster. If you want to reach your goals in no time take advange of TopCoder Analytics today!</p>
                <a href="/direct/createNewProject.action" class="btnGetStartedNow">Get Started Now!</a>
            </div>
        </div>
        <!-- End .banner -->
        <!-- section -->
        <div class="section stepSection">
            <ul>
                <li class="stepFirst">
                    <span>1.</span>
                    <h3>Start Project</h3>
                    <p>Here, you will provide a rough sketch of the problem you are trying to solve.  Don't worry - a copilot will help you through the process.</p>
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
        <%@ include file="/WEB-INF/includes/showcases.jsp" %>
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
                        <h3>TopCoder</h3>
                        <ul>
                            <li><a href="http://www.topcoder.com">Home</a></li>
                            <li><a href="http://community.topcoder.com/tc">Community</a></li>
                            <li><a href="http://www.topcoder.com/direct">Cockpit</a></li>
                            <li><a href="http://www.topcoder.com/reg">Register</a></li>
                        </ul>
                    </div>
                    <!-- End .topcoderArticle -->
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
                                <p>TopCoder, Inc.<br>95 Glastonbury Blvd.<br>Glastonbury, CT 06033 U.S.A.</p>
                                <dl>
                                    <dt>Telephone:</dt>
                                    <dd>1-866-867-2633</dd>
                                    <dt>FAX:</dt>
                                    <dd>860-657-4276</dd>
                                </dl>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="command">
                            <span>Follow us on:</span>
                            <a href="http://www.facebook.com/TopCoderInc" class="faceboxLink">Facebox</a>
                            <a href="http://www.twitter.com/TopCoder" class="twitterLink">Twitter</a>
                            <a href="http://www.linkedin.com/company/topcoder" class="linkelnLink">Linkeln</a>
                            <a href="https://plus.google.com/104268008777050019973" class="gplusLink">Google+</a>
                            <a href="http://youtube.com/topcoderinc" class="youtubeLink">YouTube</a>
                        </div>
                    </div>
                    <!-- End .contactUsArticle -->
                </div>
            </div>
        </div>
        <!-- End .bottomSection -->
    </div>
    <!-- End #mainContainer -->

<%@ include file="/WEB-INF/includes/footer.jsp" %>
<%@ include file="/WEB-INF/includes/modal.jsp" %>
</body>
</html>
