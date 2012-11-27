<%--
  - Author: TCSASSEMBER
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file of delete group dialogs, it's statically included in the view user groups page
  - and view group details page.
--%>
<div id="modalBackground"></div>
<div id="new-modal">
	<!-- #deleteGroupModal -->
	<div id="deleteGroupModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">Are you sure you want to delete group &lt;Group Name&gt; ?</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" id="confirmDeleteGroupBtn" class="newButton1 triggerNoPreloaderModal" rel="#deleteGroupConfirmModal"><span class="btnR"><span class="btnC">YES</span></span></a>
    			<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">NO</span></span></a>
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
	<!-- end #deleteGroupModal -->
	
	<!-- #deleteGroupConfirmModal -->
	<div id="deleteGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="/images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">&lt;Group Name&gt; has been deleted.</div>
			
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
	<!-- end #deleteGroupConfirmModal -->
	
</div>
<!-- end modal -->
