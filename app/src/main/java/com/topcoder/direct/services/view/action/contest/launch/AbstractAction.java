/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * The AbstractAction provides common action functionality for actions. All actions will extend
 * AbstractAction. It holds the model (it's of type AggregateDataModel) and action attributes.
 * </p>
 *
 * <p>
 * The AbstractAction extends the struts2 ActionSupport class. Methods are provided in this
 * class to set and get the model and action name.
 * </p>
 *
 * <p>
 * <strong>GUI Demo using JWebUnit
 * (shows how LoginAction and EmployeeAction are used for login and for viewing employees):</strong>
 * <pre>
 * tester.beginAt("/login");
 *
 * // try going to the employees page, but since
 * // we haven't logged in we will be forced to the login
 * // page
 * tester.gotoPage("/employees");
 *
 * // make sure we ended up at the login page
 * tester.assertTextPresent("Login Name");
 *
 * // perform the login
 * tester.setTextField("loginName", "topcoder");
 * tester.setTextField("password", "password");
 * tester.submit();
 *
 * // now we should be at the employees page
 * tester.assertTextInTable("empTable", "John");
 * tester.assertTextInTable("empTable", "Jane");
 * tester.assertTextInTable("empTable", "Doe");
 *
 * // now that we've been authenticated, we should
 * // be able to go directly to the employees
 * // page without having to login again
 * tester.gotoPage("/employees");
 * tester.assertTextInTable("empTable", "John");
 * tester.assertTextInTable("empTable", "Jane");
 * tester.assertTextInTable("empTable", "Doe");
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Example configuration (shows how to configure LoginAction bean in applicationContext.xml):</strong>
 * <pre>
 * &lt;bean id="loginAction" class="com.topcoder.service.actions.LoginAction"
 *   scope="prototype"&gt;
 *   &lt;property name="action" value="loginAction"&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractAction extends ActionSupport {

    /**
     * <p>
     * Represents the model attribute of the AbstractAction entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private AggregateDataModel model;

    /**
     * <p>
     * Represents the action attribute of the AbstractAction entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private String action = this.getClass().getName();

    /**
     * Default constructor, constructs an instance of this class.
     */
    protected AbstractAction() {
        // does nothing
    }

    /**
     * Getter for the model.
     *
     * @return the model
     */
    public AggregateDataModel getModel() {
        return model;
    }

    /**
     * Setter for the model. This set method does not perform any check on the argument.
     *
     * @param model the model to set to the AbstractAction
     */
    public void setModel(AggregateDataModel model) {
        this.model = model;
    }

    /**
     * Getter for the action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for the action. This set method does not perform any check on the argument.
     *
     * @param action the action to set to the AbstractAction
     */
    public void setAction(String action) {
        this.action = action;
    }
}
