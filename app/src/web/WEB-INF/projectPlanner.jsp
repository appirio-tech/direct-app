<%--
  - Author: GreatKevin, Veve
  -
  - Version: 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
  -
  - Version: 1.1 (Release Assembly - TopCoder Cockpit Project Planner and game plan preview Update)
  - - Add bug race fee and bug race cost calculation to the project planner
  - - Add VM cost plan for the project planner
  -
  - Version: 1.2 (TC Direct Rebranding Assembly Project and Contest related pages)
  - Rebranding the project planner page
  -
  - Version 1.3 (TopCoder Direct - Change Right Sidebar to pure Ajax)
  - Add the right sidebar script reference
  -
  - Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders the project planner page.
  -
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>Topcoder Direct</title>
    <ui:projectPageType tab="gameplan"/>
    <!-- Meta Tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <jsp:include page="includes/serverConfiguration.jsp"/>

    <!-- External CSS -->
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
    <link rel="stylesheet" href="/css/direct/screen.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/launchcontest.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/dashboard.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/thickbox.css" media="all" type="text/css" />
    <link rel="stylesheet" href="/css/direct/jScrollPane.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/jquery-ui-1.7.2.custom.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/modal.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/datepicker.css" media="all" type="text/css"/>
    <link rel="stylesheet" href="/css/direct/instantSearch.css" media="all" type="text/css" />

    <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/dashboard-ie6.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/homepage-ie6.css"/>
    <![endif]-->

    <!--[if IE 7]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie7.css"/>
    <![endif]-->

    <!--[if IE 8]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie8.css"/>
    <![endif]-->

    <!--[if IE 9]>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/screen-ie9.css"/>
    <![endif]-->

    <script type="text/javascript" src="/scripts/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/scripts/jquery-ui-1.7.2.custom.min.js"></script>

    <script type="text/javascript" src="/scripts/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="/scripts/thickbox-compressed.js"></script>
    <script type="text/javascript" src="/scripts/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="/scripts/jquery.em.js"></script>
    <script type="text/javascript" src="/scripts/jScrollPane.js"></script>
    <script type="text/javascript" src="/scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="/scripts/date.prev.js"></script>
    <script type="text/javascript" src="/scripts/common.js"></script>
    <script type="text/javascript" src="/scripts/jquery.datePicker.js"></script>
    <script type="text/javascript" src="/scripts/jquery.stylish-select.js"></script>
    <script type="text/javascript" src="/scripts/jquery.scrollfollow.js"></script>
    <script type="text/javascript" src="/scripts/jquery.blockUI.js"></script>
    <script type="text/javascript" src="/scripts/ajaxupload2.js"></script>
    <script type="text/javascript" src="/scripts/jquery.validate.js"></script>
    <script type="text/javascript" src="/scripts/ckeditor/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="/scripts/jquery.hoverIntent.minified.js"></script>
    <script type="text/javascript" src="/scripts/jquery.cookie.js"></script>

    <script type="text/javascript" src="/scripts/dashboard.js"></script>
    <script type="text/javascript" src="/scripts/loadHelps.js"></script>
    <script type="text/javascript" src="/scripts/modalWindows.js"></script>
    <script type="text/javascript" src="/scripts/maintenance.js"></script>
    <script type="text/javascript" src="/scripts/instantSearch.js"></script>
    <script type="text/javascript" src="/scripts/rightSidebar.js"></script>

    <link rel="stylesheet" href="/css/direct/jsgantt.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/jsganttPlanner.js"></script>
    <link rel="stylesheet" href="/css/direct/projectPlanner.css" media="all" type="text/css"/>
    <script type="text/javascript" src="/scripts/projectPlanner.js"></script>
</head>

<body id="page">
<div id="wrapper">
<div id="wrapperInner">
<div id="container">
<div id="content">


<jsp:include page="includes/header.jsp"/>

<div id="mainContent" class="projectPlanner">


<div id="area1"><!-- the main area -->
<div class="area1Content">
<div class="currentPage">
    <a href="<s:url action="dashboardActive" namespace="/"/>" class="home">Dashboard</a> &gt;
    <a href='<s:url action="projectOverview" namespace="/"><s:param name="formData.projectId" value="sessionData.currentSelectDirectProjectID"/></s:url>'><s:property
            value="sessionData.currentProjectContext.name"/></a> &gt;
    <strong>Project Planner</strong>
    <input type="hidden" name="directProjectId" value="<s:property value='formData.projectId'/>"/>
    <input type="hidden" name="fixedBugRaceFee" value="<s:property value='viewData.fixedBugRaceFee'/>"/>
    <input type="hidden" name="percentageBugRaceFee" value="<s:property value='viewData.percentageBugRaceFee'/>"/>
