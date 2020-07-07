<%--
  - Author: FireIce
  - Version: 1.0
  - Copyright (C) 2018 TopCoder Inc., All Rights Reserved.
  -
  - Description: The footer of scripts
  -
--%>
<%@ include file="taglibs.jsp" %>
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ page import="com.topcoder.direct.services.view.util.SessionData" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

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

<!-- Auth0 RS256 staff -->
<script language="javascript" type="text/javascript" src="https://auth0-login.topcoder-dev.com/setupAuth0.js"></script>

<script>
  if (qs['test'] == 'true') {
        window.onload = authSetup;
  }
</script>
<!-- End Auth0 RS256 staff -->