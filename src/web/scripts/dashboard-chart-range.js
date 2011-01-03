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
});
