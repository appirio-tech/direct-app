/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.failuretests;

import java.util.Properties;

import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.catalog.entity.IdGenerator;
import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.idgenerator.ejb.IDGeneratorBean;
import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;

/**
 * Failure tests for IdGenerator class.
 *
 * @author extra
 * @version 1.0
 */
public class IdGeneratorFailureTests extends TestCase {
    /**
     * Represents instance of IdGenerator for test.
     */
    private IdGenerator generator;

    /**
     * Represents instance of Properties for test.
     */
    private Properties params;

    /**
     * Sets up the environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        MockContextFactory.setAsInitial();
        SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("IDGeneratorBean/home",
                IDGeneratorHome.class, IDGenerator.class, new IDGeneratorBean());
        new MockContainer(new InitialContext()).deploy(beanDescriptor);
        generator = new IdGenerator();
        params = new Properties();
    }

    /**
     * Tears down the environment.
     */
    protected void tearDown() {
        MockContextFactory.revertSetAsInitial();
        generator = null;
        params = null;
    }

    /**
     * Tests constructor method for failure. Without
     * java:ejb/IDGeneratorHome bound, HibernateException should be thrown.
     */
    public void testConstructorFail1() {
        MockContextFactory.revertSetAsInitial();
        try {
            generator = new IdGenerator();
            fail("HibernateException should be thrown. without java:ejb/IDGeneratorHome bound.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests constructor method for failure. With java:ejb/IDGeneratorHome
     * bound incorrect class, HibernateException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorFail2() throws Exception {
        MockContextFactory.revertSetAsInitial();
        MockContextFactory.setAsInitial();
        SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor("java:ejb/IDGeneratorHome",
                FailIDGeneratorHome.class, FailIDGenerator.class, new FailIDGeneratorBean());
        new MockContainer(new InitialContext()).deploy(beanDescriptor);
        try {
            generator = new IdGenerator();
            fail("HibernateException should be thrown. without java:ejb/IDGeneratorHome bound.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method configure(Type type, Properties params, Dialect d) for
     * failure. With empty params, HibernateException should be thrown.
     */
    public void testConfigureFail1() {
        try {
            generator.configure((Type) null, params, (Dialect) null);
            fail("HibernateException should be thrown. With empty params.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method configure(Type type, Properties params, Dialect d) for
     * failure. The params contains not String value with key sequence_name,
     * HibernateException should be thrown.
     */
    public void testConfigureFail2() {
        params.put("sequence_name", new Object());
        try {
            generator.configure((Type) null, params, (Dialect) null);
            fail("HibernateException should be thrown. With empty params.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method configure(Type type, Properties params, Dialect d) for
     * failure. The params contains empty String value with key sequence_name,
     * HibernateException should be thrown.
     */
    public void testConfigureFail3() {
        params.put("sequence_name", "  \r\t");
        try {
            generator.configure((Type) null, params, (Dialect) null);
            fail("HibernateException should be thrown. With empty params.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method configure(Type type, Properties params, Dialect d) for
     * failure. With null params, HibernateException should be thrown.
     */
    public void testConfigureFail4() {
        params = null;
        try {
            generator.configure((Type) null, params, (Dialect) null);
            fail("HibernateException should be thrown. With empty params.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method generate(SessionImplementor session, Object object) for
     * failure. With seqName not configured, HibernateException should be
     * thrown.
     */
    public void testGenerateFail1() {
        try {
            generator.generate((SessionImplementor) null, (Object) null);
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * Tests method generate(SessionImplementor session, Object object) for
     * failure. With seqName not exist in the table, HibernateException should
     * be thrown.
     */
    public void testGenerateFail2() {
        params.put("sequence_name", "not_existed");
        generator.configure((Type) null, params, (Dialect) null);
        try {
            generator.generate((SessionImplementor) null, (Object) null);
        } catch (HibernateException e) {
            // expected
        }
    }

}
