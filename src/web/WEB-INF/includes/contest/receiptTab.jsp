<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<div class="receipt" id="launchContestOut">
											<div class="orderReview">
											
											<s:if test="viewData.contestStats.isStudio == true">
												<span class="paymentNumBox">No. 10-000001</span>
												<h2 class="sectionHead">Receipt</h2>
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
															<td>${viewData.contest.contestType.name}</td>
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
													</tbody></table>
												</div>
												<!-- end .overviewBox -->
												
											</div>
											<!-- end .orderReview -->
												<div class="contestDetails">
												
													<h2 class="sectionHead">Contest Details</h2>
													<h3>Contest Prizes:</h3>
													<table class="prizesTable">
														<tbody><tr>
															<s:set name="total" value="0" scope="page" />
															<s:iterator value="viewData.contestStats.prizes"
																			status="status">
																	<s:set value="place" var="place" scope="page"/>
																	<s:set value="amount" var="amount" scope="page"/>
															<td>${place} : $${amount}</td>
															</s:iterator>
															<td>Studio Cup points : ${(viewData.contestStats.totalFees  - viewData.contestStats.adminFees) * 0.25}</td>;
															<td class="last">$${viewData.contestStats.totalMainPrizes}</td>
														</tr>
													</tbody></table>
													
													<s:if test="viewData.contestStats.milestonePrizes.amount != 0">
													<h3>Milestone Prizes</h3>
													<table class="prizesTable">
														<tbody><tr>
														<s:set id="amount" name="amount" value="viewData.contestStats.milestonePrizes.amount" scope="page"/>
														<s:set id="numberOfSubmissions" var="numberOfSubmissions" value="viewData.contestStats.milestonePrizes.numberOfSubmissions" scope="page"/>
														<c:forEach begin="1" end="${numberOfSubmissions}" var="cur">
														  <td>${cur} : ${amount}</td>
														</c:forEach>
														<td class="last">$${amount*numberOfSubmissions}</td>
														</tr>
													</tbody></table>
													</s:if>
													<h3>Milestone Prizes</h3>
													<table class="prizesTable">
														<tbody><tr>
															<td class="large">Administration Fee : $<s:property value="viewData.contestStats.adminFees"/> </td>
															<td class="last">$<s:property value="viewData.contestStats.adminFees"/></td>
														</tr>
													</tbody></table>
													
													<table class="total">
														<tbody><tr>
															<td class="toLeft">Total:</td>
															<td class="toRight">$${viewData.contestStats.totalFees}</td>
															
														</tr>
													</tbody></table>
													
												</div>
											</s:if>
											<s:if test="viewData.contestStats.isStudio == false">
												<span class="paymentNumBox">No. 10-000001</span>
												<h2 class="sectionHead">Receipt</h2>
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
													</tbody></table>
												</div>
												<!-- end .overviewBox -->
												
											</div>
											<!-- end .orderReview -->
												<div class="contestDetails">
												
													<h2 class="sectionHead">Contest Details</h2>
													<h3>Contest Prizes:</h3>
													<table class="prizesTable">
														<tbody><tr>
															<s:set id="firstPrize" name="firstPrize" 
																value="result.projectHeader.properties['First Place Cost']" scope="page"/>
															<s:set id="secondPrize" name="secondPrize" 
																value="result.projectHeader.properties['Second Place Cost']" scope="page"/>
															<s:set id="drPoints" name="drPoints" 
																value="result.projectHeader.properties['DR points']" scope="page"/>
															<s:set id="reliabilityBonusCost" name="reliabilityBonusCost" 
																value="result.projectHeader.properties['Reliability Bonus Cost']" scope="page"/>
															<td>First Place Cost : $${firstPrize}</td>
															<td>Second Place Cost : $${secondPrize}</td>
															<td>DR points : $${drPoints}</td>
															<td>Reliability Bonus Cost : $${reliabilityBonusCost}</td>
															<td class="last">$${firstPrize + secondPrize + drPoints + reliabilityBonusCost}</td>
														</tr>
													</tbody></table>
													<h3>Additional Costs:</h3>
													<table class="prizesTable">
														<tbody><tr>
															<s:set id="contestFee" name="contestFee" 
																value="result.projectHeader.properties['Admin Fee']" scope="page"/>
															<s:set id="specificationReviewPayment" name="specificationReviewPayment" 
																value="result.projectHeader.properties['Spec Review Cost']" scope="page"/>
															<s:set id="reviewPayment" name="reviewPayment" 
																value="result.projectHeader.properties['Review Cost']" scope="page"/>
															<td>Contest Fee : $${contestFee}</td>
															<td>Specification Review : $${specificationReviewPayment}</td>
															<td>Review  : $${reviewPayment}</td>
															<td>Copilot Fee  : $${copilotCost}</td>
															<td class="last">$${contestFee + specificationReviewPayment + reviewPayment + copilotCost}</td>
														</tr>
													</tbody></table>
													<table class="total">
														<tbody><tr>
															<td class="toLeft">Total:</td>
															<td class="toRight">$${firstPrize + secondPrize + drPoints + reliabilityBonusCost
															+ contestFee + specificationReviewPayment + reviewPayment + copilotCost}</td>
															
														</tr>
													</tbody></table>													
												</div>
											</s:if>											<!-- end .contestDetails -->
											<hr class="dualDivider">
											<br><br>
										</div>
