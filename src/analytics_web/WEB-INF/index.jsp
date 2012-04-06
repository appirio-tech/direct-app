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
<title>LANDINGPAGE</title>
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
                    <li><a href="javascript:;">Start New Project</a></li>
                    <li><a href="http://info.topcoder.com/blog">Blog</a></li>
                    <li><a href="http://www.topcoder.com/help">Help</a></li>
                    <li><a href="javascript:;" id="loginLink">Login</a></li>
                </ul>
            </div>
            <div class="loginNav">
                <ul>
                    <li><a href="/direct/createNewProject.action">Start New Project</a></li>
                    <li><a href="http://info.topcoder.com/blog">Blog</a></li>
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
                            <li><a href="http://community.topcoder.com/tc">Community</a></li>
                            <li><a href="http://www.topcoder.com/direct">Cockpit</a></li>
                            <li><a href="http://www.topcoder.com/reg">Register</a></li>
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
                            <a href="http://www.facebook.com/TopCoderInc" class="faceboxLink">Facebox</a>
                            <a href="http://www.twitter.com/TopCoder" class="twitterLink">Twitter</a>
                            <a href="http://www.linkedin.com/company/topcoder" class="linkelnLink">Linkeln</a>
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
</body>
</html>
