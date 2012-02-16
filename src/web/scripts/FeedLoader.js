/*
 * Copyright (C) 2004 - 2010 TopCoder Inc., All Rights Reserved.
 *
 * <p>This is a helper javascript for the home page in order to retrieve RSS feeds.</p>
 * 
 * @author pulky, TCSDEVELOPER
 * @version 1.1
 */

/**
 * <p>This method loads a particular feed, transform it and add the result as the inner HTML of the specified element.
 * If an error occurs, it adds errorMessage content.</p>
 *
 * @param rss the rss source
 * @param template the transformation template
 * @param element the target element for the transformed text
 * @param errorMessage the error message to be shown if any error occurs
 *
 * @throws Exception if any error occurs
 */
function loadFeed(rss, template, element, errorMessage) {
    try {
        var processor = new js.topcoder.rss.template.RSSProcessor(false, template);
//        document.getElementById(element).innerHTML = (processor.transformRSSFeed(rss));

         $.ajax({
            url: rss,
            dataType: "text",
            beforeSend: function(xhr) {
                $("#newsColumn").show();
            },
            success: function(data) {
               $("#rssLoading").hide();

                if(data.length > 0) {
                    $("#" + element).html((processor.transformRSSText(data)));
                } else {
                    $("#" + element).html(errorMessage);
                }


            }
        });


        // $("#" + element).html((processor.transformRSSFeed(rss)));
    } catch (e) {
        document.getElementById(element).innerHTML = errorMessage;
        throw e;
    }
}




/**
 * <p>This method loads the three RSS feeds in the studio home page.</p>
 *
 * @throws Exception if any error occurs
 */
function loadHomePageFeeds() {

    // NOTE: The following block is commented out for testing/review purposes only. It should be
    // uncommented when deploying to Production environment
    loadFeed("https://www.topcoder.com/feed/?cat=57", "/scripts/DirectNewsTemplate.txt", "newsColumn",
        "Error reading Direct News feed.");

    // NOTE: The following block is provided for testing/review purposes only. It should be
    // removed when deploying to Production environment 
    // loadFeed("/scripts/mockDirectNews.xml", "/scripts/DirectNewsTemplate.txt", "newsColumn",
       //      "Error reading Direct News feed.");

}
