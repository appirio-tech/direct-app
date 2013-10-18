/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
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
 * Version 1.3 (Module Assembly - TC Cockpit Enterprise Dashboard New Active Contests)
 * - add filter for enterprise dashboard active contests
 *
 * Version 1.4 (Module Assembly - TC Cockpit Operations Dashboard For PMs) changes:
 * - add filter for operations dashboard, filter projects in "Active" status by default.
 *
 * Version 1.5 (Release Assembly - TC Cockpit Operations Dashboard Bug Fix and Improvements 1) changes:
 * - update filter of customer, project manager, and project filter value for operations dashboard.
 *
 * Version 1.6 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * - Update the contest status filter to correctly filter unique contest status.
 *
 * Version 1.7 (Release Assembly - TC Cockpit Operations Dashboard Improvements 2) changes:
 * - add risk filter handler and parseNumberFromString.
 *
 * Version 1.8 (Release Assembly - TC Cockpit Operations Dashboard Improvements 3) changes:
 * - add duplication test before add value from contests dropdown.
 *
 * Version 1.9 (Release Assembly - TC Cockpit Operations Dashboard Improvements 4) changes:
 * - added support for Copilot filter
 *
 * Version 2.0 (TopCoder Direct Contest VM Instances Management) changes:
 * - add filter handler for contest vm instances page.
 *
 * Version 2.1 (Release Assembly - TopCoder Direct VM Instances Management) changes:
 * - add filter handler for project vm management page.
 *
 * @author GreatKevin, tangzx, Fanazhe, gentva, jiajizhou86
 * @version 2.1
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
    var searchPattern = id;
    if($.trim(id).length > 0) {
        searchPattern = "<span>"+ id + "</span>";
    }
    if (handleName == "projectsResult") {
        $('#customerFilter').val(id);
        tableHandle.fnFilter(searchPattern, 10);
    }
    else if (handleName == "pmProjectsResult") {
        if (filterStr.indexOf('No Customer') < 0) {
            searchPattern = filterStr;
        } else {
            searchPattern = 'none';
        }
        if (filterStr.indexOf('All Customers') != -1) {
            searchPattern = '';
        }
		if(searchPattern!=''){
			tableHandle.fnFilter("^"+searchPattern+"$", 12, true, false);
		} else {
			tableHandle.fnFilter(searchPattern, 12);
		} 
    }
    else if (handleName == "activeContests") {
        tableHandle.fnFilter(searchPattern, 10);
    }
    else if (handleName == "MyCopilotPostings") {
        tableHandle.fnFilter(searchPattern, 9);
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
        if ($(".dashboardPage #activeContest").length > 0) {
            tableHandle = $.dashboardActiveContestsDataTable;
        } else {
            tableHandle = $.activeContestsDataTable;
        }
    }
    else if (handleName == "projectsResult") { //all projects table
        tableHandle = $.allProjectTable;
    }
    else if (handleName == "pmProjectsResult") { //Platform Managers' projects table
        tableHandle = $.pmProjectTable;
    }
    else if (handleName == "MyCopilotPostings") {//my copilot postings
        tableHandle = $.myCopilotPostings;
    }
    else if (handleName == "ProjectsContests") { //project contests
        tableHandle = $.ProjectsContests;
    } else if (handleName == 'vmFilter') {
        tableHandle = vmTable;
    } else if (handleName == 'contestVMFilter') {
        tableHandle = contestVMTable;
    } else if (handleName == 'projectVMFilter') {
        tableHandle = projectVMTable;
    }
    //common part
    var customerFilter, len;

    //new enterprise dashboard active contest page
    $('.dashboardPage #activeContest #customerNameFilter').change(function(){
        var str = $(this).val();
        if (str.indexOf('All') != -1) str = '';

        tableHandle.fnFilter(str, 10);
    });
    $('.customerSelectMask .contestsDropDown li a').each(function () {
        var value = $(this).html();

        if (handleName != "projectsResult" && handleName != "pmProjectsResult") {
            if (value != 'All Customers' && $('#customerFilter option[value=\'' + value + '\']').length == 0)
                $('#customerFilter').append("<option value='" + value + "'>" + value + "</option>");
        }

    })
    //all projects or Platform Managers's projects
    if (handleName == "projectsResult" || handleName == "pmProjectsResult") {
        var projectStatusFilter = tableHandle.fnGetColumnData(8);
        
        if (handleName == "pmProjectsResult") {
            projectStatusFilter = tableHandle.fnGetColumnData(7);
        }
        
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
    if (handleName == "activeContests" || handleName == "activeContest") {
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
        var statusMap = {};
        for (var i = 0; i < len; i++) {
            var statusArray = contestStatusFilter[i].split("</span>");
            for(var k = 0; k < statusArray.length; ++k) {
                if($.trim(statusArray[k]).length == 0) continue;
                statusArray[k] = statusArray[k].replace(/<font.*?\/font>/g, "");
                var index1 = statusArray[k].indexOf('>');
                var value = statusArray[k].substring(index1 + 1);
                statusMap[value] = true;
            }


        }

        $.each(statusMap, function (key, value) {
            $('#contestStatusFilter').append("<option value='" + key + "'>" + key + "</option>");
        });
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
    if (handleName == 'contestVMFilter') {
        var imageNameFilter = tableHandle.fnGetColumnData(0);
        $('#imageNameFilter option:gt(0)').remove();
        len = imageNameFilter.length;
        for (var i = 0; i < len; i++) {
            var name = imageNameFilter[i];
            $('#imageNameFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var memberHandleFilter = tableHandle.fnGetColumnData(3);
        len = memberHandleFilter.length;
        $('#memberHandleFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = memberHandleFilter[i];
            $('#memberHandleFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var vmStatusFilter = tableHandle.fnGetColumnData(7);
        len = vmStatusFilter.length;
        $('#vmStatusFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = vmStatusFilter[i];
            $('#vmStatusFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var createdByFilter = tableHandle.fnGetColumnData(8);
        len = createdByFilter.length;
        $('#createdByFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = createdByFilter[i];
            $('#createdByFilter').append("<option value='" + name + "'>" + name + "</option>");
        }


        var usageFilter = tableHandle.fnGetColumnData(6);
        len = usageFilter.length;
        $('#vmUsageFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = $.trim(usageFilter[i]);
            if (name.length > 0)
                $('#vmUsageFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        $('#imageNameFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';

            tableHandle.fnFilter(str, 0);
        });

        $('#memberHandleFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 3);
        });

        $('#vmUsageFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 6);
        });

        $('#vmStatusFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 7);
        });

        $('#createdByFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 8);
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

        if ($("#vmStatusFilter").length > 0) {
            var all_option = $("#vmStatusFilter option:eq(0)")
            var my_options = $("#vmStatusFilter option:gt(0)");

            my_options.sort(function (a, b) {
                a = a.text.toUpperCase();
                b = b.text.toUpperCase();
                if (a > b) return 1;
                else if (a < b) return -1;
                else return 0
            });

            $("#vmStatusFilter").empty().append(all_option).append(my_options);
            $("#vmStatusFilter option:eq(0)").attr('selected', 'selected');
        }

        // by default filter by RUNNING.
        if ($('#vmStatusFilter option[value="RUNNING"]').length > 0) {
            $('#vmStatusFilter option[value="RUNNING"]').attr('selected', 'selected');
            tableHandle.fnFilter("RUNNING", 7);
        }
    }

    if (handleName == 'projectVMFilter') {
        var oSettings = tableHandle.fnSettings();
        oSettings.aoPreSearchCols[ 10 ].sSearch = '';
        tableHandle.fnDraw();

        var imageNameFilter = tableHandle.fnGetColumnData(2);
        $('#imageNameFilter option:gt(0)').remove();
        len = imageNameFilter.length;
        for (var i = 0; i < len; i++) {
            var name = imageNameFilter[i];
            $('#imageNameFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var memberHandleFilter = tableHandle.fnGetColumnData(5);
        len = memberHandleFilter.length;
        $('#memberHandleFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = memberHandleFilter[i];
            $('#memberHandleFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var vmStatusFilter = tableHandle.fnGetColumnData(10);
        len = vmStatusFilter.length;
        $('#vmStatusFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = vmStatusFilter[i];
            $('#vmStatusFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var createdByFilter = tableHandle.fnGetColumnData(6);
        len = createdByFilter.length;
        $('#createdByFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = createdByFilter[i];
            $('#createdByFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        var usageFilter = tableHandle.fnGetColumnData(9);
        len = usageFilter.length;
        $('#vmUsageFilter option:gt(0)').remove();
        for (var i = 0; i < len; i++) {
            var name = $.trim(usageFilter[i]);
            if (name.length > 0)
                $('#vmUsageFilter').append("<option value='" + name + "'>" + name + "</option>");
        }

        $('#imageNameFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';

            tableHandle.fnFilter(str, 2);
            setupVMActionDropDownList();
        });

        $('#memberHandleFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 5);
            setupVMActionDropDownList();
        });

        $('#vmUsageFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 9);
            setupVMActionDropDownList();
        });

        $('#vmStatusFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 10);
            setupVMActionDropDownList();
        });

        $('#createdByFilter').change(function () {
            var str = $(this).val();
            if (str.indexOf('All') != -1) str = '';
            tableHandle.fnFilter(str, 6);
            setupVMActionDropDownList();
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
            });

            $("#memberHandleFilter").empty().append(all_option).append(my_options);
            $("#memberHandleFilter option:eq(0)").attr('selected', 'selected');
        }

        if ($("#vmStatusFilter").length > 0) {
            var all_option = $("#vmStatusFilter option:eq(0)")
            var my_options = $("#vmStatusFilter option:gt(0)");

            my_options.sort(function (a, b) {
                a = a.text.toUpperCase();
                b = b.text.toUpperCase();
                if (a > b) return 1;
                else if (a < b) return -1;
                else return 0
            });

            $("#vmStatusFilter").empty().append(all_option).append(my_options);
            $("#vmStatusFilter option:eq(0)").attr('selected', 'selected');
        }

        // by default filter by RUNNING.
        if ($('#vmStatusFilter option[value="RUNNING"]').length > 0) {
            $('#vmStatusFilter option[value="RUNNING"]').attr('selected', 'selected');
            tableHandle.fnFilter("RUNNING", 10);
        }
    }

    var updateBreadcrumb = function(customer) {
        var breadcrumb = $("#area1 .currentPage");
        var lastText = breadcrumb.find("strong");
        breadcrumb.find(".customer").remove();
        if (customer == "") {
            lastText.text("All Active Contests");
        } else {
            lastText.text("Active Contests");
            lastText.before($("<a/>", {
                "text":customer,
                "class":"customer",
                "href":"#"
            }));
            lastText.before($("<span/>", {
                "text":" > ",
                "class":"customer"
            }));
        }
    }

    $('#customerFilter').change(function () {
        var str = '';
        var customerId= -1;

        if ($("#allProjectsFilter").size() > 0) {
            customerId = $(this).val();
            str = $(this).find("option:selected").text();
        } else {
            str = $(this).val();
            $(".customerSelectMask  ul li a").each(function (index, element) {
                if ($(this).html() == str) {
                    customerId = $(this).parent().data("id");
                }
            });
        }

        // handle the change of project filters and project filter values select, only apply for
        // all projects page and project search page
        if ($("#allProjectsFilter").size() > 0) {
            $("#groupBy").val('no');
            $("#groupValue").val('all');
            filterbyCustomer(customerId, str);
            if (str != 'All Customers' && str != 'No Customer') {
                $("#groupBy").attr('disabled', false);
                $("#groupValue").attr('disabled', false);

                // reset the cache maps
                currentProjectFilters = {};
                currentProjectFilterValuesMap = {};

                // remove existing options
                $("#groupBy option:gt(0)").remove();
                $("#groupValue option:gt(0)").remove();

                // load project filters options
                $("#projectsResult table.projectStats tr, #pmProjectsResult table.projectStats tr").each(function(){
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
                $("#groupBy").attr('disabled', true);
                $("#groupValue").attr('disabled', true);
            }
            $("#groupBy").trigger("change");
        } else if ($("#activeContestsFilter").size() > 0 || $("#CopilotPosingFilter").size() > 0) {     // active contests page or copilot posting page
			filterbyCustomer(customerId, str);
            updateBreadcrumb(str);
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
        }).val('no').trigger('change');

        // setup the change event for "Project Filter Values" select
        $("#groupValue").change(function () {
            tableHandle.fnFilter('');
        }).val('all').trigger('change');

        $("#projectStatusFilter").val('All');
    }


    $('#projectStatusFilter').change(function () {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';
        
        if (handleName == "pmProjectsResult") {
            tableHandle.fnFilter(str, 7);
        } else {
            tableHandle.fnFilter(str, 8);
        }
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
        tableHandle.fnDraw();
        tableHandle.fnFilter("");
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

    // pop data for project managers
    if (handleName == "pmProjectsResult") {
        var values = {};
    
        var managerFilter = tableHandle.fnGetColumnData(15);
        
        var len = managerFilter.length;
        var managerMap = {};
        for (var i = 0; i < len; i++) {
            var index1 = managerFilter[i].indexOf('>');
            var index2 = managerFilter[i].indexOf('<', 1);
            var value = managerFilter[i].substring(index1 + 1, index2);

            managerMap[value] = true;
        }

        $.each(managerMap, function (key, value) {
            //$('#projectManagerFilter').append("<option value=" + key + ">" + key + "</option>");
        });
    
        $("#copilotFilter").change(function () {
            var searchPattern = $(this).val();
            if (searchPattern == '-1') {
                searchPattern = ',none,';
            } else if (searchPattern != '') {
                searchPattern = "," + searchPattern + ",";
            }

            tableHandle.fnFilter(searchPattern, 19);
        }).val('').trigger('change');
        
        $("#projectManagerFilter").val('').trigger('change');

    }        
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

    if ($("#ContestVMFilter").length > 0) {
        handleName = "contestVMFilter";
    }

    if ($("#ProjectVMFilter").length > 0) {
        handleName = "projectVMFilter";
    }

    if ($("#ProjectsContestsFilter").length > 0) {
        handleName = "ProjectsContests";
    }

    if ($(".dashboardPage #activeContest").length > 0) {
        handleName = "activeContests";
    }

    var startDateCol, endDateCol, statusCol;
    if (handleName == 'activeContests') {
        startDateCol = 3;
        endDateCol = 4;
        statusCol = 8;
    }
    else if (handleName == 'MyCopilotPostings' || handleName == 'ProjectsContests') {
        startDateCol = 2;
        endDateCol = 3;
        statusCol = 7;
    }
    else if (handleName == 'projectsResult' || handleName == 'pmProjectsResult') {
        startDateCol = 1;
        endDateCol = 2;
    } else if (handleName == 'vmFilter') {
        var additionalColumnNumber = $("#contest_vms thead th").length - 11;
        startDateCol = 7 + additionalColumnNumber;
    } else if (handleName == 'contestVMFilter') {
        startDateCol = 5;
    } else if (handleName == 'projectVMFilter') {
        startDateCol = 8;
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
                    var dd = (handleName == "pmProjectsResult") ? aData[13] : aData[11];
                    if (dd == null || $.trim(dd).length == 0) {
                        return filterValue == 'none';
                    }
                    var data = $("<div></div>").html(dd);
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

                    if(findKey == false && filterValue == 'none') {
                        return true;
                    }
                }
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
    $.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
            var value = $("#contestStatusFilter").val();
            if (!value || !statusCol) {
                return true;
            }
            if(value == 'All') {
                return true;
            }

            var statusMap = {};
            var statusArray = aData[statusCol].split("</span>");
            for (var k = 0; k < statusArray.length; ++k) {
                if ($.trim(statusArray[k]).length == 0) continue;
                statusArray[k] = statusArray[k].replace(/<font.*?\/font>/g, "");
                var index1 = statusArray[k].indexOf('>');
                var availableStatus = statusArray[k].substring(index1 + 1);
                statusMap[availableStatus.toLowerCase()] = true;
            }

            if(statusMap[value.toLowerCase()]) {
                return true;
            } else {
                return false;
            }
        });

    setupFilterPanel();

    if ($(".enterpriseCalendar .milestoneCalView").length > 0) {
        $("#customerFilter").trigger('change');
    }

    //reset the filter panel for consistency
    if (document.getElementById("filterPanelForm") != null) {
        document.getElementById("filterPanelForm").reset();
    }

    if(handleName == "pmProjectsResult" && $("#projectStatusFilter option:contains('Active')").length > 0){
        $("#projectStatusFilter").val("Active");
        $("#projectStatusFilter").trigger('change'); 
    }
    
    // set risk filter
    if (handleName == "pmProjectsResult") {
        var defaultMinVal = -1;
        var defaultMaxVal = 9999999;
        
        var riskFilterLength = 4;
        var riskDataIndex = 17;
        var riskArray = [];
        for (var i = 0; i < riskFilterLength; i++) {
            riskArray.push([defaultMinVal, defaultMaxVal]);
        }
        
        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var riskData = aData[riskDataIndex].toLowerCase().split("</span>");
                
                for (var i = 0; i < riskFilterLength; i++) {
                    // the data here will always be valid number
                    var str = $.trim(riskData[i].substring(riskData[i].indexOf('>') + 1));
                    
                    var numbers = str.split(";");
                    var hasValid = false;
                    
                    for (var j = 0; j < numbers.length; j++) {
                        var data = parseFloat($.trim(numbers[j]));
                        
                        // data == -1 means no data
                        if (data == -1) {
                            if ($.trim($($('.riskFieldDiv .riskField input[type=text]')[i]).val()).length > 0 
                                && $($('.riskFieldDiv .riskField .riskError')[i]).hasClass('hide')) {
                                return false;
                                
                            } else {
	                            // match this row anyway since the input in the current filter is invalid
	                            hasValid = true;
	                            break;
                            }
                            
                        } else if (data > riskArray[i][0] && data < riskArray[i][1]) {
                            hasValid = true;
                            break;
                        }
                    }
                    
                    if (!hasValid) {
                        return false;
                    }
                }
                return true;
            }
        );
        
        $('#applyRiskFilters').click(function() {
            $('.riskFieldDiv .riskField').each(function(index, item) {
                var isMax = $(item).find('select').val() == 'lt';
                var str = $(item).find("input[type=text]").val();
                
                if ($.trim(str).length == 0) {
                    $(item).find('.riskError').addClass('hide');
                    
                    riskArray[index][0] = defaultMinVal;
                    riskArray[index][1] = defaultMaxVal;
                    
                    return;
                }
                
                var num = parseNumberFromString(str);
                if (isNaN(num) && str) {
                    $(item).find('.riskError').removeClass('hide');
                    
                    riskArray[index][0] = defaultMinVal;
                    riskArray[index][1] = defaultMaxVal;
                } else {
                    $(item).find('.riskError').addClass('hide');
                    
                    riskArray[index][0] = defaultMinVal;
                    riskArray[index][1] = defaultMaxVal;
                    
                    if (isMax) {
                        riskArray[index][1] = num;
                    } else {
                        riskArray[index][0] = num;
                    }
                }
            });
            
            tableHandle.fnDraw();
        });
    }

});

/**
 * Parse string to number.
 *
 * @param str the string to parse.
 * @return the result, NaN if invalid.
 * @since 1.7
 */
function parseNumberFromString(str) {
    var ret = parseFloat(str);
    
    if (isNaN(ret)) {
        return ret;
    }
    
    var p = 0;
    var tmp = str;
    
    // remove '0' in start
    while (p < str.length && str[p] == '0') {
        p++;
    }
    if (p == str.length) {
        return 0;
    }
    tmp = str.substring(p);
    
    // remove '0' in end
    if (tmp.indexOf('.') > -1) {
        if (tmp[0] == '.') {
            tmp = '0' + tmp;
        }
        p = tmp.length - 1;
        while (p > -1 && tmp[p] == '0') {
            p--;
        }
        tmp = tmp.substring(0, p + 1);
        
        if (tmp[tmp.length - 1] == '.') {
            tmp = tmp.substring(0, tmp.length - 1);
        }
    }
    
    if ("" + ret != tmp) {
        return NaN;
    }
    return ret;
}

/**
 * Filters the list of projects in Operations Dashboard on multiple selected platform specialists
 */
function selectPlatformSpecsCallback() {
    var selectedValues = [];
    $('.multiSelectOptions').find('INPUT:checkbox:checked').not('.optGroup, .selectAll').each(function () {
        selectedValues.push($(this).attr('value'));
    });

    var searchPattern;
    if (selectedValues.length == 0) {
        searchPattern = '';
        tableHandle.fnFilter(searchPattern, 15);
    } else {
        searchPattern = '';
        for (var i = 0; i < selectedValues.length; i++) {
            var value = selectedValues[i];
            if (value == '-1') {
                value = 'none';
            }
            if (i > 0) {
                searchPattern += '|';
            }
            searchPattern += value;
        }
        tableHandle.fnFilter(searchPattern, 15, true, false);
    }
}