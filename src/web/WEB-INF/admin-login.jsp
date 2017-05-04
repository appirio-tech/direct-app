<%--
  - Author: Veve, duxiaoyang
  - Version: 1.1
  - Copyright (C) 2014 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the admin login page for TC Direct application.
  -
  - Version 1.0 (TopCoder Direct - Update Login and add Admin Login)
  -
  - Version 1.1 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace escape attribute for s:property with escapeHtml attribute
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            $('#LoginForm').submit();
            return false;
        }

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
                <h2>Admin Login</h2>

                <s:form action="home" namespace="/" id="LoginForm" method="post" onsubmit="return submitLoginForm();">

                    <p class="errorMessage">
                        <s:if test="hasActionErrors()">
                            <s:iterator value="actionErrors">
                                <s:property escapeHtml="false"/>
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
                        <s:hidden name="forwardUrl" value="%{#session.redirectBackUrl}"/>
                    </c:if>

                    <p class="additonalAction jqtransform">
                        <input type="checkbox" name="formData.remember" id="remember_me"><label>Remember me</label>
                    </p>

                </s:form>

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
                <a href="http://www.topcoder.com/community/how-it-works/privacy-policy/" target="_blank"
                   title="Privacy Policy">Privacy
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
