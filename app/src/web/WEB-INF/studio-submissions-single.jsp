<%--
  - Author: isv, flexme, minhu
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Direct Submission Viewer Release 2 ) change notes:
  - 1.Remove "bank:" row.
  - 2.Hide "Submitter Notes:" row.
  - 3.Display Feedback when feedback text is not null or empty.
  - 4.include the contestVars.jsp.
  -
  - Version 1.2 (Direct Submission Viewer Release 4 ) change notes:
  - 1.Display Feedback as text only when submission is "Confirmed"
  -
  - Version 1.3 (TC Direct Release Assembly 7) change Notes: 
  - 1.Added hasContestWritePermission field.  
  -
  - Version 1.4 (TC Direct Replatforming Release 3  ) change notes: The parameter name is changed from contestId to projectId.
  -
  - Version 1.4 (TC Direct Contest Dashboard Update Assembly) change Notes: 
  - 1.Apply to new submission view.
  - 2.Add dashboard header.  
  -
  - Version 1.5 (TC Direct Replatforming Release 5  ) change notes: Add support to the Submission Declaration section.
  -
  - Version 1.6 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp) change notes:
  -   Updated to follow the new prototype.
  -
  - Version: 1.7
  - Since: Submission Viewer Release 1 assembly
  -
  - Version 1.8 (Release Assembly - TC Direct Cockpit Release Two) changes:
  - - Update the single submission viewer hover to display font and stock art information
  -
  - Version 1.9 (TC Cockpit - Studio - Final Fixes Integration Part Two Assembly) changes:
  - - The bread-crumb now refers to Final Fix page when appropriate
  -
  - Description: This page renders the single submission for Studio contest.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="submissions"/>
    <jsp:include page="includes/contest/submissionViewer/submissionViewerHtmlHead.jsp"/>
                                         
    <link rel="stylesheet" href="/css/direct/submission_viewer.css"  type="text/css" />
    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery.jcarousel.min.js"></script>
    <script type="text/javascript" src="/scripts/json2.js"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js"></script>
    <script type="text/javascript" src="/scripts/submission_viewer.js"></script>
    <script type="text/javascript" src="/scripts/bank-single.js"></script>
    <script type="text/javascript">
        var hasContestWritePermission = ${viewData.hasContestWritePermission};
        var checkpointReviewPhaseOpen = ${viewData.checkpointReviewPhaseOpen};
    </script>    
</head>

