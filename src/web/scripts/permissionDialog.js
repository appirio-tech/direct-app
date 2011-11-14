/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * Used to handle dialog UI process.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
$(function() {
  /**
   * Represents the URL to get users.
   */
  var GET_USER_URL = "getUser";

  /**
   * Create the ajax processor.
   */
  var processor = new js.topcoder.ajax.BufferedAjaxProcessor( {
    method : "get",
    url : GET_USER_URL,
    responseType : "text",
    onSuccess : function(processor) {
      hanldeUserResult(JSON.parse(processor.getResponseText()));
    }
  });

  /**
   * Handle user result, render to specified div.
   *
   * @param jsonResult
   *            the result
   */
  var hanldeUserResult = function hanldeUserResult(jsonResult) {
    var dialogId;
    var dialogAlias;

    if (!jsonResult['result']) {
      $.permission.showErrors("fail to get users");
    }
    jsonResult = jsonResult['result']['return'];
    if ($("#manageUserDialog").dialog("isOpen")) {
      dialogId = "#manageUserDialog";
      dialogAlias = "mu";
    } else {
      dialogId = "#addUserDialog";
      dialogAlias = "au";
    }
    var listDiv = $(dialogId + " .left .list");
        var alreadyUsed = '';
        $(dialogId + " .right .list .listItem").each(function() {
      alreadyUsed += ',' + $(this).attr("name") + ',';
    });

    for ( var i = 0; i < jsonResult.length; i++) {
            var handle = ',' + jsonResult[i].handle + ',';
            if (alreadyUsed.indexOf(handle) < 0) {
                var htmlToAdd = getListItemString(jsonResult[i], dialogId,
                        dialogAlias);
                listDiv.append(htmlToAdd);
            }
    }

    setListItemClickEventHanlde();
  };

  /**
   * Set click event handle for each list item.
   */
  var setListItemClickEventHanlde = function setListItemClickEventHanlde() {
    $(".ui-dialog-content .list .listItem").unbind("click");
    $(".ui-dialog-content .list .listItem").click(function() {
      $(this).toggleClass("active");
    });
  };

  /**
   * Get list item string with specified dialogId and dialogAlias.
   *
   * @param jsonObj
   *            the json object
   * @param dialogId
   *            the id of dialog used
   * @param dialogAlias
   *            the alias for dialog
   */
  var getListItemString = function getListItemString(jsonObj, dialogId,
      dialogAlias) {
    var searchText = $(dialogId + " .searchTxt").val();
    var userName = jsonObj.handle;
    searchText = userName.replace(searchText, "<span class='b'>"
        + searchText + "</span>");

    return "<div class='listItem' id='" + dialogAlias + "_l_"
        + jsonObj.userId + "' name='" + jsonObj.handle + "'>"
        + searchText + "</span>";
  };

  /**
   * Add projects to assign projects dialog.
   *
   * @param projects
   *            projects to add
   * @param searchText
   *            the text to search
   */
  var addProjectsToAssignProjectsDialog = function addProjectsToAssignProjectsDialog(
      projects, searchText) {
        var alreadyUsed = '';
        $("#addProjectDialogPm .right .list .listItem").each(function() {
      alreadyUsed += ',' + $(this).attr("name") + ',';
    });

    var listDiv = $("#addProjectDialogPm .left .list");
    for ( var pId in projects) {
      var pName = projects[pId].projectName;
            if (alreadyUsed.indexOf(',' + pName + ',') < 0) {
                var displayName = pName;
                displayName = displayName.replace(searchText, "<span class='b'>"
                        + searchText + "</span>");
                var htmlToAdd = "<div class='listItem' id='ap_l_" + pId
                        + "' name='" + pName + "'>" + displayName + "</span>";

                listDiv.append(htmlToAdd);
            }
    }
    setListItemClickEventHanlde();
  };

  /**
   * Handle select all event.
   */
  $(".left .rightTxt").click(function() {
    $(this).parent().parent().find(".list .listItem").each(function() {
      $(this).addClass("active");
    });
  });

  /**
   * Handle remove all event.
   */
  $(".right .rightTxt").click(function() {
    $(this).parent().parent().find(".list .listItem").each(function() {
      $(this).addClass("active");
    });
  });

  $.each([".right .rightTxt", ".middle .removeItem"], function(index, item) {
      $(item).click(function() {
        var rightDiv = $(this).parent().parent().parent().find("div.right");

          rightDiv.find(".listItem.active").each(function() {
            var leftDiv = $(this).parent().parent().parent().find(".left");
            var id = $(this).attr("id");
            var dstId = id.replace("_r_", "_l_");

            // add to left side
            if (leftDiv.find("#" + dstId).length == 0) {
              var item = $(this).clone();
              item.attr("id", dstId);
              item.toggleClass("active");
              leftDiv.find(".list").append(item);
            }

            // remove this
            $(this).remove();
          });

          setListItemClickEventHanlde();
      });
  });

  /**
   * Handle add items event.
   */
  $(".middle .addItem").click(function() {
    var leftDiv = $(this).parent().parent().find(".left");

    leftDiv.find(".listItem.active").each(function() {
      var rightDiv = $(this).parent().parent().parent().find(".right");
      var id = $(this).attr("id");
      var dstId = id.replace("_l_", "_r_");

      // add to right side
      if (rightDiv.find("#" + dstId).length == 0) {
        var item = $(this).clone();
        item.attr("id", dstId);
        item.toggleClass("active");
        rightDiv.find(".list").append(item);
      }

      // remove this
      $(this).remove();
    });

    setListItemClickEventHanlde();
  });

  /**
   * Pop modal window.
   */
  $.each( [ "manageUserDialog", "addUserDialog", "addProjectDialogPm" ],
      function(index, item) {
        $('#' + item).dialog( {
          autoOpen : false,
          height : 450,
          width : 580,
          modal : true,
          draggable : false,
          resizable : false
        });

        $('#' + item + ' .closeDialog').click(function() {
          $('#' + item).dialog("close");
          return false;
        });
      });

  /**
   * Handle save button click event.
   */
  $("#mu_save").click(function() {
    $("#manageUserDialog").dialog("close");
    $.permission.processAddUsersToProject();
  });

  /**
   * Handle save button click event.
   */
  $("#au_save").click(function() {
    $("#addUserDialog").dialog("close");
    $.permission.processAddUsers();
  });

  /**
   * Handle save button click event.
   */
  $("#ap_save").click(function() {
    $("#addProjectDialogPm").dialog("close");
    $.permission.processAssignProjects();
    pbutton_submit();
  });

  /**
   * Set click event handle.
   */
  $.each( [ "manageUserDialog", "addUserDialog" ], function(index, item) {
    $("#" + item + " .button1").click(function() {
      $("#" + item + " .left .list").empty();

      var userName = $("#" + item + " .searchTxt").val();
      processor.addSendingData("searchText=" + userName);
    });
  });

  /**
   * Add click event handle for add project dialog.
   */
  $("#addProjectDialogPm .button1").click(function() {
    $("#addProjectDialogPm .left .list").empty();

    var projectName = $("#addProjectDialogPm .searchTxt").val();
    if ($.trim(projectName) == "") {
      addProjectsToAssignProjectsDialog($.permission.projects, "");
    } else {
      var projects = {};
      var p = $.permission.projects;

      for ( var pId in p) {
        if (p[pId].projectName.indexOf(projectName) != -1) {
          projects[pId] = p[pId];
        }
      }

      addProjectsToAssignProjectsDialog(projects, projectName);
    }
  });


  /**
   * Add users for projects assign.
   */
  $("#ap_add").click(function() {
    $.permission.addMoreUsersDirect = false;

    // clear and open the dialog
    $.permission.clearAndOpenDialog("addUserDialog");

        $.each($.permission.currentUserMap, function(uId, handle) {
            var listDiv = $("#addUserDialog .right .list");
            var htmlToAdd = "<div name='" + handle + "' id='au_r_" + uId + "' class='listItem'>"
                + handle + "</div>";
            listDiv.append(htmlToAdd);
        });

        setListItemClickEventHanlde();
  });

  /**
   * Set the project side click event handle.
   */
  $(".permissions .firstItem").click(function() {
    $("#projects").show();
    $("#users").hide();
    $(this).addClass("on");
    $(".permissions .lastItem").removeClass("on");

    $.permission.currentTable = 0;
  })

  /**
   * Set the user side click event handle.
   */
  $(".permissions .lastItem").click(function() {
    $("#users").show();
    $("#projects").hide();
    $(this).addClass("on");
    $(".permissions .firstItem").removeClass("on");

    $.permission.currentTable = 1;
  })
});
