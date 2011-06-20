<%--
  - Author: Blues
  - Version: 1.1 (Release Assembly - TC Cockpit Sidebar Header and Footer Update)
  - Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: The footer of the topcoder cockpit.
  -
  - Version 1.1 - updated to the new footer.
--%>
<%@ include file="taglibs.jsp" %>
<div id="footer">
	<!--Update footer-->
 	<div class="socialNetwork">
        <span>Follow us on :</span>
        <a href="http://www.twitter.com/TopCoder" class="twitterIcon" target="_blank" title="Follow us on Twitter"></a>
        <a href="http://www.linkedin.com/company/topcoder" class="linkedInIcon" target="_blank"title="Follow us on LinkedIn"></a>
        <a href="http://www.facebook.com/TopCoderInc" class="facebookIcon" target="_blank" title="Follow us on Facebook"></a>
    </div>
    <!--End socialNetwork-->
    <div class="copyright">
    	<span>Copyright TopCoder, Inc. 2001-2011</span>
        <a href="http://www.topcoder.com/tc?module=Static&d1=about&d2=terms" target="_blank" title="Terms of Use">Terms of Use</a>
        <a href="http://www.topcoder.com/tc?module=Static&d1=about&d2=privacy" target="_blank" title="Privacy Policy">Privacy Policy</a>
    </div>
    <!--End copyright-->
</div>

<% 
    String handle = (String) request.getSession().getAttribute("userHandle"); 
%>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-6340959-9']);
  _gaq.push(['_setDomainName', '.topcoder.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<!-- End #footer -->

