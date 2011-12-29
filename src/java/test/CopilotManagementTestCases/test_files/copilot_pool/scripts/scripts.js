// JavaScript Document

$(function(){
  
   
	 
	 /*------------------------------------------------ Carousel --*/
	 var current_elmnt = -1;  // the index of the visible li element of the carousel
	 
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
	
	
	
	/*48Hr Cockpit Header Footer and Sidebar Prototype new js codes begin from this line*/
	/*text-shadow*/
   $(".darkenBtn,#helpCenterWidget h6").css("text-shadow","0 -1px 1px #221d1a");
   /*help center widget tab function*/
   $("#helpCenterWidget .tabList li a.tab").click(function(){
		$("#helpCenterWidget .tabContent").hide();
		$(this).addClass("actived");
		$(this).parent("li").siblings("li").children("a.tab").removeClass("actived");
		switch($(this).attr("id")){
			case "FAQTab":
				$("#FAQTabCotent").show();
			break;
			case "videoTab":
				$("#videoTabCotent").show();
			break;
			case "tutorialTab":
				$("#tutorialTabCotent").show();
			break;
			case "exampleTab":
				$("#exampleTabCotent").show();
			break;
			case "moreTab":
				$(".tab").hide();
				$(".tabMore,#exampleTab").css("display","inline-block");
				$("#exampleTabCotent").show();
				$("#exampleTab").addClass("actived");
			break;
			default:
			break;
		}
	});
    $("#helpCenterWidget .tabList li a#lessTab").click(function(){
		$("#moreTab").removeClass("actived");
		$(".tab").show();
		$("#exampleTabCotent").hide();
		$("#exampleTab").removeClass("actived");
		$(".tabMore,#exampleTab").css("display","none");
		$("#FAQTabCotent").show();
		$("#FAQTab").addClass("actived");															
	});	
	var Sys = {}; 
	var ua = navigator.userAgent.toLowerCase(); // FF 3 
	if(ua.match(/firefox\/([\d.]+)/)!=null && ua.match(/firefox\/([\d.]+)/)[1].split('.')[0]>2){ 
		$("#helpCenterWidget ul.tabList a#moreTab .middle,#helpCenterWidget ul.tabList a#lessTab .middle").css("padding","0 3px");
		$(".tabContent").css({"position":"relative","top":"-4px"});
	}
   
	
});