<%--
  - Author: Ghost_141, GreatKevin
  - Version: 1.3
  - Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 (Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0) change notes:
  - Fix multiple bugs.
  -
  - Version 1.2 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
  - - Add loading progress image for select filters.
  -
  - Version 1.3 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
  - - Hides the date range data if the page is project health
  - 
  - Description: The filter panel of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="modalBackground"></div>
<div id="new-modal">

    <!-- filter modal -->
    <div class="outLay" id="filterModal">
        <div class="modalHeader">
            <div class="modalHeaderRight">
                <div class="modalHeaderCenter">
                    Filter
                    <a title="Close" class="closeModal" href="javascript:;">Close</a>
                </div>
            </div>
        </div>
        <!-- end .modalHeader -->

        <div class="modalBody">
            <div class="filterContainer">
                <div class="selectFilter">
                    <div class="row">
                        <div class="column">
                            <div>
                                <label>Select a Customer</label><img src="/images/dots-white.gif" class="indicator" alt/>
                                <s:select list="clients"
                                          id="clientFilter" size="1"/>
                            </div>
                        </div>
                        <div class="column">
                            <div>
                                <label>Select Project Status</label><img src="/images/dots-white.gif" class="indicator" alt/>
                                <s:select list="directProjectStatus"
                                          id="projectStatusFilter" size="1" value="defaultProjectStatus"/>
                            </div>
                        </div>
                        <div class="column">
                            <div>
                                <label>Select a Project</label><img src="/images/dots-white.gif" class="indicator" alt/>
                                <s:select list="clientProjects"
                                          id="projectFilter" size="1"/>
                            </div>
                        </div>
                    </div>
                    <div class="row lastRow">
                        <div class="column">
                            <div>
                                <label>Project Filters</label><img src="/images/dots-white.gif" class="indicator" alt/>
                                <s:select list="clientMetadataKeys"
                                          id="metaFilter" size="1"/>
                            </div>
                        </div>
                        <div class="column">
                            <div>
                                <label>Project Filter Values</label><img src="/images/dots-white.gif" class="indicator" alt/>
                                <select id="metaValueFilter"><option>None</option></select>
                            </div>
                        </div>
                        <div class="column" <c:if test="${requestScope.CURRENT_SIDEBAR eq 'health'}">style="display: none"</c:if>>
                            <div id="zoomSelect">
                                <span class="label">Zoom</span><span class="validationMessage" style="padding-left: 10px; color: red; display: none">Please select date range</span>

                                <ul>
                                    <li class="currentMonth"><a href="javascript:;"><span>Current Month</span></a></li>
                                    <li class="threeMonths"><a href="javascript:;"><span>3 Months</span></a></li>
                                    <li class="sixMonths"><a href="javascript:;"><span>6 Months</span></a></li>
                                    <li class="oneYear"><a href="javascript:;"><span>1 Year</span></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="selectDate <c:if test="${requestScope.CURRENT_SIDEBAR eq 'health'}">hide</c:if>">
                    <a href="javascript:;" class="prevYear">PREV YEAR</a>
                    <div class="monthList">
                        <div class="timeLine">
                            <ul>
                                <s:iterator value="monthOptions">
                                    <li><a href="javascript:;"><span><span><s:property/></span></span></a></li>
                                </s:iterator>
                            </ul>
                        </div>
                    </div>
                    <a href="javascript:;" class="nextYear">NEXT YEAR</a>
                </div>
            </div>

            <div class="modalCommandBox">
                <a class="redButton" id="filterButton" href="javascript:;"><span class="left"><span class="right">FILTER</span></span></a>
                <a class="redButton closeModal" href="javascript:;"><span class="left"><span class="right">CANCEL</span></span></a>
                <div class="clear"></div>
            </div>
        </div>
        <!-- end .modalBody -->

        <div class="modalFooter">
            <div class="modalFooterRight">
                <div class="modalFooterCenter"></div>
            </div>
        </div>
        <!-- end .modalFooter -->
    </div>
    <!-- End #filterModal -->

</div>
