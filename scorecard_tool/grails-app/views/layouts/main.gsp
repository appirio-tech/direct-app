<%--
 - Author: pvmagacho, GreatKevin
 - Version: 1.3
 - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page is the main layout of pages.
 - 
 - Version 1.1 (System Assembly - Direct TopCoder Scorecard Tool Integration) Change notes:
 - - added include for right bar
 - - added new scripts to handle right bar
 - - fixed login/help/logout links
 - - fixed other direct links
 -
 - Version 1.2 (Release Assembly - TopCoder Scorecard Tool Ajax Loading and Static Files Sharing) Change notes:
 - - Move css/javascripts/images to common folder of direct
 -
 - Version 1.3 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 - - Reskin the scorecard tool to the new look
 --%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Meta Tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><g:layoutTitle default="topcoder Scorecard Tool" /></title>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/direct/customFont.css"/>
        <link rel="stylesheet" href="/css/scorecard/screen.css" />
        <link rel="stylesheet" href="/css/scorecard/dashboard.css" />
        <link rel="stylesheet" href="/css/scorecard/modal.css" />
        <link rel="stylesheet" href="/css/scorecard/launchcontest.css" />
        <link rel="stylesheet" href="/css/scorecard/permissions.css" />
        <link rel="stylesheet" href="/css/direct/jquery.multiSelect.css" />
        <!--[if IE 6]>
        <link rel="stylesheet" href="/css/direct/dashboard-ie6.css" />
        <![endif]-->
        <!--[if IE 7]>
        <link rel="stylesheet" href="/css/direct/dashboard-ie7.css" />
        <![endif]-->
        <link rel="stylesheet" href="/css/direct/ui.dialog.css" />
        <link rel="stylesheet" href="/css/direct/ui.theme.css" />
        <link rel="stylesheet" href="/css/direct/ui.core.css" />
		<link rel="stylesheet" href="/css/direct/jScrollPane.css" />
		<link rel="stylesheet" href="/css/direct/instantSearch.css" />

        <g:javascript library="jquery-1.4.1.min" base="/scripts/" />
        <g:javascript library="jquery-ui-1.7.2.custom" base="/scripts/"/>
        <g:javascript library="jquery.blockUI" base="/scripts/"/>
        <g:javascript library="dashboard" base="/scripts/scorecard/"/>
        <g:javascript library="launchcontest" base="/scripts/scorecard/"/>
        <g:javascript library="ui.resizable" base="/scripts/"/>
        <g:javascript library="ui.dialog" base="/scripts/"/>
        <g:javascript library="jquery.tablesorter.min" base="/scripts/"/>
        <g:javascript library="fullcalendar.min" base="/scripts/"/>
        <g:javascript library="jquery.mousewheel" base="/scripts/"/>
        <g:javascript library="jquery.em" base="/scripts/"/>
        <g:javascript library="jScrollPane" base="/scripts/"/>
        <g:javascript library="jquery.stylish-select" base="/scripts/"/>
        <g:javascript library="date" base="/scripts/"/>
        <g:javascript library="jquery.datePicker" base="/scripts/"/>
        <g:javascript library="jquery.bgiframe" base="/scripts/"/>
        <g:javascript library="jquery.scrollfollow" base="/scripts/"/>
        <g:javascript library="jquery.select" base="/scripts/"/>
        <g:javascript library="jquery.multiselect" base="/scripts/"/>
        <g:javascript library="jquery.validate" base="/scripts/"/>
        <g:javascript library="ui.core" base="/scripts/"/>
        <g:javascript library="ui.widget" base="/scripts/"/>
        <g:javascript library="ui.mouse" base="/scripts/"/>
        <g:javascript library="ui.sortable" base="/scripts/"/>
        <g:javascript library="ui.draggable" base="/scripts/"/>
        <g:javascript library="ui.droppable" base="/scripts/"/>
        <g:javascript library="ui.accordion" base="/scripts/"/>
        <g:javascript library="json2" base="/scripts/"/>
        <g:javascript library="scorecard" base="/scripts/scorecard/"/>
        <g:javascript library="right" base="/scripts/scorecard/"/>
        <g:javascript library="loadHelps" base="/scripts/"/>
        <g:javascript library="instantSearch" base="/scripts/"/>
        <g:layoutHead />
    </head>
    <body id="page">
        <div id="wrapper">
            <div id="wrapperInner">
                <div id="container">
                    <div id="content">
                
		                <div id="newHeader">

                        <div class="topMenu">
                        <ul class="menus">
                        <li class=" ">
                            <span class="t"><a href="javascript:;">Dashboard</a>  <i></i>
                            </span>

                            <div class="flyout lv1" style="display: none;">
                                <span class="h"><a href="javascript:;">Dashboard</a>
                                </span>

                                <ul>
                                    <li class=" ">
                                        <a href="/direct/enterpriseDashboard/overview.action"
                                           class="first">Enterprise</a>
                                    </li>

                                    <li class=" ">
                                        <a href="/direct/operationsDashboardEnterprise.action">Operations</a>
                                    </li>


                                    <li class=" ">
                                        <a href="/direct/clientUserStatsReport.action">Client User Statistics</a>
                                    </li>


                                    <li class=" ">
                                        <a href="/direct/platformSpecialistsReport.action">Platform Specialists</a>
                                    </li>

                                </ul>
                            </div>
                        </li>
                        <span id="recentProjectItemTemplate" class="hide">
                            <a href="javascript:;" class="recentProjectName">Project 1 <span class="arrow"></span></a>

                            <div class="flyout lv3 lv3Wide">
                                <ul class="liIcons">
                                    <li>
                                        <a class="first recentProjectOverview">Overview <span
                                                class="icon overviewI"></span></a>
                                    </li>

                                    <li>
                                        <a class="recentProjectPlan">Game Plan <span class="icon gameI"></span></a>
                                    </li>

                                    <li>
                                        <a class="last recentProjectSetting">Settings <span
                                                class="icon settinsI"></span></a>
                                    </li>
                                </ul>
                            </div>
                        </span>
                        <li class=" ">
                            <span class="t"><a href="javascript:;">Projects</a>  <i></i>
                            </span>

                            <div class="flyout lv1" style="display: none;">
                                <span class="h"><a href="javascript:;">Projects</a>
                                </span>

                                <ul>
                                    <li>
                                        <a href="/direct/createNewProject.action" class="first">Start New</a>
                                    </li>

                                    <li id="recentProjectsTopNav" class="trigger" style="display: none;">
                                        <a href="javascript:;">Recent Projects <span class="arrow"></span></a>

                                        <div class="flyout lv2 flyoutWide">
                                            <ul class="recentProjectsFlyout">

                                            </ul>
                                        </div>
                                    </li>

                                    <li>
                                        <a href="/direct/allProjects.action" class="last">All Projects</a>
                                    </li>

                                    <li class="trigger">
                                        <a href="javascript:;">Project Copilots <span class="arrow"></span></a>

                                        <div class="flyout lv2">
                                            <ul class="liIcons">
                                                <li>
                                                    <a href="/direct/copilot/launchCopilotContest.action"
                                                       class="first">Get a copilot</a>
                                                </li>

                                                <li>
                                                    <a href="/direct/copilot/listCopilotPostings.action">Copilot Postings</a>
                                                </li>

                                                <li>
                                                    <a href="/direct/copilot/manageCopilots.action"
                                                       class="last">Manage Copilots</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>

                        <li class=" ">
                            <span class="t"><a href="javascript:;">Reporting</a>  <i></i>
                            </span>

                            <div class="flyout lv1" style="display: none;">
                                <span class="h"><a href="javascript:;">Reporting</a>
                                </span>

                                <ul class="liIcons">
                                    <li>
                                        <a href="/direct/dashboardBillingCostReport.action"
                                           class="first">Competition Costs <span class="icon costsI"></span></a>
                                    </li>
                                    <li>
                                        <a href="/direct/dashboardCostReport.action">Cost Analysis <span
                                                class="icon costAnal"></span></a>
                                    </li>
                                    <li>
                                        <a href="/direct/dashboardReports.action">Pipeline <span
                                                class="icon pipelineI"></span></a>
                                    </li>

                                    <li>
                                        <a href="/direct/dashboardProjectMetricsReport.action">Project Metrics <span
                                                class="icon proMetricsI"></span></a>
                                    </li>

                                    <li>
                                        <a href="/direct/dashboardParticipationReport.action">Participation Metrics <span
                                                class="icon parMetricsI"></span></a>
                                    </li>

                                    <li>
                                        <a href="/direct/dashboardJiraIssuesReport.action"
                                           class="last">Issue Metrics <span class="icon issMetricsI"></span></a>
                                    </li>
                                </ul>
                            </div>
                        </li>

                        <li class="current">
                            <span class="t"><a href="javascript:;">Admin</a>  <i></i>
                            </span>

                            <div class="flyout lv1" style="display: none;">
                                <span class="h"><a href="javascript:;">Admin</a>
                                </span>

                                <ul>
                                    <li id="adminGeneralMenu" class="trigger">
                                        <a href="javascript:;" class="first">General <span class="arrow"></span></a>

                                        <div class="flyout lv2">
                                            <ul class="liIcons">

                                                <li>
                                                    <a href="/direct/settings/admin/contestFee.action"
                                                       class="first">Challenge Fee Management <span
                                                            class="icon feeI"></span></a>
                                                </li>


                                                <li>
                                                    <a href="/direct/dashboardVMAction.action">VM Management <span
                                                            class="icon vmI"></span></a>
                                                </li>


                                                <li>
                                                    <a href="/direct/manageCopilotFeedback.action">Manage Copilot Feedback <span
                                                            class="icon feedbackI"></span></a>
                                                </li>

                                                <li>
                                                    <a href="https://community.topcoder.com/tc?module=BadgeAdminHome">Manage Member Badges <span
                                                            class="icon badgesI"></span></a>
                                                </li>


                                                <li>
                                                    <a href="/direct/scorecard/" class="last">Scorecard Management <span
                                                            class="icon scorecardI"></span></a>
                                                </li>


                                                <li>
                                                    <a href="/direct/settings/admin/syncUser.action"
                                                       class="first">Synchronize User <span
                                                            class="icon syncUser"></span></a>
                                                </li>

                                            </ul>
                                        </div>
                                    </li>

                                    <li class="trigger">
                                        <a href="javascript:;" class="last">Settings <span class="arrow"></span></a>

                                        <div class="flyout lv2">
                                            <ul class="liIcons">
                                                <li>
                                                    <a target="_blank"
                                                       href="http://community.topcoder.com/tc?module=MyHome"
                                                       class="first">My Profile <span class="icon profileI"></span></a>
                                                </li>
                                                <li>
                                                    <a href="/direct/settings/notifications.action">Notifications <span
                                                            class="icon notifyI"></span></a>
                                                </li>
                                                <li>
                                                    <a href="/direct/settings/permissions.action">Permissions <span
                                                            class="icon permissions"></span></a>
                                                </li>

                                                <li>
                                                    <a href="/direct/group/viewUserGroupsAction.action?criteria.permissions=REPORT&amp;criteria.permissions=READ&amp;criteria.permissions=WRITE&amp;criteria.permissions=FULL"
                                                       class="last">Groups <span class="icon groupsI"></span></a>
                                                </li>

                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        </ul>

                        <div class="helloUser">
                            <div class="instantSearch">
                                <div class="instantSearchBg">
                                    <input value="Search for challenges, projects and features"
                                           name="instantSearchInput">
                                    <span class="searchIcon"></span>
                                </div>

                                <div class="resultDropDown" style="display: none;">
                                    <div class="ajaxLoading">
                                        <img width="60" height="9" src="/images/fading-squares.gif" alt="">
                                    </div>

                                    <div class="quickResultList"></div>
                                </div>
                            </div>

                            <ul>
                                <li>
                                    <strong>Hello</strong>
                                    <g:memberLink coderId="${request.userId}"/>
                                </li>

                                <li class="helloUserLink">
                                    <a href="/direct/logout.action">Logout</a>
                                </li>

                                <li class="helloUserLink">
                                    <a target="_blank" href="https://www.topcoder.com/university/help-center">Help</a>
                                </li>
                            </ul>
                        </div>
                        <!-- End .helloUSer -->
                        </div>


                        <div class="midarea">
                            <a class="logo" href="/direct/scorecard/">Scorecard Tool</a>
                        </div>


		                    <!-- the left tabs -->
		                    <div id="tabs1" class="scorecardTabs">
		                        <ul>
		                            <li <g:if test="${activeTab == 0}">class="on"</g:if>><g:link controller="scorecard" action="find"><span>Find Scorecard</span></g:link></li>  
		                            <li <g:if test="${activeTab == 1}">class="on"</g:if>><g:link controller="scorecard" action="create"><span>Create/Edit Scorecard</span></g:link></li>
		                        </ul>
		                    </div>
		                    <!-- End #tabs1 -->

		                </div><!-- End #header -->
                                
		                <div id="mainContent" class="newSidebarCollapse">
		                	<g:include view="scorecard/right.gsp"/>

		                    <g:layoutBody />
		                </div><!-- End #mainContent -->

                    <div id="footer">
                        <!--Update footer-->
                        <div class="socialNetwork">
                            <span>Follow us on :</span>
                            <a href="https://www.twitter.com/TopCoder" class="twitterIcon" target="_blank" title="Follow us on Twitter"></a>
                            <a href="https://www.linkedin.com/company/topcoder" class="linkedInIcon" target="_blank" title="Follow us on LinkedIn"></a>
                            <a href="https://www.facebook.com/TopCoderInc" class="facebookIcon" target="_blank" title="Follow us on Facebook"></a>
                            <a href="https://plus.google.com/104268008777050019973" class="gPlusIcon" target="_blank" title="Follow us on Google+"></a>
                            <a href="https://youtube.com/topcoderinc" class="youtubeIcon" target="_blank" title="Follow us on YouTube"></a>
                        </div>
                        <!--End socialNetwork-->
                        <div class="copyright">
                            <%--<div class="logoFooterWrapper"><img src="/images/CS_TopCoder-Banner_782x175.png" alt="TopCoder" width="250"></div>--%>
                            <span>&#169; Copyright TopCoder, Inc. 2001 - <script type="text/javascript">d=new Date();document.write(d.getFullYear());</script></span>&nbsp;|&nbsp;
                            <a href="https://www.topcoder.com/tc?module=Static&d1=about&d2=terms" target="_blank" title="Terms of Use">Terms of Use</a>&nbsp;|&nbsp;
                            <a href="https://www.topcoder.com/tc?module=Static&d1=about&d2=privacy" target="_blank" title="Privacy Policy">Privacy Policy</a>
                        </div>
                     </div>
		                %{----}%
		                %{--<div id="footer">--}%
		                    %{--<p>TopCoder is the world's largest competitive software development community with 232,058 developers representing over 200 countries.</p>--}%
		                    %{--<p><strong>Copyright ©2001 - 2014, TopCoder, Inc. All rights reserved</strong></p>--}%
		                    %{--<a href="javascript:;" class="poweredBy"><img src="/images/logo2.png" alt="TopCoder" /></a>--}%
		                %{--</div><!-- End #footer -->  --}%
                        <input id="ctx" value="${request.contextPath}" type="hidden"/>
                    </div><!-- End #content -->
                </div><!-- End #container -->
            </div>
        </div><!-- End #wrapper -->
	<div id="popups">
		<div id="modalBackground"></div>
		<div id="new-modal">
			<div class="outLay" id="preloaderModal" style="display: none;">
				<div class="modalHeaderSmall">
					<div class="modalHeaderSmallRight">
						<div class="modalHeaderSmallCenter"></div>
					</div>
				</div>
				<div class="modalBody">
					<span id="preloaderAnimation">
					<img alt="Loading" src="/images/preloader-loading.gif">
					</span>
					<div class="preloaderTips">Loading...</div>
				</div>
				<div class="modalFooter">
					<div class="modalFooterRight">
						<div class="modalFooterCenter">
							<div class="&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<span class="hide">
			<img src="/images/preloader-corner.png"/>
			<img src="/images/modal-header.png"/>
			<img src="/images/modal-body-normal.png"/>
			<img src="/images/preloader-body.png"/>
			<img src="/images/modal-warning.png"/>
			<img src="/images/modal-error.png"/>
			<img src="/images/modal-success.png"/>
			<img src="/images/modal-confirmation.png"/>
			<img src="/images/modal-arrow.png"/>
			<img src="/images/button-new-1.png"/>
		</span>
	</div>
    </body>
</html>
