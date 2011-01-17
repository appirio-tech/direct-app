<%@ include file="taglibs.jsp" %>
<div id="footer">
    <h2><a href="http://forums.topcoder.com/?module=ThreadList&forumID=535887">Need Help?</a></h2>

    <p>TopCoder is the world's largest competitive software development community with
        <s:property value="sessionData.memberCount"/> developers representing over 200 countries.</p>

    <p><strong>Copyright &copy;2011, TopCoder, Inc. All rights reserved</strong></p>
    <a href="http://www.topcoder.com" class="poweredBy"><img src="/images/logo2.png" alt="TopCoder"/></a>
</div>

<% 
    String handle = (String) request.getSession().getAttribute("userHandle"); 
%>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-6340959-9']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<!-- End #footer -->


<!-- Performable Analytics -->
<%
 if (handle != null && !handle.equals("")) {
%>
<script type="text/javascript">

    var _paq = _paq || [];
 
    _paq.push(["identify", {
    id: "<%=handle %>"
    }]);
</script>

<%}%>

<script src="//d1nu2rn22elx8m.cloudfront.net/performable/pax/4wrbNk.js" type="text/javascript"></script>

