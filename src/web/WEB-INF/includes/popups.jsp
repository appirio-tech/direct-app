<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%--
  - Author: winsty, GreatKevin, duxiaoyang, Ghost_141, GreatKevin, TCSASSEMBLER
  - Version: 3.3
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - add repost and new version pop ups .
  -
  - Version 1.2 - TC Direct UI Improvement Assembly 1 Change Note
  - - add delete user confirmation
  -
  - Version 1.2.1 - Release Assembly - TC Direct UI Improvement Assembly 3 Change Note
  - - remove uneccessary TB_overlay and TB_window
  -
  - Version 1.3 - Release Assembly - TopCoder Cockpit AJAX Revamp Change Note
  - - move all the popup windows into popups jsp
  -
  - Version 1.4 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R1
  - - add modal windows for the start a new project process
  -
  - Version 1.4 - Release Assembly - Release Assembly - TopCoder Cockpit Project Overview Update 1 Changes Note
  - - add the copilot management widget modal window
  -
  - Version 1.5 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R1
  - - add modal windows for the start a new project process   
  - 
  - Version 1.6 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R2
  - - add modal windows for the start a new project process R2
  -
  - Version 1.7 - TC Accounting Tracking Invoiced Payments Part 2
  - - add modal windows for creating invoice in biling cost report page
  -
  - Version 1.8 - Module Assembly - TC Cockpit Project Overview Project General Info
  - - add modal window for the project description in project overview page
  -
  - Version 1.9 - Module Assembly - TC Cockpit Project Milestones Management Front End
  - - add modal window for the project milestone management in project milestone page
  -
  - Version 2.0 - Release Assembly - TC Direct Cockpit Release Three version 1.0
  - - Add tooltip for the timeline of contest dashboard in contest details page
  -
  - Version 2.1 - Release Assembly - TC Direct Cockpit Release Four
  - - Add modal background for spec review modal
  -
  - Version 2.2 - Release Assembly - TopCoder Cockpit Software Milestone Management
  - - Add modal windows for software milestone management
  -
  - Version 2.3 - Release Assembly - TC Direct Project Forum Configuration Assembly
  - - Added popup for forum configuration
  -
  - Version 2.4 - Release Assembly - TC Direct Project Forum Configuration Assembly 2
  - - Added popup for forum configuration on project overview page
  -
  - Version 2.5 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
  - - Fix multiple bugs.
  -
  - Version 2.6 - Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0
  - - Fixed multiple bugs.  
  -
  - Version 2.7 - TC-Studio - Wireframe Viewer Modal Window Direct integration assembly v1.0
  - - Added Wireframe Viewer Modal Window.
  -
  - Version 2.8 - Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly
  - - Adds the copilot posting winner pickup confirmation dialogue
  -
  -  Version 2.9 Release Assembly - TopCoder Copilot Feedback Updates
  - - Adds 4 ratings to the copilot feedback add, edit, view modal windows
  -
  - Version 3.0 (Module Assembly TC - Cockpit Tasks Management Services Setup and Quick Add Task)
  - - Adds the modal window for the project tasks page
  -
  - Version 3.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
  - - Adds the modal window for the project assets page
  -
  - Version 3.2 (BUGR - 9796)
  - - Add modal for update/set round id.
  -
  - Version 3.3 (TopCoder Cockpit Copilot Posting Submission Game Plan Preview and Stats)
  - - Adds the modal window for the copilot posting submission preview
  -
  - Version 3.4 (TC Direct Rebranding Assembly Project and Contest related pages)
  - - Updated the popup modals for reabranding
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="popups"><!-- this area will contain the popups of this page -->
      <!-- Help Popup -->
    <div id="helpPopup" class="hide">
        <div class="helpPopupInner details">

        </div>
        <!-- End .helpPopupInner -->
    </div>
    <!-- End #helpPopup -->

    <!-- Message Dialog-->
    <div id="msgDialog" title="Message" class="dialog-box hide">
        <p>
        </p>

    </div>

    <!-- Error Dialog-->
    <div id="errorDialog" title="Errors" class="dialog-box hide">
        <p>
        </p>

    </div>

      <!-- Repost Dialog -->
    <div id="repostDialog" title="Repost" class="dialog-box hide">
      <div id="repostForm">
          <p>
              Are you sure to repost the failed challenge?
          </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>CANCEL</span></a>
          <a href="javascript:;" onclick="repostContest();" class="button1"><span>YES</span></a>
        </div>
      </div><!-- End #repostForm -->

      <div id="repostResult">
        <p>
            Challenge has been reposted successfully! <br/>
            Click edit button if you want to edit it.
        </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="refreshPage(this);" class="button1"><span>CLOSE</span></a>
            <a href="javascript:;" onclick="editRepost();" class="button1"><span>EDIT</span></a>
        </div>
      </div><!-- End #repostResult -->
    </div><!-- End #repostDialog -->

      <!-- New Version Dialog -->
    <div id="newVersionDialog" title="New Version" class="dialog-box hide">
      <div id="newVersionConfirm">
          <p>
               Are you sure to release a new version?
          </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>CANCEL</span></a>
          <a href="javascript:;" onclick="newVersionConfirm();" class="button1"><span>YES</span></a>
        </div>
      </div><!-- End #newVersionStep1 -->

      <div id="newVersionDev">
          <p>
               Do you want to create corresponding development challenge?
          </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="newVersionDevYes();" class="button1"><span>YES</span></a>
          <a href="javascript:;" onclick="newVersionDevNo();" class="button1"><span>NO</span></a>
        </div>
      </div><!-- End #newVersionDev -->

      <div id="newVersionMinor">
          <p>
               Do you want to create a major version or minor version?
          </p>
        <div class="popupButtons">
            <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>CANCEL</span></a>
          <a href="javascript:;" onclick="newVersionChooseMajor();" class="button1"><span>MAJOR VERSION</span></a>
          <a href="javascript:;" onclick="newVersionChooseMinor();" class="button1"><span>MINOR VERSION</span></a>
        </div>
      </div><!-- End #newVersionMinor -->

      <div id="newVersionResult">
        <p>
            New version has been created successfully! <br/>
            Click edit button if you want to edit it.
        </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="refreshPage(this);" class="button1"><span>CLOSE</span></a>
            <a href="javascript:;" onclick="editNewVersionProject();" class="button1"><span>EDIT</span></a>
        </div>
      </div><!-- End #repostResult -->
    </div><!-- End #repostDialog -->


    <div id="TB_overlay" class="TB_overlayBG" style="display:none"></div>

    <div id="TB_window_custom" class="specrev-window">
        <h1>Plan Specification Review</h1>
        <a class="review-now newButtonGreen" href="../contest/startSpecReview.action?startMode=now">START SPEC REVIEW NOW</a>
        <p class="or">or</p>
        <a class="review-later newButtonGreen" href="../contest/startSpecReview.action?startMode=later">START SPEC REVIEW LATER</a>
        <p class="note">*48 hours prior to the scheduled challenge start time.</p>
    </div>

 <div id="deleteUserConfirmation" title="Do you really want to delete the user?" style="display:none;">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This will delete the user from the project. Please confirm you want to delete the user?</p>
    </div>

    <!-- AJAX preloading indicator -->
    <div id="modalBackground"></div>
    <div id="new-modal">
        <!-- ajax preloader modal -->
        <div class="outLay" id="preloaderModal" style="display: none;">
            <div class="modalHeaderSmall">
                <div class="modalHeaderSmallRight">
                    <div class="modalHeaderSmallCenter"></div>
                </div>
            </div>
            <div class="modalBody">
                <span id="preloaderAnimation">
                <img alt="Loading" src="/images/preloader-loading.gif">
                </span>
                <div class="preloaderTips">Loading...</div>
            </div>
            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter">
                        <div class="&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;"></div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end ajax preloader modal -->

        <div id="cofirmChooseCopilotConfirmModal" class="outLay newModal">
            <div class="barTop">
                <div class="barTopR">
                    <div class="barTopM"></div>
                </div>
            </div>
            <div class="barMid">
                <div class="barMidR">
                    <div class="barMidM">
                        <!-- content -->
                        <div class="modal-content">
                            <div class="modalContainer">
                                <dl>
                                    <dd>
                                        Are you sure you don't want to choose a <strong class="red">runner-up</strong>?
                                    </dd>
                                </dl>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonArea">
                                <a href="javascript:;" title="YES" class="yesbutton newButtonGreen button6"><span class="left"><span class="right">YES</span></span></a>
                                <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6"><span class="left"><span class="right">NO</span></span></a>
                            </div>
                        </div>
                        <!-- End .content -->
                    </div>
                </div>
            </div>
            <div class="barBot">
                <div class="barBotR">
                    <div class="barBotM"></div>
                </div>
            </div>
        </div>

        <div id="forgiveChooseCopilotConfirmModal" class="outLay newModal">
            <div class="barTop">
                <div class="barTopR">
                    <div class="barTopM"></div>
                </div>
            </div>
            <div class="barMid">
                <div class="barMidR">
                    <div class="barMidM">
                        <!-- content -->
                        <div class="modal-content">
                            <div class="modalContainer">
                                <dl>
                                    <dd>
                                        Are you sure you don't want to choose any <strong class="red">copilot</strong>?
                                    </dd>
                                </dl>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonArea">
                                <a href="javascript:;" title="YES" class="yesbutton newButtonGreen button6"><span class="left"><span class="right">YES</span></span></a>
                                <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6"><span class="left"><span class="right">NO</span></span></a>
                            </div>
                        </div>
                        <!-- End .content -->
                    </div>
                </div>
            </div>
            <div class="barBot">
                <div class="barBotR">
                    <div class="barBotM"></div>
                </div>
            </div>
        </div>

        <div id="projectPlanEmptyModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span>Project Plan Empty</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <p>Project plan is empty. Please fill challenges table to view plan chart.</p>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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

        <!-- #jsGanttChartPopup -->
        <div id="jsGanttChartModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="fLeft">Project Plan Gantt Chart</span>
                        <a class="button5" id="hideLeftPanel" href="javascript:;">Hide Left Panel</a>

                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <table class="projectStats" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr>
                            <th>Estimated Duration</th>
                            <th>Estimated Member Cost</th>
                            <th>Estimated Challenge Fee</th>
                            <th>Estimated Total Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td id="durationStat" class="date fees">xxx days</td>
                            <td id="costStat" class="date fees">$xxx,xxx</td>
                            <td id="feeStat" class="date fees">$xxx,xxx</td>
                            <td id="totalStat" class="date fees">$xxx,xxx</td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- gantt chart container-->
                    <div id="jsGanttChartDiv" class="gantt"></div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #jsGanttChartPopup -->

        <!-- #projectPlanCircular -->
        <div id="projectPlanCircularModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span>Project Plan Circular</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <div id="ganttChartCircular">
                        <span id="circularContestNumbers"></span> have circular dependencies.
                    </div>
                    <div id="ganttChartCircularMultiple">
                        The following challenges have circular dependencies:
                        <ul id="multipleCircularsList"></ul>
                    </div>
                    <!-- end gantt chart circular -->
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #projectPlanCircular -->

                <!-- #addNewProjectModal -->
        <div id="addNewProjectModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Create New Project
                        <a href="javascript:;" class="closeModal closeProjectModal" title="Close" onclick="modalCloseAddNewProject();">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Name:</label>
                        <input type="text" class="text" id="newProjectName" name="newProjectName"/>

                        <div class="clearFix"></div>
                    </div>
                    <div class="modalRow">
                        <label>Description:</label>
                        <textarea id="newProjectDescription" name="newProjectDescription" class="textField" rows=""
                                  cols=""></textarea>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1"><span class="btnR"><span class="btnC"
                                                                                       onclick="addNewProject();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CREATE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal closeProjectModal"
                       onclick="modalCloseAddNewProject();"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
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
        <!-- end #addNewProjectModal -->

        <!-- #showWireframeModal -->
        <div id="showWireframeModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Submission ID: <span id="submissionIdInTitle"></span>
                        <!--<a id="validator" href='javascript:;'>Validate Wireframe</a>-->
                        <a href="javascript:;" class="closeModal closeProjectModal" title="Close" onclick="modalCloseAddNewProject();">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="modalBodyContent">
                    <iframe id="frame_window" name="my_frame" src=""></iframe>
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


        <!-- #processInvoiceRecord -->
        <div id="processInvoiceRecordModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="title">Process Invoice Record</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Invoice Number:</label>
                        <input type="text" class="text" id="invoiceNumber"/>
                        <div class="clearFix"></div>
                        <div class="errorInfo hide">Please input invoice number</div>
                    </div>
                    <div class="modalRow creditRow hide">
                        <label>Credit Amount:</label>
                        <input type="text" class="text" id="creditAmount"/>
                        <div class="clearFix"></div>
                        <div class="errorInfo errorInfo1 hide">Please input credit amount</div>
                        <div class="errorInfo errorInfo2 hide">Credit amount must be negative</div>
                    </div>
                    <div class="modalRow">
                        <label class="fLeft">Invoice Date:</label>
                        <input type="text" class="text fLeft date-pick" id="invoiceDate" readonly value="" />
                        <div class="clearFix"></div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 updateInvoice newButtonGreen"><span class="btnR"><span class="btnC"
                                                                                       onclick="">SAVE</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel newButtonOrange closeModal"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
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
        <!-- end #processInvoiceRecordModal -->
        
        <div id="invoiceNumberDuplicatedModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Warning
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <div class="errorIcon fLeft"><img src="/images/warning_milestone.png" alt=""/></div>
                        <p class="twoLine">The invoice number is existing in the table, do you want to proceed?</p>
                    </div>
                </div>
                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 updateInvoice newButtonGreen"><span class="btnR"><span class="btnC"
                                                                                       onclick="">YES</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal newButtonOrange"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
                </div>
            </div>
            
            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter"></div>
                </div>
            </div>
        </div>
        
        <!-- copilot manage modal -->
        <div id="copilotManageModal" class="outLay">
        <div class="inner">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Choose Your Copilot
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <!-- content -->
            <div class="modalBody">
                <div class="addUserForm">
                    <div class="addUserLeft">
                        <!-- title -->
                        <div class="addUserTitle">
                            <p>Available Copilots</p>
                            <a href="javascript:;" class="selectAll">Select All</a>
                        </div>
                        <!-- End .addUserTitle -->
                        <!-- search -->
                        <div class="searchBox">
                            <input type="text" class="text"/>
                            <a href="javascript:;" class="downloadProfile newButtonGreen searchCopilot"><span
                                    class="profileLeft">Search</span></a>
                        </div>
                        <!-- End .searchBox -->
                        <!-- list -->
                        <div class="addUserList">
                            <ul>

                            </ul>
                        </div>
                        <!-- End .addUserList -->
                    </div>
                    <div class="addUserButton">
                        <a href="javascript:;" class="addItem">Add</a>
                        <a href="javascript:;" class="removeItem">Add</a>
                    </div>
                    <div class="addUserRight">
                        <!-- title -->
                        <div class="addUserTitle">
                            <p>Chosen Copilots</p>
                            <a href="javascript:;" class="removeAll">Remove All</a>
                        </div>
                        <!-- End .addUserTitle -->
                        <!-- list -->
                        <div class="addUserList">
                            <ul>

                        </ul>
                    </div>
                    <!-- End .addUserList -->
                </div>
                <div class="clear"></div>
                <span class="errorMessage"></span>
            </div>
            <div class="buttonArea">
                <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span class="left"><span
                        class="right">SAVE</span></span></a>
                <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span
                        class="left"><span class="right">CANCEL</span></span></a>

                    <div class="clearFix"></div>
                </div>
            </div>
            <!-- End .content -->

            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter"></div>
                </div>
            </div>
            <!-- end .modalFooter -->
        </div>
    </div>
    <!-- end copilot manage modal -->
    
 	<!-- #copilotRemoveModal -->
    <div id="copilotRemoveModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Remove Copilot</span>
                    <a href="javascript:" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
            	<br/>
                <p style="padding:5px;">The Copilot <span class="b handle addSpace"> </span> has been removed from Project <span class="b projectName">.</span></p>
                <br/> 
            </div>
            <div class="modalCommandBox"> 
                <a href="javascript:" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
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
    <!-- end #copilotRemoveModal -->	
    
    <!-- #copilotSaveSuccessModal -->
    <div id="copilotSaveSuccessModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Confirmation</span>
                    <a href="javascript:" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
            	<br/>
                <p style="padding:5px;">Copilots of project <span class="b projectName"></span> have been saved.</p>
                <br/> 
            </div>
            <div class="modalCommandBox"> 
                <a href="javascript:" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
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
    <!-- end #copilotSaveSuccessModal -->        
        
    <!-- #addFeedbackModal -->
    <div id="addFeedbackModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Submission Feedback</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <textarea cols="10" rows="10"></textarea>
                <p class="error hide">Feedback to add cannot be empty.</p>
            </div>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 addBtn"><span class="btnR"><span class="btnC">ADD</span></span></a>
                <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
    <!-- end #addFeedbackModal -->

    <!-- #editFeedbackModal -->
    <div id="editFeedbackModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Edit General Feedback</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <textarea cols="10" rows="10"></textarea>
                <p class="error hide"></p>
            </div>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 saveBtn"><span class="btnR"><span class="btnC">SAVE</span></span></a>
                <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
    <!-- end #editFeedbackModal -->
                                                      
    <!-- #saveSuccessModal -->
    <div id="saveSuccessModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Successfully Saved</span>
                    <a href="javascript:" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>Checkpoint winners and Feedback saved.</p> 
            </div>
            <div class="modalCommandBox"> 
                <a href="javascript:" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
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
    <!-- end #saveSuccessModal -->
    
    <!-- #lockinSuccessModal -->
    <div id="lockinSuccessModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Successfully Locked In</span>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>Checkpoint winners and Feedback locked.</p> 
            </div>
            <div class="modalCommandBox"> 
                <a href="javascript:" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
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
    <!-- end #lockinSuccessModal -->
 
    <!-- #lockinConfirmModal -->
    <div id="lockinConfirmModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Lock In Checkpoints</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>Are you sure you want to lock selected submissions?<span>You cannot undo or provide/ edit feedback after lock in.</span></p>
            </div>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 yesBtn"><span class="btnR"><span class="btnC">YES</span></span></a>
                <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">NO</span></span></a>
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
    <!-- end #lockinConfirmModal -->


    <!-- #lockinInvalidModal -->
    <div id="lockinInvalidModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Lock In Checkpoints</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>The winner you chosen is invalid. You should choose winners with placements starting from 1 consecutively.</p>
            </div>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">OK</span></span></a> 
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
    <!-- end #lockinInvalidModal -->
    
    <!-- #lockinNoWinnerModal -->
    <div id="lockinNoWinnerModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>Lock In Checkpoints</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p><strong>No Checkpoint Winners are selected.</strong>Are you sure you don't want to select any milestone winners for this challenge?<span>You cannot undo or provide feedback after lock in.</span></p>
            </div>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">GO BACK TO SELECT MILESTONE WINNERS</span></span></a>
                <a href="javascript:;" class="newButton1 lockinBtn"><span class="btnR"><span class="btnC">LOCK IN MILESTONS</span></span></a>
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
    <!-- end #lockinNoWinnerModal -->
    
    <!-- #lockinNoFeedbackModal -->
    <div id="lockinNoFeedbackModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                <span>Lock In Checkpoints</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <p><strong>Neither feedback on milestone winning submissions, nor general feedback is provided. </strong>Do you want to continue locking in milestones?<span>You cannot undo or provide feedback after lock in.</span></p>
        </div>
        <div class="modalCommandBox">
        <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">GO BACK TO PROVIDE FEEDBACK</span></span></a>
            <a href="javascript:;" class="newButton1 lockinBtn"><span class="btnR"><span class="btnC">LOCK IN MILESTONES</span></span></a>
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
    <!-- end #lockinNoFeedbackModal -->

        <!-- #setRoundIdModal -->
        <div id="setRoundIdModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Set Round ID
                        <a href="javascript:;" class="closeModal closeProjectModal" title="Close" onclick="modalRoundIdClose();">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Round ID:</label>
                        <input type="text" class="text" id="newRoundId" name="newRoundId"/>

                        <div class="clearFix"></div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1"><span class="btnR"><span class="btnC" onclick="setRoundId();">SET</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal closeProjectModal"
                       onclick="modalRoundIdClose();"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
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
        <!-- end #setRoundIdModal -->

        <!-- #updateRoundIdModal -->
        <div id="updateRoundIdModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Update Round ID
                        <a href="javascript:;" class="closeModal closeProjectModal" title="Close" onclick="modalRoundIdClose();">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <label>Round ID:</label>
                        <input type="text" class="text" id="updatedRoundId" name="updatedRoundId"/>

                        <div class="clearFix"></div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1"><span class="btnR"><span class="btnC" onclick="updateRoundId();">UPDATE</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal closeProjectModal"
                       onclick="modalRoundIdClose();"><span class="btnR"><span
                            class="btnC">CANCEL</span></span></a>
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
        <!-- end #updateRoundIdModal -->

        <s:if test='%{#request.CURRENT_TAB  == "tasks" && #request.PAGE_TYPE  == "project"}'>
            <jsp:include page="./modal/projectTaskModals.jsp"/>
        </s:if>
        <s:if test='%{#request.CURRENT_TAB  == "assets" && #request.PAGE_TYPE  == "project"}'>
            <jsp:include page="./modal/assetModals.jsp"/>
        </s:if>
        <s:if test='%{#request.CURRENT_TAB  == "overview" && #request.PAGE_TYPE  == "project"}'>
            <div id="projectDescModal" class="outLay">
                <div class="inner">
                    <div class="modalHeader">
                        <div class="modalHeaderRight">
                            <div class="modalHeaderCenter">
                                Project Description
                                <a href="javascript:;" class="closeModal" title="Close">Close</a>
                            </div>
                        </div>
                    </div>
                    <!-- end .modalHeader -->

                    <!-- content -->
                    <div class="modalBody">
                        <div class="description">
                            <p><c:out value="${viewData.projectGeneralInfo.project.description}"/></p>
                        </div>
                        <div class="buttonArea">
                            <a href="javascript:;" title="OK" class="button6 closeModal"><span class="left"><span class="right">OK</span></span></a>
                            <div class="clearFix"></div>
                        </div>
                    </div>
                    <!-- End .content -->

                    <div class="modalFooter">
                        <div class="modalFooterRight">
                            <div class="modalFooterCenter"></div>
                        </div>
                    </div>
                    <!-- end .modalFooter -->
                </div>
            </div>

            <div id="newCopilotFeedbackModal" class="outLay feedbackModal">
                <div class="inner">
                    <div class="modalHeader">
                        <div class="modalHeaderRight">
                            <div class="modalHeaderCenter">
                                Copilot Feedback
                                <a href="javascript:;" class="closeModal" title="Close">Close</a>
                            </div>
                        </div>
                    </div>
                    <!-- end .modalHeader -->
                    <!-- content -->
                    <div class="modalBody">
                        <div class="question">
                            <p>Would you like to work with this copilot <span class="copilotHandleSpan"></span> again?</p>
                            <input type="radio" name="workAgain" value="yes"><label>Yes</label>
                            <input type="radio" name="workAgain" value="no"><label>No</label>
                            <span class="errorMessage"> </span>
                        </div>
                        <div class="rating">
                            <dl class="pRatings">
                                <dt>How would you rate <span class="copilotHandleSpan"></span> on:</dt>
                                <dd>
                                    <label title="Timelines, gameplans">Project timelines</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Quality of deliverables and final &quot;product&quot;">Quality</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Setting expectations correctly, proactively raising issues">Communication</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Manages other copilots, manages challenges, manages inter-contest work">Challenge management</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                            </dl>
                            <span class="errorMessage"> </span>
                        </div>
                        <div class="comment">
                            <p>Your feedback</p>
                            <textarea rows="10" cols="74" style="max-width:613px"></textarea>
                            <p><span class="errorMessage"> </span></p>
                        </div>
                        <div class="buttonArea">
                            <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton createNewFeedback"><span class="left"><span class="right">SAVE</span></span></a>
                            <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                            <div class="clearFix"></div>
                        </div>
                    </div>
                    <!-- End .content -->

                    <div class="modalFooter">
                        <div class="modalFooterRight">
                            <div class="modalFooterCenter"></div>
                        </div>
                    </div>
                    <!-- end .modalFooter -->
                </div>
            </div>

            <div id="editCopilotFeedbackModal" class="outLay feedbackModal">
                <div class="inner">
                    <div class="modalHeader">
                        <div class="modalHeaderRight">
                            <div class="modalHeaderCenter">
                                Edit Copilot Feedback
                                <a href="javascript:;" class="closeModal" title="Close">Close</a>
                            </div>
                        </div>
                    </div>
                    <!-- end .modalHeader -->
                    <!-- content -->
                    <div class="modalBody">
                        <div class="question">
                            <p class="warningMessage">Edit your feedback below</p>
                            <p>Would you like to work with this copilot <span class="copilotHandleSpan"></span> again?</p>
                            <input type="radio" name="workAgain" value="yes"><label>Yes</label>
                            <input type="radio" name="workAgain" value="no"><label>No</label>
                            <span class="errorMessage"> </span>
                        </div>
                        <div class="rating">
                            <dl class="pRatings">
                                <dt>How would you rate <span class="copilotHandleSpan"></span> on:</dt>
                                <dd>
                                    <label title="Timelines, gameplans">Project timelines</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Quality of deliverables and final &quot;product&quot;">Quality</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Setting expectations correctly, proactively raising issues">Communication</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                                <dd>
                                    <label title="Manages other copilots, manages challenges, manages inter-contest work">Challenge management</label>
                                    <div class="ratingEdit"></div>
                                </dd>
                            </dl>
                            <span class="errorMessage"> </span>
                        </div>
                        <div class="comment">
                            <p>Your feedback</p>
                            <textarea rows="10" cols="74" style="max-width:613px"></textarea>
                            <p><span class="errorMessage"> </span></p>
                        </div>
                        <div class="buttonArea">
                            <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton editNewFeedback"><span class="left"><span class="right">SAVE</span></span></a>
                            <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                            <div class="clearFix"></div>
                        </div>
                    </div>
                    <!-- End .content -->

                    <div class="modalFooter">
                        <div class="modalFooterRight">
                            <div class="modalFooterCenter"></div>
                        </div>
                    </div>
                    <!-- end .modalFooter -->
                </div>
            </div>

            <div id="viewCopilotFeedbackModal" class="outLay feedbackModal">
                <div class="inner">
                    <div class="modalHeader">
                        <div class="modalHeaderRight">
                            <div class="modalHeaderCenter">
                                View Copilot Feedback
                                <a href="javascript:;" class="closeModal" title="Close">Close</a>
                            </div>
                        </div>
                    </div>
                    <!-- end .modalHeader -->
                    <!-- content -->
                    <div class="modalBody">
                        <div class="question">
                            <b>Would you like to work with this copilot <span class="copilotHandleSpan"></span> again?</b><br/>
                            <span class="answer"></span>
                        </div>
                        <div class="rating">
                            <dl class="pRatings">
                                <dt><b>How would you rate <span class="copilotHandleSpan"></span> on:</b></dt>
                                <dd>
                                    <label title="Timelines, gameplans">Project timelines</label>
                                    <div class="ratingView"></div>
                                </dd>
                                <dd>
                                    <label title="Quality of deliverables and final &quot;product&quot;">Quality</label>
                                    <div class="ratingView"></div>
                                </dd>
                                <dd>
                                    <label title="Setting expectations correctly, proactively raising issues">Communication</label>
                                    <div class="ratingView"></div>
                                </dd>
                                <dd>
                                    <label title="Manages other copilots, manages challenges, manages inter-contest work">Challenge management</label>
                                    <div class="ratingView"></div>
                                </dd>
                            </dl>
                            <span class="errorMessage"> </span>
                        </div>
                        <div class="comment">
                            <b>Your feedback</b><br/>
                            <span class="text"></span>
                        </div>

                    </div>
                    <!-- End .content -->

                    <div class="modalFooter">
                        <div class="modalFooterRight">
                            <div class="modalFooterCenter"></div>
                        </div>
                    </div>
                    <!-- end .modalFooter -->
                </div>
            </div>

        </s:if>

        <!-- start modals for create new project -->
        <s:if test='%{#request.CURRENT_TAB  == "createNewProject"}'>
        <div id="errortModal" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Errors</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <div class="modalContainer">
                                    <div class="errorIcon"><img src="/images/errorIcon.png" alt=""/></div>
                                    <p>There's no selected project game plan.
                                        You must select one before continue
                                        to next step.</p>
                                </div>
                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="OK" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span
                                            class="right">OK</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="errortModalStep4" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Errors</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <div class="modalContainer">
                                    <div class="errorIcon"><img src="/images/errorIcon.png" alt=""/></div>
                                    <p class="twoLine">You haven't answered the question about copilot,
                                        please select one answer to continue.</p>
                                </div>
                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="OK" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">OK</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="errorcModal" class="outLay smallModal">
            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Errors</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <div class="modalContainer">
                                    <div class="errorIcon"><img src="/images/errorIcon.png" alt=""/></div>
                                    <p class="twoLine">You haven't select need a copilot options, please select one
                                        option to continue.</p>
                                </div>
                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="OK" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">OK</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div id="detailModal" class="outLay modal">
            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Project Type 1</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p class="paragraph1">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium
                                    doloremque
                                    laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et
                                    quasi
                                    architecto beatae vitae dicta sunt explicabo.</p>

                                <p class="paragraph2">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                                    incididunt.</p>

                                <div class="tableOut">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td class="firstTd">Budget</td>
                                            <td class="dataCost">$ 0000 - $ 0000</td>
                                        </tr>
                                        <tr>
                                            <td class="firstTd">Duration</td>
                                            <td class="dataDur">000 - 000 days</td>
                                        </tr>
                                        <tr>
                                            <td rowspan="6" class="firstTd">Challenge</td>
                                            <td><span>Conceptualization</span><span class="dataContest">1</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><span>Wireframes</span><span class="dataContest">1-3</span></td>
                                        </tr>
                                        <tr>
                                            <td><span>Web Design</span><span class="dataContest">1-2</span></td>
                                        </tr>
                                        <tr>
                                            <td><span>Design</span><span class="dataContest">2-6</span></td>
                                        </tr>
                                        <tr>
                                            <td><span>Development</span><span class="dataContest">2-6</span></td>
                                        </tr>
                                        <tr>
                                            <td><span>Assembly</span><span class="dataContest">1</span></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="CLOSE"
                                       class="closebutton newButtonOrange  button6 closeButton"><span
                                            class="left"><span class="right">CLOSE</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="gamePlanModal" class="outLay newModal">
            <div class="barTop">
                <div class="barTopR">
                    <div class="barTopM"></div>
                </div>
            </div>
            <div class="barMid">
                <div class="barMidR">
                    <div class="barMidM">
                        <!-- title -->
                        <div class="modal-title">
                            <h2>Game Plan for Project Type 1 - Medium</h2>
                            <a href="javascript:;" class="closeModal">Close</a>
                        </div>
                        <!-- End .modal-title -->
                        <!-- content -->
                        <div class="modal-content">
                            <div class="actions">
                                <a class="whiteBtn" href="#">
                                    <span class="buttonMask"><span class="text">Full Screen Preview</span></span>
                                </a>
                            </div>
                            <div id="ganttChartDiv"></div>
                            <div class="clear"></div>
                            <div class="buttonArea">
                                <a href="javascript:;" title="CLOSE" class="closebutton newButtonOrange  button6 closeButton"><span
                                        class="left"><span class="right">CLOSE</span></span></a>
                            </div>
                        </div>
                        <!-- End .content -->
                    </div>
                </div>
            </div>
            <div class="barBot">
                <div class="barBotR">
                    <div class="barBotM"></div>
                </div>
            </div>
        </div>
        <div id="customConfirmModal" class="outLay newModal">
            <div class="barTop">
                <div class="barTopR">
                    <div class="barTopM"></div>
                </div>
            </div>
            <div class="barMid">
                <div class="barMidR">
                    <div class="barMidM">
                        <!-- title -->
                        <div class="modal-title">
                            <h2>Custom Game Plan</h2>
                            <a href="javascript:;" class="closeModal">Close</a>
                        </div>
                        <!-- End .modal-title -->
                        <!-- content -->
                        <div class="modal-content">
                            <div class="modalContainer">
                                <dl>
                                    <dd>
                                        You have chosen to setup your game plan and challenges manually.  After you complete the basic project setup, you can proceed to your project dashboard to add challenges.
                                    </dd>
                                    <dd>
                                        Next, you will name and describe the project before skipping to step 4.
                                    </dd>
                                </dl>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonArea">
                                <a href="javascript:;" title="YES, I UNDERSTAND"
                                   class="closebutton newButtonOrange  button6"><span class="left"><span
                                        class="right">YES, I UNDERSTAND</span></span></a>
                            </div>
                        </div>
                        <!-- End .content -->
                    </div>
                </div>
            </div>
            <div class="barBot">
                <div class="barBotR">
                    <div class="barBotM"></div>
                </div>
            </div>
        </div>


        <div id="addUserModal" class="outLay minModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Add User To List</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <div class="addUserForm">
                                    <div class="addUserLeft">
                                        <!-- title -->
                                        <div class="addUserTitle">
                                            <p>Available Users</p>
                                            <a href="javascript:;" class="selectAll">Select All</a>
                                        </div>
                                        <!-- End .addUserTitle -->
                                        <!-- search -->
                                        <div class="searchBox">
                                            <input type="text" class="text" id="permissionSearchUserBox"/>
                                            <a href="javascript:;" class="searchUserHandle"><span class="profileLeft">Search</span></a>
                                        </div>
                                        <!-- End .searchBox -->
                                        <!-- list -->
                                        <div class="addUserList">
                                            <ul>

                                            </ul>
                                        </div>
                                        <!-- End .addUserList -->
                                    </div>
                                    <div class="addUserButton">
                                        <a href="javascript:;" class="addItem">Add</a>
                                        <a href="javascript:;" class="removeItem">Add</a>
                                    </div>
                                    <div class="addUserRight">
                                        <!-- title -->
                                        <div class="addUserTitle">
                                            <p>Chosen Users</p>
                                            <a href="javascript:;" class="removeAll">Remove All</a>
                                        </div>
                                        <!-- End .addUserTitle -->
                                        <!-- list -->
                                        <div class="addUserList">
                                            <ul>

                                            </ul>
                                        </div>
                                        <!-- End .addUserList -->
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span
                                            class="left"><span class="right">SAVE</span></span></a>
                                    <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span
                                            class="left"><span class="right">CANCEL</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="deleteUserModal" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Delete Users</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Are you sure you want to delete
                                    the selected users from the list?</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="YES" class="yesbutton newButtonGreen button6"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">NO</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="deleteConfirmModal" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Delete Users</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Selected users were deleted.</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="OK" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">OK</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="alertModal" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Alert</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>You have not added anyone to your project. You will be the only person with access. Is this ok?</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="YES" class="yesbutton newButtonGreen button6 step6Button"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">NO</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="createProjectConfirm" class="outLay smallModal">

            <div class="modalTop">
                <div class="modalBottom">
                    <div class="modalBg">
                        <div class="inner">
                            <!-- title -->
                            <div class="modal-title">
                                <h2>Confirmation</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Now we are going to create and setup your new project, do you want to proceed?</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="YES" class="yesbutton newButtonGreen button6 step6Button"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6"><span
                                            class="left"><span class="right">NO</span></span></a>
                                </div>
                            </div>
                            <!-- End .content -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

