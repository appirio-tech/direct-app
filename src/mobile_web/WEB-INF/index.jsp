<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Home</title>
<link rel="stylesheet" href="/css/mobile/style.css" type="text/css" />
<!--[if IE 7]>
<link rel="stylesheet" href="/css/mobile/style_ie7.css" type="text/css" />
<![endif]-->
<link href='http://fonts.googleapis.com/css?family=Lato:400,700,900' rel='stylesheet' type='text/css' />

<script type="text/javascript" src="/scripts/jquery-1.4.1.min.js?v=176771"></script>
<script type="text/javascript" src="/scripts/mobile/index.js"></script>
<!--[if IE 7]>
<script type="text/javascript" src="/scripts/mobile/index_ie7.js"></script>
<![endif]-->
</head>

<body>
<!-- modal. for black background-->
<div class="modal"></div>
<!-- Begin .outer_container_showcase-->
<div class="outer_container_showcase">
	<div class="close_div"><a href="#" id="close_showcase"></a><div class="clear"></div></div>
    <!-- Begin .slide_container_outer-->
    <div class="slide_container_outer">
    <div class="slide_container_inner">
        <!-- Begin .container_showcase-->
        <div id="container_showcase">
            <div id="slides">
                <!-- Begin .slides_container -->
                <div class="slides_container">
                    <!-- Begin Slide 1-->
                    <div class="slide" id="slide_1">	
                        <div class="image_showcase" ><img src="/images/mobile/ipad_content_showcase_1.png" width="278" height="349" alt="Slide 1" /></div>
                        <div class="showcase_content">
                            <div class="showcase_title">TOPCODER MOBILE PLATFORM APP</div>
                            <p class="bigger">Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, 
                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <!-- End Slide 1-->
                    <!-- Begin Slide 2-->
                    <div class="slide" id="slide_2">	
                        <div class="image_showcase" ><img src="/images/mobile/ipad_content_showcase_2.png" width="278" height="349" alt="Slide 2" /></div>
                        <div class="showcase_content">
                            <div class="showcase_title">ACME IPHONE APP</div>
                            <p class="bigger">Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, 
                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <!-- End Slide 2-->
                    <!-- Begin Slide 3-->
                    <div class="slide" id="slide_3">	
                        <div class="image_showcase" ><img src="/images/mobile/ipad_content_showcase_3.png" width="278" height="349" alt="Slide 3" /></div>
                        <div class="showcase_content">
                            <div class="showcase_title">MEGAN COMPANY MOBILE DASHBOARD</div>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, 
                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <!-- End Slide 3-->
                    <!-- Begin Slide 4-->
                    <div class="slide" id="slide_4">	
                        <div class="image_showcase" ><img src="/images/mobile/ipad_content_showcase_4.png" width="278" height="349" alt="Slide 4" /></div>
                        <div class="showcase_content">
                            <div class="showcase_title">TOPCODER COCKPIT FOR IPAD</div>
                            <p class="bigger">Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, 
                            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                            <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <!-- End Slide 4-->
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
                <!-- End .slides_container -->
                <a href="#" class="prev" id="prevButton"></a>
                <a href="#" class="next" id="nextButton"></a>
                <div class="pagination">
                    <li><a href="#" class="linkSlide" id="linkSlide_1">1</a></li>
                    <li><a href="#" class="linkSlide" id="linkSlide_2">2</a></li>
                    <li><a href="#" class="linkSlide" id="linkSlide_3">3</a></li>
                    <li><a href="#" class="linkSlide" id="linkSlide_4">4</a></li>
                </div>
            </div>
        </div>
        <!-- End .container_showcase-->
    </div>
    </div>
    <!-- End .slide_container_outer-->
</div>
<!-- End .outer_container_showcase-->

