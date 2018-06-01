<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>TopCoder Direct</title>

    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- External CSS -->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/homepage.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery.jcarousel.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/thickbox.css" media="all" type="text/css"/>
    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="css/homepage-ie6.css"/>
    <![endif]-->

    <!-- External javascript -->
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js"></script>
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/dashboard.js"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js"></script>
</head>

<body id="page">
<div id="landingPage">

    <!-- The header of the landing page -->
    <div id="header">
        <div class="headerInner">

            <a href="#" class="logo"><img src="/images/logo.png" alt="TopCoder"/></a>
        </div>
        <!-- End .headerInner -->
    </div>
    <!-- End #header -->

    <div id="wrapper">
        <div class="wrapperTop">
            <div class="wrapperBottom">
                <div class="wrapperLeft">
                    <div class="wrapperRight">
                        <div class="wrapperTopLeft">
                            <div class="wrapperBottomLeft">
                                <div class="content">
                                    <div class="contentInner">
                                        <div class="area1content">
                                            <b><span style="color:red;">ERROR</span></b>
                                            <br/>
                                            <br/>
                                            <b>The form has already been processed or no token was supplied, please go back to the page and try again.</b>
                                            <br/>
                                            <br/>
                                            You may click <a href="javascript:history.back();">here</a> to return to
                                            the last page you were viewing.
                                            <br/>
                                            <br/>
                                            If you have a question or comment, please email
                                            <link:mailToDirectAssist/><br/>and be sure to include this timestamp:<br/>
                                            <%=new Date()%>
                                            <br/>
                                            <br/>
                                        </div>
                                        <!-- End .area1content -->

                                        <!-- this area will contain the login form, video area and somme links... -->
                                    </div>
                                    <!-- End .contentInner -->
                                </div>
                                <!-- end .content -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End #wrapper -->

    <jsp:include page="/WEB-INF/includes/footer.jsp"/>

</div>
<!-- End #landingPage -->
<jsp:include page="/WEB-INF/includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
