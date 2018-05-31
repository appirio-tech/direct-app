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

<% 
    String handle = (String) request.getSession().getAttribute("userHandle"); 
	long userId = -1;
	HttpServletRequest req = DirectUtils.getServletRequest();
	if (req != null) {
		userId = new SessionData(req.getSession()).getCurrentUserId();
	}
       
%>


<script>
  !function(){var analytics=window.analytics=window.analytics||[];if(!analytics.initialize)if(analytics.invoked)window.console&&console.error&&console.error("Segment snippet included twice.");else{analytics.invoked=!0;analytics.methods=["trackSubmit","trackClick","trackLink","trackForm","pageview","identify","reset","group","track","ready","alias","debug","page","once","off","on"];analytics.factory=function(t){return function(){var e=Array.prototype.slice.call(arguments);e.unshift(t);analytics.push(e);return analytics}};for(var t=0;t<analytics.methods.length;t++){var e=analytics.methods[t];analytics[e]=analytics.factory(e)}analytics.load=function(t,e){var n=document.createElement("script");n.type="text/javascript";n.async=!0;n.src=("https:"===document.location.protocol?"https://":"http://")+"cdn.segment.com/analytics.js/v1/"+t+"/analytics.min.js";var o=document.getElementsByTagName("script")[0];o.parentNode.insertBefore(n,o);analytics._loadOptions=e};analytics.SNIPPET_VERSION="4.1.0";
  analytics.load("L0IPw4j1ZBRrjSbfpJiSToUJxoCfs1gF");
  analytics.page();
  }}();
</script>

<script>
analytics.identify('', {
  username: '<%= handle %>',
  id: '<%=userId%>'
});
</script>

<!-- Start of topcoder Zendesk Widget script -->
<script>/*<![CDATA[*/window.zE||(function(e,t,s){var n=window.zE=window.zEmbed=function(){n._.push(arguments)}, a=n.s=e.createElement(t),r=e.getElementsByTagName(t)[0];n.set=function(e){ n.set._.push(e)},n._=[],n.set._=[],a.async=true,a.setAttribute("charset","utf-8"), a.src="https://static.zdassets.com/ekr/asset_composer.js?key="+s, n.t=+new Date,a.type="text/javascript",r.parentNode.insertBefore(a,r)})(document,"script","d4e16f55-6e5d-4957-8345-e218128ae36b");/*]]>*/</script>
<!-- End of topcoder Zendesk Widget script -->


<!-- End #footer -->

