<!--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
  - - First version of payment overview content page.
  -
  - Description: Render the payment overview content page.
-->
<!-- left column -->
<div class="leftColumnTwoThird">
    <div class="leftColumnTwoThirdLeft">
        <div class="memberPaymentBlockContainer memberPaymentPaymentsBlock">
            <div class="sectionInner">
                <div class="projectTitle">
                    <h3>Payments</h3>
                    <a href="javascript:;" class="icon" rel="This table shows the overview of payments by each methods">!</a>
                    <div class="fRight noteRow">
                        <span class="tblNote redNote">Transfer needed</span><br/>
                        <span class="tblNote greenNote">Payments are covered</span>
                    </div>
                </div>
                <!-- title --><!-- container -->
                <div class="tableContainer">
                    <table border="0" cellpadding="0" cellspacing="0" id="memberPaymentsTable">
                        <colgroup>
                            <col width="27%"/>
                            <col width="30%"/>
                            <col width="20%"/>
                            <col width="23%"/>
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="alignLeft">Payment method</th>
                                <th>Bank Balance</th>
                                <th>Pullable</th>
                                <th>Difference</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="loadRow">
                                <td colspan="4">
                                    <div class="ajaxTableLoader">
                                        <img src="/images/rss_loading.gif" alt="loading"/>
                                    </div>
                                </td>
                            </tr>
                            <tr class="contentRow hide paypalRow">
                                <td class="alignLeft">Paypal</input>
                                </td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                            </tr>
                            <tr class="odd contentRow hide payoneerRow">
                                <td class="alignLeft">Payoneer</input>
                                </td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                            </tr>
                            <tr class="contentRow hide westRow">
                                <td class="alignLeft">Western Union</input>
                                </td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                            </tr>
                            <tr class="odd contentRow hide notSetRow">
                                <td class="alignLeft">Not Set</input>
                                </td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                            </tr>                            
                            <tr class="noDataRow hide"><td colspan="4"><div class="noData">No data available</div></td></tr>
                        </tbody>
                        <tfoot>
                            <tr class="totalRow hide">
                                <td class="alignLeft">Total</td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                                <td class="alignRight"></td>
                            </tr>                        
                        </tfoot>
                    </table>
                </div>
                <!-- End .containerSection -->
                <div class="corner tl"></div>
                <div class="corner tr"></div>
            </div>
        </div>
        <!-- End .memberPaymentBlockContainer -->
    </div>
    <!-- .leftColumnTwoThirdLeft -->
    <div class="leftColumnTwoThirdRight">
        <div class="memberPaymentBlockContainer memberPaymentCashOutflowBlock">
            <div class="sectionInner">
                <div class="projectTitle">
                    <h3>Potential Cash Outflow</h3>
                    <a href="javascript:;" class="icon" rel="This table shows potential cash out details data.">!</a>
                </div>
                <!-- title --><!-- container -->
                <div class="tableContainer">
                    <table border="0" cellpadding="0" cellspacing="0" id="potentialCashOutflowTable">
                        <colgroup>
                            <col width="28%"/>
                            <col width="19%"/>
                            <col width="17%"/>
                            <col width="18%"/>
                            <col width="18%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th class="alignLeft">Payment method</th>
                            <th>Now</th>
                            <th>7 days</th>
                            <th>15 days</th>
                            <th>30 days</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr class="loadRow">
                                <td colspan="5">
                                    <div class="ajaxTableLoader">
                                        <img src="/images/rss_loading.gif" alt="loading"/>
                                    </div>
                                </td>
                            </tr>
                            <tr class="noDataRow hide"><td colspan="5"><div class="noData">No data available</div></td></tr>
                        </tbody>
                        <tfoot></tfoot>
                    </table>
                </div>
                <!-- End .containerSection -->
                <div class="corner tl"></div>
                <div class="corner tr"></div>
            </div>
        </div>
        <!-- End .memberPaymentBlockContainer -->
    </div>
    <!-- .leftColumnTwoThirdRight -->
    <div class="leftColumnTwoThirdWhole">
        <div class="memberPaymentBlockContainer  memberPaymentByMethodBlock">
            <div class="sectionInner">
                <div class="projectTitle">
                    <h3>Payments by payment method</h3>
                    <a href="javascript:;" class="icon" rel="This table shows payment data for past 6 months">!</a>
                </div>
                <!-- title --><!-- container -->
                <div class="tableContainer">
                    <table border="0" cellpadding="0" cellspacing="0" id="paymentsByMethodTable">
                        <colgroup>
                            <col width="16%"/>
                            <col width="14%"/>
                            <col width="14%"/>
                            <col width="14%"/>
                            <col width="14%"/>
                            <col width="14%"/>
                            <col width="14%"/>
                        </colgroup>
                        <tbody>
                            <tr class="loadRow">
                                <td colspan="7">
                                    <div class="ajaxTableLoader">
                                        <img src="/images/rss_loading.gif" alt="loading"/>
                                    </div>
                                </td>
                            </tr>
                            <tr class="noDataRow hide"><td colspan="7"><div class="noData">No data available</div></td></tr>
                        </tbody>
                        <tfoot></tfoot>
                    </table>
                </div>
                <!-- End .containerSection -->
                <div class="corner tl"></div>
                <div class="corner tr"></div>
            </div>
        </div>
        <!-- End .memberPaymentBlockContainer -->
        <div class="memberPaymentBlockContainer  memberPaymentAverageTotalBlock">
            <div class="sectionInner">
                <div class="projectTitle">
                    <h3>Average total $ of payments</h3>
                    <a href="javascript:;" class="icon" rel="This chart shows average member payments.">!</a>
                </div>
                <!-- title --><!-- container -->
                <div class="chartsContainer">
                    <div class="zoomControl">
                        <label>Zoom :</label> 
                        <a href="javascript:;" class="chartZoomLink chartZoomByDay selected" id="chartZoomByDay">by day</a>
                        <a href="javascript:;" class="chartZoomLink chartZoomByWeek" id="chartZoomByWeek">by week</a>
                        <a href="javascript:;" class="chartZoomLink chartZoomByMonth" id="chartZoomByMonth">by month</a>
                        <select id="chartZoomByDaySelect">
                            <option value="90">90 days</option>
                            <option value="180">180 days</option>
                            <option value="365">365 days</option>
                        </select>
                        <select id="chartZoomByWeekSelect" class='hide'>
                            <option value="4">4 weeks</option>
                            <option value="26">26 weeks</option>
                            <option value="52">52 weeks</option>
                        </select>
                        <select id="chartZoomByMonthSelect" class='hide'>
                            <option value="3">3 months</option>
                            <option value="6">6 months</option>
                            <option value="12">12 months</option>
                        </select>
                        <input type="hidden" id="chartZoomByDayInput" value="day"></input>
                        <input type="hidden" id="chartZoomByWeekInput" value="week"></input>
                        <input type="hidden" id="chartZoomByMonthInput" value="month"></input>
                    </div>
                    <div id="averageTotalPayment">
                        <div class="ajaxTableLoader">
                            <img src="/images/rss_loading.gif" alt="loading"/>
                        </div>
                    </div>
                    <div class="chartLegendWrapper">
                        <ul class="fLeft">
                            <li class="chartLegend skyblueLegend">Created</li>
                            <li class="chartLegend greenLegend">Paid</li>
                        </ul>
                        <ul class="payMethodLegend fRight">
                            <li class="chartLegend blueLegend">Paypal</li>
                            <li class="chartLegend redLegend">Payoneer</li>
                            <li class="chartLegend yellowLegend lastLegend">Western Union</li>
                        </ul>
                        <label class="fRight paidByLabel">Paid By:</label>
                    </div>
                </div>
                <!-- End .containerSection -->
                <div class="corner tl"></div>
                <div class="corner tr"></div>
            </div>
        </div>
        <!-- End .memberPaymentBlockContainer -->
    </div>
    <!-- .leftColumnTwoThirdWhole -->
