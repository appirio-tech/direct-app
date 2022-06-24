/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This filter make servletRequest and servletResponse to use UTF-8 encoding. 
 * 
 * @author TCASSEMBLER
 * @version 1.0
 */
public class CharacterEncodingFilter implements Filter {

    /**
     * Initializes this filter, do nothing here.
     * 
     * @param fConfig
     *            the filter configuration
     */
    public void init(FilterConfig filterConfig)
            throws ServletException {
        // nothing to do
    }

    /**
     * Make servletRequest and servletResponse to use UTF-8 encoding. 
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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Destroys this filter. For this implementation there is nothing to do
     * here.
     */
    public void destroy() {
        // nothing to do
    }
}