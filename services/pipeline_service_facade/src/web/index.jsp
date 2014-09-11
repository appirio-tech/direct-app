<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is the main page for the demonstration of the TopCoder Project Service Facade web service.
  - This page is the default page to open.
  - The page lists the operations provided by provides the Project Service Facade web service and provides the controls
    for setting the parameters and calling the desired web service operation.
--%>
<html>
<head>
    <title>Project Service Facade Demo</title>
    <script type="text/javascript">
        function callService(type) {
            var form = document.Form;
            form.operation.value = type;
            form.submit();
        }
    </script>
</head>

<body>
<p>Project Service Facade Demo
    (User: <%=request.getUserPrincipal().getName()%> (<%=request.isUserInRole("Cockpit Administrator")
                                                        ? "Cockpit Administrator" : "Cockpit User"%>))</p><br/><br/>
<span style="color:blue"><b>Pipeline service Demo</b></span><br/>
<form action="callFacadeService.jsp" method="POST" name="Form" id="Form">
    <input type="hidden" name="operation" value="">
<ul>
    <li>ContestPipelineService.getContests(ContestsSearchCriteria criteria) <br/>&nbsp; &nbsp;
        ConfidenceSearchCriteria#confidence(equal): <input type="text" name="confidence" value=""> &nbsp; &nbsp;        
        <input type="button" value="Execute" onclick="callService('getContests_confidence');"/></li><br/>
    
    <li>ContestPipelineService.getContests(ContestsSearchCriteria criteria) <br/>&nbsp; &nbsp;
        PassedSpecReviewSearchCriteria#(pass)
        <input type="button" value="Execute" onclick="callService('getContests_passSepcReview');"/></li><br/>
    
    <li>ContestPipelineService.getContests(ContestsSearchCriteria criteria) <br/>&nbsp; &nbsp;
        PassedSpecReviewSearchCriteria#(not pass)
        <input type="button" value="Execute" onclick="callService('getContests_notpassSepcReview');"/></li><br/>
    
    <li>ContestPipelineService.getContests(ContestsSearchCriteria criteria) <br/>&nbsp; &nbsp;
        DateSearchCriteria#(startDate=2007-01-09 12:00:00.000, endDate = 2007-02-14 14:00:00.000) Studio
        <input type="button" value="Execute" onclick="callService('getContests_date');"/></li><br/>  
    
      <li>ContestPipelineService.getContests(ContestsSearchCriteria criteria) <br/>&nbsp; &nbsp;
        DateSearchCriteria#(startDate=2009-07-18 00:00:00.000, endDate = 2009-07-26 14:00:00.000) SOFTWARE
        <input type="button" value="Execute" onclick="callService('getContests_date_software');"/></li><br/>  
            
      <li>getContestDateChangeHistory(long contestId, CompetitionType competitionType) &nbsp; &nbsp;
    	contest Id:<input type="text" name="contestId1" value=""> &nbsp; &nbsp;
    	CompetitionType = STUDIO
        <input type="button" value="Execute" onclick="callService('getContestDateChangeHistory_studio');"/></li><br/>
        
      <li>getContestDateChangeHistory(long contestId, CompetitionType competitionType) &nbsp; &nbsp;
    	contest Id:<input type="text" name="contestId2" value=""> &nbsp; &nbsp;
    	CompetitionType = SOFTWARE
        <input type="button" value="Execute" onclick="callService('getContestDateChangeHistory_software');"/></li><br/> 
        
       <li>getContestPrizeChangeHistory(long contestId, CompetitionType competitionType) &nbsp; &nbsp;
    	contest Id:<input type="text" name="contestId3" value=""> &nbsp; &nbsp;
    	CompetitionType = STUDIO
        <input type="button" value="Execute" onclick="callService('getContestPrizeChangeHistory_studio');"/></li><br/>
        
      <li>getContestPrizeChangeHistory(long contestId, CompetitionType competitionType) &nbsp; &nbsp;
    	contest Id:<input type="text" name="contestId4" value=""> &nbsp; &nbsp;
    	CompetitionType = SOFTWARE
        <input type="button" value="Execute" onclick="callService('getContestPrizeChangeHistory_software');"/></li><br/>   
                              
</ul>
</form>
<br/>
<br/>
</body>
</html>
