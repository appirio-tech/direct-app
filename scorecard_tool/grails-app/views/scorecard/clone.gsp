<%--
 - Author: pvmagacho, GreatKevin
 - Version: 1.2
 - Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 -
 - Description: This page renders the scorecard cloning page.
 -
 - Version 1.1 ((TCCC-3917) Scorecard Tool - Document Upload Option) Change notes:
 - - Modified upload options from YES/NO to N/A, Optional or Required. Will use both
 - - uploadDocument and uploadDocumentRequired properties.
 -
  - Version 1.2 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 - - Reskin the scorecard tool to the new look
 -
 --%>
<%@ page import="com.topcoder.admin.scorecards.Scorecard" %>
<%@ page import="com.topcoder.admin.scorecards.ScorecardStatus" %>
<%@ page import="com.topcoder.admin.scorecards.ScorecardType" %>
<%@ page import="com.topcoder.admin.scorecards.ProjectType" %>
<%@ page import="com.topcoder.admin.scorecards.ProjectCategory" %>
<%@ page import="com.topcoder.admin.scorecards.ScorecardQuestionType" %>
<g:set var="questionTypes" value="${ScorecardQuestionType.list()}" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
    </head>
    <body>
        <g:set var="activeTab" value="${1}" scope="request"></g:set>
        <input type="hidden" id="scorecard_id" value="0" />
        <input type="hidden" id="cloneScorecard" value="${scorecardInstance.id}" />
        <div id="area1" class="scorecard"><!-- the main area -->
            <div class="area1Content">
                <div class="currentPage">
                    <a href="/direct/dashboardActive.action" class="home">Dashboard</a> &gt;
                    <g:link action="find">Scorecards</g:link> &gt;
                    <strong>Clone Scorecard</strong>
                </div>
            </div>
            <div class="areaHeader">
                <div class="expandCollapse">
                    <a class="button6 newButtonGreen saveChanges" href="#"><span class="left"><span class="right">SAVE CHANGES</span></span></a>
                </div>
                <h2 class="title edit">Clone Scorecard</h2>
            </div><!-- End .areaHeader -->
            
            <table class="project scorecard view" width="100%" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th class="first">ID</th>
                        <th>Scorecard Name</th>
			<th>Scorecard Version</th>
                        <th>Project Type</th>
                        <th>Category</th>
                        <th>Type</th>
                        <th>Min.&nbsp;Score</th>
                        <th>Max.&nbsp;Score</th>
                        <th class="last">Status</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="first"></td>
                        <td><g:textField name="scorecardName" class="mediumText" value="${scorecardInstance.scorecardName}" /></td>
			<td><g:textField name="scorecardVersion" class="shortText" value="${scorecardInstance.scorecardVersion}" /></td>
                        <td><g:select class="shortedit" name="projectType" from="${ProjectType.list()}" value="${scorecardInstance.projectCategory.projectType.id}" optionKey="id" optionValue="name" /></td>
                        <td><g:select class="shortedit" name="projectCategory" from="${scorecardInstance.projectCategory.projectType.projectCategories}" value="${scorecardInstance.projectCategory.id}" optionKey="id" optionValue="name" /></td>
                        <td><g:select class="shortedit" name="scorecardType" from="${ScorecardType.list()}" value="${scorecardInstance.scorecardType.id}" optionKey="id" optionValue="name" /></td>
                        <td><g:textField name="minimumScore" class="shortText" value="${scorecardInstance.minimumScore}" /></td>
                        <td><g:textField name="maximumScore" class="shortText" value="${scorecardInstance.maximumScore}" /></td>
                        <td><g:select class="shortedit" name="scorecardStatus" from="${ScorecardStatus.list()}" value="${scorecardInstance.scorecardStatus.id}" optionKey="id" optionValue="name" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
            
            <div id="sortableGroups">
               <div id="sortableSections">
                   <div class="biggroup">
                       <table class="scorecard view2 edit group" width="100%" cellpadding="0" cellspacing="0">
                           <thead>
                               <tr>
                                   <th class="first">
                                       <div class="relative"><div class="move group"></div></div>
                                       <strong>Group:</strong>&nbsp;&nbsp;<g:textField name="groupName" class="longText" value="Group 1" />
                                   </th>
                                   <th colspan="4">
                                       <strong>Weight</strong>&nbsp;&nbsp;<g:textField name="groupWeight" class="shortText weightG" value="100.0" />
                                       <a class="remIcon" href="javascript:;"></a><a class="addIcon" href="javascript:;"></a>
                                   </th>
                               </tr>
                           </thead>
                           <tbody class="hide">
                               <tr class="hide"><td class="hide"></td><td class="hide" colspan="4"></td></tr>
                           </tbody>
                       </table>
                       <div class="sections">
                           <table class="scorecard view2 edit section" width="100%" cellpadding="0" cellspacing="0">
                               <tbody class="section">
                                   <tr class="section">
                                       <td class="first">
                                           <div class="relative"><div class="move section"></div></div>
                                           <strong>Section:</strong>&nbsp;<g:textField name="sectionName" class="longText" value="Section 1" />
                                       </td>
                                       <td colspan="4">
                                           <strong>Weight:</strong>&nbsp;<g:textField name="sectionWeight" class="shortText weightS" value="100.0" />
                                           <a class="remIcon" href="javascript:;"></a><a class="addIcon" href="javascript:;"></a>
                                       </td>
                                   </tr>
                                   <tr class="questionHead">
                                       <td class="first">Question</td>
                                       <td>Type</td>
                                       <td>Weight</td>
                                       <td>Document Upload</td>
                                       <td class="btn">
                                           <a class="addIcon" href="javascript:;"></a>
                                           <div class="addmenu">
                                               <a class="addManually" href="javascript:;" >Add Manually</a><br />
                                               <a class="addReq" href="javascript:;">Add from Requirements</a><br />
                                               <a class="addScorecard" href="javascript:;">Add from other scorecards</a>
                                           </div>
                                       </td>
                                   </tr>
                                   <tr class="question">
                                       <td class="first">
                                           <div class="relative"><div class="move question"></div></div>
                                           <p>Question Text:</p>
                                           <g:textArea name="questionDescription" cols="100" rows="2" class="question" value="" />
                                           <p>Question Guideline</p>
                                           <g:textArea name="questionGuideline" cols="100" rows="7" class="guideline" value="" />
                                       </td>
                                       <td>
                                           <g:select class="type edit" name="questionType" from="${questionTypes}" value="0" optionKey="id" optionValue="name" />
                                       </td>
                                       <td><g:textField name="questionWeight" class="shortText weightQ" value="100.0" /></td>
                                       <td>
                                           <g:select from="${['N/A','Optional','Required']}" name="questionUploadDocument" />
                                       </td>
                                       <td class="btn"><a class="remIcon" href="javascript:;"></a></td>
                                   </tr>
                                   <tr class="question hide">
                                       <td class="first">
                                           <div class="relative"><div class="move question"></div></div>
                                           <p>Question Text:</p>
                                           <textarea name="questionDescription" cols="100" rows="2" class="question"></textarea>
                                           <p>Question Guideline</p>
                                           <textarea name="questionGuideline" cols="100" rows="7" class="guideline"></textarea>
                                       </td>
                                       <td>
                                           <g:select class="type edit" name="questionType" from="${questionTypes}" optionKey="id" optionValue="name" />
                                       </td>
                                       <td><input name="questionWeight" type="text" class="shortText weightQ" value="0.0" /></td>
                                       <td>
                                           <g:select from="${['N/A','Optional','Required']}" name="questionUploadDocument" />
                                       </td>
                                       <td class="btn"><a class="remIcon" href="javascript:;"></a></td>
                                   </tr>
                                   <tr class="subtotal">
                                       <td class="right">
                                           <strong>Question Weights:</strong>
                                       </td>
                                       <td class="left" colspan="4">
                                           <input type="text" class="shortText weightSumQuestions green" value="0.0" /><span>Sum of question weights within a section must total 100.</span>
                                       </td>
                                   </tr>
                               </tbody>
                           </table>
                       </div><!-- .sections -->
                       
                       <table class="scorecard view2 edit group" width="100%" cellpadding="0" cellspacing="0">
                           <tfoot>
                               <tr>
                                   <th class="right first">
                                       <strong>Section Weights:</strong>
                                   </th>
                                   <th class="left" colspan="4">
                                       <input type="text" class="shortText weightSumSections green" value="0.0" /><span>Sum of section weights within a group must total 100.</span>
                                   </th>
                               </tr>
                           </tfoot>
                           <tbody>
                           <tr class="hide"><td></td></tr>
                           </tbody>
                       </table>
                   </div><!-- End .biggroup -->
                   <div id="sumOfGroups">
                       <table class="scorecard view2 edit group" width="100%" cellpadding="0" cellspacing="0">
                           <tfoot>
                               <tr>
                                   <th class="right first">
                                       <strong>Group Weights:</strong>
                                   </th>
                                   <th class="left" colspan="4">
                                       <input type="text" class="shortText weightSumGroups green" value="0.0" /><span>Sum of all group weights must equal 100.</span>
                                   </th>
                               </tr>
                           </tfoot>
                           <tbody>
                           <tr class="hide"><td></td></tr>
                           </tbody>
                       </table><!-- End .scorecard.view2.section -->
                   </div><!-- #sumOfGroups -->
               </div><!-- End #sortableSections -->
            </div><!-- End #sortableGroups -->
            
            <div class="expandCollapse">
                <a class="button6 newButtonGreen saveChanges" href="#"><span class="left"><span class="right">SAVE CHANGES</span></span></a>
            </div>
                            
        </div>
        
        <!-- add question manually window -->
    
        <!-- defined the dialog for model window -->
        <div id="addWindow" class="dialogContent" style="display: none;">
            <div id="validation" class="addWindow">
                <div class="header"><p class="title">The scorecard cannot be saved.</p></div> 
                <div class="body">
                    <p>Reason:</p>
                </div>
                
                <div class="foot">
                    <div class="buttons">
                        <a class="button7 newButtonOrange closeDialog" href="javascript:void(0)"><span class="left"><span class="right">CANCEL</span></span></a>
                    </div>
                    <div class="clear"></div>
                </div>
            </div><!-- End #validation -->  
            <div id="addManually" class="addWindow">
                <div class="header"><p class="title">Add Question(s)</p></div> 
                
                <div class="body">
                    <p>Question Text:</p>
                    <textarea cols="100" rows="2"  class="question"></textarea><br />
                    <p>Question Guideline</p>
                    <textarea cols="100" rows="7" class="guideline"></textarea><br />
                    <p class="higher">
                        <label>Type:</label>
                        <g:select name="questionType" class="type edit" from="${questionTypes}" optionKey="id" optionValue="name" />
                    </p>
                    <div class="clear"></div>
                </div>
                
                <div class="foot">
                    <a class="moreQuestions" href="javascript:;">+ More Questions</a>
                    <div class="buttons">
                        <a class="button7 newButtonOrange closeDialog" href="javascript:void(0)"><span class="left"><span class="right">CANCEL</span></span></a>
                        <a class="button6 newButtonGreen submitBtn" href="javascript:void(0)"><span class="left"><span class="right">SUBMIT</span></span></a>
                    </div>
                    <div class="clear"></div>
                </div>
            </div><!-- End #addManually -->
            
            <div id="addReq" class="addWindow">
                <div class="header"><p class="title">Create Questions from Requirements</p></div>
                
                <div class="body">
                    <p>Functional Requirements :</p>
                    <p class="note">* Each functional requirement should be on a separate line.</p>             
                    <textarea cols="100" rows="7" class="guideline"></textarea><br />
                    <p class="higher">
                        <label>Type:</label>
                        <g:select name="questionType" class="type edit" from="${questionTypes}" optionKey="id" optionValue="name" />
                    </p>
                    <div class="clear"></div>
                </div>
                
                <div class="foot">
                    <div class="buttons">
                        <a class="button7 newButtonOrange closeDialog" href="javascript:void(0)"><span class="left"><span class="right">CANCEL</span></span></a>
                        <a class="button6 newButtonGreen submitBtn" href="javascript:void(0)"><span class="left"><span class="right">SUBMIT</span></span></a>
                    </div>
                    <div class="clear"></div>
                </div>  
            </div><!-- End #addReq -->
            
            <div id="addScorecard2" class="addWindow">
                <div class="header"><p class="title">Find Scorecard</p></div>
                <div class="body">
                    <div class="scorecardFind">
                        <form class="find" action="" id="searchForm">
                            <!-- Scorecard Name -->
                            <div class="row">
                                <label class="caption">Scorecard Name</label>
                                <input name="sn" type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Contest Name -->
                            <!--
                            <div class="row">
                                <label class="caption">Contest Name</label>
                                <input type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Scorecard Usage Frequency -->
                            <!--
                            <div class="row">
                                <label class="caption">Scorecard Usage Frequency</label>
                                <select class="combo">
                                    <option>Select</option>
                                    <option>1 Times</option>
                                    <option>3 Times</option>
                                    <option>10+ Times</option>
                                </select>
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Project Type -->
                            <div class="row">
                                <label class="caption">Project Type</label>
                                <select name="spt" class="combo" id="spt">
                                <option></option>
                                <g:each in="${ProjectType.list()}" var="spt">
                                    <option value="${spt.id}">${spt.name}</option>
                                </g:each>
                                </select>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Category -->
                            <div class="row">
                                <label class="caption">Category</label>
                                <select name="sc" class="combo" id="sc"></select>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Scorecard Type -->
                            <div class="row wide">
                                <div class="caption" style="float:left;">Scorecard Type</div>
                                <div class="searchRowBox">
                                <g:each in="${ScorecardType.list()}" status="i" var="st">
                                <label class="checkCaption"><input class="check" type="checkbox" name="st" value="${st.id}" />${st.name}</label>
                                </g:each>
                                </div>
                                <div class="clear"></div>
                            </div>
                            
                            <!-- Technology -->
                            <!--
                            <div class="row">
                                <label class="caption">Technology</label>
                                <input type="text" class="text" />
                                <div class="clear"></div>
                            </div>
                            -->
                            
                            <!-- Status -->
                            <div class="row">
                                <div class="caption" style="float:left">Status</div>
                                <div class="searchRowBox">
                                <g:each in="${ScorecardStatus.list()}" status="i" var="ss">
                                <label class="radioCaption"><input class="status" type="radio" name="ss" value="${ss.id}"/>${ss.name}</label>
                                </g:each>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="foot">
                    <a class="clearBtn clearDataFind" href="javascript:;">Clear</a>
                    <div class="buttons">
                        <a class="button7 newButtonOrange closeDialog" href="javascript:;"><span class="left"><span class="right">CANCEL</span></span></a>
                        <a class="button6 newButtonGreen searchBtn" href="javascript:;"><span class="left"><span class="right">SUBMIT</span></span></a>
                    </div>
                    <div class="clear"></div>
                </div>  
            </div><!-- End #addScorecard -->
            
            <div id="addSearch" class="addWindow editSearch">
                <div class="header">
                    <p class="title">Search Results</p>
                    <a href="javascript:;" class="button7 newButtonOrange closeDialog modalTitleBtn"><span class="left"><span class="right">CLOSE</span></span></a>
                </div>
                <div class="body">
                    <div id="addScorecard" class="scorecardSearch">
                       <!-- tabHead -->
                       <div class="tabHead head">
                           
                           <div class="tl2"></div><div class="tr2"></div>
                           <div class="group expand" onclick="showHideGroup(this,'roundedBox'); showHide('apply2');">Search Criteria</div>
                           <a href="javascript:;" class="button6 newButtonGreen apply2 hide"><span class="left"><span class="right">APPLY</span></span></a>
                       </div>
                       <!-- overviewBox -->
                       <div class="roundedBox results hide">
                           <div class="scorecardFind">
                           
                           </div><!-- .scorecardFind -->
                           <div class="bl"></div><div class="br"></div>
                       </div><!-- End .roundedBox.results -->
                       <p class="foundText"><span class="redtext" id="searchResultCount"><strong>0</strong></span> Scorecards found based on your search criteria</p>
                       <div class="container2">
                                    <div class="container2Left"><div class="container2Right"><div class="container2Bottom">
                                        <div class="container2BottomLeft"><div class="container2BottomRight">
                                            <div class="container2Content">
                                                <div class="pagination top">
                                                    <div class="pages">
                                                        <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
                                                        <a href="javascript:;" class="current">1</a>
                                                        <a href="javascript:;">2</a>
                                                        <a href="javascript:;" class="next">Next</a>
                                                    </div><!-- End .pages -->
                                                    <div class="showPages"><!-- number of rows that can be displayed on the same page -->
                                                        <label><strong>Show:</strong></label>
                                                        <select class="maxResult maxResult2">
                                                            <option value="10">10</option>
                                                            <option value="25">25</option>
                                                            <option value="50">50</option>
                                                            <option value="">All</option>
                                                        </select>
                                                        <span>per page</span>
                                                    </div><!-- End .showPages -->
                                                    <div class="clear"></div>
                                                </div><!-- End .pagination -->
                                                <table class="projectStats contests" cellpadding="0" cellspacing="0" id="searchResult">
                                                    <colgroup>
                                                        <col span="1" style="width: 45%;">
                                                        <col span="1" style="width: 10%;">
                                                        <col span="1" style="width: 10%;">
                                                        <col span="1" style="width: 10%;">
                                                        <col span="1" style="width: 25%;">
                                                    </colgroup>
                                                    <thead>
                                                        <tr>
                                                            <th class="first">Scorecard Name&nbsp;&nbsp;<img src="/images/down.png" alt="" /></th>
                                                            <th>Type</th>
                                                            <th>Category</th>
                                                            <th>Status</th>
                                                            <th class="last">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table><!-- End .projectsStats -->
                                                <div class="pagination">
                                                    <div class="pages">
                                                        <span class="prev">Prev</span><!-- we are on the first page so the prev link must be deactive -->
                                                        <a href="javascript:;" class="current">1</a>
                                                        <a href="javascript:;">2</a>
                                                        <a href="javascript:;" class="next">Next</a>
                                                    </div><!-- End .pages -->
                                                    <div class="showPages"><!-- number of rows that can be displayed on the same page -->
                                                        <label><strong>Show:</strong></label>
                                                        <select class="maxResult maxResult2">
                                                            <option value="10">10</option>
                                                            <option value="25">25</option>
                                                            <option value="50">50</option>
                                                            <option value="">All</option>
                                                        </select>
                                                        <span>per page</span>
                                                    </div><!-- End .showPages -->
                                                    <div class="clear"></div>
                                                </div><!-- End .pagination -->                                              
                                            </div><!-- End .container2Content -->
                                            </div></div>
                                    </div></div></div>
                                </div><!-- End .container2 -->
                    </div>
                </div>
            </div><!-- End #addSearch -->
            
            <!-- hidden example group/section/question -->
            <div class="addManually body example">
                <p>Question Text:</p>
                <textarea cols="100" rows="2"  class="question"></textarea>
                <p>Question Guideline</p>
                <textarea cols="100" rows="7" class="guideline"></textarea>
                <p class="higher">
                    <label>Type:</label>
                    <g:select name="questionType" class="medium" from="${questionTypes}" optionKey="id" optionValue="name" />
                </p>
                <div class="clear"></div>
            </div><!-- End .addManually -->
            
            <div id="addMatch" class="addWindow">
                <div class="header">
                    <p class="title">Matching Scorecard Details</p>
                    <a href="javascript:;" class="button7 newButtonOrange closeDialog modalTitleBtn"><span class="left"><span class="right">CLOSE</span></span></a>
                </div>
                <div class="content">
                <div class="areaHeader topSpace">
                    
                    <div class="expandCollapse">
                        <a class="expandAll" href="javascript:showGroup('group','ratings');"><strong>Expand All</strong></a>&nbsp;&nbsp;<span class="borderPipe">|</span>&nbsp;
                        <a class="collapseAll" href="javascript:hideGroup('group','ratings');"><strong>Collapse All</strong></a>&nbsp;<a class="button6 newButtonGreen green copyScorecard" href="javascript:;"><span class="left"><span class="right">COPY</span></span></a>
                    </div>
                    <p class="leftAlign"><a class="button5 back" href="javascript:;"><span class="left"><span class="right">BACK TO SEARCH RESULT</span></span></a></p>
                    <div class="clear"></div>
                </div><!-- End .areaHeader -->
                <div class="body">
                    
                </div><!-- End .body -->
                <div class="areaHeader bottomSpace">
                    <div class="expandCollapse">
                        <a class="expandAll" href="javascript:showGroup('group','ratings');"><strong>Expand All</strong></a>&nbsp;&nbsp;<span class="borderPipe">|</span>&nbsp;
                        <a class="collapseAll" href="javascript:hideGroup('group','ratings');"><strong>Collapse All</strong></a>&nbsp;<a class="button6 newButtonGreen green copyScorecard" href="javascript:;"><span class="left"><span class="right">COPY</span></span></a>
                    </div>
                    <p class="leftAlign"><a class="button5 back" href="javascript:;"><span class="left"><span class="right">BACK TO SEARCH RESULT</span></span></a></p>
                    <div class="clear"></div>
                </div><!-- End .areaHeader -->
                </div><!-- End .content -->
            </div><!-- End #addMatch -->
            
        </div><!-- End .dialogContent -->
        
        
        <div class="biggroup hide example">
            <table class="scorecard view2 edit group" width="100%" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th class="first">
                            <div class="relative"><div class="move group"></div></div>
                            <strong>Group:</strong>&nbsp;&nbsp;<input type="text" class="longText groupName" value="" name="groupName"/>
                        </th>
                        <th colspan="4">
                            <strong>Weight</strong>&nbsp;&nbsp;<input type="text" class="shortText weightG" value="0.0" name="groupWeight" />
                            <a class="remIcon" href="javascript:;"></a><a class="addIcon" href="javascript:;"></a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="hide"><td></td></tr>
                </tbody>
            </table>
            <div class="sections">
            <table class="scorecard view2 edit example section" width="100%" cellpadding="0" cellspacing="0">
                <tbody class="section">
                    <tr class="section">
                        <td class="first">
                            <div class="relative"><div class="move section"></div></div>
                            <strong>Section:</strong>&nbsp;<input type="text" class="longText sectionText" value="" name="sectionName" />
                        </td>
                        <td colspan="4">
                            <strong>Weight:</strong>&nbsp;<input type="text" class="shortText weightS" value="0.0" name="sectionWeight" />
                            <a class="remIcon" href="javascript:;"></a><a class="addIcon" href="javascript:;"></a>
                        </td>
                    </tr>
                    <tr class="questionHead">
                        <td class="first">Question</td>
                        <td>Type</td>
                        <td>Weight</td>
                        <td>Document Upload</td>
                        <td class="btn"><a class="addIcon" href="javascript:;"></a>
                            <div class="addmenu">
                                <a class="addManually" href="javascript:;" >Add Manually</a><br />
                                <a class="addReq" href="javascript:;">Add from Requirements</a><br />
                                <a class="addScorecard" href="javascript:;">Add from other scorecards</a>
                            </div>
                        </td>
                    </tr>
                    <tr class="question hide">
                        <td class="first">
                            <div class="relative"><div class="move question"></div></div>
                            <p>Question Text:</p>
                            <textarea cols="100" rows="2"  class="question" name="questionDescription"></textarea>
                            <p>Question Guideline</p>
                            <textarea cols="100" rows="7" class="guideline" name="questionGuideline"></textarea>
                        </td>
                        <td>
                           <g:select class="type edit" name="questionType" from="${questionTypes}" optionKey="id" optionValue="name" />
                        </td>
                        <td><input type="text" class="shortText weightQ" name="questionWeight" value="0.0" /></td>
                        <td>
                           <g:select from="${['N/A','Optional','Required']}" name="questionUploadDocument" />
                        </td>
                        <td class="btn"><a class="remIcon" href="javascript:;"></a></td>
                    </tr>
                    <tr class="subtotal">
                        <td class="right">
                            <strong>Question Weights:</strong>
                        </td>
                        <td class="left" colspan="4">
                            <input type="text" class="shortText weightSumQuestions red" value="0.0"/>
                            <span>Sum of question weights within a section must total 100.</span>
                        </td>
                    </tr>
                </tbody>
            </table><!-- End .scorecard.view2.example -->
            </div>
            <table class="scorecard view2 edit group sumSections" width="100%" cellpadding="0" cellspacing="0">
                <tfoot>
                    <tr>
                        <th class="right first">
                            <strong>Section Weights:</strong>
                        </th>
                        <th class="left" colspan="4">
                            <input type="text" class="shortText weightSumSections green" value="0.0" /><span>Sum of section weights within a group must total 100.</span>
                        </th>
                    </tr>
                </tfoot>
                <tbody>
                <tr class="hide"><td></td></tr>
                </tbody>
            </table>
        </div>
        <script type="text/javascript">
        $(document).ready(function() {
            $("#projectType").change();
        });
        </script>
    </body>
</html>