</div>

<!-- Project Planner content -->
<div class="projectPlanner">

<h2>Project Planner</h2>
<!-- project planner note -->
<span class="note">Project Planner is a tool to set up a draft game plan for the project. The project game plan setup online can be exported to an excel file or imported from an excel file. The project game plan can be previewed in the tool as an gantt chart with estimations.</span>
<!-- End project planner note -->
<div class="projectSettings">
    <!-- billingAccoung select -->
    <s:if test="viewData.copilotPostingRegistrants">
        <select name="billingAccount" class="hide">
            <option value="<s:property value='billingAccountId'/>" selected="selected">Default Billing Account</option>
        </select>
    </s:if>
    <s:else>
        <label>Project Billing Account
        </label>
        <select name="billingAccount">
            <option value="0" selected="selected">Select Billing Account</option>
            <s:iterator value="viewData.billingAccounts">
                <option value="<s:property value="id"/>"><s:property value="name"/></option>
            </s:iterator>
        </select>
    </s:else>

    <!-- End billingAccoung select -->

    <!-- project startDate block -->
    <label>Project Start Date
    </label>

    <div class="dateContainer">
        <input name="startDate" readonly="readonly" type="text" class="date-pick text"/>
    </div>
    <!-- End project startDate block -->
</div>

<!-- End contestTable -->
<table id="contestTable" cellpadding="0" cellspacing="0" class="editTable">
<colgroup>
    <col width="25px"/>
    <col/>
    <col width="160px"/>
    <col width="100px"/>
    <col width="92px"/>
    <col width="22%"/>
    <col width="22%"/>
    <col width="55px"/>
</colgroup>
<thead>
<tr>
    <th></th>
    <th>Challenge Name</th>
    <th>Challenge Type</th>
    <th>Member Cost</th>
    <th>Challenge Fee</th>
    <th>Follow Challenge</th>
    <th>Dependencies</th>
    <th></th>
</tr>
<tr class="contestRowTemplate">
    <td><span class="contestIndex"></span></td>
    <td class="contestNameWrapper">
        <!-- contestName input -->
        <input name="contestName" type="text" value=""/>
        <!-- End contestName input -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td>
        <!-- contestType select -->
        <select name="contestType" class="contestType">
            <option value="0" selected="selected">Select Challenge Type</option>
            <optgroup label="Development">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="!value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
            <optgroup label="Design">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
        </select>
        <!-- End contestType select -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td><span class="memberCost"></span></td>
    <td><span class="contestFee"></span></td>
    <td>
        <!-- followContest select -->
        <select name="followContest" class="followContest">
            <option value="0">Select Challenge to follow</option>
        </select>
        <!-- End followContest select -->
    </td>
    <td>
        <!-- contestDependencies -->
        <div class="taskMultiSelector contestDependencies">
            <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select challenges this challenge
                depends on</a><a class="selectorArrow"></a><label class="hidden">Select challenges this challenge depends
                on</label></div>
            <div class="dropDown">
                <ul>
                </ul>
                <!-- End dependencies list -->
                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
            </div>
        </div>
        <!-- End .contestDependencies -->
    </td>
    <td>
        <a href="javascript:" class="deleteContest">Delete</a>
    </td>
</tr>
<!-- End .contestRowTemplate -->
</thead>
<tbody>
<tr>
    <td><span class="contestIndex">1</span></td>
    <td class="contestNameWrapper">
        <!-- contestName input -->
        <input name="contestName" type="text" value=""/>
        <!-- End contestName input -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td>
        <!-- contestType select -->
        <select name="contestType" class="contestType">
            <option value="0" selected="selected">Select Challenge Type</option>
            <optgroup label="Development">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="!value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
            <optgroup label="Design">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
        </select>
        <!-- End contestType select -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td><span class="memberCost"></span></td>
    <td><span class="contestFee"></span></td>
    <td>
        <!-- followContest select -->
        <select name="followContest" class="followContest">
            <option value="0">Select Challenge to follow</option>
            <option value="2" class="hide"></option>
            <option value="3" class="hide"></option>
        </select>
        <!-- End followContest select -->
    </td>
    <td>
        <!-- contestDependencies -->
        <div class="taskMultiSelector contestDependencies">
            <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select challenges this challenge
                depends on</a><a class="selectorArrow"></a><label class="hidden">Select challenges this challenge depends
                on</label></div>
            <div class="dropDown">
                <ul>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="2"/><label></label></li>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="3"/><label></label></li>
                </ul>
                <!-- End dependencies list -->
                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
            </div>
        </div>
        <!-- End .contestDependencies -->
    </td>
    <td>
        <a href="javascript:" class="deleteContest">Delete</a>
    </td>
