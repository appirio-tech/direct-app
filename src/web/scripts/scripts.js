/**
 *  Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 *
 *  The JS script for the home page.
 *
 *  Version 1.1 - Release Assembly - TC Cockpit Sidebar Header and Footer Update
 *  - Added codes to initialize the help widget in right sidebar.
 *
 * @author Blues
 * @version 1.1
 */
$(document).ready(function(){
	 
	 /* initialize the home page Carousel */
    // the index of the visible li element of the carousel
	 var current_elmnt = -1;

	 /* get the total number of li elements in the carousel */ 
	var total_elmnts = $("#myCarouselUL").children().length;
	$("#totalPages").html(total_elmnts);
	 
	mycarousel_initCallback = function(carousel) {
		jQuery('.jcarousel-control a.nextCarousel').bind('click', function() {
			 carousel.next();
		});
	}

	incElement = function(carousel,control,flag){
			current_elmnt++;
			updateHTML();
	}
	
	/* update the num of the current page */
	updateHTML = function(){ 
		 $("#currentPage").html( current_elmnt % total_elmnts + 1);
	}
	 
	 /* init the carousel */
	jQuery('#mycarousel').jcarousel({
		initCallback: mycarousel_initCallback,
		auto: 5,
		scroll: 1,
		wrap: "both",
		itemLastInCallback: incElement
    });

    /* end codes of home page carousel */

    /* help center widget tab function */

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

    var Sys = {};

    var ua = navigator.userAgent.toLowerCase();

    // FF 3
    if (ua.match(/firefox\/([\d.]+)/) != null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0] > 2) {
        $("#helpCenterWidget ul.tabList a#moreTab .middle,#helpCenterWidget ul.tabList a#lessTab .middle").css("padding", "0 3px");
        $(".tabContent").css({"position":"relative","top":"-4px"});
    }

    // FF 4
    if(ua.match(/firefox\/([\d.]+)/)!=null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0]>3){
        $("#helpCenterWidget ul.tabList a#moreTab,#helpCenterWidget ul.tabList a#lessTab").css("padding","0 14px");
        $(".tabContent").css({"position":"relative","top":"-6px"});
    }


    // change the footer class if the page is home page
    var isHomePage = (document.body.className.indexOf('homePage') !== -1);
    if(isHomePage) {
        $("#footer").addClass("homeFooter");
    }
});
