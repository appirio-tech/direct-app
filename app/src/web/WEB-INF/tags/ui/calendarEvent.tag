<%--
  - Author: isv
  - Version: 1.0
  - Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
  -
  - Description: This tag renders an HTML A element referencing the view with details for specified user account.
  -
  - TODO : Subsequent assemblies must properly update this tag to set valid value for href attribute.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="button" tagdir="/WEB-INF/tags/buttons" %>
<%@ taglib prefix="link" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags/ui" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld" %>

<%@ attribute name="activity" required="true" type="com.topcoder.direct.services.view.dto.ActivityDTO" %>
<%@ attribute name="index" required="true" type="java.lang.String" %>

<s:push value="%{#attr['activity']}">
    <s:set var="date" value='date'/>
    <s:set var="originatorId" value='originatorId'/>
    <s:set var="originatorHandle" value='originatorHandle'/>
    <s:set var="contest" value='contest'/>
    <div id="<s:property value="%{#attr['index']}"/>"
         class="tooltipContainer tooltip<s:property value="type.shortName"/>">
        <span class="arrow"></span>
        <div class="tooltipLeft">
            <div class="tooltipRight">
                <div class="tooltipBottom">
                    <div class="tooltipBottomLeft">
                        <div class="tooltipBottomRight">
                            <div class="tooltipCaption">
                                <div class="tooltipCaptionLeft">
                                    <div class="tooltipCaptionRight">
                                        <div class="tooltipCaptionInner">
                                            <h2><s:property value="contest.project.name"/></h2>
                                            <a href="javascript:;" onclick="$('#<s:property value="%{#attr['index']}"/>').css('display','none')"
                                               class="closeIco"></a>
                                        </div>
                                        <!-- End .tooltipCaptionInner -->
                                    </div>
                                </div>
                            </div>
                            <!-- End .tooltipCaption -->

                            <div class="tooltipContent">
                                <h3><s:property value="type.name"/></h3>
                                <h4>
                                    <link:contestDetails contest="${contest}"/>
                                </h4>

                                <p>Posted by <link:user userId="${originatorId}" handle="${originatorHandle}"/>
                                    on <fmt:formatDate value="${date}" pattern="MM/dd/yyyy"/></p>
                            </div>
                            <!-- End .tooltipContent -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End .tooltipContainer -->
</s:push>
