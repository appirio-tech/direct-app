/**
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for scorecard.
 *
 *  Version 2.0 - TCCC-4119 - scorecard - update right side bar
 *  - Created document header.
 *  - Modified scorecard right side bar to match direct application right side bar.
 *
 *  Version 2.1 (Release Assembly - TC Group Management and Scorecard Tool Rebranding)
 *  - Reskin the scorecard pages
 *
 * @author pvmagacho, GreatKevin
 * @version 2.1
 */

jQuery.fn.outerHTML = function () {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

(function ($) {
    $.fn.ellipsis = function () {
        return this.each(function () {
            var el = $(this);

            if (el.css("overflow") == "hidden") {
                var text = el.html();
                var multiline = el.hasClass('multiline');
                var t = $(this.cloneNode(true))
                        .hide()
                        .css('position', 'absolute')
                        .css('overflow', 'visible')
                        .width(multiline ? el.width() : 'auto')
                        .height(multiline ? 'auto' : el.height())
                    ;

                el.after(t);

                function height() {
                    return t.height() > el.height();
                };
                function width() {
                    return t.width() > el.width();
                };

                var func = multiline ? height : width;

                while (text.length > 0 && func()) {
                    text = text.substr(0, text.length - 1);
                    t.html(text + "...");
                }

                el.html(t.html());
                t.remove();
            }
        });
    };
})(jQuery);

function populateRecentProjects(data) {

    if (data && data.length > 0) {

        $("#recentProjectsTopNav").show();

        $("#recentProjectsTopNav .recentProjectsFlyout li").remove();

        var currentProjectId = $("input[name=topNavCurrentProjectId]").val();
        var hasNotCurrentContests = false;

        $.each(data, function (index, pItem) {

            if (pItem.accessItemId == currentProjectId) {
                return;
            }
            var a = $('<li class="trigger"></li>').html($("#recentProjectItemTemplate").html());

            // set project name
            a.find("a.recentProjectName").html('<span class="ellipsis">' + pItem.itemName + '</span><span class="arrow"></span>').attr('title', pItem.itemName).addClass("ellipsis");
            a.find("a.recentProjectOverview").attr('href',
                '/direct/projectOverview.action?formData.projectId=' + pItem.accessItemId);
            a.find("a.recentProjectPlan").attr('href',
                '/direct/ProjectJsGanttGamePlanView.action?formData.projectId=' + pItem.accessItemId);
            a.find("a.recentProjectSetting").attr('href',
                '/direct/editProject.action?formData.projectId=' + pItem.accessItemId);
            $("#recentProjectsTopNav .recentProjectsFlyout").append(a);
            hasNotCurrentContests = true;
        });

        if(!hasNotCurrentContests) {
            $("#recentProjectsTopNav").hide();
        }

    } else {
        // no data, hide recent projects
        $("#recentProjectsTopNav").hide();
    }
}



$(document).ready(function(){
    
    /*-------------------------- Show/hide the dropdown list --*/
    
    showHideList = function(){
        $("#dropDown1").slideToggle(100);
        $("#sortTableBy").toggle();
    }
    

    
    /*----------------- this function is for demonstration purpose, it will show some contests on the contests list --*/
    showContestsDemo = function(){
            var curr = 0;
            $("TABLE#contestsTable TBODY TR").each(function(){
                    if( curr > 2 )
                        $(this).addClass("hide");
                        
                    curr++;
            });
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
    
    /*--------------------------------------- Show/Hide group users (the table on the permissions tab ) --*/
    showHideGroup2 = function(myLink, rowClass){
        /*  myLink: the clicked link
            rowClass: the class name of the group */
        
        /* change the link ico */
        var $kid = $(myLink).children();
        if( $kid.hasClass("expand")){
            $kid.removeClass("expand");
            $kid.addClass("collapse");
        }else{
            $kid.removeClass("collapse");
            $kid.addClass("expand");
        }
                
        $("."+rowClass).toggle();
    }
    
    /*--------------------------------------- Hide group users (the table on the permissions tab ) --*/
    hideGroup = function(group, rowClass){
        /*  myLink: the clicked link
            rowClass: the class name of the group */
        
        /* change the link ico */
        $("."+group).removeClass("collapse");
        $("."+group).addClass("expand");
        

        $("."+rowClass).hide();
    }
    /*--------------------------------------- Show group users (the table on the permissions tab ) --*/
    showGroup = function(group, rowClass){
        /*  myLink: the clicked link
            rowClass: the class name of the group */
        
        /* change the link ico */
        
        $("."+group).removeClass("expand");
        $("."+group).addClass("collapse");
        
        $("."+rowClass).show();
    }
    
    /*--------------------------------------- Show/Hide spec review question (on the spec review tab ) --*/
    showHideDiv = function(div_to_show, div_to_hide){
        $('#'+div_to_show).show();
        $('#'+div_to_hide).hide();
    }

    /*--------------------------------------- Change the comment according to the input text --*/
    displayComment = function(commentId, inputId){
        $('#'+commentId).html($('#'+inputId+' textarea').val());
    }
    
    $('.textarea1 textarea').click(function(){
        if ($(this).val() == 'Input your comment...') {
            $(this).html('');
        }
    }).blur(function() {
        if ($(this).val() == '') {
            $(this).html('Input your comment...');
        }
    });
    
    /*--------------------------------------- Image button hover effect --*/
    $("div.comment_specreview a").hover(function(){
        $("img.sr_editcomment", this).attr("src", "images/edit_red.png");
    }, function() {
        $("img.sr_editcomment", this).attr("src", "images/edit.png");
    });
    
    $("div.to_add_your_comment a").hover(function(){
        $("img.sr_addcomment", this).attr("src", "images/add_your_comment_hover.png");
    }, function() {
        $("img.sr_addcomment", this).attr("src", "images/add_your_comment.png");
    });
    
    /*-------------------------------------- Check/uncheck all checkboxes functionnality --*/
    checkAll = function(myCheckbox, myContainerId){
        /* myCheckbox: the "select all" check box
           myContainerId: the id of the container of the checkboxes */
    
        $("#"+myContainerId+" input.selectUser").each(function(){
                if( myCheckbox.checked )
                    this.checked = true;
                else
                    this.checked = false;
        });
    }
    
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
    $('table.contests tbody tr:odd').addClass('even');
    
    $('a.clearDataFind').live('click', function(){
        resetForm('find');
    });
    
    showHide = function(rowClass){
        $("."+rowClass).toggle();
    }
    
    $('a.button6.apply').live('click', function(){
        
        /* change the link ico */		
        $('.collapse').addClass("expand").removeClass("collapse");
        
        $('.roundedBox').toggle();
        showHide('apply');
    });

    /*help center widget tab function*/
    $(".darkenBtn,#helpCenterWidget h6").css("text-shadow", "0 -1px 1px #221d1a");

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
    
    var ua = navigator.userAgent.toLowerCase();
        
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

    $.ajax({
        type: 'POST',
        url: '/direct/getCurrentUserRecentProjects',
        dataType: "json",
        cache: false,
        async: false,
        success: function (jsonResult) {
            handleJsonResult2(jsonResult,
                function (result) {
                    userRecentProjects = result;
                    populateRecentProjects(result);
                    $(this).find(".flyout:eq(0)").show();
                    $(this).addClass("on");
                    $(this).next().addClass("noBg");
                },
                function (errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });


    (function (newHeader) {
        newHeader.find(".topMenu .menus li").hover(function(){
            $(this).find(".flyout:eq(0)").show();
            $(this).addClass("on");
            $(this).next().addClass("noBg");
            $(this).find(".flyout:eq(0)").find(".ellipsis").ellipsis().parent().append('<span class="arrow"></span>');
        },function(){
            $(this).find(".flyout:eq(0)").hide();
            $(this).removeClass("on");
            $(this).next().removeClass("noBg");
        });
        newHeader.find(".mainMenu .menus li").hover(function () {
            $(this).find(".flyout").show();
            $(this).addClass("on");
        }, function () {
            $(this).find(".flyout").hide();
            $(this).removeClass("on");
        });

        newHeader.find(".flyout a").click(function () {
            $(this).closest(".flyout").hide();
            return true;
        });
    })($("#newHeader"));

    // functions for newSidebar
    (function(sidebar){
        sidebar.find(".switchBtn").click(function(){
            $("#mainContent").toggleClass("newSidebarCollapse");
            $(window).trigger("resize");
            return false;
        });
        sidebar.find(".sideBox dt").click(function(){
            $(this).closest(".sideBox").toggleClass("collapse");
            return false;
        })

    })($(".newSidebar"));

});

function resetForm(className) {
    $('.'+className).each(function(){
        this.reset();
    });
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
