/**
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for dashboard.
 *
 *  Version 1.1 - TC Direct - Page Layout Update Assembly & TC Direct - Page Layout Update Assembly 2
 *  - Added auto truncated function.
 *
 *  Version 1.2 - TC Direct UI Improvement Assembly 1 (BHCUI-83) change note
 *  - Check whether the project name is empty.
 *
 *  Version 1.3 - Release Assembly - TC Cockpit Sidebar Header and Footer Update
 *  - Changed the direct project drop down in right sidebar to native selection style.
 *  - Changed the scroll bar of project contests list to native browser scroll bar.
 *  - Fix the zebra style issues of 'Sort Contests by'
 *
 *  Version 1.4 - TC Direct Contest Dashboard Update Assembly
 *  - Apply to new prototype, change the layout when window resized.
 *
 *  Version 1.5 - Release Assembly - TC Cockpit Sidebar Header and Footer Update
 *  - Add the functions for ajax pre loader and modal window.
 *
 *  Version 1.6 - Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination
 *  - Add javascript codes for the customer dropdown in the right sidebar
 *
 *  Version 1.7 - Release Assembly - TopCoder Cockpit Modal Windows Revamp changes notes:
 *  - Add methods for closing new "add new project" modal window
 *
 *  Version 1.8 - Release Assembly - TopCoder Cockpit Project Overview Update 1
 *  - Add javascript codes to adjust the the project copilot box when resizing browser
 *
 *  Version 1.9 - TC Cockpit Post a Copilot Assembly 
 *  - Update the img src of pre loading modal.
 *
 *  Version 2.0 - TC Cockpit Participation Metrics Report Part One Assembly 1 
 *  - Add the click handler for the "Select Report Type" drop down.
 *
 *  Version 2.1 - Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar
 *  - Change the customer drop down to work with the new filter panels
 *
 *  Version 2.2 - Release Assembly - TopCoder Cockpit Reports Filter Panels Revamp
 *  - Add the js codes to handle multiple selection box.
 * 
 *  Version 2.3 - Release Assembly - TC Direct Cockpit Release Two
 *  - Add the js codes to handle not choose 2nd place copilot button.
 * 
 *  Version 2.4 - TC Cockpit Report Filters Group By Metadata Feature and Coordination Improvement
 *  - Add the js codes to load group by by customer id and load group values by group by id via ajax
 *  
 *  Version 2.5 - TC Direct Issue Tracking Tab Update Assembly 1
 *  - Add the js codes to handle add/edit JIRA issue in contest issue tracking page.
 *
 *  Version 2.6 - Adding Contest Approval Feature in Direct
 *  - Added the js code to submit Approval scorecard
 *
 *  Version 2.7 - Release Assembly - TC Direct Cockpit Release Three version 1
 *  - Add the js codes to display tooltip for timeline in contest dashboard of contest details page
 *
 *  Version 2.8 - Release Assembly - TC Cockpit Enterprise Calendar Revamp
 *  - Add the js codes to load enterprise calendar data for the enterprise calendar page
 *
 *  Version 2.9 - TopCoder Cockpit - Bug Race Project Contests View update:
 *  - Hide the filter panel in project contests calendar view
 *
 *  Version 2.10 - Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2) updates:
 * - Add js codes to support uploading attachments to JIRA issue in contest issue tracking page.
 *
 *  Version 2.11 - Module Assembly - TC Cockpit Project Metrics Report  update:
 *  - Add support for project metrics report
 *
 *  Version 2.2 - Release Assembly - TC Direct Cockpit Release Six
 *  - Update the codes to auto adjust width and height of billing cost report.
 *
 *  Version 2.3 - Module Assembly - JIRA issues loading update and report creation
 *  - Add support for jira issues report.
 *
 *  Version 2.4 - Release Assembly - TC Direct Cockpit Release Seven version 1.0
 *  - Add support for copilot posting review - no select any copilot feature
 *
 *  Version 2.5 - Release Assembly - TC Direct Issue Tracking Tab Update Assembly 3
 *  - Add support for create/update/display direct project bugs
 * 
 *  Version 2.6 - Module Assembly - TC Cockpit Operations Dashboard For PMs
 *  - Add support for operations dashboard.
 * 
 *  Version 2.7 - BUGR-7279
 *  - Fix button not work issue.
 * 
 *  Version 2.8 - Module Assembly - TC Cockpit Invoice History Page Update
 *  - Add support for invoice status filter.
 *
 *  Version 2.9 - Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp
 *  - Comment out the old copilot pickup handler
 *  
 *  Version 3.0 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
 *  - Update a method to fix a text inconsistency bug.
 *
 *  Version 3.1 - Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring
 *  - Add selector for the setting pages.
 *
 *  Version 3.2 - Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0
 *  - Add padding for the project mask.
 * 
 * @author tangzx, Blues, GreatKevin, isv, GreatKevin, xjtufreeman, bugbuka, notpad, GreatKevin, Ghost_141, Veve
 * @version 3.2
 */

var mouse_is_inside;

