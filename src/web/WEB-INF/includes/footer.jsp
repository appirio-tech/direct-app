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
<%@ page import="com.topcoder.direct.services.view.util.DirectUtils" %>
<%@ page import="com.topcoder.direct.services.view.util.SessionData" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>


<div id="footer">
	<!--Update footer-->
 	<div class="socialNetwork">
        <span>Follow us on :</span>
        <a href="https://www.twitter.com/TopCoder" class="twitterIcon" target="_blank" title="Follow us on Twitter"></a>
        <a href="https://www.linkedin.com/company/topcoder" class="linkedInIcon" target="_blank" title="Follow us on LinkedIn"></a>
        <a href="https://www.facebook.com/TopCoderInc" class="facebookIcon" target="_blank" title="Follow us on Facebook"></a>
		<a href="https://plus.google.com/104268008777050019973" class="gPlusIcon" target="_blank" title="Follow us on Google+"></a>
		<a href="https://youtube.com/topcoderinc" class="youtubeIcon" target="_blank" title="Follow us on YouTube"></a>
    </div>
    <!--End socialNetwork-->
    <div class="copyright">
        <div class="logoFooterWrapper"><img src="/images/CS_TopCoder-Banner_782x175.png" alt="TopCoder" width="250"></div>
    	<span>Copyright TopCoder, Inc. 2001-<script type="text/javascript">d=new Date();document.write(d.getFullYear());</script></span>
        <a href="https://www.topcoder.com/tc?module=Static&d1=about&d2=terms" target="_blank" title="Terms of Use">Terms of Use</a>
        <a href="https://www.topcoder.com/tc?module=Static&d1=about&d2=privacy" target="_blank" title="Privacy Policy">Privacy Policy</a>
    </div>
    <!--End copyright-->
    <!--Add Feedback Dialog-->
    <c:if test="${requestScope.CURRENT_TAB ne 'enterprise'}">
        <div class="fbMask">
            <div class="fbBox">
                <a class="fbBtn" href="javascript:;">
                    <span class="r">
                        <span class="m">
                            <i></i>
                            Feedback
                        </span>
                    </span>
                </a>
                <div class="fbSubmit">
                    <div class="boxTop"></div>
                    <div class="boxContent">
                        <p>Have some feedback?  Encountered an issue?  Please share with us.</p>
                        <textarea cols="6" rows="10"></textarea>
                        <div class="action">
                            <a href="javascript:;" class="button6 cancel"><span class="left"><span class="right">Cancel</span></span></a>
                            <a href="javascript:;" class="button6 submit"><span class="left"><span class="right">Submit</span></span></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="fbMsg">
                <span class="right"><span class="text">Your feedback has been submitted. Thank you.</span></span>
            </div>
        </div>
    </c:if>
    <!--End .fbMask--> 
</div>

<% 
    String handle = (String) request.getSession().getAttribute("userHandle"); 
	long userId = -1;
	HttpServletRequest req = DirectUtils.getServletRequest();
	if (req != null) {
		userId = new SessionData(req.getSession()).getCurrentUserId();
	}
       
%>


<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-39594372-1']);
  _gaq.push(['_setDomainName', '.topcoder.com']);
  _gaq.push(['_trackPageview']);
  _gaq.push(['_setCustomVar',
      1,                   // This custom var is set to slot #1.  Required parameter.
      'userid',     // The name acts as a kind of category for the user activity.  Required parameter.
      '<%=userId%>',               // This value of the custom variable.  Required parameter.
       1                 // Sets the scope to session-level.  Optional parameter.
   ]);


  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<script src="https://www.surveymonkey.com/jsPop.aspx?sm=9TuySvkc_2bkBu8a5NO08XyQ_3d_3d"> </script>

<!-- End #footer -->

