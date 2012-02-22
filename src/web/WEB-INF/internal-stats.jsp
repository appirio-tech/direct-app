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
    <link rel="stylesheet" href="/css/filter-panel.css?v=211271" media="all" type="text/css"/>
    <c:set var="PAGE_TYPE" value="internal" scope="request"/>
    <script type="text/javascript">
        $('.collapse').live('click', function() {
            $(this).removeClass('collapse').addClass('expand');
            $(this).find('img').attr("src", "/images/filter-panel/collapse_icon.png");
            $(this).parent().parent().parent().parent().parent().find('.filterContent').hide();
            $(this).parent().parent().parent().parent().parent().find('.filterBottom').hide();
            $(this).parent().parent().parent().parent().parent().find('.collapseBottom').show();
            $(this).parent().parent().parent().parent().css({"margin-bottom":"-2px","height":'30px'});
            $(this).parent().parent().parent().parent().find(".rightSide").css({"height":'30px'});
        });
        $('.expand').live('click', function() {
            $(this).removeClass('expand').addClass('collapse');
            $(this).find('img').attr("src", "/images/filter-panel/expand_icon.png");
            $(this).parent().parent().parent().parent().parent().find('.filterContent').show();
            $(this).parent().parent().parent().parent().parent().find('.filterBottom').show();
            $(this).parent().parent().parent().parent().parent().find('.collapseBottom').hide();
            $(this).parent().parent().parent().parent().css({"margin-bottom":"0px","height":'32px'});
            $(this).parent().parent().parent().parent().find(".rightSide").css({"height":'32px'});
        });
        
        var error = "${excelOpenError}";
        
        $(function() {
            if (error) {
                showErrors(error);
            }
        });
        
    </script>
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
                    <c:if test="${fn:length(sheetTabs) gt 1}">
                    <div id="tabs3">
					        <ul>
						        <c:set var="perWidth" value="${100 / fn:length(sheetTabs)}"/>
						        <c:forEach var="tab" items="${sheetTabs}" varStatus="vs">
					            <li class="<c:if test="${vs.index eq 0}">firstItem</c:if> <c:if test="${vs.index eq fn:length(sheetTabs) - 1}">lastItem</c:if> <c:if test="${sheetIndex eq vs.index}">on</c:if>" <c:if test="${vs.index != (fn:length(sheetTabs) - 1)}">style="width:${perWidth}%"</c:if>>
					                    <a href="<s:url action="internalStats" namespace="/"></s:url>?sheetIndex=${vs.index}" class="<c:if test="${vs.index eq 0}">first</c:if> <c:if test="${vs.index eq fn:length(sheetTabs) - 1}">last</c:if>">
					                    <span class="left"><span class="right">${tab}</span></span></a>
					            </li>
                                </c:forEach>
					         </ul>
					</div>
					</c:if>
                        <c:if test="${tableData != null}">
                        <div class="container2 resultTableContainer" id="resultsContainer" style="border-bottom:1px solid #A8ABAD;margin-bottom:10px;">
                            <div class="statsTable">
                                 ${tableData}
                            </div>
                        </div>
                        </c:if>
                        <!-- End .container2 -->
                        
                        <form id="uploadPanelForm" enctype="multipart/form-data" method="POST" action="<s:url action="uploadSheet" namespace="/"></s:url>">
                            <!-- start filter panel -->
                            <div class='filterPanel'>
                                <div class='filterHead' style="margin-bottom: -2px; height: 30px;">
                                    <div class='rightSide' style="height: 30px;">
                                        <div class='inner'>
                                        	<div class='filterText'>
                                                <a href='javascript:;' class='expand'><img src='/images/filter-panel/collapse_icon.png' alt=''/></a>
                                                <span class='title'>Upload</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end .filterHead-->
                                <div class='filterContent hide'>
                                	<div class='rightSide'>
                                        <div class='inner'>
                                            <div class="row">
                                                Upload excel file:<input type="file" name="file" /><input type="submit" name="Submit" value="Upload"/>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </div>
                                <!--end .filterHead-->
                                <div class='filterBottom hide'><div class='rightSide'><div class='inner'></div></div></div>
                                <!--end .filterBottom-->
                                <div class='collapseBottom'><div class='rightSide'><div class='inner'></div></div></div>
                            </div>
                            <!--end .filterPanel-->
                        </form>
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
