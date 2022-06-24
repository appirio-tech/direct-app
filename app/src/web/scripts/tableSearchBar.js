/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for data table search bar.
 *
 * @version 1.0
 * @since Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar
 */
$(document).ready(function() {

    var left = $('.searchBar').parent().width() - 333;
    $('.searchBar').css({'margin-left':left + 'px'});
    $(window).resize(function() {
        left = $('.searchBar').parent().width() - 333;
        $('.searchBar').css({'margin-left':left + 'px'});
    });

    // search bar for pipeline report
    $('#pipelineSearchBar input.searchBox').keyup(function(event) {
        $.pipelineDetailsTable.fnFilter($(this).val());
    });

    // search bar for cost report
    $('#costReportSearchBar input.searchBox').keyup(function(event) {
        $.costReportDataTable.fnFilter($(this).val());
    });

    // search bar for billing cost report
    $('#billingCostReportSearchBar input.searchBox').keyup(function(event) {
        $.billingCostReportDataTable.fnFilter($(this).val());
    });


})