<div id="selectionConfirmationModal" class="outLay newModal">
    <div class="barTop">
        <div class="barTopR">
            <div class="barTopM"></div>
        </div>
    </div>
    <div class="barMid">
        <div class="barMidR">
            <div class="barMidM">
                <!-- title -->
                <div class="modal-title">
                    <h2>Copilot Selection Confirmation</h2>
                    <a href="javascript:;" class="closeModal">Close</a>
                </div>
                <!-- End .modal-title -->
                <!-- content -->
                <div class="modal-content">
                    <div class="modalContainer">
                        <dl>
                            <dt>You've chosen this copliot for your project.</dt>
                            <dd>
                                <span>username1</span>
                                <a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=MemberProfile&amp;cr=22706873">View Copilot
                                    Profile</a>
                            </dd>
                            <dd>
                                <span>iamcopilot</span>
                                <a href="https://<%=ServerConfiguration.SERVER_NAME%>/tc?module=MemberProfile&amp;cr=22706873">View Copilot
                                    Profile</a>
                            </dd>
                        </dl>
                    </div>
                    <div class="clear"></div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="CONFIRM"
                           class="closebutton  newButtonGreen   button6 confirmBtn"><span class="left"><span class="right">CONFIRM</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6"><span class="left"><span
                                class="right">CANCEL</span></span></a>
                    </div>
                </div>
                <!-- End .content -->
            </div>
        </div>
    </div>
    <div class="barBot">
        <div class="barBotR">
            <div class="barBotM"></div>
        </div>
    </div>
