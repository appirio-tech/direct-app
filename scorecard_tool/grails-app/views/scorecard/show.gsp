<%--
 - Author: TCSASSEMBER, pvmagacho
 - Version: 1.1
 - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page render the UI for a socrecard.
 - 
 - Version 1.1 (System Assembly - Direct TopCoder Scorecard Tool Integration) Change notes:
 - - fixed minimum number format 
 --%>
 
<%@ page import="com.topcoder.admin.scorecards.Scorecard" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
        
        <div id="area1" class="scorecard"><!-- the main area -->
	        <div class="area1Content">
	            <div class="currentPage">
	                <a href="/direct/dashboardActive.action" class="home">Dashboard</a> &gt;
	                <g:link action="find">Scorecards</g:link> &gt;
	                <strong>Scorecard Details</strong>
	            </div>
	            <div class="areaHeader">
	                <div class="expandCollapse">
	                	<span class="expandCollapse">
	                    <a class="expandAll" href="javascript:showGroup('group','ratings');">Expand All</a>&nbsp;&nbsp;<span class="borderPipe">|</span>&nbsp;
	                    <a class="collapseAll" href="javascript:hideGroup('group','ratings');">Collapse All</a>&nbsp;<a class="button6" href="${createLink(action:'edit', id: scorecardInstance.id)}"><span class="left"><span class="right">EDIT</span></span></a>
	                    </span>
	                </div>
	                <h2 class="title details">Scorecard Details</h2>
	            </div><!-- End .areaHeader -->  
	            <table class="project scorecard view" width="100%" cellpadding="0" cellspacing="0">
	                <thead>
	                    <tr class="details">
	                        <th class="first">ID</th>
	                        <th>Scorecard Name</th>
	                        <th>Project Type</th>
	                        <th>Category</th>
	                        <th>Type</th>
	                        <th>Min.&nbsp;Score</th>
	                        <th>Max.&nbsp;Score</th>
	                        <th class="last">Status</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                        <td class="first">${scorecardInstance.id}</td>
	                        <td>${fieldValue(bean: scorecardInstance, field: "scorecardName")} v ${fieldValue(bean: scorecardInstance, field: "scorecardVersion")}</td>
	                        <td>${fieldValue(bean: scorecardInstance, field: "projectCategory.projectType.name")}</td>
	                        <td>${fieldValue(bean: scorecardInstance, field: "projectCategory.name")}</td>
	                        <td>${fieldValue(bean: scorecardInstance, field: "scorecardType.name")}</td>
	                        <td><g:formatNumber number="${scorecardInstance.minimumScore}" format="0.0"/></td>
	                        <td><g:formatNumber number="${scorecardInstance.maximumScore}" format=".0"/></td>
	                        <td>${fieldValue(bean: scorecardInstance, field: "scorecardStatus.name")}</td>
	                    </tr>
	                </tbody>
                </table>
                
                <br/>
                <g:each in="${scorecardInstance.scorecardGroups}" var="group" status="i">
                <table class="scorecard view2" width="100%" cellpadding="0" cellspacing="0">
                    <thead>
	                    <tr>
	                        <th><strong>Group:</strong> ${fieldValue(bean: group, field: "groupName")}</th>
	                        <th colspan="3"><strong>Weight</strong> <g:formatNumber number="${group.weight}" format=".0"/></th>
	                    </tr>
	                </thead>
	                <tbody>
	                   <g:each in="${group.scorecardSections}" var="section" status="j">
	                   <tr class="section">
	                       <td class="first"><strong>Section:</strong> ${fieldValue(bean: section, field: "sectionName")}</td>
	                       <td colspan="3"><strong>Weight:</strong> <g:formatNumber number="${section.weight}" format=".0"/></td>
	                   </tr>
	                   <tr class="questionHead">
                           <td class="first">Question</td>
                           <td>Type</td>
                           <td>Weight</td>
                           <td>Document Upload</td>
                       </tr>
                       <g:each in="${section.scorecardQuestions}" var="question" status="k">
                       <tr class="question">
                           <td class="first">
                               <div class="group expand" onclick="showHideGroup(this,'ratings${i}${j}${k}');">Question ${i + 1}.${j + 1}.${k + 1}</div>
                               <span>${fieldValue(bean: question, field: "description")}</span>
                               <div class="ratings ratings${i}${j}${k} hide">
                                   ${fieldValue(bean: question, field: "guideline")}
                                   <div class="clear"></div>
                               </div>
                           </td>
                           <td>${fieldValue(bean: question, field: "questionType.name")}</td>
                           <td><g:formatNumber number="${question.weight}" format=".0"/></td>
                           <td>${question.uploadDocument ? "YES" : "NO"}</td>
                       </tr>
                       </g:each>
	                   </g:each>
	                </tbody>
                </table>
                </g:each>
            </div>
        </div>
    </body>
</html>
