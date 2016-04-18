<%--
  - Author: isv
  - Version: 1.1
  - Copyright (C) 2011 - 2016 TopCoder Inc., All Rights Reserved.
  -
  - Description: HTML fragment file containing various modal windows
  -
  - Version 1.1 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT) changes:
  - 1) Added "pushSubmissionsConfirmation" modal window
--%>

<div id="modal-background"></div>
<div id="new-modal-window">
    <div id="demoModal" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <h2>Errors</h2>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <ul class="modalContent">
                <li>No Challenge type is selected.</li>
            </ul>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 newButtonBlue defaultBtn"><span class="btnR"><span
                        class="btnC">OK</span></span></a>
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

    <div id="saveChallengeConfirmation" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <h2>Errors</h2>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <ul class="modalContent">
                <li></li>
            </ul>
            <div class="modalCommandBox">
                <span class="checkbox">
                    <input type="checkbox"/>
                    Don't show this message again
                </span>
                <a href="javascript:;" class="newButton1 newButtonBlue defaultBtn"><span class="btnR"><span
                        class="btnC">Save As Draft</span></span></a>
                <a href="javascript:;" class="newButton1 newButtonOrange noBtn"><span class="btnR"><span
                        class="btnC">Cancel</span></span></a>
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
    <!-- end #demoModal -->
    <div id="pushSubmissionsConfirmation" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <h2>Errors</h2>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <ul class="modalContent">
                <li></li>
            </ul>
            <div class="modalCommandBox">
                <a href="javascript:;" class="newButton1 newButtonBlue defaultBtn"><span class="btnR"><span
                        class="btnC">Yes</span></span></a>
                <a href="javascript:;" class="newButton1 newButtonOrange noBtn"><span class="btnR"><span
                        class="btnC">No</span></span></a>
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
    <!-- end #demoModal -->
</div>
