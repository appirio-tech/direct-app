<%--
  - Author: GreatKevin
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups)
  - Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
  - - Reskin the group pages
  -
  - This jsp file of group processing buttons, it's statically included in the view user groups page.
--%>
<div class="leftSide">
    <a class="button7 newButtonGreen createNewGroup" href="<s:url action='enterCreateGroup' namespace='/group'/>"><span class="left" style="width:120px !important;">CREATE NEW GROUP</span></a>
</div>
<div class="rightSide">
    <a class="button7 newButtonBlue viewGroupButton" href="<s:url action='viewGroupAction' namespace='/group'/>"><span class="left">VIEW GROUP</span></a>
    <a class="button7 newButtonBlue updateGroupButton" href="<s:url action='enterUpdateGroup' namespace='/group'/>"><span class="left">EDIT GROUP</span></a>
    <a class="button7 newButtonOrange grayButton7 triggerModal deleteGroupButton" href="javascript:;" rel="#deleteGroupModal"><span class="left">DELETE GROUP</span></a>
</div>
