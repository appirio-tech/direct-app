<%--
  - Author: TCSDEVELOPER
  - Version: 1.1
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: Contest Detail page
  -
  - Version 1.1 - Direct - Repost and New Version Assembly Change Note
  - - add repost and new version pop ups .
  -
--%>
<div class="popups"><!-- this area will contain the popups of this page -->
	  <!-- Help Popup -->
    <div id="helpPopup" class="hide">
        <div class="helpPopupInner details">
            
        </div><!-- End .helpPopupInner -->
    </div><!-- End #helpPopup -->
    
	  <!-- Message Dialog-->
		<div id="msgDialog" title="Message"  class="dialog-box hide">
        <p>        	
        </p>

				<div class="popupButtons">
					<a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>OK</span></a>
				</div>
    </div>		 

	  <!-- Error Dialog-->
		<div id="errorDialog" title="Errors"  class="dialog-box hide">
        <p>        	
        </p>

				<div class="popupButtons">
					<a href="javascript:;" onclick="closeDialog(this);" class="button1"><span>OK</span></a>
				</div>
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
</div><!-- End .popups -->
