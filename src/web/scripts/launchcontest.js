/**
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 *
 * Launch Contest Javascript
 *
 * Veresion 1.1 TC Direct - Software Creation Update Assembly change notes:
 * - Add form reset to prevent firefox from caching local form data. This is intended to make selections consistent.
 * - Bind change event to project dropdown to dynamically load copilots of the selected project
 * - Add method isCopilotDropDownHidden to check if the copilot dropdown is hidden
 * - Add method handleProjectDropDownChange for project dropdown change event
 *
 * Version 1.1 TC Direct Replatforming Release 1 change note
 * - Changes were made to work for the new studio contest type and multiround type.
 *
 * Version 1.2 TC Direct Replatforming Release 2 change note
 * - The software contest can set checkpoint prizes.
 *
 * Version 1.3 Direct Improvements Assembly Release 2 Assembly change note
 * - Add character limitation for the input fields and input areas when creating contests.
 *
 * Version 1.3.1 Release Assembly - TC Direct UI Improvement Assembly 3 change note:
 * - fix catalog dropdown style, term & conditions modal window
 *
 * Version 1.4 Release Assembly - TopCoder Cockpit TinyMCE Editor Revamp change note:
 * - integrate the new cockpit tinyMCE editor
 *
 * Version 1.5 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
 * - Add the handle of "auto bug hunt creation" checkbox for assembly contest
 *
 * Version 1.6 (Release Assembly - TopCoder Studio CCA Integration) change notes:
 * - Add place holder support for tinyMCE editors for sutdio contest description, round1 info
 * - and round2 info fields.
 *
 * Version 1.7 (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 * - Add js to populate billing accounts based on project selection.
 *
 * Version 1.8 POC Assembly - Change Rich Text Editor Controls For TopCoder Cockpit note
 * - remove TinyMCE related code, replaced with CKEditor.
 *
 * Version 1.9 (Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0) change notes:
 * - Fixed a drop down layout issue for a lenghthy project name.
 *
 * Version 2.0 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) change notes:
 * - Update to support launching mm contest.
 *
 * Version 2.1 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
 * - Add billing account CCA specific
 *
 * Version 2.2 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * - Updates to support choose associated milestone when creating a new contest
 *
 * Version 2.3 (Release Assembly - TC Cockpit Private Challenge Update)
 * - Add support for choosing security group for contest eligibility. Security groups are retrieved by billing account.
 *
 * Version 2.4 (TC Cockpit Auto Assign Reviewer for First2Finish challenge)
 * - Add "Add Copilot as Iterative Reviewer" checkbox for First2Finish challenge
 *
 * Version 2.5 (TC Cockpit Software Challenge Checkpoint End Date and Final End Date)
 * - Add support for setting checkpoint end date and submission end date for software challenge
 *
 * Version 2.6 (F2F - TC Cockpit Update Auto Assign Reviewer Flow)
 * - Add review type radios to choose 'community' or 'internal' review
 *
 * Version 2.7 (TopCoder Direct - EST/EDT switch in date picker)
 * - Setup event handler for auto changing date time timezone
 *
 * @author GreatKevin, csy2012, bugbuka, GreatKevin
 * @version 2.7
 */
