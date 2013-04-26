/**
 * The JS script provides the events handling for the copilot posting registrants and submissions page.
 *
 * Version 2.0 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 * - Add codes for copilot posting submissions and comparison
 *
 * Version 2.1 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * - Add codes for new copilot posting first / second place winner pickup flow
 *
 * @author GreatKevin
 * @version 2.1
 */

$(document).ready(function() {

    $(".gridview-table td").each(function(){
        $(this).find("div.colGrp").height($(this).height());
    });

    $('.requiredXperience .more').mouseover(function() {
        var $parent = $(this).parents('.dashboardModule:eq(0)').position();
        var r = $(this).parents('.dashboardModule:eq(0)').width() - $(this).position().left;
        var r1 = r;
        var t = $parent.top + $(this).position().top + $('.moreFlyout').height() - 10;
        if (r < $('.moreFlyout').width()) {
            r = 0;
        } else {
            r1 = $('.moreFlyout').width() - $(this).position().left + 55;
            r -= 50;
        }
        $('.moreFlyout .arrow').css({
            'left' : 'auto',
            'right' : (r1 - 20) + 'px'
        });
        $('.moreFlyout').removeClass('hide').css({
            'right' : r + 'px',
            'top' : t + 'px',
            'left' : 'auto'
        });
    });
    $('.requiredXperience .more').mouseleave(function() {
        window.setTimeout($('.moreFlyout').addClass('hide'), 500);
    });

    $('.viewXperience').mouseover(function() {
        var l = $(this).parent().parent().position().left - 1;
        if ($.browser.webkit) {
            // webkit fix
            l += 1;
        }
        var t = $(this).position().top + 20;
        // cell
        var arrowLt = 70;
        if ($(this).parent().parent().hasClass('xperience')) {
            l -= 52;
            arrowLt = 103;
        }

        $('.xperienceFlyout').html($(this).parent().parent().find(".otherExperienceDiv").html());

        $('.xperienceFlyout .arrow').css({
            'left' : arrowLt + 'px'
        });
        $('.xperienceFlyout').removeClass('hide').css({
            'top' : t + 'px',
            'left' : l + 'px'
        });
    });

    $('.viewXperience').mouseleave(function() {
        window.setTimeout("$('.xperienceFlyout').addClass('hide')", 500);
    });

    /* tack flyout function */
    $('.track a').mouseover(function() {
        var l = 0;
        var t = 0;
        if ($(this).parents('td:eq(0)').length > 0) {
            l = $(this).parents('td:eq(0)').position().left - 1;
            t = $(this).parents('td:eq(0)').position().top + $(this).parent().position().top + 58;
        } else {
            l = $(this).parents('.colCopilot:first').position().left + 153;
            t = $(this).parents('.colCopilot:first').position().top + $(this).parent().position().top + 54;
        }
        if ($(this).parents('.rCopilot').length > 0) {
            t += 8;
            l += 1;
        }
        // 134 = height from top of cell

        var arrowLt = $(this).position().left + 9;
        if ($(this).hasClass('dev')) {
            $('.trackFlyout .data').text('Certified TopCoder Software Copilot');
            $('.trackFlyout').css('width', '208px');
        } else {
            $('.trackFlyout .data').text('Certified TopCoder Studio Copilot');
            $('.trackFlyout').css('width', '194px');
        }
        $('.trackFlyout .arrow').css({
            'left' : arrowLt + 'px'
        });
        $('.trackFlyout').removeClass('hide').css({
            'top' : t + 'px',
            'left' : l + 'px'
        });
    });
    $('.track a').mouseleave(function() {
        window.setTimeout("$('.trackFlyout').addClass('hide')", 500);
    });

    /* close compare row and adujust column after removal functions */
    $('.compareList .close').live('click', function() {
        var nVisible = 4;
        var idx = $(this).parents('.colCopilot:first').index();
        $(this).parents('.colCopilot:first').remove();
        window.setTimeout(function() {

            var nextNode = $('.compareList .colCopilot:visible:last').next();
            if (nextNode.length > 0) {
                nextNode.removeClass('hide');
            } else {
                var prevNode = $('.compareList .colCopilot:visible:first').prev();
                if (prevNode.length > 0) {
                    prevNode.removeClass('hide');
                } else {
                    $('.compareList .colCopilot:visible:last');
                }
            }
            if ($('#compareItems .colCopilot').length <= nVisible) {
                $('.switch.btn-nexPrev a').addClass('disabled');
            }

            $(".fLeft span.nCP").text(parseInt($(".fLeft span.nCP").html()) - 1);

        }, 1);
    });

    if ($('.drag-table').length > 0) {
        $('.drag-table').dragtable({
            items : ' thead .reorder'
        });
    }

    /* comparision table next / prev */
    $('.btn-nexPrev .next').click(next);
    $('.btn-nexPrev .prev').click(prev);

    /* sortable */
    $('#compareItems').sortable({
        connectWith : ".connectedSortable",
        handle : ".reorder",
        zIndex : '9999',
        start : function() {
            // $('#compareItems .colCopilot').css('display', 'inline');
        },
        stop : function() {
            // $('#compareItems .colCopilot').css('display', '');
        }
    }).disableSelection();

    /*Start Copilot Badges Code*/
    $('.colSkills').each(function() {
        var ui1 = $("input[name='skill-rule-55']", $(this));
        var ui2 = $("input[name='skill-rule-56']", $(this));
        var ui3 = $("input[name='skill-rule-57']", $(this));
        var ui4 = $("input[name='skill-rule-58']", $(this));
        var ui5 = $("input[name='skill-rule-59']", $(this));
        if ( (ui1.val() == "y") & (ui2.val() == "y") & (ui3.val() == "y") & (ui4.val() == "y") & (ui5.val() == "y") ){
            allUIStyle="badge-ui-ally";
        } else {
            allUIStyle="badge-ui-all";
        }
        if ( (ui1.val() == "y") | (ui2.val() == "y") | (ui3.val() == "y") | (ui4.val() == "y") | (ui5.val() == "y") ){
            uiHTML = "<div class='achiv badge-ui'>";
            uiHTML += "<div class='quoteBadgesItem skill-rule-55 skill-rule-55" + ui1.val() + "'><span class='badgeTips skill-rule-55'></span></div>";
            uiHTML += "<div class='quoteBadgesItem skill-rule-56 skill-rule-56" + ui2.val() + "'><span class='badgeTips skill-rule-56'></span></div>";
            uiHTML += "<div class='quoteBadgesItem skill-rule-57 skill-rule-57" + ui3.val() + "'><span class='badgeTips skill-rule-57'></span></div>";
            uiHTML += "<div class='quoteBadgesItem skill-rule-58 skill-rule-58" + ui4.val() + "'><span class='badgeTips skill-rule-58'></span></div>";
            uiHTML += "<div class='quoteBadgesItem skill-rule-59 skill-rule-59" + ui5.val() + "'><span class='badgeTips skill-rule-59'></span></div>";
            uiHTML += "<div class='" + allUIStyle +"'></div>";
            uiHTML += "</div>";
            $(this).append(uiHTML);
        }

        var imple1 = $("input[name='skill-rule-60']", $(this));
        var imple2 = $("input[name='skill-rule-61']", $(this));
        var imple3 = $("input[name='skill-rule-62']", $(this));
        var imple4 = $("input[name='skill-rule-63']", $(this));
        if ( (imple1.val() == "y") & (imple2.val() == "y") & (imple3.val() == "y") & (imple4.val() == "y") ){
            allIMPLEStyle="badge-imple-ally";
        } else {
            allIMPLEStyle="badge-imple-all";
        }
        if ( (imple1.val() == "y") | (imple2.val() == "y") | (imple3.val() == "y") | (imple4.val() == "y") ){
            impleHTML = "<div class='achiv badge-imple'>";
            impleHTML += "<div class='quoteBadgesItem skill-rule-60 skill-rule-60" + imple1.val() + "'><span class='badgeTips skill-rule-60'></span></div>";
            impleHTML += "<div class='quoteBadgesItem skill-rule-61 skill-rule-61" + imple2.val() + "'><span class='badgeTips skill-rule-61'></span></div>";
            impleHTML += "<div class='quoteBadgesItem skill-rule-62 skill-rule-62" + imple3.val() + "'><span class='badgeTips skill-rule-62'></span></div>";
            impleHTML += "<div class='quoteBadgesItem skill-rule-63 skill-rule-63" + imple4.val() + "'><span class='badgeTips skill-rule-63'></span></div>";
            impleHTML += "<div class='" + allIMPLEStyle +"'></div>";
            impleHTML += "</div>";
            $(this).append(impleHTML);
        }

        var ig1 = $("input[name='skill-rule-64']", $(this));
        var ig2 = $("input[name='skill-rule-65']", $(this));
        if ( (ig1.val() == "y") & (ig2.val() == "y") ){
            allIGStyle="badge-ig-ally";
        } else {
            allIGStyle="badge-ig-all";
        }
        if ( (ig1.val() == "y") | (ig2.val() == "y") ){
            igHTML = "<div class='achiv badge-ig'>";
            igHTML += "<div class='quoteBadgesItem skill-rule-64 skill-rule-64" + ig1.val() + "'><span class='badgeTips skill-rule-64'></span></div>";
            igHTML += "<div class='quoteBadgesItem skill-rule-65 skill-rule-65" + ig2.val() + "'><span class='badgeTips skill-rule-65'></span></div>";
            igHTML += "<div class='" + allIGStyle +"'></div>";
            igHTML += "</div>";
            $(this).append(igHTML);
        }

        var qa1 = $("input[name='skill-rule-66']", $(this));
        var qa2 = $("input[name='skill-rule-67']", $(this));
        if ( (qa1.val() == "y") & (qa2.val() == "y") ){
            allQAStyle="badge-qa-ally";
        } else {
            allQAStyle="badge-qa-all";
        }
        if ( (qa1.val() == "y") | (qa2.val() == "y") ){
            qaHTML = "<div class='achiv badge-qa'>";
            qaHTML += "<div class='quoteBadgesItem skill-rule-66 skill-rule-66" + qa1.val() + "'><span class='badgeTips skill-rule-66'></span></div>";
            qaHTML += "<div class='quoteBadgesItem skill-rule-67 skill-rule-67" + qa2.val() + "'><span class='badgeTips skill-rule-67'></span></div>";
            qaHTML += "<div class='" + allQAStyle +"'></div>";
            qaHTML += "</div>";
            $(this).append(qaHTML);
        }

        var bd1 = $("input[name='skill-rule-68']", $(this));
        if ( (bd1.val() == "y")){
            bdHTML = "<div class='achiv badge-bd'>";
            bdHTML += "<div class='quoteBadgesItem skill-rule-68 skill-rule-68" + bd1.val() + "'><span class='badgeTips skill-rule-68'></span></div>";
            bdHTML += "<div class='badge-bd-ally'></div>";
            bdHTML += "</div>";
            $(this).append(bdHTML);
        }

        if ($("div.achiv", $(this)).length < 1){
            $(this).append("<p class='noBadgeHint'>Hasn't earned any skill badges</p>");
        }

    });
    $(".quoteBadgesItem .badgeTips").hover(function(){
        $(this).removeClass("toolTipsHover");
    });
    
    $(".quoteBadgesItem").hover(function(){
            var tipsWrapper = $("span.badgeTips", $(this));
            var wrapperClass = tipsWrapper.attr('class');
            if( wrapperClass.length > 10 ){
                var badgeID = wrapperClass.substring(10,wrapperClass.length);
                var badgeName = $("input#" + badgeID).attr('Name');
                var badgeDesc = $("input#" + badgeID).val();
                tipsHTML = "<span class='badgeName'>" + badgeName + "</span>";
                tipsHTML += "<span class='badgeDesc'>" + badgeDesc + "</span>";
                tipsHTML += "<span class='tipsArrow'></span>";
                tipsWrapper.append(tipsHTML);
                var l = $(this).width()/2 -115;
                tipsWrapper.addClass("toolTipsHover").css({
                    'left' : l + 'px'
                });;
            }
        },
        function()
        {
            var tipsWrapper = $("span.badgeTips", $(this));
            tipsWrapper.removeClass("toolTipsHover")
            tipsWrapper.html("");
        });
    /*End Copilot Badges Code*/


    /*Start Set Copilot Skills Cell height of Compare page*/
    if ($(".compareList").length > 0){
        var maxSkillCellHeight = 144;

        $('div.colSkills').each(function() {
            var tempHeight = $(this).height();

            if (tempHeight > maxSkillCellHeight){
                maxSkillCellHeight = tempHeight;
            }
        });

        $('.rSkills').each(function() {
            $(this).height(maxSkillCellHeight);
        });
    }
    /*End Set Copilot Skills Cell height of Compare page*/

    $('.gridview-table td').each(function(index) {
        if( $('div.col1', $(this)).length > 0 ){
            $('div.col1', $(this)).css({
                'height' : ($(this).height()-20) + 'px'
            });;
        };
        if (index % 2 == 1){
            var firstOfRow=  $(this).prev();

            var height1= $('.col4', $(this)).height();
            var height2= $('.col4', $(firstOfRow)).height();
            maxHeight=Math.max(height1, height2);
            $('.col4', $(this)).height(maxHeight);
            $('.col4', $(firstOfRow)).height(maxHeight);
        }

        var minPadding = 200;
        var achivGroup = $('.achiv', $(this));
        if ( achivGroup.length > 0 ){

            achivGroup.each(function() {
                var tempPaddding = parseFloat ($(this).css('paddingLeft').replace('px', ''));
                if (tempPaddding < minPadding){
                    minPadding = tempPaddding;
                }
            });
            achivGroup.each(function() {
                var newPaddingLeft = $(this).css('paddingLeft').replace('px', '') - minPadding;
                $(this).css('paddingLeft', newPaddingLeft );
            });
        }
    });

    $("a.btn-compare").click(function(){
        var userIds = [];
        if($(".switch a.listView").hasClass('active'))  {
            // list view
            $(".listview-table .chkCompare").each(function(){
                if($(this).is(":checked")) {
                    var id = $(this).attr('id').substr(7);
                    userIds.push(id);
                }
            })
        } else {
            // grid view
            $(".gridview-table .chkCompare").each(function(){
                if($(this).is(":checked")) {
                    var id = $(this).attr('id').substr(7);
                    userIds.push(id);
                }
            })
        }

        if(userIds.length  == 0) {
            showErrors("Please use check box to choose copilots to compare.");
            return;
        }

        var params = {copilotUserIds:userIds, viewType:'comparison', projectId: $("#currentCopilotPostingId").val()};

        window.location = ctx + "/copilot/compareCopilots?" + $.param(params);
    });

    if($("#compareItems").length > 0) {
        $(this).find("li.colCopilot:gt(3)").addClass('hide');
    }

    if ($('#compareItems .colCopilot').length <= 4) {
        $('.switch.btn-nexPrev a').addClass('disabled');
    }

    $(".pickCPBox .pickCopilotCell a").live("click", function(){
        var $this = $(this);
        var $parent = $this.parent(".pickCopilotCell");
        if($parent.hasClass("active")){
            if($parent.has(".pickCopilotPrimary").length > 0){
                $(".pickCPBox .pickCopilotCell").has(".pickCopilotSecondary").removeClass("active");
            }
            $parent.removeClass("active");
        }else{
            if(!$parent.siblings(".pickCopilotCell").has(".pickCopilotPrimary").hasClass("active")){
                $this.closest(".pickCPBox").find(".pickCopilotCell").removeClass("active");
                if($this.hasClass("pickCopilotPrimary")){
                    $(".pickCPBox .pickCopilotCell").has(".pickCopilotPrimary").removeClass("active");
                }
                if($this.hasClass("pickCopilotSecondary")){
                    if($(".pickCPBox .pickCopilotCell.active").has(".pickCopilotPrimary").length > 0){
                        $(".pickCPBox .pickCopilotCell").has(".pickCopilotSecondary").removeClass("active");
                    }else{
                        return false;
                    }
                }
                $parent.addClass("active");
            }
        }
        if($(".pickCPBox .pickCopilotCell.active").length>0){
            $(".btnConfirmPlacements").removeClass("disabled");
            $(".btnForgiveChooseCopilot").addClass("disabled");
        }else{
            $(".btnConfirmPlacements").addClass("disabled");
            $(".btnForgiveChooseCopilot").removeClass("disabled");
        }
    });

    //click "I DON'T WANT TO CHOOSE ANY COPILOT" button to load modal window
    $(".btnForgiveChooseCopilot").live("click", function(){
        if(!$(this).hasClass("disabled")){
            modalLoad("#forgiveChooseCopilotConfirmModal");
        }
        return false;
    });

    //click "NO" button to close modal window.
    $("#forgiveChooseCopilotConfirmModal .closebutton, #cofirmChooseCopilotConfirmModal .closebutton").live("click", function(){
        modalAllClose();
        return false;
    });

    //click "CONFIRM PLACEMENTS" button to confirm copilot.
    $(".btnConfirmPlacements").live("click", function () {
        if (!$(this).hasClass("disabled")) {
            if ($(".pickCPBox .pickCopilotCell.active").has(".pickCopilotSecondary").length == 0) {
                modalLoad("#cofirmChooseCopilotConfirmModal");
                return;
            }

            sendChooseCopilotAjaxRequest();
        }

        return false;
    });

    // click YES of "Choose no runner-up" confirmation dialogue
    $("#cofirmChooseCopilotConfirmModal .button6:eq(0)").live('click', function(){
        sendChooseCopilotAjaxRequest();
    });

    // click YES of "Choose no copilot" confirmation dialogue
    $("#forgiveChooseCopilotConfirmModal .button6:eq(0)").click(function(){
        sendChooseCopilotAjaxRequest();
    });

});

