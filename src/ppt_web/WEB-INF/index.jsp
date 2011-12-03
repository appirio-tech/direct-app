<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Topcoder Presentation</title>

<link rel="stylesheet" type="text/css" href="/css/ppt/reset.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="/css/ppt/style.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="/css/ppt/skin.css" media="screen"/>

<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
<script type="text/javascript" src="/scripts/jquery.jcarousel.min.js?v=207547"></script>
<script type="text/javascript" src="/scripts/ppt/pptScript.js"></script>
</head>
<body>
    <div id="header">
        <div class="wrapper">
            <div id="topNav">
                <div id="logo"></div>
                <ul class="nav">
                    <li class="first">
                    <c:if test="${not empty sessionScope.userHandle}">
                    <a class="startNP" href="/direct/createNewProject.action">START NEW PROJECT</a>
                    </c:if>
                    <c:if test="${empty sessionScope.userHandle}">
                    <a class="startNP navLogin" href="javascript:;">START NEW PROJECT</a>
                    </c:if>
                    </li>
                    <li><a href="http://info.topcoder.com/blog">BLOG</a></li>
                    <li><a href="http://www.topcoder.com/help">HELP</a></li>
                    <c:if test="${empty sessionScope.userHandle}">
                    <li class="loginSection"><a href="javascript:;" class="navLogin">LOGIN</a></li>
                    <li class="welcomeSection hide"><span class="welcomeMsg">Welcome,[<span class="handle"></span>]</span><a href="javascript:;" class="logoutLink">LOGOUT</a></li>
                    </c:if>
                    <c:if test="${not empty sessionScope.userHandle}">
                    <li class="loginSection hide"><a href="javascript:;" class="navLogin">LOGIN</a></li>
                    <li class="welcomeSection"><span class="welcomeMsg">Welcome,[<span class="handle">${sessionScope.userHandle}</span>]</span><a href="javascript:;" class="logoutLink">LOGOUT</a></li>
                    </c:if>
                </ul>
            </div>
            <!--end #topNav-->
            <div class="topLinks">
                <div class="left"><img src="/images/ppt/present-icon.png" alt=""/>Presentation<span>Portal</span></div>
                <div class="right">
                    <span>Follow us on:</span>
                    <a href="javascript:;"><img src="/images/ppt/facebook-icon.png" alt=""/></a>
                    <a href="javascript:;"><img src="/images/ppt/in-icon.png" alt=""/></a>
                    <a href="javascript:;"><img src="/images/ppt/twitter-icon.png" alt=""/></a>
                </div>
            </div>
            <!--end .topLinks-->
        </div>
    </div>
    <!--end #header-->
    <div id="content">
        <div class="banner">
            <div class="wrapper">
                <div class="top">
                    <div class="left">
                        <h1>Present like</h1>
                        <h2>a pro.</h2>
                    </div>
                    <p>We've got your back. Our competitive community of <br /> designers and editors will help you and your message <br /> shine with a seamless, spectacular presentation.<br/><span>You have the substance. We'll bring the style.</span></p>
                </div>
                <!--end .top-->
                <div class="bottom">
                    <div class="section step1">
                        <div class="sep"></div>
                        <div class="stepNum"><span>STEP<br/><em>1</em></span></div>
                        <span class="title">Start a project</span>
                        <p>Open your TopCoder account and give your upcoming presentation a working title. You're off to a good start.</p>
                    </div>
                    <div class="section step2">
                        <div class="sep"></div>
                        <div class="stepNum"><span>STEP<br/><em>2</em></span></div>
                        <span class="title">Detail your requirements</span>
                        <p>Tell us what you need, upload any materials you want us to work from, and set a deadline. Your wish is our command.</p>
                    </div>
                    <div class="section step3">
                        <div class="sep"></div>
                        <div class="stepNum"><span>STEP<br/><em>3</em></span></div>
                        <span class="title">Launch a Competition</span>
                        <p>You click the button and we do the rest. You've set in motion a wonderful chain of events.</p>
                    </div>
                    <div class="section step4">
                        <div class="sep"></div>
                        <div class="stepNum"><span>STEP<br/><em>4</em></span></div>
                        <span class="title">Pick the winner</span>
                        <p>The contest is over and the entries are in, each one a complete presentation made to your specifications. Who's the winner? You are.</p>
                    </div>
                </div>
                <!--end .bottom-->
                <div class="topLine"></div>
            </div>
            <!--end .wrapper-->
        </div>
        <!--end .banner-->
        <div class="mainBody">
            <div class="wrapper">
                <div class="contentWrapper">
                    <div class="leftCol">
                        <c:if test="${not empty sessionScope.userHandle}">
                        <a href="/direct/createNewProject.action" class="bigBtn startNP"><span class="right"><span class="middle">Start a project now!</span></span></a>
                        </c:if>
                        <c:if test="${empty sessionScope.userHandle}">
                        <a href="javascript:;" class="bigBtn navLogin startNP"><span class="right"><span class="middle">Start a project now!</span></span></a>
                        </c:if>
                        <div class="showCaseSec">
                            <div class="heading">
                                <img src="/images/ppt/showcase-icon.png" alt=""/><h1>Showcase</h1>
                            </div>
                           <ul id="carousel" class="jcarousel-skin-tango">
                                <li><a href="javascript:;" class="showcaseImg showcaseImg1"><img src="/images/ppt/showcase-pic1.png" alt=""/></a>
                                    <a href="javascript:;" class="showcaseImg showcaseImg2"><img src="/images/ppt/showcase-pic2.png" alt=""/></a>
                                    <a href="javascript:;" class="showcaseImg showcaseImg3"><img src="/images/ppt/showcase-pic3.png" alt=""/></a></li>
                                <li><a href="javascript:;" class="showcaseImg showcaseImg4"><img src="/images/ppt/showcase-pic4.png" alt=""/></a>
                                    <a href="javascript:;" class="showcaseImg showcaseImg5"><img src="/images/ppt/showcase-pic5.png" alt=""/></a>
                                    <a href="javascript:;" class="showcaseImg showcaseImg6"><img src="/images/ppt/showcase-pic6.png" alt=""/></a></li>
                            </ul>
                            <!--end #carousel-->
                            <div class="bottom">
                                <a href="javascript:;" class="button3 hide"><span class="right"><span class="middle">More examples &raquo;</span></span></a>
                            </div>
                        </div>
                        <!--end .showCaseSec-->
                        <div class="publicationSec">
                            <div class="heading">
                                <img src="/images/ppt/publication-icon.png" alt=""/><h1>Press clippings</h1>
                            </div>
                            <div class="mainContent">
                                <p> TopCoder's unique outsourcing methodology attracts a lot of attention. Our innovative competition platform has been featured in books, magazine articles, national newspapers, and other major media venues. Click further to see what they're talking about!</p>
                                <ul>
                                    <li><a href="http://www.topcoder.com/aboutus/archive/2011/06/topcoder-featured-in-book-%e2%80%9dthe-open-innovation-marketplace%e2%80%9d/">TopCoder featured in book <i>The Open Innovation Marketplace</i> </a></li>
                                    <li><a href="http://www.topcoder.com/aboutus/archive/2010/12/teen-mathletes-do-battle-at-algorithm-olympics/">Teen Mathletes Do Battle at Algorithm Olympics</a></li>
                                    <li><a href="http://www.topcoder.com/aboutus/archive/2010/11/can-crowd-sourcing-spur-aerospace-ideas/">Can Crowd-Sourcing Spur Aerospace Ideas?</a></li>
                                    <li><a href="http://www.topcoder.com/aboutus/archive/2010/10/searching-for-the-next-generation-of-tech-innovators/">Searching for the Next Generation of Tech Innovators</a></li>
                                    <li><a href="http://www.topcoder.com/aboutus/archive/2010/10/nasa-harvard-beam-up-virtual-software/">NASA, Harvard Beam Up Virtual Software</a></li>
                                </ul>
                            </div>
                            <!--end .mainContent-->
                            <div class="bottom">
                                <a href="javascript:;" class="button3 hide"><span class="right"><span class="middle">More articles &raquo;</span></span></a>
                            </div>
                        </div>
                        <!--end .publicationSec-->
                    </div>
                    <!--end .leftCol-->
                    <div class="rightCol">
                        <div class="heading">
                                <img src="/images/ppt/client-icon.png" alt=""/><h1>Our clients</h1>
                        </div>
                        <div class="mainContent">
                            <p> TopCoder's satisfied customers include Fortune 500 companies, major government agencies, and some of the hottest names in high tech. You can put our winning formula to work for you, too.</p>
                            <div class="logoContainer">
                                <img src="/images/ppt/side-bar-icon1.png" alt=""/>
                            </div>
                            <div class="logoContainer">
                                <img src="/images/ppt/side-bar-icon2.png" alt=""/>
                            </div>
                            <div class="logoContainer">
                                <img src="/images/ppt/side-bar-icon3.png" alt=""/>
                            </div>
                            <div class="logoContainer">
                                <img src="/images/ppt/side-bar-icon4.png" alt=""/>
                            </div>
                        </div>
                        <!--end .mainContent-->
                        <div class="bottom">
                                <a href="javascript:;" class="button3 hide"><span class="right"><span class="middle">Learn more &raquo;</span></span></a>
                            </div>
                    </div>
                    <!--end .rightCol-->
                </div>
                <!--end .contentWrapper-->
            </div>
            <!--end .wrapper-->
        </div>
        <!--end .mainBody-->
    </div>
    <!--end #content-->
    <div id="footer">
        <div class="wrapper">
            <div class="bottomLine"></div>
            <div class="top">
                <div class="leftCol">
                    <p> Presentation designers: three steps to earning more </p>
                    <div class="line"><div class="num">1.</div><div class="content">Look for presentation styling contests on TopCoder Studio.</div></div>
                    <div class="line"><div class="num">2.</div><div class="content">Read the spec thoroughly so that you can meet the client's needs exactly. </div></div>
                    <div class="line"><div class="num">3.</div><div class="content">Design a spectacular presentation. You succeed by helping the client succeed. </div></div>
                    <a href="http://www.topcoder.com/studio" class="button2"><span class="right"><span class="middle">Learn more and start earning &raquo;</span></span></a>
                </div>
                <div class="rightCol">
                    <p>Quick Links</p>
                    <ul class="firstUl">
                        <li><a href="http://www.topcoder.com/tc">TopCoder Community</a></li>
                        <li><a href="http://www.topcoder.com/studio">TopCoder Studio</a></li>
                        <li><a href="http;//www.topcoder.com/cockpit">TopCoder Platform</a></li>
                        <li><a href="http://www.topcoder.com/help">Help Center</a></li>
                    </ul>
                    <ul class="secondUl">
                        <li><a href="http://www.topcoder.com/solutions/">What can I do?</a></li>
                        <li><a href="http://www.topcoder.com/how-it-works/">How does it work?</a></li>
                        <li><a href="http://www.topcoder.com/who-uses-topcoder/">Who uses TopCoder?</a></li>
                    </ul>
                    <ul>
                        <li><a href="http://info.topcoder.com/blog">Blog</a></li>
                        <li><a href="http://www.topcoder.com/aboutus/news/">News</a></li>               
                        <li><a href="http://www.topcoder.com/aboutus/">About Us</a></li>
                    </ul> 
                </div>
            </div>
            <!--end .top-->
            <div class="bottom">
                CopyrightTopcoder,Inc.2001-2011 | <a href="javascript:;">Terms of Use</a> | <a href="https://www.topcoder.com/reg/privacy_policy.jsp">Privacy Policy</a>
            </div>
            <!--end .bottom-->
        </div>
    </div>
    <!--end #footer-->
    <div id="loginModal" class="hide">
        <div class="header">
            <ul>
                <li class="first"><a href="javascript:;" class="registerTab">REGISTER</a></li>
                <li><a href="javascript:;" class="active loginTab">LOGIN</a></li>
            </ul>
        </div>
        <div class="banner">
            <p>Login using your credentials</p>
        </div>
        <div class="content">
            <div class="loginError hide">Incorrect username and/or password</div>
            <div class="row firstRow">
                <label for="username">Username</label>
                <img src="/images/ppt/error.png" alt="" class="errImg handleErr hide"/>
                <input id="username" type="text" class="inputCre"/>
                <span class="errorMsg handleErr hide">Please fill in your username</span>
            </div>
            <div class="row">
                <label for="password">Password</label>
                <img src="/images/ppt/error.png" alt="" class="errImg passwordErr hide"/>
                <input id="password" type="password" class="inputCre"/>
                <span class="errorMsg passwordErr hide">Please fill in your password</span>
            </div>
            <div class="row">
                <a href="https://www.topcoder.com/tc?module=RecoverPassword" class="forgetPass">Forgot your password?</a>
            </div>
            <div class="row specialRow">
                <input type="checkbox" class="checkCre" id="rememberMe"/><span>Remember me in this computer</span>
            </div>
            <div class="row">
                <a href="javascript:;" class="button2 resetBtn"><span class="right"><span class="middle">Reset</span></span></a>
                <a href="javascript:;" class="button2 loginBtn"><span class="right"><span class="middle">Login</span></span></a>
            </div>
        </div>
        <div class="footer">
        </div>
    </div>
    <!--end #loginModal-->
    <div id="registerModal" class="hide">
        <div class="header">
            <ul>
                <li class="first"><a href="javascript:;" class="active registerTab">REGISTER</a></li>
                <li><a href="javascript:;" class="loginTab">LOGIN</a></li>
            </ul>
        </div>
        <div class="banner">
            <p>Register for an account.</p>
        </div>
        <div class="content">
            <div class="row firstRow">
                <label for="firstName">First Name</label>
                <img src="/images/ppt/error.png" alt="" class="errImg firstNameErr hide"/>
                <input id="firstName" type="text" class="inputCre"/>
                <span class="errorMsg firstNameErr hide">Please fill in your first name</span>
            </div>
            <div class="row">
                <label for="lastName">Last Name</label>
                <img src="/images/ppt/error.png" alt="" class="errImg lastNameErr hide"/>
                <input id="lastName" type="text" class="inputCre"/>
                <span class="errorMsg lastNameErr hide">Please fill in your last name</span>
            </div>
            <div class="row">
                <label for="handle">Handle</label>
                <img src="/images/ppt/error.png" alt="" class="errImg handleErr hide"/>
                <input id="handle" type="text" class="inputCre"/>
                <span class="errorMsg handleErr hide">Please fill your desired handle</span>
            </div>
            <div class="row">
                <label for="email">Email</label>
                <img src="/images/ppt/error.png" alt="" class="errImg emailErr hide"/>
                <input id="email" type="text" class="inputCre"/>
                <span class="errorMsg emailErr hide">Please fill your email</span>
            </div>
            <div class="row">
                <label for="regPassword">Password</label>
                <img src="/images/ppt/error.png" alt="" class="errImg regPasswordErr hide"/>
                <input id="regPassword" type="password" class="inputCre"/>
                <span class="errorMsg regPasswordErr hide">Please fill your password</span>
            </div>
            <div class="row">
                <label for="confirmPassword">Confirm Password</label>
                <img src="/images/ppt/error.png" alt="" class="errImg passwordNotMactchErr hide"/>
                <input id="confirmPassword" type="password" class="inputCre"/>
                <span class="errorMsg passwordNotMactchErr hide">Password does not match</span>
            </div>
            <div class="row">
                <label for="veriCode">Verification Code</label>
                <img src="/images/ppt/error.png" alt="" class="errImg veriCodeErr hide"/>
                <input id="veriCode" type="text" class="inputCre"/>
                <span class="errorMsg veriCodeErr hide">Please enter the verification code</span>
            </div>
            <div class="row specialRow" style="margin-top:20px;">
                <img class="imgCode" src="" alt="" id="veriImg"/>
            </div>
            <div class="row specialRow">
                <a href="javascript:;" class="tryAnotherCode">Try Another Code</a>
            </div>
            <div class="row">
                <input type="checkbox" class="checkCre" id="accPol"/><span>I have read and accept the <a class="underline" href="https://www.topcoder.com/reg/privacy_policy.jsp">privacy policy</a></span>
                <span class="errorMsg policyErr hide">Please accept the privary policy</span>
            </div>
            <div class="row">
                <a href="javascript:;" class="button2 resetBtn"><span class="right"><span class="middle">Reset</span></span></a>
                <a href="javascript:;" class="button2 submitBtn"><span class="right"><span class="middle">Submit</span></span></a>
            </div>
        </div>
        <div class="footer">
        </div>
    </div>
    <!--end #registerModal-->
    <div class="showcaseModal hide" id="showcaseModal1">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel1" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/showcase-1.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>TopCoder Presentation Branding<br/> Update and Slide Graphics</h1>
                <p>TopCoder relied on its community members to compete in the creation of a generic presentation backdrop intended to harmonize with the look of the recently redesigned TopCoder homepage.</p>       
                <p class="boldText">1st Place <br/>ID: 112329<br/>Handle: <span>JGeeks</span><br/>Submitted on 07.15.2011 at 11:51 SGT</p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <div class="showcaseModal hide" id="showcaseModal2">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel2" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/showcase-2.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>$150 TopCoder PPT Presentation<br /> Design Contest</h1>
                <p>The TopCoder community competed to design an inspiring new look for presentations given by the NASA Tournament Lab (NTL), a site to be used jointly by NASA researchers and TopCoder community members. Designers were asked to give the pre-existing NTL logo a prominent place in the presentation.</p>       
                <p class="boldText">1st Place <br/>ID: 108271<br/>Handle: <span>ngraphics</span><br/>Submitted on 11.28.2010 at 14:50 SGT</p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <div class="showcaseModal hide" id="showcaseModal3">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel3" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/3/showcase-31.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-32.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-33.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-34.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-35.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-36.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-37.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-38.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/3/showcase-39.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>TCO11 Track Description <br />Presentation</h1>
                <p>TopCoder's competitive process resulted in the creation of a dynamic presentation about the 2011 edition of the annual TopCoder Open tournament. Presentation designers anticipated the addition of video clips and aimed for compatibility with the pre-existing graphical theme of the event.</p>       
                <p class="boldText">1st Place <br/>ID: 110877<br/>Handle: <span>djnapier</span><br/>Submitted on 05.11.2011 at 21:51 SGT </p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <div class="showcaseModal hide" id="showcaseModal4">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel4" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/4/showcase-41.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-42.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-43.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-44.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-45.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-46.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-47.jpg" alt=""/>
                    </li>
                    <li>
                        <img src="/images/ppt/4/showcase-48.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>Reference Model Concept Graphics<br />for Presentations & Training Materials</h1>
                <p>TopCoder members competed to design a set of seven presentation templates that can be used to portray common high-level concepts in software architecture. The contestants were instructed to work with a predominant color scheme of silver and green, and to produce vector graphics that can be scaled to any size.</p>       
                <p class="boldText">1st Place <br/>ID: 108426<br/>Handle: <span>grv.ashu</span><br/>Submitted on 12.13.2010 at 01:05 SGT </p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <div class="showcaseModal hide" id="showcaseModal5">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel5" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/showcase-5.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>Update Competition Flash Description<br />Presentation for TCO10</h1>
                <p>TopCoder's own community competed to design an informative and exciting video presentation about the algorithm track of the 2010 TopCoder Open tournament. The presentation contestants were asked to work within the style established by the pre-existing event website.</p>       
                <p class="boldText">1st Place <br/>ID: 105753<br/>Handle: <span>r1cs1</span><br/>Submitted on 08.16.2010 at 05:09 SGT</p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <div class="showcaseModal hide" id="showcaseModal6">
        <div class="header"></div>
        <div class="content">
            <a class="close" href="javascript:;"></a>
            <div class="showcaseSlideContainer">
                <ul id="showcaseCarousel6" class="jcarousel-skin-bill showcaseCarousel">    
                    <li>
                        <img src="/images/ppt/showcase-6.jpg" alt=""/>
                    </li>
                </ul>
                <a class="label" href="javascript:;">FULL SCREEN</a>
            </div>
            <!--end .showcaseSlideContainer-->
            <div class="rightPart">
                <h1>24 Hour Competition! Update<br /> a TopCoder Presentation!</h1>
                <p>TopCoder called on its members to design a marketing presentation for itself. A standard competition resulted in a striking format for a presentation about how the TopCoder process results in a much higher probability of project completion than the traditional labor model.</p> 
                <p class="boldText">1st Place <br/>ID: 92806<br/>Handle: <span>vlad_D</span><br/>Submitted on 10.16.2008 at 17:42 SGT</p>
            </div>
            <!--end .rightPart-->
        </div>
        <div class="footer"></div>
    </div>
    <!--end #showcaseModal-->
</body>
</html>
