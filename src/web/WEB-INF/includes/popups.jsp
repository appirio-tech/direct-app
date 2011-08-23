<%--
  - Author: winsty, GreatKevin
  - Version: 1.3
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - add repost and new version pop ups .
  -
  - Version 1.2 - TC Direct UI Improvement Assembly 1 Change Note
  - - add delete user confirmation
  -
  - Version 1.3 - Release Assembly - TopCoder Cockpit AJAX Revamp Change Note
  - - move all the popup windows into popups jsp
--%>
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
    
    <div id="TB_window">
        <div id="placeHolder">PlaceHoldertest</div>
    </div>
    
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
        <div class="outLay" id="preloaderModal" style="display: none;">
            <div class="modalHeaderSmall">
                <div class="modalHeaderSmallRight">
                    <div class="modalHeaderSmallCenter"></div>
                </div>
            </div>
            <div class="modalBody">
                <img alt="Loading" src="/images/preloader-loading.gif">

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
    </div>

    <span class="hide">
       <img src="/images/preloader-corner.png" />
       <img src="/images/modal-header.png" />
       <img src="/images/modal-body-normal.png" />
       <img src="/images/preloader-body.png" />
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
