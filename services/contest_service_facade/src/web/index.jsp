<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the TopCoder Studio Service Facade web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the Studio Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>TopCoder Service Facade Demo</title>
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
<p>Contest Service Facade Demo
    (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                        ? "Cockpit Administrator" : "Cockpit User"%>))</p><br/><br/>
<span style="color:blue"><b>Contest Services Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ContestService.getAllContestTypes() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllContestTypes');"/></li><br/>
    <li>ContestService.getAllMediums() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllMediums');"/></li><br/>
    <li>ContestService.getStatusList() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getStatusList');"/></li><br/>
    <li>ContestService.getSubmissionFileTypes() &nbsp; &nbsp;
            <input type="button" value="Execute" onclick="callService('getSubmissionFileTypes');"/></li><br/>
    <li>ContestService.getAllContests() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllContests');"/></li><br/>
    <li>ContestService.getAllContestHeaders() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllContestHeaders');"/></li><br/>
    <li>ContestService.getContest(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getContest');"/></li><br/>
    <li>ContestService.getContestsForProject(long) &nbsp; &nbsp;<br/>
        Project ID: <input type="text" name="pid1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getContestsForProject');"/></li><br/>
    <li>ContestService.updateContestStatus(long, long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid2" value=""> &nbsp; &nbsp;<br/>
        Statud ID (1 - 17): <input type="text" name="sid1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('updateContestStatus');"/></li><br/>
    <li>ContestService.uploadDocumentForContest(UploadedDocument) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="dcid1" value=""> &nbsp; &nbsp;<br/>
        Document Description: <input type="text" name="ddesc1" value=""> &nbsp; &nbsp;<br/>
        Documen Type ID (1 - 6): <input type="text" name="dtid1" value=""> &nbsp; &nbsp;<br/>
        Document Content (text): <input type="text" name="dfile1" value=""> &nbsp; &nbsp;<br/>
        Document Filename: <input type="text" name="dfilename1" value=""> &nbsp; &nbsp;<br/>
        Document Mime Type ID (1 - 22): <input type="text" name="dmtid1" value=""> &nbsp; &nbsp;<br/>
        Document Path: <input type="text" name="dpath1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('uploadDocumentForContest');"/></li><br/>
    <li>ContestService.uploadDocument(UploadedDocument) &nbsp; &nbsp;<br/>
        Document Description: <input type="text" name="ddesc2" value=""> &nbsp; &nbsp;<br/>
        Documen Type ID (1 - 6): <input type="text" name="dtid2" value=""> &nbsp; &nbsp;<br/>
        Document Content (text): <input type="text" name="dfile2" value=""> &nbsp; &nbsp;<br/>
        Document Filename: <input type="text" name="dfilename2" value=""> &nbsp; &nbsp;<br/>
        Document Mime Type ID (1 - 22): <input type="text" name="dmtid2" value=""> &nbsp; &nbsp;<br/>
        Document Path: <input type="text" name="dpath2" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('uploadDocument');"/></li><br/>
    <li>ContestService.addDocumentToContest(long, long) &nbsp; &nbsp;<br/>
        Document ID: <input type="text" name="did1" value=""> &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid3" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('addDocumentToContest');"/></li><br/>
    <li>ContestService.removeDocumentFromContest(UploadedDocument) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid4" value=""> &nbsp; &nbsp;<br/>
        Document ID: <input type="text" name="did4" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('removeDocumentFromContest');"/></li><br/>
    <li>ContestService.removeDocument(long) &nbsp; &nbsp;<br/>
        Document ID: <input type="text" name="did5" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('removeDocument');"/></li><br/>
    <li>ContestService.retrieveSubmissionsForContest(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid5" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('retrieveSubmissionsForContest');"/></li><br/>
    <li>ContestService.retrieveAllSubmissionsByMember(long) &nbsp; &nbsp;<br/>
        User ID: <input type="text" name="uid1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('retrieveAllSubmissionsByMember');"/></li><br/>
    <li>ContestService.removeSubmission(long) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('removeSubmission');"/></li><br/>
    <li>ContestService.getContestsForClient(long) &nbsp; &nbsp;<br/>
        Client ID: <input type="text" name="uid2" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getContestsForClient');"/></li><br/>
    <li>ContestService.retrieveSubmission(long) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid2" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('retrieveSubmission');"/></li><br/>
    <li>ContestService.getMimeTypeId(String) &nbsp; &nbsp;<br/>
        Contest Type: <input type="text" name="ct1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getMimeTypeId');"/></li><br/>
    <li>ContestService.purchaseSubmission(long, String, String) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid3" value=""> &nbsp; &nbsp;<br/>
        PayPalOrder ID: <input type="text" name="ppo3" value=""> &nbsp; &nbsp;<br/>
        Security Token: <input type="text" name="st3" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('purchaseSubmission');"/></li><br/>
    <li>ContestService.createContestPayment(ContestPaymentData, String) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid6" value=""> &nbsp; &nbsp;<br/>
        Payment Status ID (1 - 3): <input type="text" name="psid6" value=""> &nbsp; &nbsp;<br/>
        PayPalOrder ID: <input type="text" name="ppo6" value=""> &nbsp; &nbsp;<br/>
        Price: <input type="text" name="price6" value=""> &nbsp; &nbsp;<br/>
        Security Token: <input type="text" name="st6" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('createContestPayment');"/></li><br/>
    <li>ContestService.getContestPayment(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid7" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getContestPayment');"/></li><br/>
    <li>ContestService.editContestPayment(ContestPaymentData) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid8" value=""> &nbsp; &nbsp;<br/>
        Payment Status ID (1 - 3): <input type="text" name="psid8" value=""> &nbsp; &nbsp;<br/>
        PayPalOrder ID: <input type="text" name="ppo8" value=""> &nbsp; &nbsp;<br/>
        Price: <input type="text" name="price8" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('editContestPayment');"/></li><br/>
    <li>ContestService.removeContestPayment(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid9" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('removeContestPayment');"/></li><br/>
    <li>ContestService.setSubmissionPlacement(long, long) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid4" value=""> &nbsp; &nbsp;<br/>
        Placement: <input type="text" name="place4" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('setSubmissionPlacement');"/></li><br/>
    <li>ContestService.setSubmissionPrize(long, long) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid5" value=""> &nbsp; &nbsp;<br/>
        Prize ID: <input type="text" name="prid5" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('setSubmissionPrize');"/></li><br/>
    <li>ContestService.markForPurchase(long) &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid6" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('markForPurchase');"/></li><br/>
    <li>ContestService.getChangeHistory(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid10" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getChangeHistory');"/></li><br/>
    <li>ContestService.getLatestChanges(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid11" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('getLatestChanges');"/></li><br/>
    <li>ContestService.deleteContest(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid12" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('deleteContest');"/></li><br/>
    <li>ContestService.processMissingPayments(long) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid13" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('processMissingPayments');"/></li><br/>
    <li>ContestService.addChangeHistory(ChangeHistoryData) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid14" value=""> &nbsp; &nbsp;<br/>
        Field Name: <input type="text" name="fn14" value=""> &nbsp; &nbsp;<br/>
        Old Value: <input type="text" name="ov14" value=""> &nbsp; &nbsp;<br/>
        New Value: <input type="text" name="nv14" value=""> &nbsp; &nbsp;<br/>
        Transaction ID: <input type="text" name="trid14" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('addChangeHistory');"/></li><br/>
    <li>ContestService.updateSubmission(SubmissionData) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid15" value=""> &nbsp; &nbsp;<br/>
        Submission ID: <input type="text" name="sbmid15" value=""> &nbsp; &nbsp;<br/>
        Submitter ID: <input type="text" name="uid15" value=""> &nbsp; &nbsp;<br/>
        Rank: <input type="text" name="rank15" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('updateSubmission');"/></li><br/>
    <li>ContestService.searchContests(ContestServiceFilter) &nbsp; &nbsp;<br/>
        Contest Name:  <input type="text" name="cname1" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('searchContests');"/></li><br/>
    <li>ContestService.createContest(Competition, long) &nbsp; &nbsp;<br/>
        Project ID: <input type="text" name="pid16" value=""> &nbsp; &nbsp;<br/>
        Eligibility: <input type="text" name="ce16" value=""> &nbsp; &nbsp;<br/>
        Summary: <input type="text" name="cs16" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('createContest');"/></li><br/>
    <li>ContestService.updateContest(Competition) &nbsp; &nbsp;<br/>
        Contest ID: <input type="text" name="cid17" value=""> &nbsp; &nbsp;<br/>
        Eligibility: <input type="text" name="ce17" value=""> &nbsp; &nbsp;<br/>
        Summary: <input type="text" name="cs17" value=""> &nbsp; &nbsp;<br/>
        <input type="button" value="Execute" onclick="callService('updateContest');"/></li><br/>
	<li>ContestService.getAllDocumentTypes() &nbsp; &nbsp;
        <input type="button" value="Execute" onclick="callService('getAllDocumentTypes');"/></li><br/>
</ul>
</form>
<br/>
<br/>
</body>
</html>
