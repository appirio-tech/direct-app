$(document).ready(function(e) {
    verPressTablet();//vertical align for tablet content in ie7
});

function verPressTablet(){
	var arrH=new Array;
	//dynamic function, get any .press_col_head element
	$('.table_cell span').each(function(index) {
		arrH[index]=$(this).height();
	});
	var H=66;
	$('.table_cell span').each(function(index) {
		$(this).css("position","relative").css("top",getPadd(H,$(this).height()));
	});
}