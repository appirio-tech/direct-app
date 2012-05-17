<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 (Release Assembly - TC Cockpit Enterprise Calendar Revamp)
  - This pages renders ata for the dashboard enterprise calendar page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<div>

    <div id="allResponsiblePerson" class="hide">
        <s:iterator value="viewData.responsiblePersonIds" var="userId">
            <link:user userId="${userId}"/>
        </s:iterator>
    </div>

    <div id="calendarData" class="hide">
        {
            "events": [
            <s:iterator value="viewData.milestones" status="status1">
                {
                "title": "${fn:replace(name, '"', '\\"')}",
                "start": "<s:if test="completed"><fmt:formatDate pattern="yyyy-MM-dd" value="${completionDate}"/></s:if><s:else><fmt:formatDate pattern="yyyy-MM-dd" value="${dueDate}"/></s:else>",
                "status": "<s:property value="status.toString().toLowerCase()"/>",
                "description": "${fn:replace(description, '"', '\\"')}",
                "projectId" : "<s:property value='projectId'/>",
                "projectName": "<s:property value="viewData.projects[projectId]"/>"
                <s:if test="owners != null && owners.size > 0">
                    ,"person":{
                    "name":"<s:property value="owners[0].name"/>",
                    "color":"coderTextOrange",
                    "url":""
                    }
                </s:if>
                },
            </s:iterator>
                {}
            ]
        }
    </div>

</div>