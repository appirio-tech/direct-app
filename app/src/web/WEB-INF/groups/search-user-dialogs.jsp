<%--
  - Author: GreatKevin
  - Version: 1.2
  -
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Changes:
  - Version 1.1 (Release Assembly - TopCoder Security Groups Release 3) changes:
  -   updated to fixed the bugs in this assembly.
  -
  - Version 1.2 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
  - - Reskin the group pages
  -
  - This jsp file of search user dialogs, it's statically included in other group security pages.
--%>

<!-- #searchModal -->
<div id="searchModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                Search User Handle
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->
    
    <div class="modalBody">
        <div class="searchInputDiv">
            <label>Handle:</label>
            <input type="text" class="text" value="Name" id="handle" />
            <div class="handleInputError">Please input the handle</div>
        </div>
        
        <div class="modalCommandBox">
            <a href="javascript:;" id="searchUser" class="newButtonGreen searchButton" rel="#searchListModal"><span class="btnR"><span class="btnC">SEARCH</span></span></a>
            <a href="javascript:;" class="newButtonOrange newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
<!-- end #searchModal -->

<!-- #searchListModal -->
<div id="searchListModal" class="outLay">
    <div class="modalHeader">
        <div class="modalHeaderRight">
            <div class="modalHeaderCenter">
                Search User Handle
                <a href="javascript:;" class="closeModal" title="Close">Close</a>
            </div>
        </div>
    </div>
    <!-- end .modalHeader -->
    
    <div class="modalBody">
        <div class="searchListDiv">
            <p>10 User Handles Found:</p>
            <table border="0" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="30" />
                    <col width="316" />
                </colgroup>
                <tbody>
                    
                </tbody>
            </table>
            <div class="handleSelectError">Please select a handle</div>
        </div>
        
        <div class="modalCommandBox">
            <a href="javascript:;" class="newButton1 newButtonGreen searchButton" id="addUser"><span class="btnR"><span class="btnC">
            <c:choose>
                <c:when test="${request.CURRENT_TAB=='createAdministrator'}">SELECT USER</c:when>
                <c:when test="${request.CURRENT_TAB=='approvals'}">ADD TO SEARCH</c:when>
                <c:otherwise>ADD USER TO GROUP</c:otherwise>
            </c:choose>
            </span></span></a>
            <a href="javascript:;" class="newButtonGreen searchButton triggerNoPreloaderModal" rel="#searchModal"><span class="btnR"><span class="btnC">SEARCH AGAIN</span></span></a>
            <a href="javascript:;" class="newButtonOrange newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
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
<!-- end #searchListModal -->
<!-- ajax sending invitation mail modal -->
<div class="outLay" id="sendInvitationModal" style="display: none;">
    <div class="modalHeaderSmall">
        <div class="modalHeaderSmallRight">
            <div class="modalHeaderSmallCenter"></div>
        </div>
    </div>
    <div class="modalBody">
        <div class="preloaderTips">Sending invitation email(s)... please wait, this may take a while.</div>
    </div>
    <div class="modalFooter">
        <div class="modalFooterRight">
            <div class="modalFooterCenter">
                <div class="&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;"></div>
            </div>
        </div>
    </div>
</div>
<!-- end ajax sending invitation mail modal -->
