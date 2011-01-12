$(document).ready(function() {
    /* Multi Select */
    $('.multiselect').multiSelect();


    /* Toggle Filter */
    $("a.fiterButton").click(function() {
        if ($('.filterArea').css('display') == 'none') {
            showFilter();
        }
        else {
            hideFilter();
        }
    });


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
        $('.filterArea,.tableView').hide();
        $('.top,.chartWrapper,a.fiterButton,.filterLinkArea').show();
    });

    $('a.btnTable').click(function() {
//        $('.btnArea a').removeClass('active');
//        $(this).addClass('active');
//        //	$('.visualization').addClass('noBorder');		
//        $('.tableView').show();
//        $('.chartCollapse a.expand').html('Table View');
//        $('.top,.chartWrapper,a.fiterButton,.tableResultFilter,.filterArea,.filterLinkArea').hide();
        alert('This is to be implemented in next assemblies');
    });

    $('a.fiterButton').css('background-position', 'bottom left');
    $('.filterArea,.container2,.tableResultFilter').hide();

    $('.dashboardTableBody table').css('width', $('.dashboardTableHeader table').width());

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
