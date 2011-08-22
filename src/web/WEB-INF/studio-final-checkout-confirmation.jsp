<%--
  - Author: FireIce
  -
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
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
                                                        <s:iterator value="viewData.contestSubmissions">
                                                            <s:set var="contestId" value="projectId" scope="page"/>
                                                            <s:set var="submissionId" value="id" scope="page"/>
                                                            <s:set var="extra" value="extra" scope="page"/>
                                                            <c:choose>
                                                                <c:when test="${extra}">
                                                                    <ui:studioCheckoutConfirmItem contestId="${contestId}"
                                                                                             styleClass="dollarSlots"
                                                                                             submissionId="${submissionId}"
                                                                                             feedbackText="${viewData.submissionFeedback[submissionId]}"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <ui:studioCheckoutConfirmItem contestId="${contestId}"
                                                                                             submissionId="${submissionId}"
                                                                                             feedbackText="${viewData.submissionFeedback[submissionId]}"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </s:iterator>
                                                        </tbody>
                                                    </table>
                                                </div><!-- End #submissionList -->
                                                <div id="purchaseSummary">
                                                    <div class="selectPaymentTitle"><h3>Receipt</h3></div>
                                                    <c:set var="additionalNumber" value="${fn:length(additionalPayments)}" scope="page" />
                                                    <s:set var="positionNames" value="{'1st','2nd','3rd','4th','5th'}" scope="page" />
                                                    <table id="summary"></table>
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
<jsp:include page="includes/contest/submissionViewer/contestVars.jsp"/>
<input type="hidden" id="prizeNumber" value="${viewData.prizeNumber}" />
<input type="hidden" id="additionalPrize" value="${viewData.additionalPrize}" />
<input type="hidden" id="milestonePrize" value="${viewData.milestonePrize}" />
<input type="hidden" id="milestoneAwardNumber" value="${viewData.milestoneAwardNumber}" />
<s:iterator value="viewData.prizes">
<s:set var="place" value="place" scope="page"/>
<s:set var="prizeAmount" value="prizeAmount" scope="page"/>
<input type="hidden" id="prize_${place}" value="${prizeAmount}" />
</s:iterator>
</body>
<!-- End #page -->

</html>