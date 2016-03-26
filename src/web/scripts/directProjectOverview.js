/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 *
 * <p>
 *  Version 1.0: Provides project copilots management and project forum creation features to project overview page.
 * </p>
 *
 * <p>
 *  Version 1.1 (Module Assembly - TC Cockpit Project Overview Project General Info): Adds the JS codes for project general information table.
 * </p>
 *
 * <p>
 *  Version 1.2 (Release Assembly - TC Cockpit Edit Project and Project General Info Update): Change the codes for new data in project general information:
 *   - Projected Duration
 *   - Projected Cost
 *   - Project ratings
 *   - Additional Project Info
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Project Overview Performance Improvement)
 *  - Change the loading of project stats and project activities to ajax
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TC Direct Project Forum Configuration Assembly 2)
 *  - Added event listeners for forum configuration popups
 * </p>
 *
 * Version 1.5 (Release Assembly - TopCoder Copilot Feedback Updates)
 * - Add 4 ratings to the copilot feedback
 *
 * Version 1.6 (topcoder Direct Refactor Jira RPC and VM Count Retrieval to separate AJAX requests) @author Veve @challenge 30045453
 * - Add ajax to get project overview issues stats.
 *
 * @author Blues, GreatKevin, duxiaoyang, GreatKevin
 * @version 1.6
 * @since Release Assembly - TopCoder Cockpit Project Overview Update 1
 */
var iProjectBudget;
var calculateProjectBudget = function() {
    iProjectBudget = parseInt($('.projectBudget dd.actualNum').text().replace('$', '').replace(/,/g, '') / $('.projectBudget dd:first').text().replace('$', '').replace(/,/g, '') * 100);
    var iProjectedTotal = parseInt($('.projectBudget dd.projectedNum').text().replace('$', '').replace(/,/g, '') / $('.projectBudget dd:first').text().replace('$', '').replace(/,/g, '') * 100);

    $('.projectBudget .totalBudget .midActual').css('width', 1.87 * iProjectBudget);
    $('.projectBudget .totalBudget .midProjected').css('width', 1.87 * iProjectedTotal);
    if (iProjectBudget < 70) {
        $('.projectBudget .totalBudget').addClass('green');
        $('.projectBudget .totalBudgetInfor').addClass('green');
        if (iProjectBudget < 5) {
            $('.projectBudget .totalBudget .actualCost').css('width', 1.95 * iProjectBudget);
        }
    } else if (iProjectBudget >= 100) {
        $('.projectBudget .totalBudget').addClass('red');
        $('.projectBudget .totalBudgetInfor').addClass('red');
        $('.projectBudget .totalBudget .midActual').css('width', '187px');
    } else {
        $('.projectBudget .totalBudget').addClass('yellow');
        $('.projectBudget .totalBudgetInfor').addClass('yellow');
    }

    if (iProjectedTotal >= 100) {
        $('.projectBudget .projectedCost,.projectBudget dt.projected, .projectBudget dd.projectedNum').addClass('red');
        $('.projectBudget .totalBudget .midProjected').css('width', '187px');
    }

    $(".totalBudget").css('visibility', 'visible');
};

var calculationProjectDuration = function() {

    var iprojectDuration = parseInt($('.projectDuration dd.actualNum').text().replace('days', '') / $('.projectDuration dd:first').text().replace('days', '') * 100);
    var iprojectedTotalDuration = parseInt($('.projectDuration dd.projectedNum').text().replace('days', '') / $('.projectDuration dd:first').text().replace('days', '') * 100);

    $('.projectDuration .plannedDuration .midActual').css('width', 1.87 * iprojectDuration);
    $('.projectDuration .plannedDuration .midProjected').css('width', 1.87 * iprojectedTotalDuration);

    if (iprojectDuration < 70) {
        $('.projectDuration .plannedDuration').addClass('green');
        $('.projectDuration .totalBudgetInfor').addClass('green');
        if (iprojectDuration < 5) {
           // $('.projectDuration .plannedDuration .actualDuration').css('width', 1.95 * iprojectDuration);
        }
    } else if (iprojectDuration >= 100) {
        $('.projectDuration .plannedDuration').addClass('red');
        $('.projectDuration .totalBudgetInfor').addClass('red');
        $('.projectDuration .actualDuration .midActual').css('width', '187px');
    } else {
        $('.projectDuration .plannedDuration').addClass('yellow');
        $('.projectDuration .totalBudgetInfor').addClass('yellow');
    }

    if (iprojectedTotalDuration >= 100) {
        $('.projectDuration .projectedDuration,.projectDuration dt.projected, .projectDuration dd.projectedNum').addClass('red');
        $('.projectDuration .plannedDuration .midProjected').css('width', '187px');
    }
};