function sendChooseCopilotAjaxRequest() {

    var parentItem;
    if($("#compareItems").length > 0) {
        parentItem = "li";
    } else if($(".switch a.gridview").hasClass("active")) {
        parentItem = "td";
    } else {
        parentItem = "tr";
    }

    var request = buildSelectCopilotRequest(parentItem);

    closeModal();
    modalPreloader();

    $.ajax({
        type: 'POST',
        url:'selectCopilotAjax',
        data: request,
        dataType: "json",
        cache:false,
        async:true,
        success: function(jsonResult) {
            handleJsonResult(jsonResult,
                function(result) {
                    if(result.resultCode && result.resultCode == 'success') {
                        // highlight copilot / runner if exists
                        $(".pickCPBox .pickCopilotCell").closest(parentItem).removeClass("rowSelCP rowSelRUP");
                        $(".pickCPBox .pickCopilotCell").has(".pickCopilotPrimary").each(function () {
                            if ($(this).hasClass("active")) {
                                $(this).closest(parentItem).removeClass("rowSelRUP").addClass("rowSelCP");
                                $(this).closest(parentItem).find(".cellCopilot").append('<span class="ribbonCP"></span>');
                            }
                        });
                        $(".pickCPBox .pickCopilotCell").has(".pickCopilotSecondary").each(function () {
                            if ($(this).hasClass("active")) {
                                $(this).closest(parentItem).removeClass("rowSelCP").addClass("rowSelRUP");
                                $(this).closest(parentItem).find(".cellCopilot").append('<span class="ribbonRUP"></span>');
                            }
                        });

                        // hide all pickup buttons
                        $(".pickCPBox").hide();
                        // hide command buttons
                        $(".copilotPostingCommand").hide();
                    }
                },
                function(errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });
}

var rup = false;

function next() {
    var nVisible = 4;
    $('.disabled', $(this).parent()).removeClass('disabled');
    if ($('#compareItems .colCopilot').length <= nVisible) {
        $('a', $(this).parent()).addClass('disabled');
    }
    if ($('.compareList .colCopilot:last').is(":visible")) {
        $(this).addClass('disabled');
        return false;
    }
    var idx = $('.compareList .colCopilot:visible:first').index();
    $('.compareList .colCopilot').eq(idx).addClass('hide');
    $('.compareList .colCopilot').eq(idx + nVisible).removeClass('hide');
}
function prev() {
    var nVisible = 4;
    $('.disabled', $(this).parent()).removeClass('disabled');
    if ($('#compareItems .colCopilot').length <= nVisible) {
        $('a', $(this).parent()).addClass('disabled');
    }
    if ($('.compareList .colCopilot:first').is(":visible")) {
        $(this).addClass('disabled');
        return false;
    }
    var idx = $('.compareList .colCopilot:visible:last').index();
    $('.compareList .colCopilot').eq(idx).addClass('hide');
    $('.compareList .colCopilot').eq(idx - nVisible).removeClass('hide');
} 

function pickupCopilot(item) {
    var idx = $(item).parents('tr:first').index();
    if ($(item).parents('.gridview-table').length > 0) {
        idx = idx * 2 + $(item).parents('td:first').index();
    }
    var r = $('.listview-table tbody tr').eq(idx);
    var r1 = $('.gridview-table tbody td').eq(idx);
    r.addClass('rowSelCP');
    r1.addClass('rowSelCP');
    $('td:first .colLt', r).append('<span class="ribbon"></span>');
    $('.col1', r1).append('<span class="ribbon"></span>');
    var node = $('.pickupCell', r);
    var node1 = $('.pickupCell', r1);
    node.addClass('cpPicked').removeClass('rupPicked');
    node1.addClass('cpPicked').removeClass('rupPicked');

    $('.btn-pickup', $(item).parents('.container2Content_det:first')).hide();

    if (!rup) {
        $('.caption .choose').remove();
        $("div.fLeft").append($('<a class="choose link noRunnerUpChosen" href="javascript:;">I don\'t want to choose runner-up copilot</a>'));
    } else {
        $('.caption .choose').fadeOut('slow');
        $('.caption .choose').fadeOut('slow', function() {
            $('.caption .seprator').hide();
        });
    }

    if ($('.compareList .colPickup').length > 0) {
        $(item).parents('.colPickup').addClass('cpPicked').removeClass('rupPicked');
        $('.rCopilot', $(item).parents('.colCopilot')).append('<span class="ribbonCP"></span>');
    }

    $(".btn-pickup-rup").show();

    if($(".switch a.listview").hasClass("active")) {
        $(".listview-table").find('tr:even').addClass('even');
    }
}

function pickupRunnerup(item) {
    var idx = $(item).parents('tr:first').index();
    if ($(item).parents('.gridview-table').length > 0) {
        idx = idx * 2 + $(item).parents('td:first').index();
    }
    var r = $('.listview-table tbody tr').eq(idx);
    var r1 = $('.gridview-table tbody td').eq(idx)
    r.addClass('rowSelRUP');
    r1.addClass('rowSelRUP');
    $('td:first .colLt', r).append('<span class="ribbon"></span>');
    $('.col1', r1).append('<span class="ribbon"></span>');
    var node = $('.pickupCell', r);
    var node1 = $('.pickupCell', r1);
    node.addClass('rupPicked').removeClass('cpPicked');
    node1.addClass('rupPicked').removeClass('cpPicked');

    $('.btn-pickup-rup', $(item).parents('.container2Content_det:first')).hide();

    rup = true;
    if ($('.caption .choose').text() == "I don't want to choose runner-up copilot") {
        $('.caption .choose').fadeOut('slow', function() {
            $('.caption .seprator').hide();
        });
    }

    if ($('.compareList .colPickup').length > 0) {
        $(item).parents('.colPickup').addClass('rupPicked').removeClass('cpPicked');
        $('.rCopilot', $(item).parents('.colCopilot')).append('<span class="ribbonRUP"></span>');
    }
}



function buildSelectCopilotRequest(parentItem) {

    var projectId = $("input[name=contestId]").val();
    var directProjectId = $("input[name=directProjectId]").val();
    var winnerSubmissionId = -1;
    var winnerCopilotProfileId = -1
    var secondPlaceSubmissionId = -1;

    $(".pickCPBox .pickCopilotCell").has(".pickCopilotPrimary").each(function () {
        if ($(this).hasClass("active")) {
            winnerSubmissionId = $(this).closest(parentItem).find("input[name=submissionId]").val();
            winnerCopilotProfileId = $(this).closest(parentItem).find("input[name=profileId]").val()
        }
    });
    $(".pickCPBox .pickCopilotCell").has(".pickCopilotSecondary").each(function () {
        if ($(this).hasClass("active")) {
            secondPlaceSubmissionId = $(this).closest(parentItem).find("input[name=submissionId]").val();
        }
    });

    var request = {projectId: projectId, tcDirectProjectId: directProjectId,
        winnerSubmissionId: winnerSubmissionId, winnerProfileId: winnerCopilotProfileId, secondPlaceSubmissionId: secondPlaceSubmissionId};

    return request;
}
