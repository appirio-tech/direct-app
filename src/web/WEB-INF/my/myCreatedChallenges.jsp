<%--
  - Author: GreatKevin
  - Version: 1.0
  - Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 TopCoder Direct - My Created Challenges
  -
  - Display my created challenges.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:challengesPageType tab="myCreatedChallenges"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/search.js?v=214871"></script>

<body id="page">

<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="noRightBar">

                    <div id="area1">
                        <!-- the main area -->
                        <div class="area1Content">
                            <div class="container2 resultTableContainer" style="margin-top: 40px">
                                <div>
                                    <table id="myCreatedChallengesTable"
                                           class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0"
                                           cellspacing="0">

                                        <colgroup>
                                            <col width="8%"/>
                                            <col width="17%"/>
                                            <col width="13%"/>
                                            <col width="12%"/>
                                            <col width="10%"/>
                                            <col width="15%"/>
                                            <col width="15%"/>
                                            <col width="10%"/>
                                        </colgroup>

                                        <thead>
                                        <tr>
                                            <th>Client Name</th>
                                            <th>Billing Account Name</th>
                                            <th>Direct Project Name</th>
                                            <th>Challenge Name</th>
                                            <th>Challenge Type</th>
                                            <th>Challenge Start Date</th>
                                            <th>Challenge End Date</th>
                                            <th>Total Prize</th>
                                        </tr>
                                        </thead>

                                        <tbody>

                                        </tbody>
                                    </table>
                                    <!-- End .projectsStats -->

                                </div>

                                <div class="container2Left">
                                    <div class="container2Right">
                                        <div class="container2Bottom">
                                            <div>
                                                <div>
                                                    <div class="panel tableControlPanel"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .container2 -->
                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="../includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

</body>
<!-- End #page -->

</html>
