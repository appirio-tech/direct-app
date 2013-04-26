<%--
  - Author: GreatKevin, hanshuai, GreatKevin, TCSASSEMBLER
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  -
  - Version 1.1 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
  -  - Updated pipeline icon to link to pipeline page.
  -
  - Version 1.2 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part) changes:
  - - Add link for the roadmap icon in the sidebar
  -
  - Version 1.3 (Module Assembly - TC Cockpit Enterprise Dashboard New Active Contests) changes:
  - - Add link for the active contests icon in the sidebar
  -
  - Version 1.4 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
  - - Add link for the analysis icon in the sidebar
  -
  - Version 1.5 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Update the tip text for roadmap
  -
  - Version 1.6 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
  - - Add payment icon.
  -
  - Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: The side bar of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!-- siderbar -->
<div id="silderBar">
    <ul>
        <li <c:if test="${requestScope.CURRENT_SIDEBAR eq 'payment'}">class='active'</c:if>>
            <a href="<s:url action="overview" namespace="/payments"/>" class="memberPaymentIcon" title="Member Payments" rel="Dashboard page for member payments.">Member Payments</a>
        </li>
    </ul>
</div>
<!-- End #siderBar -->
