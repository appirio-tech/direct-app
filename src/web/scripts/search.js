/**
 * The JS script for search.
 *
 *  Version 1.1 - Direct Pipeline Integration Assembly
 *  - Added onchange event listener to #scheduledContestsViewType drop-down
 *  - Added onclick event listeners to expand/collapse buttons on Pipeline Report screen
 *
 *  Version 1.2 - Direct Pipeline Stats Update Assembly
 *  - Moved onchange event listener to #scheduledContestsViewType drop-down to dashboard-pipeline.js
 *  - Moved onclick event listeners to expand/collapse buttons on Pipeline Report screen to dashboard-pipeline.js
 *
 *  Version 1.3 - (TopCoder Cockpit - Cost Report Assembly)
 *  - Add DataTable pagination setting for aggregation cost report
 *  - Add two custom DataTable comparator for money and simple data.
 *  - Add collapse/expand event for aggregation cost report and cost details report.
 *
 *  Version 1.4 - (TC Direct - Page Layout Update Assembly)
 *  - Add more rows for data table in copilot contests page & project contests page.
 *   
 *  Version 1.5 - (TC Direct - Page Layout Update Assembly 2) changes:
 *  - Update the codes to initialize the pagination table to fix layout issues
 *   
 *  Version 1.6 - (TC Direct Page Layout Update 24Hr Assembly)) changes:
 *  - Update the codes to initialize the pagination table to fix layout issues for dashboard active page.
 *
 *  Version 1.6.1 - (Release Assembly - TC Direct UI Improvement Assembly 3) changes:
 *  - Remove the duplicate submit pipeline form method.
 * 
 *  Version 1.7 - (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination) changes:
 *  - Update the codes to initialize the pagination table of active contests table
 *  - Add the filter feature to the active contests table
 *
 *  Version 1.7.1 - (Release Assembly - TopCoder Cockpit Project Status Management) changes:
 *  - Update the codes to initialize the project result table, two columns added
 *
 *  Version 1.7.2 - (Module Assembly - Project Contest Fee Management) changes:
 *  - Update the codes to add project fees table.
 *  - New column added to project fees table.
 * 
 *  Version 1.7.3 - (TC Accounting Tracking Invoiced Payments) changes:
 *  - Add logic to update the invoice records in billing cost report page.
 * 
 *  Version 1.8 - (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) changes:
 *  - Update the data tables initialization codes to work with final panel and search bar.
 *
 * Version 1.8.1 - (TC Accounting Tracking Invoiced Payments Part 2:
 *  - Add logic to update the disabled state of invoice process button in billing cost report page.
 *
 * Version 1.8.2 - (Release Assembly - TC Cockpit All Projects Management Page Update):
 * - Add a hidden column for $.allProjectTable
 *
 * Version 1.8.3 - (TopCoder Cockpit - Bug Race Project Contests View)
 * - Update to fix the sorting of the forum/registrants/submission in project contests list view
 *
 * Version 1.8.4 - (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 * - Add support for platform fee table.
 *  
 * @author BeBetter, isv, Blues, tangzx, GreatKevin, minhu
 * @version 1.8.4
 */
var cookieOptions = { path: '/', expires: 1 };
var COOKIE_NAME = "pagination";