$(document).ready(function() {

    $(".projectInforDiv .expand").click(function () {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(".projectInforDiv .projectInformation").show();
            $(this).removeClass("collapse");
        } else {
            $(".projectInforDiv .projectInformation").hide();
            $(this).addClass("collapse");
        }
    });

    calculationProjectDuration();

    function SubstringDemo(str, num) {
        var ss;
        if (str && str.length > num) {
            ss = str.substring(0, num);
            ss += ' <a href="javascript:;" class="moreLink triggerModal triggerProjectDescription" name="projectDescModal">More...</a>'
        }
        return(ss);
    }

    $(".triggerProjectDescription").live('click', function() {
        modalLoad('#projectDescModal');
    })

    var iStr = $('.projectDescDetails p').html();

    var iWidth = $('.projectDetails').width();
    var iWidth1 = $('.columeFirst').width();
    var iWidth2 = $('.columeSecond').width();
    var iWidth3 = $('.columeThird').width();
    var iWidth4 = $('.columeForth').width();
    var iDis = iWidth - iWidth1 - iWidth2 - iWidth3 - iWidth4 - 34;
    var iDis1 = iWidth - iWidth1 - iWidth2 - iWidth3 - 34;
    if ($('body').width() >= 1410) {
        $('.projectDetails').css('min-height', '150px');
        $('.bigProjectLinks').show();
        $('.smallProjectLinks').hide();
        $('.projectDescDetails p').html(iStr);
        $('.projectDescDetails p').html(SubstringDemo($('.projectDescDetails p').html(), 418));
        $('.columeFirst,.columeSecond,.columeThird').css('margin-right', (iDis / 4));
        $('#archived .columeFirst,#archived .columeSecond,#archived .columeThird').css('margin-right', (iDis / 3));
        /*$('.projectDescDetails span').hide();
         $('.projectDescription a').css('margin-left','218px');*/
    } else {
        $('.projectDetails').css('min-height', '200px');
        $('.bigProjectLinks').hide();
        $('.smallProjectLinks').show();
        $('.projectDescDetails p').html(iStr);
        $('.projectDescDetails p').html(SubstringDemo($('.projectDescDetails p').html(), 700));
        $('.columeFirst,.columeSecond,.columeThird').css('margin-right', (iDis1 / 3));
        /*$('.projectDescDetails span').show();
         $('.projectDescription a').css('margin-left','210px');*/
    }

    if ($.browser.msie) {
        var marginTop = 0;
        if(parseInt($.browser.version, 10) === 7) {
            marginTop = 7;
        } else if(parseInt($.browser.version, 10) === 8) {
            marginTop = 2;
        }

        $("ul.notSetUl").css('margin-top', marginTop);
    }

    $(".managersMask a").each(
        function() {

            var css = $(this).attr('class');

            if(css.indexOf('coder') == 0) {
                $(this).attr('target', '_blank');
            }
        }
    );

    $(window).resize(function () {
        var iWidth = $('.projectDetails').width();
        var iWidth1 = $('.columeFirst').width();
        var iWidth2 = $('.columeSecond').width();
        var iWidth3 = $('.columeThird').width();
        var iWidth4 = $('.columeForth').width();
        var iDis = iWidth - iWidth1 - iWidth2 - iWidth3 - iWidth4 - 34;
        var iDis1 = iWidth - iWidth1 - iWidth2 - iWidth3 - 34;
        if ($('body').width() >= 1410) {
            $('.projectDetails').css('min-height', '150px');
            $('.bigProjectLinks').show();
            $('.smallProjectLinks').hide();
            $('.projectDescDetails p').html(iStr);
            $('.projectDescDetails p').html(SubstringDemo($('.projectDescDetails p').html(), 418));
            $('.columeFirst,.columeSecond,.columeThird').css('margin-right', (iDis / 4));
            $('#archived .columeFirst,#archived .columeSecond,#archived .columeThird').css('margin-right', (iDis / 3));
            /*$('.projectDescDetails span').hide();
             $('.projectDescription a').css('margin-left','218px');*/
        } else {
            $('.projectDetails').css('min-height', '200px');
            $('.bigProjectLinks').hide();
            $('.smallProjectLinks').show();
            $('.projectDescDetails p').html(iStr);
            $('.projectDescDetails p').html(SubstringDemo($('.projectDescDetails p').html(), 571));
            $('.columeFirst,.columeSecond,.columeThird').css('margin-right', (iDis1 / 3));
            /*$('.projectDescDetails span').show();
             $('.projectDescription a').css('margin-left','210px');*/
        }
    });

    $(".ratingView").each(function () {
        var _this = $(this);
        var rating = 0;
        for (var i = 0; i < 5; i++) {
            var span = $("<span/>").appendTo(_this);
        }
        if (_this.hasClass("rating1")) {
            rating = 1;
        } else if (_this.hasClass("rating2")) {
            rating = 2;
        } else if (_this.hasClass("rating3")) {
            rating = 3;
        } else if (_this.hasClass("rating4")) {
            rating = 4;
        } else if (_this.hasClass("rating5")) {
            rating = 5;
        }
        _this.find("span:lt(" + rating + ")").addClass("active");
    })

    // managebox resize
    function resizeManageBox() {

        var iWidth = $('.projectDetails').width();
        var iWidth1 = $('.columeFirst').width();
        var iWidth2 = $('.columeSecond').width();
        var iWidth3 = $('.columeThird').width();
        var iDis1 = iWidth - iWidth1 - iWidth2 - iWidth3 - 34;

        if ($('body').width() >= 1200) {
            $(".managersBox .pRatings").css('margin-right', (iDis1 / 3));
            $(".managersBox .additionalInfo").css('margin-right', 0);
            $(".managersBox .managersMask").css('width', (iWidth2 + iDis1 / 6));
            $(".managersBox .managersMask").css('margin-right', (iDis1 / 6));
        } else {

            $(".managersBox .managersMask").css('width', "auto");
            $('.managersBox .pRatings,.managersBox .managersMask,.managersBox .additionalInfo').css('margin-right', (iDis1 / 3));
        }
    }

    $(window).resize(function () {
        resizeManageBox();
    });
    $(window).trigger("resize");


    if ($(".configreButton").length > 0) {

        var ajaxTableTimer = 2000; // timer for ajax table list
        var ajaxTableLoader, strTableData = '', isReadStatus = '';
        var strTmp = '';
        var request = {};
        request['tcDirectProjectId'] = tcDirectProjectId;
        
        $.ajax({
            type: "GET",
            url: "getProjectForumsStatusAJAX",
            data: request,
            dataType: "json",
            success: function(json) {
                var isForumWatched = false;
                strTableData = "";
                strTmp = "";
                $.each(json.result['return'].projectForumThreads, function(idx, item) {
                    var fixedForum = item.threadTitle.indexOf("Project Requirements") >= 0;
                    var newRow = "";
                    newRow += '<tr>';
                    newRow += '<td class="colTab1">';
                    newRow += '<div>';

                    isReadStatus = (item.isRead == false ? "isNew" : " ");

                    newRow += '<h3 class="' + isReadStatus + '"><a target="_blank" href="https://' + SERVER_CONFIG_FORUM_SERVER_NAME + '/forums/?module=ThreadList&forumID=' + item.threadID + '">' + item.threadTitle + '</a></h3>';
                    newRow += '<p>' + item.summary + '</p>';
                    newRow += '</div>';
                    newRow += '</td>';
                    newRow += '<td class="colTab2">';
                    newRow += '<div>' + item.threadNumber + '/' + item.messageNumber + '</div>';
                    newRow += '</td>';
                    newRow += '<td class="colTab3">';
                    //  strTableData += '<a href="#" class="author">' + item.lastPostHandle + '</a>';
                    newRow += item.latestPostAuthorLink;
                    newRow += '<p>' + item.lastPostTime + '</p>';
                    newRow += '</td>';
                    newRow += '</tr>';
                    if (fixedForum) {
                        strTableData = newRow + strTableData;
                    } else {
                        strTableData += newRow;
                    }
                    
                    if(item.watching) {
                    	  isForumWatched = true;
                    }
                    
                    // add a new row into the configure forums popup
                    var popupRow = "<tr>";
                    if (fixedForum) {
                        popupRow += '<td class="first">';
                        popupRow += '<span class="group predefine">' + item.threadTitle + '</span>';
                    } else {
                        popupRow += '<td>';
                        if (item.threadNumber == 0) {
                            popupRow += '<input class="selectForumCheck" type="checkbox"/>';
                        } else {
                            popupRow += '<input class="selectForumCheck" type="checkbox" disabled="disabled"/>';
                        }
                        popupRow += '<span class="group">' + item.threadTitle + '</span>';
                    }
                    popupRow += '<span class="threadId hide">' + item.threadID + '</span></td>';
                    if (fixedForum) {
                        popupRow += '<td class="middle">';
                    } else {
                        popupRow += '<td>';
                    }
                    popupRow += '<span class="group">' + item.summary + '</span></td>';
                    if (fixedForum) {
                        popupRow += '<td class="checkbox last"></td>';
                    } else {
                        popupRow += '<td class="checkbox">';
                        if (item.threadNumber == 0) {
                            popupRow += '<a class="deleteIcon" href="javascript:;"></a>';
                        } else {
                            popupRow += '<a class="deleteIcon disabledDeleteIcon toolTip" href="javascript:;" rel="You can not delete a forum that is already used"></a>';
                        }
                        popupRow += '</td>';
                    }
                    popupRow += "</tr>";
                    if (fixedForum) {
                        $('.projectForumBody').prepend(popupRow);
                    } else {
                        $('.projectForumBody').append(popupRow);
                    }
                });
                strTmp += '<table cellpadding="0" cellspacing="0"><tbody>';
                strTmp += strTableData;
                strTmp += '</tbody></table>';
                
                if ($('#projectForumTable').length > 0) {
                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner").empty().append(strTmp);
                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');
    
                    ajaxTableLoader = setTimeout(function() {
                        $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner").empty().append(strTmp);
                        $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');
                    }, ajaxTableTimer);
                    
                    //initiate the forum watch checkbox               
                    initForumWatch(isForumWatched);
                }  
            }
        });
    }
    
    //
    // add forum watch enable/disable functions
    //
    //
    function initForumWatch(isWatched) {
    	  $('#chProjectForum').attr('checked', isWatched);    	      	  
    	  $('.watchForumOptions').show();
    	  
    	  // add listener so it could trigger watch/unwatch
        $('#chProjectForum').click(function(){
        	  forumWatchCheckBoxListener(this);
        });    	  
    }
    
     /**
      * forum checkbox listener. It will perform corresponding operation
      * depending on check box selection.
      *
      * @param cb checkbox      
      */    
    function forumWatchCheckBoxListener(cb) {
        var request = {
            tcDirectProjectId : tcDirectProjectId,
            watch : $(cb).is(':checked')
        };

     
        $.ajax({
                type : 'post',
                url : 'updateProjectForumWatchAJAX',
                cache : false,
                data : setupTokenRequest(request, getStruts2TokenName()),
                dataType : 'json',
                success : function(result) {
                    if(!result.result) {
                        showErrors(result.error.errorMessage);                        
                        return;
                    }                    
                },
                error: function(result) {
                    showErrors("Error when creating project forum");
                }
             });           	 
    }
    

    // the event handler of project forum creation button
    $(".projectForumLeader .createForumButton").live('click', function() {

        var request = {
            tcDirectProjectId : tcDirectProjectId
        };

//        showComingSoon( "This feature is coming soon. If you would like a project forum created in the meantime," +
//            ' or already have a forum that you would like to add here please contact <span class="mailTo"><a href="mailTo:support@topcoder.com">TopCoder Support</a></span>');
//
//        return;

        modalPreloader();

        $.ajax({
                    type : 'post',
                    url : 'createProjectForum',
                    cache : false,
                    data : setupTokenRequest(request, getStruts2TokenName()),
                    dataType : 'json',
                    success : function(result) {
                        modalAllClose();

                        if(!result.result) {
                            showErrors(result.error.errorMessage);
                            return;
                        }

                        location.reload(true);
                    },
                    error: function(result) {
                        showErrors("Error when creating project forum");
                    }
                });

    })

    var copilotCarouselSetting = {scroll:1,visible:1,itemLoadCallback:onCopilotCarouselChange};

    if ($.browser.msie  && parseInt($.browser.version, 10) === 7) {
        copilotCarouselSetting.wrap = 'circular';
    }

    // display the jcarousel for project copilots
    if ($('#projectCopilotsCarousel').length > 0) {
        $('#projectCopilotsCarousel').jcarousel(copilotCarouselSetting);
        setupCopilotFeedbackCurrent($('#projectCopilotsCarousel').data('jcarousel').first);
    }

    // scroll
    $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');

    // list selected
    $('#copilotManageModal .addUserForm .addUserList li').live('click', function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    });

    // select all
    $('#copilotManageModal .addUserForm .selectAll').click(function() {
        $('#copilotManageModal .addUserForm .addUserLeft ul li').filter(":visible").addClass('selected');
    });

    // remove all
    $('#copilotManageModal .addUserForm .removeAll').click(function() {
        $('#copilotManageModal .addUserForm .addUserLeft ul').append($('#copilotManageModal .addUserForm .addUserRight ul').html());
        $('#copilotManageModal .addUserForm .addUserRight ul').empty();
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // add item
    $('#copilotManageModal .addUserForm .addItem').live('click', function() {
        $('#copilotManageModal .addUserForm .addUserLeft ul li.selected').each(function() {
            $('#copilotManageModal .addUserForm .addUserRight ul').append('<li name="' + $(this).attr('name') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });

    // remove item
    $('#copilotManageModal .addUserForm .removeItem').live('click', function() {
        $('#copilotManageModal .addUserForm .addUserRight ul li.selected').each(function() {
            $('#copilotManageModal .addUserForm .addUserLeft ul').append('<li name="' + $(this).attr('name') + '" id="' + $(this).attr('id') + '">' + $(this).html() + '</li>');
            $(this).remove();
        });
        if ($('#copilotManageModal .addUserForm .addUserLeft .addUserList ul').height() >= 219) {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserLeft .addUserList').css('overflow-y', 'visible');
        }
        if ($('#copilotManageModal .addUserForm .addUserRight .addUserList ul').height() >= 266) {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'scroll');
        } else {
            $('#copilotManageModal .addUserForm .addUserRight .addUserList').css('overflow-y', 'visible');
        }
    });


    // cache the project copilots initialization data
    var widgetResult;

    // mapping of copilot profile id to
    var copilotMapping = {};

    // manage copilot button to trigger the project copilots management widget
    $('.projectCopilotsLeader .triggerModal, .projectCopilotsList .triggerModal').live('click', function() {
        // clear the search box

        if (widgetResult == undefined) {
            showErrors("Please wait for the loading of the copilot data");
        }

        initializeWidget(widgetResult);

        $("#copilotManageModal .searchBox input").val('');
        modalLoad('#' + $(this).attr('name'));
        return false;
    });


    // save the project copilots information
    $('#copilotManageModal .saveButton').live('click', function() {

        var request = {
            copilotProjectOperations : getCopilotWidgetOperations()
        };

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'copilot/processCopilotProjects',
            cache : false,
            data : setupTokenRequest(request, getStruts2TokenName()),
            dataType : 'json',
            success : function(result) {
                modalAllClose();

                if (!result.result) {
                    showErrors(result.error.errorMessage);
                    return;
                }

                // no need to refresh the token because the page will be refreshed
                location.reload(true);
            },
            error: function(result) {
                showErrors("Error when saving project copilots");
            }
        });

        return false;
    });


    // close button
    $('#copilotManageModal .cancelButton').live('click', function() {
        modalAllClose();
        if (widgetResult != null) {
            initializeWidget(widgetResult);
        }
        return false;
    });


    // handle the copilot management widget search feature
    $("#copilotManageModal .searchBox a").live('click', function() {
        var searchText = $("#copilotManageModal .searchBox input").val().toLowerCase();
        var leftList = $("#copilotManageModal .addUserLeft .addUserList ul li");

        leftList.each(function() {

            $(this).html(copilotMapping[$(this).attr('name')]);

            $(this).show();
        });

        if ($.trim(searchText).length == 0) {
            return;
        } else {
            leftList.each(function() {
                var copilotHandle = $.trim($(this).html());
                var copilotHandleLower = copilotHandle.toLowerCase();
                var index = copilotHandleLower.indexOf(searchText);
                if (index == -1) {
                    // hide
                    $(this).hide();
                } else {
                    var highlighted = copilotHandle.substring(0, index - 1) + '<strong>' +
                        copilotHandle.substring(index, index + searchText.length) +
                        '</strong>' + copilotHandle.substring(index + searchText.length);
                    $(this).html(highlighted);
                }

            });
        }
    });

    var request = {};
    request['directProjectId'] = tcDirectProjectId;

    // initialize the project copilots management widget
    var initializeWidget = function(result) {
        var leftList = $("#copilotManageModal .addUserLeft .addUserList ul").html('');
        var rightList = $("#copilotManageModal .addUserRight .addUserList ul").html('');

        $.each(result.allCopilots, function(k, v) {
            leftList.append('<li name="' + k + '">' + v + '</li>');
            copilotMapping[k] = v;
        });

        $.each(result.projectCopilots, function(k, v) {
            rightList.append('<li name="' + k + '" id="copilotProject_' + v.copilotProjectId + '">' + v.handle + '</li>');
            copilotMapping[k] = v.handle;
        });

        widgetResult = result;
    }

    var errorHandler = function(error) {
        showErrors(error);
    }

    // loads the copilots data for project copilots management widget
    $.ajax({
        type : 'POST',
        url : 'getProjectCopilotsWidgetData',
        cache : false,
        data : request,
        dataType : 'json',
        success : function(result) {
            handleJsonResult(result, initializeWidget, errorHandler)
        },
        error: function(result) {
            showErrors("Fail to load the project copilots data");
        }
    });

    // get the operations performed on the project copilots management widget
    var getCopilotWidgetOperations = function() {
        var operations = [];
        // check the right list to determine add and remove
        var rightList = $("#copilotManageModal .addUserRight .addUserList ul li");
        var leftList = $("#copilotManageModal .addUserLeft .addUserList ul li");

        // check remove first
        $.each(leftList, function() {

            if ($(this).attr('id')) {
                var copilotProfileId = $(this).attr('name');
                var copilotProjectId = $(this).attr('id').substring('copilotProject_'.length);

                operations[operations.length] = {
                    projectId : tcDirectProjectId,
                    copilotProfileId : parseInt(copilotProfileId),
                    copilotProjectId : parseInt(copilotProjectId),
                    operation : 'REMOVE'
                };

            }
        });

        // check add first
        $.each(rightList, function() {
            if (!$(this).attr('id')) {

                var copilotProfileId = $(this).attr('name');

                operations[operations.length] = {
                    projectId : tcDirectProjectId,
                    copilotProfileId : parseInt(copilotProfileId),
                    operation : 'ADD'
                };

            }
        });

        return operations;
    }

    function getDurationTextInDays(durationInHours) {
        var durationInDays = durationInHours / 24;
        var text = durationInDays.formatMoney(2);
        if (Math.round(durationInDays) != 1) {
            text += " days";
        } else {
            text += " day";
        }

        return text;
    };
    $.ajax({
        type : 'POST',
        url : 'getProjectStatsAjax',
        cache : false,
        timeout:100*1000,
        dataType:"json",
        data : {formData:{projectId:tcDirectProjectId}},
        success : function(result) {
            $("#projectStatistics tbody tr").toggleClass('hide');

            var statsData = result.result.return;

            $("#projectStatistics tbody td.draftContestsNumber").text(statsData.draftContestsNumber);
            $("#projectStatistics tbody td.pipelineContestsNumber").text(statsData.pipelineContestsNumber);
            $("#projectStatistics tbody td.runningContestsNumber").text(statsData.runningContestsNumber);
           var allContestsNumber =(statsData.finishedContestsNumber +
               statsData.cancelledNumber) + '(' +statsData.completedNumber + '/' +
               (statsData.finishedContestsNumber-statsData.completedNumber) + '/' +
               statsData.cancelledNumber + ')';
            $("#projectStatistics tbody td.allContestsNumber").text(allContestsNumber);
            $("#projectStatistics tbody td.totalMemberCost").text('$' + statsData.totalMemberCost.formatMoney(2));
            $("#projectStatistics tbody td.averageMemberCostPerContest").text('$' + statsData.averageMemberCostPerContest.formatMoney(2));
            $("#projectStatistics tbody td.totalContestFee").text('$' + statsData.totalContestFee.formatMoney(2));
            $("#projectStatistics tbody td.averageContestFeePerContest").text('$' + statsData.averageContestFeePerContest.formatMoney(2));
            $("#projectStatistics tbody td.totalProjectCost").text('$' + statsData.totalProjectCost.formatMoney(2));
            $("#projectStatistics tbody td.averageContestDuration").text(getDurationTextInDays(statsData.averageContestDuration));
            $("#projectStatistics tbody td.averageFulfillment").text('$' + statsData.averageFulfillment.formatMoney(2));

            var actualCostText = $.trim($("#totalProjectCostValue").text()).replace(/[,.$]/g,'');
            var actualCost = parseFloat(actualCostText);
            var projectedCost = actualCost + parseFloat($(".plannedCostValue").text());
            $(".actualCostSlot").html('$' + actualCost.formatMoney(0));
            $(".projectedCostSlot").html('$' + projectedCost.formatMoney(0));

            calculateProjectBudget();

            // fire the ajax request to load issues/races number
            $.ajax({
                type : 'POST',
                url : 'getProjectIssuesStatsAjax',
                cache : true,
                timeout:100*1000,
                dataType:"json",
                data : {formData:{projectId:tcDirectProjectId}},
                beforeSend: function() {
                    $("#unresolvedIssuesTD, #ongoingRacesTD").html("Loading data...");
                },
                success : function(jsonResult) {
                    handleJsonResult2(jsonResult, function(r){

                        var issuesHTML = '<a href="/direct/projectIssueTracking.action?formData.projectId='
                            + tcDirectProjectId + '&amp;subTab=issues">' + r.unresolvedIssuesNumber + '</a>';
                        var racesHTML = '<a href="/direct/projectIssueTracking.action?formData.projectId='
                            + tcDirectProjectId + '&amp;subTab=bugRaces">' + r.ongoingBugRacesNumber + '</a>';

                        $("#unresolvedIssuesTD").html(issuesHTML);
                        $("#ongoingRacesTD").html(racesHTML);
                    }, function(e) {
                        showErrors("Fail to load the project issues data");
                    });
                }
            });
        },
        error: function(result) {
            showErrors("Fail to load the project Statistics data");
        }
    });

    var isSameDay = function(d1, d2) {
        return (d1.getYear() == d2.getYear()) && (d1.getDayOfYear() == d2.getDayOfYear());
    }
    var getDateText = function(date, pattern) {
        date = new Date(date.time);
        var now = Date.today();

        var yesterday = Date.today().add({ days: -1});

        var tomorrow = Date.today().add({ days: 1});

        if (isSameDay(now, date)) {
            return "Today";
        } else if (isSameDay(date, yesterday)) {
            return "Yesterday";
        } else if (isSameDay(date, tomorrow)) {
            return "Tomorrow";
        } else {
            return date.toString(pattern);
        }
    }
    $.ajax({
        type : 'POST',
        url : 'getProjectActivitiesAjax',
        cache : false,
        timeout:100*1000,
        dataType:"json",
        data : {formData:{projectId:tcDirectProjectId}},
        success : function(result) {
            $("#projectActivities").find('.ajaxTableLoader').parents("tr").hide();
            var activitiesById = result.result.return; // title -> activities
            var activitiesHtml = '';
            for(var id in activitiesById) {
                var activities = activitiesById[id];
                for(var i = 0; i < activities.length; i++) {
                    var activity = activities[i];
                    var title = activity.title;
                        activitiesHtml +=
                        '<tr class="contestLaunch">' +
                        '<td class="contestActivities">' +
                        '<div class="' + activity.typeShortName + '">' +
                        '<div class="leftLaunch">' +
                        '<h3>' + title + '</h3>' +
                        '<p>' + activity.typeName + '</p>' +
                        '<a href="/direct/contest/detail?projectId=' + activity.contestId + '">View</a>' +
                        '</div>' +
                        '<div class="rightLaunch"><p>' + activity.typeActionText + ' : <a class="coderTextBlack" ' +
                        'href="' + activity.memberProfileUrlBase + '/tc?module=MemberProfile&cr=' + activity.originatorId + '">' + activity.originatorHandle + '</a></p>' +
                        '<p clas="date">' + getDateText(activity.date, 'MM/dd/yyyy') + '</p>' +
                        '</div>' +
                        '<div class="clearFix"></div>' +
                        '</div>' +
                        '</td>' +
                        '</tr>';
                }
            }
            if(!activitiesHtml) {
                activitiesHtml = '<tr><td style="text-align: center">No Recent Activities</td></tr>';
            }

            $("#projectActivities tbody").append(activitiesHtml);
        },
        error: function(result) {
            showErrors("Fail to load the project copilots data");
        }
    });
    
    // Added for configuring forum popups
    $('.buttonToolTip').live('mouseover', function() {
        $(this).find(".buttonToolTipContainer").removeClass("hide");
    });
    $('.buttonToolTip').live('mouseout', function() {
        $(this).find(".buttonToolTipContainer").addClass("hide");
    });
    
    $('.toolTip').live('mouseover', function() {
        $(this).append('<div class="toolTipContainer"><div class="arrow"></div><p class="textBox"></p></div>');
        $(this).find('p').append($(this).attr('rel'));
    });
    $('.toolTip').live('mouseout', function() {
        $(this).empty();
    });
    
    /* close modal */
    var modalNewClose = function() {
        $('#modalBackgroundNew').hide();
        $('.outLayNew').hide();
    };

    /* load modal (string itemID )*/
    var modalNewLoad = function(itemID) {
        modalNewClose();
        $('#modalBackgroundNew').show();
        $(itemID).show();
        modalNewPosition();
    };

    // close modal
    $('#new-modal-new .outLayNew .closeModal').live('click', function() {
        modalNewClose();
        return false;
    });

    // close modal
    $('#new-modal-new .outLayNew .saveButton,#new-modal-new .outLayNew .cancelButton').live('click', function() {
        modalNewClose();
        return false;
    });

    var modalNewPosition = function(){
        var wWidth  = window.innerWidth;
        var wHeight = window.innerHeight;

        if (wWidth==undefined) {
            wWidth  = document.documentElement.clientWidth;
            wHeight = document.documentElement.clientHeight;
        }

        var boxLeft = parseInt((wWidth / 2) - ( $("#new-modal-new").width() / 2 ));
        var boxTop  = parseInt((wHeight / 2) - ( $("#new-modal-new").height() / 2 ));

        // position modal
        $("#new-modal-new").css({
            'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
        });

        $("#modalBackgroundNew").css("opacity", 0.6);

        if ($("body").height() > $("#modalBackgroundNew").height()){
            $("#modalBackgroundNew").css("height", $("body").height() + "px");
        }
    };

    var showErrorsNew = function(error) {
        modalNewClose();
        $('#forumErrorModal .errorModal').html(error);
        $('#modalBackgroundNew').show();
        $('#forumErrorModal').show();
        modalNewPosition();
    };
    
    function validate(box) {
        var value = $.trim(box.val());
        var tips = box.data("tips")?box.data("tips"):"";
        if (value == "" || value == tips){
            box.parent().find(".errmessage").append('<span class="errorText">This field cannot be left empty.</span>');
            box.addClass("error").val("");
            return false;
        }
        if (value.indexOf('>') >= 0 || value.indexOf('<') >= 0) {
            box.parent().find(".errmessage").append('<span class="errorText">This field cannot contain special characters.</span>');
            box.addClass("error").val("");
            return false;
        }
        return true;
    }
    
    function removeError(box) {
        box.parent().find(".errorText,.errorIcon").remove();
        box.removeClass("error");
    }

    $('.configreButton').live('click', function() {
        modalLoad('#viewForumModal');
    });
    
    $('#viewForumModal .saveButton1').live('click', function() {
        modalAllClose();
    });
    
    $('.addMoreForum').live('click', function() {
        $("#createForumModalNew .descProject textarea").val("");
        $("#createForumModalNew .projectName input").val("");
        removeError($("#createForumModalNew .descProject textarea"));
        removeError($("#createForumModalNew .projectName input"));
        modalNewLoad('#createForumModalNew');
    });
    
    $('#createForumModalNew .saveButton1').live('click', function() {
        removeError($("#createForumModalNew .descProject textarea"));
        var valid1 =  validate($("#createForumModalNew .descProject textarea"));

        removeError($("#createForumModalNew .projectName input"));
        var valid2 = validate($("#createForumModalNew .projectName input"));
        
        if (!valid1 || !valid2) {
            return;
        }
        
        var forumName = $("#createForumModalNew .projectName input").val();
        var forumDesc = $("#createForumModalNew .descProject textarea").val();
        $.ajax({
            url: 'addProjectForumAJAX',
            type: 'POST',
            cache: false,
            data: setupTokenRequest({
                tcDirectProjectId: tcDirectProjectId,
                forumName: forumName,
                forumDescription: forumDesc
            }, getStruts2TokenName()),
            dataType: 'json',
            async: false,
            success: function(result) {
                if (!result.result) {
                    showErrorsNew(result.error.errorMessage);
                    return;
                } else {
                    var forumId = result.result["return"].forumId;
                    var popupRow = "<tr>";
                    popupRow += '<td><input class="selectForumCheck" type="checkbox"/><span class="group">' + forumName + '</span><span class="threadId hide">' + forumId + '</span></td>';
                    popupRow += '<td><span class="group">' + forumDesc + '</span></td>';
                    popupRow += '<td class="checkbox"><a class="deleteIcon" href="javascript:;"></a></td>';
                    popupRow += "</tr>";
                    $('.projectForumBody').append(popupRow);
                    var count = $('.projectForumTableBodyInner table tbody tr').length;
                    var tableRow = "";
                    if (count % 2 == 0) {
                        tableRow += "<tr>";
                    } else {
                        tableRow += '<tr class="odd">'
                    }
                    tableRow += '<td class="colTab1"><div><h3 class="isNew">';
                    tableRow += '<a href="https://' + SERVER_CONFIG_FORUM_SERVER_NAME + '/forums/?module=ThreadList&forumID=' + forumId + '" target="_blank">' + forumName + '</a>';
                    tableRow += '</h3><p>' + forumDesc + '</p></div></td>';
                    tableRow += '<td class="colTab2"><div>0/0</div></td>';
                    tableRow += '<td class="colTab3"></td>';
                    $('.projectForumTableBodyInner table tbody').append(tableRow);
                    modalNewClose();
                }
            },
            error: function(result) {
                showErrorsNew("Error when adding project forum");
            }
        });
    });
    
    var deleteForumId;
    var deleteForumIdx;
    
    $('.deleteIcon').live('click',function() {
        if ($(this).hasClass('disabledDeleteIcon')) {
            return;
        }
        deleteForumId = $(this).parent().parent().find('.threadId').html();
        deleteForumIdx = $(this).parent().parent().index();
        modalNewLoad('#deleteConfirmForumModal');
    });
    
    $('.deleteSelect').live('click',function() {
        if ($(".selectForumCheck:checked").length > 0) {
            modalNewLoad('#deleteConfirmForumModal');
        } else {
            modalNewLoad('#deleteErrorForumModal');
        }
    });
    
    $('#deleteConfirmForumModal .saveButton1').live('click',function() {
        var forumIds = [];
        var forumIdxes = [];
        if (deleteForumId != 0) {
            forumIds.push(deleteForumId);
            forumIdxes.push(deleteForumIdx);
            deleteForumId = 0;
            deleteForumIdx = 0;
        }
        $(".selectForumCheck:checked").each(function() {
            forumIds.push($(this).parent().find('.threadId').html());
            forumIdxes.push($(this).parent().parent().index());
        });
        $.ajax({
            url: 'deleteProjectForumsAJAX',
            type: 'POST',
            cache: false,
            data: setupTokenRequest({
                tcDirectProjectId: tcDirectProjectId,
                forumIds: forumIds
            }, getStruts2TokenName()),
            dataType: 'json',
            async: false,
            success: function(result) {
                if (!result.result) {
                    showErrorsNew(result.error.errorMessage);
                    return;
                } else {
                    for (var i = forumIdxes.length - 1; i >= 0; --i) {
                        $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:eq(" + forumIdxes[i] + ")").remove();
                        $('.projectForumBody tbody tr:eq(' + forumIdxes[i] + ')').remove();
                    }
                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr").removeClass('odd');
                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');
                    modalNewLoad('#noDeleteForumModal');
                }
            },
            error: function(result) {
                showErrorsNew("Error when deleting project forum");
            }
        });
    });

    // copilot feedback

    // Create copilot feedback rating pickers

    $(".ratingEdit span").live("mouseenter", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        wrapper.find("span").removeClass("active");
        wrapper.find("span:lt(" + (index + 1) + ")").addClass("active");
    });
    $(".ratingEdit span").live("mouseleave", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        var rating = wrapper.data("rating");
        wrapper.find("span").removeClass("active");
        wrapper.find("span:lt(" + rating + ")").addClass("active");
    });
    $(".ratingEdit span").live("click", function () {
        var wrapper = $(this).parent();
        var index = wrapper.find("span").index($(this));
        wrapper.find("span:lt(" + index + ")").addClass("active");
        wrapper.data("rating", index + 1);
        return false;
    });

    $(".ratingEdit").each(function () {
        var _this = $(this);
        for (var i = 0; i < 5; i++) {
            var span = $("<span/>").appendTo(_this);
        }

        // check the value
        var item = $(this).parent();
        var value = item.find("input[name='rating']").val();

        if (value <= 0) {
            _this.data("rating", 0);
        } else {
            $(this).find("span:eq(" + (value - 1) + ")").trigger('mouseenter').trigger('click');
        }

    });

    // 1) new copilot feedback
    $("a[name='newCopilotFeedbackModal']").click(function(){
        // clear inputs
        $("#newCopilotFeedbackModal textarea").val('');
        $("#newCopilotFeedbackModal input[type='radio']").each(function(){
           $(this).attr('checked', false);
        });
        $("#newCopilotFeedbackModal .rating .ratingEdit span").removeClass('active');
        $("#newCopilotFeedbackModal .errorMessage").text('');
        // fill in current copilot handle
        $("#newCopilotFeedbackModal span.copilotHandleSpan").html($("div.copilotsListButtonBox").data('copilot'));
        // show modal
        modalLoad('#' + $(this).attr('name'));

        $('#' + $(this).attr('name')).data('copilotProjectId', $("div.copilotsListButtonBox").data('copilotProjectId'));
    });

    $('.feedbackModal .cancelButton').live('click', function() {
        modalAllClose();
        return false;
    });

    $("a.createNewFeedback").click(function(){
        if(!validateFeedbackModal('newCopilotFeedbackModal')) {
            return;
        }

        var feedbackAnswer = $('input[name=workAgain]:checked', '#newCopilotFeedbackModal').val() == "yes";
        var feedbackText = $('.comment textarea', '#newCopilotFeedbackModal').val();
        var timelineRating = $(".rating .ratingEdit:eq(0) span.active", '#newCopilotFeedbackModal').length;
        var qualityRating = $(".rating .ratingEdit:eq(1) span.active", '#newCopilotFeedbackModal').length;
        var communicationRating = $(".rating .ratingEdit:eq(2) span.active", '#newCopilotFeedbackModal').length;
        var managementRating = $(".rating .ratingEdit:eq(3) span.active", '#newCopilotFeedbackModal').length;

        var formData = {};
        var feedback = {};
        feedback.answer = feedbackAnswer;
        feedback.text = feedbackText;
        feedback.timelineRating = timelineRating;
        feedback.qualityRating = qualityRating;
        feedback.communicationRating = communicationRating;
        feedback.managementRating = managementRating;
        formData.copilotProjectId = $('#newCopilotFeedbackModal').data('copilotProjectId');
        formData.feedback = feedback;
        formData.projectId = tcDirectProjectId;

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'createCopilotProjectFeedback',
            cache : false,
            data : setupTokenRequest({formData:formData}, getStruts2TokenName()),
            dataType : 'json',
            success : function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        if(result.result == 'success') {
                            var copilotInfo = getCurrentCopilotContainer().find(".userInfor");
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackStatus').val('Pending'));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackAnswer').val(feedbackAnswer));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackText').val(feedbackText));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackTimelineRating').val(timelineRating));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackQualityRating').val(qualityRating));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackCommunicationRating').val(communicationRating));
                            copilotInfo.append($("<input type='hidden'/>").attr('name', 'copilotFeedbackManagementRating').val(managementRating));
                            setupCopilotFeedbackCurrent($('#projectCopilotsCarousel').data('jcarousel').first);
                            showSuccessfulMessage("Your copilot feedback is added. It will be displayed on the copilot profile when approved.")
                        } else {
                            showServerError("Unknown error happened");
                        }

                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    })
            },
            error: function(result) {
                showErrors("Error when adding feedback for the copilot");
            }
        });

    });

    // 2) edit feedback modal
    $("a[name='editCopilotFeedbackModal']").click(function(){
        // clear inputs and populate feedback data
        $("#editCopilotFeedbackModal textarea").val($("div.copilotsListButtonBox").data('copilotFeedbackText'));
        var feedbackAnswer = $("div.copilotsListButtonBox").data('copilotFeedbackAnswer') == 'true' ? 'yes' : 'no';
        $("#editCopilotFeedbackModal input[name=workAgain][value=" + feedbackAnswer + "]").attr('checked', 'checked');
        $("#editCopilotFeedbackModal .rating .ratingEdit span").removeClass('active');
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(0) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackTimelineRating') + ")")
            .addClass('active').parents('.ratingEdit').data("rating", $("div.copilotsListButtonBox").data('copilotFeedbackTimelineRating'));
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(1) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackQualityRating') + ")")
            .addClass('active').parents('.ratingEdit').data("rating", $("div.copilotsListButtonBox").data('copilotFeedbackQualityRating'));
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(2) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackCommunicationRating') + ")")
            .addClass('active').parents('.ratingEdit').data("rating", $("div.copilotsListButtonBox").data('copilotFeedbackCommunicationRating'));
        $("#editCopilotFeedbackModal .rating .ratingEdit:eq(3) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackManagementRating') + ")")
            .addClass('active').parents('.ratingEdit').data("rating", $("div.copilotsListButtonBox").data('copilotFeedbackManagementRating'));

        // fill in current copilot handle
        $("#editCopilotFeedbackModal .question p span").html($("div.copilotsListButtonBox").data('copilot'));
        // show modal
        validateFeedbackModal('editCopilotFeedbackModal');
        modalLoad('#' + $(this).attr('name'));
        $('#' + $(this).attr('name')).data('copilotProjectId', $("div.copilotsListButtonBox").data('copilotProjectId'));
    });

    $("a.editNewFeedback").click(function(){
        if(!validateFeedbackModal('editCopilotFeedbackModal')) {
            return;
        }

        var feedbackAnswer = $('input[name=workAgain]:checked', '#editCopilotFeedbackModal').val() == "yes";
        var feedbackText = $('.comment textarea', '#editCopilotFeedbackModal').val();
        var timelineRating = $(".rating .ratingEdit:eq(0) span.active", '#editCopilotFeedbackModal').length;
        var qualityRating = $(".rating .ratingEdit:eq(1) span.active", '#editCopilotFeedbackModal').length;
        var communicationRating = $(".rating .ratingEdit:eq(2) span.active", '#editCopilotFeedbackModal').length;
        var managementRating = $(".rating .ratingEdit:eq(3) span.active", '#editCopilotFeedbackModal').length;

        var formData = {};
        var feedback = {};
        feedback.answer = feedbackAnswer;
        feedback.text = feedbackText;
        feedback.status = "Pending";
        feedback.timelineRating = timelineRating;
        feedback.qualityRating = qualityRating;
        feedback.communicationRating = communicationRating;
        feedback.managementRating = managementRating;
        formData.copilotProjectId = $('#editCopilotFeedbackModal').data('copilotProjectId');
        formData.feedback = feedback;
        formData.projectId = tcDirectProjectId;

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'updateCopilotProjectFeedback',
            cache : false,
            data : setupTokenRequest({formData:formData}, getStruts2TokenName()),
            dataType : 'json',
            success : function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        if(result.result == 'success') {
                            var copilotInfo = getCurrentCopilotContainer().find(".userInfor");
                            copilotInfo.find('input[name=copilotFeedbackAnswer]').val(feedbackAnswer);
                            copilotInfo.find('input[name=copilotFeedbackText]').val(feedbackText);
                            copilotInfo.find('input[name=copilotFeedbackTimelineRating]').val(timelineRating);
                            copilotInfo.find('input[name=copilotFeedbackQualityRating]').val(qualityRating);
                            copilotInfo.find('input[name=copilotFeedbackCommunicationRating]').val(communicationRating);
                            copilotInfo.find('input[name=copilotFeedbackManagementRating]').val(managementRating);
                            setupCopilotFeedbackCurrent($('#projectCopilotsCarousel').data('jcarousel').first);
                        } else {
                            showServerError("Unknown error happened");
                        }

                        showSuccessfulMessage("Your feedback has been edited and is awaiting approval.");

                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    })
            },
            error: function(result) {
                showErrors("Error when adding feedback for the copilot");
            }
        });

    });

    // 3) view feedback modal
    $("a[name='viewCopilotFeedbackModal']").click(function(){
        // populate feedback data

        // 1) feedback comment
        $("#viewCopilotFeedbackModal .comment span.text").text($("div.copilotsListButtonBox").data('copilotFeedbackText'));

        // 2) feedback answer
        var feedbackAnswer = $("div.copilotsListButtonBox").data('copilotFeedbackAnswer') == 'true' ? 'YES' : 'NO';
        $("#viewCopilotFeedbackModal .question span.answer").text(feedbackAnswer);

        // 3) feedback ratings
        var rating = $("div.copilotsListButtonBox").data('copilotFeedbackTimelineRating');
        if(rating && rating > 0) {
            $("#viewCopilotFeedbackModal .rating .ratingView span").removeClass("active");
            $("#viewCopilotFeedbackModal .rating .ratingView:eq(0) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackTimelineRating') + ")").addClass('active');
            $("#viewCopilotFeedbackModal .rating .ratingView:eq(1) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackQualityRating') + ")").addClass('active');
            $("#viewCopilotFeedbackModal .rating .ratingView:eq(2) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackCommunicationRating') + ")").addClass('active');
            $("#viewCopilotFeedbackModal .rating .ratingView:eq(3) span:lt(" + $("div.copilotsListButtonBox").data('copilotFeedbackManagementRating') + ")").addClass('active');
            $("#viewCopilotFeedbackModal div.rating").show();
        } else {
            $("#viewCopilotFeedbackModal div.rating").hide();
        }


        // fill in current copilot handle
        $("#viewCopilotFeedbackModal span.copilotHandleSpan").html($("div.copilotsListButtonBox").data('copilot'));
        // show modal
        modalLoad('#' + $(this).attr('name'));
        $('#' + $(this).attr('name')).data('copilotProjectId', $("div.copilotsListButtonBox").data('copilotProjectId'));
    });
});

