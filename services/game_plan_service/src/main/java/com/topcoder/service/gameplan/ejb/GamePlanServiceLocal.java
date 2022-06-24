/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan.ejb;

import com.topcoder.service.gameplan.GamePlanService;

import javax.ejb.Local;

/**
 * <p>This interface represents the local interface for <code>GamePlanService</code> session bean.</p>
 *
 * <p><b>Thread Safety</b>: Implementations of this interface must be thread safe when entities passed to them are used
 * in thread safe manner by the caller.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
@Local
public interface GamePlanServiceLocal extends GamePlanService {
}

