<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="search"/>
    <jsp:include page="includes/paginationSetup.jsp"/>
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
						<s:if test="viewData.isAllProjectsPage == false">Search</s:if>
						<s:if test="viewData.isAllProjectsPage == true">Projects</s:if>
						</h2>
                    </div><!-- End .areaHeader -->
					
					<s:if test="viewData.isAllProjectsPage == false">
                    <div class="search" style="height:auto;overflow:hidden">
                        <s:form method="get" action="dashboardSearch" namespace="/" id="DashboardSearchForm">
                            <label class="fLeft" for="searchFor">Search For:</label>
                            <s:textfield cssClass="fLeft" name="formData.searchFor" id="searchFor"/>
                            <label class="fLeft" for="searchIn"> In</label>
                            <s:select id="searchIn" list="requestData.dashboardSearchTypes" name="formData.searchIn"/>
                            <div id="datefilter" style="float:left;">
                               <label for="startDate" class="fLeft">Start:</label> <s:textfield cssClass="fLeft text date-pick dp-applied" style="width:80px;" name="formData.startDate" id="startDate" readonly="true"/>
                               <label for="endDate" class="fLeft">End:</label> <s:textfield cssClass="fLeft text date-pick dp-applied" name="formData.endDate" style="width:80px;" id="endDate" readonly="true"/>
                            </div>    
                            <s:hidden name="formData.excel" id="formDataExcel" value="false" />
                            <a href="javascript:directSearch();" class="button1 fLeft">
                                <span>Search</span></a>
                        </s:form>
                    </div>
					</s:if>

                    <s:if test="viewData.resultType != null">
                        <div class="container2" id="resultsContainer">
                            <div class="container2Left">
                                <div class="container2Right">
                                    <div class="container2Bottom">
                                        <div class="container2BottomLeft">
                                            <div class="container2BottomRight">
                                                <div class="container2Content">
                                                    <s:if test="viewData.resultType.name() == 'PROJECTS'">
                                                        <s:include value="includes/dashboard/projectsSearchResults.jsp"/>
                                                    </s:if>
                                                    <s:if test="viewData.resultType.name() == 'CONTESTS'">
                                                        <s:include value="includes/dashboard/contestsSearchResults.jsp"/>
                                                    </s:if>
                                                    <s:if test="viewData.resultType.name() == 'MEMBERS'">
                                                        <s:include value="includes/dashboard/membersSearchResults.jsp"/>
                                                    </s:if>

                                                </div>
                                                <!-- End .container2Content -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- End .container2 -->
                    </s:if>
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
