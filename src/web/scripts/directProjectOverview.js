/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * <p>
 *  Version 1.0: Provides project copilots management and project forum creation features to project overview page.
 * </p>
 *
 * <p>
 *  Version 1.1 (Module Assembly - TC Cockpit Project Overview Project General Info): Adds the JS codes for project general information table.
 * </p>
 *
 * @author Blues
 * @version 1.1
 * @since Release Assembly - TopCoder Cockpit Project Overview Update 1
 */
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

    var iProjectBudget = parseInt($('.projectBudget dd:last').text().replace('$', '').replace(/,/g, '') / $('.projectBudget dd:first').text().replace('$', '').replace(/,/g, '') * 100);
    if(!isNaN(iProjectBudget)) $('.projectBudget .totalBudget .midActual').css('width', 1.87 * iProjectBudget);
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

    var iprojectDuration = parseInt($('.projectDuration dd:last').text().replace('days', '') / $('.projectDuration dd:first').text().replace('days', '') * 100);
    if(!isNaN(iprojectDuration)) $('.projectDuration .plannedDuration .midActual').css('width', 1.87 * iprojectDuration);
    if (iprojectDuration < 70) {
        $('.projectDuration .plannedDuration').addClass('green');
        $('.projectDuration .totalBudgetInfor').addClass('green');
        if (iprojectDuration < 5) {
            $('.projectBudget .totalBudget .actualCost').css('width', 1.95 * iProjectBudget);
        }
    } else if (iprojectDuration >= 100) {
        $('.projectDuration .plannedDuration').addClass('red');
        $('.projectDuration .totalBudgetInfor').addClass('red');
        $('.projectDuration .actualDuration .midActual').css('width', '187px');
    } else {
        $('.projectDuration .plannedDuration').addClass('yellow');
        $('.projectDuration .totalBudgetInfor').addClass('yellow');
    }

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


    if ($("#projectForumTable").length > 0) {

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
                strTableData = "";
                strTmp = "";
                $.each(json.result['return'].projectForumThreads, function(idx, item) {
                    strTableData += '<tr>';
                    strTableData += '<td class="colTab1">';
                    strTableData += '<div>';

                    isReadStatus = (item.isRead == false ? "isNew" : " ");

                    strTableData += '<h3 class="' + isReadStatus + '"><a target="_blank" href="http://apps.topcoder.com/forums/?module=ThreadList&forumID=' + item.threadID + '">' + item.threadTitle + '</a></h3>';
                    strTableData += '<p>' + item.summary + '</p>';
                    strTableData += '</div>';
                    strTableData += '</td>';
                    strTableData += '<td class="colTab2">';
                    strTableData += '<div>' + item.threadNumber + '/' + item.messageNumber + '</div>';
                    strTableData += '</td>';
                    strTableData += '<td class="colTab3">';
//                    strTableData += '<a href="#" class="author">' + item.lastPostHandle + '</a>';
                    strTableData += item.latestPostAuthorLink;
                    strTableData += '<p>' + item.lastPostTime + '</p>';
                    strTableData += '</td>';
                    strTableData += '</tr>';
                });
                strTmp += '<table cellpadding="0" cellspacing="0"><tbody>';
                strTmp += strTableData;
                strTmp += '</tbody></table>';
                
                $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner").empty().append(strTmp);
                $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');

                ajaxTableLoader = setTimeout(function() {

                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner").empty().append(strTmp);
                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');
                }, ajaxTableTimer);
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
                    data : request,
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

    // display the jcarousel for project copilots
    if ($('#projectCopilotsCarousel').length > 0) {
        $('#projectCopilotsCarousel').jcarousel({
            scroll: 1,
            visible: 1
        });

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
    $('#new-modal .outLay .saveButton').live('click', function() {

        var request = {
            copilotProjectOperations : getCopilotWidgetOperations()
        };

        modalAllClose();
        modalPreloader();

        $.ajax({
            type : 'post',
            url : 'copilot/processCopilotProjects',
            cache : false,
            data : request,
            dataType : 'json',
            success : function(result) {
                modalAllClose();

                if (!result.result) {
                    showErrors(result.error.errorMessage);
                    return;
                }

                location.reload(true);
            },
            error: function(result) {
                showErrors("Error when saving project copilots");
            }
        });

        return false;
    });


    // close button
    $('#new-modal .outLay .cancelButton').live('click', function() {
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
        type : 'get',
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

});
