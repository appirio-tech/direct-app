/*
 * Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
 *
 * This javascript handles the instant search and search all.
 *
 * Version 1.0 (Module Assembly - TopCoder Cockpit Instant Search)
 *
 * Version 1.1 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 * - Fix the issue if instance search input is not there
 *
 * Version 1.2 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 * - Make instant search supports scorecard tool
 *
 * @Author GreatKevin
 * @Version 1.2
 */
var instantSearchDelay = 500;
var searcherId = 0;
var searcherLength = 0;
var queryURL = "/direct/instantSearch";

function loadInstantSearch() {
    if ($('.instantSearch input').length == 0) {
        return;
    }

    var q = $('.instantSearch input').val();
    if (q.length >= 3 && q != instantSearchHint && q.length !== searcherLength) {
        searcherLength = q.length;

        // use ajax to fectch the data
        $('.resultDropDown').show();
        $(".ajaxLoading").show();

        clearTimeout(searcherId);

        // enforce the delay
        searcherId = setTimeout(function () {
            $.ajax({
                type: 'POST',
                url: queryURL,
                data: {"searchKey": q},
                cache: false,
                beforeSend: function() {},
                dataType: 'json',
                async: true,
                success: function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            $('.quickResultList').each(function () {
                                $(this).empty();
                                var html = '';
                                var isAdmin = result['isAdmin'];
                                for (var label in result) {
                                    if (label == 'isAdmin') continue;

                                    var item = result[label];
                                    var display = label;
                                    if (label.toLowerCase() == 'contests') {
                                        display = "Challenges";
                                    }
                                    
                                    html += '<h3>' + display + '</h3><ul>';
                                    var resultSize = 0;
                                    if (item) {
                                        for (var i = 0; i < item.length; i++) {
                                            resultSize++;
                                            var it = item[i];
                                            var showTitle, showBrief, titleLink, titleText;
                                            if (label.toLowerCase() == 'contests') {
                                                showTitle = it.contestName;
                                                titleLink = ctx + "/contest/detail.action?projectId=" + it.contestId;
                                                var projectName = it.projectName;
                                                if ((it.contestType + " , " + it.projectName).length > 50) {
                                                    projectName = it.projectName.substring(0, 48 - it.contestType.length) + " ...";
                                                }
                                                showBrief = it.contestType + " , <a target='_blank' class='searchAllSmallLink' title='" + it.projectName + "' href='" + ctx + "/projectOverview.action?formData.projectId=" + it.projectId + "'>" + projectName + "</a>";
                                                if (showTitle.length > 45) {
                                                    showTitle = showTitle.substring(0, 43) + " ...";
                                                }
                                                titleText = it.contestName;
                                            } else {
                                                showTitle = it.projectName;
                                                titleLink = ctx + "/projectOverview.action?formData.projectId=" + it.projectId;
                                                showBrief = '';
                                                if (it.projectType && it.projectType != '-') {
                                                    showBrief += it.projectType;
                                                }
                                                if (it.clientName) {
                                                    showBrief = showBrief + " , " + it.clientName;
                                                }
                                                if (showTitle.length > 45) {
                                                    showTitle = showTitle.substring(0, 43) + " ...";
                                                }
                                                if (showBrief.length > 50) {
                                                    showBrief = showBrief.substring(0, 48) + " ...";
                                                }
                                                titleText = it.projectName;
                                            }
                                            if (showTitle.length > 45) {
                                                showTitle = showTitle.substring(0, 43) + " ...";
                                            }


                                            html += '<li><p class="resultTilte"><a target="_blank" title="' + titleText + '" href="' + titleLink + '">' + showTitle + '</a></p><p class="resultBrief">' + showBrief + '</p></li>';
                                        }
                                        
                                        if (resultSize == 0) {
                                            html += '<li class="noMatch"><p class="resultBrief">No Matched ' + display + '</p></li>';
                                        }
                                    }
                                    html += '</ul>';
                                }
                                for (var label in instantSearchFeaturesList) {
                                    var item = instantSearchFeaturesList[label];
                                    var searchCriteria = $('.instantSearch input').val().toLowerCase();
                                    html += '<h3>' + label + '</h3><ul>';

                                    var j = 0;

                                    if (item) {
                                        for (var i = 0; i < item.length; i++) {

                                            var it = item[i];
                                            var title = it.title.toLowerCase();
                                            var group = it.group.toLowerCase();

                                            if (group == "admin" && isAdmin.toLowerCase() != 'true') continue;

                                            if ((title.indexOf(searchCriteria) >= 0) | (group.indexOf(searchCriteria) >= 0)) {
                                                var showTitle = it.title;
                                                var showBrief = it.brief;
                                                if (showTitle.length > 45) {
                                                    showTitle = it.title.substring(0, 43) + " ...";
                                                }
                                                if (showBrief.length > 50) {
                                                    showBrief = it.brief.substring(0, 48) + " ...";
                                                }

                                                html += '<li><p class="resultTilte"><a target="_blank" href="' + it.link + '">' + showTitle + '</a></p><p class="resultBrief">' + showBrief + '</p></li>';
                                                j += 1;
                                            }
                                        }
                                    }

                                    if (j == 0) {
                                        html += '<li class="noMatch"><p class="resultBrief">No Matched ' + label + '</p></li>';
                                    }
                                    html += '</ul>';
                                }

                                html += "<div class='moreInfoArea'><a href='" + ctx + "/searchAll?searchKey=" + $('.instantSearch input').val() + "'>See more results for <span id='searchMoreKey'/>"  + "</a><p>Displaying top 5 results</p></div>";
                                $(this).html(html);
                                $("#searchMoreKey").text(q);
                            });
                            $(".ajaxLoading").hide();
                            $(".quickResultList").show();
                        },
                        function (errorMessage) {
                            showServerError(errorMessage);
                        })
                }
            });
        }, instantSearchDelay);
    }
};

