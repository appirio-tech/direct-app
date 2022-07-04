/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactoryException;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import org.jboss.logging.Logger;

/**
 * <p>
 * ContestEligibilityValidationManagerBean is a stateless session bean as the default implementation of
 * <code>ContestEligibilityValidationManager</code>. It is used to validate the user against a list of
 * ContestEligibility entities.
 * </p>
 * <p>
 * It will for each ContestEligibility, invoke the corresponding ContestEligibilityValidator to check, for now
 * if any one of the eligibility check is true (OR), then it will return true (eligible). If the given list is
 * empty,then return true because empty list means there is no eligibility assign.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * ContestEligibilityValidationManager contestEligibilityValidationManager =
 *     (ContestEligibilityValidationManager) context
 *         .lookup(&quot;contest_eligibility_validation/ContestEligibilityValidationManagerBean/remote&quot;);
 * // create the eligibilities list.It contains one GroupContestEligibility with group id=5
 * GroupContestEligibility contestEligibility1 = new GroupContestEligibility();
 * contestEligibility1.setGroupId(5);
 * GroupContestEligibility contestEligibility2 = new GroupContestEligibility();
 * contestEligibility2.setGroupId(3);
 * List&lt;ContestEligibility&gt; eligibilities = new ArrayList&lt;ContestEligibility&gt;();
 * eligibilities.add(contestEligibility1);
 * eligibilities.add(contestEligibility2);
 * boolean result = contestEligibilityValidationManager.validate(5, eligibilities);
 * System.out.println(&quot;Result it:&quot; + result);
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is thread safe because all properties are immutable when they are set in
 * initialize method.
 * </p>
 *
 * <p>
 * Version 1.0.1 ((TopCoder Online Review Switch To Local Calls Assembly)) Change notes:
 *   <ol>
 *     <li>Updated the class to use JBoss Logging for logging the events to make the component usable in local
 *     environment for Online Review application.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.1
 */
