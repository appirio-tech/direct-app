<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<div class="receipt" id="launchContestOut">
											<div class="orderReview">
											
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
											<!-- end .contestDetails -->
											<hr class="dualDivider">
											<br><br>
										</div>