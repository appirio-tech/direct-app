<%--
  - Author: isv, TCSDEVELOPER, TCSASSEMBLER
  - Version: 1.2 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 4) change notes: Replaced "submissionId" with "submission" attribute.
  - Version 1.2 (TC Direct Replatforming Release 3  ) change notes: Update the type of Submission. 
  -
  - Version 1.2 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Apply to new submission view prototype. 
  -
  - Description: This tag renders the HTML markup for a single submission included into a carousel view on Single Studio
  - submission page.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="if" tagdir="/WEB-INF/tags/conditions" %>
<%@ taglib prefix="tcdirect" uri="/tcdirect-functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submission" required="false" type="com.topcoder.management.deliverable.Submission" %>

<%@ attribute name="artifactNum" required="true" type="java.lang.Integer" %>
<c:set var="submissionId" value="${submission.id}"/>

<li style="position: absolute; top: 0px; left: 0px; display: list-item; z-index: 7; opacity: 1;">
    <div id="submission-${submissionId}"
         class="carouselSingleItem <if:isConfirmedStudioSubmission negate="false">prizedSubmission</if:isConfirmedStudioSubmission>">
        <div class="hidden">
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                      styleClass="submissionIMG"/>
            <label>Submission ID: <span class="submissionID">${submissionId}</span></label>
        </div>

        <a href="javascript:window.open('${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', artifactNum, pageContext.request)}' ,'Submission', 'width=' + screen.width + ', height=' + screen.height + ', fullscreen=yes');void(0);" class="thumbSingle">
            <span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="full"
                                      artifactNum="${artifactNum}" width="746" height="544"
                                      longdesc="${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', artifactNum, pageContext.request)}"/>
        </a>
    </div>
</li>
