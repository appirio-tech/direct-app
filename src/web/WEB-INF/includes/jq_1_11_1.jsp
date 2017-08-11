<%--
  - Version: 1.1
  - Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: Html fragment to import jquery 1.11.1 and setup other variable
  -
  - Version 1.1 (TOPCODER - IMPROVE USER MANAGEMENT BEHAVIOR FOR PROJECT PERMISSIONS & NOTIFICATIONS)
  - - Move some redundant code related to jquery 1.11 and magicsuggest to here
--%>
<script type="text/javascript" src="/scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="/scripts/magicsuggest.js"></script>
<script type="text/javascript">
    var jQuery_1_11_1 = $.noConflict(true);
    var member_api_url = "@memberSearchApiUrl@";
</script>