<!-- Begin .outer_container_register-->
<div class="outer_container_register">
	<div class="close_div"><a href="#" id="close_register"></a><div class="clear"></div></div>
    <div class="register_container_outer">
    	<div class="register_container_inner">
    	<h1>REGISTER</h1>
    	<form action="" method="post" class="register_form">
        	<fieldset>		
            	<!-- Column 1-->
            	<div class="col" id="col_reg_1">
                	<div class="input_div">
                    	<div class="col_1">&nbsp;</div>
                        <div class="col_2"><h1>01.</h1></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">First Name :</div>
                        <div class="col_2"><input type="text" name="firstName" id="firstName" class="input_register" /></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Last Name :</div>
                        <div class="col_2"><input type="text" name="lastName" id="lastName" class="input_register" /></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Handle :</div>
                        <div class="col_2"><input type="text" name="handle" id="handle" class="input_register" /></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Email Name :</div>
                        <div class="col_2"><input type="text" name="email" id="email" class="input_register" /></div>
                        <div class="clear"></div>
                    </div>
                </div>
                <!-- Column 2-->
                <div class="col" id="col_reg_2">
                	<div class="input_div">
                    	<div class="col_1">&nbsp;</div>
                        <div class="col_2"><h1>02.</h1></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Password : </div>
                        <div class="col_2">
                        	<div class="pass_strength"><input type="password" name="password" id="password" class="input_register_2" /></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Confirm Password : </div>
                        <div class="col_2"><input type="password" name="re_password" id="re_password" class="input_register_2" /></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">Verification Code : </div>
                        <div class="col_2"><input type="text" name="veriCode" id="veriCode" class="input_register_2" /></div>
                        <div class="clear"></div>
                    </div>
                    <div class="input_div">
                    	<div class="col_1">&nbsp;</div>
                        <div class="col_2">
                        <div><img class="imgCode" src="" alt="" id="veriImg"/></div>
                        <a id="tryAnotherCode" href="#">Try Another Code</a>
                        </div>
                        <div class="clear"></div>
                    </div>
				</div>
                <!-- Column 3-->
                <div class="col" id="col_reg_3">
                	<div class="input_div">
                        <div class="col_2"><h1>03.</h1></div>
                        <div class="clear"></div>
                    </div>
                    <div>
                        <div class="left_acpt"><input type="checkbox" name="acpt" value="y" id="acpt" /></div>
                        <div class="right_acpt">
                        	<label for="acpt" class="acpt_label">I have read and accept the</label>
                            <br />
                            <a href="https://www.topcoder.com/reg/privacy_policy.jsp" rel="external" class="acct">Privacy Policy</a>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div>
                    	<input type="button" value="Submit" class="register_button_disabled" id="register_button" disabled="disabled" />
						<div class="reset_form"><a href="#" id="reset_reg_form">Reset</a></div>
                        <div class="error_reg_info">Please fill out all fields</div>
                    </div>
				</div>    
                <div class="clear"></div>                
            </fieldset>
        </form>
        </div>
    </div>
</div>
<!-- End .outer_container_register-->

