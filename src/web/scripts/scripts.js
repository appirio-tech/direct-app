// JavaScript Document

$(document).ready(function(){
	 
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
	 
});