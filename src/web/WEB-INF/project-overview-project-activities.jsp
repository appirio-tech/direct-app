<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<s:iterator value="viewData.latestProjectActivities.activities">

    <s:iterator value="value" status="status">
        <s:set value="originatorId" var="originatorId" scope="page"/>
        <s:set value="originatorHandle" var="originatorHandle" scope="page"/>
        <s:set value="contest" var="contest" scope="page"/>
        <s:set value="date" var="date" scope="page"/>

        <tr class="contestLaunch">
            <td class="contestActivities">
                <div class="<s:property value="type.shortName"/>">
                    <div class="leftLaunch">
                        <h3><s:property value="key.title"/></h3>

                        <p><s:property value="type.name"/></p>
                        <a href="<s:url action="detail" namespace="/contest"><s:param name="projectId" value="%{#attr['contest'].id}"/></s:url>">
                            View
                        </a>
                    </div>
                    <div class="rightLaunch"><p><s:property value="type.actionText"/> : <link:user
                            userId="${originatorId}"
                            handle="${originatorHandle}"/></p>

                        <p clas="date"><c:out value="${tcdirect:getDateText(date, 'MM/dd/yyyy')}"/></p></div>
                    <div class="clearFix"></div>
                </div>
            </td>
        </tr>

    </s:iterator>


</s:iterator>

<s:if test="viewData.latestProjectActivities.activities.size == 0">
    <tr>
        <td style="text-align: center">
            No Recent Activities
        </td>
    </tr>
</s:if>
