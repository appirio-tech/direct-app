<%--
  - Author: TCSASSEMBLER
  - Version 1.0 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp)
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the full size images of single submission for Studio contest.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>                                         
    <link rel="stylesheet" href="/css/submission_viewer.css"  type="text/css" />
    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/submission_viewer.js"></script>
</head>

<s:set var="submissionId" value="viewData.submission.id" scope="page"/>
<s:set var="submissionArtifactCount" value="viewData.submissionArtifacts.size()" scope="page"/>
<s:set var="artifactNum" value="formData.artifactNum" scope="page"/>
<c:if test="${artifactNum == 0 || artifactNum > submissionArtifactCount}"><c:set var="artifactNum" value="1"/></c:if>
<input type="hidden" id="submissionArtifactCount" value="${submissionArtifactCount}"></input>

<body id="fullpageBody">
<div id="fullSizePage" class="fullminWidth">
	
	<div class="fullBox">
		
		<div class="title">
			<h1>${viewData.submissionArtifacts[artifactNum-1]}</h1>
			<div class="clear"></div>
		</div>
		
		<!-- mainContent -->
		<div class="mainContent">
			
			<!-- bar -->
			<div class="bar">
                <s:if test="contestTypeId != 18L">
                    <a href="#" class="prevLink">Previous Image</a>
                    <a href="#" class="nextLink">Next Image</a>
                    <span><strong class="lblID">${artifactNum}</strong> / ${submissionArtifactCount}</span>
                </s:if>
                <s:else>
                    <span><strong class="lblID">${artifactNum}</strong> / 1</span>
                </s:else>
                <div class="corner tl"></div>
                <div class="corner tr"></div>
			</div>
			<!-- End .bar -->
			
            <c:forEach begin="1" end="${submissionArtifactCount}" var="pos">
                <c:set var="filename" value="${viewData.submissionArtifacts[pos-1]}" />
                <input type="hidden" id="artifact${pos}" value="${filename}"/>
                <c:set var="imgUrl" value="${tcdirect:getSubmissionPreviewImageURL(submissionId, 'full', pos, pageContext.request)}"/>
                <div class="full${pos} fullSizePic hide"><span><img src="${imgUrl}" alt="thumbnails" /></span></div>
            </c:forEach>
			
			<!-- bar -->
			<div class="bar">
				  <s:if test="contestTypeId != 18L">
                    <a href="#" class="prevLink">Previous Image</a>
                    <a href="#" class="nextLink">Next Image</a>
                    <span><strong class="lblID">${artifactNum}</strong> / ${submissionArtifactCount}</span>
                </s:if>
                <s:else>
                    <span><strong class="lblID">${artifactNum}</strong> / 1</span>
                </s:else>
                <div class="corner tl"></div>
                <div class="corner tr"></div>
			</div>
			<!-- End .bar -->
		
		</div>
		<!-- End .mainContent -->
		
	</div>

</div>
</body>
</html>

