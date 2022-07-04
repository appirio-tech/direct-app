<%--
 - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 - 
 - The results detail page for marathon match contest. This page will show all system test case for this marathon match
 - contest's competitors.
 -
 - author: Ghost_141
 - Version: 1.0
 - Since: 1.0 (Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="resultDetails">
    <h4>System Test Results</h4>
    <a href="<s:url action="mmResults" namespace="/contest">
        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
    </s:url> " class="backToList">&lt;&lt; Back To List</a>

    <div class="resultTable">
        <!-- End .handleTable -->
        <div class="resultDetailsTable">
            <div class="resultTableInner">
                <div class="systemTestTableContainer">
                    <c:if test="${viewData.codersStartNumber ne 1}">
                    <a class="nextGroupUsers" href="<s:url action="mmResults" namespace="/contest">
                        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                        <s:param name="type">system</s:param>
                        <s:param name="sr" value="viewData.codersStartNumber - 40 > 1 ? viewData.codersStartNumber - 40 : 1"/>
                        <s:param name="stc" value="viewData.testCasesStartNumber"/>
                        </s:url> "><img src="/images/btn_tableScroll_U.gif" alt="UP" border="0">
                    </a>
                    </c:if>
                    <div class="systemTestTable">
                        <c:if test="${viewData.testCasesStartNumber ne 1}">
                        <div class="scrollLeftWrapper">
                            <div class="emptyBlock"></div>
                            <a href="<s:url action="mmResults" namespace="/contest">
                                <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                <s:param name="type">system</s:param>
                                <s:param name="sr" value="viewData.codersStartNumber"/>
                                <s:param name="stc" value="viewData.testCasesStartNumber - 10 > 1 ? viewData.testCasesStartNumber - 10 : 1"/>
                                </s:url> " class="bcLink"><img src="/images/btn_tableScroll_L.gif" alt="LEFT" border="0">
                            </a>
                        </div>
                        </c:if>

                        <table border="0" cellpadding="0" cellspacing="0" class="mmSystemTestsTable">
                            <colgroup>
                                <col width="143"/>
                                <col width="64" />
                                <c:forEach begin="${viewData.testCasesStartNumber}" end="${viewData.testCasesEndNumber}" varStatus="status">
                                    <col/>
                                </c:forEach>
                            </colgroup>
                            <thead>
                            <tr>
                                <th>Handle</th>
                                <th>Score</th>
                                <c:forEach begin="${viewData.testCasesStartNumber}" end="${viewData.testCasesEndNumber}" varStatus="status">
                                    <th class="systemTestCase">Test Case ${status.count + viewData.testCasesStartNumber - 1}</th>
                                </c:forEach>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="viewData.competitorsTestCases">
                                <tr>
                                    <td><tc-webtag:handle coderId="${userId}" handle="${handle}" context="marathon_match" darkBG="false"/></td>
                                    <td><a href="<s:url action="mmRegistrants" namespace="/contest">
                                                <s:param name="projectId" value="viewData.contestStats.contest.id"></s:param>
                                                <s:param name="tab">competitors</s:param>
                                                <s:param name="handle" value="handle"/>
                                                </s:url>">${finalScore}</a></td>
                                    <s:iterator value="testCases">
                                        <td><fmt:formatNumber value="${testCaseScore}" pattern="####.####"/></td>
                                    </s:iterator>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>

                        <c:if test="${viewData.testCasesEndNumber ne viewData.testCasesCount}">
                        <div class="scrollRightWrapper">
                            <div class="emptyBlock"></div>
                            <a href="<s:url action="mmResults" namespace="/contest">
                                <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                                <s:param name="type">system</s:param>
                                <s:param name="sr" value="viewData.codersStartNumber"/>
                                <s:param name="stc" value="viewData.testCasesEndNumber + 1"/>
                                </s:url> "><img src="/images/btn_tableScroll_R.gif" alt="RIGHT" border="0"></a>
                        </div>
                        </c:if>
                    </div>
                    <c:if test="${viewData.codersEndNumber ne viewData.codersCount}">
                    <a class="nextGroupUsers" href="<s:url action="mmResults" namespace="/contest">
                        <s:param name="projectId" value="viewData.contestStats.contest.id"/>
                        <s:param name="type">system</s:param>
                        <s:param name="sr" value="viewData.codersEndNumber + 1"/>
                        <s:param name="stc" value="viewData.testCasesStartNumber"/>
                        </s:url> "><img src="/images/btn_tableScroll_D.gif" alt="Down" border="0">
                    </a>
                    </c:if>

                </div>
            </div>
        </div>
        <div class="clearFix"></div>
    </div>

</div>