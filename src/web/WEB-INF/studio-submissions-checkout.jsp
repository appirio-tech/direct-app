<%--
  - Author: flexme, TCSDEVELOPER
  -
  - Version 1.1 (Direct Submission Viewer Release 4 ) change notes: Added logic for handling "Confirmed" submissions.
  -
  - Version: 1.1 (Submission Viewer Release 3 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the submissions for Studio contest for checking out.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/contest/submissionViewer/submissionViewerHtmlHead.jsp"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                </s:url>"><s:property
                                        value="sessionData.currentProjectContext.name"/></a> &gt;
                                <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                            </div>
                            <div class="areaHeader">
                                <h2 class="title contestTitle"><s:property
                                        value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->
                            
                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>
                                
                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2BottomClear">
                                            <div class="containerNoPadding">
                                                
                                                <div class="bankSelectionButtonMilestone">
                                                    <div id="bankSelectionButton">
                                                        <jsp:include page="includes/contest/submissionViewer/checkoutButtons.jsp"/>
                                                    </div>
                                                </div>
                                                
                                                <s:if test="!(formData.roundType.toString() == 'FINAL' && viewData.hasCheckout == true)">
                                                <div class="SubmissionSlotTitle">
                                                    <s:if test="formData.roundType.toString() == 'MILESTONE'">
                                                        <h3>Milestone Selection Summary</h3>
                                                    </s:if>
                                                    <s:else>
                                                        <h3>Final Selection Summary</h3>
                                                    </s:else>
                                                </div>
                                                
                                                <div id="submissionList">
                                                    <table> 
                                                        <thead>
                                                            <tr>
                                                                <th class="first"></th>
                                                                <th>Thumbnail</th>
                                                                <th>Submission ID</th>
                                                                <th>Client Feedback</th>
                                                                <th class="last"></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                        <s:iterator value="viewData.contestSubmissions">
                                                            <s:set var="contestId" value="contestId" scope="page"/>
                                                            <s:set var="submissionId" value="submissionId" scope="page"/>
                                                            <s:set var="feedbackText" value="feedbackText" scope="page"/>
                                                            <s:set var="userRank" value="userRank" scope="page"/>
                                                            <s:set var="awardMilestonePrize" value="awardMilestonePrize" scope="page"/>
                                                            <s:if test="(viewData.hasCheckout == false) || (formData.roundType.toString() == 'MILESTONE' && top.isAwardMilestonePrize()) || (formData.roundType.toString() == 'FINAL' && top.userRank > 0)">
                                                            <ui:studioCheckoutSubmissionItem contestId="${contestId}"
                                                                                             isConfirmed="${viewData.hasCheckout}"
                                                                                             submissionId="${submissionId}"
                                                                                             feedbackText="${feedbackText}"/>
                                                            </s:if>
                                                        </s:iterator>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                </s:if>
                                                <!-- End #submissionList -->
                                                
                                                <s:if test="formData.roundType.toString() == 'FINAL'">
                                                <div id="additionalPurchase" style="display:none;">
                                                    <h3>Additional Purchases</h3>
                                                    
                                                    <div class="additionalPurchaseTitle">
                                                        <p>Submission that will be purchased for <strong><fmt:formatNumber value="${viewData.additionalPrize}" type="currency" pattern="$.00" /></strong> each</p>
                                                    </div>
                                                    
                                                    <div class="additionalPurchaseContent">
                                                        <p class="information">These are the submissions you added in the <strong>Extra Purchases</strong> slot. Choose the submissions that you would like to purchase.</p>
                                                        <div class="additionalButton">
                                                            <a href="#" id="checkAll" class="buttonBankSelection"><span class="left"><span class="right">Select All</span></span></a>
                                                            <a href="#" id="checkNone" class="buttonBankSelection"><span class="left"><span class="right">Select None</span></span></a>
                                                        </div>
                                                        <div id="purchasePreview" class="additionalPreview"></div>
                                                    </div>
                                                </div><!-- End #additionalPurchase -->
                                                
                                                <div id="purchaseSummary">
                                                    <div class="selectPaymentTitle">
                                                        <h3>Purchase Summary</h3>
                                                        <div class="paymentMethod">
                                                            <label>Billing Account:</label>
                                                            <div id="selectPaymentWrapper">
                                                                <select id="slPaymentType">
                                                                    <s:iterator value="viewData.billingAccounts">
                                                                    <option value="${id}">${name}</option>
                                                                    </s:iterator>
                                                                </select>
                                                            </div>
                                                        </div>
                                                     </div>
                                                     <table id="summary"></table>
                                                 </div><!-- End #purchaseSummary -->
                                                </s:if>

                                                <s:if test="formData.roundType.toString() == 'MILESTONE'">
                                                <div id="milestoneRoundComment">
                                                    <table>
                                                        <thead>
                                                            <tr>
                                                                <th class="first"></th>
                                                                <th>Overall comments for all members competing in this contest</th>
                                                                <th class="last"></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                        <tr>
                                                            <td></td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${viewData.hasCheckout}">
                                                                        ${viewData.milestoneRoundFeedbackText}
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <textarea class="txtFeedback" cols="10" rows="5"
                                                                                  id="feedbackTextMilestoneRound">${viewData.milestoneRoundFeedbackText}</textarea>
                                                                        <a href="javascript:" class="button6" rel="${contestId}"
                                                                           id="saveGeneralFeedback">
                                                                            <span class="left"><span class="right">Save Feedback</span></span></a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td></td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                </s:if>

                                                <div class="bankSelectionButtonBottom">
                                                    <jsp:include page="includes/contest/submissionViewer/checkoutButtons.jsp"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div><!-- End .container2 -->
                            
                            <a href="javascript:;" class="button1 backToTop"><span>Back To Top</span></a>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div><!-- End #mainContent --> 
                
                <jsp:include page="includes/footer.jsp"/>
            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/contest/submissionViewer/contestVars.jsp"/>
<input type="hidden" id="prizeNumber" value="${viewData.prizeNumber}" />
<input type="hidden" id="additionalPrize" value="${viewData.additionalPrize}" />
<input type="hidden" id="milestonePrize" value="${viewData.milestonePrize}" />
<input type="hidden" id="milestoneAwardNumber" value="${viewData.milestoneAwardNumber}" />
<input type="hidden" id="hasCheckout" value="${viewData.hasCheckout}" />
<input type="hidden" id="paidSubmissions" value="${viewData.paidSubmissions}" />
<s:iterator value="viewData.prizes">
<s:set var="place" value="place" scope="page"/>
<s:set var="amount" value="amount" scope="page"/>
<input type="hidden" id="prize_${place}" value="${amount}" />
</s:iterator>
<jsp:include page="includes/contest/submissionViewer/checkoutForm.jsp" />
<jsp:include page="includes/popups.jsp"/>
</body>
<!-- End #page -->

</html>