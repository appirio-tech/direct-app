<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Launch copilot selection contest page.
  - Since: TC Direct - Launch Copilot Selection Contest assembly
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="copilot" scope="request"/>
    <link rel="stylesheet" href="/css/modal.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/jquery.tools.min.js"></script> 
    <script type="text/javascript" src="/scripts/launch/entity.js?v=49"></script>
    <script type="text/javascript" src="/scripts/launch/main.js?v=49"></script>
    <script type="text/javascript" src="/scripts/launchCopilotContest.js?v=49"></script>
</head>

<body id="page">
    <div id="wrapper">
        <div id="wrapperInner">
            <div id="container">
                <div id="content">

                    <jsp:include page="includes/header.jsp"/>

                    <div id="mainContent" style="overflow:visible">

                        <jsp:include page="includes/right.jsp"/>

                        <div id="area1"><!-- the main area -->
                        <div class="area1Content">

                                <div class="currentPage">
                                    <a href="${ctx}/dashboard" class="home">Dashboard</a> &gt;
                                    <a href="<s:url action='launchCopilotContest' namespace='/copilot'/>">Copilots</a> &gt;
                                    <strong>Launch Copilot Selection Contest</strong>
                                </div> <!-- End .currentPage -->

                                <div id="launchContestOut" class="launchCopilotContest">

                                     <div id="launchCopilotPage" class="launchpage">
                                       <jsp:include page="includes/launchCopilot/launchCopilot.jsp"/>
                                     </div>

                                     <div id="receiptCopilotPage" class="launchpage hide">
                                       <jsp:include page="includes/launchCopilot/receiptCopilot.jsp"/>
                                     </div>
                                </div>
                                <!-- end #launchContestOut -->
                        </div> <!-- End.area1Content -->
                        </div> <!-- End #area1 -->

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

    <div id="TB_overlay" class="TB_overlayBG"></div>
    <div id="TB_window">
        <div id="TB_title">
            <div id="TB_ajaxWindowTitle"></div>
            <div id="TB_closeAjaxWindow">
                <a href="javascript:;" id="TB_closeWindowButton"></a>
            </div>
        </div>
        <div id="TB_ajaxContent">
            <div class="helpPopupInner details">
                
                <div class="logoArea">
                    <a href="javascript:;"><img src="images/TopCoder_logo.png" alt="TopCoder" /></a>
                </div>
            </div><!-- End .helpPopupInner -->
        </div>
        <div id="placeHolder">PlaceHoldertest</div>
    </div>
        
    
    <div id="allDescriptionIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> Enter a description of the opportunity that you are comfortable showing to any logged in TopCoder member.  This is typically a relatively short overview that will gain the interest of copilots that are scanning opportunities.  Do not include any confidential information.
                    </p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>

    <div id="privateDescriptionIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> Enter a complete description of the opportunity.  This information will only be visible to approved copilots that have confidentiality agreements on file with TopCoder.  Include as much detail about your project as possible.  You can also upload files, which will only be visible as part of the private description.
                    </p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    
    <div id="costIcon_help" class="tooltipContainer">
        <span class="arrow11"></span>
        <div class="tooltipLeft"><div class="tooltipRight"><div class="tooltipBottom">
            <div class="tooltipBottomLeft"><div class="tooltipBottomRight">

                <div class="tooltipCaption">
                    <div class="tooltipCaptionLeft"><div class="tooltipCaptionRight">
                        <div class="tooltipCaptionInner">
                            <h2>ToolTip</h2>
                        </div>
                        <!-- End .tooltipCaptionInner -->
                    </div></div>
                </div><!-- End .tooltipCaption -->
                <div class="tooltipContent">
                    <p> placeholder info for cost information</p>
                </div><!-- End .tooltipContent -->

            </div></div>
        </div></div></div>
    </div>
    
    <div id="draftPanelTrigger" class="hide">
    </div>
    <div id="saveAsDraft" class="acceptRejectPopup hide">
        <div class="popupMask">
            <div class="popupWrap">
                <div class="popupContent">
                    <dl>
                        <dt>Your Copilot Selection Contest has been saved as draft</dt>
                         
                        <dd class="yesno">
                             <a href="#" class="button6" id="saveAsDraftOK"><span class="left"><span class="right">OK</span></span></a>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>

    <!-- this area will contain the popups of this page -->
    <div class="popups">
        <!-- Add Project Dialog-->
        <div id="addProjectDialog" title="Create New Project" class="dialog-box hide">
          <div id="addProjectForm">
            <div class="fi">
               <label for="projectName">Name:</label>
               <input id="projectName" name="projectName" type="text" width="30" maxlength="255" />
            </div>
            <div class="fi">
               <label for="projectDescription">Description:</label>
               <textarea id="projectDescription" name="projectDescription" rows="5" cols="30" ></textarea>
            </div>
            <div class="popupButtons">
              <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
              <a href="javascript:;" onclick="addNewProject();" class="button1"><span>Yes</span></a>
            </div>
          </div><!-- End #addProjectForm -->

          <div id="addProjectResult">
            <p></p>
            <div class="popupButtons">
              <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Close</span></a>
            </div>
          </div><!-- End #addProjectResult -->
        </div><!-- End #addProjectDialog -->
    </div>
    <!-- End .popups -->    
    
    <jsp:include page="includes/popups.jsp"/>
    
</body>
<!-- End #page -->

</html>
