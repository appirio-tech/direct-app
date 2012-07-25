<%--
  - Author: TCSASSEMLBER
  - Version: 1.0 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Description: The filter header of the new enterprise dashboard
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<div class="filterPanel">
    <div class="filterPanelHeader">
        <div class="filterPanelTitle">
            <h3 id="headerClient"><s:property value="defaultClient"/></h3>
            <dl>
                <dt>Projects :</dt>
                <dd id="headerProject"> All Projects </dd>
                <dt>Project Status :</dt>
                <dd id="headerProjectStatus"> All Project Status </dd>
            </dl>
        </div>
        <div class="filterPanelButton">
            <a href="javascript:;" class="xls">XLS</a>
            <a href="javascript:;" class="pdf">PDF</a>
            <a href="javascript:;" class="print">PRINT</a>
        </div>
    </div>
    <!-- header of filter panel -->
    <div class="filterPanelContainer">
        <dl>
            <dt>Project Filter :</dt>
            <dd id="headerFilter"> None </dd>
            <dt>Project Filter Values :</dt>
            <dd id="headerFilterValue"> None </dd>
            <dt>Date Range :</dt>
            <dd class="last" id="headerDate"></dd>
        </dl>
        <div class="filterPanelButton">
            <a href="javascript:;" class="greyButton triggerModal" name="filterModal"><span><span><span class="filterIcon">Filter</span></span></span></a>
        </div>
    </div>
    <!-- container of filter panel -->
</div>