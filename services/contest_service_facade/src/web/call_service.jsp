<%--
  - Author: pulky
  - Date: 21 Oct 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is a simple page to test contest facade services
--%>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.topcoder.service.contest.eligibilityvalidation.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;

    try {
        String operation = request.getParameter("operation");
        calledOperation = operation;
        if ("isEligible".equals(operation)) {
            String uid = request.getParameter("user_id");
            String cid = request.getParameter("contest_id");
            String isStudio = request.getParameter("is_studio");

            Properties env = new Properties(); 

            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            env.put(Context.PROVIDER_URL, "jnp://ec2-174-129-60-137.compute-1.amazonaws.com:1399"); // remote machine IP
            
            Context context = new InitialContext(env);
            ContestEligibilityService contestServiceFacade =
                (ContestEligibilityService) context.lookup("remote/ContestEligibilityServiceBean");

            callResult = contestServiceFacade.isEligible(Long.parseLong(uid), Long.parseLong(cid), isStudio.equalsIgnoreCase("true"));

            //callResult = "true.";
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
      <p>Contest Eligibility tests: <%=calledOperation%></p>
      <p>Resulf of the call isss <%=callResult%></p>
      <a href="test_services.jsp">Back to list of available operations</a>
  </body>
</html>
