<%--
  - Author: minhu, Ghost_141, duxiaoyang
  - Copyright (C) 2012 - 2017 TopCoder Inc., All Rights Reserved.
  -
  - Version: 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
  -
  - Version 1.1 Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
  - - Fix text inconsistency bug.
  - - Remove container2BottomLeft and container2BottomRight class in pagination part.
  -
  - Version 1.2 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
  -
  - Version 1.3 (Topcoder - Migrate Struts 2.3 to 2.5 For Direct App)
  - - Replace id attribute for s:url with var attribute
  -
  - The page to display the customer platform fee list.
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
    <script type="text/javascript" src="/scripts/DD_belatedPNG.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></scrif
    <script type="text/javascript" src="/scripts/ie6.js?v=205148"></script>
<![endif]-->
</head>

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
                                <h2 class="title contestTitle">Platform fees</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <div class="container2 resultTableContainer" id="platformFees">
                                <div>
                                        <table class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0"
                                           cellspacing="0">

                                        <colgroup>
                                            <col width="28%" />
                                            <col width="20%" />
                                            <col width="20%" />
											<col width="20%" />
                                            <col width="12%" />
                                        </colgroup>

                                        <thead>
                                            <tr>
                                                <th>Client Name</th>
                                                <th>Payment Date</th>
                                                <th>Amount</th>
                                                <th>Modified Date</th>
												<th></th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <s:iterator value="result" status="status" var="item">
                                                <tr>
													<td class="first">
														<s:property value="clientName" />
													</td>
													<td class="first">
													    <fmt:formatDate pattern="yyyy-MM-dd" value="${item['paymentDate']}"/>
													</td>
													<td class="first">
														<s:property value="amount" />
													</td>
                                                    <td class="first">
														<s:property value="modifyDate" />
                                                    </td>
                                                    <td class="first">
                                                        <a href='<s:url action="enterUpdateCustomerPlatformFee" namespace="/"><s:param name="fee.id" value="id"/></s:url>'>
                                                            <img alt="" src="/images/edit-icon.png"/> Edit</a>
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
                            <!-- End .container 2 -->
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
