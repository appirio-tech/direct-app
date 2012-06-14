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

var ctx = "/mobile";

function showErrorMessage(errorMessage) {
    alert(errorMessage);
}

$(document).ready(function(e) {	
	var matchPass=0;
	if ($.browser.mozilla == true && $.browser.version == '1.9.1') {//FF 3.5 CSS Hack
      	$(".login_input")
		.css("padding","0 6px")
		.css("height","25px")
		.css("padding-top","2px");
    }
    $(".menu_content_overlay").hide();
	
	/* event handler for tablet & showcase */
	$(".showcase_trigger").hover(showMiniScreenshot);
	$(".mySlidePaggination").hover(showMiniScreenshot);
	$("#tablet_home_button").click(function(){
		 $(".menu_tablet a").removeClass("menu_tablet_li_hover");
		$(".menu_tablet li").removeClass("menu_tablet_li_hover");
		 $(".menu_content_overlay").show();
		 return false;
	});
    $(".modal").click(function(){
		$(".outer_container_showcase").fadeOut();
		$(".outer_container_register").fadeOut();
		$(".modal").fadeOut();
		return false;
	});
	$("#close_showcase").click(function(){
		$(".outer_container_showcase").fadeOut();
		$(".modal").fadeOut();
		return false;
	});
	
	/* event handler for login*/
	$(".navLogin").click(function(){
        $(document).scrollTop(0);
        $("#handle_login").val("");
        $("#password_login").val("");
		$("#login_wrap").show();
		$("#nav_menu").hide();
		return false;
	});	 
	$("#cancel_login_button").click(function(){
		$("#login_wrap").hide();
		$("#nav_menu").show();
		return false;
	});	
	
	$(".login_input").bind('focus blur',function(){$(this).toggleClass('login_input_focus')});//focus hack for ie7
	
	/* event handler for register*/
	$("#show_register").click(function(){
		$(".outer_container_register").fadeIn();
		$(".modal").fadeIn();
        $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime());
        
        $(".input_register, .input_register_2").val("");
        $(".input_register,.input_register_2").removeClass("input_error");
        $(".error_reg_info").hide();
        $("#acpt").attr("checked", "");
        $("#register_button").attr("disabled",true).removeClass("register_button").addClass("register_button_disabled");
		return false;
	});
    $("#tryAnotherCode").click(function() {
        $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime());
    });
	$("#close_register").click(function(){
		$(".outer_container_register").fadeOut();
		$(".modal").fadeOut();
		return false;
	});
	$("#re_password,#password").keyup(function(){
		var pass=$("#password").val();
		var re_pass=$("#re_password").val();
		if(pass==re_pass){
			$("#password,#re_password").removeClass("input_register_error");
			matchPass=1;
		}else{
			$("#password,#re_password").addClass("input_register_error");
			matchPass=0;
		}
		var i;
		for(i=1;i<=4;i++){
			$(".password_meter").removeClass("password_meter_"+i);
		}
		if(matchPass==1){
			var num=PasswordStrength($(this).val());			
			for(i=1;i<=num;i++){
				$("#meter_"+i).addClass("password_meter_"+num);
			}
		}
	});
	$("#reset_reg_form").click(function(){
		$(".input_register,.input_register_2").val("");
		$("#acpt").removeAttr("checked");
		var i;
		for(i=1;i<=4;i++){
			$(".password_meter").removeClass("password_meter_"+i);
		}
		$("#password,#re_password").removeClass("input_register_error");
		$(".input_register,.input_register_2").removeClass("input_error");
		$("#acpt").trigger("change");
		$(".error_reg_info").hide();
		return false;
	});
	$("#acpt").change(function(){//disable-enable submit button
		if($(this).is(":checked")){
			$("#register_button").removeAttr("disabled").removeClass("register_button_disabled").addClass("register_button");
		}else{
			$("#register_button").attr("disabled",true).removeClass("register_button").addClass("register_button_disabled");
		}
	});
	$("#register_button").click(function(){
		$(".error_reg_info").hide();
		$(".input_register,.input_register_2").removeClass("input_error");
		var fN=$("#firstName");
		var lN=$("#lastName");
		var h=$("#handle");
		var e=$("#email");
        var pas=$("#password");
        var confirmPas=$("#re_password");
        var verCode=$("#veriCode");
        var ok = true;
        if (!cekField(fN)) {
            ok = false;
            fN.addClass("input_error");
        }
        if (!cekField(lN)) {
            ok = false;
            lN.addClass("input_error");
        }
        if (!cekField(h)) {
            ok = false;
            h.addClass("input_error");
        }
        if (!cekField(e)) {
            ok = false;
            e.addClass("input_error")
        }
        if (!cekField(pas)) {
            ok = false;
            pas.addClass("input_error");
        }
        if (!cekField(confirmPas)) {
            ok = false;
            confirmPas.addClass("input_error");
        }
        if (!cekField(verCode)) {
            ok = false;
            verCode.addClass("input_error");
        }
        var message = "";
        if (!ok) message = "Please fill out all fields";
        if (cekField(pas) && cekField(confirmPas) && pas.val() != confirmPas.val()) {
            ok = false;
            confirmPas.addClass("input_error");
            pas.addClass("input_error");
            if (message.length > 0) message += "<br/>";
            message += "Two passwords doesn't match";
        }
        if (!ok) {
            $(".error_reg_info").html(message).show();
            return false;
        }
        
        $.ajax({
              type: 'POST',
              url:  ctx + "/register",
              data: "formData.firstName="+fN.val()+"&formData.lastName="+lN.val()+"&formData.handle="+h.val()+"&formData.email="+e.val()+"&formData.password="+pas.val()+"&formData.verificationCode="+verCode.val()+"&formData.moduleFrom=mobile",
              cache: false,
              dataType: 'json',
              async : false,
              success: function(jsonResult) {
                handleJsonResult(jsonResult,
                function(result) {
                    if (jsonResult.jsonData.fieldErrors) {
                        var hasError = false;
                        for (var fname in jsonResult.jsonData.fieldErrors) {
                            hasError = true;
                            break;
                        }
                        if (hasError) {
                        	$(".input_register,.input_register_2").removeClass("input_error");
                        }
                        var messages = "";
                        for (var fname in jsonResult.jsonData.fieldErrors) {
                            hasError = true;
                            $('#' + fname).addClass("input_error");
                            var errors = jsonResult.jsonData.fieldErrors[fname];
                            for (var ind = 0; ind < errors.length; ind++) {
                                if (messages.length > 0) messages += "<br/>";
                                messages += errors[ind];
                            }
                        }
                        if (hasError) {
                            $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime());
                            $(".error_reg_info").html(messages).show();
                            return;
                        }
                    }
                    $(".outer_container_register").fadeOut();
                    $(".modal").fadeOut();
                    $("#login_wrap").hide();
                    $("#nav_menu").show();

                    alert("Your account has been created, please check your email and activate it.");
//                    $.ajax({
//                      type: 'POST',
//                      url:  ctx + "/login",
//                      data: {"username":h.val(), "password":pas.val(), "rememberMe":false},
//                      cache: false,
//                      dataType: 'json',
//                      async : false,
//                      success: function(jsonResult) {
//                        handleJsonResult(jsonResult,
//                        function(result) {
//                            if (result.success) {
//                                $("#close_register").click();
//        
//                                $(".logindiv").hide();
//                                $(".logged_acc").show();
//                                $("#menu_logout").parent().show();
//                                $("#profile").html(h.val());
//                                $("#nav_menu").addClass("logged_nav_menu");
//                                $("#profile").attr("href", "http://www.topcoder.com/tc?module=SimpleSearch&ha=" + h.val());
//                                $("#login_wrap").hide();
//                                $("#nav_menu").show();
//                                $(".startNP").removeClass("navLogin").unbind();
//                                $(".startNP").attr("href", "/direct/createNewProject.action");
//                            } else {
//                                alert("Can't login");
//                            }
//                        },
//                        function(errorMessage) {
//                            showErrorMessage(errorMessage);
//                        });
//                      }
//                    });
                },
                function(errorMessage) {
                    showErrorMessage(errorMessage);
                });
              }
            });
	});
	$('.acct').click(function(){
        window.open(this.href);
        return false;
    });
	
	//event handler for press
	$(".press_col").hover(function(){
		var div=(($(this).attr("id")).split("_"))[2];
		$("#pres_col_head_"+div).addClass("press_col_hover");
	},function(){
		var div=(($(this).attr("id")).split("_"))[2];
		$("#pres_col_head_"+div).removeClass("press_col_hover");
	});
	$(".press_col_head").hover(function(){
		var div=(($(this).attr("id")).split("_"))[3];
		$("#pres_col_"+div).addClass("press_col_hover");
	},function(){
		var div=(($(this).attr("id")).split("_"))[3];
		$("#pres_col_"+div).removeClass("press_col_hover");
	});
	
	//set vertical align for press headline
	//vertical align using javascript make it compatible with any browser, especcialy ie7
	verPressHead();
	
	//event handler for showcase
	var current=1;
	var numSlide=0;
	var width=800;
	$(".slide").css("left",width+"px");
	$("#slide_"+current).css("left","0"); 
	$('.slide').each(function(){
		numSlide++;
	});
	$(".linkSlide").click(function(){
		var newO=parseInt((($(this).attr("id")).split("_"))[1]);
		
		if(newO!=current){
			var mode=(newO>current)?"next":"prev";
			runSlide(current,newO,width,mode);
			current=newO;
		}
		return false;
	});
	$("#prevButton").click(function(){
		if(!$(".slide").is(":animated")){
			var prev=(current>1)?(current-1):numSlide;
			runSlide(current,prev,width,"prev");
			current=prev;	
		}	
		return false;
	});
	$("#nextButton").click(function(){
		if(!$(".slide").is(":animated")){
			var next=(current<numSlide)?(current+1):1;
			runSlide(current,next,width,"next");
			current=next;
		}
		return false;
	});
	var showDetails = function() {
		$(".outer_container_showcase").fadeIn();
		$(".modal").fadeIn();
		var num=parseInt((($(this).parent().attr("id")).split("_"))[1]);
		$(".slide").css("left",width+"px");
		$("#slide_"+num).css("left","0"); 
		$(".linkSlide").parent().removeClass("current");
		$("#linkSlide_"+num).parent().addClass("current");
		$(".slides_container").css("height",$("#slide_"+num).height());
		current = num;
		return false;
	}

	$(".showcase_trigger").click(showDetails);
	$(".menu_content").click(showDetails);
    
    $("#proccess_login_button").click(function() {
        var username = $('#handle_login').val();
		var password = $('#password_login').val();
        if ($.trim(username).length == 0) {
            alert("Please input your handle");
            return;
        }
        if ($.trim(password).length == 0) {
            alert("Please input your password");
            return;
        }
        var rememberMe = false;
        $.ajax({
          type: 'POST',
          url:  ctx + "/login",
          data: {"username":username, "password":password, "rememberMe":rememberMe},
          cache: false,
          dataType: 'json',
          async : false,
          success: function(jsonResult) {
            handleJsonResult(jsonResult,
            function(result) {
                if (result.success) {
                    $(".logindiv").hide();
                    $(".logged_acc").show();
                    $("#profile").html(username);
                    $("#menu_logout").parent().show();
                    $("#nav_menu").addClass("logged_nav_menu");
                    $("#profile").attr("href", "http://www.topcoder.com/tc?module=SimpleSearch&ha=" + username);
                    $("#login_wrap").hide();
                    $("#nav_menu").show();
                    $(".startNP").removeClass("navLogin").unbind();
                    $(".startNP").attr("href", "/direct/createNewProject.action");
                } else {
                    alert("Incorrect username and/or password");
                }
            },
            function(errorMessage) {
                showErrorMessage(errorMessage);
            });
          }
        });
    });
    
    $("#menu_logout").click(function() {
        $.ajax({
            type: 'GET',
            url:  ctx + "/logout",
            data: "",
            cache: false,
            dataType: 'json',
            async : false,
            success: function(jsonResult) {
            	$(".logindiv").show();
                $(".logged_acc").hide();
                $("#nav_menu").removeClass("logged_nav_menu");
                $("#menu_logout").hide();
                $(".startNP").addClass("navLogin").click(function() {
                    $(document).scrollTop(0);
                    $("#handle_login").val("");
                    $("#password_login").val("");
                    $("#login_wrap").show();
                    $("#nav_menu").hide();
                    return false;
                });
                $(".startNP").attr("href", "#");
            }
		});
    });
});

