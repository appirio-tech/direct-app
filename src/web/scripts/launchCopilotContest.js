/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file is used to render elements to launch copilot contest page, and handle
 * events.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
$(document).ready(function(){
    /**
     * Initiate select elements.
     */
    if($('select').length > 0){
        $('.selectSoftware select,.selectDesign select,.catalogSelect select,.roundelect select,.startSelect select,.milestoneSelect select,.endSelect select,.milestoneEtSelect select,.numSelect select, .cardSelect select, .selectMonth select, .selectYear select').sSelect(); 
        $('.selectDesign div.selectedTxt').html('Select Contest Type');
        $('.startEtSelect select').focus();
        
        var SelectOptions = {
            ddMaxHeight: '220px',
            yscroll: true
        };
        $('.startEtSelect select, .endEtSelect select, .projectSelect select, .billingSelect select').sSelect(SelectOptions);
    }
    
    /**
     * Initiate date pick.
     */
    if($('.date-pick').length > 0){
        $(".date-pick").datePicker().val(new Date().asString()).trigger('change');
    }

    /**
     * Represent prev popup window.
     */
    var prevPopup = null;
    
    /**
     * Show popup window with specified id.
     * 
     * @param myLink the link
     * @param myPopupId the id of popup window.
     */
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
    
    /**
     * Show popup window when help icon is hovered.
     */
    $('#launchContestOut .helpIcon, .help').hover(function(){
        showPopup(this, $(this).attr("id") + "_help");
    }, function(){
        $('#' + $(this).attr("id") + "_help").hide();
    });
    
    /**
     * Initiate TB section.
     */
    $('.TB_overlayBG').css("opacity", "0.6");
    $('#TB_HideSelect').css("opacity", "0");
    $('#TB_overlay').hide();
    $('#TB_window_custom').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window_custom").css('height')) / 2))});
    $('#TB_overlay').bgiframe();
    //$('#TB_window').scrollFollow({offset: parseInt((document.documentElement.clientHeight / 2) - (parseInt($("#TB_window").css('height')) / 2))});
    
    /**
     * Show terms and conditions window when click.
     */
    $('.conditions').click(function(){
        $('#TB_overlay').show();
        $('#TB_window').show();
        $('.TB_overlayBG').css('height',document.body.scrollHeight > document.body.offsetHeight ? document.body.scrollHeight : document.body.offsetHeight);
        $('#TB_window').css({
            'margin': '0 auto 0 ' + parseInt((document.documentElement.clientWidth / 2) - ($("#TB_window").width() / 2)) + 'px'
        });
        $('#placeHolder').hide();
        $('#TB_ajaxContent').show();
    });
    
    /**
     * Handle TB window close button click event.
     */
    $('#TB_closeWindowButton').click(function(){
        $('#TB_overlay').hide();
        $('#TB_window').hide();                                          
    });
    
    /**
     * Handle edit panel.
     */
    $(".editMask .editPanel").hide(); 

    /**
     * Save as draft dialog.
     */
    $('#draftPanelTrigger').overlay({
        closeOnClick:false,
        mask: {
            color: '#000000',
            loadSpeed: 200,
            opacity: 0.6
        },
        top:"20%",
        close :"#saveAsDraftOK",
        fixed : true,
        target : $("#saveAsDraft")
    });
    
    /**
     * Initiate mce elements.
     */
    tinyMCE.init({
        mode : "exact",
        elements : "allDescription, privateDescription, contestDescription,contestIntroduction,round1Info,round2Info,swDetailedRequirements,swGuidelines",
        plugins :"pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,wordcount,advlist",

        theme : "advanced",
        theme_advanced_buttons1 : "bold,italic,underline,strikethrough,undo,redo,pasteword, bullist,numlist,link,unlink",
        theme_advanced_buttons2 : "",
        theme_advanced_buttons3 : "",
        theme_advanced_statusbar_location : "bottom",
        theme_advanced_path : false,
        theme_advanced_resizing : true,
        theme_advanced_resize_horizontal : false,
        
        init_instance_callback : function() {
            $('table.mceLayout').css('width','100%');
        }
    });
   
    /**
     * Initiate add project dialog.
     */
    initDialog('addProjectDialog', 500);
    
    /**
     * Initiate other elements in panel.
     */
    initPanel();

    /**
     * Handle dialog open event.
     */
    $('#addProjectDialog').bind('dialogopen', function(event,ui) {
        $('#addProjectForm').show();
        clearDialog('addProjectForm');

        $('#addProjectResult').hide();
        $('#addProjectResult').find('p').html('');
    });

    /**
     * Handle add new project button click event.
     */
    $('#addNewProject').click(function(){
        $('#addProjectDialog').dialog('open');
    });
    
    /**
     * Handle continue button click event.
     */
    $('#continue').click(function() {
        if (!fillInValues()) {
            return;
        }
    
        // initiate add contest section
        $(".addNewContestSection").addClass("editMask");
        $(".addNewContestSection .addNewContest").addClass("editPanel");
        $(".addNewContestSection .addNewContest a").removeClass("hide");
        $(".addNewContestSection .addNewContest .save").removeClass("hide");
        $(".addNewContestSection .addNewContest").hide();
        $(".addNewContestSection .addNewContestInfo").show();
        
        // initiate schedule section
        initiateSpecifyPanel("scheduleSection", "schedule", true);
        
        // initiate all description section
        initiateSpecifyPanel("allDescriptionSection", "description", false);
        
        // initiate private description section
        initiateSpecifyPanel("privateDescriptionSection", "description", true);
        
        // initiate cost section
        $(".costs a").hide();
        
        // initiate upload section
        initiateSpecifyPanel("uploadSection", "fileupload", true);
        
        // initiate bottom button section
        $(".bottomButton .conditions").hide();
        $(".bottomButton #continue").hide();
        $(".bottomButton #submitButton").removeClass("hide");
        
        $('html, body').animate({scrollTop:0}, 'fast');
    });
    
    /**
     * Handle edit button click event.
     */
    $(".edit_type").click(function() {
        popValuesToInputs();
    
        var editPanel = $(this).parent().parent().parent().parent().find(".editPanelMask");
        editPanel.parent().show();
        editPanel.parent().next().hide();
    });
    
    /**
     * Handle cancel button click event.
     */
    $(".cancel_text").click(function() {
        var editPanel = $(this).parent().parent().parent().find(".editPanelMask");
        editPanel.parent().hide();
        editPanel.parent().next().show();
    });
    
    /**
     * Handle save button click event.
     */
    $(".save_btn").click(function() {
        if (!fillInValues()) {
            return;
        }
        
        var editPanel = $(this).parent().parent().parent().parent().find(".editPanelMask");
        editPanel.parent().hide();
        editPanel.parent().next().show();
    });
    
    /**
     * Bind save as draft action to save button click event.
     */
    $("#saveDraftButton").click(function() {
        saveAsDraft();
    });
    
    /**
     * Bind submit action to submit button click event.
     */
    $("#submitButton").click(function() {
        submitCompetition();
    });
});

