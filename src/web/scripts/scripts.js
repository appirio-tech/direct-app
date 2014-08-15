/**
 *  Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 *
 *  The JS script for the home page.
 *
 *  Version 1.1 - Release Assembly - TC Cockpit Sidebar Header and Footer Update
 *  - Added codes to initialize the help widget in right sidebar.
 *
 *  Version 2.0 - TC Direct Rebranding - Homepage Update
 *
 *  Version 2.1 - TopCoder Direct - Update Login and add Admin Login @author Veve @challenge 30044719
 *  - Add submitAuth0LoginForm method
 *
 * @author Blues
 * @version 2.1
 */


var submitAuth0LoginForm = function() {

    var errors = [];

    $('.loginBoxInner .inputF input').each(function () {
        if (!$(this).val() || $.trim($(this).val()).length == 0) {
            $(this).addClass('error');
            errors.push($(this).attr('placeholder'));
        } else {
            $(this).removeClass('error');
            $('.loginBoxInner .errorMessage').hide();
        }
    });

    if($("input.error").length > 0) {
        var errorsMsg = errors.length == 2 ? ( errors[0] + '/' + errors[1]) : errors[0];
        $('.loginBoxInner .errorMessage').text(errorsMsg + " should not be empty");
        $('.loginBoxInner .errorMessage').show();
        event.preventDefault();
        return false;
    } else {
        $('.loginBoxInner .errorMessage').hide();
    }

    dbLogin();
}

var submitLoginForm = function() {

    var errors = [];

    $('.loginBoxInner .inputF input').each(function () {
        if (!$(this).val() || $.trim($(this).val()).length == 0) {
            $(this).addClass('error');
            errors.push($(this).attr('placeholder'));
        } else {
            $(this).removeClass('error');
            $('.loginBoxInner .errorMessage').hide();
        }
    });

    if($("input.error").length > 0) {
        var errorsMsg = errors.length == 2 ? ( errors[0] + '/' + errors[1]) : errors[0];
        $('.loginBoxInner .errorMessage').text(errorsMsg + " should not be empty");
        $('.loginBoxInner .errorMessage').show();
        event.preventDefault();
        return false;
    } else {
        $('.loginBoxInner .errorMessage').hide();
    }

    document.LoginForm.submit();
}

$(document).ready(function () {

    var wrapperH = $('#landingPage #wrapper').height() + 25;
    $('#landingPage #wrapper').css('margin-top', -wrapperH / 2 + "px");
    if ($(".jqtransform").length > 0) {
        $(".jqtransform").jqTransform();
    }

    $('.loginBoxInner .inputF input').focus(function () {
        $(this).removeClass('error');
        $('.loginBoxInner .errorMessage').hide();
    });

    if($("p.errorMessage").text() && $.trim($("p.errorMessage").text()).length > 0) {
        $('.loginBoxInner .inputF input').addClass('error');
        $("p.errorMessage").show();
    } else {
        $('.loginBoxInner .inputF input').removeClass('error');
        $("p.errorMessage").hide();
    }

});