<!-- Begin #header_wrapper-->
<div id="header_wrapper">
	<div id="header_nav">	
    	<div id="header_nav_content">
        	<a href="/mobile/index.action" id="logo_top"></a>
            <ul id="nav_menu">
                <c:if test="${empty sessionScope.userHandle}">
            	<li><a href="#" id="menu_start_new" class="navLogin startNP">Start new project</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.userHandle}">
                <li><a href="/direct/createNewProject.action" id="menu_start_new" class="startNP">Start new project</a></li>
                </c:if>
                <li><a href="http://www.topcoder.com/blog" id="menu_blog">Blog</a></li>
                <li><a href="http://www.topcoder.com/help" id="menu_help">Help</a></li>
                <li>
                    <c:if test="${empty sessionScope.userHandle}">
                	<div class="logindiv"><a href="#" id="menu_login" class="navLogin">Log in</a></div>
                    <div class="hide logged_acc">Hello <a href="#" id="profile">mobdev!</a></div>
                    </c:if>
                    <c:if test="${not empty sessionScope.userHandle}">
                	<div class="logindiv hide"><a href="#" id="menu_login" class="navLogin">Log in</a></div>
                    <div class="logged_acc">Hello <a href="http://www.topcoder.com/tc?module=SimpleSearch&ha=${sessionScope.userHandle}" id="profile">${sessionScope.userHandle}</a></div>
                    </c:if>
                </li>
            </ul>
            <!-- Begin #login_wrap-->
            <div id="login_wrap">
            	<form action="" method="post" id="login_form">
                <fieldset>
                    <div class="login_wrap_inner">
                        <div class="not_have_acc">
                        Need an account?<br />
                        <a href="#" id="show_register">Register Now!</a>
                        </div>
                    </div>
                    <div class="login_wrap_inner">
                        Handle<br />
                        <input type="text" name="handle" id="handle_login" class="login_input" />
                    </div>
                    <div class="login_wrap_inner pass">
                        Password<br />
                        <input type="password" name="password" id="password_login" class="login_input"  /><br />
                        <a href="https://www.topcoder.com/tc?module=RecoverPassword" class="forgot_pass_link">Forgot?</a>
                    </div>
                    <div class="login_wrap_inner">
                        <a href="#" id="proccess_login_button">Log In</a>
                    </div>
                    <div class="login_wrap_inner"><a href="#" id="cancel_login_button"></a></div>
                    <div class="clear"></div>
                </fieldset>
                </form>
            </div>
            <!-- End #login_wrap-->
            <div class="clear"></div>
        </div>
    </div>
    <!-- Begin #header_headline-->
    <div id="header_headline">
    	<div id="headline_content">
        	<!-- Right header -->
        	<div class="right">
                <div class="tablet_wraper">
                    <div class="tablet">
                        <div class="tablet_content">
                            <div class="tablet_content_inner">
                                <div class="tablet_top">What We Have Developed</div>
                                <div class="tablet_bottom">
                                    <ul class="menu_tablet">
                                        <li id="li_1">
                                            <a href="#0" id="menu_1" class="mySlidePaggination">
                                                <span class="showcase_trigger" id="tr_1"></span>
                                                <span class="table_cell"><span>TopCoder Mobile Platform</span></span>
                                                <span class="menu_content">
                                                    <span class="top">TopCoder Mobile Platform</span>
                                                    <span class="middle"><img alt="" src="/images/mobile/ipad_content_1.png" /></span>
                                                    <span class="bottom">Click to view details</span>
                                                </span>
                                            </a>                    
                                        </li>
                                        <li id="li_2">
                                            <a href="#1" id="menu_2" class="mySlidePaggination">
                                                <span class="showcase_trigger" id="tr_2"></span>
                                                <span class="table_cell"><span>Acme iPhone App</span></span>
                                                <span class="menu_content">
                                                    <span class="top">Acme iPhone App</span>
                                                    <span class="middle"><img alt="" src="/images/mobile/ipad_content_2.png" /></span>
                                                    <span class="bottom">Click to view details</span>
                                                </span>
                                            </a>                    
                                        </li>
                                        <li id="li_3">
                                            <a href="#2" id="menu_3" class="mySlidePaggination">
                                                <span class="showcase_trigger" id="tr_3"></span>
                                                <span class="table_cell"><span>Megan Company Mobile Dashboard</span></span>
                                                <span class="menu_content">
                                                    <span class="top">Megan Company Mobile Dashboard</span>
                                                    <span class="middle"><img alt="" src="/images/mobile/ipad_content_3.png" /></span>
                                                    <span class="bottom">Click to view details</span>
                                                </span>
                                            </a>                    
                                        </li>
                                        <li class="last" id="li_4">
                                            <a href="#3" id="menu_4" class="mySlidePaggination">
                                                <span class="showcase_trigger" id="tr_4"></span>
                                                <span class="table_cell"><span>TopCoder Cockpit for iPad</span></span>
                                                <span class="menu_content">
                                                    <span class="top">TopCoder Cockpit for iPad</span>
                                                    <span class="middle"><img alt="" src="/images/mobile/ipad_content_4.png" /></span>
                                                    <span class="bottom">Click to view details</span>
                                                </span>
                                            </a>                    
                                        </li>
                                    </ul>
                                    <div class="menu_content_overlay">&nbsp;</div>
                                </div>
                            </div>
                        </div>
                        <a href="#" id="tablet_home_button"></a>
                    </div>
                </div>
            </div>
            <!-- Left header -->
            <div class="left">
            	<!-- Begin #headline_title_wrapper -->
                <!-- This title is verticaly dynamic, will wrap overflowed text-->
                <div id="headline_title_wrapper">
                    <div id="headline_title_top"></div>
                    <div id="headline_title_middle">
                        <div id="headline_middle_title_top">                    	
                            <div class="top">Your software wants</div>
                            <div class="bottom">to take road trip</div>
                        </div>
                    </div>
                    <div id="headline_title_bottom"></div>
                    <div id="headline_subtitle">
                        <div id="headline_subtitle_top">                    
                            TopCoder develops application for all mobile platforms:
                        </div>
                    </div>
                    <div id="headline_subtitle_bottom"></div>
                </div>
                <!-- End #headline_title_wrapper -->
                <!-- Begin #phone_wrapper -->
                <div id="phone_wrapper">
                    <div class="phone">
                        <img alt="" src="/images/mobile/phone_01.png" width="91" height="110" />
                        <div class="phone_tooltip">iOs</div>
                    </div>
                    <div class="phone">
                        <img alt="" src="/images/mobile/phone_02.png" width="91" height="110" />
                        <div class="phone_tooltip">Android</div>
                    </div>
                    <div class="phone">
                        <img alt="" src="/images/mobile/phone_03.png" width="91" height="110" />
                        <div class="phone_tooltip">Blackberry</div>
                    </div>
                    <div class="phone">
                        <img alt="" src="/images/mobile/phone_04.png" width="91" height="110" />
                        <div class="phone_tooltip">any other</div>
                    </div>
                    <div class="clear"></div>
                </div>
                <!-- End #phone_wrapper -->
                <!-- Begin #get_started_wrapper -->
                <div id="get_started_wrapper">
                    <div class="get_started_button_div">
                    <c:if test="${empty sessionScope.userHandle}">
                    <a href="#" class="navLogin startNP" id="get_started_button">Start mobile app development</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.userHandle}">
                    <a href="/direct/createNewProject.action" class="startNP" id="get_started_button">Start mobile app development</a>
                    </c:if>
                    </div>
                    <div class="get_started_text">
                        Get started today with application porting or new application development using TopCoder's unique competitive process.
                    </div>
                    <div class="clear"></div>
                </div>
                <!-- End #get_started_wrapper -->
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <!-- End #header_headline-->
</div>
<!-- End #header_wrapper-->

