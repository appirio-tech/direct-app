<%--
  - Author: TCSASSEMBLER
  - Version 1.0 (HTML Cockpit Spec Review Assembly assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the specification reivew view.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="specReview"/>
    <ui:contestPageType tab="specReview"/>
    <jsp:include page="includes/htmlhead.jsp"/>
    <script type="text/javascript" src="/scripts/specReview.js"></script>
    <script type="text/javascript">
        var questionNames = {};
        
        $(function() {
            var items = {};
            var userComments = {};
            var guidelines = {};
            
            var qId;
            var contestId = ${viewData.contestStats.contest.id};
            var userName = "<s:property value='currentUserHandle'/>" 
                || "<c:out value='${currentUserHandle}'/>" 
                || "<c:out value='${userHandle}'/>";
            
            <s:iterator value="viewData.specificationReview.review.items">
                qId = ${question};
                items[qId] = {
                    "answer" : "${answer}",
                    "comments" : []
                };
                <s:iterator value="comments">
                    items[qId]['comments'][items[qId]['comments'].length] = {
                        "comment" : "${comment}",
                        "commentType" : "${commentType.name}"
                    }
                </s:iterator>
            </s:iterator>
            <s:iterator value="viewData.specReviewComments">
                qId = ${questionId};
                userComments[qId] = [];
                <s:iterator value="comments">
                    userComments[qId][userComments[qId].length] = {
                        "commentBy" : "${commentBy}",
                        "commentDate" : <c:choose>
                                            <c:when test="${commentDate ne null}">
                                                "(<fmt:formatDate value="${commentDate}" pattern="MM/dd/yyyy HH:mm"/>)"
                                            </c:when>
                                            <c:otherwise>"(00/00/0000 00.00)"</c:otherwise>
                                        </c:choose>,
                        "comment" : "${comment}",
                        "commentId" : "${commentId}"
                    };
                </s:iterator>
            </s:iterator>
            <s:iterator value="viewData.specificationReview.scorecard.groups">
                <s:iterator value="sections">
                    <s:iterator value="questions">
                        guidelines[${id}] = "${guideline}";
                        questionNames[${id}] = "${description}";
                    </s:iterator>
                </s:iterator>
            </s:iterator>
            
            renderSpecReviewPage(items, userComments, guidelines, contestId, userName);
            
            addComment = function(questionId) {
                handleAddOrUpdateCommentEvent(questionId, contestId, userName, "add", "-1");
            };
            
            <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_FIXES'}">
                setPageWaitingForFixes();
            </c:if>
            
        });
    </script>
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
                                <div id="dropdownWrapper">
                                    <select id="contestSelect">
                                       <option>Contest Links</option>
                                       <option>Contest Specification</option>
                                       <option>Contest Forum</option>
                                       <option>Online Review</option>
                                       <option>Jira</option>
                                    </select>
                                 </div>
                                <h2 class="title contestTitle"><s:property
                                        value="viewData.contestStats.contest.title"/></h2>
                            </div>
                            <!-- End .areaHeader -->
                            
                            <jsp:include page="includes/contest/contestStats.jsp"/>
                    
                            <div class="container2 tabs3Container">

                                <jsp:include page="includes/contest/tabs.jsp"/>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div class="container2BottomLeft">
                                                <div class="container2BottomRight">
                                                    <div class="container2Content_det">
                                                        <span class="status">
                                                            <c:if test="${viewData.specificationReviewStatus eq 'PENDING_REVIEW'}">
                                                                Spec Review in Progress...
                                                            </c:if>    
                                                            <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_FIXES'}">
                                                                Waiting For Fixes...
                                                            </c:if>
                                                        </span>
                                                        
                                                        <span class="reviewer_handle_text"><s:property value="viewData.specificationReview.creationUserHandle"/></span>
                                                        <span class="reviewer_text">Reviewer : </span>

                                                        <div class="clearForIE6">
                                                        </div>

                                                        <s:set var="questionNumber" value="0" scope="page"/>
                                                        <s:iterator value="viewData.specificationReview.scorecard.groups">
                                                            <s:iterator value="sections">
                                                                <s:iterator value="questions">
                                                                    <s:set var="name" value="name" scope="page"/>
                                                                        <!-- Start no_details -->
                                                                        <div class="no_details">
                                                                            <div class="caption_specreview" id="caption_specreview${id}" style="display:none">
                                                                                <a href="javascript:;" onclick="showHideDiv('det_specreview${id}', 'caption_specreview${id}');"><img src="/images/ico-show-srquestion-detail.png" alt="show detail" class="sr_question_showdetail" /></a>
                                                                                <div class="captionInner">
                                                                                    <h2>
                                                                                        <img src="/images/ico-success2.png" alt="success" class="icon_status" />
                                                                                        <span style="display:block">
                                                                                        ${description}
                                                                                        </span>
                                                                                    </h2>
                                                                                </div>
                                                                            </div>
                                                                            <!-- End .caption_specreview -->
                                                                            <div class="det_specreview" id="det_specreview${id}">
                                                                                <a href="javascript:;" onclick="showHideDiv('caption_specreview${id}', 'det_specreview${id}');">
                                                                                    <img src="/images/ico-hide-srquestion-detail.png" alt="hide detail" class="sr_question_showdetail" />
                                                                                </a>
                                                                                <div class="captionInner">                                                                    		<h2>
                                                                                        <img src="/images/ico-success2.png" alt="success" class="icon_status" />
                                                                                        <span style="display:block">
                                                                                        ${description}
                                                                                        </span>
                                                                                    </h2>
                                                                                </div>
                                                                                <p class="srquestion_detail_font">
                                                                                    ${guideline}
                                                                                </p>
                                                                            </div>                                                         
                                                                            <!-- End .det_specreview -->
                                                                            <div class="comment_specreview" id="reviewer_comment_specreview${id}">
                                                                                <div class="old_comment">
                                                                                    <p class="text_comment">Comments</p>
                                                                                    <p><span class="text_reviewer_handle"><s:property value="viewData.specificationReview.creationUserHandle"/>: </span>(
                                                                                    <c:choose>
                                                                                        <c:when test="${viewData.specificationReview.review.creationTimestamp ne null}">
                                                                                            <fmt:formatDate value="${viewData.specificationReview.review.creationTimestamp}" pattern="MM/dd/yyyy HH:mm"/>
                                                                                        </c:when>
                                                                                        <c:otherwise>00/00/0000 00.00</c:otherwise>
                                                                                    </c:choose>
                                                                                    )</p>
                                                                                </div>
                                                                            </div>
                                                                            <div class="comment_specreview" id="comment_specreview${id}" style="display:none">
                                                                            </div>
                                                                            <div class="to_add_your_comment" id="to_add_your_comment${id}">
                                                                                <div class="captionInner">
                                                                                    <h2>
                                                                                        &nbsp;
                                                                                        <a href="javascript:;" onclick="showHideDiv('add_your_comment${id}', 'to_add_your_comment${id}');"><img src="/images/add_your_comment.png" alt="Add Your Comment" class="sr_addcomment" /></a>
                                                                                    </h2>
                                                                                </div>
                                                                            </div>
                                                                            <!-- End .to_add_your_comment -->
                                                                            <div class="add_your_comment" id="add_your_comment${id}" style="display:none">
                                                                                <div class="textarea1">
                                                                                    <textarea rows="" cols="">Input your comment...</textarea>
                                                                                </div>
                                                                                <p class="add_comment">
                                                                                    <a href="javascript:;" onclick="showHideDiv('to_add_your_comment${id}', 'add_your_comment${id}');" class="add_comment_cancel_text">Cancel</a>
                                                                                    <a href="javascript:;" style="display:none" class="add_comment_cancel_text">Edit</a>
                                                                                    <a href="javascript:;" onclick="showHideDiv('comment_specreview${id}', 'add_your_comment${id}'); showHideDiv('to_add_your_comment${id}', '_nothingtohide_'); addComment(${id});"><img src="/images/add_comment.png" alt="Add Comment" class="save_btn" /></a>
                                                                                </p>
                                                                            </div>
                                                                            <!-- End .add_your_comment -->                                                                
                                                                        </div>
                                                                        <!-- End .no_details -->                                                               
                                                                </s:iterator>
                                                            </s:iterator>
                                                        </s:iterator>
                                                        
                                                        <div id="resubmit">
                                                            <a href="<s:url action="contest/detail" namespace="/"><s:param name="projectId" value="viewData.contestStats.contest.id"/></s:url>" class="resubmit"><img src="/images/resubmit.png" alt="resubmit" /></a>
                                                            <br />
                                                            <span class="bottom_text"></span>
                                                        </div>

                                                        <div class="panel">&nbsp;</div>
                                                        <div class="panel">&nbsp;</div>
                                                    </div>
                                                    <!-- End .container2Content -->
                                                
                                                </div>
                                            </div>
                                        </div>
                                        
                                                            
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->

                            <a href="#" class="button1 backToTop"><span>Back To Top</span></a>
                        </div>
                    </div>

                    <div class="clear"></div>
                    <span id="xConvertor" style="display:none"></span>
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

<jsp:include page="includes/popups.jsp"/>
</body>
<!-- End #page -->

</html>
