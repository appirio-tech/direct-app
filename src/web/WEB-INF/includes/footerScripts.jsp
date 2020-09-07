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
<%@ page import="com.topcoder.direct.services.configs.ServerConfiguration" %>

<% 
    String handle = (String) request.getSession().getAttribute("userHandle"); 
	long userId = -1;
	HttpServletRequest req = DirectUtils.getServletRequest();
	if (req != null) {
		userId = new SessionData(req.getSession()).getCurrentUserId();
	}
       
%>

<script>
  !function(){var analytics=window.analytics=window.analytics||[];if(!analytics.initialize)if(analytics.invoked)window.console&&console.error&&console.error("Segment snippet included twice.");else{analytics.invoked=!0;analytics.methods=["trackSubmit","trackClick","trackLink","trackForm","pageview","identify","reset","group","track","ready","alias","debug","page","once","off","on","addSourceMiddleware","addIntegrationMiddleware","setAnonymousId","addDestinationMiddleware"];analytics.factory=function(e){return function(){var t=Array.prototype.slice.call(arguments);t.unshift(e);analytics.push(t);return analytics}};for(var e=0;e<analytics.methods.length;e++){var key=analytics.methods[e];analytics[key]=analytics.factory(key)}analytics.load=function(key,e){var t=document.createElement("script");t.type="text/javascript";t.async=!0;t.src="https://cdn.segment.com/analytics.js/v1/" + key + "/analytics.min.js";var n=document.getElementsByTagName("script")[0];n.parentNode.insertBefore(t,n);analytics._loadOptions=e};analytics.SNIPPET_VERSION="4.13.1";
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

<script>
      //window.onload = authSetup;
      function prepareFrame() {
        var ifrm = document.createElement("iframe");
        ifrm.setAttribute("src", '<%=ServerConfiguration.TOPCODER_NEW_AUTH_URL%>');
        ifrm.style.width = "0px";
        ifrm.style.height = "0px";
        document.body.appendChild(ifrm);
      }
      window.onload = prepareFrame;
</script>
<!-- End Auth0 RS256 staff -->
