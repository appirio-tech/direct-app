// JavaScript Document
function handleJsonResult(result, succHandler, failHandler) {
    if (result.jsonData.error) {
        if (failHandler) {
            failHandler(result.jsonData.message);
        }
    } else {
        if (succHandler) {
            succHandler(result.jsonData.result);
        }
    }
}

var ctx = "/present";

function showErrorMessage(errorMessage) {
    alert(errorMessage);
}

$(document).ready(function() {
	//text shadow
	$('#content .banner h1,#content .banner h2').css({'text-shadow':'0px 5px 2px #000'});
	$('#content .banner .stepNum em').css({'text-shadow':'0px 3px 2px #434343'});
	$('.button2,.bigBtn,.button3').css({'text-shadow':'2px 2px 0 #305862'});
	
	//ie fix
	if(jQuery.browser.msie){
		$('#footer .bottomLine').css({'margin-left':'1px'});
		$('#content .mainBody .publicationSec .bottom').css({'margin-top':'35px'});
		if(jQuery.browser.version>7)
			$('#content .mainBody .rightCol .bottom').css({'margin-top':'75px'})
	}
	//firefox fix
	if(jQuery.browser.mozilla){
		$('#footer .bottomLine').css({'margin-left':'1px'})
	}
	
	//login modal
	var screenwidth,screenheight,popWinPosLeft,popWinPosTop,mytop;
	var docheight = $(document).height();
	var docwidth = $(document).width();
	screenwidth = $(window).width();
	screenheight = $(window).height();
	$(window).scroll(function(){
		popWinPosTop = screenheight/2 - $('#loginModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#loginModal').width()/2;
		mytop = $(document).scrollTop();
		if(popWinPosTop<0)
			$('#loginModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		else
			$('#loginModal').css({"position":"fixed","top":popWinPosTop});
		popWinPosTop = screenheight/2 - $('#registerModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#registerModal').width()/2;
		if(popWinPosTop<0){
			$('#registerModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		}
		else{
			$('#registerModal').css({"position":"fixed","top":popWinPosTop});
		}
		popWinPosTop = screenheight/2 - $('.showcaseModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('.showcaseModal').width()/2;
		if(popWinPosTop<0)
			$('.showcaseModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		else
			$('.showcaseModal').css({"position":"fixed","top":popWinPosTop});
	});
	$(window).resize(function(){
		screenwidth = $(window).width();
		screenheight = $(window).height();
		docheight = $(document).height();
		docwidth = $(document).width();
		popWinPosLeft = screenwidth/2 - $('#loginModal').width()/2;
		popWinPosTop = screenheight/2 - $('#loginModal').height()/2;
		$("#greybackground").css({"height":docheight,"width":docwidth});
		if(popWinPosTop<0)
			$('#loginModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		else
			$('#loginModal').css({"position":"fixed","top":popWinPosTop});
		popWinPosTop = screenheight/2 - $('#registerModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#registerModal').width()/2;
		if(popWinPosTop<0){
			$('#registerModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		}
		else{
			$('#registerModal').css({"position":"fixed","top":popWinPosTop});
		}
		popWinPosTop = screenheight/2 - $('.showcaseModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('.showcaseModal').width()/2;
		if(popWinPosTop<0)
			$('.showcaseModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"position":"absolute"});
		else
			$('.showcaseModal').css({"position":"fixed","top":popWinPosTop});
	});
	//login
	$('.navLogin').live("click", function(e) {
        $("#veriImg").attr("src", "/present/captchaImage.action?t=" + new Date().getTime());
        $("input[type='password']").val("");
		clearLoginErrStyle();
        popWinPosTop = screenheight/2 - $('#loginModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#loginModal').width()/2;
        $("body").append("<div id='greybackground'></div>");
		$("#greybackground").css({"position":'absolute',"left":0,"top":0,"height":docheight,"width":docwidth,'z-index':900,'background':'url(/images/ppt/overlay-bg.png) repeat left top'});
		$('#loginModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"top":(popWinPosTop>0)?(popWinPosTop):0});
		$('#loginModal').show("fast");
    });
    $(".tryAnotherCode").click(function() {
        $("#veriImg").attr("src", "/present/captchaImage.action?t=" + new Date().getTime());
    });
	function clearLoginErrStyle(){
		$('.loadingbackground').remove();
		$('#username').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.handleErr').hide();
		$('#password').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.passwordErr').hide();
		$('#firstName').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.firstNameErr').hide();
		$('#lastName').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.lastNameErr').hide();
		$('#handle').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.handleErr').hide();
		$('#email').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.emailErr').hide();
		$('#regPassword').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.regPasswordErr').hide();
		$('#confirmPassword').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.passwordNotMactchErr').hide();
		$('#veriCode').css({'border':'1px solid #8a8a8a','background':'#fff'});
		$('.veriCodeErr').hide();
        $('.policyErr').hide();
        $('.loginError').hide();
	}
	function showLoginErrStyle(e){
		e.css({'border':'1px solid #e71f19','background':'#fdeded'});
	}
	var users = ["test","admin","demo"];
	var passwords = ["test","admin","demo"];
	//login demo
	$('#loginModal .loginBtn').click(function(e) {
		clearLoginErrStyle();
		var flag = true;
        var username = $('#username').val();
		var password = $('#password').val();
		if(!username){
			showLoginErrStyle($('#username'));
			$('#loginModal span.handleErr').html('Please fill in your username');
			$('.handleErr').show();
			flag = false;
		}
		if(!password){
			showLoginErrStyle($('#password'));
			$('#loginModal span.passwordErr').html('Please fill in your password');
			$('.passwordErr').show();
			flag = false;
		}
		if(!flag)return;
        var rememberMe = $("#rememberMe").is(":checked");
		//start loading
		$('#loginModal').append("<div class='loadingbackground'><div class='loadingIndicator'><div class='loadingCover'></div></div><div class='indicatorMsg'>Logging in,please wait...</div></div>");
		loginProcess = 0;
        longProcessorIntervalId = setInterval(loginLoading, 10);
        loginLoading();
        $.ajax({
          type: 'POST',
          url:  ctx + "/login",
          data: {"username":username, "password":password, "rememberMe":rememberMe},
          cache: false,
          dataType: 'json',
          async : false,
          success: function(jsonResult) {
            clearInterval(longProcessorIntervalId);
            handleJsonResult(jsonResult,
            function(result) {
                if (result.success) {
                    $('#loginModal .indicatorMsg').html('Login succeeded!')
					setTimeout(function(){
						$('#greybackground').remove();
						$('#loginModal').hide();
						$('.nav .welcomeSection').show();
						$('.nav .loginSection').hide();	
                        $(".handle").html(username);
                        $(".startNP").removeClass("navLogin").unbind();
                        $(".startNP").attr("href", "/direct/createNewProject.action");
					},500);
                } else {
                    $('#loginModal .loadingbackground').remove();
					showLoginErrStyle($('#username'));
                    showLoginErrStyle($('#password'));
                    $('.loginError').show();
                }
            },
            function(errorMessage) {
                showErrorMessage(errorMessage);
            });
          }
        });
    });
	function loginLoading(){
		loginProcess++;
        if (loginProcess > 100) return;
		$('#loginModal .loadingCover').css({'width':loginProcess+'%'});
	}
	//logout
	$('.nav .logoutLink').click(function(e) {
		$.ajax({
            type: 'GET',
            url:  ctx + "/logout",
            data: "",
            cache: false,
            dataType: 'json',
            async : false,
            success: function(jsonResult) {
            	$('.nav .welcomeSection').hide();
        		$('.nav .loginSection').show();
                $(".startNP").attr("href","javascript:;");
                $(".startNP").addClass("navLogin");
            }
		});
    });
	//reset input 
	$('.resetBtn').click(function(e) {
        $('.inputCre').val('');
		clearLoginErrStyle();
    });
	//register submit
	$('#registerModal .submitBtn').click(function(e) {
        clearLoginErrStyle();
		var flag = true;
		if(!$('#registerModal #firstName').val()){//first name empty
			showLoginErrStyle($('#firstName'));
			$('#registerModal .firstNameErr').show();
			flag = false;
		}
        var firstName = $('#registerModal #firstName').val();
		if(!$('#lastName').val()){
			showLoginErrStyle($('#lastName'));
			$('.lastNameErr').show();
			flag = false;
		}
        var lastName = $('#lastName').val();
		if(!$('#handle').val()){
			showLoginErrStyle($('#handle'));
			$('.handleErr').show();
			flag = false;
		}
        var handle = $('#handle').val();
		if(!$('#email').val()){
			showLoginErrStyle($('#email'));
			$('.emailErr').show();
			flag = false;
		}
        var emailAddress = $('#email').val();
		if(!$('#regPassword').val()){
			showLoginErrStyle($('#regPassword'));
			$('.regPasswordErr').show();
			flag = false;
		}
        var password = $('#regPassword').val();
		if(!$('#confirmPassword').val()){
			showLoginErrStyle($('#confirmPassword'));
			$('.passwordNotMactchErr').html('Please fill in your password again');
			$('.passwordNotMactchErr').show();
			flag = false;
		}
        if($('#regPassword').val()!=$('#confirmPassword').val()){
            showLoginErrStyle($('#confirmPassword'));
            $('.passwordNotMactchErr').html('Password does not match');
            $('.passwordNotMactchErr').show();
            flag = false;
        }
        if (!$("#accPol").is(":checked")) {
            $('.policyErr').show();
            flag = false;
        }
		if(!$('#veriCode').val()){
			showLoginErrStyle($('#veriCode'));
			$('.veriCodeErr').show();
			flag = false;
		}
        var veriCode = $('#veriCode').val();
		if(flag){
			//start loading
			$('#registerModal').append("<div class='loadingbackground'><div class='loadingIndicator'><div class='loadingCover'></div></div><div class='indicatorMsg'>Creating account,please wait...</div></div>");
			registerProcess = 0;
            registerProcessIntervalId = setInterval(registerLoading, 10);
            
            $.ajax({
              type: 'POST',
              url:  ctx + "/register",
              data: "formData.firstName="+firstName+"&formData.lastName="+lastName+"&formData.handle="+handle+"&formData.email="+emailAddress+"&formData.password="+password+"&formData.verificationCode="+veriCode,
              cache: false,
              dataType: 'json',
              async : false,
              success: function(jsonResult) {
                clearInterval(registerProcessIntervalId);
                handleJsonResult(jsonResult,
                function(result) {
                    if (jsonResult.jsonData.fieldErrors) {
                        var hasError = false;
                        for (var fname in jsonResult.jsonData.fieldErrors) {
                            hasError = true;
                            break;
                        }
                        if (hasError) {
                        	clearLoginErrStyle();
                        }
                        for (var fname in jsonResult.jsonData.fieldErrors) {
                            hasError = true;
                            showLoginErrStyle($('#' + fname));
                            var errors = jsonResult.jsonData.fieldErrors[fname];
                            var errEle = $(".errorMsg", $('#' + fname).parent());
                            errEle.html("");
                            for (var ind = 0; ind < errors.length; ind++) {
                                if (ind > 0) errEle.html("<br/>");
                                errEle.html(errors[ind]);
                            }
                            errEle.show();
                        }
                        if (hasError) {
                            $("#veriImg").attr("src", "/present/captchaImage.action?t=" + new Date().getTime());
                            return;
                        }
                    }
                    $('#registerModal .indicatorMsg').html('Account created.<br/>Thank you for your registration!');
                    $.ajax({
                      type: 'POST',
                      url:  ctx + "/login",
                      data: {"username":handle, "password":password, "rememberMe":false},
                      cache: false,
                      dataType: 'json',
                      async : false,
                      success: function(jsonResult) {
                        handleJsonResult(jsonResult,
                        function(result) {
                            if (result.success) {
                                setTimeout(function(){
                                    $('#greybackground').remove();
                                    $('#registerModal').hide();
                                    $('.nav .welcomeSection').show();
                                    $('.nav .loginSection').hide();	
                                    $(".handle").html(handle);
                                    $(".startNP").removeClass("navLogin").unbind();
                                    $(".startNP").attr("href", "/direct/createNewProject.action");
                                },500);
                            } else {
                                alert("Can't login");
                            }
                        },
                        function(errorMessage) {
                            showErrorMessage(errorMessage);
                        });
                      }
                    });
                },
                function(errorMessage) {
                    showErrorMessage(errorMessage);
                });
              }
            });
		}
    });
	function registerLoading(){
		registerProcess++;
        if (registerProcess > 100) return;
		$('#registerModal .loadingCover').css({'width':registerProcess+'%'});
	}
	$('#loginModal .registerTab').click(function(e) {
		$('#loginModal').hide("fast");
		clearLoginErrStyle();
		popWinPosTop = screenheight/2 - $('#registerModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#registerModal').width()/2;
		mytop = $(document).scrollTop();
		$('#registerModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"top":(popWinPosTop>0)?(popWinPosTop):mytop});
		$('#registerModal').show("fast");
	});
	$('#registerModal .loginTab').click(function(e) {
		$('#registerModal').hide("fast");
		clearLoginErrStyle();
		popWinPosTop = screenheight/2 - $('#loginModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('#loginModal').width()/2;
		$('#loginModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"top":(popWinPosTop>0)?(popWinPosTop):0});
		$('#loginModal').show("fast");
	});
	
	//show case pop up
	$('.showcaseImg').click(function(e) {
        popWinPosTop = screenheight/2 - $('.showcaseModal').height()/2;
		popWinPosLeft = screenwidth/2 - $('.showcaseModal').width()/2;
		mytop = $(document).scrollTop();
        $("body").append("<div id='greybackground'></div>");
		$("#greybackground").css({"position":'absolute',"left":0,"top":0,"height":docheight,"width":docwidth,'z-index':900,'background':'url(/images/ppt/overlay-bg2.png) repeat left top'});
		$('.showcaseModal').css({"left":(popWinPosLeft>0)?popWinPosLeft:0,"top":(popWinPosTop>0)?(popWinPosTop):mytop});	
    });
	$('.showcaseImg1').click(function(e) {
		$('#showcaseModal1').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel1').jcarousel({
			visible:1,
			scroll:1
		});
	});
	$('.showcaseImg2').click(function(e) {
		$('#showcaseModal2').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel2').jcarousel({
			visible:1,
			scroll:1
		});
	});
	var index3 = 1;
	$('.showcaseImg3').click(function(e) {
		$('#showcaseModal3').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel3').jcarousel({
			visible:1,
			scroll:1,
			itemLoadCallback: function(carousel, state){
				index3 = carousel.first;
			}
		});
	});
	var index4 = 1;
	$('.showcaseImg4').click(function(e) {
		$('#showcaseModal4').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel4').jcarousel({
			visible:1,
			scroll:1,
			itemLoadCallback: function(carousel, state){
				index4 = carousel.first;
			}
		});
	});
	$('.showcaseImg5').click(function(e) {
		$('#showcaseModal5').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel5').jcarousel({
			visible:1,
			scroll:1
		});
	});
	$('.showcaseImg6').click(function(e) {
		$('#showcaseModal6').show("fast");
		//showcaseCarousel
		$('#showcaseCarousel6').jcarousel({
			visible:1,
			scroll:1
		});
	});
	$('.showcaseModal .close').click(function(e) {
        $('.showcaseModal').hide('fast');
		$('#greybackground').remove();
    });
	$('#greybackground').live('click',function(e) {
        $('#greybackground').remove();
		$('.showcaseModal').hide('fast');
		$('#registerModal').hide('fast');
		$('#loginModal').hide('fast');
    });

	$('#showcaseModal1 .label').click(function(e){
		window.open("/images/ppt/showcase-1-full.png");
	});
	$('#showcaseModal2 .label').click(function(e){
		window.open("/images/ppt/showcase-2-full.jpg");
	});
	$('#showcaseModal3 .label').click(function(e){
		window.open("/images/ppt/3/showcase-3"+index3+"-full.jpg");
	});
	$('#showcaseModal4 .label').click(function(e){
		window.open("/images/ppt/4/showcase-4"+index4+"-full.png");
	});
	$('#showcaseModal5 .label').click(function(e){
		window.open("/images/ppt/showcase-5-full.jpg");
	});
	$('#showcaseModal6 .label').click(function(e){
		window.open("/images/ppt/showcase-6-full.jpg");
	});
});