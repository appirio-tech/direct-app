/**
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 *
 * The JS script for the right side bar, it loads all the data via AJAX.
 *
 *  Version 1.0 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 *
 * @author Veve
 * @version 1.0
 */

var rightSidebarData = {};
var currentProjectName = "";
var contestData;
var isInProjectScope;
var currentProjectContests;
var currentContestsData = [];

var updateContestsTable = function (contestsData, emptyMessage) {
    var contests = $(".contestList .contestListMask .tableBody table tbody");

    if (!contests) {
        // there is no right sidebar or there is no contests panel, return
        return;
    }

    contests.empty();

    $.each(contestsData, function (i, item) {
        var urlPart = "/contest/detail?projectId=";

        if (item.typeName == 'Copilot Posting') {
            urlPart = "/copilot/copilotContestDetails?projectId=";
        }

        var newRow = $("<tr><td><span class='"
        + item.statusShortName.toLowerCase() + "' title='" + item.statusName
        + "'></span></td><td class='leftAlign'></td><td><img/></td></tr>");

        $("td.leftAlign", newRow).html($("<a></a>").attr('href',
            ctx + urlPart + item.id).text(item.name).addClass('contestLink').attr('target', '_blank'));

        switch (item.statusShortName) {
            case "Running":
                $("td:eq(0) span", newRow).attr("class", "running");
                break;

            case "Draft":
                $("td:eq(0) span", newRow).attr("class", "draft");
                break;

            case "Completed":
                $("td:eq(0) span", newRow).attr("class", "completed");
                break;

            case "Cancelled":
                $("td:eq(0) span", newRow).attr("class", "cancelled");
                break;
        }
        $("td:eq(2) img", newRow).attr("alt", item.typeShortName.toLowerCase()).attr("src", "/images/"
        + item.typeShortName.toLowerCase() + "_small.png").attr('title', item.typeName);

        if (i % 2 == 0) {
            newRow.removeClass("even");
        } else {
            newRow.addClass("even");
        }
        newRow.appendTo(contests);
    });

    if (contestsData.length == 0) {
        $("<tr><td class='hide'></td><td class='hide'></td><td colspan='3'>" + emptyMessage + "</td></tr>").appendTo(contests);
    }
    $("#contestsTable:has(tbody tr)").tablesorter();
    $("#contestsTable").trigger("update");
    sortCurrentContestsTable();
}

var selectProjectRightSidebar = function (projectId) {

    var list = $("#dropDown1 .dropList");
    if (!list.is(":hidden")) {
        list.hide();
        if ($(".projectSelectMask .contestsDropDown UL").height() > 200) {
            $(".projectSelectMask .contestsDropDown UL").css('width', 233);
        }
    }

    currentProjectId = projectId;
    var projectName = "Select a project here";
    $(".projectSelectMask .dropList li").each(function () {
        if ($(this).data("id") == projectId) {
            projectName = $.trim($("a", this).text());
        }
    });
    $(".projectSelectMask .inputSelect input").val(projectName);

    if (!projectId || projectId <= 0) {
        return;
    }

    $.ajax({
        type: 'POST',
        url: "/direct/getDirectProjectContests",
        data: {directProjectId: projectId},
        cache: false,
        dataType: 'json',
        success: function (jsonResult) {
            handleJsonResult2(jsonResult,
                function (contestsData) {
                    currentContestsData = contestsData;
                    updateContestsTable(contestsData, "No Contests this project");
                    $(".contestList .searchMask input").val('').trigger("keyup");
                },
                function (errorMessage) {
                    showServerError(errorMessage);
                });
        }
    });
}

var currentSorting = [[0, 1]];

var sortCurrentContestsTable = function () {
    if ($("#contestsTable tbody tr").size() > 0) {
        $("#contestsTable").trigger("sorton", [currentSorting]);
        $("#contestsTable tr").removeClass("even");
        $("#contestsTable tr:even").addClass("even");
    }
}

/* sort contest by title */
var sortTitle = function () {
    var sorting = [[1, 0]];
    $("#contestsTable").trigger("sorton", [sorting]);
    $("#contestsTable tr").removeClass("even");
    $("#contestsTable tr").each(function () {
        $("#contestsTable tr:even").addClass("even");
    });
}

