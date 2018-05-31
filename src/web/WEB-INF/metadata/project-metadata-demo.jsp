<%--
  - Author: TCSASSEMBLER
  - Version: 1.0.0
  - Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page provides the demonstration of Direct project metadata services and Direct project metadata key service.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <jsp:include page="../includes/htmlhead.jsp"/>
    <!-- the tab does not exist because it's demo -->
    <ui:projectPageType tab="metadata"/>
    <script type="text/javascript" src="/scripts/projectMetadataDemo.js"></script>
</head>

<body id="page">
<div id="wrapper">
    <div id="wrapperInner">
        <div id="container">
            <div id="content">

                <jsp:include page="../includes/header.jsp"/>

                <div id="mainContent" class="newSidebarCollapse">

                    <jsp:include page="../includes/right.jsp"/>

                    <div id="area1"><!-- the main area -->

                        <div class="area1Content">
                            <div class="areaHeader">
                                <h2 class="title">
                                    Direct Project Metadata Demo
                                </h2>
                            </div>
                            <!-- End .areaHeader -->
                            <span style="font-size:13px;padding-right:8px;color:#f02020; font-weight:bold">Please choose a project to manage its metadata:</span>
                            <s:select label="Choose a project for metadata management"
                                      name="directProjectInput"
                                      list="viewData.projects"
                                      listKey="data.projectId"
                                      listValue="data.projectName"
                                      multiple="false"
                                      size="1"
                                      cssStyle="width:250px"
                                    />

                            <div id="metdataInputSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                                       <span>
                                                       Project Metadata Add </span>
                                </div>


                                <s:iterator value="predefinedKeys">
                                    <div style="padding-top:8px; padding-left:20px;">
                                    <span>
                                        <s:property value="name"/> (
                                        <s:if test="single">
                                            Single Value
                                        </s:if>
                                        <s:else>
                                            Multiple Values
                                        </s:else>
                                        )
                                    </span>
                                        <input type="hidden" class="metadataKeyID" value='<s:property value="id"/>'/>
                                        <s:if test="predefinedValues.size == 0">
                                            <input type="text" class="metadataValue"
                                                   style="width:250px;margin-left:20px"/>
                                        </s:if>
                                        <s:else>
                                            <s:select label="Choose the value"
                                                      name="metadataListValue"
                                                      list="predefinedValues"
                                                      listKey="predefinedMetadataValue"
                                                      listValue="predefinedMetadataValue"
                                                      multiple="false"
                                                      size="1"
                                                      cssStyle="width:300px"
                                                    />
                                        </s:else>
                                        <input class="add" type="button" style="margin-left:10px" value="ADD"/>
                                    </div>
                                </s:iterator>


                            </div>

                             <div id="metdataKeyAddSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                                                       <span>
                                                       Project Metadata Key Add </span>
                                </div>

                                 <div style="padding-left:20px">

                                 <div style="padding-top:20px">
                              <span>
                              Key Name:</span>
                                     <input type="text" name="keyName" width="300px"/>
                                 </div>

                                   <div style="padding-top:20px">
                              <span>
                              Key Description:</span>
                                     <input type="text" name="keyDescription" width="300px"/>
                                 </div>

                                 <div style="padding-top:20px">
                              <span>
                              Single or not:</span>
                                     <select id="singleChoose">
                                         <option value="single">Single</option>
                                         <option value="multiple">Multiple</option>
                                     </select>
                                 </div>
                                   <div style="padding-top:20px">
                                 <input type="button" id="addKeyButton" value="Add Metadata key"/>
                                                </div>
                                       </div>
                            </div>

                            <div id="metdataValueSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                              <span>
                              Project Metadata </span>
                                </div>

                                <table style="margin-top:20px; margin-left: 20px;" border="1">

                                    <thead>
                                    <th>Direct Project ID</th>
                                    <th>Metadata Key</th>
                                    <th>Metadata KeyID</th>
                                    <th>Metadata Value</th>
                                    <th>Operation</th>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>

                            </div>

                            <div id="metdataSearchSection"
                                 style="border-top-width:2px;border-top-style:dotted;border-top-color:#ff9900;font-size:13px;margin-top:15px">

                                <div style="padding-top:10px; font-size:15px; font-weight: bold">
                              <span>
                              Search Projects by project metadata</span>
                                </div>

                                <div style="padding-top:20px">
                              <span>
                              Choose the project metadata key used for search:</span>
                                    <s:select label="Choose the key to search"
                                              name="metadataSearchKey"
                                              list="predefinedKeys"
                                              listKey="name"
                                              listValue="name"
                                              multiple="false"
                                              size="1"
                                              cssStyle="width:300px"
                                            />
                                </div>

                                <div style="padding-top:20px">
                              <span>
                              Choose the project metadata search methods:</span>
                                    <s:select label="Choose the method to search"
                                              name="metadataSearchMethod"
                                              list="searchMethods"
                                              multiple="false"
                                              size="1"
                                              cssStyle="width:90px"
                                            />
                                </div>

                                <div style="padding-top:20px">
                              <span>
                              Value used to search:</span>
                                    <input type="text" name="metadataSearchValue" width="300px"/>
                                </div>

                                <div style="padding-top:20px">
                                    <input type="button" name="metadataSearchbutton" value="Search Projects"/>
                                </div>

                                <div style="padding-top:20px">
                                    <table style="margin-top:20px; margin-left: 20px;" border="1">

                                        <thead>
                                        <th>Direct Project ID</th>
                                        <th>Direct Project Name</th>
                                        <th>Direct Project Description</th>
                                        <th>Direct Project Status ID</th>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>

                            </div>


                        </div>
                        <!-- End .container2 -->
                    </div>
                </div>

            </div>
            <!-- End #mainContent -->

            <jsp:include page="../includes/footer.jsp"/>

        </div>
        <!-- End #content -->
    </div>
    <!-- End #container -->
</div>
</div>
<!-- End #wrapper -->

<jsp:include page="../includes/popups.jsp"/>

</body>
<!-- End #page -->

</html>
