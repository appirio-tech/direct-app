<%--
  - Author: GreatKevin, Veve
  - Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
  - Version 1.5
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Two) changes:
  - - Add the checkpoint prize for software contest receipt if exists
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Five) changes:
  - - Add Launch By information to the JSP page
  -
  - Version 1.3 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
  - - Show the receipt details in table
  -
  - Version 1.4 (TopCoder Direct - Add Estimation Cost Details to Receipt page )
  - - Add estimated cost table
  -
  - Version 1.5 ([Direct] - challenge receipt page update)
  - - Merge the estimated cost and actual cost into one table
  -
  - Description: This page fragment renders receipt page content for software contest.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<div class="receipt" id="launchContestOut">

<s:if test="contestReceipt.showReceipt">
    <div class="orderReview">

        <h2 class="sectionHead">Receipt</h2>

        <!-- overviewBox -->
        <div class="overviewBox">
            <table cellspacing="0" cellpadding="0" class="overviewData">
                <tbody>
                <tr>
                    <th>Date :</th>
                    <td><fmt:formatDate value="<%= new java.util.Date()%>"
                                        pattern="${defaultDateTimeFormat}" timeZone="${defaultTimeZone}"/></td>
                </tr>
                <tr>
                    <th>Competition Type :</th>
                    <td>${result.projectHeader.projectCategory.name}</td>
                </tr>
                <tr>
                    <th>Competition Title :</th>
                    <td><s:property value="viewData.contestStats.contest.title"/></td>
                </tr>
                <tr>
                    <th>Project :</th>
                    <td><s:property value="viewData.contestStats.contest.project.name"/></td>
                </tr>
                <tr>
                    <th>Payment Method :</th>
                    <td>${viewData.contestStats.paymentReferenceId}</td>
                </tr>
                <tr>
                    <th>Launch Time :</th>
                    <td><fmt:formatDate value="${viewData.contestStats.startTime}" pattern="${defaultDateTimeFormat}"
                                        timeZone="${defaultTimeZone}"/></td>
                </tr>
                <tr>
                    <th>Launch By :</th>
                    <td>
                        <s:if test="contestReceipt.contestLauncherId > 0">
                            <link:user userId="${contestReceipt.contestLauncherId}"/>
                        </s:if>
                        <s:else>
                            n/a
                        </s:else>

                    </td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
    <!-- end .orderReview -->
    <div class="contestDetails" id="participationMetricsReportsSection">

        <h2 class="sectionHead">Details</h2>

        <table class="pipelineStats">
            <thead>
            <tr class="subTheadRow">
                <th class="tableColumn">
                    Payment Item
                </th>
                <th class="tableColumn">
                    Estimated
                </th>
                <th class="tableColumn">
                    Actual
                </th>
            </tr>
            </thead>
            <tbody>

            <s:set var="aggregatedMap" value="contestReceipt.nonWinnerEntries"/>


            <!-- winners -->
            <s:iterator value="contestReceipt.winnerEntries">
                <tr>
                    <td>
                            ${paymentType}
                    </td>
                    <td>
                        $${estimatedAmount}
                    </td>
                    <td>
                        $${paymentAmount}
                    </td>
                </tr>
            </s:iterator>


            <s:iterator value="contestReceipt.checkpointWinnerEntries">
                <tr>
                    <td>
                            ${paymentType}
                    </td>
                    <td>
                        $${estimatedAmount}
                    </td>
                    <td>
                        $${paymentAmount}
                    </td>
                </tr>
            </s:iterator>

            <tr>
                <td>
                    Review
                </td>
                <td>
                    $${contestReceipt.estimation.reviewCost + contestReceipt.estimation.specReviewCost}
                </td>
                <td>
                    $${(aggregatedMap['Review'] == null ? 0 : aggregatedMap['Review'].paymentAmount) + (aggregatedMap['Spec Review'] == null ? 0 : aggregatedMap['Spec Review'].paymentAmount)}
                </td>
            </tr>

            <tr>
                <td>
                    Copilot
                </td>
                <td>
                    $${contestReceipt.estimation.copilotCost}
                </td>
                <td>
                    $${aggregatedMap['Copilot'] == null ? 0 : aggregatedMap['Copilot'].paymentAmount}
                </td>
            </tr>

            <tr>
                <td>
                    Reliability Bonus
                </td>
                <td>
                    $${contestReceipt.estimation.reliabilityBonus}
                </td>
                <td>
                    $${aggregatedMap['Reliability'] == null ? 0 : aggregatedMap['Reliability'].paymentAmount}
                </td>
            </tr>

            <tr>
                <td>
                    <s:if test="viewData.contestStats.isStudio == false">
                        DR Points
                    </s:if>
                    <s:else>
                        Studio Cup Points
                    </s:else>
                </td>
                <td>
                    $${contestReceipt.estimation.drPoints}
                </td>
                <td>
                    $${aggregatedMap['DR points'] == null ? 0 : aggregatedMap['DR points'].paymentAmount}
                </td>
            </tr>

            <tr>
                <td>
                    Admin Fee
                </td>
                <td>
                    $${contestReceipt.estimation.adminFee}
                </td>
                <td>
                    $${aggregatedMap['Contest Fee'] == null ? 0 : aggregatedMap['Contest Fee'].paymentAmount}
                </td>
            </tr>

            <s:iterator value="#aggregatedMap">
                <s:if test="key != 'Winner' && key != 'Checkpoint Winner' && key != 'Review' && key != 'Spec Review' && key != 'Copilot' && key != 'Reliability' && key != 'DR points' && key != 'Contest Fee'">
                    <tr>
                        <td>
                            <s:property value="key"/>
                        </td>
                        <td>
                            $0
                        </td>
                        <td>
                            $<s:property value="value.paymentAmount"/>
                        </td>
                    </tr>
                </s:if>
            </s:iterator>

            <tr>
                <td>
                    <b>Total</b>
                </td>
                <td>
                    <b>
                        $${contestReceipt.estimation.total}
                    </b>
                </td>
                <td>
                    <b>
                        $${contestReceipt.totalCost}
                    </b>
                </td>
            </tr>

            </tbody>
        </table>


        <hr class="dualDivider">
        <br><br>
    </div>
</s:if>
<s:else>
    <div class="noReceipt">
        <div>
            The receipt will be available after the challenge is finished
        </div>
    </div>
    </div>
</s:else>
