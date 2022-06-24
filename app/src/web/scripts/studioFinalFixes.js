/**
 * Studio Final Fixes Javascript
 *
 * @author isv
 * @version 1.0
 */
var contestId;
var finalFixesCount;
var NUMBERS = ['', 'First', 'Second', 'Third', 'Fourth', 'Fifth', 'Sixth', 'Seventh', 'Eighth', 'Ninth', 'Tenth', 
    'Eleventh', 'Thirteenth', 'Fourteenth', 'Fifteenth', 'Sixteenth', 'Seventeenth', 'Eighteenth', 'Nineteenth', 'Next'];
var currentTab;
var backup;


$(document).ready(function () {

    contestId = $("#contestId").val();
    finalFixesCount = parseInt($("#finalFixesCount").val());
    currentTab = finalFixesCount - 1;
    
    var numIndex = Math.min(NUMBERS.length - 1, finalFixesCount);
    if (finalFixesCount > 1) {
        $('h3.reviewFinalFixesTitle').html('Review ' + NUMBERS[numIndex] + ' Round of Final Fixes');
    }

    (function (wrapper) {
        //final fix link tool tips.
        if ($.fn.tooltip) {
            wrapper.find(".finalFixConfirm p a").tooltip({
                tip: "#finalFixesTip",
                delay: 10,
                position: "center right",
                offset: [10, 12]
            })
        }
        wrapper.find(".finalFixConfirm a.noBtn").click(function () {
            var parent = $(this).parents(".finalFixConfirm");
            parent.find(".p1,.p2").toggle();
            return false;
        });

        // add more tasks
        wrapper.find(".addTasks .addLink a").click(function () {
            // remember the number of the task
            var num = $(this).data("number");
            if (!num) {
                $(this).data("number", 1);
                num = 1;
            }
            $(this).data("number", num + 1);
            var clone = $(".addTasks .cloneit");
            var task = clone.clone();
            task.find("input").attr("name", "requestType_" + (num + 1));
            task.removeClass("cloneit");
            task.removeClass("noValidation");
            clone.before(task);
            return false;
        });

        // view winner submission
        wrapper.find("a.thumbGrid").hover(function () {
            var $next = $(this).next(".submissionAction");
            $next.css("display", "block");
            $next.hover(function () {
                $next.css("display", "block");
            }, function () {
                $next.css("display", "none");
            });
        }, function () {
            var $next = $(this).next(".submissionAction");
            $next.css("display", "none");
        });

        wrapper.find("a.actButtonzoom").hover(function () {
            var $this = $(this);
            var $next = $(this).next(".dialog-mini-wrapper");
            $next.css("display", "block");
            $(this).children("span").addClass("hover");
            $next.hover(function () {
                $next.css("display", "block");
                $this.children("span").addClass("hover");
            }, function () {
                $next.css("display", "none");
                $this.children("span").removeClass("hover");
            });
        }, function () {
            var $next = $(this).next(".dialog-mini-wrapper");
            $next.css("display", "none");
            $(this).children("span").removeClass("hover");
        });

        wrapper.find(".ffAction li div").css("height", wrapper.find(".ffAction ul").height() - 18 + "px");
        $(window).resize(function () {
            wrapper.find(".ffAction li div").css("height", "auto");
            setTimeout(function () {
                wrapper.find(".ffAction li div").css("height", wrapper.find(".ffAction ul").height() - 18 + "px");
            }, 10);
        });

        if ($.fn.jqTransform && wrapper.find(".ffItems").length > 0) {
            wrapper.find(".ffItems").jqTransform();
        }

        // TabsSlider functions
        var tabsBox = wrapper.find(".tabsPanel");
        var firstSlider =
            TabsSlider = {
                movingslider: tabsBox.find("li:eq(0)"),
                current: 0,
                init: function () {
                    tabsBox.find(".navi").remove();
                    if (this.needslider()) {
                        $('<p class="navi"><a class="prevLink off" href="javascript:;"></a><a class="nextLink" href="javascript:;"></a></p>').appendTo(tabsBox);
                    }
                },
                needslider: function () {
                    var width = 0;
                    tabsBox.find("li").each(function () {
                        width += $(this).outerWidth();
                    })
                    var width1 = tabsBox.find("ul").width();
                    if (width1 < width) {
                        tabsBox.find("li").show();
                        tabsBox.find("li:gt(" + (Math.floor(width1 / this.movingslider.outerWidth()) - 1) + ")").hide();
                        return true;
                    }
                    return false;
                },
                go: function (direction) {
                    var count = tabsBox.find("li:visible").length;
                    tabsBox.find(".off").removeClass("off")
                    if (direction == 1) {

                        if (this.current + count >= tabsBox.find("li").length) {
                            tabsBox.find(".nextLink").addClass("off");
                            return;
                        }
                        tabsBox.find("li:eq(" + this.current + ")").hide();
                        tabsBox.find("li:eq(" + (this.current + count) + ")").show();
                        this.current++

                        if (this.current + count == tabsBox.find("li").length) {
                            tabsBox.find(".nextLink").addClass("off");
                        }
                    } else {
                        if (this.current <= 0) {
                            tabsBox.find(".prevLink").addClass("off");
                            return;
                        }
                        this.current--
                        tabsBox.find("li:eq(" + this.current + ")").show();
                        tabsBox.find("li:eq(" + (this.current + count) + ")").hide();
                        if (this.current <= 0) {
                            tabsBox.find(".prevLink").addClass("off");
                        }
                    }
                }
            };
        
        TabsSlider.init();
        tabsBox.find(".prevLink").live("click", function () {
            TabsSlider.go(-1);
            return false;
        });
        tabsBox.find(".nextLink").live("click", function () {
            TabsSlider.go(1);
            return false;
        });

        $(window).resize(function () {
            TabsSlider.init();
        })


    })($(".studioFinalFixes"));


    $('#previewFFRequestBtn').click(showConfirmFinalFixesScreen);
    
    function showConfirmFinalFixesScreen() {
        // validate ff requirements
        var valid = true;
//        $('.FFitem').each(function () {
//            if (!$(this).parent().hasClass('noValidation')) {
//                var val = $(this).val();
//                $(this).parent().find('.error').remove();
//                if ($.trim(val) == '') {
//                    valid = false;
//                    $(this).parent().append('<span class="error">The item is required</span>');
//                }
//            }
//        });
        if (valid) {
            $('#ffConfirmTBody').empty();

            var idx = 0;
            $('.FFitem').each(function (index) {
                if (!$(this).parent().hasClass('noValidation')) {
                    var val = $(this).val();
                    if ($.trim(val) != '') {
                        var escapedVal  = $('<div/>').text(val).html();
                        var html = '';
                        if (idx % 2 == 0) {
                            html += '<tr class="odd">';
                        } else {
                            html += '<tr>';
                        }
                        html += '<td class="first">';
                        html += (idx + 1);
                        html += '.';
                        html += '</td>';
                        html += '<td class="left">';
                        html += escapedVal;
                        html += '</td>';
                        html += '</tr>';

                        $('#ffConfirmTBody').append(html);
                        idx++;
                    }
                }
            });

            $('.finalFix-notStarted').hide();
            $('.finalFix-notStarted-confirm').show();
        }

    }
    
    $('#editFFBtn').click(function() {
        // Copy original values
        backup = {};
        backup['items'] = [];
        $('textarea.FFitem').each(function () {
            if (!$(this).parent().hasClass('cloneit')) {
                backup['items'].push($(this).val());
            }
        });

        $('.finalFix-notStarted-confirm').hide();
        $('.addTasks').addClass('addTasksEdit');
        
        $('.EDIT_FF_HIDE').hide();
        $('.EDIT_FF_SHOW').show();
        
        $('.finalFix-notStarted').show();
    });
    
    $('#cancelEditFFBtn').click(function() {
        // Restore original values
        $('textarea.FFitem').each(function (index) {
            if (!$(this).parent().hasClass('cloneit')) {
                if (index < backup['items'].length) {
                    $(this).val(backup['items'][index]);
                } else {
                    $(this).parent().remove();
                }
            }
        });

        $('.finalFix-notStarted-confirm').show();
        $('.addTasks').removeClass('addTasksEdit');

        $('.EDIT_FF_HIDE').show();
        $('.EDIT_FF_SHOW').hide();

        $('.finalFix-notStarted').hide();
    });
    
    $('#saveEditFFBtn').click(function() {
        showConfirmFinalFixesScreen();
    });
    
    $('#editRejectedFFBtn').click(function() {
        // Copy original values
        backup = {};
        backup['fixed'] = [];
        $('.ffItemFixedStateBox').each(function () {
            backup['fixed'].push($(this).is(':checked'));
        });
        backup['comments'] = $('#additionalFFComment').val();

        // Hide tab for upcoming FF round
        $('li.ADDED').hide();

        // Re-open tab for current FF round
        $('a.ffTabBtn:eq(' + (finalFixesCount - 1) + ')').trigger('click');

        $('#editableFFArea').addClass('ffEditMaks');
        
        // Decorate the tab for current FF round
        $('.EDIT_REVIEW_FF_HIDE').hide();
        $('.EDIT_REVIEW_FF_SHOW').show();

    });
    
    $('#cancelEditRejectedFFBtn').click(function() {
        // Restore original values
        $('.ffItemFixedStateBox').each(function (index) {
            $(this).attr('checked', backup['fixed'][index]);
            if (backup['fixed'][index]) {
                $(this).parent().find('a.jqTransformCheckbox').addClass('jqTransformChecked')
            } else {
                $(this).parent().find('a.jqTransformCheckbox').removeClass('jqTransformChecked')
            }
        });
        $('#additionalFFComment').val(backup['comments']);

        $('#editableFFArea').removeClass('ffEditMaks');

        // Show the tab for upcoming FF round
        $('li.ADDED').show();
        $('li.ADDED a.ADDED').trigger('click');

        $('.REJECT_FF_CONFIRMATION_HIDE').hide();
        $('.REJECT_FF_CONFIRMATION_SHOW').show();
        $('.EDIT_REVIEW_FF_HIDE').show();
        $('.EDIT_REVIEW_FF_SHOW').hide();
    });
    
    $('#saveEditRejectedFFBtn').click(function() {
        $('#editableFFArea').removeClass('ffEditMaks');

        // Fill the content of tab for upcoming Final Fixes round
        var html = '';

        $('.ffItemFixedStateBox').each(function () {
            if (!$(this).is(':checked')) {
                var t1 = $(this).parent().parent().parent().find('.first').html();
                var t2 = $(this).parent().parent().parent().find('.left').html();
                html += '<tr>';
                html += '<td class="first">';
                html += t1;
                html += '</td>';
                html += '<td class="left">';
                html += t2;
                html += '</td>';
                html += '</tr>';
            }
        });

        $('#unfixedItemsBody').empty().append(html);
        $('#unfixedItemsBody tr:even').addClass('odd');

        
        $('#ffRejectionReason').text($('#additionalFFComment').val());

        // Show the tab for upcoming FF round
        $('li.ADDED').show();
        $('li.ADDED a.ADDED').trigger('click');

        $('.REJECT_FF_CONFIRMATION_HIDE').hide();
        $('.REJECT_FF_CONFIRMATION_SHOW').show();
        $('.EDIT_REVIEW_FF_HIDE').show();
        $('.EDIT_REVIEW_FF_SHOW').hide();
    });
    
    $('#saveFFItemsBtn').click(function() {
        var request = {};
        request['contestId'] = contestId;
        request['committed'] = true;
        request['finalFixItems'] = [];
        $('.FFitem').each(function () {
            if (!$(this).parent().hasClass('noValidation')) {
                var val = $(this).val();
                if ($.trim(val) != '') {
                    request['finalFixItems'].push(val);
                }
            }
        });

        $.ajax({
            type: 'POST',
            url: ctx + "/contest/saveFinalFix",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                modalPreloader();
            },
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        modalClose();
                        var studioSiteURL = $('#studioSiteBaseURL').val();
                        $('#saveFFItemsBtn').remove();
                        $('#editFFBtn').remove();
                        $('.confirmFFdiv h3.red').html('Final Fixes in Progress');
                        $('.confirmFFdiv p').addClass('red');
                        $('.confirmFFdiv p').html('Your final fix request has been sent to the winner and is now '
                            + 'visible on the <a href="http://' + studioSiteURL + '/?module=ViewContestDetails&amp;ct=' 
                            + contestId + '">Studio site</a>. You will receive an email notification ' +
                            'once the fixed files have been uploaded by the winner.');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });
    
    $('.saveFFBtn').click(function() {
        saveFinalReview('save', saveFinalReviewCallback);
    });

    $('.rejectFFBtn').click(function () {
        // validate that at least 1 item is not fixed
        var valid = false;
        $('.ffItemFixedStateBox').each(function () {
            if (!$(this).is(':checked')) {
                valid = true;
            }
        });
        if (!valid) {
            showValidationError('Can not reject final fixes if all items are fixed');
        } else {
            if ($('li.ADDED').length > 0) {
                $('li.ADDED').remove();
                $('.REJECT_FF_CONFIRMATION').hide();
                $('#unfixedItemsBody').empty();
                $('#ffRejectionReason').empty();
            }
            modalLoad('#rejectConfirmModal');
        }
    });

    $('.acceptFFBtn').click(function () {
        // validate that all items are fixed
        var valid = true;
        $('.ffItemFixedStateBox').each(function () {
            if (!$(this).is(':checked')) {
                valid = false;
            }
        });
        if (!valid) {
            showValidationError('All items must be fixed');
        } else {
            modalLoad('#acceptConfirmModal');
        }
    });

    // Handle the case when user clicked Yes on modal window asking to confirm Final Fixes rejection
    $('.rejectReviewedFFBtn').click(function () {
        modalClose();

        // Add new Final Fixes tab
        $('.tabsPanel ul').append('<li class="ADDED"><a class="ffTabBtn ADDED" rel="' + finalFixesCount + '" href="javascript:;"><span>Final Fixes (R' + (finalFixesCount + 1) + ')</span></a></li>');
        
        // Fill the content of tab for upcoming Final Fixes round
        var html = '';
        
        $('.ffItemFixedStateBox').each(function () {
            if (!$(this).is(':checked')) {
                var t1 = $(this).parent().parent().parent().find('.first').html();
                var t2 = $(this).parent().parent().parent().find('.left').html();
                html += '<tr>';
                html += '<td class="first">';
                html += t1;
                html += '</td>';
                html += '<td class="left">';
                html += t2;
                html += '</td>';
                html += '</tr>';
            }
        });

        $('#unfixedItemsBody').empty().append(html);
        $('#unfixedItemsBody tr:even').addClass('odd');
        
        $('#ffRejectionReason').text($('#additionalFFComment').val());
        
        // Open the tab for upcoming Final Fixes round
        $('.REJECT_FF_CONFIRMATION').addClass('ffItems' + finalFixesCount);
        $('a.ADDED').trigger('click');
        
        var numIndex = Math.min(NUMBERS.length - 1, finalFixesCount + 1);
        $('h3.REJECT_FF_CONFIRMATION_SHOW').html('Confirm ' + NUMBERS[numIndex] + ' Round of Final Fixes');
        
        $('.REJECT_FF_CONFIRMATION_HIDE').hide();
        $('.REJECT_FF_CONFIRMATION_SHOW').show();
    });

    $('.acceptReviewedFFBtn').click(function() {
        saveFinalReview('accept', acceptFinalReviewCallback);
    });
    
    $('#startNextFFRoundBtn').click(function() {
        saveFinalReview('reject', rejectFinalReviewCallback);
    });
    
    // Clicking the Final Fixes tabs
    $('.ffTabBtn').live('click', function() {
        $('.tabsPanel ul li').removeClass('on');
        $(this).parent().addClass('on');
        var tabIndex = $(this).attr('rel');
        $('.ffItems' + currentTab).hide();
        $('.ffItems' + tabIndex).show();
        currentTab = tabIndex;
    });
    
    function saveFinalReviewCallback(result) {
        modalLoad('#saveConfirmModal');
    }

    function rejectFinalReviewCallback(result) {
        window.location.reload();
    }

    function acceptFinalReviewCallback(result) {
        window.location.reload();
    }

    function saveFinalReview(action, callback) {
        var request = {};
        request['contestId'] = contestId;
        request['action'] = action;
        request['additionalComment'] = $('#additionalFFComment').val();
        request['itemFixedStatuses'] = [];
        $('.ffItemFixedStateBox').each(function () {
            if ($(this).is(':checked')) {
                request['itemFixedStatuses'].push(true);
            } else {
                request['itemFixedStatuses'].push(false);
            }
        });

        $.ajax({
            type: 'POST',
            url: ctx + "/contest/reviewFinalFix",
            data: setupTokenRequest(request, getStruts2TokenName()),
            cache: false,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                modalPreloader();
            },
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        callback(result);
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    }


    function showValidationError(message) {
        displayServerError("#demoModal", "Validation Error", message);
    }

});

/* function to style the input file */
(function ($) {
    $.fn.styleingInputFile = function () {
        this.each(function () {
            var fileInput = $(this);
            var parentWrap = fileInput.parents(".attachFile");
            var wrapOffset = parentWrap.offset();
            fileInput.attr("style", "opacity: 0;-moz-opacity: 0;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);")
            parentWrap.mousemove(function (event) {
                fileInput.blur();
                var relatedX = event.pageX - wrapOffset.left - fileInput.width() + 12;
                var relatedY = event.pageY - wrapOffset.top;
                fileInput.css("left", relatedX + "px");
                fileInput.css("top", "0px");
            });
            fileInput.change(function () {
                $(this).blur();
                parentWrap.find(".fakeText input").val($(this).val());
            })
        })
    }
})(jQuery);