<!-- Begin #content_wrapper-->
<div id="content_wrapper" class="content_home">
    <div id="step_div">
        <div class="step_div_item" id="step_1">
            <div class="img"><img alt="" src="/images/mobile/step_1.png" width="167" height="108" /></div>
            <h1><span class="step_number">01.</span>Tell us what you need</h1>
            <div class="step_content">
                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            </div>
        </div>
        <div class="step_div_item" id="step_2">
            <div class="img"><img alt="" src="/images/mobile/step_2.png" width="128" height="118" /></div>
            <h1><span class="step_number">02.</span>Let the world compete</h1>
            <div class="step_content">
                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            </div>
        </div>
        <div class="step_div_item" id="step_3">
            <div class="img"><img alt="" src="/images/mobile/step_3.png" width="122" height="137" /></div>
            <h1><span class="step_number">03.</span>Get winning results</h1>
            <div class="step_content">
                Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!-- End #content_wrapper-->

<!-- Begin .press_container-->
<div class="press_container">
    <div class="press_div">
        <span class="press_button">In the press</span>
        <div class="press_col_container">
            <div class="press_col_head" id="pres_col_head_1">
                <h1><a href="#">Duis aute irure dolor</a></h1>
            </div>
            <div class="press_col_head" id="pres_col_head_2">
                <h1><a href="#">Ut enim ad minim</a></h1>
            </div>
            <div class="press_col_head" id="pres_col_head_3">
                <h1><a href="#">Excepteur sint occaecat</a></h1>
            </div>
            <div class="press_col_head" id="pres_col_head_4">
                <h1><a href="#">Exercitation ullamco laboris nisi ut aliquip ex ea commodo</a></h1>
            </div>
            <div class="clear"></div>
            <div class="press_col" id="pres_col_1">
                <a href="#">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua</a>
            </div>
            <div class="press_col" id="pres_col_2">
                <a href="#">Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</a>
            </div>
            <div class="press_col" id="pres_col_3">
                <a href="#">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sunt in culpa qui officia deserunt mollit anim id est laborum.</a>
            </div>
            <div class="press_col" id="pres_col_4">
                <a href="#">Lorem ipsum dolor sit amet, consectetur adipisicing elit.</a>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<!-- End .press_container-->

<!-- Begin #footer-->
<div id="footer">
	<!-- Begin #footer_top-->
	<div id="footer_top">
    	<div id="footer_top_content">
            <ul class="link_footer">
            	<li><a href="http://www.topcoder.com">TopCoder Home</a></li>
                <li><a href="http://community.topcoder.com">Community</a></li>
                <li><a href="http://www.topcoder.com/direct">Cockpit</a></li>
                <li><a href="#">Duis aute irure</a></li>
            </ul>
            <ul class="link_footer">
            	<li><a href="#">Duis aute irure</a></li> 
                <li><a href="#">Dolor in reprehenderit</a></li> 
                <li><a href="#">Voluptate velit esse</a></li> 
                <li><a href="#">Cillum dolore</a></li> 
            </ul>
            <div id="share_wrap">
            	<span class="share_title">Share : </span>
                <a href="https://www.facebook.com/TopCoderInc" class="facebook_link share_link"></a>
                <a href="https://www.twitter.com/TopCoder" class="twitter_link share_link"></a>
                <a href="https://plus.google.com/104268008777050019973" class="gplus_link share_link"></a>
                <a href="https://www.linkedin.com/company/topcoder" class="lin_link share_link"></a>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <!-- End #footer_top-->
    <!-- Begin #footer_bottom-->
    <div id="footer_bottom">
    	<div id="footer_bottom_content">
            Copyright TopCoder, Inc. 2001-<script type="text/javascript">d=new Date();document.write(d.getFullYear());</script>
            <a href="http://www.topcoder.com" id="footer_logo"></a>
        </div>
    </div>
    <!-- End #footer_bottom-->
</div>
<!-- End #footer-->
</body>
</html>