$(document).ready(function() {
    var sStdMenu =
            '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="5">5</option>' +
            '<option value="10">10</option>' +
            '<option value="25">25</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    var sLongStdMenu =
            '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="10">10</option>' +
            '<option value="25">25</option>' +
            '<option value="50">50</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    function trim(str) {
        str = str.replace(/^\s+/, '');
        for (var i = str.length - 1; i >= 0; i--) {
            if (/\S/.test(str.charAt(i))) {
                str = str.substring(0, i + 1);
                break;
            }
        }
        return str;
    }

    jQuery.fn.dataTableExt.oSort['direct-projectNumber-asc'] = function(a, b) {
        var num1 = a.split('/');
        var num2 = b.split('/');
        var x = num1[0] * 1;
        var y = num2[0] * 1;
        var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
        return z;
    };

    jQuery.fn.dataTableExt.oSort['direct-projectNumber-desc'] = function(a, b) {
        var num1 = a.split('/');
        var num2 = b.split('/');
        var x = num1[0] * 1;
        var y = num2[0] * 1;
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };
    jQuery.fn.dataTableExt.oSort['date-direct-asc'] = function(a, b) {
        if (trim(a) != '') {
            var frDatea = trim(a).split(' ');
            var frTimea = frDatea[1].split(':');
            var frDatea2 = frDatea[0].split('/');
            var x = (frDatea2[2] + frDatea2[0] + frDatea2[1] + frTimea[0] + frTimea[1]) * 1;
        } else {
            var x = 10000000000000; // = l'an 1000 ...
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split(' ');
			var frTimeb = frDateb[1].split(':');
			var frDateb2 = frDateb[0].split('/');
			var y = (frDateb2[2] + frDateb2[0] + frDateb2[1] + frTimeb[0] + frTimeb[1]) * 1;		                
		} else {
			var y = 10000000000000;		                
		}
		var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
		return z;
	};

	jQuery.fn.dataTableExt.oSort['date-direct-desc'] = function(a, b) {
		if (trim(a) != '') {
			var frDatea = trim(a).split(' ');
			var frTimea = frDatea[1].split(':');
			var frDatea2 = frDatea[0].split('/');
			var x = (frDatea2[2] + frDatea2[0] + frDatea2[1] + frTimea[0] + frTimea[1]) * 1;		                
		} else {
			var x = 10000000000000;		                
		}

        if (trim(b) != '') {
            var frDateb = trim(b).split(' ');
            var frTimeb = frDateb[1].split(':');
            var frDateb2 = frDateb[0].split('/');
            var y = (frDateb2[2] + frDateb2[0] + frDateb2[1] + frTimeb[0] + frTimeb[1]) * 1;
        } else {
            var y = 10000000000000;
        }
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };

    /** sort comparator for simple date "yyyy-MM-DD" **/
    jQuery.fn.dataTableExt.oSort['simple-date-asc'] = function(a, b) {
        if (trim(a) != '') {
            var frDatea = trim(a).split('-');
            var x = (parseFloat(frDatea[2]) + parseFloat(frDatea[1]) * 30 + parseFloat(frDatea[0]) * 365) * 1;
        } else {
            var x = 10000000000000;
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split('-');
            var y = (parseFloat(frDateb[2]) + parseFloat(frDateb[1]) * 30 + parseFloat(frDateb[0]) * 365) * 1;
		} else {
			var y = 10000000000000;
		}
		var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
		return z;
	};

	jQuery.fn.dataTableExt.oSort['simple-date-desc'] = function(a, b) {
		if (trim(a) != '') {
            var frDatea = trim(a).split('-');
            var x = (parseFloat(frDatea[2]) + parseFloat(frDatea[1]) * 30 + parseFloat(frDatea[0]) * 365) * 1;
        } else {
            var x = 10000000000000;
        }

		if (trim(b) != '') {
			var frDateb = trim(b).split('-');
            var y = (parseFloat(frDateb[2]) + parseFloat(frDateb[1]) * 30 + parseFloat(frDateb[0]) * 365) * 1;
		} else {
			var y = 10000000000000;
		}
        var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
        return z;
    };

    /***TCCC-2516*/
    jQuery.fn.dataTableExt.oSort['html-trimmed-asc'] = function (a, b) {
        var x = trim(a.replace(/<.*?>/g, "").toLowerCase());
        var y = trim(b.replace(/<.*?>/g, "").toLowerCase());
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['html-trimmed-desc'] = function (a, b) {
        var x = trim(a.replace(/<.*?>/g, "").toLowerCase());
        var y = trim(b.replace(/<.*?>/g, "").toLowerCase());
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['money-asc'] = function (a, b) {
        var x = parseFloat(trim(a.replace(/[^\d]/g, "")));
        var y = parseFloat(trim(b.replace(/[^\d]/g, "")));
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['money-desc'] = function (a, b) {
        var x = parseFloat(trim(a.replace(/[^\d]/g, "")));
        var y = parseFloat(trim(b.replace(/[^\d]/g, "")));
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };


    jQuery.fn.dataTableExt.oSort['reliability-asc'] = function (a, b) {
        var x = getReliability(trim(a));
        var y = getReliability(trim(b));
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['reliability-desc'] = function (a, b) {
        var x = getReliability(trim(a));
        var y = getReliability(trim(b));
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };


    jQuery.fn.dataTableExt.oSort['project-status-asc'] = function (a, b) {
       var x = getProjectStatus(a);
       var y = getProjectStatus(b);

       //alert("status A:" + x + " status B:" + y);

       return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['project-status-desc'] = function (a, b) {
       var x = getProjectStatus(a);
       var y = getProjectStatus(b);
       return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['link-number-asc'] = function (a, b) {
        var x = getNumberOfTheLink(a);
        var y = getNumberOfTheLink(b);

        //alert("status A:" + x + " status B:" + y);

        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['link-number-desc'] = function (a, b) {
        var x = getNumberOfTheLink(a);
        var y = getNumberOfTheLink(b);
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    function getNumberOfTheLink(text) {
        if(text.indexOf('href') == -1) {
            return -1;
        }

        return parseInt($.trim($(text).text()));
    }

    function getProjectStatus(text) {
        text = text.toLowerCase();

        if (text == null || text.length == 0) {
            return "";
        }

        var end = text.indexOf("</span>");
        var s1 = text.substring(0, end);
        var start = s1.indexOf(">");
        var status = $.trim(s1.substring(start + 1)).toLowerCase();

        if (status.length == 0) {
            return "";
        } else {
            return status;
        }
    }

    function getReliability(text) {
        if (text == null || text.length == 0) {
            return 0;
        }
        text = text.replace(/,/g, '.');
        var f = parseFloat(text.substring(0, text.length - 1));
        if (isNaN(f)) {
            return 0;
        } else {
            return f;
        }
    }

    $.allProjectTable = $("#projectsResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + " per page"
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [
            [0,'asc']
        ],
        "aoColumns": [
            { "sType": "html-trimmed" },
            { "sType": "date-direct" },
            { "sType": "date-direct" },
            { "sType": "direct-projectNumber" },
            { "sType": "direct-projectNumber" },
            { "sType": "direct-projectNumber" },
            { "sType": "direct-projectNumber" },
            { "sType": "direct-projectNumber" },
            { "sType": "project-status" },
            null,
            null,
            null
        ]

    });

    $.activeContestsDataTable = $("#activeContests .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + " per page",
            "sInfoFiltered":""
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [
            [3,'desc']
        ],
        "aoColumns": [
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "date-direct" },
            { "sType": "date-direct" },
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "html" },
            null,
            null
        ]

    });

    $("#membersResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
			]

    });
    $("#contestsResult .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"fp><"bottom1"l',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "date-direct" },
                { "sType": "date-direct" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				null
			]

    });
    $.ProjectsContests = $("#ProjectContests .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "date-direct" },
                { "sType": "date-direct" },
				{ "sType": "link-number" },
				{ "sType": "link-number" },
				{ "sType": "link-number" },
				{ "sType": "html" },
				null
			]

    });
    $("#ProjectRegistrants .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"fp><"bottom1"l',
        "aaSorting": [
            [0,'asc']
        ],
        "aoColumns": [
            { "sType": "html" },
            { "sType": "html" },
            { "sType": "reliability" },
            { "sType": "date-direct" },
            { "sType": "date-direct" }
        ]

    });



    // sort by start date
    //$.activeContestsDataTable.fnSort( [[3,'desc']] );

    $.billingAccountsDataTable = $("#billingAccounts .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page",
                "sInfoFiltered":""
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
           
			]

    });

    $.platformFeesDataTable = $("#platformFees .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page",
                "sInfoFiltered":""
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[3,'desc'], [1,'desc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "money" },
				{ "sType": "date-direct" },
				{ "bSortable": false }
			]

    });    
    
    $("#notificationsContent .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bStateSave": true,
        "bFilter": false,
        "bSort": false,
		"bAutoWidth": false,
			  "oLanguage": {
 			  	"sLengthMenu": sStdMenu + " per page"
 			  },            
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
		"aaSorting": [[0,'asc']],
		"aoColumns": [
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" }
			] 
	});

    var pipelineDataTable = $("#pipelineReportArea .paginatedDataTable").dataTable({
        "iDisplayLength": 5,
        "bStateSave": true,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "simple-date" },
                { "sType": "simple-date" }
            ]

    });

    $.pipelineDetailsTable = pipelineDataTable;

    $.myCopilotPostings = $("#MyCopilotPostings .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },                
                null,
                null
            ]

    });

    $("#CopilotPostingRegistrants .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                null,
                { "sType": "date-direct" },
                { "sType": "date-direct" }
            ]

    });

    $("#CopilotPostingSubmissions .paginatedDataTable").dataTable({
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom1"l><"bottom2"fp',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
                null,
                { "sType": "date-direct" },
                null,
                null
            ]

    });

    $.costReportDataTable = $("#costReportSection .paginatedDataTable").dataTable({
        "iDisplayLength": 25,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
				{ "sType": "html" },
                { "sType": "html" },
				{ "sType": "html" },
                { "sType": "html" },
                { "sType": "simple-date" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" }
            ]

    });
    
    $.projectReportDataTable = $("#projectMetricsReportsSection .paginatedDataTable").dataTable({
        "iDisplayLength": 25,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[0,'asc']],
        "aoColumns": [
                { "sType": "html-trimmed" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "direct-projectNumber" },
                { "sType": "direct-projectNumber" },
                { "sType": "direct-projectNumber" },
                { "sType": "direct-projectNumber" },
                { "sType": "direct-projectNumber" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "money" },
                { "sType": "date-direct" },
                { "sType": "date-direct" },
                { "sType": "html" },
                { "sType": "html" }
            ]

    });
    
    $.fn.dataTableExt.afnSortData['dom-checkbox'] = function(oSettings, iColumn) {
        var aData = [];
        $('td:eq('+iColumn+') input', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
            aData.push( this.checked==true ? "1" : "0" );
        });
        return aData;
    };
    var ths = $("#billingCostReportSection .paginatedDataTable thead th").length - 1;
    var aoColumns = [
                { "sType": "simple-date" },             
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "html" },
                { "sType": "simple-date" },
                { "sType": "simple-date" }
            ];
            
    if (ths >= 18) {
        aoColumns.push({ "sType": "money" });
    }
    
    aoColumns.push({ "sType": "html" });
    aoColumns.push({ "sType": "money" });
    aoColumns.push({ "sSortDataType": "html" });
    aoColumns.push({ "sSortDataType": "simple-date" });
    
    if (ths >= 18) {
        aoColumns.push({ "sSortDataType": "money" });
        aoColumns.push({ "sSortDataType": "html" });
        aoColumns.push({ "bSortable": false });
    }
    $.billingCostReportDataTable = $("#billingCostReportSection .paginatedDataTable").dataTable({
        "fnDrawCallback": function() {
            var chs = $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:checked:not(:disabled)");
            if (chs.length == 0) {
                $("#billingCostReportSection .processBtn").attr("disabled", "disabled");
            } else {
                $("#billingCostReportSection .paginatedDataTable .processBtn").attr("disabled", "");
            }
            
            if (chs.length == $("#billingCostReportSection .paginatedDataTable tbody tr input[name='invoiceRecordProcessed']:not(:disabled)").length) {
                $("#checkAllInvoice").attr("checked", "checked");
            } else {
                $("#checkAllInvoice").removeAttr("checked");
            }            
        },
        "iDisplayLength": 50,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
              "oLanguage": {
                   "sLengthMenu": sLongStdMenu + " per page"
               },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l',
        "aaSorting": [[4,'asc']],
        "aoColumns": aoColumns

    });



    function formatDate(d) {
        var t1 = d.getDate();
        if (t1 < 10) {
            t1 = '0' + t1;
        }

        var t2 = d.getMonth() + 1;
        if (t2 < 10) {
            t2 = '0' + t2;
        }

        var t3 = d.getFullYear();

        return t2 + "/" + t1 + "/" + t3;
    }

    $('.summaryWeek').click(function() {
        var weekOf = $(this).attr('rel');

        var d1 = new Date();
        d1.setTime(parseInt(weekOf));
        d1.setHours(0);
        d1.setMinutes(0);
        d1.setSeconds(0);
        d1.setMilliseconds(0);

        var d2 = new Date();
        d2.setTime(d1.getTime() + 7 * 24 * 3600 * 1000 - 1000);

        $('#DashboardSearchForm input[name=formData.startDate]').val(formatDate(d1));
        $('#DashboardSearchForm input[name=formData.endDate]').val(formatDate(d2));
        $('#DashboardSearchForm').submit();
    });

    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");
    $(".dataTables_length").addClass("showPages");
    
    /* init date-pack */
    if($('.date-pick').length > 0){
        Date.firstDayOfWeek = 0;
    	$(".date-pick").datePicker({startDate:'01/01/2001'});
    }

    /* start/end only visible when contest type */
    $('#searchIn').bind("change",function() {
    	  if($('#searchIn').val() == 'CONTESTS') {
    	  	$('#datefilter').show();
    	  } else {
    	  	$('#datefilter').hide();
    	  }
    });
    $('#searchIn').trigger("change");

    $('#scheduledContestsViewType').change(function() {
        $('.scData').hide();
        $('.' + $(this).val() + 'ScheduledContests').show();
    });


    $("#pipelineSummary .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineScheduledContests .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $('.' + $('#scheduledContestsViewType').val() + 'ScheduledContests').show();
            $('.viewType').show();
            $(this).removeClass("collapse");
        } else {
            $(".scData").hide();
            $('.viewType').hide();
            $(this).addClass("collapse");
        }
    });

    $("#costReportAggregationArea .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $('.' + $('#aggregationCostReportType').val() + 'AggregationCostReport').show();
            $('.viewType').show();
            $(this).removeClass("collapse");
        } else {
            $(".scData").hide();
            $('.viewType').hide();
            $(this).addClass("collapse");
        }
    });

    $("#pipelineDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#costDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $("#billingCostDetails .expand").click(function() {
        $(this).blur();
        if ($(this).hasClass("collapse")) {
            $(this).parent().parent().next().show();
            $(this).parent().parent().parent().next().show();
            $(this).removeClass("collapse");
        } else {
            $(this).parent().parent().next().hide();
            $(this).parent().parent().parent().next().hide();
            $(this).addClass("collapse");
        }
    });

    $('.removeNumericalFilter').click(function() {
        $(this).parent().remove();
    });

});
   
/**
 * submits the search form.
 */    
function directSearch() {
	$('#formDataExcel').val("false");
	document.DashboardSearchForm.submit();
    modalPreloader();
}    

/**
 * submits the search form and trigger excel download.
 */    
function directExcel() {
	$('#formDataExcel').val("true");
	document.DashboardSearchForm.submit();
}    

function isNumber(v) {
    var regExp1 = new RegExp('^[0-9]*\\.[0-9]*$');
    var regExp2 = new RegExp('^[0-9]*$');
    return regExp1.test(v) || regExp2.test(v);
}
