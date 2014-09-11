/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This the demo of this component.
 *
 * @author iamajia, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    protected void tearDown() throws Exception {
        // remove the namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * General usage demo and manipulating project properties.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    @SuppressWarnings("unused")
    public void testDemo1() throws Exception {
        // create manager using default namespace
        ProjectManager manager = new ProjectManagerImpl();

        // project types, categories and statuses are pre-defined in the
        // persistence
        // this component don't add/remove them

        // get all project categories from the persistence
        ProjectCategory[] projectCategories = manager.getAllProjectCategories();

        // get all project statuses from the persistence
        ProjectStatus[] projectStatuses = manager.getAllProjectStatuses();

        // create the project using a project category and a project status. The
        // project id will be zero,
        // which mean it isn't persisted
        Project project = new Project(projectCategories[0], projectStatuses[0]);

        // persist the project instance with the operator name "admin"
        // the operator will be the creation/modification of this project
        // instance
        manager.createProject(project, "admin");

        // after creation, new values will be set to the project instance such
        // as
        // project id, creation user, creation date, modification user,
        // modification date.

        // set new project category to the project instance
        project.setProjectCategory(projectCategories[1]);

        // update project with the operator "programmer"
        manager.updateProject(project, "no reason", "programmer");

        // project types can also be retrieved from the persistence, each
        // project type can contains
        // 0-n project category.
        ProjectType[] projectTypes = manager.getAllProjectTypes();

        // to add a property to the project, first we have to get the defined
        // property type
        // because only defined property name can be persisted
        ProjectPropertyType[] definedPropTypes = manager.getAllProjectPropertyTypes();

        // get a property names from defined types
        String definedName1 = definedPropTypes[0].getName();
        String definedName2 = definedPropTypes[1].getName();

        // add the properties to the project instance with some value
        project.setProperty(definedName1, "value1");
        project.setProperty(definedName2, "value2");

        // remove a property from the project by setting its value to null
        project.setProperty(definedName2, null);

        // update the project
        manager.updateProject(project, "property updated", "programmer");
    }

    /**
     * Search for projects using various conditions.
     *
     * @throws Exception
     *             throw exception to junit.
     */
    @SuppressWarnings({"unused", "unchecked"})
    public void testDemo2() throws Exception {

        // create manager using default namespace
        ProjectManager manager = new ProjectManagerImpl();
        // Searching requires to build a search filter/
        // then pass it to the ProjectManagerImpl#searchProjects() method

        // build filter to search for project with category id = 1, similar
        // methods are defined
        // for status id and type id
        Filter categoryIdFilter = ProjectFilterUtility.buildCategoryIdEqualFilter(1);

        // search for projects
        Project[] searchResult = manager.searchProjects(categoryIdFilter);

        // build filter to search for project with category name "Database",
        // similar methods are defined
        // for status name and type name
        Filter categoryNameFilter = ProjectFilterUtility.buildCategoryNameEqualFilter("Database");

        // each type of filter has two format: EqualFilter and InFilter
        // to search for status id = 1,2,3, use this
        List statusIds = new ArrayList();
        statusIds.add(new Long(1));
        statusIds.add(new Long(2));
        statusIds.add(new Long(3));
        Filter statusIdsFilter = ProjectFilterUtility.buildStatusIdInFilter(statusIds);

        // search for project using its property name, value or combination
        // similar methods are defined for resource properties.
        // build filter to search for project which contains property name
        // "testName"
        Filter propertyNameFilter = ProjectFilterUtility.buildProjectPropertyNameEqualFilter("testName");

        // build filter to search for project which contains property with value
        // "testValue"
        Filter propertyValueFilter = ProjectFilterUtility.buildProjectPropertyValueEqualFilter("testValue");

        // build filter to search for project which contains property with name
        // "testName" and value "testValue"
        Filter propertyFilter = ProjectFilterUtility.buildProjectPropertyEqualFilter("testName", "testValue");

        // filter can be combined to create new filters
        Filter projectType = ProjectFilterUtility.buildTypeIdEqualFilter(1);
        Filter projectStatus = ProjectFilterUtility.buildStatusIdEqualFilter(1);
        // create new filter using AND filter
        Filter andFilter = ProjectFilterUtility.buildAndFilter(projectType, projectStatus);
        // create new filter using OR filter
        Filter orFilter = ProjectFilterUtility.buildOrFilter(projectType, projectStatus);
        // create new filter using NOT filter
        Filter notFilter = ProjectFilterUtility.buildNotFilter(projectType);

    }

    /**
     * <p>
     * Demonstrates how to CRUD operations for the file types, prizes and studio specification.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    @SuppressWarnings("unused")
    public void testDemo3() throws Exception {
        // create manager using default namespace
        ProjectManager manager = new ProjectManagerImpl();

        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        manager.createProject(project, "user");

        long projectId = project.getId();

        // get projects by directProjectId
        Project[] projects = manager.getProjectsByDirectProjectId(1);

        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);

        // create FileType entity
        fileType = manager.createFileType(fileType, "admin");

        // update FileType entity
        fileType.setBundledFile(true);
        fileType.setSort(2);
        fileType.setExtension("extension3");
        manager.updateFileType(fileType, "admin");

        // remove FileType entity
        manager.removeFileType(fileType, "admin");

        fileType = TestHelper.createFileType("description 2", "extension 2", true, false, 1);
        manager.createFileType(fileType, "admin");

        // get all FileTypes
        FileType[] fileTypes = manager.getAllFileTypes();

        // get all FileTypes by project id
        fileTypes = manager.getProjectFileTypes(1);

        // update relationship for project and FileTypes
        manager.updateProjectFileTypes(1, Arrays.asList(fileTypes), "admin");

        // get all PrizeTypes
        PrizeType[] prizeTypes = manager.getPrizeTypes();

        Prize prize = TestHelper.createPrize(1, 600.00, 5);

        // create Prize entity
        prize = manager.createPrize(prize, "admin");

        // update Prize entity
        prize.setPlace(2);
        prize.setNumberOfSubmissions(10);
        manager.updatePrize(prize, "admin");

        // remove Prize entity
        manager.removePrize(prize, "admin");

        // get all Prizes by project id
        Prize[] prizes = manager.getProjectPrizes(1);

        // update relationship for project and Prizes
        manager.updateProjectPrizes(1, Arrays.asList(prizes), "admin");

        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        // create ProjectStudioSpecification entity
        spec = manager.createProjectStudioSpecification(spec, "admin");

        // update ProjectStudioSpecification entity
        spec.setSubmittersLockedBetweenRounds(false);
        spec.setGoals("goal");
        manager.updateProjectStudioSpecification(spec, "admin");

        // remove ProjectStudioSpecification entity
        manager.removeProjectStudioSpecification(spec, "admin");

        // get all ProjectStudioSpecification by project id
        spec = manager.getProjectStudioSpecification(1);
        spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience", "roundOneIntroduction1");

        // update relationship for project and ProjectStudioSpecification
        manager.updateStudioSpecificationForProject(spec, 1, "admin");
    }
}