/* sort contest by status */
var sortStatus = function () {
    var sorting = [[0, 1]];
    $("#contestsTable").trigger("sorton", [sorting]);
    $("#contestsTable tr").removeClass("even");
    $("#contestsTable tr").each(function () {
        $("#contestsTable tr:even").addClass("even");
    });
}


/* sort contest by type */
var sortType = function () {
    var sorting = [[2, 0]];
    $("#contestsTable").trigger("sorton", [sorting]);
    $("#contestsTable tr").removeClass("even");
    $("#contestsTable tr").each(function () {
        $("#contestsTable tr:even").addClass("even");
    });
}

/* get the selected index and sort the contests table */
var sortTable = function (mySelect) {
    var selected = mySelect.options[mySelect.selectedIndex].value;

    if (selected == "title")
        sortTitle();
    else if (selected == "status")
        sortStatus();
    else
        sortType();
}

/*-Show the scrollbar when the number of contests is more than 10-*/

var adjustContestListHeight = function () {
    var rows_height = 0;
    var contests_nbre = 0;

    /* get the height of the 10 first rows ( one contest per row)*/
    $("#contestsTable TBODY").children().each(function () {
        if (contests_nbre < 10)
            rows_height += $(this).height();
        contests_nbre++;
    });

    if (contests_nbre > 10) {
        $(".contestsContent").height(rows_height);

        // Chrome
        if (ua.match(/chrome\/([\d.]+)/) != null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0] > 2) {
            $(".contestsContent").height(rows_height + 20);
        }

        // Safari
        if (ua.match(/version\/([\d.]+).*safari/) != null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0] > 3) {
            $(".contestsContent").height(rows_height + 20);
        }

        // IE 7
        if ($.browser.msie && $.browser.version == 7.0) {
            $(".contestsContent").height(rows_height + 20);
        }

        // IE 8
        if ($.browser.msie && $.browser.version == 8.0) {
            $(".contestsContent").height(rows_height + 20);
        }
        $(".contestsContent TABLE").css("width", "232px");

        return rows_height + 20;
    }

    return 0;
}

var showHideRows = function (myLink, tableId) {

    if ($(myLink).html() == "View More") { //when the user click the view more link we will show the hidden rows
        $("#" + tableId + " TBODY TR").each(function () {
            $(this).removeClass("hide");
        });

        $(myLink).html("Hide Rows");
        $(myLink).addClass("less");

    } else { //when the user click the hide rows link we will hide some rows
        var hide_row = false;
        $("#" + tableId + " TBODY TR").each(function () {

            if (this.className.search("hideStart") != -1) {
                hide_row = true;
            }

            if (hide_row)
                $(this).addClass("hide");
        });

        $(myLink).html("View More");
        $(myLink).removeClass("less");
    }

};


