/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
/**
 * Javascript for security group pages.
 *
 * @author xjtufreeman, TCSASSEMBLER, freegod
 * @version 1.8
 * 
 * Version: 1.1 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 *   Updated to support view invitation and pending approvals pages.
 *
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 *   Updated to support view user groups page.
 *
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 *   Updated to support updated create-group.jsp and new groups pages of Create Admin, Send Invitation
 *   and Audit Information.
 *
 * Version 1.4 (Release Assembly - TopCoder Security Groups Release 3) changes:
 *   Updated to fixed the bugs in this assembly.
 *
 * Version 1.5 (https://apps.topcoder.com/bugs/browse/TCCC-4704) changes:
 *   Added the 'Report' permission to group security
 *
 * Version 1.6 (Release Assembly - TopCoder Security Groups Release 4) changes:
 *   Updated to fixed the bugs in this assembly.
 *
 * Version 1.7 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
 *   Updated to remove resource restrictions and add Automatically grant permission.
 *   
 * Version 1.8 (48hr Cockpit Group Management Improvement Release Assembly) change notes:
 *   Add new method checkHandlesExist, send array of handles to check whether these users exist in async method.
 *   Updated method validateMember, changed to single get handles Ajax call.
 *   Improve group management page, disable added handles input box, only allow for remove.
 *   
 * Version 1.8.1 change notes:
 *   Update the group members after validate member handles with user id. So there will be only one Search(Handles[])
 *   during the whole create/update group call. 
 *   
 */
