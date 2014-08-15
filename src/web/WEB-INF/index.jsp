<%--
  - Author: isv, winsty, Blues, Ghost_141, GreatKevin
  - Version: 2.1
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
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
  - Version 2.0 (TC Direct Rebranding - Homepage Update)
  - - Rebranding the home page.
  - Version 2.1 (TC - Direct Rebranding NewHome Page Social Login Update)
  - - Change the social login implementation from widget to direct links
  - - Add GitHub and Salesforce social login
  - Version 2.2 (TopCoder Direct - Update Login and add Admin Login)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.topcoder.shared.util.ApplicationServer,
                 com.topcoder.direct.services.view.util.DirectProperties" %>
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>[topcoder] Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/jqtransform.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/homepage.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/customFont.css" media="all" type="text/css"/>

    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script src="https://cdn.auth0.com/w2/auth0-1.6.4.min.js"></script>


    <script type="text/javascript">

        function isEnterKeyPressed(e) {
            if (window.event) return (window.event.keyCode == 13);
            if (e) return (e.which == 13);
            return false;
        }

        function submitOnEnter(e) {
            if (!isEnterKeyPressed(e)) return true;
            submitAuth0LoginForm();
            return false;
        }


        var auth0Login = new Auth0({
            domain: '<%=DirectProperties.DOMAIN_AUTH0%>',
            clientID: '<%=DirectProperties.CLIENT_ID_AUTH0%>',
            callbackURL: 'https://<%=DirectProperties.REG_SERVER_NAME%><%=DirectProperties.REDIRECT_URL_AUTH0%>'
        });

        var dbLogin = function () {
            var isError = false;
            try {
                auth0Login.login({
                    connection: '<%=DirectProperties.LDAP_AUTH0_CONNECTION_NAME%>',
                    username: $("#LoginForm_formData_username").val(),
                    password: $("#LoginForm_formData_password").val(),
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'
                }, function(error) {
                    $('.loginBoxInner .errorMessage').text(error.message);
                    $('.loginBoxInner .errorMessage').show();
                    isError=true;
                })
            } catch (e) {
                console.log(e);
            }

            if(isError == false) {
                $('.loginBoxInner .errorMessage').text('');
                $('.loginBoxInner .errorMessage').hide();
            }
        };

        $(document).ready(function(){


            $('.gPlusIcon').click( function () {
                auth0Login.login({
                    connection: 'google-oauth2',
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'}); // this tells Auth0 to send the user back to the main site after login. Please replace the var for current page URL.
            });

            $('.facebookIcon').click( function () {
                auth0Login.login({connection: 'facebook',
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'}); // this tells Auth0 to send the user back to the main site after login. Please replace the var for current page URL.
            });

            $('.twitterIcon').click( function () {
                auth0Login.login({connection: 'twitter',
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'}); // this tells Auth0 to send the user back to the main site after login. Please replace the var for current page URL.
            });

            $('.githubIcon').click( function () {
                auth0Login.login({connection: 'github',
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'});  // this tells Auth0 to send the user back to the main site after login. Please replace the var for current page URL.
            });

            $('.salesforceIcon').click( function () {
                auth0Login.login({connection: 'salesforce',
                    state: 'https://<%=ServerConfiguration.SERVER_NAME%>/direct/'});  // this tells Auth0 to send the user back to the main site after login. Please replace the var for current page URL.
            });

        })

    </script>

</head>

<body class="homePage" id="page">

<div id="landingPage">

    <div id="wrapper">
        <div id="header">
            <div class="headerInner">
                <a href="#" class="logo2"><img src="/images/new_image_logo.png" alt="TopCoder"/></a>
                <a href="#" class="logo"><img src="/images/new_text_logo.png" alt="TopCoder"/></a>

                <div class="clear"></div>


            </div>
            <!-- End .headerInner -->
        </div>
        <!-- End #header -->
        <div class="loginBox">
            <div class="loginBoxInner">
                <h2>Login to your account</h2>

                    <p class="errorMessage">

                    </p>

                    <p class="username inputF"><input type="text" placeholder="Username" onkeypress="submitOnEnter(event);" id="LoginForm_formData_username" value="" name="formData.username"><span></span></p>

                    <p class="password inputF"><input type="password" placeholder="Password" onkeypress="submitOnEnter(event);" id="LoginForm_formData_password" name="formData.password"><span></span></p>

                    <a class="login" href="javascript:;" onclick="submitAuth0LoginForm();">Login</a>

                    <p class="additonalAction jqtransform">
                        <span class="signForget"><a href="http://<%=ServerConfiguration.NEW_SERVER_NAME%>/?action=callback" target="_blank">Sign Up</a> | <a href="http://www.topcoder.com/password-recovery/" target="_blank">Forgot Password
                            ?</a></span>
                        <input type="checkbox" name="formData.remember" id="remember_me"><label>Remember me</label>
                    </p>

            </div>      
            <div class="socialNetwork">
                <a href="javascript:;" class="salesforceIcon socialLogin" title="Login with Salesforce"></a>
                <a href="javascript:;" class="githubIcon socialLogin" title="Login with GitHub"></a>
                <a href="javascript:;" class="twitterIcon socialLogin" title="Login with Twitter"></a>
                <a href="javascript:;" class="gPlusIcon socialLogin"  title="Login with Google+"></a>
                <a href="javascript:;" class="facebookIcon socialLogin" title="Login with Facebook"></a>
                <span>Login with :</span>
            </div>

        </div>
        <!-- End .loginBox -->

    </div>
    <!-- End #landingPage -->

    <div id="footer">
        <!--Update footer-->
        <div id="footerInner">

            <div class="copyright">
        <span>&#0169; Copyright TopCoder, Inc. 2001 - <script type="text/javascript">d = new Date();
        document.write(d.getFullYear());</script></span> &nbsp;|&nbsp;
                <a href="http://www.topcoder.com/community/how-it-works/privacy-policy/" target="_blank" title="Privacy Policy">Privacy
                    Policy</a> &nbsp;|&nbsp;
                <a href="http://www.topcoder.com/community/how-it-works/terms/" target="_blank" title="Terms of Use">Terms
                    of Use</a>
            </div>
            <!--End copyright-->
            <!--Add Feedback Dialog-->

        </div>
    </div>

    <!-- External javascripts -->
    <script type="text/javascript" src="/scripts/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="/scripts/scripts.js"></script>

</body>
<!-- End #page -->

</html>
