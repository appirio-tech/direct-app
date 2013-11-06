<%--
  - Author: minhu, TCSASSEMBLER
  - Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
  - Version: 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the page type to admin
  -
  - The page used to create/udpate the customer platform fee.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:adminPageType tab="contestFee"/>
	<jsp:include page="../includes/paginationSetup.jsp"/>
    <link rel="stylesheet" href="/css/direct/platform-fee.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/platform-fee.js"></script>
<!--[if IE 6]>
    <script type="text/javascript" src="/scripts/DD_belatedPNG.js?v=185283"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js?v=187251"></script>
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
                                <s:url action="contestFee" namespace="/settings/admin" id="feeMainPage">
                                </s:url>
                                <strong><a href="<s:property value="#feeMainPage"/>">Contest Fee Management</a></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:if test="fee.id==0">Create</s:if><s:else>Update</s:else> Platform Fee</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <s:form method="post" action="createUpdateCustomerPlatformFee" namespace="/"
                                    id="savePlatformFeeForm">
                            <div class="container2 platformFeeContainer">
                              <div class="platformFeeColumns">
                                <br/>                               
                                <div style="color:red">
                                    <s:actionerror />
                                    <s:fielderror />
                                </div>
                                
                                <s:hidden id="fee_id" name="fee.id"/>
                                
                                <div class="platformFeeColumn">
                                    <div>
                                        <label>Customer Name:</label>
                                    </div>
                                    <s:if test="fee.id > 0"><s:hidden name="fee.clientId" id="fee_clientId"/><label class="contentLbl"><s:property value="clients.get(fee.clientId)"/></label>
                                    </s:if><s:else><s:select list="clients" id="fee_clientId" name="fee.clientId" headerKey="-1" headerValue="-- Please Select --" size="1"/>
                                    </s:else>
                                    
                                </div>
                                   
                                <div class="platformFeeColumn">
                                    <div>
                                        <label for="paymentDate">Payment Date:</label>
                                    </div>        
                                    <span class="hide" id="editPaymentDate"><fmt:formatDate pattern="MM/dd/yyyy" value="${fee.paymentDate}"/></span>
                                    <s:textfield name="fee.paymentDate" readonly="true"
                                                 id="paymentDate" value=""
                                                 cssClass="text fLeft date-pick"/>
                                </div>
                                    
                                <div class="platformFeeColumn">
                                    <div>
                                        <label for="fee_amount">Amount:</label>
                                    </div>
                                    <s:textfield name="fee.amount" id="fee_amount" cssClass="text"/>
                                </div>
                                
                                <div class="saveButtonBox">
                                    <a class="button6 applyButton" href="<s:property value="#feeMainPage"/>" id="cancelPlatformFee"><span class="left"><span class="right">CANCEL</span></span></a>
                                    <a class="button6 applyButton" href="javascript:" id="submitPlatformFee"><span class="left"><span class="right">SAVE</span></span></a>
                                </div>

                              </div>
                            </div>
                            </s:form>
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

                    
                    
