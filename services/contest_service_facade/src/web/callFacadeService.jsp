<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.1
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from index.jsp for invoking the selected web service operation
  - with provided parameters and displaying the results of the call. In fact, this page acts like a web service client
  - demonstrating the code which could be used for calling the web service.
  -
  - V1.1: Update in Cockpit Security Facade V1.0 change to new WebService facade.
--%>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFacadeWebService" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.topcoder.service.project.Competition" %>
<%@ page import="com.topcoder.service.studio.ContestTypeData" %>
<%@ page import="com.topcoder.service.studio.MediumData" %>
<%@ page import="com.topcoder.service.studio.ContestStatusData" %>
<%@ page import="com.topcoder.service.project.StudioCompetition" %>
<%@ page import="com.topcoder.service.studio.UploadedDocument" %>
<%@ page import="com.topcoder.service.studio.SubmissionData" %>
<%@ page import="com.topcoder.service.studio.ContestPaymentData" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.topcoder.service.studio.ChangeHistoryData" %>
<%@ page import="javax.xml.datatype.DatatypeFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFilter" %>
<%@ page import="com.topcoder.search.builder.filter.LikeFilter" %>
<%@ page import="com.topcoder.service.project.CompetionType" %>
<%@ page import="com.topcoder.security.auth.module.UserProfilePrincipal" %>
<%@ page import="com.topcoder.service.project.CompetitionPrize" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.topcoder.service.studio.ContestData" %>
<%@ page import="com.topcoder.service.studio.contest.DocumentType" %>
<%@ page import="javax.print.attribute.standard.Media" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;

    System.out.println("UserPrincipal: " + request.getUserPrincipal().getName());
    try {
        String operation = request.getParameter("operation");
        calledOperation = operation;
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.contest.facade.service.topcoder.com/",
                                      "ContestServiceFacadeWebServiceBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        ContestServiceFacadeWebService port = service.getPort(ContestServiceFacadeWebService.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                         request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");
        if ("getAllContestTypes".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<ContestTypeData> types = port.getAllContestTypes();
            for (ContestTypeData t : types) {
                b.append("    ContestType: ID = ").append(t.getContestTypeId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
        } else if ("getAllMediums".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<MediumData> types = port.getAllMediums();
            for (MediumData t : types) {
                b.append("    MediumData: ID = ").append(t.getMediumId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved mediums:<br/>" + b.toString();
        } else if ("getStatusList".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<ContestStatusData> types = port.getStatusList();
            for (ContestStatusData t : types) {
                b.append("    ContestStatusData: ID = ").append(t.getStatusId()).append(", name = ").append(t.getName()).
                        append(", desc = ").append(t.getDescription()).append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
        } else if ("getSubmissionFileTypes".equals(operation)) {
            String types = port.getSubmissionFileTypes();
            callResult = "Retrieved submission file types:<br/>" + types;
        } else if ("getAllContestHeaders".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getAllContestHeaders();
            for (StudioCompetition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contest headers:<br/>" + b.toString();
        } else if ("getAllContests".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getAllContests();
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contests:<br/>" + b.toString();
        } else if ("createContest".equals(operation)) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date(System.currentTimeMillis() + 10 * 24 * 3600 * 1000L));
            GregorianCalendar calendar2 = new GregorianCalendar();
            calendar2.setTime(new Date(System.currentTimeMillis() + 20 * 24 * 3600 * 1000L));
            ContestData data = new ContestData();
            StudioCompetition newContest = new StudioCompetition(data);
            //newContest.setAdminFee(100);
            newContest.setCompetitionId(-1L);
            newContest.setCreatorUserId(((UserProfilePrincipal) request.getUserPrincipal()).getUserId());
            newContest.setDrPoints(50);
            newContest.setEligibility(request.getParameter("ce16"));
            newContest.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            newContest.setId(-1);
            newContest.setPrizes(new ArrayList<CompetitionPrize>());
            newContest.setProject(null);
            newContest.setShortSummary(request.getParameter("cs16"));
            newContest.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            newContest.setType(CompetionType.STUDIO);
            data.setContestChannelId(1);
            data.setContestDescriptionAndRequirements("Desc");
            data.setContestId(-1);
            data.setContestTypeId(1);
            data.setCreatorUserId(((UserProfilePrincipal) request.getUserPrincipal()).getUserId());
            data.setDocumentationUploads(new ArrayList<UploadedDocument>());
            data.setDurationInHours(96);
            data.setFinalFileFormat("doc");
            data.setForumId(-1);
            data.setForumPostCount(0);
            data.setLaunchDateAndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            data.setMaximumSubmissions(10);
            data.setMedia(new ArrayList<MediumData>());
            data.setName("New Contest");
            data.setNotesOnWinnerSelection("Notes on winner");
            data.setNumberOfRegistrants(100);
            data.setOtherFileFormats("Other");
            data.setOtherRequirementsOrRestrictions("Other reqs");
            data.setPrizeDescription("Pirze desc");
            data.setProjectId(Long.parseLong(request.getParameter("pid16")));
            data.setRequiredOrRestrictedColors("colros");
            data.setRequiredOrRestrictedFonts("Fonts");
            data.setSizeRequirements("Size");
            data.setStatusId(15);
            data.setSubmissionCount(0);
            data.setTcDirectProjectId(Long.parseLong(request.getParameter("pid16")));
            data.setWinnerAnnoucementDeadline(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));

            newContest = port.createContest(newContest, Long.parseLong(request.getParameter("pid16")));
            callResult = "Created new contest: ID = " + newContest.getId();
        } else if ("updateContest".equals(operation)) {
            String cid = request.getParameter("cid17");
            StudioCompetition contest = port.getContest(Long.parseLong(cid));
            contest.setEligibility(request.getParameter("ce17"));
            contest.setShortSummary(request.getParameter("cs17"));
            port.updateContest(contest);
            callResult = "Updated contest succesfully";
        } else if ("getContest".equals(operation)) {
            String cid = request.getParameter("cid1");
            StudioCompetition contest = port.getContest(Long.parseLong(cid));
            callResult = "Retrieved contest: ID = " + contest.getId() + ", start = " + contest.getStartTime()
                         + ", end = " + contest.getEndTime();
        } else if ("getContestsForProject".equals(operation)) {
            String pid = request.getParameter("pid1");
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getContestsForProject(Long.parseLong(pid));
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).append(", start = ").
                        append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contests for project :<br/>" + b.toString();
        } else if ("updateContestStatus".equals(operation)) {
            String cid = request.getParameter("cid2");
            String sid = request.getParameter("sid1");
            port.updateContestStatus(Long.parseLong(cid), Long.parseLong(sid));
            callResult = "Updated contests status successfully";
        } else if ("uploadDocumentForContest".equals(operation)) {
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(Long.parseLong(request.getParameter("dcid1")));
            doc.setDescription(request.getParameter("ddesc1"));
            doc.setDocumentId(-1);
            doc.setDocumentTypeId(Long.parseLong(request.getParameter("dtid1")));
            doc.setFile(request.getParameter("dfile1").getBytes());
            doc.setFileName(request.getParameter("dfilename1"));
            doc.setMimeTypeId(Long.parseLong(request.getParameter("dmtid1")));
            doc.setPath(request.getParameter("dpath1"));
            doc = port.uploadDocumentForContest(doc);
            callResult = "Uploaded document for contest: <br/> Document: ID = " + doc.getDocumentId()
                         + ", desc = " + doc.getDescription() + ", file = " + doc.getFileName();
        } else if ("uploadDocument".equals(operation)) {
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(-1);
            doc.setDescription(request.getParameter("ddesc2"));
            doc.setDocumentId(-1);
            doc.setDocumentTypeId(Long.parseLong(request.getParameter("dtid2")));
            doc.setFile(request.getParameter("dfile2").getBytes());
            doc.setFileName(request.getParameter("dfilename2"));
            doc.setMimeTypeId(Long.parseLong(request.getParameter("dmtid2")));
            doc.setPath(request.getParameter("dpath2"));
            doc = port.uploadDocument(doc);
            callResult = "Uploaded document: <br/> Document: ID = " + doc.getDocumentId()
                         + ", desc = " + doc.getDescription() + ", file = " + doc.getFileName();
        } else if ("addDocumentToContest".equals(operation)) {
            String did = request.getParameter("did1");
            String cid = request.getParameter("cid3");
            port.addDocumentToContest(Long.parseLong(did), Long.parseLong(cid));
            callResult = "Added document to contest successfully";
        } else if ("removeDocumentFromContest".equals(operation)) {
            String did = request.getParameter("did4");
            String cid = request.getParameter("cid4");
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(Long.parseLong(cid));
            doc.setDocumentId(Long.parseLong(did));
            port.removeDocumentFromContest(doc);
            callResult = "Removed document from contest successfully";
        } else if ("retrieveSubmissionsForContest".equals(operation)) {
            String cid = request.getParameter("cid5");
            List<SubmissionData> submissions = port.retrieveSubmissionsForContest(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (SubmissionData sc : submissions) {
                b.append("    Submission: ID = ").append(sc.getSubmissionId()).append(", date = ").
                        append(sc.getSubmittedDate()).append("<br/>");
            }
            callResult = "Retrieved submissions for contest :<br/>" + b.toString();
        }/* else if ("retrieveAllSubmissionsByMember".equals(operation)) {
            String uid = request.getParameter("uid1");
            List<SubmissionData> submissions = port.retr(Long.parseLong(uid));
            StringBuilder b = new StringBuilder();
            for (SubmissionData sc : submissions) {
                b.append("    Submission: ID = ").append(sc.getSubmissionId()).append(", date = ").
                        append(sc.getSubmittedDate()).append("<br/>");
            }
            callResult = "Retrieved submissions for user :<br/>" + b.toString();
        } */else if ("removeSubmission".equals(operation)) {
            String sid = request.getParameter("sbmid1");
            port.removeSubmission(Long.parseLong(sid));
            callResult = "Removed submission successfully";
        }/* else if ("getContestsForClient".equals(operation)) {
            String uid = request.getParameter("uid2");
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getContestsForClient(Long.parseLong(uid));
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contest for client:<br/>" + b.toString();
        }*/ else if ("retrieveSubmission".equals(operation)) {
            String sid = request.getParameter("sbmid2");
            SubmissionData s = port.retrieveSubmission(Long.parseLong(sid));
            StringBuilder b = new StringBuilder();
            b.append("    Submission: ID = ").append(s.getSubmissionId()).append(", date = ").
                    append(s.getSubmittedDate());
            callResult = "Retrieved submission: <br/>" + b;
        } else if ("removeDocument".equals(operation)) {
            String did = request.getParameter("did5");
            port.removeDocument(Long.parseLong(did));
            callResult = "Removed document successfully";
        } else if ("getMimeTypeId".equals(operation)) {
            String ct = request.getParameter("ct1");
            long mimeTypeId = port.getMimeTypeId(ct);
            callResult = "Retrieved mime type ID for content type: " + mimeTypeId;
        } /*else if ("purchaseSubmission".equals(operation)) {
            String sid = request.getParameter("sbmid3");
            String ppo = request.getParameter("ppo3");
            String st = request.getParameter("st3");
            port.purchaseSubmission(Long.parseLong(sid), ppo, st);
            callResult = "Purchase submission successfully";
        } else if ("createContestPayment".equals(operation)) {
            ContestPaymentData p = new ContestPaymentData();
            String cid = request.getParameter("cid6");
            String ppo = request.getParameter("ppo6");
            String psid = request.getParameter("psid6");
            String price = request.getParameter("price6");
            String st = request.getParameter("st6");
            p.setContestId(Long.parseLong(cid));
            p.setCreateDate(new Date());
            p.setPaymentStatusId(Long.parseLong(psid));
            p.setPaypalOrderId(ppo);
            p.setPrice(Double.parseDouble(price));
            p = port.createContestPayment(p, st);
            callResult = "Created contest payment:<br/> Payment: contest ID = " + p.getContestId()
                         + ", date = " + p.getCreateDate() + ", status = " + p.getPaymentStatusId()
                         + ", price = " + p.getPrice() + ", PayPal Order ID = " + p.getPaypalOrderId();
            ;
        } else if ("getContestPayment".equals(operation)) {
            String cid = request.getParameter("cid7");
            ContestPaymentData p = port.getContestPayment(Long.parseLong(cid));
            callResult = "Retrieved contest payment details: <br/> Payment: contest ID = " + p.getContestId()
                         + ", date = " + p.getCreateDate() + ", status = " + p.getPaymentStatusId()
                         + ", price = " + p.getPrice() + ", PayPal Order ID = " + p.getPaypalOrderId();
        } else if ("editContestPayment".equals(operation)) {
            ContestPaymentData p = new ContestPaymentData();
            String cid = request.getParameter("cid8");
            String ppo = request.getParameter("ppo8");
            String psid = request.getParameter("psid8");
            String price = request.getParameter("price8");
            p.setContestId(Long.parseLong(cid));
            p.setCreateDate(new Date());
            p.setPaymentStatusId(Long.parseLong(psid));
            p.setPaypalOrderId(ppo);
            p.setPrice(Double.parseDouble(price));
            port.editContestPayment(p);
            callResult = "Updated contest payment successfully";
        } else if ("removeContestPayment".equals(operation)) {
            String cid = request.getParameter("cid9");
            boolean result = port.removeContestPayment(Long.parseLong(cid));
            callResult = "Removed contest payment. Deleted = " + result;
        } */else if ("setSubmissionPlacement".equals(operation)) {
            String sid = request.getParameter("sbmid4");
            String place = request.getParameter("place4");
            port.setSubmissionPlacement(Long.parseLong(sid), Integer.parseInt(place));
            callResult = "Set submission placement successfully";
        } else if ("setSubmissionPrize".equals(operation)) {
            String sid = request.getParameter("sbmid5");
            String prid = request.getParameter("prid5");
            port.setSubmissionPrize(Long.parseLong(sid), Long.parseLong(prid));
            callResult = "Set submission prize successfully";
        } else if ("markForPurchase".equals(operation)) {
            String sid = request.getParameter("sbmid6");
            port.markForPurchase(Long.parseLong(sid));
            callResult = "Marked submission for purchase successfully";
        } else if ("getChangeHistory".equals(operation)) {
            String cid = request.getParameter("cid10");
            List<ChangeHistoryData> history = port.getChangeHistory(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (ChangeHistoryData historyData : history) {
                b.append("    History: field = ").append(historyData.getFieldName()).append(", old = ").
                        append(historyData.getOldData()).append(", new = ").append(historyData.getNewData()).
                        append(", timestamp").append(historyData.getTimestamp()).append(", user = ").
                        append(historyData.getUserName()).append(", transaction = ").
                        append(historyData.getTransactionId()).append("<br/>");
            }
            callResult = "Retrieved history data for contest :<br/>" + b.toString();
        } else if ("getLatestChanges".equals(operation)) {
            String cid = request.getParameter("cid11");
            List<ChangeHistoryData> history = port.getLatestChanges(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (ChangeHistoryData historyData : history) {
                b.append("    History: field = ").append(historyData.getFieldName()).append(", old = ").
                        append(historyData.getOldData()).append(", new = ").append(historyData.getNewData()).
                        append(", timestamp").append(historyData.getTimestamp()).append(", user = ").
                        append(historyData.getUserName()).append(", transaction = ").
                        append(historyData.getTransactionId()).append("<br/>");
            }
            callResult = "Retrieved latest changes for contest :<br/>" + b.toString();
        } else if ("deleteContest".equals(operation)) {
            String cid = request.getParameter("cid12");
            port.deleteContest(Long.parseLong(cid));
            callResult = "Deleted contest successfully";
        } else if ("processMissingPayments".equals(operation)) {
            String cid = request.getParameter("cid13");
            port.processMissingPayments(Long.parseLong(cid));
            callResult = "Processed missing payments for contest successfully";
        } else if ("addChangeHistory".equals(operation)) {
            ChangeHistoryData h = new ChangeHistoryData();
            h.setContestId(Long.parseLong(request.getParameter("cid14")));
            h.setFieldName(request.getParameter("fn14"));
            h.setNewData(request.getParameter("nv14"));
            h.setOldData(request.getParameter("ov14"));
            h.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
            h.setTransactionId(Long.parseLong(request.getParameter("trid14")));
            h.setUserAdmin(request.isUserInRole("Cockpit Administrator"));
            h.setUserName(request.getUserPrincipal().getName());
            List<ChangeHistoryData> list = new ArrayList<ChangeHistoryData>();
            list.add(h);
            port.addChangeHistory(list);
            callResult = "Added change history records for contest successfully";
        } else if ("updateSubmission".equals(operation)) {
            SubmissionData s = new SubmissionData();
            s.setContestId(Long.parseLong(request.getParameter("cid15")));
            s.setMarkedForPurchase(false);
            s.setPaidFor(true);
            s.setPassedScreening(false);
            s.setPlacement(1);
            s.setRemoved(false);
            s.setSubmissionId(Long.parseLong(request.getParameter("sbmid15")));
            s.setSubmittedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
            s.setSubmitterId(Long.parseLong(request.getParameter("uid15")));
            port.updateSubmission(s);
            callResult = "Updated submission successfully";
        } else if ("searchContests".equals(operation)) {
            String name = request.getParameter("cname1");
            com.topcoder.search.builder.filter.Filter d = new LikeFilter("name", name);
            ContestServiceFilter filter = new ContestServiceFilter(d);
            List<StudioCompetition> contests = port.searchContests(filter);
            StringBuilder b = new StringBuilder();
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Found contests by name:<br/>" + b.toString();
        } else if ("getAllDocumentTypes".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<DocumentType> types = port.getAllDocumentTypes();
            for (DocumentType t : types) {
                b.append("    DocumentType: ID = ").append(t.getDocumentTypeId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
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
<html>
  <head>
      <title>Contest Service Facade Demo</title>
  </head>
  <body>
      <p>Called Contest Service Facade operation: <%=calledOperation%></p>
      <p>Resulf of the call: <%=callResult%></p>
      <a href="index.jsp">Back to list of available operations</a>
  </body>
</html>
