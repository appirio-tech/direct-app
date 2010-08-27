<%--
  - Author: flexme
  -
  - Version: 1.0 (Submission Viewer Release 3 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the submissions for Studio contest for checking out Confirmation.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <script type="text/javascript" src="/scripts/jquery-1.4.1.min.js"></script>
    <script type="text/javascript" src="/scripts/confirmCheckout.js"></script>
    <script type="text/javascript" src="/scripts/studio.js"></script>
    <jsp:include page="includes/htmlhead.jsp"/>
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
                                <a href="<s:url action="currentProjectDetails" namespace="/"/>"><s:property
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
                                                <div class="SubmissionSlotTitle"> 
                                                    <h3 class="red">Checkout Confirmation</h3> 
                                                </div> <!-- End .SubmissionSlotTitle -->
                                                <div id="submissionList">
                                                    <table> 
                                                        <thead> 
                                                            <tr> 
                                                                <th class="first"></th> 
                                                                <th>Placement</th> 
                                                                <th class="left">Submission Details</th> 
                                                                <th class="last"></th> 
                                                            </tr>
                                                        </thead> 
                                                        <tbody>
                                                        <s:iterator value="placePayments">
                                                            <s:set var="contestId" value="contestId" scope="page"/>
                                                            <s:set var="submissionId" value="submissionId" scope="page"/>
                                                            <s:set var="feedbackText" value="feedbackText" scope="page"/>
                                                            <ui:studioCheckoutConfirmItem feedbackText="${feedbackText}" contestId="${contestId}" submissionId="${submissionId}" />
                                                        </s:iterator>
                                                        <s:iterator value="additionalPayments">
                                                            <s:set var="contestId" value="contestId" scope="page"/>
                                                            <s:set var="submissionId" value="submissionId" scope="page"/>
                                                            <s:set var="feedbackText" value="feedbackText" scope="page"/>
                                                            <ui:studioCheckoutConfirmItem feedbackText="${feedbackText}" contestId="${contestId}" submissionId="${submissionId}" styleClass="dollarSlots"/>
                                                        </s:iterator>
                                                        </tbody>
                                                    </table>
                                                </div><!-- End #submissionList --> 
                                                <div id="purchaseSummary">
                                                    <div class="selectPaymentTitle"><h3>Receipt</h3></div>
                                                    <c:set var="additionalNumber" value="${fn:length(additionalPayments)}" scope="page" />
                                                    <s:set var="positionNames" value="{'1st','2nd','3rd','4th','5th'}" scope="page" />
                                                    <table>
                                                        <tbody>
                                                            <c:forEach var="placePay" items="${placePrizes}" varStatus="status">
                                                            <tr>
                                                                <td class="label">${positionNames[status.index]} Place Prize:</td>
                                                                <td>1</td>
                                                                <td class="sum"><fmt:formatNumber value="${placePay}" type="currency" pattern="$.00" /></td>
                                                                <td class="sum"><fmt:formatNumber value="${placePay}" type="currency" pattern="$.00" /></td>
                                                            </tr>
                                                            </c:forEach>
                                                            <s:if test="additionalNumber > 0">
                                                            <tr>
                                                                <td class="label">Additional Purchases:</td>
                                                                <td>${additionalNumber}</td>
                                                                <td class="sum"><fmt:formatNumber value="${viewData.additionalPrize}" type="currency" pattern="$.00" /></td>
                                                                <td class="sum"><fmt:formatNumber value="${viewData.additionalPrize * additionalNumber}" type="currency" pattern="$.00" /></td>
                                                            </tr>
                                                            </s:if>
                                                            <s:if test="viewData.milestoneAwardNumber > 0">
                                                            <tr>
                                                                <td class="label">Milestone Prizes:</td>
                                                                <td>${viewData.milestoneAwardNumber}</td>
                                                                <td class="sum"><fmt:formatNumber value="${viewData.milestonePrize}" type="currency" pattern="$.00" /></td>
                                                                <td class="sum"><fmt:formatNumber value="${viewData.milestonePrize * viewData.milestoneAwardNumber}" type="currency" pattern="$.00" /></td>
                                                            </tr>
                                                            </s:if>
                                                            <tr class="total">
                                                                <td class="label">Total</td>
                                                                <td colspan="3" class="sum"><fmt:formatNumber value="${totalPayments}" type="currency" pattern="$.00" /></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a href="javascript:;" class="button1 backToTop"><span>Back To Top</span></a>
                            </div>
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
</body>
<!-- End #page -->

</html>