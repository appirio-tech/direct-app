/**
 * The javascript for the final panel in the my created challenges and my challenges page.
 *
 * Version 1.0 (TopCoder Direct - Challenges Section Filters Panel)
 *
 * @author GreatKevin
 */
$(document).ready(function () {

    var filters = [
        "#customerFilter",
        "#projectFilter",
        "#challengeStatusFilter",
        "#challengeTypeFilter"
    ];

    $('.challengesFilter .folder').live('click', function () {
        if ($(this).hasClass('unfolder')) {
            $(this).removeClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', '#bdbdbd solid 1px');
            $('.challengesFilter .filterContainer').show();
        } else {
            $(this).addClass('unfolder');
            $(this).parents('.filterTitle').css('border-bottom', 'none');
            $('.challengesFilter .filterContainer').hide();
        }
    });


    $.each(filters, function (index, value) {
        sortDropDown(value);

        $(value).change(function () {

            if (value == "#customerFilter") {
                loadOptionsByClientId($(this).val() == 'All' ? 0 : $(this).val());
            } else {
                var table = $.fn.dataTable.fnTables(true);

                if (table.length > 0) {
                    $(table).dataTable().fnDraw();
                }
            }


        });
    });

    function loadOptionsByClientId(clientId) {

        showIndicator($("#projectFilter"));

        $.ajax({
            type: 'POST',
            url: "dashboardGetOptionsForClientAJAX",
            data: {'formData.customerIds': clientId},
            cache: false,
            dataType: 'json',
            async: 'false',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        var $project = $("#projectFilter");

                        $project.html("");

                        if (result.projects) {

                            var projects = result.projects;

                            $.each(projects, function (key, value) {
                                $project.append($('<option></option>').val(key).html(value));
                            });
                        }

                        // append the default "select all"
                        $project.append($('<option></option>').val("All").html("All Projects"));
                        $project.val("All");

                        sortDropDown("#projectFilter");

                        hideIndicator($("#projectFilter"));

                        var table = $.fn.dataTable.fnTables(true);

                        if (table.length > 0) {
                            $(table).dataTable().fnDraw();
                        }

                    },
                    function (errorMessage) {
                        showErrors(errorMessage);
                        hideIndicator($("#projectFilter"));
                    });
            }
        });
    }


});
