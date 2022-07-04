/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.model.MockIdentifiableEntity;

/**
 * <p>This is mock implementation of <code>{@link GenericDAOImpl}</code> class used only for testing.</p>
 *
 * <p>This dao uses a not mapped class to cause a hibernate error during persistence operation during testing.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockIdentifiableEntityDAO extends GenericDAOImpl<MockIdentifiableEntity> {
}
