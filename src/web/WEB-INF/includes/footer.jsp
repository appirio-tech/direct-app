<%--
  - Author: Blues, GreatKevin, TCASSEMBLER
  - Version: 1.4
  - Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
  - - Do not show feedback in the error page
  - Version 1.3 (Module Assembly - TC Direct Struts 2 Upgrade)
  - - Fixed the anchor for linkedin.
  - Version 1.4 (Release Assembly - TopCoder Cockpit Navigation Update)
  - - Update the feedback widget style
  -
  - Description: The footer of the topcoder cockpit.
  -
--%>
<%@ include file="taglibs.jsp" %>
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ page import="com.topcoder.direct.services.view.util.SessionData" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>


<div id="footer">
	<!--Update footer-->
 	<div class="socialNetwork">
        <span>Follow us on :</span>
        <a href="https://www.twitter.com/topcoder" class="twitterIcon" target="_blank" title="Follow us on Twitter"></a>
        <a href="https://www.linkedin.com/company/topcoder" class="linkedInIcon" target="_blank" title="Follow us on LinkedIn"></a>
        <a href="https://www.facebook.com/topcoder" class="facebookIcon" target="_blank" title="Follow us on Facebook"></a>
		<a href="https://plus.google.com/u/0/b/104268008777050019973/104268008777050019973/posts" class="gPlusIcon" target="_blank" title="Follow us on Google+"></a>
		<a href="https://youtube.com/topcoderinc" class="youtubeIcon" target="_blank" title="Follow us on YouTube"></a>
    </div>
    <!--End socialNetwork-->
    <div class="copyright">
        <%--<div class="logoFooterWrapper"><img src="/images/CS_TopCoder-Banner_782x175.png" alt="TopCoder" width="250"></div>--%>
    	<span>&#169; Copyright TopCoder, Inc. 2001 - <script type="text/javascript">d=new Date();document.write(d.getFullYear());</script></span>&nbsp;&nbsp;All Rights Reserved.|&nbsp;
        <a href="http://www.topcoder.com/community/how-it-works/terms/" target="_blank" title="Terms">Terms</a>&nbsp;|&nbsp;
        <a href="http://www.topcoder.com/community/how-it-works/privacy-policy/" target="_blank" title="Privacy">Privacy</a>
    </div>
    <!--End copyright-->
</div>
<!-- End #footer -->

