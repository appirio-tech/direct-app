/** Create the Gantt chart using the data retrieved via Ajax Call*/

// global variable used to store generated XML data
var ganttChartData;
var actionErrorHeader = "GamePlanActionError:";
var ganttChartControl;

function createChartControl(htmlDiv, xml) {
    // Create Gantt control
    ganttChartControl = new GanttChart();

    // Setup paths and behavior
    ganttChartControl.setImagePath("/images/gantt/");
    ganttChartControl.setStylePath("/css/direct/ganttchart.css");
    ganttChartControl.setEditable(false);
    ganttChartControl.showContextMenu(false);
    ganttChartControl.showTreePanel(true);

    // Build control on the page
    ganttChartControl.create(htmlDiv);
    ganttChartControl.showDescTask(true, 'n,e,d');
    ganttChartControl.showDescProject(true, 'n');

    // Load data structure
    ganttChartControl.loadData(xml, false, true);
}

function recreateChartControl(htmlDiv) {
    $('#' + htmlDiv).html('');
    createChartControl(htmlDiv, ganttChartData);
}

function showErrorMessage(htmlDiv, errorDiv, message) {
    var defaultErrorMessage = "Error occurs while retrieving project game plan, please try refresh later";

    $('#' + htmlDiv).html('');

    if (message.indexOf(actionErrorHeader) == -1) {
        // show the default error message
        $('#' + errorDiv).html(defaultErrorMessage);
    } else {
        $('#' + errorDiv).html(message.substr(message.indexOf(actionErrorHeader) + actionErrorHeader.length));
    }
}

function hasError(response) {
    if (response.indexOf("<projects>") == -1) {
        return true;
    }
    return false;
}


var g;

var calculateLeftWidth = function () {
    // setup the width
    var leftPanelWidth = 675;

    if ($("#leftside tbody tr").length > 4) {
        leftPanelWidth = 10;
        $("#leftside tbody tr:eq(3) td").each(function () {
            leftPanelWidth += $(this).width();
        });
    }

    $("#leftside").css('width', leftPanelWidth + 'px');
};

var calculateRightWidth = function() {
    var rightWidth = $(window).width();

    if (!$("#leftside").is(":hidden")) {
        rightWidth -= $("#leftside").width();
    }

    $("#rightside").css('width', rightWidth + 'px');
};


