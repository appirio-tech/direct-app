/**
 * Launch Contest Javascript
 *
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
$(document).ready(function(){
    // Drop Down Select Project
    $(".dropdown dt a").click(function() {
      $(".dropdown dd ul").toggle();
      return false;
    });

    $(".dropdown dd ul li a").click(function() {
      var text = $(this).html();
      $(".dropdown dt a span").html(text);
      $(".dropdown dd ul").hide();
      $("#result").html("Selected value is: " + getSelectedValue("sample"));
      return false;
    });

    function getSelectedValue(id) {
      return $("#" + id).find("dt a span.value").html();
    }

    $(document).bind('click', function(e) {
      var $clicked = $(e.target);
      if (! $clicked.parents().hasClass("dropdown"))
        $(".dropdown dd ul").hide();
    });

    // Drop Down Sort Contests
    $(".dropdown2 dt a").click(function() {
      $(".dropdown2 dd ul").toggle();
      return false;
    });

    $(".dropdown2 dd ul li a").click(function() {
      var text = $(this).html();
      $(".dropdown2 dt a span").html(text);
      $(".dropdown2 dd ul").hide();
      $("#result").html("Selected value is: " + getSelectedValue("sample2"));
      $('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17});
      return false;
    });

    function getSelectedValue(id) {
      return $("#" + id).find("dt a span.value2").html();
    }

    $(document).bind('click', function(e) {
      var $clicked = $(e.target);
      if (! $clicked.parents().hasClass("dropdown2"))
        $(".dropdown2 dd ul").hide();
    });

    // Drop Down Search
    $(".dropdown3 dt a").click(function() {
      $(".dropdown3 dd ul").toggle();
      return false;
    });

    $(".dropdown3 dd ul li a").click(function() {
      var text = $(this).html();
      $(".dropdown3 dt a span").html(text);
      $(".dropdown3 dd ul").hide();
      $("#result").html("Selected value is: " + getSelectedValue("sample"));
      return false;
    });

    function getSelectedValue(id) {
      return $("#" + id).find("dt a span.value3").html();
    }

    $(document).bind('click', function(e) {
      var $clicked = $(e.target);
      if (! $clicked.parents().hasClass("dropdown3"))
        $(".dropdown3 dd ul").hide();
    });

    $("a.link-sort").click(function() {
      var toLoad = $(this).attr('href');
      $('#scrollbar-wrapper div').hide('fast',loadContent);
      $('#load').remove();
      $('#scrollbar-wrapper div').append('<div id="load"></div>');
      $('#load').fadeIn('slow');
      window.location.hash = $(this).attr('href').substr(0,$(this).attr('href').length-5);
      function loadContent() {
        $('#scrollbar-wrapper div').load(toLoad,'',showNewContent());
      }
      function showNewContent() {
        $('#scrollbar-wrapper div').show(0.001,hideLoader());
      }
      function hideLoader() {
        $('#load').fadeOut('slow');
      }
    });


  $("#expand-table").click(function() {
      $("#collapse-table").show();
      $(".row-hide").slideDown(0.0001);
      $(this).hide();
      return false;
    });

  $("#collapse-table").click(function() {
      $("#expand-table").show();
      $(".row-hide").slideToggle(0.0001);
      $(this).hide();
      return false;
    });

  $('#scrollbar-wrapper').jScrollPane({showArrows:true, scrollbarWidth: 17});
  $(".table-sidebar tr:odd").addClass("alt");
  $(".table-summary-content tr:even").addClass("alt");
  $("#collapse-table").hide();
  $(".row-hide").slideToggle(0.0001);
  $("#wb-1").hide();
  $("#wb-2").hide();

  $(".greentext").click(function() {
      $("#wb-1").fadeTo("slow", 1.0);
      return false;
    });

  $(".redtext").click(function() {
      $("#wb-2").fadeTo("slow", 1.0);
      return false;
    });

  $("a.btn-list").hover(function() {
      var $next = $(this).next(".dialog-mini-wrapper");
      $next.css("display", "block");
      $next.hover(function() {
        $next.css("display", "block");
        },function() {
        $next.css("display", "none");
      });
      },function() {
      var $next = $(this).next(".dialog-mini-wrapper");
      $next.css("display", "none");
    });

    /* initialize selects */
    if($('select').length > 0){
      $('.selectSoftware select,.selectDesing select,.projectSelect select,.billingSelect select,.roundelect select,.startSelect select,.milestoneSelect select,.endSelect select,.startEtSelect select,.milestoneEtSelect select,.endEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect();

      $('.selectDesing div.selectedTxt').html('Select Contest Type');
    }

    /* Optgroup 2 columns fix */
    if($('.selectDesing optgroup, .selectDesign .newListOptionTitle').length > 0){
      var optgroupMaxHeight = 0, num;

      $('.selectDesing optgroup').each(function(){
        num = $(this).children().length + 1;
        optgroupMaxHeight = num * 22 > optgroupMaxHeight ? num * 22 : optgroupMaxHeight;
      });

      $('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');

      $(window).resize(function(){$('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');});
      $(window).scroll(function(){$('.selectDesing .newList').css('height', optgroupMaxHeight + 'px');});
    }

  /* init tab **/
  $('#tab li').click(function(){

    $('#tab li a').removeClass('current');
    $(this).find('a').addClass('current');
    if($(this).attr('class') == 'top'){
      $('.selectDesing').show();
      $('.selectSoftware').addClass('visibility');
    }else{
      $('.selectDesing').hide();
      $('.selectSoftware').removeClass('visibility');
    }

  });

  /* init pop */
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

    $('#'+myPopupId).css("top",(myLinkTop+4)+'px');
    $('#'+myPopupId).css("left",( myLinkLeft+22 )+'px');

    /* set the positio of the arrow inside the popup */
    $(".tooltipContainer SPAN.arrow").css("top",popUpHeight2+'px');

    /* show the popup */
    $('#'+myPopupId).css("display","block");

  }

  var prevPopups = null;
  showPopups = function(myLinks,myPopupIds){
    var myLinkLefts = myLinkTops  = 0;

    /* hide the previous popup */
    if( prevPopups )
      $(prevPopups).css("display","none");

    prevPopups = $('#'+myPopupIds);

    /* get the position of the current link */
    do{
      myLinkLefts += myLinks.offsetLeft;
      myLinkTops += myLinks.offsetTop;
    }while( myLinks = myLinks.offsetParent );

    /* set the position of the popup */
    var popUpHeight2s = $('#'+myPopupIds).height()/2;

    myLinkTops -= popUpHeight2s;

    $('#'+myPopupIds).css("top",(myLinkTops+4)+'px');
    $('#'+myPopupIds).css("left",( myLinkLefts+104 )+'px');

    /* set the positio of the arrow inside the popup */
    $(".tooltipContainer SPAN.arrow").css("top",popUpHeight2s+'px');

    /* show the popup */
    $('#'+myPopupIds).css("display","block");

  }

  $('.description .helpIcon').hover(function(){
    showPopup(this,'contestLaunch1');
  },function(){
    $('#contestLaunch1').hide();
  });

  $('.selectDesing .help,.selectSoftware .help').hover(function(){
    showPopups(this,'contestLaunch1');
  },function(){
    $('#contestLaunch1').hide();
  });

  $('.mPrizes .helpIcon').hover(function(){
    showPopup(this,'contestLaunch2');
  },function(){
    $('#contestLaunch2').hide();
  });

  $('.deliverables .helpIcon').hover(function(){
    showPopup(this,'contestLaunch3');
  },function(){
    $('#contestLaunch3').hide();
  });

  $('.TB_overlayBG').css("opacity", "0.6");
  $('#TB_HideSelect').css("opacity", "0");
  $('#TB_overlay').hide();
  /* init help */
  $('.helloUser .help,.tabContest .moreLink,.help .helpIcon,.conditions').click(function(){
    $('#TB_overlay').show();
    $('#TB_window').show();
    $('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
    $('#TB_window').css({
            'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window").width() / 2)) + 'px'
        });
    $('#placeHolder').hide();
    $('#TB_ajaxContent').show();
  });

  $('#TB_closeWindowButton').click(function(){
    $('#TB_overlay').hide();
    $('#TB_window').hide();
  });

  /* add button */
  $('.uploadInner .addButton').click(function(){
    $(this).parent().after('<div class="uploadInner" style="margin-top:12px;"><input type="text" class="text uploadInput" value="File Name" /><a href="javascript:;" class="button6"><span class="left"><span class="right">CHOOSE...</span></span></a><input type="text" class="text fileInput" value="File Description" /><a href="javascript:;" class="removeButton"><span class="hide">REMOVE</span></a><a href="javascript:;" class="addButton"><span class="hide">ADD</span></a></div>');
  });

  $('.uploadInner .removeButton').click(function(){
    if($(this).parent().parent().find('.uploadInner').length > 1){
      $(this).parent().parent().find('.uploadInner:last').remove();
    }
  });

  /* bigframe */
  $('#TB_overlay').bgiframe();

  $('#TB_window').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});

  $(".uploadInner .button6").click(function(){$(".uploadInner .fileIn").trigger("click")})

  /********************************
   *   Launch Contest Main Widget
   ********************************/
  /*****************************
   *   Select Contest Type
   ****************************/   
   // choose contest type
   $('#contestTypes').bind("change", function() {
       if("SOFTWARE" == getContestType(true)[0]) {
           alert("it is to be implemented in next assembly!");
      }

      $.each(studioSubtypeOverviews, function(i, overview) {
          if(overview.id == getContestType(true)[1]) {
             // update overview description
             $('#contestDescriptionTooltip').html(overview.description);
          }
      });

      $.each(studioSubtypeFees, function(i, fee) {
          if(fee.id == getContestType(true)[1]) {
             // update contest fees
             mainWidget.competition.contestData.contestAdministrationFee = fee.contestFee;
             // not set yet, auto fill
             if(isEmpty($('#prize3').val())) {
                 $('#prize1').val(fee.firstPlaceCost)
                 $('#prize2').val(fee.secondPlaceCost)
             }
          }
      });

      resetFileTypes(getContestType(true)[1]);
      
      getCapacityDatesForStudioSubType(getContestType(true)[1]);
   });

   // add project
   initDialog('addProjectDialog', 500);

   $('#addProjectDialog').bind('dialogopen', function(event,ui) {
     $('#addProjectForm').show();
     clearDialog('addProjectForm');

     $('#addProjectResult').hide();
     $('#addProjectResult').find('p').html('');
   });

   $('#addNewProject').click(function(){
      $('#addProjectDialog').dialog('open');
   });

   // round types
   $('#roundTypes').bind("change", function() {
        var roundType = $('#roundTypes').val();
        if(roundType == 'single') {
           $('#mileStoneDiv').hide();
           $('#milestonePrizeDiv').hide();
           $('#round1InfoDiv').hide();
           $('#round2InfoDiv').hide();
        } else {
           $('#mileStoneDiv').show();
           $('#milestonePrizeDiv').show();
           $('#round1InfoDiv').show();
           $('#round2InfoDiv').show();
        }
   });
   $('#roundTypes').trigger("change");

  /* init date-pack */
  if($('.date-pick').length > 0){
    $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
  }

  $('#startDate').dpSetRenderCallback(function($td, thisDate, month, year) {
  	   var studioSubTypeId = getContestType(true)[1];
       var fullDates = capacityFullDates[studioSubTypeId];
       
       if(fullDates != null) {
       	   $.each(fullDates, function(i, fullDate) {
       	   	  if(thisDate.toString("MM/dd/yyyy") == fullDate) {
       	   	  	 $td.addClass('disabled');
       	   	  }
       	   });
       }
  });

  /*****************************
   *   Overview page
   ****************************/
  tinyMCE.init({
  	mode : "exact",
  	elements : "contestDescription,contestIntroduction,round1Info,round2Info",
  	plugins : "paste",
  	theme : "advanced",  	
	  theme_advanced_buttons1 : "bold,italic,underline,strikethrough,undo,redo,pasteword, bullist,numlist",
	  theme_advanced_buttons2 : "",
	  theme_advanced_buttons3 : "",
	  init_instance_callback : function() {
	  	  $('table.mceLayout').css('width','100%');
	  }
  });  
}); // end of jQuery onload