// JavaScript Document
$(document).ready(function () {

    if ($(".newSidebar").length > 0) {

        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();

        $.ajax({
            url: '/direct/customerProjectsAjaxAction',
            async: false,
            dataType: 'json',
            success: function (data) {
                var returnData = data.result['return'];
                rightSidebarData = returnData.projects;
                contestData = returnData.contests;
                currentProjectName = $.trim(returnData.currentProjectName);
            }
        });

        adjustContestListHeight = function () {
            var rows_height = 0;
            var contests_nbre = 0;

            /* get the height of the 10 first rows ( one contest per row)*/
            $("#contestsTable TBODY").children().each(function () {
                if (contests_nbre < 10)
                    rows_height += $(this).height();
                contests_nbre++;
            });

            if (contests_nbre > 10) {
                $(".contestsContent").height(rows_height);

                // Chrome
                if (ua.match(/chrome\/([\d.]+)/) != null && ua.match(/chrome\/([\d.]+)/)[1].split('.')[0] > 2) {
                    $(".contestsContent").height(rows_height + 20);
                }

                // Safari
                if (ua.match(/version\/([\d.]+).*safari/) != null && ua.match(/version\/([\d.]+).*safari/)[1].split('.')[0] > 3) {
                    $(".contestsContent").height(rows_height + 20);
                }

                // IE 7
                if ($.browser.msie && $.browser.version == 7.0) {
                    $(".contestsContent").height(rows_height + 20);
                }

                // IE 8
                if ($.browser.msie && $.browser.version == 8.0) {
                    $(".contestsContent").height(rows_height + 20);
                }

                $(".contestsContent TABLE").css("width", "250px");

                return rows_height + 20;
            } else {
                $(".contestsContent").css("width", "250px");
            }

            return 0;
        }

        //------------------------------------------------- Contests List

        filterProject = function () {
            if (!$("#dropDown1 .dropList").is(":visible")) {
                showHideProjectList();
            }
            var input = $(".projectSelectMask .inputSelect>input")[0];
            var typedText = input.value;
            if (input.value != input.originalValue) {
                input.originalValue = input.value;
            } else {
                return;
            }
            var idx = 0;
            $("#dropDown1>ul>li").each(function () {
                if ($(this).find("a")[0].innerHTML.toLowerCase().indexOf(typedText.toLowerCase()) == -1) {
                    $(this).css('display', 'none').removeClass("hover");
                } else {
                    ++idx;
                    $(this).css('display', '').removeClass("even");
                    if (idx % 2 == 0) {
                        $(this).addClass("even");
                    }
                }
            });
        }

        selectProject = function (e) {
            if (!$("#dropDown1 .dropList").is(":visible")) {
                showHideProjectList();
            }
            var code = e.which ? e.which : e.keyCode;
            var current = $("#dropDown1>ul>li.hover:visible");
            var selected;
            if (code == 38) {
                // up
                if (current.size() == 0) {
                    selected = $("#dropDown1>ul>li:visible:first");
                } else {
                    selected = current.prev(":visible");
                }
            } else if (code == 40) {
                // down
                if (current.size() == 0) {
                    selected = $("#dropDown1>ul>li:visible:first");
                } else {
                    selected = current.next(":visible");
                }
            } else if (code == 13) {
                // enter
                if (current.size() > 0) {
                    $("a", current).each(function () {
                        this.click();
                    });
                }
                return;
            } else {
                return;
            }
            if (selected.size() > 0) {
                $("#dropDown1>ul>li").removeClass("hover");
                selected.addClass("hover");
                var container = $("#dropDown1");
                var cHeight = container.height() - 25;
                var u = container[0].scrollTop;
                var v = u + cHeight;
                var offsetTop = selected[0].offsetTop;
                var offsetBottom = offsetTop + selected.height();
                if (offsetTop < u) {
                    container.animate({
                        scrollTop: offsetTop
                    }, 20);
                } else if (offsetBottom > v) {
                    container.animate({
                        scrollTop: offsetBottom - cHeight
                    }, 20);
                }
            }
        }

        filterCustomer = function () {
            if (!$("#dropDown2 .dropList").is(":visible")) {
                showHideCustomerList();
            }
            var input = $(".customerSelectMask .inputSelect>input")[0];
            var typedText = input.value;
            if (input.value != input.originalValue) {
                input.originalValue = input.value;
            } else {
                return;
            }
            var idx = 0;
            $("#dropDown2>ul>li").each(function () {
                if ($(this).find("a")[0].innerHTML.toLowerCase().indexOf(typedText.toLowerCase()) == -1) {
                    $(this).css('display', 'none').removeClass("hover");
                } else {
                    ++idx;
                    $(this).css('display', '').removeClass("even");
                    if (idx % 2 == 0) {
                        $(this).addClass("even");
                    }
                }
            });
        }

        selectCustomer = function (e) {
            if (!$("#dropDown2 .dropList").is(":visible")) {
                showHideCustomerList();
            }
            var code = e.which ? e.which : e.keyCode;
            var current = $("#dropDown2>ul>li.hover:visible");
            var selected;
            if (code == 38) {
                // up
                if (current.size() == 0) {
                    selected = $("#dropDown2>ul>li:visible:first");
                } else {
                    selected = current.prev(":visible");
                }
            } else if (code == 40) {
                // down
                if (current.size() == 0) {
                    selected = $("#dropDown2>ul>li:visible:first");
                } else {
                    selected = current.next(":visible");
                }
            } else if (code == 13) {
                // enter
                if (current.size() > 0) {
                    current.trigger("click");
                }
                return;
            } else {
                return;
            }
            if (selected.size() > 0) {
                $("#dropDown2>ul>li").removeClass("hover");
                selected.addClass("hover");
                var container = $("#dropDown2");
                var cHeight = container.height() - 25;
                var u = container[0].scrollTop;
                var v = u + cHeight;
                var offsetTop = selected[0].offsetTop;
                var offsetBottom = offsetTop + selected.height();
                if (offsetTop < u) {
                    container.animate({
                        scrollTop: offsetTop
                    }, 20);
                } else if (offsetBottom > v) {
                    container.animate({
                        scrollTop: offsetBottom - cHeight
                    }, 20);
                }
            }
        }

        showHideProjectList = function () {
            var list = $("#dropDown1 .dropList");
            if (list.is(":hidden")) {
                list.show();
            } else {
                list.hide();
            }

            if ($(".projectSelectMask .contestsDropDown UL").height() > 200) {
                $(".projectSelectMask .contestsDropDown UL").css('width', 233);
            }
            var input = $(".projectSelectMask .inputSelect>input")[0];
            input.originalValue = input.value;
        }

        showHideCustomerList = function () {
            var contestsDropDown = $(".customerSelectMask .contestsDropDown");
            if (contestsDropDown.is(":hidden")) {
                $(".customerSelectMask .contestsDropDown").hide();
            }

            var list = contestsDropDown.find("ul");
            if (list.is(":hidden")) {
                $(".dropdownWidget .dropList").hide();
                list.show();
            } else {
                list.hide();
            }
            //contestsDropDown.slideToggle(100);
            var input = $(".customerSelectMask .inputSelect>input")[0];
            input.originalValue = input.value;
        }

        $(".customerSelectMask .inputSelect input").focus(function () {
            showHideCustomerList();
        });
        $(".customerSelectMask .inputSelect a").click(function () {
            showHideCustomerList();
        });

        var updateCustomerDropDown = function (dropDownWrapper, items, customerName) {
            var dropDown = dropDownWrapper.find(".contestsDropDown ul");
            var input = dropDownWrapper.find(".inputSelect input");
            dropDown.find("li").remove();
            input.val("");
            $.each(items, function (index, item) {
                var li = $("<li><a class='longWordsBreak' href='#'></a></li>");
                li.data("id", item.id);
                li.find("a").text(item.value);
                if (customerName == undefined && index == 0) {
                    input.val(item.value);
                } else {
                    input.val(customerName);
                }
                li.appendTo(dropDown);
            })
            dropDown.find("li:odd").addClass("even");
        }

        var updateProjectDropDown = function (dropDownWrapper, items) {
            var dropDown = dropDownWrapper.find(".contestsDropDown ul");
            var input = dropDownWrapper.find(".inputSelect input");
            dropDown.find("li").remove();
            input.val("");
            var hasCurrentProject = false;
            $.each(items, function (index, item) {
                var li = $("<li><a class='longWordsBreak' href='javascript:selectProjectRightSidebar(" + item.id + ");'></a></li>");
                li.data("id", item.id);
                li.find("a").text(item.value);
                li.appendTo(dropDown);
                if ($.trim(item.value) == currentProjectName) {
                    input.val(item.value);
                    hasCurrentProject = true;
                    selectProjectRightSidebar(item.id);
                }
            })

            if (!hasCurrentProject) input.val("Select a project here");

            if (typeof isInProjectScope != 'undefined' && !isInProjectScope) {

                if (hasCurrentProject) {
                    if ((undefined != currentProjectContests) && $("#contestsTable tbody tr").length <= 0) {
                        $("#contestsTable tbody").html(currentProjectContests);
                        var newHeight = adjustContestListHeight();
                        if (newHeight > 0) {
                            $(".jScrollPaneContainer").height(newHeight);
                        }
                        $('.contestsContent').jScrollPane({
                            scrollbarWidth: 17,
                            showArrows: true
                        });
                    }
                } else {
                    // clear the contests if needed
                    if ($("#contestsTable tbody tr").length > 0) {
                        currentProjectContests = $("#contestsTable tbody").html();
                        $("#contestsTable tbody").html('');
                        $(".jScrollPaneContainer").height(233);
                        $('.contestsContent').jScrollPane({
                            scrollbarWidth: 17,
                            showArrows: true
                        });
                    }
                }


            }

            dropDown.find("li:odd").addClass("even");
        }

        function compareProject(projectA, ProjectB) {
            if (projectA.value.toLowerCase() < ProjectB.value.toLowerCase())
                return -1;
            if (projectA.value.toLowerCase() > ProjectB.value.toLowerCase())
                return 1;
            return 0;
        }

        function compareCustomer(customerA, customerB) {
            if (customerA.value.toLowerCase() == 'all customers') return -1;
            if (customerB.value.toLowerCase() == 'all customers') return 1;

            if (customerA.value.toLowerCase() < customerB.value.toLowerCase())
                return -1;
            if (customerA.value.toLowerCase() > customerB.value.toLowerCase())
                return 1;
            return 0;
        }

        var getCustomers = function () {
            var arr = [{"id": "", "value": "All Customers"}];
            var count = 0;
            var noCustomer;
            if (typeof rightSidebarData != 'undefined' && rightSidebarData) {
                for (var p in rightSidebarData) {
                    if (typeof(rightSidebarData[p]) != "function") {
                        count++;
                        var obj = new Object();
                        obj.value = p;
                        obj.id = rightSidebarData[p]["id"];
                        obj.projects = rightSidebarData[p]["projects"];

                        if (!(obj.id == "none")) {
                            arr.push(obj);
                        } else {
                            noCustomer = obj;
                        }
                    }
                }
            }

            arr.sort(compareCustomer);

            if (undefined != noCustomer) {
                arr.push(noCustomer);
            }

            if (count == 1) {
                arr.shift();
            }

            return arr;
        }
        var customerList = getCustomers();
        //updateCustomerDropDown($(".customerSelectMask"), customerList);

        var getCustomerWithProject = function (projectName) {
            var result = {};

            result.id = '';
            result.name = 'All Customers';

            $.each(customerList, function (index, item) {
                var projects = item.projects;
                if (!(item.id == '' || item.id == 'none')) {
                    for (var p in projects) {
                        if (typeof(projects[p]) != "function") {
                            var name = projects[p];
                            if ($.trim(name) == $.trim(projectName)) {
                                result.id = item.id;
                                result.name = item.value;
                            }
                        }
                    }
                }
            });

            return result;
        }

        var getProjects = function (id) {
            var arr = [];
            $.each(customerList, function (index, item) {
                var projects = item.projects;
                if (id == "" || id == "0") {
                    for (var p in projects) {
                        if (typeof(projects[p]) != "function") {
                            var obj = new Object();
                            obj.id = p;
                            obj.value = projects[p];
                            arr.push(obj);
                        }
                    }
                } else {
                    if (item.id == id) {
                        for (var p in projects) {
                            if (typeof(projects[p]) != "function") {
                                var obj = new Object();
                                obj.id = p;
                                obj.value = projects[p];
                                arr.push(obj);
                            }
                        }
                    }
                }
            });

            arr.sort(compareProject);

            return arr;
        }

        if (typeof (currentProjectName) != "undefined" && currentProjectName != '') {
            var result = getCustomerWithProject(currentProjectName);
            updateCustomerDropDown($(".customerSelectMask"), customerList, result.name);
            updateProjectDropDown($(".projectSelectMask"), getProjects(result.id));
        } else {
            updateCustomerDropDown($(".customerSelectMask"), customerList);
            updateProjectDropDown($(".projectSelectMask"), getProjects(""));
        }

        var count = 0;
        $("#contestsContent").html('');

        $.each(contestData, function (key, value) {
            var templateData = value;
            templateData.i = count++;

            var row = '<tr' + " class='" + "'>" +
                '<td>' + '<span class="' + templateData.status + '"></span></td>' +
                '<td class="leftAlign"><a target="_blank" class="contestLink" href="' + + '/direct/contest/detail.action?projectId=' + templateData.id + '">' + templateData.title + '</a></td>' +
                '<td class="type">' + '<img alt="' + templateData.type + '" src="/images/' + templateData.type + '_small.png"/>' + '</td></tr>';

            $("#contestsContent").append(row);
        });

        $(".customerSelectMask UL LI").click(function () {
            var mask = $(this).parents(".customerSelectMask");
            mask.find("input").val($(this).find("a").text());

            if (!mask.find(".contestsDropDown .dropList").is(":hidden")) {
                mask.find(".contestsDropDown .dropList").hide();
            }
            updateProjectDropDown($(".projectSelectMask"), getProjects($(this).data("id")));
            return false;
        })

        /*---------------------- Show the scrollbar when the number of contests is more than 10----*/

        adjustContestListHeight();

        /* Stylished scrollbar*/
        $('.contestsContent').jScrollPane({
            scrollbarWidth: 17,
            showArrows: true
        });

        /*-------------------------- Show/hide the dropdown list --*/

        /*------------------------- hover state of the dropdown list  --*/

        $(".contestsDropDown UL LI").live('mouseover', function () {
            $(this).addClass("hover");
        });

        $(".contestsDropDown UL LI").live('mouseout', function () {
            $(this).removeClass("hover");
        });

        /*------------------------- hover state of the contests table --*/
        $("table#contestsTable tr").live('mouseover', function () {
            $(this).addClass("highlight");
        });

        $("table#contestsTable tr").live('mouseout', function () {
            $(this).removeClass("highlight");
        });


        /*------------------------- show or hide rows functionnality in dashboard.html --*/
        // we will show just the first rows_nbre rows
        $("TABLE.rowsToHide").each(function () {

            var table_id = $(this).attr("id");
            var hide_row = false;

            $("#" + table_id + " TBODY TR").each(function () {

                if (this.className.search("hideStart") != -1) {
                    hide_row = true;
                }

                if (hide_row)
                    $(this).addClass("hide");
            })
        });


        /*----------------- projects table hover --*/
        $("table.project TR").mouseover(function () {
            $(this).addClass("hover");
        });

        $("table.project TR").mouseout(function () {
            $(this).removeClass("hover");
        });


        if ($.browser.msie && $.browser.version == 7.0) {
            $(".contestsContent").css("overflow-x", "hidden");
        }

        $(".contestList input.selectProjectBtn").click(function () {
            if (typeof (currentProjectId) != "undefined" && currentProjectId > 0) {
                document.location.href = '/direct/projectOverview?formData.projectId=' + currentProjectId;
            } else {
                alert('Select a project first');
            }
        });

        $(".contestList .searchMask input").keyup(function () {
            var keyword = $.trim($(this).val()).toLowerCase();
            var count = 0;
            var filtered = [];
            $.each(currentContestsData, function (i, item) {
                var found = keyword.length == 0 ||
                    item.statusName.toLowerCase().indexOf(keyword) >= 0 ||
                    item.name.toLowerCase().indexOf(keyword) >= 0 ||
                    item.typeName.toLowerCase().indexOf(keyword) >= 0;
                if (found) {
                    filtered.push(item);
                }
            });
            updateContestsTable(filtered, "No Matched Challenge");
        });

        var zIndex = 100;
        $(".newSidebar .dropdownWidget").each(function () {
            $(this).css("z-index", zIndex--);
        });

        $(".newSidebar .contestList .tableBody td").live("mouseenter", function () {
            $(this).parent().addClass("hover");
        })
        $(".newSidebar .contestList .tableBody td").live("mouseleave", function () {
            $(this).parent().removeClass("hover");
        })

        $(document).click(function (event) {

            if ($(event.target).parents(".dropList").length == 0) {
                //$(".dropdownWidget .dropList").hide();
            }

        });

        /* sort contests */
        if ($("#contestsTable").length > 0) {

            $("#contestsTable:has(tbody tr)").tablesorter();

            $("#rightTableHeader .statusTh").click().click();
        }

        adjustContestListHeight();

        if ($(".contestsContent").length > 0) {
            /* Stylished scrollbar*/
            $('.contestsContent').jScrollPane({
                scrollbarWidth: 17,
                showArrows: true
            });
        }
    }

    $(".newSidebar .contestList .tableHeader span").click(function(){
        var header = $(this).parent();
        if($(this).hasClass("down")){
            $(this).removeClass("down").addClass("up");
        }else if($(this).hasClass("up")){
            $(this).removeClass("up").addClass("down");
        }else{
            header.find("span").removeClass("down").removeClass("up");
            $(this).addClass("down");
        }

        var o = 0;
        if ($(this).hasClass("down")) {
            o = 1;
        }

        var oo;

        if ($(this).hasClass("statusTh")) {
            oo = 0;
        } else if ($(this).hasClass("titleTh")) {
            oo = 1;
        } else {
            oo = 2;
        }

        var sorting = [[oo, o]];
        currentSorting = sorting;
        sortCurrentContestsTable();
    })

    $(".newSidebar .contestList .tableBody td").live("mouseenter",function(){
        $(this).parent().addClass("hover");
    })
    $(".newSidebar .contestList .tableBody td").live("mouseleave",function(){
        $(this).parent().removeClass("hover");
    })

});


