/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for data table filter panel.
 *
 * @version 1.0 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar)
 *
 * Version 1.1 (Release Assembly - TC Cockpit All Projects Management Page Update) changes:
 * - Add handling for project filters and project filter values select in all projects and search project pages.
 *
 * Version 1.2 (Release Assembly - TC Cockpit Enterprise Calendar Revamp)
 * - Add filter panel for the enterprise milestone calendar
 *
 * @since Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar
 */
(function($) {
    /*
     * Function: fnGetColumnData
     * Purpose:  Return an array of table values from a particular column.
     * Returns:  array string: 1d data array
     * Inputs:   object:oSettings - dataTable settings object. This is always the last argument past to the function
     *           int:iColumn - the id of the column to extract the data from
     *           bool:bUnique - optional - if set to false duplicated values are not filtered out
     *           bool:bFiltered - optional - if set to false all the table data is used (not only the filtered)
     *           bool:bIgnoreEmpty - optional - if set to false empty values are not filtered from the result array
     * Author:   Benedikt Forchhammer <b.forchhammer /AT\ mind2.de>
     */
    $.fn.dataTableExt.oApi.fnGetColumnData = function (oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
        // check that we have a column id
        if (typeof iColumn == "undefined") return new Array();

        // by default we only wany unique data
        if (typeof bUnique == "undefined") bUnique = true;

        // by default we do want to only look at filtered data
        if (typeof bFiltered == "undefined") bFiltered = true;

        // by default we do not wany to include empty values
        if (typeof bIgnoreEmpty == "undefined") bIgnoreEmpty = true;

        // list of rows which we're going to loop through
        var aiRows;
        // use only filtered rows
        if (bFiltered == true) aiRows = oSettings.aiDisplay;
        // use all rows
        else aiRows = oSettings.aiDisplayMaster; // all row numbers

        // set up data array
        var asResultData = new Array();
        for (var i = 0, c = aiRows.length; i < c; i++) {
            iRow = aiRows[i];
            var aData = this.fnGetData(iRow);
            var sValue = aData[iColumn];

            // ignore empty values?
            if (bIgnoreEmpty == true && sValue.length == 0) continue;

            // ignore unique values?
            else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1) continue;

            // else push the value onto the result data array
            else asResultData.push(sValue);
        }
        return asResultData;
    }
}(jQuery));

function filterbyCustomer(id, filterStr) {
    $('#customerFilter').val(filterStr);
    if (handleName == "projectsResult") {
        tableHandle.fnFilter(id, 10);
    }
    else if (handleName == "activeContests") {
        tableHandle.fnFilter(id, 10);
    }
    else if (handleName == "MyCopilotPostings") {
        tableHandle.fnFilter(id, 9);
    }
}

// Function for converting a mm/dd/yyyy date value into a numeric string for comparison (example 08/12/2010 becomes 20100812
var parseDateValue = function(rawDate) {
    if ($.trim(rawDate) == '') {
        return '';
    }
    rawDate = $.trim(rawDate);
    var dateArray = rawDate.split(" ")[0];
    dateArray = dateArray.split("/");
    var parsedDate = dateArray[2] + dateArray[0] + dateArray[1];
    return parsedDate;
}

var handleName;
var tableHandle;

// store the mapping of project filters - metadata key
var currentProjectFilters = {};
// store the mapping of meta data keys to values
var currentProjectFilterValuesMap = {};

