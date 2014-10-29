/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
/**
 * This js used to render studio jsp views of single submission, including grid view and full size view.
 *
 * Author: minhu
 * Version 1.0 (Release Assembly - TopCoder Cockpit Submission Viewer Revamp)
 */
// resize the image keep width-height ratio 
function imgLoaded(obj,maxHeight, maxWidth){
    var img=$(obj);
    var w=obj.width;
    var h=obj.height;
    if (w && h) {
        var r=Math.max(w/maxHeight, h/maxWidth);
        var nw=w/r;
        var nh=h/r;
        var left=(maxHeight-nw)/2;
        var top=(maxWidth-nh)/2;
        img.attr('width', nw);
        img.attr('height', nh);
        img.css({position:"relative",left:left,top:top});
    }	  
}	
// JavaScript Document
$(document).ready(function(){
	//singleCarousel
	function itemVisibleInCallbackFunction(carousel){
		$('.imagesPage p strong').html('Image '+carousel.first);
	}
	$('#singleCarouselLookup a').each(function(){
	    $(this).attr('href',$.trim($(this).attr('href')));
	});
	
	function itemLoadCallbackFunction(carousel){
	    // load dynamically
        for (var i = carousel.first; i <= carousel.last+1; i++) {
            if (carousel.has(i)) {
                continue;
            }
            if (i > $('#singleCarouselLookup li').length) {
                break;
            }
            var img = $('#singleCarouselLookup li').eq(i-1).html().
                replace('#IMGSTART#','<img id="JCarouselImage' + i +'"').replace('#IMGEND#',
                    ' onload="imgLoaded(this, 800, 600)"/>');
            
            carousel.add(i, img);
        }	    
	}
	if($('#singleCarousel').length > 0){
		$('#singleCarousel').jcarousel({
			scroll:1,
			size:$('#singleCarouselLookup li').length,
			itemVisibleInCallback: itemVisibleInCallbackFunction,
			itemLoadCallback: itemLoadCallbackFunction
		});	
	}
	$('.singleViewDiv').hide();
	
	function closeWindow(){
		var browserName=navigator.appName; 
		if($.browser.mozilla){
			netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite"); 
    		window.open('','_self');
    		window.close();
		}else if($.browser.msie){
			//window.opener=null; window.open('','_self');
   			window.opener = "whocares"; 
   			window.close(); 
		}else{
			window.open('','_self');
    		window.close();	
		}
	}
    var submissionArtifactCount=$('#submissionArtifactCount').val();
	var artifactNum=$('.lblID:eq(0)').html();
	if (submissionArtifactCount) {
	    if (artifactNum==submissionArtifactCount) {
	        $('.nextLink').hide();    
	    } 
	    if (artifactNum==1) {
	        $('.prevLink').hide();    
	    }
	}
	
	$('.prevLink,.nextLink').live('click',function(){
        var offset=$(this).hasClass('nextLink') ? 1 : -1;
	    artifactNum=offset+parseInt($('.lblID:eq(0)').html());
        
        $('div.fullSizePic').hide();
        $('div.full' + artifactNum).show();
        var filename=$('#artifact'+artifactNum).val();
        $('div.title h1').text(filename);
        $('.lblID').html(artifactNum);
        if (artifactNum==submissionArtifactCount) {
            $('.nextLink').hide();    
        } else {
            $('.nextLink').show();    
        }
        if (artifactNum==1) {
            $('.prevLink').hide();    
        } else {
            $('.prevLink').show(); 
        }
	});
	
	$('.thumbnailsList a,.singleSubmission a,.helpLink').attr('target','_blank');
	
	$('.singleSubmission a').hover(function(){
		$(this).find('span').show();										
	},function(){
		$(this).find('span').hide();		
	});
	
	$('.gridView,.singleView').click(function(){
	        if ($(this).hasClass('gridView') && !$(this).hasClass('gridViewActive')) {
	            $('.imagesPage').find('p').hide();
	            $('.gridViewDiv').show();
	            $('.singleViewDiv').hide();
	            $('.singleView').removeClass('singleViewActive');
	            $(this).addClass('gridViewActive');
	        } else if ($(this).hasClass('singleView') && !$(this).hasClass('singleViewActive')) {
	            $('.imagesPage').find('p').show();
	            $('.gridViewDiv').hide();
	            $('.singleViewDiv').removeClass('hide');
	            $('.singleViewDiv').show();
	            $('.gridView').removeClass('gridViewActive');
	            $(this).addClass('singleViewActive');
	        } 
	});
	
	//hack
	if($.browser.msie&&($.browser.version == "7.0")){
		$('.mainArea .inactiveBar a.gridView span').css('background','url(images/grid-view-icon.png) no-repeat left 7px');	
		$('.mainArea .inactiveBar a.singleView span').css('background','url(images/single-view-icon.png) no-repeat left 6px');	
		$('.mainArea .pageBar .showPerPage .select').css('position','relative');
		$('.mainArea .pageBar .showPerPage .select').css('top','2px');
		$('.mainArea .pageBar .showPerPage').css('top','-8px');
	}
	
	var iItem = parseInt($('.thumbnailsList').width()/($('.thumbnailsItem').width()+32));
	var iLeft =  $('.thumbnailsList').width() - ($('.thumbnailsItem').width()+32)*iItem;
	$('.thumbnailsList').css('margin-left',iLeft/2);
	
	$(window).resize(function(){
		$('.thumbnailsList').css('margin-left','0');
		var iItem = parseInt($('.thumbnailsList').width()/($('.thumbnailsItem').width()+32));
		var iLeft =  $('.thumbnailsList').width() - ($('.thumbnailsItem').width()+32)*iItem;
		$('.thumbnailsList').css('margin-left',iLeft/2);	
	});

	$('a#downTips').bind('mouseover',function(){$(".infoPointBox").removeClass("none")});	
	$('a#downTips').bind('mouseout',function(){$(".infoPointBox").addClass("none")});
    $('div.full' + artifactNum).show();
    
	$('img.studioSubmissionImg').each(function(){
	    imgLoaded(this, 200, 200);
	});
	
	$('#bankSelectionItem a.thumb img').each(function(){
	    imgLoaded(this, 66, 66);
	});
});