//capacity dates
var capacityFullDates = {};

function getStudioFileTypes(studioSubtypeId) {
  var types = [];

  $.each(fileTypes, function(i, fileType) {
      if(studioSubtypeId == fileType.id) {
         $.each(fileType.fileFormats, function(j, fileFormat) {
              types.push(fileFormat.value);
         });
      }
  });

  return types;
}

function resetFileTypes(studioSubtypeId) {
   $('#deliverablesCheckboxs').html('');

   var types = getStudioFileTypes(studioSubtypeId);
   var html = "";
   $.each(types, function(i, type) {
        html += '<input type="checkbox" checked="checked" value="' + type +'" class="defaultFileType" /> <label>' + type + '</label>';
   });

   $('#deliverablesCheckboxs').html(html);
}

function getCapacityDatesForStudioSubType(studioSubtypeId) {
	 if(capacityFullDates[studioSubtypeId] == null) {
	 	   var request = {
	 	   	  studio : true,
	 	   	  contestTypeId : studioSubtypeId
	 	   };
       $.ajax({
          type: 'GET',
          url:  ctx + "/launch/getCapacityFullDates",
          data: request,
          cache: false,
          dataType: 'json',
          success: handleGetCapacityResult
       });	 	   
	 }
}

function handleGetCapacityResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
    	  var contestTypeId = result.contestTypeId;
    	  var fullDates = result.fullDates;
    	  capacityFullDates[contestTypeId] = fullDates;
    	  
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}


