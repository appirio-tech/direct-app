<%--
  - Author: GreatKevin
  - Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
  - Version 1.3
  -
  - Version 1.1 (Release Assembly - TC Direct Cockpit Release Two) changes:
  - - Add the milestone prize for software contest receipt if exists
  -
  - Version 1.2 (Release Assembly - TC Direct Cockpit Release Five) changes:
  - - Add Launch By information to the JSP page
  -
  - Version 1.3 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
  - - Show the receipt details in table
  -
  - Description: This page fragment renders receipt page content for software contest.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<div class="receipt" id="launchContestOut">

    <s:if test="contestReceipt.showReceipt">
                                            <div class="orderReview">
                                            <s:set id="firstPrize" name="firstPrize" 
                                                value="contestReceipt.firstPlacePrize" scope="page"/>
                                            <s:set id="secondPrize" name="secondPrize" 
                                                value="contestReceipt.secondPlacePrize" scope="page"/>
                                            <s:set id="thirdPrize" name="thirdPrize" 
                                                value="contestReceipt.thirdPlacePrize" scope="page"/>
                                            <s:set id="fourthPrize" name="fourthPrize" 
                                                value="contestReceipt.fourthPlacePrize" scope="page"/>
                                            <s:set id="fifthPrize" name="fifthPrize" 
                                                value="contestReceipt.fifthPlacePrize" scope="page"/>
                                            <s:set id="milestonePrize" name="milestonePrize"
                                                value="contestReceipt.milestonePrize" scope="page"/>
                                            <s:set id="drPoints" name="drPoints" 
                                                value="contestReceipt.drPoints" scope="page"/>
                                            <s:set id="reliabilityBonusCost" name="reliabilityBonusCost" 
                                                value="contestReceipt.reliabilityBonus" scope="page"/>
                                            <s:set id="contestFee" name="contestFee" 
                                                value="contestReceipt.contestFee" scope="page"/>
                                            <s:set id="specificationReviewPayment" name="specificationReviewPayment" 
                                                value="contestReceipt.specReviewCost" scope="page"/>
                                            <s:set id="reviewPayment" name="reviewPayment" 
                                                value="contestReceipt.reviewCost" scope="page"/>
                                            <s:set id="copilotCost" name="copilotCost" 
                                                value="contestReceipt.copilotCost" scope="page"/>
                                            <s:set id="bugRacePayment" name="bugRacePayment"
                                                value="contestReceipt.bugFixCost"/>
                                            <s:set id="totalPayment" name="totalPayment">
                                            ${firstPrize + secondPrize + thirdPrize + fourthPrize + fifthPrize + milestonePrize + drPoints + reliabilityBonusCost
                                            + contestFee + specificationReviewPayment + reviewPayment + copilotCost + bugRacePayment}
                                            </s:set>
                                            <s:set id="otherPayment" name="otherPayment" value="0" />
                                            <s:if test="contestReceipt.finished == true && contestReceipt.totalCost > #attr['totalPayment']">
                                                <s:set id="otherPayment" name="otherPayment">
                                                ${contestReceipt.totalCost - totalPayment}
                                                </s:set>
                                            </s:if>
                                            
                                            <s:if test="viewData.contestStats.isStudio == true">
                                                <h2 class="sectionHead"><s:if test="contestReceipt.finished == true">Receipt</s:if><s:else>Estimated Cost</s:else></h2>
                                                <div class="overviewBox">
                                                    <table cellspacing="0" cellpadding="0" class="overviewData">
                                                        <tbody><tr>
                                                            <th>Date :</th>
                                                            <td><fmt:formatDate value="<%= new java.util.Date()%>"
                                                                                        pattern="MM/dd/yyyy HH:mm zzz"/></td>
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
                                                            <td><fmt:formatDate value="${viewData.contestStats.startTime}" pattern="MM/dd/yyyy HH:mm zzz-05"/></td>
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
                                                    </tbody></table>
                                                </div>

                                            </div>
                                            <!-- end .orderReview -->
                                                <div class="contestDetails" id="participationMetricsReportsSection">
                                                
                                                    <h2 class="sectionHead">Contest Details</h2>

                                                    <table class="pipelineStats">
                                                        <thead>
                                                        <tr class="subTheadRow">
                                                            <th class="tableColumn">
                                                                Payment Type
                                                            </th>
                                                            <th class="tableColumn">
                                                                Amount
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                            <s:if test="contestReceipt.firstPlacePrize > 0">
                                                                    <tr>
                                                                        <td>
                                                                            First Place Prize
                                                                        </td>
                                                                        <td>
                                                                            $${firstPrize}
                                                                        </td>
                                                                    </tr>
                                                            </s:if>
                                                            <s:if test="contestReceipt.secondPlacePrize > 0">
                                                                <tr>
                                                                    <td>
                                                                        Second Place Prize
                                                                    </td>
                                                                    <td>
                                                                        $${secondPrize}
                                                                    </td>
                                                                </tr>
                                                            </s:if>
                                                            <s:if test="contestReceipt.thirdPlacePrize > 0">
                                                                <tr>
                                                                    <td>
                                                                        Third Place Prize
                                                                    </td>
                                                                    <td>
                                                                        $${thirdPrize}
                                                                    </td>
                                                                </tr>
                                                            </s:if>
                                                            <s:if test="contestReceipt.fourthPlacePrize > 0">
                                                                <tr>
                                                                    <td>
                                                                        Fourth Place Prize
                                                                    </td>
                                                                    <td>
                                                                        $${fourthPrize}
                                                                    </td>
                                                                </tr>
                                                            </s:if>
                                                            <s:if test="contestReceipt.fifthPlacePrize > 0">
                                                                <tr>
                                                                    <td>
                                                                        Fifth Place Prize
                                                                    </td>
                                                                    <td>
                                                                        $${fifthPrize}
                                                                    </td>
                                                                </tr>
                                                            </s:if>

                                                            <tr>
                                                                <td>
                                                                    Studio Cup points
                                                                </td>
                                                                <td>
                                                                    $${drPoints}
                                                                </td>
                                                            </tr>
                                                        <s:if test="contestReceipt.milestonePrize > 0">
                                                                    <tr>
                                                                        <td>
                                                                            Milestone Prize
                                                                        </td>
                                                                        <td>
                                                                            $${milestonePrize}
                                                                        </td>
                                                                    </tr>
                                                        </s:if>
                                                        <tr>
                                                            <td>
                                                                Administration Fee
                                                            </td>
                                                            <td>
                                                                $${contestFee}
                                                            </td>
                                                        </tr>

                                                        <tr>
                                                            <td>
                                                                Specification Review
                                                            </td>
                                                            <td>
                                                                $${specificationReviewPayment}
                                                            </td>
                                                        </tr>

                                                        <tr>
                                                            <td>
                                                                Review
                                                            </td>
                                                            <td>
                                                                $${reviewPayment}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Copilot Fee
                                                            </td>
                                                            <td>
                                                                $${copilotCost}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Bug Race
                                                            </td>
                                                            <td>
                                                                $${bugRacePayment}
                                                            </td>
                                                        </tr>
                                                        <s:if test="contestReceipt.finished == true && #attr['otherPayment'] > 0">
                                                            <tr>
                                                                <td>
                                                                    Other
                                                                </td>
                                                                <td>
                                                                    $${otherPayment}
                                                                </td>
                                                            </tr>
                                                        </s:if>
                                                        <tr>
                                                            <td>
                                                                <b>Total</b>
                                                            </td>
                                                            <td>
                                                                <b>
                                                                    <s:if test="contestReceipt.finished == true">
                                                                        $${contestReceipt.totalCost}
                                                                    </s:if>
                                                                    <s:else>
                                                                        $${totalPayment}
                                                                    </s:else>
                                                                </b>
                                                            </td>
                                                        </tr>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </s:if>
                                            <s:if test="viewData.contestStats.isStudio == false">
                                                <h2 class="sectionHead"><s:if test="contestReceipt.finished == true">Receipt</s:if><s:else>Estimated Cost</s:else></h2>
                                                 <!-- overviewBox -->
                                                <div class="overviewBox">
                                                    <table cellspacing="0" cellpadding="0" class="overviewData">
                                                        <tbody><tr>
                                                            <th>Date :</th>
                                                            <td><fmt:formatDate value="<%= new java.util.Date()%>"
                                                                                        pattern="MM/dd/yyyy HH:mm zzz"/></td>
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
                                                            <td><fmt:formatDate value="${viewData.contestStats.startTime}"
                                                                                        pattern="MM/dd/yyyy HH:mm zzz"/></td>
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
                                                    </tbody></table>
                                                </div>
                                                <!-- end .overviewBox -->
                                                
                                            </div>
                                            <!-- end .orderReview -->
                                                <div class="contestDetails" id="participationMetricsReportsSection" cellspacing="0" cellpadding="0">


                                                    <h2 class="sectionHead">Contest Details</h2>
                                                    <table class="pipelineStats">
                                                        <thead>
                                                        <tr class="subTheadRow">
                                                            <th class="tableColumn">
                                                                Payment Type
                                                            </th>
                                                            <th class="tableColumn">
                                                                Amount
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <tr>
                                                            <td>
                                                                First Place Cost
                                                            </td>
                                                            <td>
                                                                $${firstPrize}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Second Place Cost
                                                            </td>
                                                            <td>
                                                                $${secondPrize}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                DR points
                                                            </td>
                                                            <td>
                                                                $${drPoints}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Reliability Bonus Cost
                                                            </td>
                                                            <td>
                                                                $${reliabilityBonusCost}
                                                            </td>
                                                        </tr>
                                                        <s:if test="contestReceipt.milestonePrize > 0">

                                                            <s:set id="milestoneSinglePrize" name="milestoneSinglePrize"
                                                                   value="(contestReceipt.milestonePrize / contestReceipt.milestonePrizeNumber)"
                                                                   scope="page"/>

                                                            <c:forEach var="currentIndex" begin="1" end="${contestReceipt.milestonePrizeNumber}">

                                                                <tr>
                                                                    <td>
                                                                        Milestone Prize {currentIndex}
                                                                    </td>
                                                                    <td>
                                                                        $${milestonePrize}
                                                                    </td>
                                                                </tr>

                                                            </c:forEach>

                                                        </s:if>
                                                        <tr>
                                                            <td>
                                                                Contest Fee
                                                            </td>
                                                            <td>
                                                                $<fmt:formatNumber value="${contestFee}" pattern="0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Specification Review
                                                            </td>
                                                            <td>
                                                                $${specificationReviewPayment}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Review
                                                            </td>
                                                            <td>
                                                                $${reviewPayment}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Copilot Fee
                                                            </td>
                                                            <td>
                                                                $${copilotCost}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Bug Race
                                                            </td>
                                                            <td>
                                                                $${bugRacePayment}
                                                            </td>
                                                        </tr>
                                                        <s:if test="contestReceipt.finished == true && #attr['otherPayment'] > 0">
                                                            <tr>
                                                                <td>
                                                                    Other
                                                                </td>
                                                                <td>
                                                                    $${otherPayment}
                                                                </td>
                                                            </tr>
                                                        </s:if>
                                                        <tr>
                                                            <td>
                                                                <b>Total</b>
                                                            </td>
                                                            <td>
                                                                <b>
                                                                    <s:if test="contestReceipt.finished == true">
                                                                        $${contestReceipt.totalCost}
                                                                    </s:if>
                                                                    <s:else>
                                                                        $${totalPayment}
                                                                    </s:else>
                                                                </b>
                                                            </td>
                                                        </tr>

                                                        </tbody>
                                                    </table>
                                                    <%--<h3>Contest Prizes:</h3>--%>
                                                    <%--<table class="prizesTable">--%>
                                                        <%--<tbody><tr>--%>
                                                            <%--<td>First Place Cost : $${firstPrize}</td>--%>
                                                            <%--<td>Second Place Cost : $${secondPrize}</td>--%>
                                                            <%--<td>DR points : $${drPoints}</td>--%>
                                                            <%--<td>Reliability Bonus Cost : $${reliabilityBonusCost}</td>--%>
                                                            <%--<td class="last">$<fmt:formatNumber value="${firstPrize + secondPrize + drPoints + reliabilityBonusCost}" pattern="0.00"/></td>--%>
                                                        <%--</tr>--%>
                                                    <%--</tbody></table>--%>
                                                    <%--<s:if test="contestReceipt.milestonePrize > 0">--%>
                                                        <%--<h3>Milestone Prizes:</h3>--%>
                                                        <%--<table class="prizesTable">--%>
                                                            <%--<tbody>--%>
                                                            <%--<tr>--%>
                                                                <%--<s:set id="milestoneSinglePrize" name="milestoneSinglePrize"--%>
                                                                       <%--value="(contestReceipt.milestonePrize / contestReceipt.milestonePrizeNumber)"--%>
                                                                       <%--scope="page"/>--%>

                                                                <%--<c:forEach var="currentIndex" begin="1" end="${contestReceipt.milestonePrizeNumber}">--%>

                                                                    <%--<td>${currentIndex}:${milestoneSinglePrize}</td>--%>

                                                                <%--</c:forEach>--%>


                                                                <%--<td class="last">$${milestonePrize}</td>--%>
                                                            <%--</tr>--%>
                                                            <%--</tbody>--%>
                                                        <%--</table>--%>
                                                    <%--</s:if>--%>
                                                    <%--<h3>Additional Costs:</h3>--%>
                                                    <%--<table class="prizesTable">--%>
                                                        <%--<tbody><tr>--%>
                                                            <%--<td>Contest Fee : $<fmt:formatNumber value="${contestFee}" pattern="0.00"/> </td>--%>
                                                            <%--<td>Specification Review : $${specificationReviewPayment}</td>--%>
                                                            <%--<td>Review  : $${reviewPayment}</td>--%>
                                                            <%--<td>Copilot Fee  : $${copilotCost}</td>--%>
                                                            <%--<td>Bug Race : $${bugRacePayment}</td>--%>
                                                            <%--<s:if test="contestReceipt.finished == true && #attr['otherPayment'] > 0">--%>
                                                            <%--<td>Other : $${otherPayment}</td>--%>
                                                            <%--</s:if>--%>
                                                            <%--<td class="last">$  --%>
                                                            <%--<fmt:formatNumber value="${(contestFee + specificationReviewPayment + reviewPayment + copilotCost + bugRacePayment + otherPayment)}" pattern="0.00"/>--%>
                                                            <%--</td>--%>
                                                        <%--</tr>--%>
                                                    <%--</tbody></table>--%>
                                                    <%--<table class="total">--%>
                                                        <%--<tbody><tr>--%>
                                                            <%--<td class="toLeft">Total:</td>--%>
                                                            <%--<td class="toRight">--%>
                                                            <%--<s:if test="contestReceipt.finished == true">--%>
                                                            <%--$${contestReceipt.totalCost}--%>
                                                            <%--</s:if>--%>
                                                            <%--<s:else>--%>
                                                            <%--$${totalPayment}--%>
                                                            <%--</s:else>--%>
                                                            <%--</td>--%>
                                                        <%--</tr>--%>
                                                    <%--</tbody></table>                                                    --%>
                                                </div>
                                            </s:if>                                         <!-- end .contestDetails -->
                                            <hr class="dualDivider">
                                            <br><br>
                                        </div>
    </s:if>
    <s:else>
        <div class="noReceipt">
            <div>
                The receipt will be available after the contest is finished
            </div>
    </s:else>
