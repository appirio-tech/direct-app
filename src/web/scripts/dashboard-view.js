/**
 * The JS script for dashboard views.
 *
 * Version 1.1 (TC Cockpit Performance Improvement Enterprise Dashboard 1) updates
 * - Added the logic for retrieving data for Enterprise Health area of Enterprise Dashboard view via AJAX call and
 * - rendering the data on page.
 *
 * Version 1.1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info)
 * - Fix a small bug causing js error in the project overview page.
 *
 * @author isv, GreatKevin
 * @version 1.1.1
 */
$(document).ready(function(){
    // Color the rows for projects in Enterprise Health area
    $('#enterpriseHealthTable tr:even').addClass('even');

    /* init date-pack */
    if($('.date-pick').length > 0){
        $(".date-pick").datePicker({startDate:'01/01/2001'});
    }
    
    /* dashboard view Table expand/collapse function */ 
    $(".dashboardTable .expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(".dashboardTable dd").show();
            $(this).removeClass("collapse");
        }else{
             $(".dashboardTable dd").hide();
             $(this).addClass("collapse");
        }
    });

    $(".chartCollapse a.expand").click(function(){
        $(this).blur();
        if($(this).hasClass("collapse")){
            $(".chartContent").show();
            $(this).removeClass("collapse");
        }else{
             $(".chartContent").hide();
             $(this).addClass("collapse");
        }
    });

    /* show details */
    $(".dashboardTable a.triggerDetail").click(function(){
         $(this).blur();
        var detail = $(this).parents("td").find(".detail");
        if(detail.is(":hidden")){
            $(this).text("Hide Details");
        }else{
            $(this).text("View Details"); 
        }
        detail.toggle();
    });
    
    $(".dashboardTable .viewall input").change(function(){
        $(".dashboardTable td .detail").hide();
        $(".dashboardTable a.triggerDetail").text("View Details");
        if($(this).attr("checked")){
            $(".dashboardTable a.triggerDetail").trigger("click");                
        }
    })
    
    var $select = $("#EnterpriseDashboardForm_formData_projectId");
    if ($select.length > 0) {
        var selectedVal = $select.val();
        var $options = $('option', $select);
        var arrVals = [];
        $options.each(function(){
            arrVals.push({
                val: $(this).val(),
                text: $(this).text()
            });
        });
        arrVals.sort(function(a, b){
            if(a.text > b.text){
                return 1;
            }
            else if (a.text == b.text){
                return 0;
            }
            else {
                return -1;
            }
        });
        for (var i = 0, l = arrVals.length; i < l; i++) {
            $($options[i]).val(arrVals[i].val).text(arrVals[i].text);
        }
        $select.val(selectedVal);
    }
    
    if ($("#enterpriseHealthTable").length != 0) {
        // Load project contests health data via AJAX call
        $.ajax({
                   type:'POST',
                   url:'dashboardEnterpriseHealthAJAX',
                   cache:false,
                   dataType:'json',
                   timeout: 100000,
                   error:function (e) {
                       $('#loaderEnterpriseHealth').remove();
                       $('#enterpriseHealthTableBody').empty();
                       $('#enterpriseHealthTableBody').html('<tr class="odd"><td valign="top" colspan="10">Failed to retrieve data</td></tr>');
                   },
                   success:function (jsonResult) {
                       handleJsonResult(jsonResult,
                                        function (result) {
                                            $('#loaderEnterpriseHealthWrapper').remove();
                                            $('#loaderEnterpriseHealth').remove();
                                            $('#enterpriseHealthTableBody').empty();
                                            var n = result.length;
                                            for (var i = 0; i < n; i++) {
                                                var p = result[i];
                                                var html =
                                                        '<tr>' +
                                                        '<td class="first">' +
                                                        '<a class="longWordsBreak ' + p.projectStatusColor.toLowerCase() + '"' +
                                                        'href="projectOverview?formData.projectId=' + p.project.id +
                                                        '">' +
                                                        p.project.name + '</a>' +
                                                        '</td>' +
                                                        '<td><span class="">' +
                                                        p.averageContestDuration.toFixed(0) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        '$' + p.averageCostPerContest.toFixed(2) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        '$' + p.totalProjectCost.toFixed(2) + '</span></td>' +
                                                        '<td><span class="">' +
                                                        p.averageFulfillment.toFixed(2) + '%</span></td>' +
                                                        '</tr>';


                                                $('#enterpriseHealthTableBody').append(html);
                                            }
                                        },
                                        function (errorMessage) {
                                            showServerError(errorMessage);
                                            $("#contestLoading").hide();
                                        });
                   }
               });
    }

    if ($("#projectHealthTable").length != 0) {
        $('#loaderProjectHealthWrapper td').width($('.projectHealthHeader').width());
        
        // Load project contests health data via AJAX call
        var c;
        var request = {};
        request['projectId'] = tcDirectProjectId;
        $.ajax( {
            type : 'POST',
            url : 'projectContestsHealthAJAX',
            cache : false,
            data : request,
            dataType : 'html',
            error : function(e) {
                $('#loaderProjectHealth').remove();
                $('#projectHealthTableBody').empty();
                $('#projectHealthTableBody').html('<tr class="odd"><td valign="top" colspan="10">Failed to retrieve data</td></tr>');
            },
            success : function(result) {
                $('#loaderProjectHealthWrapper').remove();
                $('#loaderProjectHealth').remove();
                $('#projectHealthTableBody').empty();
                $('#projectHealthTableBody').append(result);
                c = result;
                $("#projectHealthTable").dataTable({
                        "bAutoWidth": false,
                        "bPaginate": false,
                        "bInfo": false,
                        "bFilter": false,
                        "bSort": true,
                        "aaSorting": [[0,'asc']],
                        "aoColumns": [
                                        { "sType": "html", "sWidth": '26%' },
                                        { "sType": "html", "sWidth": '13%' },
                                        { "sType": "html", "sWidth": '10%' },
                                        { "sType": "html", "sWidth": '14%' },
                                        { "sType": "html", "sWidth": '7%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' },
                                        { "sType": "html", "sWidth": '6%' }
                                ],
                        "sScrollY": "156px",
                        "oLanguage": {
                            "sEmptyTable": '<tr class="odd"><td valign="top" colspan="10" align="center" class="dataTables_empty">No data available in table</td></tr>'                               
                         }
               }
        );
                
                adjustTables();
                
                $(".projectHealthBody").scroll(function(){
                    $('.tooltipArea .tooltipBox').hide();
                });
    
                $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                    $(this).data('dateFull',$.trim($(this).text()));
                });
                
                $('.projectHealthBody .dataTables_scrollBody').height($('.projectHealthBody').height());
                $('.dataTables_scrollHead').remove();
                $(('.dataTables_scrollBody')).css('display', 'block');
                
                initTooltips();
            }
        });

        if ($.browser.msie) {
            $(window).resize($.debounce(200, function() {
                truncateProjectHealthTable();
                adjustTables();
            }));
        } else {
            $(window).resize(function() {
                truncateProjectHealthTable();
                adjustTables();
            });
        }

        truncateProjectHealthTable();
        
    }    

    //Truncate Project Health Table
    var truncArr,arrKey,arrResult;
    truncArr = {
        'Submissions'    :    "Subs",
        'Submission'    :    "Sub",
        'R1 Submissions'    :    "R1 Subs",
        'R2 Submissions'    :    "R2 Subs",
        'Winner Announcement'    :    "Winner",
        'Specification Submission'    :    "Spec Sub",
        'Specification Review'    :    "Spec Review",
        'Appeals Response'    :    "Appeals Res"
    };
    
    //set words for special TH and TD in 1024*768 and 1440 * 960, etc
    function truncateProjectHealthTable(){
        var width = $(window).width();
        
        if(width<1580){ // for 1024 * 768 and 1440 * 960 ,etc
            $(".projectHealthHeader thead th:eq(5)").text("Reg");
            $(".projectHealthHeader thead th:eq(8)").text("Depend");
            $(".projectHealthHeader thead th:eq(9)").text("Issue");
        }else{
            $(".projectHealthHeader thead th:eq(5)").text("Registration");
            $(".projectHealthHeader thead th:eq(8)").text("Dependencies");
            $(".projectHealthHeader thead th:eq(9)").text("Issue Tracking");
        }
        
        if(width < 1260){ // for 1024 * 768 
            $(".projectHealthHeader thead th:eq(2)").text("Current");
            $(".projectHealthHeader thead th:eq(3)").text("Next");
            $(".projectHealthBody tbody tr td:nth-child(3) div, .projectHealthBody tbody tr td .dateNotice").each(function(){
                arrKey = $.trim($(this).text());
                if(typeof truncArr[arrKey] != "undefined"){
                    $(this).text(truncArr[arrKey]);
                }
            });
            
            $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                var date = $(this).text().split(" ");
                if (date[0].indexOf('/') >= 0) {
                    if (date[0].split('/')[2].length) {
                        if (date[0].split('/')[2].length == 4) {
                            $(this).text(date[0].split('/')[0] + "/" + date[0].split('/')[1] + "/" +
                                         date[0].split('/')[2].substring(2) + " " + date[1]);
                        }
                    }
                }
            });
            
        }else{
            $(".projectHealthHeader thead th:eq(2)").text("Current Phase");
            $(".projectHealthHeader thead th:eq(3)").text("Next Phase");
            $(".projectHealthBody tbody tr td:nth-child(3) div, .projectHealthBody tbody tr td .dateNotice").each(function(){
                arrResult = $.trim($(this).text());
                for(arrKey in truncArr){
                    if(arrResult == truncArr[arrKey]){
                        $(this).text(arrKey);
                    }
                }
            });
            $(".projectHealthBody tbody tr td .dateFullFormat").each(function(){
                $(this).text($(this).data('dateFull'));
            });
        }
    }

    function adjustTables() {
if ($('.dataTables_scrollBody').length != 0) {        // Determine if scrollbar is displayed for table
        var d = $('.dataTables_scrollBody')[0];
        var o = 0;
        if (typeof(d.clientHeight) != 'undefined') {
            if (d.clientHeight < d.scrollHeight) {
                o = 17;
            }
        }
        
        if ($.browser.msie) {
            $('.projectHealthHeader').css('padding-right', o + 'px');
            if (parseInt($.browser.version) == 7) {
                $('#projectHealthTable').css('width', $('#projectHealthTable').parent().width() - o);
            } else {
                $('#projectHealthTable').css('width', '100%');
            }
        } else {
            $('#projectHealthTable').css('width', '100%');
            $('.projectHealthHeader table').css('padding-right', o + 'px');
        }
    }
}
    
    function initTooltips() {

        //set hover status for Project Health table
        $('.projectHealthBody #projectHealthTable tbody tr').hover(
                function() {
                    $('.projectHealthBody #projectHealthTable tbody tr').removeClass("hover");
                    $(this).addClass("hover");
                },
                function() {
                    $(this).removeClass("hover");
                }
        );


        //tooltops
        var tooltopsTimer = null, timeLong = 500;
        $('.tooltopsBtn').live('mouseover', function() {
            var p,targetHeight;
            var targetTT = $(this).siblings('.tooltipBox').clone();
            var targetTTColor = $(this).children('span').attr('class') + 'ToolTipBox';

            $('.tooltopsBtn').removeClass('onActive');
            $(this).addClass('onActive');

            if (tooltopsTimer) clearTimeout(tooltopsTimer);
            $('.tooltipArea').empty();
            $('.tooltipArea').append(targetTT.addClass(targetTTColor));
            $('.tooltipArea .tooltipBox').show();

            p = $(this).offset();
            targetHeight = $('.tooltipArea .tooltipBox').height();
            $(targetTT).css({'left':eval(p.left - 85), 'top':eval(p.top - targetHeight - 10)});

        });

        //tooltops clear
        $('.tooltipBox').live('mouseover', function() {
            if (tooltopsTimer) clearTimeout(tooltopsTimer);
            $('#projectHealthTable .onActive').closest('tr').addClass("hover");
        });
        $('.tooltopsBtn, .tooltipBox').live('mouseout', function() {
            tooltopsTimer = setTimeout(function() {
                $('.tooltipArea').empty();
            }, timeLong);
        });
    }
});