@Stateless
public class ContestEligibilityValidationManagerBean implements ContestEligibilityValidationManagerLocal,
    ContestEligibilityValidationManagerRemote {

    /**
     * The logger is used to log the method.Never be null.It is always required.
     */
    private Logger logger;

    /**
     * Represents the log name.Default value is 'contest_eligibility_logger'.You also could change the default value
     * via deploy descriptor.Can not be null but can be empty if really needed.
     */
    @Resource(name = "logName")
    private String logName = "contest_eligibility_logger";

    /**
     * Represents the configuration file name.Default value is
     * 'ContestEligibilityValidationManagerBean.xml'.You also could change the default value via deploy
     * descriptor.Can not be null or empty.
     */
    @Resource(name = "configFileName")
    private String configFileName = "ContestEligibilityValidationManagerBean.xml";

    /**
     * Represents the configuration namespace.Default value is
     * 'com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerBean'.You also
     * could change the default value via deploy descriptor.Can not be null or empty.
     */
    @Resource(name = "namespace")
    private String namespace =
        "com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerBean";

    /**
     * Represents the validation map.It is not changed after setting in initialize method. The key is concrete
     * class name of contest eligibility entity,the value is its corresponding contest eligibility validator
     * instance. It will be used in validate method.Can be empty,but not null and contains null item.
     */
    private Map<String, ContestEligibilityValidator> validators =
        new HashMap<String, ContestEligibilityValidator>();

    /**
     * Default empty constructor.
     */
    public ContestEligibilityValidationManagerBean() {
        // does nothing
    }

    /**
     * Handle the post-construct event. It will initialize any internal needed properties.
     *
     * @throws ContestEligibilityValidationManagerConfigurationException
     *             if namespace or configFileName is empty or any errors occurred when initializing
     */
    @PostConstruct
    protected void initialize() {
        checkEmpty(namespace, "namespace");
        checkEmpty(configFileName, "configFileName");
        // note logName can be empty if user really need,it will not raise IAE
        logger = Logger.getLogger(this.logName);
        try {
            ConfigurationFileManager manager = new ConfigurationFileManager();
            manager.loadFile(namespace, configFileName);
            ConfigurationObject config = manager.getConfiguration(namespace);

            ConfigurationObject root = getRequiredChildConfigurationObject(config, namespace);

            // object_factory_config configuration
            ConfigurationObject objectFactoryConfig =
                getRequiredChildConfigurationObject(root, "object_factory_config");

            // Create configuration object specification factory
            ConfigurationObjectSpecificationFactory cosf =
                new ConfigurationObjectSpecificationFactory(objectFactoryConfig);

            // Create object factory
            ObjectFactory objectFactory = new ObjectFactory(cosf);

            ConfigurationObject validatorsObj = getRequiredChildConfigurationObject(root, "validators");

            ConfigurationObject[] objKeys = validatorsObj.getAllChildren();
            // initialize validators map
            for (ConfigurationObject configurationObject : objKeys) {
                String validatorObjKey = getRequiredProperty(configurationObject, "validator_obj_key");
                String entityName = getRequiredProperty(configurationObject, "entity_name");
                ContestEligibilityValidator validator =
                    (ContestEligibilityValidator) objectFactory.createObject(validatorObjKey);
                if (validators.containsKey(entityName)) {
                    throw new ContestEligibilityValidationManagerConfigurationException("The validator -"
                        + validator.getClass().getName() + " has been existed.");
                }
                validators.put(entityName, validator);
            }
        } catch (InvalidClassSpecificationException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "The spceification is not valid and can't be used to create an object.", e);
        } catch (ConfigurationPersistenceException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "Any errors occur when parsing the configuration file to configuration object.", e);
        } catch (ConfigurationAccessException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "Any errors occured when using configuration object.", e);
        } catch (SpecificationFactoryException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "Any errors occured when createing object using object factory.", e);
        } catch (ClassCastException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "Created object type from the Object Factory is should be ContestEligibilityValidator.", e);
        } catch (IOException e) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "Any IO errors occur when parsing the configuration file.", e);
        }
    }

    /**
     * <p>
     * Validate the user against a list of ContestEligibility entities.
     * </p>
     * <p>
     * It will for each ContestEligibility, invoke the corresponding ContestEligibilityValidator to check, for
     * now if any one of the eligibility check is true (OR), then it will return true (eligible). If the given
     * list is empty,then return true because empty list means there is no eligibility assign.
     * </p>
     *
     * @param userId
     *            the id of user
     * @param eligibilities
     *            a list of ContestEligibility entities
     * @return true if any one of the eligibility check is true or the given list is empty,otherwise false
     * @throws IllegalArgumentException
     *             if eligibilities is null or eligibilities contains null item
     * @throws ContestEligibilityValidationManagerException
     *             if any errors occurred when validating
     * @throws UnsupportedContestEligibilityValidatiorException
     *             if there is no corresponding validator for its ContestEligibility type in validators map
     */
    public boolean validate(long userId, List<ContestEligibility> eligibilities)
        throws ContestEligibilityValidationManagerException {
        logEntrance("ContestEligibilityValidationManagerBean#validate", new String[] {"userId",
            "eligibilities"}, new Object[] {userId, eligibilities});
        checkNull(eligibilities, "eligibilities");
        for (ContestEligibility contestEligibility : eligibilities) {
            checkNull(contestEligibility, "contestEligibility item in eligibilities list");
            if (!validators.containsKey(contestEligibility.getClass().getName())) {
                throw logError(new UnsupportedContestEligibilityValidatiorException(
                    "There is no corresponding validator for contestEligibility -"
                        + contestEligibility.getClass().getName()));
            }
        }

        boolean result = false;
        if (eligibilities.size() == 0) {
            result = true;
        } else {
            for (ContestEligibility contestEligibility : eligibilities) {
                ContestEligibilityValidator validator =
                    validators.get(contestEligibility.getClass().getName());
                try {
                    if (validator.validate(userId, contestEligibility)) {
                        result = true;
                        break;
                    }
                } catch (ContestEligibilityValidatorException e) {
                    throw logError(new ContestEligibilityValidationManagerException(
                        "Any errors occurred when validating the contestEligibility.", e));
                }
            }
        }
        logExit("ContestEligibilityValidationManagerBean#validate");
        return result;
    }

    /**
     * <p>
     * Logs the error.
     * </p>
     *
     * @param <T>
     *            the generic class type of error
     * @param error
     *            the error needs to be logged.
     * @return the error
     */
    private <T extends Exception> T logError(T error) {
        logger.error("Error recognized: " + error.getMessage(), error);
        return error;
    }

    /**
     * <p>
     * Log the entrance of a method and all the input arguments.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param paramNames
     *            the name of the parameters
     * @param params
     *            the parameters
     */
    @SuppressWarnings("unchecked")
    private void logEntrance(String methodName, String[] paramNames, Object[] params) {
        logger.debug("Enter into Method: " + methodName + " At " + new Date());
        if (paramNames != null) {
            StringBuilder logInfo = new StringBuilder("Parameters:");
            for (int i = 0; i < paramNames.length; i++) {
                if (params[i] instanceof List && ((List<ContestEligibility>) params[i]).size() != 0) {
                    List<ContestEligibility> list = (List<ContestEligibility>) params[i];
                    StringBuilder paramLog = new StringBuilder();
                    for (ContestEligibility contestEligibility : list) {
                        if (contestEligibility == null) {
                            paramLog.append("  null");
                            continue;
                        }
                        paramLog.append(contestEligibility.getClass().getName() + " with id="
                            + contestEligibility.getId() + "  ");
                    }
                    logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
                    continue;
                }
                logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
            }
            logger.info(logInfo);
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logExit(String methodName) {
        logger.debug("Exit out Method: " + methodName + " At " + new Date());
    }

    /**
     * <p>
     * Get the required child configuration object by given key.
     * </p>
     *
     * @param parentObj
     *            the parent configuration object used to retrieve the child
     * @param key
     *            the name of child configuration object
     * @return the child configuration object
     * @throws ConfigurationAccessException
     *             any errors occurred when retrieving the child configuration object
     * @throws ContestEligibilityValidationManagerConfigurationException
     *             if the child configuration object does not exist
     */
    private static ConfigurationObject getRequiredChildConfigurationObject(ConfigurationObject parentObj,
        String key) throws ConfigurationAccessException,
        ContestEligibilityValidationManagerConfigurationException {
        if (!parentObj.containsChild(key)) {
            throw new ContestEligibilityValidationManagerConfigurationException(
                "The child configuration named " + key + " under " + parentObj.getName()
                    + " element should exist.");
        }
        return parentObj.getChild(key);
    }

    /**
     * <p>
     * Get the required non-empty string property value by given key.
     * </p>
     *
     * @param co
     *            the configuration object used to retrieve the property
     * @param key
     *            the property key
     * @return the value of property
     * @throws ConfigurationAccessException
     *             any errors occurred when retrieving the property
     * @throws ContestEligibilityValidationManagerConfigurationException
     *             if the property does not exist
     */
    private static String getRequiredProperty(ConfigurationObject co, String key)
        throws ConfigurationAccessException, ContestEligibilityValidationManagerConfigurationException {
        if (!co.containsProperty(key)) {
            throw new ContestEligibilityValidationManagerConfigurationException("The property named " + key
                + " under " + co.getName() + " element should exist.");
        }
        String value = (String) co.getPropertyValue(key);
        if (value.trim().length() == 0) {
            throw new ContestEligibilityValidationManagerConfigurationException("The property named '" + key
                + " under " + co.getName() + " cannot be empty.");
        }
        return value;
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private void checkNull(Object arg, String name) {
        if (arg == null) {
            IllegalArgumentException e =
                new IllegalArgumentException("The argument " + name + " cannot be null.");
            throw logError(e);
        }
    }

    /**
     * <p>
     * Checks whether the given Object is empty.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws ContestEligibilityValidationManagerConfigurationException
     *             if the given Object is empty
     */
    private static void checkEmpty(String arg, String name) {
        if (arg.trim().length() == 0) {
            throw new ContestEligibilityValidationManagerConfigurationException("The argument " + name
                + " cannot be empty.");
        }
    }
}