(function($) {
    /**
     * Function to style the input file.
     */
   $.fn.styleingInputFile = function(){
       this.each(function(){
           var fileInput = $(this);
           var parentWrap = fileInput.parents(".attachFile");
           var wrapOffset = parentWrap.offset();
           fileInput.attr("style","opacity: 0;-moz-opacity: 0;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);")
           parentWrap.mousemove(function(event){
               fileInput.blur();
               var relatedX = event.pageX - wrapOffset.left - fileInput.width() + 12;
               var relatedY = event.pageY - wrapOffset.top;
               fileInput.css("left",relatedX + "px");
               fileInput.css("top","0px");
           });
           fileInput.change(function(){
               $(this).blur();
               parentWrap.find(".fakeText input").val($(this).val());
           })
       })
   }
})(jQuery);

/**
 * Adds a new project.
 */
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
      url:  "../launch/createProject",
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
};

/**
 * Initiate the panel, and set some parameters.
 */
function initPanel() {
    mainWidget.softwareCompetition.projectHeader.projectCategory={};
    mainWidget.softwareCompetition.projectHeader.projectCategory.id = 29;
    mainWidget.softwareCompetition.projectHeader.projectCategory.name = "Copilot Posting";
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType={};
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.id = 2;
    mainWidget.softwareCompetition.projectHeader.projectCategory.projectType.name = "Application";
   
    mainWidget.softwareCompetition.assetDTO.directjsDesignNeeded = false;
    mainWidget.softwareCompetition.assetDTO.directjsDesignId = -1;   
      
    mainWidget.softwareCompetition.projectHeader.setConfidentialityTypePublic();
    
    // update prize fields
    updateSoftwarePrizes();
    $('#sworAdminFee').html(mainWidget.softwareCompetition.projectHeader.getAdminFee().formatMoney(0));
    $('#sworFirstFee').html(mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost().formatMoney(0));
    $('#sworSecondFee').html(mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost().formatMoney(0));
    $('#sworTotal').html("$" + getCurrentContestTotal().formatMoney(0));
};

