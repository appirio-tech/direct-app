
<%@ page import="com.topcoder.admin.scorecards.Scorecard" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scorecard.label', default: 'Scorecard')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'scorecard.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="scorecardName" title="${message(code: 'scorecard.scorecardName.label', default: 'Scorecard Name')}" />
                        
                            <g:sortableColumn property="scorecardVersion" title="${message(code: 'scorecard.scorecardVersion.label', default: 'Scorecard Version')}" />
                        
                            <g:sortableColumn property="minimumScore" title="${message(code: 'scorecard.minimumScore.label', default: 'Minimum Score')}" />
                        
                            <g:sortableColumn property="maximumScore" title="${message(code: 'scorecard.maximumScore.label', default: 'Maximum Score')}" />
                        
                            <th><g:message code="scorecard.scorecardType.label" default="Scorecard Type" /></th>
                        
                            <th><g:message code="scorecard.scorecardStatus.label" default="Scorecard Status" /></th>
                        
                            <th><g:message code="scorecard.projectCategory.label" default="Project Category" /></th>
                        
                            <g:sortableColumn property="createUser" title="${message(code: 'scorecard.createUser.label', default: 'Create User')}" />
                        
                            <g:sortableColumn property="modifyUser" title="${message(code: 'scorecard.modifyUser.label', default: 'Modify User')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'scorecard.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'scorecard.lastUpdated.label', default: 'Last Updated')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${scorecardInstanceList}" status="i" var="scorecardInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${scorecardInstance.id}">${fieldValue(bean: scorecardInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "scorecardName")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "scorecardVersion")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "minimumScore")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "maximumScore")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "scorecardType")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "scorecardStatus")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "projectCategory")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "createUser")}</td>
                        
                            <td>${fieldValue(bean: scorecardInstance, field: "modifyUser")}</td>
                        
                            <td><g:formatDate date="${scorecardInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${scorecardInstance.lastUpdated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${scorecardInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
