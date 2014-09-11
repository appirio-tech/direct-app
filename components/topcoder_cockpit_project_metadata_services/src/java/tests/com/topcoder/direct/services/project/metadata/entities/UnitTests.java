/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.direct.services.project.metadata.entities.PersistenceTest;
import com.topcoder.direct.services.project.metadata.entities.dao.AuditEntityTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAuditTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAuditTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAuditTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueTest;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataTest;
import com.topcoder.direct.services.project.metadata.entities.dao.IdentifiableEntityTest;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProjectTest;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilterTest;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTOTest;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilterTest;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilterTest;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AuditEntityTest.class,
    DirectProjectMetadataTest.class,
    DirectProjectMetadataAuditTest.class,
    DirectProjectMetadataKeyTest.class,
    DirectProjectMetadataKeyAuditTest.class,
    DirectProjectMetadataPredefinedValueTest.class,
    DirectProjectMetadataPredefinedValueAuditTest.class,
    IdentifiableEntityTest.class,
    TcDirectProjectTest.class,
    CompositeFilterTest.class,
    DirectProjectMetadataDTOTest.class,
    MetadataKeyIdValueFilterTest.class,
    MetadataKeyNameValueFilterTest.class,
    PersistenceTest.class})
public class UnitTests extends TestCase {
    /**
     * <p>
     * Test suite.
     * </p>
     * @return the test suite
     */
    public static Test suite() {
        return new JUnit4TestAdapter(UnitTests.class);
    }

}