/**
 * Populate values to inputs from main widget.
 */
function popValuesToInputs() {
    $('input#contestName').val(mainWidget.softwareCompetition.assetDTO.name);
    $('select#projects').getSetSSValue(mainWidget.softwareCompetition.projectHeader.tcDirectProjectId);
    $('select#billingProjects').getSetSSValue(mainWidget.softwareCompetition.projectHeader.getBillingProject());
   
    $('#startDate').val(getDatePart(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
    $('#startTime').getSetSSValue(getTimePart(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
    
    tinyMCE.get('allDescription').setContent(mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements);
    tinyMCE.get('privateDescription').setContent(mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription);        
};

/**
 * Fill values into panel for display from main widget.
 *
 * @return whether these fields are valid
 */
function fillInValues() {
    if (!validateFields()) {
        return false;
    };
    
    $('#copilotPostingNameEdit').text(mainWidget.softwareCompetition.assetDTO.name);
    $('#projectNameEdit').text(mainWidget.softwareCompetition.projectHeader.tcDirectProjectName);
    $('#billingAccountEdit').text($("#billingProjects option[value="+ mainWidget.softwareCompetition.projectHeader.getBillingProject() +"]").text());
    
    $('#startDateEdit').text(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
    
    $('#allDescriptionEdit').html(mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements);
    $('#privateDescriptionEdit').html(mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription);
    
    $('#fileUploadTableEdit').find('tr').remove();
    $.each(swDocuments, function(index, item) {
        var tr = $("<tr>");
        
        var tdName = $("<td>");
        tdName.attr("class", "fileName");
        tdName.html(index + 1 + ". <a href='#'>" + item.fileName + "</a>");
        tr.append(tdName);
        
        var tdDesc = $("<td>");
        tdDesc.attr("class", "fileDesc");
        tdDesc.html($('<div>').text(item.description).html());
        tr.append(tdDesc);
        
        $('#fileUploadTableEdit').append(tr);
    });

    return true;
};

/**
 * Validate fields.
 * 
 * @return whether these fields are valid
 */
function validateFields() {
    var contestName = $('input#contestName').val();
    var tcProjectId = parseInt($('select#projects').val());
    var billingProjectId = parseInt($('select#billingProjects').val());
    var startDate = getDateByIdPrefix('start');
    var allDescription = tinyMCE.get('allDescription').getContent();
    var privateDescription = tinyMCE.get('privateDescription').getContent();    
    
    // validation
    var errors = [];

    if(tcProjectId < 0) {
        errors.push('Project must be selected.');
    } 
    if(!checkRequired(contestName)) {
        errors.push('Copilot posting name should not be empty.');
    } 
    if (billingProjectId <= 0) {
        errors.push('Billing account should be selected.');
    }
    
    if(errors.length > 0) {
        showErrors(errors);
        return false;
    }

    mainWidget.softwareCompetition.assetDTO.name = contestName;      
    mainWidget.softwareCompetition.assetDTO.directjsProductionDate = startDate;
    mainWidget.softwareCompetition.assetDTO.productionDate = formatDateForRequest(startDate);
    
    mainWidget.softwareCompetition.projectHeader.tcDirectProjectId = tcProjectId;
    mainWidget.softwareCompetition.projectHeader.tcDirectProjectName = $("#projects option[value="+ tcProjectId +"]").text();
    mainWidget.softwareCompetition.projectHeader.setProjectName(contestName);
    mainWidget.softwareCompetition.projectHeader.setBillingProject(billingProjectId);
    mainWidget.softwareCompetition.projectHeader.projectSpec.detailedRequirements = allDescription;
    mainWidget.softwareCompetition.projectHeader.projectSpec.privateDescription = privateDescription;
    
    return true;
};

/**
 * Initiate a specified section.
 * 
 * @param sectionName the section name
 * @param subName the sub section name
 * @param withGrey should be grey or not
 */
function initiateSpecifyPanel(sectionName, subName, withGrey) {
    $("." + sectionName).addClass("editMask");
    if (withGrey) {
        $("." + sectionName).addClass("greybg");
    }
    $("." + sectionName + " ." + subName).addClass("editPanel");
    $("." + sectionName + " .iconDiv").removeClass("hide");
    $("." + sectionName + " ." + subName + " .save").removeClass("hide");
    $("." + sectionName + " .iconDivToHide").hide();
    $("." + sectionName + " ." + subName).hide();
    $("." + sectionName + " ." + subName + "Info").show();
};

/**
 * Handle save as draft action.
 */
function saveAsDraft() {
    if (!validateFields()) {
        return;
    }
    
    // construct request data
    var request = saveAsDraftRequestSoftware();
    request['startDate'] = formatDateForRequest(mainWidget.softwareCompetition.assetDTO.directjsProductionDate);
    
    $.ajax({
        type: 'POST',
        url: '../launch/saveDraftContest',
        data: request,
        cache: false,
        dataType: 'json',
        success: handleCopilotContestSaveAsDraftResult,
        beforeSend: beforeAjax,
        complete: afterAjax   
    });
};

/**
 * Handle result of save as draft action. 
 * 
 * @param jsonResult the json result to handle
 */
function handleCopilotContestSaveAsDraftResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) { 
        var contestName = mainWidget.softwareCompetition.assetDTO.name;
        var messageToShow = "Your Copilot Selection Contest " + contestName;
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
            messageToShow += " has been saved as draft successfully!";
        } else {
            messageToShow += " has been updated as draft successfully!";
        }
        
        //update paid fee
        mainWidget.softwareCompetition.paidFee = result.paidFee;
        
        $('#saveAsDraft dt').html(messageToShow);
        $('#draftPanelTrigger').overlay().load();
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });
};

/**
 * Handle submit competition action.
 */
function submitCompetition() {
    if (!validateFields()) {
        return;
    }
    
    // construct request data
    var request = saveAsDraftRequestSoftware();
    request['startDate'] = formatDateForRequest(mainWidget.softwareCompetition.assetDTO.directjsProductionDate);
    request['activationFlag'] = true;
    
    $.ajax({
        type: 'POST',
        url: '../launch/saveDraftContest',
        data: request,
        cache: false,
        dataType: 'json',
        success: handleCopilotContestActivateResult,
        beforeSend: beforeAjax,
        complete: afterAjax   
    });
};

/**
 * Handle result of submit contest action. 
 * 
 * @param jsonResult the json result to handle
 */
function handleCopilotContestActivateResult(jsonResult) {
    handleJsonResult(jsonResult,
    function(result) {
        if(mainWidget.softwareCompetition.projectHeader.id < 0 ) {
            mainWidget.softwareCompetition.projectHeader.id = result.projectId;
        } 

        // render content to receipt page
        $('#idReceipt').html("No." + mainWidget.softwareCompetition.projectHeader.id);
        $('#contestNameReceipt').html(mainWidget.softwareCompetition.assetDTO.name);
        $('#projectNameReceipt').html(mainWidget.softwareCompetition.projectHeader.tcDirectProjectName);
        $('#paymentMethodReceipt').html("Billing Account - " + $("#billingProjects option[value="+ mainWidget.softwareCompetition.projectHeader.getBillingProject() +"]").text());
        $('#startTimeReceipt').html(formatDateForReview(mainWidget.softwareCompetition.assetDTO.directjsProductionDate));
        $('#adminFeeReceipt').html(mainWidget.softwareCompetition.projectHeader.getAdminFee().formatMoney(0));
        $('#firstFeeReceipt').html(mainWidget.softwareCompetition.projectHeader.getFirstPlaceCost().formatMoney(0));
        $('#secondFeeReceipt').html(mainWidget.softwareCompetition.projectHeader.getSecondPlaceCost().formatMoney(0));      
        $('.totalFeeReceipt').html("$" + getCurrentContestTotal().formatMoney(0));
        $('#contestDetailLinkDiv a').attr("href", "../contest/detail?projectId=" + mainWidget.softwareCompetition.projectHeader.id);

        $('#launchCopilotPage').hide();
        $('#receiptCopilotPage').show();
    },
    function(errorMessage) {
        showErrors(errorMessage);
    });   
};
