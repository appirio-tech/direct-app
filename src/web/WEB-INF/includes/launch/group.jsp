<%--
  - Version: 1.0
  - Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page fragment renders challenge group multiple select option.
  -
--%>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<h3>Choose Your Challenge Group:</h3>

<div class="prizesInner_tech">
    <span class="head_font">Master Groups&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <span class="head_font_space">Your Challenge Groups</span>
    <br />
    <select multiple class="masterGroupsSelect">
        <s:iterator value="referenceDataBean.groups">
            <option value='<s:property value="id" />'><s:property value="name" /></option>
        </s:iterator>
    </select>

    <div class="button_tech">
        <img src="/images/add_tech.png" alt="add" class="addGroups" />
        <br /><br />
        <img src="/images/remove_tech.png" alt="remove" class="removeGroups" />
    </div>

    <select multiple class="masterGroupsChoosenSelect">
    </select>
</div>