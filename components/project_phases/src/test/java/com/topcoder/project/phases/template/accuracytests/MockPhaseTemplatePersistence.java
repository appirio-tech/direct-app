/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;

/**
 * mock class only for testing purpose.
 * @author FireIce
 * @version 1.0
 */
public class MockPhaseTemplatePersistence implements PhaseTemplatePersistence {

    /**
     * default no-arg constructor.
     */
    public MockPhaseTemplatePersistence() {
    }

    /**
     * constructor which use file to config.
     */
    public MockPhaseTemplatePersistence(String namespace) {
    }

    /**
     * mock method.
     */
    public void generatePhases(String templateName, Project project) throws PhaseGenerationException,
        PersistenceException {
        int count = "FireIce".equals(templateName) ? 5 : 6;

        for (int i = 0; i < count; i++) {
            project.addPhase(new Phase(project, 1000));
        }
    }

    /**
     * mock method.
     */
    public String[] getAllTemplateNames() {
        return new String[0];
    }

}
