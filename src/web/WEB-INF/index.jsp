<%--
  - Author: isv, winsty, Blues, Ghost_141, GreatKevin
  - Version: 2.0
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
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.topcoder.shared.util.ApplicationServer,
                 com.topcoder.direct.services.view.util.DirectProperties" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>TopCoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- External CSS -->
    <link rel="stylesheet" href="/css/direct/jqtransform.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/homepage.css" media="all" type="text/css"/>

    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script src="/scripts/auth0-widget-2.3.6.min.js"></script>
    <script src="https://d19p4zemcycm7a.cloudfront.net/w2/auth0-widget-2.3.6.min.js"></script>

    <script type="text/javascript">

        function isEnterKeyPressed(e) {
            if (window.event) return (window.event.keyCode == 13);
            if (e) return (e.which == 13);
            return false;
        }

        function submitOnEnter(e) {
            if (!isEnterKeyPressed(e)) return true;
            $('#LoginForm').submit();
            return false;
        }

        var widget = new Auth0Widget({
            domain: '<%=DirectProperties.DOMAIN_AUTH0%>',
            clientID: '<%=DirectProperties.CLIENT_ID_AUTH0%>',
            callbackURL: 'https://<%=DirectProperties.REG_SERVER_NAME%><%=DirectProperties.REDIRECT_URL_AUTH0%>'
        });

        function showAuth0Widget() {
            widget.signin({
                state: 'https://<%=ApplicationServer.SERVER_NAME%>/direct/',
                icon: 'http://www.topcoder.com/i/24x24_brackets.png',
                showIcon: true}).on('signin_ready', function () {
                        $('.a0-email input').each(function () {
                            $(this)
                                    .clone()
                                    .attr('type', 'text')
                                    .attr('placeholder', 'Username')
                                    .attr('title', 'Username')
                                    .insertAfter($(this)).prev().remove();
                        });
                    });
        }

        $(function () {
            $('.socialLogin').click(function () {
                showAuth0Widget();
            });
        });

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

                <s:form action="login" namespace="/" id="LoginForm" method="post" onsubmit="return submitLoginForm();">

                    <p class="errorMessage">
                        <s:if test="hasActionErrors()">
                            <s:iterator value="actionErrors">
                                <s:property escape="false"/>
                            </s:iterator>
                        </s:if>
                    </p>

                    <p class="username inputF"><s:textfield name="formData.username" value="" placeholder="Username"
                                                            onkeypress="submitOnEnter(event);"/><span></span></p>

                    <p class="password inputF"><s:password name="formData.password" placeholder="Password"
                                                           onkeypress="submitOnEnter(event);"/><span></span></p>

                    <a class="login" href="javascript:;" onclick="submitLoginForm();">Login</a>
                        <s:submit cssStyle="display:none;"/>

                    <c:if test="${not empty sessionScope.redirectBackUrl}">
                        <s:hidden name="forwardUrl" value="%{#session.redirectBackUrl}" />
                    </c:if>

                    <p class="additonalAction jqtransform">
                        <span class="signForget"><a href="http://www.topcoder.com/?action=callback" target="_blank">Sign Up</a> | <a href="https://community.topcoder.com/tc?module=FindUser" target="_blank">Forgot Password
                            ?</a></span>
                        <input type="checkbox" name="formData.remember" id="remember_me"><label>Remember me</label>
                    </p>

                </s:form>
            </div>      
            <div class="socialNetwork">
                <a href="javascript:;" class="linkedInIcon socialLogin" title="Sign up with LinkedIn"></a>
                <a href="javascript:;" class="twitterIcon socialLogin" title="Sign up with Twitter"></a>
                <a href="javascript:;" class="gPlusIcon socialLogin"  title="Sign up with Google+"></a>
                <a href="javascript:;" class="facebookIcon socialLogin" title="Sign up with Facebook"></a>
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
        <span>&#0169; Copyright TopCoder, Inc. 2001-<script type="text/javascript">d = new Date();
        document.write(d.getFullYear());</script></span>
                <a href="http://www.topcoder.com/community/how-it-works/privacy-policy/" target="_blank" title="Privacy Policy">Privacy
                    Policy</a>
                <a href="http://www.topcoder.com/community/how-it-works/terms/" target="_blank" title="Terms of Use">Terms
                    of Use</a>
            </div>
            <!--End copyright-->
            <!--Add Feedback Dialog-->

        </div>
    </div>



    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400" rel="stylesheet" type="text/css"/>

    <!-- External javascripts -->
    <script type="text/javascript" src="/scripts/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="/scripts/scripts.js"></script>

</body>
<!-- End #page -->

</html>
