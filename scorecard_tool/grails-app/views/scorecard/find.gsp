<%--
 - Author: pvmagacho, GreatKevin
 - Version: 1.2
 - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page renders the scorecard finding page. 
 - Version 1.1 (System Assembly - Direct TopCoder Scorecard Tool Integration) Change notes:
 - - fixed direct links
 -
  - Version 1.2 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 - - Reskin the scorecard tool to the new look
 --%>
<%@ page import="com.topcoder.admin.scorecards.ScorecardStatus" %>
<%@ page import="com.topcoder.admin.scorecards.ScorecardType" %>
<%@ page import="com.topcoder.admin.scorecards.ProjectType" %>
<%@ page import="com.topcoder.admin.scorecards.ProjectCategory" %>

<html>
    <head>
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:set var="activeTab" value="${0}" scope="request"></g:set>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${scorecardInstance}">
            <div class="errors">
                <g:renderErrors bean="${scorecardInstance}" as="list" />
            </div>
        </g:hasErrors>
        
        <div id="area1" class="scorecard">
            <div class="area1Content">
                <div class="currentPage">
                    <a href="/direct/dashboardActive.action" class="home">Dashboard</a> &gt;
                    <g:link action="find">Scorecards</g:link> &gt;
                    <strong id="navTitle">Find Scorecard</strong>
                </div>
                <div class="areaHeader">
                    <h2 class="title">Scorecard Search</h2>
                </div><!-- End .areaHeader -->
                            
                <div id="addScorecard" class="roundedBox addSearch">
                    <div class="tl searchResultHide"></div><div class="bl searchResultHide"></div>
                    <!-- tabHead -->
                    <div class="tabHead head searchResultShow hide">
                        
                        <div class="tl2"></div><div class="tr2"></div>
                        <div class="group expand" onclick="showHideGroup(this,'roundedBox'); showHide('apply');">Search Criteria</div>
                        <a href="javascript:;" class="button6 newButtonGreen apply hide"><span class="left"><span class="right">APPLY</span></span></a>
                    </div>
                    <!-- overviewBox -->
                    <div class="roundedBox results hide" id="searchResultForm">
                        <div class="bl"></div><div class="br"></div>
                    </div>
                    <div class="scorecardFind">
                        <form class="find" action="" id="searchForm">
                            <!-- Scorecard Name -->
                            <div class="row">
                                <label class="caption">Scorecard Name</label>
                                <input name="sn" type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Contest Name -->
                            <!--
                            <div class="row">
                                <label class="caption">Contest Name</label>
                                <input type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Scorecard Usage Frequency -->
                            <!--
                            <div class="row">
                                <label class="caption">Scorecard Usage Frequency</label>
                                <select class="combo">
                                    <option>Select</option>
                                    <option>1 Times</option>
                                    <option>3 Times</option>
                                    <option>10+ Times</option>
                                </select>
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Project Type -->
                            <div class="row">
                                <label class="caption">Project Type</label>
                                <select name="spt" class="combo" id="spt">
                                <option></option>
                                <g:each in="${ProjectType.list()}" var="spt">
                                    <option value="${spt.id}">${spt.name}</option>
                                </g:each>
                                </select>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Category -->
                            <div class="row">
                                <label class="caption">Category</label>
                                <select name="sc" class="combo" id="sc"></select>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Scorecard Type -->
                            <div class="row wide">
                                <div class="caption" style="float:left;">Scorecard Type</div>
                                <div class="searchRowBox">
                                <g:each in="${ScorecardType.list()}" status="i" var="st">
                                <div style="float:left;"><label class="checkCaption"><input class="check" type="checkbox" name="st" value="${st.id}" />${st.name}</label></div>
                                </g:each>
                                </div>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Technology -->
                            <!--
                            <div class="row">
                                <label class="caption">Technology</label>
                                <input type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Status -->
                            <div class="row">
                                <div class="caption" style="float:left;">Status</div>
                                <div class="searchRowBox">
                                <g:each in="${ScorecardStatus.list()}" status="i" var="ss">
                                <label class="radioCaption"><input class="status" type="radio" name="ss" value="${ss.id}"/>${ss.name}</label>
                                </g:each>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </form>
                    </div>
                    <div class="tr searchResultHide"></div><div class="br searchResultHide"></div>
                </div><!-- End .roundedBox -->
                
                <div class="buttons searchResultHide">
                    <a class="button7 newButtonOrange clearDataFind" href="javascript:;"><span class="left"><span class="right">CLEAR</span></span></a>
                    <a class="button6 newButtonGreen" href="javascript:;" id="search"><span class="left"><span class="right">SEARCH</span></span></a>
                </div><!-- End .buttons -->
                
                <div class="hide searchResultShow">
	                <p class="foundText"><span class="redtext" id="searchResultCount"><strong>0</strong></span> Scorecards found based on your search criteria</p>
	                <div class="container2">
		                <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
		                    <div class="container2BottomLeft"><div class="container2BottomRight">
		                        <div class="container2Content">
		                            <div class="pagination top">
		                                <div class="pages">
		                                    <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
		                                    <a href="javascript:;" class="current">1</a>
		                                    <a href="javascript:;">2</a>
		                                    <a href="javascript:;" class="next">Next</a>
		                                </div><!-- End .pages -->
		                                <div class="showPages"><!-- number of rows that can be displayed on the same page -->
		                                    <label><strong>Show:</strong></label>
		                                    <select class="maxResult">
		                                        <option value="10">10</option>
		                                        <option value="25">25</option>
		                                        <option value="50">50</option>
		                                        <option value="">All</option>
		                                    </select>
		                                    <span>per page</span>
		                                </div><!-- End .showPages -->
		                                <div class="clear"></div>
		                            </div><!-- End .pagination -->
		                            <table class="projectStats contests" cellpadding="0" cellspacing="0" id="searchResult">
                                        <colgroup>
                                            <col span="1" style="width: 45%;">
                                            <col span="1" style="width: 10%;">
                                            <col span="1" style="width: 10%;">
                                            <col span="1" style="width: 10%;">
                                            <col span="1" style="width: 25%;">
                                        </colgroup>
		                                <thead>
		                                    <tr>
		                                        <th class="first">Scorecard Name&nbsp;&nbsp;<img src="/images/down.png" alt="" /></th>
		                                        <th>Type</th>
		                                        <th>Category</th>
		                                        <th>Status</th>
		                                        <th class="last">Action</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                </tbody>
		                            </table><!-- End .projectsStats -->
		                            <div class="pagination">
		                                <div class="pages">
		                                    <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
		                                    <a href="javascript:;" class="current">1</a>
		                                    <a href="javascript:;">2</a>
		                                    <a href="javascript:;" class="next">Next</a>
		                                </div><!-- End .pages -->
		                                <div class="showPages"><!-- number of rows that can be displayed on the same page -->
		                                    <label><strong>Show:</strong></label>
		                                    <select class="maxResult">
		                                        <option value="10">10</option>
		                                        <option value="25">25</option>
		                                        <option value="50">50</option>
		                                        <option value="">All</option>
		                                    </select>
		                                    <span>per page</span>
		                                </div><!-- End .showPages -->
		                                <div class="clear"></div>
		                            </div><!-- End .pagination -->                                              
		                        </div><!-- End .container2Content -->
		                        </div></div>
		                </div></div></div>
		            </div><!-- End .container2 -->
                </div>                                                     
            </div><!-- End .area1Content -->
        </div><!-- End .area1 -->
        <script type="text/javascript">
            var initMultiselect = true;
        </script>
    </body>
</html>