</tr>
<tr>
    <td><span class="contestIndex">2</span></td>
    <td class="contestNameWrapper">
        <!-- contestName input -->
        <input name="contestName" type="text" value=""/>
        <!-- End contestName input -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td>
        <!-- contestType select -->
        <select name="contestType" class="contestType">
            <option value="0" selected="selected">Select Challenge Type</option>
            <optgroup label="Development">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="!value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
            <optgroup label="Design">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
        </select>
        <!-- End contestType select -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td><span class="memberCost"></span></td>
    <td><span class="contestFee"></span></td>
    <td>
        <!-- followContest select -->
        <select name="followContest" class="followContest">
            <option value="0" selected="selected">Select Challenge to follow</option>
            <option value="1" class="hide"></option>
            <option value="3" class="hide"></option>
        </select>
        <!-- End followContest select -->
    </td>
    <td>
        <!-- contestDependencies -->
        <div class="taskMultiSelector contestDependencies">
            <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select challenges this challenge
                depends on</a><a class="selectorArrow"></a><label class="hidden">Select challenges this challenge depends
                on</label></div>
            <div class="dropDown">
                <ul>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="1"/><label></label></li>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="3"/><label></label></li>
                </ul>
                <!-- End dependencies list -->
                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
            </div>
        </div>
        <!-- End .contestDependencies -->
    </td>
    <td>
        <a href="javascript:" class="deleteContest">Delete</a>
    </td>
</tr>
<tr>
    <td><span class="contestIndex">3</span></td>
    <td class="contestNameWrapper">
        <!-- contestName input -->
        <input name="contestName" type="text" value=""/>
        <!-- End contestName input -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td>
        <!-- contestType select -->
        <select name="contestType" class="contestType">
            <option value="0" selected="selected">Select Challenge Type</option>
            <optgroup label="Development">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="!value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
            <optgroup label="Design">
                <s:iterator value="viewData.contestTypes">
                    <s:if test="value.studio">
                        <option value="${value.typeId}">${value.description}</option>
                    </s:if>
                </s:iterator>
            </optgroup>
        </select>
        <!-- End contestType select -->
        <span class="validation">This field cannot be empty</span>
    </td>
    <td><span class="memberCost"></span></td>
    <td><span class="contestFee"></span></td>
    <td>
        <!-- followContest select -->
        <select name="followContest" class="followContest">
            <option value="0" selected="selected">Select Challenge to follow</option>
            <option value="1" class="hide"></option>
            <option value="2" class="hide"></option>
        </select>
        <!-- End followContest select -->
    </td>
    <td>
        <!-- contestDependencies -->
        <div class="taskMultiSelector contestDependencies">
            <div class="trigger"><a class="msValue" href="javascript:;" title="Select">Select challenges this challenge
                depends on</a><a class="selectorArrow"></a><label class="hidden">Select challenges this challenge depends
                on</label></div>
            <div class="dropDown">
                <ul>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="1"/><label></label></li>
                    <li class="hide"><input name="contestFilter" type="checkbox" value="2"/><label></label></li>
                </ul>
                <!-- End dependencies list -->
                <div class="btnWrapper"><a class="buttonRed1" href="javascript:;"><span>OK</span></a></div>
            </div>
        </div>
        <!-- End .contestDependencies -->
    </td>
    <td>
        <a href="javascript:" class="deleteContest">Delete</a>
    </td>
</tr>


</tbody>
</table>
<!-- End #contestTable -->

<!-- addMoreWrapper -->
<div class="addMoreWrapper">
    <a class="buttonGray addMoreContests fLeft" href="javascript:;"><span><i></i>Add More</span></a>
</div>
<!-- End .addMoreWrapper -->

