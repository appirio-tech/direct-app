<%--
  - Author: TCSASSEMBLER
  -
  - Version 1.1 (HTML Cockpit Spec Review Assembly assembly)
  -
  - Version 1.2 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Add dashboard header.
  -
  - Version 1.2.1 (Release Assembly - TC Direct Cockpit Release Four) change notes:
  - - Uses the new flag hasActiveSpecSubmssion to check if need to display resubmit spec button
  -  
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
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
    <script type="text/javascript" src="/scripts/specReview.js?v=206299"></script>
    <script type="text/javascript">
        var questionNames = {};
        var contestId = ${viewData.contestStats.contest.id};
        var userName = "<s:property value='currentUserHandle'/>" 
            || "<c:out value='${currentUserHandle}'/>" 
            || "<c:out value='${userHandle}'/>";
        
        $(function() {
            var items = {};
            var userComments = {};
            var guidelines = {};
            
            var qId;
            
            <s:iterator value="viewData.specificationReview.scorecard.groups">
                <s:iterator value="sections">
                    <s:iterator value="questions">
                        questionNames[${id}] = "${description}";
                    </s:iterator>
                </s:iterator>
            </s:iterator>
            
            addComment = function(questionId) {
                handleAddOrUpdateCommentEvent(questionId, contestId, userName, "add", "-1");
            };
            
            <c:if test="${viewData.specificationReviewStatus eq 'FINISHED'}">
                $(".comment_specreview a").hide();
                $(".to_add_your_comment").hide();
            </c:if>
            
            <c:if test="${viewData.specReviewComments eq null}">
                $(".comment_specreview a").hide();
                $(".to_add_your_comment").hide();
            </c:if>            

            $(".add_your_comment").hide();
            registerEditEvent();
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
                                <a href="<s:url action="currentProjectDetails" namespace="/">
                                    <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                    </s:url>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
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
                                                    <div class="container2Content_det">
                                                        <span class="status">
                                                            <c:if test="${viewData.specificationReviewStatus eq 'PENDING_REVIEW'}">
                                                                Spec Review in Progress...
                                                            </c:if>    
                                                            <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_FIXES'}">
															<c:choose>
                                                               <c:when  test="${viewData.hasActiveSpecSubmission}">
                                                                   The submission has been received.
                                                               </c:when >
                                                               <c:otherwise>
                                                                   Waiting For Fixes...
                                                               </c:otherwise>
															</c:choose>
                                                            </c:if>
                                                            <c:if test="${viewData.specificationReviewStatus eq 'NO_SPEC_REVIEW'}">
                                                                No Spec Review Now...
                                                            </c:if>    
                                                            <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_SUBMIT'}">
                                                                Waiting For Submit...
                                                            </c:if>
                                                            <c:if test="${viewData.specificationReviewStatus eq 'FINISHED'}">
                                                                Spec Review is Complete
                                                            </c:if>
                                                        </span>
                                                        <c:if test="${viewData.specificationReview ne null}">
                                                            <span class="reviewer_handle_text"><s:property value="viewData.specificationReview.creationUserHandle"/></span>
                                                            <span class="reviewer_text">Reviewer : </span>
                                                        </c:if>

                                                        <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_FIXES'}">
                                                            <p class="specForumInfo">Please review the scorecard results below. If your spec reviewer has asked for fixes, you will need to edit your contest before clicking "Resbmit for Review" below. If you have 
                                                            any questions regarding your spec review, please use the forum (linked at the bottom) to discuss it with your spec reviewer.</p>
                                                        </c:if>

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
                                                                                <div class="captionInner">                                                                            <h2>
                                                                                    <c:choose>
                                                                                        <c:when test="${viewData.responses[id]}">
                                                                                            <img src="/images/ico-success2.png" alt="success" class="icon_status" />
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <img src="/images/ico-fail2.png" alt="failed" class="icon_status" />
                                                                                        </c:otherwise>
                                                                                    </c:choose>
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
                                                                            
                                                                            <s:set var="comments" value="%{#attr['viewData']['specComments'][#attr['id']]}"/> 
                                                                           <div id="comment_area_${id}"> 
                                                                            <s:iterator value="comments">
                                                                                <c:if test="${commentType eq 'REVIEWER_COMMENT'}">
                                                                                    <div class="comment_specreview">
                                                                                        <div class="old_comment">
                                                                                            <p class="text_comment">${reviewerCommentType}</p>
                                                                                            <p><span class="text_reviewer_handle">${commentBy}: </span>(
                                                                                            <c:choose>
                                                                                                <c:when test="${commentDate ne null}">
                                                                                                    <fmt:formatDate value="${commentDate}" pattern="MM/dd/yyyy HH:mm"/>
                                                                                                </c:when>
                                                                                                <c:otherwise>00/00/0000 00.00</c:otherwise>
                                                                                            </c:choose>
                                                                                            )</p>
                                                                                            <p>${comment}</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </c:if>  
                                                                                
                                                                                <c:if test="${commentType eq 'USER_COMMENT'}">
                                                                                    <div class="comment_specreview" id="commentDiv_${commentId}">
                                                                                        <div class="old_comment">
                                                                                            <c:if test="${commentBy eq userHandle}">
                                                                                                <a href="javascript:;">
                                                                                                    <img src="/images/edit.png" alt="Edit comment" class="sr_editcomment">
                                                                                                </a>
                                                                                            </c:if>
                                                                                            <p>
                                                                                                <span class="text_reviewer_handle">
                                                                                                    <c:if test="${commentBy eq userHandle}">
                                                                                                    Your
                                                                                                    </c:if>
                                                                                                    <c:if test="${commentBy ne userHandle}">
                                                                                                    ${commentBy}
                                                                                                    </c:if>
                                                                                                    comment: 
                                                                                                </span>
                                                                                                <span class="date_field">(<fmt:formatDate value="${commentDate}" pattern="MM/dd/yyyy HH:mm"/>)</span>
                                                                                            </p>
                                                                                            <p class="comment_field">
                                                                                                ${comment}
                                                                                            </p>
                                                                                        </div>
                                                                                    </div>   
                                                                                    <div class="add_your_comment">
                                                                                        <div class="textarea1"><textarea></textarea></div>
                                                                                        <p class="add_comment">
                                                                                            <a href="javascript:;" class="add_comment_cancel_text cancel_link">Cancel</a>
                                                                                            <a href="javascript:;" class="add_comment_cancel_text edit_link">Edit</a>
                                                                                        </p>
                                                                                    </div>
                                                                                </c:if>                                                                                
                                                                            </s:iterator>
                                                                            </div> 
                                                                            
                                                                            <div class="to_add_your_comment" id="to_add_your_comment${id}">
                                                                                <div class="captionInner">
                                                                                    <h2>
                                                                                        &nbsp;
                                                                                        <a href="javascript:;" onclick="showAddCommentDiv('${id}', 'add_your_comment${id}', 'to_add_your_comment${id}');"><img src="/images/add_your_comment.png" alt="Add Your Comment" class="sr_addcomment" /></a>
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
                                                        
                                                        <c:if test="${viewData.contestStats.isStudio and viewData.specificationReview ne null}">
                                                            <p class="specForumInfo">Discuss scorecard with spec reviewer in <a href="https://studio.topcoder.com/forums?module=ThreadList&forumID=${viewData.contestStats.forumId}" target="_blank">forum</a>.</p>
                                                        </c:if>
                                                        
                                                        <c:if test="${viewData.specificationReviewStatus eq 'WAITING_FOR_FIXES' and !viewData.hasActiveSpecSubmission}">
                                                            <div id="resubmit">
                                                                <a href="javascript:;" class="resubmit"><img src="/images/resubmit.png" alt="resubmit" /></a>
                                                                <br />
                                                                <span class="bottom_text"></span>
                                                            </div>
                                                            <div class="panel">&nbsp;</div>
                                                        </c:if>
                                                        
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
