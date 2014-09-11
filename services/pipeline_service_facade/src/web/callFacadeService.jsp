<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.pipeline.ContestPipelineService" %>
<%@ page import="com.topcoder.service.pipeline.entities.*" %>
<%@ page import="com.topcoder.service.pipeline.searchcriteria.*" %>
<%@ page import="com.topcoder.service.project.*" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.topcoder.clients.model.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String calledOperation = null;
    String callResult = null;
    Throwable error = null;
    try {
        // Determine the requested operation
        String operation = request.getParameter("operation");
        calledOperation = "operation";

        // Obtain a client stub for accessing the web service
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.pipeline.service.topcoder.com/",
                                      "ContestPipelineServiceBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        ContestPipelineService port = service.getPort(ContestPipelineService.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                         request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Call the appropriate wen service operation passing provided parameters
        if ("getContests_confidence".equals(operation)) {            
            int confidence = Integer.parseInt(request.getParameter("confidence"));
            ContestsSearchCriteria criteria = new ConfidenceSearchCriteria(confidence, false, true, false);
            List<Competition> results = port.getContests(criteria);
            callResult = "results:";
            for(Competition c: results) {
            	callResult += "id = " + c.getId() + ", confidence " + c.getConfidence() + ", review_payment=" + c.getReviewPayment()
            	+ ",SpecificationReviewPayment()=" + c.getSpecificationReviewPayment() + ", client_name = " + c.getClientName()
            	+ ",re_posted =" + c.getWasReposted() + ", contest_fee " + c.getContestFee() + ", dr-points = " + c.getDrPoints()
            	+ ", type = " + c.getType() + " , notes = " + c.getNotes() + ", client_approval = " + c.getClientApproval()
            	+ ", HasDependentCompetitions() = " + c.getHasDependentCompetitions() + ", pricing approval = " + c.getPricingApproval()
            	+ ", getHasWikiSpecification = " + c.getHasWikiSpecification();
            }
        } else if ("getContests_passSepcReview".equals(operation)) {
        	ContestsSearchCriteria criteria = new PassedSpecReviewSearchCriteria(true);
            List<Competition> results = port.getContests(criteria);
            callResult = "results:";
            for(Competition c: results) {
            	callResult += "id = " + c.getId() + ", confidence " + c.getConfidence() + ", review_payment=" + c.getReviewPayment()
            	+ ",SpecificationReviewPayment()=" + c.getSpecificationReviewPayment() + ", client_name = " + c.getClientName()
            	+ ",re_posted =" + c.getWasReposted() + ", contest_fee " + c.getContestFee() + ", dr-points = " + c.getDrPoints()
            	+ ", type = " + c.getType() + " , notes = " + c.getNotes() + ", client_approval = " + c.getClientApproval()
            	+ ", HasDependentCompetitions() = " + c.getHasDependentCompetitions() + ", pricing approval = " + c.getPricingApproval()
            	+ ", getHasWikiSpecification = " + c.getHasWikiSpecification();
            }
        } else if ("getContests_notpassSepcReview".equals(operation)) {
        	ContestsSearchCriteria criteria = new PassedSpecReviewSearchCriteria(false);
            List<Competition> results = port.getContests(criteria);
            callResult = "results:";
            for(Competition c: results) {
            	callResult += "id = " + c.getId() + ", confidence " + c.getConfidence() + ", review_payment=" + c.getReviewPayment()
            	+ ",SpecificationReviewPayment()=" + c.getSpecificationReviewPayment() + ", client_name = " + c.getClientName()
            	+ ",re_posted =" + c.getWasReposted() + ", contest_fee " + c.getContestFee() + ", dr-points = " + c.getDrPoints()
            	+ ", type = " + c.getType() + " , notes = " + c.getNotes() + ", client_approval = " + c.getClientApproval()
            	+ ", HasDependentCompetitions() = " + c.getHasDependentCompetitions() + ", pricing approval = " + c.getPricingApproval()
            	+ ", getHasWikiSpecification = " + c.getHasWikiSpecification();
            }
        } else if ("getContests_date".equals(operation)) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.set(Calendar.YEAR, 2009);
        	calendar.set(Calendar.MONTH, 4);
        	calendar.set(Calendar.DATE, 1);
        	Date startDate = calendar.getTime();
        	
        	Date endDate = new Date(calendar.getTimeInMillis() + 24*3600*1000); 
        	
        	DateSearchCriteria criteria = new DateSearchCriteria(startDate, endDate, true);
            List<Competition> results = port.getContests(criteria);
            callResult = "results:";
            for(Competition c: results) {
            	callResult += "id = " + c.getId() + ", confidence " + c.getConfidence() + ", review_payment=" + c.getReviewPayment()
            	+ ",SpecificationReviewPayment()=" + c.getSpecificationReviewPayment() + ", client_name = " + c.getClientName()
            	+ ",re_posted =" + c.getWasReposted() + ", contest_fee " + c.getContestFee() + ", dr-points = " + c.getDrPoints()
            	+ ", type = " + c.getType() + " , notes = " + c.getNotes() + ", client_approval = " + c.getClientApproval()
            	+ ", HasDependentCompetitions() = " + c.getHasDependentCompetitions() + ", pricing approval = " + c.getPricingApproval()
            	+ ", getHasWikiSpecification = " + c.getHasWikiSpecification();
            }
        } else if ("getContests_date_software".equals(operation)) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.set(Calendar.YEAR, 2009);
        	calendar.set(Calendar.MONTH, 6);
        	calendar.set(Calendar.DATE, 19);
        	Date startDate = calendar.getTime();
        	
        	Date endDate = new Date(calendar.getTimeInMillis() + 5*24*3600*1000); 
        	
        	DateSearchCriteria criteria = new DateSearchCriteria(startDate, endDate, true);
            List<Competition> results = port.getContests(criteria);
            callResult = "results:";
            for(Competition c: results) {
            	callResult += "id = " + c.getId() + ", confidence " + c.getConfidence() + ", review_payment=" + c.getReviewPayment()
            	+ ",SpecificationReviewPayment()=" + c.getSpecificationReviewPayment() + ", client_name = " + c.getClientName()
            	+ ",re_posted =" + c.getWasReposted() + ", contest_fee " + c.getContestFee() + ", dr-points = " + c.getDrPoints()
            	+ ", type = " + c.getType() + " , notes = " + c.getNotes() + ", client_approval = " + c.getClientApproval()
            	+ ", HasDependentCompetitions() = " + c.getHasDependentCompetitions() + ", pricing approval = " + c.getPricingApproval()
            	+ ", getHasWikiSpecification = " + c.getHasWikiSpecification();
            }
        }  if ("getContestDateChangeHistory_studio".equals(operation)) {
        	long contestId = Long.parseLong(request.getParameter("contestId1"));
        	List<CompetitionChangeHistory> results = port.getContestDateChangeHistory(contestId, CompetitionType.STUDIO);
            callResult = "results:";
            for(CompetitionChangeHistory c: results) {
            	callResult += c.toString();
            }
        } else if ("getContestDateChangeHistory_software".equals(operation)) {
        	long contestId = Long.parseLong(request.getParameter("contestId2"));
        	List<CompetitionChangeHistory> results = port.getContestDateChangeHistory(contestId, CompetitionType.SOFTWARE);
            callResult = "results:";
            for(CompetitionChangeHistory c: results) {
            	callResult += c.toString();
            }
        } else if ("getContestPrizeChangeHistory_studio".equals(operation)) {
        	long contestId = Long.parseLong(request.getParameter("contestId3"));
        	List<CompetitionChangeHistory> results = port.getContestPrizeChangeHistory(contestId, CompetitionType.STUDIO);
            callResult = "results:";
            for(CompetitionChangeHistory c: results) {
            	callResult += c.toString();
            }
        } else if ("getContestPrizeChangeHistory_software".equals(operation)) {
        	long contestId = Long.parseLong(request.getParameter("contestId4"));
        	List<CompetitionChangeHistory> results = port.getContestPrizeChangeHistory(contestId, CompetitionType.SOFTWARE);
            callResult = "results:";
            for(CompetitionChangeHistory c: results) {
            	callResult += c.toString();
            }
        }
    } catch (Throwable e) {
        error = e;
    }
    if (error != null) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        callResult = "ERROR!<br/>" + sw.getBuffer().toString().replaceAll("\\n", "<br/>");
    }
%>

<%@page import="com.topcoder.service.pipeline.CompetitionType"%>
<%@page import="java.util.Calendar"%><html>
  <head>
      <title>Project Service Facade Demo</title>
  </head>
  <body>
      <p>Called Project Service Facade operation: <%=calledOperation%></p>
      <p>Result of the call: <%=callResult%></p>
      <a href="index.jsp">Back to list of available operations</a>
  </body>
</html>