var setupFilterPanel = function () {

    if (handleName == "activeContests") { //active contests table
        tableHandle = $.activeContestsDataTable;
    }
    else if (handleName == "projectsResult") { //all projects table
        tableHandle = $.allProjectTable;
    }
    else if (handleName == "MyCopilotPostings") {//my copilot postings
        tableHandle = $.myCopilotPostings;
    }
    else if (handleName == "ProjectsContests") { //project contests
        tableHandle = $.ProjectsContests;
    } else if (handleName == 'vmFilter') {
        tableHandle = vmTable;
    }
    //common part
    var customerFilter, len;

    $('.customerSelectMask .contestsDropDown li a').each(function () {
        var value = $(this).html();

        if (value != 'All Customers')
            $('#customerFilter').append("<option value='" + value + "'>" + value + "</option>");
    })
    //all projects
    if (handleName == "projectsResult") {
        var projectStatusFilter = tableHandle.fnGetColumnData(8);
        var len = projectStatusFilter.length;
        var statusMap = {};
        for (var i = 0; i < len; i++) {
            var index1 = projectStatusFilter[i].indexOf('>');
            var index2 = projectStatusFilter[i].indexOf('<', 1);
            var value = projectStatusFilter[i].substring(index1 + 1, index2);

            statusMap[value] = true;
        }

        $.each(statusMap, function (key, value) {
            $('#projectStatusFilter').append("<option value=" + key + ">" + key + "</option>");
        });
    }

    //active contests
    if (handleName == "activeContests") {
        var projectNameFilter = tableHandle.fnGetColumnData(2);
        len = projectNameFilter.length;
        for (var i = 0; i < len; i++) {
            var index1 = projectNameFilter[i].indexOf('>');
            index1 = projectNameFilter[i].indexOf('>', index1 + 1);
            var index2 = projectNameFilter[i].indexOf('<', 1);
            index2 = projectNameFilter[i].indexOf('<', index2 + 1);
            index2 = projectNameFilter[i].indexOf('<', index2 + 1);
            var value = $.trim(projectNameFilter[i].substring(index1 + 1, index2));
            $('#projectNameFilter').append('<option>' + value + '</option>');
        }
    }


    if (handleName == "activeContests" || handleName == "ProjectsContests") {
        var contestTypeFilter = tableHandle.fnGetColumnData(0);
        for (var i = 0; i < contestTypeFilter.length; i++) {
            $('#contestTypeFilter').append("<option value=" + contestTypeFilter[i] + ">" + contestTypeFilter[i] + "</option>");
        }
    }

    var contestStatusFilter;

    if (handleName == 'activeContests')
        contestStatusFilter = tableHandle.fnGetColumnData(8);

    else if (handleName == 'ProjectsContests')
        contestStatusFilter = tableHandle.fnGetColumnData(7);

    if (handleName == 'activeContests' || handleName == 'ProjectsContests') {
        len = contestStatusFilter.length;
        for (var i = 0; i < len; i++) {
            var index1 = contestStatusFilter[i].indexOf('>');
            var index2 = contestStatusFilter[i].indexOf('<', 1);
            var value = contestStatusFilter[i].substring(index1 + 1, index2);

            $('#contestStatusFilter').append("<option value='" + value + "'>" + value + "</option>");
        }
    }
    //copilot posting
    if (handleName == "MyCopilotPostings") {
        var copilotPostingStatusFilter = tableHandle.fnGetColumnData(7);
        len = copilotPostingStatusFilter.length;
        for (var i = 0; i < len; i++) {
            var index1 = copilotPostingStatusFilter[i].indexOf('>');
            var index2 = copilotPostingStatusFilter[i].indexOf('</', 1);
            var value = $.trim(copilotPostingStatusFilter[i].substring(index1 + 1, index2));
            $('#copilotPostingStatusFilter').append("<option value='" + value + "'>" + value + "</option>");
        }
    }

    // VM Management
    if (handleName == 'vmFilter') {

        var additionalColumnNumber = $("#contest_vms thead th").length - 11;

        var contestNameFilter = tableHandle.fnGetColumnData(1);
        $('#contestNameFilter option:gt(0)').remove();
        len = contestNameFilter.length;
        for (var i = 0; i < len; i++) {
            var name = contestNameFilter[i];
            $('#contestNameFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var memberHandleFilter = tableHandle.fnGetColumnData(5);
        len = memberHandleFilter.length;
        $('#memberHandleFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = memberHandleFilter[i];
            $('#memberHandleFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var usageFilter = tableHandle.fnGetColumnData(8 + additionalColumnNumber);
        len = usageFilter.length;
        $('#vmUsageFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = $.trim(usageFilter[i]);
            if (name.length > 0)
                $('#vmUsageFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        $('#contestNameFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';

            tableHandle.fnFilter(str, 1);
        });

        $('#memberHandleFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 5);
        });

        $('#vmUsageFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 8 + additionalColumnNumber);
        });

        if ($("#memberHandleFilter").length > 0) {
            var all_option = $("#memberHandleFilter option:eq(0)")
            var my_options = $("#memberHandleFilter option:gt(0)");

            my_options.sort(function (a, b) {
                a = a.text.toUpperCase();
                b = b.text.toUpperCase();
                if (a > b) return 1;
                else if (a < b) return -1;
                else return 0
            })

            $("#memberHandleFilter").empty().append(all_option).append(my_options);
            $("#memberHandleFilter option:eq(0)").attr('selected', 'selected');
        }
    }


    $('#customerFilter').change(function () {
        var str = $(this).val();
        $(".customerSelectMask").find(".inputSelect input").val(str);

        var customerId;

        $(".customerSelectMask  ul li a").each(function (index, element) {
            if ($(this).html() == str) {
                customerId = $(this).parent().data("id");
                $(this).parent().trigger('click');
            }
        });

        // handle the change of project filters and project filter values select, only apply for
        // all projects page and project search page
        if ($("#allProjectsFilter").size() > 0) {
            if (str != 'All Customers' && str != 'No Customer') {
                $("#groupBy").attr('disabled', false).val('no');
                $("#groupValue").attr('disabled', false).val('all');

                // reset the cache maps
                currentProjectFilters = {};
                currentProjectFilterValuesMap = {};

                // remove existing options
                $("#groupBy option:gt(0)").remove();
                $("#groupValue option:gt(0)").remove();

                // load project filters options
                $("#projectsResult table.projectStats tr").each(function(){
                    var metadataTD = $(this).find("td.metadataTD");
                    metadataTD.find(".metadataGroup").each(function(){
                        var keyId = $(this).find(".metadataKeyId").text();
                        var keyName = $(this).find(".metadataKeyName").text();
                        if(currentProjectFilters[keyId] == null) {
                            currentProjectFilters[keyId] = keyName;
                        }
                        if (currentProjectFilterValuesMap[keyId] == null) {
                            currentProjectFilterValuesMap[keyId] = {};
                        }

                        $(this).find(".metadataValue").each(function(){
                            var value = $(this).text();
                            currentProjectFilterValuesMap[keyId][value] = value;
                        });

                    });
                });

                var a = [], b = {};

                $.each(currentProjectFilters, function (key, value) {
                    a.push(value);
                    b[value] = key;
                });

                a.sort();
                for(var i = 0; i < a.length; ++i) {
                    $("#groupBy").append($("<option></option>").attr('value', b[a[i]]).text(a[i]));
                }

            } else {
                $("#groupBy").val('no').attr('disabled', true);
                $("#groupValue").val('all').attr('disabled', true);
            }
        }

        // handle the customer change for enterprise calendar
        if ($(".enterpriseCalendar").length > 0) {

            var selector = $("#groupBy");

            $("#groupValue option:gt(0)").remove();

            if (str != 'All Customers' && str != 'No Customer') {
                $.ajax({
                    type:'GET',
                    url:ctx + "/getGroupByOptionsForCustomer",
                    data:{customerId:customerId},
                    cache:false,
                    dataType:'json',
                    success:function (jsonResult) {
                        handleJsonResult2(jsonResult,
                                function (result) {
                                    selector.empty();

                                    selector.append($("<option></option>").attr('value', 0).text("No Filter Applied"));

                                    $.each(result, function (index, value) {
                                        selector.append($("<option></option>").attr('value', value.id).text(value.name));
                                    });

                                    $("#groupBy").val('0').attr('disabled', false);
                                    $("#groupValue").val('all').attr('disabled', false);

                                },
                                function (errorMessage) {
                                    showErrors(errorMessage);
                                });
                    }
                });
            } else {
                $("#groupBy").val('0').attr('disabled', true);
                $("#groupValue").val('all').attr('disabled', true);
            }

            if (str == 'No Customer') {
                customerId = 0;
            } else if (str == 'All Customers') {
                customerId = -1;
            }

            loadEnterpriseCalendar(customerId, null, null, true);
        }

    });


    if ($(".enterpriseCalendar").length > 0) {

        // set up the change event for "Project Filters"
        $("#groupBy").change(function () {
            var filterId = $(this).val();
            var reload = $("#groupValue").val() != 'all';

            // clear the project filter values drop down
            $("#groupValue option:gt(0)").remove();

            if (filterId == 0) {
                // No Filter Applied
                $("#groupValue").val('all');
                $("#groupValue").attr('disabled', false);
            } else {
                // load filter values
                $.ajax({
                    type:'GET',
                    url:ctx + "/getGroupValuesForGroupBy",
                    data:{groupKeyId:filterId},
                    cache:false,
                    dataType:'json',
                    success:function (jsonResult) {
                        handleJsonResult2(jsonResult,
                                function (result) {

                                    $.each(result, function (index, value) {
                                        $("#groupValue").append($("<option></option>").attr('value', value).text(value));
                                    });

                                    // append none item
                                    $("#groupValue").append($("<option></option>").attr('value', 'none').text('None'));

                                },
                                function (errorMessage) {
                                    showErrors(errorMessage);
                                });
                    }
                });
            }

            if (reload) {
                $("#customerFilter").trigger('change');
            }

        });

        // set up the change event for "Project Filter Values"
        $("#groupValue").change(function () {
            var value = $(this).val();
            var customerValue = $("#customerFilter").val();

            var customerId;

            $(".customerSelectMask  ul li a").each(function (index, element) {
                if ($(this).html() == customerValue) {
                    customerId = $(this).parent().data("id");
                }
            });

            if (value.toLowerCase() == 'all') {
                loadEnterpriseCalendar(customerId, null, null, true);
            } else {
                var projectFilterId = $("#groupBy").val();
                loadEnterpriseCalendar(customerId, projectFilterId, value, true);
            }

        });

    }

    if ($("#allProjectsFilter").size() > 0) {
        // setup the change event for "Project Filters" select
        $("#groupBy").change(function () {
            var keyId = $(this).val();
            if (keyId == 'no') {
                $("#groupValue option:gt(0)").remove();
            } else {
                $("#groupValue option:gt(0)").remove();
                var valuesMap = currentProjectFilterValuesMap[keyId];
                var valuesArray = [];
                $.each(valuesMap, function(key, value) {
                    valuesArray.push(key);
                });
                valuesArray.sort();
                valuesArray.push("None");
                $.each(valuesArray, function (index, value) {
                    $("#groupValue").append($("<option></option>").attr('value', value).text(value));
                });
            }
            tableHandle.fnFilter('');
        });

        // setup the change event for "Project Filter Values" select
        $("#groupValue").change(function(){
            tableHandle.fnFilter('');
        });

        $("#groupBy").val('no');
        $("#groupValue").val('all');
        $("#projectStatusFilter").val('All');
    }


    $('#projectStatusFilter').change(function () {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';
        tableHandle.fnFilter(str, 8);
    });
    $('#contestTypeFilter').change(function () {
        var str = $(this).val();
        var searchFor;
        if (str.indexOf('All') != -1) {
            searchFor = '';
        }
        else {
            searchFor = $(this).find("option:selected").text();
        }
        tableHandle.fnFilter(searchFor, 0);
    });
    $('#contestStatusFilter').change(function() {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';

        if (handleName == 'activeContests')
            tableHandle.fnFilter(str, 8);
        else if (handleName == 'ProjectsContests') {
            tableHandle.fnFilter(str, 7, true);
        }
    });
    $('#projectNameFilter').change(function() {
        var str = $(this).find("option:selected").text();
        if (str.indexOf('All') != -1)str = '';
        tableHandle.fnFilter(str, 2);
    });
    $('#copilotPostingStatusFilter').change(function() {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';
        tableHandle.fnFilter(str, 7);
    });
    $('input.searchBox').keyup(function(event) {
        tableHandle.fnDraw();
    });
    if ($("#VMFilter").length > 0) {
        $('input.searchBox').keyup(function (event) {
            var filterStr = $('input.searchBox').val();
            tableHandle.fnFilter(filterStr);
        });
    }


    $("#startDateBegin").keyup(function () {
        tableHandle.fnDraw();
    });

    $("#startDateBegin").change(function() {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#startDateEnd").keyup(function() {
        tableHandle.fnDraw();
    });

    $("#startDateEnd").change(function () {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#endDateBegin").keyup(function () {
        tableHandle.fnDraw();
    });

    $("#endDateBegin").change(function () {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#endDateEnd").keyup(function () {
        tableHandle.fnDraw();
    });

    $("#endDateEnd").change(function () {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

}

$(function() {

    var left = $('#activeContestsFilterSearch').parent().width() - 333;
    $('#activeContestsFilterSearch').css({'margin-left':left + 'px'});
    if ($('.filterContent .column3').prev().hasClass('column2')) {
        $('.filterContent .column3').css({'width':'300px'});
        $('#allProjectsFilter .filterContent .column3').css({'width':'340px'});
        $('#enterpriseCalendarFilter .filterContent .column3').css({'width':'auto'});
    }
    if (jQuery.browser.msie && jQuery.browser.version == 9) {
        $('.filterSearch').find('img').css({'margin-left':'-22px'});
    }
    $(window).resize(function() {
        left = $('#activeContestsFilterSearch').parent().width() - 333;
        $('#activeContestsFilterSearch').css({'margin-left':left + 'px'});
    });
    $('.collapse').live('click', function() {
        $(this).removeClass('collapse').addClass('expand');
        $(this).find('img').attr("src", "/images/filter-panel/collapse_icon.png");
        $(this).parent().parent().parent().parent().parent().find('.filterContent').hide();
        $(this).parent().parent().parent().parent().parent().find('.filterBottom').hide();
        $(this).parent().parent().parent().parent().parent().find('.collapseBottom').show();
        $(this).parent().parent().parent().parent().css({"margin-bottom":"-2px","height":'30px'});
        $(this).parent().parent().parent().parent().find(".rightSide").css({"height":'30px'});
    });
    $('.expand').live('click', function() {
        $(this).removeClass('expand').addClass('collapse');
        $(this).find('img').attr("src", "/images/filter-panel/expand_icon.png");
        $(this).parent().parent().parent().parent().parent().find('.filterContent').show();
        $(this).parent().parent().parent().parent().parent().find('.filterBottom').show();
        $(this).parent().parent().parent().parent().parent().find('.collapseBottom').hide();
        $(this).parent().parent().parent().parent().css({"margin-bottom":"0px","height":'32px'});
        $(this).parent().parent().parent().parent().find(".rightSide").css({"height":'32px'});
    });

    handleName = $('.paginatedDataTable').parent().parent().parent().attr('id');
    if (handleName == "" || typeof(handleName) === 'undefined') handleName = $('.paginatedDataTable').parent().parent().attr('id');
    if (handleName == "" || typeof(handleName) === 'undefined') handleName = $('.paginatedDataTable').parent().parent().parent().parent().attr('id');

    if ($("#VMFilter").length > 0) {
        handleName = "vmFilter";
    }

    if ($("#ProjectsContestsFilter").length > 0) {
        handleName = "ProjectsContests";
    }

    var startDateCol, endDateCol;
    if (handleName == 'activeContests') {
        startDateCol = 3;
        endDateCol = 4;
    }
    else if (handleName == 'MyCopilotPostings' || handleName == 'ProjectsContests') {
        startDateCol = 2;
        endDateCol = 3;
    }
    else if (handleName == 'projectsResult') {
        startDateCol = 1;
        endDateCol = 2;
    } else if (handleName == 'vmFilter') {
        var additionalColumnNumber = $("#contest_vms thead th").length - 11;
        startDateCol = 7 + additionalColumnNumber;
    }

    if ($("#allProjectsFilter").size() > 0) {
        // setup customer filter grouping for project filters and project filter values
        $.fn.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {

            if($('#groupBy').attr('disabled') == true) {
                return true;
            }
            var keyId = $('#groupBy').val();
            var result = false;
            if (keyId == 'no') return true;
            else {
                var filterValue = $("#groupValue").val().toLowerCase();
                if (filterValue == 'all') {
                    return true;
                } else {
                    if (aData[11] == null || $.trim(aData[11]).length == 0) {
                        return filterValue == 'none';
                    }
                    var data = $("<div></div>").html(aData[11]);
                    var findKey = false;
                    data.find(".metadataGroup").each(function(){
                       if($(this).find(".metadataKeyId").text() == keyId) {
                           findKey = true;
                           // check the value
                           $(this).find(".metadataValue").each(function(){
                              if($(this).text().toLowerCase() == filterValue) {
                                  result = true;
                              }
                           });
                       }
                    });

                }
            }

            if(findKey == false && filterValue == 'none') {
                return true;
            }

            return result;
        });
    }

    // The plugin function for adding a new filtering routine
    $.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
            var dateStart;
            var dateEnd;

            if ($("#startDateBegin").val() == null || $("#startDateBegin").val() == '')
                dateStart = '';
            else
                dateStart = parseDateValue($("#startDateBegin").val())
            if ($("#startDateEnd").val() == null || $("#startDateEnd").val() == '')
                dateEnd = 'zzzzzzzz';
            else
                dateEnd = parseDateValue($("#startDateEnd").val())
            // aData represents the table structure as an array of columns, so the script access the date value
            // in the fourth column of the table via aData[3]
            var evalDate = parseDateValue(aData[startDateCol]);

            if (evalDate >= dateStart && evalDate <= dateEnd) {
                return true;
            }
            else {
                return false;
            }
        });
    $.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
            var dateStart;
            var dateEnd;
            // aData represents the table structure as an array of columns, so the script access the date value
            // in the fifth column of the table via aData[4]
            if ($("#endDateBegin").val() == null || $("#endDateBegin").val() == '')
                dateStart = '';
            else
                dateStart = parseDateValue($("#endDateBegin").val())
            if ($("#endDateEnd").val() == null || $("#endDateEnd").val() == '')
                dateEnd = 'zzzzzzzz';
            else
                dateEnd = parseDateValue($("#endDateEnd").val());

            var evalDate = parseDateValue(aData[endDateCol]);

            if ($.trim(evalDate) == '') {
                return true;
            }

            if (evalDate >= dateStart && evalDate <= dateEnd) {
                return true;
            }
            else {
                return false;
            }
        });
    $.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
            var filterStr = $('input.searchBox').val();
            var len = aData.length;
            for (var i = 0; i < len - 1; i++) {
                if (aData[i].replace(/<[^>]*>/g, "").toLowerCase().indexOf(filterStr.toLowerCase()) != -1)
                    return true;
            }
            return false;
        });

    setupFilterPanel();

    if ($(".enterpriseCalendar .milestoneCalView").length > 0) {
        $("#customerFilter").trigger('change');
    }

    //reset the filter panel for consistency
    if (document.getElementById("filterPanelForm") != null) {
        document.getElementById("filterPanelForm").reset();
    }


})
