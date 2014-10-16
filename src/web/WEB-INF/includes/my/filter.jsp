<%--
  - Author: GreatKevin
  - Version: 1.0
  - Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.0 TopCoder Direct - Challenges Section Filters Panel
  -
  - Filter panel for my created / my challenges.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- filter panel -->
<div class="challengesFilter">
    <div class="filterTitle">
        <a href="javascript:;" class="folder">Filter</a>
    </div>
    <div class="filterContainer">
        <div class="row">
            <div class="column firstColumn">
                <div>
                    <label>Customer:</label>
                    <select id='customerFilter'>
                        <option selected="selected" value="All">All Customers</option>
                        <s:iterator value="customers" var="customer">
                            <s:if test="key != 0">
                                <option value="<s:property value='key'/>"><s:property value='value'/></option>
                            </s:if>
                        </s:iterator>
                    </select>
                </div>
            </div>
            <div class="column secondColumn">
                <div>
                    <label>Challenge Type:</label>
                    <select id='challengeTypeFilter'>
                        <option selected="selected" value="All">All Challenge Types</option>
                        <s:iterator value="challengeTypes">
                            <s:if test="key != 0">
                                <option value="<s:property value='value'/>"><s:property value='value'/></option>
                            </s:if>
                        </s:iterator>
                    </select>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="column firstColumn">
                <div>
                    <label>Project:</label>
                    <select id='projectFilter'>
                        <option selected="selected" value="All">All Projects</option>
                        <s:iterator value="projects">
                            <s:if test="key != 0">
                                <option value="<s:property value='id'/>"> <s:property escapeJavaScript="true" value='name'/></option>
                            </s:if>
                        </s:iterator>
                    </select>
                </div>
            </div>
            <div class="column secondColumn">
                <div>
                    <label>Challenge Status:</label>
                    <select id='challengeStatusFilter'>
                        <option selected="selected" value="All">All Challenge Status</option>
                        <s:iterator value="challengeStatus">
                                <option value="<s:property value='key.shortName'/>"><s:property value='key.name'/></option>
                        </s:iterator>
                    </select>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- End .filterPanel -->