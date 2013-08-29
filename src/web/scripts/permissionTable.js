/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file is used to render permission data to page, and handle
 * event.
 *
 * <p>
 * Version 1.0.1 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Updated getReturnObj() method.</li>
 *     <li>Updated addProject() method.</li>
 *   </ol>
 * </p>
 *
 * Version 1.0.2 TC Direct UI Improvement Assembly 1 (BHCUI-38, BHCUI-70, BHCUI-98) Change notes:
 *  - Solve "checkbox exists when no data in Settings > Permissions"
 *  - User can't delete the permision of himself. When deleting other's permision, there is a dialog to make sure this action.
 *
 * Version 1.0.3 - Release Assembly - TC Direct UI Improvement Assembly 3
 * - Fix the li issue under error dialogue of notification page
 *
 * Version 1.1 - Release Assembly - TC Direct Cockpit Release One
 * - Saving the permissions via ajax instead of submitting a form.
 *
 * Version 1.2 - Release Assembly - TC Direct Cockpit Release Four
 * - Remove the user permission id when removing a user project permission to fix TCCC-4173.
 *
 * Version 2.0 - Release Assembly - TC Cockpit Project Report Permission
 * - Update the javascript codes to support the newly added project permission type: 'report'
 * 
 * Version 2.1 - Release Assembly - TopCoder Cockpit Direct UI Text and Layout Bugs Termination 1.0
 * - Update to use newly added modal.
 *
 * Version 2.2 - Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring
 * - Loads the permissions data when the permission setting page finishes loading.
 * 
 * Version 2.2.1 - Release Assembly - TopCoder Cockpit Direct UI Layout Bugs Termination 2.0
 * - Fix the permission setting page rendering.
 *
 * @author Veve, csy2012
 * @version 2.2.1
 */
//$(function() {
/**
   * Represents the URL to get project permission.
   */
var GET_PROJECT_PERMISSIONS_URL = "getProjectPermissions";

/**
   * Represents the URL to update project permission.
   */
var UPDATE_PROJECT_PERMISSION_URL = "updateProjectPermissions";

/**
   * Comparator of two div objects.
   */
$.fn.dataTableExt.afnSortData['dom-text'] = function (oSettings, iColumn) {
    var aData = [];
    $('td:eq(' + iColumn + ') div', oSettings.oApi._fnGetTrNodes(oSettings)).each(function () {
        aData.push($(this).html());
    });
    return aData;
}

$(document).ready(function(){
    $("#projects").after($("#permissions"));
    $("#permissions_wrapper").remove();
    $("#projects").remove();
    $("#permissions").wrap('<div id="projects"/>');


    requestPermissions();
    $(".paging_permission_button .next").removeClass("next");
})

/**
   * Create the ajax processor.
   */
function requestPermissions() {
    var processor = new js.topcoder.ajax.BufferedAjaxProcessor({
        method:"get",
        url:GET_PROJECT_PERMISSIONS_URL,
        responseType:"text",
        sendingData:"",
        onSuccess:function (processor) {
            handlePermissionJsonResult(JSON.parse(processor.getResponseText()));
            $("#permissions .applyForAll:gt(0)").remove();
    }
  });
}

/**
   * Handle json result, render it to the page.
   *
   * @param jsonResult
   *            json result
   */
var handlePermissionJsonResult = function handleJsonResult(jsonResult) {
    if (!jsonResult['result']) {
      $.permission.showErrors("fail to get project permissions");
    }

    jsonResult = jsonResult['result']['return'];
        if (jsonResult.length != 0) {
        $("#permissions thead").append('<tr class="applyForAll"><td class="markRed"></td><td class="checkbox"><input type="checkbox" class="selectUser"/></td><td class="checkbox"><input type="checkbox" class="selectUser"/></td><td class="checkbox"><input type="checkbox" class="selectUser"/></td><td class="checkbox"><input type="checkbox" class="selectUser"/></td><td></td></tr>');
    }
    for (var i = 0; i < jsonResult.length; i++) {
      jsonResult[i].tperm = false;
      jsonResult[i].rperm = false;
      jsonResult[i].wperm = false;
      jsonResult[i].fperm = false;
      if (jsonResult[i].permission == "write") {
        jsonResult[i].wperm = true;
      } else if (jsonResult[i].permission == "read") {
        jsonResult[i].rperm = true;
      } else if (jsonResult[i].permission == "report") {
          jsonResult[i].tperm = true;
      } else if (jsonResult[i].permission == "full") {
        jsonResult[i].fperm = true;
      }
      $.permission.addProject(jsonResult[i]);
    }
    $.permission.renderToHtml();
};

/**
   * Submit the form.
   */
//	$("#permission_submit").click(
function pbutton_submit() {

    if (!$.permission.tableCreateComplete) {
        $.permission.showErrors("fail to load data");
        return;
    }
    var ret = {};
    ret['result'] = {};
    ret['result']['return'] = {};
    ret['result']['return']['permissions'] = [];

    $.each($.permission.projects, function (pId, project) {
        var pName = project.projectName;
        var isStudio = project.studio;
        $.each(project.updateUserPermissions, function (uId, uP) {
            ret['result']['return']['permissions'].push(uP
                .getReturnObj(pId, pName, isStudio));
        });
    });

    var result = JSON.stringify(ret);
    var request = {permissionsJSON:result};

    modalPreloader();

    $.ajax({
        type:'POST',
        url:"updateProjectPermissions",
        data:request,
        cache:false,
        dataType:'json',
        success:function (jsonResult) {
            modalClose();
            handleJsonResult2(jsonResult,
                function (result) {
                    if (result) {
                        var data = result;
                        $.each(data, function (index, item) {
                            var projectId = item.projectId;
                            var userId = item.userId;
                            var permission = item.permission;
                            var permissionId = item.permissionId;
                            var removeTotalProject = item.removeTotalProject;

                            if($.permission.projects[projectId].userPermissions[userId])  {
                                $.permission.projects[projectId].userPermissions[userId].originalPperm = permission;
                                $.permission.projects[projectId].userPermissionIds[userId] = permissionId;
                            }

                            $.permission.projects[projectId].updateUserPermissions = {};


                            if (permission == "") {
                                // remove
                                $.permission.deleteUserOnProject(userId, projectId);
                            }

                            if (removeTotalProject == 'true') {
                                $("#permissions .checkPermissions tr").each(function () {
                                    if ($(this).hasClass('subTr')) {
                                        if ($(this).attr('id').indexOf('project' + projectId + 'user') != -1) {
                                            $(this).remove();
                                        }
                                    } else {
                                        if ($(this).find('div').attr('id').indexOf('project' + projectId) != -1) {
                                            $(this).remove();
                                        }
                                    }
                                });

                                $("#permissions .checkPermissions tr:not(.subTr)").each(function (index) {
                                    $(this).removeClass();
                                    if (index % 2 == 0) {
                                        $(this).attr('class', 'even');
                                    } else {
                                        $(this).attr('class', 'odd');
                                    }
                                });
                            }

                        });


                        showSuccessfulMessage("Permission settings have been updated successfully");
                    } else if (result.warning) {
                        showErrors(result.warning);
                    } else {
                        showErrors("Unknown response from the server side.");
                    }
                },
                function (errorMessage) {
                    showErrors(errorMessage);
                });
        }
    });


//    $("#permissionHiddenInput").val(result);
//    $("#permissionForm").attr("action", UPDATE_PROJECT_PERMISSION_URL);
//    document.forms['permissionForm'].submit();
}
//);

