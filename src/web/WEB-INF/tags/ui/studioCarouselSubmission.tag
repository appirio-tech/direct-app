<%--
  - Author: isv, TCSDEVELOPER
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  -
  - Description: This tag renders the HTML markup for a single submission included into a carousel view on Single Studio
  - submission page.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.service.studio.SubmissionData" %>

<%@ attribute name="artifactNum" required="true" type="java.lang.Integer" %>
<c:set var="submissionId" value="${submission.submissionId}"/>

<li>
    <div id="submission-${submissionId}"
         class="carouselSingleItem ${tcdirect:isCheckedOut(formData.roundType, submission) ? 'prizedSubmission' : ''}">
        <div class="hidden">
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                      styleClass="submissionIMG"/>
            <label>Submission ID: <span class="submissionID">${submissionId}</span></label>
        </div>

        <a href="javascript:window.open('${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', artifactNum, pageContext.request)}' ,'Submission', 'width=' + screen.width + ', height=' + screen.height + ', fullscreen=yes');void(0);" class="thumbSingle">
            <span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="full"
                                      artifactNum="${artifactNum}" width="659" height="478"
                                      longdesc="${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', artifactNum, pageContext.request)}"/>
        </a>
    </div>
</li>
