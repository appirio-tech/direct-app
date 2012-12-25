<%--
  - Author: TCSASSEMBER
  - Version: 1.0 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - This jsp file of group processing buttons, it's statically included in the view user groups page.
--%>
<div class="leftSide">
    <a class="button7 createNewGroup" href="<s:url action='enterCreateGroup' namespace='/group'/>"><span class="left" style="width:120px !important;">CREATE NEW GROUP</span></a>
</div>
<div class="rightSide">
    <a class="button7 viewGroupButton" href="<s:url action='viewGroupAction' namespace='/group'/>"><span class="left">VIEW GROUP</span></a>
    <a class="button7 updateGroupButton" href="<s:url action='enterUpdateGroup' namespace='/group'/>"><span class="left">UPDATE GROUP</span></a>
    <a class="button7 grayButton7 triggerModal deleteGroupButton" href="javascript:;" rel="#deleteGroupModal"><span class="left">DELETE GROUP</span></a>
</div>
