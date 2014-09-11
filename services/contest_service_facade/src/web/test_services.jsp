<%--
  - Author: pulky
  - Date: 21 Oct 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is a simple page to test contest facade services
--%>
<html>
<head>
    <title>Contest Eligibility test page</title>
    <script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
    <style type="text/css">
        body {
            font-size: 14px;
            font-family: Arial Verdana sans-serif;
        }
    </style>
</head>

<body>
<span style="color:blue"><b>Contest Eligibility tests</b></span><br/>
<form action="call_service.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>booleanddd isEligible(long userId, long contestId, boolean isStudio) &nbsp; &nbsp;<br/>
        User id: <input type="text" name="user_id" value=""> &nbsp; &nbsp;<br/>
        Contest id (1 - 6): <input type="text" name="contest_id" value=""> &nbsp; &nbsp;<br/>
        is studio: <input type="text" name="is_studio" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('isEligible');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
