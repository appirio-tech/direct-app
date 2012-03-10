<%--
  - Author: TCSASSEMBLER
  - Version: 1.1
  - Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (Release Assembly - Project Contest Fee Management Update 1) changes:
  - Initialized the page functions. This page renders the form for creating contest fees for billing accounts.
  - Version 1.1 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
  - Added contest fee base on percentage support.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="project_fee"/>
    <jsp:include page="../includes/paginationSetup.jsp"/>
    <!--[if IE 6]>
    <script type="text/javascript" src="/scripts/DD_belatedPNG.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></script>
    <script type="text/javascript" src="/scripts/ie6.js?v=205148"></script>
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
                            <span class="pic"><img src="/images/ie_icon.png" alt="Internet Explorer 8"/></span>
                            <a href="http://windows.microsoft.com/en-US/internet-explorer/downloads/ie-8"
                               class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>, 
                <strong class="ff"><a href="javascript:;">Firefox 5.0</a>
                    <span class="tip">
                        <span class="tipTop"></span>
                        <span class="tipBg">
                            <span class="pic"><img src="/images/ff_icon.png" alt="Firefox 5.0"/></span>
                            <a href="http://www.mozilla.com/en-US/firefox/fx/"
                               class="downloadSubmission"><span>DOWNLOAD</span></a>
                        </span>
                        <span class="tipBottom"></span>
                    </span>
                </strong>, 
                <strong class="safari"><a href="javascript:;">Safari 5</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/safari_icon.png" alt="Safari 5.0"/></span>
                                <a href="http://www.apple.com/safari/download/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                </strong> or 
                    <strong class="chrome"><a href="javascript:;">Google Chrome</a>
                        <span class="tip">
                            <span class="tipTop"></span>
                            <span class="tipBg">
                                <span class="pic"><img src="/images/chrome_icon.png" alt="Google Chrome"/></span>
                                <a href="http://www.google.com/chrome/" class="downloadSubmission"><span>DOWNLOAD</span></a>
                            </span>
                            <span class="tipBottom"></span>
                        </span>
                    </strong>
            </span>
        </div>
        <div class="noAsk">
            <a href="javascript:;" class="close">Close</a>

            <div class="clear"></div>
            <div class="noAakInner"><input type="checkbox" id="noAshAgain"/><label>Don&acute;t show again</label>
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

                <div id="mainContent">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <s:url action="dashboardNotifications" namespace="/" id="feeMainPage">
                                    <s:param name="pn">fee</s:param>
                                </s:url>
                                <strong><a href="<s:property value="#feeMainPage"/>">Contest Fee Management</a></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle">Default Contest Fees</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <s:form action="createContestFeesAction" method="post" validate="true">
                            <div class="container2" id="contestFeeEditDetail">
                                <div>
                                    <br/>
                                    <br/>

                                    <p style="width: 100%;text-align: center;">
                                        Create Contest Fees for
                                        <s:select name="projectId" list="billingAccountsWithNoFees"/>
                                    </p>

                                    <div style="color:red">
                                        <s:fielderror/>
                                    </div>
 
                                    <div id="contest_fee_type">
                                        <s:radio name="formData.contestFeeFixed" list="#{'true':'Fixed Contest Fee', 'false':'Member Cost Percentage Based Contest Fee'}"/>
                                        <s:textfield label="Percentage" name="formData.contestFeePercentage" size="20"/>
                                    </div>
                                    <table id="contestFeeEditDetail"
                                           class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0" cellspacing="0">
                                        <colgroup>
                                            <col width="40%"/>
                                            <col width="60%"/>
                                        </colgroup>

                                        <thead>
                                        <tr>
                                            <th>Type</th>
                                            <th>Value</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td colspan="2"><strong>Studio</strong></td>
                                        </tr>

                                        <s:iterator value="formData.contestFees" id="formData.contestFees"
                                                    status="status">
                                            <s:if test="studio">
                                                <tr>
                                                    <td><s:property value="contestTypeDescription"/></td>
                                                    <td>
                                                        <s:textfield name="formData.contestFees[%{#status.index}].contestFee"
                                                                     size="40"/>
                                                    </td>
                                                </tr>
                                            </s:if>
                                        </s:iterator>
                                        <tr>
                                            <td colspan="2"><strong>Software</strong></td>
                                        </tr>

                                        <s:iterator value="formData.contestFees" id="formData.contestFees"
                                                    status="status">
                                            <s:if test="!studio">
                                                <tr>
                                                    <td><s:property value="contestTypeDescription"/></td>
                                                    <td>
                                                        <s:textfield name="formData.contestFees[%{#status.index}].contestFee"
                                                                     size="40"/>
                                                    </td>
                                                </tr>
                                            </s:if>
                                        </s:iterator>
                                        </tbody>
                                    </table>
                                    <br/><br/>

                                    <p style="float:right;"><s:if test="%{formData.contestFees!=null}"><s:submit
                                            value="Submit"/> &nbsp; </s:if>
                                        <s:submit action="cancelFeesManagement" value="Cancel"
                                                  onclick="form.onsubmit=null"/></p>
                                    </s:form>

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

                    
                    