$(document).ready(function(){
						   
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
    var filterToSynchronized = ['customerId', 'billingAccountId', 'projectId', 'startDate', 'endDate', 'statusIds', 'projectCategoryIds'];

    var synchronizeFilters = function() {

        if($("#pipelineReportsPage").length != 0) {
            return "";
        }

        var requestPart = "?";
        var requestForm = $("form").serialize();
        var requests = requestForm.split('&');
        for(var i = 0; i < requests.length; ++i) {
            var requestsNeeded = false;
            for(var k = 0; k < filterToSynchronized.length; ++k) {
                if (requests[i].indexOf(filterToSynchronized[k]) != -1) {
                    requestsNeeded = true;
                    break;
                }
            }
            if (requestsNeeded) {
                if(requestPart != "") requestPart += "&";
                requestPart += requests[i];
            }
        }
        return requestPart;
    }
	
    $("#selectReport").change(function() {
        var reportType = $(this).val();
        if (reportType == 'COST') {
            window.location.href = '/direct/dashboardCostReport' + synchronizeFilters();
        } else if (reportType == 'PIPELINE') {
            window.location.href = '/direct/dashboardReports';
        } else if (reportType == 'BILLING_COST') {
            window.location.href = '/direct/dashboardBillingCostReport' + synchronizeFilters();
        } else if (reportType == 'PARTICIPATION') {
            window.location.href = '/direct/dashboardParticipationReport' + synchronizeFilters();
        } else if (reportType == 'PROJECT_METRICS') {
            window.location.href = '/direct/dashboardProjectMetricsReport' + synchronizeFilters();
        } else if (reportType == 'JIRA_ISSUES') {
            window.location.href = '/direct/dashboardJiraIssuesReport' + synchronizeFilters();
        }
    });

    $("#selectSetting").change(function() {
        var setting = $(this).val();
        if (setting == 'notifications') {
            window.location.href = '/direct/settings/notifications';
        } else if (setting == 'permissions') {
            window.location.href = '/direct/settings/permissions';
        } else if (setting == 'fee') {
            window.location.href = '/direct/settings/admin/contestFee';
        } else if (setting == 'sync') {
            window.location.href = '/direct/settings/admin/syncUser';
        }
    });
	
	//------------------------------------------------- Contests List
	
	/* sort contests */
    if ($("#contestsTable").length > 0) {
        $("#contestsTable").tablesorter();
        
        $("#rightTableHeader .statusTh").click().click();
    }
    
    /* init date-pack */
    if($('.date-pick').length > 0){
        $(".date-pick").datePicker({startDate:'01/01/2001'});
    }   
	
	/* sort contest by title */
	sortTitle = function(){
		 var sorting = [[1,0]];
        $("#contestsTable").trigger("sorton",[sorting]);
        $("#contestsTable tr").removeClass("even");
		$("#contestsTable tr").each(function(){
			$("#contestsTable tr:even").addClass("even");
		});
	}
	
	/* sort contest by status */
	sortStatus = function(){
		var sorting = [[0,1]];
        $("#contestsTable").trigger("sorton",[sorting]);
        $("#contestsTable tr").removeClass("even");
		$("#contestsTable tr").each(function(){
			$("#contestsTable tr:even").addClass("even");
		});
	}

    if ($("#contestsTable tbody tr").length > 0) {
        sortStatus();
    }

	
	/* sort contest by type */
	sortType = function(){
		var sorting = [[2,0]];
        $("#contestsTable").trigger("sorton",[sorting]);
        $("#contestsTable tr").removeClass("even");
		$("#contestsTable tr").each(function(){
			$("#contestsTable tr:even").addClass("even");
		});
	}
	
	/* get the selected index and sort the contests table */
	sortTable = function(mySelect){
		var selected = mySelect.options[mySelect.selectedIndex].value;
		
		if( selected == "title" )
			sortTitle();
		else if(selected == "status")
			sortStatus();
		else 
			sortType();
	}
	
	/*-Show the scrollbar when the number of contests is more than 10-*/
	
	adjustContestListHeight = function() {
        var rows_height = 0;
        var contests_nbre = 0;

        /* get the height of the 10 first rows ( one contest per row)*/
        $("#contestsTable TBODY").children().each(function() {
            if (contests_nbre < 10)
                rows_height += $(this).height();
            contests_nbre++;
        });

        if (contests_nbre > 10) {
            $(".contestsContent").height(rows_height);

            // Chrome
            if (ua.match(/chrome\/([\d.]+)/) != null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0] > 2) {
                $(".contestsContent").height(rows_height + 20);
            }

            // Safari
            if (ua.match(/version\/([\d.]+).*safari/) != null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0] > 3) {
                $(".contestsContent").height(rows_height + 20);
            }

            // IE 7
            if ($.browser.msie && $.browser.version == 7.0) {
                $(".contestsContent").height(rows_height + 20);
            }

            // IE 8
            if ($.browser.msie && $.browser.version == 8.0) {
                $(".contestsContent").height(rows_height + 20);
            }
            $(".contestsContent TABLE").css("width", "232px");

            return rows_height + 20;
        }

        return 0;
    }

    adjustContestListHeight();

    if ($(".contestsContent").length > 0) {
        /* Stylished scrollbar*/
        $('.contestsContent').jScrollPane({
            scrollbarWidth: 17,
            showArrows: true
        });
    }
	
	/*-------------------------- Show/hide the dropdown list --*/

    var updateCustomerDropDown  = function(dropDownWrapper,items, customerName){
        var dropDown = dropDownWrapper.find(".contestsDropDown ul");
        var input = dropDownWrapper.find(".inputSelect input");
        dropDown.find("li").remove();
        input.val("");
        $.each(items,function(index,item){
            var li = $("<li><a class='longWordsBreak' href='#'></a></li>");
            li.data("id",item.id);
            li.find("a").text(item.value);
            if(customerName == undefined && index == 0){
                input.val(item.value);
            } else {
                input.val(customerName);
            }
            li.appendTo(dropDown);
        })
        dropDown.find("li:odd").addClass("even");
    }

    var updateProjectDropDown  = function(dropDownWrapper,items){
        var dropDown = dropDownWrapper.find(".contestsDropDown ul");
        var input = dropDownWrapper.find(".inputSelect input");
        dropDown.find("li").remove();
        input.val("");
        var hasCurrentProject = false;
        $.each(items,function(index,item){
            var li = $("<li><a class='longWordsBreak' href='/direct/projectOverview?formData.projectId=" + item.id + "'></a></li>");
            li.data("id",item.id);
            li.find("a").text(item.value);
            if(item.value == currentProjectName){
                input.val(item.value);
                hasCurrentProject = true;
            }
            li.appendTo(dropDown);
        })

        if (!hasCurrentProject){
        	$(".contestList .filter .projectSelectMask .inputSelect input").addClass("paddingleft");
        	input.val("Select a project here");
        }
        

        if (typeof isInProjectScope != 'undefined' && !isInProjectScope) {

            if (hasCurrentProject) {
                if ((undefined != currentProjectContests) && $("#contestsTable tbody tr").length <= 0) {
                    $("#contestsTable tbody").html(currentProjectContests);
                    var newHeight =  adjustContestListHeight();
                    if (newHeight > 0) {
                        $(".jScrollPaneContainer").height(newHeight);
                    }
                    $('.contestsContent').jScrollPane({
                        scrollbarWidth: 17,
                        showArrows: true
                    });
                    
                    $("#contestsTable").tablesorter();
                }
                
                $("#rightTableHeader").show();
            } else {
                $("#rightTableHeader").hide();
            
                // clear the contests if needed
                if ($("#contestsTable tbody tr").length > 0) {
                    currentProjectContests = $("#contestsTable tbody").html();
                    $("#contestsTable tbody").html('');
                    $(".jScrollPaneContainer").height(233);
                    $('.contestsContent').jScrollPane({
                        scrollbarWidth: 17,
                        showArrows: true
                    });
                }
            }
        }

        dropDown.find("li:odd").addClass("even");
    }
    
    
    function compareProject(projectA, ProjectB) {
        if (projectA.value.toLowerCase() < ProjectB.value.toLowerCase())
            return -1;
        if (projectA.value.toLowerCase() > ProjectB.value.toLowerCase())
            return 1;
        return 0;
    }
    
    function compareCustomer(customerA, customerB) {
        if (customerA.value.toLowerCase() == 'all customers') return -1;
        if (customerB.value.toLowerCase() == 'all customers') return 1;
        
        if (customerA.value.toLowerCase() < customerB.value.toLowerCase())
            return -1;
        if (customerA.value.toLowerCase() > customerB.value.toLowerCase())
            return 1;
        return 0;
    }

    var getCustomers = function(){
        var arr = [{"id":"","value":"All Customers"}];
        var count = 0;
        var noCustomer;
        if (typeof rightSidebarData != 'undefined' && rightSidebarData) {
            for(var p in rightSidebarData){
                 if(typeof(rightSidebarData[p])!="function"){
                    count ++;
                    var obj = new Object();
                     obj.value = p;
                     obj.id = rightSidebarData[p]["id"];
                     obj.projects = rightSidebarData[p]["projects"];

                     if (!(obj.id == "none")) {
                        arr.push(obj);
                     } else {
                        noCustomer = obj;
                     }
                 }
            }
        }

        arr.sort(compareCustomer);
        
        if (undefined != noCustomer) {
            arr.push(noCustomer);
        }

        if (count == 1) { arr.shift(); }

        return arr;
    }
    var customerList = getCustomers();

    var getCustomerWithProject = function(projectName) {
        var result = {};

        result.id = '';
        result.name = 'All Customers';

        $.each(customerList, function(index, item) {
            var projects = item.projects;
            if (!(item.id == '' || item.id == 'none')) {
                for (var p in projects) {
                    if (typeof(projects[p]) != "function") {
                        var name = projects[p];
                        if ($.trim(name) == $.trim(projectName)) {
                            result.id = item.id;
                            result.name = item.value;
                        }
                    }
                }
            }
        });

        return result;
    }



    var getProjects = function(id){
        var arr = [];
        $.each(customerList,function(index,item){
            var projects = item.projects;
            if(id == "" || id == "0"){
                for(var p in projects){
                     if(typeof(projects[p])!="function"){
                        var obj = new Object();
                         obj.id = p;
                         obj.value = projects[p];
                         arr.push(obj);
                     }
                }
            }else{
                if(item.id == id){
                    for(var p in projects){
                         if(typeof(projects[p])!="function"){
                            var obj = new Object();
                             obj.id = p;
                             obj.value = projects[p];
                             arr.push(obj);
                         }
                    }
                }
            }
        });

        arr.sort(compareProject);

        return arr;
    }

    updateProjectDropDown($(".projectSelectMask"),getProjects(""));

    if (typeof (currentProjectName) != "undefined" && currentProjectName != '') {
        var result = getCustomerWithProject(currentProjectName);
        updateCustomerDropDown($(".customerSelectMask"), customerList, result.name);
        updateProjectDropDown($(".projectSelectMask"), getProjects(result.id));
    } else {
        updateCustomerDropDown($(".customerSelectMask"), customerList);
    }

	showHideProjectList = function(){
		var list = $("#dropDown1 .dropList");
        if(list.is(":hidden")){
            list.show();
        }else{
            list.hide();    
        }        

        if($(".projectSelectMask .contestsDropDown UL").height() > 200) {
            $(".projectSelectMask .contestsDropDown UL").css('width', 233);
        }
        var input = $(".projectSelectMask .inputSelect>input")[0];
        input.originalValue = input.value;
	}

    showHideCustomerList = function(){
		var  contestsDropDown = $(".customerSelectMask .contestsDropDown");
        if (contestsDropDown.is(":hidden")) {
            $(".customerSelectMask .contestsDropDown").hide();
        }
        
        var list = contestsDropDown.find("ul");
        if(list.is(":hidden")){
            $(".dropdownWidget .dropList").hide();
            list.show();
        }else{
            list.hide();    
        }        
        //contestsDropDown.slideToggle(100);
        var input = $(".customerSelectMask .inputSelect>input")[0];
        input.originalValue = input.value;
	}
	
	/*TCCC-2398*/
	/*-------------------------- filter the project --*/
	
	filterProject = function(){
		if (!$("#dropDown1 .dropList").is(":visible")) {
			showHideProjectList();
		}
        var input = $(".projectSelectMask .inputSelect>input")[0];
        var typedText = input.value;
        if (input.value != input.originalValue) {
            input.originalValue = input.value;
        } else {
            return;
        }
        var idx = 0;
        $("#dropDown1>ul>li").each(function() {
            if ($(this).find("a")[0].innerHTML.toLowerCase().indexOf(typedText.toLowerCase()) == -1) {
                $(this).css('display', 'none').removeClass("hover");
            } else {
                ++idx;
                $(this).css('display', '').removeClass("even");
                if (idx % 2 == 0) {
                    $(this).addClass("even");
                }
            }
        });
	}
    
    selectProject = function(e) {
        if (!$("#dropDown1 .dropList").is(":visible")) {
			showHideProjectList();
		}
        var code = e.which ? e.which : e.keyCode;
        var current = $("#dropDown1>ul>li.hover:visible");
        var selected;
        if (code == 38) {
            // up
            if (current.size() == 0) {
                selected = $("#dropDown1>ul>li:visible:first");
            } else {
                selected = current.prev(":visible");
            }
        } else if (code == 40) {
            // down
            if (current.size() == 0) {
                selected = $("#dropDown1>ul>li:visible:first");
            } else {
                selected = current.next(":visible");
            }
        } else if (code == 13) {
            // enter
            if (current.size() > 0) {
                $("a", current).each(function() {
                    this.click();
                });
            }
            return;
        } else {
            return;
        }
        if (selected.size() > 0) {
            $("#dropDown1>ul>li").removeClass("hover");
            selected.addClass("hover");
            var container = $("#dropDown1");
            var cHeight = container.height() - 25;
            var u = container[0].scrollTop;
            var v = u + cHeight;
            var offsetTop = selected[0].offsetTop;
            var offsetBottom = offsetTop + selected.height();
            if (offsetTop < u) {
                container.animate({
                    scrollTop: offsetTop
                }, 20);
            } else if (offsetBottom > v) {
                container.animate({
                    scrollTop: offsetBottom - cHeight
                }, 20);
            }
        }
    }
    
	filterCustomer = function(){
		if (!$("#dropDown2 .dropList").is(":visible")) {
			showHideCustomerList();
		}
        var input = $(".customerSelectMask .inputSelect>input")[0];
        var typedText = input.value;
        if (input.value != input.originalValue) {
            input.originalValue = input.value;
        } else {
            return;
        }
        var idx = 0;
        $("#dropDown2>ul>li").each(function() {
            if ($(this).find("a")[0].innerHTML.toLowerCase().indexOf(typedText.toLowerCase()) == -1) {
                $(this).css('display', 'none').removeClass("hover");
            } else {
                ++idx;
                $(this).css('display', '').removeClass("even");
                if (idx % 2 == 0) {
                    $(this).addClass("even");
                }
            }
        });
	}

    selectCustomer = function(e) {
        if (!$("#dropDown2 .dropList").is(":visible")) {
			showHideCustomerList();
		}
        var code = e.which ? e.which : e.keyCode;
        var current = $("#dropDown2>ul>li.hover:visible");
        var selected;
        if (code == 38) {
            // up
            if (current.size() == 0) {
                selected = $("#dropDown2>ul>li:visible:first");
            } else {
                selected = current.prev(":visible");
            }
        } else if (code == 40) {
            // down
            if (current.size() == 0) {
                selected = $("#dropDown2>ul>li:visible:first");
            } else {
                selected = current.next(":visible");
            }
        } else if (code == 13) {
            // enter
            if (current.size() > 0) {
                current.trigger("click");
            }
            return;
        } else {
            return;
        }
        if (selected.size() > 0) {
            $("#dropDown2>ul>li").removeClass("hover");
            selected.addClass("hover");
            var container = $("#dropDown2");
            var cHeight = container.height() - 25;
            var u = container[0].scrollTop;
            var v = u + cHeight;
            var offsetTop = selected[0].offsetTop;
            var offsetBottom = offsetTop + selected.height();
            if (offsetTop < u) {
                container.animate({
                    scrollTop: offsetTop
                }, 20);
            } else if (offsetBottom > v) {
                container.animate({
                    scrollTop: offsetBottom - cHeight
                }, 20);
            }
        }
    }
    
    $(".customerSelectMask .inputSelect input").focus(function(){
        showHideCustomerList();
    });
    $(".customerSelectMask .inputSelect a").click(function(){
        showHideCustomerList();
    });

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



    $(".customerSelectMask UL LI").click(function() {
        var mask = $(this).parents(".customerSelectMask");
        mask.find("input").val($(this).find("a").text());
        
        if (!mask.find(".contestsDropDown .dropList").is(":hidden")) {
            mask.find(".contestsDropDown .dropList").hide();
        }
        updateProjectDropDown($(".projectSelectMask"), getProjects($(this).data("id")));
        
        /*
        if ($("#activeContests").length > 0 || $("#projectsResult").length > 0 || $("#pmProjectsResult").length > 0 || $("#MyCopilotPostings").length > 0) {
            // call method defined by filter panel
            filterbyCustomer($(this).data("id"),$(this).find('a').html());
            var customer = "";
            if ($(this).data("id") != "0" && $(this).data("id") != "") {
                customer = $(this).text();
            }
            updateBreadcrumb(customer);
        }
        */
        return false;
    })
	
	/*------------------------- hover state of the dropdown list  --*/
	
	$(".contestsDropDown UL LI").live("mouseover",function(){
			$(this).addClass("hover");
	});

	$(".contestsDropDown UL LI").live("mouseout",function(){
			$(this).removeClass("hover");
	});
	
	/*------------------------- hover state of the contests table --*/
	$("table#contestsTable tr").mouseover(function(){
		$(this).addClass("highlight");
	});
	
	$("table#contestsTable tr").mouseout(function(){
		$(this).removeClass("highlight");
	});
	
	/*------------------------- show or hide rows functionality in dashboard.html --*/
	// we will show just the first rows_nbre rows 
	$("TABLE.rowsToHide").each(function(){
		
		var table_id = $(this).attr("id");
		var hide_row = false;
		
		$("#"+table_id+" TBODY TR").each(function(){
												  
				if( this.className.search("hideStart") != -1 ){
						hide_row = true;
				}
					
				if( hide_row )
						$(this).addClass("hide");								
		})
	});
	
	
	showHideRows = function(myLink, tableId){
		
		if( $(myLink).html() == "View More" ){ //when the user click the view more link we will show the hidden rows
			$("#"+tableId+" TBODY TR").each(function(){
				$(this).removeClass("hide");
			});
			
			 $(myLink).html("Hide Rows");
			 $(myLink).addClass("less");
			 
		}else{ //when the user click the hide rows link we will hide some rows
			var hide_row = false;
			$("#"+tableId +" TBODY TR").each(function(){
					
					if( this.className.search("hideStart") != -1 ){
						hide_row = true;
					}
					
					if( hide_row )
						$(this).addClass("hide");
			});
			
			$(myLink).html("View More");
			$(myLink).removeClass("less");
		}
		
	}
	
	/*----------------- projects table hover --*/
	$("table.project TR").mouseover(function(){
			$(this).addClass("hover");
	});
	
	$("table.project TR").mouseout(function(){
			$(this).removeClass("hover");
	});

	/*----------------- this function is for demonstration purpose, it will show some contests on the contests list --*/
	showContestsDemo = function(){
//			var curr = 0;
//			$("TABLE#contestsTable TBODY TR").each(function(){
//					if( curr > 2 )
//						$(this).addClass("hide");
//
//					curr++;
//			});
	}
	
	
	/*-------------------------------------------------------------- Popup -----------------*/
	
	var prevPopup = null;
	showPopup = function(myLink,myPopupId){
		var myLinkLeft = myLinkTop  = 0;
		
		/* hide the previous popup */
		if( prevPopup )
			$(prevPopup).css("display","none");
			
		prevPopup = $('#'+myPopupId);
		
		/* get the position of the current link */
		do{
			myLinkLeft += myLink.offsetLeft;
			myLinkTop += myLink.offsetTop;
		}while( myLink = myLink.offsetParent );
		
		/* set the position of the popup */
		var popUpHeight2 = $('#'+myPopupId).height()/2;
		
		myLinkTop -= popUpHeight2;
	
		$('#'+myPopupId).css("top",myLinkTop+'px');
		$('#'+myPopupId).css("left",( myLinkLeft + 50 )+'px');
		
		/* set the positio of the arrow inside the popup */
		$(".tooltipContainer SPAN.arrow").css("top",popUpHeight2+'px');
		
		/* show the popup */
		$('#'+myPopupId).css("display","block");
		
	}
	
	/* if the user click the next of prev link of the calendar we will hide the popups */
	$(".fc-button-prev a").click(function(){
		$(prevPopup).css('display','none');
	});
	
	$(".fc-button-next a").click(function(){
		$(prevPopup).css('display','none');
	});
	
	/*------------------------------------------------------------------------------------------*/
	
	/*-----------------  tabs4 navigation   -*/
	showHideTabs = function(myLink, myContainerId, myTabsIndex){
		/* myLink: the clicked link
		   myContainerID: the id of the tabs container
		   myTabsIndex: the index num of the tab */
		
		// get the first "ul" parent
		// the html structure is as fellow: <ul><li><a href=""></a></li></ul>
		var ULparent = $(myLink).parents()[1];
		var curr_link = 0;
		
		$($(ULparent).children()).each(function(){ //add the "on" class to the parent (li) of the clicked link
				
				if( myTabsIndex == curr_link ){
					$(this).addClass("on");
				}else{
					$(this).removeClass("on");
				}
				
				curr_link++;
				   
		});
		   
		var current_tab = 0;
		$($("#"+myContainerId).children()).each(function(){ // show the tab with the index myTabsIndex and hide the others
				
				if( current_tab == myTabsIndex ){
					$(this).css('display','block');
				}else{
					$(this).css('display','none');
				}
				
				current_tab++;
				
		})
	}
	
	/*--------------------------------------- Show/Hide group users (the table on the permissions tab ) --*/
	showHideGroup = function(myLink, rowClass){
		/*  myLink: the clicked link
			rowClass: the class name of the group */
		
		/* change the link ico */
		if( myLink.className.search("expand") != -1 ){
			$(myLink).removeClass("expand");
			$(myLink).addClass("collapse");
		}else{
			$(myLink).removeClass("collapse");
			$(myLink).addClass("expand");
		}
		
		
		$("."+rowClass).toggle();
	}
	
	/*-------------------------------------- Check/uncheck all checkboxes functionnality --*/
	/* myCheckbox: the "select all" check box
	   myContainerId: the id of the container of the checkboxes */
	/*
	checkAll = function(myCheckbox, myContainerId){
		$("#"+myContainerId+" input.selectUser").each(function(){
				if( myCheckbox.checked )
					this.checked = true;
				else
					this.checked = false;
		});
	}
	*/

	/*------------------------------ w/search ---*/
	var prev_result = null;
	showResults = function(mySelect, containerId){
	
		$("#"+containerId).css('display','block');
		
		if( prev_result )
			$(prev_result).css('display','none');
		
		var result_container_id =  mySelect[0].options[mySelect[0].selectedIndex].value;
		$("#"+result_container_id).css('display','block');
		
		prev_result = $("#"+result_container_id);
		
	}
	
	/*-------------------------------------------------------------------------*/
	/* add zebra stripping for projectStats tables */
	/* Uncommented since zebra style is looking weird */
	// $('table.contests tbody tr:odd').addClass('even');
	
	/*-------------------------------------------------- fix the width of the tabs3 items ----------------------*/	

	fixTabs3Width = function(){

		var myLastLIWidth = $("#tabs3 UL LI.lastItem").width();
		var myFirstLIWidth = $("#tabs3 UL LI.firstItem").width();
	
		var addit_pixels = eval( myFirstLIWidth ) * 4 - eval( $("#tabs3 UL").width() ); 
		
		if( $.browser.safari || ($.browser.msie && $.browser.version <= 7.0 ) )
			$("#tabs3 UL LI.lastItem").css("width", (myLastLIWidth - addit_pixels)+"px");
	}
	
	
	//fixTabs3Width();
	
	//$("#tabs3").resize(function(){
		/*-------------------------------------------------- fix the width of the tabs3 items ----------------------*/	
	//	if( $.browser.msie || $.browser.safari )
	//		document.location.reload();
	//});
	
	$('#projectName').change(function() {
		var projectName = jQuery.trim($("#projectName").val());
		$("#projectName").val(projectName);
		return true;
	});
	
    /* open link in new windows for .memberProfile and .copilotProfile */
    $(".memberProfile[rel='_blank']").attr("target","_blank");
    $(".copilotProfile[rel='_blank']").attr("target","_blank");

    /*
     * ************************************************
     * scripts for bug tracking - contest integration
     * ************************************************
     */


    $('#issue .issueSelectionContent div.rowItem:odd').addClass('oddRowItem');


    $('#issue .issueSelectionContent td').each(function() {
        $(this).find('div.rowItem:last').addClass('lastRowItem');
    });

    $('#issue .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
    $("#issue tbody>tr:visible:last").addClass("lastTr");

    //view all for issue
    /*$('#issue .checkbox').live('click',function(){
     if($(this).attr('checked')){
     $('#issue .hideDetails').show();
     $('#issue .viewDetails').hide();
     $('#issue .longDetails').show();
     $('#issue .shortDetails').hide();
     }else{
     $('#issue .hideDetails').hide();
     $('#issue .viewDetails').show();
     $('#issue .longDetails').hide();
     $('#issue .shortDetails').show();
     }
     });*/

    $('#issue .hideDetails').live('click', function() {
        $(this).hide();
        $(this).parent().find('.viewDetails').show();
        $(this).parent().parent().parent().find('.longDetails').hide();
        $(this).parent().parent().parent().find('.shortDetails').show();
    });

    $('#issue .viewDetails').live('click', function() {
        $(this).hide();
        $(this).parent().find('.hideDetails').show();
        $(this).parent().parent().parent().find('.shortDetails').hide();
        $(this).parent().parent().parent().find('.longDetails').show();
    });

    function sortFunction() {
        $('#issue .issueSelectionContent div.rowItem').removeClass('lastRowItem');
        $('#issue .issueSelectionContent div.rowItem').removeClass('oddRowItem');
        $('#issue .container2Opt .corner').removeClass('evencorner');
        $('#issue .issueSelectionContent div.rowItem:visible:odd').addClass('oddRowItem');
        $('#issue .issueSelectionContent td').each(function() {
            $(this).find('div.rowItem').removeClass('lastRowItem');
            $(this).find('div.rowItem:visible:last').addClass('lastRowItem');
        });
        $('#issue .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
        if ($('#issue .issueSelectionContent div.rowItem:visible').length % 2 != 0) {
            $('#issue .container2Opt .corner').addClass('evencorner');
        }

        $("#issue tbody>tr").removeClass("lastTr");
        $("#issue tbody>tr:visible:last").addClass("lastTr");
    }

    function hideIssuesEmptyContest() {
        if ($("#issue .contestIssuesTd").length > 0) {

            $("#issue .contestIssuesTd").each(function() {
                $(this).parent().show();
                if ($(this).find(".rowItem:visible").length == 0) {
                    // hide the row
                    $(this).parent().hide();
                }

            });
        }
    }

    $('#issue .select2').change(function() {
        if ($(this).val().match('All Issues')) {
            $('#issue .rowItem').show();
            hideIssuesEmptyContest();
            sortFunction();
        } else if ($(this).val().match('Unresolved Issues')) {
            $('#issue .rowItem').show();
            $('#issue .rowItem').each(function() {
                $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().hide();
                $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().hide();
            });
            hideIssuesEmptyContest();
            sortFunction();
        } else {
            $('#issue .rowItem').hide();
            $('#issue .rowItem').each(function() {
                $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().show();
                $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().show();
            });
            hideIssuesEmptyContest();
            sortFunction();
        }
    });

    if ($('#issue .issueSelectionContent div.rowItem').length % 2 != 0) {
        $('#issue .container2Opt .corner').addClass('evencorner');
    }

    $('#bugRace .issueSelectionContent div.rowItem:odd').addClass('oddRowItem');
    $('#bugRace .issueSelectionContent td').each(function() {
        $(this).find('div.rowItem:last').addClass('lastRowItem');
    });
    $('#bugRace .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
    $("#bugRace tbody>tr:visible:last").addClass("lastTr");

    $(".viewAll input").attr("checked", "");

    //view all for issue
    $('.checkbox').live('click', function() {
        if ($(this).attr('checked')) {
            $('.hideDetails').show();
            $('.viewDetails').hide();
            $('.longDetails').show();
            $('.shortDetails').hide();
            $('.checkbox').attr('checked', true);
        } else {
            $('.hideDetails').hide();
            $(' .viewDetails').show();
            $('.longDetails').hide();
            $('.shortDetails').show();
            $('.checkbox').attr('checked', false);
        }
    });

    $('.viewAll label').live('click', function() {
        if ($(this).parent().find('.checkbox').attr('checked')) {
            $('.hideDetails').hide();
            $(' .viewDetails').show();
            $('.longDetails').hide();
            $('.shortDetails').show();
            $('.checkbox').attr('checked', false);
        } else {
            $('.hideDetails').show();
            $('.viewDetails').hide();
            $('.longDetails').show();
            $('.shortDetails').hide();
            $('.checkbox').attr('checked', true);
        }
    });

    $('#bugRace .hideDetails').live('click', function() {
        $(this).hide();
        $(this).parent().find('.viewDetails').show();
        $(this).parent().parent().parent().find('.longDetails').hide();
        $(this).parent().parent().parent().find('.shortDetails').show();
    });

    $('#bugRace .viewDetails').live('click', function() {
        $(this).hide();
        $(this).parent().find('.hideDetails').show();
        $(this).parent().parent().parent().find('.shortDetails').hide();
        $(this).parent().parent().parent().find('.longDetails').show();
    });

	$("#bugRace .FileUpload").hover(
		function(){
			$(this).children(".draft").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").css("background-position","left bottom");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right bottom");
		},
		function(){
			$(this).children(".draft").css("background-position","left top");
			$(this).children(".draft").children("span.left").css("background-position","left top");
			$(this).children(".draft").children("span.left").children("span.right").css("background-position","right top");
		}
    );

    function restoreBugrFileInputs() {
        bugrAttachments = [];
        $(".FileField").val("");
        $([1,2,3]).each(function() {
            if ($("#file" + this).length == 0) {
                var txtfile = $("#txtfile" + this);
                txtfile.before('<input id="file' + this + '" class="BrowserHidden" type="file" name="document" size="24" onchange="getElementById(\'txtfile' + this + '\').value = getElementById(\'file' + this + '\').value;">');
                if ($.browser.mozilla) {
                    // firefox
                    $("input.BrowserHidden").attr("size", "8");
                }
                txtfile.parent().parent().find(".btnUpload").show();
            }
        });
        $('.issueSelectionContent #txtfile1').unbind("click").click(function(){
            $('.issueSelectionContent #file1').trigger("click");
        });
        $('.issueSelectionContent #txtfile2').unbind("click").click(function(){
            $('.issueSelectionContent #file2').trigger("click");
        });
        $('.issueSelectionContent #txtfile3').unbind("click").click(function(){
            $('.issueSelectionContent #file3').trigger("click");
        });
        $("input.BrowserHidden").val("");
        $("input.BrowserHidden").unbind("change").change(function() {
            $(this).parent().find(".FileField").val($(this).val());
        });
    }
    
// when click "Add New" button in contest tracking page under Bug Race tab
    $('#issue .btnAddNew, #bugRace .btnAddNew').click(function() {
        $("li.bugRaceTab a").click();
        $('#bugRace .issueSelectionContent .inputContainer').show();
        $('#bugRace .issueSelectionContent .content').hide();
        $('#bugRace .issueSelectionContent .inputContainer .btnCreate').show();
        $('#bugRace .issueSelectionContent .inputContainer .btnUpdate').hide();
        $("#issueId").val("");
        $("#cca").attr("checked", "");
        $('#bugRace label').removeClass('required');
        $('#bugRace .issueSelectionContent .inputContainer label:lt(2)').show();
        $('#bugRace .issueSelectionContent .inputContainer .row:lt(2)').show();
        $('#bugRace .issueSelectionContent .inputContainer .text,.issueSelectionContent .inputContainer .textarea').val('');
        $('#tcoPoints')[0].selectedIndex = 0;
        if ($.browser.msie && ($.browser.version == "7.0")) {
            $("#bugRace label:contains('Summary')").css('padding-top','0');
        }
        $("#bugRace .viewAll").hide();
        $("#attachmentNames").val("");
        restoreBugrFileInputs();
		$("#existingAtt").hide();
        return false;
    });

    // when click "Cancel" button in "Add Bug Race" form panel in contest tracking page
    $('#bugRace .btnCancel').click(function() {
        $('.issueSelectionContent .content').show();
        $('.issueSelectionContent .inputContainer').hide();
        $('label').removeClass('required');
        $("#bugRace .viewAll").show();
        if( $("#bugForm #projectBug").val() == 'true' ) {
            hideBugRacesEmptyContest();
            bugSortFunction();
        }

        return false;
    });

	function updateAttachmentsSection(rowItem) {
        var atts = [];
        rowItem.find(".attachmentName").each(function() {
            atts.push($(this).val());
        });
        $("#existingAtt").show();
        $("#existingAtt .row").html(atts.length > 0 ? atts.join("<br/>") : "None");
    }
    
	// when click "Edit" button in contest tracking page under Bug Race tab
    $('#bugRace .issueSelectionContent .button11').live("click", function() {
        if($(this).hasClass("contestEdit")){
            var tr = $(this).parents("tr");
            location.href = $(tr).find(".contestNameLink a").attr("href").trim() + "&bugIdx=" + $(this).attr("index");
        } else {
            $('.issueSelectionContent .inputContainer').show();
            $('.issueSelectionContent .content').hide();
            $('.issueSelectionContent .inputContainer .row:lt(1)').hide();
            $('.issueSelectionContent .inputContainer .btnUpdate').show();
            $('.issueSelectionContent .inputContainer .btnCreate').hide();
            if ($.browser.msie && ($.browser.version == "7.0")) {
                $("label:contains('Summary')").css('padding-top','20px');
            }
            var rowItem = $(this).parents(".rowItem");
            $("#issueName").val(rowItem.find(".contestName").val());
            $("#issueId").val(rowItem.find("input.issueId").val());
            $("#environment").val(rowItem.find(".environment").val());
            $("#description").val(rowItem.find(".description").val());
            $("#bugRace .issueSelectionContent .firstPayment").val(rowItem.find(".prize").val());
            $("#tcoPoints").val(rowItem.find(".tcoPoints").val());
            $("#cca").attr("checked", rowItem.find("input.issueCCA").val() == "true" ? "checked" : "");
            $("#bugType").val(rowItem.find("input.issueType").val());
            $("#bugRace .viewAll").hide();

            var names = [];
            rowItem.find(".attachmentName").each(function() {
                names.push($(this).val());
            });
            $("#attachmentNames").val(names.join("\\"));
            restoreBugrFileInputs();
            updateAttachmentsSection(rowItem);
            return false;
        }
    });
    
    // fill the row using the bug race data
    function fillBugRaceRow(row, bugRace) {
        row.find("input.contestName").val(bugRace.issueSummary);
        row.find(".description").val(bugRace.description);
        row.find(".environment").val(bugRace.environment);
        row.find("input.prize").val(bugRace.prize);
        row.find("input.status").val(bugRace.status);
        row.find("input.tcoPoints").val(bugRace.tcoPoints);
        row.find("input.issueId").val(bugRace.issueId);
        row.find("input.issueCCA").val($("#cca").is(":checked") ? "true" : "false");
		row.find("input.issueType").val($("#bugType").val());
        row.find("p.issueLink a").attr("href", bugRace.issueLink).html(bugRace.projectName + " / " + bugRace.issueKey);
        row.find("p.issueName a").attr("href", bugRace.issueLink).text(bugRace.issueSummary);
        row.find("div.shortDetails dd.issueStatus strong").addClass(bugRace.issueStatusClass).html(bugRace.statusName);
        row.find("div.shortDetails dd.issueCreationDate").html(bugRace.creationDateString);
        row.find("div.longDetails dd.issueStatus strong").addClass(bugRace.issueStatusClass).html(bugRace.statusName);
        row.find("div.longDetails dd.issueResolution").html(bugRace.resolutionName);
        row.find("div.longDetails dd.issuePrize").html("$" + bugRace.prize.formatMoney(2));
        row.find("div.longDetails dd.issueVotes").html(bugRace.votesNumber);
        row.find("div.longDetails dd.issueReporter a").attr("href", bugRace.reporterProfile).html(bugRace.reporter);
        var assigneeHtml;
        if (bugRace.assignee == 'Unassigned' || bugRace.assignee == '0') {
            assigneeHtml = "Unassigned";
        } else {
            assigneeHtml = '<a href="' + bugRace.assigneeProfile + '" target="_blank">' + bugRace.assignee + '</a>';
        }
        row.find("div.longDetails dd.issueAssignee").html(assigneeHtml);
        row.find("div.longDetails dd.issueCreationDate").html(bugRace.creationDateString);
        row.find("div.longDetails dd.issueUpdateDate").html(bugRace.updateDateString);
        row.find("div.longDetails dd.issueDueDate").html(bugRace.dueDateString);
    }
    
    // validate the Add Bug Race form in contest issue tracking page
    function validateBugForm(obj) {
        var flag = 1;
        if ($(obj).parents('.inputContainer').find('.firstPayment').val() != '0') {
            if(!$(obj).parents('.inputContainer').find('.firstPayment').val().match(/^0*$/)){
                $(obj).parents('.inputContainer').find('.firstPayment').val($(obj).parents('.inputContainer').find('.firstPayment').val().replace(/^0*/,''));
            } else {
                if ($(obj).parents('.inputContainer').find('.firstPayment').val()) {
                    $(obj).parents('.inputContainer').find('.firstPayment').val('0');    
                } else {
                    $(obj).parents('.inputContainer').find('.firstPayment').val('');    
                }
            }
        }
        $(obj).parents('.inputContainer').find('label').removeClass('required');
        if (!$(obj).parents('.inputContainer').find('.firstPayment').val().match(/^\d+$/)) {
            flag = 0;
            $(obj).parents('.inputContainer').find('.firstPayment').parent().prev().addClass('required');
        }
        if (!$(obj).parents('.inputContainer').find('.summary').val()) {
            flag = 0;
            $(obj).parents('.inputContainer').find('.summary').parent().prev().addClass('required');
        }
        if (!flag) {
            showErrors("Please input the required fields");
            return false;
        }
        if ($("#rdoNo").is(":checked") || $("#bugForm #projectBug").val() == 'true') {
            var attIds = [];
            for (var i = 0; i < bugrAttachments.length; i++) attIds.push(bugrAttachments[i]['documentId']);
            $("#attachmentIds").val(attIds.join(","));
        } else {
            $("#attachmentIds").val("");
        }
        return true;
    }
    
    // add a new JIRA issue (Bug Race)
    $('#bugRace .btnCreate').click(function() {
        var bugRaceUrl = 'addBugRace';
        if($("#bugForm #projectBug").val() == 'true') {
            bugRaceUrl = 'addProjectBugRace';
        }
        if (validateBugForm(this)) {
            $.ajax({
                type: 'POST',
                url: bugRaceUrl,
                data: $('#bugForm').serialize(),
                dataType: "json",
                cache:false,
                async:true,
                beforeSend: modalPreloader,
                complete: modalClose,
                timeout: 36000000,
                success: function(jsonResult) {
                    handleJsonResult(jsonResult,
                        function(result) {
                            var row = $("#rowItemTemplate>div").clone().addClass("rowItem");
                            fillBugRaceRow(row, result);
                            if($("#bugForm #projectBug").val() == 'true') {
                                row.prependTo("#bugRace .issueSelectionContent .directProjectBugs");
                                $("#bugRace .issueSelectionContent tr").show();
                                $("#bugRace .issueSelectionContent tr:last").addClass("lastTr");
                                $("#bugRace .issueSelectionContent .directProjectBugs .rowItem:last").addClass("lastRowItem");
                            } else {
                                row.appendTo("#bugRace .issueSelectionContent .content");
                            }
                            $('.issueSelectionContent .content').show();
                            $('.issueSelectionContent .inputContainer').hide();
                            var total1 = parseInt($($("#bugRace .total dl dd")[0]).text());
                            var total2 = parseInt($($("#bugRace .total dl dd")[1]).text());
                            total1++;
                            total2++;
                            $($("#bugRace .total dl dd")[0]).text(total1);
                            $($("#bugRace .total dl dd")[1]).text(total2);
                            var options = $("#bugRace .viewSort select option");
                            options[0].text="All Bug Races(" + total1 + ")";
                            options[1].text="Ongoing Bug Races(" + total2 + ")";
                            $("li.bugRaceTab a span").text("Bug Race (" + total1 + ")");
                            bugSortFunction();
                            
                            if ($("#bugRace .viewAll input").is(":checked")) {
                                $('.hideDetails').show();
                                $('.viewDetails').hide();
                                $('.longDetails').show();
                                $('.shortDetails').hide();
                                $('.checkbox').attr('checked', true);
                            }
                            $("#bugRace .viewAll").show();
                            
                            if (result.attachmentError) {
                                // error when add attachment
                            } else {
                                if ($("#rdoNo").is(":checked") || $("#bugForm #projectBug").val() == 'true') {
                                    for (var i = 0; i < bugrAttachments.length; i++)
                                        row.append('<input class="attachmentName" type="hidden" value="' + bugrAttachments[i]['fileName'] + '">');
                                } else {
                                    row.append('<input class="attachmentName" type="hidden" value="Final_Fix_' + ($("#lastClosedFinalFixPhaseId").val()) + '">');
                                }
                            }
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        });
                }
            });
        }
        return false;
    });
    
    // update a JIRA issue (Bug Race)
    $('#bugRace .btnUpdate').live('click', function() {
        if (validateBugForm(this)) {
            // final fix
            if ($("#bugForm #projectBug").val() != 'true' && !$("#rdoNo").is(":checked")) {
                var names = $("#attachmentNames").val().split("\\");
                var fileName = "Final_Fix_" + $("#lastClosedFinalFixPhaseId").val();
                for (var i = 0; i < names.length; i++) {
                    if (names[i].indexOf(fileName) === 0) {
                        showErrors("The final fix already exists in the issue.");
                        return false;
                    }
                }
            }
            
            $.ajax({
                type: 'POST',
                url:'updateBugRace',
                data: $('#bugForm').serialize(),
                dataType: "json",
                cache:false,
                async:true,
                beforeSend: modalPreloader,
                complete: modalClose,
                timeout: 36000000,
                success: function(jsonResult) {
                    handleJsonResult(jsonResult,
                        function(result) {
                            var rowItem = $("input.issueId[value='" + $("#issueId").val() + "']").parents(".rowItem");
                            rowItem.find(".description").val($("#description").val());
                            rowItem.find(".environment").val($("#environment").val());
                            rowItem.find(".contestName").val($("#issueName").val());
                            rowItem.find(".prize").val($("#firstPayment").val());
                            rowItem.find(".tcoPoints").val($("#tcoPoints").val());
                            rowItem.find(".issueContestHead .issueName a").text($("#issueName").val());
                            rowItem.find("dd.issuePrize").html("$" + parseFloat($("#firstPayment").val()).formatMoney(2));
                            rowItem.find(".issueCCA").val($("#cca").is(":checked") ? "true" : "false");
							rowItem.find(".issueType").val($("#bugType").val());
                            
                            $('.issueSelectionContent .content').show();
                            $('.issueSelectionContent .inputContainer').hide();
                            $("#bugRace .viewAll").show();
                            
                            if (result.attachmentError) {
                                // error when add attachment
                            } else {
                                if ($("#rdoNo").is(":checked") || $("#bugForm #projectBug").val() == 'true') {
                                    for (var i = 0; i < bugrAttachments.length; i++)
                                        rowItem.append('<input class="attachmentName" type="hidden" value="' + bugrAttachments[i]['fileName'] + '">');
                                } else  {
                                    rowItem.append('<input class="attachmentName" type="hidden" value="Final_Fix_' + ($("#lastClosedFinalFixPhaseId").val()) + '">');
                                }
                            }
                            return;
                        },
                        function(errorMessage) {
                            showServerError(errorMessage);
                        });
                }
            });
        }
        return false;
    });
    
    $('#bugRace .firstPayment').keypress(function(event) {
        if (event.which != 8 && (event.which < 48 || event.which > 57)){
            return false;
        }
    });

    function bugSortFunction() {
        $('#bugRace .container2Opt .corner').removeClass('evencorner');
        $('#bugRace .issueSelectionContent div.rowItem').removeClass('lastRowItem');
        $('#bugRace .issueSelectionContent div.rowItem').removeClass('oddRowItem');
        $('#bugRace .issueSelectionContent div.rowItem:visible:odd').addClass('oddRowItem');
        $('#bugRace .issueSelectionContent td').each(function() {
            $(this).find('div.rowItem').removeClass('lastRowItem');
            $(this).find('div.rowItem:visible:last').addClass('lastRowItem');
        });
        $('#bugRace .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
        if ($('#bugRace .issueSelectionContent div.rowItem:visible').length % 2 != 0) {
            $('#bugRace .container2Opt .corner').addClass('evencorner');
        }
        $("#bugRace tbody tr").removeClass("lastTr");
        $("#bugRace tbody>tr:visible:last").addClass("lastTr");
    }

    function hideBugRacesEmptyContest() {
        if ($("#bugRace .contestIssuesTd").length > 0) {

            $("#bugRace .contestIssuesTd").each(function() {
                $(this).parent().show();
                if ($(this).find(".rowItem:visible").length == 0) {
                    // hide the row
                    $(this).parent().hide();
                }

            });
        }
    }

    $('#bugRace .select2').change(function() {
        if ($(this).val().match('All Bug Races') != null) {
            $('#bugRace .rowItem').show();
            hideBugRacesEmptyContest();
            bugSortFunction();
        } else if ($(this).val().match('Ongoing Bug Races') != null) {
            $('#bugRace .rowItem').show();
            $('#bugRace .rowItem').each(function() {
                $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().hide();
                $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().hide();
                $(this).find('.longDetails ul').show();
            });
            hideBugRacesEmptyContest();
            bugSortFunction();
        } else {
            $('#bugRace .rowItem').hide();
            $('#bugRace .rowItem').each(function() {
                $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().show();
                $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().show();
            });
            hideBugRacesEmptyContest();
            bugSortFunction();
        }
    });

    if ($('#bugRace .issueSelectionContent div.rowItem').length % 2 != 0) {
        $('#bugRace .container2Opt .corner').addClass('evencorner');
    }


    //tab click
    $('.issueTab').live('click', function() {
        $('#issue').show();
        $('#bugRace').hide();
        $('#issue .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
        $('#bugRace .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
    });

    $('.bugRaceTab').live('click', function() {
        $('#issue').hide();
        $('#bugRace').show();
        $('#issue .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
        $('#bugRace .issueSelectionContent div.rowItem:visible:last').addClass('lastRowItem');
    });

    function getUrlPara(paraName){
		var sUrl = location.href;
		var sReg = "(?:\\?|&){1}"+paraName+"=([^&]*)"
		var re=new RegExp(sReg,"gi");
		re.exec(sUrl);
		return RegExp.$1;
	}

    /**
     * check whether the url has parameter to indicate which sub tab to use.
     */
    if (getUrlPara('subTab') == 'issues') {
        $('#issue').show();
        $('#bugRace').hide();
        $('#issue .select2').get(0).selectedIndex = 1;
        $('#issue .rowItem').show();
        $('#issue .rowItem').each(function() {
            $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().hide();
            $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().hide();
        });
        hideIssuesEmptyContest();
        sortFunction();
    } else if (getUrlPara('subTab') == 'bugRaces') {
        $('#bugRace').show();
        $('#issue').hide();
        $('#bugRace .select2').get(0).selectedIndex = 1;
        $('#bugRace .rowItem').show();
        $('#bugRace .rowItem').each(function() {
            $(this).find('.issueStatus:contains("Resolved")').parent().parent().parent().hide();
            $(this).find('.issueStatus:contains("Closed")').parent().parent().parent().hide();
            $(this).find('.longDetails ul').show();
        });
        hideBugRacesEmptyContest();
        bugSortFunction();
        
        var bugIdx = 0;
        if (isNumber(getUrlPara('bugIdx'))) {
            bugIdx = parseInt(getUrlPara('bugIdx'));
            $("#bugRace .content .rowItem .shortDetails .button11").eq(bugIdx).trigger('click');
        }
    }



    
    if ($.browser.mozilla) {
        // firefox
        $("input.BrowserHidden").attr("size", "8");
    }
    
    if($(".issueSelectionContent #rdoYes").attr('checked')) {
        $('.issueSelectionContent #divUpload').hide();
    } else {
        $('.issueSelectionContent #divUpload').show();
    }
    
	$('.issueSelectionContent #rdoYes').live('click',function(){
	 	if($(this).attr('checked')) {
			$('.issueSelectionContent #divUpload').hide();
		} else {
			$('.issueSelectionContent #divUpload').show();
		}
	});
    $('.issueSelectionContent #rdoNo').live('click',function(){
	 	if($(this).attr('checked')) {
			$('.issueSelectionContent #divUpload').show();
		} else {
			$('.issueSelectionContent #divUpload').hide();
		}
	});
    
    // bug race attachment uploader
    var bugrCurrentAttachment = {};
    var bugrAttachments = [];
    var currentUploadInput;
    var bugrUploader =
        new AjaxUpload(null, {
            action: ctx + '/launch/documentUpload',
            name : 'document',
            responseType : 'json',
            onSubmit : function(file , ext) {
                //software document
                bugrCurrentAttachment['fileName'] = file;

                bugrUploader.setData(
                {studio:false}
                );

                modalPreloader();
            },
            onComplete : function(file, jsonResult){ handleJsonResult(jsonResult,
                    function(result) {
                        var documentId = result.documentId;
                        bugrCurrentAttachment['documentId'] = documentId;
                        bugrAttachments.push(bugrCurrentAttachment);

                        bugrCurrentAttachment = {};

                        currentUploadInput.hide();
                        currentUploadInput.parent().find(".FileField").unbind("click");
                        
                        modalClose();
                    },
                    function(errorMessage) {
                        showErrors(errorMessage);
                        var txtfile = bugrUploader.curRow.find(".FileField");
                        txtfile.val("");
                        var ind = txtfile.attr("id").substr(7);
                        txtfile.before('<input id="file' + ind + '" class="BrowserHidden" type="file" name="document" size="24" onchange="getElementById(\'txtfile' + ind + '\').value = getElementById(\'file' + ind + '\').value;">');
                        $('#file' + ind).change(function() {
                            txtfile.val($(this).val());
                        });
                        if ($.browser.mozilla) {
                            // firefox
                            $("input.BrowserHidden").attr("size", "8");
                        }
                        modalClose();
                    });
            }
        }, false); 

    $(".issueSelectionContent .btnUpload").click(function() {
        bugrUploader.setInput($(this).parent().find("input[type=file]").get(0));
        bugrUploader.curRow = $(this).parent();
        
        var fileName = bugrUploader._input.value;

        var errors = [];

        if (!checkRequired($(this).parent().find("input.FileField").val()) || !checkRequired(fileName)) {
            errors.push('No file is selected.');
        }
        
        var startIndex = (fileName.indexOf('\\') >= 0 ? fileName.lastIndexOf('\\') : fileName.lastIndexOf('/'));
        if (startIndex != -1) {
            fileName = fileName.substring(startIndex + 1);
        }
        
        var ok = true;
        for (var i = 0; i < bugrAttachments.length; i++) {
            if (fileName.toLowerCase() == bugrAttachments[i]['fileName'].toLowerCase()) {
                ok = false;
                errors.push('The file name already exists.');
            }
        }
        if (ok) {
            var names = $("#attachmentNames").val().split("\\");
            for (var i = 0; i < names.length; i++) {
                if (fileName.length > 0 && fileName.toLowerCase() == names[i].toLowerCase()) {
                    errors.push('The file name already exists.');
                }
            }
        }

        if(errors.length > 0) {
            showErrors(errors);
            return false;
        }
        
        currentUploadInput = $(this);

        bugrUploader.submit();
        return false;
    });
    
    /* added js code */
	
    /**
     * Auto truncate columns.

     */
    function  truncateTH(){
        var width = $(window).width();
        if(width < 1200){
             $(".myCopilotsContestsList .truncateRegs, .myProjectContests .truncateRegs").text("Regs");
             $(".myCopilotsContestsList .truncateSubs, .myProjectContests .truncateSubs").text("Subs");
        } else{
             $(".myCopilotsContestsList .truncateRegs, .myProjectContests .truncateRegs").text("Registrants");
             $(".myCopilotsContestsList .truncateSubs, .myProjectContests .truncateSubs").text("Submissions");
        } 
    }

    function  truncateTableHeaderNames(){
        var width = $(window).width();
        // 1240 is better than 1024 when testing
        if(width < 1240){
             $(".resultTable th.truncateRegs").text("Regs");
             $(".resultTable th.truncateSubs").text("Subs");
        } else{
             $(".resultTable th.truncateRegs").text("Registrants");
             $(".resultTable th.truncateSubs").text("Submissions");
        }
    }


    /**
     * Aligns the copilots list in the project overview page.
     */
    function alignProjectCopilots() {
        var width = $(window).width();

        // align to center when the browser width is less than 1400
        if (width < 1300) {
            $(".projectCopilotsList").addClass("copilotsAlignCenter");
        } else {
            $(".projectCopilotsList").removeClass("copilotsAlignCenter");
        }
    }


    $(window).resize(function(){
          truncateTH();
	      truncateTableHeaderNames();
          alignProjectCopilots();
    }) ;

    truncateTH();
    truncateTableHeaderNames();

    $(".darkenBtn,#helpCenterWidget h6").css("text-shadow", "0 -1px 1px #221d1a");

    /*help center widget tab function*/

    $("#helpCenterWidget .tabList li a.tab").click(function() {
        $("#helpCenterWidget .tabContent").hide();
        $(this).addClass("actived");
        $(this).parent("li").siblings("li").children("a.tab").removeClass("actived");
        switch ($(this).attr("id")) {
            case "FAQTab":
                $("#FAQTabContent").show();
                break;
            case "videoTab":
                $("#videoTabContent").show();
                break;
            case "tutorialTab":
                $("#tutorialTabContent").show();
                break;
            case "exampleTab":
                $("#exampleTabContent").show();
                break;
            case "moreTab":
                $(".tab").hide();
                $(".tabMore,#exampleTab").css("display", "inline-block");
                $("#exampleTabContent").show();
                $("#exampleTab").addClass("actived");
                break;
            default:
                break;
        }
    });
    $("#helpCenterWidget .tabList li a#lessTab").click(function() {
        $("#moreTab").removeClass("actived");
        $(".tab").show();
        $("#exampleTabContent").hide();
        $("#exampleTab").removeClass("actived");
        $(".tabMore,#exampleTab").css("display", "none");
        $("#FAQTabContent").show();
        $("#FAQTab").addClass("actived");

    });

    // FF 3
	if(ua.match(/firefox\/([\d.]+)/)!=null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0]>2){
		$("#helpCenterWidget ul.tabList a#moreTab,#helpCenterWidget ul.tabList a#lessTab").css("padding","0 14px");
		$(".dashboardPage #helpCenterWidget .tabContent").css({"position":"relative","top":"-5px"});
		$(".dashboardPage #helpCenterWidget ul.tabList a#moreTab,.dashboardPage #helpCenterWidget ul.tabList a#lessTab").css("padding","0 12px");
	}
	// FF 4
	if(ua.match(/firefox\/([\d.]+)/)!=null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0]>3){
		$("#helpCenterWidget ul.tabList a#moreTab,#helpCenterWidget ul.tabList a#lessTab").css("padding","0 14px");
		$(".dashboardPage #helpCenterWidget .tabContent").css({"position":"relative","top":"-6px"});
		$(".dashboardPage #helpCenterWidget ul.tabList a#moreTab,.dashboardPage #helpCenterWidget ul.tabList a#lessTab").css("padding","0 13px");
	}

    // IE 7
    if ($.browser.msie && $.browser.version == 7.0) {
        $(".contestsContent").css("overflow-x", "hidden");
    }

    /*select a project function*/
    $(".selectProject").change(function() {
        if ($(this).val().length != 0) {
            window.location = $(this).val();
        }
    });

    $(".selectProject option:even").css("background", "#f3f3f3");
    
	/* new added for contest dashboard */
    // BUGR-4901 - Resize timeline relative the amount of phases.
	var progressContainerWidth = $('.progressContainer').width();

	// 771 = max size of the progressContainer to no break in min-width
	// phaseNamePadding = space free / amount phases / 2 (2 sides)
    var minWidth = parseInt($('.timelineContainer').css('min-width')) - 1;
	var phaseNamePadding = Math.floor((minWidth - progressContainerWidth) / $('.progressContainer li').size() / 2);
	
	// set paddings
	$('.dashboardModule .content .progressContainer li .phaseName').css('padding-left', phaseNamePadding + 'px');
	$('.dashboardModule .content .progressContainer li .phaseName').css('padding-right', phaseNamePadding + 'px');
	
	var firstPhaseWidth = $(".dashboardModule .content .progressContainer li:first").width();
	if(firstPhaseWidth < 120)
		$(".dashboardModule .content .progressContainer li:first .phaseDate p").css({"position":"relative","left":Math.floor((120-firstPhaseWidth)/2)+"px"});

	var lastPhaseWidth = $(".dashboardModule .content .progressContainer li:last").width();
	if(lastPhaseWidth < 120)
		$(".dashboardModule .content .progressContainer li:last .phaseDate p").css({"position":"relative","right":Math.floor((120-lastPhaseWidth)/2)+"px"});


	// IE progressStatus fix
	if($.browser.msie) {
		$('.dashboardModule .content .progressContainer li .progressStatus').each(function() {
			$(this).width($(this).parent().width());
		});
		$('.dashboardModule .content .progressContainer li:first .progressStatus').width($('.dashboardModule .content .progressContainer li:first').width()-7);
		$('.dashboardModule .content .progressContainer li:last .progressStatus').width($('.dashboardModule .content .progressContainer li:last').width()-7);
	}

	$(".statusP .helpBtn").hover(
		function(){
			if($(this).parent(".statusP").hasClass("lessThanIdeal")){
				$(this).parents(".dashboardModule").find(".tooltipContainer").removeClass("tooltipForum").addClass("tooltipLessThanIdeal");
			}
			else if($(this).parent(".statusP").hasClass("healthy")){
				$(this).parents(".dashboardModule").find(".tooltipContainer").removeClass("tooltipLessThanIdeal").addClass("tooltipForum");
			}
			else{
				$(this).parents(".dashboardModule").find(".tooltipContainer").removeClass("tooltipLessThanIdeal").removeClass("tooltipForum");
				
			}
			$(this).parents(".dashboardModule").find(".tooltipContainer").css("display", "block");
		}
		,
		function(){
			$(this).siblings(".tooltipContainer").hide();
		}
	);
	
	$(".appositeContainer .registrationModule .tooltipContainer .closeIco").click(function(){
		$(this).parents(".tooltipContainer").css("display", "none");
	});
	
	var Sys = {}; 
	var ua = navigator.userAgent.toLowerCase(); 
	/*
	if(ua.match(/chrome\/([\d.]+)/)!=null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0]>2){ 
		$(".appositeContainer .issueModule").css({"width":"17.2%"});
		$(".appositeContainer.studio .issueModule").css("width","24.7%");
	} 
	if(ua.match(/version\/([\d.]+).*safari/)!=null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){ 
		$(".appositeContainer .issueModule").css("width","17.4%");
		$(".appositeContainer.studio .issueModule").css("width","24.7%");
	} 
	*/
    var currentPhase = null;

	function adjust(){

        var timelineTip = $("#timeLineTip");
        var isTimelineTipShow = timelineTip.is(":visible");

		if($(".dashboardModule .content .timelineContainer").parents(".dashboardModule").width() > 830){
			$(".dashboardModule .content .timelineContainer").css("padding", "41px 29px 39px");
			$(".dashboardModule .content .timelineContainer .startDate").css("left", "30px");
			$(".dashboardModule .content .timelineContainer .endDate").css("right", "31px");
		}
		else{
			$(".dashboardModule .content .timelineContainer").css("padding", "41px 0 39px");
			$(".dashboardModule .content .timelineContainer .startDate").css("left", "2px");
			$(".dashboardModule .content .timelineContainer .endDate").css("right", "3px");
		}
		if($(window).width() > 1024){
			$(".dashboardModule .content .timelineContainer").css("padding-bottom", "48px");
			$(".dashboardModule .content .timelineContainer.studio").css("padding-bottom", "46px");
            $(".dashboardModule .content .timelineContainer.copilot").css("padding-bottom", "39px");
			/*
			if(ua.match(/chrome\/([\d.]+)/)!=null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0]>2){
				$(".appositeContainer .issueModule").css({"width":"17.2%"}); 
				$(".appositeContainer.studio .issueModule").css("width","24.5%"); 
			} 
			if(ua.match(/version\/([\d.]+).*safari/)!=null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){ 
				$(".appositeContainer .issueModule").css("width","17.3%");
				$(".appositeContainer.studio .issueModule").css("width","24.5%");
			} 
			*/

		}
		else{
			$(".dashboardModule .content .timelineContainer").css("padding-bottom", "39px");
			$(".dashboardModule .content .timelineContainer.studio").css("padding-bottom", "42px");
            $(".dashboardModule .content .timelineContainer.copilot").css("padding-bottom", "39px");
			/*
			if(ua.match(/chrome\/([\d.]+)/)!=null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0]>2){  
				$(".appositeContainer .issueModule").css("width","17.4%");
				$(".appositeContainer.studio .issueModule").css("width","24.8%");
			} 
			if(ua.match(/version\/([\d.]+).*safari/)!=null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0]>3){ 
				$(".appositeContainer .issueModule").css("width","17.4%");
				$(".appositeContainer.studio .issueModule").css("width","24.8%");
			} 
			// IE 7
			if($.browser.msie && $.browser.version == 7.0){  
				$(".appositeContainer .issueModule").css("width","17.1%");
				$(".appositeContainer.studio .issueModule").css("width","24.3%");
			} 
			*/
		}

        if (isTimelineTipShow) {
            setTimeout(function () {
               // timeLineHover(currentPhase);
            }, 200);
        }
	}
	adjust();
	$(window).resize(function(){
          adjust();
    });
	// IE 8 
	if($.browser.msie && $.browser.version == 8.0){ 
		$(".appositeContainer .registrationModule .statusP .helpBtn").css("top","0");
	} 
	/* end */

    if ($(".progressContainer").length > 0) {
		window.phaseLens = [];
		$(".progressContainer li").each(function() {
	        phaseLens.push($(this).width());
	    });
	    window.progressWidth = $(".progressContainer").width();
		var updateTimeline = function() {
	        var leftWidth = $(".timelineContainer").width() - 3 - progressWidth;
	        var perWidth = parseInt(leftWidth / phaseLens.length);
	        var clWidth = leftWidth - perWidth * phaseLens.length;
	        var index = 0;
	        $(".progressContainer li").each(function() {
	            var wid = phaseLens[index] + perWidth;
	            if (index == phaseLens.length - 1) wid += clWidth;
	            $(this).width(wid);
	            index++;
	        });
	        if($.browser.msie) {
	            $('.dashboardModule .content .progressContainer li .progressStatus').each(function() {
	                $(this).width($(this).parent().width());
	            });
	            $('.dashboardModule .content .progressContainer li:first .progressStatus').width($('.dashboardModule .content .progressContainer li:first').width()-7);
	            $('.dashboardModule .content .progressContainer li:last .progressStatus').width($('.dashboardModule .content .progressContainer li:last').width()-7);
	        }
	    }
	    updateTimeline();
	    $(window).resize(function() {
	        window.setTimeout(updateTimeline, 50);
	    });

        var timeLineHover = function (object) {
            var pos = object.offset();
            var obj = $("#timeLineTip");
            var t = pos.top - obj.height() - 5;
            obj.css("top", t).css("left", pos.left + object.outerWidth() / 2 - obj.width() / 2);
            var arrow = $("#timeLineTip .arrow");
            arrow.css("top", obj.height() - 1);
            arrow.css("left", obj.width() / 2 - 2);

            var startTime = object.parent().find(".tooltipStartTime").text();
            var endTime = object.parent().find(".tooltipEndTime").text();


            $("#timeLineTip .tooltipCaptionInner h2").html(object.text());
            $("#timeLineTip  .tooltipStartTime").text(startTime);
            $("#timeLineTip  .tooltipEndTime").text(endTime);

            var li = object.parent();
            if ($(li).attr("class") != "") {
                obj.removeClass().addClass("tooltipContainer tooltipForum " + $(li).attr("class"));
            } else {
                obj.removeClass().addClass("tooltipContainer tooltipForum");
            }
            obj.show();
            currentPhase = object;
        }

        var timeLineHoverConfig = {
            sensitivity:3, // number = sensitivity threshold (must be 1 or higher)
            interval:300, // number = milliseconds for onMouseOver polling interval
            timeout:300, // number = milliseconds delay before onMouseOut
            over:function () {
                timeLineHover($(this));
            },
            out:function () {
               // do nothing
               $("#timeLineTip").hide();
            }
        }

        $.fn.hoverIntent && $(".progressContainer li .phaseName").hoverIntent(timeLineHoverConfig);
    }

    $(" .contestViews  .contestCView .loading").css("opacity", "0.8");

    var loadContestsCalendarView = function () {
        var views = $(".contestViews");
        views.find(".contestCView").show();
        views.find(".calendarLegends").show();
        var directProjectId = $.trim($("#currentDirectProjectID").html());
        var formData = {};
        formData.projectId = directProjectId;
        var request = {formData:formData};


            if ($.browser.msie && $.browser.version == 7.0) {
                $(".contestViews .calendar .fc-header").css("margin-left", "0");
            }

            var loaded = false;

            var fc = views.find(".calendar");
            if (!fc.hasClass("fc")) {
                fc.fullCalendar({
                    header: {
                        left: 'prev',
                        center: 'title',
                        right: 'next'
                    },
                    dayNamesShort: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
                    eventClick : function(calEvent, jsEvent, view) {
                        window.open(calEvent.url, "_blank");
                        return false;
                    },
                    viewDisplay : function(view) {

                        // the json data
                        var calendarJsonData;

                        if (!loaded) {

                            // show ajax loading
                            views.find(".loading").show();

                            // get data via ajax
                            $.ajax({
                                type: 'POST',
                                url:'projectContestsCalendarView',
                                data: request,
                                dataType: "json",
                                cache:false,
                                async:true,
                                success: function(jsonResult) {
                                    handleJsonResult(jsonResult,
                                        function(result) {

                                            // set the result
                                            calendarJsonData = result;

                                            // hide the loading
                                            views.find(".loading").hide();

                                            $.each(calendarJsonData.events, function(index, item) {
                                                fc.fullCalendar("renderEvent", item, true);
                                            })


                                            /* fix the bug in IE7 */
                                            if ($.browser.msie && $.browser.version == 7.0) {
                                                $(".contestViews .calendar .fc-header").css("margin-left", "-4px");
                                            }

                                            loaded = true;
                                        },
                                        function(errorMessage) {

                                            // hide loading and show error
                                            views.find(".loading").hide();
                                            showServerError(errorMessage);
                                        })
                                }
                            });

                        }

                },
                eventRender:function (event, element) {
                    switch (event["status"]) {
                        case 'completed':
                            element.addClass("fc-completed");
                            break;
                        case 'cancelled':
                            element.addClass("fc-cancelled");
                            break;
                        case 'active':
                            element.addClass("fc-active");
                            break;
                        case 'draft':
                            element.addClass("fc-draft");
                            break;
                    }
                },
                editable:false
            });
        }
    };

    if($("#contestsCalendarView").length > 0) {
        loadContestsCalendarView();
    }


    $('.multiSelectArea .multiSelectBox').css('overflow-x','hidden');

    	//expand function
    	$('.filterTitle .expanded').click(function(){
    		var filterTitle = $(this).closest('.filterTitle');
    		if(!$(this).hasClass('collapsed')){
    			filterTitle.addClass('filterTitleCollapsed');
    			$(this).addClass('collapsed');
    			$('.filterContainer').hide();
    		}else{
    			filterTitle.removeClass('filterTitleCollapsed');
    			$(this).removeClass('collapsed');
    			$('.filterContainer').show();
    		}
    	});

    	//Multi Select checkbox function
    	$('.multiSelectBox :checkbox').live('click', function(){
    			var parentBox = $(this).closest('.multiSelectBox');
    			var parentRow = $(this).parent('.multiOptionRow');
    			var parentSelectLen = parentBox.find(':checkbox').length;

    			if($(this).attr('checked')){
    				if($(this).siblings('label').text() == 'Select All'){
    					parentBox.find(':checkbox').attr('checked',true);
    					parentBox.find('.multiOptionRow').addClass('multiOptionRowChecked');
    				}else{
    					parentRow.addClass('multiOptionRowChecked');
    					if(parentBox.find(':checked').length+1 == parentSelectLen){
    						parentBox.find(':checkbox:first').attr('checked',true);
    						parentBox.find('.multiOptionRow:first').addClass('multiOptionRowChecked');
    					}
    				}
    			}else{
    				if($(this).siblings('label').text() == 'Select All'){
    					parentBox.find(':checkbox').attr('checked',false);
    					parentBox.find('.multiOptionRow').removeClass('multiOptionRowChecked');
    				}else{
    					parentBox.find(':checkbox:first').attr('checked',false);
    					parentBox.find('.multiOptionRow:first').removeClass('multiOptionRowChecked');
    					parentRow.removeClass('multiOptionRowChecked');
    				}
    			}
    	});

        var selectAllCheckOrNot = function() {
            $(".multiSelectBox").each(function() {
                var allChecked = true;
                $(this).find(".optionItem").each(function(){
                    if(!$(this).attr('checked')) {
                        allChecked = false;
                    }
                });

                var selectAll = $(this).find('.optionAll');

                if(!allChecked) {
                    selectAll.attr('checked', false);
                    selectAll.parent().removeClass('multiOptionRowChecked');
                } else {
                    selectAll.attr('checked', true);
                    selectAll.parent().addClass('multiOptionRowChecked');
                }

            })
        }

        selectAllCheckOrNot();

        adjustReportFilterHeight();
        multiSelectAreaSet();

    	//resize Multi Select Area width
    	$(window).resize(function(){
    		if($('.filterContainer').length>0){
    			multiSelectAreaSet();
    		}
            adjustReportFilterHeight();
        }) ;



        if($(".editIcon").length > 0 && $('.editIcon').tctip )  {
            $('.editIcon').tctip({
                title: "Edit / Update Project Details",
                content: "Go to the edit project page to edit and update the project details and settings"
            });
        }

    $('#radioApproveReject').click(function (e) {
        $('.approvalRejectSection').show();
    });
    
    $('#radioApproveApproved').click(function (e) {
        $('.approvalRejectSection').hide();
    });
    
    $('textarea.hint').focus(function () {
        if ($(this).hasClass('hint')) {
            $(this).val('');
        }
    });
    
    $('textarea.hint').blur(function (e) {
        if (!$(this).val()) {
            if (!$(this).hasClass('hint')) {
                $(this).addClass('hint');
            }
            $(this).val($(this).attr('name'));
        }
        else if ($(this).val() != $(this).attr('name')) {
            $(this).removeClass('hint');
        }
    });

    $('#submitApprovalButton').click(function() {
        var d = $(this).attr('rel').split('_');;
        var projectId = parseInt(d[1]); 
        var submissionId = parseInt(d[0]); 
        var rejectionReason = $('.rejectTextarea').val();
        var approved = $('#radioApproveApproved:checked').length > 0;
        if (!approved && $.trim(rejectionReason) == '') {
            showErrors('Reason for rejection is required');
        } else {
            $.ajax({
                       type:'POST',
                       url:ctx + "/contest/submitApproval",
                       data:{approved:approved, rejectionReason:rejectionReason, projectId:projectId, submissionId:submissionId},
                       cache:false,
                       dataType:'json',
                       beforeSend:modalPreloader,
                       complete:modalClose,
                       success:function (jsonResult) {
                           handleJsonResult(jsonResult,
                                            function (result) {
                                                $('.ApprovalSection').html('<p>The submission has been ' +
                                                                           (approved ? 'approved' : 'rejected') +
                                                                           '.</p>');
                                            },
                                            function (errorMessage) {
                                                showErrors(errorMessage);
                                            });
                       }
                   });
        }
    });
});


var calendarData;
var isEnterpriseCalendarShown;

function loadEnterpriseCalendar(customerId, projectFilterId, projectFilterValue, showModal) {
    var formData = {};

    formData.customerId = customerId;

    if (projectFilterId != null) {
        formData.projectFilterId = projectFilterId;
    }

    if (projectFilterValue != null) {
        formData.projectFilterValue = projectFilterValue;
    }

    if(showModal) {
        // modalPreloader();
        $(".roadmapViewArea").css('min-height', '600px');
        $(".roadmapViewArea .loading").css("opacity", "0.6");
        $(".roadmapViewArea .loading").show();
    }
    
    isEnterpriseCalendarShown = false;


    $.ajax({
        type:'POST',
        url:'dashboardMilestoneCalendar',
        data:{formData:formData},
        dataType:"html",
        cache:false,
        success:function (result) {

            if (result.indexOf("calendarData") != -1) {
                $(result).find("#allResponsiblePerson").insertAfter(".enterpriseCalendar .milestoneCalView");
                calendarData = jQuery.parseJSON($(result).find("#calendarData").html());

                if (calendarData != null) {
                        $('.milestoneCalView').empty();

                        // build the handle map
                        var userHandleColorMap = {};

                        $("#allResponsiblePerson a").each(function () {
                            var handle = $.trim($(this).text());
                            var color = $(this).attr('class');
                            var url = $(this).attr('href');

                            userHandleColorMap[handle] = {color:color, url:url};
                        });

                        // update handle color and link first
                        $.each(calendarData.events, function (index, item) {
                            if (item.person && item.person.name) {
                                if (userHandleColorMap[item.person.name] != null) {
                                    item.person.color = userHandleColorMap[item.person.name].color;
                                    item.person.url = userHandleColorMap[item.person.name].url;
                                }
                            }
                        });

                        $('.milestoneCalView').fullCalendar({
                            header:{
                                left:'prev',
                                center:'title',
                                right:'next'
                            },
                            editable:false,
                            dayNamesShort:['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                            //events:calendarData.events,
                            eventRender:function (event, element) {
                                var inner = element.find(".fc-event-inner");

                                inner.click(function(e){
                                    e.preventDefault();
                                    window.open('projectMilestoneView?formData.viewType=list&formData.projectId=' + event.projectId, '');
                                });

                                var milestoneNameClass = "upcoming";

                                switch (event["status"]) {
                                    case 'completed':
                                        element.addClass("fc-milestone-completed");
                                        if (event.person) {
                                            $("<a/>", {
                                                "text":event.person.name,
                                                "href":event.person.url,
                                                "class":event.person.color
                                            }).appendTo(inner);
                                        }
                                        milestoneNameClass = "completed";
                                        break;
                                    case 'upcoming':
                                        element.addClass("fc-milestone-upcoming");
                                        if (event.person) {
                                            $("<a/>", {
                                                "text":event.person.name,
                                                "href":event.person.url,
                                                "class":event.person.color
                                            }).appendTo(inner);
                                        }
                                        milestoneNameClass = "upcoming";
                                        break;
                                    case 'overdue':
                                        element.addClass("fc-milestone-overdue");
                                        if (event.person) {
                                            $("<a/>", {
                                                "text":event.person.name,
                                                "href":event.person.url,
                                                "class":event.person.color
                                            }).appendTo(inner);
                                        }
                                        milestoneNameClass = "overdue";
                                        break;
                                }

                                var name = $("<span class='milestoneName'></span>").addClass(milestoneNameClass).text(event.title);
                                var project = $("<span></span>").text(event.projectName);

                                inner.find(".fc-event-title").empty().append(name).append(project);

                                var tcTip = $("<div/>", {
                                    "class":"milestoneTips"
                                });
                                $("<div/>", {
                                    "class":"triangle"
                                }).appendTo(tcTip);
                                $("<h2/>", {
                                    "text":event.title,
                                    "class":"tipsTitle"
                                }).appendTo(tcTip);
                                $("<h2/>", {
                                    "text":event.projectName,
                                    "class":"tipsTitle"
                                }).appendTo(tcTip);
                                $("<p/>", {
                                    "text":event.description
                                }).appendTo(tcTip);
                                tcTip.appendTo(inner.parent());

                                inner.find(".fc-event-title").hover(function () {
                                    tcTip.css("top", $(this).height() + 5 + "px");
                                    inner.parent().css("z-index", 9);
                                    tcTip.show();
                                }, function () {
                                    tcTip.hide();
                                    inner.parent().css("z-index", 8);
                                });
                            },
                            eventAfterRender:function (event, element) {
                                if (event["status"] == "overdue") {
                                    $('.milestoneCalView .fc-content tbody td:eq(' + element.data("tdIndex") + ")").addClass("fc-overdue");
                                }
                            },
                            viewDisplay:function () {
                                $(".milestoneCalView .fc-today .fc-day-number").html("TODAY");
                                // this will be replaced with the ajax call in Assembly
                                if (!isEnterpriseCalendarShown) {
                                	isEnterpriseCalendarShown = true;
                                	
	                                $.each(calendarData.events, function (index, item) {
	                                    $('.milestoneCalView').fullCalendar("renderEvent", item, true);
	                                })
	                              }
                            }
                        });
                    }

                if(showModal) {
                    // modalAllClose();
                    $(".roadmapViewArea .loading").hide();
                }
            } else {
                showErrors("Failed to load the milestone data");
            }
        }
    });


}

/**
 * Adjusts the height of report filter controls according to the browser width.
 */
function adjustReportFilterHeight() {
    var height = $('.filterContainer .leftFilterContent').height();
    $('.filterContainer .multiSelectArea .multiSelectBox').css({'height':(height - 40) + 'px'});
    $('.filterContainer #groupFilter .multiSelectArea .multiSelectBox').css({'height':(height - 110) + 'px'});
    $('.filterContainer .statusMultiSelect .multiSelectBox').css({'height':(height / 2 - 55) + 'px'});
    $('#billingCostReportsPage .filterContainer #groupFilter .multiSelectArea .multiSelectBox').css({'height':(height / 2 - 76) + 'px'});
	$('#billingCostReportsPage .filterContainer #invoiceStatusFilter .multiSelectArea .multiSelectBox').css({'height':(height / 2 - 120) + 'px'});
    $('#billingCostReportsPage .filterContainer1400 .multiSelectArea .multiSelectBox').css({'height':(height + 85) + 'px'});
    $('#billingCostReportsPage .filterContainer1400 #groupFilter .multiSelectArea .multiSelectBox').css({'height':(height - 90) + 'px'});
    $('#billingCostReportsPage .filterContainer1400 #invoiceStatusFilter .multiSelectArea .multiSelectBox').css({'height':(height - 203) + 'px'});
	$('.filterContainer1400 .statusMultiSelect .multiSelectBox').css({'height':(height - 90) + 'px'});
    $('#pipelineReportsPage .multiSelectArea .multiSelectBox').css({'height':(height - 37) + 'px'});
    $('#pipelineReportsPage #groupFilter .multiSelectArea .multiSelectBox').css({'height':(height - 93) + 'px'});
	
}


//Multi Select Area width
function multiSelectAreaSet(){
    var bestWidth = 1380;

    if ($("#costReportsPage").length > 0) {
        bestWidth = 1340;
    }

    var width = $(window).width();
    if($('.filterContainer').length>0){
        if(width < bestWidth){
            $('.filterContainer').removeClass('filterContainer1400');
        }else{
            $('.filterContainer').addClass('filterContainer1400');
        }
        $('.rightFilterContent').width($('.filterContainer').width()-$('.leftFilterContent').width() - 10);
    }
}

/*
 * BHCUI-83
 * Check whether the project name is empty.
 */
function checkProjectName(){
	var projectName = $('#projectName').val();
    projectName = jQuery.trim(projectName);

    var errors = [];

    if (!checkRequired(projectName)) {
        errors.push('project name is empty.');
    }

	if (projectName.length > 60){
		errors.push('project name cannot be longer than 60 chars.');
	}
    if (errors.length > 0) {
		initDialog('errorDialog', 500);
        showErrors(errors);
        return false;
    }
	return true;
}

$('#createProjectViewSave').live('click', function(){
	if (checkProjectName()){
		$('#createProjectAndContest').submit();
	}
});

$('#addExistContest').live('click', function(){
	if (checkProjectName()){
		$('#CreateProjectForm').submit();
	}
});



function exportContestRegistrantsToExcel() {
    $('#formDataExcel').val("true");
    document.ContestRegistrantsForm.submit();
}

function setCopilotSelection(sid, pid, place, prid, handle, projectName) {
    if (place == 1) {
        $(".chooseConfirmationButton").removeClass("selectRunnerUpCopilot").addClass("selectWinnerCopilot");
        $('.selectWinnerCopilot').data('sid', sid);
        $('.selectWinnerCopilot').data('pid', pid);
        $('.selectWinnerCopilot').data('prid', prid);
    } else {

        $(".chooseConfirmationButton").removeClass("selectWinnerCopilot").addClass("selectRunnerUpCopilot");
        $('.selectRunnerUpCopilot').data('sid', sid);
        $('.selectRunnerUpCopilot').data('pid', pid);
        $('.selectRunnerUpCopilot').data('prid', prid);
    }
    if (place == 1) {
        $('.selectWinnerCopilot').data('place', 1);
        $("#removeProjectDialog .header .title").text("Choose Copilot Confirmation");
        $("#removeProjectDialog .body").html("Choose <strong id='firstPlaceCopilot'></strong> as copilot of project <strong id='projectNameLabel'></strong> ?");
        
        $('#firstPlaceCopilot').html(handle);
        $('#projectNameLabel').html(projectName);
    } else if (place == 2) {
        $('.selectRunnerUpCopilot').data('place', 2);
        $("#removeProjectDialog .header .title").text("Choose second place Confirmation");
        $("#removeProjectDialog .body").html('Choose <strong id="secondPlaceCopilot"></strong> as second place of this copilot posting ?');
        $('#secondPlaceCopilot').html(handle);
    } else if (place == 3) {
        $('.selectRunnerUpCopilot').data('place', 3);
        $("#removeProjectDialog .header .title").text("Do not select 2nd place");
        $("#removeProjectDialog .body").html("Are you sure you don't want to select 2nd place?");
    } else {
        $('.selectRunnerUpCopilot').data('place', 100);
        $("#removeProjectDialog .header .title").text("Do not select any copilot");
        $("#removeProjectDialog .body").html("Are you sure you don't want to any copilot?");
    }
    
    // move click bind logic here
    // from BUGR-7279
    $('.selectRunnerUpCopilot').unbind('click').bind('click', function() {
        var data = $(this).data('rel');
        $('#scpPlacement').val($(this).data('place'));
        $('#scpSubmissionId').val($(this).data('sid'));
        $('#scpProfileId').val($(this).data('pid'));
        $('#scpTcdProjectId').val($(this).data('prid'));
        //console.log($('#SelectCopilotForm').serialize());
        // $('#SelectCopilotForm').submit();
        return false;
    });
        
    $('.selectWinnerCopilot').unbind('click').bind('click', function() {
        var data = $(this).data('rel');
        $('#scpPlacement').val('1');
        $('#scpSubmissionId').val($(this).data('sid'));
        $('#scpProfileId').val($(this).data('pid'));
        $('#scpTcdProjectId').val($(this).data('prid'));
        //console.log($('#SelectCopilotForm').serialize());
        // $('#SelectCopilotForm').submit();
        return false;
    });

    $('#removeProjectDialog').dialog("open");
    return false;
}

/**
 * Show the indicator of report filter dropdown.
 *
 * @param object the jquery object represents the dropdown.
 */
function showIndicator(object) {
    object.attr('disabled', true);
    var indicator = object.parent().find('.indicator');
    indicator.show();
}

/**
 * Hide the indicator of report filter dropdown.
 *
 * @param object the jquery object represents the dropdown.
 */
function hideIndicator(object) {
    var indicator = object.parent().find('.indicator');
    indicator.hide();
    object.attr('disabled', false);
}


function loadGroupByOptions(selector, customerId, successCallback) {

    showIndicator(selector);

    $.ajax({
        type:'GET',
        url:ctx + "/getGroupByOptionsForCustomer",
        data:{customerId:customerId},
        cache:false,
        dataType:'json',
        success:function (jsonResult) {
            handleJsonResult(jsonResult,
                    function (result) {
                        selector.empty();

                        selector.append($("<option></option>").attr('value', -1).text("No Grouping"));

                        $.each(result, function (index, value) {
                            selector.append($("<option></option>").attr('value', value.id).text(value.name));
                        });

                        hideIndicator(selector);

                        successCallback(result);

                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
        }
    });
}

function loadGroupValuesForGroup(selector, groupId, successCallback) {
    showIndicator(selector);
    selector.empty();
    selector.parent().find(".reportWarningMessage").hide();
    $.ajax({
        type:'GET',
        url:ctx + "/getGroupValuesForGroupBy",
        data:{groupKeyId:groupId},
        cache:false,
        dataType:'json',
        success:function (jsonResult) {
            handleJsonResult(jsonResult,
                    function (result) {
                        var hasValue = false;
                        var count = 0;

                        var selectAll = $("<div></div>").attr('class', 'multiOptionRow multiOptionRowChecked hide');
                        selectAll.append($("<input class='optionAll' type='checkbox' checked='checked'>")
                                .attr('id', 'groupValuesSelectAll'));
                        selectAll.append($("<label></label>").attr('title', "Select All").attr('for', 'groupValuesSelectAll').text("Select All"));
                        selector.append(selectAll);

                        $.each(result, function (index, value) {
                            var item = $("<div></div>").attr('class', 'multiOptionRow multiOptionRowChecked');
                            item.append($("<input class='optionItem' type='checkbox' name='formData.groupValues' checked='checked'>")
                                    .attr('id', 'groupValuesCheckBox' + index).val(value));
                            item.append($("<label></label>").attr('title', value).attr('for', 'groupValuesCheckBox' + index).text(value));
                            selector.append(item);
                            hasValue = true;
                            count = index;
                        });

                        var noneItem = $("<div></div>").attr('class', 'multiOptionRow multiOptionRowChecked');
                        noneItem.append($("<input class='optionItem' type='checkbox' name='formData.groupValues' checked='checked'>")
                                .attr('id', 'groupValuesCheckBox' + (count+1)).val("None"));
                        noneItem.append($("<label></label>").attr('title', "None").attr('for', 'groupValuesCheckBox' + (count+1)).text("None"));

                        selector.append(noneItem);

//                        if(!hasValue) {
//                            selector.parent().find(".reportWarningMessage").show();
//                        }

                        hideIndicator(selector);

                        successCallback(result);

                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                    });
        }
    });
}

/* new code for Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1 - add ajax preloader functions */
var intPreloaderTimmer = 2000;	//timer
var strTip = "Loading...";		//string for preloader
var objPreloaderTimmer;			//timer for modal
var floatOverlayOpacity = 0.6;	//opacity for modal Background

(function($) {

	/* position modal */
	modalPosition = function(){
		var wWidth  = window.innerWidth;
		var wHeight = window.innerHeight;

		if (wWidth==undefined) {
			wWidth  = document.documentElement.clientWidth;
			wHeight = document.documentElement.clientHeight;
		}

		var boxLeft = parseInt((wWidth / 2) - ( $("#new-modal").width() / 2 ));
		var boxTop  = parseInt((wHeight / 2) - ( $("#new-modal").height() / 2 ));

		// position modal
		$("#new-modal").css({
			'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
		});

		$("#modalBackground").css("opacity", floatOverlayOpacity);

		if ($("body").height() > $("#modalBackground").height()){
            $("#modalBackground").css("height", $("body").height() + 50 + "px");
		} else {
            $("#modalBackground").css("height", $("#modalBackground").height() + 50 + "px");
        }
	}

	/* close modal */
	modalClose = function() {
        $('#modalBackground').hide();
		$('#new-modal #preloaderModal').hide();
		$('#new-modal .outLay').hide();
        //$('.outLay').hide();
    }

    modalAllClose = function() {
        $('#modalBackground').hide();
		$('#new-modal .outLay').hide();
        //$('.outLay').hide();
    }
    
    /**
     * Close the add new project modal window.
     */
    modalCloseAddNewProject = function() {
        $('#modalBackground').hide();
		$('#new-modal #addNewProjectModal').hide();
    }

	/* load modal (string itemID )*/
	modalLoad = function(itemID) {
		modalClose();
        $('#modalBackground').show();

        // setTimeout($("#loadingImg").attr('src', '/images/preloader-loadingie.gif?time=23213213213'), 50000);

        $(itemID).show();

        modalPosition();

    }


	/*
	 * Function modalPreloader
	 *
	 * string itemID e.g. #preloaderModal
	 * string strMarginTop e.g. 40px
	 * object callback e.g. function(){}
	 *
	 */
	modalPreloader2 = function(itemID,strMarginTop,callback){

//        if($.browser.msie) {
//            $('#new-modal #preloaderModal').remove();
//        }


		if($('#new-modal #preloaderModal').length == 0){
			var preloaderHtml = '';
			preloaderHtml += '<div id="preloaderModal" class="outLay">';
			preloaderHtml += 	'<div class="modalHeaderSmall">';
			preloaderHtml += 	'<div class="modalHeaderSmallRight">';
			preloaderHtml += 	'<div class="modalHeaderSmallCenter">';
			preloaderHtml += 	'</div></div></div>';

			preloaderHtml += 	'<div class="modalBody">';
			preloaderHtml += 	'<img id="loadingImg" src="/images/preloader-loading.gif" alt="Loading" />';
			preloaderHtml += 	'<div class="preloaderTips">';
			preloaderHtml += 	strTip;
			preloaderHtml += 	'</div></div>';

			preloaderHtml += 	'<div class="modalFooter">';
			preloaderHtml += 	'<div class="modalFooterRight">';
			preloaderHtml += 	'<div class="modalFooterCenter">';
			preloaderHtml += 	'<div class="</div></div></div>">';
			preloaderHtml += '</div>';

			$('#new-modal').append(preloaderHtml);
		}

        modalLoad('#preloaderModal');
        
        if(objPreloaderTimmer) clearTimeout(objPreloaderTimmer);
	    objPreloaderTimmer = setTimeout(function(){
			$('#new-modal #preloaderModal').hide();
			modalLoad(itemID);
			if(strMarginTop) $('#new-modal').css({'margin-top':strMarginTop});
            if(callback){
              callback.call(this);
            } 
        },intPreloaderTimmer);
        
	}
    modalPreloader = function(itemID,strMarginTop,callback) {
        //        if($.browser.msie) {
//            $('#new-modal #preloaderModal').remove();
//        }


		if($('#new-modal #preloaderModal').length == 0){
			var preloaderHtml = '';
			preloaderHtml += '<div id="preloaderModal" class="outLay">';
			preloaderHtml += 	'<div class="modalHeaderSmall">';
			preloaderHtml += 	'<div class="modalHeaderSmallRight">';
			preloaderHtml += 	'<div class="modalHeaderSmallCenter">';
			preloaderHtml += 	'</div></div></div>';

			preloaderHtml += 	'<div class="modalBody">';
			preloaderHtml += 	'<img id="loadingImg" src="/images/preloader-loading.gif" alt="Loading" />';
			preloaderHtml += 	'<div class="preloaderTips">';
			preloaderHtml += 	strTip;
			preloaderHtml += 	'</div></div>';

			preloaderHtml += 	'<div class="modalFooter">';
			preloaderHtml += 	'<div class="modalFooterRight">';
			preloaderHtml += 	'<div class="modalFooterCenter">';
			preloaderHtml += 	'<div class="</div></div></div>">';
			preloaderHtml += '</div>';

			$('#new-modal').append(preloaderHtml);
		}

        modalLoad('#preloaderModal');
    }

	$('#new-modal .outLay .closeModal').live('click', function(){
		modalAllClose();
		return false;
	});

    $('#new-modal .outLay .closeProjectModal').live('click', function(){
		modalCloseAddNewProject();
		return false;
	});
	
	//copilot Manage
	$('.addMoreUserModal').live('click',function(){
		modalPreloader2('#addMoreUsersModal');
		$('#addMoreUsersModal').addClass('focusModal');
		return false;
	});
	$('.returnUserModal,.closeOtherModal').live('click',function(){
		if($('#addMoreUsersModal').hasClass('focusModal')){
			modalPreloader2('#assignProjectModal');
            $('#addMoreUsersModal').removeClass('focusModal');
		}else{
			modalClose();
		}
		return false;
	});

})(jQuery);

 /* Add js code for  https://apps.topcoder.com/bugs/browse/BUGR-6104 */
$(document).ready(function(){

     var zIndex = 100;
     $(".newSidebar .dropdownWidget").each(function(){
        $(this).css("z-index",zIndex--);    
     });
    
    /*
     $(".newSidebar .dropdownWidget a.arrow").click(function(){
        var widget = $(this).parents(".dropdownWidget");
        var list = widget.find(".dropList");
        if(list.is(":hidden")){
            $(".dropdownWidget .dropList").hide();
            list.show();
        }else{
            list.hide();    
        }
        return false;
    })

    $(".newSidebar .dropdownWidget .dropList a").live("click",function(){
         var widget = $(this).parents(".dropdownWidget");
         widget.find("input:text").val($(this).text());
         $(this).parents(".dropList").hide();
         return false;
     })
    */
    
    $(".newSidebar .contestList .tableHeader span").click(function(){
        var header = $(this).parent();
        if($(this).hasClass("down")){
            $(this).removeClass("down").addClass("up");
        }else if($(this).hasClass("up")){
            $(this).removeClass("up").addClass("down");    
        }else{
            header.find("span").removeClass("down").removeClass("up");
            $(this).addClass("down");
        }
        
        var o = 0;
        if ($(this).hasClass("down")) {
            o = 1;
        }
        
        var oo;
        
        if ($(this).hasClass("statusTh")) {
            oo = 0;
        } else if ($(this).hasClass("titleTh")) {
            oo = 1;
        } else {
            oo = 2;
        }
        
		var sorting = [[oo, o]];
        $("#contestsTable").trigger("sorton",[sorting]);
        $("#contestsTable tr").removeClass("even");
		$("#contestsTable tr:even").addClass("even");
    })

    $(".newSidebar .contestList .tableBody td").live("mouseenter",function(){
        $(this).parent().addClass("hover");
    })
    $(".newSidebar .contestList .tableBody td").live("mouseleave",function(){
        $(this).parent().removeClass("hover");
    })

    $(document).click(function(event){

        if($(event.target).parents(".dropList").length == 0){
            //$(".dropdownWidget .dropList").hide(); 
        }

        // click other area of the page closes the dropdown menu
        if($("li#contest div.dropDwnLst").is(':visible') && !mouse_is_inside){
            $("li#contest div.dropDwnLst").hide();
        }
         
    });

    $("li#contest a span.arrow, li#contest a:eq(0)").click(function(){
        if($("li#contest div.dropDwnLst").is(':visible')){
            $("li#contest div.dropDwnLst").hide();
        }else{
            setTimeout(function(){
                $("li#contest div.dropDwnLst").show();
            },100);
        }
        return false;
    });

    $("li#contest div.dropDwnLst").hover(function(){
        mouse_is_inside=true;
    }, function(){
        mouse_is_inside=false;
    });
    
    /* Add js code for BUGR-6759 */
    var tip = "Well, I think you can ...";
    $(".fbMask a.fbBtn").click(function(){
        if(!$(this).hasClass("expand")){
            $(this).addClass("expand");
            $('.fbMask .fbBox').animate({
                height:"310px"
            }, 500);
        }else{
            $('.fbMask .fbBox').animate({
                height:"40px"
            }, 500,function(){
                $(".fbMask a.fbBtn").removeClass("expand");
            }); 
        }
        return false;
    })
    $(".fbMask .fbSubmit  .action a.cancel").click(function(){
        $('.fbMask .fbBox').animate({
            height:"40px"
        }, 500,function(){
            $(".fbMask a.fbBtn").removeClass("expand");
            $(".fbMask .fbSubmit textarea").val(tip);   
        });
        return false;
    })
    $(".fbMask .fbSubmit  .action a.submit").click(function(){
        var content = $.trim($(".fbMask .fbSubmit textarea").val());
        var tip = "Well, I think you can ...";
        if (content == "" || content == tip) {
            showErrors("Please input your feedback.");
            return false;
        }
        $.ajax({
            type: 'POST',
            url:'dashboardSendFeedback',
            data: {feedbackContent: content, feedbackURL: window.location.href},
            dataType: "json",
            cache:false,
            async:true,
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        $(".fbMask .fbMsg").fadeIn();
                        $(".fbMask .fbSubmit textarea").val(tip);
                        setTimeout(function() {
                            $(".fbMask a.fbBtn").removeClass("expand");
                            $(".fbMask .fbMsg").fadeOut(200,function(){
                                $('.fbMask .fbBox').animate({
                                    height:"40px"
                                }, 500); 
                            });
                        },3000)
                    },
                    function(errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
        $('.fbMask .fbBox').animate({
            height:"0"
        }, 500);
        return false;
    })
    $(".fbMask .fbSubmit textarea").focusin(function() {
        var value = $.trim($(this).val());
        if(value == "" || value == tip) {
            $(this).removeClass("tipIt").val("");
        }
    });
    $(".fbMask .fbSubmit textarea").focusout(function() {
        var value = $.trim($(this).val());
        if(value == "" || value == tip){
            $(this).addClass("tipIt").val(tip);
        }
    });
    $(".fbMask .fbSubmit textarea").trigger("focusout");
})