/**
 * Contest Selection Page
 */
function validateFieldsContestSelection() {
   //values from contest selection selection
   var competitionType = getContestType()[0];
   var contestTypeId = parseInt(getContestType()[1]);
   var contestName = $('input#contestName').val();
   var tcProjectId = $('select#projects').val();
   var billingProjectId = $('select#billingProjects').val();
   var isMultiRound = ('multi' == $('#roundTypes').val());

   //dates
   var startDate = getDateByIdPrefix('start');
   var endDate = getDateByIdPrefix('end');
   var milestoneDate = getDateByIdPrefix('milestone');

   //validation
   var errors = [];

   if(!checkRequired(competitionType)) {
       errors.push('No contest type is selected.');
   }

   if(!checkRequired(contestName)) {
       errors.push('Contest name is empty.');
   }

   if(tcProjectId < 0) {
       errors.push('No project is selected.');
   }

   //check dates
   if(startDate >= endDate) {
       errors.push('Start date should be smaller than end date.');
   }

   if(isMultiRound) {
      if(startDate >= milestoneDate || milestoneDate >= endDate) {
         errors.push('Milestone date should be between start date and end date.');
      }
   }

   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   // update competition value
   // Launch Contest -- Studio:
   // we assume only STUDIO competition is handled here for this assembly
   mainWidget.competitionType = 'STUDIO';

   mainWidget.competition.contestData.contestTypeId = contestTypeId;
   mainWidget.competition.contestData.name = contestName;
   mainWidget.competition.contestData.tcDirectProjectId = tcProjectId;
   mainWidget.competition.contestData.billingProject = billingProjectId;
   mainWidget.competition.contestData.multiRound = isMultiRound;

   mainWidget.competition.startDate = startDate;
   mainWidget.competition.endDate = endDate;
   mainWidget.competition.milestoneDate = milestoneDate;

   return true;
}