</div>
<!-- End .leftColumnTwoThird -->
<div class="rightColumnOneThird">
    <div class="memberPaymentBlockContainer  memberPaymentStatusAllBlock">
        <div class="sectionInner">
            <div class="projectTitle">
                <h3>Aging of all 2012+ payments</h3>
                <a href="javascript:;" class="icon" rel="This table shows payments with different status">!</a>
            </div>
            <!-- title --><!-- container -->
            <div class="tableContainer">
                <table border="0" cellpadding="0" cellspacing="0" id="statusOfPaymentsTable">
                    <colgroup>
                        <col width="20%"/>
                        <col width="20%"/>
                        <col width="20%"/>
                        <col width="20%"/>
                        <col width="20%"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>Owed + Accruing</th>
                        <th>On Hold</th>
                        <th>Paid</th>
                        <th>Entered into PS</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr class="loadRow">
                            <td colspan="5">
                                <div class="ajaxTableLoader">
                                    <img src="/images/rss_loading.gif" alt="loading"/>
                                </div>
                            </td>
                        </tr>
                        <tr class="noDataRow hide"><td colspan="5"><div class="noData">No data available</div></td></tr>
                    </tbody>
                </table>
            </div>
            <!-- End .containerSection -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
        </div>
    </div>
    <!-- End .memberPaymentBlockContainer -->
    <div class="memberPaymentBlockContainer  memberPaymentTopTenBlock">
        <div class="sectionInner">
            <div class="projectTitle">
                <h3>Top 10 Member Balances</h3>
                <a href="javascript:;" class="icon" rel="This table shows members with top payments.">!</a>
            </div>
            <!-- title --><!-- container -->
            <div class="tableContainer">
                <div class="sortControl">
                    <label>Sort by: &nbsp;&nbsp;</label>
                    <select name="sortTop10" class="sortTop10">
                        <option>Amount Hight to Low</option>
                        <option>Amount Low to High</option>
                    </select>
                </div>
                <table border="0" cellpadding="0" cellspacing="0" id="topMembeBalancesTable" class="tablesorter">
                    <colgroup>
                        <col width="6%"/>
                        <col width="24%"/>
                        <col width="26%"/>
                        <col width="44%"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>No.</th>
                        <th>Username</th>
                        <th>Amount</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr class="loadRow">
                            <td colspan="4">
                                <div class="ajaxTableLoader">
                                    <img src="/images/rss_loading.gif" alt="loading"/>
                                </div>
                            </td>
                        </tr>
                        <tr class="noDataRow hide"><td colspan="4"><div class="noData">No data available</div></td></tr>
                    </tbody>
                </table>
            </div>
            <!-- End .containerSection -->
            <div class="corner tl"></div>
            <div class="corner tr"></div>
        </div>
    </div>
    <!-- End .memberPaymentBlockContainer -->
</div>
<!-- End .rightColumn -->
<div class="clear"></div>
