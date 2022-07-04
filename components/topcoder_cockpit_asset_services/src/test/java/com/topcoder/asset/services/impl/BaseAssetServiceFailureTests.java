/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import com.topcoder.asset.exceptions.AssetConfigurationException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseAssetService.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseAssetServiceFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseAssetServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseAssetService#checkInit() for failure.
     * It tests the case that when entityManager is null and expects AssetConfigurationException.
     * </p>
     */
    public void testCheckInit_Failure() {
        BaseAssetService instance = new BaseAssetService() {
        };
        try {
            instance.checkInit();
            fail("AssetConfigurationException expected.");
        } catch (AssetConfigurationException e) {
            //good
        }
    }

}