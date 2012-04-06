/**
  - Author: pinoydream
  - Version: 1.1
  - Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
  -
  - Version 1.1 : BUGR-6377 TC Analytics Landing Page Assembly Enhancement
  - Version 1.0 TopCoder Analytics Landing Page Assembly
*/
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

function showErrorMessage(errorMessage) {
    alert(errorMessage);
}

$(document).ready(function(){

    if (withLoggedUser) {
        $('.nav').hide();
        $('.loginNav').show();
    }

    function mycarousel_initCallback(carousel, item, idx, state){
        carousel.clip.hover(function(){
            carousel.stopAuto();
        }, function(){
            carousel.startAuto();
        });
    };

    //carousel
    $('#showcaseCarousel').jcarousel({
        auto:10,
        scroll:1,
        wrap:'circular',
        buttonNextHTML:'<a href="javascript:;"></a>',
        buttonPrevHTML:'<a href="javascript:;"></a>',
        initCallback: mycarousel_initCallback
    });

    //logout
    $('#logoutLink').live('click',function(){
        $.ajax({
            type: 'POST',
            url:  ctx + "/logout.action",
            cache: false,
            dataType: 'json',
            async : false,
            success: function(jsonResult) {
                $('.nav').show();
                $('.loginNav').hide();
            }
        });
    });

    //login
    $('#loginLink').live('click',function(){
        $('.loginSection').show();
        $('.errorMessage').hide();
        $('#userName').val('');
        $('#password').val('');
        $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime()); // load the captcha ahead
    });

    $('.loginSection .close').live('click',function(){
        $('.loginSection').hide();
        $('.loginSection').removeClass('errorLoginSection');
        $('.login div').removeClass('errorField');
        $('.password div').removeClass('errorField');
        $("#header .loginSection .errorBoth").hide();
        $('.login span').text('');
        $('.login span').text('');
    });

    function clearLoginFields() {
        $('.loginSection').removeClass('errorLoginSection');
        $('.login div').removeClass('errorField');
        $('.password div').removeClass('errorField');
        $('.login span').text('');
        $('.password span').text('');
        $('.errorMessage').hide();
    }

    function showLoginErrorFields() {
        $('.loginSection').addClass('errorLoginSection');
        $('.login span').text('');
        $('.password span').text('');
        $('.errorMessage').show();
    }

    $('#loginBtn').live('click',function(){
        clearLoginFields();
        var username = $.trim($('#userName').val());
        var password = $.trim($('#password').val());
        var flag = true;
        if(username == ''){
            $('.loginSection').addClass('errorLoginSection');
            $('.login div').addClass('errorField');
            $('.login span').text('Please enter username');
            flag = false;
        }
        if(password == ''){
            $('.loginSection').addClass('errorLoginSection');
            $('.password div').addClass('errorField');
            $('.password span').text('Please enter password');
            flag = false;
        }
        if (!flag) return;
        clearLoginFields();
        var rememberMe = false; // always false for now
        $.ajax({
            type: 'POST',
            url:  ctx + "/login.action",
            data: {"username":username, "password":password, "rememberMe":rememberMe},
            cache: false,
            dataType: 'json',
            async : false,
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                    function(result) {
                        if (result.success) {
                        setTimeout(function(){
                            // set the username here
                            $('.loggedUsername').html(username);
                            $('.loginSection').hide();
                            $('.nav').hide();
                            $('.loginNav').show();
                            }, 500);
                        } else {
                            showLoginErrorFields();
                        }
                    },
                    function(errorMessage) {
                        showErrorMessage(errorMessage);
                    });
                }
        });
    });

    $('body').live('keypress',function(event){
        if(!$('.loginSection').is(':hidden') && $('#registerModal').is(':hidden')){
            if(event.which == 13){
                $('.loginSection #loginBtn').trigger('click');
            }
        }
    });

    $('#detailModal .btn').attr('target','_blank');

    //modal
    positionModal = function(){
        var wWidth  = window.innerWidth;
        var wHeight = window.innerHeight;

        if (wWidth==undefined) {
            wWidth  = document.documentElement.clientWidth;
            wHeight = document.documentElement.clientHeight;
        }

        var boxLeft = parseInt((wWidth / 2) - ( $("#modal").width() / 2 ));
        var boxTop  = parseInt($(window).scrollTop() + 5);

        // position modal
        $("#modal").css({
            'margin': boxTop + 'px auto 0 ' + boxLeft + 'px'
        });

        $("#modalBackground").css("opacity", "0.5");

        if ($("body").height() > $("#modalBackground").height()){
            $("#modalBackground").css("height", $("body").height() + "px");
        }
    }

    loadModal = function(itemId) {
        $('#modalBackground').show();
        $(itemId).show();
        positionModal();
    }

    closeModal = function() {
        $('#modalBackground').hide();
        $('.modal').hide();
        $('#checkbox').attr('checked',false);
    }

    $('.colseModal,.closeBtn').live('click',function(){
        closeModal();
        $('.jspPane,.jspDrag').css('top','0');
    });

    setRegisterModalBox = function(isset) {
        if (isset) {
            closeModal();
            loadModal('#registerModal');
            $('#registerModal .text').val('');
        }
        $('#registerModal .errorModalBottom').hide();
        $('#registerModal .modalBottom').show();
        $('#registerModal label').removeClass('errorLabel');
        $('#registerModal .field').removeClass('errorField');
    }

    $('a.registerModal').live('click',function(){
        setRegisterModalBox(true);
    });

    $('.showcaseSection a.btn').live('click',function(){
        closeModal();
        loadModal('#detailModal');
        var items = $('#detailModalCarousel').anythingSlider({
            buildNavigation:false,
            buildStartStop:false,
            autoPlay:false,
            delay:10000,
            resumeDelay:1000,
            onSlideComplete:function(slider){
                $('.jspPane,.jspDrag').css('top','0');
            }
        });

        positionModal();
        //scroller
        var pane = $('.scrollPanel');
        pane.jScrollPane({
            verticalDragMinHeight:142,
            verticalDragMaxHeight:142
        });
        var api = pane.data('jsp');
    });

    $('#button1').live('click',function(){
        $('#detailModalCarousel').anythingSlider(1);
    });
    $('#button2').live('click',function(){
        $('#detailModalCarousel').anythingSlider(2);
    });
    $('#button3').live('click',function(){
        $('#detailModalCarousel').anythingSlider(3);
    });
    $('#button4').live('click',function(){
        $('#detailModalCarousel').anythingSlider(4);
    });

    function setFieldErrorForRegistration(errorSets, fieldName, errorFieldName, errorMessage) {
        var v = $(fieldName).parent().parent().find('label');
        if (v) {
            v.addClass('errorLabel');
            $(fieldName).parent().addClass('errorField');
            $(errorFieldName).show();
            $(errorFieldName).find('span').html(errorMessage);
        }
        // only record the first error
        if (errorSets[0] === '') {
            errorSets[0] = errorMessage;
        }
    }

    $('#submitButton').live('click',function(){
        $('.errorModalBottom p').text('');
        clearRegistrationFieldsFromError();
        var flag = 1;
        var errorSets = [''];
        errorSets[0] = '';
        // validation
        var firstName = $.trim($('#firstName').val());
        if (firstName == '') {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#firstName', '.firstNameErr', 'Please fill in your first name');
        }
        var lastName = $.trim($('#lastName').val());
        if (lastName == '') {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#lastName', '.lastNameErr', 'Please fill in your last name');
        }
        var handle = $.trim($('#handel').val());
        if (handle == '') {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#handle', '.handleErr', 'Please fill in your desired handle');
        }
        var email = $.trim($('#email').val());
        if (email == '' || !$('#email').val().match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#email', '.emailErr', 'Please fill in your email');
        }
        var password = $.trim($('#passwordWord').val());
        if (password == '') {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#passwordWord', '.regPasswordErr', 'Please fill in your password');
        }
        var confirmPassword = $.trim($('#confirmPassword').val());
        if (confirmPassword == '' || password != confirmPassword) {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#confirmPassword', '.passwordNotMactchErr', 'Password does not match');
        }
        var verCode = $.trim($('#verificationCode').val());
        if (verCode == '') {
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#verificationCode', '.veriCodeErr', 'Please enter the verification code');
        }
        if(!$('#checkboxPrivacy').attr('checked')){
            flag = 0;
            setFieldErrorForRegistration(errorSets, '#checkboxErr', '.checkboxErr', 'Please check on Privacy Policy');
        }
        if(flag){
            $('#registerModal .errorModalBottom').hide();
            // do registration here
            var fieldErrorMap = {'firstName':['firstName','firstNameErr'],
            'lastName':['lastName','lastNameErr'],
            'handle':['handle','handleErr'],
            'email':['email','emailErr'],
            'regPassword':['passwordWord','regPasswordErr'],
            'veriCode':['verificationCode','veriCodeErr']};
            $.ajax({
                type: 'POST',
                url:  ctx + "/register.action",
                data: "formData.firstName="+firstName+"&formData.lastName="+lastName+"&formData.handle="+handle+"&formData.email="+email+"&formData.password="+password+"&formData.verificationCode="+verCode,
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
                                clearRegistrationFieldsFromError();
                            }
                            for (var fname in jsonResult.jsonData.fieldErrors) {
                                hasError = true;
                                var errors = jsonResult.jsonData.fieldErrors[fname];
                                var errorMessages = '';
                                for (var ind = 0; ind < errors.length; ind++) {
                                    if (ind > 0) errorMessages += "<br/>";
                                    errorMessages += errors[ind];
                                }
                                alert(('#'+fieldErrorMap[fname][0])+':'+('.'+fieldErrorMap[fname][1])+':'+errorMessages);
                                setFieldErrorForRegistration(errorSets, '#'+fieldErrorMap[fname][0],'.'+fieldErrorMap[fname][1],errorMessages);
                            }
                            if (hasError) {
                                $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime());
                                return;
                            }
                        }
                        //$('#registerModal .indicatorMsg').html('Account created.<br/>Thank you for your registration!');
                        $.ajax({
                            type: 'POST',
                            url:  ctx + "/login.action",
                            data: {"username":handle, "password":password, "rememberMe":false},
                            cache: false,
                            dataType: 'json',
                            async : false,
                            success: function(jsonResult) {
                                handleJsonResult(jsonResult,
                                    function(result) {
                                        if (result.success) {
                                            setTimeout(function(){
                                                // set the username here
                                                $('.loggedUsername').html(handle);
                                                $('.loginSection').hide();
                                                $('.nav').hide();
                                                $('.loginNav').show();
                                                }, 500);
                                            closeModal();
                                            $('.loginSection,.nav').hide();
                                            $('.loginNav').show();
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
        } else {
            if (errorSets[0] !== '') {
                $('.errorModalBottom p').text(errorSets[0]);
            } else {
                $('.errorModalBottom p').text('Please correct the errors above.');
            }
            $('#registerModal .errorModalBottom').show();
            $('#registerModal .modalBottom').hide();
        }
    });

    $('.jspDrag').hover(function(){
        $(this).addClass('jspDragHover');
        },function(){
        $(this).removeClass('jspDragHover');
    });

    function clearRegistrationFieldsFromError() {
        setRegisterModalBox(false);
        $('.firstNameErr').hide();
        $('.lastNameErr').hide();
        $('.handleErr').hide();
        $('.emailErr').hide();
        $('.regPasswordErr').hide();
        $('.passwordNotMactchErr').hide();
        $('.veriCodeErr').hide();
        //$('.checkboxErr').hide();
    }

    var html ='<img alt="" src="/images/analytics/password-strength-empty.png">' +
    '<img alt="" src="/images/analytics/password-strength-empty.png">' +
    '<img alt="" src="/images/analytics/password-strength-empty.png">' +
    '<img alt="" src="/images/analytics/password-strength-empty.png">' +
    '<div class="clear"></div>' ;

    $('.resetButton').live('click',function(){
        clearRegistrationFieldsFromError();
        $('#registerModal .text').val('');
        $('#registerModal .handelText').val('');
        $('#checkboxPrivacy').attr('checked',false);
        $("#passwordWord").parent().parent().siblings(".passwordStrength").find("img").remove();
        $("#passwordWord").parent().parent().siblings(".passwordStrength").find("span").after(html);
    });

    $(".tryAnotherCode").click(function() {
        $("#veriImg").attr("src", ctx + "/captchaImage.action?t=" + new Date().getTime());
    });

    //text shadow
    $('.btn .right').css('text-shadow','0 -1px 1px #ba0000');
    $('.resetButton .right').css('text-shadow','0 -1px 1px #6f6f6f');
    $('#showcaseCarousel li .title h3').css('text-shadow','1px 1px 1px #ebebeb');
    $('#modal .modalTitle h2').css('text-shadow','0 1px 1px #ffd96d');
    $('.btnGetStartedNow').css('text-shadow','1px 1px 1px #b00101');

    if($.browser.msie && ($.browser.version == "7.0")){
        $('#registerModal .codeInput').css('padding-top','0');
        $('#mainContainer .bottomSection .testimonialsArticle .figure .detail em.leftQ').css('left','-28px');
        $('#mainContainer .bottomSection .testimonialsArticle .figure .detail em.rightQ').css('margin-left','-14px');
    }

    //password strength
    $("#passwordWord").passStrength({
        userid: "#handel"
    });
});
