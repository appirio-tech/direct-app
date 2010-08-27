<%--
  - Author: isv, flexme
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes: remove firstSlots class on single page.
  -
  - Version: 1.1
  - Since: Submission Viewer Release 1 assembly
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a collection of buttons used for ranking, viewing,
  - purchasing, voting for Studio submissions by client.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>

<s:set var="viewType" value="formData.viewType.toString()" scope="page"/>
<c:set var="divStyleClass" value="submissionAction" scope="page"/>
<c:if test="${viewType eq 'LIST'}">
    <c:set var="divStyleClass" value="submissionListAction" scope="page"/>
</c:if>

<div class="${divStyleClass}">
    <c:if test="${viewType eq 'SINGLE'}">
        <span class="icoSingleSubmissionBankLocation"></span>
    </c:if>
    <ul>
        <li>
            <a href="javascript:;" class="actButtonlike"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to <strong>Like</strong> Bank?
                            <span class="question"><a href="javascript:;" class="greentext likeSubmission " rel="${submissionId}">Yes</a> | <a
                                    href="javascript:;" class="noLikeSubmission" rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
        <li><a href="javascript:;" class="actButtondislike"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to <strong>Dislike</strong> Bank?
                            <span class="question"><a href="javascript:;" class="redtext dislikeSubmission " rel="${submissionId}">Yes</a> | <a
                                    href="javascript:;" class="noDislikeSubmission" rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
        <li>
            <a href="<s:url action="studioSubmission" namespace="/contest">
                            <s:param name="contestId" value="%{#attr['contestId']}"/>
                            <s:param name="formData.submissionId" value="%{#attr['submissionId']}"/>
                            <s:param name="formData.roundType" value="formData.roundType"/>
                            </s:url>"
               class="actButtonzoom"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini-sm">
                    <div class="dialog-mini-sm-inside">
                        <div class="dialog-mini-sm-outside">
                            View Larger Size
                        </div>
                    </div>
                </div>
            </div>
        </li>
        <li><a href="javascript:;" class="actButtondollar"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Extra <strong>Purchase?</strong>
                            <span class="question">
                                <a href="javascript:;" class="extraSlot greenDarktext"
                                   title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}" rel="${submissionId}">Yes</a> |
                                <a href="javascript:;" class="noExtraSlot" rel="${submissionId}">No</a></span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
        <li><a href="javascript:;" class="actButtonstar"><span></span></a>

            <div class="dialog-mini-wrapper">
                <div class="dialog-mini-arrow"></div>
                <div class="dialog-mini last">
                    <div class="dialog-mini-inside">
                        <div class="dialog-mini-outside">
                            Add to Bank Number?
                            <span class="question addToBank">
                                <c:forEach var="ind" begin="1" end="${viewData.prizeNumber}">
                                <a href="javascript:;" class="blackLink" title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}" rel="${submissionId}">${ind}</a>
                                </c:forEach>
                                |
                                <a href="javascript:;" class="extraSlot" title="${tcdirect:getSubmissionPreviewImageURL(submissionId, "thumb", 0, pageContext.request)}" rel="${submissionId}">Xtra</a>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End .dialog-mini-wrapper -->
        </li>
    </ul>
</div>
