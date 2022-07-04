<%--
  - Author: isv
  -
  - Version: 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the Final Fixes page for Studio contests.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="finalFixes"/>
    <jsp:include page="includes/contest/submissionViewer/submissionViewerHtmlHead.jsp"/>
    <script type="text/javascript" src="/scripts/studioFinalFixes.js"></script>
    <script type="text/javascript" src="/scripts/jquery.jqtransform.js"></script>
</head>
<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="studioFinalFixes newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="currentPage">
                                <a href="<s:url action=" dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <a href="<s:url action=" currentProjectDetails" namespace="/">
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

                            <jsp:include page="includes/contest/dashboard.jsp"/>

                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">
                                                    <c:choose>
                                                        <c:when test="${viewData.dataUnavailable}">
                                                            <div class="containerNoPadding">
                                                                <div class="SubmissionSlotTitle">
                                                                    <h3 class="red">Final Fix/Final Review is not opened
                                                                        yet. Please wait.</h3>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!-- NOT STARTED -->
                                                            <jsp:include page="includes/finalFixes/not-started.jsp"/>
                                                            <!-- IN PROGRESS or REVIEW or COMPLETED -->
                                                            <jsp:include page="includes/finalFixes/in-progress-review-completed.jsp"/>
                                                        </c:otherwise>
                                                    </c:choose>
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
                </div>
                <!-- End #mainContent -->
                <jsp:include page="includes/footer.jsp"/>
            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->


<!-- modal -->
<div id="modalBackground"></div>
<div id="new-modal">

    <!-- #saveConfirmModal -->
    <div id="saveConfirmModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Save
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">

            <div class="noticeContent">Saved your review successfully</div>

            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span
                        class="btnC">OK</span></span></a>
            </div>
        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- end #saveConfirmModal -->
    <!-- #rejectConfirmModal -->
    <div id="rejectConfirmModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Reject
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">

            <div class="noticeContent">Are you sure you want to reject the Final Fixes?</div>

            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 rejectReviewedFFBtn"><span class="btnR"><span
                        class="btnC">YES</span></span></a>
                <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span
                        class="btnC">NO</span></span></a>
            </div>
        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- end #rejectConfirmModal -->
    <!-- #acceptConfirmModal -->
    <div id="acceptConfirmModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Accept
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">

            <div class="noticeContent">Are you sure to accept the final fixes?</div>

            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 acceptReviewedFFBtn"><span class="btnR"><span class="btnC">YES</span></span></a>
                <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span
                        class="btnC">NO</span></span></a>
            </div>
        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- end #acceptConfirmModal -->


    <!-- #nonFixedItemsModal -->
    <div id="nonFixedItemsModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Error
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">

            <div class="noticeContent">Not all items are fixed</div>

        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- end #nonFixedItemsModal -->

</div>
<!-- end modal -->
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>