/**
 * The JS script for dashboard views.
 *
 * Version 1.1 (TC Cockpit Performance Improvement Enterprise Dashboard 1) updates
 * - Added the logic for retrieving data for Enterprise Health area of Enterprise Dashboard view via AJAX call and
 * - rendering the data on page.
 *
 * Version 1.1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info)
 * - Fix a small bug causing js error in the project overview page.
 *
 * Version 1.1.2 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0)
 * - Fix a small rendering issue for project health table.
 *
 * Version 1.2 (TC Direct Rebranding Assembly Project and Contest related pages)
 * - Remove the padding added for active challenges health table after rebranding
 *
 * @author isv, GreatKevin, csy2012, TCSASSEMBLER
 * @version 1.2
 */
$(document).ready(function(){
    // Color the rows for projects in Enterprise Health area
    $('#enterpriseHealthTable tr:even').addClass('even');

    /* init date-pack */
    if($('.date-pick').length > 0){
        $(".date-pick").datePicker({startDate:'01/01/2001'});
    }
    
    /* dashboard view Table expand/collapse function */ 
    $(".dashboardTable .expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(".dashboardTable dd").show();
            $(this).removeClass("collapse");
        }else{
             $(".dashboardTable dd").hide();
             $(this).addClass("collapse");
        }
    });

    $(".chartCollapse a.expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(".chartContent").show();
            $(this).removeClass("collapse");
        }else{
             $(".chartContent").hide();
             $(this).addClass("collapse");
        }
    });

    /* show details */
    $(".dashboardTable a.triggerDetail").click(function(){
         $(this).blur();
        var detail = $(this).parents("td").find(".detail");
        if(detail.is(":hidden")){
            $(this).text("Hide Details");
        }else{
            $(this).text("View Details"); 
        }
        detail.toggle();
    });
    
    $(".dashboardTable .viewall input").change(function(){
        $(".dashboardTable td .detail").hide();
        $(".dashboardTable a.triggerDetail").text("View Details");
        if($(this).attr("checked")){
            $(".dashboardTable a.triggerDetail").trigger("click");                
        }
    })
    
    var $select = $("#EnterpriseDashboardForm_formData_projectId");
    if ($select.length > 0) {
        var selectedVal = $select.val();
        var $options = $('option', $select);
        var arrVals = [];
        $options.each(function(){
            arrVals.push({
                val: $(this).val(),
                text: $(this).text()
            });
        });
        arrVals.sort(function(a, b){
            if(a.text > b.text){
                return 1;
            }
            else if (a.text == b.text){
                return 0;
            }
            else {
                return -1;
            }
        });
        for (var i = 0, l = arrVals.length; i < l; i++) {
            $($options[i]).val(arrVals[i].val).text(arrVals[i].text);
        }
        $select.val(selectedVal);
    }
    
    if ($("#enterpriseHealthTable").length != 0) {
        // Load project contests health data via AJAX call
        $.ajax({
                   type:'POST',
                   url:'dashboardEnterpriseHealthAJAX',
                   cache:false,
                   dataType:'json',
                   timeout: 100000,
                   error:function (e) {
                       $('#loaderEnterpriseHealth').remove();
                       $('#enterpriseHealthTableBody').empty();
                       $('#enterpriseHealthTableBody').html('<tr class="odd"><td valign="top" colspan="10">Failed to retrieve data</td></tr>');
                   },
                   success:function (jsonResult) {
                       handleJsonResult(jsonResult,
                                        function (result) {
                                            $('#loaderEnterpriseHealthWrapper').remove();
                                            $('#loaderEnterpriseHealth').remove();
                                            $('#enterpriseHealthTableBody').empty();
                                            var n = result.length;
                                            for (var i = 0; i < n; i++) {
                                                var p = result[i];
                                                var html =
                                                        '<tr>' +
                                                        '<td class="first">' +
                                                        '<a class="longWordsBreak ' + p.projectStatusColor.toLowerCase() + '"' +
                                                        'href="projectOverview?formData.projectId=' + p.project.id +
                                                        '">' +
                                                        p.project.name + '</a>' +
                                                        '</td>' +
                                                        '<td><span class="">' +
                                                        p.averageContestDuration.toFixed(0) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        '$' + p.averageCostPerContest.toFixed(2) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        '$' + p.totalProjectCost.toFixed(2) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        p.averageFulfillment.toFixed(2) + '%</span></td>' +
                                                        '</tr>';


                                                $('#enterpriseHealthTableBody').append(html);
                                            }
                                            if(n<5 && n>0){
                                            	var _html='<tr><td colspan="5"/></tr>';
                                            	$('#enterpriseHealthTableBody').append(_html);
                       						}
                                            
                                        },
                                        function (errorMessage) {
                                            showServerError(errorMessage);
                                            $("#contestLoading").hide();
                                        });
                   }
               });
    }

    function formatEDTDate(time) {
        //format date into string like "03/26/2016 09:00 EDT"
        var result = "";
        
        result += (time.month < 9 ? "0" : "") + (time.month+1) + "/";
        result += (time.date < 10 ? "0" : "") + time.date + "/";
        result += (time.year + 1900) + " ";
        result += (time.hours < 10 ? "0" : "") + time.hours + ":";
        result += (time.minutes < 10 ? "0" : "") + time.minutes + " EDT";

        return result;
    }

    function generateSectionHeader(contestData, healthData, type) {
        var copilotContestDetailLink = "/direct/copilot/copilotContestDetails.action?projectId=" + contestData.id,
            contestDetailLink = "/direct/contest/detail.action?projectId=" + contestData.id;

        var row = "<td>";
        if (contestData.contestTypeName === "Copilot Posting") {
            row += "<a class='tooltopsBtn' href='" + copilotContestDetailLink + "'>";
        } else {
            row += "<a class='tooltopsBtn' href='" + contestDetailLink + "'>";
        }
        if (type === 'Timeline') {
            row += '<span class="' + healthData.phaseStatusColor.toLowerCase() + '"></span></a>';
        } else if (type === 'Registration') {
            row += '<span class="' + healthData.regStatusColor.toLowerCase() + '"></span></a>';
        } else if (type === 'Review') {
            row += '<span class="' + healthData.reviewersSignupStatusColor.toLowerCase() + '"></span></a>';
        } else if (type === 'Forum') {
            row += '<span class="' + healthData.forumActivityStatusColor.toLowerCase() + '"></span></a>';
        } else if (type === 'Dependencies') {
            row += '<span class="' + healthData.dependenciesStatusColor.toLowerCase() + '"></span></a>';
        } else if (type === 'Issue Tracking') {
            row += '<span class="' + healthData.contestIssuesColor.toLowerCase() + '"></span></a>';
        }
        
        row += '<div class="tooltipBox">' + 
            '<span class="arrow"></span>' + 
            '<div class="tooltipHeader">' + 
                '<div class="tooltipHeaderRight">' + 
                    '<div class="tooltipHeaderCenter">' + 
                        '<h2>' + type + '</h2>' + 
                    '</div>' + 
                '</div>' + 
            '</div>';

        row += '<div class="tooltipContent">';
        return row;
    }

    function generateSectionFooter() {
        var row = "</div>";
        row += '<div class="tooltipFooter">' + 
                '<div class="tooltipFooterRight">' + 
                    '<div class="tooltipFooterCenter"></div>' + 
                '</div>' + 
            '</div>' + 
        '</div>';
        row += "</td>";
        return row;
    }

    function generateProjectHealthTable(allData) {
        var result = JSON.parse(allData);
        var data = result['result']['return'];

        var renderedResult = "";
        for (var i=0;i<data.length;i++) {
            var contestData = data[i].contest,
                healthData = data[i].healthData;

            var currentPhaseStartTime = healthData.dashboardData.currentPhase.startTime,
                currentPhaseEndTime = healthData.dashboardData.currentPhase.endTime,
                currentPhaseName = healthData.dashboardData.currentPhase.phaseName,
                nextPhaseName = healthData.dashboardData.nextPhase ? healthData.dashboardData.nextPhase.phaseName : "",
                nextPhaseStartTime = healthData.dashboardData.nextPhase ? healthData.dashboardData.nextPhase.startTime : null,
                numberOfRegistrants = healthData.dashboardData.numberOfRegistrants;

            var copilotContestDetailLink = "/direct/copilot/copilotContestDetails.action?projectId=" + contestData.id,
                contestDetailLink = "/direct/contest/detail.action?projectId=" + contestData.id;

            var row = "<tr " + (i%2 === 0 ? "class='even'" : "") + ">";

            //first td
            row += "<td class='first'>";
            if (contestData.contestTypeName === "Copilot Posting") {
                row += "<a class='longWordsBreak " + healthData.contestStatusColor.toLowerCase() + "' href='" +
                    copilotContestDetailLink + "'>";
            } else {
                row += "<a class='longWordsBreak " + healthData.contestStatusColor.toLowerCase() + "' href='" +
                    contestDetailLink + "'>";
            }
            row += contestData.title;
            row += "</a>";
            row += "</td>";

            //second td
            row += "<td class='alignTop'>";
            row += "<div class='unitContent'>" + contestData.contestTypeName + "</div>";
            row += "</td>";

            //third td
            row += '<td class="alignTop">';
            row += '<div class="unitContent">' + currentPhaseName + '&nbsp;</div>'
            row += '</td>';

            row += '<td class="alignTop">';
            row += '<div class="unitContent dateNotice">' + nextPhaseName + '&nbsp;</div>'
            row += '<div class="unitContent dateFullFormat">';
            if (nextPhaseStartTime != null) {
                row += formatEDTDate(nextPhaseStartTime);
            }
            row += '</div>';
            row += '</td>';

            //Timeline
            row += generateSectionHeader(contestData, healthData, "Timeline");

            var currentPhaseStatus = healthData.currentPhaseStatus;
            row += "<h3>";
            if (currentPhaseStatus === "RUNNING") {
                row += currentPhaseName + " is running."
            } else if (currentPhaseStatus === "CLOSING") {
                row += currentPhaseName + " is closing."
            } else if (currentPhaseStatus === "LATE") {
                row += currentPhaseName + " is late."
            } else {
                row += "There is no running phase.";
            }
            row += "</h3>";
            if (healthData.dashboardData.nextPhase) {
                row += "<p>Start Time:" + formatEDTDate(currentPhaseStartTime) + "</p>";
                row += "<p>End Time:" + formatEDTDate(currentPhaseEndTime) + "</p>";
            }
            row += generateSectionFooter();
            

            //Registration
            row += generateSectionHeader(contestData, healthData, "Registration");
            var regStatus = healthData.registrationStatus;
            if (regStatus === 'REGISTRATION_LESS_IDEAL_ACTIVE') {
                row += '<h3>Registration is less than ideal.</h3>' + 
                        '<p>Consider increasing prize money and double-check the clarity and scope of your challenge.</p>';
            } else if (regStatus === 'REGISTRATION_LESS_IDEAL_CLOSED') {
                row += '<h3>Registration is less than ideal.</h3>' + 
                        '<p>Consider re-opening registration and increasing prize money.</p>';
            } else if (regStatus === 'REGISTRATION_POOR') {
                row += '<h3>Registration is poor.</h3>' + 
                        '<p>It is unlikely you will receive good submissions. Consider reposting.</p>';
            } else {
                row += '<h3>Registration is healthy.</h3>';
            }
            row += "<p><strong># of registrants</strong> :" + numberOfRegistrants + "</p>";
            row += generateSectionFooter();

            //Review
            row += generateSectionHeader(contestData, healthData, "Review");
            row += '<p><strong>' + healthData.dashboardData.reviewers.length + '</strong> of '+
                '<strong>' + healthData.dashboardData.requiredReviewersNumber + '</strong> reviewers are registered.</p>';
            row += generateSectionFooter();

            //Forum
            row += generateSectionHeader(contestData, healthData, "Forum");
            row += '<p><strong>' + healthData.unansweredForumPostsNumber + '</strong> unanswered threads.</p> ' +
                    '<p><a href="' + healthData.dashboardData.forumURL + '">View Thread</a></p>';
            row += generateSectionFooter();

            //Dependencies
            row += generateSectionHeader(contestData, healthData, "Dependencies");
            var depStatus = healthData.dependenciesStatus;
            if (depStatus === 'DEPENDENCIES_NON_SATISFIED') {
                row += 'Some dependencies are not satisfied.';
            } else if (depStatus === 'DEPENDENCIES_SATISFIED') {
                row += 'All dependencies are satisfied.';
            } else if (depStatus === 'NO_DEPENDENCIES') {
                row += 'This challenge has no dependencies.';
            }

            row += generateSectionFooter();

            //Issue Tracking
            row += generateSectionHeader(contestData, healthData, "Issue Tracking");
            row += '<a class="viewDetailsLink" href="' + 
                '/direct/contestIssuesTracking.action?projectId=' + contestData.id + '&amp;subTab=issues">'
            row += 'View Details</a>';
            row += '<p>Open Issue : <strong>' + healthData.unresolvedIssuesNumber + '</strong></p>';
            row += '<div class="clearFix"></div>';
            row += generateSectionFooter();
            row += "</tr>\n";

            renderedResult += row;
        }

        return renderedResult
    }

    if ($("#projectHealthTable").length != 0) {
        $('#loaderProjectHealthWrapper td').width($('.projectHealthHeader').width());
        
        // Load project contests health data via AJAX call
        var c;
        var request = {};
        request['projectId'] = tcDirectProjectId;
        $.ajax( {
            type : 'GET',
            url : 'projectContestsHealthAJAX' + '?projectId=' + tcDirectProjectId,
            cache : false,
            error : function(e) {
                $('#loaderProjectHealth').remove();
                $('#projectHealthTableBody').empty();
                $('#projectHealthTableBody').html('<tr class="odd"><td valign="top" colspan="10">Failed to retrieve data</td></tr>');
            },
            success : function(result) {
                var haha = generateProjectHealthTable(result);
                $('#loaderProjectHealthWrapper').remove();
                $('#loaderProjectHealth').remove();
                $('#projectHealthTableBody').empty();
                $('#projectHealthTableBody').append(generateProjectHealthTable(result));
                c = result;
                $("#projectHealthTable").dataTable({
                        "bAutoWidth": false,
                        "bPaginate": false,
                        "bInfo": false,
                        "bFilter": false,
                        "bSort": true,
                        "aaSorting": [[0,'asc']],
                        "aoColumns": [
                                        { "sType": "html", "sWidth": '26%' },
                                        { "sType": "html", "sWidth": '13%' },
                                        { "sType": "html", "sWidth": '10%' },
                                        { "sType": "html", "sWidth": '14%' },
                                        { "sType": "html", "sWidth": '7%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' }
                                ],
                        "sScrollY": "156px",
                        "oLanguage": {
                            "sEmptyTable": '<tr class="odd"><td valign="top" colspan="10" align="center" class="dataTables_empty">No data available in table</td></tr>'                               
                        }
                    });
                
                adjustTables();
                
                $(".projectHealthBody").scroll(function(){
                    $('.tooltipArea .tooltipBox').hide();
                });
    
                $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                    $(this).data('dateFull',$.trim($(this).text()));
                });
                
                $('.projectHealthBody .dataTables_scrollBody').height($('.projectHealthBody').height());
                $('.dataTables_scrollHead').remove();
                $(('.dataTables_scrollBody')).css('display', 'block');
                
                initTooltips();
            }
        });

        if ($.browser.msie) {
            $(window).resize($.debounce(200, function() {
                truncateProjectHealthTable();
                adjustTables();
            }));
        } else {
            $(window).resize(function() {
                truncateProjectHealthTable();
                adjustTables();
            });
        }

        truncateProjectHealthTable();
        
    }    

    //Truncate Project Health Table
    var truncArr,arrKey,arrResult;
    truncArr = {
        'Submissions'    :    "Subs",
        'Submission'    :    "Sub",
        'R1 Submissions'    :    "R1 Subs",
        'R2 Submissions'    :    "R2 Subs",
        'Winner Announcement'    :    "Winner",
        'Specification Submission'    :    "Spec Sub",
        'Specification Review'    :    "Spec Review",
        'Appeals Response'    :    "Appeals Res"
    };
    
    //set words for special TH and TD in 1024*768 and 1440 * 960, etc
    function truncateProjectHealthTable(){
        var width = $(window).width();
        
        if(width<1580){ // for 1024 * 768 and 1440 * 960 ,etc
            $(".projectHealthHeader thead th:eq(5)").text("Reg");
            $(".projectHealthHeader thead th:eq(8)").text("Depend");
            $(".projectHealthHeader thead th:eq(9)").text("Issue");
        }else{
            $(".projectHealthHeader thead th:eq(5)").text("Registration");
            $(".projectHealthHeader thead th:eq(8)").text("Dependencies");
            $(".projectHealthHeader thead th:eq(9)").text("Issue Tracking");
        }
        
        if(width < 1260){ // for 1024 * 768 
            $(".projectHealthHeader thead th:eq(2)").text("Current");
            $(".projectHealthHeader thead th:eq(3)").text("Next");
            $(".projectHealthBody tbody tr td:nth-child(3) div, .projectHealthBody tbody tr td .dateNotice").each(function(){
                arrKey = $.trim($(this).text());
                if(typeof truncArr[arrKey] != "undefined"){
                    $(this).text(truncArr[arrKey]);
                }
            });
            
            $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                var date = $(this).text().split(" ");
                if (date[0].indexOf('/') >= 0) {
                    if (date[0].split('/')[2].length) {
                        if (date[0].split('/')[2].length == 4) {
                            $(this).text(date[0].split('/')[0] + "/" + date[0].split('/')[1] + "/" +
                                         date[0].split('/')[2].substring(2) + " " + date[1]);
                        }
                    }
                }
            });
            
        }else{
            $(".projectHealthHeader thead th:eq(2)").text("Current Phase");
            $(".projectHealthHeader thead th:eq(3)").text("Next Phase");
            $(".projectHealthBody tbody tr td:nth-child(3) div, .projectHealthBody tbody tr td .dateNotice").each(function(){
                arrResult = $.trim($(this).text());
                for(arrKey in truncArr){
                    if(arrResult == truncArr[arrKey]){
                        $(this).text(arrKey);
                    }
                }
            });
            $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                $(this).text($(this).data('dateFull'));
            });
        }
    }

    function adjustTables() {
if ($('.dataTables_scrollBody').length != 0) {        // Determine if scrollbar is displayed for table
        var d = $('.dataTables_scrollBody')[0];
        var o = 0;
        if (typeof(d.clientHeight) != 'undefined') {
            if (d.clientHeight < d.scrollHeight) {
                o = 17;
            }
        }
        
        if ($.browser.msie) {
            // $('.projectHealthHeader').css('padding-right', o + 'px');
            if (parseInt($.browser.version) == 7) {
                $('#projectHealthTable').css('width', $('#projectHealthTable').parent().width() - o);
            } else {
                $('#projectHealthTable').css('width', '100%');
            }
        } else {
            $('#projectHealthTable').css('width', '100%');
            // $('.projectHealthHeader table').css('padding-right', o + 'px');
        }
    }
}
    
    function initTooltips() {

        //set hover status for Project Health table
        $('.projectHealthBody #projectHealthTable tbody tr').hover(
                function() {
                    $('.projectHealthBody #projectHealthTable tbody tr').removeClass("hover");
                    $(this).addClass("hover");
                },
                function() {
                    $(this).removeClass("hover");
                }
        );


        //tooltops
        var tooltopsTimer = null, timeLong = 500;
        $('.tooltopsBtn').live('mouseover', function() {
            var p,targetHeight;
            var targetTT = $(this).siblings('.tooltipBox').clone();
            var targetTTColor = $(this).children('span').attr('class') + 'ToolTipBox';

            $('.tooltopsBtn').removeClass('onActive');
            $(this).addClass('onActive');

            if (tooltopsTimer) clearTimeout(tooltopsTimer);
            $('.tooltipArea').empty();
            $('.tooltipArea').append(targetTT.addClass(targetTTColor));
            $('.tooltipArea .tooltipBox').show();

            p = $(this).offset();
            targetHeight = $('.tooltipArea .tooltipBox').height();
            $(targetTT).css({'left':eval(p.left - 85), 'top':eval(p.top - targetHeight - 10)});

        });

        //tooltops clear
        $('.tooltipBox').live('mouseover', function() {
            if (tooltopsTimer) clearTimeout(tooltopsTimer);
            $('#projectHealthTable .onActive').closest('tr').addClass("hover");
        });
        $('.tooltopsBtn, .tooltipBox').live('mouseout', function() {
            tooltopsTimer = setTimeout(function() {
                $('.tooltipArea').empty();
            }, timeLong);
        });
    }
});
