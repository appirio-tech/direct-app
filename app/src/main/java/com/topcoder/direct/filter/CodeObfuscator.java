/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * This Servlet Filter implementation will obfuscate JavaScript and CSS code for
 * all clients which are not in the list of trusted IP addresses.
 * 
 * @author TCASSEMBLER
 * @version 1.0
 */
public class CodeObfuscator extends WireframeBaseFilter {
    /**
     * This set contains the list of trusted IP addresses.
     */
    private final Set<String> trustedIPs = new HashSet<String>();

    /**
     * Compresses the CSS.
     * 
     * @param content
     *            the CSS content.
     * @return compressed content
     * @throws IOException
     *             if an error occurs
     */
    private String compressCSS(String content) throws IOException {
        CssCompressor cc = new CssCompressor(new StringReader(content));
        StringWriter w = new StringWriter(content.length());

        cc.compress(w, -1);

        return w.toString();
    }

    /**
     * Compresses the JavaScript.
     * 
     * @param content
     *            the JavaScript content.
     * @return compressed content
     * @throws IOException
     *             if an error occurs
     */
    private String compressJavaScript(String content) throws IOException {
        StringWriter w = new StringWriter(content.length());
        JavaScriptCompressor jsc = new JavaScriptCompressor(new StringReader(content), new ErrorReporter() {

            public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
                // ignore
            }

            public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
                // ignore
            }

            public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
                    int lineOffset) {
                error(message, sourceName, line, lineSource, lineOffset);
                return new EvaluatorException(message);
            }
        });

        jsc.compress(w, -1, true, false, false, false);

        return w.toString();
    }

    /**
     * Initializes this filter with the list of trusted IP addresses.
     * 
     * @param fConfig
     *            the filter configuration
     */
    public void init(FilterConfig fConfig) {
        if (fConfig != null) {
            String trustedIPs = fConfig.getInitParameter("trustedIPs");

            if (trustedIPs != null && trustedIPs.trim().length() > 0) {
                this.trustedIPs.addAll(Arrays.asList(trustedIPs.trim().split(";")));
            }
        }
    }

    /**
     * Check if we need to process the response by looking up the remote address
     * in the list of trusted IPs. If it is not found we do need processing.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * @return true if we need processing, false otherwise
     */
    @Override
    protected boolean needResponseProcessing(HttpServletRequest request, HttpServletResponse response) {
        return !this.trustedIPs.contains(request.getRemoteAddr());
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
    protected String processResponseContent(HttpServletRequest request, String responseContent) throws IOException {
        if (request.getHeader("referer")==null || request.getHeader("referer").equals(""))
            return "Access denied";
        
        String requestUri = request.getRequestURI().toUpperCase();
        String processedContent = responseContent;
        if (requestUri.endsWith(".JS")) {
            processedContent = compressJavaScript(responseContent);
        } else if (requestUri.endsWith(".CSS")) {
            processedContent = compressCSS(responseContent);
        }
        return processedContent;
    }

}