package com.topcoder.direct.services.project.milestone.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * Accuracy unit tests for class implementation of {@link ResponsiblePersonService}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class ResponsiblePersonServiceImplUnitTests extends BaseAccuracyTest {
    /**
     * <p>
     * private ResponsiblePersonService instance for testing.
     * </p>
     */
    private ResponsiblePersonService instance;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ResponsiblePersonServiceImplUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = (ResponsiblePersonService) APP_CONTEXT.getBean("responsiblePersonService");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for constructor.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link ResponsiblePersonService#getAllResponsiblePeople(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetAllResponsiblePeople() throws Exception {
        List<ResponsiblePerson> list =  instance.getAllResponsiblePeople(1);
        assertEquals(3, list.size());
        assertEquals("handle1", list.get(0).getName());
        assertEquals(654321, list.get(0).getUserId());
        assertEquals("handle1", list.get(1).getName());
        assertEquals(1, list.get(1).getUserId());
        assertEquals("handle2", list.get(2).getName());
        assertEquals(2, list.get(2).getUserId());
    }
}