$(document).ready(function () {
    var projectId = $("#projectIDHolder").val();

    //  game plan page
    if ($("#ganttChartDiv").length > 0) {
        var request = {formData:{projectId:projectId}};

        $.ajax({
            type:"POST",
            url:"projectGamePlan",
            data:request,
            dataType:"text",
            cache:false,
            success:function (xml) {

                if (hasError(xml)) {
                    // show error message if the response indicates an error
                    showErrorMessage('ganttChartDiv', 'ganttChartError', xml);
                } else {
                    ganttChartData = xml;
                    createChartControl('ganttChartDiv', ganttChartData);

                    // recreate the Gantt chart when browser window is resized
                    $(window).resize(function () {
                        recreateChartControl('ganttChartDiv');
                    });
                }

            },
            error:function (xhr, ajaxOptions, thrownError) {
                // show the error message when the ajax call failed
                showErrorMessage('ganttChartDiv', 'ganttChartError', thrownError);
            }
        })
    }

    // new jsgantt game plan page
    if ($("#jsGanttChartDiv").length > 0) {
        g = new JSGantt.GanttChart('g', document.getElementById('jsGanttChartDiv'), 'week');

        g.setShowRes(1); // Show/Hide Responsible (0/1)
        g.setShowDur(1); // Show/Hide Duration (0/1)
        g.setShowComp(1); // Show/Hide % Complete(0/1)

        g.setCaptionType('topcoder');  // Set to Show Caption (None,Caption,Resource,Duration,Complete)
        if (g) {

            // Parameters (pID,pName,pStart,pEnd,pColor,pLink,pMile,pRes,pComp,pGroup,pParent,pOpen)
            // use the XML file parser
            JSGantt.parseXML('jsGanttProjectGamePlan?formData.projectId=' + projectId, g);

            g.Draw();

            g.DrawDependencies();

        }
        else {
            showErrors("The jsgantt chart game plan is not defined");
        }

        calculateLeftWidth();
        calculateRightWidth();

        var oldWidth = 0;


        var hideExtraColumns = function() {
            var width = 0;
            $("#leftside tbody tr").each(function () {
                $(this).find("td:gt(2):lt(7)").hide();
                var x = $(this).find("td:eq(1)").width() + $(this).find("td:eq(2)").width();

                if (x > width) width = x;

            });

            if (oldWidth <= 0) {
                oldWidth = $("#leftside").width();
            }

            $("#leftside").css('width', width + 'px');
            calculateRightWidth();
        }

        $("#hideColumnsControl").live('click', function () {

            if ($(this).is(":checked")) {
                hideExtraColumns();
            } else {
                $("#leftside tbody tr td").show();
                $("#leftside").css('width', oldWidth + 'px');
                calculateRightWidth();
            }


        });

        $("#hideLeftAll").live('click', function () {
            if (!$("#leftside").is(":hidden")) {
                $("#leftside").hide();
                $("#rightside").css('width', '1024px');
                $("#hideLeftAll").text("Show Left Panel");
                calculateRightWidth();
            } else {
                $("#leftside").show();
                $("#rightside").css('width', '605px');
                $("#hideLeftAll").text("Hide Left Panel");
                calculateRightWidth();
            }
        });

        $("#showMilestonesControl").live('click', function(){
            var count=0;
            if($(this).is(":checked")){
                $("#leftside tbody tr").each(function() {
                    if($(this).find("td:eq(2) nobr").html()=="Milestone"){
                        $(this).find("td:eq(2) nobr").parent().parent().show();
                    }
                    count++;
                    if(count%2==0){
                        $(this).css("background","#FFFFFF");
                    }else{
                        $(this).css("background","#F5F3F3");
                    }
                });
                $("#rightside div div [id^='taskbar_']").each(function() {
                    if($(this).find(".MilestoneRow").html()!=null){
                        $(this).parent().parent().show();
                    }
                    count++;
                    if(count%2==0){
                        $(this).parent().parent().find("tbody tr").css("background","#F5F3F3");
                    }else{
                        $(this).parent().parent().find("tbody tr").css("background","#FFFFFF");
                    }
                });
                $(".upcomingDummy").each(function() {
                    $(this).removeClass("upcomingDummy");
                    $(this).addClass("upcomingHeader");
                });
                $(".overdueDummy").each(function() {
                    $(this).removeClass("overdueDummy");
                    $(this).addClass("overdueHeader");
                });
                $(".completedDummy").each(function() {
                    $(this).removeClass("completedDummy");
                    $(this).addClass("completedHeader");
                });
            }else{
                $("#leftside tbody tr").each(function() {
                    if($(this).find("td:eq(2) nobr").html()=="Milestone"){
                        $(this).find("td:eq(2) nobr").parent().parent().hide();
                    }else{
                        count++;
                    }
                    if(count%2==0){
                        $(this).css("background","#FFFFFF");
                    }else{
                        $(this).css("background","#F5F3F3");
                    }
                });
                count=0;
                $("#rightside div div [id^='taskbar_']").each(function() {
                    if($(this).find(".MilestoneRow").html()!=null){
                        $(this).parent().parent().hide();
                    }else{
                        count++;
                    }
                    if(count%2==0){
                        $(this).parent().parent().find("tbody tr").css("background","#FFFFFF");
                    }else{
                        $(this).parent().parent().find("tbody tr").css("background","#F5F3F3");
                    }
                });
                $(".upcomingHeader").each(function() {
                    $(this).removeClass("upcomingHeader");
                    $(this).addClass("upcomingDummy");
                });
                $(".overdueHeader").each(function() {
                    $(this).removeClass("overdueHeader");
                    $(this).addClass("overdueDummy");
                });
                $(".completedHeader").each(function() {
                    $(this).removeClass("completedHeader");
                    $(this).addClass("completedDummy");
                });
            }
        });

        extendStyle();

        if($(window).width() < 1280) {
            hideExtraColumns();
            $("#hideColumnsControl").attr('checked', 'checked');
        }

        $(window).resize(function () {
            calculateRightWidth();
        });

        $("input[name=radFormat]").live('click', function(){
           calculateRightWidth();
        });
    }
});
