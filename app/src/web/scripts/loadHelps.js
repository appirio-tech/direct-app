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
var videoLink = "https://www.youtube.com/v/ubU0_mVtxJcxx";
var allVideoLink = "http://www.topcoder.com/help/template-video/?subject=startproject&catIdx=18";

// config help center widget

/** the contents for the FAQ tab */
var FAQ = { links: [
            { text: "Who are the copilots?", link: "https://www.topcoder.com/help/p=301/?subject=startproject"},
            { text: "How to monitor Forum Activity?", link: "https://www.topcoder.com/help/?p=622"},
            { text: "What is TopCoder forum?", link: "https://www.topcoder.com/help/?p=620"},
            { text: "How to increase Prize?", link: "https://www.topcoder.com/help/?p=616"},
            { text: "What can I do once a challenge is running?", link: "https://www.topcoder.com/help/?p=614"},
            { text: "What types of forums does TopCoder provide?", link: "https://www.topcoder.com/help/?p=606"},
            { text: "How do I know a member has posted in forum?", link: "https://www.topcoder.com/help/?p=604"}
            ],
            viewAllLink: "https://www.topcoder.com/help/?page_id=82"
};

/** the contents for the Video tab */
var VIDEO = { links: [
            { text: "My Game Plan", link: "https://www.topcoder.com/help/?p=118"},
            { text: "How to get a copilot", link: "https://www.topcoder.com/help/2012/03/13/how-to-get-a-copilot/?subject=startproject"},
            { text: "Launching a Conceptualization Challenge", link: "https://www.topcoder.com/help/?p=583"},
            { text: "Direct - Login", link: "https://www.topcoder.com/help/2012/03/27/cockpit-login/?subject=startproject"},
            { text: "Direct - Project Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-project-dashboard-1/?subject=startproject"},
            { text: "Direct: Setting Permissions", link: "httpss://www.topcoder.com/help/?p=729"},
            { text: "Direct - Challenge Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-contest-dashboard-1/?subject=startproject"}
            ],
            viewAllLink: "http://www.topcoder.com/help/template-video/?subject=startproject&catIdx=18"
};

/** the contents for the tutorial tab */
var TUTORIAL = { links: [
            { text: "My Game Plan", link: "https://www.topcoder.com/help/?p=118"},
            { text: "How to get a copilot", link: "https://www.topcoder.com/help/2012/03/13/how-to-get-a-copilot/?subject=startproject"},
            { text: "Launching a Conceptualization Challenge", link: "https://www.topcoder.com/help/?p=583"},
            { text: "Direct - Login", link: "http://www.topcoder.com/help/2012/03/27/cockpit-login/?subject=startproject"},
            { text: "Direct - Project Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-project-dashboard-1/?subject=startproject"},
            { text: "Direct: Setting Permissions", link: "https://www.topcoder.com/help/?p=729"},
            { text: "Direct - Challenge Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-contest-dashboard-1/?subject=startproject"}
            ],
            viewAllLink: "https://www.topcoder.com/help/?page_id=86"
};

/** the contents for the example tab */
var EXAMPLE = { links: [
            { text: "Sample Project", link: "https://www.topcoder.com/how-it-works/"},
            { text: "How to get a copilot", link: "https://www.topcoder.com/help/2012/03/13/how-to-get-a-copilot/?subject=startproject"},
            { text: "Launching a Conceptualization Challenge", link: "https://www.topcoder.com/help/?p=583"},
            { text: "Direct - Login", link: "https://www.topcoder.com/help/2012/03/27/cockpit-login/?subject=startproject"},
            { text: "Direct - Project Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-project-dashboard-1/?subject=startproject"},
            { text: "Direct: Setting Permissions", link: "https://www.topcoder.com/help/?p=729"},
            { text: "Direct - Challenge Dashboard 1", link: "https://www.topcoder.com/help/2012/03/27/cockpit-contest-dashboard-1/?subject=startproject"}
            ],
            viewAllLink: "https://www.topcoder.com/help/?page_id=95"
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

