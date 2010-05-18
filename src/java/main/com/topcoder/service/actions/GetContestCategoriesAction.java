/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

/**
 * <p>
 * This action will get the available contest categories.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure bean in applicationContext.xml for Spring:</strong>
 * <pre>
 * &lt;bean id="getContestCategoriesAction"
 *     class="com.topcoder.service.actions.GetContestCategoriesAction"&gt;
 *     &lt;property name="contestServiceFacade" ref="contestServiceFacade"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="getContestCategoriesAction" class="getContestCategoriesAction"&gt;
 *     &lt;interceptor-ref name="demoTCSStack" /&gt;
 *     &lt;result name="success"&gt;/success.jsp?type=getContestCategories&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the
 * actions (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class GetContestCategoriesAction extends ContestAction {

    /**
     * Default constructor, creates new instance.
     */
    public GetContestCategoriesAction() {
    }

    /**
     * Executes the action. Stores all available categories in the model.
     *
     * @throws Exception if some error occurs during method execution
     * @throws IllegalStateException if <code>contestServiceFacade</code> hasn't been injected
     */
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
        setResult(getContestServiceFacade().getActiveCategories(null));
    }
}
