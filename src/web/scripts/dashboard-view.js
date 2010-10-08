// javascript functions for dashboard view

$(document).ready(function(){
    // Color the rows for projects in Enterprise Health area
    $('#enterpriseHealthTable tr:even').addClass('even');

    /* init date-pack */
    if($('.date-pick').length > 0){
    	$(".date-pick").datePicker({startDate:'01/01/2001'});
    }
    
    $('#enterpriseDashboardSubmit').click(function() {
        // validate dates
        $('#validationErrors').html('');
        var d1 = new Date($('#startDateEnterprise').val());
        var d2 = new Date($('#endDateEnterprise').val());
        if (d1 > d2) {
            $('#validationErrors').html('Start date must not be after end date');
        } else {
            $('#EnterpriseDashboardForm').submit();
            return false;
        }
    });

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
            $(".visualization").show();
            $(this).removeClass("collapse");
        }else{
             $(".visualization").hide();
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
})