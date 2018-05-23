<%--
  - Author: Ghost_141, duxiaoyang
  - Version: 1.4
  - Copyright (C) 2010 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (Module Assembly - Project Contest Fee Management) changes:
  - Initialized the page functions.
  -
  - Version 1.1 (Release Assembly - Project Contest Fee Management) changes:
  - Added a new clientName column to the billing account table.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) changes:
  - Remove the container2BottomLeft and container2BottomRight class in pagination part.
  -
  - Version 1.3 (TC Direct Rebranding Assembly Dashboard and Admin related pages)
  - - Update the header for rebranding
  -
  - Version 1.4 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace id attribute for s:url with var attribute
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:adminPageType tab="contestFee"/>
    <jsp:include page="../includes/paginationSetup.jsp"/>
<!--[if IE 6]>
    <script type="text/javascript" src="/scripts/DD_belatedPNG.js"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js"></script>
    <script type="text/javascript" src="/scripts/ie6.js"></script>
<![endif]-->
</head>

<c:set var="CURRENT_TAB" scope="request" value="settings"/>
<body id="page">
<!-- ie6 notification module -->
<div id="ie6-notification">
    <div class="content">
        <div class="alert">
            <span class="title">You&acute;re using Internet Explorer 6 or Lower. </span>
            <span class="desc">For the full features supported and best experience we strongly recommend upgrading to
                <strong class="ie8"><a href="javascript:;">Internet Explorer 8</a>
                    <span class="tip">
                        <span class="tipTop"></span>
                        <span class="tipBg">
                            <span class="pic"><img src="/images/ie_icon.png" alt="Internet Explorer 8" /></span>
                            <a href="https://windows.microsoft.com/en-US/internet-explorer/downloads/ie-8" class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>,
                <strong class="ff"><a href="javascript:;">Firefox 5.0</a>
                    <span class="tip">
                        <span class="tipTop"></span>
                        <span class="tipBg">
                            <span class="pic"><img src="/images/ff_icon.png" alt="Firefox 5.0" /></span>
                            <a href="https://www.mozilla.com/en-US/firefox/fx/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>,
                <strong class="safari"><a href="javascript:;">Safari 5</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/safari_icon.png" alt="Safari 5.0" /></span>
                                <a href="https://www.apple.com/safari/download/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong> or
                    <strong class="chrome"><a href="javascript:;">Google Chrome</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/chrome_icon.png" alt="Google Chrome" /></span>
                                <a href="https://www.google.com/chrome/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>
            </span>
        </div>
        <div class="noAsk">
            <a href="javascript:;" class="close">Close</a>
            <div class="clear"></div>
            <div class="noAakInner"><input type="checkbox" id="noAshAgain" /><label>Don&acute;t show again</label>
          </div>
        </div>
    </div>
</div>
<!-- ie6 notification module ends -->

<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;

                                <s:url action="contestFee" namespace="/settings/admin" var="feeMainPage">
                                </s:url>
                                <strong><a href="<s:property value="#feeMainPage"/>">Challenge Fee Management</a></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Challenge Fees</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <div class="container2 resultTableContainer" id="billingAccounts">
                                <div>
                                        <table id="billingAccounts" class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0"
                                           cellspacing="0">

                                        <colgroup>
                                            <col width="30%" />
											<col width="30%" />
                                            <col width="40%" />
                                        </colgroup>

                                        <thead>
                                            <tr>
                                                <th>Billing Account Id</th>
												<th>Client Name</th>
                                                <th>Billing Account</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <s:iterator value="result.items" status="status">
                                                <s:set value="projectId" var="projectId" scope="page"/>
                                                <s:set value="name" var="name" scope="page"/>
                                                <tr>
                                                    <td class="first"><s:property value="projectId"/></td>
													<td class="first">
														<s:property value="clientName" />
													</td>
                                                    <td class="first">
                                                        <a href="listContestFeesAction.action?projectId=${projectId}"><s:property value="name"/></a>
                                                    </td>

                                                </tr>
                                            </s:iterator>

                                        </tbody>
                                    </table>




                                </div>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div>
                                                <div>
                                                    <div class="panel tableControlPanel"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->
                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="../includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
