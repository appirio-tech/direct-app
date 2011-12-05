/**
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
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
 * @author tangzx, Blues, GreatKevin, TCSASSEMBLER
 * @version 2.2
 */
$(document).ready(function(){
						   
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	
    $("#selectReport").change(function() {
        var reportType = $(this).val();
        if (reportType == 'COST') {
            window.location.href = '/direct/dashboardCostReport';
        } else if (reportType == 'PIPELINE') {
            window.location.href = '/direct/dashboardReports';
        } else if (reportType == 'BILLING_COST') {
           window.location.href = '/direct/dashboardBillingCostReport';
        } else if (reportType == 'PARTICIPATION') {
           window.location.href = '/direct/dashboardParticipationReport';
        }
    });
	
	//------------------------------------------------- Contests List
	
	/* sort contests */
	$("#contestsTable").tablesorter(); 
    
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
		var sorting = [[0,0]];
        $("#contestsTable").trigger("sorton",[sorting]);
        $("#contestsTable tr").removeClass("even");
		$("#contestsTable tr").each(function(){
			$("#contestsTable tr:even").addClass("even");
		});
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
	
	/* Stylished scrollbar*/
	$('.contestsContent').jScrollPane({
		scrollbarWidth: 17,
		showArrows: true
	});
	
	/* if a user click a contest cell he will be taken to that Contest description page*/
	$(".contestsContent TD").click(function(){
		document.location.href='contest-details.html';
	});
	
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

        if (!hasCurrentProject) input.val("Select a project here");

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
                }
            } else {
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
		$("#dropDown1").slideToggle(100);
		$("#sortTableBy").toggle();

        if($(".projectSelectMask .contestsDropDown UL").height() > 200) {
            $(".projectSelectMask .contestsDropDown UL").css('width', 233);
        }
	}

    showHideCustomerList = function(){
		var  contestsDropDown = $(".customerSelectMask .contestsDropDown");
        if(contestsDropDown.is(":hidden")){
            $(".customerSelectMask .contestsDropDown").hide();
        }
        contestsDropDown.slideToggle(100);
	}
	
	/*TCCC-2398*/
	/*-------------------------- filter the project --*/
	
	filterProject = function(){
		if ($("#dropDown1").attr("display") == "none") {
			showHideProjectList();
		}
        var typedText = $(".projectSelectMask .inputSelect>input")[0].value;

        $("#dropDown1>ul>li").each(function() {
            if ($(this).find("a")[0].innerHTML.toLowerCase().indexOf(typedText.toLowerCase()) == -1) {
                $(this).css('display', 'none');
            } else {
                $(this).css('display', '');
            }
        });
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



    $(".customerSelectMask  UL LI").click(function() {
        var mask = $(this).parents(".customerSelectMask");
        mask.find("input").val($(this).find("a").text());
        if (mask.find(".contestsDropDown").is(":visible")) {
            mask.find(".contestsDropDown:visible").slideToggle(100);
        }
        updateProjectDropDown($(".projectSelectMask"), getProjects($(this).data("id")));
        if ($("#activeContests").length > 0 || $("#projectsResult").length > 0 || $("#MyCopilotPostings").length > 0) {
            // call method defined by filter panel
            filterbyCustomer($(this).data("id"),$(this).find('a').html());
            var customer = "";
            if ($(this).data("id") != "0" && $(this).data("id") != "") {
                customer = $(this).text();
            }
            updateBreadcrumb(customer);
        }
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
	
	/*------------------------------------------------------------ Calendar --*/

    try {
        $('#calendar').fullCalendar(getCalendarConfig());
    } catch(e) {
    }

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

    $('.selectWinnerCopilot').click(function() {
        var data = $(this).data('rel');
        $('#scpPlacement').val('1');
        $('#scpSubmissionId').val($(this).data('sid'));
        $('#scpProfileId').val($(this).data('pid'));
        $('#scpTcdProjectId').val($(this).data('prid'));
        $('#SelectCopilotForm').submit();
        return false;
    });

    $('.selectRunnerUpCopilot').click(function() {
        var data = $(this).data('rel');
        $('#scpPlacement').val('2');
        $('#scpSubmissionId').val($(this).data('sid'));
        $('#scpProfileId').val($(this).data('pid'));
        $('#scpTcdProjectId').val($(this).data('prid'));
        $('#SelectCopilotForm').submit();
        return false;
    });


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
        });
        hideBugRacesEmptyContest();
        bugSortFunction();
    }

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
	var phaseNamePadding = Math.floor((771 - progressContainerWidth) / $('.progressContainer li').size() / 2);
	
	// set paddings
	$('.dashboardModule .content .progressContainer li .phaseName').css('padding-left', phaseNamePadding + 'px');
	$('.dashboardModule .content .progressContainer li .phaseName').css('padding-right', phaseNamePadding + 'px');
	
	// get new progressContainer width and update timelineContainer
	progressContainerWidth = $('.progressContainer').width();
	$('.dashboardModule .content .timelineContainer').width(progressContainerWidth+1);
	
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
	
	// get new progressContainer width and update timelineContainer
	progressContainerWidth = $('.progressContainer').width();
	$('.dashboardModule .content .timelineContainer').width(progressContainerWidth+1);

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
	function adjust(){
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


    $(" .contestViews  .contestCView .loading").css("opacity", "0.8");

    var isInitView = true;

    $(".contestViews .areaHeader .viewBtns a").click(function() {


        if ($(this).hasClass("active")) return;
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        var views = $(this).parents(".contestViews");
        if (views.find(".contestTView").is(":hidden")) {
            views.find(".contestTView").show();
            views.find(".contestCView").hide();
            views.find(".calendarLegends").hide();
        } else {
            views.find(".contestTView").hide();
            views.find(".contestCView").show();
            views.find(".calendarLegends").show();

            // get direct project ID
            var directProjectId = $.trim($("#currentDirectProjectID").html());
            var request = {"directProjectId":directProjectId};


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
                    eventRender: function(event, element) {
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
                    editable: false
                });
            }
        }
    });


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
    	$('.multiSelectBox :checkbox').each(function(){
    		$(this).click(function(){
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
    			$('.rightFilterContent').width($('.filterContainer').width()-$('.leftFilterContent').width());
    		}
    	}

    	//resize Multi Select Area width
    	$(window).resize(function(){
    		if($('.filterContainer').length>0){
    			multiSelectAreaSet();
    		}
        }) ;



    	multiSelectAreaSet();

});

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
        $('.selectWinnerCopilot').data('sid', sid);
        $('.selectWinnerCopilot').data('pid', pid);
        $('.selectWinnerCopilot').data('prid', prid);
    } else {
        $('.selectRunnerUpCopilot').data('sid', sid);
        $('.selectRunnerUpCopilot').data('pid', pid);
        $('.selectRunnerUpCopilot').data('prid', prid);
    }
    if (place == 1) {
        $('#firstPlaceCopilot').html(handle);
        $('#projectNameLabel').html(projectName);
    } else {
        $('#secondPlaceCopilot').html(handle);
    }
    
    $('#removeProjectDialog').dialog("open");
    return false;
}

/* new code for Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1 - add ajax preloader functions */
var intPreloaderTimmer = 20000;	//timer
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
            $("#modalBackground").css("height", $("body").height() + "px");
		}
	}

	/* close modal */
	modalClose = function() {
        $('#modalBackground').hide();
		$('#new-modal #preloaderModal').hide();
    }

    modalAllClose = function() {
        $('#modalBackground').hide();
		$('#new-modal .outLay').hide();
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
	modalPreloader = function(itemID,strMarginTop,callback){

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

})(jQuery);
