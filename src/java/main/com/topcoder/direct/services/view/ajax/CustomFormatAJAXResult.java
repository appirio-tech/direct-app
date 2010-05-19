/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.topcoder.direct.services.view.action.contest.launch.AbstractAction;
import com.topcoder.direct.services.view.action.contest.launch.AggregateDataModel;
import com.topcoder.direct.services.view.ajax.processors.DefaultAJAXResultPreProcessor;
import com.topcoder.direct.services.view.ajax.serializers.JSONDataSerializer;

/**
 * <p>
 * This result processes the result data into an AJAX compatible response. The actual serialization to
 * specific AXAJ representation is performed by the injected AJAXModelSerializer. In addition a pre-processing
 * and post-processing modules can also be injected for complete processing abstraction.
 * </p>
 *
 * <p>
 * This result also provides the abilities to to compress response, disable response cache, and set custom
 * response content type and charset.
 * </p>
 *
 * <p>
 *
 * <pre>
 * // create a CustomFormatAJAXResult instance
 * CustomFormatAJAXResult instance = new CustomFormatAJAXResult();
 * // create an invocation
 * MockActionInvocation invocation = new MockActionInvocation();
 * MockAbstractAction action = new MockAbstractAction();
 * AggregateDataModel dataModel = new AggregateDataModel();
 *
 * // create a java bean object
 * Project project = new Project();
 * project.setId(100);
 * project.setName(&quot;TC_Project&quot;);
 * project.setDescription(&quot;Project description&quot;);
 *
 * dataModel.setData(&quot;result&quot;, project);
 * dataModel.setAction(&quot;tc&quot;);
 * action.setModel(dataModel);
 *
 * // create HttpServletResponse instance
 * invocation.setAction(action);
 * MockHttpServletResponse response = new MockHttpServletResponse();
 * ServletActionContext.setResponse(response);
 *
 * // invoke the execute
 * instance.execute(invocation);
 * System.out.println(((MockServletOutputStream) response.outputStream).buf.toString());
 *
 * // here is the output result
 * // {&quot;result&quot;: {&quot;name&quot;: &quot;tc&quot;,&quot;return&quot;: {&quot;description&quot;:&quot;
 * // Project description&quot;,&quot;id&quot;:100,&quot;name&quot;:&quot;TC_Project&quot;}}}
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread safety: Technically this class is NOT thread safe since it has mutable states, but the intended
 * usage of the mutators is for IoC injection and thus we expect for these values not to be changed after
 * initialization. Thus this class can be considered as thread-safe conditional in an IoC injection usage of
 * the mutators.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CustomFormatAJAXResult implements Result {
    /**
     * <p>
     * The key under which the data to be serialized will be stored.
     * </p>
     */
    private static final String RESULT_DATA_KEY = "result";

    /**
     * <p>
     * Represent the "no-cache" string, used to set to the response.
     * </p>
     */
    private static final String NO_CACHE = "no-cache";
    /**
     * <p>
     * Represent the buffer size to read/write data.
     * </p>
     */
    private static final int BUFFER_SIZE = 1024;
    /**
     * <p>
     * This is the serialized which will take the data for the response and convert/serialize it to the
     * specific format as needed by the caller.
     * </p>
     *
     * <p>
     * Defaults to new JSONDataSerializer() , Can not be set to null.
     * </p>
     */
    private AJAXDataSerializer dataSerializer = new JSONDataSerializer();
    /**
     * <p>
     * Represents the preprocessor which will be used to pre-process the result data before it is run through
     * the serialization. This is useful when we want to remove certain data from the serialization process
     * for example.
     * </p>
     *
     * <p>
     * Defaults to new DefaultAJAXResultPreProcessor() , Can be set to null.
     * </p>
     */
    private AJAXResultPreProcessor dataPreProcessor = new DefaultAJAXResultPreProcessor();
    /**
     * <p>
     * Represents the post-processor which will be used to post-process the result data before it is returned
     * to the caller (after it was serialized). This is useful when we want to validate the result data or to
     * make sure that it contains no sensitive data. This is more of a convenience which by default is not
     * used.
     * </p>
     *
     * <p>
     * Defaults to null.Can be set to null.
     * </p>
     */
    private AJAXResultPostProcessor dataPostProcessor;
    /**
     * <p>
     * Represents the charset of response. Defaults to "UTF-8".Can not be set to null/empty.
     * </p>
     */
    private String charset = "UTF-8";
    /**
     * <p>
     * Represents whether the response should be cache disabled. Defaults to false.
     * </p>
     *
     */
    private boolean noCache = false;
    /**
     * <p>
     * Represents the content type of response. Defaults to "application/json". Can not be set to null/empty.
     * </p>
     * <p>
     * Not using "application/json" to avoid prompt window.
     * </p>
     */
    private String contentType = "text/plain";

    /**
     * <p>
     * Represents whether the response should be compressed in GZip format. Defaults to false.
     * </p>
     */
    private boolean enabledGzip = false;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public CustomFormatAJAXResult() {
    }

    /**
     * <p>
     * This method gets the response data from the ValueStack and then: 1. Pre-processes the data. 2.
     * serializes it using the aggregated instance of the dataSerializer. 3. post processes the result. 4.
     * writes it to the response.
     * </p>
     *
     * @param invocation
     *            The action invocation to process.
     *
     * @throws IllegalArgumentException
     *             If given invocation is null (should not happen)
     * @throws AJAXDataPreProcessingException
     *             if the pre-processing failed.
     * @throws AJAXDataSerializationException
     *             if the serialization failed.
     * @throws AJAXDataPostProcessingException
     *             if the post-processing failed.
     * @throws IOException
     *             if IO error occurs during the action execution.
     */
    public void execute(ActionInvocation invocation) throws AJAXDataPreProcessingException,
            AJAXDataSerializationException, AJAXDataPostProcessingException, IOException {
        Helper.checkNull(invocation, "invocation");
        Object action = invocation.getAction();
        if (action instanceof AbstractAction) {
            Object data = null;
            String actionName = null;
            AggregateDataModel dataModel = ((AbstractAction) action).getModel();
            if (dataModel != null) {
                data = dataModel.getData(RESULT_DATA_KEY);
                actionName = dataModel.getAction();
            }

            if (dataPreProcessor != null) {
                data = dataPreProcessor.preProcessData(data);
            }
            String result = dataSerializer.serializeData(actionName, data);

            if (dataPostProcessor != null) {
                result = dataPostProcessor.postProcessData(result);
            }
            // Write the data back to the requester
            // the response will never be null
            HttpServletResponse response = ServletActionContext.getResponse();
            // Set response content type
            response.setContentType(contentType + ";charset=" + charset);
            if (noCache) {
                response.setHeader("Cache-Control", NO_CACHE);
                response.setHeader("Expires", "0");
                response.setHeader("Pragma", NO_CACHE);
            }

            // Create input stream and get response output stream:
            InputStream in = new ByteArrayInputStream(result.getBytes(charset));
            // the output stream should never be null
            OutputStream out = response.getOutputStream();

            // compress
            if (enabledGzip && isGzipAccepted(ServletActionContext.getRequest())) {
                // add "Content-Encoding" to response header
                response.addHeader("Content-Encoding", "gzip");
                // create GZip output stream
                out = new GZIPOutputStream(out);
            } else {
                // no need compress the result, set response content length
                response.setContentLength(result.getBytes(charset).length);
            }
            // Write input stream to output stream, and close the streams
            writeDataAndClose(in, out);
        }
    }

    /**
     * <p>
     * Write the data from the input stream to output stream, and close them.
     * </p>
     *
     * @param in
     *            the data come from
     * @param out
     *            the data to write
     * @throws IOException
     *             if any io error happens
     */
    private static void writeDataAndClose(InputStream in, OutputStream out) throws IOException {
        try {
            // Write input stream to output stream
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // flush output stream
            out.flush();
        } finally {
            // close the input stream
            closeStream(in);
            // close the output stream
            out.close();
        }
    }

    /**
     * <p>
     * Close a stream, and ignore the exception if it happens.
     * </p>
     *
     * @param stream
     *            the stream to close
     */
    private static void closeStream(Closeable stream) {
        try {
            stream.close();
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Setter for the dataSerializer field.
     * </p>
     *
     * @param dataSerializer
     *            the specific instance of AJAXDataSerializer to set
     *
     * @throws IllegalArgumentException
     *             If given dataSerializer is null.
     */
    public void setDataSerializer(AJAXDataSerializer dataSerializer) {
        Helper.checkNull(dataSerializer, "dataSerializer");
        this.dataSerializer = dataSerializer;
    }

    /**
     * <p>
     * Setter for the namesake field.
     * </p>
     *
     * @param charset
     *            the specific characters set to use
     * @throws IllegalArgumentException
     *             If given input is null or the charset is not supported.
     * @throws IllegalCharsetNameException
     *             If the given charset name is illegal
     */
    public void setCharset(String charset) {
        if (!Charset.isSupported(charset)) {
            throw new IllegalArgumentException("The charset [" + charset + "] is not supported");
        }
        this.charset = charset;
    }

    /**
     * <p>
     * Setter for the dataPreProcessor field.
     * </p>
     *
     * @param dataPreProcessor
     *            the specific instance of AJAXResultPreProcessor to use
     */
    public void setDataPreProcessor(AJAXResultPreProcessor dataPreProcessor) {
        this.dataPreProcessor = dataPreProcessor;
    }

    /**
     * <p>
     * Setter for the dataPostProcessor field.
     * </p>
     *
     * @param dataPostProcessor
     *            the specific instance of AJAXResultPostProcessor to use
     */
    public void setDataPostProcessor(AJAXResultPostProcessor dataPostProcessor) {
        this.dataPostProcessor = dataPostProcessor;
    }

    /**
     * <p>
     * Setter for the contentType field.
     * </p>
     *
     * @param contentType
     *            the content type to be used.
     * @throws IllegalArgumentException
     *             If given input is null/empty string.
     */
    public void setContentType(String contentType) {
        Helper.checkString(contentType, "contentType");
        this.contentType = contentType;
    }

    /**
     * <p>
     * Setter for the noCache field.
     * </p>
     *
     * @param noCache
     *            the flag to specific if cache should be used or not in the header.
     */
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;

    }

    /**
     * <p>
     * Setter for the enabledGzip field.
     * </p>
     *
     * @param enabledGzip
     *            true the the caller would like to use gzip compression (if available)
     */
    public void setEnabledGzip(boolean enabledGzip) {
        this.enabledGzip = enabledGzip;
    }

    /**
     * <p>
     * Getter for the dataSerializer field.
     * </p>
     *
     *
     * @return the dataSerializer
     */
    public AJAXDataSerializer getDataSerializer() {
        return dataSerializer;
    }

    /**
     * <p>
     * Getter for the dataPreProcessor field.
     * </p>
     *
     * @return the dataPreProcessor
     */
    public AJAXResultPreProcessor getDataPreprocessor() {
        return dataPreProcessor;
    }

    /**
     * <p>
     * Getter for the dataPostProcessor field.
     * </p>
     *
     * @return the dataPostProcessor
     */
    public AJAXResultPostProcessor getDataPostProcessor() {
        return dataPostProcessor;
    }

    /**
     * <p>
     * Getter for the charset field.
     * </p>
     *
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * <p>
     * Getter for the noCache field.
     * </p>
     *
     * @return the noCache
     */
    public boolean isNoCache() {
        return noCache;
    }

    /**
     * <p>
     * Getter for the contentType field.
     * </p>
     *
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * <p>
     * Getter for the enabledGzip field.
     * </p>
     *
     * @return the enabledGzip
     */
    public boolean isEnabledGzip() {
        return enabledGzip;
    }

    /**
     * <p>
     * Check whether the GZip encoding is accepted by client.
     * </p>
     *
     * @param request
     *            the request from the client
     * @return true if the GZip encoding is accepted by the client, false other wise (including the request in
     *         null)
     */
    private static boolean isGzipAccepted(HttpServletRequest request) {
        if (request != null) {
            String header = request.getHeader("Accept-Encoding");
            return (header != null) && (header.indexOf("gzip") >= 0);
        }
        return false;
    }
}
