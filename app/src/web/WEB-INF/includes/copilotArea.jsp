<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:push value="viewData.coPilotStats">
<div class="copilotArea">
    <div class="copilotLeft"><div class="copilotRight">
        <div class="copilotInner">
            <div class="fLeft">
                <a href="help.jsp?height=400&amp;width=800&amp;inlineId=helpPopup" class="title thickbox">Copilots</a>
                <strong>Available </strong>Copilots
                <strong class="number"><s:property value="availableCopilots"/></strong> |&nbsp;&nbsp;
                <a href="https://www.topcoder.com/wiki/display/tc/Active+Copilot+Opportunities" style="color:black;text-decoration: none;" target="_blank">
                    <strong>Projects Available </strong>for Copilots
                </a> 
                <strong class="number"><s:property value="availableCopilotProjects"/></strong>
            </div>
            <button:hireCoPilot/>
        </div><!-- End .copilotInner -->
    </div></div>
</div><!-- End .copilotArea -->
</s:push>