<%--
 - Author: TCSASSEMBLER, pvmagacho
 - Version: 1.1
 - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page simply renders a scorecard information which will be used in AJAX
 - call in scorecard editing page.
 -
 - Version 1.1 ((TCCC-3917) Scorecard Tool - Document Upload Option) Change notes:
 - - Modified upload options from YES/NO to N/A, Optional or Required. Will use both
 - - uploadDocument and uploadDocumentRequired properties.
 -
 --%>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${scorecardInstance}">
    <div class="errors">
        <g:renderErrors bean="${scorecardInstance}" as="list" />
    </div>
</g:hasErrors>
<table class="project scorecard view" width="100%" cellpadding="0" cellspacing="0">
    <thead>
        <tr class="details">
            <th>ID</th>
            <th>Scorecard Name</th>
            <th>Project Type</th>
            <th>Category</th>
            <th>Type</th>
            <th>Min.&nbsp;Score</th>
            <th>Max.&nbsp;Score</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td class="first">${fieldValue(bean: scorecardInstance, field: "id")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "scorecardName")} v ${fieldValue(bean: scorecardInstance, field: "scorecardVersion")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "projectCategory.projectType.name")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "projectCategory.name")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "scorecardType.name")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "minimumScore")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "maximumScore")}</td>
            <td>${fieldValue(bean: scorecardInstance, field: "scorecardStatus.name")}</td>
        </tr>
    </tbody>
</table>
<!-- ajaxContent -->
<g:each in="${scorecardInstance.scorecardGroups.sort {it.sort}}" var="group" status="i">
<div class="card">
    <table class="scorecard view2 checks group" width="100%" cellpadding="0" cellspacing="0">
    <thead>
        <tr>
            <th><div class="relative"><div class="checkMatch"><input type="checkbox" class="group" /></div></div><strong>Group:</strong> <span class="groupName">${fieldValue(bean: group, field: "groupName")}</span></th>
            <th colspan="3"><strong>Weight</strong> <span class="groupWeight">${group.weight}</span></th>
        </tr>
    </thead>
    <tbody class="hide">
        <tr class="hide"><td class="hide"></td><td class="hide" colspan="4"></td></tr>
    </tbody>
    </table>
    <g:each in="${group.scorecardSections.sort {it.sort}}" var="section" status="j">
    <table class="scorecard view2 checks section" width="100%" cellpadding="0" cellspacing="0">
    <tbody>
        <tr class="section">
            <td class="first"><div class="relative"><div class="checkMatch"><input type="checkbox" class="section" /></div></div><strong>Section:</strong> <span class="sectionText">${fieldValue(bean: section, field: "sectionName")}</span></td>
            <td colspan="3"><strong>Weight:</strong> <span class="sectionWeight">${fieldValue(bean: section, field: "weight")}</span></td>
        </tr>
        <tr class="questionHead">
            <td class="first">Question</td>
            <td>Type</td>
            <td>Weight</td>
            <td>Document Upload</td>
        </tr>
        <g:each in="${section.scorecardQuestions.sort {it.sort}}" var="question" status="k">
        <tr class="question">
            <td class="first">
                <div class="relative"><div class="checkMatch"><input type="checkbox" class="question" /></div></div>
                <div class="group expand" onclick="showHideGroup(this,'ratings${i}${j}${k}');">Question ${i + 1}.${j + 1}.${k + 1}</div>
                <span class="questionText">${fieldValue(bean: question, field: "description")}</span>
           
                <div class="ratings ratings${i}${j}${k} hide">${fieldValue(bean: question, field: "guideline")}<div class="clear"></div></div><!-- End .ratings -->
            </td>
            <td><span class="questionType">${fieldValue(bean: question, field: "questionType.name")}</span></td>
            <td><span class="questionWeight">${fieldValue(bean: question, field: "weight")}</span></td>
           <td><span class="questionUpload">${(question.uploadDocument ? (question.uploadDocumentRequired ? 'Required' : 'Optional') : 'N/A')}</span></td>
        </tr>
        </g:each>
    </tbody>
    </table>
    </g:each>
</div>
</g:each>
