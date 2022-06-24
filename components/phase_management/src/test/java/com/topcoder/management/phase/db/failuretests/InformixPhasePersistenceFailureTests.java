/*
 * Copyright (C) 2006 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.db.AbstractInformixPhasePersistence;
import com.topcoder.management.phase.db.InformixPhasePersistence;


/**
 * Failure test cases for the class InformixPhasePersistence.
 *
 * @author waits
 * @version 1.1
 * @since Apr 17, 2007
 */
public class InformixPhasePersistenceFailureTests extends BasePersistenceSupport {

	/**
	 * Create InformixPhasePersistence instance.
	 */
    protected AbstractInformixPhasePersistence getPersistence() throws Exception{
    	return new InformixPhasePersistence(TestHelper.INFORMIX_PHASE_PERSISTENCE_NAMESPACE);
	}
    

    /**
     * Test the ctor with null namespace, iae expected.
     *
     * @throws ConfigurationException into JUnit
     */
    public void testCtor_nullNamespace() throws ConfigurationException {
        try {
            new InformixPhasePersistence(null);
            fail("The namespace is null");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the ctor with empty namespace, iae expected.
     *
     * @throws ConfigurationException into JUnit
     */
    public void testCtor_emptyNamespace() throws ConfigurationException {
        try {
            new InformixPhasePersistence(" ");
            fail("The namespace is empty");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test the InformixPhasePersistence(DBConnectionFactory connectionFactory, String connectionName, IDGenerator
     * idGen) method, with null connectionFactory, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullConnFactory() throws Exception {
        try {
            new InformixPhasePersistence(null, CONN_NAME, this.createIDGenerator());
            fail("The DBConnectionFactory instance is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixPhasePersistence(DBConnectionFactory connectionFactory, String connectionName, IDGenerator
     * idGen) method, with empty connectionName, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_emptyConnName() throws Exception {
        DBConnectionFactory factory = this.createDBConnectionFactory();

        try {
            new InformixPhasePersistence(factory, " ", this.createIDGenerator());
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixPhasePersistence(DBConnectionFactory connectionFactory, String connectionName, IDGenerator
     * idGen) method, with null connectionFactory, iae expected
     *
     * @throws Exception into Junit
     */
    public void testCtor_nullIDGenerator() throws Exception {
        try {
            new InformixPhasePersistence(null, " ", this.createIDGenerator());
            fail("The connectionName instance is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the InformixPhasePersistence's ctor with invalid configuraton. ConfigurationException expected.
     *
     * @param namespace
     */
    private void createInstanceWithInvalidConfiguration(String namespace) {
        try {
            new InformixPhasePersistence(namespace);
            fail("The configuration with the namespace is invalid.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * Test the ctor with invalid configuration, the property 'Idgenerator ' is missing, ConfigurationException
     * expected.
     */
    public void testCtor_invalidConfiguration1() {
        createInstanceWithInvalidConfiguration("failure4");
    }

    /**
     * Test the ctor with invalid configuration, the property ' ConnectionFactory' is missing, ConfigurationException
     * expected.
     */
    public void testCtor_invalidConfiguration2() {
        createInstanceWithInvalidConfiguration("failure1");
    }

    /**
     * Test the ctor with invalid configuration, the property ' ConnectionFactory.className' is missing,
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration3() {
        createInstanceWithInvalidConfiguration("failure2");
    }

    /**
     * Test the ctor with invalid configuration, the property 'ConnectionFactory.namespace ' is missing,
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration4() {
        createInstanceWithInvalidConfiguration("failure3");
    }

    /**
     * Test the ctor with invalid configuration, the property 'Idgenerator.sequenceName ' is missing,
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration5() {
        createInstanceWithInvalidConfiguration("failure5");
    }

    /**
     * Test the ctor with invalid configuration, the property 'Idgenerator.className ' is invalid,
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration7() {
        createInstanceWithInvalidConfiguration("failure7");
    }

    /**
     * Test the ctor with invalid configuration, the property 'ConnectionFactory.namespace ' is invalid,
     * ConfigurationException expected.
     */
    public void testCtor_invalidConfiguration8() {
        createInstanceWithInvalidConfiguration("failure8");
    }

	

   
}