var instantSearchHint = "Search for challenges, projects and features";

$(document).ready(function() {
    loadInstantSearch();

    /*Focus / Blur Effect*/

    $('.instantSearch input').focus(function() {
        if($(this).val()==instantSearchHint){
            $(this).val("");
        }
        $(this).addClass("focused");
//        if($(this).val().length >2) {
//            $(".resultDropDown").show();
//            $(".quickResultList").show();
//        }

    });

    //Reset Instant Search Hint Text
    $('.instantSearch input').blur(function() {
        if($(this).val()=="") {
            $(this).val(instantSearchHint);
            $(this).removeClass("focused");
        }
    });

    //Perform searhing for each keyup
    $('.instantSearch input').keyup(function() {
        $(".quickResultList").hide();
        if($(this).val().length >2) {
            isInstantSearchDirty = true;
            loadInstantSearch();
        }else{
            $(".resultDropDown").hide();
            $(".ajaxLoading").hide();
        }
    });

    //Hide Instant Search Dropdown when click outside
    $(document).click(function(event){
        if( $(event.target).parents('.instantSearch').length <1  ){
            $('.resultDropDown').hide();
        }
    })

    //Hide Instant Search Dropdown when click outside
    $(document).click(function(event){
        if( $(event.target).parents('.instantSearch').length <1  ){
            $('.resultDropDown').hide();
        }
    })

    //Search Results Page Filter Click
    $('.resultsArea .resultsFilter a').click(function() {
        $('.resultsArea .resultsFilter li').removeClass("on");
        $('.resultsArea .filterArea').removeClass("firstOn").removeClass("lastOn");
        $(this).parent().addClass("on");

        if( $(this).parent().hasClass("first") ){
            $('.resultsArea .filterArea').addClass("firstOn");
        }
        if( $(this).parent().hasClass("last") ){
            $('.resultsArea .filterArea').addClass("lastOn");
        }

        var targetName = "type" + $(this).text();

        $('.resultsArea .resultsList h2').text( $(this).text() );


        if ($(this).text() == "All Results"){
            $('.resultsArea .resultsList .typeContests').show();
            $('.resultsArea .resultsList .typeProjects').show();
            $('.resultsArea .resultsList .typeFeatures').show();
        }else{
            $('.resultsArea .resultsList .typeContests').hide();
            $('.resultsArea .resultsList .typeProjects').hide();
            $('.resultsArea .resultsList .typeFeatures').hide();
            $('.resultsArea .resultsList .' + targetName).show();
        }
    });

    if ($(".searchAllResults").length > 0) {
        var searchAllKey = $("input[name=searchAllKey]").val();
        var isAdmin = $("input[name=isTCAdmin]").val();
        for (var label in instantSearchFeaturesList) {
            var item = instantSearchFeaturesList[label];
            var searchCriteria = searchAllKey.toLowerCase();
            html = '';

            var j = 0;

            if (item) {
                for (var i = 0; i < item.length; i++) {
                    var it = item[i];
                    var title = it.title.toLowerCase();
                    var group = it.group.toLowerCase();

                    if(group == "admin" && isAdmin.toLowerCase() != 'true') continue;

                    if ((title.indexOf(searchCriteria) >= 0) | (group.indexOf(searchCriteria) >= 0)) {
                        var showTitle = it.title;
                        var showBrief = it.brief;

                        html = html + '<li><p class="resultTilte"><a target="_blank" href="' + it.link + '">' + showTitle
                           + '</a></p><p class="resultBrief">' + showBrief +'</p></li>'

                        j += 1;
                    }
                }
            }
        }

        if (j > 0) {
            $("ul.typeFeatures").html(html);
        }


        $(".resultsList ul").each(function(){
            $(this).find("li:last").addClass("lastLi");
        });
    }
});


