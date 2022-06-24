<%--
  - Author: isv, flexme, minhu, TCSASSEMBLER
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: remove firstSlots class on single page.
  - Version 1.2 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  - Version 1.3 (TC Direct Release Assembly 7) change notes: not to show link if user has no write permission.
  - Version 1.4 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId.
  - Version 1.5 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) change notes: Updated to follow the new prototype.
  - Version 1.6 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0) change notes:
  - - Add submissionId as "rel" attribute to the "actButtonzoom" link.
  - - Display the text of "actButtonzoom" link based on the contest type.
  - Version: 1.6
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a collection of buttons used for ranking, viewing,
  - purchasing, voting for Studio submissions by client.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.management.deliverable.Submission" %>
<%@ attribute name="singleSubmissionFlag" required="false" type="java.lang.Boolean" %>

<c:set var="submissionId" value="${submission.id}"/>
<s:set var="viewType" value="formData.viewType.toString()" scope="page"/>
<c:set var="divStyleClass" value="submissionAction" scope="page"/>
<c:if test="${viewType eq 'LIST'}">
    <c:set var="divStyleClass" value="submissionListAction" scope="page"/>
</c:if>
<c:if test="${singleSubmissionFlag}">
    <c:set var="divStyleClass" value="submissionActionGrid" scope="page"/>
</c:if>

<c:set var="isContestCheckedOut" value="${viewData.hasCheckout}"/>

<div class="${divStyleClass}">
    <c:if test="${singleSubmissionFlag}">
        <a href="#" class="numIcon icoNull"></a>
    </c:if>
    <ul>
        <li>
            <c:choose>            
                <c:when test="${viewData.hasContestWritePermission}">
                    <a href="javascript:;" class="actButtonlike"><span></span></a>
                </c:when>
                <c:otherwise>
                    <div class="actButtonlike"><span></span></div>                
                </c:otherwise>
            </c:choose>
            
            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to <strong>Like</strong> Bank?
                            <span class="question"><a href="javascript:;"
                                                      class="${isContestCheckedOut ? '' : 'greentext likeSubmission'}"
                                                      rel="${submissionId}">Yes</a> | <a
                                    href="javascript:;" class="${isContestCheckedOut ? '' : 'noLikeSubmission'}"
                                    rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
        <li>
            <c:choose>            
                <c:when test="${viewData.hasContestWritePermission}">
                    <a href="javascript:;" class="actButtondislike"><span></span></a>
                </c:when>
                <c:otherwise>
                    <div class="actButtondislike"><span></span></div>
                </c:otherwise>
            </c:choose>
            
            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to <strong>Dislike</strong> Bank?
                            <span class="question"><a href="javascript:;"
                                                      class="${isContestCheckedOut  ? '' : 'redtext dislikeSubmission '}"
                                                      rel="${submissionId}">Yes</a> | <a
                                    href="javascript:;" class="${isContestCheckedOut  ? '' : 'noDislikeSubmission'}"
                                    rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
    <c:if test="${not singleSubmissionFlag}">
        <li>
            <a href="<s:url action="studioSubmission" namespace="/contest">
                            <s:param name="projectId" value="%{#attr['contestId']}"/>
                            <s:param name="formData.submissionId" value="%{#attr['submissionId']}"/>
                            <s:param name="formData.roundType" value="formData.roundType"/>
                            </s:url>"
               class="actButtonzoom" rel="${submissionId}"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini-sm">
                    <div class="dialog-mini-sm-inside">
                        <div class="dialog-mini-sm-outside">
                            <c:if test="${not isWireframeContest}">View Larger Size</c:if><c:if test="${isWireframeContest}">Wireframe Viewer</c:if>
                        </div>
                    </div>
                </div>
            </div>
        </li>
    </c:if>
        <li>
            <c:choose>            
                <c:when test="${viewData.hasContestWritePermission}">
                    <a href="javascript:;" class="actButtondollar"><span></span></a>
                </c:when>
                <c:otherwise>
                    <div class="actButtondollar"><span></span></div>
                </c:otherwise>
            </c:choose>
            
            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Extra <strong>Purchase?</strong>
                            <span class="question">
                                <a href="javascript:;"
                                   class="${isContestCheckedOut ? '' : 'extraSlot greenDarktext'}"
                                   title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}"
                                   rel="${submissionId}">Yes</a> |
                                <a href="javascript:;"
                                   class="${isContestCheckedOut ? '' : 'noExtraSlot'}"
                                   rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
        <li>
            <c:choose>            
                <c:when test="${viewData.hasContestWritePermission}">
                    <a href="javascript:;" class="actButtonstar"><span></span></a>
                </c:when>
                <c:otherwise>
                    <div class="actButtonstar"><span></span></div>
                </c:otherwise>
            </c:choose>
            
            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini last">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to Bank Number?
                            <span class="question addToBank">
                                <c:forEach var="ind" begin="1" end="${viewData.prizeNumber}">
                                <a href="javascript:;"
                                   ${isContestCheckedOut ? 'class="disabledControl"' : 'class="blackLink"'}
                                   title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}"
                                   rel="${submissionId}">${ind}</a>
                                </c:forEach>
                                |
                                <a href="javascript:;"
                                   ${isContestCheckedOut ? '' : 'class="extraSlot"'}
                                   title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}"
                                   rel="${submissionId}">Xtra</a>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
    </ul>
</div>
