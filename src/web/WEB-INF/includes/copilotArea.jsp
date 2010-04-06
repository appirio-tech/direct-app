<%@ include file="/WEB-INF/includes/taglibs.jsp" %>
<s:push value="viewData.coPilotStats">
<div class="copilotArea">
    <div class="copilotLeft"><div class="copilotRight">
        <div class="copilotInner">
            <div class="fLeft">
                <a href="javascript:alert('To be implemented by sub-sequent assemblies');" class="title">CoPilots</a>
                <strong>Available </strong>Co-Pilots
                <strong class="number"><s:property value="availableCopilots"/></strong> |&nbsp;&nbsp;
                <strong>Projects Available </strong>for Co-Pilots
                <strong class="number"><s:property value="availableCopilotProjects"/></strong>
            </div>
            <button:hireCoPilot/>
        </div><!-- End .copilotInner -->
    </div></div>
</div><!-- End .copilotArea -->
</s:push>