function continueContestSelection() {
   if(!validateFieldsContestSelection()) {
       return;
   }

   showPage('overviewPage');
}

function saveAsDraftContestSelection() {
   // only handle studio type
   if("SOFTWARE" == getContestType()[0]) {
        alert("it is to be implemented in next assembly");
        return;
   }

   if(!validateFieldsContestSelection()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult
   });
}

/**
 * Overview Page
 */ 
function validateFieldsOverview() {
   var contestDescription = tinyMCE.get('contestDescription').getContent();
   var contestIntroduction = tinyMCE.get('contestIntroduction').getContent(); 
   var round1Info = tinyMCE.get('round1Info').getContent(); 
   var round2Info = tinyMCE.get('round2Info').getContent(); 

    //milestone prize and submission numbers
    var milestonePrizeInput = $('#milestonePrize').val();

   //validation
   var errors = [];

   if(!checkRequired(contestIntroduction)) {
       errors.push('Contest introduction is empty.');
   }

   if(!checkRequired(contestDescription)) {
       errors.push('Contest description is empty.');
   }

   var prizes = validatePrizes(errors);
   
   var fileTypesResult = validateFileTypes(errors);
   var fileTypes = fileTypesResult[0];
   var otherFileTypes = fileTypesResult[1];

   var milestonePrize;
   if(mainWidget.competition.contestData.multiRound) {
      milestonePrize = parseFloat(milestonePrizeInput);
       if(!checkRequired(milestonePrizeInput) || !checkNumber(milestonePrizeInput) || isNaN(milestonePrize)) {
           errors.push('Milestone prize is invalid.');
       }
       
      if(!checkRequired(round1Info)) {
          errors.push('Round 1 information is empty.');
      }
      
      if(!checkRequired(round2Info)) {
          errors.push('Round 2 information is empty.');
      }       
   }


   if(errors.length > 0) {
       showErrors(errors);
       return false;
   }

   //update
   mainWidget.competition.contestData.prizes = prizes;
   mainWidget.competition.contestData.finalFileFormat = fileTypes.join(',');
   mainWidget.competition.contestData.otherFileFormats = otherFileTypes.join(',');
   mainWidget.competition.contestData.shortSummary = contestIntroduction;
   mainWidget.competition.contestData.contestDescriptionAndRequirements = contestDescription;   
   
   mainWidget.milestonePrizeData.amount = milestonePrize;
   mainWidget.milestonePrizeData.numberOfSubmissions = parseInt($('#milestoneSubmissionNumber').val());
   mainWidget.competition.contestData.multiRoundData.roundOneIntroduction = round1Info;
   mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction = round2Info;
   
   return true;
}