/*
   * Bind the click event of delete single user.
   */
$(".removeProject").live('click', function () {
    //It is current user.
    if ($(this).attr('userid') < 0) {
      return true;
    }

    var id = $(this).attr('userid');
        var userName = $.trim($(this).parent().parent().children(':first').find("div").html());

        showConfirmation("Do you really want to delete the user ?",
            "This will delete the user <span class='messageContestName'>" + userName + "</span> from the project. Please confirm you want to delete ?",
            "YES",
        function () {
          modalClose();
          $.permission.removeSingleUser(id);
          pbutton_submit();
          return true;
      }
        );


});

function showDeleteOneselfError() {
    initDialog('errorDialog', 300);
        showErrors("You cannot delete yourself in permission setting.");
    $("#errorDialog").parent().find(".ui-dialog-titlebar").show();
}

/**
   * The permission object, used to store information and handle event.
   */
$.permission = {
    /**
     * The classed of group.
     */
    GROUP_CLASS:[ "expand", "collapse" ],

    /**
     * The images used for sort.
     */
    SORT_IMAGES:[ "/images/down.png", "/images/up.png" ],

    /**
     * The class name for hidden.
     */
    HIDDEN_CLASS:"hide",

    /**
     * The report permission,
     */
    PERMISSION_TYPE_T:"t",

    /**
     * The read permission.
     */
    PERMISSION_TYPE_R:"r",

    /**
     * The write permission.
     */
    PERMISSION_TYPE_W:"w",

    /**
     * The full permission.
     */
    PERMISSION_TYPE_F:"f",

    /**
     * Represents the project table.
     */
    TABLE_TYPE_PROJECTS:"projects",

    /**
     * Represents the user table.
     */
    TABLE_TYPE_USERS:"users",

    /**
     * Indicates the head type of source.
     */
    SRC_TYPE_ALL:"all",

    /**
     * Indicates the main type of source.
     */
    SRC_TYPE_MAIN:"main",

    /**
     * Indicates the detail type of source.
     */
    SRC_TYPE_DETAIL:"detail",

    /**
     * The project table object.
     */
    oProjectTable:{},

    /**
     * The user table object.
     */
    oUserTable:{},

    /**
     * Used to store the project objects.
     */
    projects:{},

    /**
     * Used to store the user objects.
     */
    users:{},

    /**
     * Used to store current users.
     */
    currentUserMap:{},

    /**
     * Used to indicate current project.
     */
    currentProjectId:Number,

    /**
     * Used to store current user id.
     */
    currentUserId:Number,

    /**
     * Used to indicate which table is shown.
     */
    currentTable:0,

    /**
     * Used to indicate whether the table has been created.
     */
    tableCreateComplete:false,

    /**
     * Used to indicate how the add more users dialog created.
     */
    addMoreUsersDirect:false,

    /**
     * Add quota for text.
     *
     * @param text
     *            the text to add quotas
     */
    addQuo:function (text) {
      return "\"" + text + "\"";
    },

    /**
     * Show errors.
     *
     * @param errMsg
     *            the error to show
     */
    showErrors:function (errMsg) {
      showErrors("Error occurs: " + errMsg);
    },

    /**
     * Get string to represent checkbox.
     *
     * @param tableType
     *            the type of table
     * @param srcType
     *            the type of source
     * @param permissionType
     *            the type of permission
     * @param id
     *            the id
     * @param checked
     *            whether the checkbox is checked
     * @param mainId
     *            the main id, optional
     */
    getCheckboxString:function (tableType, srcType, permissionType, id, checked, mainId) {
      var ret = "";
      ret += "<td class='checkbox'><input type='checkbox' class='selectUser' onclick='$.permission.handleCheckboxEvent(";
      ret += this.addQuo(tableType) + "," + this.addQuo(srcType) + ","
          + this.addQuo(permissionType) + "," + id;
      if (mainId != undefined) {
        ret += "," + mainId;
      }
      ret += ")' ";
      if (checked) {
        ret += "checked='yes'";
      }
      ret += "/></td>\n";

      return ret;
    },

    /**
     * Used to store user permission.
     *
     * @param projectJson
     *            the project json object
     */
    userPermission:function (projectJson) {
      this.userId = projectJson.userId;
      this.handle = projectJson.handle;
      this.rperm = projectJson.rperm;
      this.wperm = projectJson.wperm;
      this.fperm = projectJson.fperm;
      this.tperm = projectJson.tperm;

      this.html;

      /**
       * Get pperm string from tperm, rperm, wperm and fperm.
       *
       * @param tperm
       *            the report permission
       * @param rperm
       *            the read permission
       * @param wperm
       *            the write permission
       * @param fperm
       *            the full permission
       * @return the pperm
       */
      this.getPperm = function getPperm(tperm, rperm, wperm, fperm) {
          var pperm;

          if (fperm) {
              pperm = "full";
          } else {
              if (wperm) {
                  pperm = "write";
              } else if (rperm) {
                  pperm = "read";
              } else if (tperm) {
                  pperm = "report";
              } else {
                  pperm = "";
              }
          }

          return pperm;
      };

        /**
       * The orignal pperm.
       */
      this.originalPperm = this.getPperm(this.tperm, this.rperm, this.wperm,
          this.fperm);

      /**
       * Set permission.
       *
       * @param tperm
       *            the report permission
       * @param rperm
       *            the read permission
       * @param wperm
       *            the write permission
       * @param fperm
       *            the full permission
       * @return whether this user permission is changed
       */
      this.setPermission = function setPermission(tperm, rperm, wperm, fperm) {
        this.tperm = tperm;
        this.rperm = rperm;
        this.wperm = wperm;
        this.fperm = fperm;

        this.toHtml();
        var changed = !(this.getPperm(this.tperm, this.rperm, this.wperm,
            this.fperm) == this.originalPperm);

        return changed;
      };

      /**
       * Generate html.
       *
       * @param projectId
       *            the project id.
       * @return generated html
       */
      this.toHtml = function toHtml(projectId) {
          var p = $.permission;
          var combileId = "p_project" + projectId + "userPermission"
              + this.userId;
          var html = "";
          html += "<tr id='" + combileId + "' class='group" + projectId
              + " subTr hide'>\n";
          html += "<td><a href='javascript:$.permission.deleteUserOnProject("
              + this.userId;
          html += ","
              + projectId
              + ");pbutton_submit();' class='subgroup'><img src='/images/remove.png' alt='' />";
          html += " &nbsp;" + this.handle + "</a></td>\n";

          html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_T, this.userId,
              this.tperm, projectId);
          html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_R, this.userId,
              this.rperm, projectId);
          html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_W, this.userId,
              this.wperm, projectId);
          html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_F, this.userId,
              this.fperm, projectId);

          html += "<td ></td>\n";
          html += "</tr>\n";

          this.html = html;
          return html;
      };

      /**
       * Get return object.
       *
       * @param projectId
       *            the project id
       * @param projectName
       *            the project name
       */
      this.getReturnObj = function getReturnObj(projectId, projectName, studio) {
        var pperm = this.getPperm(this.tperm, this.rperm, this.wperm, this.fperm);

        return {
                userId:this.userId,
                handle:this.handle,
                permission:pperm,
                projectId:projectId,
                projectName:projectName,
                studio:studio,
                userPermissionId:$.permission.projects[projectId].userPermissionIds[this.userId] ? $.permission.projects[projectId].userPermissionIds[this.userId] : -1
        };
      };
    },

    /**
     * Used to store project permission.
     *
     * @param projectJson
     *            the project json object
     */
    projectPermission:function (projectJson) {
      this.projectId = projectJson.projectId;
      this.projectName = projectJson.projectName;
      this.studio = projectJson.studio;
      this.tperm = projectJson.tperm;
      this.rperm = projectJson.rperm;
      this.wperm = projectJson.wperm;
      this.fperm = projectJson.fperm;
      this.html;

      /**
       * Set permission.
       *
       * @param tperm
       *            the report permission
       * @param rperm
       *            the read permission
       * @param wperm
       *            the write permission
       * @param fperm
       *            the full permission
       */
      this.setPermission = function setPermission(tperm, rperm, wperm, fperm) {
        this.tperm = tperm;
        this.rperm = rperm;
        this.wperm = wperm;
        this.fperm = fperm;

        this.toHtml();
      };

      /**
       * Generate html.
       *
       * @param userId
       *            the userId
       * @return generate html
       */
      this.toHtml = function toHtml(userId) {
          var p = $.permission;
          var combileId = "u_user" + userId + "projectPermission"
              + this.projectId;
          var html = "";
          html += "<tr id='" + combileId + "' class='group" + userId
              + " subTr hide'>\n";
          html += "<td><a href='javascript:$.permission.deleteUserOnProject("
              + userId;
          html += ","
              + this.projectId
              + ");pbutton_submit();' class='subgroup'><img src='/images/remove.png' alt='' />";
          html += " &nbsp;" + this.projectName + "</a></td>\n";

          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_T, this.projectId,
              this.tperm, userId);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_R, this.projectId,
              this.rperm, userId);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_W, this.projectId,
              this.wperm, userId);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_DETAIL, p.PERMISSION_TYPE_F, this.projectId,
              this.fperm, userId);

          html += "<td ></td>\n";
          html += "</tr>\n";

          this.html = html;
          return html;
      };
    },

    /**
     * The project used for rendering project table.
     *
     * @param projectJson
     *            the json project
     */
    project:function (projectJson) {
      this.projectId = projectJson.projectId;
      this.projectName = projectJson.projectName;
      this.studio = projectJson.studio;
      this.userPermissions = {};
      this.updateUserPermissions = {};
      this.userPermissionsHtml;
      this.groupState = 0;
      this.html;
      this.tperm = true;
      this.rperm = true;
      this.wperm = true;
      this.fperm = true;

      this.userPermissionIds = {};

      /**
       * Add user permission.
       *
       * @param uPermission
       *            the user permission to add
       */
      this.addUserPermissions = function addUserPermissions(uPermission) {
        if (this.userPermissions[uPermission.userId] == null) {
          this.userPermissions[uPermission.userId] = uPermission;
          // add a row to table
        }

        this.setSingleUserPermission(uPermission.userId, uPermission.tperm,
                uPermission.rperm, uPermission.wperm,
                uPermission.fperm);
      };

      /**
       * Generate html.
       *
       * @return generated html
       */
      this.toHtml = function toHtml() {
        var p = $.permission;
        var html = "<tr>\n";
        html += "<td><div class='group expand' onclick='$.permission.changePermissionHideGroup(this, \"";
        html += this.projectId;
        html += "\");' ";
        html += "id='p_project" + this.projectId + "'>";
        html += this.projectName;
        html += "</div></td>\n";

        html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
            p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_T, this.projectId,
            this.tperm);
        html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
            p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_R, this.projectId,
            this.rperm);
        html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
            p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_W, this.projectId,
            this.wperm);
        html += p.getCheckboxString(p.TABLE_TYPE_PROJECTS,
            p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_F, this.projectId,
            this.fperm);
        html += "<td class='checkbox'><a href='javascript:$.permission.handleAddUserClick(";
        html += this.projectId + ")' class='addUser'></a></td>\n";

        html += "</tr>\n";

        this.html = html;
        this.getUserPermissionsHtml();

        return this.html;
      };

      /**
       * Get user permission html.
       */
      this.getUserPermissionsHtml = function getUserPermissionsHtml() {
        this.userPermissionsHtml = "";

            for (var userId in this.userPermissions) {
          this.userPermissionsHtml += this.userPermissions[userId]
              .toHtml(this.projectId);
        }
      };

      /**
       * Set single user permission.
       *
       * @param userId
       *            the user id
       * @param tperm
       *            the report permission
       * @param rperm
       *            the read permission
       * @param wperm
       *            the write permission
       * @param fperm
       *            the full permission
       * @param notChangeProjectRow
       *            not to change the project row, optional
       */
        this.setSingleUserPermission = function setSingleUserPermission(userId, tperm, rperm, wperm, fperm, notChangeProjectRow) {
        if (this.userPermissions[userId] == null) {
          return;
        }
        var changed = this.userPermissions[userId].setPermission(tperm, rperm,
            wperm, fperm);

        if (changed) {
          this.updateUserPermissions[userId] = this.userPermissions[userId];
        } else if (this.updateUserPermissions[userId] != null) {
          delete this.updateUserPermissions[userId];
        }

        this.getUserPermissionsHtml();
        if (!notChangeProjectRow) {
          this.updateMainPermission();
        } else {
          var selectString = "#p_project" + this.projectId
              + "userPermission" + userId;
          var tr = $(selectString);
          $.permission.setTrCheckStatus(tr, tperm, rperm, wperm, fperm);
        }

        if ($.permission.tableCreateComplete
            && $.permission.currentTable == 0) {
          var user = $.permission.users[userId];
          user.setSingleProjectPermission(this.projectId, tperm, rperm,
              wperm, fperm, true);
          user.updateMainPermission();
        }
      };

      /**
       * Update the project row, set checked if all related user rows are
       * selected.
       */
      this.updateMainPermission = function updateMainPermission() {
          this.tperm = true;
          this.rperm = true;
          this.wperm = true;
          this.fperm = true;

          var mark = true;
          var hasUser = false;
          for (var userId in this.userPermissions) {
              hasUser = true;
              var uP = this.userPermissions[userId];

              this.tperm = this.tperm && uP.tperm;
              this.rperm = this.rperm && uP.rperm;
              this.wperm = this.wperm && uP.wperm;
              this.fperm = this.fperm && uP.fperm;

              if (!this.tperm && !this.rperm && !this.wperm && !this.fperm) {
                  mark = false;
                  break;
              }
          }

          if (!hasUser) {
              this.tperm = false;
              this.rperm = false;
              this.wperm = false;
              this.fperm = false;
          }

          var selectString = "#p_project" + this.projectId;
          var tr = $(selectString).parent().parent();
          $.permission.setTrCheckStatus(tr, this.tperm, this.rperm, this.wperm, this.fperm);

          $.permission.updateAllPermission();
      };

      /**
       * Set the project row, update all related user rows.
       *
       * @param permissionType
       *            the permission type
       * @param notUpdateAllPermission
       *            not to change the head row, optional
       */
        this.setMainPermission = function setMainPermission(permissionType, notUpdateAllPermission) {
            if (permissionType == $.permission.PERMISSION_TYPE_T) {
                this.tperm = !this.tperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_R) {
                this.rperm = !this.rperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_W) {
                this.wperm = !this.wperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_F) {
                this.fperm = !this.fperm;
            }

            for (var userId in this.userPermissions) {
                var uP = this.userPermissions[userId];

                if (permissionType == $.permission.PERMISSION_TYPE_T) {
                    this.setSingleUserPermission(userId, this.tperm, uP.rperm,
                        uP.wperm, uP.fperm, true);
                } else if (permissionType == $.permission.PERMISSION_TYPE_R) {
                    this.setSingleUserPermission(userId, uP.tperm, this.rperm,
                        uP.wperm, uP.fperm, true);
                } else if (permissionType == $.permission.PERMISSION_TYPE_W) {
                    this.setSingleUserPermission(userId, uP.tperm, uP.rperm,
                        this.wperm, uP.fperm, true);
                } else if (permissionType == $.permission.PERMISSION_TYPE_F) {
                    this.setSingleUserPermission(userId, uP.tperm, uP.rperm,
                        uP.wperm, this.fperm, true);
                }
            }

            if (!notUpdateAllPermission) {
                $.permission.updateAllPermission();
            }
      };

    },

    /**
     * The user object, used for rendering the user table.
     *
     * @param projectJson
     *            the project json object
     */
    user:function (projectJson) {
      this.userId = projectJson.userId;
      this.handle = projectJson.handle;
      this.projectPermissions = {};
      this.projectPermissionsHtml;
      this.groupState = 0;
      this.html;
      this.tperm;
      this.rperm;
      this.wperm;
      this.fperm;

      /**
       * Add project permission.
       *
       * @param pPermission
       *            the project permission to add
       */
        this.addProjectPermissions = function addProjectPermissions(pPermission) {
        if (this.projectPermissions[pPermission.projectId] == null) {
          this.projectPermissions[pPermission.projectId] = pPermission;
        }

        this
            .setSingleProjectPermission(pPermission.projectId, pPermission.tperm,
                pPermission.rperm, pPermission.wperm,
                pPermission.fperm);
      };

      /**
       * Generate html.
       *
       * @return generated html
       */
      this.toHtml = function toHtml() {
        var p = $.permission;
        var html = "<tr>\n";
        html += "<td class='permCol'>";
        html += "<input type='checkbox' class='selectUser' name='"
            + this.handle + "'/>";
        html += "<div class='group expand' onclick='$.permission.changePermissionHideGroup(this, \"";
        html += this.userId;
        html += "\");' ";
        html += "id='u_user" + this.userId + "'>";
        html += this.handle;
        html += "</div></td>\n";

          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_T, this.userId,
              this.tperm);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_R, this.userId,
              this.rperm);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_W, this.userId,
              this.wperm);
          html += p.getCheckboxString(p.TABLE_TYPE_USERS,
              p.SRC_TYPE_MAIN, p.PERMISSION_TYPE_F, this.userId,
              this.fperm);
          html += "<td class='checkbox'>";
          html += "<a href='javascript:$.permission.handleAssignProjectClick(";
          html += this.userId + ")' class='addProject'></a>";

        // Check whether it is current user
        var currentUser = $(".helloUser li:first a").html();
            if (currentUser == this.handle) {
          html += "<a userid=-1 href=\"javascript:showDeleteOneselfError()\" class='removeProject'></a>";
        } else {
          html += "<a userid = ";
          html += this.userId + " href=\"javascript:void(0)\" class='removeProject'></a>";
        }

        html += "</tr>";

        html += "</tr>\n";

        this.html = html;
        this.getProjectPermissionsHtml();

        return this.html;
      };

      /**
       * Get project permission html.
       */
      this.getProjectPermissionsHtml = function getProjectPermissionsHtml() {
        this.projectPermissionsHtml = "";

            for (var pId in this.projectPermissions) {
          this.projectPermissionsHtml += this.projectPermissions[pId]
              .toHtml(this.userId);
        }
      };

      /**
       * Set single project permission.
       *
       * @param projectId
       *            the project id
       * @param tperm
       *            the report permission
       * @param rperm
       *            the read permission
       * @param wperm
       *            the write permission
       * @param fperm
       *            the full permission
       * @param notChangeProjectRow
       *            not to change the project row, optional
       */
        this.setSingleProjectPermission = function setSingleProjectPermission(projectId, tperm, rperm, wperm, fperm, notChangeUserRow) {
        if (this.projectPermissions[projectId] == null) {
          return;
        }
        this.projectPermissions[projectId].setPermission(tperm, rperm, wperm,
            fperm);

        this.getProjectPermissionsHtml();
        if (!notChangeUserRow) {
          this.updateMainPermission();
        } else {
          var selectString = "#u_user" + this.userId
              + "projectPermission" + projectId;
          var tr = $(selectString);
          $.permission.setTrCheckStatus(tr, tperm, rperm, wperm, fperm);
        }

        if ($.permission.tableCreateComplete
            && $.permission.currentTable == 1) {
          var project = $.permission.projects[projectId];
          project.setSingleUserPermission(this.userId, tperm, rperm, wperm,
              fperm, true);
          project.updateMainPermission();
        }
      };

      /**
       * Update the user row, set checked if all related project rows are
       * selected.
       */
      this.updateMainPermission = function updateMainPermission() {
        this.tperm = true;
        this.rperm = true;
        this.wperm = true;
        this.fperm = true;

        var mark = true;
        var hasProject = false;
            for (var projectId in this.projectPermissions) {
          hasProject = true;
          var pP = this.projectPermissions[projectId];

          this.tperm = this.tperm && pP.tperm;
          this.rperm = this.rperm && pP.rperm;
          this.wperm = this.wperm && pP.wperm;
          this.fperm = this.fperm && pP.fperm;

          if (!this.tperm && !this.rperm && !this.wperm && !this.fperm) {
            mark = false;
            break;
          }
        }

        if (!hasProject) {
          this.tperm = false;
          this.rperm = false;
          this.wperm = false;
          this.fperm = false;
        }
        var selectString = "#u_user" + this.userId;
        var tr = $(selectString).parent().parent();
        $.permission.setTrCheckStatus(tr, this.tperm, this.rperm, this.wperm, this.fperm);

        $.permission.updateAllPermission();
      };

      /**
       * Set the user row, update all related project rows.
       *
       * @param permissionType
       *            the type of permission
       * @param notUpdateAllPermission
       *            not update head row, optional
       */
        this.setMainPermission = function setMainPermission(permissionType, notUpdateAllPermission) {
            if (permissionType == $.permission.PERMISSION_TYPE_T) {
                this.tperm = !this.tperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_R) {
                this.rperm = !this.rperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_W) {
                this.wperm = !this.wperm;
            }
            if (permissionType == $.permission.PERMISSION_TYPE_F) {
                this.fperm = !this.fperm;
            }

            for (var projectId in this.projectPermissions) {
                var pP = this.projectPermissions[projectId];

                if (permissionType == $.permission.PERMISSION_TYPE_T) {
                    this.setSingleProjectPermission(projectId, this.tperm, pP.rperm,
                        pP.wperm, pP.fperm, true);
                }
                else if (permissionType == $.permission.PERMISSION_TYPE_R) {
                    this.setSingleProjectPermission(projectId, pP.tperm, this.rperm,
                        pP.wperm, pP.fperm, true);
                } else if (permissionType == $.permission.PERMISSION_TYPE_W) {
                    this.setSingleProjectPermission(projectId, pP.tperm, pP.rperm,
                        this.wperm, pP.fperm, true);
                } else if (permissionType == $.permission.PERMISSION_TYPE_F) {
                    this.setSingleProjectPermission(projectId, pP.tperm, pP.rperm,
                        pP.wperm, this.fperm, true);
                }
            }

            if (!notUpdateAllPermission) {
                $.permission.updateAllPermission();
            }
        };
    },

    /**
     * Update the permissions in first row, set checked if all below rows
     * are checked.
     */
    updateAllPermission:function () {
        var tperm = true;
        var rperm = true;
        var wperm = true;
        var fperm = true;
        var tr;

        // update project table
        $("#projects .group").each(function () {
            var p = $.permission;
            var projectId = $(this).attr("id");
            projectId = projectId.substring("p_project".length);

            tperm = tperm && p.projects[projectId].tperm;
            rperm = rperm && p.projects[projectId].rperm;
            wperm = wperm && p.projects[projectId].wperm;
            fperm = fperm && p.projects[projectId].fperm;
        });
        tr = $("#projects .applyForAll");
        $.permission.setTrCheckStatus(tr, tperm, rperm, wperm, fperm);

        // update user table
        tperm = true;
        rperm = true;
        wperm = true;
        fperm = true;
        $("#users .group").each(function () {
            var p = $.permission;
            var userId = $(this).attr("id");
            userId = userId.substring("u_user".length);

            tperm = tperm && p.users[userId].tperm
            rperm = rperm && p.users[userId].rperm;
            wperm = wperm && p.users[userId].wperm;
            fperm = fperm && p.users[userId].fperm;
        });
        tr = $("#users .applyForAll");
        $.permission.setTrCheckStatus(tr, tperm, rperm, wperm, fperm);
    },

    /**
     * Set the head row, update all related project/user rows.
     *
     * @param permissionType
     *            the permission type
     * @param checked
     *            the state of checkbox
     */
    setAllPermission:function (permissionType, checked) {
      if (this.currentTable == 0) {
        $("#projects .group")
            .each(
                function () {
                  var p = $.permission;
                  var projectId = $(this).attr("id");
                  projectId = projectId
                      .substring("p_project".length);
                  var project = p.projects[projectId];
                  var tr = $(this).parent().parent();
                  var inputs = tr.find("input");

                    if (permissionType == p.PERMISSION_TYPE_T
                        && project.tperm != checked) {
                        $(inputs[0]).attr("checked", checked);
                        project.setMainPermission(
                            permissionType, true);
                    }
                  else if (permissionType == p.PERMISSION_TYPE_R
                      && project.rperm != checked) {
                    $(inputs[1]).attr("checked", checked);
                    project.setMainPermission(
                        permissionType, true);
                  } else if (permissionType == p.PERMISSION_TYPE_W
                      && project.wperm != checked) {
                    $(inputs[2]).attr("checked", checked);
                    project.setMainPermission(
                        permissionType, true);
                  } else if (permissionType == p.PERMISSION_TYPE_F
                      && project.fperm != checked) {
                    $(inputs[3]).attr("checked", checked);
                    project.setMainPermission(
                        permissionType, true);
                  }
                });
      } else {
        $("#users .group").each(
                function () {
                    var p = $.permission;
                    var userId = $(this).attr("id");
                    userId = userId.substring("u_user".length);
                    var user = p.users[userId];
                    var tr = $(this).parent().parent();
                    var inputs = tr.find("input");

                    if (permissionType == p.PERMISSION_TYPE_T
                        && user.tperm != checked) {
                        $(inputs[1]).attr("checked", checked);
                        user.setMainPermission(permissionType, true);
                    }
                    else if (permissionType == p.PERMISSION_TYPE_R
                        && user.rperm != checked) {
                        $(inputs[2]).attr("checked", checked);
                        user.setMainPermission(permissionType, true);
                    } else if (permissionType == p.PERMISSION_TYPE_W
                        && user.wperm != checked) {
                        $(inputs[3]).attr("checked", checked);
                        user.setMainPermission(permissionType, true);
                    } else if (permissionType == p.PERMISSION_TYPE_F
                        && user.fperm != checked) {
                        $(inputs[4]).attr("checked", checked);
                        user.setMainPermission(permissionType, true);
                    }
                });
      }
    },

    /**
     * Delete user on project group.
     *
     * @param userId
     *            the user id
     * @param projectId
     *            the project id
     */
    deleteUserOnProject:function (userId, projectId) {
      // update project table
      var project = this.projects[projectId];

      // cancel all permissions and delete it
      project.setSingleUserPermission(userId, false, false, false, false);
      delete project.userPermissions[userId];
      delete project.userPermissionIds[userId];
      project.getUserPermissionsHtml();

      // remove the row
      var selectString = "#p_project" + projectId + "userPermission"
          + userId;
      $(selectString).remove();

      // update the project row
      project.updateMainPermission();

      // update user table
      var user = this.users[userId];

      // cancel all permissions and delete it
      user.setSingleProjectPermission(projectId, false, false, false, false);
      delete user.projectPermissions[projectId];
      user.getProjectPermissionsHtml();

      // remove the row
      var selectString = "#u_user" + userId + "projectPermission"
          + projectId;
      $(selectString).remove();

      // update the user row
      user.updateMainPermission();
    },

    /**
     * Remove a single user.
     *
     * @param userId
     *            the user id
     */
    removeSingleUser:function (userId) {
      var user = this.users[userId];

        for (var pId in user.projectPermissions) {
        var project = user.projectPermissions[pId];
        this.deleteUserOnProject(userId, project.projectId);
      }

      // delete the user row
      var userDiv = $("#u_user" + userId);
      var tr = userDiv.parent().parent();
      this.oUserTable.fnDeleteRow(tr[0]);

      delete this.users[userId];
      this.initTable();
    },

    /**
     * Remove users.
     */
    removeUsers:function () {
        $("#users .group").each(function () {
        if ($($(this).parent().find("input")[0]).attr("checked")) {
          var id = $(this).attr("id");
          id = id.substring("u_user".length);

          $.permission.removeSingleUser(id);
        }
      });
    },

    /**
     * Clear and open specified dialog.
     *
     * @param dialogId
     *            the id of dialog
     */
    clearAndOpenDialog:function (dialogId) {
        $("#" + dialogId + " .list").each(function () {
        $(this).empty();
      });
      $("#" + dialogId + " .searchTxt").val("");
      $("#" + dialogId).dialog("open");
    },
    
    /**
     * Show Specified modal window.
     * @param modalId
     *            the id of modal.
     */
    showModalWindow:function (modalId) {
        $("#" + modalId + " .list").each(function() {
            $(this).empty();
        });
        $("#" + modalId + " .searchText").val("");
        modalPreloader2('#' + modalId);
    },

    /**
     * Handle add user click event.
     *
     * @param projectId
     *            the project id
     */
    handleAddUserClick:function (projectId) {
      // clear and open the dialog
      //this.clearAndOpenDialog("manageUserDialog");
      this.showModalWindow("userManageModal");

      // set current project id
      this.currentProjectId = projectId;

        $.each($.permission.projects[projectId].userPermissions, function (index, item) {
          //var listDiv = $("#manageUserDialog .right .list");
          var listDiv = $("#userManageModal .right .list");
          var htmlToAdd = "<div name='" + item.handle + "' id='mu_r_" + index + "' class='listItem'>"
              + item.handle + "</div>";
          listDiv.append(htmlToAdd);
      });

      $(".ui-dialog-content .list .listItem").unbind("click");
        $(".ui-dialog-content .list .listItem").click(function () {
          $(this).toggleClass("active");
        });
      $("#userManageModal .list .listItem").click(function () {
          $(this).toggleClass("active");
      });
    },

    /**
     * The assign project click event.
     *
     * @param userId
     *            the user id
     */
    handleAssignProjectClick:function (userId) {
      // clear and open the dialog
      //this.clearAndOpenDialog("addProjectDialogPm");
      this.showModalWindow("assignProjectModal");

      // initiate the currentUserList
      this.currentUserMap = {};
      this.currentUserMap[userId] = this.users[userId].handle;

        $.each($.permission.users[userId].projectPermissions, function (index, item) {
          //var listDiv = $("#addProjectDialogPm .right .list");
          var listDiv = $("#assignProjectModal .right .list");
          var htmlToAdd = "<div name='" + item.projectName + "' id='ap_r_" + index + "' class='listItem'>"
              + item.projectName + "</div>";
          listDiv.append(htmlToAdd);
      });

      /*
      $(".ui-dialog-content .list .listItem").unbind("click");
        $(".ui-dialog-content .list .listItem").click(function () {
          $(this).toggleClass("active");
        });
      */
      $("#assignProjectModal .list .listItem").click(function() {
          $(this).toggleClass("active");
      });
    },

    /**
     * Add more users.
     */
    addMoreUsers:function () {
      this.addMoreUsersDirect = true;

      // clear and open the dialog
      //this.clearAndOpenDialog("addUserDialog");
      this.showModalWindow("addMoreUsersModal");
    },

    /**
     * Handle add user process.
     */
    processAddUsers:function () {
        //$("#addUserDialog .right .list .listItem").each(function () {
        $("#addMoreUsersModal .right .list .listItem").each(function () {
        var userId = $.permission.retrieveId(this, "au_r_");
        var handle = $(this).attr("name");

        if ($.permission.addMoreUsersDirect) {
          $.permission.addUserIfNotExist(userId, handle);
        } else {
          $.permission.currentUserMap[userId] = handle;
        }
      });
    },

    /**
     * Handle add user to project process.
     */
    processAddUsersToProject:function () {
      //$("#manageUserDialog .right .list .listItem")
      $("#userManageModal .right .list .listItem")
          .each(
            function () {
                var project = $.permission.projects[$.permission.currentProjectId];
                var userId = $.permission.retrieveId(this,
                    "mu_r_");
                var handle = $(this).attr("name");

                var newUser = false;
                var uP = project.userPermissions[userId];
                if (uP == null || typeof(uP) == "undefined") {
                  newUser = true;
                }
                $.permission.addUserProjectPair(userId, handle,
                    project.projectId);
                if (newUser) {
                  var JSON = new Object();
                  JSON['userId'] = userId;
                  JSON['handle'] = handle;
                  JSON['tperm'] = false;
                  JSON['rperm'] = false;
                  JSON['wperm'] = false;
                  JSON['fperm'] = false;
                  uPermission = new $.permission.userPermission(JSON);
                  project.addUserPermissions(uPermission);
                                              project.updateUserPermissions[uPermission.userId] = uPermission;
                }
              });


        $.each($.permission.projects[$.permission.currentProjectId].userPermissions, function (index, item) {
                if ($("#mu_r_" + index).length == 0) {
                    $.permission.deleteUserOnProject(index, $.permission.currentProjectId);
                }
            });
    },

    /**
     * Handle assign projects process.
     */
    processAssignProjects:function () {
        //$("#addProjectDialogPm .right .list .listItem").each(function () {
        $("#assignProjectModal .right .list .listItem").each(function() {
        var pId = $.permission.retrieveId(this, "ap_r_");

            $.each($.permission.currentUserMap, function (uId, handle) {
          var newUser = false;
          var project = $.permission.projects[pId];
          var uP = project.userPermissions[uId];
                if (uP == null || typeof(uP) == "undefined") {
            newUser = true;
          }
          $.permission.addUserProjectPair(uId, handle, pId);
                if (newUser) {
            var JSON = new Object();
            JSON['userId'] = uId;
            JSON['handle'] = handle;
            JSON['tperm'] = true;
            JSON['rperm'] = false;
            JSON['wperm'] = false;
            JSON['fperm'] = false;
            uPermission = new $.permission.userPermission(JSON);
            project.addUserPermissions(uPermission);
          }
        });
      });

        $.each($.permission.currentUserMap, function (uId, handle) {
            $.each($.permission.users[uId].projectPermissions, function (index, item) {
              if ($("#ap_r_" + index).length == 0) {
                  $.permission.deleteUserOnProject(uId, index);
              }
          });
      });
    },

    /**
     * Retrieve id from object.
     *
     * @param obj
     *            the object to retrieve
     * @param prefix
     *            the prefix string
     */
    retrieveId:function (obj, prefix) {
      var id = $(obj).attr("id");
      id = id.substring(prefix.length);
      return id;
    },

    /**
     * Add user to user table if it is not exist.
     */
    addUserIfNotExist:function (userId, handle) {
      if (this.users[userId] != null) {
        return;
      }

      var obj = {
            userId:userId,
            handle:handle,
            tperm:false,
            rperm:false,
            wperm:false,
            fperm:false
      };
      var user = new this.user(obj);
      this.users[userId] = user;
      user.toHtml();
      var tr = $(user.html);
      var tds = tr.find("td");

      // save the current table
      var tmpCurrentTable = this.currentTable;

      this.currentTable = 1;
        this.oUserTable.fnAddData([ $(tds[0]).html(), $(tds[1]).html(),
          $(tds[2]).html(), $(tds[3]).html(), $(tds[4]).html(), $(tds[5]).html() ]);

      var addDiv = $("#u_user" + userId);
        addDiv.parent().parent().find("td").each(function (tdIndex) {
        if (tdIndex == 0) {
          $(this).addClass("permCol");
        } else {
          $(this).addClass("checkbox");
        }
      });

      this.initTable();
      this.currentTable = tmpCurrentTable;
    },

    /**
     * Add user and project pair.
     *
     * @param userId
     *            the user id
     * @param handle
     *            the handle of user
     * @param projectId
     *            the project id
     */
    addUserProjectPair:function (userId, handle, projectId) {
      if ($.permission.projects[projectId].userPermissions[userId] != null) {
        return;
      }
      // add user to html if it is not exist
      $.permission.addUserIfNotExist(userId, handle);

      // initiate
      var project = $.permission.projects[projectId];
      var projectName = project.projectName;
      var obj = {
            userId:userId,
            handle:handle,
            projectId:projectId,
            projectName:projectName,
            tperm:false,
            rperm:false,
            wperm:false,
            fperm:false
      };
      var tmpCurrentTable = $.permission.currentTable;

      // add project
      $.permission.addProject(obj);

      // update the project table
      $.permission.currentTable = 0;
      var pDiv = $("#p_project" + projectId);
      project.toHtml();
      if (project.groupState == 0) {
            $("#permissions tr.group" + projectId).each(function () {
          $(this).remove();
        });
      } else {
        $.permission.changePermissionHideGroup(pDiv, projectId);
            $("#permissions tr.group" + projectId).each(function () {
          $(this).remove();
        });
        $.permission.changePermissionHideGroup(pDiv, projectId);
      }

      // update the user table
      $.permission.currentTable = 1;
      var uDiv = $("#u_user" + userId);
      var user = $.permission.users[userId];
      user.toHtml();
      if (user.groupState == 0) {
            $("#userTable tr.group" + userId).each(function () {
          $(this).remove();
        });
      } else {
        $.permission.changePermissionHideGroup(uDiv, userId);
            $("#userTable tr.group" + userId).each(function () {
          $(this).remove();
        });
        $.permission.changePermissionHideGroup(uDiv, userId);
      }

      // restore the current table field
      $.permission.currentTable = tmpCurrentTable;
    },

    /**
     * Add project.
     *
     * @param projectJson
     *            the project json object to add
     */
    addProject:function (projectJson) {
      // add to user table
      var pPermission = new this.projectPermission(projectJson);
      if (this.users[projectJson.userId] == null) {
        this.users[projectJson.userId] = new this.user(projectJson);
      }
      this.users[projectJson.userId].addProjectPermissions(pPermission);

      // add to project table
      var uPermission = new this.userPermission(projectJson);
      if (this.projects[projectJson.projectId] == null) {
        this.projects[projectJson.projectId] = new this.project(
            projectJson);
      }
      this.projects[projectJson.projectId]
          .addUserPermissions(uPermission);

      if (projectJson.userPermissionId) {
        this.projects[projectJson.projectId].userPermissionIds[projectJson.userId] = projectJson.userPermissionId;
      }
    },

    /**
     * Render to html.
     */
    renderToHtml:function () {
      var tmpTable = this.currentTable;

      // render to project table
      this.currentTable = 0;
      var html = "";
        for (var projectId in this.projects) {
        html += this.projects[projectId].toHtml();
      }
      $("#permissions tbody").html(html);
      this.oProjectTable = $("#permissions")
          .dataTable(
              {
                "bFilter":false,
                "bSort":true,
                "asSorting":[ "asc" ],
                "bInfo":false,
                "oLanguage":{
                    "sLengthMenu":"_MENU_ Per page"
                },
                "sPaginationType":"permission_button",
                "bAutoWidth":false,
                "sDom":'"bottom"<"pagination-info"<"pagination"p><"display-perpage"l>>',
                "aoColumns":[
                    { "sSortDataType":"dom-text" },
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false}
                  ]
              });

      // render to user table
      this.currentTable = 1;
      html = "";
        for (var userId in this.users) {
        html += this.users[userId].toHtml();
      }
      $("#userTable tbody").html(html);
      this.oUserTable = $("#userTable")
          .dataTable(
              {
                "bFilter":false,
                "bSort":true,
                "asSorting":[ "asc" ],
                "bInfo":false,
                "oLanguage":{
                    "sLengthMenu":"_MENU_ Per page"
                },
                "sPaginationType":"permission_button",
                "bAutoWidth":false,
                "sDom":'"bottom"<"pagination-info"<"pagination"p><"display-perpage"l>>',
                "aoColumns":[
                    null,
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false},
                    {"bSortable":false}
                ]
              });

      // update head row
      this.currentTable = 0;
      this.updateAllPermission();
      this.currentTable = 1;
      this.updateAllPermission();
      this.currentTable = tmpTable;

      // set table create complete
      this.tableCreateComplete = true;

      // set global event handle
      this.setGlobalEventHandle();
    },

    /**
     * Set global event handles.
     */
    setGlobalEventHandle:function () {
      // handle checkbox click event in 'applyForAll' row
      var tableIds = [ "#projects", "#users" ];
        for (var i = 0; i < tableIds.length; i++) {
        var inputs = $(tableIds[i] + " .applyForAll input");
            $(inputs[0]).click(
                function () {
                    $.permission.setAllPermission(
                        $.permission.PERMISSION_TYPE_T, $(this)
                            .attr("checked"));
                });
        $(inputs[1]).click(
                function () {
              $.permission.setAllPermission(
                  $.permission.PERMISSION_TYPE_R, $(this)
                      .attr("checked"));
            });
        $(inputs[2]).click(
                function () {
              $.permission.setAllPermission(
                  $.permission.PERMISSION_TYPE_W, $(this)
                      .attr("checked"));
            });
        $(inputs[3]).click(
                function () {
              $.permission.setAllPermission(
                  $.permission.PERMISSION_TYPE_F, $(this)
                      .attr("checked"));
            });
      }

      // handle add more users event and delete users event
        $("#u_addMoreUserA").click(function () {
        $.permission.addMoreUsers();
      });
        $("#u_deleteUsersA").click(function () {
        $.permission.removeUsers();
        pbutton_submit();
      });

      // handle user list click event
        $($("#users thead tr")[0]).click(function () {
        $.permission.initTable();
      });

      // hanlde sort image change
        $("#users thead tr th.permCol").click(function () {
        var img = $(this).find("img");
        if (img.attr("src") == $.permission.SORT_IMAGES[0]) {
          img.attr("src", $.permission.SORT_IMAGES[1]);
        } else {
          img.attr("src", $.permission.SORT_IMAGES[0]);
        }
      });

      // handle select value change event
        $.each(["notifications_length", "userTable_length"], function (index, item) {
            $("#" + item).change(function () {
              $.permission.initTable();
          });
      });
    },

    /**
     * Change the visibility of specified hide group.
     */
    changePermissionHideGroup:function (obj, groupId) {
      var group;
      var htmlToAdd;
      var nTr = $(obj).parent().parent();
      var selectString = " .subTr.group" + groupId;

      if (this.currentTable == 0) {
        group = this.projects[groupId];
        htmlToAdd = group.userPermissionsHtml;
        selectString = "#projects" + selectString;
      } else {
        group = this.users[groupId];
        htmlToAdd = group.projectPermissionsHtml;
        selectString = "#users" + selectString;
      }

      if ($(selectString).length == 0) {
        $(htmlToAdd).insertAfter(nTr);
      }
        $(selectString).each(function () {
        if ($(this).hasClass($.permission.HIDDEN_CLASS)) {
          $(this).removeClass($.permission.HIDDEN_CLASS);
        } else {
          $(this).addClass($.permission.HIDDEN_CLASS);
        }
      });

      // change group class
      group.groupState ^= 1;
      $(obj).removeClass(this.GROUP_CLASS[group.groupState ^ 1]);
      $(obj).addClass(this.GROUP_CLASS[group.groupState]);
    },

    /**
     * Initiate the table.
     */
    initTable:function () {
      if (this.currentTable == 0) {
        $("#projects .group")
            .each(
                function () {
                  var projectId = $(this).attr("id");
                  projectId = projectId
                      .substring("p_project".length);

                  if ($.permission.projects[projectId].groupState == 1) {
                    $.permission.projects[projectId].groupState = 0;
                    $.permission.changePermissionHideGroup(
                        this, projectId);
                  }

                  $.permission.projects[projectId]
                      .updateMainPermission();
                });
      }

      if (this.currentTable == 1) {
            $("#users .group").each(function () {
          var userId = $(this).attr("id");
          userId = userId.substring("u_user".length);

          var addDiv = $("#u_user" + userId);
                addDiv.parent().parent().find("td").each(function (tdIndex) {
            if (tdIndex == 0) {
              $(this).addClass("permCol");
            } else {
              $(this).addClass("checkbox");
            }
          });

          if ($.permission.users[userId].groupState == 1) {
            $.permission.users[userId].groupState = 0;
            $.permission.changePermissionHideGroup(this, userId);
          }

          $.permission.users[userId].updateMainPermission();

        });
      }

      // restore the current table
      this.updateAllPermission();
    },

    /**
     * Set the status of td in specified tr.
     *
     * @param tr
     *            the tr to set
     * @param tperm
     *            the report permission to set
     * @param rperm
     *            the read permission to set
     * @param wperm
     *            the write permission to set
     * @param fperm
     *            the full permission to set
     */
    setTrCheckStatus:function (tr, tperm, rperm, wperm, fperm) {
        $(tr.children()[1]).children().attr("checked", tperm);
        $(tr.children()[2]).children().attr("checked", rperm);
        $(tr.children()[3]).children().attr("checked", wperm);
        $(tr.children()[4]).children().attr("checked", fperm);
    },

    /**
     * Handle checkbox click event.
     *
     * @param tableType
     *            the type of table
     * @param srcType
     *            the type of source
     * @param permissionType
     *            the type of permission
     * @param id
     *            the id
     * @param mainId
     *            the main id, optional
     */
    handleCheckboxEvent:function (tableType, srcType, permissionType, id, mainId) {
      if (tableType == this.TABLE_TYPE_PROJECTS) {
        if (srcType == this.SRC_TYPE_DETAIL) {
          var project = this.projects[mainId];
          var uP = project.userPermissions[id];
          var tp = uP.tperm;
          var rp = uP.rperm;
          var wp = uP.wperm;
          var fp = uP.fperm;

          if (permissionType == this.PERMISSION_TYPE_T) {
            tp = !tp;
          } else if (permissionType == this.PERMISSION_TYPE_R) {
            rp = !rp;
          } else if (permissionType == this.PERMISSION_TYPE_W) {
            wp = !wp;
          } else if (permissionType == this.PERMISSION_TYPE_F) {
            fp = !fp;
          }

          project.setSingleUserPermission(id, tp, rp, wp, fp);

        } else if (srcType == this.SRC_TYPE_MAIN) {
          var project = this.projects[id];

          project.setMainPermission(permissionType);
        }
      } else {
        if (srcType == this.SRC_TYPE_DETAIL) {
          var user = this.users[mainId];
          var pP = user.projectPermissions[id];
          var tP = pP.tperm;
          var rp = pP.rperm;
          var wp = pP.wperm;
          var fp = pP.fperm;

          if (permissionType == this.PERMISSION_TYPE_T) {
            tp = !tp;
          } else if (permissionType == this.PERMISSION_TYPE_R) {
            rp = !rp;
          } else if (permissionType == this.PERMISSION_TYPE_W) {
            wp = !wp;
          } else if (permissionType == this.PERMISSION_TYPE_F) {
            fp = !fp;
          }

          user.setSingleProjectPermission(id, tp, rp, wp, fp);
        } else if (srcType == this.SRC_TYPE_MAIN) {
          var user = this.users[id];

          user.setMainPermission(permissionType);
        }
      }
    }
};
//});
