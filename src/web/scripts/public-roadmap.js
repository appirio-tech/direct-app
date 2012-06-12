/**
 * Javascript for the public topcoder road map page.
 *
 * @since Module Assembly - TC Cockpit Public Page for TopCoder Road map and RSS syndication
 * @author TCSASSEMBLER
 */

function loadRoadmap(index) {
    if ($(".roadMapSection  #overDueData").length > 0) {
        $.ajax({
            type:"GET",
            url:"getTopCoderRoadMap",
            dataType:"json",
            success:function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (json) {

                        var ajaxTableLoader, strDataOverDue = '', strDataUpcoming = '', strDataCompleted = '', strDataTotal = '', strDataTab = '';
                        var totalNumberOverDue = 0, totalNumberUpcoming = 0, totalNumberCompleted = 0;
                        if (json.overdue.length) {
                            for (i = 0; i < json.overdue.length; i++) {
                                strDataOverDue += '<tr>';
                                strDataOverDue += '<td>';
                                strDataOverDue += '<h4>' + json.overdue[i].milestone + '</h4>';
                                strDataOverDue += '<p>' + json.overdue[i].detail + '</p>';
                                strDataOverDue += '</td>';
                                strDataOverDue += '<td class="roadmapProject">';
                                strDataOverDue += json.overdue[i].project;
                                strDataOverDue += '</td>';
                                strDataOverDue += '<td class="alignCenter">';
                                strDataOverDue += '<span class="date">' + json.overdue[i].date + '</span>';
                                strDataOverDue += '</td>';
                                strDataOverDue += '</tr>';
                            }
                        }
                        if (json.upcoming.length) {
                            for (i = 0; i < json.upcoming.length; i++) {
                                strDataUpcoming += '<tr>';
                                strDataUpcoming += '<td>';
                                strDataUpcoming += '<h4>' + json.upcoming[i].milestone + '</h4>';
                                strDataUpcoming += '<p>' + json.upcoming[i].detail + '</p>';
                                strDataUpcoming += '</td>';
                                strDataUpcoming += '<td class="roadmapProject">';
                                strDataUpcoming += json.upcoming[i].project;
                                strDataUpcoming += '</td>';
                                strDataUpcoming += '<td class="alignCenter">';
                                strDataUpcoming += '<span class="date">' + json.upcoming[i].date + '</span>';
                                strDataUpcoming += '</td>';
                                strDataUpcoming += '</tr>';
                            }
                        }
                        if (json.completed.length) {
                            for (i = 0; i < json.completed.length; i++) {
                                strDataCompleted += '<tr>';
                                strDataCompleted += '<td>';
                                strDataCompleted += '<h4>' + json.completed[i].milestone + '</h4>';
                                strDataCompleted += '<p>' + json.completed[i].detail + '</p>';
                                strDataCompleted += '</td>';
                                strDataCompleted += '<td class="roadmapProject">';
                                strDataCompleted += json.completed[i].project;
                                strDataCompleted += '</td>';
                                strDataCompleted += '<td class="alignCenter">';
                                strDataCompleted += '<span class="date">' + json.completed[i].date + '</span>';
                                strDataCompleted += '</td>';
                                strDataCompleted += '</tr>';
                            }
                        }

                        strDataTotal += '<li>Overdue:<strong class="red">' + json.overdue.length + '</strong></li>';
                        strDataTotal += '<li>Upcoming<strong class="yellow">' + json.upcoming.length + '</strong></li>';
                        strDataTotal += '<li class="last">Completed<strong class="green">' + json.completed.length + '</strong></li>';

                        totalNumberOverDue = json.overdue.length;
                        totalNumberUpcoming = json.upcoming.length;
                        totalNumberCompleted = json.completed.length;

                        $(".roadMapSection .roadMapNum ul").empty().append(strDataTotal);
                        $(".roadMapSection .tabPanel ul li:eq(0) strong").text('(' + totalNumberOverDue + ')');
                        $(".roadMapSection .tabPanel ul li:eq(1) strong").text('(' + totalNumberUpcoming + ')');
                        $(".roadMapSection .tabPanel ul li:eq(2) strong").text('(' + totalNumberCompleted + ')');
                        $(".roadMapSection .overDueTable .topPagePanel .pageTotal span.totalNumber").text(totalNumberOverDue);
                        $(".roadMapSection .upcomingData .topPagePanel .pageTotal span.totalNumber").text(totalNumberUpcoming);
                        $(".roadMapSection .completedData .topPagePanel .pageTotal span.totalNumber").text(totalNumberCompleted);
                        $('.roadMapSection .tabPanel li a').removeClass('current');
                        $('.roadMapSection .tabPanel li a').eq(index).trigger('click');
                        if (json.overdue.length) {
                            $(".roadMapSection #overDueData tbody").empty().append(strDataOverDue);
                            $('.roadMapSection #overDueData tbody tr:odd').addClass('odd');
                        } else {
                            $(".roadMapSection #overDueData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
                        }
                        if (json.upcoming.length) {
                            $(".roadMapSection #upcomingData tbody").empty().append(strDataUpcoming);
                            $('.roadMapSection #upcomingData tbody tr:odd').addClass('odd');
                        } else {
                            $(".roadMapSection #upcomingData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
                        }
                        if (json.completed.length) {
                            $(".roadMapSection #completedData tbody").empty().append(strDataCompleted);
                            $('.roadMapSection #completedData tbody tr:odd').addClass('odd');
                        } else {
                            $(".roadMapSection #completedData tbody").empty().append('<td colspan="3"><div class="noData">No data available</div></td>');
                        }
                    },
                    function (error) {
                        showError(index, 'Error when loading roadmap data');
                    })
            },
            error:function () {
                showError(index, 'No data available');
            }
        });
    }
}

function showError(index, message) {
    $(".roadMapSection .roadMapNum ul").empty().append('<li>Overdue:<strong class="red">0</strong></li><li>Upcoming<strong class="yellow">0</strong></li><li class="last">Completed<strong class="green">0</strong></li>');
    $('.roadMapSection .tabPanel li a').removeClass('current');
    $('.roadMapSection .tabPanel li a').eq(index).addClass('current');
    $(".roadMapSection #overDueData tbody").empty().append('<td colspan="3"><div class="noData">' + message + '</div></td>');
    $(".roadMapSection #upcomingData tbody").empty().append('<td colspan="3"><div class="noData">' + message +  '</div></td>');
    $(".roadMapSection #completedData tbody").empty().append('<td colspan="3"><div class="noData">' + message + '</div></td>');
}

$('.roadMapSection .tabPanel li a').live('click', function () {
    $('.roadMapSection .tabPanel li a').removeClass('current');
    $(this).addClass('current');
    $('.roadMapSection .tabSection').hide();
    $('.roadMapSection .tabSection').eq($('.roadMapSection .tabPanel li a').index(this)).show();
});

$(document).ready(function () {
    loadRoadmap(1);
});