function backOverview() {
   showPage('contestSelectionPage');
}

function continueOverview() {
   if(!validateFieldsOverview()) {
       return;
   }

   showPage('reviewPage');
}

function saveAsDraftOverview() {
   if(!validateFieldsOverview()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult
   });
}

function isExtraPrizesEmpty() {
  var empty = true;

  $.each($('#extraPrizes input'),function(i, element){
     if(isNotEmpty($(this).val())) {
        empty = false;
     }
  });

  return empty;
}

/**
 * Review Page
 */
/**
 * Rerender the review page.
 */ 
function updateReview() {
   $('#rContestTypeName').html($("#contestTypes option[value=STUDIO"+ mainWidget.competition.contestData.contestTypeId +"]").text());
   $('#rContestName').html(mainWidget.competition.contestData.name);
   $('#rProjectName').html($("#projects option[value="+ mainWidget.competition.contestData.tcDirectProjectId +"]").text());

   var billingProjectId = mainWidget.competition.contestData.billingProject;
   $('#rBillingAccount').html((billingProjectId == -1)?"&nbsp;":$("#billingProjects option[value="+ billingProjectId +"]").text());

   var isMultiRound = mainWidget.competition.contestData.multiRound;
   $('#rRoundType').html((!isMultiRound)?"Contest will be run in single-round":"Contest will be run in multi-rounds");

   if(!isMultiRound) {
       $('#rMileStoneTR').hide();
       $('#rMPrizesDiv').hide();
       
       $('.rMultiInfo').hide();
   } else {
       $('#rMileStoneTR').show();
       $('#rMilestoneDate').html(formatDateForReview(mainWidget.competition.milestoneDate));

       $('#rMPrizesDiv').show();
       $('#rMPrizesAmount').html(mainWidget.milestonePrizeData.amount);
       $('#rMPrizesNumberOfSubmissions').html(mainWidget.milestonePrizeData.numberOfSubmissions);
       
       $('.rMultiInfo').show();
       $('#rRound1Info').html(mainWidget.competition.contestData.multiRoundData.roundOneIntroduction);
       $('#rRound2Info').html(mainWidget.competition.contestData.multiRoundData.roundTwoIntroduction);
   }

   $('#rStartDate').html(formatDateForReview(mainWidget.competition.startDate));
   $('#rEndDate').html(formatDateForReview(mainWidget.competition.endDate));

   $('#rContestDescription').html(mainWidget.competition.contestData.contestDescriptionAndRequirements);
   $('#rContestIntroduction').html(mainWidget.competition.contestData.shortSummary);

   //prizes
   var html = "";
   var placeMap = {1:"1st Place",2:"2nd Place", 3:"3rd Place", 4:"4th Place", 5:"5th Place"};
   $.each(mainWidget.competition.contestData.prizes, function(i, prize) {
       var place = i+1;
       var amount = prize.amount;
       html = html +
        '<label class="first">' + placeMap[place] + '</label>' +
        '<span class="dw">$</span>' +
        '<span class="numberDor">' + amount + '</span>';
   });
   $('#rPrizes').html(html);

   // file types
   var fileTypes = mainWidget.competition.contestData.finalFileFormat.split(",");
   $.merge(fileTypes,mainWidget.competition.contestData.otherFileFormats.split(","));

   html = "";
   $.each(fileTypes, function(i, fileType) {
       html += '&nbsp; <label>' + fileType + '</label>';
   });
   $('#rFinalDeliveries').html(html);
   
   // uploads
   html = "";
   $.each(documents, function(i, doc) {
       html = html + 
			 "<dt>" + doc.fileName + "</dt>" +
			 "<dd>" + doc.description + "</dd>";
   });
   $('#docUploadList').html(html);
   
}

function validateFieldsReview() {
   return true;
}