<s:set var="submission" value="viewData.submission" scope="page"/>
<s:set var="submissionArtifacts" value="viewData.submissionArtifacts" scope="page"/>
<s:set var="previousSubmissionId" value="viewData.previousSubmissionId" scope="page"/>
<s:set var="nextSubmissionId" value="viewData.nextSubmissionId" scope="page"/>
<s:set var="roundType" value="formData.roundType.toString()" scope="page"/>
<s:set var="contestId" value="projectId" scope="page"/>
<s:set var="feedbackAction" scope="page"><s:if test="%{(viewData.feedbackText+'').trim().length()!=0}">Edit</s:if><s:else>Add</s:else> Feedback</s:set>
<s:if test="!viewData.checkpointReviewPhaseOpen"><s:set var="feedbackAction" scope="page">View Feedback</s:set></s:if>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

				<div id="wholeContent">
					<div id="wholeArea">
						
						<div class="wholeArea">
						  <!-- current Page -->
                          <div class="currentPageBox">
                            <div class="currentPage">
                                <div class="crumbs">
                                    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                    <a href="<s:url action="projectOverview" namespace="/">
                                        <s:param name="formData.projectId" value="sessionData.currentProjectContext.id"/>
                                    </s:url>"><s:property
                                            value="sessionData.currentProjectContext.name"/></a> &gt;
                                    <a href="<s:url action="detail" namespace="/contest">
                                        <s:param name="projectId" value="projectId"/>
                                    </s:url>"><s:property
                                            value="viewData.contestStats.contest.title"/></a> &gt;
                                    <c:choose>
                                        <c:when test="${viewData.submission.submissionType.id eq 4}">
                                            <a href="<s:url action="viewFinalFix" namespace="/contest">
                                        <s:param name="projectId" value="projectId"/>
                                    </s:url>">Final Fix</a> &gt;

                                        </c:when>
                                        <c:otherwise>
                                    <a href="<s:url action="submissions" namespace="/contest">
                                        <s:param name="formData.viewType" value="'GRID'"/>
                                        <s:param name="projectId" value="projectId"/>
                                    </s:url>">Submissions</a> &gt;
                                        </c:otherwise>
                                    </c:choose>
                                    <strong>Submission ID: <s:property value="viewData.submission.id"/></strong>
                                </div>
                                <div class="pageTurn">
                                    <c:if test="${previousSubmissionId != 0}">
                                    <link:studioSubmission
                                        submissionId="${previousSubmissionId}"
                                        contestId="${contestId}"
                                        roundType="${roundType}"
                                        styleClass="prevLink">Previous</link:studioSubmission><span>|</span></c:if>
                                    <a href="<s:url action="submissions" namespace="/contest">
                                        <s:param name="formData.viewType" value="'GRID'"/>
                                        <s:param name="projectId" value="projectId"/>
                                    </s:url>" class="current">All Submissions</a>
                                    <c:if test="${nextSubmissionId != 0}"><span>|</span>
                                    <link:studioSubmission
                                        submissionId="${nextSubmissionId}"
                                        contestId="${contestId}"
                                        roundType="${roundType}"
                                        styleClass="nextLink">Next</link:studioSubmission></c:if>
                                  </div>
                              </div>
                            </div>
							<!-- End current Page -->
                            
                                  <!-- Submission Info Point -->
                                  <div class="relative">
                                    <div class="infoPointBox none">
                                      <h2> 
                                        <span class="titleLeft"></span> 
                                        <span class="titleContent">Submission Info</span> 
                                        <span class="titleRight"></span> 
                                      </h2>
                                        <div class="contentLeft">
                                          <div class="contentRight">
                                            <div class="contentBox">
                                              <!-- form Text -->
                                              <div class="formText">
                                                <div class="left">Submission ID:</div>
                                                <div class="right">${submission.id}</div>
                                              </div>
                                              <!-- End form Text -->
                                              <!-- form Text -->
                                              <div class="formText">
                                                <div class="left">Date Submitted:</div>
                                                <div class="right"><fmt:formatDate value="${submission.creationTimestamp}"
                                                                                        pattern="MMM dd, yyyy, hh:mm:ss aa z" timeZone="${defaultTimeZone}"/></div>
                                              </div>
                                              <!-- End form Text -->
                                              <!-- form Text -->
                                              <div class="formText">
                                                <div class="left">Bank:</div>
                                                <div class="right" id="popupRankText">not assigned</div>
                                              </div>
                                              <!-- End form Text -->
                                                <h3>Submitter Notes</h3>
                                                  <p><c:if test="${submission.submissionDeclaration != null}"><c:out value="${submission.submissionDeclaration.comment}" /></c:if></p>

                                                <h3>Fonts</h3>
                                                <p>
                                                    <s:if test="viewData.submission.submissionDeclaration != null && viewData.submission.submissionDeclaration.externalContents != null">
                                                        <s:iterator value="viewData.submission.submissionDeclaration.externalContents">
                                                            <s:if test="externalContentType.id == 1L">
                                                            <div class="formText"><div class="left">Font&nbsp;${displayPosition}</div></div>
                                                            	  <div class="formText">
			                                                                <s:iterator value="externalContentProperties">
			                                                                        <s:if test="name.toLowerCase() == 'name'">
			                                                                            <div class="left">Name:</div>
			                                                                            <div class="right">${value}</div>
			                                                                        </s:if>
			                                                                </s:iterator>
			                                                                <s:iterator value="externalContentProperties">
			                                                                        <s:if test="name.toLowerCase() == 'url'">
			                                                                            <div class="left">Url:</div>
			                                                                            <div class="right"><a href="${value}" target="_blank">${value}</a></div>
			                                                                        </s:if>
			                                                                </s:iterator>
                                                                 </div>
                                                            </s:if>
                                                        </s:iterator>
                                                    </s:if>
                                                </p>

                                                <h3>Stock Arts</h3>
                                                <p>
                                                    <s:if test="viewData.submission.submissionDeclaration != null && viewData.submission.submissionDeclaration.externalContents != null">
                                                        <s:iterator
                                                                value="viewData.submission.submissionDeclaration.externalContents">
                                                            <s:if test="externalContentType.id == 2L">
                                                            <div class="formText"><div class="left">Stock Art&nbsp;${displayPosition}</div></div>
                                                                <div class="formText"> 
		                                                                <s:iterator value="externalContentProperties">
		                                                                
		                                                                    <s:if test="name.toLowerCase() == 'name'">
		                                                                        <div class="left">Name:</div>
		                                                                        <div class="right">${value}</div>
		                                                                    </s:if>
		                                                                 
		                                                                 
		                                                                </s:iterator>
		                                                                <s:iterator value="externalContentProperties">
		                                                                
		                                                                    <s:if test="name.toLowerCase() == 'url'">
		                                                                        <div class="left">Url:</div>
		                                                                        <div class="right"><a href="${value}" target="_blank">${value}</a></div>
		                                                                    </s:if>
		                                                                 
		                                                                </s:iterator>
		                                                                <s:iterator value="externalContentProperties">
		                                                                
		                                                                    <s:if test="name.toLowerCase() == 'filenumber'">
		                                                                        <div class="left">FileNumber:</div>
		                                                                        <div class="right">${value}</div>
		                                                                    </s:if>
		                                                                 
		                                                                </s:iterator>
                                                               </div>
                                                                <br/>
                                                            </s:if>
                                                        </s:iterator>
                                                    </s:if>
                                                </p>
                                            </div>  
                                          </div>
                                        </div>
                                          <div class="bottomBox"> 
                                            <span>
                                              <strong></strong>
                                            </span> 
                                          </div>                                    
                                    </div>
                                  </div>
                                  <!-- End Submission Info Point -->
							
                          <!-- titleBarBox -->
                          <div class="titleBarBox">
                            <h1>
                              <span>Submission ID : ${submission.id}</span>
                              <a href="javascript:;" id="downTips"></a>
                            </h1>
                            <link:studioSubmissionDownload submissionId="${submission.id}" styleClass="downloadBtn">
                                <span>DOWNLOAD</span>
                            </link:studioSubmissionDownload>

                              <!-- submission info -->
						      <div class="submissionInfoFeedback">   
                                <span class="corner tl"></span>
                                <span class="corner tr"></span>
                                <span class="corner bl"></span>
                                <span class="corner br"></span>                                 
                                  <!-- Add Feedback -->
                                  <s:if test="formData.roundType.toString() == 'CHECKPOINT'">
                                  <div class="addFeedback">
                                    <a href="#" class="addFeedbackButton">${feedbackAction}</a>    
                                  </div>
                                  </s:if>
                                  <!-- End Add Feedback -->                               								 	
                                     <!-- Action Bar -->
                                     <div class="actionBar">
                                        <ui:submissionAction contestId="${contestId}" submission="${submission}" singleSubmissionFlag="true"/>
                                      </div>
                                      <!-- End .actionBar -->
                                      <!--  inactiveBar -->
                                      <div class="inactiveBar">
                                          <a href="javascript:;" class="gridView gridViewActive"><span>Grid View</span></a>
                                          <a href="javascript:;" class="singleView"><span>Single View</span></a>
                                      </div>
                                      <!-- End .inactiveBar -->
                              </div>
							  <!-- End .submissionInfo -->   
                              <div class="clear"></div>   
                            </div>
                          <s:if test="contestTypeId != 18L">
                              <c:set var="artifactNumEnd" value="${fn:length(submissionArtifacts)}"/>
                          </s:if>
                          <s:else>
                              <c:set var="artifactNumEnd" value="1"/>
                          </s:else>
                            <!-- End titleBarBox -->                         
                            <!-- imagesPage --> 
                              <div class="imagesPage">
                                    <p class="hide"><strong>Image 1</strong> of ${artifactNumEnd} images</p>
                              </div>
                              <!-- End imagesPage --> 
							<!-- mainContent -->
							<div class="mainContent">			
								<!-- mainArea -->
								<div class="mainArea">
									<!-- thumbnails -->

                                    <s:if test="contestTypeId != 18L">
                                        <div class="thumbnailsList gridViewDiv">
                                            <c:forEach begin="1" end="${fn:length(submissionArtifacts)}"
                                                       var="artifactNum">
                                                <c:set var="imgUrl"
                                                       value="${tcdirect:getSubmissionPreviewImageURL(submission.id, 'medium', artifactNum, pageContext.request)}"/>
                                                <c:set var="filename" value="${submissionArtifacts[artifactNum-1]}"/>
                                                <div class="thumbnailsItem">
                                                    <link:studioSubmission
                                                            submissionId="${submission.id}"
                                                            contestId="${contestId}"
                                                            roundType="${roundType}"
                                                            fullView="true"
                                                            artifactNum="${artifactNum}"><img src="${imgUrl}"
                                                                                              alt="thumbnails" onload="imgLoaded(this, 350, 350)"/><span
                                                            class="hoverPic"></span></link:studioSubmission>
                                                    <p>${filename}</p>
                                                </div>
                                            </c:forEach>
                                            <div class="clear"></div>
                                        </div>
                                    </s:if>
                                    <s:else>
                                        <div class="thumbnailsList thumbnailsListSingle gridViewDiv">
                                            <c:forEach begin="1" end="1"
                                                       var="artifactNum">
                                                <c:set var="imgUrl"
                                                       value="${tcdirect:getSubmissionPreviewImageURL(submission.id, 'medium', artifactNum, pageContext.request)}"/>
                                                <c:set var="filename" value="${submissionArtifacts[artifactNum-1]}"/>
                                                <div class="thumbnailsItem thumbnailsItemLarge">
                                                    <link:studioSubmission
                                                            submissionId="${submission.id}"
                                                            contestId="${contestId}"
                                                            roundType="${roundType}"
                                                            fullView="true"
                                                            artifactNum="${artifactNum}"><img src="${imgUrl}"
                                                                                              alt="thumbnails" onload="imgLoaded(this, 350, 350)"/><span
                                                            class="hoverPic"></span></link:studioSubmission>
                                                    <p>${filename}</p>
                                                </div>

                                            </c:forEach>

                                            <div class="clear"></div>
                                        </div>
                                    </s:else>

								   
									<!-- single submission -->
									<div class="singleSubmission singleViewDiv hide">
										<div class="singleCarousel">
											<ul id="singleCarousel" class="jcarousel-skin-tango">
											</ul>
											<ul id="singleCarouselLookup" class="hide">
                                                <c:forEach begin="1" end="${artifactNumEnd}"
                                                           var="artifactNum">
                                                    <c:set var="imgUrl" value="${tcdirect:getSubmissionPreviewImageURL(submission.id, 'full', artifactNum, pageContext.request)}"/>
                                                    <li><link:studioSubmission
                                                        submissionId="${submission.id}"
                                                        contestId="${contestId}"
                                                        roundType="${roundType}"
                                                        fullView="true"
                                                        artifactNum="${artifactNum}">#IMGSTART# src="${imgUrl}" alt="${artifactNum}" #IMGEND#<span class="hoverPic"></span></link:studioSubmission></li>
                                                </c:forEach>
											</ul>
										</div>
									</div>
									<!-- End .singleSubmission -->
                               </div>
                           </div>
                       </div>
                   </div>

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/contest/submissionViewer/contestVars.jsp"/>
<input type="hidden" id="submissionId" value="${formData.submissionId}"/>