</div>

<div id="createPostingModal" class="outLay newModal">
    <div class="barTop">
        <div class="barTopR">
            <div class="barTopM"></div>
        </div>
    </div>
    <div class="barMid">
        <div class="barMidR">
            <div class="barMidM">
                <!-- title -->
                <div class="modal-title">
                    <h2>Create A Copilot Posting</h2>
                    <a href="javascript:;" class="closeModal">Close</a>
                </div>
                <!-- End .modal-title -->
                <!-- content -->
                <div class="modal-content">
                    <div class="modalContainer">
                        <dl>
                            <dd>
                                You've selected "<span>Create a copilot posting</span>" to select copilot,
                                We will need you to provide specific information about your project, to find out the best
                                copilot you are looking for.
                                This process will be done after this initial setup.
                            </dd>
                        </dl>
                    </div>
                    <div class="clear"></div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="OK" class="closebutton newButtonOrange  button6 okButton"><span
                                class="left"><span class="right">OK</span></span></a>
                    </div>
                </div>
                <!-- End .content -->
            </div>
        </div>
    </div>
    <div class="barBot">
        <div class="barBotR">
            <div class="barBotM"></div>
        </div>
    </div>
</div>

    <div id="infoModalMobile">
        <div class="modalTop">
            <div class="modalBottom">
                <div class="modal-content">
                    <div class="title">Your mobile project has been launched.</div>
                    <div class="msg">Our management team is reviewing your full specifications in order to assign the most qualified copilot to your project. You can expect to hear from your new copilot within 48 hours.</div>
                    <div class="button">
                        <a class="projectOverView" href="javascript:;">Project Overview</a>
                        <a class="dashboard" href='<s:url action="dashboardActive" namespace="/"/>'>Dashboard</a>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="infoModalPresentation">
        <div class="modalTop">
            <div class="modalBottom">
                <div class="modal-content">
                    <div class="title">Your presentation project has been launched.</div>
                    <div class="msg">Our management team is reviewing your full specifications in order to assign the most qualified copilot to your project. You can expect to hear from your new copilot within 48 hours.</div>
                    <div class="button">
                        <a class="projectOverView" href="javascript:;">Project Overview</a>
                        <a class="dashboard" href='<s:url action="dashboardActive" namespace="/"/>'>Dashboard</a>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</s:if>

        <s:if test='%{#request.CURRENT_TAB  == "createNewProject"}'>
            <div id="gamePlanBigModal">
                <!--<h2>Game Plan for Project Type 1 - Full Screen</h2> -->
                <div id="ganttChartBigDiv"></div>
                <div class="clear"></div>
                <div class="buttonArea">
                    <a href="javascript:;" title="CLOSE" class="button6"><span class="left"><span
                            class="right">Close</span></span></a>
                </div>
            </div>
        </s:if>

        <s:if test='%{#request.CURRENT_TAB == "milestone"}'>
                    <div id="editMilestoneModal" class="outLay newOutLay">
                            <div class="inner">
                                <div class="modalHeader">
                                    <div class="modalHeaderRight">
                                        <div class="modalHeaderCenter">
                                            Edit Project Milestone
                                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- end .modalHeader -->

                                <!-- content -->
                                <div class="modalBody">
                                    <ul>
                                        <li>
                                            <label>Name :</label>
                                            <div class="right">
                                                <input type="text" name="projectName" value="Milestone name lorem ipsum dolor sit amet" class="text limitText" />
                                                <p><span class="errorMessage"></span><span class="num">30</span> characters remaining</p>
                                            </div>
                                        </li>
                                        <li>
                                            <label>Description :</label>
                                            <div class="right">
                                                <textarea cols="10"  name="projectDesc" rows="5">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi commodo, ipsum sed pharetra gravida dolor sit amet, consect etuer adispiscing elit.</textarea>
                                                <p><span class="errorMessage"></span><span class="num">70</span> characters remaining</p>
                                            </div>
                                        </li>
                                        <li class="dateLine">
                                            <label>Due Date :</label>
                                            <div class="right">
                                                 <input type="text"  name="projectDuedate" readonly="readonly"  class="text"/>
                                                <span class="errorMessage" style="float:none"></span>
                                            </div>
                                        </li>
                                        <li>
                                            <label>Person Responsible :</label>
                                            <div class="right">
                                                <select  name="projectRes">
                                                    <option value="">Unassigned</option>
                                                    <option value="TonyJ" selected="selected">TonyJ</option>
                                                    <option value="hohosky">hohosky</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                    <p class="notify hide">
                                        <input type="checkbox" name="emailNotify" id="emailNotifyEdit"/>
                                        <label for="emailNotifyEdit">Notification email reminder now &amp; 48 hours before the milestone due.</label>
                                    </p>
                                    <div class="buttonArea">
                                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                                        <div class="clearFix"></div>
                                    </div>
                                </div>
                                <!-- End .content -->

                                <div class="modalFooter">
                                    <div class="modalFooterRight">
                                        <div class="modalFooterCenter"></div>
                                    </div>
                                </div>
                                <!-- end .modalFooter -->
                            </div>
                        </div>

                    <div id="removeMilestoneModal" class="outLay newOutLay">
                            <div class="inner">
                                <div class="modalHeader">
                                    <div class="modalHeaderRight">
                                        <div class="modalHeaderCenter">
                                            Remove Project Milestone
                                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- end .modalHeader -->

                                <!-- content -->
                                <div class="modalBody">
                                    <div class="confirmInfo">
                                        <p>
                                            Are you sure you want to delete the project milestone?<br/>
                                            You cannot undo after removal.
                                        </p>
                                    </div>
                                    <div class="buttonArea">
                                        <a href="javascript:;" class="button6 newButtonGreen saveButton"><span class="left"><span class="right">YES</span></span></a>
                                        <a href="javascript:;" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">NO</span></span></a>
                                        <div class="clearFix"></div>
                                    </div>
                                </div>
                                <!-- End .content -->

                                <div class="modalFooter">
                                    <div class="modalFooterRight">
                                        <div class="modalFooterCenter"></div>
                                    </div>
                                </div>
                                <!-- end .modalFooter -->
                            </div>
                        </div>
        </s:if>

        <div id="addMilestoneModal" class="outLay newOutLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Add A Project Milestone
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <ul>
                        <li>
                            <label>Name :</label>
                            <div class="right">
                                <input type="text"  name="projectName" class="text limitText" />
                                <p><span class="errorMessage"></span><span class="num">30</span> characters remaining</p>
                            </div>
                        </li>
                        <li>
                            <label>Description :</label>
                            <div class="right">
                                <textarea cols="10" rows="5" name="projectDesc"></textarea>
                                <p><span class="errorMessage"></span><span class="num">70</span> characters remaining</p>
                            </div>
                        </li>
                        <li class="dateLine">
                            <label>Due Date :</label>
                            <div class="right">
                                <input type="text" name="projectDuedate" value="mm/dd/yyyy" readonly="readonly"  class="text tip"/>
                                <span class="errorMessage" style="float:none"></span>
                            </div>
                        </li>
                        <li>
                            <label>Person Responsible :</label>
                            <div class="right">
                                <select name="projectRes">
                                    <option value=""  selected="selected">Unassigned</option>
                                    <option value="TonyJ">TonyJ</option>
                                    <option value="hohosky">hohosky</option>
                                </select>
                            </div>
                        </li>
                    </ul>
                    <p class="notify hide">
                        <input type="checkbox" name="emailNotify" id="emailNotifyAdd"/>
                        <label for="emailNotifyAdd">Notification email reminder now  &amp; 48 hours before the milestone due.</label>
                    </p>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 saveButton newButtonGreen"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton newButtonGreen"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>

        <div id="createForumConfirmModal" class="outLay newModal">
            <div class="barTop">
                <div class="barTopR"><div class="barTopM"></div></div>
            </div>
            <div class="barMid">
                <div class="barMidR"><div class="barMidM">
                    <!-- title -->
                    <div class="modal-title">
                        <h2>Insert Custom Forum Confirmation</h2>
                        <a href="javascript:;" class="closeModal">Close</a>
                    </div>
                    <!-- End .modal-title -->
                    <!-- content -->
                    <div class="modal-content">
                        <div class="modalContainer">
                            <dl>
                                <dd>
                                    You have successfully added a new topic to your project forum. It wil appear under the Forum list, So you can insert another Forum or you can delete an existing one, You Must have one Forum in the List.
                                </dd>
                            </dl>
                        </div>
                        <div class="clear"></div>
                        <div class="buttonArea">
                            <a href="javascript:;" title="OK" class="closebutton newButtonOrange  buttonBlue"><span class="left"><span class="right">OK</span></span></a>
                        </div>
                </div>
                <!-- End .content --> 
            </div></div>
        </div>
        <div class="barBot">
            <div class="barBotR"><div class="barBotM"></div></div>
        </div>
    </div>

        <div id="createForumModal" class="outLay minModal">
        <div class="modalTop"><div class="modalBottom"><div class="modalBg">
            <div class="inner">
                <!-- title -->
                <div class="modal-title">
                    <h2>Insert Custom Forum</h2>
                    <a href="javascript:;" class="closeModal">Close</a>
                </div>
                <!-- End .modal-title -->
                <!-- content -->
                <div class="modal-content">
                    <div class="addUserForm">
                        <div class="forumModelContent">
                            <!-- form -->
                            <div class="form">
                                <!-- row -->
                                <div class="row projectName">
                                    <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
                                </div>
                                <!-- row -->
                                <div class="row projectName">
                                    <a href="javascript:;" class="toolTip" id="toolTip1" rel='&lt;p&gt;&lt;b&gt;30 characters max.&lt;/b&gt;&lt;/p&gt;&lt;p&gt;Examples: "XYZ System Upgrade",&lt;/p&gt; &lt;p&gt;"ABC Refactoring", "XYZ Website" etc.&lt;/p&gt;'></a>
                                    <label>Forum Name <span class="red">*</span></label>
                                    <input type="text" class="text" maxlength="30" /> 
                                    <div class="clear"></div> 
                                    <p class="message"/>
                                </div>
                                <!-- End .row -->
                                <!-- row -->
                                <div class="row descProject">
                                    <a href="javascript:;" class="toolTip" id="toolTip2" rel="&lt;p&gt;&lt;b&gt;A short description about the project,&lt;/b&gt; such as:&lt;/p&gt;&lt;p&gt;Is this a new application or an upgrade of an existing application ?&lt;/p&gt; &lt;p&gt;What is the scope of your project ?&lt;/p&gt;&lt;p&gt;What application do you want to build ?&lt;/p&gt;&lt;p&gt;What is your main objective for building the application ?&lt;/p&gt;"></a>
                                    <label>Description<span class="red">*</span></label>
                                    <textarea rows="10" cols="10" ></textarea> 
                                    <div class="clear"></div> 
                                    <p class="message"/>
                                </div>
                                <!-- End .row -->  
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton1"><span class="left"><span class="right">INSERT</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                    </div>
                </div>
                <!-- End .content -->
            </div>
        </div></div></div>
    </div>

    <div id="viewForumModal" class="outLay">
        <div class="inner">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Configure Project Forum
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <!-- content -->
            <div class="modalBody">
                <div class="modal-content">
                    <table class="projectForum" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <th class="first">Forum Lists</th>
                            <th>Description</th>
                            <th class="last"></th>
                        </tr>
                        <tr class="applyForAll">
                            <td>
                                <a class="downloadProfile deleteSelect" href="javascript:;">
                                    <span class="profileLeft">Delete Selected</span>
                                </a>
                                <a class="downloadProfile addMoreForum" href="javascript:;">
                                    <span class="profileLeft">Add More Forums</span>
                                </a>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                    </table>
                    <table class="projectForum projectForumBody" border="0" cellpadding="0" cellspacing="0">
                    </table>
                </div>
                <div class="buttonArea">
                    <a href="javascript:;" title="SAVE" class="button6 saveButton1"><span class="left"><span class="right">OK</span></span></a>
                </div>
            </div>
            <!-- End .content -->

            <div class="modalFooter">
                <div class="modalFooterRight">
                    <div class="modalFooterCenter"></div>
                </div>
            </div>
            <!-- end .modalFooter -->
        </div>
    </div>
    
    <!-- #addMoreUsersModal -->
	<div id="addMoreUsersModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Add to Users List...
					<a href="javascript:;" class="closeOtherModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="modalBodyContent">
				<div class="left">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Available User</a>
	    				<a href="javascript:void(0)" class="rightTxt">Select All</a>
	    			</div>
	    			<div class="searchBox">
	    				<div class="searchTxt"><input type="text" class="searchText"/></div>
						<a class="button1" href="javascript:void(0)"><span>Search</span></a>
					</div>
	    			<div class="list">
	    			</div>
	    		</div>
	    		<div class="middle">
	    			<a class="addItem" href="javascript:void(0)"><img src="/images/add_tech.png" alt=""/></a>
	    			<a class="removeItem" href="javascript:void(0)"><img src="/images/remove_tech.png" alt=""/></a>
	    		</div>
	    		<div class="right">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Chosen User</a>
	    				<a href="javascript:void(0)" class="rightTxt">Remove All</a>
	    			</div>
	    			<div class="list">
	    			</div>
	    		</div>
				<div class="clearFix"></div>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 addMoreUserSave"><span class="btnR"><span class="btnC">SAVE</span></span></a>
    			<a href="javascript:;" class="newButton1 returnUserModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
	<!-- end #addMoreUsersModal -->

		<!-- #assignProjectModal -->
	<div id="assignProjectModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Assign Project(s)...
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="modalBodyContent">
				<div class="left">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Available Projects</a>
	    				<a href="javascript:void(0)" class="rightTxt">Select All</a>
	    			</div>
	    			<div class="searchBox">
	    				<div class="searchTxt"><input type="text" class="searchText"/></div>
						<a class="button1" href="javascript:void(0)"><span>Search</span></a>
					</div>
	    			<div class="list">
	    			</div>
	    		</div>
	    		<div class="middle">
	    			<a class="addItem" href="javascript:void(0)"><img src="/images/add_tech.png" alt=""/></a>
	    			<a class="removeItem" href="javascript:void(0)"><img src="/images/remove_tech.png" alt=""/></a>
	    		</div>
	    		<div class="right">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Chosen Projects</a>
	    				<a href="javascript:void(0)" class="rightTxt">Remove All</a>
	    			</div>
	    			<div class="list">
	    			</div>
	    		</div>
				<div class="clearFix"></div>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 assignProjectSave"><span class="btnR"><span class="btnC">SAVE</span></span></a>
    			<a href="javascript:;" class="newButton1 addMoreUserModal"><span class="btnR"><span class="btnC">ADD MORE USERS</span></span></a>
    			<a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
	<!-- end #assignProjectModal -->
    
    <!-- #userManageModal -->
	<div id="userManageModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					User Management
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="modalBodyContent">
				<div class="left">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Available Users</a>
	    				<a href="javascript:void(0)" class="rightTxt">Select All</a>
	    			</div>
	    			<div class="searchBox">
	    				<div class="searchTxt"><input type="text" class="searchText"/></div>
						<a class="button1" href="javascript:void(0)"><span>Search</span></a>
					</div>
	    			<div class="list">
	    			</div>
	    		</div>
	    		<div class="middle">
	    			<a class="addItem" href="javascript:void(0)"><img src="/images/add_tech.png" alt=""/></a>
	    			<a class="removeItem" href="javascript:void(0)"><img src="/images/remove_tech.png" alt=""/></a>
	    		</div>
	    		<div class="right">
	    			<div class="subtitle">
	    				<a href="javascript:void(0)" class="leftTxt">Chosen Users</a>
	    				<a href="javascript:void(0)" class="rightTxt">Remove All</a>
	    			</div>
	    			<div class="list">
	    			</div>
	    		</div>
				<div class="clearFix"></div>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 userManageSave"><span class="btnR"><span class="btnC">SAVE</span></span></a>
    			<a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
	<!-- end #userManageModal -->

    <s:if test='%{#request.CURRENT_TAB == "editProject"}'>
        <div id="clientManagersModal" class="userManagementModal outLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span>Manage Client Managers</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="addUserForm">
                        <div class="addUserLeft">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Available users</p>
                                <a href="javascript:;" class="selectAll">Select All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- search -->
                            <div class="searchBox">
                                <input type="text" class="text" value="" />
                                <a href="javascript:;" class="downloadProfile"><span class="profileLeft">Search</span></a>
                            </div>
                            <!-- End .searchBox -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="addUserButton">
                            <a href="javascript:;" class="addItem">Add</a>
                            <a href="javascript:;" class="removeItem">Add</a>
                        </div>
                        <div class="addUserRight">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Chosen users</p>
                                <a href="javascript:;" class="removeAll">Remove All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>

        <div id="projectManagersModal" class="userManagementModal outLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span>Manage TopCoder Platform Specialists</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="addUserForm">
                        <div class="addUserLeft">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Available users</p>
                                <a href="javascript:;" class="selectAll">Select All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- search -->
                            <div class="searchBox">
                                <input type="text" class="text" value="" />
                                <a href="javascript:;" class="downloadProfile"><span class="profileLeft">Search</span></a>
                            </div>
                            <!-- End .searchBox -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="addUserButton">
                            <a href="javascript:;" class="addItem">Add</a>
                            <a href="javascript:;" class="removeItem">Add</a>
                        </div>
                        <div class="addUserRight">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Chosen users</p>
                                <a href="javascript:;" class="removeAll">Remove All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>

        <div id="accountManagersModal" class="userManagementModal outLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span>Manage Topcoder Account Managers</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="addUserForm">
                        <div class="addUserLeft">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Available users</p>
                                <a href="javascript:;" class="selectAll">Select All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- search -->
                            <div class="searchBox">
                                <input type="text" class="text" value="" />
                                <a href="javascript:;" class="downloadProfile"><span class="profileLeft">Search</span></a>
                            </div>
                            <!-- End .searchBox -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="addUserButton">
                            <a href="javascript:;" class="addItem">Add</a>
                            <a href="javascript:;" class="removeItem">Add</a>
                        </div>
                        <div class="addUserRight">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Chosen users</p>
                                <a href="javascript:;" class="removeAll">Remove All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen  saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>


        <div id="saveProject" class="outLay" >
            <div class="inner">
                <div class="modalHeaderSmall">
                    <div class="modalHeaderSmallRight">
                        <div class="modalHeaderSmallCenter">
                        </div>
                    </div>
                </div>
                <div class="modalBody">
                    <h3>Your project details have been updated.</h3>
                    <img alt="Loading" src="/images/preloader-loading.gif" />
                    <p>We are returning you to project overview</p>
                </div>
                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!--end #saveProject-->

        <div id="leavePageModal" class="outLay" >
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Leaving This Page?
                        <a title="Close" class="closeModal" href="javascript:;">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="leaveNotes">
                    <div class="iconNotice"><img alt="notice" src="/images/modal-notice.png" /></div>
                    <h3>Your updates haven't been saved</h3>
                    <p>Would you like to save it?</p>
                </div>

                <div class="modalCommandBox">
                    <a class="button6 closeModal" href="javascript:;"><span class="left"><span class="right">YES</span></span></a>
                    <a class="button6 newButtonCancel closeModal" href="javascript:;"><span class="left"><span class="right">NO</span></span></a>
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
        <!-- end #leavePageModal -->

        <div id="removeCustomeMeta" class="outLay" >
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Delete Custom Project Metadata?
                        <a title="Close" class="closeModal" href="javascript:;">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="deleteNotes">
                    <div class="iconNotice"><img alt="notice" src="/images/modal-notice.png" /></div>
                    <h3>Are you sure you want to delete <strong>Key1(single Value)</strong>?</h3>
                    <p>Value(s) in this key will also be deleted.</p>
                </div>

                <div class="modalCommandBox">
                    <a class="button6 closeModal" href="javascript:;"><span class="left"><span class="right">YES</span></span></a>
                    <a class="button6 newButtonCancel closeModal" href="javascript:;"><span class="left"><span class="right">NO</span></span></a>
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
        <!-- end #removeCustomeMeta -->

        <div id="addCustomeMeta" class="outLay" >
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        Add Custom Project Metadata
                        <a title="Close" class="closeModal" href="javascript:;">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->

            <div class="modalBody">
                <div class="noticeContent">
                    <div class="modalRow">
                        <div class="fieldLabel">Project Metadata Key Name:</div>
                        <input id="lib60" type="text" value="" class="text validateLength" name="customMetadataKeyName"/>
                        <span class="hintTxt row1Hint"> <span id="customKeyNameError" class="customKeyError"></span> <span>36 characters remaining</span>  </span>
                        <div class="clearFix"></div>
                    </div>
                    <div class="modalRow">
                        <div class="fieldLabel">Project Metadata Key Description:</div>
                        <textarea cols="" rows="" class="textField validateLength" id="lib120" name="customMetadataKeyDescription"></textarea>
                        <span class="hintTxt row2Hint"> <span id="customKeyDescriptionError" class="customKeyError"></span> <span>120 characters remaining</span></span>
                        <div class="clearFix"></div>
                    </div>
                    <div class="modalRow radioRow">
                        <div class="fieldLabel">Used for Grouping Flag:</div>
                        <input name="isGrouping" type="radio" value="true" /> <label>Yes</label>
                        <input name="isGrouping" type="radio" value="false" checked="checked" /> <label>No</label>
                        <div class="clearFix"></div>
                    </div>
                    <div class="modalRow radioRow">
                        <div class="fieldLabel">Allowed Values:</div>
                        <input name="allowedValues" type="radio" value="true" checked="checked" /> <label>Single</label>
                        <input name="allowedValues" type="radio" value="false" /> <label>Multiple</label>
                        <div class="clearFix"></div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a class="button6 saveCustomKey" href="javascript:;"><span class="left"><span class="right">SAVE</span></span></a>
                    <a class="button6 newButtonCancel closeModal cancelButton" href="javascript:;"><span class="left"><span class="right">CANCEL</span></span></a>
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
        <!-- end #addCustomeMeta -->

        <!-- add user permission -->
        <div id="addUserModal" class="outLay userManagementModal">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span>Add Users To Permission List</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="addUserForm">
                        <div class="addUserLeft">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Users</p>
                                <a href="javascript:;" class="selectAll">Select All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- search -->
                            <div class="searchBox">
                                <input type="text" class="text" value="" />
                                <a href="javascript:;" class="downloadProfile"><span class="profileLeft">Search</span></a>
                            </div>
                            <!-- End .searchBox -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="addUserButton">
                            <a href="javascript:;" class="addItem">Add</a>
                        </div>
                        <div class="addUserRight">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Existing Users</p>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6  newButtonGreen  saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- end user permission -->

        <!-- add group permission -->
        <div id="addGroupModal" class="outLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span>Add Groups To Permissions List</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="addUserForm">
                        <div class="addUserLeft">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Available Groups</p>
                                <a href="javascript:;" class="selectAll">Select All</a>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- search -->
                            <div class="searchBox">
                                <input type="text" class="text" value=""/>
                                <a href="javascript:;" class="downloadProfile"><span class="profileLeft">Search</span></a>
                            </div>
                            <!-- End .searchBox -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>
                                    <c:forEach items="${viewData.availableSecurityGroups}" var="group">
                                        <li name="${group.id}"><c:out value="${group.name}"/></li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="addUserButton">
                            <a href="javascript:;" class="addItem">Add</a>
                            <a href="javascript:;" class="removeItem">Remove</a>
                        </div>
                        <div class="addUserRight">
                            <!-- title -->
                            <div class="addUserTitle">
                                <p>Groups to add</p>
                            </div>
                            <!-- End .addUserTitle -->
                            <!-- list -->
                            <div class="addUserList">
                                <ul>

                                </ul>
                            </div>
                            <!-- End .addUserList -->
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 newButtonGreen saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span
                                class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- end user permission -->

        <!-- start contest setting modal -->
        <div id="settingModal" class="outLay">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            <span><span class="userTitle">Username</span> - Challenge Notifications Setting</span>
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="settingTable">
                        <div class="tableHeader">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <colgroup>
                                    <col />
                                    <col width="150" />
                                    <col width="150" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>Challenge</th>
                                    <th><input type="checkbox" />
                                        <label>Timeline Notification</label></th>
                                    <th><input type="checkbox" />
                                        <label>Forum Notification</label></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td colspan="3"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="tableBody">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <colgroup>
                                    <col />
                                    <col width="150" />
                                    <col width="150" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="3"></th>
                                </tr>
                                </thead>
                                <tbody>


                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="SAVE" class="button6 closebutton newButtonGreen  saveSetting"><span class="left"><span class="right">SAVE</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelBtn"><span class="left"><span class="right">CANCEL</span></span></a>
                        <div class="clearFix"></div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- end contest setting modal -->
    </s:if>
    
    </div>

    <div id="modalBackgroundNew"></div>
    <div id="new-modal-new">
        <!--create forum-->
        <div id="createForumModalNew" class="outLayNew">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Insert Project Forum
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="modal-content">
                        <div class="addUserForm">
                            <div class="forumModelContent">
                                <!-- form -->
                                <div class="form">
                                    <!-- row -->
                                    <div class="row projectName">
                                        <span class="intro">All fields marked with <span class="red">*</span> are mandatory</span>
                                    </div>

                                    <!-- row -->
                                    <div class="row projectName">
                                        <a href="javascript:;" class="toolTip" id="toolTip1" rel='&lt;p&gt;&lt;b&gt;30 characters max.&lt;/b&gt;&lt;/p&gt;&lt;p&gt;Examples: "XYZ System Upgrade",&lt;/p&gt; &lt;p&gt;"ABC Refactoring", "XYZ Website" etc.&lt;/p&gt;'></a>
                                        <label>Forum Name <span class="red">*</span></label>
                                        <input type="text" class="text" maxlength="30" />
                                        <div class="clear"></div>
                                        <p class="errmessage">
                                        </p>
                                    </div>
                                    <!-- End .row -->

                                    <!-- row -->
                                    <div class="row descProject">

                                        <a href="javascript:;" class="toolTip" id="toolTip2" rel="&lt;p&gt;&lt;b&gt;A short description about the project,&lt;/b&gt; such as:&lt;/p&gt;&lt;p&gt;Is this a new application or an upgrade of an existing application ?&lt;/p&gt; &lt;p&gt;What is the scope of your project ?&lt;/p&gt;&lt;p&gt;What application do you want to build ?&lt;/p&gt;&lt;p&gt;What is your main objective for building the application ?&lt;/p&gt;"></a>
                                        <label>Description<span class="red">*</span></label>
                                        <textarea rows="10" cols="10" ></textarea>
                                        <div class="clear"></div>
                                        <p class="errmessage">
                                        </p>
                                    </div>
                                    <!-- End .row -->
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="buttonArea">
                            <a href="javascript:;" title="SAVE" class="button6  newButtonGreen saveButton1"><span class="left"><span class="right">INSERT</span></span></a>
                            <a href="javascript:;" title="CANCEL" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
                        </div>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- End modal -->

        <!--delete confirm forum-->
        <div id="deleteConfirmForumModal" class="outLayNew">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Delete Confirmation
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="modal-content">
                        <span class="deleteModal">Are you sure you want to delete the selected <br/>Custom Forum from the List?</span>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="YES" class="button6 newButtonGreen saveButton1"><span class="left"><span class="right">YES</span></span></a>
                        <a href="javascript:;" title="NO" class="closebutton newButtonOrange  button6 cancelButton"><span class="left"><span class="right">NO</span></span></a>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- End modal -->

        <!--delete confirm forum-->
        <div id="noDeleteForumModal" class="outLayNew">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Delete Confirmation
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <!-- End .modal-title -->
                    <!-- content -->
                    <div class="modal-content">
                        <span class="deleteModal">You have Successfully Deleted the Custom Forum <br/> from the List.</span>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="OK" class="buttonBlue cancelButton"><span class="left"><span class="right">OK</span></span></a>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- End modal -->

        <!--delete Error forum-->
        <div id="deleteErrorForumModal" class="outLayNew">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Error
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <div class="modal-content">
                        <span class="deleteModal">You should select a forum to delete.</span>
                   </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="OK" class="buttonBlue cancelButton"><span class="left"><span class="right">OK</span></span></a>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- End modal -->

        <!--delete confirm forum-->
        <div id="forumErrorModal" class="outLayNew">
            <div class="inner">
                <div class="modalHeader">
                    <div class="modalHeaderRight">
                        <div class="modalHeaderCenter">
                            Errors
                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                        </div>
                    </div>
                </div>
                <!-- end .modalHeader -->

                <!-- content -->
                <div class="modalBody">
                    <!-- End .modal-title -->
                    <!-- content -->
                    <div class="modal-content">
                        <span class="errorModal"></span>
                    </div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="OK" class="buttonBlue cancelButton"><span class="left"><span class="right">OK</span></span></a>
                    </div>
                </div>
                <!-- End .content -->

                <div class="modalFooter">
                    <div class="modalFooterRight">
                        <div class="modalFooterCenter"></div>
                    </div>
                </div>
                <!-- end .modalFooter -->
            </div>
        </div>
        <!-- End modal -->
    </div>

    <s:if test='%{#request.CURRENT_TAB == "milestone"}'>
        <div class="addMilestonePopup" id="addMilestonePopup1">
            <div class="popupMask">
                <div class="single">
                    <a href="javascript:;" class="grayButton">
                         <span class="buttonMask"><span class="text">Single Milestone</span></span>
                     </a>
                    <p>Create a new milestone for your project.</p>
                </div>
                <div class="multi">
                    <a href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/><s:param name="formData.viewType">multiple</s:param></s:url>" class="grayButton">
                         <span class="buttonMask"><span class="text">Multiple Milestones</span></span>
                     </a>
                    <p>Create multiple milestones for your project and save them all at one time.</p>
                </div>
            </div>
            <div class="arrow"></div>
        </div> <!-- End .addMilestonePopup -->

        <div class="addMilestonePopup" id="addMilestonePopup2">
            <div class="popupMask">
                <div class="single">
                    <a href="javascript:;" class="grayButton">
                         <span class="buttonMask"><span class="text">Single Milestone</span></span>
                     </a>
                    <p>Create a new milestone for your project.</p>
                </div>
                <div class="multi">
                    <a href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/><s:param name="formData.viewType">multiple</s:param></s:url>" class="grayButton">
                         <span class="buttonMask"><span class="text">Multiple Milestones</span></span>
                     </a>
                    <p>Create multiple milestones for your project and save them all at one time.</p>
                </div>
            </div>
            <div class="arrow"></div>
        </div>

        <div class="setDatePopup" id="setDatePopup">
            <h4>Set completion date</h4>
            <dl>
                <dt>When?</dt>
                <dd><input type="text" class="text" readonly="readonly" /></dd>
            </dl>
            <dl>
                <dt>Choose completion Date</dt>
                <dd>
                    <input type="radio" id="curDate" name="pickdate" checked="checked"/>
                    <label for="curDate"><b>Current Date</b></label>
                    <input type="radio" id="pickupDate" name="pickdate" />
                    <label for="pickupDate">Pick up a Date</label>
                    <p>The completion date marks when the project milestone is finished.</p>
                </dd>
            </dl>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 done"><span  class="btnR"><span class="btnC"  onclick="">DONE</span></span></a>
                <span class="verticalAlign">or</span>
                <a class="cancel verticalAlign" href="javascript:;">Cancel</a>
            </div>
        </div>

    </s:if>
    
    <s:if test='%{#request.CURRENT_TAB == "editProject"}'>
        <div class="setDatePopup" id="setDatePopup">
            <h4>Set completion date</h4>
            <dl>
                <dt>When?</dt>
                <dd><input type="text" class="text" readonly="readonly" /></dd>
            </dl>
            <dl>
                <dt>Choose completion Date</dt>
                <dd>
                    <input type="radio" id="curDate" name="pickdate" checked="checked"/>
                    <label for="curDate"><b>Current Date</b></label>
                    <input type="radio" id="pickupDate" name="pickdate" />
                    <label for="pickupDate">Pick up a Date</label>
                    <p>The completion date marks when your project is completed.</p>
                </dd>
            </dl>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 done"><span class="btnR"><span class="btnC" onclick="">DONE</span></span></a>
                <span>or</span>
                <a class="cancel" href="javascript:;">Cancel</a>
            </div>
        </div>

    </s:if>

    <span class="hide">
       <img src="/images/preloader-corner.png"/>
       <img src="/images/modal-header.png"/>
       <img src="/images/modal-body-normal.png"/>
       <img src="/images/preloader-body.png"/>
       <img src="/images/modal-warning.png"/>
       <img src="/images/modal-error.png"/>
       <img src="/images/modal-success.png"/>
       <img src="/images/modal-confirmation.png"/>
       <img src="/images/modal-arrow.png"/>
       <img src="/images/button-new-1.png"/>
    </span>

    <!-- Add Project Dialog-->
    <div id="addProjectDialog" title="Create New Project" class="dialog-box hide">
        <div id="addProjectForm">
            <div class="fi">
                <label for="projectName">Name:</label>
                <input id="projectName" name="projectName" type="text" width="45" maxlength="255"/>
            </div>
            <div class="fi">
                <label for="projectDescription">Description:</label>
                <textarea id="projectDescription" name="projectDescription" rows="6" cols="45"></textarea>
            </div>
            <div class="popupButtons">
                <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>CANCEL</span></a>
                <a href="javascript:;" onclick="addNewProject();" class="button1"><span>YES</span></a>
            </div>
        </div>
        <!-- End #addProjectForm -->

        <div id="addProjectResult">
            <p></p>

            <div class="popupButtons">
                <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Close</span></a>
            </div>
        </div>
        <!-- End #addProjectResult -->
    </div>
    <!-- End #addProjectDialog -->

    <div id="timeLineTip" class="tooltipContainer tooltipForum hide">
        <span class="arrow"></span>

        <div class="tooltipLeft">
            <div class="tooltipRight">
                <div class="tooltipBottom">
                    <div class="tooltipBottomLeft">
                        <div class="tooltipBottomRight">

                            <div class="tooltipCaption">
                                <div class="tooltipCaptionLeft">
                                    <div class="tooltipCaptionRight">
                                        <div class="tooltipCaptionInner">
                                            <h2 class="tooltipPhaseName"></h2>
                                            <a class="closeIco" onclick="$('#timeLineTip').css('display','none')"
                                               href="javascript:;"></a>
                                        </div>
                                        <!-- End .tooltipCaptionInner -->
                                    </div>
                                </div>
                            </div>
                            <!-- End .tooltipCaption -->

                            <div class="tooltipContent">
                                <p><span class="label">Start:</span> <span class="tooltipStartTime">04/12/2011 11:00</span></p>

                                <p><span class="label">End:</span> <span class="tooltipEndTime">04/12/2011 11:00</span></p>
                            </div>
                            <!-- End .tooltipContent -->

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End .popups -->
