/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * Used to create specified button style for permission page.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
$.fn.dataTableExt.oPagination.permission_button = {
    /*
    * Function: oPagination.permission_button.fnInit
    * Purpose:  Initalise dom elements required for pagination with a list of the pages
    * Returns:  -
    * Inputs:   object:oSettings - dataTables settings object
    *           node:nPaging - the DIV which contains this pagination control
    *           function:fnCallbackDraw - draw function which must be called on update
    */
    "fnInit": function (oSettings, nPaging, fnCallbackDraw) {
        var nPrevious = $("<li class='prev'><a href='javascript:;'><span>Prev</span></a></li>");
        var nList = $("<li class='pageList'/>");
        var nNext = $("<li class='next'><a href='javascript:;'><span>Next</span></a></li>");

        var ul = $("<ul/>")
        ul.append(nPrevious);
        ul.append(nList);
        ul.append(nNext);

        nPaging.appendChild(ul[0]);

        nPrevious.click(function () {
            if (oSettings.oApi._fnPageChange(oSettings, "previous")) {
                fnCallbackDraw(oSettings);
                $.permission.initTable();
            }
        });

        nNext.click(function () {
            if (oSettings.oApi._fnPageChange(oSettings, "next")) {
                fnCallbackDraw(oSettings);
                $.permission.initTable();
            }
        });

        /* Disallow text selection */
        nPrevious.bind('selectstart', function () { return false; });
        nNext.bind('selectstart', function () { return false; });
    },

    /*
    * Function: oPagination.full_numbers.fnUpdate
    * Purpose:  Update the list of page buttons shows
    * Returns:  -
    * Inputs:   object:oSettings - dataTables settings object
    *           function:fnCallbackDraw - draw function to call on page change
    */
    "fnUpdate": function (oSettings, fnCallbackDraw) {
        if (!oSettings.aanFeatures.p) {
            return;
        }

        var iPageCount = $.fn.dataTableExt.oPagination.iFullNumbersShowPages;
        var iPageCountHalf = Math.floor(iPageCount / 2);
        var iPages = Math.ceil((oSettings.fnRecordsDisplay()) / oSettings._iDisplayLength);
        var iCurrentPage = Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength) + 1;
        var sList = "";
        var iStartButton, iEndButton, i, iLen;
        var oClasses = oSettings.oClasses;

        /* Pages calculation */
        if (iPages < iPageCount) {
            iStartButton = 1;
            iEndButton = iPages;
        }
        else {
            if (iCurrentPage <= iPageCountHalf) {
                iStartButton = 1;
                iEndButton = iPageCount;
            }
            else {
                if (iCurrentPage >= (iPages - iPageCountHalf)) {
                    iStartButton = iPages - iPageCount + 1;
                    iEndButton = iPages;
                }
                else {
                    iStartButton = iCurrentPage - Math.ceil(iPageCount / 2) + 1;
                    iEndButton = iStartButton + iPageCount - 1;
                }
            }
        }

        /* Build the dynamic list */
        for (i = iStartButton; i <= iEndButton; i++) {
            if (iCurrentPage != i) {
                sList += '<li class><span class="' + oClasses.sPageButton + '">';
                sList += '<a href="javascript:;">' + i + '</a></span></li>';
            }
            else {
                sList += '<li class="current"><span class="' + oClasses.sPageButtonActive + '">';
                sList += '<a href="javascript:;">' + i + '</a></span></li>';
            }
        }

        /* Loop over each instance of the pager */
        var an = oSettings.aanFeatures.p;
        var anButtons, anStatic, nPaginateList;
        var fnClick = function () {
            /* Use the information in the element to jump to the required page */
            var iTarget = ($(this).text() * 1) - 1;
            oSettings._iDisplayStart = iTarget * oSettings._iDisplayLength;
            fnCallbackDraw(oSettings);
            $.permission.initTable();
            return false;
        };
        var fnFalse = function () { return false; };

        nPaginateList = $($(".pagination .pageList")[$.permission.currentTable]);
        nPaginateList.html(sList);

        $('span', nPaginateList).click(fnClick).bind('mousedown', fnFalse)
						.bind('selectstart', fnFalse);
    }
};
