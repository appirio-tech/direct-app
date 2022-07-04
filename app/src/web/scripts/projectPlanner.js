/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * Javascript for for the project planner page.
 *
 * -version 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
 *
 * -version 1.1 (Release Assembly - TopCoder Cockpit Project Planner and game plan preview Update)
 * - Add preview and estimates calculation for the project planner and copilot submissions page
 * 
 * Version 1.2 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * 
 *
 * @author GreatKevin, TCCoder
 * @version 1.2 
 */
$(function(){

    var contestInterval = 72;
    var contestDescription;

    var updateVMCost = function (result) {

        if (!result && ($("select[name=billingAccount]").val() <= 0 || !$('input[name=useVM]').is(":checked"))) {
            $("#vmCost").text("$0.00");
            return 0;
        }

        var contests;

        if (result) {
            contests = result.contests;

            if (result.useVM == false) {
                return 0;
            }

        } else {
            contests = buildProjectTree(true);

            if(contests.circular) {
                contests = contests.contests;
            }

        }


        var vmCost = 0;
        $.each(contests, function (index, c) {
            if (c.type == '2' || c.type == '9' || c.type == '13' || c.type == '14') {
                vmCost += getContestTypeValue(c.type, 'cost') * 0.15;
            }
        })

        $("#vmCost").text('$' + vmCost.formatMoney(2));

        return vmCost;
    };

    $(':input').live('focus',function(){
        $(this).attr('autocomplete', 'off');
    });

    $("input[name=contestName]").val('');
    $("select[name=contestType]").val(0);

    $("input[name=contestName]").live('keydown keyup paste', limitContestProjectNameChars(200));
    $("input[name=contestName]").live('keydown keyup paste', updateVMCost);

    $("select[name=billingAccount]").change(function(){
        var billingAccountId = $(this).val();
        if(billingAccountId <= 0) {
            // clear all member cost and contest fee
            $("span.memberCost, span.contestFee").text('');
            return;
        }

        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/getProjectPlannerConfiguration",
            data: {billingAccountId: billingAccountId, formData: {projectId: $("input[name=directProjectId]").val()}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        contestDescription = result;
                        $("select[name=contestType]").trigger('change');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    })

    $("select[name=billingAccount]").trigger('change');

    // helper for getting contest type values from contest types description hash
    function getContestTypeValue(contestType, field) {
        if (contestDescription[contestType]) {
            if(contestDescription[contestType][field] == 0) {
                return 0;
            }
            return contestDescription[contestType][field] || '';
        } else {
            return '';
        }
    }

    function getContestTypeContestFee(contestType) {
        var cost = contestType >= 0 ? getContestTypeValue(contestType, 'cost') : '0';
        var fee = cost;

        if(getContestTypeValue(contestType, 'isFixedFee') == true) {
            fee = getContestTypeValue(contestType, 'fixedFee');
        } else {
            fee = cost * getContestTypeValue(contestType, 'percentageFee');
        }

        return fee;
    }

    // date formatter
    function formatDateTime(date){
        return [date.getMonth() + 1, date.getDate(), date.getFullYear()].join('/') + ' ' + date.getHours() + ':00';
    }

    var contestTable = $('#contestTable').find('tbody');

    function generateFollowContestOption(value, label, hidden){
        return '<option value="' + value + '"' + (hidden ? ' class="hide"' : '') + '>' + label + '</option>';
    }

    function generateDependOnContestOption(value, label, hidden){
        return '<li' + (hidden ? ' class="hide"' : '') + '><input name="contestFilter" type="checkbox" value="'+ value +'"><label>' + label + '</label></li>';
    }

    // add new contest
    function addContest(){

        var tableBody = contestTable;
        var rows = $('tr', tableBody);
        var count = rows.length;

        // copy template row if no contests exists
        if (count == 0) {
            var row = $('.contestRowTemplate').clone().removeClass('contestRowTemplate');
            $('.contestIndex', row).text(1);
            tableBody.append(row);
            updateDisabledControls();
            return;
        }

        // duplicate last row
        var lastRow = rows.last();
        var newRow = lastRow.clone();

        // create new elements for dependency multiselect and follow select
        var newItem = generateDependOnContestOption(count + 1, '', true);
        var newOption = generateFollowContestOption(count + 1, '', true);

        // append elements to existing rows
        rows.each(function(){
            $('.followContest', this).append(newOption);
            $('.contestDependencies ul', this).append(newItem);
        });

        // reset contest values
        $('input[name="contestName"]', newRow).val('');
        $('.contestType', newRow).val(0);
        $('.memberCost, .contestFee', newRow).text('');

        // append last row values to the new row select elements
        var lastName = $('input[name="contestName"]', lastRow).val();
        $('.followContest', newRow).val(0).append(generateFollowContestOption(count, lastName, !lastName));

        $('.contestDependencies ul', newRow).append(generateDependOnContestOption(count, lastName, !lastName))
            .find('input[name="contestFilter"]:checked').attr('checked', '');

        $('.contestDependencies', newRow).trigger('changelabel');

        // set new contest row index
        $('.contestIndex', newRow).text(count + 1);

        // append new row to table
        tableBody.append(newRow);

        // show hidden delete links
        $('.deleteContest.hide', tableBody).removeClass('hide');

        updateDisabledControls();
    }

    // add new contest row
    $('.addMoreContests').click(function(){
        // calling function with timeout to close opened selects, if any
        setTimeout(addContest, 0);
    });

    // delete contest
    function deleteContest(index){

        // update dependency multiselects
        var items = $('input[name="contestFilter"]', contestTable);
        items.each(function(){
            var value = this.getAttribute('value');

            // remove contest from multiselects
            if (value == index) {
                if (this.checked) {
                    this.checked = false;
                    $(this).closest('.contestDependencies').trigger('changelabel');
                }
                $(this).parent().remove();
                return;
            }

            // update index in appropriate checkboxes
            if (value > index) {
                this.setAttribute('value', value - 1);
            }
        });

        // update follow selects
        var options = $('.followContest option', contestTable);
        options.each(function(){
            var value = this.getAttribute('value');

            // remove contest from select
            if (value == index) {
                $(this).remove();
                return;
            }

            // update value in appropriate option
            if (value > index) {
                this.setAttribute('value', value - 1);
            }
        });

        // update row numeration
        $('.contestIndex', contestTable).each(function(index){
            this.innerHTML = index + 1;
        });

        updateDisabledControls();
    }

    // toggle controls disabled state
    function updateDisabledControls(){
        $('.followContest', contestTable).each(function(){
            if ($('option:not(.hide)', this).length > 1) {
                $(this).removeAttr('disabled');
            } else {
                $(this).attr('disabled', 'disabled');
            }
        });
        $('.contestDependencies', contestTable).each(function(){
            if ($('li:not(.hide)', this).length > 0) {
                $(this).removeClass('disabled');
            } else {
                $(this).addClass('disabled');
            }
        });
    }

    // set controls state at start
    updateDisabledControls();

    // change contest name
    var changeContestName = (function(){
        var lastChangedIndex;
        var lastChangedName;

        // wrap handler to closure to prevent double calling in non-IE browsers
        return function(event){
            var row = $(event.target).closest('tr');
            var tableBody = row.closest('tbody');
            var contestIndex = $('.contestIndex', row).text();
            var newName = event.target.value;

            // check values - if equals - it is double calling
            if (lastChangedIndex == contestIndex && lastChangedName == newName) return;

            var checkboxes = $('input[name="contestFilter"][value="' + contestIndex + '"]', tableBody);

            // change labels for all multiselect checkboxes
            var items = checkboxes.parent();
            items.toggleClass('hide', (newName == ''));
            $('label', items).html(newName).trigger('change');

            // change labels for all select options
            var options = $('.followContest option[value='+ contestIndex +']', contestTable);
            options.toggleClass('hide', (newName == ''));
            options.html(newName);

            // uncheck selected checkboxes if name became empty and set selects value to 0
            if (!newName) {
                checkboxes.filter(':checked').attr('checked', '');
                $('.followContest[value=' + contestIndex + ']', tableBody).val(0);
            } else {
                // hide validation alert when name filled
                $(event.target).siblings('.validation').hide();
            }

            lastChangedIndex = contestIndex;
            lastChangedName = newName;

            updateDisabledControls();
        }
    })();

    // needs to bind on both 'change' and 'blur' events
    // because IE<9 fired only 'blur' on first input change and only 'change' on next
    $('input[name="contestName"]', contestTable).live('change', changeContestName);
    $('input[name="contestName"]', contestTable).live('blur', changeContestName);

    // hide validation alert when type filled
    $('.contestType', contestTable).live('change', function(){
        if (this.value > 0) {
            $(this).siblings('.validation').hide();
            if($("select[name=billingAccount]").length) {
                if($("select[name=billingAccount]").val() <= 0) {
                    showErrors("Please choose a billing account first");
                }
            }
        } else {
            return;
        }

        // get member cost
        var cost = this.value >= 0 ? getContestTypeValue(this.value, 'cost') : '0';

        $(this).closest('tr').find('.memberCost').text('$' + parseFloat(cost).toFixed(1));
        $(this).closest('tr').find('.contestFee').text('$' + parseFloat(getContestTypeContestFee(this.value)).toFixed(1));

        updateVMCost();
    });

    // delete contest row
    $('.deleteContest', contestTable).live('click', function(event){
        var row = $(event.target).closest('tr');
        var index = $('.contestIndex', row).text();
        row.remove();
        deleteContest(index);

        // hide delete links if only one row left
        if ($('tr', contestTable).length <= 1) {
            $('.deleteContest', contestTable).addClass('hide');
        }

        updateVMCost();
    });

    // returns project tree for gantt chart if no circulars presents
    // returns circular contest array when config have circular
    function buildProjectTree(excludeEmpty){

        // sets contest time start and returns time end in normal case
        // returns circular contest array if circular appears
        function getContestTime(contest){

            // visited contest, so circular appears - return circular info
            if (contest.timeStart == -1) {
                return { circular: [contest.index] };
            }

            // time start already known - so return end
            if (contest.timeStart) {
                return parseInt(contest.timeStart) + parseInt(contest.timeLength);
            }

            // independent contest, so it can start at project start
            if (!(contest.dependsOn.length) && contest.follow <= 0) {
                contest.timeStart = 0;
                return contest.timeLength;
            }

            // mark current contest as visited
            contest.timeStart = -1;

            // calculate times for all contests on which depends this one
            var dependTimes = [];

            // check follow after contest, if set
            if (contest.follow > 0) {
                var followTime = getContestTime(contests[contest.follow - 1]);

                // circular was found
                if (followTime.circular) {
                    // circular closed, when contest circuit return to itself
                    if (followTime.circular[0] == contest.index) {
                        followTime.circularClose = true;
                    }
                    if (!followTime.circularClose) followTime.circular.push(contest.index);
                    return followTime;
                }
                dependTimes.push(followTime);
            }

            // check all depends on contests
            for (var i = 0; i < contest.dependsOn.length; i++) {
                var dependTime = getContestTime(contests[contest.dependsOn[i] - 1]);

                // circular was found
                if (dependTime.circular) {
                    // circular closed, when contest circuit return to itself
                    if (dependTime.circular[0] == contest.index) {
                        dependTime.circularClose = true;
                    }
                    if (!dependTime.circularClose) dependTime.circular.push(contest.index);
                    return dependTime;
                }
                dependTimes.push(dependTime);
            }

            // find the last contest on which depends this ends
            contest.timeStart = Math.max.apply(null, dependTimes) + contestInterval;

            return parseInt(contest.timeStart) + parseInt(contest.timeLength);
        }

        var contests = [];
        var circulars = [];

        // fills array with contest structures
        $('tr', contestTable).each(function(index){
            var contest = {
                index: index + 1,
                name: $('input[name="contestName"]', this).val(),
                type: $('.contestType', this).val(),
                follow: $('.followContest', this).val(),
                dependsOn: [],
                timeStart: null
            };
            $('input[name="contestFilter"]:checked', this).each(function(){
                contest.dependsOn.push(parseInt(this.getAttribute('value')));
            });
            contest.timeLength = parseInt(parseFloat(getContestTypeValue(contest.type, 'time') || 0).toFixed());
            contests.push(contest);
        });

        var projectStart = Date.parse($('input[name="startDate"]').val());

        // calculate timing for all contests
        for (var i = 0; i < contests.length; i++) {
            contests[i].timeEnd = getContestTime(contests[i]);

            // return circular info if circular appears
            if (contests[i].timeEnd.circular) {
                if (contests[i].timeEnd.circularClose) {
                circulars.push(contests[i].timeEnd);
                }
            }


            contests[i].startStr = formatDateTime(new Date(projectStart + contests[i].timeStart * 3600*1000));

            // subtract 24 hours from end time, because jsgantt includes whole last day to task timing
            contests[i].endStr = formatDateTime(new Date(projectStart + (contests[i].timeEnd - 24) * 3600*1000));

        }

        // sort contests on it's start time
        contests.sort(function(c1, c2){
            return c1.timeStart - c2.timeStart;
        });

        // filter empty contests (remove it if needed)
        if (excludeEmpty) {
            var filtered = [];
            for (var i = 0; i < contests.length; i++) {
                if (contests[i].name) filtered.push(contests[i]);
            }
            contests = filtered;
        }

        if (circulars.length) return ({
            circular: true,
            circulars: circulars,
            contests: contests
        });

        return contests;
    }

    // validate contest table filling
    function validateTableValues(){
        var valid = true;
        var empty = true;
        $('.validation', contestTable).hide();

        var firstErrors = [];

        if($("select[name=billingAccount]").length) {
            if($("select[name=billingAccount]").val() <= 0) {
                firstErrors.push("Please choose a billing account first");
            }
        }

        if(!$("input[name=startDate]").val()) {
            firstErrors.push("Please set the project start date");
        }


        if(firstErrors.length > 0) {
            showErrors(firstErrors);
            return false;
        }

        $('tr', contestTable).each(function(index){
            var contestName = $('input[name="contestName"]', this);
            var contestType = $('.contestType', this);
            var nameFilled = (!!contestName.val()) && $.trim(contestName.val()).length > 0;
            var typeFilled = contestType.val() != 0;
            if (nameFilled && typeFilled) {
                empty = false;
                return;
            }
            if ($('.followContest', this).val() != 0 ||
                $('input[name="contestFilter"]:checked', this).length ||
                nameFilled || typeFilled) {
                valid = false;
                empty = false;

                if (!nameFilled) {
                    contestName.siblings('.validation').show();
                }
                if (!typeFilled) {
                    contestType.siblings('.validation').show();
                }
            }
        });
        // show empty warning modal if no fields filled
        if (empty) {
            modalLoad('#projectPlanEmptyModal');
            return false;
        }
        return valid;
    }

    // show circular warning modal
    function showCircularsWarning(circulars){
        circulars = circulars.circulars;
        var circularText = [];
        for (var i = 0; i < circulars.length; i++) {
            circulars[i].circular.sort();
            circularText.push('Contests ' + circulars[i].circular.join(', '));
        }

        if (circularText.length > 1) {
            // show multiple circulars
            $('#ganttChartCircular').addClass('hide')
            $('#ganttChartCircularMultiple').removeClass('hide');
            $('#multipleCircularsList').html('<li>' + circularText.join('</li><li>') + '</li>');
        } else {
            // show single circular
            $('#ganttChartCircularMultiple').addClass('hide')
            $('#ganttChartCircular').removeClass('hide');
            $('#circularContestNumbers').text(circularText[0]);
        }

        modalLoad('#projectPlanCircularModal');
    }

    // generate contest table from json
    function importJSON(data){
        var template = $('.contestRowTemplate');

        var contests = data.contests;

        // sort contests by indexes
        contests.sort(function(contest1, contest2){
            return contest1.index - contest2.index;
        });

        var followOptions = [];
        var dependOnOptions = [];
        var i, contest, row, follows, depends;

        followOptions.push($('.followContest', template).html());

        // generate options
        for (i = 0; i < contests.length; i++) {
            followOptions.push(generateFollowContestOption(i + 1, contests[i].name, false));
            dependOnOptions.push(generateDependOnContestOption(i + 1, contests[i].name, false));
        }

        var tableBody = contestTable;
        tableBody.empty();

        // generate table rows
        for (i = 0; i < contests.length; i++) {
            contest = contests[i];
            row = template.clone().removeClass('contestRowTemplate');
            follows = followOptions.slice();
            depends = dependOnOptions.slice();
            follows.splice(i + 1, 1);
            depends.splice(i, 1);
            $('.contestIndex', row).text(i + 1);
            $('input[name="contestName"]', row).val(contest.name);
            $('.contestType', row).val(contest.type);
            $('.followContest', row).html(follows.join('')).val(contest.follow);
            $('.contestDependencies ul', row).html(depends.join(''));
            for (var j = 0; j < contest.dependsOn.length; j++) {
                $('input[name="contestFilter"][value="' + contest.dependsOn[j] + '"]', row).attr('checked', 'checked');
            }
            tableBody.append(row);
            $('.contestDependencies', row).trigger('changelabel');
        }
        updateDisabledControls();

        $("select[name=contestType]").trigger('change');

        if (data.useVM == true) {
            $("input[name=useVM]").attr('checked', 'checked');
        }
        else {
            $("input[name=useVM]").removeAttr('checked');
        }

        updateVMCost();
    }

    var projectPlanUploader =
        new AjaxUpload(null, {
            action: ctx + '/importProjectPlan',
            name: 'uploadProjectPlan',
            responseType: 'json',
            onSubmit: function (file, ext) {

                modalPreloader();
            },
            onComplete: function (file, jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        modalAllClose();
                        importJSON(result);
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                        modalClose();
                    });
            }
        }, false);

    // import from excel mock
    $('.importFromExcel').click(function(){

        if($("select[name=billingAccount]").val() <= 0) {
            showErrors("Please choose a billing account first");
            return false;
        }

        if(!$("input[name=uploadProjectPlan]").val()) {
            showErrors("Please choose the project plan excel file to import");
            return false;
        }

        projectPlanUploader._input = $("input[name=uploadProjectPlan]").get(0);

        projectPlanUploader.submit();

        if ($("input[name=uploadProjectPlan]").length == 0) {
            $('<input type="file" name="uploadProjectPlan"/>').insertBefore("a.importFromExcel");
        }

    });

    // export to excel mock
    $('.exportToExcel').click(function(){
        // returns if field fills validation failed
        if (!validateTableValues()) return;

        var contests = buildProjectTree(true);

        // show circular warning if contest circular appears
        if (contests.circular) {
            showCircularsWarning(contests);
            return;
        }

        // show possible send-to-server json for generate export excel file
        var exportJson = {
            useVM : $("input[name=useVM]").is(":checked"),
            contests:contests
        }


        $.post(ctx + "/exportProjectPlan", {projectPlan:exportJson}, function(retData) {
            $("body").append("<iframe src='" + (ctx + "/downloadProjectPlan?projectPlanExportKey=" + retData.result['return'].exportKey) + "' style='display: none;' ></iframe>");
        }, 'json');
    });

    // show project plan gantt chart
    $('.previewPlan').click(function(){
        // returns if field fills validation failed
        if (!validateTableValues()) return;



        var contests = buildProjectTree(true);

        // show circular warning if contest circular appears
        if (contests.circular) {
            showCircularsWarning(contests);
            return;
        }

        var totalMemberCost = 0;
        var totalContestFee = 0;

        $("span.memberCost").each(function(){
            var costText = $(this).text();
            if(costText.length > 0) {
                costText = costText.replace("$", "");
                totalMemberCost += parseFloat(costText);
            }
        });

        $("span.contestFee").each(function(){
            var feeText = $(this).text();
            if(feeText.length > 0) {
                feeText = feeText.replace("$", "");
                totalContestFee += parseFloat(feeText);
            }
        });

        var minHours = 10000000;
        var maxHours = -1;

        for(var i = 0; i < contests.length; ++i) {
            var value = contests[i];
            minHours = value.timeStart < minHours ? value.timeStart : minHours;
            maxHours = value.timeEnd > maxHours ? value.timeEnd : maxHours;
        }


        displayGanttChart(contests);

        extendStyle();
        calculateWidth();
        modalPosition();

        var vmCost = updateVMCost();

        var totalCost = totalContestFee + totalMemberCost + vmCost;

        $("#durationStat").text(((maxHours - minHours) / 24) + " Days");
        $("#totalStat").text(("$" + totalCost.formatMoney(0))).attr('title', 'Member Cost: $' + totalMemberCost + "  Fee: $" + totalContestFee + "  VM Cost: $" + vmCost);


    });

    function displayGanttChart(contests){
        // show gantt chart popup
        modalLoad('#jsGanttChartModal');

        // needs to use global 'g' variable because of jsgantt module structure
        window.g = new JSGantt.GanttChart('g',document.getElementById('jsGanttChartDiv'), 'day');
        g.setShowRes(1); // Show/Hide Responsible (0/1)
        g.setShowDur(1); // Show/Hide Duration (0/1)
        g.setShowComp(0); // Show/Hide % Complete(0/1)

        g.setCaptionType('topcoder');  // Set to Show Caption (None,Caption,Resource,Duration,Complete)

        if ( g ) {

            // append project plan parent title to chart
            g.AddTaskItem(new JSGantt.TaskItem(1000, $("span#projectTitleSpan").text() + ' - Project Plan', '', '', '000000', 'javascript:;', 0, '', 0, 1, 0, 1));

            // append all contests to chart
            for (var i = 0; i < contests.length; i++) {
                var contest = contests[i];
                g.AddTaskItem(new JSGantt.TaskItem(
                    contest.index,
                    contest.name,
                    contest.startStr,
                    contest.endStr,
                    getContestTypeValue(contest.type, 'color') || '177DE3',
                    'javascript:;',
                    0,
                    getContestTypeValue(contest.type, 'name'),
                    0,
                    0,
                    1000,
                    1,
                    contest.dependsOn.length ? contest.dependsOn : 0,
                    undefined,
                    undefined,
                    getContestTypeValue(contest.type, 'cost') + getContestTypeContestFee(contest.type)
                ));
            }

            g.Draw();
            g.DrawDependencies();
        }
    }

    // width and position calculate functions

    // sets gantt chart popup width
    function calculateWidth(){
        $('#jsGanttChartModal').css('width', ($(window).width() - 40) + 'px');
        $('#jsGanttChartDiv').css('maxHeight', ($(window).height() - 200) + 'px');
        calculateLeftWidth();
    }

    // sets gantt chart left and right parts width
    function calculateLeftWidth() {
        // setup the width
        var leftPanelWidth = 675;
        var leftside = $("#leftside");

        if ($("tbody tr", leftside).length > 4) {
            leftside.css('width', '675px');
            leftPanelWidth = $("tbody tr:eq(3)", leftside).outerWidth();
        }
        leftside.css('width', leftPanelWidth + 'px');

        var rightWidth = $(window).width() - 50;
        if (!leftside.is(":hidden")) {
            rightWidth -= leftside.outerWidth();
        }
        $("#rightside").css('width', rightWidth + 'px');
        $("#rightside table").css('width', (rightWidth - 10) + 'px');
    }

    // assign global variable for use from jsgantt.js module
    window.calculateLeftWidth = calculateLeftWidth;

    // toggle timing columns of gantt chart
    $("#hideColumnsControl").live('click', function () {
        if ($(this).is(":checked")) {
            $("#leftside tbody tr").each(function () {
                $(this).find("td:gt(2):lt(7)").hide();
            });
        } else {
            $("#leftside tbody tr td").show();
        }
        calculateLeftWidth();
    });

    // toggle whole left side of gantt chart
    $("#hideLeftPanel").live('click', function () {
        if (!$("#leftside").is(":hidden")) {
            $("#leftside").hide();
            $("#hideLeftPanel").text("Show Left Panel");
        } else {
            $("#leftside").show();
            $("#hideLeftPanel").text("Hide Left Panel");
        }
        calculateLeftWidth();
    });

    // recalculate popup and chart sizes and position on window resize
    $(window).resize(function () {
        calculateWidth();
        modalPosition();
    });

    // add event handler for input bug race plan

    $("input[name=useVM]").click(function() {
        if($(this).is(":checked")) {
            updateVMCost();
        } else {
            $("#vmCost").text("$0.00");
        }
    })

    if ($("#CopilotPostingSubmissions").length > 0) {

        var submissionsResultCache = {};

        function handleCopilotSubmissionResult(result, viewType, submissionId) {

            var totalMemberCost = 0;
            var totalContestFee = 0;

            $.each(result.contests, function (index, value) {
                totalMemberCost += parseFloat(getContestTypeValue(value.type, 'cost'));
                totalContestFee += getContestTypeContestFee(value.type);
            });


            var minHours = 10000000;
            var maxHours = -1;

            for(var i = 0; i < result.contests.length; ++i) {
                var value = result.contests[i];
                minHours = value.timeStart < minHours ? value.timeStart : minHours;
                maxHours = value.timeEnd > maxHours ? value.timeEnd : maxHours;
            }


            var vmCost = updateVMCost(result);

            var totalCost = totalContestFee + totalMemberCost + vmCost;
            var totalDuration = (maxHours - minHours) / 24;

            if(!viewType) {
                displayGanttChart(result.contests);
                extendStyle();
                calculateWidth();
                modalPosition();
                $("#durationStat").text(totalDuration + " Days");
                $("#totalStat").text(("$" + totalCost.formatMoney(0))).attr('title', 'Member Cost: $' + totalMemberCost + "  Fee: $" + totalContestFee + "  VM Cost: $" + vmCost);
            } else {
                var estHolder;
                if(viewType == 'list') {
                    $(".listview-table tbody tr").each(function() {
                        var sid = $(this).find('input[name=submissionId]').val();
                        if(sid == submissionId) {
                            estHolder = $(this).find('.estimates');
                        } else {
                            return;
                        }
                    })
                } else if (viewType == 'grid') {
                    $(".gridview-table tbody td").each(function() {
                        var sid = $(this).find('input[name=submissionId]').val();
                        if(sid == submissionId) {
                            estHolder = $(this).find('.estimates');
                        } else {
                            return;
                        }
                    })
                } else if (viewType == 'comparison') {
                    $("#compareItems .colCopilot").each(function() {
                        var sid = $(this).find('input[name=submissionId]').val();
                        if(sid == submissionId) {
                            estHolder = $(this).find('div.colEstimates');
                        } else {
                            return;
                        }
                    })
                }



                estHolder.append("<p>Duration: " + totalDuration + " days </p>");
                estHolder.append($("<p>Total Member Cost: $" + totalMemberCost.formatMoney(0) + "</p>").attr('title', ' Challenges Cost: $' + (totalMemberCost)));
                estHolder.append($("<p>Total Challenge Fee: $" + totalContestFee.formatMoney(0) + "</p>").attr('title', 'Challenges Fee: $' + (totalContestFee)));
                estHolder.append($("<p>Total Cost: $" + totalCost.formatMoney(0) + "</p>").attr('title', 'Member Cost: $' + totalMemberCost + "  Fee: $" + totalContestFee + "  VM Cost: $" + vmCost));
                estHolder.append("<p>Planned Challenge Number: " + result.contests.length + " </p>");
            }
        }

        var loadConfigurationSuccess = true;

        if($("input[name=enablePreview]").val() == 'true') {
            $.ajax({
                type: 'POST',
                url: ctx + "/getProjectPlannerConfiguration",
                data: {billingAccountId:$("input[name=copilotPostingBillingAccountId]").val(), formData: {projectId: $("input[name=directProjectId]").val()}},
                cache: false,
                dataType: 'json',
                success: function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            contestDescription = result;
                            $(".previewCopilotGamePlan").trigger('click');
                            modalPreloader();
                        },
                        function (errorMessage) {
                            loadConfigurationSuccess = false;
                            showServerError(errorMessage);
                        });
                }
            });
        }


        var totalRequests = $(".previewCopilotGamePlan").length;
        var loadEstimatesErrors = {};

        // preview game plan
        $(".previewCopilotGamePlan").click(function(){
            var _parent = $(this).parent();
            var submissionId = _parent.find('input[name=submissionId]').val();
            var projectId = _parent.find('input[name=submissionProjectId]').val();

            if(submissionsResultCache[submissionId]) {
                handleCopilotSubmissionResult(submissionsResultCache[submissionId]);
                return;
            }

            $.ajax({
                type: 'POST',
                url: ctx + "/copilot/previewGamePlan",
                data: {submissionId:submissionId, projectId:projectId},
                cache: false,
                dataType: 'json',
                success: function (jsonResult) {
                    handleJsonResult2(jsonResult,
                        function (result) {
                            submissionsResultCache[submissionId] = result;
                            handleCopilotSubmissionResult(result, $("input[name=viewType]").val(), submissionId);
                            totalRequests--;
                            if(totalRequests == 0) {
                                modalAllClose();
                            }
                        },
                        function (errorMessage) {
                            // showServerError(errorMessage);
                            loadEstimatesErrors[submissionId] = errorMessage;

                            totalRequests--;
                            if(totalRequests == 0) {
                                modalAllClose();
                                $.each(loadEstimatesErrors, function(key, value) {
                                    var estHolder;
                                    var viewType = $("input[name=viewType]").val();
                                    if(viewType == 'list') {
                                        $(".listview-table tbody tr").each(function() {
                                            var sid = $(this).find('input[name=submissionId]').val();
                                            if(sid == key) {
                                                estHolder = $(this).find('.estimates');
                                            } else {
                                                return;
                                            }
                                        })
                                    } else if (viewType == 'grid') {
                                        $(".gridview-table tbody td").each(function() {
                                            var sid = $(this).find('input[name=submissionId]').val();
                                            if(sid == key) {
                                                estHolder = $(this).find('.estimates');
                                            } else {
                                                return;
                                            }
                                        })
                                    } else if (viewType == 'comparison') {
                                        $("#compareItems .colCopilot").each(function() {
                                            var sid = $(this).find('input[name=submissionId]').val();
                                            if(sid == key) {
                                                estHolder = $(this).find('div.colEstimates');
                                            } else {
                                                return;
                                            }
                                        })
                                    }
                                    estHolder.html('<p style="color:red">Failed to load game plan estimates:' + value + '</p>');
                                });
                            } else if(totalRequests < 0) {
                                showServerError(errorMessage);
                            }

                        });
                }
            });
        });
    }

});
