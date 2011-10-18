<%--
  - Author: winsty, GreatKevin
  - Version: 1.6
  - Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
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
  - Version 1.4 - Release Assembly - Release Assembly - TopCoder Cockpit Project Overview Update 1 Changes Note
  - - add the copilot management widget modal window
  -
  - Version 1.5 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R1
  - - add modal windows for the start a new project process   
  - 
  - Version 1.6 - Release Assembly - TopCoder Cockpit Start A New Project Revamp R2
  - - add modal windows for the start a new project process R2
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
    
    
    
    <div id="TB_window_custom" class="specrev-window">
        <h1>Plan Specification Review</h1>
        <a class="review-now" href="../contest/startSpecReview.action?startMode=now"></a>
        <p class="or">or</p>
        <a class="review-later" href="../contest/startSpecReview.action?startMode=later"></a>
        <p class="note">*48 hours prior to the scheduled contest start time.</p>
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
                                <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium
                                    doloremque
                                    laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et
                                    quasi
                                    architecto beatae vitae dicta sunt explicabo.</p>

                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
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
                                        You've selected custom game plan, your game plan will need to be created
                                        after the
                                        initial setup on the project page
                                    </dd>
                                    <dd>
                                        At this point, you'll skip Step 3 and go to Step 4
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
                                <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873">View Copilot
                                    Profile</a>
                            </dd>
                            <dd>
                                <span>iamcopilot</span>
                                <a href="http://www.topcoder.com/tc?module=MemberProfile&amp;cr=22706873">View Copilot
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
        <!-- end modals for create new project -->

    </div>

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
</div>
<!-- End .popups -->
