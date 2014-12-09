/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet filter adds the X-FRAME-OPTIONS (https://developer.mozilla.org/en-US/docs/Web/HTTP/X-Frame-Options)
 * response header to prevent Clickjacking.
 *
 * https://www.owasp.org/index.php/Clickjacking_Protection_for_Java_EE
 */
public class ClickjackFilter implements Filter
{

    /**
     * The default X-FRAME-OPTIONS: https://developer.mozilla.org/en-US/docs/Web/HTTP/X-Frame-Options
     */
    private String mode = "DENY";

    /**
     * Add X-FRAME-OPTIONS response header to tell IE8 (and any other browsers who
     * decide to implement) not to display this content in a frame. For details, please
     * refer to http://blogs.msdn.com/sdl/archive/2009/02/05/clickjacking-defense-in-ie8.aspx.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse res = (HttpServletResponse)response;
        res.addHeader("X-FRAME-OPTIONS", mode );
        chain.doFilter(request, response);
    }

    /**
     * Destroy method.
     */
    public void destroy() {
    }

    /**
     * Initialize the filter.
     *
     * @param filterConfig the filter config.
     */
    public void init(FilterConfig filterConfig) {
        String configMode = filterConfig.getInitParameter("mode");
        if ( configMode != null ) {
            mode = configMode;
        }
    }

}