function backReview() {
   showPage('overviewPage');
}

function continueReview() {
   if(!validateFieldsReview()) {
       return;
   }

   alert("it is to be implemented in next assembly!");
}



function saveAsDraftReview() {
   if(!validateFieldsReview()) {
       return;
   }

   //construct request data
   var request = saveAsDraftRequest();

   $.ajax({
      type: 'POST',
      url:  "saveDraftContest",
      data: request,
      cache: false,
      dataType: 'json',
      success: handleSaveAsDraftContestResult
   });
}

function previewContest() {
  //http://studio.topcoder.com/?module=ViewContestDetails&ct=1001503
  var contestId = mainWidget.competition.contestData.contestId;
  if(contestId < 0) {
     showErrors("You must 'Save as Draft' before you can preview your contest.");
  } else {
      window.open('http://studio.topcoder.com/?module=ViewContestDetails&ct='+contestId);
  }
}

function cancelContest() {
   if(window.confirm("Are you sure you want to cancel? Please save your work first if you want to keep this contest.")) {
       window.location.href = "home";
   }
}

function saveAsDraftRequest() {
   var request = {};
   request['projectId'] = -1;
   request['contestId'] = mainWidget.competition.contestData.contestId;
   request['tcDirectProjectId'] = mainWidget.competition.contestData.tcDirectProjectId;
   request['competitionType'] = 'STUDIO';
   request['contestData'] = mainWidget.competition.contestData;

   //dates
   request['startDate'] = formatDateForRequest(mainWidget.competition.startDate);
   request['endDate'] = formatDateForRequest(mainWidget.competition.endDate);
   request['milestoneDate'] = formatDateForRequest(mainWidget.competition.milestoneDate);
   request['prizes'] = mainWidget.competition.contestData.prizes;

   //milestone prizes
   request['milestonePrizeAmount'] = mainWidget.milestonePrizeData.amount;
   request['milestonePrizeNumberOfSubmissions'] = mainWidget.milestonePrizeData.numberOfSubmissions;

   //document uploads
   request['docUploadIds'] = getDocumentIds();

   return request;   
}

function handleSaveAsDraftContestResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        var contestName = mainWidget.competition.contestData.name;
        if(mainWidget.competition.contestData.contestId < 0 ) {
          mainWidget.competition.contestData.contestId = result.contestId;
          showMessage("Contest " + contestName +" has been saved successfully!");
        } else {
          showMessage("Contest " + contestName +" has been updated successfully!");
        }
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
}


/**
 * Gets contest type.
 *
 * @param ignoreTextCheck true if it doesn't need to check the selected text. it will be passed
 *        as true when the method is called from contestTypes change listener
 * @return a 2 element array. first element's value is "STUDIO" or "SOFTWARE"
 *         second element is the sub type id. both could be null if nothing is
 *         selected
 */
function getContestType(ignoreTextCheck) {
   if(!ignoreTextCheck && $('.selectDesing div.selectedTxt').html() == 'Select Contest Type') {
       return [null,null];
   }

   var typeValues = $('#contestTypes').val().match(/^(STUDIO|SOFTWARE)(\d+)$/);
   return [typeValues[1],typeValues[2]];
}

function addNewProject() {
   var projectName = $('#addProjectForm').find('input[name="projectName"]').val();
   var projectDescription =  $('#addProjectForm').find('input[name="projectDescription"]').val();

   var errors = [];

   if(!checkRequired(projectName)) {
       errors.push('project name is empty.');
   }

   if(errors.length > 0) {
       showErrors(errors);
       return;
   }


   $.ajax({
      type: 'POST',
      url:  "createProject",
      data: {'projectName':projectName,
             'projectDescription':projectDescription},
      cache: false,
      dataType: 'json',
      success: function(jsonResult) {
          handleJsonResult(jsonResult,
          function(result) {
             var projectData = result;
             $("<option/>").val(projectData.projectId).text(projectData.name).appendTo("#projects");
             $('#projects').resetSS();
             $('#projects').getSetSSValue(projectData.projectId);

              $('#addProjectForm').hide();
              $('#addProjectResult').show();
              $('#addProjectResult').find('p').html('Project is created successfully.');

          },
          function(errorMessage) {
              $('#addProjectForm').hide();
              $('#addProjectResult').show();
              $('#addProjectResult').find('p').html(errorMessage);
          });
      }
   });
}

function closeTBBox() {
		$('#TB_overlay').hide();
		$('#TB_window').hide();
}