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
            $("#enterpriseHealthTable").dataTable({
                    "bInfo": false,
                    "bPaginate": false,
                    "bFilter": false,
                    "bSort": true,
                    "sDom": 'rt<"bottom1"il><"bottom2"fp',
                    "aaSorting": [[0,'asc']],
                    "aoColumns": [
                                    { "sType": "html" },
                                    { "sType": "html" },
                                    { "sType": "html" },
                                    { "sType": "html" },
                                    { "sType": "html" }
                            ]

            });
        }


})
