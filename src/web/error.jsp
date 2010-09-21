<%@ page import="java.io.PrintWriter" %>
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
    <link rel="stylesheet" href="/css/screen.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/homepage.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/jquery.jcarousel.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/thickbox.css" media="all" type="text/css"/>
    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="css/homepage-ie6.css"/>
    <![endif]-->

    <!-- External javascript -->
</head>

<body id="page" onload="loadHomePageFeeds();">
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
                                            <s:if test="exception.class.simpleName == 'PermissionServiceException'">
                                            	 <b>Sorry, you do not have permission to view the requested page.</b>
                                            </s:if>
                                            <s:else>
                                            	<b>An error has occurred when attempting to process your request.</b>
                                            </s:else>                                            	                                            
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

</body>
<!-- End #page -->

</html>