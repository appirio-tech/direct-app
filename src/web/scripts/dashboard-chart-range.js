/**
 * - Version 1.1 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) Change Notes:
 * - Add the codes for the new summary panel and it's tips
 */
$(document).ready(function() {
    /* Multi Select */
    $('.multiselect').multiSelect();


    /* Toggle Filter */
    $("a.fiterButton").click(function() {
        $(".filterContest .contestType a span").css({"width": "165px"});
        $(".filterContest .contestType div").css({"width": "188px"});
        if ($('.filterArea').css('display') == 'none') {
            showFilter();
        }
        else {
            hideFilter();
        }
    });

    /* new add */
	$(".summaryPanel .tabs a").click(function(){
		$(".summaryPanel .tabs a.current").removeClass("current");
		$(".summaryPanel .tabs a.currentPrev").removeClass("currentPrev");
		$(this).parent().prev().children().addClass("currentPrev");
		$(this).addClass("current");
		switch($(this).parent().index()){
			case 0:
				$(".summaryPanel .tabs").removeClass("market").removeClass("compare").addClass("customer");
				$(".customerSummary").removeClass("hide");
				$(".marketSummary").addClass("hide");
				$(".compare").addClass("hide");
				break;
			case 1:
				$(".summaryPanel .tabs").addClass("market").removeClass("compare").removeClass("customer");
				$(".customerSummary").addClass("hide");
				$(".marketSummary").removeClass("hide");
				$(".compare").addClass("hide");
				break;
			case 2:
				$(".summaryPanel .tabs").removeClass("market").addClass("compare").removeClass("customer");
				$(".customerSummary").addClass("hide");
				$(".marketSummary").addClass("hide");
				$(".compare").removeClass("hide");
				break;
			default:
				break;
		}
	})
	$(".fieldName").hover(
		function(){
			$(this).find(".tooltipBox").css("display", "block");
		},
		function(){
			$(this).find(".tooltipBox").css("display", "none");
		}
	);

    /* Apply button action */
    $('a.applyButton').click(function(event) {
        //	doFilter();
        $('#chart_div iframe').width($(".visualization").width() - 5);
    });

    $('.dateRange a').click(function() {
        return false;
    });

    /* button graph and table */
    $('a.btnGraph').click(function() {
        $('.btnArea a').removeClass('active');
        $(this).addClass('active');
        $('.chartCollapse a.expand').html('Chart View');

        $('a.fiterButton').css('background-position', 'bottom left');
        $('.filterArea,#firstTableDataArea').hide();
        $("#dynamicTableView").addClass("hide");
        $('.top,.chartWrapper,a.fiterButton,.filterLinkArea').show();
    });


    $('a.fiterButton').css('background-position', 'bottom left');
    $('.filterArea,.container2,.tableResultFilter').hide();

    hideFilter = function() {
        $('.filterArea').hide();
        $('a.fiterButton').css('background-position', 'bottom left');
    };

    showFilter = function() {
        $('.filterArea').show();
        $('a.fiterButton').css('background-position', 'top left');
    }

     function sortDropDown(dropDownId) {
        // alert('sort ' + dropDownId);
        // get the select
        var $dd = $(dropDownId);
        if ($dd.length > 0) { // make sure we found the select we were looking for

            // save the selected value
            var selectedVal = $dd.val();

            // get the options and loop through them
            var $options = $('option', $dd);
            var arrVals = [];
            $options.each(function() {
                // push each option value and text into an array
                arrVals.push({
                    val: $(this).val(),
                    text: $(this).text()
                });
            });

            // sort the array by the value (change val to text to sort by text instead)
            arrVals.sort(function(a, b) {
                if(a.val == 0) {
                    return -1;
                }
                if (b.val == 0) {
                    return 1;
                }

                if (a.text > b.text) {
                    return 1;
                }
                else if (a.text == b.text) {
                    return 0;
                }
                else {
                    return -1;
                }
            });

            // loop through the sorted array and set the text/values to the options
            for (var i = 0, l = arrVals.length; i < l; i++) {
                $($options[i]).val(arrVals[i].val).text(arrVals[i].text);
            }

            // set the selected value back
            $dd.val(selectedVal);
        }
    }

    sortDropDown("#formData\\.projectIds");
    sortDropDown("#formData\\.billingAccountIds");


    function loadOptionsByClientId(clientId) {

      $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForClientAJAX",
            data: {'formData.customerIds':clientId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                                function(result) {
                                    var billings = result.billings;
                                    var projects = result.projects;
                                    var $billing = $("#formData\\.billingAccountIds");
                                    var $project = $("#formData\\.projectIds");

                                    $billing.html("");
                                    $.each(billings, function(key, value){
                                        $billing.append($('<option></option>').val(key).html(value));
                                    });

                                    // append the default "select all"
                                    $billing.append($('<option></option>').val(0).html("All Billing Accounts"));
                                    $billing.val(0);

                                    $project.html("");
                                    $.each(projects, function(key, value){
                                        $project.append($('<option></option>').val(key).html(value));
                                    });

                                     // append the default "select all"
                                    $project.append($('<option></option>').val(0).html("All Projects"));
                                    $project.val(0);

                                    sortDropDown("#formData\\.projectIds");
                                    sortDropDown("#formData\\.billingAccountIds");

                                },
                                function(errorMessage) {
                                    $('#validationErrors').html(errorMessage);
                                });
            }
        });
    }


    $("#formData\\.customerIds").change(function() {

        var customerId = $(this).val();

        loadOptionsByClientId(customerId);
    });

    $("#formData\\.billingAccountIds").change(function() {

        var billingId = $(this).val();

        if(billingId == 0) {
            // select all again, load all the billings and projects for customer
            var customerId = $("#formData\\.customerIds").val();

            loadOptionsByClientId(customerId);

            return;
        }

        $.ajax({
            type: 'POST',
            url:  "dashboardGetOptionsForBillingAJAX",
            data: {'formData.billingAccountIds':billingId},
            cache: false,
            dataType: 'json',
            success: function(jsonResult) {
                handleJsonResult(jsonResult,
                                function(result) {
                                    var projects = result.projects;
                                    var $project = $("#formData\\.projectIds");

                                    $project.html("");
                                    $.each(projects, function(key, value){
                                        $project.append($('<option></option>').val(key).html(value));
                                    });

                                    // append the default "select all"
                                    $project.append($('<option></option>').val(0).html("All Projects"));
                                    $project.val(0);
                                    sortDropDown("#formData\\.projectIds");

                                },
                                function(errorMessage) {
                                    $('#validationErrors').html(errorMessage);
                                });
            }
        });

    });
});
