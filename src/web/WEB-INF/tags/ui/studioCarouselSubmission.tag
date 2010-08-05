<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a single submission included into a carousel view on Single Studio
  - submission page.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>
<%@ attribute name="artifactNum" required="true" type="java.lang.Integer" %>

<li>
    <div id="submission-${submissionId}" class="carouselSingleItem">
        <div class="hidden">
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                      styleClass="submissionIMG"/>
            <label>Submission ID: <span class="submissionID">${submissionId}</span></label>
        </div>

        <a href="javascript:;" class="thumbSingle">
            <span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="full"
                                      artifactNum="${artifactNum}" width="659" height="478"
                                      longdesc="${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', artifactNum, pageContext.request)}"/>
        </a>

        <ui:submissionAction contestId="${contestId}" submissionId="${submissionId}"/>

    </div>
</li>
