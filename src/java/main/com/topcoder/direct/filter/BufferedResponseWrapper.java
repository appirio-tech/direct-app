/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Wraps a ServletResponse by saving its content into a
 * {@link ByteArrayOutputStream}.
 * <p>
 * The buffer can either be filled by a {@link ServletOutputStream} got by
 * {@link BufferedResponseWrapper#getOutputStream()} or a {@link PrintWriter}
 * got by {@link BufferedResponseWrapper#getWriter()} but not both (this is the
 * standard behavior of servlet response classes).
 * 
 * @author TCASSEMBLER
 * @version 1.0
 */
class BufferedResponseWrapper extends HttpServletResponseWrapper {
    /**
     * Buffer data.
     */
    private final ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();

    /**
     * Servlet output stream.
     */
    private ServletOutputStream stream;

    /**
     * Print writer.
     */
    private PrintWriter printWriter;

    /**
     * Creates a new instance for the given response.
     * 
     * @param response
     *            The HTTP response to be wrapped.
     */
    public BufferedResponseWrapper(ServletResponse response) {
        super((HttpServletResponse) response);
    }

    /**
     * Returns a {@link ServletOutputStream}.
     * 
     * @return a {@link ServletOutputStream}.
     */
    @Override
    public ServletOutputStream getOutputStream() {
        this.stream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteArrayOutput.write(b);
            }
        };

        return this.stream;
    }

    /**
     * Returns a PrintWriter.
     * 
     * @return a PrintWriter.
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        this.printWriter = new PrintWriter(new OutputStreamWriter(this.byteArrayOutput, this.getCharacterEncoding()));
        return this.printWriter;
    }

    /**
     * Returns the response as a string.
     * 
     * @return the response as a string.
     * @throws IOException
     *             if an error occurs
     */
    public String asString() throws IOException {
        return new String(this.byteArrayOutput.toByteArray(), this.getResponse().getCharacterEncoding());
    }

    /**
     * Close the wrapper. Exceptions are ignored.
     */
    public void close() {
        if (this.printWriter != null) {
            this.printWriter.close();
        }

        try {
            if (this.stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }
}
