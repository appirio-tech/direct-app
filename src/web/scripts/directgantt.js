/** Create the Gantt chart using the data retrieved via Ajax Call*/

// global variable used to store generated XML data
var ganttChartData;
var actionErrorHeader = "GamePlanActionError:";
var ganttChartControl;

function createChartControl(htmlDiv, xml)
{
    // Create Gantt control
    ganttChartControl = new GanttChart();

    // Setup paths and behavior
    ganttChartControl.setImagePath("/images/gantt/");
    ganttChartControl.setStylePath("/css/ganttchart.css");
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


$(document).ready(function() {

    var projectId = $("#projectIDHolder").val();
    var request = {formData:{projectId:projectId}};

    $.ajax({
        type: "GET",
        url: "projectGamePlan",
        request:request,
        dataType: "text",
        cache:false,
        success: function(xml) {

            if (hasError(xml)) {
                // show error message if the response indicates an error
                showErrorMessage('ganttChartDiv', 'ganttChartError', xml);
            } else {
                ganttChartData = xml;
                createChartControl('ganttChartDiv', ganttChartData);

                // recreate the Gantt chart when browser window is resized
                $(window).resize(function() {
                    recreateChartControl('ganttChartDiv');
                });
            }

        },
        error:function (xhr, ajaxOptions, thrownError) {
            // show the error message when the ajax call failed
            showErrorMessage('ganttChartDiv', 'ganttChartError', thrownError);
        }
    })
});
