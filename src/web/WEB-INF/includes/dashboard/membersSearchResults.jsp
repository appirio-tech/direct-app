<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div id="membersResult">
    <table class="projectStats contests paginatedDataTable" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <th>#</th>
            <th>Project</th>
            <th>Contest</th>
            <th>Handle</th>
            <th>Permission</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="viewData.members" status="status">
            <s:set value="contest" var="contest" scope="page"/>
            <s:set value="userId" var="userId" scope="page"/>
            <s:set value="handle" var="handle" scope="page"/>

            <tr>
                <td><s:property value="#status.index + 1"/></td>
                <td><link:projectDetails project="${contest.project}"/></td>
                <td><link:contestDetails contest="${contest}"/></td>
                <td><link:user userId="${userId}" handle="${handle}"/></td>
                <td class="lastColumn">
                    <s:if test="permissions.length == 0">
                        <a class="button1 thickbox"
                           href="TB_inline?height=260&amp;width=500&amp;inlineId=setPermissionPopup">
                            <span>Set Permission</span></a>
                    </s:if>
                    <s:else>
                        <s:iterator value="permissions"><s:property value="description"/><br/></s:iterator>
                    </s:else>
                </td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <!-- End .projectsStats -->
</div>
<!-- End #membersResult -->
