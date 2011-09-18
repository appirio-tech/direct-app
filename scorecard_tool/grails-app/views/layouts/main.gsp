<%--
 - Author: TCSASSEMBER, pvmagacho
 - Version: 1.1
 - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page is the main layout of pages.
 - 
 - Version 1.1 (System Assembly - Direct TopCoder Scorecard Tool Integration) Change notes:
 - - added include for right bar
 - - added new scripts to handle right bar
 - - fixed login/help/logout links
 - - fixed other direct links
 --%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Meta Tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><g:layoutTitle default="Topcoder Scorecard Tool" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'dashboard.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'modal.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'launchcontest.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'permissions.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.multiSelect.css')}" />
        <!--[if IE 6]>
        <link rel="stylesheet" href="${resource(dir:'css',file:'dashboard-ie6.css')}" />
        <![endif]-->
        <!--[if IE 7]>
        <link rel="stylesheet" href="${resource(dir:'css',file:'dashboard-ie7.css')}" />
        <![endif]-->
        <link rel="stylesheet" href="${resource(dir:'css',file:'ui.dialog.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'ui.theme.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'ui.core.css')}" />
        
        <g:javascript library="jquery-1.4.1.min" />
        <g:javascript library="jquery-ui-1.7.2.custom" />
        <g:javascript library="jquery.blockUI" />
        <g:javascript library="dashboard" />
        <g:javascript library="launchcontest" />
        <g:javascript library="ui.resizable" />
        <g:javascript library="ui.dialog" />
        <g:javascript library="jquery.tablesorter.min" />
        <g:javascript library="fullcalendar.min" />
        <g:javascript library="jquery.mousewheel" />
        <g:javascript library="jquery.em" />
        <g:javascript library="jScrollPane" />
        <g:javascript library="jquery.stylish-select" />
        <g:javascript library="date" />
        <g:javascript library="jquery.datePicker" />
        <g:javascript library="jquery.bgiframe" />
        <g:javascript library="jquery.scrollfollow" />
        <g:javascript library="jquery.select" />
        <g:javascript library="jquery.multiselect" />
        <g:javascript library="jquery.validate" />
        <g:javascript library="ui.core" />
        <g:javascript library="ui.widget" />
        <g:javascript library="ui.mouse" />
        <g:javascript library="ui.sortable" />
        <g:javascript library="ui.draggable" />
        <g:javascript library="ui.droppable" />
        <g:javascript library="ui.accordion" />
        <g:javascript library="json2" />
        <g:javascript library="scorecard" />
        <g:javascript library="loadHelps" />
        <g:layoutHead />
    </head>
    <body id="page">
        <div id="wrapper">
            <div id="wrapperInner">
                <div id="container">
                    <div id="content">
                
		                <div id="header">
		                    <div id="tabs0"><!-- the header tabs -->
		                        <ul>
		                            <li><a href="/direct/dashboardActive.action"><span>Dashboard</span></a></li>
		                            <li><a href="/direct/allProjects.action"><span>Projects</span></a></li>
		                            <li><a href="/direct/copilot/launchCopilotContest.action"><span>Copilots</span></a></li>
		                            <li class="on"><a href="#"><span>Scorecards</span></a></li>
		                        </ul>
		                    </div><!-- End #tabs1 -->
		                    
							<a href="javascript:;" class="logo"><img src="${request.contextPath}/images/TopCoder_logo3.png" alt="TopCoder"/></a>
		                    
		                    <div class="scorecardLogo">
		                        <img src="${request.contextPath}/images/scorecard-tool-logo.png" alt="TopCoder Scorecard Tool" />
		                    </div>
		                    
		                    <div class="helloUser scorecard">
		                        <ul>
		                            <li><strong>Hello</strong> <g:memberLink coderId="${request.userId}"/>&nbsp;|</li>
		                            <li class="helloUserLink"><a href="/direct/logout.action">Logout</a>|</li>
		                            <li class="helloUserLink"><a href="http://topcoder.com/home/help" class="help">Help</a></li>
		                        </ul>
		                    </div><!-- End .helloUSer -->
		                    
		                    <!-- the left tabs -->
		                    <div id="tabs1" class="scorecardTabs">
		                        <ul>
		                            <li <g:if test="${activeTab == 0}">class="on"</g:if>><g:link controller="scorecard" action="find"><span>Find Scorecard</span></g:link></li>  
		                            <li <g:if test="${activeTab == 1}">class="on"</g:if>><g:link controller="scorecard" action="create"><span>Create/Edit Scorecard</span></g:link></li>
		                        </ul>
		                    </div>
		                    <!-- End #tabs1 -->
		                    <div id="tabs2"><!-- tabs on the right side-->
						        <ul>
						            <li >
						                <a href="/direct/dashboardSearchView.action"><span>Search</span></a>
						            </li>
						            <li >
						                <a href="/direct/dashboardNotifications.action"><span>Settings</span></a>
						            </li>
						            <li >
						                <a href="/direct/dashboardReports.action"><span>Reports</span></a>
						            </li>										            
						            <li >
						            	<a href="/direct/dashboardVMAction.action"><span>VM Management</span></a>
						            </li>						            
						        </ul>
						    </div><!-- End #tabs2 -->
		                </div><!-- End #header -->
                                
		                <div id="mainContent">	
		                	<g:include view="scorecard/right.gsp"/>

		                    <g:layoutBody />
		                </div><!-- End #mainContent --> 
		                
		                <div id="footer">
		                    <p>TopCoder is the world's largest competitive software development community with 232,058 developers representing over 200 countries.</p>
		                    <p><strong>Copyright ©2001-2011, TopCoder, Inc. All rights reserved</strong></p>
		                    <a href="javascript:;" class="poweredBy"><img src="${request.contextPath}/images/logo2.png" alt="TopCoder" /></a>
		                </div><!-- End #footer -->  
                        <input id="ctx" value="${request.contextPath}" type="hidden"/>
                    </div><!-- End #content -->
                </div><!-- End #container -->
            </div>
        </div><!-- End #wrapper -->
    </body>
</html>
