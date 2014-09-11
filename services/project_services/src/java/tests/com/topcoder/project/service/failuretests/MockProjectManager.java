package com.topcoder.project.service.failuretests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implemetation of ProjectManager.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {
    /**
     * <p>
     * Represents the state of the mock.
     * </p>
     */
    private byte state = 0x0;

    /**
     * @see com.topcoder.management.project.ProjectManager#createProject
     *      (com.topcoder.management.project.Project, java.lang.String)
     */
    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getAllProjectCategories()
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        if (this.state == 0x1) {
            throw new PersistenceException("Fail");
        }
        return new ProjectCategory[0];
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getAllProjectPropertyTypes()
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getAllProjectStatuses()
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getAllProjectTypes()
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getProject(long)
     */
    public Project getProject(long arg0) throws PersistenceException {
        if (this.state == 0x3) {
            throw new PersistenceException("Fail");
        }
        if (arg0 == 1) {
            Project ret = new Project(1, new ProjectCategory(1, "Java", new ProjectType(1, "Level1")),
                    new ProjectStatus(1, "active"));
            ret.setProperty("External Reference ID", "1");
            return ret;
        } else if (arg0 == 3) {
            Project ret = new Project(3, new ProjectCategory(1, ".NET", new ProjectType(1, "Level1")),
                    new ProjectStatus(1, "active"));
            ret.setProperty("External Reference ID", "3");
            return ret;
        } else {
            return null;
        }
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getProjects(long[])
     */
    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#getUserProjects(long)
     */
    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#searchProjects(com.topcoder.search.builder.filter.Filter)
     */
    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        if (this.state == 0x2) {
            throw new PersistenceException("Fail");
        }
        return null;
    }

    /**
     * @see com.topcoder.management.project.ProjectManager#updateProject
     *      (com.topcoder.management.project.Project, java.lang.String, java.lang.String)
     */
    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException,
            ValidationException {
    }

    /**
     * <p>
     * Sets the state of the mock.
     * </p>
     * 
     * @param state
     */
    public void setState(byte state) {
        this.state = state;
    }
}
