<%--
  - Author: isv
  - Version: 1.0 (Submission Viewer Release 1 assembly)
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders the HTML markup for a single submission displayed on Studio Submissions List
  - page for selected contest.
--%>
<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>

<%@ attribute name="contestId" required="true" type="java.lang.Long" %>
<%@ attribute name="submissionId" required="true" type="java.lang.Long" %>

<tr id="submission-${submissionId}">
    <td></td>
    <td>
        <a href="javascript:;" class="thumbList">
            <span></span>
            <ui:studioSubmissionImage submissionId="${submissionId}" imageType="small" dimension="130"/>
            <span class="hidden">
                <ui:studioSubmissionImage submissionId="${submissionId}" imageType="thumb" dimension="66"
                                          styleClass="submissionIMG"/>
            </span>
        </a>

    </td>
    <td class="submissionID">${submissionId}</td>
    <td><span class="icoBankLocation"></span></td>
    <td>
        <ui:submissionAction contestId="${contestId}" submissionId="${submissionId}"/>
    </td>
    <td></td>
</tr>

