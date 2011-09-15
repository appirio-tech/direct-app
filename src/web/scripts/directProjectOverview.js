/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 *
 * <p>
 *  Provides project copilots management and project forum creation features to project overview page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Release Assembly - TopCoder Cockpit Project Overview Update 1
 */
$(document).ready(function() {

//    if ($("#projectForumTable").length > 0) {
//
//        var ajaxTableTimer = 2000; // timer for ajax table list
//        var ajaxTableLoader, strTableData = '', isReadStatus = '';
//        var strTmp = '';
//
//        $.ajax({
//            type: "GET",
//            url: "./data/dataProjectForumThreads.json",
//            dataType: "json",
//            success: function(json) {
//                $.each(json.projectForumThreads, function(idx, item) {
//                    strTableData += '<tr>';
//                    strTableData += '<td class="colTab1">';
//                    strTableData += '<div>';
//
//                    isReadStatus = (item.isRead == 'false' ? "isNew" : " ");
//
//                    strTableData += '<h3 class="' + isReadStatus + '"><a href="http://apps.topcoder.com/forums/?module=ThreadList&forumID=' + item.threadID + '">' + item.threadTitle + '</a></h3>';
//                    strTableData += '<p>' + item.summary + '</p>';
//                    strTableData += '</div>';
//                    strTableData += '</td>';
//                    strTableData += '<td class="colTab2">';
//                    strTableData += '<div>' + item.threadNumber + '/' + item.messageNumber + '</div>';
//                    strTableData += '</td>';
//                    strTableData += '<td class="colTab3">';
//                    strTableData += '<a href="#" class="author">' + item.lastPostHandle + '</a>';
//                    strTableData += '<p>' + item.lastPostTime + '</p>';
//                    strTableData += '</td>';
//                    strTableData += '</tr>';
//                });
//                strTmp += '<table cellpadding="0" cellspacing="0"><tbody>';
//                strTmp += strTableData;
//                strTmp += '</tbody></table>';
//
//                ajaxTableLoader = setTimeout(function() {
//
//                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner").empty().append(strTmp);
//                    $("#projectForumTable .projectForumTableBody .projectForumTableBodyInner table tr:odd").addClass('odd');
//                }, ajaxTableTimer);
//            }
//        });
//    }
//
//    $('.projectForumLeader .projectForumLeaderButton .buttonRed1').attr('target', '_blank');
//
//
//    }

    // the event handler of project forum creation button
    $(".projectForumLeader .createForumButton").live('click', function() {

        var request = {
            tcDirectProjectId : tcDirectProjectId
        };

        showComingSoon( "This feature is coming soon. If you would like a project forum created in the meantime," +
            ' or already have a forum that you would like to add here please contact <span class="mailTo"><a href="mailTo:support@topcoder.com">TopCoder Support</a></span>');
        return;

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