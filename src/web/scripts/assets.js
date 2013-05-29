/**
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for project assets pages.
 *
 *  Version 1.0 - Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 *  - Added this file and setup the codes for basic upload and assets view display - Group by date and category of current version.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
$(document).ready(function(){

    $('.hrefBlank').live('click',function(){
        var sHref = $(this).attr('href');
        window.open(sHref);
        return false;
    });

    $(':input').live('focus',function(){
        $(this).attr('autocomplete', 'off');
    });

    //Toggle
    $('#filterPanelForm .collapse').live('click', function(){
        $('#filterPanelForm .collapse').find('img').attr("src", "../images/filter-panel/collapse_icon.png");
        $('#filterPanelForm .collapse').removeClass('collapse').addClass('expand');
        $('.filterPanel .filterContent').hide();
        $('.filterPanel .filterBottom').hide();
        $('.filterPanel .collapseBottom').show();
        $('.filterPanel .filterHead').css({"margin-bottom":"-2px","height":'30px'});
    });
    $('#filterPanelForm .expand').live('click', function(){
        $('#filterPanelForm .expand').find('img').attr("src", "../images/filter-panel/expand_icon.png");
        $('#filterPanelForm .expand').removeClass('expand').addClass('collapse');
        $('.filterPanel .filterContent').show();
        $('.filterPanel .filterBottom').show();
        $('.filterPanel .collapseBottom').hide();
        $('.filterPanel .filterHead').css({"margin-bottom":"0px","height":'32px'});
    });

    var parseDateValue = function(rawDate){
        if ($.trim(rawDate) == '') {
            return '';
        }
        rawDate = $.trim(rawDate);
        var dateArray = rawDate.split(" ")[0];
        dateArray = dateArray.split("/");
        var parsedDate = dateArray[2] + dateArray[0] + dateArray[1];
        return parsedDate;
    }

    // The plugin function for adding a new filtering routine
    var dateCol;
    var category = [];
    var uploader = [];
    $.fn.dataTableExt.afnFiltering.push(function(oSettings,aData,iDataIndex){
        var dateStart;
        var dateEnd;
        if($('#SectionForDateVersion').is(':visible')){
            if($("#startDateDataVersion").val() == null || $("#startDateDataVersion").val() == ''){
                dateStart = '';
            }else{
                dateStart = parseDateValue($("#startDateDataVersion").val())
            }
            if($("#endDateDataVersion").val() == null || $("#endDateDataVersion").val() == ''){
                dateEnd = 'zzzzzzzz';
            }else{
                dateEnd = parseDateValue($("#endDateDataVersion").val())
            }
        }else if($('#SectionForCategoryVersion').is(':visible')){
            if($("#startDateCaegoryVersion").val() == null || $("#startDateCaegoryVersion").val() == ''){
                dateStart = '';
            }else{
                dateStart = parseDateValue($("#startDateCaegoryVersion").val())
            }
            if($("#endDateCategoryVersion").val() == null || $("#endDateCategoryVersion").val() == ''){
                dateEnd = 'zzzzzzzz';
            }else{
                dateEnd = parseDateValue($("#endDateCategoryVersion").val())
            }
        }else if($('#SectionForDateProfile').is(':visible')){
            if($("#startDateDateProfile").val() == null || $("#startDateDateProfile").val() == ''){
                dateStart = '';
            }else{
                dateStart = parseDateValue($("#startDateDateProfile").val())
            }
            if($("#endDateDateProfile").val() == null || $("#endDateDateProfile").val() == ''){
                dateEnd = 'zzzzzzzz';
            }else{
                dateEnd = parseDateValue($("#endDateDateProfile").val())
            }
        }else{
            if($("#startDateCaegoryProfile").val() == null || $("#startDateCaegoryProfile").val() == ''){
                dateStart = '';
            }else{
                dateStart = parseDateValue($("#startDateCaegoryProfile").val())
            }
            if($("#endDateCategoryProfile").val() == null || $("#endDateCategoryProfile").val() == ''){
                dateEnd = 'zzzzzzzz';
            }else{
                dateEnd = parseDateValue($("#endDateCategoryProfile").val())
            }
        }
        var evalDate = parseDateValue(aData[dateCol]);
        if(evalDate >= dateStart && evalDate <= dateEnd){
            return true;
        }
        else{
            return false;
        }

    });

    $.fn.dataTableExt.afnFiltering.push(function(oSettings,aData,iDataIndex){
        var evalCategory = $(aData[2]).text();
        if(category.length){
            if(jQuery.inArray(evalCategory,category) == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    });

    $.fn.dataTableExt.afnFiltering.push(function(oSettings,aData,iDataIndex){
        var evalUploader = $(aData[3]).text();
        if(uploader.length){
            if(jQuery.inArray(evalUploader,uploader) == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    });

    //Apply Button
    $('.btnApply').live('click', function () {
        category = [];
        uploader = [];
        $("input.searchBoxForAssets").val('');
        $('.resultSearch').hide();
        $('.resultSearch .result').empty();
        switch ($('.btnApply').index($(this))) {
            case 1 :
                dateCol = 4;
                $('#SectionForCategoryVersion .columnCategory input:checked').each(function () {
                    category.push($(this).parents('.multiOptionRow').find('label').text());
                });
                $('#SectionForCategoryVersion .columnUploader input:checked').each(function () {
                    uploader.push($(this).parents('.multiOptionRow').find('label').text());
                });
                oCategroyCurrentTable.fnDraw();
                oCategroyCurrentTable.fnFilter('');
                break;
            case 2 :
                dateCol = 3;
                $('#SectionForDateProfile .columnCategory input:checked').each(function () {
                    category.push($(this).parents('.multiOptionRow').find('label').text());
                });
                oDateTable.fnDraw();
                oDateTable.fnFilter('');
                break;
            case 3 :
                dateCol = 3;
                $('#SectionForCategoryProfile .columnCategory input:checked').each(function () {
                    category.push($(this).parents('.multiOptionRow').find('label').text());
                });
                oCategroyTable.fnDraw();
                oCategroyTable.fnFilter('');
                break;
            default:
                dateCol = 4;
                $('#SectionForDateVersion .columnCategory input:checked').each(function () {
                    category.push($(this).parents('.multiOptionRow').find('label').text());
                });
                $('#SectionForDateVersion .columnUploader input:checked').each(function () {
                    uploader.push($(this).parents('.multiOptionRow').find('label').text());
                });
                oDateCurrentTable.fnDraw();
                oDateCurrentTable.fnFilter('');
        }
    });

    //Page
    var sStdMenu =
        '<strong>Show:  </strong><select size="1" name="dataTableLength" id="dataTableLength">' +
            '<option value="5">5</option>' +
            '<option value="10">10</option>' +
            '<option value="25">25</option>' +
            '<option value="-1">All</option>' +
            '</select>';

    //Date Table For Current Version
    oDateCurrentTable = $('#assetsCurrentDateData').dataTable({
        "fnDrawCallback": function (oSettings) {
            if (oSettings.aiDisplay.length == 0) {
                return;
            }
            var nTrs = $('#assetsCurrentDateData tbody tr');
            var iColspan = nTrs[0].getElementsByTagName('td').length;
            var sLastGroup = "";
            for (var i = 0; i < nTrs.length; i++) {
                var iDisplayIndex = oSettings._iDisplayStart + i;
                var sGroup = oSettings.aoData[oSettings.aiDisplay[iDisplayIndex]]._aData[0];
                if (sGroup != sLastGroup) {
                    var nGroup = document.createElement('tr');
                    var nCell = document.createElement('td');
                    nCell.colSpan = iColspan;
                    nCell.className = "groupTitle";
                    nCell.innerHTML = sGroup;
                    nGroup.appendChild(nCell);
                    nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                    sLastGroup = sGroup;
                }
            }

        },
        "aoColumnDefs": [
            {"bVisible": false, "aTargets": [0]},
            {"sWidth": "435px", "aTargets": [1]},
            {"sWidth": "8%", "aTargets": [3]},
            {"sWidth": "8%", "aTargets": [4]},
            {"sWidth": "24%", "bSortable": false, "aTargets": [5]}
        ],
        "aaSorting": [
            [ 4, 'asc' ]
        ],
        "aaSortingFixed": [
            [ 0, 'asc' ]
        ],
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + "&nbsp; per page",
            "sInfoFiltered": ""
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l'
    });


    //Category Table For Current Version
    oCategroyCurrentTable = $('#assetsCurrentCategoryData').dataTable({
        "fnDrawCallback": function (oSettings) {
            if (oSettings.aiDisplay.length == 0) {
                return;
            }

            var nTrs = $('#assetsCurrentCategoryData tbody tr');
            var iColspan = nTrs[0].getElementsByTagName('td').length;
            var sLastGroup = "";
            for (var i = 0; i < nTrs.length; i++) {
                var iDisplayIndex = oSettings._iDisplayStart + i;
                var sGroup = oSettings.aoData[oSettings.aiDisplay[iDisplayIndex]]._aData[0];
                if (sGroup != sLastGroup) {
                    var nGroup = document.createElement('tr');
                    var nCell = document.createElement('td');
                    nCell.colSpan = iColspan;
                    nCell.className = "groupTitle";
                    nCell.innerHTML = sGroup;
                    nGroup.appendChild(nCell);
                    nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                    sLastGroup = sGroup;
                }
            }
        },
        "aoColumnDefs": [
            {"bVisible": false, "aTargets": [0]},
            {"sWidth": "435px", "aTargets": [1]},
            {"sWidth": "8%", "aTargets": [3]},
            {"sWidth": "8%", "aTargets": [4]},
            {"sWidth": "24%", "bSortable": false, "aTargets": [5]}
        ],
        "aaSorting": [
            [ 4, 'asc' ]
        ],
        "aaSortingFixed": [
            [ 0, 'asc' ]
        ],
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + "per page",
            "sInfoFiltered": ""
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l'
    });

    //Date Table For My Profile
    oDateTable = $('#assetsDateData').dataTable({
        "fnDrawCallback": function (oSettings) {
            if (oSettings.aiDisplay.length == 0) {
                return;
            }

            var nTrs = $('#assetsDateData tbody tr');
            var iColspan = nTrs[0].getElementsByTagName('td').length;
            var sLastGroup = "";
            for (var i = 0; i < nTrs.length; i++) {
                var iDisplayIndex = oSettings._iDisplayStart + i;
                var sGroup = oSettings.aoData[oSettings.aiDisplay[iDisplayIndex]]._aData[0];
                if (sGroup != sLastGroup) {
                    var nGroup = document.createElement('tr');
                    var nCell = document.createElement('td');
                    nCell.colSpan = iColspan;
                    nCell.className = "groupTitle";
                    nCell.innerHTML = sGroup;
                    nGroup.appendChild(nCell);
                    nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                    sLastGroup = sGroup;
                }
            }
        },
        "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            $(nRow).removeClass('even').removeClass('odd');
            if ($(nRow).attr('class') == 'groupA') {
                iGroupA = iDisplayIndex + 1;
                if ((iDisplayIndex + 1) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupB') {
                iGroupB = iDisplayIndex + 1 - iGroupA;
                if ((iDisplayIndex + 1 - iGroupA) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupC') {
                iGroupC = iDisplayIndex + 1 - iGroupA - iGroupB;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupD') {
                iGroupD = iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
        },
        "aoColumnDefs": [
            {"bVisible": false, "aTargets": [0]},
            {"sWidth": "375px", "aTargets": [1]},
            {"sWidth": "8%", "aTargets": [3]},
            {"sWidth": "24%", "bSortable": false, "aTargets": [4]}
        ],
        "aaSorting": [
            [ 3, 'asc' ]
        ],
        "aaSortingFixed": [
            [ 0, 'asc' ]
        ],
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + "<div class='perpage'>per page<div>",
            "sInfoFiltered": ""
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l'
    });

    //Category Table For My Profile
    oCategroyTable = $('#assetsCategoryData').dataTable({
        "fnDrawCallback": function (oSettings) {
            if (oSettings.aiDisplay.length == 0) {
                return;
            }

            var nTrs = $('#assetsCategoryData tbody tr');
            var iColspan = nTrs[0].getElementsByTagName('td').length;
            var sLastGroup = "";
            for (var i = 0; i < nTrs.length; i++) {
                var iDisplayIndex = oSettings._iDisplayStart + i;
                var sGroup = oSettings.aoData[oSettings.aiDisplay[iDisplayIndex]]._aData[0];
                if (sGroup != sLastGroup) {
                    var nGroup = document.createElement('tr');
                    var nCell = document.createElement('td');
                    nCell.colSpan = iColspan;
                    nCell.className = "groupTitle";
                    nCell.innerHTML = sGroup;
                    nGroup.appendChild(nCell);
                    nTrs[i].parentNode.insertBefore(nGroup, nTrs[i]);
                    sLastGroup = sGroup;
                }
            }
        },
        "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            $(nRow).removeClass('even').removeClass('odd');
            if ($(nRow).attr('class') == 'groupA') {
                iGroupA = iDisplayIndex + 1;
                if ((iDisplayIndex + 1) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupB') {
                iGroupB = iDisplayIndex + 1 - iGroupA;
                if ((iDisplayIndex + 1 - iGroupA) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupC') {
                iGroupC = iDisplayIndex + 1 - iGroupA - iGroupB;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupD') {
                iGroupD = iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupE') {
                iGroupE = iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC - iGroupD;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC - iGroupD) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
            if ($(nRow).attr('class') == 'groupF') {
                iGroupF = iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC - iGroupD - iGroupE;
                if ((iDisplayIndex + 1 - iGroupA - iGroupB - iGroupC - iGroupD - iGroupE) % 2 == 0) {
                    $(nRow).addClass('even');
                }
            }
        },
        "aoColumnDefs": [
            {"bVisible": false, "aTargets": [0]},
            {"sWidth": "375px", "aTargets": [1]},
            {"sWidth": "8%", "aTargets": [3]},
            {"sWidth": "24%", "bSortable": false, "aTargets": [4]}
        ],
        "aaSorting": [
            [ 3, 'asc' ]
        ],
        "aaSortingFixed": [
            [ 0, 'asc' ]
        ],
        "iDisplayLength": 10,
        "bStateSave": false,
        "bFilter": true,
        "bSort": true,
        "bAutoWidth": false,
        "oLanguage": {
            "sLengthMenu": sStdMenu + "<div class='perpage'>per page<div>",
            "sInfoFiltered": ""
        },
        "sPaginationType": "full_numbers",
        "sDom": 'rti<"bottom2"p><"bottom1"l'
    });

    $(".dataTables_paginate .last").addClass("hide");
    $(".dataTables_paginate .first").addClass("hide");
    $(".previous").html("&nbsp;Prev&nbsp;");
    $(".next").html("&nbsp;Next&nbsp;");

    //Search Input Text
    $("input.searchBoxForAssets").keyup(function(){
        var iLength = 0;
        switch($("input.searchBoxForAssets").index($(this))){
            case 1:
                oCategroyCurrentTable.fnDraw();
                oCategroyCurrentTable.fnFilter(this.value);
                iLength = oCategroyCurrentTable.$('tr',{"filter": "applied"}).length;
                break;
            case 2:
                oDateTable.fnDraw();
                oDateTable.fnFilter(this.value);
                iLength = oDateTable.$('tr',{"filter": "applied"}).length;
                break;
            case 3:
                oCategroyTable.fnDraw();
                oCategroyTable.fnFilter(this.value);
                iLength = oCategroyTable.$('tr',{"filter": "applied"}).length;
                break;
            default:
                oDateCurrentTable.fnDraw();
                oDateCurrentTable.fnFilter(this.value);
                iLength = oDateCurrentTable.$('tr',{"filter": "applied"}).length;
        }
        if($(this).val()){
            $(this).parents('.assetsWrapper').find('.resultSearch').show();
            $(this).parents('.assetsWrapper').find('.resultSearch .result').empty().append('<strong class="num">'+ iLength +'</strong> Files found for <strong>"'+ this.value +'"</strong>');
        }else{
            $(this).parents('.assetsWrapper').find('.resultSearch').hide();
        }
    });

    //Clear Selection
    $('.filterContent .clearLink').live('click',function(){
        $('.columnCategory input:checkbox').attr('checked',false);
        $('.columnCategory .multiOptionRow').removeClass('multiOptionRowChecked');
        $('.columnUploader input:checkbox').attr('checked',false);
        $('.columnUploader .multiOptionRow').removeClass('multiOptionRowChecked');
        $('.dateRangeRow input:text').val('');
        $('input.searchBoxForAssets').val('');
        $('.resultSearch').hide();
        $('.resultSearch .result').empty();
        category = [];
        uploader = [];
        oCategroyCurrentTable.fnDraw();
        oCategroyCurrentTable.fnFilter('');
        oDateCurrentTable.fnDraw();
        oDateCurrentTable.fnFilter('');
        oDateTable.fnDraw();
        oDateTable.fnFilter('');
        oCategroyTable.fnDraw();
        oCategroyTable.fnFilter('');
    });

    //All Assets Links
    $('.resultSearch a').live('click',function(){
        switch($('.resultSearch a').index($(this))){
            case 1:
                oCategroyCurrentTable.fnFilter('');
                break;
            case 2:
                oDateTable.fnFilter('');
                break;
            case 3:
                oCategroyTable.fnFilter('');
                break;
            default:
                oDateCurrentTable.fnFilter('');
        }
        $(this).parents('.assetsWrapper').find("input.searchBoxForAssets").val('');
        $(this).parents('.assetsWrapper').find('.resultSearch').hide();
    });

    //Group By
    $('.groupBy li').live('click',function(){
        $('#SectionForDateVersion,#SectionForCategoryVersion,#SectionForDateProfile,#SectionForCategoryProfile').hide();
        if($(this).parents('.viewOptionArea').find('.view li a.myFile').parents('li').hasClass('active')){
            if($(this).find('a').hasClass('date')){
                $('#SectionForDateProfile').show();
            }else{
                $('#SectionForCategoryProfile').show();
            }
        }else{
            if($(this).find('a').hasClass('date')){
                $('#SectionForDateVersion').show();
            }else{
                $('#SectionForCategoryVersion').show();
            }
        }
        fnSetFilterWidth();
    });

    //View
    $('.view li').live('click',function(){
        $('#SectionForDateVersion,#SectionForCategoryVersion,#SectionForDateProfile,#SectionForCategoryProfile').hide();
        if($(this).find('a').hasClass('currentVersion')){
            if($(this).parents('.viewOptionArea').find('.groupBy li a.date').parents('li').hasClass('active')){
                $('#SectionForDateVersion').show();
            }else{
                $('#SectionForCategoryVersion').show();
            }
        }else{
            if($(this).parents('.viewOptionArea').find('.groupBy li a.date').parents('li').hasClass('active')){
                $('#SectionForDateProfile').show();
            }else{
                $('#SectionForCategoryProfile').show();
            }
        }
        fnSetFilterWidth();
    });

    //Check All
    $('#checkVersionDate').live('click',function(){
        if($(this).attr('checked')){
            oDateCurrentTable.$('input:checkbox').attr('checked',true);
            $('#SectionForDateVersion .batchOperation').show();
        }else{
            oDateCurrentTable.$('input:checkbox').attr('checked',false);
            $('#SectionForDateVersion .batchOperation').hide();
        }
    });

    $('#checkVersionCategory').live('click',function(){
        if($(this).attr('checked')){
            oCategroyCurrentTable.$('input:checkbox').attr('checked',true);
            $('#SectionForCategoryVersion .batchOperation').show();
        }else{
            oCategroyCurrentTable.$('input:checkbox').attr('checked',false);
            $('#SectionForCategoryVersion .batchOperation').hide();
        }
    });

    $('#checkProfileDate').live('click',function(){
        if($(this).attr('checked')){
            oDateTable.$('input:checkbox').attr('checked',true);
            $('#SectionForDateProfile .batchOperation').show();
        }else{
            oDateTable.$('input:checkbox').attr('checked',false);
            $('#SectionForDateProfile .batchOperation').hide();
        }
    });

    $('#checkProfileCategory').live('click',function(){
        if($(this).attr('checked')){
            oCategroyTable.$('input:checkbox').attr('checked',true);
            $('#SectionForCategoryProfile .batchOperation').show();
        }else{
            oCategroyTable.$('input:checkbox').attr('checked',false);
            $('#SectionForCategoryProfile .batchOperation').hide();
        }
    });

    //Batch Operation
    $('#assetsCurrentDateData input:checkbox').live('click',function(){
        var checkAllFlag = true;
        var checkedNum = 0;
        $('#SectionForDateVersion .batchOperation').hide();
        if($(this).attr('checked')){
            oDateCurrentTable.$('input:checkbox').each(function(){
                if(!$(this).attr('checked')){
                    checkAllFlag = false;
                }
            });
            if(checkAllFlag){
                $('#checkVersionDate').attr('checked',true);
            }
        }else{
            $('#checkVersionDate').attr('checked',false);
        }
        oDateCurrentTable.$('input:checkbox').each(function(){
            if($(this).attr('checked')){
                checkedNum++;
            }
        });
        if(checkedNum>1){
            $('#SectionForDateVersion .batchOperation').show();
        }
    });

    $('#assetsCurrentCategoryData input:checkbox').live('click',function(){
        var checkAllFlag = true;
        var checkedNum = 0;
        $('#SectionForCategoryVersion .batchOperation').hide();
        if($(this).attr('checked')){
            oCategroyCurrentTable.$('input:checkbox').each(function(){
                if(!$(this).attr('checked')){
                    checkAllFlag = false;
                }
            });
            if(checkAllFlag){
                $('#checkVersionCategory').attr('checked',true);
            }
        }else{
            $('#checkVersionCategory').attr('checked',false);
        }
        oCategroyCurrentTable.$('input:checkbox').each(function(){
            if($(this).attr('checked')){
                checkedNum++;
            }
        });
        if(checkedNum>1){
            $('#SectionForCategoryVersion .batchOperation').show();
        }
    });

    $('#assetsDateData input:checkbox').live('click',function(){
        var checkAllFlag = true;
        var checkedNum = 0;
        $('#SectionForDateProfile .batchOperation').hide();
        if($(this).attr('checked')){
            oDateTable.$('input:checkbox').each(function(){
                if(!$(this).attr('checked')){
                    checkAllFlag = false;
                }
            });
            if(checkAllFlag){
                $('#checkProfileDate').attr('checked',true);
            }
        }else{
            $('#checkProfileDate').attr('checked',false);
        }
        oDateTable.$('input:checkbox').each(function(){
            if($(this).attr('checked')){
                checkedNum++;
            }
        });
        if(checkedNum>1){
            $('#SectionForDateProfile .batchOperation').show();
        }
    });

    $('#assetsCategoryData input:checkbox').live('click',function(){
        var checkAllFlag = true;
        var checkedNum = 0;
        $('#SectionForCategoryProfile .batchOperation').hide();
        if($(this).attr('checked')){
            oCategroyTable.$('input:checkbox').each(function(){
                if(!$(this).attr('checked')){
                    checkAllFlag = false;
                }
            });
            if(checkAllFlag){
                $('#checkProfileCategory').attr('checked',true);
            }
        }else{
            $('#checkProfileCategory').attr('checked',false);
        }
        oCategroyTable.$('input:checkbox').each(function(){
            if($(this).attr('checked')){
                checkedNum++;
            }
        });
        if(checkedNum>1){
            $('#SectionForCategoryProfile .batchOperation').show();
        }
    });


    //Remove Assets
    $('.managementCell .remove').live('click',function(){
        modalPreloader('#removeFileFromCockpit');
        //Get File Size
        $('#removeFileFromCockpit .fileDetails li span').text($(this).parents('tr').find('.fileDetails .size').text());
        if($('.versionViewDetails').length){
            //GET File Name
            $('.uploadFileDetails .fileName').text($('.versionViewDetails .fileName').text());
            //Get File Version
            $('#removeFileFromCockpit .fileDetails .version').text($(this).parents('tr').find('.version').text());
            //Set File Icon
            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('src',eSrc);
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('alt','');
            if($('.versionViewDetails .icon-file').hasClass('imageView')){
                $('#removeFileFromCockpit .fileDetails .icon-file').addClass('imageView');
                $('#removeFileFromCockpit .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('#removeFileFromCockpit .fileDetails .icon-file').removeClass('imageView');
                $('#removeFileFromCockpit .fileDetails .iconView').remove();
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor','default');
            }
        }else{
            //Get File Name
            $('#removeFileFromCockpit .fileDetails .fileName').text($(this).parents('tr').find('.fileDetails .first a').text());
            //Get File Version
            $('#removeFileFromCockpit .fileDetails .version').text($(this).parents('tr').find('.fileDetails .version').text());
            $('#removeFileFromCockpit .fileDetails .version').removeClass('imageVersion');
            if($(this).parents('tr').find('.fileDetails .version').hasClass('modifyVersion')){
                $('#removeFileFromCockpit .fileDetails .version').addClass('modifyVersion');
            }else{
                $('#removeFileFromCockpit .fileDetails .version').removeClass('modifyVersion');
            }
            //Set File Icon
            var eSrc = $(this).parents('tr').find('.icon-file-small img').attr('src');
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('src',eSrc.replace('-small',''));
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('alt','');
            if($(this).parents('tr').find('.icon-file-small').hasClass('imageView')){
                $('#removeFileFromCockpit .fileDetails .icon-file').addClass('imageView');
                $('#removeFileFromCockpit .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('#removeFileFromCockpit .fileDetails .icon-file').removeClass('imageView');
                $('#removeFileFromCockpit .fileDetails .iconView').remove();
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor','default');
            }
        }
        return false;
    });
    $('#removeFileFromCockpit .btnCancel').live('click',function(){
        modalClose();
        return false;
    });
    $('#removeFileFromCockpit .btnYes').live('click',function(){
        modalLoad('#deletingModal');
        setTimeout(function(){
            modalClose();
        },intPreloaderTimmer);
        return false;
    });

    if($('#dynamicUpload').length){
        var options = {iframe: {url: '', force: false}};
        var zone = new FileDrop('dynamicUpload', options);
        $('.fd-file').live('click',function(){
            return false;
        });
        $('.dragSucess ul li').remove();
        zone.on.send.push(function(files){
            $('.dragPre').hide();
            $('.dragSucess').show();
            for(i=0;i<files.length;i++){
                var eFileStyle = (/\.[^\.]+$/.exec(files[i].name).toString().replace('.',''));
                var iSize;
                if(Math.round(files[i].size/1000*100)/100 > 1 && Math.round(files[i].size/1000*100)/100 < 1000){
                    iSize = Math.round(files[i].size/1000*100)/100 + 'KB';
                }else if(Math.round(files[i].size/1000*100)/100 > 1000){
                    iSize = Math.round(files[i].size/1000/1000*100)/100 + 'MB';
                }else{
                    iSize = files[i].size + 'Byte';
                }
                switch (eFileStyle){
                    case 'zip' :
                        eFileStyle = 'zip';
                        break;
                    case 'rar' :
                        eFileStyle = 'rar';
                        break;
                    case '7z' :
                        eFileStyle = '7z';
                        break;
                    case 'pdf' :
                        eFileStyle = 'pdf';
                        break;
                    case 'doc' :
                        eFileStyle = 'doc';
                        break;
                    case 'xls' :
                        eFileStyle = 'xls';
                        break;
                    case 'ppt' :
                        eFileStyle = 'ppt';
                        break;
                    case 'txt' :
                        eFileStyle = 'txt';
                        break;
                    case 'png' :
                        eFileStyle = 'png';
                        break;
                    case 'gif' :
                        eFileStyle = 'gif';
                        break;
                    case 'jpg' :
                        eFileStyle = 'jpg';
                        break;
                    case 'bmp' :
                        eFileStyle = 'bmp';
                        break;
                    case 'psd' :
                        eFileStyle = 'psd';
                        break;
                    case 'ai' :
                        eFileStyle = 'ai';
                        break;
                    case 'eps' :
                        eFileStyle = 'eps';
                        break;
                    case 'fla' :
                        eFileStyle = 'fla';
                        break;
                    case 'swf' :
                        eFileStyle = 'swf';
                        break;
                    case 'html' :
                        eFileStyle = 'html';
                        break;
                    case 'css' :
                        eFileStyle = 'css';
                        break;
                    case 'js' :
                        eFileStyle = 'js';
                        break;
                    case 'xml' :
                        eFileStyle = 'xml';
                        break;
                    default:
                        eFileStyle = 'other';

                }
                if($('#selectFileForVersion').length){
                    if(files[i].name.length > 20){
                        eFile = files[i].name.substr(0,20)+ '...';
                    }else{
                        eFile = files[i].name;
                    }
                }else{
                    eFile = files[i].name;
                }
                if($('#selectFileForVersion').length){
                    $('.dragSucess ul').empty();
                    $('.dragSucess ul').append('<li><a href="javascript:;" class="removeFile">X</a><span class="icon-file-small"><img src="../images/icon-small-'+eFileStyle+'.png" alt="" /></span><div class="fileName">'+eFile+'<span> ('+iSize+')</span></div></li>');
                }else{
                    $('.dragSucess ul').append('<li><a href="javascript:;" class="removeFile">X</a><span class="icon-file-small"><img src="../images/icon-small-'+eFileStyle+'.png" alt="" /></span><div class="fileName">'+eFile+'<span> ('+iSize+')</span></div></li>');
                    $('.dragSucess p span').text($('.dragSucess li').length);
                    fnSetHeight();
                }
            }
        });
        $('.dragSucess li .removeFile').live('click',function(){
            $(this).parents('li').remove();
            $('.dragSucess p span').text($('.dragSucess li').length);
            fnSetHeight();
            if(!$('.dragSucess li').length){
                $('.dragPre').show();
                $('.dragSucess').hide();
            }
        });
    }

    //Set Upload Container
    function fnSetHeight(){
        if($('.sectionContainer #dynamicUpload').length && $('.sectionContainer .commonUpload').length){
            if($('.sectionContainer #dynamicUpload').height() >= $('.sectionContainer .commonUpload').height()){
                $('#beforeUpload .sectionContainer').css('height',$('.sectionContainer #dynamicUpload').height());
            }else{
                $('#beforeUpload .sectionContainer').css('height',$('.sectionContainer .commonUpload').height());
            }
        }
    }
    fnSetHeight();

    //Add File Upload
    $('.uploadSection .btnMoreFile').live('click',function(){
        $('.uploadSection .uploadRow:last').after($('.uploadSection .uploadRow:last').clone(true));
        fnSetHeight();
    });

    //Check Box For Pop Up Access
    $('.popUpPrivateAccess .firstItem input:checkbox').live('click',function(){
        if($(this).attr('checked')){
            $(this).parents('.group').find('input:checkbox').attr('checked',true);
        }else{
            $(this).parents('.group').find('input:checkbox').attr('checked',false);
        }
    });

    $('.popUpPrivateAccess input:checkbox.assetUserPermission').live('click', function () {

        var allChecked = $(this).parents('.group').find('input:checkbox.assetUserPermission').length
            == $(this).parents('.group').find('input:checkbox.assetUserPermission:checked').length;

        if (allChecked) {
            $(this).parents('.group').find(".firstItem input:checkbox").attr('checked', 'checked');
        } else {
            $(this).parents('.group').find(".firstItem input:checkbox").removeAttr('checked');
        }

    });

    //Upload Function
    var loadTimer = 0;
    var timerCT;
    var sizeCT;
    var processCT;
    if($('#uploading').length){
        clearInterval(timerCT);
        clearInterval(sizeCT);
        uploadLoading = function(){
            var upProcess = 0;
            $('.processed').animate({
                width: '100%'
            },loadTimer);
            timerCT = setInterval(function(){
                $('.processTime').each(function(){
                    var ramainSecond  = parseInt($(this).text());
                    $(this).text(ramainSecond-1);
                });
            },1000);
            var uploadSize = 0;
            sizeCT = setInterval(function(){
                uploadSize = uploadSize + 0.075
                $('.fileSize').each(function(){
                    $(this).text(Math.round((uploadSize)*10)/10);
                });
            },1000);
        }
        // uploadLoading();
        uploadCT = setTimeout(function(){
            $('.uploading').hide();
            $('.uploadSucess').show();
            $('.noFlyout').removeClass('noFlyout');
        },loadTimer);
    }

    $('body').live('click',function(){
        $('.popUpNewCategory').hide();
        // $('.popUpPrivateAccess').hide();
        // $('.popUpPrivateAccessView').hide();
    });

    //Add Category
    $('.LinkNewCategory:not(".noFlyout")').live('click',function(e){
        $('.fileUploadItem').css({
            zIndex: '1'
        });
        $(this).parents('.fileUploadItem').css({
            zIndex: '10'
        });
        $(this).parents('.fileUploadItem').find('.selectAccess').css({
            zIndex: '1'
        });
        $(this).parents('.fileUploadItem').find('.selectCategory').css({
            zIndex: '10'
        });
        $('.popUpNewCategory').hide();
        $('.popUpPrivateAccess').hide();
        $('.popUpPrivateAccessView').hide();
        $(this).parents('.selectCategory').find('.popUpNewCategory').show();
        if($(this).parents('#uploadFinish').length || $(this).parents('#editFileDetails').length){
            $(this).parents('.selectCategory').find('.popUpNewCategory').css({
                top: $(this).position().top + 30,
                right: '-20px'
            });
        }else{
            $(this).parents('.selectCategory').find('.popUpNewCategory').css({
                top: $(this).position().top + 30,
                left: $(this).position().left - $(this).parents('.selectCategory').find('.popUpNewCategory').width()/2 + $(this).width()/2 + 6
            });
        }
        e.stopPropagation();
    });

    $('.popUpNewCategory').live('click',function(e){
        e.stopPropagation();
    });

    $('.popUpNewCategory .linkCancel').live('click',function(){
        $(this).parents('.selectCategory').find('input:text').val('');
        $('.popUpNewCategory').hide();
    });

    $('.popUpNewCategory .linkAddCategory').live('click',function(){
        var sText = $(this).parents('.selectCategory').find('input:text').val();
        if(sText){
            $(this).parents('.selectCategory').find('select').append('<option>'+ sText +'</option>');
        }
        $(this).parents('.selectCategory').find('input:text').val('');
        $('.popUpNewCategory').hide();
    });

    //Select Access
    $('input.private:not(".selected")').live('click',function(e){

        if(!$(this).hasClass('noFlyout')){
            $('.fileUploadItem').css({
                zIndex: '1'
            });
            $(this).parents('.fileUploadItem').css({
                zIndex: '10'
            });
            $(this).parents('.fileUploadItem').find('.selectCategory').css({
                zIndex: '1'
            });
            $(this).parents('.fileUploadItem').find('.selectAccess').css({
                zIndex: '10'
            });
            $(this).parents('.editFileSection').find('.selectCategory').css({
                zIndex: '1'
            });
            $(this).parents('.editFileSection').find('.selectAccess').css({
                zIndex: '10'
            });
            $('.popUpNewCategory').hide();
            $('.popUpPrivateAccess').hide();
            $('.popUpPrivateAccessView').hide();
            $(this).parents('.selectAccess').find('.popUpPrivateAccess').show();
            if($(this).parents('#uploadFinish').length || $(this).parents('#editFileDetails').length){
                $(this).parents('.selectAccess').find('.popUpPrivateAccess .popUpPrivateAccessSArrow').css({
                    bottom: '8px',
                    left: '-9px'
                });
                $(this).parents('.selectAccess').find('.popUpPrivateAccess').css({
                    bottom: '-10px',
                    left: $(this).position().left + $(this).parents('.radioBox').find('label').width() + 35
                });
            }else{
                $(this).parents('.selectAccess').find('.popUpPrivateAccess .popUpPrivateAccessSArrow').css({
                    top: ($(this).parents('.selectAccess').find('.popUpPrivateAccess').height()-$(this).parents('.selectAccess').find('.popUpPrivateAccess .popUpPrivateAccessSArrow').height())/2-2,
                    left: '-8px'
                });
                $(this).parents('.selectAccess').find('.popUpPrivateAccess').css({
                    top: $(this).position().top - ($(this).parents('.selectAccess').find('.popUpPrivateAccess').height()-$(this).height())/2,
                    left: $(this).position().left + $(this).parents('.radioBox').find('label').width() + 35
                });
            }
        }
        e.stopPropagation();
    });

    $('input.public').live('click',function(e){
        $(this).parents('.radioBox').find('.accessOperate').css('visibility','hidden');
        $(this).parents('.selectAccess').find('.popUpPrivateAccess').find('input:checkbox').each(function(){
            $(this).attr('checked',false);
        });
        $(this).parents('.radioBox').find('.private').removeClass('selected');
        $(".popUpPrivateAccess").hide();
        e.stopPropagation();
    });

    $('.linkEditAccess').live('click',function(e){
        $(this).parents('.selectAccess').find('.popUpPrivateAccess').show();
        $('.popUpNewCategory').hide();
        e.stopPropagation();
    });

    $('.popUpPrivateAccess').live('click',function(e){
        e.stopPropagation();
    });

    $('.popUpPrivateAccess .linkSubmit').live('click',function(){
        var flag = false;
        $('.popUpPrivateAccess').hide();
        $(this).parents('.popUpPrivateAccess').find('input:checkbox').each(function(){
            if($(this).attr('checked')){
                flag = true;
            }
        });
        if(flag){
            $(this).parents('.selectAccess').find('.accessOperate').css('visibility','visible');
            $(this).parents('.selectAccess').find('.private').addClass('selected');
        }else{
            $(this).parents('.selectAccess').find('.accessOperate').css('visibility','hidden');
            $(this).parents('.selectAccess').find('.private').removeClass('selected');
        }
    });

    //View Access
    $('.linkViewAccess').live('click',function(e){
        $('.fileUploadItem').css({
            zIndex: 1
        });
        $(this).parents('.fileUploadItem').css({
            zIndex: 10
        });
        $(this).parents('.fileUploadItem').find('.selectCategory').css({
            zIndex: 1
        });
        $(this).parents('.fileUploadItem').find('.selectAccess').css({
            zIndex: 10
        });
        $('.popUpNewCategory').hide();
        $('.popUpPrivateAccess').hide();
        $('.popUpPrivateAccessView').hide();
        var eAccess = $(this).parents('.selectAccess').find('.popUpPrivateAccess .accessUser').clone();
        //Remove No Checked
        eAccess.find('.group div:not(".firstItem") input:checkbox').each(function(){
            if(!$(this).attr('checked')){
                $(this).parent('div').remove();
            }
        });
        eAccess.find('.group').each(function(){
            if(!$(this).find('div:not(".firstItem")').length){
                $(this).remove();
            }
        });
        eAccess.find('input:checkbox').remove();
        eAccess.find('label').attr('for','').css('cursor','default');
        $(this).parents('.selectAccess').find('.popUpPrivateAccessView .accessUser').empty().append(eAccess);
        $(this).parents('.selectAccess').find('.popUpPrivateAccessView').show();
        if(!$(this).parents('.outLay').length){
            $(this).parents('.selectAccess').find('.popUpPrivateAccessView').css({
                top: $(this).position().top - ($(this).parents('.selectAccess').find('.popUpPrivateAccessView').height()-$(this).height())/2,
                left: $(this).position().left + $(this).width() + 8
            });
            $(this).parents('.selectAccess').find('.popUpPrivateAccessView .popUpPrivateAccessSArrow').css({
                top: ($(this).parents('.selectAccess').find('.popUpPrivateAccessView').height()-$(this).parents('.selectAccess').find('.popUpPrivateAccessView .popUpPrivateAccessSArrow').height())/2,
                left: '-8px'
            });
        }else{
            $(this).parents('.selectAccess').find('.popUpPrivateAccessView').css({
                bottom: '-10px',
                left: $(this).position().left + $(this).width() + 8
            });
            $(this).parents('.selectAccess').find('.popUpPrivateAccessView .popUpPrivateAccessSArrow').css({
                bottom: '8px',
                left: '-9px'
            });
        }
        e.stopPropagation();
    });

    $('.popUpPrivateAccessView .linkClose').live('click',function(e){
        $('.popUpPrivateAccessView').hide();
        e.stopPropagation();
    });

    $('.popUpPrivateAccessView').live('click',function(e){
        e.stopPropagation();
    });

    //Remove Uploading Item
//	$('#uploading .fileUploadItem .removeFileUploadItem').live('click',function(){
//		$(this).parents('.itemSection').remove();
//		$('.sectionHeader .fileTotalNumber').text($('.itemSection').length);
//		$('.sectionHeader .fileTotalSize').text($('.itemSection').length*1.5);
//		if(!$('#uploading .itemSection').length){
//			window.location.href = 'uploadFile.html';
//		}
//	});

    $('#bitchEditSection .fileUploadItem .removeFileUploadItem').live('click',function(){
        $(this).parents('.itemSection').remove();
        if(!$('#bitchEditSection .itemSection').length){
            window.location.href = 'asset.html';
        }
    });

    $('.uploadSection .btnSaveDetails').live('click',function(){
        strTip = "Saving...";
        var validationErrors = [];
        // do the validation first
        if($(".itemSection-temp .selectCategory select").val() < 0) {
            validationErrors.push("Please select the asset category");
        }

        if($.trim($(".itemSection-temp .addDescription textarea").val()).length == 0) {
            validationErrors.push("Please enter the description for the file");
        }

        if (validationErrors.length > 0) {
            showErrors(validationErrors);
            return;
        }

        var saveRequest = {
            assetCategoryId:$(".itemSection-temp .selectCategory select").val(),
            assetDescription:$(".itemSection-temp .addDescription textarea").val(),
            assetPublic:$("#accessRadio1").is(":checked"),
            privateUserIds:$(".popUpPrivateAccessSection").find("input[type=checkbox].assetUserPermission:checked").map(function(){
                return $(this).attr('id');
            }).get(),
            sessionKey:$(".itemSection-temp").data("uploadSessionKey")
        };

        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/saveAssetFile",
            data: saveRequest,
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;
                        window.open('./projectAssets?formData.projectId=' + result.directProjectId, '_self');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

    });

//	oFileVersion = $('#fileVersionData').dataTable({
//		"aoColumnDefs": [{"bSortable":false,"aTargets":[3]}],
//        "aaSorting": [[2, 'asc']],
//		"bPaginate": false,
//        "bLengthChange": false,
//        "bFilter": false,
//        "bInfo": false,
//        "bAutoWidth": false
//    });

    function fnSetVersionViewHeight(){
        if($('.versionViewTable').length && $('.uploadButtonBox').length){
            if($('.versionViewTable').height() >= $('.uploadButtonBox').height()){
                $('.versionView').css('height',$('.versionViewTable').height())
            }else{
                $('.versionView').css('height',$('.uploadButtonBox').height())
            }
        }
    }
    fnSetVersionViewHeight();

    function fnSetHarrow(){
        if($('.versionViewTable .hArrow').length){
            $('.versionViewTable .hArrow').css({
                top: $('#fileVersionData tbody tr.selected').position().top + $('#fileVersionData tbody tr.selected td').height()/2,
                left: '-13px'
            });
        }
    }

    //View Version
    $('#fileVersionData tbody tr').live('click',function(e){
        $('#fileVersionData tbody tr').removeClass('selected');
        $(this).addClass('selected');
        $('.versionViewDetails .version').text($(this).find('.version').text());
        $('.versionViewDetails .versionSize').text($(this).find('.size').text());
        $('.versionViewDetails .fileNameDes').text($(this).find('.fileNameDes').text());
        $('.versionViewDetails .uploaderName').html($(this).find('.uploaderCell ').html());
        fnSetHarrow();
    });

    //Batch Delete
    $('.batchOperation .delete').live('click',function(){
        modalPreloader('#batchDelete');
        return false;
    });

    $('#batchDelete .btnCloseModal').live('click',function(){
        modalClose();
        return false;
    });

    $('#batchDelete .btnSucess').live('click',function(){
        modalLoad('#deletingModal');
        setTimeout(function(){
            modalLoad('#batchDeleted');
            return false;
        },intPreloaderTimmer);
    });

    //Batch Edit
    $('.batchOperation .edit').live('click',function(){
        window.location.href = 'batchEdit.html';
    });

    //Download Function
    var downloadTimer = 20000;
    var downloadProcessCT;
    var downloadTimerCT;
    var downloadSizeCT;
    var downloadCT;
    $('.batchOperation .download').live('click',function(){
        modalPreloader('#batchDownload');
        var downloadSize = 0;
        var remainTime = downloadTimer;
        $('#batchDownload .fileDownloading').show();
        $('#batchDownload .fileDondloaded').hide();
        $('#batchDownload .downloadingButtons').show();
        $('#batchDownload .downloadedButtons').hide();
        function setWidth(){
            $('#batchDownload .processBarInner').animate({
                width: '100%'
            },downloadTimer);
        }
        $('#batchDownload .processBarInner').css({
            width: '0%'
        });
        $("#batchDownload .processBarInner").stop(true);
        setTimeout(function(){
            if(downloadTimerCT){
                clearInterval(downloadTimerCT);
            }
            if(downloadSizeCT){
                clearInterval(downloadSizeCT);
            }
            if(downloadCT){
                clearTimeout(downloadCT);
            }
            setWidth();
            $('#batchDownload .fileSize span').text(downloadSize);
            $('#batchDownload dd').text('Client Questionnaire.docx');
            downloadTimerCT = setInterval(function(){
                remainTime = remainTime - 1000;
                iMinutes = parseInt(remainTime/1000/60);
                iSeconds = remainTime/1000%60;
                if(iMinutes){
                    if(iSeconds){
                        $('#batchDownload .ramainTime').text(iMinutes+' minutes '+iSeconds+' seconds left');
                    }else{
                        $('#batchDownload .ramainTime').text(iMinutes+' minutes left');
                    }
                }else{
                    if(iSeconds < 0){
                        $('#batchDownload .ramainTime').text('0 seconds left');
                    }else{
                        $('#batchDownload .ramainTime').text(iSeconds+' seconds left');
                    }

                }
            },1000);
            downloadSizeCT = setInterval(function(){
                downloadSize = downloadSize + 0.25
                $('#batchDownload .fileSize span').text(Math.round((downloadSize)*10)/10);
                if(Math.round((downloadSize)*10)/10 >= 1.5 && Math.round((downloadSize)*10)/10 < 2.6){
                    $('#batchDownload dd').text('Timeline.xls');
                }
                if(Math.round((downloadSize)*10)/10 >= 2.6 && Math.round((downloadSize)*10)/10 < 3.6){
                    $('#batchDownload dd').text('Client Ideas (Sketch).png');
                }
                if(Math.round((downloadSize)*10)/10 > 3.6){
                    $('#batchDownload dd').text('BRD Document.pdf');
                }
            },1000);
            downloadCT = setTimeout(function(){
                clearInterval(downloadTimerCT);
                clearInterval(downloadSizeCT);
                $('#batchDownload .fileDownloading').hide();
                $('#batchDownload .fileDondloaded').show();
                $('#batchDownload .downloadingButtons').hide();
                $('#batchDownload .downloadedButtons').show();
            },downloadTimer);
        },2000);
    });

    $('#batchDownload .btnCancelDownload,#batchDownload .btnDownload').live('click',function(){
        modalClose();
    });

    //Set Permission
    $('.batchOperation .setPermission').live('click',function(){
        modalPreloader('#batchSetPermission');
        $('#batchSetPermission .accessOperate').css('visibility','hidden');
        $('#batchSetPermission .popUpPrivateAccess input:checkbox').attr('checked',false);
        $('#batchSetPermission .radioBox input#radioModalPublic').attr('checked',true);
        $('#batchSetPermission .radioBox input#radioModalPrivate').removeClass('selected');
        return false;
    });

    $('#batchSetPermission .btnDiscard').live('click',function(){
        modalClose();
        return false;
    });

    //Upload File Details
    $('.managementCell .edit').live('click',function(){
        modalPreloader('#editFileDetails');
        //Get File Size
        $('#editFileDetails .fileDetails li span').text($(this).parents('tr').find('.fileDetails .size').text());

        if($('.versionViewDetails').length){
            //GET File Name
            $('#editFileDetails .fileDetails .fileName').text($('.versionViewDetails .fileName').text());
            //Get File Version
            $('#editFileDetails .fileDetails .version').text($(this).parents('tr').find('.version').text());
            //Set File Icon
            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
            $('#editFileDetails .icon-file img').attr('src',eSrc);
            $('#editFileDetails .icon-file img').attr('alt','');
            if($('.versionViewDetails .icon-file').hasClass('imageView')){
                $('#editFileDetails .fileDetails .icon-file').addClass('imageView');
                $('#editFileDetails .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#editFileDetails .fileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('#editFileDetails .fileDetails .icon-file').removeClass('imageView');
                $('#editFileDetails .fileDetails .iconView').remove();
                $('#editFileDetails .fileDetails .icon-file img').css('cursor','default');
            }
        }else{
            //Get File Name
            $('#editFileDetails .fileDetails .fileName').text($(this).parents('tr').find('.fileDetails .first a').text());
            //Get File Version
            $('#editFileDetails .fileDetails .version').text($(this).parents('tr').find('.fileDetails .version').text());
            $('#editFileDetails .fileDetails .version').removeClass('imageVersion');
            if($(this).parents('tr').find('.fileDetails .version').hasClass('modifyVersion')){
                $('#editFileDetails .fileDetails .version').addClass('modifyVersion');
            }else{
                $('#editFileDetails .fileDetails .version').removeClass('modifyVersion');
            }
            //Set File Icon
            var eSrc = $(this).parents('tr').find('.icon-file-small img').attr('src');
            $('#editFileDetails .icon-file img').attr('src',eSrc.replace('-small',''));
            $('#editFileDetails .icon-file img').attr('alt','');
            if($(this).parents('tr').find('.icon-file-small').hasClass('imageView')){
                $('#editFileDetails .fileDetails .icon-file').addClass('imageView');
                $('#editFileDetails .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#editFileDetails .fileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('#editFileDetails .fileDetails .icon-file').removeClass('imageView');
                $('#editFileDetails .fileDetails .iconView').remove();
                $('#editFileDetails .fileDetails .icon-file img').css('cursor','default');
            }
        }
        return false;
    });

    $('.editVersion').live('click',function(){
        modalPreloader('#editFileDetails');
        return false;
    });

    $('#editFileDetails .btnNoUpload').live('click',function(){
        modalClose();
        return false;
    });

    $('#editFileDetails .btnSaveDetails').live('click',function(){
        modalLoad('#savingModal');
        setTimeout(function(){
            modalClose();
            return false;
        },intPreloaderTimmer);
    });

    //Upload Version
    $('.managementCell .upload').live('click',function(){
        modalPreloader('#uploadFileVersion');
        //Get File Size
        $('.uploadFileDetails li span').text($(this).parents('tr').find('.fileDetails .size').text());

        if($('.versionViewDetails').length){
            //GET File Name
            $('.uploadFileDetails .fileName').text($('.versionViewDetails .fileName').text());
            //Get File Version
            $('.uploadFileDetails .version').text($(this).parents('tr').find('.version').text());
            //Set File Icon
            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
            $('.uploadFileDetails .icon-file img').attr('src',eSrc);
            $('.uploadFileDetails .icon-file img').attr('alt','');
            if($('.versionViewDetails .icon-file').hasClass('imageView')){
                $('.uploadFileDetails .icon-file').addClass('imageView');
                $('.uploadFileDetails .icon-file img').after('<span class="iconView" />');
                $('.uploadFileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('.uploadFileDetails .icon-file').removeClass('imageView');
                $('.uploadFileDetails .iconView').remove();
                $('.uploadFileDetails .icon-file img').css('cursor','default');
            }
        }else{
            //GET File Name
            $('.uploadFileDetails .fileName').text($(this).parents('tr').find('.fileDetails .first a').text());
            //Get File Version
            $('.uploadFileDetails .version').text($(this).parents('tr').find('.fileDetails .version').text());
            $('.uploadFileDetails .version').removeClass('imageVersion');
            if($(this).parents('tr').find('.fileDetails .version').hasClass('modifyVersion')){
                $('.uploadFileDetails .version').addClass('modifyVersion');
            }else{
                $('.uploadFileDetails .version').removeClass('modifyVersion');
            }
            //Set File Icon
            var eSrc = $(this).parents('tr').find('.icon-file-small img').attr('src');
            $('.uploadFileDetails .icon-file img').attr('src',eSrc.replace('-small',''));
            $('.uploadFileDetails .icon-file img').attr('alt','');
            if($(this).parents('tr').find('.icon-file-small').hasClass('imageView')){
                $('.uploadFileDetails .icon-file').addClass('imageView');
                $('.uploadFileDetails .icon-file img').after('<span class="iconView" />');
                $('.uploadFileDetails .icon-file img').css('cursor','pointer');
            }else{
                $('.uploadFileDetails .icon-file').removeClass('imageView');
                $('.uploadFileDetails .iconView').remove();
                $('.uploadFileDetails .icon-file img').css('cursor','default');
            }
        }
        return false;
    });

    $('.btnUploadVersion').live('click',function(){
        modalPreloader('#uploadFileVersion');
        return false;
    });

    $('#uploadFileVersion .btnUseLateVersion').live('click',function(){
        modalClose();
        return false;
    });

    $('#uploadFileVersion .btnUploadYes').live('click',function(){
        modalLoad('#selectFileForVersion');
        $('#selectFileForVersion .dragPre').show();
        $('#selectFileForVersion .dragSucess').hide();
        $('.dragSucess ul li').remove();
        return false;
    });

    $('#selectFileForVersion .btnCancel').live('click',function(){
        modalClose();
        return false;
    });

    var uploadTimer = 20000;
    var uploadTimerCT;
    var uploadSizeCT;
    var uploadProcessCT;
    var remainTime;

    $('#selectFileForVersion .btnUpload').live('click',function(){
        modalLoad('#uploadingFileVersion');
        //Int Upload
        var uploadSize = 0;
        remainTime = uploadTimer;
        function setWidth(){
            $('#uploadingFileVersion .processBarInner').animate({
                width: '100%'
            },uploadTimer);
        }
        $('#uploadingFileVersion .processBarInner').css({
            width: '0'
        });
        $('#uploadingFileVersion h2').text('Uploading file');
        $('#uploadingFileVersion .ramainTime').text('20 seconds left');
        $('#uploadingFileVersion .fileSize span').text('0');
        setWidth();
        if(uploadTimerCT){
            clearInterval(uploadTimerCT);
        }
        if(uploadSizeCT){
            clearInterval(uploadSizeCT);
        }
        uploadTimerCT = setInterval(function(){
            remainTime = remainTime - 1000;
            iSeconds = remainTime/1000%60;
            if(iSeconds > 0){
                $('#uploadingFileVersion .ramainTime').text(iSeconds+' seconds left');
            }else{
                $('#uploadingFileVersion .ramainTime').text('0 seconds left');
            }
        },1000);
        uploadSizeCT = setInterval(function(){
            uploadSize = uploadSize + 0.08
            if(uploadSize > 1.5){
                $('#uploadingFileVersion .fileSize span').text('1.5');
            }else{
                $('#uploadingFileVersion .fileSize span').text(Math.round((uploadSize)*10)/10);
            }
        },1000);
        uploadCT = setTimeout(function(){
            clearInterval(uploadTimerCT);
            clearInterval(uploadSizeCT);
            $('#uploadingFileVersion h2').text('File upload succeeded');
            $('#uploadingFileVersion .ramainTime').text('Finished');
            uploadRedirectCT = setTimeout(function(){
                modalPreloader('#uploadFinish');
            },2000);
        },uploadTimer);
        return false;
    });

    $('#uploadingFileVersion .closeModal').live('click',function(){
        modalClose();
        clearTimeout(uploadCT);
        $("#uploadingFileVersion .processBarInner").stop(true);
        return false;
    });

    $('#uploadingFileVersion .btnCancelUpload').live('click',function(){
        modalLoad('#uploadCancel');
        clearTimeout(uploadCT);
        $("#uploadingFileVersion .processBarInner").stop();
        return false;
    });

    $('#uploadCancel .btnContinue').live('click',function(){
        modalLoad('#uploadingFileVersion');
        function setWidth(){
            $('#uploadingFileVersion .processBarInner').animate({
                width: '100%'
            },remainTime);
        }
        setWidth();
        uploadCT = setTimeout(function(){
            clearInterval(uploadSizeCT);
            clearInterval(uploadProcessCT);
            $('#uploadingFileVersion h2').text('File upload succeeded');
            $('#uploadingFileVersion .ramainTime').text('Finished');
            uploadRedirectCT = setTimeout(function(){
                modalPreloader('#uploadFinish');
            },2000);
        },remainTime);
        return false;
    });

    $('#uploadCancel .closeModal,#uploadCancel .btnUploapCancel').live('click',function(){
        modalClose();
        clearTimeout(uploadCT);
        $("#uploadingFileVersion .processBarInner").stop(true)
        return false;
    });

    $('#uploadFinish .btnNoUpload').live('click',function(){
        modalClose();
        return false;
    });

    $('#uploadFinish .btnSaveDetails').live('click',function(){
        modalLoad('#savingModal');
        setTimeout(function(){
            window.location.href = 'asset.html';
            return false;
        },intPreloaderTimmer);
    });

    //Image View Modal
    $('.imageView').live('click',function(){
        modalPreloader('#imageViewModal');
        setTimeout(function(){
            var wWidth  = window.innerWidth;
            var wHeight = window.innerHeight;
            if (wWidth==undefined) {
                wWidth  = document.documentElement.clientWidth;
            }
            if (wHeight==undefined) {
                wHeight  = document.documentElement.clientHeight;
            }
            var boxTop = parseInt((wHeight / 2) - ( $("#imageViewModal").width() / 2 ));
            var boxLeft = parseInt((wWidth / 2) - ( $("#imageViewModal").width() / 2 ));
            if(boxTop<0){
                boxTop = 0;
            }
            $('#imageViewModal').css({
                top: boxTop+(document.documentElement.scrollTop || document.body.scrollTop),
                left: boxLeft
            });
        },intPreloaderTimmer);
    });

    $('#imageViewModal .viewFullSize').live('click',function(){
        window.open('../images/larger_submission_1.png');
    });

    $('#imageViewModal .closeModal').live('click',function(){
        modalClose();
        $('#imageViewModal').hide();
        return false;
    });

    //Image Version View Modal
    $('.imageVersion').live('click',function(){
        modalPreloader('#ImageVersionViewModal');
        setTimeout(function(){
            var wWidth  = window.innerWidth;
            var wHeight = window.innerHeight;
            if (wWidth==undefined) {
                wWidth  = document.documentElement.clientWidth;
            }
            if (wHeight==undefined) {
                wHeight  = document.documentElement.clientHeight;
            }
            var boxTop = parseInt((wHeight / 2) - ( $("#ImageVersionViewModal").width() / 2 ));
            var boxLeft = parseInt((wWidth / 2) - ( $("#ImageVersionViewModal").width() / 2 ));
            if(boxTop<0){
                boxTop = 0;
            }
            $('#ImageVersionViewModal').css({
                top: boxTop+(document.documentElement.scrollTop || document.body.scrollTop),
                left: boxLeft
            });
        },intPreloaderTimmer);
    });

    $('#ImageVersionViewModal .closeModal').live('click',function(){
        modalClose();
        $('#ImageVersionViewModal').hide();
        return false;
    });

    var fImageURL = '/images/larger_submission_1.png';
    $('#ImageVersionViewModal .navigation li').live('click',function(){
        var idx = $('#ImageVersionViewModal .navigation li').index($(this));
        $('#ImageVersionViewModal .navigation li').removeClass('currentVersion');
        $(this).addClass('currentVersion');
        $('#ImageVersionViewModal .viewSection li').hide();
        $('#ImageVersionViewModal .viewSection li').eq(idx).show();
        fImageURL = $('#ImageVersionViewModal .viewSection li img').eq(idx).attr('src');
    });

    $('#ImageVersionViewModal .viewFullSize').live('click',function(){
        window.open(fImageURL);
    });

    $(window).resize(function(){
        var wWidth  = window.innerWidth;
        if (wWidth==undefined) {
            wWidth  = document.documentElement.clientWidth;
        }
        var boxLeft = parseInt((wWidth / 2) - ( $("#imageViewModal").width() / 2 ));
        $('#imageViewModal').css({
            left: boxLeft
        });
        $('#ImageVersionViewModal').css({
            left: boxLeft
        });
        fnSetVersionViewHeight();
        fnSetHarrow();
        fnSetUploadItem();
        fnSetFilterWidth();
    });

    //More Txt
    $('td.fileDetailsCell .fileDetails p a').live('click',function(){
        $(this).hide();
        $(this).parents('p').find('.ellipsis').hide();
        $(this).parents('p').find('.moreText').show();
    });

    fnSetUploadItem()
    function fnSetUploadItem(){
        if($('.itemSection').length){
            $('.itemSection').each(function(){
                $(this).css({
                    width: ($(this).parents('.fileUploadingSection').width()-70)/3
                });
            });
            if($.browser.msie && $.browser.version == 7.0){
                $('.itemSection').each(function(){
                    $(this).css({
                        width: ($(this).parents('.fileUploadingSection').width()-60)/3
                    });
                });
            }
        }
    }

    fnSetFilterWidth();
    function fnSetFilterWidth(){
        if($('#filterPanelForm').length){
            $('#filterPanelForm .filterContent .columnDateRange').each(function(){
                $(this).css({
                    width: $(this).parents('.inner').width() - 	$(this).parents('.inner').find('.columnCategory').width() - $(this).parents('.inner').find('.columnUploader').width() - $(this).parents('.inner').width()*0.04
                });
            });
        }
    }

    // single asset file upload handler
    var singleAssetUploader =
        new AjaxUpload(null, {
            action: ctx + '/uploadAssetFile',
            name: 'uploadFile',
            responseType: 'json',
            onSubmit: function (file, ext) {

                // set the form data for ajax upload form
                singleAssetUploader.setData(
                    {
                        uploadFileName: file,
                        uploadFileProjectId: $("#assetProjectId").val()
                    }
                );

                modalPreloader();
            },
            onComplete: function (file, jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        modalAllClose();
                        $("#beforeUpload").hide();
                        $("#uploading").show();
                        populateUploadItem($(".itemSection-temp"), result);
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                        modalClose();
                    });
            }
        }, false);

    $(".singleUploadButton").live('click', function () {
        singleAssetUploader._input = $("input[name=uploadFile]").get(0);

        singleAssetUploader.submit();
        if ($(".uploadInputsSection input[name=uploadFile]").length == 0) {
            $(".uploadInputsSection").append('<input type="file" name="uploadFile"/>');
        }
    });
});

function populateUploadItem(item, json) {
    item.find("p.fileName").text(json.fileName);
    item.find("span.size").text(json.fileSizeDisplay);
    item.find("span.uploader").text(json.uploader);
    item.find("p.uploadTime").text(json.uploadTime + ' EDT');
    item.data("uploadSessionKey", json.uploadSessionKey);
    item.find(".icon-file img").attr('src', '/images/icon-' + json.uploadFileTypeIcon + '.png').attr('alt',
        json.uploadFileTypeIcon);
}