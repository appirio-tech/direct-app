/*
 * Copyright (C) 2008 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

/**
 * <p>
 * Represents the competition type.
 * </p>
 * 
 * <p>
 * TopCoder Service Layer Integration 3 Assembly change: new competition types are added.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page v1.0)
 * - ALGORITHM competition type is added.
 * </p>
 *
 * @author FireIce, Veve
 * @version 1.1
 */
public enum CompetionType {
    STUDIO,
    CONCEPTUALIZATION,
    SPECIFICATION,
    ARCHITECTURE,
    COMPONENT_DESIGN,
    COMPONENT_DEVELOPMENT,
    ASSEMBLY,
    TESTING,
    SOFTWARE,
    ALGORITHM
}
