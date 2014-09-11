<%--
  - Author: TCSDEVELOPER
  - Version: 1.1 (Cockpit Spec Review Backend Service Update v1.0)
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from specificationReviewBackend.jsp for invoking the selected 
  - web service operations with provided parameters and displaying the results of the call.
  -
  -- V1.1: Update in Cockpit Security Facade V1.0 change to new WebService facade.
--%>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFacadeWebService" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.topcoder.management.project.Project" %>
<%@ page import="com.topcoder.project.service.FullProjectData" %>
<%@ page import="com.topcoder.project.service.ScorecardReviewData" %>
<%@ page import="com.topcoder.management.review.data.Comment" %>
<%@ page import="com.topcoder.management.review.data.CommentType" %>
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
        if ("createSpecReview".equals(operation)) {
            StringBuilder b = new StringBuilder();
            String pid = request.getParameter("pid1");
            FullProjectData p = port.createSpecReview(Long.parseLong(pid));
            b.append("    Project: ID = ").append(p.getId()).append("<br/>");
            callResult = "Created the following spec review project:<br/>" + b.toString();
        } else if ("getScorecardAndReview".equals(operation)) {
            StringBuilder b = new StringBuilder();
            String pid = request.getParameter("pid2");
            ScorecardReviewData srd = port.getScorecardAndReview(Long.parseLong(pid));
            b.append("    Review = ").append(srd.getReview() != null ? srd.getReview().getId() : null).
                append("<br/>    Scorecard = ").append(srd.getScorecard() != null ? srd.getScorecard().getId() : null).
                append("<br/>");
            callResult = "Scorecard Review Data retrieved:<br/>" + b.toString();
        } else if ("markSoftwareContestReadyForReview".equals(operation)) {
            String pid = request.getParameter("pid3");
            port.markSoftwareContestReadyForReview(Long.parseLong(pid));
            callResult = "Operation executed successfully.<br/>";
        } else if ("addReviewComment".equals(operation)) {
            Long reviewId = Long.parseLong(request.getParameter("rid"));
            Long commentTypeId = Long.parseLong(request.getParameter("ctid"));
            String commentText = request.getParameter("comment");
            Long author = Long.parseLong(request.getParameter("author"));

            Comment comment = new Comment();
            comment.setAuthor(author);
            comment.setCommentType(new CommentType(commentTypeId, "name"));
            comment.setComment(commentText);
            
            port.addReviewComment(reviewId, comment);
            callResult = "Operation executed successfully.<br/>";
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
        <title>Contest Service Facade Demo - Specification Review Backend</title>
    </head>
    <body>
        <p>Called Contest Service Facade operation: <%=calledOperation%></p>
        <p>Resulf of the call: <%=callResult%></p>
        <a href="specificationReviewBackend.jsp">Back to list of available operations</a>
    </body>
</html>
