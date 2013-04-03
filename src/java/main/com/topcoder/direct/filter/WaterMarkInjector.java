/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This filter injects a watermark image into the background of an HTML page.
 * 
 * <p>
 * Version 1.1 (TC-Studio - Wireframe Viewer Modal Window Direct Updates assembly v1.0) change notes:
 *   <ol>
 *     <li>Update {@link #processResponseContent(HttpServletRequest, String)} to comment out code line 139-145 related to
 *     add watermark.</li>
 *   </ol> 
 * </p>
 *
 * @author TCASSEMBLER
 * @version 1.1
 * @since Wireframe Viewer Modal Window Direct integration assembly v1.0
 */
public class WaterMarkInjector extends WireframeBaseFilter {
    /**
     * Pattern to insert the watermark CSS into the HTML page.
     */
    private static final String CSS_LINK = "<link href=\"{0}\" type=\"text/css\" rel=\"stylesheet\">\n";

    /**
     * The mapping of HTML files to watermark images.
     */
    private final List<String[]> watermarkImageMapping = new ArrayList<String[]>();

    /**
     * Initializes this filter with the watermark image mapping.
     * 
     * @param fConfig
     *            the filter configuration
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            String watermarkImageMapping = filterConfig
                    .getInitParameter("watermarkImageMapping");

            if (watermarkImageMapping != null
                    && watermarkImageMapping.trim().length() > 0) {
                String[] split = watermarkImageMapping.split("\n");
                for (String mapping : split) {
                    String[] html2wm = mapping.trim().split(";");
                    if (html2wm.length == 2) {
                        html2wm[0] = html2wm[0].toUpperCase();
                        this.watermarkImageMapping.add(html2wm);
                    }
                }
            }
        }
    }

    /**
     * Returns the watermark required for the given request.
     * 
     * @param request
     *            HTTP request
     * @return the watermark required for the given request or null if no
     *         watermark is available.
     */
    private String getWatermark(HttpServletRequest request) {
        String lookup = request.getServletPath().toUpperCase();

        String wm = null;
        if (!lookup.endsWith("/INDEX.HTML") && !lookup.endsWith("/START.HTML")) {
            for (String[] html2wm : this.watermarkImageMapping) {
                if (lookup.startsWith(html2wm[0])) {
                    wm = html2wm[1];
                    break;
                }
            }
        }

        return wm;
    }

    /**
     * we need to insert the watermark and disable the context menu
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * @return true if we need processing, false otherwise
     */
    @Override
    protected boolean needResponseProcessing(HttpServletRequest request,
            HttpServletResponse response) {
        return true;
    }

    /**
     * Process the given response content and return the processed content.
     * 
     * @param request
     *            the HTTP request
     * @param responseContent
     *            the buffered response content before processing
     * @return the response content after processing
     * @throws IOException
     *             if an error occurs
     */
    @Override
    protected String processResponseContent(HttpServletRequest request,
            String responseContent) throws IOException {
        String processedHtmlContent = responseContent;
        int insertIndex = responseContent.toLowerCase().indexOf("</head>");

        if (insertIndex == -1) {
            // the file contains no head element, try it before the body
            insertIndex = responseContent.toLowerCase().indexOf("<body");
        }

        if (insertIndex == -1) {
            // also no body found? append it to the end of the file
            insertIndex = responseContent.length();
        }

        if (insertIndex != -1) {
            StringBuffer b = new StringBuffer(responseContent);

            if (getWatermark(request) != null) {
                // Comment out the watermark related function, may be used in the future
                /*String contextPath = request.getContextPath();
                String wmUrl = getWatermark(request);
                if (wmUrl.startsWith("/")) {
                    wmUrl = contextPath + "/../" + wmUrl;
                }

                b.insert(insertIndex, MessageFormat.format(CSS_LINK, wmUrl));*/
                
                // redirect to a page contains iframe
                b.insert(
                        insertIndex,
                        new StringBuilder("<SCRIPT type=\"text/javascript\">")
                        .append("if (top==self) location.href='/start.html?src="+ request.getRequestURL() +"'")
                        .append("</SCRIPT>\n").toString());
            }
            
            //for page which should load iframe automaticly
            String src = request.getParameter("src");
            if (src!=null)
                b.insert(
                        insertIndex,
                        new StringBuilder("<SCRIPT type=\"text/javascript\">")
                        .append("$(document).ready(function() {$('#frame_window').attr('src','"+src+"');});\n")
                        .append("</SCRIPT>").toString());
            
            // restriction right click
            b.insert(
                    insertIndex,
                    new StringBuilder("<script type='text/javascript' src='/scripts/norightclick.js'></script>")
                    .append("<SCRIPT type=\"text/javascript\">")
                    .append("noRightClick();")
                    .append("</SCRIPT>\n").toString());
            
            //add copyright comments
            Date date = new Date();  
            SimpleDateFormat dateFm = new SimpleDateFormat("yyyy");  
            
            b.insert(
                    insertIndex,
                    "<!-- Copyright (C) "+ dateFm.format(date) +" TopCoder Inc., All Rights Reserved -->\n");
            processedHtmlContent = b.toString();
        }

        return processedHtmlContent;
    }
}