$(document).ready(function() {

    // reset the fake form to prevent Firefox from caching local form data
    document.getElementById("fakeForm").reset();

    //truncate the project name
    $(".addNewContest .row .projectSelect select option").each(function(){
        if($(this).text().length>60){
            var txt=$(this).text().substr(0,60)+'...';
            $(this).text(txt);
        }
    });

    //truncate the billing account, copilot and round type
    $(".addNewContest .row .billingSelect select option," +
        ".addNewContest .row .copilotSelect select option, .addNewContest .row .milestoneSelect select option," +
        ".schedule #roundTypeDiv .roundelect select option, " +
        "#overviewAlgorithmPage .problemDiv select option").each(function(){
        if($(this).text().length>60){
            var txt=$(this).text().substr(0,50)+'...';
            $(this).text(txt);
        }
    });

    $("#billingGroupCheckBox input[type=checkbox]").change(function () {

        if ($(this).is(":checked")) {
            $("#billingGroupCheckBox select").show();
            $("#billingGroupCheckBox select option").remove();
            var selectedBillingID = $("#billingProjects").val();
            if(selectedBillingID > 0 && billingGroups[selectedBillingID] && billingGroups[selectedBillingID].length > 0) {
                $.each(billingGroups[selectedBillingID], function(i, v){
                    $("#billingGroupCheckBox select").append($("<option/>").attr('value', v.id).text(v.name));
                })
            }
        } else {
            $("#billingGroupCheckBox select").hide();
        }
    });

    // copilot select dropdown change
    $('.copilotSelect select').change(copilotDropDownChange);

    // Drop Down Select Project
    $(".dropdown dt a").click(function() {
        $(".dropdown dd ul").toggle();
        return false;
    });

    $(".dropdown dd ul li a").click(function() {
        var text = $(this).html();
        $(".dropdown dt a span").html(text);
        $(".dropdown dd ul").hide();
        $("#result").html("Selected value is: " + getSelectedValue("sample"));
        return false;
    });

    function getSelectedValue(id) {
        return $("#" + id).find("dt a span.value").html();
    }


    $("input[name=reviewType]").click(function(){
        if('internal' == $("input[name=reviewType]:checked").val()) {
            // show the reviewer dropdown
            var selectedValue = $("#reviewer").val();
            $(".reviewerRow").show();
            $("#reviewer").resetSS();
            $("#reviewer").getSetSSValue(selectedValue);
        } else {
            $(".reviewerRow").hide();
        }
    });

    $(document).bind('click', function(e) {
        var $clicked = $(e.target);
        if (! $clicked.parents().hasClass("dropdown"))
            $(".dropdown dd ul").hide();
    });

    // Drop Down Sort Contests
    $(".dropdown2 dt a").click(function() {
        $(".dropdown2 dd ul").toggle();
        return false;
    });

    $(".dropdown2 dd ul li a").click(function() {
        var text = $(this).html();
        $(".dropdown2 dt a span").html(text);
        $(".dropdown2 dd ul").hide();
        $("#result").html("Selected value is: " + getSelectedValue("sample2"));
        $('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17});
        return false;
    });

    function getSelectedValue(id) {
        return $("#" + id).find("dt a span.value2").html();
    }

    $(document).bind('click', function(e) {
        var $clicked = $(e.target);
        if (! $clicked.parents().hasClass("dropdown2"))
            $(".dropdown2 dd ul").hide();
    });

    // Drop Down Search
    $(".dropdown3 dt a").click(function() {
        $(".dropdown3 dd ul").toggle();
        return false;
    });

    $(".dropdown3 dd ul li a").click(function() {
        var text = $(this).html();
        $(".dropdown3 dt a span").html(text);
        $(".dropdown3 dd ul").hide();
        $("#result").html("Selected value is: " + getSelectedValue("sample"));
        return false;
    });

    function getSelectedValue(id) {
        return $("#" + id).find("dt a span.value3").html();
    }

    $(document).bind('click', function(e) {
        var $clicked = $(e.target);
        if (! $clicked.parents().hasClass("dropdown3"))
            $(".dropdown3 dd ul").hide();
    });

    $("a.link-sort").click(function() {
        var toLoad = $(this).attr('href');
        $('#scrollbar-wrapper div').hide('fast', loadContent);
        $('#load').remove();
        $('#scrollbar-wrapper div').append('<div id="load"></div>');
        $('#load').fadeIn('slow');
        window.location.hash = $(this).attr('href').substr(0, $(this).attr('href').length - 5);
        function loadContent() {
            $('#scrollbar-wrapper div').load(toLoad, '', showNewContent());
        }

        function showNewContent() {
            $('#scrollbar-wrapper div').show(0.001, hideLoader());
        }

        function hideLoader() {
            $('#load').fadeOut('slow');
        }
    });


    $("#expand-table").click(function() {
        $("#collapse-table").show();
        $(".row-hide").slideDown(0.0001);
        $(this).hide();
        return false;
    });

    $("#collapse-table").click(function() {
        $("#expand-table").show();
        $(".row-hide").slideToggle(0.0001);
        $(this).hide();
        return false;
    });

    $('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17});
    $(".table-sidebar tr:odd").addClass("alt");
    $(".table-summary-content tr:even").addClass("alt");
    $("#collapse-table").hide();
    $(".row-hide").slideToggle(0.0001);
    $("#wb-1").hide();
    $("#wb-2").hide();

    $(".greentext").click(function() {
        $("#wb-1").fadeTo("slow", 1.0);
        return false;
    });

    $(".redtext").click(function() {
        $("#wb-2").fadeTo("slow", 1.0);
        return false;
    });

    $("a.btn-list").hover(function() {
        var $next = $(this).next(".dialog-mini-wrapper");
        $next.css("display", "block");
        $next.hover(function() {
            $next.css("display", "block");
        }, function() {
            $next.css("display", "none");
        });
    }, function() {
        var $next = $(this).next(".dialog-mini-wrapper");
        $next.css("display", "none");
    });

    /* init tab **/
    $('#tab li').click(function() {

        $('#tab li a').removeClass('current');
        $(this).find('a').addClass('current');
        if ($(this).attr('class') == 'top') {
            $('.selectDesing').show();
            $('.selectSoftware').addClass('visibility');
        } else {
            $('.selectDesing').hide();
            $('.selectSoftware').removeClass('visibility');
        }

    });

    $("#checkpointDateDay").val(3).trigger('change');
    $("#endDateDay").val(3).trigger('change');

    /* init pop */
    var prevPopup = null;
    showPopup = function(myLink, myPopupId) {
        var myLinkLeft = myLinkTop = 0;

        /* hide the previous popup */
        if (prevPopup)
            $(prevPopup).css("display", "none");

        prevPopup = $('#' + myPopupId);

        /* get the position of the current link */
        do{
            myLinkLeft += myLink.offsetLeft;
            myLinkTop += myLink.offsetTop;
        } while (myLink = myLink.offsetParent);

        /* set the position of the popup */
        var popUpHeight2 = $('#' + myPopupId).height() / 2;

        myLinkTop -= popUpHeight2;

        $('#' + myPopupId).css("top", (myLinkTop + 4) + 'px');
        $('#' + myPopupId).css("left", ( myLinkLeft + 22 ) + 'px');

        /* set the positio of the arrow inside the popup */
        $(".tooltipContainer SPAN.arrow").css("top", popUpHeight2 + 'px');

        /* show the popup */
        $('#' + myPopupId).css("display", "block");

    }

    var prevPopups = null;
    showPopups = function(myLinks, myPopupIds) {
        var myLinkLefts = myLinkTops = 0;

        /* hide the previous popup */
        if (prevPopups)
            $(prevPopups).css("display", "none");

        prevPopups = $('#' + myPopupIds);

        /* get the position of the current link */
        do{
            myLinkLefts += myLinks.offsetLeft;
            myLinkTops += myLinks.offsetTop;
        } while (myLinks = myLinks.offsetParent);

        /* set the position of the popup */
        var popUpHeight2s = $('#' + myPopupIds).height() / 2;

        myLinkTops -= popUpHeight2s;

        $('#' + myPopupIds).css("top", (myLinkTops + 4) + 'px');
        $('#' + myPopupIds).css("left", ( myLinkLefts + 104 ) + 'px');

        /* set the positio of the arrow inside the popup */
        $(".tooltipContainer SPAN.arrow").css("top", popUpHeight2s + 'px');

        /* show the popup */
        $('#' + myPopupIds).css("display", "block");

    }

    $('#ContestScheduleHelpIcon .helpIcon').hover(function() {
        showPopup(this, 'contestScheduleToolTip');
    }, function() {
        $('#contestScheduleToolTip').hide();
    });

    $('#ContestDescriptionHelpIcon .helpIcon').hover(function() {
        showPopup(this, 'contestDescriptionToolTip');
    }, function() {
        $('#contestDescriptionToolTip').hide();
    });

    $('#cca .helpme').hover(function() {
        showPopup(this, 'enforceCCAToolTip');
    }, function() {
        $('#enforceCCAToolTip').hide();
    });

    $('#assembly_bug_hunt .helpme').hover(function () {
        showPopup(this, 'bugHuntForAssemblyToolTip');
    }, function () {
        $('#bugHuntForAssemblyToolTip').hide();
    });

    $('#Round1HelpIcon .helpIcon').hover(function() {
        showPopup(this, 'contestRound1ToolTip');
    }, function() {
        $('#contestRound1ToolTip').hide();
    });

    $('#Round2HelpIcon .helpIcon').hover(function() {
        showPopup(this, 'contestRound2ToolTip');
    }, function() {
        $('#contestRound2ToolTip').hide();
    });

    $('.selectDesing .help,.selectSoftware .help').hover(function() {
        showPopups(this, 'contestLaunch1');
    }, function() {
        $('#contestLaunch1').hide();
    });

    $('.mPrizes .helpIcon').hover(function() {
        showPopup(this, 'contestLaunch2');
    }, function() {
        $('#contestLaunch2').hide();
    });

    $('.deliverables .helpIcon').hover(function() {
        showPopup(this, 'contestLaunch3');
    }, function() {
        $('#contestLaunch3').hide();
    });

    $('.TB_overlayBG').css("opacity", "0.6");
    $('#TB_HideSelect').css("opacity", "0");
    $('#TB_overlay').hide();
    /* init help */
    $('.helloUser .help,.tabContest .moreLink,.help .helpIcon').click(function() {
        $('#TB_overlay').show();
        $('#TB_window').show();
        $('.TB_overlayBG').css('height', document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
        $('#TB_window').css('margin', '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window").width() / 2)) + 'px');


        $('#placeHolder').hide();
        $('#TB_ajaxContent').show();
    });

    $('#TB_closeWindowButton').click(function() {
        $('#TB_overlay').hide();
        $('#TB_window').hide();
    });

    /* add button */
    $('.uploadInner .addButton').click(function() {
        $(this).parent().after('<div class="uploadInner" style="margin-top:12px;"><input type="text" class="text uploadInput" value="File Name" /><a href="javascript:;" class="button6"><span class="left"><span class="right">CHOOSE...</span></span></a><input type="text" class="text fileInput" value="File Description" /><a href="javascript:;" class="removeButton"><span class="hide">REMOVE</span></a><a href="javascript:;" class="addButton"><span class="hide">ADD</span></a></div>');
    });

    $('.uploadInner .removeButton').click(function() {
        if ($(this).parent().parent().find('.uploadInner').length > 1) {
            $(this).parent().parent().find('.uploadInner:last').remove();
        }
    });

    /* bigframe */
    $('#TB_overlay').bgiframe();
    //$('#TB_window').scrollFollow({offset: parseInt((document.documentElement.scrollHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});

    $(".uploadInner .button6").click(function() {
        $(".uploadInner .fileIn").trigger("click")
    })

    /********************************
     *   Launch Contest Main Widget
     ********************************/

    // initialize selects
    // populate the select option for software group
    $.each(projectCategoryArray, function(i, projectCategory) {
        if (projectCategory.hideInDropdown) {
            return;
        }
        // not show copilot contest type
        if (projectCategory.id != 29 && projectCategory.id != ALGORITHM_CATEGORY_ID_MARATHON && projectCategory.typeId != 3) {
            $("<option/>").val("SOFTWARE" + projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Development']");
        }
        if (projectCategory.typeId == 3) {
            $("<option/>").val("STUDIO"+projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Design']");
        }
        if (projectCategory.id == ALGORITHM_CATEGORY_ID_MARATHON) {
            $("<option/>").val("ALGORITHM"+projectCategory.id).text(projectCategory.label).appendTo("optgroup[label='Data']");
        }
    });


    if ($('select').length > 0) {
        $('.selectSoftware select,.selectDesing select,.startSelect select,.checkpointSelect select,.endSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect();
        var SelectOptions = {
            ddMaxHeight: '220px',
            yscroll: true
        };
        $('.billingSelect select,.roundelect select,.startEtSelect select,.checkpointEtSelect select,.endEtSelect select').sSelect(SelectOptions);
        $('.projectSelect select').sSelect(SelectOptions).change(function() {
             handleProjectDropDownChange();
        });
        //$('#catalogSelect').sSelect();
        $('.selectDesing div.selectedTxt').html('Select Challenge Type');
        $("#checkpointDiv").hide();
    }

    /* Optgroup 2 columns fix */
    if ($('.selectDesing optgroup, .selectDesign .newListOptionTitle').length > 0) {
        var optgroupMaxHeight = 0, num;

        $('.selectDesing optgroup').each(function() {
            num = $(this).children().length + 1;
            optgroupMaxHeight = num * 22 > optgroupMaxHeight ? num * 22 : optgroupMaxHeight;
        });

        $('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');

        $(window).resize(function() {
            $('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');
        });
        $(window).scroll(function() {
            $('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');
        });
    }

    /*****************************
     *   Select Contest Type
     ****************************/
    var SGTemplatesList = ['/scripts/ckeditor/templates/software_guidelines_templates.js'];
    var DRTemplatesList = ['/scripts/ckeditor/templates/detailed_requirements_templates.js'];
    var StudioContestSpecTemplates = ['/scripts/ckeditor/templates/studio/studio_contest_spec_templates.js'];
    CKEDITOR.loadTemplates(SGTemplatesList);
    CKEDITOR.loadTemplates(DRTemplatesList);
    CKEDITOR.loadTemplates(StudioContestSpecTemplates);

    // choose contest type
    $('#contestTypes').bind("change", function() {
        onContestTypeChange();
    });

    onContestTypeChange();


    $('#billingProjects').bind("change", function() {
        updateContestFee();
        updateBillingGroups();
    });

    $('#addNewProject').click(function() {
        clearAddNewProjectForm();
        modalLoad("#addNewProjectModal");
        $('#addNewProjectModal').find('input[name="newProjectName"]').focus();
    });

    $('#addNewProjectModal input, #addNewProjectModal textarea').focus(function () {
        $(this).removeClass("invalid");
        $(this).parent().find(".errorMessage").text('');
    });

    // round types
    $('#roundTypes').bind("change", function () {
        var contestType = getContestType(true)[0];
        var typeId = getContestType(true)[1];
        var roundType = $('#roundTypes').val();
        updateRoundDurationLabels();
        if (roundType == 'single') {
            $('#checkpointDiv').hide();
            $('#checkpointPrizeDiv').hide();
            $('#swCheckpointPrizeDiv').hide();
            $('#round1InfoDiv').hide();
            $('#round2InfoDiv').hide();
            $("#checkPointEndDateDiv").hide();
        } else {
            if(contestType == 'SOFTWARE') {
                $('#checkpointDiv').hide();
                $("#checkPointEndDateDiv").show();
            } else {
                $('#checkpointDiv').show();
                $("#checkPointEndDateDiv").hide();
            }

            if(typeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
                // do not display any end date picker for Design First2Finish
                $('#checkpointDiv').hide();
            }

            $('#checkpointPrizeDiv').show();
            $('#swCheckpointPrizeDiv').show();
            $('#round1InfoDiv').show();
            $('#round2InfoDiv').show();

            //set checkpoint end date to be 2 days after start date
            $("#startDate").bind('change.chkpt', function () {
              $("#checkPointEndDate").dpSetSelected(Date.parse($("#startDate").val()).add(2).days().toString('MM/dd/yyyy'));
            }).trigger('change');

            $("#startTime").bind('change.chkpt', function () {
              $("#checkPointEndTime").getSetSSValue($("#startTime").getSetSSValue());
            }).trigger('change');
        }
    });

    $('#roundTypes').trigger("change");

    /* init date-picker and time picker */
    if ($('.date-pick').length > 0) {
        var dt = $.trim($("#currentServerDate").text());
        var date = $.datepicker.parseDate('mm/dd/yy', dt);
        date.setDate(date.getDate() + 1);
        $(".date-pick").datePicker().val($.datepicker.formatDate('mm/dd/yy', date)).trigger('change');
        setupDateTimeSuffix("start");
        setupDateTimeSuffix("end");
        setupDateTimeSuffix("checkPointEnd");
    }

    if($("#startTime").length > 0) {
        $(".startEtSelect ul li:eq(9) a, #endDateDiv .endEtSelect ul li:eq(9) a, #checkPointEndDateDiv .endEtSelect ul li:eq(9) a").trigger('click');
    }


    CKEDITOR.replace( 'contestIntroduction' );
    CKEDITOR.replace( 'round1Info' );
    CKEDITOR.replace( 'round2Info' );

    CKEDITOR.replace( 'matchDetails' );
    CKEDITOR.replace( 'matchRules' );


    handleProjectDropDownChange();

    handleProblemsDropDownChange();

    $('#overviewAlgorithmPage').hide();
}); // end of jQuery onload

//capacity dates
var capacityFullDates = {};

// flag to indicate copilot dropdown initialization
var copilotDropdownFlag = false;

// flag to indicate milestone dropdown initialization
var milestoneDropdownFlag = false;

// method to populate copilots selection based on the project selection change
function handleProjectDropDownChange() {
    var value = $('.projectSelect select').getSetSSValue();

    billingAccounts = getBillingAccountsByDirectProjectId(value);

    $("#billingProjects").empty();
    $("#billingProjects").append($('<option></option>').val(0).html("Please select an existing account"));

    var hasCMCBilling = false;
    var CMCBillingExisting = false;

    if($("input[name=CMCBillingID]").val() && $("input[name=CMCBillingID]").val() > 0 && value > 0) {
        hasCMCBilling = true;
    }


    $.each(billingAccounts, function(key, value) {

        $("#billingProjects").append($('<option></option>').val(value["id"]).html(value["name"]).data("cca", (value["cca"] == "true" ? true : false)));

        if(value["id"] == $("input[name=CMCBillingID]").val()) {
            CMCBillingExisting = true;
        }

    });

    $("#billingProjects").val(0);

    if(hasCMCBilling && !CMCBillingExisting) {
        $("#billingProjects").append($('<option></option>').val($("input[name=CMCBillingID]").val()).html($("input[name=CMCBillingName]").val()).data("cca", false));
    }

    $("#billingProjects").resetSS();

    $('#billingProjects').bind("change", function() {
        if ($(this).find(":selected").data("cca")){
            $("#lccCheckBox").attr('checked','true').attr('disabled','true');
            mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePrivate();
        }else{
            $("#lccCheckBox").removeAttr('disabled');
        }
        updateContestFee();
        updateBillingGroups();
    });

    if(hasCMCBilling) {
        $("#billingProjects").val($("input[name=CMCBillingID]").val());
        $("#billingProjects").getSetSSValue($("input[name=CMCBillingID]").val());
    } else {
        $("#billingProjects").getSetSSValue("0");
    }


    $("#lccCheckBox").removeAttr('disabled');
    mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    if(value > 0) {
        $("a.addBilling, a.addMilestone, div.addNewMilestone").show();
        $("a.addBilling").attr("href", "../editProject?formData.projectId=" + value + "#addBillingAccount");
        $("a.addMilestone").attr("href", "../projectMilestoneView?formData.projectId=" + value + "&formData.viewType=list");
        tcDirectProjectId = value; // put the current selected direct project ID into a global variable
    } else {
        $("a.addBilling, a.addMilestone, div.addNewMilestone").hide();
        $("a.addBilling, a.addMilestone").attr("href", "javascript:;");
    }

    var result = getCopilotsByDirectProjectId(value);

    var copilots = result.copilots;
    var selected = result.selected;
    var $contestCopilots = $("#contestCopilot");

    $contestCopilots.html("");

    $contestCopilots.append($('<option></option>').val(0).html("Unassigned"));

    $.each(copilots, function(key, value) {
        $contestCopilots.append($('<option></option>').val(key).html(value));
    });

    // set the selection drop down value
    $contestCopilots.val(selected);

    // we only refresh stylish selection when it's not hidden
    $('.copilotSelect select').resetSS();
    $('.copilotSelect select').change(copilotDropDownChange);
    $('.copilotSelect select').getSetSSValue(selected);


    var milestones = getMilestonesByDirectProjectId(value);
    var $contestMilestones = $("#contestMilestone");

    $contestMilestones.html("");

    $contestMilestones.append($('<option></option>').val(0).html("Please select a milestone to associate"));

    $.each(milestones, function(id, value) {
        $contestMilestones.append($('<option></option>').val(value.id).text(htmlEncode(value.name)).attr('title', htmlEncode(value.description)));
    });

    $('.milestoneSelect select').resetSS();

    var resources;
    // update the reviewer dropdown
    if(value > 0) {
        resources = getProjectResourcesByDirectProjectId(value);
    } else {
        resources = [];
    }

    var $projectResources = $("#reviewer");

    $projectResources.html("");

    $.each(resources, function(id, value) {
        $projectResources.append($('<option></option>').val(value.userId).html(value.name));
    });

    // we only refresh stylish selection when it's not hidden
    if($("#reviewer").parent().is(":visible")) {
        $('#reviewer').resetSS();
        $('#reviewer').getSetSSValue(selected);
    } else {
        // set the selection drop down value
        $projectResources.val(selected);
    }
}

function handleProblemsDropDownChange() {
    var problems = getActiveProblemSet();

    $("#problems").empty();
    $("#problems").append($('<option></option>').val(-1).html("Please select a problem"));

    $.each(problems, function(key, value) {
        $("#problems").append($('<option></option>').val(key).html(value));
    });
    $("#problems").val(-1);
    $("#problems").resetSS();
    $("#problems").getSetSSValue(-1);
}

function updateRoundDurationLabels() {
    var contestType = getContestType(true)[0];
    var typeId = getContestType(true)[1];

    var roundType = $('#roundTypes').val();
    if(contestType == "SOFTWARE") {
        $("#checkpointDiv label").html("Checkpoint Duration:");
    } else {
        if (roundType == "single") {
            $("#endDiv label").html("Round 1 Duration:");
            $("#endDiv").show();
        } else {
            $("#checkpointDiv label").html("Round 1 Duration:");
            $("#checkpointDiv").show();
            $("#endDiv label").html("Round 2 Duration:");
            $("#endDiv").show();
        }

        if(typeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
            // do not display any end date picker for Design First2Finish
            $("#checkpointDiv").hide();
            $("#endDiv").hide();
        }
    }
}

/**
 * event handler function when contest type is changed.
 */
function onContestTypeChange() {

    var currentTypeId = -1;
    var contestType = getContestType(true)[0];
    var typeId = getContestType(true)[1];
    if (isContestSaved()) {
        currentTypeId = mainWidget.softwareCompetition.projectHeader.projectCategory.id;
        if (currentTypeId == typeId) {
            return false;
        }
    }

    var SGTemplatesList = ['/scripts/ckeditor/templates/software_guidelines_templates.js'];
    var DRTemplatesList = ['/scripts/ckeditor/templates/detailed_requirements_templates.js'];
    var StudioContestSpecTemplates = ['/scripts/ckeditor/templates/studio/studio_contest_spec_templates.js'];
    if (contestType == 'SOFTWARE') {
        var swGuidelines = CKEDITOR.instances['swGuidelines'];
        if (swGuidelines) {
            swGuidelines.destroy(true);
        }
        var swDetailedRequirements = CKEDITOR.instances['swDetailedRequirements'];
        if (swDetailedRequirements) {
            swDetailedRequirements.destroy(true);
        }
        CKEDITOR.replace('swGuidelines', {
            templates: getSGTemplatesName(typeId),
            templates_files: SGTemplatesList
        });
        CKEDITOR.replace('swDetailedRequirements', {
            templates: getDRTemplatesName(typeId),
            templates_files: DRTemplatesList
        });
    } else {
        var contestDescription = CKEDITOR.instances['contestDescription'];
        if (contestDescription) {
            contestDescription.destroy(true);
        }
        CKEDITOR.replace('contestDescription', {
            templates: getStudioTemplatesName(typeId),
            templates_files: StudioContestSpecTemplates
        });
    }

    if (typeId == SOFTWARE_CATEGORY_ID_F2F
        || typeId == SOFTWARE_CATEGORY_ID_CODE
        || typeId == STUDIO_CATEGORY_ID_DESIGN_F2F) {
        $("#milestoneManSymbol").hide();
    } else {
        $("#milestoneManSymbol").show();
    }

    if(isContestSaved() && typeId != currentTypeId) {
        if(typeId == SOFTWARE_CATEGORY_ID_F2F) {
            showErrors("You cannot change saved non-First2Finish challenge to First2Finish challenge type");
            // switch back to First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == SOFTWARE_CATEGORY_ID_F2F) {
            showErrors("You cannot change saved First2Finish challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);
            return;
        }
        if(typeId == SOFTWARE_CATEGORY_ID_CODE) {
            showErrors("You cannot change saved non-Code challenge to Code challenge type");
            // switch back to First2Finish
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);

            return;
        }
        if(currentTypeId == SOFTWARE_CATEGORY_ID_CODE) {
            showErrors("You cannot change saved Code challenge to other challenge type");
            setTimeout(function () {
                $("#contestTypes").getSetSSValue('SOFTWARE' + currentTypeId);
            }, 1000);
            return;
        }
    }

    if (typeId == 14 && contestType == 'SOFTWARE') {
        // show the bug hunt check box and default set to checked
        $("#assembly_bug_hunt").show();
        $("#bug_hunt_CheckBox").attr('checked', 'checked');
    } else {
        $("#assembly_bug_hunt").hide();
        $("#bug_hunt_CheckBox").removeAttr('checked');
    }

    if (typeId == ALGORITHM_CATEGORY_ID_MARATHON && contestType == 'ALGORITHM') {
        // show the end date for marathon match
        $("#endDateDiv").show();
        $("div.milestoneSelect").parents("div.row").hide();
    } else if(contestType == 'SOFTWARE') {
        $("#endDateDiv").show();
        $("div.milestoneSelect").parents("div.row").show();
    } else {
        $("#endDateDiv").hide();
        $("div.milestoneSelect").parents("div.row").show();
    }

    if (isContestSaved() && mainWidget.competitionType != contestType) {
        showErrors("You can not switch between studio, software and algorithm after it is saved.");

        return;
    }

    updateRoundDurationLabels();
    mainWidget.competitionType = contestType;
    if (typeId == currentTypeId) {
        // it is a revert, nothing to do here
        return;
    }


    if (mainWidget.softwareCompetition.projectHeader.projectCategory && mainWidget.softwareCompetition.projectHeader.projectCategory.id > 0) {
        mainWidget.softwareCompetition.projectHeader.projectCategory.id = typeId;
    }

    $('.software').hide();
    $('.studio').hide();
    $(".schedule").css("margin-bottom", "0px");

    if (hasMultiRound(typeId)) {
        $("#roundTypeDiv").show();
        $('#roundTypes').trigger("change");
    } else {
        $("#roundTypeDiv").hide();
        $("#checkpointDiv").hide();
        $("#checkPointEndDateDiv").hide();
        mainWidget.softwareCompetition.multiRound = false;
    }

    if (!copilotDropdownFlag) {
        // copilot dropdown has never been initialized, do it
        $('.copilotSelect select').sSelect(SelectOptions);
        copilotDropdownFlag = true;
    } else {
        // initialized before, we only do the reset to update the data
        $('.copilotSelect select').resetSS();
        $('.copilotSelect select').change(copilotDropDownChange);
    }

    if (!milestoneDropdownFlag) {
        // milestone dropdown has never been initialized, do it
        $('.milestoneSelect select').sSelect(SelectOptions);
        milestoneDropdownFlag = true;
    } else {
        // initialized before, we only do the reset to update the data
        $('.milestoneSelect select').resetSS();
    }


    if (mainWidget.isSoftwareContest()) {
        //Software Contest
        $('.software').show();
        $('.studio').hide();

        // the copilot dropdown options
        var SelectOptions = {
            ddMaxHeight: '220px',
            yscroll: true
        };

        if (typeId == SOFTWARE_CATEGORY_ID_DEVELOPMENT) {
            $('#contestName').hide();
            $('#contestNameFromDesign').show();
            $('#contestNameFromDesign').val("");
            $('#contestIdFromDesign').val("");
            $('#devOnlyDiv').show();
            $('#devOnlyDiv').css("display", "inline");
            $('#devOnlyCheckBox').attr('checked', false);
        } else {
            $('#contestName').show();
            $('#contestNameFromDesign').hide();
            $('#devOnlyDiv').hide();
            $('#devOnlyCheckBox').attr('checked', false);
        }

        if (typeId == SOFTWARE_CATEGORY_ID_F2F || typeId == SOFTWARE_CATEGORY_ID_CODE) {
            if(!$("input[name=reviewType]").parent().is(":visible")) {
                // the radios are not display, display them
                $("input[name=reviewType]").parent().show();
            }

            // now check the radio value
            if('internal' == $("input[name=reviewType]:checked").val()) {
                // show the reviewer dropdown
                var selectedValue = $("#reviewer").val();
                $(".reviewerRow").show();
                $("#reviewer").resetSS();
                $("#reviewer").getSetSSValue(selectedValue);
            }
        } else {
            $(".reviewRow").hide();
        }


        // hide the end date for F2F and set default 30 days
        if(typeId == SOFTWARE_CATEGORY_ID_F2F) {
            $("#startDate").bind('change.f2f', function(){
                $("#endDate").dpSetSelected(Date.parse($("#startDate").val()).add(30).days().toString('MM/dd/yyyy'));
            }).trigger('change');

            $("#startTime").bind('change.f2f', function(){
                $("#endTime").getSetSSValue($("#startTime").getSetSSValue());
            }).trigger('change');
            $("#endDateDiv").hide();
            $("#startDate").unbind('change.dev');
            $("#startTime").unbind('change.dev');

        } else {  //default all other Software challenges to 5 days in length
            $("#endDateDiv").show();
            $("#startDate").unbind('change.f2f');
            $("#startTime").unbind('change.f2f');

          if($("#startDate").val() !== null && $("#startDate").val() !== '') {
            $("#startDate").bind('change.dev', function () {
              $("#endDate").dpSetSelected(Date.parse($("#startDate").val()).add(5).days().toString('MM/dd/yyyy'));
            }).trigger('change');

            $("#startTime").bind('change.dev', function () {
              $("#endTime").getSetSSValue($("#startTime").getSetSSValue());
            }).trigger('change');
          }
        }
    }

    /// Studio Contest
    if (mainWidget.isStudioContest()) {
        $('.software').hide();
        $(".reviewRow").hide();
        $('.studio').show();
        $('#roundTypes').trigger('change');

        $.each(studioSubtypeOverviews, function (i, overview) {
            if (overview.id == typeId) {
                // update overview description
                $('#contestDescriptionTooltip').html(overview.description);
            }
        });

        $.each(studioSubtypeFees, function (i, fee) {
            if (fee.id == typeId) {
                // not set yet, auto fill
                if (isEmpty($('#prize3').val())) {
                    $('#prize1').val(fee.firstPlaceCost)
                    $('#prize2').val(fee.secondPlaceCost)
                }
            }
        });

        resetFileTypes(typeId);
        $(".schedule").css("margin-bottom", "0px");

        getCapacityDatesForStudioSubType(getContestType(true)[1]);
    }

    if (mainWidget.isAlgorithmContest()) {
        $(".reviewRow").hide();
        $.each(algorithmSubtypeFees, function (i, fee) {
            if (fee.id == typeId) {
                // not set yet, auto fill
                if (isEmpty($('#prize3').val())) {
                    $('#alPrize1').val(fee.firstPlaceCost)
                    $('#alPrize2').val(fee.secondPlaceCost)
                }
            }
        });

        $("#endDateDiv").show();
    }

    updateContestFee();
    updateBillingGroups();
    copilotDropDownChange();
}

/**
 * Resets file types.
 *
 * @param studioSubtypeId studio sub type id
 */
function resetFileTypes(studioSubtypeId) {
    $('#deliverablesCheckboxs').html('');

    var types = getStudioFileTypes(studioSubtypeId);
    var html = "";
    $.each(types, function(i, type) {
        html += '<div><input type="checkbox" value="' + type.value + '" class="defaultFileType" /> <label>' + type.description + '</label></div>';
    });

    $('#deliverablesCheckboxs').html(html);
}

var validateNewProjectModalInput = function (modalId) {
    var modal = $("#" + modalId);
    var passValidation = true;

    // name, description are required
    if ($.trim(modal.find("input[name='newProjectName']").val()) == '') {
        passValidation = false;
        modal.find("input[name='newProjectName']").addClass('invalid').parent().find(".errorMessage").text('Project Name cannot be empty');
    }
    else if (containTags($.trim(modal.find("input[name='newProjectName']").val()))) {
        passValidation = false;
        modal.find("input[name='newProjectName']").addClass('invalid').parent().find(".errorMessage").text('Project Name cannot contain HTML tags');
    }
    else {
        modal.find("input[name='newProjectName']").removeClass('invalid').parent().find(".errorMessage").text('');
    }

    if ($.trim(modal.find("textarea[name='newProjectDescription']").val()) == '') {
        passValidation = false;
        modal.find("textarea[name='newProjectDescription']").addClass('invalid').parent().find(".errorMessage").text('Project Description cannot be empty');
    }
    else if (containTags($.trim(modal.find("textarea[name='newProjectDescription']").val()))) {
        passValidation = false;
        modal.find("textarea[name='newProjectDescription']").addClass('invalid').parent().find(".errorMessage").text('Project Description cannot contain HTML tags');
    }
    else {
        modal.find("textarea[name='newProjectDescription']").removeClass('invalid').parent().find(".errorMessage").text('');
    }

    return passValidation;
}


/**
 * Adds a new project.
 */
function addNewProject() {

    if(!validateNewProjectModalInput('addNewProjectModal')) {
        return;
    }

    var projectName = $('#addNewProjectModal').find('input[name="newProjectName"]').val();
    var projectDescription = $('#addNewProjectModal').find('textarea[name="newProjectDescription"]').val();

    var errors = [];

    if (!checkRequired(projectName)) {
        errors.push('Project name is empty.');
    }

    if (!checkRequired(projectDescription)) {
        errors.push('Project description is empty.');
    }


    if (errors.length > 0) {
        showErrors(errors);
        $("#modal-background").hide();
        return;
    }


    $.ajax({
        type: 'POST',
        url:  "createProject",
        data: setupTokenRequest({'projectName':projectName,
            'projectDescription':projectDescription}, getStruts2TokenName()),
        cache: false,
        dataType: 'json',
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                            function(result) {
                                var projectData = result;
                                $("<option/>").val(projectData.projectId).text(projectData.name).appendTo("#projects");
                                $('#projects').resetSS();
                                $('#projects').change(function() {
                                    handleProjectDropDownChange();
                                });
                                $('#projects').getSetSSValue(projectData.projectId);

                                modalCloseAddNewProject();
                                showSuccessfulMessage('Project <span class="messageContestName">' + projectData.name + '</span> is created successfully.');

                                // we need to clear the copilots options and set to unassigned for new created project
                                $("#contestCopilot").html("");
                                $("<option/>").val(0).text("Unassigned").appendTo("#contestCopilot");
                                $("#contestCopilot").resetSS();
                                $('.copilotSelect select').change(copilotDropDownChange);
                                $("#contestCopilot").getSetSSValue(0);
                                copilotDropdownFlag = true;
                            },
                            function(errorMessage) {
                                modalCloseAddNewProject();
                                showServerError(errorMessage);
                            });
        }
    });
}

function getCapacityDatesForStudioSubType(studioSubtypeId) {
    if (capacityFullDates[studioSubtypeId] == null) {
        var request = {
            studio : true,
            contestTypeId : studioSubtypeId
        };
        $.ajax({
            type: 'POST',
            url:  ctx + "/launch/getCapacityFullDates",
            data: request,
            cache: false,
            dataType: 'json',
            success: handleGetCapacityResult
        });
    }
}

function handleGetCapacityResult(jsonResult) {
    handleJsonResult(jsonResult,
                    function(result) {
                        var contestTypeId = result.contestTypeId;
                        var fullDates = result.fullDates;
                        capacityFullDates[contestTypeId] = fullDates;

                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    });
}

function closeTBBox() {
    $('#TB_overlay').hide();
    $('#TB_window').hide();
    $('#TB_window div').remove();
}

/**
 * The change event handler of copilot dropdown in launch new challenge page.
 *
 * @since 2.4
 */
function copilotDropDownChange() {
    // place holder
}

