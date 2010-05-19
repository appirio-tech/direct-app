/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.direct.services.view.action.contest.launch.AggregateDataModel;
import com.topcoder.direct.services.view.action.contest.launch.Helper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This interceptor will simply log exceptions. Basically, when the action is done it will intercept the result and
 * check if there is an AggregateDataModel instance on the value stack. If an AggregateDataModel instance is found,
 * there will be a result in it with key of <code>result</code>. If the result is a java.lang.Throwable instance, then
 * the interceptor will log its error message and stack trace using the Logging Wrapper.
 * </p>
 *
 * <p>
 * This class extends the AbstractInterceptor class. The name used for the logger is configurable.
 * </p>
 *
 * <p>
 * <strong>Demo:</strong>
 *
 * <pre>
 * // create the struts2 action proxy
 * ActionProxy proxy = getActionProxy(&quot;/login&quot;);
 * TestHelper.prepareActionProxy(proxy);
 *
 * // set the stack values for the proxy
 * ValueStack stack = proxy.getInvocation().getStack();
 * AggregateDataModel model = new AggregateDataModel();
 * model.setData(&quot;result&quot;, new Exception(&quot;this is a test exception&quot;));
 * stack.push(model);
 *
 * // call the intercept method and get the result (will be
 * // the login page)
 * String result = loggingInterceptorInstance.intercept(proxy.getInvocation());
 * System.out.println(&quot;result of loggingInterceptor is &quot; + result);
 *
 * // check the logged messages and find the
 * // exception that was logged by the interceptor
 * for (String msg : CustomAppender.getMessages()) {
 *     if (msg.equals(&quot;this is a test exception&quot;)) {
 *         System.out.println(&quot;found the logged exception message.&quot;);
 *     }
 * }
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Example configuration for struts2 (shows how to configure the interceptors and actions):</strong>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;!DOCTYPE struts PUBLIC
 *     &quot;-//Apache Software Foundation//DTD Struts Configuration 2.0//EN&quot;
 *     &quot;http://struts.apache.org/dtds/struts-2.0.dtd&quot;&gt;
 *
 * &lt;struts&gt;
 *     &lt;constant name=&quot;struts.devMode&quot; value=&quot;true&quot; /&gt;
 *
 *     &lt;package name=&quot;tcs-default&quot; extends=&quot;struts-default&quot; abstract=&quot;true&quot;&gt;
 *         &lt;!-- Setup interceptors stack --&gt;
 *         &lt;interceptors&gt;
 *             &lt;interceptor name=&quot;authentication&quot; class=&quot;authenticationInterceptor&quot; /&gt;
 *             &lt;interceptor name=&quot;logging&quot; class=&quot;loggingInterceptor&quot; /&gt;
 *
 *             &lt;interceptor-stack name=&quot;defaultTCSStack&quot;&gt;
 *                 &lt;interceptor-ref name=&quot;authentication&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;logging&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;defaultStack&quot;/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;!-- Make the default one used for all actions unless otherwise configured. --&gt;
 *         &lt;default-interceptor-ref name=&quot;defaultTCSStack&quot; /&gt;
 *
 *         &lt;!-- Configure global results for AuthenticationInterceptor --&gt;
 *         &lt;global-results&gt;
 *             &lt;result name=&quot;login&quot;&gt;/login.jsp&lt;/result&gt;
 *         &lt;/global-results&gt;
 *
 *     &lt;/package&gt;
 *
 *     &lt;package name=&quot;default&quot; namespace=&quot;/&quot; extends=&quot;tcs-default&quot;&gt;
 *
 *         &lt;interceptors&gt;
 *             &lt;!-- for the login stack, we exclude the authentication interceptor --&gt;
 *             &lt;interceptor-stack name=&quot;loginTCSStack&quot;&gt;
 *                 &lt;interceptor-ref name=&quot;logging&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;defaultStack&quot;/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;action name=&quot;login&quot; class=&quot;loginAction&quot;&gt;
 *             &lt;interceptor-ref name=&quot;loginTCSStack&quot; /&gt;
 *             &lt;result name=&quot;input&quot;&gt;/login.jsp&lt;/result&gt;
 *             &lt;result type=&quot;redirect&quot;&gt;employees&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *         &lt;action name=&quot;employees&quot; class=&quot;employeeAction&quot;&gt;
 *             &lt;result&gt;/employees.jsp&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *     &lt;/package&gt;
 *
 * &lt;/struts&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Example configuration (shows how to configure LoggingInterceptor bean in applicationContext.xml):</strong>
 *
 * <pre>
 * &lt;bean id=&quot;loggingInterceptor&quot; class=&quot;com.topcoder.service.interceptors.LoggingInterceptor&quot;&gt;
 *   &lt;constructor-arg index=&quot;0&quot;&gt;
 *     &lt;value&gt;strutsLoggerName&lt;/value&gt;
 *   &lt;/constructor-arg&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class LoggingInterceptor extends AbstractInterceptor {

    /**
     * The key to use when getting the result from the action.
     */
    private static final String ACTION_KEY_RESULT = "result";

    /**
     * <p>
     * Represents the logger attribute of the LoggingInterceptor.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It should be non-null after being set. The default value is null.
     * </p>
     */
    private Log logger;

    /**
     * Default constructor, constructs an instance of this class.
     */
    public LoggingInterceptor() {
        // does nothing
    }

    /**
     * Constructor which takes the logger name as an argument. It initializes the logger using the given argument.
     *
     * @param loggerName the name to use when initializing the logger
     *
     * @throws IllegalArgumentException if logger name is null/empty
     */
    public LoggingInterceptor(String loggerName) {
        setLogger(loggerName);
    }

    /**
     * <p>
     * This method provides the intercept logic.
     * </p>
     *
     * <p>
     * When the action is done it will intercept the result and check if there is an AggregateDataModel instance on
     * the value stack. If an AggregateDataModel instance is found, there will be a result in it with key of
     * <code>result</code>. If the result is a java.lang.Throwable instance, then the interceptor will log its error
     * message and stack trace using the Logging Wrapper.
     * </p>
     *
     * @param invocation the action invocation to intercept
     *
     * @return the result of the action invocation
     *
     * @throws IllegalStateException if the logger has not been injected (i.e. if it's null)
     *
     * @throws Exception if an error occurs when invoking the action
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (logger == null) {
            throw new IllegalStateException("logger has not been injected.");
        }

        // invoke the action and store the result
        String result = null;
        try {
            result = invocation.invoke();
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            throw e;
        }

        // get the object from the stack and see if it's an AggregateDataModel
        Object obj = invocation.getStack().peek();
        if (obj instanceof AggregateDataModel) {

            // see if there is an exception present
            Object exceptionObj = ((AggregateDataModel) obj).getData(ACTION_KEY_RESULT);
            if (exceptionObj instanceof Throwable) {
                // log the exception and stack trace
                logger.log(Level.ERROR, (Throwable) exceptionObj, ((Throwable) exceptionObj).getMessage());
            }
        }

        // return the result of the action invocation
        return result;
    }

    /**
     * Getter for the logger.
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Setter for the logger.
     *
     * @param loggerName the logger name to use when creating the logger
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setLogger(String loggerName) {
        Helper.checkNotNullOrEmpty(loggerName, "loggerName");
        logger = LogManager.getLog(loggerName);
    }
}
