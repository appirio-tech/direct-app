/**
 * The script which contains the change for repost/new version functions for software contests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Direct - Repost and New Version Assembly
 */
/**
 * initialization on load.
 */ 
$(document).ready(function() {
  jQuery.ajaxSetup({
     timeout: 90000
  });

   $(document).ajaxError(function(event, XMLHttpRequest, ajaxOptions, thrownError){
       $('#repostDialog').dialog('close');
       $('#newVersionDialog').dialog('close');
       showGeneralError();
   });
   
   initDialog('msgDialog', 500);
   initDialog('errorDialog', 500);   
   initDialog('repostDialog', 500);   
   initDialog('newVersionDialog', 500);
   
   $('#repostDialog').bind('dialogopen', function(event,ui) {
     $('#repostForm').show();
     $('#repostResult').hide();
   });   
   
   $('#newVersionDialog').bind('dialogopen', function(event,ui) {
   	  newVersionShowSection('newVersionConfirm');
   });   
}); // end of initiation
   
/**
 * repost variables.
 */   
var repostProjectId = -1;
var repostTcProjectId = -1;
var repostNewProjectId = -1;

/**
 * repost event handler. It shows the repost dialog.
 *
 * @param projectId the project id
 * @param tcProjectId the tc project id 
 */    	
function repostHandler(projectId, tcProjectId) {
	 repostProjectId = projectId;
	 repostTcProjectId = tcProjectId;
	 
	 $('#repostDialog').dialog('open');
}    	 

/**
 * reposts the contest.
 */
function repostContest() {
	 if(repostProjectId < 0 || repostTcProjectId < 0) {
	 	  return;
	 }
	 
   $.ajax({
      type: 'POST',
      url:  ctx+"/contest/repostContest",
      data: {
        projectId: repostProjectId,
        tcProjectId: repostTcProjectId
      },
      cache: false,
      dataType: 'json',
      success: handleRepostContestResult,
      beforeSend: function(){
      	 $.blockUI({ message: '<div id=loading> reposting .... </div>' });
      },
      complete: function(){
      	 $.unblockUI();
      }       
   });
}

/**
 * The callback function to handle repost contest result.
 */
function handleRepostContestResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
    	 repostNewProjectId = result.newProjectId;
    	 
       $('#repostForm').hide();
       $('#repostResult').show();    	 
    },
    function(errorMessage) {    	
       $('#repostDialog').dialog('close');
       showErrors(errorMessage);    	
    });
}

/** 
 * Edits the reposted contest.
 */
function editRepost() {
	  if(repostNewProjectId < 0 ) {
	  	 return;
	  }
	  
	  location.href = ctx + "/contest/detail?projectId=" + repostNewProjectId;
}

/**
 * New version variables.
 */
var newVersionProjectId = -1;
var newVersionTcProjectId = -1;
var newVersionDevCreation = false;
var newVersionMinorVersion = false;
var newVersionIsDesign = false;
var newVersionNewProjectId = -1;

/**
 * New version even handlder.
 *
 * @param projectId the projectId
 * @param tcProjectId the tc project id
 * @param isDesign indicates if it is design or not
 */
function newVersionHandler(projectId, tcProjectId, isDesign) {
	newVersionProjectId = projectId;
	newVersionTcProjectId = tcProjectId;
	newVersionDevCreation = isDesign;
	newVersionIsDesign = isDesign;
	newVersionMinorVersion = false;
	
	$('#newVersionDialog').dialog('open');
}

/**
 * The confirm function.
 */
function newVersionConfirm() {
	if(newVersionIsDesign) {
		 //it needs to take to ask if the dev component needs to be created
		 newVersionShowSection('newVersionDev');
	} else {
		 // go directly to ask about minor version or major version
		 newVersionShowSection('newVersionMinor');
	}
}

/**
 * The new version dev creation function.
 */
function newVersionDevYes() {
	newVersionDevCreation = true;
	newVersionShowSection('newVersionMinor');
}

/**
 * The new version dev creation function.
 */
function newVersionDevNo() {
	newVersionDevCreation = false;
	newVersionShowSection('newVersionMinor');
}

/**
 * The new version major version/minor version handler.
 */
function newVersionChooseMajor() {
	newVersionMinorVersion = false; 
	newVersionContest();
}

/**
 * The new version major version/minor version handler.
 */
function newVersionChooseMinor() {
	newVersionMinorVersion = true;
	newVersionContest();
}

/**
 * Creates the new version contest.
 */
function newVersionContest() {
	 if(newVersionProjectId < 0 || newVersionTcProjectId < 0) {
	 	  return;
	 }
	 
   $.ajax({
      type: 'POST',
      url:  ctx+"/contest/newVersion",
      data: {
        projectId: newVersionProjectId,
        tcProjectId: newVersionTcProjectId,
        autoDevCreating: newVersionDevCreation,
        minorVersion: newVersionMinorVersion
      },
      cache: false,
      dataType: 'json',
      success: handleNewVersionContestResult,
      beforeSend: function(){
      	 $.blockUI({ message: '<div id=loading> new versioning .... </div>' });
      },
      complete: function(){
      	 $.unblockUI();
      }       
   });
}

/**
 * Callback to handle new version contest result.
 */
function handleNewVersionContestResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
    	 newVersionNewProjectId = result.newProjectId;
       newVersionShowSection('newVersionResult');    	 
    },
    function(errorMessage) {    	
       $('#newVersionDialog').dialog('close');
       showErrors(errorMessage);    	
    });
}

/**
 * Edit new version project.
 */
function editNewVersionProject() {
	  if(newVersionNewProjectId < 0 ) {
	  	 return;
	  }
	  
	  location.href = ctx + "/contest/detail?projectId=" + newVersionNewProjectId;
}

/**
 * show section.
 */
function newVersionShowSection(sectionId) {
	 $('#newVersionDialog > div').hide();
	 $('#newVersionDialog div#'+sectionId).show();
}

/**
 * refreshes the page.
 */
function refreshPage(dialogButton) {
	 if(location.href.indexOf("/contest/detail") == -1) {
	     location.reload(true);
	 } else {
	 	   closeDialog(dialogButton);
	 }
}

