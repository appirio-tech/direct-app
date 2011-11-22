/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for data table filter panel.
 *
 * @version 1.0
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

$(function() {

    //reset the filter panel for consistency
    if (document.getElementById("filterPanelForm") != null) {
        document.getElementById("filterPanelForm").reset();
    }

    var left = $('#activeContestsFilterSearch').parent().width() - 333;
    $('#activeContestsFilterSearch').css({'margin-left':left + 'px'});
    if ($('.filterContent .column3').prev().hasClass('column2')) {
        $('.filterContent .column3').css({'width':'300px'})
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

    $('.collapse').trigger('click');

    handleName = $('.paginatedDataTable').parent().parent().parent().attr('id');
    if (handleName == "" || typeof(handleName) === 'undefined') handleName = $('.paginatedDataTable').parent().parent().attr('id');
    if (handleName == "" || typeof(handleName) === 'undefined') handleName = $('.paginatedDataTable').parent().parent().parent().parent().attr('id');

    if($("#ProjectsContestsFilter").length > 0) {
        handleName = "ProjectsContests";
    }

    var startDateCol,endDateCol;
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
    }
    // The plugin function for adding a new filtering routine
    $.fn.dataTableExt.afnFiltering.push(
        function(oSettings, aData, iDataIndex) {
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
        function(oSettings, aData, iDataIndex) {
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
        function(oSettings, aData, iDataIndex) {
            var filterStr = $('input.searchBox').val();
            var len = aData.length;
            for (var i = 0; i < len - 1; i++) {
                if (aData[i].replace(/<[^>]*>/g, "").indexOf(filterStr) != -1)
                    return true;
            }
            return false;
        });
    // Function for converting a mm/dd/yyyy date value into a numeric string for comparison (example 08/12/2010 becomes 20100812
    function parseDateValue(rawDate) {
        if ($.trim(rawDate) == '') {
            return '';
        }
        rawDate = $.trim(rawDate);
        var dateArray = rawDate.split(" ")[0];
        dateArray = dateArray.split("/");
        var parsedDate = dateArray[2] + dateArray[0] + dateArray[1];
        return parsedDate;
    }

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
    }
    //common part
    var customerFilter,len;

    $('.customerSelectMask .contestsDropDown li a').each(function() {
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

        $.each(statusMap, function(key, value) {
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
    $('#customerFilter').change(function() {
        var str = $(this).val();
        $(".customerSelectMask").find(".inputSelect input").val(str);
        $(".customerSelectMask  ul li a").each(function(index, element) {
            if ($(this).html() == str) {
                var id = $(this).parent().data("id");
                var id = $(this).parent().trigger('click');
            }
        });
    });
    $('#projectStatusFilter').change(function() {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';
        tableHandle.fnFilter(str, 8);
    });
    $('#contestTypeFilter').change(function() {
        var str = $(this).val();
        if (str.indexOf('All') != -1)str = '';
        tableHandle.fnFilter(str, 0);
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
    $("#startDateBegin").keyup(function() {
        tableHandle.fnDraw();
    });

    $("#startDateBegin").change(function() {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#startDateEnd").keyup(function() {
        tableHandle.fnDraw();
    });

    $("#startDateEnd").change(function() {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#endDateBegin").keyup(function() {
        tableHandle.fnDraw();
    });

    $("#endDateBegin").change(function() {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

    $("#endDateEnd").keyup(function() {
        tableHandle.fnDraw();
    });

    $("#endDateEnd").change(function() {
        tableHandle.fnDraw();
        tableHandle.fnFilter("")
    });

})