$(document).ready(function(){

	$('.helloUserLinkTarget').attr('target','_blank');
	
	//table zebra
	$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	
    String.prototype.trim = function()
    {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    }
    	
/* Modal window */
	var intPreloaderTimmer = 2000;	//timer
	var strTip = "Loading...";		//string for preloader
	var objPreloaderTimmer;			//timer for modal
	var floatOverlayOpacity = 0.6;	//opacity for modal Background
	
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
		$('.outLay').hide();
    }
	
	/* load modal (string itemID )*/
	modalLoad = function(itemID) {
		modalClose();
        $('#modalBackground').show();
		$(itemID).show();
		modalPosition();
    }
	
	// double click #modalBackground to close modal
	/*$('#modalBackground').live('dblclick', function(){
		modalClose();
		return false;
	});*/
	// close modal
	$('#new-modal .outLay .closeModal').live('click', function(){
		modalClose();
		return false;
	});
	
    var currentUserInput;
	// custom method
	$('.searchUser').live('click',function(){
        currentUserInput = $(this).parent().find("input");
        $("#handle").val(currentUserInput.val());
        $(".handleInputError").hide();
		modalLoad("#searchModal");
		return false;
	});

    $("#searchModal input").focus(function(){
        $(".handleInputError").hide();
    });
    
    // search user by handle
    $("#searchUser").click(function() {
        var handle = $("#handle").val();
        if (handle.trim().length == 0) {
            $(".handleInputError").text("Please enter the handle");
            $(".handleInputError").show();
            return;
        }
        if (handle.trim().length > 45) {
            $(".handleInputError").text("Member Handle can't exceed 45 characters");
            $(".handleInputError").show();
            return;
        }
        $.ajax({
          type: 'GET',
          url:  ctx+"/group/searchUser",
          data: {handle: handle.trim().replace(/_/g, "\\_").replace(/%/g, "\\%")},
          cache: false,
          dataType: 'json',
          //async : false,
          beforeSend: modalPreloader,
          success: function (jsonResult) {
              handleJsonResult(jsonResult,
              function(result) {
                var html = "";
                for (var i = 0; i < result.length; i++) {
                    html += '<tr><td class="firstColumn"><input type="radio" name="userId" rel="' + result[i].handle + '" value="' + result[i].userId + '"/></td><td>' + result[i].handle + '</td></tr>';
                }
                result.length == 0 ? $("#addUser").hide() : $("#addUser").show();
                $(".handleSelectError").hide();
                var label = result.length > 1 ? " User Handles Found:" : " User Handle Found:";
                if (result.length == 0) label = " User Handle Found"
                $("#searchListModal .searchListDiv p").html(result.length + label);
                $("#searchListModal .searchListDiv table tbody").html(html);
                modalLoad("#searchListModal");
                if (result.length == 1) {
                    $("#searchListModal .searchListDiv input").attr("checked", "checked");
                }
              },
              function(errorMessage) {
                  modalClose();
                  showServerError(errorMessage);
              });
          }
        });
    });
    $("#addUser").click(function() {
        var sel = null;
        $("input[name='userId']").each(function() {
            if ($(this).is(":checked")) {
                sel = $(this);
            }
        });
        if (sel) {
            currentUserInput.attr("userId", sel.val());
            currentUserInput.val(sel.attr("rel"));
            modalClose();
        } else {
            $(".handleSelectError").show();    
        }
        
    });
    $("#searchListModal input").live("click", function(){
        $(".handleSelectError").hide();
    });
	
	// custom method
	$('.triggerNoPreloaderModal').live('click',function(){
		modalLoad($(this).attr('rel'));
		return false;
	});
/* end Modal window */


/* init date-pack */
    if($('.date-pick').length > 0){
        Date.firstDayOfWeek = 0;
    	$(".date-pick").datePicker({startDate:'01/01/2001'});
        var expNum = function(d) {
            d = "" + d;
            if (d.length < 2) d = "0" + d;
            return d;
        }
        $(".date-pick").blur(function() {
            var d = $(this).val().trim();
            if (d.length > 0) {
                var dd = parseDate(d);
                if (dd.toString() == "Invalid Date" ||
                (expNum(dd.getMonth() + 1) + "/" + expNum(dd.getDate()) + "/" + dd.getFullYear() != d)) {
                    showErrors("The date is invalid");
                    $(this).val("");
                    $(this).change();
                    return false;
                }
            }
        });
    }

	
	$('.multiSelectBox').css('overflow-x','hidden');
	//Multi Select checkbox function
    function updateMulti(obj) {
        var parentBox = $(obj).closest('.multiSelectBox');
        var parentRow = $(obj).parent('.multiOptionRow');
        var parentSelectLen = parentBox.find(':checkbox').length;
        
        if($(obj).attr('checked')){
            if($(obj).siblings('label').text() == 'Select All'){
                parentBox.find(':checkbox').attr('checked',true);
                parentBox.find('.multiOptionRow').addClass('multiOptionRowChecked');
            }else{
                parentRow.addClass('multiOptionRowChecked');
                if(parentBox.find(':checked').length+1 >= parentSelectLen){
                    parentBox.find(':checkbox:first').attr('checked',true);
                    parentBox.find('.multiOptionRow:first').addClass('multiOptionRowChecked');
                }
            }
        }else{
            if($(obj).siblings('label').text() == 'Select All'){
                parentBox.find(':checkbox').attr('checked',false);
                parentBox.find('.multiOptionRow').removeClass('multiOptionRowChecked');
            }else{
                parentBox.find(':checkbox:first').attr('checked',false);
                parentBox.find('.multiOptionRow:first').removeClass('multiOptionRowChecked');
                parentRow.removeClass('multiOptionRowChecked');
            }
        }
    }
    
    $('.multiSelectBox :checkbox').each(function(){
        $(this).unbind("click").bind("click", function(){
            updateMulti(this);
        });
    });

    if ($("#autoGrantCheck").is(":checked")) {
        $("#billingAccounts input").each(function(){
            $(this).attr("checked", "checked");
        });
        $("#directProjects input").each(function(){
            $(this).attr("checked", "checked");
        });
        $("#billingAccounts input").attr("disabled", $(this).is(":checked") ? "disabled" : "");
        $("#directProjects input").attr("disabled", $(this).is(":checked") ? "disabled" : "");
    }

    $('input[name="projects"]').each(function() {
        updateMulti(this);
    });

    $('input[name="accounts"]').each(function() {
        updateMulti(this);
    });
    
	$('#selectCreateCustomerName').width($('#selectCreateCustomerName').parent().width());
	$(window).resize(function(){
		$('#selectCreateCustomerName').width($('#selectCreateCustomerName').parent().width());
	});
	
	
	$('#fetchUrlButton').live('click',function(){
		$('.fetchUrlDiv').show();
	});
	
	$(window).resize(function(){
		$('.selectCustomer').css('width',$('.compareText').width()+14);
	});
	
	$('.selectCustomer').css('width',$('.compareText').width()+14);
	
	$('#addMemberButton').live('click',function(){
		for(i=0;i<5;i++){
			$('#moreMember').append('<dd><input type="text" /><a href="javascript:;" class="searchDetails triggerModal searchUser" rel="#searchModal">Search</a><div class="clearFixed"></div></dd>');
		}
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
	});
	
	var items = 5;
    var addedStatus = $("#groupMemberTable thead tr th").length == 5;
	$('#addGroupMemberButton').live('click',function(){
	    items = $("#groupMemberTable tbody tr").length;
		for(i=0;i<5;i++){
			items++;
			$('#groupMemberTable').append('<tr><td class="firstColumn"><input type="text" class="text" /><a href="javascript:;" class="searchDetails searchUser triggerModal" rel="#searchModal">Search</a></td><td class="secondColumn"><div class="leftPart"><input type="radio" name="radioGroupMembers'+items+'" checked="checked" value="Group Default" id="gruopRadio'+items+'" /><label for="gruopRadio'+items+'">Group Default</label></div><div class="rightPart"><input type="radio" name="radioGroupMembers'+items+'" value="User Specific" id="userRadio'+items+'" /><label for="userRadio'+items+'">User Specific</label></div></td><td class="thirdColumn"><div class="optionWrapper"><div class="unit"><input type="radio" disabled="disabled" id="reportRadio'+items+'" value="REPORT" name="accesslevel'+items+'"/><label for="reportRadio'+items+'">Report</label></div><div class="unit"><input type="radio" disabled="disabled" checked="checked" id="readRadio'+items+'" value="READ"  name="accesslevel'+items+'"/><label for="readRadio'+items+'">Read</label></div><div class="unit"><input type="radio" disabled="disabled" id="writeRadio'+items+'" value="WRITE" name="accesslevel'+items+'"/><label for="writeRadio'+items+'">Write</label></div><div class="unit"><input type="radio"  disabled="disabled" id="fullRadio'+items+'" value="FULL" name="accesslevel'+items+'"/><label for="fullRadio'+items+'">Full</label></div><div style="clear:both;"></div></div></td>' + (addedStatus ? '<td class="forthColumn"></td>' : '') + '<td class="forthColumn"><a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a></td></tr>');
		}
		$('#groupMemberTable tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$('#groupMemberTable .removeButton').live('click',function(){
		$(this).parent().parent().remove();
		$('#groupMemberTable tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	});
	
	$('#groupMemberTable .secondColumn input:radio').live('click',function(){
		if($(this).val() == 'Group Default'){
			$(this).parent().parent().parent().find('.thirdColumn input:radio').attr('disabled',true);	
		}else{
			$(this).parent().parent().parent().find('.thirdColumn input:radio').attr('disabled',false);
		}
	});
	
	$('#addGroupMemberButtons').live('click',function(){
		for(i=0;i<5;i++){
			items++;
			$('#groupMemberTables').append('<tr><td class="firstColumn"><input type="text" class="text" value="Handle" /><a href="javascript:;" class="searchDetails triggerModal" rel="#searchModal">Search</a></td><td class="secondColumn"><div class="leftPart"><input type="radio" name="radioGroupMembers'+items+'" checked="checked" value="Group Default" id="gruopRadio'+items+'" /><label for="gruopRadio'+items+'">Group Default</label></div><div class="rightPart"><input type="radio" name="radioGroupMembers'+items+'" value="User Specific" id="userRadio'+items+'" /><label for="userRadio'+items+'">User Specific</label></div></td><td class="thirdColumn"><div class="optionWrapper"><div class="unit"><input type="checkbox" disabled="disabled" id="reportRadio'+items+'" /><label for="reportRadio'+items+'">Report</label></div><div class="unit"><input type="checkbox" disabled="disabled" id="readRadio'+items+'" /><label for="readRadio'+items+'">Read</label></div><div class="unit"><input type="checkbox" disabled="disabled" id="writeRadio'+items+'" /><label for="writeRadio'+items+'">Write</label></div><div class="unit"><input type="checkbox" checked="checked" disabled="disabled" id="fullRadio'+items+'" /><label for="fullRadio'+items+'">Full</label></div><div style="clear:both;"></div></div></td><td class="forthColumn"><a href="javascript:;" class="newButton2 removeButton"><span class="btnR"><span class="btnC"><span class="btnIcon">Remove</span></span></span></a></td></tr>');
		}
		$('#groupMemberTables tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-78);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-78);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-68);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$('#groupMemberTables .removeButton').live('click',function(){
		$(this).parent().parent().remove();
		$('#groupMemberTables tbody tr').removeClass('even');
		$('.tableGroupWrapper .normalTableList tbody tr:odd,.groupMembersTableContainer .normalTableList tbody tr:odd,#searchListModal .searchListDiv tbody tr:odd,.groupMembersTable tbody tr:odd').addClass('even');
	});
	
	$('#groupMemberTables input:radio').live('click',function(){
		if($(this).val() == 'Group Default'){
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',true);	
		}else{
			$(this).parent().parent().parent().find('input:checkbox').attr('disabled',false);
		}
	});
	
	$('#groupMemberTable input:radio').css('position','relative');
	$('#groupMemberTable input:radio').css('top','-2px');
	$('#groupMemberTable input:checkbox').css('position','relative');
	$('#groupMemberTable input:checkbox').css('top','-1px');
	$('.tableControlPage select').css('position','relative');
	$('.tableControlPage select').css('top','3px');
	
	// disable edit existing users's handle.
	$("#groupMemberTable input[type='text'][value]").each(function(){
	    $(this).attr('disabled','disabled');
	    $(this).next().remove();
	});
	
	$(window).load(function(){
		$('.topFilterBox .text').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-18));
		$('.topFilterBox select').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .specialColumn').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .date-pick').css('width',($('.topFilterBox .specialColumn').width()-140)/2);
		$('.topFilterBox .searchText').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-52));
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-82);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-82);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-72);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
	
	$(window).resize(function(){
		$('.topFilterBox .text').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-18));
		$('.topFilterBox select').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .specialColumn').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-4));
		$('.topFilterBox .date-pick').css('width',($('.topFilterBox .specialColumn').width()-140)/2);
		$('.topFilterBox .searchText').css('width',($('.topFilterBox .leftSide').width()-$('.topFilterBox .leftSide .label').width()-52));
		$('#moreMember input').css('width',$('.sendGroupInvitationTable table').width()/2-82);
		$('.newAdminText').css('width',$('.groupDetailsTableContainer table').width()/2-82);
		$('.firstColumn .text').css('width',$('.groupMembersTableContainer th:first').width()-72);
		$('.groupMembersTableContainer table tbody td.secondColumn .leftPart').css('margin-left',($('.groupMembersTableContainer table th:eq(1)').width()-266)/2);
	});
    
    function buildMulti(obj, data, prefix, name) {
        var html = '<div class="multiOptionRow">';
        if(data.length == 0) {
            if(name == 'accounts') {
                html += '<div>No billing account available</div></div>';
            } else if (name == 'projects') {
                html += '<div>No project available</div></div>';
            } else {
                html += '<div>No ' + name + ' available</div></div>';
            }
        } else {
            html += '<input type="checkbox" id="' + prefix + '00"/><label for="' + prefix + '00">Select All</label></div>';
            for (var i = 0; i < data.length; i++) {
                html += '<div class="multiOptionRow"><input type="checkbox" id="' + prefix + (i + 1) + '" name="' + name + '" value="' + data[i].projectId + '"/><label for="' + prefix + (i + 1) + '">' + data[i].name + '</label></div>';
            }
        }
        obj.html(html);
    }
    
    // get the projects and billing accounts according to the client
    $("#selectCreateCustomerName").change(function() {
        var clientId = $(this).val();
        $.ajax({
          type: 'GET',
          url:  ctx+"/group/getProjectsAndAccounts",
          data: {selectedClientId: clientId},
          cache: false,
          dataType: 'json',
          async : false,
          success: function (jsonResult) {
              handleJsonResult(jsonResult,
              function(result) {
                var projects = result.projects;
                var accounts = result.accounts;
                buildMulti($("#billingAccounts"), accounts, "multiSelect01CheckBox", "accounts");
                buildMulti($("#directProjects"), projects, "multiSelect02CheckBox", "projects");
                $('.multiSelectBox :checkbox').each(function(){
                    $(this).unbind("click").bind("click", function(){
                        updateMulti(this);
                    });
                });
                if($("#autoGrantCheck").is(":checked")) {
                    $("#billingAccounts input").each(function(){
                        $(this).attr("checked", "checked");
                    });
                    $("#directProjects input").each(function(){
                        $(this).attr("checked", "checked");
                    });

                    $("#billingAccounts input").attr("disabled", "disabled");
                    $("#directProjects input").attr("disabled", "disabled");                    
                }
              },
              function(errorMessage) {
                  showServerError(errorMessage);
              })
          }
       });
    });

    $("#billingAccounts input").attr("disabled", $("#autoGrantCheck").is(":checked") ? "disabled" : "");
    $("#directProjects input").attr("disabled", $("#autoGrantCheck").is(":checked") ? "disabled" : "");

    $("#autoGrantCheck").click(function(){
        $("#billingAccounts input").each(function(){
            $(this).attr("checked", "checked");
        });
        $("#directProjects input").each(function(){
            $(this).attr("checked", "checked");
        });
        $("#billingAccounts input").attr("disabled", $(this).is(":checked") ? "disabled" : "");
        $("#directProjects input").attr("disabled", $(this).is(":checked") ? "disabled" : "");
    });

    function getRadiosValue(rs) {
        var v = false;
        rs.each(function() {
            if ($(this).is(":checked")) {
                v = $(this).val();
            }
        });
        return v;
    }
    function getCheckboxesValue(name, key, isArray) {
        var vv = [];
        $("input[name='" + name + "']").each(function() {
            if ($(this).is(":checked")) {
                if (isArray) {
                    vv.push(parseInt($(this).val()));
                } else {
                    var o = {};
                    o[key] = parseInt($(this).val());
                    vv.push(o);
                }
            }
        });
        return vv;
    }
    
    // validate the group members, and get the group members object
    function getGroupMembers() {
        var members = [];
        var mh = {};
        var ok = true;
        $("#groupMemberTable tbody tr").each(function() {
            var inp = $(this).find(".firstColumn input[type='text']");
            var handle = inp.val().trim();
            var userId = parseInt(inp.attr("userId"));
            if (!userId) userId = 0;
            if (handle.length > 0) {
                if (!checkStrLen(handle) && ok) {
                    ok = false;
                    showErrors("Handle can't exceed 45 characters");
                }
                var radios = $(this).find(".secondColumn input");
                var groupDefault = false;
                var userSpec = false;
                if ($(radios[0]).is(":checked")) groupDefault = true;
                if ($(radios[1]).is(":checked")) userSpec = true;
                var mem = {
                    handle: handle,
                    userId: userId,
                    useGroupDefault: groupDefault
                };
                
                if (userSpec) {
                    mem.specificPermission = getRadiosValue($(this).find(".thirdColumn input"));
                }
                members.push(mem);
                if (mh[handle] && ok) {
                    ok = false;
                    showErrors("User " + handle + " shows multiple times");
                }
                mh[handle] = true;
            }
        });
        if (!ok) return false;
        return members;
    }
    
    function checkStrLen(str) {
        if (str.length > 45) {
            return false;
        }
        return true;
    }
    
    // validate the group date, and get the group object
    function validateGroup() {
        var groupName = $("#groupName").val().trim();
        if (groupName.length == 0) {
            showErrors("Group Name can't be empty");
            return false;
        }
        if (!checkStrLen(groupName)) {
            showErrors("Group Name can't exceed 45 characters");
            return false;
        }
        var defaultPerm = getRadiosValue($("input[name='defaultPerm']"));
        if (defaultPerm === false) {
            showErrors("Please select Group Authorization Attributes");
            return false;
        }
        var customerId = $("#selectCreateCustomerName").val();
        if (customerId <= 0) {
            showErrors("Please select Customer Name");
            return false;
        }
        var billingCheck = $("#billingCheck").is(":checked");
        var projectsCheck = $("#projectsCheck").is(":checked");
        var projects = [];
        var billings = [];

        billings = getCheckboxesValue("accounts", "id");
        projects = getCheckboxesValue("projects", "directProjectId");

        var autoGrant = $("#autoGrantCheck").is(":checked");
        var members = getGroupMembers();
        if (members === false) {
            return false;
        }
        var group = {
            name: groupName,
            client: {
                id: customerId
            },
            billingAccounts: billings,
            directProjects: projects,
            defaultPermission: defaultPerm,
            groupMembers: members,
            autoGrant: autoGrant
        };
        return group;
    }
    
    var oriGroup;
    if ($("#updateGroup").length > 0) {
        oriGroup = validateGroup();
    }
    
    function compareGroup(group1, group2) {
        if (group1.name != group2.name) return false;
        if (group1.client.id != group2.client.id) return false;
        if (group1.billingAccounts.length != group2.billingAccounts.length) return false;
        for (var i = 0; i < group1.billingAccounts.length; i++) {
            if (group1.billingAccounts[i].id != group2.billingAccounts[i].id) return false;
        }
        if (group1.directProjects.length != group2.directProjects.length) return false;
        for (var i = 0; i < group1.directProjects.length; i++) {
            if (group1.directProjects[i].directProjectId != group2.directProjects[i].directProjectId) return false;
        }
        if (group1.defaultPermission != group2.defaultPermission) return false;
        if (group1.groupMembers.length != group2.groupMembers.length) return false;
        for (var i = 0; i < group1.groupMembers.length; i++) {
            if (group1.groupMembers[i].handle != group2.groupMembers[i].handle) return false;
            if (group1.groupMembers[i].specificPermission != group2.groupMembers[i].specificPermission) return false;
        }
        if(group1.autoGrant != group2.autoGrant) return false;
        return true;
    }
    function newAddedMember(oriGroup, group) {
        var members = [];
        for (var i = 0; i < oriGroup.groupMembers.length; i++) members[oriGroup.groupMembers[i].handle] = true;
        var tot = 0;
        for (var i = 0; i < group.groupMembers.length; i++) if (!members[group.groupMembers[i].handle]) tot++;
        return tot;
    }
    
    function checkHandlesExist(handles, callback) {
        var userIds = new Array(handles.length);
        $.ajax({
            type: 'GET',
            url:  ctx+"/group/getUsers",
            data: {handles: handles},
            cache: false,
            dataType: 'json',
            beforeSend: modalPreloader,
            timeout:3600000,
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                function(result) {
                    for(var i=0;i<handles.length;i++) {
                        for(var j=0;j<result.length;j++) {
                            if (handles[i] === result[j].handle) {
                                userIds[i] = result[j].userId;
                                break;
                            }
                        }
                    }
                    callback(userIds);
                },
                function(errorMessage) {
                    modalClose();
                    showServerError(errorMessage);
                    callback(userIds);
                });
            }
        });
    }

    //make sure every user exists.
    function validateMember(callback) {
        var handles = new Array();
        //retrieve user handles and search if not empty.
        $("#groupMemberTable input[type='text']:not(:disabled)").each(function() {
            var handle = $(this).val().trim();
            if(handle.length > 0) {
                if (!checkStrLen(handle)) {
                    showErrors("Handle can't exceed 45 characters");
                    return false;
                }
                for(var i=0;i<handles.length;i++) {
                    if( handle == handles[i]) {
                        showErrors('Duplicate handle: ' + handle);
                        return false;
                    }
                }
                handles.push(handle);
            }
        });

        checkHandlesExist(handles, function(userIds) {
            var errorHandles = new Array();
            for(var i=0;i<handles.length;i++) {
                if(!userIds[i]) {
                    errorHandles.push(handles[i]);
                }
            }
            
            if (errorHandles.length == 1) {
                showErrors('User with handle: ' + errorHandles[0] + ' doesn\'t exist.');
                callback(false);
                return;
            } else if (errorHandles.length > 1) {
                showErrors('Users with handles: ' + errorHandles.join(", ") + ' don\'t exist.');
                callback(false);
                return;
            }

            $("#groupMemberTable input[type='text']:not(:disabled)").each(function(index) {
                $(this).attr("userId", userIds[index]);
            });
            callback(true);
        });
    }
    
    // create group
    $("#createGroup").click(function() {
        if ($("#createGroup").hasClass("disabled")) {
            return;
        }
        $("#createGroup").addClass("disabled");
        $("#createGroup .btnC").text("CREATING...");
        var group = validateGroup();
        if (group === false) {
            $("#createGroup .btnC").text("CREATE GROUP");
            $("#createGroup").removeClass("disabled");
            return;
        }
        validateMember(function (userExist) {
            if(userExist == false) {
                $("#createGroup .btnC").text("CREATE GROUP");
                $("#createGroup").removeClass("disabled");
                return ;
            }
            // update the group members with userid.
            group.groupMembers = getGroupMembers();
            var skipInvitationEmail = $('#skipInvitationEmail').is(":checked");
            $("#sendInvitationModal .modalBody .preloaderTips").text("Sending invitation email(s)... please wait, this may take a while.");
            $.ajax({
              type: 'POST',
              url:  ctx+"/group/createGroup",
              data: {group: group, skipInvitationEmail: skipInvitationEmail},
              cache: false,
              dataType: 'json',
              //async : false,
              beforeSend: (skipInvitationEmail || group.groupMembers.length == 0) ? modalPreloader : modalSendInvitation,
              timeout:3600000,
              success: function (jsonResult) {
                  handleJsonResult(jsonResult,
                  function(result) {
                    $(".gotoGroupDetail").attr("rel", result.groupId);
                    $(".confirmGroupName").text(group.name);
                    $("#confirmCustomName").text($($("#selectCreateCustomerName")[0].options[$("#selectCreateCustomerName")[0].selectedIndex]).text());
                    if (skipInvitationEmail) {
                        if (group.groupMembers.length == 0) {
                            $(".emailMessage").text("");
                        } else {
                            $(".emailMessage").text("The group " + (group.groupMembers.length > 1 ? "members have" : "member has") + " been granted access.");
                        }
                    } else {
                        if (group.groupMembers.length == 1) {
                            $(".emailMessage").text("Invitation emails have been sent to the member added to the group.");
                        } else if (group.groupMembers.length > 1) {
                            $(".emailMessage").text("Invitation emails have been sent to the members added to the group.");
                        } else $(".emailMessage").text("");
                    }
                    modalLoad("#createGroupConfirmModal");
                    $("#createGroup .btnC").text("CREATE GROUP");
                    $("#createGroup").removeClass("disabled");
                  },
                  function(errorMessage) {
                      modalClose();
                      showServerError(errorMessage);
                      $("#createGroup .btnC").text("CREATE GROUP");
                      $("#createGroup").removeClass("disabled");
                  });
              }
            }); 
        });
    });

    // update group
    $("#updateGroup").click(function() {
        if ($("#updateGroup").hasClass("disabled")) {
            return;
        }
        $("#updateGroup").addClass("disabled");
        $("#updateGroup .btnC").text("SAVING...");
        var group = validateGroup();
        if (group === false) {
            $("#updateGroup .btnC").text("SAVE GROUP");
            $("#updateGroup").removeClass("disabled");
            return;
        }
        if (compareGroup(oriGroup, group)) {
            $("#noChangeGroupName").text(group.name);
            modalLoad("#noChangeConfirmModal");
            $("#updateGroup .btnC").text("SAVE GROUP");
            $("#updateGroup").removeClass("disabled");
            return;
        }
        validateMember(function (userExist) {
            if(userExist == false) {
                $("#updateGroup .btnC").text("SAVE GROUP");
                $("#updateGroup").removeClass("disabled");
                return ;
            }
            // update the group members with userid.
            group.groupMembers = getGroupMembers();
            var skipInvitationEmail = $('#skipInvitationEmail').is(":checked");
            var hasNew = hasNewMembers(oldGroupMemberHandles, getGroupMemberHandles());
            $.ajax({
              type: 'POST',
              url:  ctx+"/group/updateGroup",
              data: {group: group, groupId: parseInt($("#groupId").val()), skipInvitationEmail: skipInvitationEmail},
              cache: false,
              dataType: 'json',
              timeout:3600000,
              //async : false,
              beforeSend: (skipInvitationEmail || group.groupMembers.length == 0 || !hasNew) ? modalPreloader : modalSendInvitation,
              success: function (jsonResult) {
                  handleJsonResult(jsonResult,
                  function(result) {
                    $(".gotoGroupDetail").attr("rel", result.groupId);
                    $(".confirmGroupName").text(group.name);
                    
                    var totAdd = newAddedMember(oriGroup, group);
                    if (skipInvitationEmail) {
                        if (totAdd == 0) {
                            $(".emailMessage").text("");
                        } else {
                            $(".emailMessage").text("The new group " + (totAdd > 1 ? "members have" : "member has") + " been granted access.");
                        }
                    } else {
                        if (totAdd == 1) {
                            $(".emailMessage").text("Invitation emails have been sent to the member added to the group.");
                        } else if (totAdd > 1) {
                            $(".emailMessage").text("Invitation emails have been sent to the members added to the group.");
                        } else $(".emailMessage").text("");
                    }
                    
                    modalLoad("#updateGroupConfirmModal");
                    $("#updateGroup .btnC").text("SAVE GROUP");
                    $("#updateGroup").removeClass("disabled");
                  },
                  function(errorMessage) {
                      modalClose();
                      showServerError(errorMessage);
                      $("#updateGroup .btnC").text("SAVE GROUP");
                      $("#updateGroup").removeClass("disabled");
                  });
              }
            });
        });
    });

    //bind the enter keyboard button to filter input
    $("dl.filterUserGroup input").keypress(function(e) {
        if (e.which == 13) {
            filterItems(false, 1); //Call the filter method
        }
    });

    //update group page.
    var oldGroupMemberHandles;
    if($('#updateGroup').length > 0) {
        oldGroupMemberHandles = getGroupMemberHandles();
    }

    // get the group member handles
    function getGroupMemberHandles() {
        var memberHandles = [];
        var groupMembers = getGroupMembers();
        for(var i=0; i<groupMembers.length; i++) {
            memberHandles.push(groupMembers[i].handle);
        }
        return memberHandles;
    }
    // check if new group member are entered
    function hasNewMembers(oldGroupMemberHandles, newGroupMemberHandles) {
        var hasNew = false;
        for(var i=0; i<newGroupMemberHandles.length; i++) {
            if(!(oldGroupMemberHandles.indexOf(newGroupMemberHandles[i]) >= 0)) {
                hasNew = true;
                break;
            }
        }
        return hasNew;
    }
    
    // go to the group detail page
    $(".gotoGroupDetail").click(function() {
        window.location.href = ctx + "/group/viewGroupAction.action?groupId=" + $(this).attr("rel");
    });
    
    function parseDate(str) {
        // MM/dd/yyyy
        var parts = str.split("/");
        return new Date(parts[2], (parts[0] - 1) ,parts[1]);
    }
    
    // filter items
    $('#filterViewInvitations, #filterAuditInfo, #filterUserGroups').click(function() {
         filterItems(false, 1);
    });
    
    // page size update
    $('#pageSizeSelect').change(function() {
         filterItems(true, 1);
    });
    
    // pagination
    $('.tableControlPage .rightSide ul a').live('click', function() {
         var total = parseInt($('#totalInput').val());
         var current = parseInt($('.tableControlPage .rightSide ul li.current').text());
         var pageSize= parseInt($('#pageSizeSelect').val());
         var op = $(this).text();
         var dest;
         if (op == "Prev") {
             if (current <= 1)return;
             dest = current - 1;
         } else if (op == "Next") {
             if (pageSize == 0 || current >= Math.floor((total + pageSize - 1) / pageSize))return;
             dest = current + 1;
         } else {
             dest = parseInt(op);
         }
         filterItems(true, dest);            
    });
    
    // check group name and permissions
    var criteria;
    var approveRejectData;
    function checkGroupNameAndPermissions() {
        // group name
        var groupName = $("#groupNameInput").val().trim();
        if (groupName.length > 0) {
            if (!checkStrLen(groupName)) {
                showErrors("Group Name can't exceed 45 characters");
                return false;
            }
            criteria.groupName = groupName;
        }
        
        // check permission
        criteria.permissions = [];

        if ($('#reportRadio').is(":checked")) {
           criteria.permissions.push("REPORT");
        }
        if ($('#readRadio').is(":checked")) {
           criteria.permissions.push("READ");
        }
        if ($('#writeRadio').is(":checked")) {
           criteria.permissions.push("WRITE");
        }
        if ($('#fullRadio').is(":checked")) {
           criteria.permissions.push("FULL");
        }
        if (criteria.permissions.length==0) {
            showErrors("At least one Access Right must be checked");
            return false;
        }
        return true;
    } 
    
    // check project name and billing account name
    function checkProjectNameAndBillingAccountName(projectName, billingAccountName) { 
        if (projectName.length > 0) {
            if (!checkStrLen(projectName)) {
                showErrors("Project Name can't exceed 45 characters");
                return false;
            }
            criteria.projectName = projectName;
        }
        
        if (billingAccountName.length > 0) {
            if (!checkStrLen(billingAccountName)) {
                showErrors("Billing account Name can't exceed 45 characters");
                return false;
            }
            criteria.billingAccountName = billingAccountName;
        }
        return true;
    }
    
    // validate invitation criteria filter  
    function validateInvitationFilter() {
        if (!checkGroupNameAndPermissions())return false;
        
        // received date range
        if ($.trim($("#startDateReceived").val()).length>0) {
            criteria.sentDateFrom = $("#startDateReceived").val();
        }
        if ($.trim($("#endDateReceived").val()).length>0) {
            criteria.sentDateTo = $("#endDateReceived").val();
        }
        if (criteria.sentDateFrom && criteria.sentDateTo &&
                parseDate(criteria.sentDateFrom).getTime()>parseDate(criteria.sentDateTo).getTime()) {
            showErrors("Invitation Received end date can't be earlier than Invitation Received start date");
            return false;
        }
        
        // accepted date range
        if ($.trim($("#startDateAccepted").val()).length>0) {
            criteria.acceptedDateFrom = $("#startDateAccepted").val();
        }
        if ($.trim($("#endDateAccepted").val()).length>0) {
            criteria.acceptedDateTo = $("#endDateAccepted").val();
        }
        if (criteria.acceptedDateFrom && criteria.acceptedDateTo &&
                parseDate(criteria.acceptedDateFrom).getTime()>parseDate(criteria.acceptedDateTo).getTime()) {
            showErrors("Invitation Accepted end date can't be earlier than Invitation Accepted start date");
            return false;
        }
        return true;
    }
    
    // validate pending approval criteria filter
    function validateApprovalFilter() {
        // inviteeHandle
        var inviteeHandle = $("#inviteeHandle").val().trim();
        if (inviteeHandle.length > 0) {
            if (!checkStrLen(inviteeHandle)) {
                showErrors("Invitee Handle can't exceed 45 characters");
                return false;
            }
            criteria.inviteeHandle = inviteeHandle;
        }
       
        // inviteeEmail
        var inviteeEmail = $("#inviteeEmail").val().trim();
        if (inviteeEmail.length > 0) {
            if (!checkStrLen(inviteeEmail)) {
                showErrors("Invitee Email can't exceed 45 characters");
                return false;
            }
            var emailPattern = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (!emailPattern.test(inviteeEmail)) {
                showErrors("Invitee Email is not valid");
                return false;
            }
            criteria.inviteeEmail = inviteeEmail;
        }
        
        // received date range
        if ($.trim($("#startDateReceived").val()).length>0) {
            criteria.sentDateFrom = $("#startDateReceived").val();
        }
        if ($.trim($("#endDateReceived").val()).length>0) {
            criteria.sentDateTo = $("#endDateReceived").val();
        }
        if (criteria.sentDateFrom && criteria.sentDateTo &&
                parseDate(criteria.sentDateFrom).getTime()>parseDate(criteria.sentDateTo).getTime()) {
            showErrors("Request Date end date can't be earlier than Request Date start date");
            return false;
        }        
        return true;
    }
    
    // validate user group criteria filter
    function validateGroupFilter() {
        if (!checkGroupNameAndPermissions())return false;
        
        // customer name
        var customerName = $("#customerNameInput").val().trim();
        if (customerName.length > 0) {
            if (!checkStrLen(customerName)) {
                showErrors("Customer Name can't exceed 45 characters");
                return false;
            }
            criteria.clientName = customerName;
        }
        
        // project name
        var projectName = $("#projectNameInput").val().trim();    
        // billing account name
        var billingAccountName = $("#accountNameInput").val().trim();
        if(!checkProjectNameAndBillingAccountName(projectName, billingAccountName)){
            return false;
        }
        // group member name
        var groupMemberName = $("#groupMemberNameInput").val().trim();
        if (groupMemberName.length > 0) {
            if (!checkStrLen(groupMemberName)) {
                showErrors("Group Member Name can't exceed 45 characters");
                return false;
            }
            criteria.groupMemberHandle = groupMemberName;
        }
        
        return true;
    }
    
    // validates audit info filter
    function validateAuditFilter() {
        if (!checkGroupNameAndPermissions())return false;
        
        // Had Access date range
        if ($.trim($("#startDateReceived").val()).length>0) {
            criteria.hadAccessFrom = $("#startDateReceived").val();
        }
        if ($.trim($("#endDateReceived").val()).length>0) {
            criteria.hadAccessTill = $("#endDateReceived").val();
        }
        if (criteria.hadAccessFrom && criteria.hadAccessTill &&
                parseDate(criteria.hadAccessFrom).getTime()>parseDate(criteria.hadAccessTill).getTime()) {
            showErrors("Had Access end date can't be earlier than Had Access start date");
            return false;
        }       
        
        // project name
        var projectName = $("#projectNameInput").val().trim();    
        // billing account name
        var billingAccountName = $("#billingAccountInput").val().trim();
        if(!checkProjectNameAndBillingAccountName(projectName, billingAccountName)) {
            return false;    
        }
        
        // member handle
        var memberHandle = $("#memberHandleInput").val().trim();
        if (memberHandle.length > 0) {
            if (!checkStrLen(memberHandle)) {
                showErrors("Member Handle can't exceed 45 characters");
                return false;
            }
            criteria.memberHandle = memberHandle;
        }
        
        // client name
        var clientName = $("#clientIdSelect option:selected").html().trim();
        if (clientName && clientName != "--") {
            if (!checkStrLen(clientName)) {
                showErrors("Client name can't exceed 45 characters");
                return false;
            }
            criteria.clientName = clientName;
        }       
        return true; 
    }
    
    (function() {
        var isAuditPage = $('#auditPage').val();
        var isGroupPage = $('#groupPage').val();
        var isGroupDetailsPage = $('#groupDetailsPage').val();
        var isApprovalPage = $('#approvalPage').val();
        var isInvitation = $('#filterViewInvitations').length > 0;
        criteria = {};
        if (isAuditPage) {
            validateAuditFilter();
        } else if (isGroupPage) {
            validateGroupFilter();
        } else if (isGroupDetailsPage)
        {} else if (isApprovalPage) {
            validateApprovalFilter();
        } else if (isInvitation) {
            validateInvitationFilter();
        }
        $("#pageSizeSelect").data("criteria", criteria);
    })();
    
    updateMultiRowsCell();
    
    // retrieve the group invitations
    function filterItems(useOldCriteria, page, fromApproval, criteriaOverwrite) {
        if (criteriaOverwrite) criteria = criteriaOverwrite;
        else criteria = {};
        var isAuditPage = $('#auditPage').val();
        var isGroupPage = $('#groupPage').val();
        var isGroupDetailsPage = $('#groupDetailsPage').val();
        var isApprovalPage = $('#approvalPage').val();
        var url;
        if (isAuditPage) {
            if (!useOldCriteria && !validateAuditFilter())return;
            url = ctx + "/group/searchAuditingInfoAction";
        } else if (isGroupPage) {
            if (!useOldCriteria && !criteriaOverwrite && !validateGroupFilter())return;
            url = ctx + "/group/searchGroupAction";
        } else if(isGroupDetailsPage) {
            location.href = '/direct/group/viewUserGroupsAction.action?criteria.permissions=REPORT&criteria.permissions=READ&criteria.permissions=WRITE&criteria.permissions=FULL';
            return;
        } else if (!isApprovalPage && !useOldCriteria && !validateInvitationFilter()
            || isApprovalPage && !useOldCriteria && !validateApprovalFilter()) {
            if (fromApproval) {
                // for invalid filter settings, we uses empty criteria
                criteria = {};
            } else {
                return;
            }
        }
        if (!url) {
            url = ctx+ (isApprovalPage ? "/group/searchPendingApproval" : "/group/searchReceivedInvitations");
        }
        
        var pageSize=$('#pageSizeSelect').val();
        if (pageSize == 0)page = 0;
        
        if (useOldCriteria) {
            criteria = $("#pageSizeSelect").data("criteria");
            if (!criteria) criteria = {};
        } else {
            $("#pageSizeSelect").data("criteria", criteria);
        }
        var data = {page: page, pageSize: pageSize, criteria: criteria};
        if (isApprovalPage) {
            data.clientId = $('#clientIdSelect').val();  
        }
        
        $.ajax({
          type: 'POST',
          url:  url,
          data: data,
          cache: false,
          dataType: 'json',
          beforeSend: (fromApproval ? null : modalPreloader),
          success: function (jsonResult) {
              handleJsonResult2(jsonResult,
              function(result) {
                  page=result.page;
                  pageSize=result.pageSize;
                  var total=result.total;
                  var items=result.items;
                  if (items.length == 0) {
                      $('div.mainContent').hide();
                      $('div.approvalButton').hide();
                      $('div.noInvitation').show();                      
                  } else {
                      $('div.noInvitation').hide();  
                      $('div.mainContent').show();
                      $('div.approvalButton').show();
                      $('#totalInput').val(total);
                      
                      // pagination 
                      $('.tableControlPage .leftSide').html("Showing <strong>"+(page == 0 ? 1 : (page-1)*pageSize+1)+
                          "</strong> to <strong>" + (page == 0 ? total : (page-1)*pageSize+items.length) + 
                          "</strong> of <strong>"+ total +"</strong> " + (total > 1 ? "entries" : "entry"));                      
                      $('.tableControlPage .rightSide ul').html(
                          '<li><a href="javascript:;" class="prevPage ' +
                              (page<=1 ? 'disable' : '') + '">Prev</a></li>' + 
                          (page>1 ? '<li><a href="javascript:;">' + (page-1) + '</a></li>' : '') +
						  '<li class="current"><strong>' + (page == 0 ? 1 : page) + '</strong></li>' +
						  (0<page && page<Math.floor((total+pageSize-1)/pageSize) ? 
						      '<li><a href="javascript:;">' + (page+1) + '</a></li>' : '') +
						  '<li><a href="javascript:;" class="nextPage ' + 
						      (page==0 || page==Math.floor((total+pageSize-1)/pageSize) ? 'disable' : '') + '">Next</a></li>');
					  
					  // main content
					  var content = '';
					  $.each(items, function(idx, obj) {
					        if (isAuditPage) {
                                content += 
					              '<tr ' + (idx==0?'class="firstRow"':'')+'>'+
                                        '<td>'+obj.groupName+'</td>'+
                                        '<td>'+obj.member+'</td>'+
                                        '<td>'+obj.customerName+'</td>'+
                                        '<td>'+tooltipOnMultiRows(obj.accounts)+'</td>'+
                                        '<td>'+tooltipOnMultiRows(obj.projects)+'</td>'+
                                        '<td>'+(obj.autoGrant ? 'Yes' : 'No')+'</td>'+
                                        '<td>'+obj.fromTo.join('<br/>')+'</td>'+
                                        '<td>'+obj.accessRights+'</td>'+
                                    '</tr>';
                            } else if (isGroupPage) {
                                content +=
					              '<tr ' + (idx==0?'class="firstRow"':'')+'>'+
                                        '<td><input type="radio" name="userGroupSelectRadio" class="userGroupSelectRadio"'
                                            + (idx==0?' checked="checked"':'')+'/></td>'+
                                        '<td><a '+' rel="'+obj.groupId+'" href="'+(ctx + '/group/viewGroupAction?groupId='+obj.groupId+'">')+obj.groupName+'</a></td>'+
                                        '<td>'+obj.accessRights+'</td>'+
                                        '<td>'+obj.customerName+'</td>'+
                                        '<td>'+tooltipOnMultiRows(obj.accounts)+'</td>'+
                                        '<td>'+tooltipOnMultiRows(obj.projects)+'</td>'+
                                        '<td>'+(obj.autoGrant ? 'Yes' : 'No')+'</td>'+
                                        '<td>'+obj.members.join('<br/>')+'</td>'+
                                        '<td class="hide">'+obj.groupId+'</td>'+
                                    '</tr>';
					        } else if (isApprovalPage) {
                                content += 
					              '<tr ' + (idx==0?'class="firstRow"':'')+'>'+
                                        '<td><input type="checkbox"/></td>'+
                                        '<td>'+obj.handle+'</td>'+
                                        '<td>'+obj.email+'</td>'+
                                        '<td>'+obj.requestDate+'</td>'+
                                        '<td>'+obj.customerName+'</td>'+
                                        '<td class="hide">'+obj.invitationId+'</td>'+
                                    '</tr>';
                            } else {
                                content += 
					              '<tr ' + (idx==0?'class="firstRow"':'')+'>'+
                                        '<td>'+obj.groupName+'</td>'+
                                        '<td>'+obj.accessRight+'</td>'+
                                        '<td>'+obj.sentOn+'</td>'+
                                        '<td>'+obj.acceptedOrRejectedOn+'</td>'+
                                        '<td>'+obj.approvalProcessedOn+'</td>'+
                                    '</tr>';
                            }
					  });
                      $('.mainContent table tbody').html(content);                      
                      $('.mainContent table tbody tr:odd').addClass('even');
                      tooltipOnMultiRowsEvents();
                  }
                  
                  // show message modal
                  if (fromApproval) {
                      if (approveRejectData.approved) {
                        modalLoad("#approveModal"); 
                      } else {
                        modalLoad("#rejecResulttModal"); 
                      }
                  } else {
                      modalClose();
                  }
              },
              function(errorMessage) {
                  modalClose();
                  showServerError(errorMessage);
              });
          }
        });
    }
    
    // approve/reject approvals    
    function approveRejectApprove() {
        $.ajax({
          type: 'POST',
          url:  ctx+ "/group/approveRejectPendingUserAction",
          data: approveRejectData,
          cache: false,
          dataType: 'json',
          //async : false,
          beforeSend: modalPreloader,
          success: function (jsonResult) {
              handleJsonResult2(jsonResult,
              function(result) {
                  // refetch the pending approvals
                 filterItems(true, 1, 1);
              },
              function(errorMessage) {
                  modalClose();
                  showServerError(errorMessage);
              });
          }
        });
    }
    
    $('div.approvalButton a.triggerModal').click(function() {
        var approved = $(this).hasClass('approveButton');
        var invitationIds=[];
        $('#approvalsTbl tbody tr td input:checked').each(function() {
                var id = $(this).parent().parent().find('td:eq(5)').text();
            invitationIds.push(id);
        });
        if (invitationIds.length==0){
            showErrors("At least one row should be checked");
            return false;
        }
        approveRejectData = {approved:approved, invitationIds:invitationIds};
        if (!approved) {
            $('div.rejectTextArea textarea').val('');
            modalLoad("#rejectModal"); 
        } else {
            approveRejectApprove();   
        }            
    });

    //limit the user's input
    var rejectedTextMaxLen = 200;
    function limitedText(selector, max) {
        function limit(elem) {
            var val = elem.val().trim();
            if (val.length > max) {
                elem.val(val.substring(0, max));
            }
        }
        $(selector).keydown(function() {
            limit($(this));
        });
        $(selector).keyup(function() {
            limit($(this));
        });
    }
    limitedText('div.rejectTextArea textarea', rejectedTextMaxLen);
    
    $('#rejectModal a.triggerNoPreloaderModal').click(function() {
        var reason = $('div.rejectTextArea textarea').val().trim();
        if (reason.length > rejectedTextMaxLen) {
            showErrors("The length of reject reason should not be more than " + rejectedTextMaxLen + " characters");
            return false;
        }
        approveRejectData.rejectReason = reason;
        approveRejectApprove();
    });
    
    // the radio check 
    var groupName;
    var groupId;
    function getGroupNameAndId() {
        var checked = $('.userGroupSelectRadio:checked');
        if (checked.length != 1)return false;
        var link=checked.parent().next().find("a");
        groupName= link.html();
        groupId = link.attr("rel");
        return true;
    }
    $(".userGroupSelectRadio").live("click", function() {
        var groupIdOld = groupId;
        if (getGroupNameAndId() && groupId != groupIdOld)  { 
            // check groupId permission
            $.ajax({
              type: 'POST',
              url:  ctx+ "/group/checkUpdateRemovePermission",
              data: {groupId:groupId},
              cache: false,
              dataType: 'json',
              beforeSend: modalPreloader,
              success: function (jsonResult) {
                  handleJsonResult(jsonResult,
                  function(result) {
                      if (result.access == 'true') {
                          $(".updateGroupButton,.deleteGroupButton").show();  
                      } else {
                          $(".updateGroupButton,.deleteGroupButton").hide(); 
                      }                      
                  },
                  function(errorMessage) {
                      showServerError(errorMessage);
                  });
              }
            });
        }
    });
    $(".userGroupSelectRadio:first").trigger('click');
    
    // view/update/delete group buttons click event
    $(".viewGroupButton, .updateGroupButton, .deleteGroupButton").live('click',function() {
        if ($("#groupIdInput").length>0) {
            groupId=$("#groupIdInput").val();
            groupName=$("#groupNameInput").val();
        } else {
            if (!getGroupNameAndId()) {
                showErrors("One radio button of the user group should be checked");
                return;
            }
        }
        if ($(this).hasClass("deleteGroupButton")) {
            $("#deleteGroupModal div.noticeContent").html("Are you sure you want to delete group <span class='confirmGroupName'>"+
                groupName+"</span> ?");
            $("#deleteGroupConfirmModal div.noticeContent").html("<span class='confirmGroupName'>" + groupName+
                "</span> has been deleted.");
            modalLoad("#deleteGroupModal"); 
        } else {
            if ($(this).attr("href").indexOf("groupId=")>0) {
                window.location.href = $(this).attr("href");
            } else {
                window.location.href = $(this).attr("href")+'?groupId=' + groupId;
            }
        }
        return false;
    });
  
    // fire the delete group ajax
    $("#confirmDeleteGroupBtn").click(function() {
        $.ajax({
          type: 'POST',
          url:  ctx+ "/group/deleteGroupAction",
          data: {groupId:groupId},
          cache: false,
          dataType: 'json',
          beforeSend: modalPreloader,
          success: function (jsonResult) {
              handleJsonResult2(jsonResult,
              function(result) {
                  modalLoad("#deleteGroupConfirmModal"); 
              },
              function(errorMessage) {
                  modalClose();
                  showServerError(errorMessage);
              });
          }
        });
        return false;
    });
    
    // close the confirm modal and redirect to view user groups page
    $("#deleteGroupConfirmModal .closeModal").click(function() {          
          filterItems(true, 1, false);
          return false;
    });
    
    // validates groups and handles input
    function validatesGroupsAndHandles() {
        var groupIds = getCheckboxesValue("groups", 0, true);
        if (groupIds.length == 0) {
            showErrors("At least one Group must be selected");
            return false;
        }
        var handles = [];
        var mh = {};   
        var ok = true;     
        $("#moreMember dd input").each(function() {
            var handle = $(this).val().trim();
            if (handle.length > 0) {
                if (!checkStrLen(handle)) {
                    ok = false;
                    showErrors("Handle can't exceed 45 characters");
                    return false;
                }
                if (mh[handle]) {
                    ok = false;
                    showErrors("User " + handle + " shows multiple times");
                    return false;
                }
                handles.push(handle);
                mh[handle] = true;
            }
        });
        if (!ok)return false;
        if (handles.length == 0) {
            showErrors("At least one member handle must be set");
            return false;
        }
        var res= {groupIds:groupIds, handles:handles};
        return res;
    }
    
    // fire the send invitations ajax
    $("#sendInvitation").click(function() {
        var data = validatesGroupsAndHandles();
        if (!data)return;

        var groupIds = data.groupIds;
        var userIds = [];
        var hasSend = false;
        var groupIdx = 0;
        var text = "Sending invitation email(s) for group <span>1</span>/"+ groupIds.length +"... please wait, this may take a while.";
        $("#sendInvitationModal .modalBody .preloaderTips").html(text);


        $.ajax({
            type: 'POST',
            url:  ctx+"/group/validateUserHandle",
            data: {groupIds:groupIds, handles:data.handles},
            cache: false,
            dataType: 'json',
            beforeSend: modalPreloader,
            timeout: 600000,
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                        function(result) {
                            userIds = result.result;
                            if( groupIdx < groupIds.length) {
                                sendInvitationEmail();
                            }
                        },
                        function(errorMessage) {
                            showErrors(errorMessage);
                        });
            },
            error: function(xmlhttprequest, textstatus, message) {
                modalClose();
                showServerError("An unexpected error occurred: " + textstatus + ", " + message);
            }
        });


        function sendInvitationEmail() {
            $.ajax({
                type: 'POST',
                url:  ctx+"/group/sendInvitationAction",
                data: {groupIds:[groupIds[groupIdx]], handles:data.handles, userIds:userIds},
                cache: false,
                dataType: 'json',
                beforeSend: modalSendInvitation,
                timeout: 600000,
                success: function (jsonResult) {
                    handleJsonResult(jsonResult,
                            function(result) {
                                if(result.result != "nope") {
                                    hasSend = true;
                                }
                                if(++groupIdx < groupIds.length) {
                                    $("#sendInvitationModal .modalBody .preloaderTips span").text(groupIdx+1);
                                    sendInvitationEmail();
                                } else {
                                    if (!hasSend) {
                                        $(".noticeContentSendInviteWarn").text("No invitation email has been sent out, the members are already in the checked groups.");
                                        modalLoad("#sendInvitationWarnModal");
                                    } else {
                                        $(".noticeContentSendInviteConfirmation").text("Invitation emails have been sent to the new members added to the checked groups.");
                                        modalLoad("#sendInvitationConfirmModal");
                                    }
                                }
                            },
                            function(errorMessage) {
                                showServerError(errorMessage);
                            });
                },
                error: function(xmlhttprequest, textstatus, message) {
                    modalClose();
                    if (textstatus == "timeout") {
                        showErrors("There might be too many emails and the server is still sending invitation emails");
                    } else {
                        showServerError("An unexpected error occurred: " + textstatus + ", " + message);
                    }
                }
            });
        }
    });
  
    // fire the create new admin ajax
    $("div.createAdminButton a").click(function() {
        var handle = $('#newAdminHandle').val().trim();
        if (handle.length == 0) {
            showErrors("Member handle can't be empty");
            return false;
        }
        if (!checkStrLen(handle)) {
            showErrors("Member handle can't exceed 45 characters");
            return false;
        }
        var clientId = $('#clientIdSelect').val();
        if (clientId<=0) {
            showErrors("You must select a customer");
            return false;
        }
        
        $.ajax({
          type: 'POST',
          url:  ctx+ "/group/createCustomerAdminAction",
          data: {handle:handle, clientId:clientId},
          cache: false,
          dataType: 'json',
          beforeSend: modalPreloader,
          success: function (jsonResult) {
              handleJsonResult2(jsonResult,
              function(result) {
                  $('#createAdminModal noticeContent').html('Customer Adminstrator '+result.handle+' was successfully <br />created.');
                  modalLoad("#createAdminModal"); 
              },
              function(errorMessage) {
                  modalClose();
                  showServerError(errorMessage);
              });
          }
        });
        return false;
    });

    //Open the send invitation mail modal
    function modalSendInvitation() {
        modalLoad('#sendInvitationModal');    
    }
    
    //tooltips popup.
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
    
    $('#groupNameFieldRow .helpme').hover(function(){
    	showPopup(this, 'groupNameHelpMsg');
    }, function(){
    	$('#groupNameHelpMsg').hide();
    });

    $('#accessRightsFieldRow .helpme').hover(function(){
    	showPopup(this, 'accessRightsHelpMsg');
    }, function(){
    	$('#accessRightsHelpMsg').hide();
    });
    
    $('#customerNameFieldRow .helpme').hover(function(){
    	showPopup(this, 'customerNameHelpMsg');
    }, function(){
    	$('#customerNameHelpMsg').hide();
    });
    
    $('#billingAccountsFieldRow .helpme').hover(function(){
    	showPopup(this, 'billingAccountsHelpMsg');
    }, function(){
    	$('#billingAccountsHelpMsg').hide();
    });
    
    $('#projectsFieldRow .helpme').hover(function(){
    	showPopup(this, 'projectsHelpMsg');
    }, function(){
    	$('#projectsHelpMsg').hide();
    });
    
    $('#autoGrantPermissionRow .helpme').hover(function(){
    	showPopup(this, 'autoGrantPermissionHelpMsg');
    }, function(){
    	$('#autoGrantPermissionHelpMsg').hide();
    });
});
