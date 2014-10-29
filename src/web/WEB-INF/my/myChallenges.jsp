<%--
  - Author: GreatKevin
  - Version: 1.1
  - Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 TopCoder Direct - My Created Challenges
  -
  - Version 1.1 (TopCoder Direct - Challenges Section Filters Panel)
  - - Add filter panel
  -
  - Display my challenges.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <ui:challengesPageType tab="myChallenges"/>
    <script type="text/javascript" src="/scripts/jquery.dataTables-1.9.1.min.js"></script>
    <script type="text/javascript" src="/scripts/search.js?v=214871"></script>
    <script type="text/javascript" src="/scripts/my/challenges.js"></script>

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


                            <jsp:include page="../includes/my/filter.jsp"/>

                            <div class="container2 resultTableContainer" style="margin-top: 40px">
                                <div>
                                    <table id="myChallengesTable"
                                           class="projectStats contests paginatedDataTable resultTable"
                                           cellpadding="0"
                                           cellspacing="0">

                                        <colgroup>
                                            <col width="7%"/>
                                            <col width="14%"/>
                                            <col width="11%"/>
                                            <col width="12%"/>
                                            <col width="7%"/>
                                            <col width="7%"/>
                                            <col width="14%"/>
                                            <col width="14%"/>
                                            <col width="7%"/>
                                            <col width="7%"/>
                                        </colgroup>

                                        <thead>
                                        <tr>
                                            <th>Customer</th>
                                            <th>Billing Account</th>
                                            <th>Project</th>
                                            <th>Challenge</th>
                                            <th>Type</th>
                                            <th>Status</th>
                                            <th>Start Date</th>
                                            <th>End Date</th>
                                            <th>Created By</th>
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
