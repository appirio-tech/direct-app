/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class for the Wireframe filters.
 * 
 * @author TCASSEMBLER
 * @version 1.0
 */
abstract class WireframeBaseFilter implements Filter {
    /**
     * Performs the actual filtering by obfuscating CSS and JS files.
     * 
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     * @param chain
     *            the filter chain
     * @throws IOException
     *             if an IO error occurs
     * @throws ServletException
     *             if an error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        boolean processing = false;

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            processing = needResponseProcessing(httpRequest, httpResponse);
            if (processing) {
                httpResponse.setCharacterEncoding("UTF-8");
                BufferedResponseWrapper wrappedResponse = null;
                try {
                    wrappedResponse = new BufferedResponseWrapper(response);
                    
                    chain.doFilter(request, wrappedResponse);
                    
                    Date date = new Date();
                    date.setTime(Long.MAX_VALUE);
                    wrappedResponse.setDateHeader("Expires", 0);  
                    wrappedResponse.setHeader("Cache-Control", "no-cache");  
                    wrappedResponse.setHeader("Prama", "no-cache"); 
                    wrappedResponse.setDateHeader("Last-Modified", date.getTime());
                    

                    byte[] c = processResponseContent(httpRequest, wrappedResponse.asString()).getBytes(
                            response.getCharacterEncoding());

                    if (c.length > 0) {
                        response.setContentLength(c.length);
                        response.getOutputStream().write(c);
                    }
                    
                    
                } catch (Exception e) {
                    httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Could not process requested file: " + e.getMessage());
                } finally {
                    if (wrappedResponse != null) {
                        wrappedResponse.close();
                    }
                    
                    response.flushBuffer();
                }
            }
        }

        if (!processing) {
            chain.doFilter(request, response);
        }
    }

    /**
     * Check if we need to process the response.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * @return true if we need processing, false otherwise
     */
    protected abstract boolean needResponseProcessing(HttpServletRequest request, HttpServletResponse response);

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
    protected abstract String processResponseContent(HttpServletRequest request, String responseContent)
            throws IOException;

    /**
     * Destroys this filter. For this implementation there is nothing to do
     * here.
     */
    public void destroy() {
        // nothing to do
    }
}