<!-- bugRacesWrapper -->
<div class="planBugRacesWrapper">
    <p><label>Race Estimation</label></p>
    <p><span class="text">Plan</span> <input type="text" name="bugRaces" class="bugRaces" value="0"/> <span class="text">Races for this project with prize $ </span><input
        type="text" name="bugRacesPrize" class="bugRaces" value="150"/><span class="text"> Per Race</span>(<i>It's recommended to make race number 1.5 times of planned challenges number.</i>)</p>
    <p><span class="total">Race Fee:</span><span id="bugRaceFeeTotal"></span></p>
    <p><span class="total">Total Race Cost:</span><span id="bugRaceCostTotal"></span></p>
</div>
<!-- End .bugRacesWrapper -->

<!-- bugRacesWrapper -->
<div class="planVMWrapper">
    <p><label>VM Environment Cost</label></p>
    <p><input type="checkbox" name="useVM" title="Check if the project is planned to use VM for dev"/> Do you plan to use VM (Virtual Machine environments)

    </p>
    <p><span>Based on our calculations, you should expect to spend about <span id="vmCost">$0.00</span> on VM's during the course of this project</span></p>
</div>
<!-- End .bugRacesWrapper -->

<!-- buttonBar -->
<div class="buttonBar bottom">
    <a class="newButton1 exportToExcel fRight" href="javascript:;"><span class="btnR"><span
            class="btnC">EXPORT TO EXCEL</span></span></a>
    <a class="newButton1 previewPlan fRight" href="javascript:;"><span class="btnR"><span
            class="btnC">PREVIEW</span></span></a>

    <input type="file" name="uploadProjectPlan"/>
    <a class="newButton1 importFromExcel" href="javascript:;"><span class="btnR"><span
            class="btnC">IMPORT FROM EXCEL</span></span></a>
</div>
<!-- End .buttonBar -->

</div>
<!-- End Project Planner content -->

</div>
</div>
</div>

<div class="clear"></div>


</div>
<!-- End #mainContent -->


<jsp:include page="includes/footer.jsp"/>

<!-- End #footer -->


</div>
<!-- End #content -->
</div>
<!-- End #container -->
</div>
<!-- End #wrapper -->


<div class="popups"><!-- this area will contain the popups of this page -->
    <!-- AJAX preloading indicator -->
    <div id="modalBackground"></div>
    <div id="new-modal">
        <!-- #jsGanttChartPopup -->
        <div id="jsGanttChartModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span class="fLeft">Project Plan Gantt Chart</span>
                        <a class="button5" id="hideLeftPanel" href="javascript:;">Hide Left Panel</a>

                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <table class="projectStats" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr>
                            <th>Estimated Duration</th>
                            <th>Estimated Member Cost</th>
                            <th>Estimated Challenge Fee</th>
                            <th>Estimated Total Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td id="durationStat" class="date fees">xxx days</td>
                            <td id="costStat" class="date fees">$xxx,xxx</td>
                            <td id="feeStat" class="date fees">$xxx,xxx</td>
                            <td id="totalStat" class="date fees">$xxx,xxx</td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- gantt chart container-->
                    <div id="jsGanttChartDiv" class="gantt"></div>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #jsGanttChartPopup -->

        <!-- #projectPlanCircular -->
        <div id="projectPlanCircularModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span>Project Plan Circular</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <div id="ganttChartCircular">
                        <span id="circularContestNumbers"></span> have circular dependencies
                    </div>
                    <div id="ganttChartCircularMultiple">
                        The following challenges have circular dependencies:
                        <ul id="multipleCircularsList"></ul>
                    </div>
                    <!-- end gantt chart circular -->
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #projectPlanCircular -->


        <!-- #projectPlanExportJSON -->
        <div id="projectPlanExportJSONModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span>Project Plan Export JSON</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <textarea cols="30" rows="20" id="projectPlanExportJSON" readonly="readonly">

                    </textarea>
                    <!-- gantt chart container-->
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #projectPlanExportJSON -->

        <!-- #projectPlanEmpty -->
        <div id="projectPlanEmptyModal" class="outLay">
            <div class="modalHeader">
                <div class="modalHeaderRight">
                    <div class="modalHeaderCenter">
                        <span>Project Plan Empty</span>
                        <a href="javascript:;" class="closeModal" title="Close">Close</a>
                    </div>
                </div>
            </div>
            <!-- end .modalHeader -->
            <div class="modalBody">
                <div class="modalBodyContent">
                    <p>Project plan is empty. Please fill challenges table to view plan chart.</p>
                </div>

                <div class="modalCommandBox">
                    <a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
        <!-- end #projectPlanEmpty -->

    <span class="hide">
       <img src="/images/preloader-corner.png" alt="preloaded"/>
       <img src="/images/modal-header.png" alt="preloaded"/>
       <img src="/images/modal-body-normal.png" alt="preloaded"/>
       <img src="/images/preloader-body.png" alt="preloaded"/>
       <img src="/images/modal-warning.png" alt="preloaded"/>
       <img src="/images/modal-error.png" alt="preloaded"/>
       <img src="/images/modal-success.png" alt="preloaded"/>
       <img src="/images/modal-confirmation.png" alt="preloaded"/>
       <img src="/images/modal-arrow.png" alt="preloaded"/>
       <img src="/images/button-new-1.png" alt="preloaded"/>
    </span>
    </div>
</div>
<!-- End .popups -->

<jsp:include page="includes/footerScripts.jsp"/>
</body>
<!-- End #page -->

</html>
