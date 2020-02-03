<%--
  - Version: 1.2
  - Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: Html fragment to import jquery 1.11.1 and setup other variable
  -
  - Version 1.1 (TOPCODER - IMPROVE USER MANAGEMENT BEHAVIOR FOR PROJECT PERMISSIONS & NOTIFICATIONS)
  - - Move some redundant code related to jquery 1.11 and magicsuggest to here
  - Version 1.2 (TOPCODER - IMPROVE TASK ASSIGNEE FILTERING FOR CHALLENGES WITH GROUPS)
  - - Add group member search var
--%>
<script type="text/javascript" src="/scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/magicsuggest.js"></script>
<script type="text/javascript">
    var jwtCookieName = "@ApplicationServer.JWT_V3_COOKIE_KEY@";
    var jQuery_1_11_1 = $.noConflict(true);
    var member_api_url = "@memberSearchApiUrl@";
    var group_member_api_url = "@groupMemberSearchApiUrl@";
    var TRIAL_BILLING_ID = "@trialBillingId@";
    var DEFAULT_GROUP_ID_FOR_TRIAL = "@defaultGroupIdForTrial@";
</script>