function showMiniScreenshot() {
	$(".menu_tablet li").removeClass("menu_tablet_li_hover");
	$(this).parents("li").addClass("menu_tablet_li_hover");
	$(".menu_content_overlay").hide();
}

function PasswordStrength(passw){
	// initialized point to 0
	var pts = 0;
	// contains at least 5 characters
	pts += (passw.length > 5) ? 1 : 0;
	if (pts == 0) return pts;
	// contains number
	pts += (/\d/.test(passw)) ? 1 : 0;
	// contains at least 1 uppercase and 1 lowercase
	pts += (/[a-z]/.test(passw) && /[A-Z]/.test(passw)) ? 1 : 0;
	// contains at least 1 symbol
	pts += (/[^a-zA-Z0-9]/.test(passw)) ? 1 : 0;
	return pts;
}
function cekField(str){//function for check register field
	str=str.val();
	var lt=str.length;
	var i=0;
	var cnt=0;
	for(i=0;i<lt;i++){
		s=str.substr(i,1);
		if(s!="" && s!=" ") cnt++;
		if(cnt>0) break;
	}
	if(cnt>0) return true;
	else return false;
}

function getPadd(hC,hI){
	return (hC-hI)/2;
}
function verPressHead(){
	var arrH=new Array;
	//dynamic function, get any .press_col_head element
	$('.press_col_head').each(function(index) {
		arrH[index]=$(this).height();
	});
	var maxH=0;
	for(i=0;i<arrH.length;i++){
		if(arrH[i]>maxH){
			 maxH=arrH[i];
		}
	}
	$('.press_col_head').each(function(index) {
		$(this).css("padding-top",getPadd(maxH,$(this).height()));
	});
}
//do easing slider
function runSlide(curO,newO,width,mode){
	$(document).ready(function(e) {
		$(".slides_container").css("height",$("#slide_"+newO).height());
		if(mode=="next"){
			$("#slide_"+curO).animate({left: '-'+width});
			$("#slide_"+newO).css("left",width+"px").animate({left: '0'});
		}else{
			$("#slide_"+curO).animate({left: width});
			$("#slide_"+newO).css("left","-"+width+"px").animate({left: '0'});
		}
		$(".linkSlide").parent().removeClass("current");
		$("#linkSlide_"+newO).parent().addClass("current");
		$(".menu_tablet li").removeClass("menu_tablet_li_hover");
		$("#li_"+newO).addClass("menu_tablet_li_hover");
		$(".menu_content_overlay").hide();
	});
}