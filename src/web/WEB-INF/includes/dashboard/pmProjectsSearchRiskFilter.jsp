<%--
  - Author: TCSASSEMBLER
  - Version: 1.0
  - Since: Module Assembly - TC Cockpit Operations Dashboard 
  - Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page renders risk filter.  
--%>
<form id="riskFilterPanelForm" autocompleted="off">
    <div class='filterPanel riskPanel' id='allProjectsFilter2'>
        <div class='filterHead'>
            <div class='rightSide'>
                <div class='inner'>
                    <div class='filterText'>
                        <a href='javascript:;' class='collapse'><img src='/images/filter-panel/expand_icon.png' alt=''/></a>
                        <span class='title'>Risk Filter</span>
                    </div>
                    <!-- 
                    Save button will not be used in this version, but we still retain it since we will enable it later.
                    <a class="redBtn hide" href="javascript:;" id="saveFilters">
                        <span class="left">Save Filters</span>
                    </a>
                    -->
                    <a class="redBtn" href="javascript:;" id="applyRiskFilters">
                        <span class="left">Apply</span>
                    </a>
                </div>
            </div>
        </div>
        <!--end .filterHead-->
        <div class='filterContent'>
            <div class='rightSide'>
                <div class='inner'>
                    <div class='column1 riskFieldDiv'>
                        <div class='row riskField'>
                            <span class='title'>Fulfillment </span>
                            <select id='fulfillmentFilterOp'>
                                <option value='lt'>&lt;</option>
                                <option value='gt'>&gt;</option>
                            </select>
                            <input type='text' size='5'/>
                            <span class='riskFilterPrefix'>%</span>
                            <br/>
                            <span class="riskError hide">The fulfillment input is invalid.</span>
                        </div>
                        
                        <div class='row riskField'>
                            <span class='title'>Forum Activity</span>
                            <select id='forumActivityOp'>
                                <option value='lt'>&lt;</option>
                                <!-- 
                                '>' is meaningless for forum activity
                                <option value='gt'>&gt;</option>
                                -->
                            </select>
                            <input type='text' size='5'/>
                            <span class='riskFilterPrefix'>days</span>
                            <br/>
                            <span class="riskError hide">The forum activity input is invalid.</span>
                        </div>
                        
                        <div class='row riskField'>
                            <span class='title'>Budget</span>
                            <select id='budgetFilterOp'>
                                <option value='lt'>&lt;</option>
                                <option value='gt'>&gt;</option>
                            </select>
                            <input type='text' size='5'/>
                            <span class='riskFilterPrefix'>%</span>
                            <br/>
                            <span class="riskError hide">The budget input is invalid.</span>
                        </div>
                        
                        <div class='row riskField'>
                            <span class='title'>Duration</span>
                            <select id='durationFilterOp'>
                                <option value='lt'>&lt;</option>
                                <option value='gt'>&gt;</option>
                            </select>
                            <input type='text' size='5'/>
                            <span class='riskFilterPrefix'>%</span>
                            <br/>
                            <span class="riskError hide">The duration input is invalid.</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--end .filterHead-->
        <div class='filterBottom'>
            <div class='rightSide'>
                <div class='inner'></div>
            </div>
        </div>
        <!--end .filterBottom-->
        <div class='collapseBottom hide'>
            <div class='rightSide'>
                <div class='inner'></div>
            </div>
        </div>
    </div>
</form>