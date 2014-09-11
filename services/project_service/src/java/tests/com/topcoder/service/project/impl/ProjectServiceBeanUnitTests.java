/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.security.TCSubject;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.BaseTestCase;
import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * Unit test for <code>{@link ProjectServiceBean}</code> class.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.1
 */
public class ProjectServiceBeanUnitTests extends BaseTestCase {

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit User</code> role.</p>
     * 
     * @since 1.1
     */
    public static final long USER_ID = 132456L;

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit Administrator</code> role.</p>
     * 
     * @since 1.1
     */
    public static final long ADMIN_ID = 132458L;

    /**
     * <p>A <code>TCSubject</code> for user account.</p>
     *
     * @since 1.1
     */
    private TCSubject user = new TCSubject(USER_ID);

    /**
     * <p>A <code>TCSubject</code> for admin account.</p>
     *
     * @since 1.1
     */
    private TCSubject admin = new TCSubject(ADMIN_ID);

    /**
     * <p>
     * Represents the <code>ProjectService</code> instance for testing.
     * </p>
     */
    private ProjectService projectService;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectServiceBeanUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectService = (ProjectService) ctx.lookup("remote/ProjectServiceBean");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        executeScriptFile("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property is missing, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_Missing() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanMissingPersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_Empty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanEmptyPersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_TrimmedEmpty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanTrimmedEmptyPersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property is invalid class name, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_InvalidClass() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanInvalidPersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property type is invalid, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_InvalidEnvType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanInvalidEnvTypePersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'project_persistence_class' property is not ProjectPersistence type, should throw
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_InvalidType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanInvalidTypePersistenceClass/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If failing to create the project persistence class, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_PersistenceClass_UnableConstruct() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanPersistenceClassFailConstruct/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_LogName_Missing() throws Exception {
        projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanMissingLogName/remote");

        // need to invoke one business method to make the ejb initialized.
        projectService.getAllProjects();
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'log_name' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_LogName_Empty() throws Exception {
        try {
            projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanEmptyLogName/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'log_name' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_LogName_TrimmedEmpty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanTrimmedEmptyLogName/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'log_name' property type is invalid, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_LogName_InvalidEnvType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanLogNameInvalidEnvType/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'roles_key' property is missing, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_RolesKey_Missing() throws Exception {
        try {
            projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanMissingRolesKey/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'roles_key' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_RolesKey_Empty() throws Exception {
        try {
            projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanEmptyRolesKey/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'roles_key' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_RolesKey_TrimmedEmpty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanTrimmedEmptyRolesKey/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'roles_key' property type is invalid, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_RolesKey_InvalidEnvType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanRolesKeyInvalidEnvType/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'administrator_role' property is missing, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_AdministratorRole_Missing() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanMissingAdministratorRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'administrator_role' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_AdministratorRole_Empty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanEmptyAdministratorRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'administrator_role' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_AdministratorRole_TrimmedEmpty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanTrimmedEmptyAdministratorRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'administrator_role' property type is invalid, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_AdministratorRole_InvalidEnvType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanAdministratorRoleInvalidEnvType/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'user_role' property is missing, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_UserRole_Missing() throws Exception {
        try {
            projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanMissingUserRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'user_role' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_UserRole_Empty() throws Exception {
        try {
            projectService = (ProjectService) ctx.lookup("project_service/ProjectServiceBeanEmptyUserRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'user_role' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_UserRole_TrimmedEmpty() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanTrimmedEmptyUserRole/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     * <p>
     * If the required 'user_role' property type is invalid, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_UserRole_InvalidEnvType() throws Exception {
        try {
            projectService = (ProjectService) ctx
                    .lookup("project_service/ProjectServiceBeanUserRoleInvalidEnvType/remote");

            // need to invoke one business method to make the ejb initialized.
            projectService.getAllProjects();
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct." + e.getCause(), e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#ProjectServiceBean()}</code> constructor.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testProjectServiceBean_accuracy() throws Exception {
        // need to invoke one business method to make the ejb initialized.
        projectService.getAllProjects();
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#createProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed project data is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_null() throws Exception {
        try {
            projectService.createProject(user, null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#createProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_nullName() throws Exception {
        try {
            projectService.createProject(admin, new ProjectData());
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#createProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_emptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("");

        try {
            projectService.createProject(user, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#createProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is trimmed empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_TrimmedEmptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("  ");

        try {
            projectService.createProject(admin, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#createProject(TCSubject, ProjectData)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_accuracy() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        ProjectData newPrjData = projectService.createProject(user, projectData);

        assertNotNull("Never return null.", newPrjData);
        assertNotNull("The project id should be updated.", newPrjData.getProjectId());

        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id=?");
            pstmt.setLong(1, newPrjData.getProjectId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("Incorrect name", "Project Service", rs.getString("name"));
            assertEquals("Incorrect description", newPrjData.getDescription(), rs.getString("description"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for the <code>{@link ProjectServiceBean#getProjectByName(String, long)}</code> method.
     * </p>
     * <p>
     * If the project is not found the method should throw <code>ProjectNotFoundFault</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit
     */
    public void testGetProjectByNameNotFound() throws Exception {
        try {
            projectService.getProjectByName("foo", 1);
            fail("ProjectNotFoundFault expected.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProject(TCSubject, long)}</code> method.
     * </p>
     * <p>
     * If the project is not present in persistence, should throw ProjectNotFoundFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_NotFound() throws Exception {
        try {
            projectService.getProject(user, 1);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProject(TCSubject, long)}</code> method.
     * </p>
     * <p>
     * If the project is not associated with the current user, should throw AuthorizationFailedFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_AuthorizationFailed() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        try {
            projectService.getProject(admin, 1);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProject(TCSubject, long)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_accuracy2() throws Exception {
        // current the user id is 0.
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 0, '2008-02-20 12:53:45')"});

        ProjectData projectData = projectService.getProject(user, 1);
        assertNotNull("Should not return null.", projectData);
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProject(TCSubject, long)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_accuracy() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");

        // current the user id is 0.
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 0, '2008-02-20 12:53:45')"});

        ProjectData projectData = projectService.getProject(user, 1);
        assertNotNull("Should not return null.", projectData);
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getAllProjects()}</code> method.
     * </p>
     * <p>
     * If the login user is not administrator, should return its own projects.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllProjects_NotAdministrator() throws Exception {
        executeSQL(new String[] {
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (2, 'Project Service', 0, '2008-02-20 12:53:45')"});

        List<ProjectData> projectDatas = projectService.getAllProjects();

        assertNotNull("Null is not expected.", projectDatas);
        assertEquals("The return list size is incorrect.", 1, projectDatas.size());
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getAllProjects()}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllProjects_Administrator() throws Exception {
        executeSQL(new String[] {
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (2, 'Project Service', 0, '2008-02-20 12:53:45')"});

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");

        List<ProjectData> projectDatas = projectService.getAllProjects();

        assertNotNull("Null is not expected.", projectDatas);
        assertEquals("The return list size is incorrect.", 2, projectDatas.size());
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     * <p>
     * If the login user is not administrator, ejb container will do authorization check.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_NotAdministrator() throws Exception {
        try {
            projectService.getProjectsForUser(1);
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     * <p>
     * If no project found for the specified user, should throw UserNotFoundFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_UserNotFound() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");

        try {
            projectService.getProjectsForUser(1);
            fail("Expect UserNotFoundFault");
        } catch (UserNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_accuracy() throws Exception {
        executeSQL(new String[] {
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (2, 'Project Service', 0, '2008-02-20 12:53:45')"});

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");

        List<ProjectData> projectDatas = projectService.getProjectsForUser(2);

        assertNotNull("Null is not expected.", projectDatas);
        assertEquals("The return list size is incorrect.", 1, projectDatas.size());
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed project data is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_null() throws Exception {
        try {
            projectService.updateProject(user, null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed project id is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullProjectId() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Hello");
        try {
            projectService.updateProject(admin, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(user, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_emptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);
        projectData.setName("");

        try {
            projectService.updateProject(admin, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is trimmed empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_TrimmedEmptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);
        projectData.setName("  ");

        try {
            projectService.updateProject(user, projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the project is not found, should throw ProjectNotFoundFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotFound() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(admin, projectData);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     * <p>
     * If the project is not owned by the user, should throw AuthorizationFailedFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotOwned() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(user, projectData);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#updateProject(TCSubject, ProjectData)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_accuracy() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 0, '2008-02-20 12:53:45')"});

        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        projectData.setProjectId(1L);

        projectService.updateProject(admin, projectData);

        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id=?");
            pstmt.setLong(1, projectData.getProjectId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("Incorrect name", "Project Service", rs.getString("name"));
            assertEquals("Incorrect description", projectData.getDescription(), rs.getString("description"));
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#deleteProject(TCSubject, long)}</code> method.
     * </p>
     * <p>
     * If the project to delete does not exist, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_NotFound() throws Exception {
        assertFalse("Should return false.", projectService.deleteProject(admin, 1L));
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#deleteProject(TCSubject, long)}</code> method.
     * </p>
     * <p>
     * If user is not authorized to delete the project, should throw AuthorizationFailedFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_NotAuthorized() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 1, '2008-02-20 12:53:45')"});

        try {
            projectService.deleteProject(user, 1L);
            fail("Expect AuthorizationFailedFault");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link ProjectServiceBean#deleteProject(TCSubject, long)}</code> method.
     * </p>
     * <p>
     * If user is associated with project, should delete it.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_accuracy() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 0, '2008-02-20 12:53:45')"});

        assertTrue("project should be deleted.", projectService.deleteProject(admin, 1L));
    }
}
