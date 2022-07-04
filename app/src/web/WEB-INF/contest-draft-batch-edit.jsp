<%--
  - Author: TCSASSEMBLER
  -
  - The jsp for the project contests batch edit.
  -
  - Version 1.0 (Module Assembly - TC Cockpit Project Contests Batch Edit ) changes:
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="includes/htmlhead.jsp"/>
    <ui:projectPageType tab="gameplan"/>
    <link rel="stylesheet" href="/css/direct/contests-batch.css" media="all" type="text/css" />
    <script type="text/javascript" src="/scripts/contests-batch.js"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content" style="position: relative;">
                            <div class="currentPage">
                                <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
                                <strong><s:property value="sessionData.currentProjectContext.name"/></strong>
                                <span id="currentDirectProjectID" class="hide"><s:property value="formData.projectId"/></span>
                            </div>

                            <h2 class="batchTitle">Batch Edit Draft Challenges</h2>

                            <div class="buttonBar">
                                <a class="newButton1 defaultBtn cancel" href="<s:url action="projectDetails" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
                                <a class="newButton1 defaultBtn saveAll" href="javascript:;"><span class="btnR"><span class="btnC">SAVE ALL</span></span></a>
                                <a class="remove" href="javascript:;"><span class="btnR"><span class="btnC">Remove</span></span></a>
                            </div>
                            <span class="note">You can assign same value for Challenge Type, Billing Account, Start Date or remove multiple draft challenges by using check boxes.</span>

                            <table id="contestTable" cellpadding="0" cellspacing="0" class="editTable">
                            <colgroup>
                                <col width="25px"/>
                                <col width="370px"/>
                                <col width="180px"/>
                                <col width="180px"/>
                                <col width="125px"/>
                                <col width="100px"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <td><input type="checkbox" class="all"/></td>
                                <td>Challenge Name</td>
                                <td>Challenge Type</td>
                                <td>Billing Account</td>
                                <td>Start Date</td>
                                <td>End Date</td>
                            </tr>
                            </thead>
                            <tbody>

                                <s:iterator value="viewData.draftContests">

                                    <tr>
                                        <td><input type="checkbox"/></td>
                                        <td>
                                            <input name="contestName" type="text" value='<s:property value="contest.title"/>' autocomplete="off"/>
                                            <input name="contestId" type="hidden" value='<s:property value="contest.id"/>'/>
                                            <span class="validation">This field cannot be empty</span>
                                        </td>
                                        <td>
                                            <s:if test="isStudio">
                                                <s:select list="%{#{}}" name="contestType" value="contestType.id">
                                                    <s:optgroup label="Design"
                                                                list="viewData.studioContestTypes" />
                                                </s:select>
                                            </s:if>
                                            <s:else>
                                                <s:select list="%{#{}}" name="contestType" value="contestType.id">
                                                    <s:optgroup label="Development"
                                                                list="viewData.softwareContestTypes" />
                                                </s:select>
                                            </s:else>
                                            <span class="validation">This field cannot be empty</span>
                                        </td>
                                        <td>
                                            <select name="billingAccount">
                                                <option value="0" <s:if test='contest.BillingAccountId == 0'>selected="selected"</s:if> <s:if test='contest.BillingAccountId == 0'>class="enabled"</s:if>>None</option>
                                                <s:iterator value="billingProjects">
                                                    <option value="<s:property value='projectId'/>" <s:if test='projectId == contest.BillingAccountId'>selected="selected"</s:if> <s:if test='projectId == contest.BillingAccountId'>class="enabled"</s:if>>
                                                        <s:property value='name'/>
                                                    </option>
                                                </s:iterator>
                                            </select>
                                        </td>
                                        <td>
                                            <div class="dateContainer">
                                                <input name="startDate" readonly="readonly" type="text" class="date-pick text" value='<s:date name="startTime" format="MM/dd/yyyy" />'/>
                                            </div>
                                            <span class="validation">This field cannot be empty</span>
                                        </td>
                                        <td>
                                            <span class="endDate"><s:date name="endTime" format="MM/dd/yyyy" /></span>
                                        </td>
                                    </tr>

                                </s:iterator>

                            </tbody>
                            </table>

                            <div class="buttonBar bottom">
                                <div class="postpone">
                                    <span class="text">Postpone the checked challenges by</span><input type="text" name="days" class="days"/> <span class="text">Days</span>
                                    <a class="postponeBtn" href="javascript:;"><span class="btnR"><span class="btnC">Postpone</span></span></a>
                                    <br/><span class="warning rowsEmpty">Please select one or more challenges</span>
                                    <span class="warning daysEmpty">This field cannot be empty</span>
                                </div>
                                <a class="newButton1 defaultBtn cancel" href="<s:url action="projectDetails" namespace="/"> <s:param name="formData.projectId" value="formData.projectId" /></s:url>"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
                                <a class="newButton1 defaultBtn saveAll" href="javascript:;"><span class="btnR"><span class="btnC">SAVE ALL</span></span></a>
                                <a class="remove" href="javascript:;"><span class="btnR"><span class="btnC">Remove</span></span></a>
                            </div>


                            <!-- End .container2 -->
                        </div>
                    </div>
                </div>

                <div class="clear"></div>


            </div>
            <!-- End #mainContent -->

            <jsp:include page="includes/footer.jsp"/>

        </div>
        <!-- End #content -->
    </div>
    <!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="includes/popups.jsp"/>
<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
