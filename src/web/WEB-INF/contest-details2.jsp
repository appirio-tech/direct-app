<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="contests"/>
    <ui:contestPageType tab="details"/>

    <script type="text/javascript">
    //<![CDATA[
        var paramContestId = ${param.contestId};
    //]]>
    </script>    
    <script type="text/javascript" src="/scripts/launch/entity.js?v=20"></script>	
    <script type="text/javascript" src="/scripts/launch/main.js?v=21"></script>		
    <script type="text/javascript" src="/scripts/launch/contestDetail.js?v=23"></script>		
</head>

<body id="page">
<a name="top" style="width:0px;height:0px;"></a>
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                    <div class="area1Content">
                    <div class="currentPage">
                        <a href="<s:url action="dashboard" namespace="/"/>" class="home">Dashboard</a> &gt;
                        <a href="<s:url action="currentProjectDetails" namespace="/"/>"><s:property value="sessionData.currentProjectContext.name"/></a> &gt;
                        <strong><s:property value="viewData.contestStats.contest.title"/></strong>
                    </div>
                    <div class="areaHeader">
                        <h2 class="title contestTitle" style="background:url('/images/<s:property value="viewData.contest.contestType.letter"/>.png') no-repeat scroll left center transparent">                       
                        <s:property value="viewData.contestStats.contest.title"/>
                        <a href="javascript:previewContest();" class="button5" style="float:right;text-align:center;">View Contest</a>    	
                        </h2>                        
                    </div><!-- End .areaHeader -->
                                        
                    <jsp:include page="includes/contest/contestStats.jsp"/>

                    <div class="container2 tabs3Container">

                        <jsp:include page="includes/contest/tabs.jsp"/>

                        <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
                            <div class="container2BottomLeft"><div class="container2BottomRight">
                            	
                            <div class="container2Content_det">
                               <jsp:include page="includes/contest/editTab.jsp"/>	
									          </div><!-- End .container2Content_det -->
	
                            </div></div>
                        </div></div></div>
                    </div><!-- End .container2 -->

                    <a href="#top" class="button1 backToTop"><span>Back To Top</span></a>
                    </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>