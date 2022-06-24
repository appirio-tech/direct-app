/** Create the gannt chart using the data*/

function createChartControl(htmlDiv, hasTreePanel) {
	  var ganttChartControl;
	  
    // Create Gantt control
    ganttChartControl = new GanttChart();
    // Setup paths and behavior
    ganttChartControl.setImagePath("images/gantt/");
    ganttChartControl.setEditable(true);
    ganttChartControl.showContextMenu(true);
    ganttChartControl.showTreePanel(hasTreePanel);

    // Build control on the page
    ganttChartControl.create(htmlDiv);
    ganttChartControl.showDescTask(true,'n,e,d');
    ganttChartControl.showDescProject(true,'n');

    // Load data structure    
    ganttChartControl.loadData("data/data.xml",true,true);
}

function recreateChartControl(htmlDiv, hasTreePanel) {
	$('#' + htmlDiv).html('');
	createChartControl(htmlDiv, hasTreePanel);
}
    
$(document).ready(function() {
 
   createChartControl('ganttChartDiv', true);
   
   $(window).resize(function() { recreateChartControl('ganttChartDiv', true); } );

});