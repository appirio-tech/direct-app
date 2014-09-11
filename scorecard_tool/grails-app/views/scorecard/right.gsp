<%--
 - Author: pvmagacho, GreatKevin
 - Version: 1.3
 - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 -
 - Description: The right side of the topcoder cockpit scorecard page.
 -
 - Version 1.1(Module Assembly - TopCoder Scorecard Tool Right Bar v1.0) changes
 - - Added html codes for customer and project dropdown and the contests list panel
 -
 - Version 1.2 (TCCC-4119 - scorecard - update right side bar) changes
 - Modified scorecard right side bar to match direct application right side bar.
 -
 - Version 1.3 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 - - Reskin the scorecard tool to the new look
--%>

<div id="area2" class="dashboardPage"> <!-- the right column -->
    <div class="newSidebar">
        <div class="topBtns">
            <a href="/direct/copilot/launchCopilotContest" class="copilot" title="Finds a TopCoder Copilot for your project">Get a Copilot</a>
            <a href="/direct/createNewProject" class="start" title="Starts a new project">Start a Project</a>
            <a href="/direct/launch/home" class="launch" title="Launch a new challenge for your project">Launch Contest</a>
        </div>
                
        <div class="contestList">
            <div class="contestListMask">
                <div class="filter">
                    <label>Select Customer</label>
                    
                    <div class="dropdownWidget customerSelectMask">
                        <div class="inputSelect">
                            <input type="text" value="All Customers" />
                            <a href="javascript:;" class="arrow"></a>
                        </div>
                        <div class="contestsDropDown">
                            <ul class="dropList">
                            </ul>
                        </div>

                    </div>
                   
                    <label>Select Project</label>
                    
                    <div class="dropdownWidget projectSelectMask">
                        <div class="inputSelect">
                            <input type="text" onkeyup="filterProject();" onfocus="showHideProjectList();"
                                id="sessionData_currentProjectContext_name" value=""
                                name="sessionData.currentProjectContext.name">
                        </div>
                        
                        <a href="javascript:;" onclick="showHideProjectList();" class="arrow"></a>
                        
                        <div class="contestsDropDown" id="dropDown1" >                        
                            <ul class="dropList ">                           
                            </ul>
                        </div>
                    </div>

                    <input type='button' value='Go' class='selectProjectBtn' />

                    <label>Search</label>
                    <div class="dropdownWidget searchMask">
                        <input type='text' />
                    </div>
                </div>
                
                <div class="tableHeader" id="rightTableHeader">
                    <span class="statusTh down">Status</span>
                    <span class="titleTh">Contest Title</span>
                    <span class="typeTh">Type</span>
                </div>
                <div class="tableBody">                        
                    <table cellpadding="0" cellspacing="0" id="contestsTable">
                        <colgroup>
                            <col width="42px" />
                            <col width="144px" />
                            <col class="last" />
                        </colgroup>
                        <thead>
                        <tr class="hide"><!-- table header is necessary for the sorting functionality-->
                            <th>c1</th>
                            <th>c2</th>
                            <th>c3</th>
                        </tr>
                        </thead>                            
                        <tbody id="contestsContent">                                
                            
                        </tbody>
                    </table>
                </div>

            </div>
        </div>        
        
        <div class="archiveLink">
            <a href="/direc/allProjects">All Projects</a>
        </div>
        <a class="switchBtn" href="javascript:;"></a>
    </div>

    <span id="contextPath" value="${request.contextPath}"/>


</div>


<script type="text/html" id="contest_template">

</script>