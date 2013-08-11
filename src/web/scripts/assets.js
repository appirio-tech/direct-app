/**
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for project assets pages.
 *
 *  Version 1.0 - Release Assembly - TopCoder Cockpit Asset View And Basic Upload
 *  - Added this file and setup the codes for basic upload and assets view display - Group by date and category of current version.
 *
 *  Version 1.1 - Release Assembly - TopCoder Cockpit Asset View And File Version
 *  - Added upload new version
 *  - Added remove asset / asset view
 *  - Added edit asset / asset version
 *
 *  Version 1.2 - (Release Assembly - TopCoder Cockpit Asset View Release 3)
 *  - Adds batch edit
 *  - Adds batch download
 *  - Adds batch edit permission
 *  - Adds batch remove
 *  - Adds drag and drop support
 *  - Adds multiple files upload support
 *
 * @author GreatKevin, GreatKevin, TCSASSEMBLER
 * @version 1.2
 */
$(document).ready(function(){

    if ($.browser.msie) {
        $("#multipleUploadArea").remove();
        $("#singleUploadArea").show();
    } else {
        $("#singleUploadArea").remove();
        $("#multipleUploadArea").show();
    }

    $('.hrefBlank').live('click',function(){
        var sHref = $(this).attr('href');
        window.open(sHref);
        return false;
    });

    $(':input').live('focus',function(){
        $(this).attr('autocomplete', 'off');
    });

    // adjust the height of tr of versionView
    if($(".versionView").length && $("#fileVersionData tbody tr").length <= 3) {
        var totalHeight = $(".versionView").height();
        var theadHeight = $("#fileVersionData thead").height();

        $("#fileVersionData tbody tr").height((totalHeight - theadHeight) / $("#fileVersionData tbody tr").length);
    }

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
            "sLengthMenu": sStdMenu + "per page",
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
            "sLengthMenu": sStdMenu + "per page",
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
    $('.managementCell .remove').live('click', function () {

        modalLoad('#removeFileFromCockpit');

        var editRow = $(this).parents("tr");

        var assetVersionIdToRemove = editRow.find("input[name=assetVersionId]").val();
        var assetIdToRemove = editRow.find("input[name=assetId]").val();


        $('#removeFileFromCockpit').data("assetVersionIdToRemove", assetVersionIdToRemove);
        $('#removeFileFromCockpit').data("assetIdToRemove", assetIdToRemove);

        var assetPage = true;

        if ($('.versionViewDetails').length) {
            assetPage = false;
        }

        //Get File Size
        $('#removeFileFromCockpit .fileDetails li span').text($(this).parents('tr').find('.fileDetails .size').text());

        if ($('.versionViewDetails').length) {

            //GET File Name
            $('#removeFileFromCockpit .fileName').text($('.versionViewDetails .fileName').text()).attr('href', 'downloadAsset?assetId=' +
                editRow.find("input[name=assetId]").val() + '&assetVersionId='
                + editRow.find("input[name=assetVersionId]").val());

            //Get File Version
            $('#removeFileFromCockpit .fileDetails .version').text(editRow.find('.version').text());

            $("#removeFileFromCockpit .radioRow:eq(0) label").text("This version only (" + editRow.find('.version').text() + ")");

            // Get File Size
            $('#removeFileFromCockpit li span').text(editRow.find('span.size').text());

            //Set File Icon
            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('src', eSrc);
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('alt', '');
            if ($('.versionViewDetails .icon-file').hasClass('imageView')) {
                $('#removeFileFromCockpit .fileDetails .icon-file').addClass('imageView');
                $('#removeFileFromCockpit .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor', 'pointer');
            } else {
                $('#removeFileFromCockpit .fileDetails .icon-file').removeClass('imageView');
                $('#removeFileFromCockpit .fileDetails .iconView').remove();
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor', 'default');
            }

            // set file description
            $('#removeFileFromCockpit .description').text(editRow.find(".fileNameDes").text());

            // set file category
            $("#removeFileFromCockpit .fileCategory").text($('.versionViewDetails .category').text());

            // set uploader
            $("#removeFileFromCockpit .uploadBy a").remove();
            $("#removeFileFromCockpit .uploadBy").append(editRow.find("td.uploaderCell ").html());

            // set upload time
            $("#removeFileFromCockpit .date").text(editRow.find("td.dateCell").text());

        } else {
            //Get File Name
            $('#removeFileFromCockpit .fileDetails .fileName').text(editRow.find('.fileDetails .first a').text()).attr('href',
                editRow.find("td.fileDetailsCell .fileDetails li.first a").attr('href'));

            //Get File Version
            $('#removeFileFromCockpit .fileDetails .version').text(editRow.find('.fileDetails .version').text());
            $("#removeFileFromCockpit .radioRow:eq(0) label").text("This version only (" + editRow.find('.fileDetails .version').text() + ")");
            $('#removeFileFromCockpit .fileDetails .version').removeClass('imageVersion');

            if (editRow.find('.fileDetails .version').hasClass('modifyVersion')) {
                $('#removeFileFromCockpit .fileDetails .version').addClass('modifyVersion');
            } else {
                $('#removeFileFromCockpit .fileDetails .version').removeClass('modifyVersion');
            }

            //Set File Icon
            var eSrc = $(this).parents('tr').find('.icon-file-small img').attr('src');
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('src', eSrc.replace('-small', ''));
            $('#removeFileFromCockpit .fileDetails .icon-file img').attr('alt', '');
            if ($(this).parents('tr').find('.icon-file-small').hasClass('imageView')) {
                $('#removeFileFromCockpit .fileDetails .icon-file').addClass('imageView');
                $('#removeFileFromCockpit .fileDetails .icon-file img').after('<span class="iconView" />');
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor', 'pointer');
            } else {
                $('#removeFileFromCockpit .fileDetails .icon-file').removeClass('imageView');
                $('#removeFileFromCockpit .fileDetails .iconView').remove();
                $('#removeFileFromCockpit .fileDetails .icon-file img').css('cursor', 'default');
            }

            // set file description
            $('#removeFileFromCockpit .description').text(editRow.find("span.trimmedText").text());

            // set file category
            $("#removeFileFromCockpit .fileCategory").text(editRow.find(".categoryCell strong").text());

            // set uploader
            $("#removeFileFromCockpit .uploadBy a").remove();
            $("#removeFileFromCockpit .uploadBy").append(editRow.find("td.uploaderCell ").html());

            // set upload time
            $("#removeFileFromCockpit .date").text(editRow.find("td.dateCell").text());
        }
        return false;
    });


    $('#removeFileFromCockpit .btnCancel').live('click',function(){
        modalClose();
        return false;
    });


    $('#removeFileFromCockpit .btnYes').live('click',function(){

        modalPreloader();

        var request;
        var path;


        if($("#removeFileFromCockpit .radioRow:eq(1) input").is(":checked")) {
            // delete all
            request = {assetId: $('#removeFileFromCockpit').data("assetIdToRemove")};
            path = "deleteAsset";
        } else {
            request = {assetVersionIds: [$('#removeFileFromCockpit').data("assetVersionIdToRemove")]};
            path = "deleteAssetVersion";
        }

        $.ajax({
            type: 'POST',
            url: ctx + "/" + path,
            data: request,
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;
                        window.open('./projectAssets?formData.projectId=' + $("#assetProjectId").val(), '_self');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

    });

    $(document).bind('drop dragover', function (e) {
        e.preventDefault();
    });

    var fileUploadExtraForm = {uploadFileProjectId: $("#assetProjectId").val()};
    var totalNumberOfFiles = 0, totalNumberOfFilesSubmitted = 0;
    var dataFiles = [];
    if ($('#multipleUploadArea').length) {
        var totalNum = 0;
        var totalSize = 0;
        var processedFileCount = 0;
        var jqXHR = $('#fileDropInput').fileupload({
            url: 'uploadAssetFile',
            dataType: 'json',
            dropZone: $('#multipleUploadArea .dragDropUpload'),
            formData: fileUploadExtraForm,
            add: function (e, data) {

                $('.dragPre').hide();
                $('.dragSucess').show();
                // total number
                $('.dragSucess p span').text(data.files.length);
                // each file
                $.each(data.files, function (index, file) {
                    var eFileStyle = (/\.[^\.]+$/.exec(file.name).toString().replace('.', ''));
                    var iSize = fnFormatSize(file.size);
                    switch (eFileStyle) {
                        case 'zip' :
                            eFileStyle = 'zip';
                            break;
                        case 'rar' :
                            eFileStyle = 'rar';
                            break;
                        case '7z' :
                            eFileStyle = 'zip';
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
                    if ($("#selectFileForVersion").is(":visible")) {
                        if (file.name.length > 20) {
                            eFile = file.name.substr(0, 20) + '...';
                        } else {
                            eFile = file.name;
                        }
                    } else {
                        if (file.name.length > 30) {
                            eFile = file.name.substr(20, 20) + '...';
                        } else {
                            eFile = file.name;
                        }
                    }
                    var newUploadFileRow;
                    if ($("#selectFileForVersion").is(":visible")) {
                        $('.dragSucess ul').empty();
                        newUploadFileRow = $('<li><a href="javascript:;" class="removeFile">X</a><span class="icon-file-small"><img src="/images/icon-small-'
                            + eFileStyle + '.png" alt="" /></span><div class="fileName">' + eFile + '<span> (' + iSize + ')</span></div></li>');
                        $('.dragSucess ul').append(newUploadFileRow);
                        // add file info
                        data.context = $('#uploadingFileVersion .uploadingFileSection');
                        data.context.find('.fileName .name').text(eFile);
                        data.context.find('.fileName .size').text(iSize);
                        data.context.find('.fileSize .size').text(iSize);
                        data.context.find('.icon-file-small img').attr({'src': '/images/icon-small-' + eFileStyle + '.png', 'alt': eFileStyle});
                        totalNumberOfFiles++;
                    } else {
                        newUploadFileRow = $('<li><a href="javascript:;" class="removeFile">X</a><span class="icon-file-small"><img src="/images/icon-small-'
                            + eFileStyle + '.png" alt="" /></span><div class="fileName">' + eFile + '<span> (' + iSize + ')</span></div></li>');
                        // add item to uploading area
                        $('.dragSucess ul').append(newUploadFileRow);
                        $('.dragSucess p span').text($('#beforeUpload .dragSucess li').length);
                        fnSetHeight();
                        totalNumberOfFiles++
                    }
                    dataFiles.push(data.files);
                });
                // remove file
                $('.removeFile').die('click');
                $('.removeFile').live('click', function () {
                    var li = $(this).parent();
                    var index = $(this).closest('ul').children('li').index(li);
                    li.remove();
                    dataFiles[index].splice(0,1);
                    //data.files.splice(index, 1);
                    $('.dragSucess p span').text($('#beforeUpload .dragSucess li').length);
                    totalNumberOfFiles = $('#beforeUpload .dragSucess li').length;
                });
                // upload button
                $('#fileDropInputSubmit').click(function () {
                    data.submit();
                });
            },
            submit: function (e, data) {
                if ($("#selectFileForVersion").is(":visible")) {
                    modalPreloader();
                    $("#selectFileForVersion").data('upload', true);
                } else {
                    // upldate total file info
                    totalNum = totalNum + data.files.length;
                    $.each(data.files, function (index, file) {
                        totalNumberOfFilesSubmitted++;
                        totalSize = totalSize + file.size;
                        var eFile;
                        if ($("#selectFileForVersion").is(":visible")) {
                            if (file.name.length > 20) {
                                eFile = file.name.substr(0, 20) + '...';
                            } else {
                                eFile = file.name;
                            }
                        } else {
                            if (file.name.length > 30) {
                                eFile = file.name.substr(20, 20) + '...';
                            } else {
                                eFile = file.name;
                            }
                        }
                        // add file info
                        var uploadingNew = $('.itemSection-temp').clone(true).removeClass('itemSection-temp');
                        var eFileStyle = (/\.[^\.]+$/.exec(file.name).toString().replace('.', ''));
                        var iSize = fnFormatSize(file.size);
                        switch (eFileStyle) {
                            case 'zip' :
                                eFileStyle = 'zip';
                                break;
                            case 'rar' :
                                eFileStyle = 'rar';
                                break;
                            case '7z' :
                                eFileStyle = 'zip';
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
                        var count = $('#beforeUpload .dragSucess li').length;
                        uploadingNew.find("input[type=radio]").attr('name', 'accessRadio' + processedFileCount);
                        uploadingNew.find("input[type=radio]:eq(0)").attr('id', 'accessRadioPublic' + processedFileCount);
                        uploadingNew.find(".radioBox label:eq(0)").attr('for', 'accessRadioPublic' + processedFileCount);
                        uploadingNew.find("input[type=radio]:eq(1)").attr('id', 'accessRadioPrivate' + processedFileCount);
                        uploadingNew.find(".radioBox label:eq(1)").attr('for', 'accessRadioPrivate' + processedFileCount);
                        uploadingNew.find('.fileName').text(eFile).attr('title', file.name);
                        uploadingNew.find('.size').text(iSize);
                        uploadingNew.find('.icon-file img').attr({'src': '/images/icon-' + eFileStyle + '.png', 'alt': eFileStyle});
                        data.context = uploadingNew.appendTo($('.fileUploadingSection .sectionInner'));
                        processedFileCount++;
                    });
                    $('#uploading .fileTotalNumber').text(totalNum);
                    $('#uploading .fileTotalSize').text(fnFormatSize(totalSize));
                    $("#beforeUpload").hide();
                    $("#uploading").show();
                    if (totalNumberOfFilesSubmitted == totalNumberOfFiles) {
                        $('.itemSection-temp').remove();
                        totalNumberOfFiles = totalNumberOfFilesSubmitted = 0;
                    }
                }
                if (!data.files || !data.files.length) {
                    return false;
                }
            },
            start: function (e) {
                $('#uploadFiles').hide();
                $('#uploading').show();
                fnSetUploadItem();
            },
            progress: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                var timeLeft = parseInt((data.total - data.loaded) / data.bitrate);
                if ($("#selectFileForVersion").is(":visible")) {
                    data.context.find('.fileSize span').text(fnFormatSize(data.loaded));
                } else {
                    data.context.find('.fileSize').text(fnFormatSize(data.loaded));
                }
                data.context.find('.processTime').text(timeLeft);
                data.context.find('.processed').css(
                    'width',
                    progress + '%'
                );
            },
            progressall: function (e, data) {
                if (!$("#selectFileForVersion").is(":visible")) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    var timeLeft = parseInt((data.total - data.loaded) / data.bitrate);
                    $('.allFileProcessBar .processTime').text(timeLeft);
                    $('.allFileProcessBar .processed').css(
                        'width',
                        progress + '%'
                    );
                }
            },
            done: function (e, data) {
                if ($("#selectFileForVersion").data('upload') == true) {
                    var responseData = data.result.result['return'];
                    populateUploadNewVersion($("#uploadFinish"), responseData);
                    modalLoad("#uploadFinish");
                    $("#selectFileForVersion").removeData('upload');
                } else {
                    data.context.find('.uploading').hide();
                    data.context.find('.uploadSucess').show();
                    data.context.find('.noFlyout').removeClass('noFlyout');
                    var responseData = data.result.result['return'];
                    populateUploadItem(data.context, responseData);
                }
            },
            stop: function (e, data) {
                if ($("#selectFileForVersion").is(":visible")) {
                    modalPreloader('#uploadFinish');
                } else {
                    $('.sectionHeader').find('.uploading').hide();
                    $('.sectionHeader').find('.uploadSucess').show();
                }
            }
        });
        // upload button
        $('.btnUpload').click(function () {

            // choose one to upload if it is version upload
            if (!$("#selectFileForVersion").is(":visible") || ($('.dragSucess').get(0).clientHeight == 0 && $('.dragSucess li').length == 0 )) {
                // add files from common upload
                $('.commonUpload input').each(function () {
                    if ($(this).val() != '') {
                        $('#fileDropInput').fileupload('add', {
                            fileInput: $(this)
                        });
                    }
                });
            }
            // submit together
            $('#fileDropInputSubmit').click();
        });
    }

    function fnFormatSize(size) {
        if (Math.round(size / 1000 * 100) / 100 > 1 && Math.round(size / 1000 * 100) / 100 < 1000) {
            iSize = Math.round(size / 1000 * 100) / 100 + ' KB';
        } else if (Math.round(size / 1000 * 100) / 100 > 1000) {
            iSize = Math.round(size / 1000 / 1000 * 100) / 100 + ' MB';
        } else {
            iSize = size + ' Byte';
        }
        return iSize;
    }

    //Set Upload Container
    function fnSetHeight(){
        $("div.uploadContainer").each(function () {
            var container = $(this);
            if (container.find(".dragDropUpload").length && container.find(".commonUpload").length) {
                if (container.find(".dragDropUpload").height() >= container.find(".commonUpload").height()) {
                    container.css('height', container.find(".dragDropUpload").height());
                } else {
                    container.css('height', container.find(".commonUpload").height());
                }
            }

        });
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

        var newCategoryName = $(".popUpNewCategory:visible input[type=text]").val();

        if(!newCategoryName) {
            alert('The file category name should not be empty');
            return;
        }

        var _this = $(this);

        $.ajax({
            type: 'POST',
            url: ctx + "/addNewCategory",
            data: {category:{name:newCategoryName},formData:{projectId:$("#assetProjectId").val()}},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult2(jsonResult,
                    function (result) {
                        $('.selectCategory').find('select').append($("<option/>").text(result.name).val(result.id));
                        _this.parents('.selectCategory').find('input:text').val('');
                        _this.parents('.selectCategory').find('select').val(result.id)
                        $('.popUpNewCategory').hide();
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });

    // Select Access
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

    // private access setting submit
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

    //View private Access setting flyout
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
    $('#uploading .fileUploadItem .removeFileUploadItem').live('click', function () {
        $(this).parents('.itemSection').remove();
        $('.sectionHeader .fileTotalNumber').text($('.itemSection').length);
        $('.sectionHeader .fileTotalSize').text($('.itemSection').length * 1.5);
        if (!$('#uploading .itemSection').length) {
            window.onbeforeunload = null;
            window.open('./projectAssets?formData.projectId=' + $("#assetProjectId").val(), '_self');
        }
    });

    $('#bitchEditSection .fileUploadItem .removeFileUploadItem').live('click',function(){
        $(this).parents('.itemSection').remove();
        if(!$('#bitchEditSection .itemSection').length){
            window.location.href = 'asset.html';
        }
    });

    $('.uploadSection .btnSaveDetails').live('click',function(){
        var validationErrors = [];

        var saveAssets = [];

        $("#processing .sectionInner .itemSection").each(function () {
            var fileName = $(this).find("p.fileName").attr('title');
            // do the validation first
            if ($(this).find(".selectCategory select").val() < 0) {
                validationErrors.push("Please select the asset category for " + fileName);
            }

            if ($.trim($(this).find(".addDescription textarea").val()).length == 0) {
                validationErrors.push("Please enter the description for the file " + fileName);
            }

            saveAssets.push({
                assetCategoryId: $(this).find(".selectCategory select").val(),
                assetDescription: $(this).find(".addDescription textarea").val(),
                assetPublic: $(this).find(".radioBox input[type=radio]:eq(0)").is(":checked"),
                privateUserIds: $(this).find(".popUpPrivateAccessSection").find("input[type=checkbox].assetUserPermission:checked").map(function () {
                    return $(this).attr('id');
                }).get(),
                sessionKey: $(this).data("uploadSessionKey")
            });
        });


        if (validationErrors.length > 0) {
            showErrors(validationErrors);
            return;
        }


        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/saveAssetFile",
            data: {assets:saveAssets},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;
                        window.open('./projectAssets?formData.projectId=' + result[0].directProjectId, '_self');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

    });

	oFileVersion = $('#fileVersionData').dataTable({
		"aoColumnDefs": [{"bSortable":false,"aTargets":[3]}],
        "aaSorting": [[2, 'desc']],
		"bPaginate": false,
        "bLengthChange": false,
        "bFilter": false,
        "bInfo": false,
        "bAutoWidth": false
    });

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

        // update file icon
        $('.versionViewDetails .icon-file').html('').append($(this).find("img.fileTypeIcon").clone());
        $('.versionViewDetails .icon-file img').removeClass('hide');

        // update version
        $('.versionViewDetails .version').text($(this).find('.version').text());

        // update size
        $('.versionViewDetails .versionSize').text($(this).find('.size').text());

        // update file description
        $('.versionViewDetails .fileNameDes').text($(this).find('.fileNameDes').text());

        // update uploader
        $('.versionViewDetails .uploaderName').html($(this).find('.uploaderCell ').html());

        // update upload time
        $('.versionViewDetails .time').html($(this).find('.timeCell .time ').text());

        // update category
        $('.versionViewDetails .cateogry').text($(this).find("input[name=assetCategory]").val());

        // update file name
        $(".versionViewDetails .fileName").text($(this).find("input[name=fileName]").val()).attr('href', $(this).find('.actionMenuBody li:eq(2) a').attr('href'));

        if ($(this).find("input[name=latestId]").val() != $(this).find("input[name=assetVersionId]").val()) {
            $(".versionViewDetails .icon-lastest").hide();
        } else {
            $(".versionViewDetails .icon-lastest").show();
        }

        fnSetHarrow();
    });

    if($(".versionView").length > 0) {
        $(".versionView .versionViewTable tbody tr.selected").trigger('click');
    }

    //Batch Delete
    $('.batchOperation .delete').live('click',function(){
        var checkedRows = $("table.projectStats:visible tr input[type=checkbox]:checked");
        $("#batchDelete .fileList ul li:gt(0)").remove();
        $("#batchDelete span.listFileNumber").text(checkedRows.length);
        var assetVersionIdsToDelete = [];
        checkedRows.each(function(){
            var row = $(this).parents("tr");
            var item = $("#batchDelete .fileList li.item-temp").clone(true).removeClass("item-temp").removeClass("hide");
            item.find(".icon-file-small img").attr("src", row.find("a.icon-file-small img").attr('src'));
            item.find(".fileName").text(row.find(".fileDetails li.first a").text());
            item.find(".size").text("(" + row.find(".fileDetails li:last span").text() + ")");
            assetVersionIdsToDelete.push(row.find("input[name=assetVersionId]").val());
            item.appendTo("#batchDelete .fileList ul");
        });

        $("#batchDelete").data("assetVersionIdsToDelete", assetVersionIdsToDelete);

        modalLoad('#batchDelete');
        return false;
    });

    $('#batchDelete .btnCloseModal').live('click',function(){
        modalClose();
        return false;
    });

    $('#batchDelete .btnSucess').live('click', function () {
        modalLoad('#deletingModal');

        $.ajax({
            type: 'POST',
            url: ctx + "/" + "deleteAssetVersion",
            data: {assetVersionIds: $("#batchDelete").data("assetVersionIdsToDelete")},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;
                        window.open('./projectAssets?formData.projectId=' + $("#assetProjectId").val(), '_self');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });

    // Batch Edit
    $('.batchOperation .edit').live('click',function(){
        var checkedRows = $("table.projectStats:visible tr input[type=checkbox]:checked");
        var assetIdsToEdit = [];
        checkedRows.each(function(){
            var row = $(this).parents("tr");
            assetIdsToEdit.push(row.find("input[name=assetId]").val());
        });
        window.onbeforeunload = null;
        window.open('./batchEditAssets?' + $.param({assetIds:assetIdsToEdit, formData:{projectId: $("#assetProjectId").val()}}), '_self');
    });

    // Batch Download

    $('.batchOperation .download').live('click',function(){
        modalPreloader();
        var checkedRows = $("table.projectStats:visible tr input[type=checkbox]:checked");
        $("#batchDownload .fileList ul li:gt(0)").remove();
        $("#batchDownload span.listFileNumber").text(checkedRows.length);
        var assetVersionIdsToDownload = [];
        checkedRows.each(function(){
            var row = $(this).parents("tr");
            var item = $("#batchDownload .fileList li.item-temp").clone(true).removeClass("item-temp").removeClass("hide");
            item.find(".icon-file-small img").attr("src", row.find("a.icon-file-small img").attr('src'));
            item.find(".fileName").text(row.find(".fileDetails li.first a").text());
            item.find(".size").text("(" + row.find(".fileDetails li:last span").text() + ")");
            assetVersionIdsToDownload.push(row.find("input[name=assetVersionId]").val());
            item.appendTo("#batchDownload .fileList ul");
        });

        $.ajax({
            type: 'POST',
            url: ctx + "/" + "getAssetsZip",
            data: {assetVersionIds: assetVersionIdsToDownload, uploadFileProjectId:$("#assetProjectId").val()},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        modalLoad('#batchDownload');
                        $('#batchDownload').find(".fileDdownloadInfor span.filaName").html(
                            result.fileName + " <span>(" + result.fileSizeDisplay + ")</span>");
                        $('#batchDownload').find(".downloadedButtons a").attr('href', 'downloadAssets?sessionKey=' + result.uploadSessionKey);
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });
    });

    $('#batchDownload .btnCancelDownload, #batchDownload .btnDownload').live('click',function(){
        modalClose();
    });

    // Batch Set Permission
    $('.batchOperation .setPermission').live('click', function () {
        var checkedRows = $("table.projectStats:visible tr input[type=checkbox]:checked");
        $("#batchSetPermission .fileList ul li:gt(0)").remove();
        $("#batchSetPermission span.listFileNumber").text(checkedRows.length);
        var assetIdsToUpdate = [];
        checkedRows.each(function(){
            var row = $(this).parents("tr");
            var item = $("#batchDelete .fileList li.item-temp").clone(true).removeClass("item-temp").removeClass("hide");
            item.find(".icon-file-small img").attr("src", row.find("a.icon-file-small img").attr('src'));
            item.find(".fileName").text(row.find(".fileDetails li.first a").text());
            item.find(".size").text("(" + row.find(".fileDetails li:last span").text() + ")");
            assetIdsToUpdate.push(row.find("input[name=assetId]").val());
            item.appendTo("#batchSetPermission .fileList ul");
        });

        $("#batchSetPermission").data("assetIdsToUpdate", assetIdsToUpdate);

        modalLoad('#batchSetPermission');
        $('#batchSetPermission .accessOperate').css('visibility', 'hidden');
        $('#batchSetPermission .popUpPrivateAccess input:checkbox').attr('checked', false);
        $('#batchSetPermission .radioBox input#radioModalPublic').attr('checked', true);
        $('#batchSetPermission .radioBox input#radioModalPrivate').removeClass('selected');

        return false;
    });

    $('#batchSetPermission .btnDiscard').live('click',function(){
        modalClose();
        return false;
    });

    $('#batchSetPermission .btnSaveChanges').live('click',function(){


        var assetsPermissionUpdateRequest = {
            assetIds: $("#batchSetPermission").data("assetIdsToUpdate"),
            assetPublic: $("#batchSetPermission input.public").is(":checked"),
            privateUserIds: $("#batchSetPermission .popUpPrivateAccessSection").find("input[type=checkbox].assetUserPermission:checked").map(function () {
                return $(this).attr('id');
            }).get()
        };

        $.ajax({
            type: 'POST',
            url: ctx + "/" + "updateAssetsPermission",
            data: assetsPermissionUpdateRequest,
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;
                        window.open('./projectAssets?formData.projectId=' + $("#assetProjectId").val(), '_self');
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

        return false;
    });

    //Upload File Details
    $('.managementCell .edit, .versionViewDetails a.editVersion').live('click', function () {

        modalPreloader();

        var editRow = $(this).parents("tr");
        var assetPage = true;

        if($('.versionViewDetails').length) {
            assetPage = false;
            editRow = $("#fileVersionData tbody tr.selected");
            $("#editFileDetails div.selectCategory").hide();
            $("#editFileDetails div.selectAccess").hide();
        } else {
            $("#editFileDetails div.selectCategory").show();
            $("#editFileDetails div.selectAccess").show();
        }


        var assetVersionIdToEdit = editRow.find("input[name=assetVersionId]").val();

        $('#editFileDetails').data('assetPage', assetPage);

        $.ajax({
            type: 'POST',
            url: ctx + "/getAssetVersion",
            data: {assetVersionId:assetVersionIdToEdit},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        modalLoad('#editFileDetails');

                        if(result.isLatest == true) {
                            $('#editFileDetails .versionSection').addClass('lastVersion');
                        } else {
                            $('#editFileDetails .versionSection').removeClass('lastVersion');
                        }

                        // bind asset version id and asset id to this modal
                        $("#editFileDetails").data("assetVersionId", result.id).data("assetId", result.assetId);

                        // set file name
                        $('#editFileDetails .fileDetails .fileName').text(result.fileName)

                        //set File Version
                        $('#editFileDetails .fileDetails .version').text("Version " + result.version);

                        $('#editFileDetails .fileDetails .version').removeClass('imageVersion');

                        // set file size
                        $('#editFileDetails .fileDetails ul li:eq(1) span').text(result.fileSizeDisplay);

                        // set file description
                        $('#editFileDetails .fileDetails .description').text(result.description);
                        $('#editFileDetails .editFileSection textarea').val(result.description);

                        // set file category
                        $("#editFileDetails .fileDetails .fileCategory").text(result.categoryName);
                        $('#editFileDetails .editFileSection .selectCategory select').val(result.categoryId);


                        // set upload time
                        $("#editFileDetails .fileDetails .date").text(result.uploadTime + " EDT");

                        // set permission
                        if (result.isPublic) {
                            $('#editFileDetails .editFileSection .selectAccess input.public').attr("checked",
                                "checked").trigger('click').trigger('change');
                        } else {

                            $.each(result.users, function (index, value) {
                                $('#editFileDetails .editFileSection .selectAccess .popUpPrivateAccess #' + value).attr('checked',
                                    'checked');
                            });

                            $('#editFileDetails .editFileSection .selectAccess a.linkSubmit').trigger('click');
                            $('#editFileDetails .editFileSection .selectAccess input.private ').attr("checked",
                                "checked").trigger('change');

                        }


                        // set uploader
                        $("#editFileDetails .fileDetails .uploadBy a").remove();
                        $("#editFileDetails .fileDetails .uploadBy").append(editRow.find("td.uploaderCell ").html());

                        // asset version view page
                        if ($('.versionViewDetails').length) {

                            // set file download link
                            $('#editFileDetails .fileDetails .fileName').attr('href', 'downloadAsset?assetId=' +
                                editRow.find("input[name=assetId]").val() + '&assetVersionId='
                                + editRow.find("input[name=assetVersionId]").val());

                            //Set File Icon
                            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
                            $('#editFileDetails .icon-file img').attr('src', eSrc);
                            $('#editFileDetails .icon-file img').attr('alt', '');
                            if ($('.versionViewDetails .icon-file').hasClass('imageView')) {
                                $('#editFileDetails .fileDetails .icon-file').addClass('imageView');
                                $('#editFileDetails .fileDetails .icon-file img').after('<span class="iconView" />');
                                $('#editFileDetails .fileDetails .icon-file img').css('cursor', 'pointer');
                            } else {
                                $('#editFileDetails .fileDetails .icon-file').removeClass('imageView');
                                $('#editFileDetails .fileDetails .iconView').remove();
                                $('#editFileDetails .fileDetails .icon-file img').css('cursor', 'default');
                            }

                        } else {
                            // set file download link
                            $('#editFileDetails .fileDetails .fileName').attr('href',
                                editRow.find("td.fileDetailsCell .fileDetails li.first a").attr('href'));

                            if (editRow.find('.fileDetails .version').hasClass('modifyVersion')) {
                                $('#editFileDetails .fileDetails .version').addClass('modifyVersion');
                            } else {
                                $('#editFileDetails .fileDetails .version').removeClass('modifyVersion');
                            }

                            // Set File Icon
                            var eSrc = editRow.find('.icon-file-small img').attr('src');
                            $('#editFileDetails .icon-file img').attr('src', eSrc.replace('-small', ''));
                            $('#editFileDetails .icon-file img').attr('alt', '');
                            if (editRow.find('.icon-file-small').hasClass('imageView')) {
                                $('#editFileDetails .fileDetails .icon-file').addClass('imageView');
                                $('#editFileDetails .fileDetails .icon-file img').after('<span class="iconView" />');
                                $('#editFileDetails .fileDetails .icon-file img').css('cursor', 'pointer');
                            } else {
                                $('#editFileDetails .fileDetails .icon-file').removeClass('imageView');
                                $('#editFileDetails .fileDetails .iconView').remove();
                                $('#editFileDetails .fileDetails .icon-file img').css('cursor', 'default');
                            }
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

    });

    $('.editVersion').live('click',function(){
        modalPreloader('#editFileDetails');
        return false;
    });

    $('#editFileDetails .btnNoUpload').live('click',function(){
        modalClose();
        return false;
    });

    $('#editFileDetails .btnSaveDetails').live('click', function () {

        var editAsset = {
            assetVersionId: $("#editFileDetails").data("assetVersionId"),
            assetCategoryId: $("#editFileDetails .selectCategory select").val(),
            assetDescription: $("#editFileDetails .addDescription textarea").val(),
            assetPublic: $("#editFileDetails input.public").is(":checked"),
            privateUserIds: $(".popUpPrivateAccessSection").find("input[type=checkbox].assetUserPermission:checked").map(function () {
                return $(this).attr('id');
            }).get()
        };


        $.ajax({
            type: 'POST',
            url: ctx + "/editAssetVersion",
            data: {assets:[editAsset]},
            cache: false,
            dataType: 'json',
            success: function (jsonResult) {
                handleJsonResult(jsonResult,
                    function (result) {
                        window.onbeforeunload = null;

                        if ($('#editFileDetails').data('assetPage')) {
                            window.open('./projectAssets?formData.projectId=' + result[0].directProjectId, '_self');
                        } else {
                            window.open('./viewAssetVersions?assetId=' + result[0].assetId, '_self');
                        }
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                    });
            }
        });

    });

    //Upload Version
    $('.managementCell .upload, .sectionHeaderM .btnUploadVersion, .uploadButtonBox .btnUploadVersion').live('click', function () {


        var editRow = $(this).parents("tr");

        if ($('.versionViewDetails').length) {
            $(".versionViewTable tbody tr").each(function(){
                if($(this).find("input[name=isLatest]").val() == 'true') {
                    editRow = $(this);
                }
            });
        }

        modalLoad('#uploadFileVersion');
        //Get File Size
        $('.uploadFileDetails li span').text($(this).parents('tr').find('.fileDetails .size').text());

        if ($('.versionViewDetails').length) {
            //GET File Name
            $('.uploadFileDetails .fileName')
                .text(handleLongFileName(editRow.find("input[name=fileName]").val(), 28))
                .attr('title', editRow.find("input[name=fileName]").val())
                .attr('href', 'downloadAsset?assetId=' +
                editRow.find("input[name=assetId]").val() + '&assetVersionId='
                + editRow.find("input[name=assetVersionId]").val());
            //Get File Version
            var currentVersion = editRow.find('.version').text();
            $('.uploadFileDetails .version').text(currentVersion);
            $("#uploadFinish").data("newVersionNumber",
                    (parseFloat(currentVersion.replace("Version", "")) + 0.1).toFixed(1))
                .data("assetId", editRow.find("input[name=assetId]").val());


            // Get File Size
            $('.uploadFileDetails li span').text(editRow.find('span.size').text());

            //Set File Icon
            var eSrc = $('.versionViewDetails .icon-file img').attr('src');
            $('.uploadFileDetails .icon-file img').attr('src', eSrc);
            $('.uploadFileDetails .icon-file img').attr('alt', '');
            if ($('.versionViewDetails .icon-file').hasClass('imageView')) {
                $('.uploadFileDetails .icon-file').addClass('imageView');
                $('.uploadFileDetails .icon-file img').after('<span class="iconView" />');
                $('.uploadFileDetails .icon-file img').css('cursor', 'pointer');
            } else {
                $('.uploadFileDetails .icon-file').removeClass('imageView');
                $('.uploadFileDetails .iconView').remove();
                $('.uploadFileDetails .icon-file img').css('cursor', 'default');
            }

            // set file description
            var description = editRow.find(".fileNameDes").text();
            $('.uploadFileDetails .description').text(description);
            $("#uploadFinish").data("currentDescription", description);

            // set file category
            $(".uploadFileDetails .fileCategory").text($('.versionViewDetails .category').text());

            // set uploader
            $(".uploadFileDetails .uploadBy a").remove();
            $(".uploadFileDetails .uploadBy").append(editRow.find("td.uploaderCell ").html());

            // set upload time
            $(".uploadFileDetails .date").text(editRow.find("td.dateCell").text());

        } else {
            //GET File Name
            $('.uploadFileDetails .fileName').text(editRow.find('.fileDetails .first a').text())
                .attr('href',
                    editRow.find("td.fileDetailsCell .fileDetails li.first a").attr('href'));;

            //Get File Version
            var currentVersion = editRow.find('.fileDetails .version').text();
            $('.uploadFileDetails .version').text(currentVersion);


            $("#uploadFinish").data("newVersionNumber",
                    (parseFloat(currentVersion.replace("Version", "")) + 0.1).toFixed(1))
                .data("assetId", editRow.find("input[name=assetId]").val());


            $('.uploadFileDetails .version').removeClass('imageVersion');

            if ($(this).parents('tr').find('.fileDetails .version').hasClass('modifyVersion')) {
                $('.uploadFileDetails .version').addClass('modifyVersion');
            } else {
                $('.uploadFileDetails .version').removeClass('modifyVersion');
            }

            // Get File Size
            $('.uploadFileDetails li span').text(editRow.find('.fileDetails .size').text());

            //Set File Icon
            var eSrc = $(this).parents('tr').find('.icon-file-small img').attr('src');
            $('.uploadFileDetails .icon-file img').attr('src', eSrc.replace('-small', ''));
            $('.uploadFileDetails .icon-file img').attr('alt', '');
            if ($(this).parents('tr').find('.icon-file-small').hasClass('imageView')) {
                $('.uploadFileDetails .icon-file').addClass('imageView');
                $('.uploadFileDetails .icon-file img').after('<span class="iconView" />');
                $('.uploadFileDetails .icon-file img').css('cursor', 'pointer');
            } else {
                $('.uploadFileDetails .icon-file').removeClass('imageView');
                $('.uploadFileDetails .iconView').remove();
                $('.uploadFileDetails .icon-file img').css('cursor', 'default');
            }

            // set file description
            var description = editRow.find("span.trimmedText").text();

            if(editRow.find("span.moreText").length > 0) {
                description = editRow.find("span.moreText").text();
            }

            $('.uploadFileDetails .description').text(description);
            $("#uploadFinish").data("currentDescription", description);

            // set file category
            $(".uploadFileDetails .fileCategory").text(editRow.find(".categoryCell strong").text());

            // set uploader
            $(".uploadFileDetails .uploadBy a").remove();
            $(".uploadFileDetails .uploadBy").append(editRow.find("td.uploaderCell ").html());

            // set upload time
            $(".uploadFileDetails .date").text(editRow.find("td.dateCell").text());

        }
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

    var uploadSizeCT;
    var uploadProcessCT;
    var remainTime;

    var newFileVersionUploader =
        new AjaxUpload(null, {
            action: ctx + '/uploadAssetFile',
            name: 'files',
            responseType: 'json',
            onSubmit: function (file, ext) {
                // set the form data for ajax upload form
                newFileVersionUploader.setData(
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
                        populateUploadNewVersion($("#uploadFinish"), result);
                        modalLoad("#uploadFinish");
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                        modalClose();
                    });
            }
        }, false);

    if ($.browser.msie) {
        $('#selectFileForVersion .btnUpload').live('click', function () {
            
            newFileVersionUploader._input = $("#selectFileForVersion input[name=files]").get(0);

            newFileVersionUploader.submit();

            if ($("#selectFileForVersion input[name=files]").length == 0) {
                $("#selectFileForVersion .commonUpload").append('<input type="file" name="files"/>');
            }

            return false;
        });
    }

    $('#uploadingFileVersion .closeModal').live('click',function(){
        modalClose();
        $("#uploadingFileVersion .processBarInner").stop(true);
        return false;
    });

    $('#uploadingFileVersion .btnCancelUpload').live('click',function(){
        modalLoad('#uploadCancel');
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

    $('#uploadFinish .btnSaveDetails,#uploadFinish .btnNoUpload').live('click', function () {
        modalLoad('#savingModal');

        var newVersionAssetRequest = {
            assetDescription: $("#uploadFinish textarea").val(),
            sessionKey:$("#uploadFinish").data("uploadSessionKey")

        };

        if($(this).hasClass('btnNoUpload')) {
            newVersionAssetRequest.assetDescription = $("#uploadFinish").data("currentDescription");
        }

        modalPreloader();

        $.ajax({
            type: 'POST',
            url: ctx + "/saveNewAssetVersion",
            data: {assetId: $("#uploadFinish").data("assetId"), assets:[newVersionAssetRequest]},
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

    //Image View Modal
    $('.imageView').live('click', function () {
        $("#imageViewModal .viewSection img").attr('src',
            './viewAssetImage.action?preview=true&assetVersionId=' + $(this).parents("tr").find("input[name=assetVersionId]").val());
        $("#imageViewModal .imageViewModalFooter a:eq(1)").attr('href',
            './downloadAsset.action?assetVersionId=' + $(this).parents("tr").find("input[name=assetVersionId]").val() + '&assetId=' + $(this).parents("tr").find("input[name=assetId]").val());
        $("#imageViewModal .imageViewModalFooter span.fileName").text($(this).parents("tr").find(".fileDetails li:eq(0) a").text());
        $("#imageViewModal").data('viewFullSize',
            './viewAssetImage.action?preview=false&assetVersionId=' + $(this).parents("tr").find("input[name=assetVersionId]").val());
        modalLoad('#imageViewModal');
    });

    $('#imageViewModal .viewFullSize').live('click',function(){
        window.open($("#imageViewModal").data('viewFullSize'));
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
        fnSetVersionViewHeight();
        fnSetHarrow();
        fnSetUploadItem();
        fnSetFilterWidth();
    });

    //More Txt
    $('td.fileDetailsCell .fileDetails p a').live('click',function(){
        $(this).hide();
        $(this).parents('p').find('.ellipsis, .trimmedText').hide();
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
            name: 'files',
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
                        $("#uploading .uploadSucess").show();
                        $("#uploading .uploading").hide();
                        populateUploadItem($(".itemSection-temp"), result);
                        $(".itemSection-temp .noFlyout").removeClass("noFlyout");
                    },
                    function (errorMessage) {
                        showServerError(errorMessage);
                        modalClose();
                    });
            }
        }, false);

    $(".singleUploadButton").live('click', function () {
        singleAssetUploader._input = $("input[name=files]").get(0);

        singleAssetUploader.submit();
        if ($(".uploadInputsSection input[name=files]").length == 0) {
            $(".uploadInputsSection").append('<input type="file" name="files"/>');
        }
    });

    var sorting = 'asc';
    $($('#assetsCurrentDateData th')[3]).click(function() {
        if (sorting == 'asc') {
            sorting = 'desc';
        } else {
            sorting = 'asc';
        }
        var table = $('#assetsCurrentDateData').dataTable();
        table.fnSettings().aaSortingFixed[0][1] = sorting;
        table.fnSort([[0, sorting], [4, sorting]]);
        return false;
    });

    $("table.projectStats").each(function(){
        if($(this).find("tbody .dataTables_empty").length > 0) {
            $(this).parents(".assetsWrapper").find(".allAssetsCheckbox").hide();
        }
    })

    // batch edit page
    if ($(".batchEditSection").length) {

        var assetIdsPermission = [];
        $(".batchEditSection .itemSection").each(function () {
            if($(this).find("input[name=isPublic]").val() == 'false') {
                assetIdsPermission.push($(this).find("input[name=assetId]").val());
            } else {
                $(this).find(".radioBox input[type=radio]:eq(0)").attr('checked', 'checked');
            }


            $(this).find(".selectCategory select").val($(this).find("input[name=categoryId]").val());
        });

        if (assetIdsPermission && assetIdsPermission.length > 0) {
            modalPreloader();
            $.ajax({
                type: 'POST',
                url: ctx + "/batchGetAssetsPermission",
                data: {assetIds: assetIdsPermission},
                cache: false,
                dataType: 'json',
                success: function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function (result) {
                            $(".batchEditSection .itemSection").each(function () {
                                var _item = $(this);
                                if (_item.find("input[name=isPublic]").val() == 'false') {
                                    $.each(result[_item.find("input[name=assetId]").val()], function (index, value) {
                                        _item.find('.popUpPrivateAccess #' + value).attr('checked',
                                            'checked');
                                    });

                                    _item.find('a.linkSubmit').trigger('click');
                                    _item.find('input.private ').attr("checked",
                                        "checked").trigger('change');
                                }
                            });
                        },
                        function (errorMessage) {
                            showServerError(errorMessage);
                        });
                }
            });
        }

        $(".btnBatchEditSave").click(function(){
            var editAssets = [];
            // build request
            $(".batchEditSection .itemSection").each(function () {
                var editAsset = {
                    assetId: $("input[name=assetId]", this).val(),
                    assetCategoryId: $(".selectCategory select", this).val(),
                    assetDescription: $("textarea", this).val(),
                    assetPublic: $("input.public", this).is(":checked"),
                    privateUserIds: $(".popUpPrivateAccessSection",
                        this).find("input[type=checkbox].assetUserPermission:checked").map(function () {
                        return $(this).attr('id');
                    }).get()
                };

                editAssets.push(editAsset);
            });

            $.ajax({
                type: 'POST',
                url: ctx + "/editAssetVersion",
                data: {assets: editAssets},
                cache: false,
                dataType: 'json',
                success: function (jsonResult) {
                    handleJsonResult(jsonResult,
                        function (result) {
                            window.onbeforeunload = null;
                            window.open('./projectAssets?formData.projectId=' + result[0].directProjectId, '_self');
                        },
                        function (errorMessage) {
                            showServerError(errorMessage);
                        });
                }
            });
        })

    }
});

function populateUploadItem(item, json) {
    item.find("p.fileName").text(handleLongFileName(json.fileName, 80));
    item.find("span.size").text(json.fileSizeDisplay);
    item.find("span.uploader").text(json.uploader);
    item.find("p.uploadTime").text(json.uploadTime + ' EDT');
    item.data("uploadSessionKey", json.uploadSessionKey);
    item.find(".icon-file img").attr('src', '/images/icon-' + json.uploadFileTypeIcon + '.png').attr('alt',
        json.uploadFileTypeIcon);
    item.find(".uploading").hide();
    item.find(".uploadSucess").show();
}

function handleLongFileName(fileName, lengthLimit) {
    if(fileName.length > lengthLimit) {
        return fileName.substring(0, lengthLimit - 3) + "...";
    } else {
        return fileName;
    }
}

function populateUploadNewVersion(item, json) {
    item.find("a.fileName").text(handleLongFileName(json.fileName, 28)).attr('title', json.fileName);
    item.find(".versionSection ul span").text(json.fileSizeDisplay);
    item.find(".uploadBy a").text(json.uploader);
    item.find("p.date").text(json.uploadTime + ' EDT');
    item.find(".icon-file img").attr('src', '/images/icon-' + json.uploadFileTypeIcon + '.png').attr('alt',
        json.uploadFileTypeIcon);
    item.find(".version").text("Version " + $("#uploadFinish").data("newVersionNumber")).addClass("modifyVersion");
    item.data("uploadSessionKey", json.uploadSessionKey);
}

