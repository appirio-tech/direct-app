/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.service.AssetDTO;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Bug fix test case for <code>AssetDTO</code>.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class BugFixTest extends TestCase {
    /**
     * <p>
     * Represents <code>AssetDTO</code> instance for test.
     * </p>
     */
    private AssetDTO assetDTO;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        assetDTO = new AssetDTO();
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDependencies()</code>
     * and <code>getDependencies()</code>.
     * </p>
     */
    public void testAssetDTO_GetSetDependencies() {
        List<Long> dependencies = new ArrayList<Long>();
        assetDTO.setDependencies(dependencies);
        assertEquals("The dependencies should be set correctly.", dependencies,
            assetDTO.getDependencies());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setComments()</code>
     * and <code>getComments()</code>.
     * </p>
     */
    public void testAssetDTO_GetSetComments() {
        assetDTO.setComments("comments");
        assertEquals("The comments should be set correctly.", "comments",
            assetDTO.getComments());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPhase()</code>
     * and <code>getPhase()</code>.
     * </p>
     */
    public void testAssetDTO_GetSetPhase() {
        assetDTO.setPhase("phase");
        assertEquals("The phase should be set correctly.", "phase",
            assetDTO.getPhase());
    }
}
