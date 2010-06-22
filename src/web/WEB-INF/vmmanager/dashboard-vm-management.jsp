<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="/WEB-INF/includes/htmlhead.jsp"/>
    <ui:dashboardPageType tab="dashboard"/>
    <script type="text/javascript" src="/scripts/vmservice.js"></script>
</head>

<c:set var="CURRENT_TAB" scope="request" value="VM Management"/>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="/WEB-INF/includes/header.jsp"/>

                <div id="mainContent">

                    <jsp:include page="/WEB-INF/includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->
                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">VM Management</h2>
                            </div>
                            <!-- End .areaHeader -->

                            <div class="message">
                                <div class="messageTop">
                                    <div class="messageBottom">
                                        <div class="messageLeft">
                                            <div class="messageRight">
                                                <div class="messageTopLeft">
                                                    <div class="messageTopRight">
                                                        <div class="messageBottomLeft">
                                                            <div class="messageBottomRight">
                                                                <div class="messageInner">
                                                                    <p>Here you can launch Virtual Machines.</p>
                                                                </div>
                                                                <!-- .messageInner -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .message -->

                            <div class="form">
                                <form id="vm-launch-form" action="javascript:vmService.launch('vm-launch-form');">
                                    <label for="vm_image_id" >Image id</label>
                                    <select id="vm_image_id" name="vmImageId">
                                        <c:forEach items="${vmImages}" var="vmImage">
                                            <option value="${vmImage.id}">${vmImage.awsImageId}</option>
                                        </c:forEach>
                                    </select>

                                    <br/>
                                    <label for="contest_id" >Contest id</label>
                                    <input type="text" id="contest_id" name="contestId" /> <span id="contestIdError" style="color:red" class="error"></span>
                                    <br/>
                                    <label for="svn_branch" >SVN branch</label>
                                    <input type="text" id="svn_branch" name="svnBranch" /> <span id="svnBranchError" style="color:red" class="error"></span>
                                    <br/>
                                    <label for="tc_handle" >TopCoder handle</label>
                                    <input type="text" id="tc_handle" name="tcHandle" /> <span id="tcHandleError" style="color:red" class="error"></span>

                                    <br/>
                                    <label for="vm_contest_type_id" >Contest type</label>
                                    <select id="vm_contest_type_id" name="vmContestTypeId">
                                        <c:forEach items="${vmContestTypes}" var="vmContestType">
                                            <option value="${vmContestType.id}">${vmContestType.name}</option>
                                        </c:forEach>
                                    </select>
                                    
                                    <span id="launch_button"><button type="submit" value="Submit" >Submit</button></span>
                                    <br/>
                                    <span id="generalError" style="color:red" class="error"></span>

                                    <div id="loading" style="display:none;"> Sending request.... </div>
                                </form>


                            </div>
                            <!-- End .form -->


                            <div class="message">
                                <div class="messageTop">
                                    <div class="messageBottom">
                                        <div class="messageLeft">
                                            <div class="messageRight">
                                                <div class="messageTopLeft">
                                                    <div class="messageTopRight">
                                                        <div class="messageBottomLeft">
                                                            <div class="messageBottomRight">
                                                                <div class="messageInner">
                                                                    <p>Here you can view and destroy Virtual Machines.</p>
                                                                </div>
                                                                <!-- .messageInner -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End .message -->

                            <div>

                                <table border="1px">
                                    <thead>
                                        <tr><th colspan="10"><button type="button" onclick="vmService.refresh();">Refresh</button></th></tr>
                                        <tr>
                                            <th>Contest Id</th>
                                            <th>SVN Branch</th>
                                            <th>TC member handle</th>
                                            <c:if test="${admin}">
                                                <th>Manager Handle</th>
                                            </c:if>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody class="vm_instances_body">
                                        <c:forEach items="${vmInstances}" var="vmInstance">
                                            <tr id="vm_instance_${vmInstance.instance.id}">
                                                <td>${vmInstance.instance.contestId}</td>
                                                <td>${vmInstance.instance.svnBranch}</td>
                                                <td>${vmInstance.instance.tcMemberHandle}</td>
                                                <c:if test="${admin}">
                                                    <td>${vmInstance.managerHandle}</td>
                                                </c:if>
                                                <td class="vm_instance_status">${vmInstance.status}</td>
                                                <td class="vm_instance_action"><c:if test="${vmInstance.status eq 'RUNNING'}">
                                                    <button type="button" value="Terminate" onclick="vmService.terminate(${vmInstance.instance.id});">Terminate</button>
                                                </c:if></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <%--template to render ajax responses--%>
                                <table style="visibility:hidden;display:none;">
                                    <tbody id="vm_instance_template">
                                    <tr id="vm_instance_#instance.id#">
                                        <td>#instance.contestId#</td>
                                        <td>#instance.svnBranch#</td>
                                        <td>#instance.tcMemberHandle#</td>
                                        <c:if test="${admin}">
                                            <td>#managerHandle#</td>
                                        </c:if>
                                        <td class="vm_instance_status">#status#</td>
                                        <td class="vm_instance_action">#action#</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>


                        </div>
                    </div>

                </div>
                <!-- End #mainContent -->

                <jsp:include page="/WEB-INF/includes/footer.jsp"/>

            </div>
            <!-- End #content -->
        </div>
        <!-- End #container -->
    </div>
</div>
<!-- End #wrapper -->

<jsp:include page="/WEB-INF/includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>