/**
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 *
 * Loads the contents of video widget which contains video tutorial and help center widget which contains
 * links to help contents in help center dynamically.
 *
 * @author: TCSASSEMBLER
 * @version: 1.0 (Release Assembly - TC Cockpit Sidebar Header and Footer Update v1.0)
 */

/**
 * Configuration Area:
 *
 * Use the defined variables to config the contents in video widget and help center widget.
 */

// config video widget
var videoLink = "http://www.youtube.com/v/ubU0_mVtxJcxx";
var allVideoLink = "http://topcoder.com/home/help/?page_id=900";

// config help center widget

/** the contents for the FAQ tab */
var FAQ = { links: [
            { text: "How to monitor Forum Activity?", link: "http://topcoder.com/home/help/?p=622"},
            { text: "What is TopCoder forum?", link: "http://topcoder.com/home/help/?p=620"},
            { text: "How to Extend deadline?", link: "http://topcoder.com/home/help/?p=618"},
            { text: "How to increase Prize?", link: "http://topcoder.com/home/help/?p=616"},
            { text: "What can I do once a contest is running?", link: "http://topcoder.com/home/help/?p=614"},
            { text: "What types of forums does TopCoder provide?", link: "http://topcoder.com/home/help/?p=606"},
            { text: "How do I know a member has posted in forum?", link: "http://topcoder.com/home/help/?p=604"}
            ],
            viewAllLink: "http://topcoder.com/home/help/?page_id=82"
};

/** the contents for the Video tab */
var VIDEO = { links: [
            { text: "My Game Plan", link: "http://topcoder.com/home/help/?p=118"},
            { text: "Contest Health", link: "http://topcoder.com/home/help/?p=102"},
            { text: "Launching a Conceptualization Contest", link: "http://topcoder.com/home/help/?p=583"},
            { text: "Manufacturing Software", link: "http://topcoder.com/home/help/?p=433"},
            { text: "Component Architecture", link: "http://topcoder.com/home/help/?p=431"},
            { text: "Cockpit: Setting Permissions", link: "http://topcoder.com/home/help/?p=729"},
            { text: "Launching a Conceptualization Contest", link: "http://topcoder.com/home/help/?p=583"}
            ],
            viewAllLink: "http://topcoder.com/home/help/?page_id=90"
};

/** the contents for the tutorial tab */
var TUTORIAL = { links: [
            { text: "How to delete Draft contest", link: "http://topcoder.com/home/help/?p=817"},
            { text: "How to create a bug ticket in JIRA and move it to Mod Dash", link: "http://topcoder.com/home/help/?p=815"},
            { text: "How to post new version of a contest", link: "http://topcoder.com/home/help/?p=807"},
            { text: "How to repost a contest", link: "http://topcoder.com/home/help/?p=805"},
            { text: "How to increase contest prize (while the contest is running)", link: "http://topcoder.com/home/help/?p=801"},
            { text: "How to extend contest deadline (while the contest is running)", link: "http://topcoder.com/home/help/?p=797"},
            { text: "How to launch a Copilot Contest", link: "http://topcoder.com/home/help/?p=791"}
            ],
            viewAllLink: "http://topcoder.com/home/help/?page_id=86"
};

/** the contents for the example tab */
var EXAMPLE = { links: [
            { text: "My Game Plan", link: "http://topcoder.com/home/help/?p=118"},
            { text: "Contest Health", link: "http://topcoder.com/home/help/?p=102"},
            { text: "Launching a Conceptualization Contest", link: "http://topcoder.com/home/help/?p=583"},
            { text: "Manufacturing Software", link: "http://topcoder.com/home/help/?p=433"},
            { text: "Component Architecture", link: "http://topcoder.com/home/help/?p=431"},
            { text: "Cockpit: Setting Permissions", link: "http://topcoder.com/home/help/?p=729"},
            { text: "Launching a Conceptualization Contest", link: "http://topcoder.com/home/help/?p=583"}
            ],
            viewAllLink: "http://topcoder.com/home/help/?page_id=95"
};

/**
 * Loads the predefined data to the given tab of help widget.
 *
 * @param tabId  the id of the help widget tab.
 * @param data  the contents of the tab to load.
 */
function loadHelpLinksForTab(tabId, data) {
    $('#' + tabId + ' ul').empty();
    $.each(
            data.links,
            function(intIndex, objValue) {
                var template = unescape($('#helpItemTemplate').html());
                var html = $.validator.format(template, objValue.link, objValue.text);
                $('#' + tabId + ' ul').append(html);
            }
    );

    $('#' + tabId + ' .viewAllLink').attr("href", data.viewAllLink);
}

$(document).ready(function() {
    // load contents for video widget
    $(".videoBox .allVideos").attr("href", allVideoLink);
    $(".videoBox #movieLink").attr("value", videoLink);

    // load contents for help widget
    if (!($.browser.msie && $.browser.version == 7.0)) {
        loadHelpLinksForTab('FAQTabContent', FAQ);
    }
    loadHelpLinksForTab('videoTabContent', VIDEO);
    loadHelpLinksForTab('tutorialTabContent', TUTORIAL);
    loadHelpLinksForTab('exampleTabContent', EXAMPLE);
});

