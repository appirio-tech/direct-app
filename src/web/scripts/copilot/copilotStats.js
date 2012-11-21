/**
 * The JS script provides the events handling for the copilot posting registrants and submissions page.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - Cockpit Copilot Posting Registrants Analysis)
 */

$(document).ready(function() {

    $(".gridview-table td").each(function(){
        $(this).find("div.colGrp").height($(this).height());
    });

    $('.requiredXperience .more').mouseover(function() {
        var $parent = $(this).parents('.dashboardModule:eq(0)').position();
        var r = $(this).parents('.dashboardModule:eq(0)').width() - $(this).position().left;
        var r1 = r;
        var t = $parent.top + $(this).position().top + $('.moreFlyout').height() - 10;
        if (r < $('.moreFlyout').width()) {
            r = 0;
        } else {
            r1 = $('.moreFlyout').width() - $(this).position().left + 55;
            r -= 50;
        }
        $('.moreFlyout .arrow').css({
            'left' : 'auto',
            'right' : (r1 - 20) + 'px'
        });
        $('.moreFlyout').removeClass('hide').css({
            'right' : r + 'px',
            'top' : t + 'px',
            'left' : 'auto'
        });
    });
    $('.requiredXperience .more').mouseleave(function() {
        window.setTimeout($('.moreFlyout').addClass('hide'), 500);
    });

    $('.viewXperience').mouseover(function() {
        var l = $(this).parent().parent().position().left - 1;
        if ($.browser.webkit) {
            // webkit fix
            l += 1;
        }
        var t = $(this).position().top + 20;

        var arrowLt = 70;
        if ($(this).parent().parent().hasClass('xperience')) {
            l -= 52;
            arrowLt = 103;
        }

        $('.xperienceFlyout .arrow').css({
            'left' : arrowLt + 'px'
        });
        $('.xperienceFlyout').removeClass('hide').css({
            'top' : t + 'px',
            'left' : l + 'px'
        });
    });

    $('.viewXperience').mouseleave(function() {
        window.setTimeout("$('.xperienceFlyout').addClass('hide')", 500);
    });

    /* tack flyout function */
    $('.track a').mouseover(function() {
        var l = 0;
        var t = 0;
        if ($(this).parents('td:eq(0)').length > 0) {
            l = $(this).parents('td:eq(0)').position().left - 1;
            t = $(this).parents('td:eq(0)').position().top + $(this).parent().position().top + 58;
        } else {
            l = $(this).parents('.colCopilot:first').position().left + 153;
            t = $(this).parents('.colCopilot:first').position().top + $(this).parent().position().top + 54;
        }
        if ($(this).parents('.rCopilot').length > 0) {
            t += 8;
            l += 1;
        }
        // 134 = height from top of cell

        var arrowLt = $(this).position().left + 9;
        if ($(this).hasClass('dev')) {
            $('.trackFlyout .data').text('Certified TopCoder Software Copilot');
            $('.trackFlyout').css('width', '208px');
        } else {
            $('.trackFlyout .data').text('Certified TopCoder Studio Copilot');
            $('.trackFlyout').css('width', '194px');
        }
        $('.trackFlyout .arrow').css({
            'left' : arrowLt + 'px'
        });
        $('.trackFlyout').removeClass('hide').css({
            'top' : t + 'px',
            'left' : l + 'px'
        });
    });
    $('.track a').mouseleave(function() {
        window.setTimeout($('.trackFlyout').addClass('hide'), 500);
    });

    /* pickup copilot */
    $('.btn-pickup').click(function() {

        var idx = $(this).parents('tr:first').index();
        if ($(this).parents('.gridview-table').length > 0) {
            idx = idx * 2 + $(this).parents('td:first').index();
        }
        var r = $('.listview-table tbody tr').eq(idx);
        var r1 = $('.gridview-table tbody td').eq(idx);
        r.addClass('rowSelCP');
        r1.addClass('rowSelCP');
        $('td:first .colLt', r).append('<span class="ribbon"></span>');
        $('.col1', r1).append('<span class="ribbon"></span>');
        var node = $('.pickupCell', r);
        var node1 = $('.pickupCell', r1);
        node.addClass('cpPicked').removeClass('rupPicked');
        node1.addClass('cpPicked').removeClass('rupPicked');

        $('.btn-pickup', $(this).parents('.container2Content_det:first')).hide();

        if (!rup) {
            $('.caption .choose').text("I don't want to choose runner-up copilot");
        } else {
            $('.caption .choose').fadeOut('slow');
            $('.caption .choose').fadeOut('slow', function() {
                $('.caption .seprator').hide();
            });
        }

        if ($('.compareList .colPickup').length > 0) {
            $(this).parents('.colPickup').addClass('cpPicked').removeClass('rupPicked');
            $('.rCopilot', $(this).parents('.colCopilot')).append('<span class="ribbonCP"></span>');
        }
    });
    /* pickup runner up */
    $('.btn-pickup-rup').click(function() {
        var idx = $(this).parents('tr:first').index();
        if ($(this).parents('.gridview-table').length > 0) {
            idx = idx * 2 + $(this).parents('td:first').index();
        }
        var r = $('.listview-table tbody tr').eq(idx);
        var r1 = $('.gridview-table tbody td').eq(idx)
        r.addClass('rowSelRUP');
        r1.addClass('rowSelRUP');
        $('td:first .colLt', r).append('<span class="ribbon"></span>');
        $('.col1', r1).append('<span class="ribbon"></span>');
        var node = $('.pickupCell', r);
        var node1 = $('.pickupCell', r1);
        node.addClass('rupPicked').removeClass('cpPicked');
        node1.addClass('rupPicked').removeClass('cpPicked');

        $('.btn-pickup-rup', $(this).parents('.container2Content_det:first')).hide();

        rup = true;
        if ($('.caption .choose').text() == "I don't want to choose runner-up copilot") {
            $('.caption .choose').fadeOut('slow', function() {
                $('.caption .seprator').hide();
            });
        }

        if ($('.compareList .colPickup').length > 0) {
            $(this).parents('.colPickup').addClass('rupPicked').removeClass('cpPicked');
            $('.rCopilot', $(this).parents('.colCopilot')).append('<span class="ribbonRUP"></span>');
        }
    });

    /* close compare row and adujust column after removal functions */
    $('.compareList .close').live('click', function() {
        var nVisible = 4;
        var idx = $(this).parents('.colCopilot:first').index();
        $(this).parents('.colCopilot:first').remove();
        window.setTimeout(function() {

            var nextNode = $('.compareList .colCopilot:visible:last').next();
            if (nextNode.length > 0) {
                nextNode.removeClass('hide');
            } else {
                var prevNode = $('.compareList .colCopilot:visible:first').prev();
                if (prevNode.length > 0) {
                    prevNode.removeClass('hide');
                } else {
                    $('.compareList .colCopilot:visible:last');
                }
            }
            if ($('#compareItems .colCopilot').length <= nVisible) {
                $('.switch.btn-nexPrev a').addClass('disabled');
            }

        }, 1);
    });

    if ($('.drag-table').length > 0) {
        $('.drag-table').dragtable({
            items : ' thead .reorder'
        });
    }

    /* comparision table next / prev */
    $('.btn-nexPrev .next').click(next);
    $('.btn-nexPrev .prev').click(prev);

    /* sortable */
    $('#compareItems').sortable({
        connectWith : ".connectedSortable",
        handle : ".reorder",
        zIndex : '9999',
        start : function() {
            // $('#compareItems .colCopilot').css('display', 'inline');
        },
        stop : function() {
            // $('#compareItems .colCopilot').css('display', '');
        }
    }).disableSelection();
});

var rup = false;

function next() {
    var nVisible = 4;
    $('.disabled', $(this).parent()).removeClass('disabled');
    if ($('#compareItems .colCopilot').length <= nVisible) {
        $('a', $(this).parent()).addClass('disabled');
    }
    if ($('.compareList .colCopilot:last').is(":visible")) {
        $(this).addClass('disabled');
        return false;
    }
    var idx = $('.compareList .colCopilot:visible:first').index();
    $('.compareList .colCopilot').eq(idx).addClass('hide');
    $('.compareList .colCopilot').eq(idx + nVisible).removeClass('hide');
}
function prev() {
    var nVisible = 4;
    $('.disabled', $(this).parent()).removeClass('disabled');
    if ($('#compareItems .colCopilot').length <= nVisible) {
        $('a', $(this).parent()).addClass('disabled');
    }
    if ($('.compareList .colCopilot:first').is(":visible")) {
        $(this).addClass('disabled');
        return false;
    }
    var idx = $('.compareList .colCopilot:visible:last').index();
    $('.compareList .colCopilot').eq(idx).addClass('hide');
    $('.compareList .colCopilot').eq(idx - nVisible).removeClass('hide');
}