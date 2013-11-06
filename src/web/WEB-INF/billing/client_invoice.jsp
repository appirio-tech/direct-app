<%--
  - Author: TCSASSEMBLER
  - Version: 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the client invoice management page for admin and renders the client invoice table
  - for the client user.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <jsp:include page="/WEB-INF/includes/paginationSetup.jsp"/>
    <script type="text/javascript" src="/scripts/clientInvoiceManage.js"></script>
    <ui:dashboardPageType tab="dashboard"/>
</head>

<c:set var="CURRENT_TAB" scope="request" value="billing"/>

<body id="page">

<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>
                    <div id="area1"><!-- the main area -->
                        <div class="area1Content clientInvoice">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a>
                                &gt;
                                <strong>Billing</strong>
                            </div>

                            <s:if test="clientInvoicesViewable">
                                <h2 class="header">Billing</h2>

                                <div class="tableData">
                                    <table>
                                        <thead>
                                        <th>Invoice Date</th>
                                        <th>Description</th>
                                        </thead>
                                        <tbody>
                                        <s:iterator value="clientInvoices">
                                            <tr>
                                                <td>
                                                    <a href="<s:url action='downloadInvoiceUpload'><s:param name='invoiceUploadId' value='id'/></s:url>"><s:date
                                                            name="invoiceUploadDate" format="M/d/yyyy"/></a></td>
                                                <td><s:property value="description"/></td>
                                            </tr>
                                        </s:iterator>

                                        </tbody>
                                    </table>
                                </div>
                            </s:if>

                            <s:if test="clientInvoiceUploadViewable">
                                <h2 class="header">Manage Invoices</h2>

                                <div class="clientInvoiceManageRow">
                                    <span class="fieldName">Customer Name:</span>
                                    <s:select id="clientOption" list="clients" listKey="id" listValue="name"/>
                                    <span class="errorMessage">* Please choose the customer first</span>
                                </div>
                                <div class="clientInvoiceManageRow">
                                    <span class="fieldName">Existing Invoices for this customer:</span>

                                    <div class="timeRange">
                                        <span class="fieldName2 title">Time Range:</span>
                                        <input id='dateBegin' type='text' readonly="readonly" class="date-pick"/>
                                        <span class='title toLabel'>to</span>
                                        <input id='dateEnd' type='text' readonly="readonly" class="date-pick"/>
                                        <a href="javascript:;" id="clientInvoicesFilter">Show Invoices</a>
                                        <span class="errorMessage">* Please choose the start date and end date to filter</span>
                                        <span class="errorMessage2">* Start Date should not larger than then end date</span>
                                    </div>
                                    <div class="clientInvoicesResult">
                                        <ol>
                                        </ol>
                                    </div>
                                </div>
                                <div class="clientInvoiceManageRow last" style="padding: 0px 0px;">
                                    <div class="uploadTitle">Upload an invoice for this customer:</div>

                                    <div class="uploadRow">
                                        <span class="fieldName2 title">Invoice Date:</span>
                                        <input id='uploadDate' type='text' readonly="readonly" class="date-pick"/>
                                        <span class="errorMessage">* Please select the invoice date</span>
                                        <span class="errorMessage2">* Invoice Date cannot be a future date</span>
                                    </div>
                                    <div class="uploadRow uploadFileSection">
                                        <span class="fieldName2">Invoice File:</span>
                                        <input type="file" name="uploadInvoiceFile"/>
                                        <span class="errorMessage">* Please choose the invoice to upload</span>
                                        <span class="errorMessage2">* The file should be of type PDF</span>
                                    </div>
                                    <div class="uploadRow" style="height: 100px">
                                        <span class="fieldName2">Description:</span>
                                        <textarea rows="5" cols="40" name="uploadInvoiceDescription"></textarea>
                                        <span class="errorMessage">* Should not exceeds 500 chars</span>
                                    </div>
                                    <div>
                                        <a id="invoiceUploadButton" class="button6" href="javascript:;"><span
                                                class="left"><span class="right"
                                                                   style="padding-left: 5px; padding-right: 5px;">Upload</span></span></a>
                                    </div>
                                </div>
                            </s:if>


                        </div>
                    </div>

                </div>


            </div>
            <!-- End #mainContent -->
        </div>
        <!-- End #content -->
        <jsp:include page="/WEB-INF/includes/footer.jsp"/>

    </div>
    <!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>


</body>
<!-- End #page -->


</html>
