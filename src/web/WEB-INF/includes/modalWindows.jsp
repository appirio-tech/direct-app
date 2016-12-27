<%--
  - Author: isv, TCSCODER
  - Version: 1.3
  - Copyright (C) 2011 - 2016 TopCoder Inc., All Rights Reserved.
  -
  - Description: HTML fragment file containing various modal windows
  -
  - Version 1.1 (TOPCODER DIRECT - ASP INTEGRATION WORK MANAGEMENT IMPROVEMENT) changes:
  - 1) Added "pushSubmissionsConfirmation" modal window
  -
  - Version 1.2 (TopCoder Direct - Remove ASP Integration Related Logic) changes:
  - - Remove "pushSubmissionsConfirmation" modal window.
  - Version 1.3 (TOPCODER DIRECT - CLOSE PRIVATE CHALLENGE IMMEDIATELY) changes:
  - - Add modal for closing and canceling private challenge
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
    <div id="yesNoConfirmation" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <h2>Confirm</h2>
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

                <a href="javascript:;" class="newButton1 newButtonBlue defaultBtn"><span class="btnR"><span
                        class="btnC">OK</span></span></a>
                <a href="javascript:;" class="newButton1 newButtonOrange noBtn"><span class="btnR"><span
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
    <div id="selectWinnerConfirmation" class="outLay">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    <h2>Select Winner</h2>
                    <a href="javascript:;" class="closeModal" title="Close">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <ul class="modalContent">
                <li><div id="winnerDiv" class="editDropDown">
                    <span class="name fixWidthName"><strong>Winner</strong></span>

                    <div class="registrantsSelect" style="float:left">
                        <select id="registrants" name="registrants" class="bigin">
                            <option value="0">Please select the winner</option>
                        </select>
                    </div>
                    <div class="clearFix"></div>
                </div>
                </li>
            </ul>
            <div class="modalCommandBox">
                <span class="checkbox">

                <a href="javascript:;" class="newButton1 newButtonBlue defaultBtn"><span class="btnR"><span
                        class="btnC">OK</span></span></a>
                <a href="javascript:;" class="newButton1 newButtonOrange noBtn"><span class="btnR"><span
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
</div>
