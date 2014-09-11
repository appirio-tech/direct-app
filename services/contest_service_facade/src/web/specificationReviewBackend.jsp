<%--
  - Author: pulky
  - Version: 1.0 (Cockpit Spec Review Backend Service Update v1.0)
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of all specification review related operations in the
  - Contest Service Facade web service.
--%>
<html>
    <head>
        <title>TopCoder Service Facade Demo - Specification Review Backend</title>
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
        <p>Specification Review Backend Demo
            (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                                ? "Cockpit Administrator" : "Cockpit User"%>))</p>
        <br/><br/>
        <span style="color:blue"><b>Contest Services Demo</b></span><br/>
        <form action="callSpecificationReviewBackendServices.jsp" method="POST" name="Form" id="Form">
            <input type="hidden" name="operation" value="">
            <ul>
                <li>ContestService.createSpecReview() &nbsp; &nbsp;<br/>
                    Project ID: <input type="text" name="pid1" value=""> &nbsp; &nbsp;<br/>
                    <input type="button" value="Execute" onclick="callService('createSpecReview');"/>
                </li><br/>
                <li>ContestService.getScorecardAndReview() &nbsp; &nbsp;<br/>
                    Project ID: <input type="text" name="pid2" value=""> &nbsp; &nbsp;<br/>
                    <input type="button" value="Execute" onclick="callService('getScorecardAndReview');"/>
                </li><br/>
                <li>ContestService.markSoftwareContestReadyForReview() &nbsp; &nbsp;<br/>
                    Project ID: <input type="text" name="pid3" value=""> &nbsp; &nbsp;<br/>
                    <input type="button" value="Execute" onclick="callService('markSoftwareContestReadyForReview');"/>
                </li><br/>
                <li>ContestService.addReviewComment() &nbsp; &nbsp;<br/>
                    Review ID: <input type="text" name="rid" value=""> &nbsp; &nbsp;<br/>
                    Comment Type ID: <input type="text" name="ctid" value=""> &nbsp; &nbsp;<br/>
                    Comment: <input type="text" name="comment" value=""> &nbsp; &nbsp;<br/>
                    Author: <input type="text" name="author" value=""> &nbsp; &nbsp;<br/>
                    <input type="button" value="Execute" onclick="callService('addReviewComment');"/>
                </li><br/>
            </ul>
        </form>
    </body>
</html>
