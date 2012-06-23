<%--
  - Author: winsty, GreatKevin, TCSASSEMBLER
  - Version: 2.2
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
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
              Are you sure to repost the failed contest?
          </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
          <a href="javascript:;" onclick="repostContest();" class="button1"><span>Yes</span></a>
        </div>
      </div><!-- End #repostForm -->

      <div id="repostResult">
        <p>
            Contest has been reposted successfully! <br/>
            Click edit button if you want to edit it.
        </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="refreshPage(this);" class="button1"><span>Close</span></a>
            <a href="javascript:;" onclick="editRepost();" class="button1"><span>Edit</span></a>
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
          <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
          <a href="javascript:;" onclick="newVersionConfirm();" class="button1"><span>Yes</span></a>
        </div>
      </div><!-- End #newVersionStep1 -->

      <div id="newVersionDev">
          <p>
               Do you want to create corresponding development contest?
          </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="newVersionDevYes();" class="button1"><span>Yes</span></a>
          <a href="javascript:;" onclick="newVersionDevNo();" class="button1"><span>No</span></a>
        </div>
      </div><!-- End #newVersionDev -->

      <div id="newVersionMinor">
          <p>
               Do you want to create a major version or minor version?
          </p>
        <div class="popupButtons">
            <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
          <a href="javascript:;" onclick="newVersionChooseMajor();" class="button1"><span>Major Version</span></a>
          <a href="javascript:;" onclick="newVersionChooseMinor();" class="button1"><span>Minor Version</span></a>
        </div>
      </div><!-- End #newVersionMinor -->

      <div id="newVersionResult">
        <p>
            New version has been created successfully! <br/>
            Click edit button if you want to edit it.
        </p>
        <div class="popupButtons">
          <a href="javascript:;" onclick="refreshPage(this);" class="button1"><span>Close</span></a>
            <a href="javascript:;" onclick="editNewVersionProject();" class="button1"><span>Edit</span></a>
        </div>
      </div><!-- End #repostResult -->
    </div><!-- End #repostDialog -->


    <div id="TB_overlay" class="TB_overlayBG" style="display:none"></div>

    <div id="TB_window_custom" class="specrev-window">
        <h1>Plan Specification Review</h1>
        <a class="review-now" href="../contest/startSpecReview.action?startMode=now"></a>
        <p class="or">or</p>
        <a class="review-later" href="../contest/startSpecReview.action?startMode=later"></a>
        <p class="note">*48 hours prior to the scheduled contest start time.</p>
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

                <!-- #addNewProjectModal -->
        <div id="addNewProjectModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        CREATE NEW PROJECT
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
                                                                                       onclick="addNewProject();">YES</span></span></a>
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
                    <div class="modalRow">
                        <label class="fLeft">Invoice Date:</label>
                        <input type="text" class="text fLeft date-pick" id="invoiceDate" readonly value="" />
                        <div class="clearFix"></div>
                    </div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 updateInvoice"><span class="btnR"><span class="btnC"
                                                                                       onclick="">Save</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal"><span class="btnR"><span
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
                    <a href="javascript:;" class="newButton1 updateInvoice"><span class="btnR"><span class="btnC"
                                                                                       onclick="">YES</span></span></a>
                    <a href="javascript:;" class="newButton1 newButtonCancel closeModal"><span class="btnR"><span
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
                        CHOOSE YOUR COPILOT
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
                            <a href="javascript:;" class="downloadProfile searchCopilot"><span
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
                <a href="javascript:;" title="SAVE" class="button6 saveButton"><span class="left"><span
                        class="right">SAVE</span></span></a>
                <a href="javascript:;" title="CANCEL" class="closebutton button6 cancelButton"><span
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
        
    <!-- #addFeedbackModal -->
    <div id="addFeedbackModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <span>SUBMISSION FEEDBACK</span>
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
                    <span>EDIT GENERAL FEEDBACK</span>
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
                    <span>SUCCESSFULLY SAVED</span>
                    <a href="javascript:" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>Milestone winners and Feedback saved.</p> 
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
                    <span>SUCCESSFULLY LOCKED IN</span>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p>Milestone winners and Feedback locked.</p> 
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
                    <span>LOCK IN MILESTONES</span>
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
                    <span>LOCK IN MILESTONES</span>
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
                    <span>LOCK IN MILESTONES</span>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="modalBodyContent">
                <p><strong>No Milestone Winners are selected.</strong>Are you sure you don't want to select any milestone winners for this contest?<span>You cannot undo or provide feedback after lock in.</span></p>
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
                <span>LOCK IN MILESTONES</span>
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->

    <div class="modalBody">
        <div class="modalBodyContent">
            <p><strong>Neither feedback on milestone winning submissions, nor genaral feedback is provided. </strong>Do you want to continue locking in milestones?<span>You cannot undo or provide feedback after lock in.</span></p>
        </div>
        <div class="modalCommandBox">
        <a href="javascript:;" class="newButton1 closeIt"><span class="btnR"><span class="btnC">GO BACK TO PROVIDE FEEDBACK</span></span></a>
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
    <!-- end #lockinNoFeedbackModal -->
    
        <s:if test='%{#request.CURRENT_TAB  == "overview" && #request.PAGE_TYPE  == "project"}'>
            <div id="projectDescModal" class="outLay">
                <div class="inner">
                    <div class="modalHeader">
                        <div class="modalHeaderRight">
                            <div class="modalHeaderCenter">
                                PROJECT DESCRIPTION
                                <a href="javascript:;" class="closeModal" title="Close">Close</a>
                            </div>
                        </div>
                    </div>
                    <!-- end .modalHeader -->

                    <!-- content -->
                    <div class="modalBody">
                        <div class="description">
                            <p>${viewData.projectGeneralInfo.project.description}</p>
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
                                <h2>ERRORS</h2>
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
                                    <a href="javascript:;" title="OK" class="closebutton button6"><span
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
                                <h2>ERRORS</h2>
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
                                    <a href="javascript:;" title="OK" class="closebutton button6"><span
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
                                <h2>ERRORS</h2>
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
                                    <a href="javascript:;" title="OK" class="closebutton button6"><span
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
                                            <td rowspan="6" class="firstTd">Contest</td>
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
                                       class="closebutton button6 closeButton"><span
                                            class="left"><span class="right">Close</span></span></a>
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
                                <a href="javascript:;" title="CLOSE" class="closebutton button6 closeButton"><span
                                        class="left"><span class="right">Close</span></span></a>
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
                            <h2>CUSTOM GAME PLAN</h2>
                            <a href="javascript:;" class="closeModal">Close</a>
                        </div>
                        <!-- End .modal-title -->
                        <!-- content -->
                        <div class="modal-content">
                            <div class="modalContainer">
                                <dl>
                                    <dd>
                                        You have chosen to setup your game plan and contests manually.  After you complete the basic project setup, you can proceed to your project dashboard to add contests.
                                    </dd>
                                    <dd>
                                        Next, you will name and describe the project before skipping to step 4.
                                    </dd>
                                </dl>
                            </div>
                            <div class="clear"></div>
                            <div class="buttonArea">
                                <a href="javascript:;" title="YES, I UNDERSTAND"
                                   class="closebutton button6"><span class="left"><span
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
                                <h2>ADD USER TO LIST</h2>
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
                                    <a href="javascript:;" title="SAVE" class="button6 saveButton"><span
                                            class="left"><span class="right">SAVE</span></span></a>
                                    <a href="javascript:;" title="CANCEL" class="closebutton button6 cancelButton"><span
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
                                <h2>DELETE USERS</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Are you sure you want to delete
                                    the selected users from the list?</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="YES" class="yesbutton button6"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton button6"><span
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
                                <h2>DELETE USERS</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Selected users were deleted.</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="OK" class="closebutton button6"><span
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
                                <h2>ALERT</h2>
                                <a href="javascript:;" class="closeModal">Close</a>
                            </div>
                            <!-- End .modal-title -->
                            <!-- content -->
                            <div class="modal-content">
                                <p>Are you sure to decide to not add authorized user(s) to your new project?</p>

                                <div class="clear"></div>
                                <div class="buttonArea">
                                    <a href="javascript:;" title="YES" class="step6Button button6"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton button6"><span
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
                                    <a href="javascript:;" title="YES" class="step6Button button6"><span
                                            class="left"><span class="right">YES</span></span></a>
                                    <a href="javascript:;" title="NO" class="closebutton button6"><span
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
                    <h2>COPILOT SELECTION CONFIRMATION</h2>
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
                                <a href="https://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873">View Copilot
                                    Profile</a>
                            </dd>
                            <dd>
                                <span>iamcopilot</span>
                                <a href="https://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873">View Copilot
                                    Profile</a>
                            </dd>
                        </dl>
                    </div>
                    <div class="clear"></div>
                    <div class="buttonArea">
                        <a href="javascript:;" title="CONFIRM"
                           class="closebutton button6 confirmBtn"><span class="left"><span class="right">CONFIRM</span></span></a>
                        <a href="javascript:;" title="CANCEL" class="closebutton button6"><span class="left"><span
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
                    <h2>CREATE A COPILOT POSTING</h2>
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
                        <a href="javascript:;" title="OK" class="closebutton button6 okButton"><span
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
                                            EDIT PROJECT MILESTONE
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
                                        <a href="javascript:;" title="SAVE" class="button6 saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                                        <a href="javascript:;" title="CANCEL" class="closebutton button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
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
                        <div id="addMilestoneModal" class="outLay newOutLay">
                            <div class="inner">
                                <div class="modalHeader">
                                    <div class="modalHeaderRight">
                                        <div class="modalHeaderCenter">
                                            ADD A PROJECT MILESTONE
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
                                        <a href="javascript:;" title="SAVE" class="button6 saveButton"><span class="left"><span class="right">SAVE</span></span></a>
                                        <a href="javascript:;" title="CANCEL" class="closebutton button6 cancelButton"><span class="left"><span class="right">CANCEL</span></span></a>
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
                                            REMOVE PROJECT MILESTONE
                                            <a href="javascript:;" class="closeModal" title="Close">Close</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- end .modalHeader -->

                                <!-- content -->
                                <div class="modalBody">
                                    <div class="confirmInfo">
                                        <p>
                                            Are you sure you want to delete the project milestone?
                                            <span>You cannot undo after removal.</span>
                                        </p>
                                    </div>
                                    <div class="buttonArea">
                                        <a href="javascript:;" class="button6 saveButton"><span class="left"><span class="right">YES</span></span></a>
                                        <a href="javascript:;" class="closebutton button6 cancelButton"><span class="left"><span class="right">NO</span></span></a>
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
                    <a href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/><s:param name="formData.viewType">multiple</s:param></s:url>" class="grayButton">
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
                    <a href="<s:url action="projectMilestoneView" namespace="/"><s:param name="formData.projectId" value="%{#session.currentSelectDirectProjectID}"/><s:param name="formData.viewType">multiple</s:param></s:url>" class="grayButton">
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
            <div class="buttonLine">
                <input type="button" class="done" value="Done" />
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
                <a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>Cancel</span></a>
                <a href="javascript:;" onclick="addNewProject();" class="button1"><span>Yes</span></a>
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