var instantSearchFeaturesList = {
    'Features': [
        {
            "title":    "Enterprise Dashboard - Overview",
            "link":     "/direct/direct/enterpriseDashboard/overview",
            "brief":    "Provides overview of your cockpit projects at enterprise level",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Financials",
            "link":     "/direct/enterpriseDashboard/financial",
            "brief":    "Provides financial information like actual cost, budget usage, and projected cost of your cockpit projects.",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Project Health",
            "link":     "javascript:;",
            "brief":    "NOT implemented",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Roadmap",
            "link":     "/direct/enterpriseDashboard/roadmap",
            "brief":    "SearShows you the overdue, upcoming and completed milestones (roadmap) of your cockpit projects.ch",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Pipeline",
            "link":     "/direct/enterpriseDashboard/pipeline",
            "brief":    "Shows the numbers and chart of the contests pipeline of your cockpit projects.",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Community",
            "link":     "javascript:;",
            "brief":    "NOT implemented",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Analytics",
            "link":     "/direct/enterpriseDashboard/analysis",
            "brief":    "Provides analysis of average challenge duration, average challenge cost, project fulfillment and challenge volume of your cockpit projects, the data are showing in line charts.",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Active",
            "link":     "/direct/enterpriseDashboard/activeContests",
            "brief":    "Shows all your active contests, bug races",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Enterprise Dashboard - Latest Activities",
            "link":     "javascript:;",
            "brief":    "NOT implemented",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Latest Activities",
            "link":     "/direct/dashboardLatest",
            "brief":    "View latest activities of all your projects",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Roadmap Calendar",
            "link":     "/direct/calendar",
            "brief":    "View milestones calendar of all your projects",
            "group":    "Dashboard",
            "reqAdmin": "no"
        },
        {
            "title":    "Operations Dashboard",
            "link":     "/direct/operationsDashboardEnterprise.action",
            "brief":    "Administrator - Views fulfillment, Cost and Timeline data for all the projects running on TopCoder Platform",
            "group":    "Dashboard",
            "reqAdmin": "yes"
        },
        {
            "title":    "Client User Stats Report",
            "link":     "/direct/clientUserStatsReport.action",
            "brief":    "Administrator  - Views the report of client users who use the TopCoder Platform in past 12 months",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Pipeline Report",
            "link":     "/direct/dashboardReports.action",
            "brief":    "Detailed report of the contests pipeline of your cockpit projects",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "Cost Analysis Report",
            "link":     "/direct/dashboardCostReport",
            "brief":    "Detailed report of the contests cost of your cockpit projects",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "Invoice History Report",
            "link":     "/direct/dashboardBillingCostReport",
            "brief":    "Detailed report of the paid invoice",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "Participation Metrics Report",
            "link":     "/direct/dashboardParticipationReport",
            "brief":    "Report of number metrics on how many TopCoder members work on your cockpit projects as competitors and copilots. ",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "Project Metrics Report",
            "link":     "/direct/dashboardGetProjectMetricsReport",
            "brief":    "Detailed Report on different metrics of your cockpit projects - the numer of draft, scheduled, active and completed contests, cost data, fulfillment, project timeline and project status etc. ",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "Jira Issues Report",
            "link":     "/direct/dashboardGetJiraIssuesReport ",
            "brief":    "Detailed Report on Jira Issues and Bug Races of your Cockpit Projects",
            "group":    "Report",
            "reqAdmin": "no"
        },
        {
            "title":    "All Projects Management",
            "link":     "/direct/allProjects.action",
            "brief":    "Allow you to manage all your cockpit pages in one page",
            "group":    "Project",
            "reqAdmin": "no"
        },
        {
            "title":    "Notification Settings",
            "link":     "/direct/settings/notifications",
            "brief":    "Manage your forum / timeline notifications on your cockpit projects and contests",
            "group":    "Settings",
            "reqAdmin": "no"
        },
        {
            "title":    "Permission Settings",
            "link":     "/direct/settings/permissions",
            "brief":    "Manage the permissions of your cockpit projects - you can assign permission to new users to your project or change / delete existing permissions",
            "group":    "Settings",
            "reqAdmin": "no"
        },
        {
            "title":    "Challenge Fee Management",
            "link":     "/direct/settings/contestFee",
            "brief":    "Manage the challenge fee for all clients on TopCoder Platform",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Sync User",
            "link":     "/direct/settings/syncUser",
            "brief":    "Synchronize user information from TopCoder Persistence from JIRA",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Copilot Feedback Management",
            "link":     "/direct/manageCopilotFeedback",
            "brief":    "View, approve or reject all copilot feedback",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Member Badges Management",
            "link":     "https://" + SERVER_CONFIG_SERVER_NAME + "/tc?module=BadgeAdminHome",
            "brief":    "Manage the member badges and copilot badges of TopCoder users",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Virutal Machine Management",
            "link":     "/direct/dashboardVMAction",
            "brief":    "Create, assign or destory the virtual machines used for challenge, bug races etc",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Scorecard Management",
            "link":     "/direct/scorecard/",
            "brief":    "Manage the challenge scorecard - your can create new scorecard templates here",
            "group":    "Admin",
            "reqAdmin": "yes"
        },
        {
            "title":    "Launch Copilot Posting",
            "link":     "/direct/copilot/launchCopilotContest",
            "brief":    "Launch a new copilot posting for your cockpit project to recruite a copilot",
            "group":    "Copilot",
            "reqAdmin": "no"
        },
        {
            "title":    "Manage Copilot Postings",
            "link":     "/direct/copilot/listCopilotPostings",
            "brief":    "View and manage all your copilot postings",
            "group":    "Copilot",
            "reqAdmin": "no"
        },
        {
            "title":    "Manage Copilots",
            "link":     "/direct/copilot/manageCopilots",
            "brief":    "View and manage copilots of all your projects",
            "group":    "Copilot",
            "reqAdmin": "no"
        },
        {
            "title":    "Create New Challenge",
            "link":     "/direct/launch/home",
            "brief":    "Create new challenge for your project",
            "group":    "Project",
            "reqAdmin": "no"
        },
        {
            "title":    "Create New Project",
            "link":     "/direct/createNewProject.action",
            "brief":    "Create a new project to build your requirements",
            "group":    "Project",
            "reqAdmin": "no"
        }
    ]
};
