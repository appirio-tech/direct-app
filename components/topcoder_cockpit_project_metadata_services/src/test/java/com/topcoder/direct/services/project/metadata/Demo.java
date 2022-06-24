/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager em;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        em = TestsHelper.getEntityManager();
        TestsHelper.clearDB(em);
        TestsHelper.loadDB(em);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(em);
        em = null;
    }

    /**
     * <p>
     * Demo API usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemo() throws Exception {
        // Initialize application context
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve services
        DirectProjectMetadataService metadataService =
            (DirectProjectMetadataService) ctx.getBean("directProjectMetadataService");
        DirectProjectMetadataKeyService metadataKeyService =
            (DirectProjectMetadataKeyService) ctx.getBean("directProjectMetadataKeyService");

        // Consider the user id is retrieved from session
        int userId = 1;

        // Persist key entity
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setName("name3");
        key.setDescription("some text");
        key.setGrouping(null);
        key.setClientId(null);
        key.setSingle(true);

        // Create project metadata key
        long keyId = metadataKeyService.createProjectMetadataKey(key, userId);

        // Persist metadata entity
        DirectProjectMetadata metadata = new DirectProjectMetadata();
        metadata.setTcDirectProjectId(5);
        metadata.setProjectMetadataKey(key);
        metadata.setMetadataValue("value");

        // Create project metadata
        long metadataId = metadataService.createProjectMetadata(metadata, userId);

        // Update metadata entity
        metadata.setTcDirectProjectId(8);
        metadataService.updateProjectMetadata(metadata, userId);

        // Delete metadata entity
        metadataService.deleteProjectMetadata(1, userId);

        // Retrieve project metadata by id
        metadataService.getProjectMetadata(metadataId);

        DirectProjectMetadataDTO projectMetadata = new DirectProjectMetadataDTO();
        projectMetadata.setProjectMetadataKeyId(key.getId());
        projectMetadata.setMetadataValue("value");
        // Add project metadata list
        metadataService.addProjectMetadata(new long[] {7, 9}, projectMetadata, userId);

        // Search projects
        MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
        filter.setProjectMetadataKeyName("name2");
        filter.setMetadataValue("sec");
        filter.setMetadataValueOperator(MetadataValueOperator.LIKE);
        List<TcDirectProject>  projects = metadataService.searchProjects(filter);

        // Project with id 1 should be returned
    }
}