function onCopilotCarouselChange(carousel, state) {
    setupCopilotFeedbackCurrent(carousel.first);
}

function getCurrentCopilotContainer() {
    return $('.itemContainer .userInfor', "li.jcarousel-item-"  + $('#projectCopilotsCarousel').data('jcarousel').first).parent();
}

function setupCopilotFeedbackCurrent(index) {
    var container = $('.itemContainer .userInfor', "li.jcarousel-item-" + index);

    // associate current copilot feedback data
    $("div.copilotsListButtonBox").data('copilotProjectId', container.find("input[name='copilotProjectIdInput']").val());
    $("div.copilotsListButtonBox").data('copilot', container.parent().find(".handleLink").html());


    if (container.find("input[name='copilotFeedbackStatus']").length == 0) {
        // no copilot feedback for this copilot

        // show leave feedback button
        $(".copilotsListButtonBox a[name='newCopilotFeedbackModal']").show();

        // hide edit feedback and view feedback button
        $(".copilotsListButtonBox a[name='viewCopilotFeedbackModal']").hide();
        $(".copilotsListButtonBox a[name='editCopilotFeedbackModal']").hide();
        $("div.copilotsListButtonBox").removeData('copilotFeedbackStatus');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackAnswer');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackText');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackTimelineRating');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackQualityRating');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackCommunicationRating');
        $("div.copilotsListButtonBox").removeData('copilotFeedbackManagementRating');

    } else {
        // has copilot feedback for this copilot
        $(".copilotsListButtonBox a[name='newCopilotFeedbackModal']").hide();


        if (container.find("input[name='copilotFeedbackStatus']").val().toLowerCase() != "pending") {
            // if status is pending, show edit feedback button
            $(".copilotsListButtonBox a[name='viewCopilotFeedbackModal']").show();
            $(".copilotsListButtonBox a[name='editCopilotFeedbackModal']").hide();
        } else {
            // status is approved, show view feedback button
            $(".copilotsListButtonBox a[name='viewCopilotFeedbackModal']").hide();
            $(".copilotsListButtonBox a[name='editCopilotFeedbackModal']").show();
        }

        // put the feedback data into the jquery data of this copilot view
        $("div.copilotsListButtonBox").data('copilotFeedbackStatus', container.find("input[name='copilotFeedbackStatus']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackAnswer', container.find("input[name='copilotFeedbackAnswer']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackText', container.find("input[name='copilotFeedbackText']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackTimelineRating', container.find("input[name='copilotFeedbackTimelineRating']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackQualityRating', container.find("input[name='copilotFeedbackQualityRating']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackCommunicationRating', container.find("input[name='copilotFeedbackCommunicationRating']").val());
        $("div.copilotsListButtonBox").data('copilotFeedbackManagementRating', container.find("input[name='copilotFeedbackManagementRating']").val());
    }
}

function validateFeedbackModal(modalId) {
    var modal = $("#" + modalId);
    var passValidation = true;

    // validate feedback answer
    if(modal.find("input[name=workAgain]:checked").length == 0) {
        modal.find(".question span.errorMessage").text("Please choose an answer");
        passValidation = false;
    } else {
        modal.find(".question span.errorMessage").text('');
    }

    // validate feedback ratings
    var passRatingValidation = true;
    modal.find(".rating .ratingEdit").each(function(){
        var ratingValue = getFeedbackRating($(this));
        if (ratingValue < 1) {
            // at least one star is needed for each rating
            passValidation = false;
            passRatingValidation = false;
        }
    })

    if(passRatingValidation) {
        modal.find(".rating span.errorMessage").text('');
    } else {
        modal.find(".rating span.errorMessage").text('Please give your ratings on each rating category, at least 1 star needs to give');
    }

    // validate feedback comment
    if($.trim(modal.find(".comment textarea").val()).length == 0) {
        modal.find(".comment span.errorMessage").text("Please enter your feedback here");
        passValidation = false;
        return false;
    } else {
        modal.find(".comment span.errorMessage").text('');
    }

    if($.trim(modal.find(".comment textarea").val()).length > 800) {
        modal.find(".comment span.errorMessage").text("Your feedback text cannot exceed 800 chars");
        passValidation = false;
    } else {
        modal.find(".comment span.errorMessage").text('');
    }

    return passValidation;
}

function getFeedbackRating(ratingControl) {
    return ratingControl.find("span.active").length;
}

