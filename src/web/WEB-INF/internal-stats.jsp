<%--
  - Author: FireIce
  - Version: 1.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the internal stats info.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <c:set var="PAGE_TYPE" value="internal" scope="request"/>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->

                    <div class="area1Content">
					<div class="areaHeader">
                        <h2 class="title">
						Internal Stats
						</h2>
                    </div><!-- End .areaHeader -->					                  
                        <div class="container2 resultTableContainer" id="resultsContainer">
                            <div class="statsTable">
                                 ${tableData}
                            </div>

                        </div>
                        <!-- End .container2 -->
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