<!-- modal -->

<div id="modalBackground"></div>
<div id="new-modal">
  <s:if test="formData.roundType.toString() == 'CHECKPOINT' && !viewData.checkpointReviewPhaseOpen">
        <!-- #alertModalView -->
        <div id="alertModalView" class="submissions smodal_2">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="feedbackTitle">${feedbackAction}</span>
                      <a href="javascript:;" class="closeModal" id="alertModalViewClose"  title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBodyBox">
              <div class="hasaddFeedback">
                  <div class="textBox">
                      <p class="viewFeedbackContent"><c:out value="${viewData.feedbackText}" /></p>
                  </div>          
              </div> 
              <div class="modalCommandBox viewFeedback"> 
                <a href="javascript:;" class="bottomBtn btnRed widthBox1" id="closeViewBtn"><span><em>Close</em></span></a>
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
        <!-- end #alertModalView -->
  </s:if>
  <s:if test="formData.roundType.toString() == 'CHECKPOINT' && viewData.checkpointReviewPhaseOpen">
        <!-- #alertModal2 -->
        <div id="alertModal2" class="submissions smodal_1">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="feedbackTitle">${feedbackAction}</span>
                      <a href="javascript:;" class="closeModal" id="alertModal2Close" title="Close">Close</a>
    
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBodyBox">
              <div class="addFeedbackBox">
                <h3>Feedback</h3>
                <div>
                  <textarea name="textarea" id="feedback" cols="" rows=""><c:out value="${viewData.feedbackText}" /></textarea>
                </div>
              </div>
              <div class="modalCommandBox"> 
                <a href="javascript:;" class="bottomBtn btnRed widthBox1" id="cancelBtn"><span><em>CANCEL</em></span></a>
                <a href="javascript:;" class="bottomBtn btnRed widthBox2" id="saveFeedbackBtn"><span><em>SAVE FEEDBACK</em></span></a>
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
        <!-- end #alertModal2 -->
        
        <!-- #alertModal3 -->
        <div id="alertModal3" class="submissions smodal_2">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="feedbackTitle">${feedbackAction}</span>
                      <a href="javascript:;" class="closeModal" id="alertModal3Close"  title="Close">Close</a>
                    </div>
    
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBodyBox">
              <div class="hasaddFeedback">
                <div class="hasAdded">Your feedback has been <s:if test="%{(viewData.feedbackText+'').trim().length()!=0}">edited</s:if><s:else>added</s:else></div>
                  <div class="textBox">
                    <h4>Feedback</h4>
    
                      <p class="viewFeedbackContent"><c:out value="${viewData.feedbackText}" /></p>
                  </div>          
              </div>
              <div class="modalCommandBox"> 
                <a href="javascript:;" class="bottomBtn btnRed widthBox1" id="doneBtn"><span><em>DONE</em></span></a>
                <a href="javascript:;" class="bottomBtn btnRed widthBox2" id="editFeedbackBtn"><span><em>EDIT FEEDBACK</em></span></a>
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
        <!-- end #alertModal3 -->
  </s:if>
</div>
<!-- end modal -->
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>

